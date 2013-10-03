package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.LongConverter;

public final class BigrLongConverter extends BigrConverterBase {

    public BigrLongConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new LongConverter());
    }

    public BigrLongConverter(Object defaultValue) {
        super(
            true,
            false,
            new LongConverter(defaultValue));
    }

}
