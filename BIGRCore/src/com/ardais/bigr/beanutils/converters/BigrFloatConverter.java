package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.FloatConverter;

public final class BigrFloatConverter extends BigrConverterBase {

    public BigrFloatConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new FloatConverter());
    }

    public BigrFloatConverter(Object defaultValue) {
        super(
            true,
            false,
            new FloatConverter(defaultValue));
    }

}
