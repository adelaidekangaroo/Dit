package org.sber.simonov.dit.util;

import org.sber.simonov.dit.exception.NotFoundException;
import org.sber.simonov.dit.model.Message;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNew(Message entity) {
        if (entity.getId() != null) {
            entity.setId(null);
        }
    }

    public static <T> T checkNotFound(T object, Class<T> clazz) {
        if (object == null) {
            throw new NotFoundException(String.format("%s not found", clazz.getCanonicalName()));
        } else {
            return object;
        }
    }
}