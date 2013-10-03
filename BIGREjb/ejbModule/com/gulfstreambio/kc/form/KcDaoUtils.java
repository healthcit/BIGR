package com.gulfstreambio.kc.form;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.VariablePrecisionDate;
import com.gulfstreambio.kc.det.DatabaseElement;
import com.gulfstreambio.kc.det.DetElement;

class KcDaoUtils {

  static int getSqlType(DetElement detElement) {
    if (detElement.isDatatypeCv() || detElement.isDatatypeText()) {
      return Types.VARCHAR;
    }
    else if (detElement.isDatatypeDate() || detElement.isDatatypeVpd()) {
      return Types.DATE;
    }
    else if (detElement.isDatatypeFloat()) {
      return Types.FLOAT;
    }
    else if (detElement.isDatatypeInt()) {
      return Types.INTEGER;
    }
    else if (detElement.isDatatypeReport()) {
      return Types.CLOB;
    }
    else {
      throw new ApiException("In KcDbUtils.getSqlType, unknown datatype: " + detElement.getDatatype());
    }
  }
  
  static Integer getValueInt(ElementValue value) {
    return ApiFunctions.safeInteger(value.getValue());
  }

  static BigDecimal getValueBigDecimal(ElementValue value) {
    return ApiFunctions.safeBigDecimal(value.getValue());
  }
  
  static Date getValueDate(ElementValue value) {
    return ApiFunctions.safeDate(value.getValue());
  }
  
  static VariablePrecisionDate getValueVpd(ElementValue value) {
    return new VariablePrecisionDate(value.getValue());
  }
  
  static boolean isValueNull(ElementValue[] values) {
    boolean isNull = false;
    if (values.length == 0) {
      isNull = true;
    }
    else {
      if (ApiFunctions.isEmpty(values[0].getValue())) {
        isNull = true;              
      }
    }
    return isNull;
  }
  
  static boolean isValueOtherNull(ElementValue[] values) {
    boolean isNull = false;
    if (values.length == 0) {
      isNull = true;
    }
    else {
      if (ApiFunctions.isEmpty(values[0].getValueOther())) {
        isNull = true;              
      }
    }
    return isNull;
  }

  static void populateValue(ElementValue value, DetElement detElement, ResultSet rs) {
    DatabaseElement dbElement = detElement.getDatabaseElement();
    try {
      String v = null;
      String vOther = null;
      if (detElement.isDatatypeCv()) {
        v = rs.getString(dbElement.getColumnDataValue());
        if (detElement.isHasOtherValue()) {
          vOther = rs.getString(dbElement.getColumnDataValueOther());
        }
      }
      else if (detElement.isDatatypeFloat() || detElement.isDatatypeInt()
          || detElement.isDatatypeText()) {
        v = rs.getString(dbElement.getColumnDataValue());
      }
      else if (detElement.isDatatypeDate()) {
        Date date = rs.getDate(dbElement.getColumnDataValue());
        if (date != null) {
          VariablePrecisionDate vpd = 
            new VariablePrecisionDate(date, VariablePrecisionDate.DAY_STRING);
          v = vpd.displayVpd();
        }
      }
      else if (detElement.isDatatypeVpd()) {
        Date date = rs.getDate(dbElement.getColumnDataValue());
        String dpc = rs.getString(dbElement.getColumnDataValueDpc());
        if (date != null) {
          VariablePrecisionDate vpd = new VariablePrecisionDate(date, dpc);
          v = vpd.displayVpd();
        }
      }
      else if (detElement.isDatatypeReport()) {
        v = ApiFunctions.getStringFromClob(rs.getClob(dbElement.getColumnDataValue()));
      }

      if (v == null) {
        String std = dbElement.getColumnDataValueSystemStandard();
        if (std != null) {
          v = rs.getString(std);
        }
      }
      if (v != null) {
        value.setValue(v);
      }
      if (vOther != null) {
        value.setValueOther(vOther);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }
  
  private KcDaoUtils() {
    super();
  }

}
