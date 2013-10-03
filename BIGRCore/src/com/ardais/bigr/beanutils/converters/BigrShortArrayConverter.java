package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.ShortArrayConverter;

public final class BigrShortArrayConverter extends BigrConverterBase {

    public BigrShortArrayConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new short[0],
            new ShortArrayConverter());
    }

    public BigrShortArrayConverter(Object defaultValue) {
        super(
            true,
            false,
            new ShortArrayConverter(defaultValue));
    }

}
