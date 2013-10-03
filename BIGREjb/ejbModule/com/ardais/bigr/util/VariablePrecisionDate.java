package com.ardais.bigr.util;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConversionException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrConvertUtilsBean;

public class VariablePrecisionDate implements Comparable, Serializable {

  public final static String YEAR_STRING = "YEAR";
  public final static String MONTH_STRING = "MONTH";
  public final static String DAY_STRING = "DAY";
  private final static String FILL_MISSING_DAY_STRING = "-15";
  private final static String FILL_MISSING_MONTH_STRING = "-06-30";

  private Date _date;
  private String _precision;

  public VariablePrecisionDate(String dateValue) {
    super();
    _precision = getDatePrecisionComponent(dateValue);
    _date = getCompleteDateComponent(dateValue);
  }

  public VariablePrecisionDate(Date dateValue, String precision) {
    super();
    _precision = precision;
    _date = dateValue;
  }

  /**
   * @return
   */
  public String getPrecision() {
    return _precision;
  }

  /**
   * @return
   */
  public Date getDate() {
    return _date;
  }

  /**
   * Returns a java.sql.Date constructed from the given string.  The string is first trimmed.
   * If the string is empty or cannot be successfully converted to a Date then null is 
   * returned. If the string has only year "-06-30" will be appended to create a valid date.
   * If the string is in mm/dddd format, "-15" will be appended to create a valid date. 
   *
   * @param  s  the string to convert to a Date
   * @return if the string is empty return <code>null</code>
   *    otherwise return the Date obtained by parsing the string.
   */
  private Date getCompleteDateComponent(String s) {
    if ((s == null) || (s.trim().length() == 0)) {
      return null;
    }
    else {
      String[] dateValues = ApiFunctions.splitAndTrim(s.toString(), "/");
      String newValue = null;
      if (dateValues.length == 3) {
        newValue = dateValues[2] + "-" + dateValues[0] + "-" + dateValues[1];
      }
      else if (dateValues.length == 2) {
        newValue = dateValues[1] + "-" + dateValues[0] + getMissingDay();
      }
      else if (dateValues.length == 1) {
        newValue = dateValues[0] + getMissingMonthAndDay();
      }
            
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      format.setLenient(false);  
      try {
        return new Date(format.parse(newValue).getTime());
      }
      catch (ParseException e) { 
        throw(new ConversionException("Cannot convert '" + s + "' to a variable precision date"));
      }     
    }
  }

  private String getDatePrecisionComponent(String s) {
    String dpc = null;
    if (getCompleteDateComponent(s) == null) {
      return null;
    }
    else {
      String[] dateValues = ApiFunctions.splitAndTrim(s.toString(), "/");
      if (dateValues.length == 3) {
        dpc = DAY_STRING;
      }
      else if (dateValues.length == 2) {
        dpc = MONTH_STRING;
      }
      else if (dateValues.length == 1) {
        dpc = YEAR_STRING;
      }
      return dpc;
    }
  }

  public String displayVpd() {
    Date date = getDate();
    String precision = getPrecision();

    if (date == null) return null;

    if (YEAR_STRING.equals(precision)) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
      return dateFormat.format(date);
    }
    else if (MONTH_STRING.equals(precision)) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
      return dateFormat.format(date);
    }
    else if (DAY_STRING.equals(precision)) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
      return dateFormat.format(date);
    }
    else {
      return null;
    }
  }
  
  protected String getMissingDay() {
    return FILL_MISSING_DAY_STRING;     
  }

  protected String getMissingMonthAndDay() {
    return FILL_MISSING_MONTH_STRING;     
  }

  public int compareTo(VariablePrecisionDate o) {
    Date thisVal = this.getDate();
    Date anotherVal = o.getDate();
    return (thisVal.compareTo(anotherVal));
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object o) {
    return compareTo((VariablePrecisionDate)o);
  }


}
