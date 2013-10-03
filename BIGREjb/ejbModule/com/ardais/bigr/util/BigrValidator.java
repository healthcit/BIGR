package com.ardais.bigr.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValue;
import com.ardais.bigr.orm.helpers.BigrGbossData;

public class BigrValidator {

  private BigrValidator() {
  }

  /**
   * Convenience method for getting a value from a bean property as a
   * <code>String[]</code> array.  A runtime exception will be thrown if the
   * property's value can't be cast to a string array.
   */
  public static String[] getValueAsStringArray(Object bean, String property) {
    Object value = null;

    try {
      value = PropertyUtils.getProperty(bean, property);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return (String[]) value;
  }

  /**
   * Convenience method for getting a value from a bean property as an
   * <code>Object[]</code>.  A runtime exception will be thrown if the
   * property's value can't be cast to a string array.
   */
  public static Object getValueAsObject(Object bean, String property) {
    Object value = null;

    try {
      value = PropertyUtils.getProperty(bean, property);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return value;
  }

  /**
   * <p>Checks if the value can safely be converted to a an Integer.</p>
   *
   * @param   value     The value validation is being performed on.
   * @return the Integer representation of the string, or null if the string can't
   *    be converted to an Integer.
   */
  public static Integer formatInteger(String value) {
    Integer result = null;

    try {
      result = new Integer(value);
    }
    catch (NumberFormatException e) {
      // return null when there's an exception.
    }

    return result;
  }

  /**
   * <p>Checks if the value can safely be converted to an Integer.</p>
   *
   * @param   value     The value validation is being performed on.
   * @return true if the value can be converted to an Integer.
   */
  public static boolean isInteger(String value) {
    return (formatInteger(value) != null);
  }

  /**
   * <p>Checks if the field is a valid date.  The pattern is used with 
   * <code>java.text.SimpleDateFormat</code>.  If strict is true, then the 
   * length will be checked so '2/12/1999' will not pass validation with 
   * the format 'MM/dd/yyyy' because the month isn't two digits. 
   * The setLenient method is set to <code>false</code> for all.</p>
   *
   * @param   value     The value validation is being performed on.
   * @param   datePattern The pattern passed to <code>SimpleDateFormat</code>.
   * @param   strict          Whether or not to have an exact match of the datePattern.
  */
  public static Date formatDate(String value, String datePattern, boolean strict) {
    Date date = null;

    if (value != null && datePattern != null && datePattern.length() > 0) {
      SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
      formatter.setLenient(false);
      ParsePosition pos = new ParsePosition(0);

      date = formatter.parse(value, pos);

      if (pos.getErrorIndex() > -1) {
        date = null;
      }
      else if (strict) {
        // The date validation functions that ship with the Commons Validator
        // don't handle "strict" correctly so we've written our own versions of
        // formatDate and isDate.  The Validator code tests for strict by comparing
        // the pattern length to the value length, which doesn't work for all
        // patterns.  For example, with the pattern "yyyy-MM-dd", the Validator
        // accepts "2003-06-2x" as a valid date, since SimpleDateFormat parses it
        // as June 2, 2003 and ignores the "x" at the end, and the length comparison
        // test passes as well.  I submitted Bugzilla bug 27414, you can track if/when
        // this ever gets fixes in Commons Validator at
        // http://issues.apache.org/bugzilla/show_bug.cgi?id=27414
        //
        if (pos.getIndex() < value.length()) {
          date = null;
        }
      }
    }

    return date;
  }

  /**
   * <p>Checks if the field is a valid date.  The pattern is used with 
   * <code>java.text.SimpleDateFormat</code>.  If strict is true, then the 
   * length will be checked so '2/12/1999' will not pass validation with 
   * the format 'MM/dd/yyyy' because the month isn't two digits. 
   * The setLenient method is set to <code>false</code> for all.</p>
   *
   * @param   value     The value validation is being performed on.
   * @param   datePattern The pattern passed to <code>SimpleDateFormat</code>.
   * @param   strict          Whether or not to have an exact match of the datePattern.
   */
  public static boolean isDate(String value, String datePattern, boolean strict) {
    // The date validation functions that ship with the Commons Validator
    // don't handle "strict" correctly so we've written our own versions of
    // formatDate and isDate.  The Validator code tests for strict by comparing
    // the pattern length to the value length, which doesn't work for all
    // patterns.  For example, with the pattern "yyyy-MM-dd", the Validator
    // accepts "2003-06-2x" as a valid date, since SimpleDateFormat parses it
    // as June 2, 2003 and ignores the "x" at the end, and the length comparison
    // test passes as well.
    //
    return (formatDate(value, datePattern, strict) != null);
  }

  /**
   * Returns an indication of whether the specified code is a valid sample type.
   * 
   * @param  code  the code in question
   * @return  true if the code is a valid sample type; false otherwise
   */
  public static boolean isValidSampleType(String code) {
    Iterator sampleTypes = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_TYPE).getIterator();
    while (sampleTypes.hasNext()) {
      LegalValue sampleType = (LegalValue) sampleTypes.next();
      if (sampleType.getValue().equals(code)) {
        return true;       
      }
    }
    return false;
  }

}
