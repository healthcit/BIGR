package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.CharacterConverter;

public final class BigrCharacterConverter extends BigrConverterBase {

    public BigrCharacterConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new CharacterConverter());
    }

    public BigrCharacterConverter(Object defaultValue) {
        super(
            true,
            false,
            new CharacterConverter(defaultValue));
    }

}
