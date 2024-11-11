package com.sy.common.core.util;


import com.sy.common.core.base.BaseRCodeInterface;
import com.sy.common.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Monster
 * @version v1.0
 */
public class MessageUtils {

    private static final MessageSource MESSAGE_SOURCE = SpringUtils.getContext().getBean(MessageSource.class);

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args) {
        return MESSAGE_SOURCE.getMessage(code, args, code, LocaleContextHolder.getLocale());
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param baseErrorInfoInterface 消息键
     * @return 获取国际化翻译值
     */
    public static String message(BaseRCodeInterface baseErrorInfoInterface, Object... args) {
        String key = baseErrorInfoInterface.getMsg();
        if (StringUtils.isNotBlank(baseErrorInfoInterface.getI18nKey())) {
            key = baseErrorInfoInterface.getI18nKey();
        }
        return MESSAGE_SOURCE.getMessage(key, args, key, LocaleContextHolder.getLocale());
    }

    public static String message(BusinessException businessException, Object... args) {
        String key = businessException.getMessage();
        if (StringUtils.isNotBlank(businessException.getI18nKey())) {
            key = businessException.getI18nKey();
        }
        return MESSAGE_SOURCE.getMessage(key, args, key, LocaleContextHolder.getLocale());
    }
}
