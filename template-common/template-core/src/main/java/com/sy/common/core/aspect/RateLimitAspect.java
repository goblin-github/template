package com.sy.common.core.aspect;

import com.sy.common.core.anno.RateLimit;
import com.sy.common.core.base.BaseRCodeEnum;
import com.sy.common.core.exception.BusinessException;
import com.sy.common.core.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author Monster
 * @version v1.0
 */
@Aspect
@Component
@Slf4j
@Order(4)
public class RateLimitAspect {

    private static final String RATE_LIMIT_PREFIX = "access_rate_limit:";

    @Pointcut("execution(public * com.sy.*..controller..*.*(..)) && @annotation(com.sy.common.core.anno.RateLimit)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String requestKey = RATE_LIMIT_PREFIX + joinPoint.getTarget().getClass().getName() + "." + method.getName();

        RateLimit rateLimitAnno = method.getAnnotation(RateLimit.class);
        Duration duration = buildDuration(rateLimitAnno.times(), rateLimitAnno.timeUnit());

        RRateLimiter rateLimiter = RedisUtils.REDISSON_CLIENT.getRateLimiter(requestKey);
        if (!rateLimiter.isExists()) {
            rateLimiter.trySetRate(RateType.OVERALL, rateLimitAnno.maxCount(), duration, duration);
        }
        try {
            if (!rateLimiter.tryAcquire()) {
                throw new BusinessException(BaseRCodeEnum.PLEASE_TRY_AGAIN_LATER);
            }
            return joinPoint.proceed();
        } catch (BusinessException businessException) {
            throw businessException;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将时间单位和时间间隔转换为 Duration 对象
     *
     * @param times    时间间隔数量
     * @param timeUnit 时间单位
     * @return 对应的 Duration 实例
     */
    public static Duration buildDuration(int times, TimeUnit timeUnit) {
        return switch (timeUnit) {
            case NANOSECONDS -> Duration.ofNanos(times);
            case MICROSECONDS -> Duration.ofNanos(TimeUnit.MICROSECONDS.toNanos(times));
            case MILLISECONDS -> Duration.ofMillis(times);
            case SECONDS -> Duration.ofSeconds(times);
            case MINUTES -> Duration.ofMinutes(times);
            case HOURS -> Duration.ofHours(times);
            case DAYS -> Duration.ofDays(times);
        };
    }
}
