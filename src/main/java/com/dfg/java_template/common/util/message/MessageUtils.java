package com.dfg.java_template.common.util.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 获取i18n资源文件
 *
 * @author zgc
 */
@Component
@Slf4j
public class MessageUtils {

    private static MessageSource staticMessageSource;

    @Resource
    private MessageSource messageSource;

    @PostConstruct
    public void init() {
        staticMessageSource = messageSource;
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param message 消息键
     * @param args    参数
     * @return 获取国际化翻译值
     */
    public static String message(String message, Object... args) {
        return staticMessageSource.getMessage(message, (args != null && args.length > 0) ? args : null, LocaleContextHolder.getLocale());
    }
}
