package com.sy.common.core.aspect;

import com.sy.common.core.anno.Debounce;
import com.sy.common.core.anno.DebounceParam;
import com.sy.common.core.base.BaseRCodeEnum;
import com.sy.common.core.exception.BusinessException;
import com.sy.common.core.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 接口防抖切面处理器
 *
 * @author Monster
 * @version v1.0
 */
@Aspect
@Component
@Slf4j
public class DebounceAspect {
    @Pointcut("execution(public * com.sy.*..controller..*.*(..)) && @annotation(com.sy.common.core.anno.Debounce)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Debounce debounce = method.getAnnotation(Debounce.class);

        int expireTime = debounce.expire();
        TimeUnit timeUnit = debounce.timeUnit();
        String key = buildDebounceKey(joinPoint);

        //获取自定义key
        final RLock lock = RedisUtils.REDISSON_CLIENT.getLock(key);
        boolean isLocked = false;

        try {
            //尝试抢占锁
            isLocked = lock.tryLock(0L, expireTime, timeUnit);
            if (!isLocked) {
                throw new BusinessException(BaseRCodeEnum.REPEAT_SUBMIT);
            }
            return joinPoint.proceed();
        } catch (BusinessException businessException) {
            throw businessException;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            //释放锁
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 构造接口防抖缓存key
     *
     * @param joinPoint 切点
     * @return 防抖缓存key
     */
    public String buildDebounceKey(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取参数
        Object[] args = joinPoint.getArgs();
        // 获取注解
        final Parameter[] parameters = method.getParameters();
        Debounce cacheLock = method.getAnnotation(Debounce.class);
        String prefix = cacheLock.prefix();
        String requestKey = "." + joinPoint.getTarget().getClass().getName() + "." + method.getName();

        StringBuilder paramKey = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            DebounceParam cacheParam = parameters[i].getAnnotation(DebounceParam.class);
            if (Objects.isNull(cacheParam)) {
                continue;
            }
            paramKey.append(cacheLock.delimiter()).append(args[i]);
        }
        // 如果方法参数没有CacheParam注解 从参数类的内部尝试获取
        if (StringUtils.isEmpty(paramKey.toString())) {
            for (int i = 0; i < parameters.length; i++) {
                final Object object = args[i];
                if (Objects.isNull(object)) {
                    continue;
                }
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    final DebounceParam annotation = field.getAnnotation(DebounceParam.class);
                    if (Objects.isNull(annotation)) {
                        continue;
                    }
                    field.setAccessible(true);
                    try {
                        paramKey.append(cacheLock.delimiter()).append(ReflectionUtils.getField(field, object));
                    } catch (Exception e) {
                        log.error("Error build debounce key: {}", field.getName(), e);
                    }
                }
            }
        }
        return prefix + requestKey + paramKey;
    }
}
