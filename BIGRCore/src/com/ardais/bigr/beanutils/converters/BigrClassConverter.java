package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.ClassConverter;

public final class BigrClassConverter extends BigrConverterBase {

    public BigrClassConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new ClassConverter());
    }

    public BigrClassConverter(Object defaultValue) {
        super(
            true,
            false,
            new ClassConverter(defaultValue));
    }

}
