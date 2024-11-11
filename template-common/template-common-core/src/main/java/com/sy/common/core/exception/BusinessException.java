package com.sy.common.core.exception;

import com.sy.common.core.base.BaseRCodeInterface;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Monster
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;
    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;
    /**
     * 国际化key
     */
    private String i18nKey;

    public BusinessException(BaseRCodeInterface errorInfoInterface) {
        this.code = errorInfoInterface.getCode();
        this.message = errorInfoInterface.getMsg();
        this.i18nKey = errorInfoInterface.getI18nKey();
    }

    public BusinessException(BaseRCodeInterface errorInfoInterface, String detailMessage) {
        this.code = errorInfoInterface.getCode();
        this.message = errorInfoInterface.getMsg();
        this.i18nKey = errorInfoInterface.getI18nKey();
        this.detailMessage = detailMessage;
    }
}

