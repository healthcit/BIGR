package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.ByteArrayConverter;

public final class BigrByteArrayConverter extends BigrConverterBase {

    public BigrByteArrayConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new byte[0],
            new ByteArrayConverter());
    }

    public BigrByteArrayConverter(Object defaultValue) {
        super(
            true,
            false,
            new ByteArrayConverter(defaultValue));
    }

}
