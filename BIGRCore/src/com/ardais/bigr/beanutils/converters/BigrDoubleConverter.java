package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.DoubleConverter;

public final class BigrDoubleConverter extends BigrConverterBase {

    public BigrDoubleConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new DoubleConverter());
    }

    public BigrDoubleConverter(Object defaultValue) {
        super(
            true,
            false,
            new DoubleConverter(defaultValue));
    }

}
