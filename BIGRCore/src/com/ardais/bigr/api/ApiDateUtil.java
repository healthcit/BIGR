package com.ardais.bigr.api;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ApiDateUtil {

  public ApiDateUtil() {
    super();
  }
 

  public static String convertDateToString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    String dateString = formatter.format(date);
    return dateString;
  }

  public static java.sql.Timestamp convertStringToTimeStamp(String param, String format) {
    if (ApiFunctions.isEmpty(param)) {
      return null;
    }
    if (ApiFunctions.isEmpty(format)) {
      throw new IllegalArgumentException("ApiDateUtil: Value for format must not be null or empty.");
    }
    else {
      SimpleDateFormat formatter = new SimpleDateFormat(format);
      formatter.setLenient(false);

      ParsePosition pos = new ParsePosition(0);
      Date date = formatter.parse(param, pos);
      if (pos.getErrorIndex() > -1 || pos.getIndex() < param.length()) {
        date = null;
      }
      if (date == null) {
        throw new IllegalArgumentException(
          "Cannot parse date '" + param + "' using format '" + format + "'.");
      }
      return (new java.sql.Timestamp(date.getTime()));
    }
  }

  public static Date getDatePart(Date date) {
    Calendar calResult = Calendar.getInstance();

    calResult.setTime(date);

    calResult.set(Calendar.HOUR_OF_DAY, 0);
    calResult.set(Calendar.MINUTE, 0);
    calResult.set(Calendar.SECOND, 0);
    calResult.set(Calendar.MILLISECOND, 0);

    return calResult.getTime();
  }

  /**
   * This is a wrapper for {@link Calendar#add(int, int)} for doing date arithmetic.  See
   * the documentation of that method for descriptions of what to pass to the <code>field</code>
   * and <code>amount</code> parameters.
   * 
   * @param date The date to add to.
   * @param field The date field to add to.
   * @param amount The amount to add to the date field.
   * @return The result of the date arithmetic.
   */
  public static Date add(Date date, int field, int amount) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(field, amount);
    return cal.getTime();
  }
}
