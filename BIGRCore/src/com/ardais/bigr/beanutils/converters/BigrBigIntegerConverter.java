package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.BigIntegerConverter;

public final class BigrBigIntegerConverter extends BigrConverterBase {

    public BigrBigIntegerConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new BigIntegerConverter());
    }

    public BigrBigIntegerConverter(Object defaultValue) {
        super(
            true,
            false,
            new BigIntegerConverter(defaultValue));
    }

}
