package com.ardais.bigr.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;

/**
 * Represents a date and time with variable precision.  Supports precision of day, hour and minute.
 */
public class VariablePrecisionDateTime implements Serializable {

  private static final String DATE_FORMAT = "MM/dd/yyyy";
  private static final String DATE_TIME_FORMAT_HOUR = "MM/dd/yyyy h a";
  private static final String DATE_TIME_FORMAT_HOUR_MINUTE = "MM/dd/yyyy h:mm a";

  private Timestamp _timestamp;
  private String _precision;

  private String _date;
  private String _hour;
  private String _minute;
  private String _meridian;

  private boolean _initialized = false;

  /**
   * Creates a new VariablePrecisionDateTime.  Callers should call the setters to set the 
   * components of the date and time.
   */
  public VariablePrecisionDateTime() {
  }

  /**
   * Creates a new VariablePrecisionDateTime, initialized with the data from a
   * VariablePrecisionDateTime passed in.
   */
  public VariablePrecisionDateTime(VariablePrecisionDateTime variablePrecisionDateTime) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, variablePrecisionDateTime);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (variablePrecisionDateTime.getTimestamp() != null) {
      _timestamp = (Timestamp) variablePrecisionDateTime.getTimestamp().clone();
    }
  }

  /**
   * Creates a new VariablePrecisionDateTime from the specified timestamp and precision.  This
   * constructor is intended to be used when retrieving variable precision datetimes from the
   * database.
   * 
   * @param  timestamp  the timestamp that holds the date and time
   * @param  precision  the precision of the timestamp.  Must be one of the following:
   *                    Constants.DAY, Constants.MINUTE.
   * @throws  ApiException if precision is not one of Constants.DAY, Constants.HOUR or 
   *          Constants.MINUTE.
   */
  public VariablePrecisionDateTime(Timestamp timestamp, String precision) {
    super();
    if (timestamp != null) {
      if (!(Constants.DAY.equals(precision)
        || Constants.HOUR.equals(precision)
        || Constants.MINUTE.equals(precision))) {
        throw new ApiException(
          "Attempt to initialize a VariablePrecisionDateTime with an invalid precision: "
            + precision);
      }
    }
    else if (precision != null) {
      throw new ApiException(
        "Attempt to initialize a VariablePrecisionDateTime with a null timestamp and a non-null precision");
    }
    _timestamp = timestamp;
    _precision = precision;
    initialize();
  }

  /**
   * Creates a new VariablePrecisionDateTime from the specified date, hour, minute and meridian.
   * This constructor will allow an invalid date to be specified, and thus the user should 
   * validate this variable precision datetime before calling
   * other methods, such as {@link #getTimestamp}, that will throw an exception if an invalid
   * date was specified here.
   * 
   * @param  date  the full date, in MM/dd/yyyy format 
   * @param  hour  the hour
   * @param  minute  the minute
   * @param  meridian  the meridian
   */
  public VariablePrecisionDateTime(String date, String hour, String minute, String meridian) {
    super();
    _date = date;
    _hour = hour;
    _minute = minute;
    _meridian = meridian;
  }

  /**
   * Initialize the timestamp and precision from the string values, or the string values from
   * the timestamp and precision as appropriate. 
   */
  private void initialize() {
    if (_initialized) {
      return;
    }
    _initialized = true;

    Calendar calendar = Calendar.getInstance();
    calendar.setLenient(false);

    // This object was constructed with a timestamp, so calculate and set the string components.
    Timestamp timestamp = getTimestamp();
    if (timestamp != null) {
      calendar.setTime(timestamp);

      SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
      _date = formatter.format(timestamp);

      if (Constants.MINUTE.equals(getPrecision())) {
        _hour = String.valueOf(calendar.get(Calendar.HOUR));
        // Calendar.HOUR range is 1-11 for hours 1-11, but hour 12 is 0.
        // Check for this case and return 12.
        if ("0".equals(_hour)) {
          _hour = "12";
        }
        _minute = String.valueOf(calendar.get(Calendar.MINUTE));
        if (_minute.length() == 1) {
          _minute = "0" + _minute;
        }
        _meridian = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
      }
    }

    // This object was constructed with separate string components, so calculate and set the 
    // timestamp and precision.
    else {
      String date = getDate();
      if (!ApiFunctions.isEmpty(date)) {
        _precision = Constants.DAY;
        int year = Integer.parseInt(date.substring(6));
        int month = (Integer.parseInt(date.substring(0, 2))) - 1; // month is "0" based
        int day = Integer.parseInt(date.substring(3, 5));
        Integer hour = ApiFunctions.safeInteger(getHour());
        Integer minute = ApiFunctions.safeInteger(getMinute());
        String ampm = ApiFunctions.safeString(getMeridian());
        int hourVal = 0;
        if (hour != null) {
          hourVal = hour.intValue();
          // Adjust the hour based on whether AM or PM was specified and whether the hour is 12.
          if ("AM".equalsIgnoreCase(ampm) && hourVal == 12) {
            hourVal = 0;
          }
          if ("PM".equalsIgnoreCase(ampm) && hourVal != 12) {
            hourVal = hourVal + 12;
          }
          _precision = Constants.HOUR;
        }
        int minuteVal = 0;
        if (minute != null) {
          minuteVal = minute.intValue();
          _precision = Constants.MINUTE;
        }
        calendar.set(year, month, day, hourVal, minuteVal, 0);
        _timestamp = new Timestamp(calendar.getTimeInMillis());
      }
    }
  }

  /**
   * Returns an indication of whether this VariablePrecisionDateTime is empty.  It is considered
   * empty if neither the timestamp or precision have a value.
   * 
   * @return  <code>true</code> if this VariablePrecisionDateTime is empty; <code>false</code>
   *          otherwise.  
   */
  public boolean isEmpty() {
    initialize();
    return (getTimestamp() == null) && (getPrecision() == null);
  }

  public String getDate() {
    return _date;
  }

  public void setDate(String date) {
    _date = date;
  }

  public String getHour() {
    return _hour;
  }

  public void setHour(String hour) {
    _hour = hour;
  }

  public String getMeridian() {
    return _meridian;
  }

  public void setMeridian(String meridian) {
    _meridian = meridian;
  }

  public String getMinute() {
    return _minute;
  }

  public void setMinute(String minute) {
    _minute = minute;
  }

  public String getPrecision() {
    initialize();
    return _precision;
  }

  public Timestamp getTimestamp() {
    initialize();
    return _timestamp;
  }

  public String toString() {
    Timestamp timestamp = getTimestamp();
    String precision = getPrecision();
    
    SimpleDateFormat formatter = null;    
    if (Constants.DAY.equals(precision)) {
      formatter = new SimpleDateFormat(DATE_FORMAT);
    }
    else if (Constants.HOUR.equals(precision)) {
      formatter = new SimpleDateFormat(DATE_TIME_FORMAT_HOUR);
    }
    else if (Constants.MINUTE.equals(precision)) {
      formatter = new SimpleDateFormat(DATE_TIME_FORMAT_HOUR_MINUTE);
    }

    if ((formatter == null) || (timestamp == null)) {
      return ApiFunctions.EMPTY_STRING; 
    }
    else {
      return formatter.format(timestamp); 
    }
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof VariablePrecisionDateTime)) {
      return false;
    }    

    VariablePrecisionDateTime o2 = (VariablePrecisionDateTime) obj;
    if (ApiFunctions.safeEquals(getTimestamp(), o2.getTimestamp())) {
      if (ApiFunctions.safeEquals(getPrecision(), o2.getPrecision())) {
        return true;
      }
    }

    return false;
  }

  public int hashCode() {
    return new HashCodeBuilder(23, 35).append(getTimestamp()).append(getPrecision()).toHashCode();
  }

}
