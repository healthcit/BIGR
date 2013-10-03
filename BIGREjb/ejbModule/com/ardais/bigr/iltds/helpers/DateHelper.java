package com.ardais.bigr.iltds.helpers;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * <code>DateHelper</code> provides convenience methods for
 * dealing with dates in BIGR.
 */
public class DateHelper {
	// The format to be used for display of dates.
	private static final String DATE_FORMAT = "MM/dd/yyyy";

	private Date _date;
	private String _dayString;
	private String _monthString;
	private String _yearString;
	private SimpleDateFormat _dateFormatter;
/**
 * Creates a new <code>DateHelper</code> for the specified
 * day, month and year.  Day, month and year must be convertable
 * to <code>int</code>, and must represent a valid date according
 * to strict interpretation rules (i.e. the same as
 * <code>Calendar.setLenient(false)</code>).
 *
 * @param  dayString  the day
 * @param  monthString  the month, <b>a number from 0 to 11</b>,
 *     not from 1 to 12.
 * @param  yearString  the year
 * @throws  <code>NumberFormatException</code> if the day, month or year are
 *		not convertable to <code>int</code>.
 * @throws  <code>IllegalArgumentException</code> if the day, month and year 
 *		do not specify a valid date.
 */
public DateHelper(String dayString, String monthString, String yearString) {
	super();
  initializeDateFromStrings(dayString, monthString, yearString); 
}
/**
 * Creates a new <code>DateHelper</code> from the specified formatted string.  The string must
 * be formatted as MM/dd/yyyy as defined in SimpleDateFormat (example: 10/14/2005), and must
 * represent a valid date according to strict interpretation rules (i.e. the same as
 * <code>Calendar.setLenient(false)</code>).
 *
 * @param  date  the date
 * @throws  <code>IllegalArgumentException</code> if the day, month and year 
 *    do not specify a valid date.
 */
public DateHelper(String date) {
  super();
  String monthString = date.substring(0, 1);
  int month = Integer.parseInt(monthString) - 1;
  monthString = String.valueOf(month);
   
  String dayString = date.substring(3, 4); 
  String yearString = date.substring(6, 9);
  
  initializeDateFromStrings(dayString, monthString, yearString); 
}
/**
 * Creates a new <code>DateHelper</code> for the specified
 * <code>Date</code>.
 */
public DateHelper(Date date) {
	super();
    Calendar cal = Calendar.getInstance();
    cal.setLenient(false);
    cal.setTime(date);
    _date = new Date(cal.getTime().getTime());
   	_dayString = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
	_monthString = String.valueOf(cal.get(Calendar.MONTH));
	_yearString = String.valueOf(cal.get(Calendar.YEAR));
}
private void initializeDateFromStrings(String dayString, String monthString, String yearString) {
  int day = Integer.parseInt(dayString);
  int month = Integer.parseInt(monthString);
  int year = Integer.parseInt(yearString);

  Calendar cal = Calendar.getInstance();
  cal.setLenient(false);    
  cal.set(year, month, day, 0, 0, 1);    
  _date = new Date(cal.getTime().getTime());
  
  _dayString = dayString;
  _monthString = monthString;
  _yearString = yearString;
}
/**
 * Returns the date.
 *
 * @return  The date.
 */
public Date getDate() {
	return _date;
}
/**
 * Returns a <code>SimpleDateFormat</code> for formatting dates.
 *
 * @return  The <code>SimpleDateFormat</code>.
 */
private SimpleDateFormat getDateFormatter() {
	if (_dateFormatter == null) {
		_dateFormatter = new SimpleDateFormat(DATE_FORMAT);
	}
	return _dateFormatter;
}
/**
 * Returns a <code>LegalValueSet</code> containing days
 * 1 through 31.  Both the value and display value are
 * the same.  The first entry in the <code>LegalValueSet</code>
 * is -1/"DD".
 *
 * @return  The <code>LegalValueSet</code> of days.
 */
public static LegalValueSet getDayList() {
	LegalValueSet days = new LegalValueSet();
	days.addLegalValue("", "DD");
	for (int i = 1; i <= 31; i++) {		
		days.addLegalValue(String.valueOf(i));
	}

	return days;
}
/**
 * Returns the day as a <code>String</code>.
 *
 * @return  The day.
 */
public String getDayString() {
	return _dayString;
}
/**
 * Returns a date as a string, formatted according to
 * DATE_FORMAT defined in this class.  If the passed in
 * date is null, then the empty string is returned.
 *
 * @return  The formatted date.
 */
public String getFormattedDate() {
	SimpleDateFormat formatter = getDateFormatter();
	return formatter.format(_date);
}
/**
 * Returns a <code>LegalValueSet</code> containing months
 * "Jan" through "Dec".  The values are <code>Calendar.JANUARY</code>
 * to <code>Calendar.DECEMBER</code> and the display values are
 * "Jan" to "Dec".  The first entry in the <code>LegalValueSet</code>
 * is -1/"MMM".
 *
 * @return  The <code>LegalValueSet</code> of months.
 */
public static LegalValueSet getMonthList() {
	LegalValueSet months = new LegalValueSet();
	months.addLegalValue("", "MM");
	months.addLegalValue(String.valueOf(Calendar.JANUARY), "Jan");
	months.addLegalValue(String.valueOf(Calendar.FEBRUARY), "Feb");
	months.addLegalValue(String.valueOf(Calendar.MARCH), "Mar");
	months.addLegalValue(String.valueOf(Calendar.APRIL), "Apr");
	months.addLegalValue(String.valueOf(Calendar.MAY), "May");
	months.addLegalValue(String.valueOf(Calendar.JUNE), "Jun");
	months.addLegalValue(String.valueOf(Calendar.JULY), "Jul");
	months.addLegalValue(String.valueOf(Calendar.AUGUST), "Aug");
	months.addLegalValue(String.valueOf(Calendar.SEPTEMBER), "Sep");
	months.addLegalValue(String.valueOf(Calendar.OCTOBER), "Oct");
	months.addLegalValue(String.valueOf(Calendar.NOVEMBER), "Nov");
	months.addLegalValue(String.valueOf(Calendar.DECEMBER), "Dec");
	
	return months;
}
/**
 * Returns a <code>LegalValueSet</code> containing months
 * "Jan" through "Dec".  The values are <code>00</code>
 * to <code>11</code> and the display values are
 * "Jan" to "Dec".
 *
 * @return  The <code>LegalValueSet</code> of months.
 */
public static LegalValueSet getMonthList1() {
	LegalValueSet months = new LegalValueSet();
	months.addLegalValue("00", "Jan");
	months.addLegalValue("01", "Feb");
	months.addLegalValue("02", "Mar");
	months.addLegalValue("03", "Apr");
	months.addLegalValue("04", "May");
	months.addLegalValue("05", "Jun");
	months.addLegalValue("06", "Jul");
	months.addLegalValue("07", "Aug");
	months.addLegalValue("08", "Sep");
	months.addLegalValue("09", "Oct");
	months.addLegalValue("10", "Nov");
	months.addLegalValue("11", "Dec");
	
	return months;
}
/**
 * Returns the month as a <code>String</code>, <b>returned in the range
 * 0-11 not 1-12</b>.
 *
 * @return  The month.
 */
public String getMonthString() {
	return _monthString;
}
/**
 * Returns a <code>LegalValueSet</code> containing years.
 * The first year is specified by <code>firstYear</code>,
 * and the last year is specified by <code>lastYear</code>.
 * Both the value and display value are the same.  The first
 * entry in the <code>LegalValueSet</code> is -1/"YYYY".
 *
 * @return  The <code>LegalValueSet</code> of years.
 * @throws  IllegalArgumentException  If <code>lastYear</code>
 *		is before <code>firstYear</code>.
 */
public static LegalValueSet getYearList(int firstYear, int lastYear) {
	if (lastYear < firstYear) {
		String msg = "Error calling getYearList: lastYear (" + lastYear + ") is < firstYear (" + firstYear + ")";
		throw new IllegalArgumentException(msg);
	}
	
	LegalValueSet years = new LegalValueSet();
	years.addLegalValue("", "YYYY");
	for (int i = firstYear; i <= lastYear; i++) {		
		years.addLegalValue(String.valueOf(i));
	}

	return years;
}
/**
 * Returns a <code>LegalValueSet</code> containing years.
 * The first year is specified by <code>firstYear</code>,
 * and the last year is specified by <code>lastYear</code>.
 * Both the value and display value are the same.  
 *
 * @return  The <code>LegalValueSet</code> of years.
 * @throws  IllegalArgumentException  If <code>lastYear</code>
 *		is before <code>firstYear</code>.
 */
public static LegalValueSet getYearListYY(int firstYear, int lastYear) {
	if (lastYear < firstYear) {
		String msg = "Error calling getYearList: lastYear (" + lastYear + ") is < firstYear (" + firstYear + ")";
		throw new IllegalArgumentException(msg);
	}
	
	LegalValueSet years = new LegalValueSet();
	for (int i = firstYear; i <= lastYear; i++) {		
		years.addLegalValue(String.valueOf(i));
	}

	return years;
}
/**
 * Returns the year as a <code>String</code>.
 *
 * @return  The year.
 */
public String getYearString() {
	return _yearString;
}
}
