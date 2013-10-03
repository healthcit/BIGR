package com.ardais.bigr.beanutils.converters;

import org.apache.commons.beanutils.converters.SqlDateConverter;

import com.ardais.bigr.api.ApiFunctions;

public final class BigrSqlDateConverter extends BigrConverterBase {

    public BigrSqlDateConverter(boolean returnNullOnNullWhenNoDefault) {
        super(
            false,
            returnNullOnNullWhenNoDefault,
            new SqlDateConverter());
    }

    public BigrSqlDateConverter(Object defaultValue) {
        super(
            true,
            false,
            new SqlDateConverter(defaultValue));
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
    String[] dateValues = ApiFunctions.splitAndTrim(value.toString(), "/");
    if (dateValues.length == 3) {
      String dateValue = dateValues[2] + "-" + dateValues[0] + "-" + dateValues[1];
      newValue = dateValue;
    }

     return _converter.convert(type, newValue);
  }

}
