package com.fintech.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

@UtilityClass
public class ValidateUtils {

    public <T> void requireNotNullOrEmpty(T values, Supplier<RuntimeException> exceptionSupplier) {
        if (isNullOrEmpty(values)) {
            throw exceptionSupplier.get();
        }
    }

    @SafeVarargs
    public <T> boolean isNullOrEmpty(T... values) {
        if (values == null || values.length == 0) {
            return true;
        }

        for (T value : values) {
            if (isNullOrEmpty(value)) {
                return true;
            }
        }

        return false;
    }

    @SafeVarargs
    public <T> boolean isNotNullOrEmpty(T... values) {
        return !isNullOrEmpty(values);
    }

    private <T> boolean isNullOrEmpty(T value) {
        if (value == null) {
            return true;
        }

        if (value instanceof String) {
            return String.valueOf(value).isBlank();
        } else if (value instanceof Collection<?>) {
            return ((Collection<?>) value).isEmpty();
        } else if (value instanceof Map<?, ?>) {
            return ((Map<?, ?>) value).isEmpty();
        } else if (value instanceof Object[]) {
            return ObjectUtils.isEmpty(value);
        } else if (value.getClass().isArray()) {
            return Array.getLength(value) == 0;
        } else {
            return false;
        }
    }

}