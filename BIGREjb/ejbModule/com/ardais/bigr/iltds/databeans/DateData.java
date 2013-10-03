package com.ardais.bigr.iltds.databeans;

import java.sql.Timestamp;
import java.util.Calendar;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
/**
 * Insert the type's description here.
 * Creation date: (3/27/2002 11:57:34 AM)
 * @author: Jake Thompson
 */
public class DateData implements java.io.Serializable {
	private java.sql.Timestamp timestamp;
	private String day;
	private String month;
	private String year;
	private String hour;
	private String minute;
	private String ampm;
	private String dayofweek;
/**
 * DateData constructor comment.
 */
public DateData() {
	super();
	hour = "-1";
	minute = "-1";
	ampm = String.valueOf(Calendar.AM); 
}

/**
 * DateData constructor comment.
 */
public DateData(DateData dateData) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, dateData);
  //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
  //does a shallow copy.
  if (dateData.getTimestamp() != null) {
    setTimestamp((Timestamp) dateData.getTimestamp().clone());
  }
}

/**
 * DateData constructor comment.
 */
public DateData(java.sql.Timestamp newTimestamp) {
	
	super();
	if(newTimestamp != null){
		setTimestamp(newTimestamp);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public java.lang.String getAmpm() {
	return ampm;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public int getAmpmInt() {
	Integer blah = new Integer(ampm);
	return blah.intValue();
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public java.lang.String getDay() {
	return day;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public int getDayInt() {
	Integer blah = new Integer(day);
	return blah.intValue();
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public java.lang.String getDayofweek() {
	return dayofweek;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public int getDayofweekInt() {
	Integer blah = new Integer(dayofweek);
	return blah.intValue();
}
/**
 * Insert the method's description here.
 * Creation date: (4/1/2002 10:30:31 AM)
 * @return java.lang.String
 */
public String getFormat() {

    /*
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy 'at' hh:mm a");
        */

    return timestamp.toString();

}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public java.lang.String getHour() {
	return hour;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public int getHourInt() {
	Integer blah = new Integer(hour);
	return blah.intValue();
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public java.lang.String getMinute() {
	return minute;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public int getMinuteInt() {
	
	return new Integer(minute).intValue();
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public java.lang.String getMonth() {
	return month;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public int getMonthInt() {
	return new Integer(month).intValue();
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:33 AM)
 * @return java.sql.Timestamp
 */
public java.sql.Timestamp getTimestamp() {
	return timestamp;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public java.lang.String getYear() {
	return year;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @return java.lang.String
 */
public int getYearInt() {
	return new Integer(year).intValue();
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @param newAmpm java.lang.String
 */
void setAmpm(java.lang.String newAmpm) {
	ampm = newAmpm;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @param newDay java.lang.String
 */
void setDay(java.lang.String newDay) {
	day = newDay;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @param newDayofweek java.lang.String
 */
void setDayofweek(java.lang.String newDayofweek) {
	dayofweek = newDayofweek;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @param newHour java.lang.String
 */
void setHour(java.lang.String newHour) {
	hour = newHour;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @param newMinute java.lang.String
 */
void setMinute(java.lang.String newMinute) {
	minute = newMinute;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @param newMonth java.lang.String
 */
void setMonth(java.lang.String newMonth) {
	month = newMonth;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:33 AM)
 * @param newTimestamp java.sql.Timestamp
 */
public void setTimestamp(java.sql.Timestamp newTimestamp) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(newTimestamp);
    timestamp = newTimestamp;

    day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    month = String.valueOf(cal.get(Calendar.MONTH));
    year = String.valueOf(cal.get(Calendar.YEAR));
    dayofweek = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
    hour = String.valueOf(cal.get(Calendar.HOUR));
    if(hour.equals("0")){
		hour = "12";
    }
    minute = String.valueOf(cal.get(Calendar.MINUTE));
    ampm = String.valueOf(cal.get(Calendar.AM_PM));

}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:59:53 AM)
 * @param newYear java.lang.String
 */
void setYear(java.lang.String newYear) {
	year = newYear;
}
}
