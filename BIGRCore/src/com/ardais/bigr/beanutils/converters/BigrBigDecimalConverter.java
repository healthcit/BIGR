package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.BigDecimalConverter;

public final class BigrBigDecimalConverter extends BigrConverterBase {

    public BigrBigDecimalConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new BigDecimalConverter());
    }

    public BigrBigDecimalConverter(Object defaultValue) {
        super(
            true,
            false,
            new BigDecimalConverter(defaultValue));
    }

}
