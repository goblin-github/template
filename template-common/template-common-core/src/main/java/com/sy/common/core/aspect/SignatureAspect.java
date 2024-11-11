package com.sy.common.core.aspect;


import com.sy.common.core.base.BaseRCodeEnum;
import com.sy.common.core.base.BaseRequestParam;
import com.sy.common.core.constant.CommonConstant;
import com.sy.common.core.exception.BusinessException;
import com.sy.common.core.util.MD5Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author Monster
 * @version v1.0
 */
@Aspect
@Component
@Order(2)
public class SignatureAspect {

    @Value("${security.key}")
    private String securityKey;

    @Pointcut("@within(com.sy.common.core.anno.Signature) || @annotation(com.sy.common.core.anno.Signature)")
    public void pointSignature() {
    }

    @Before("pointSignature() ")
    public void signatureValidation(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (Objects.nonNull(attributes)) {
            request = attributes.getRequest();
        }
        String requestSign = Objects.nonNull(request) ? request.getHeader(CommonConstant.SIGN) : null;
        Object arg = joinPoint.getArgs()[0];
        if (Objects.isNull(arg) || !(arg instanceof BaseRequestParam baseRequestParam) || Objects.isNull(baseRequestParam.getTimestamp())) {
            throw new BusinessException(BaseRCodeEnum.SIGN_ERROR);
        }
        String sign;
        sign = "timestamp=" + baseRequestParam.getTimestamp() + "&key=" + securityKey;
        String signStr = MD5Utils.md5Encode(MD5Utils.md5Encode(sign));
        if (!signStr.equalsIgnoreCase(requestSign)) {
            throw new BusinessException(BaseRCodeEnum.SIGN_ERROR);
        }
    }
}