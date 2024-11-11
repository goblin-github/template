package com.sy.common.core.base;

import com.sy.common.core.exception.BusinessException;
import com.sy.common.core.util.MessageUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Monster
 * @version v1.0
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;


    private static <T> Result<T> build(T data, Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> success() {
        return build(BaseRCodeEnum.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return Result.build(data, BaseRCodeEnum.SUCCESS.getCode(), MessageUtils.message(BaseRCodeEnum.SUCCESS.getMsg()));
    }

    public static <T> Result<T> fail() {
        return build(BaseRCodeEnum.ERROR);
    }

    public static <T> Result<T> fail(String msg) {
        return Result.build(null, BaseRCodeEnum.ERROR.getCode(), MessageUtils.message(msg));
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return Result.build(null, code, MessageUtils.message(msg));
    }

    public static <T> Result<T> fail(BaseRCodeInterface rCodeInterface) {
        return build(rCodeInterface);
    }

    public static <T> Result<T> fail(BusinessException businessException) {
        return build(businessException);
    }


    private static <T> Result<T> build(BaseRCodeInterface rCodeInterface) {
        return Result.build(null, rCodeInterface.getCode(), MessageUtils.message(rCodeInterface));
    }

    private static <T> Result<T> build(BusinessException businessException) {
        return Result.build(null, businessException.getCode(), MessageUtils.message(businessException));
    }

}
