package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.SqlTimeConverter;

public final class BigrSqlTimeConverter extends BigrConverterBase {

    public BigrSqlTimeConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new SqlTimeConverter());
    }

    public BigrSqlTimeConverter(Object defaultValue) {
        super(
            true,
            false,
            new SqlTimeConverter(defaultValue));
    }

}
