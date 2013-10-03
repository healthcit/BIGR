package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.ShortConverter;

public final class BigrShortConverter extends BigrConverterBase {

    public BigrShortConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new ShortConverter());
    }

    public BigrShortConverter(Object defaultValue) {
        super(
            true,
            false,
            new ShortConverter(defaultValue));
    }

}
