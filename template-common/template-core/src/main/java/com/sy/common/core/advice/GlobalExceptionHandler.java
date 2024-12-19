package com.sy.common.core.advice;


import com.sy.common.core.base.BaseRCodeEnum;
import com.sy.common.core.base.Result;
import com.sy.common.core.exception.BusinessException;
import com.sy.common.core.exception.UtilException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author Monster
 * @version v1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(value = BusinessException.class)
    public Result<String> businessHandle(BusinessException e) {
        log.error("业务异常！message:{}", e.getMessage(), e);
        return Result.fail(e);
    }

    @ExceptionHandler(value = UtilException.class)
    public Result<String> utilHandle(UtilException e) {
        log.error("工具类异常！message:{}", e.getMessage(), e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandle(Exception e) {
        log.error("未知异常！", e);
        return Result.fail(BaseRCodeEnum.ERROR);
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public Result<String> duplicateKeyException(DuplicateKeyException e) {
        log.error("主键重复异常！", e);
        return Result.fail(BaseRCodeEnum.REPEAT_SUBMIT);
    }

    @ExceptionHandler(value = {BindException.class, ConstraintViolationException.class})
    public Result<String> validException(Exception e) {
        String message = BaseRCodeEnum.PARAM_ERROR.getMsg();
        if (e instanceof ConstraintViolationException ex) {
            message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        } else if (e instanceof BindException ex) {
            message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        }
        log.error("参数校验异常！message:{}", message, e);
        return Result.fail(BaseRCodeEnum.PARAM_ERROR.getCode(), message);
    }
}
