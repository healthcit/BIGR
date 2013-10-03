package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.LongArrayConverter;

public final class BigrLongArrayConverter extends BigrConverterBase {

    public BigrLongArrayConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new long[0],
            new LongArrayConverter());
    }

    public BigrLongArrayConverter(Object defaultValue) {
        super(
            true,
            false,
            new LongArrayConverter(defaultValue));
    }

}
