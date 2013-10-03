package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.ByteConverter;

public final class BigrByteConverter extends BigrConverterBase {

    public BigrByteConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new ByteConverter());
    }

    public BigrByteConverter(Object defaultValue) {
        super(
            true,
            false,
            new ByteConverter(defaultValue));
    }

}
