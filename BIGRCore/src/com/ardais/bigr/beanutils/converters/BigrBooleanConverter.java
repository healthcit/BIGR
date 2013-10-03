package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.BooleanConverter;

public final class BigrBooleanConverter extends BigrConverterBase {

    public BigrBooleanConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new BooleanConverter());
    }

    public BigrBooleanConverter(Object defaultValue) {
        super(
            true,
            false,
            new BooleanConverter(defaultValue));
    }

}
