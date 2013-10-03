package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.CharacterArrayConverter;

public final class BigrCharacterArrayConverter extends BigrConverterBase {

    public BigrCharacterArrayConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new char[0],
            new CharacterArrayConverter());
    }

    public BigrCharacterArrayConverter(Object defaultValue) {
        super(
            true,
            false,
            new CharacterArrayConverter(defaultValue));
    }

}
