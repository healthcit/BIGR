package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.StringConverter;

import com.ardais.bigr.api.ApiFunctions;

public final class BigrStringConverter extends BigrConverterBase {

    public BigrStringConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new StringConverter());
    }

  /**
   * Converts the specified value to a sql Date and returns it.
   * 
   * @param  type  data type to which the value should be converted
   * @param  value  the value to be converted.  It may be a class implementing Collection or
   *                an array.
   */
  public Object convert(Class type, Object value) {

    if (shouldReturnNullValue(value)) {
      return _nullValueToReturn;
    }

    Object newValue = value;
    if ((value instanceof java.sql.Date)){
      String[] dateValues = ApiFunctions.splitAndTrim(value.toString(), "-");
      if (dateValues.length == 3) {
        String dateValue = dateValues[1] + "/" + dateValues[2] + "/" + dateValues[0];
        newValue = dateValue;
      }
    }

     return _converter.convert(type, newValue);
  }

}
