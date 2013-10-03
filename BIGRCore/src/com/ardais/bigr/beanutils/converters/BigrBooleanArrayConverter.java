package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.BooleanArrayConverter;

public final class BigrBooleanArrayConverter extends BigrConverterBase {

    public BigrBooleanArrayConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new boolean[0],
            new BooleanArrayConverter());
    }

    public BigrBooleanArrayConverter(Object defaultValue) {
        super(
            true,
            false,
            new BooleanArrayConverter(defaultValue));
    }

}
