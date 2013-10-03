package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.DoubleArrayConverter;

public final class BigrDoubleArrayConverter extends BigrConverterBase {

    public BigrDoubleArrayConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new double[0],
            new DoubleArrayConverter());
    }

    public BigrDoubleArrayConverter(Object defaultValue) {
        super(
            true,
            false,
            new DoubleArrayConverter(defaultValue));
    }

}
