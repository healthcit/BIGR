package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.IntegerArrayConverter;

public final class BigrIntegerArrayConverter extends BigrConverterBase {

    public BigrIntegerArrayConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new int[0],
            new IntegerArrayConverter());
    }

    public BigrIntegerArrayConverter(Object defaultValue) {
        super(
            true,
            false,
            new IntegerArrayConverter(defaultValue));
    }

}
