package com.company.gojob.common_service.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import java.util.List;
import java.util.Locale;

@Configuration
public class LocalResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept-Language");
        List<Locale.LanguageRange> currentLanguage = Locale.LanguageRange.parse(acceptHeader);

        List<Locale> supportLanguages = List.of(
                new Locale("en"),
                new Locale("vi")
        );

        return StringUtils.hasLength(acceptHeader)
                ? Locale.lookup(currentLanguage, supportLanguages)
                : Locale.getDefault();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }
}

