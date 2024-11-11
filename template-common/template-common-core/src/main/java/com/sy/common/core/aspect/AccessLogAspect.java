package com.sy.common.core.aspect;


import com.google.common.collect.Maps;
import com.sy.common.core.constant.CommonConstant;
import com.sy.common.core.util.JacksonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

/**
 * @author Monster
 * @version v1.0
 */
@Aspect
@Component
@Slf4j(topic = "access.log")
public class AccessLogAspect {

    @Pointcut("execution(public * com.sy.*..controller..*.*(..))")
    public void access() {
    }

    @Before("access()")
    public void doBefore(JoinPoint joinPoint) {
        Object attributes = RequestContextHolder.getRequestAttributes();
        if (!(attributes instanceof ServletRequestAttributes servletAttributes)) {
            return;
        }
        String uuid = MDC.get(CommonConstant.REQUEST_ID);
        HttpServletRequest request = servletAttributes.getRequest();
        HttpServletResponse response = servletAttributes.getResponse();
        if (Objects.nonNull(response) && StringUtils.isNotBlank(uuid)) {
            response.setHeader(CommonConstant.REQUEST_ID, uuid);
        }
        log.info("ACCESS REQUEST - URI: {}, METHOD: {}, PARAMETERS: {}", request.getRequestURI(), request.getMethod(), getMethodParams(joinPoint));
    }


    /**
     * 获取请求参数
     *
     * @param joinPoint 切点
     * @return 请求参数
     */
    private String getMethodParams(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Map<String, Object> parameters = Maps.newHashMap();
        if (Objects.isNull(signature)) {
            return JacksonUtils.toJson(parameters);
        }
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();
        if (ArrayUtils.isEmpty(args) || ArrayUtils.isEmpty(parameterNames)) {
            return JacksonUtils.toJson(parameters);
        }
        for (int i = 0; i < parameterNames.length; i++) {
            Object arg = args[i];
            if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse || arg instanceof MultipartFile) {
                continue;
            }
            parameters.put(parameterNames[i], arg);
        }
        return JacksonUtils.toJson(parameters);
    }
}
