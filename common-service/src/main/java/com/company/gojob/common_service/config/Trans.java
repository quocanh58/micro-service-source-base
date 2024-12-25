package com.company.gojob.common_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Trans {
    private static ResourceBundleMessageSource messageSource;

    @Autowired
    public Trans(ResourceBundleMessageSource messageSource) {
        Trans.messageSource = messageSource;
    }

    public static String getMessage(String key) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(key, null, locale);
        } catch (NoSuchMessageException e) {
            return key;
        }
    }

    public static String getMessage(String key, Object[] args) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException e) {
            return key;
        }
    }
}
