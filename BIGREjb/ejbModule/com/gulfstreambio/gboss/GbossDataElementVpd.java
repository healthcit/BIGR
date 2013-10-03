package com.gulfstreambio.gboss;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.VariablePrecisionDate;
import com.gulfstreambio.kc.det.DetElementDatatypes;

public class GbossDataElementVpd extends GbossDataElementDateBase {
  
  private VariablePrecisionDate _minVpd = null;
  private VariablePrecisionDate _maxVpd = null;
  
  /**
   * Create a new GbossDataElementVpd object.
   */
  public GbossDataElementVpd() {
    super();
  }
  
  public String getDatatype() {
    return DetElementDatatypes.VPD; 
   }
  
  public String toHtml() {
    StringBuffer buff = new StringBuffer(100);
    buff.append(super.toHtml());
    if (getMin() != null) {
      buff.append(", ");
      buff.append("min");
      if (getMinInclusive() != null && getMinInclusive().booleanValue()) {
        buff.append (" (inclusive)");
      }
      buff.append(" = ");
      if (TODAY.equalsIgnoreCase(getMinString())) {
        buff.append(TODAY);
        buff.append(" (");
        buff.append(getMin().displayVpd());
        buff.append(")");
      }
      else {
        buff.append(getMin().displayVpd());
      }
    }
    if (getMax() != null) {
      buff.append(", ");
      buff.append("max");
      if (getMaxInclusive() != null && getMaxInclusive().booleanValue()) {
        buff.append (" (inclusive)");
      }
      buff.append(" = ");
      if (TODAY.equalsIgnoreCase(getMaxString())) {
        buff.append(TODAY);
        buff.append(" (");
        buff.append(getMax().displayVpd());
        buff.append(")");
      }
      else {
        buff.append(getMax().displayVpd());
      }
    }
    return buff.toString();
  }
  
  /**
   * @return Returns the max.
   */
  public VariablePrecisionDate getMax() {
    if (_maxVpd == null) {
      _maxVpd = getDateFromString(getMaxString());
    }
    return _maxVpd;
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
  public VariablePrecisionDate getMin() {
    if (_minVpd == null) {
      _minVpd = getDateFromString(getMinString());
    }
    return _minVpd;
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
  
  private VariablePrecisionDate getDateFromString(String date) {
    VariablePrecisionDate returnValue = null;
    if (!ApiFunctions.isEmpty(date)) {
      Date theDate = null;
      if (TODAY.equalsIgnoreCase(date)) {
        theDate = new Date(System.currentTimeMillis());
      }
      else {
        //note that any date value passed in will be in the format yyyy-mm-dd
        //(since that is the format of the xs:date type), and since that is the
        //format that Date.valueOf() expects there is no conversion necessary.
        theDate = Date.valueOf(date);
      }
      //convert the date into the format that the VariablePrecisionDate class expects
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
      returnValue = new VariablePrecisionDate(dateFormat.format(theDate));    
    }
    return returnValue;
  }
}
