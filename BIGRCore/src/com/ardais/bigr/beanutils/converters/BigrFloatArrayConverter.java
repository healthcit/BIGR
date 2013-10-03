package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.FloatArrayConverter;

public final class BigrFloatArrayConverter extends BigrConverterBase {

    public BigrFloatArrayConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new float[0],
            new FloatArrayConverter());
    }

    public BigrFloatArrayConverter(Object defaultValue) {
        super(
            true,
            false,
            new FloatArrayConverter(defaultValue));
    }

}
