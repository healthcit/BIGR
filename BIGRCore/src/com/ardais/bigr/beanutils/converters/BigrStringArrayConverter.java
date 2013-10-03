package com.ardais.bigr.beanutils.converters;

import java.util.Collection;

import org.apache.commons.beanutils.converters.StringArrayConverter;

import com.ardais.bigr.api.ApiFunctions;

public final class BigrStringArrayConverter extends BigrConverterBase {

  public BigrStringArrayConverter(boolean returnNullOnNullWhenNoDefault) {
    super(false, returnNullOnNullWhenNoDefault, new String[0], new StringArrayConverter());
  }

  public BigrStringArrayConverter(Object defaultValue) {
    super(true, false, new StringArrayConverter(defaultValue));
  }

  /**
   * Converts the specified value to a string array and returns it.
   * 
   * @param  type  data type to which the value should be converted
   * @param  value  the value to be converted.  It may be a class implementing Collection or
   *                an array.
   */
  public Object convert(Class type, Object value) {

    if (shouldReturnNullValue(value)) {
      return _nullValueToReturn;
    }

    // If the value passed in is an instance of a Collection, then create an array from
    // it and return it.  
    if (value instanceof Collection) {
      return ApiFunctions.toStringArray((Collection)value);
    }

    else {
      return _converter.convert(type, value);
    }    
  }

}
