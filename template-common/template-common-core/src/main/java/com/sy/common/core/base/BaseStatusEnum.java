package com.sy.common.core.base;

import lombok.Getter;

/**
 * @author Monster
 * @version v1.0
 */
@Getter
public enum BaseStatusEnum {
    /**
     * 启用
     */
    ENABLE(0),
    /**
     * 禁用
     */
    DISABLE(1),
    ;
    private final Integer code;

    BaseStatusEnum(Integer code) {
        this.code = code;
    }
}
