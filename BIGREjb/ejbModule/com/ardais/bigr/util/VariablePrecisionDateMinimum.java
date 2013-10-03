package com.ardais.bigr.util;

/**
 * A {@link VariablePrecisionDate} that fills missing date components with the minimum date
 * component, i.e. the first day of the month and the first month of the year.
 */
public class VariablePrecisionDateMinimum extends VariablePrecisionDate {

  public VariablePrecisionDateMinimum(String dateValue) {
    super(dateValue);
  }
  
  protected String getMissingDay() {
    return "-01";
  }
  
  protected String getMissingMonthAndDay() {
    return "-01-01";
  }
}
