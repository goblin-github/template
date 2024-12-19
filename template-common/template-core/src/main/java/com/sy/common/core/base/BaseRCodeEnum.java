package com.sy.common.core.base;

import lombok.Getter;

/**
 * @author Monster
 * @version v1.0
 */
@Getter
public enum BaseRCodeEnum implements BaseRCodeInterface {

    /**
     * 成功
     */
    SUCCESS(200, "success", "success"),
    /**
     * 参数异常
     */
    PARAM_ERROR(400, "bad param", "bad.param"),
    /**
     * 未登录
     */
    NOT_LOGIN(401, "not login", "not.login"),
    /**
     * 签名错误
     */
    SIGN_ERROR(403, "sign error", "sign.error"),

    /**
     * 请求不支持
     */
    REQUEST_NOT_SUPPORT(405, "request not support", "request.not.support"),
    /**
     * 重复提交
     */
    REPEAT_SUBMIT(409, "repeat submit", "repeat.submit"),
    /**
     * 失败
     */
    ERROR(500, "service internal error", "service.internal.error"),
    /**
     * 请稍后重试
     */
    PLEASE_TRY_AGAIN_LATER(503, "please try again later", "please.try.again.later"),
    ;
    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误描述
     */
    private final String msg;
    /**
     * 国际化key
     */
    private final String i18nKey;

    BaseRCodeEnum(Integer code, String msg, String i18nKey) {
        this.code = code;
        this.msg = msg;
        this.i18nKey = i18nKey;
    }
}
