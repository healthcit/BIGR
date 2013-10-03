package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.IntegerConverter;

public final class BigrIntegerConverter extends BigrConverterBase {

    public BigrIntegerConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new IntegerConverter());
    }

    public BigrIntegerConverter(Object defaultValue) {
        super(
            true,
            false,
            new IntegerConverter(defaultValue));
    }

}
