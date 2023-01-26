package com.test.drone.core.base;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Messages implements IMessages {

    private final MessageSource messageSource;

    public Messages(@Qualifier("messageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String id) {
        return getMessage(id, null);
    }

    @Override
    public String getMessage(String id, Object[] params) {
        if (Objects.isNull(id))
            return Strings.EMPTY;

        String message = messageSource.getMessage(id, params, id, LocaleContextHolder.getLocale());
        if (!id.equals(message))
            return message;

        return messageSource.getMessage(id, params, id, LocaleContextHolder.getLocale());
    }
}
