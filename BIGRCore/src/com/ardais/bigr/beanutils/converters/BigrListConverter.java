package com.ardais.bigr.beanutils.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public class BigrListConverter extends BigrConverterBase {

  /**
   * The default value to return if the value to be converted is null.
   */
  private Object _defaultValue = null;


  /**
   * A flag indicating whether we should we return the default value on conversion errors.
   */
  private boolean _useDefault = false;

  public BigrListConverter(boolean returnNullOnNullWhenNoDefault) {
    super(false, returnNullOnNullWhenNoDefault, new ArrayList());
    _defaultValue = null;
    _useDefault = false;
  }

  public BigrListConverter(Object defaultValue) {
    super(true, false);
    _defaultValue = defaultValue;
    _useDefault = true;
  }

  /**
   * Converts the specified value to a List and returns it.
   * 
   * @param  type  data type to which the value should be converted
   * @param  value  the value to be converted.  It must be a class implementing Collection or
   *                an array.
   */
  public Object convert(Class type, Object value) {

    if (shouldReturnNullValue(value)) {
      return _nullValueToReturn;
    }

    // First check for a null value and return the default if instructed to do so.
    if (value == null) {
      if (_useDefault) {
        return (_defaultValue);
      } 
      else {
        throw new ConversionException("No value specified in BigrListConverter.");
      }
    }
    
    // If the value passed in is an instance of a List, then return it.  
    if (value instanceof List) {
      return value;
    }

    // If the value passed in is an instance of a Collection, then create a new ArrayList from
    // it and return it.  
    else if (value instanceof Collection) {
      return new ArrayList((Collection) value);
    }

    // If the value passed in is an array, then convert the array to a List and return it.  
    else if (value.getClass().isArray()) {
      return Arrays.asList((Object[])value);
    }

    else {
      throw new ConversionException("Could not convert " + value.getClass().getName() + " to a List");
    }    
  }
}
