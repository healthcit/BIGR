package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.SqlTimestampConverter;

public final class BigrSqlTimestampConverter extends BigrConverterBase {

    public BigrSqlTimestampConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new SqlTimestampConverter());
    }

    public BigrSqlTimestampConverter(Object defaultValue) {
        super(
            true,
            false,
            new SqlTimestampConverter(defaultValue));
    }

}
