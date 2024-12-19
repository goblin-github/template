package com.sy.common.core.base;

import lombok.Getter;

/**
 * @author Monster
 * @version v1.0
 */
@Getter
public enum BaseDeleteFlagEnum {
    /**
     * 未删除
     */
    UN_DELETED(0),
    /**
     * 已删除
     */
    DELETED(1),
    ;
    private final Integer code;

    BaseDeleteFlagEnum(Integer code) {
        this.code = code;
    }
}

