package com.gulfstreambio.gboss;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DetElementDatatypes;

public class GbossDataElementDate extends GbossDataElementDateBase {
  
  /**
   * Create a new GbossDataElementDate object.
   */
  public GbossDataElementDate() {
    super();
  }
  
  public String getDatatype() {
    return DetElementDatatypes.DATE; 
   }
  
  public String toHtml() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    StringBuffer buff = new StringBuffer(100);
    buff.append(super.toHtml());
    if (getMin() != null) {
      String displayValue= dateFormat.format(getMin());
      buff.append(", ");
      buff.append("min");
      if (getMinInclusive() != null && getMinInclusive().booleanValue()) {
        buff.append (" (inclusive)");
      }
      buff.append(" = ");
      if (TODAY.equalsIgnoreCase(getMinString())) {
        buff.append(TODAY);
        buff.append(" (");
        buff.append(displayValue);
        buff.append(")");
      }
      else {
        buff.append(displayValue);
      }
    }
    if (getMax() != null) {
      String displayValue= dateFormat.format(getMax());
      buff.append(", ");
      buff.append("max");
      if (getMaxInclusive() != null && getMaxInclusive().booleanValue()) {
        buff.append (" (inclusive)");
      }
      buff.append(" = ");
      if (TODAY.equalsIgnoreCase(getMaxString())) {
        buff.append(TODAY);
        buff.append(" (");
        buff.append(displayValue);
        buff.append(")");
      }
      else {
        buff.append(displayValue);
      }
    }
    return buff.toString();
  }
  
  /**
   * @return Returns the max.
   */
  public Date getMax() {
    //since Date is mutable, always return a new Date created from the String passed in.
    return getDateFromString(getMaxString());
  }
  
  /**
   * @return Returns the max value as String.
   */
  public String getMaxValue() {
  	if (getMaxString() != null)
  		return getMaxString();
    else
      return null;
  }
  
  /**
   * @return Returns the min.
   */
  public Date getMin() {
    //since Date is mutable, always return a new Date created from the String passed in.
    return getDateFromString(getMinString());
  }

  /**
   * @return Returns the min value as String.
   */
  public String getMinValue() {
    if (getMinString() != null)
    	return getMinString();
    else
      return null;
  }
  
  
  private Date getDateFromString(String date) {
    Date returnValue = null;
    if (!ApiFunctions.isEmpty(date)) {
      if (TODAY.equalsIgnoreCase(date)) {
        returnValue = new Date(System.currentTimeMillis());
      }
      else {
        //note that any date value passed in will be in the format yyyy-mm-dd
        //(since that is the format of the xs:date type), and since that is the
        //format that Date.valueOf() expects there is no conversion necessary.
        returnValue = Date.valueOf(date);
      }
    }
    return returnValue;
  }
}
