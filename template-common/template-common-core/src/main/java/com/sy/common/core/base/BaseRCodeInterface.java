package com.sy.common.core.base;

/**
 * @author Monster
 * @version v1.0
 */
public interface BaseRCodeInterface {

    /**
     * 错误码
     */
    Integer getCode();

    /**
     * 错误描述
     */
    String getMsg();

    /**
     * 获取国际化key
     *
     * @return 国际化key
     */
    String getI18nKey();
}
