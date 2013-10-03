package com.gulfstreambio.kc.form;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.util.BigrCallableStatement;
import com.ardais.bigr.util.VariablePrecisionDate;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DatabaseElement;
import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.util.KcNamingUtils;

class KcAdeDataAccessObjectImpl implements KcAdeDataAccessObject {
  private DetDataElement _detDataElement;
  private DataElementValue _value;
  private String _formId;
  private Integer _parentId;

  public KcAdeDataAccessObjectImpl(DetDataElement detDataElement, DataElementValue value, String formId, 
                       Integer parentId) {
    super();
    if (!detDataElement.isHasAde()) {
      throw new ApiException("Attempt to create an ADE DAO for a data element that does not have an ADE (" + detDataElement.getCui() + ")");
    }
    _detDataElement = detDataElement;
    _formId = formId;
    _parentId = parentId;
    _value = value;
  }

  public void create() {
    if (isAnyAdeValues()) {
      Connection con = ApiFunctions.getDbConnection();
      BigrCallableStatement cstmt = null;
      try {
        cstmt = new BigrCallableStatement(con, getSqlForCreate());
        bindAndExecuteCreate(cstmt);
        Integer primaryKey = new Integer(cstmt.getInt(cstmt.getNumBindVariables()));
        createMulti(primaryKey);
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(cstmt);
        ApiFunctions.close(con);
      }          
    }
  }

  private String getSqlForCreate() {
    DetAde detAde = _detDataElement.getAde();
    StringBuffer buf = new StringBuffer(1024);
    buf.append("{ CALL INSERT INTO ");
    buf.append(KcNamingUtils.getDatabaseTableNameConventional(detAde));
    buf.append(" (id, form_id, parent_id, data_element_cui");
    
    int numPlaceholders = 3;
    DetAdeElement[] detElements = detAde.getAdeElements();
    for (int i = 0; i < detElements.length; i++) {
      DetAdeElement detElement = detElements[i];
      DatabaseElement dbElement = detElement.getDatabaseElement();
      if (detElement.isMultivalued()) {
        continue;
      }
      buf.append(", ");
      buf.append(dbElement.getColumnDataValue());
      numPlaceholders++;
      if (detElement.isHasOtherValue()) {          
        buf.append(", ");
        buf.append(dbElement.getColumnDataValueOther());
        numPlaceholders++;
      }
      if (detElement.isDatatypeVpd()) {
        buf.append(", ");
        buf.append(dbElement.getColumnDataValueDpc());            
        numPlaceholders++;
      }
    }
    
    buf.append(") VALUES (CIR_SEQ.NEXTVAL");
    for (int i = 0; i < numPlaceholders; i++) {
      buf.append(", ?");
    }
    buf.append(") ");
    buf.append("RETURNING id INTO ? }");

    return buf.toString();
  }

  private void bindAndExecuteCreate(CallableStatement cstmt) throws SQLException {
    List tempClobs = new ArrayList();
    DetAde detAde = _detDataElement.getAde();

    try {
      int paramIndex = 1;
      cstmt.setString(paramIndex++, _formId);
      cstmt.setInt(paramIndex++, _parentId.intValue());
      cstmt.setString(paramIndex++, _detDataElement.getCui());

      Ade ade = _value.getAde();
      DetAdeElement[] detElements = detAde.getAdeElements();
      for (int i = 0; i < detElements.length; i++) {
        DetAdeElement detElement = detElements[i];
        if (detElement.isMultivalued()) {
          continue;
        }
        
        AdeElement adeElement = ade.getAdeElement(detElement.getCui());
        AdeElementValue[] values = 
          (adeElement == null) ? new AdeElementValue[0] : adeElement.getAdeElementValues();
        if (KcDaoUtils.isValueNull(values)) {
          cstmt.setNull(paramIndex++, KcDaoUtils.getSqlType(detElement));
          if (detElement.isHasOtherValue()) {
            if (KcDaoUtils.isValueOtherNull(values)) {
              cstmt.setNull(paramIndex++, Types.VARCHAR);
            }
            else {
              cstmt.setString(paramIndex++, values[0].getValueOther());
            }
          }
          if (detElement.isDatatypeVpd()) {
            cstmt.setNull(paramIndex++, Types.VARCHAR);
          }
        }
        else {
          AdeElementValue value = values[0];
          if (detElement.isDatatypeCv()) {
            cstmt.setString(paramIndex++, value.getValue());
            if (detElement.isHasOtherValue()) {
              if (ApiFunctions.isEmpty(value.getValueOther())) {
                cstmt.setNull(paramIndex++, Types.VARCHAR);
              }
              else {
                cstmt.setString(paramIndex++, value.getValueOther());
              }
            }
          }
          else {
            String v = value.getValue();
            if (detElement.isDatatypeDate()) {
              cstmt.setDate(paramIndex++, KcDaoUtils.getValueDate(value));
            }
            else if (detElement.isDatatypeFloat()) {
              cstmt.setBigDecimal(paramIndex++, KcDaoUtils.getValueBigDecimal(value));
            }
            else if (detElement.isDatatypeInt()) {
              cstmt.setInt(paramIndex++, KcDaoUtils.getValueInt(value).intValue());
            }
            else if (detElement.isDatatypeReport()) {
              TemporaryClob tempClob = new TemporaryClob(cstmt.getConnection(), value.getValue());
              cstmt.setClob(paramIndex++, tempClob.getSQLClob());
              tempClobs.add(tempClob);
            }
            else if (detElement.isDatatypeText()) {
              cstmt.setString(paramIndex++, value.getValue());
            }
            else if (detElement.isDatatypeVpd()) {
              VariablePrecisionDate vpd = KcDaoUtils.getValueVpd(value);
              cstmt.setDate(paramIndex++, vpd.getDate());
              cstmt.setString(paramIndex++, vpd.getPrecision());
            }
            else {
              throw new ApiException("In bindAndExecuteCreate: unknown datatype: " + detElement.getDatatype());
            }
          }
        }      
      }
      cstmt.registerOutParameter(paramIndex, Types.INTEGER);  
      cstmt.execute();
    }
    finally {
      // Clean up any temporary clobs.
      Iterator iterator = tempClobs.iterator();
      while (iterator.hasNext()) {
        TemporaryClob clob = (TemporaryClob) iterator.next();
        ApiFunctions.close(clob, cstmt.getConnection());
      }
    }
  }

  private void createMulti(Integer parentId) {
    DetAde detAde = _detDataElement.getAde();
    StringBuffer buf = new StringBuffer(256);
    buf.append("{ CALL INSERT INTO ");
    buf.append(KcNamingUtils.getDatabaseTableNameMulti(detAde));
    buf.append(" (id, form_id, parent_id, data_element_cui, data_value_cui, other_data_value)");
    buf.append(" VALUES (CIR_SEQ.NEXTVAL, ?, ?, ?, ?, ?)");
    buf.append(" RETURNING id INTO ? }");

    Connection con = ApiFunctions.getDbConnection();
    BigrCallableStatement cstmt = null;
    try {
      cstmt = new BigrCallableStatement(con, buf.toString());
      Ade ade = _value.getAde();
      DetAdeElement[] detElements = detAde.getAdeElements();
      for (int i = 0; i < detElements.length; i++) {
        DetAdeElement detElement = detElements[i];
        if (detElement.isMultivalued()) {
          AdeElement adeElement = ade.getAdeElement(detElement.getCui());
          ElementValue[] values = adeElement.getElementValues();
          for (int j = 0; j < values.length; j++) {
            ElementValue value = values[j];
            if (!value.isEmpty()) {
              cstmt.setString(1, _formId);
              cstmt.setInt(2, parentId.intValue());
              cstmt.setString(3, detElement.getCui());
              String v = value.getValue();
              if (ApiFunctions.isEmpty(v)) {
                cstmt.setNull(4, Types.VARCHAR);
              }
              else {
                cstmt.setString(4, v);
              }
              v = value.getValueOther();
              if (ApiFunctions.isEmpty(v)) {
                cstmt.setNull(5, Types.VARCHAR);
              }
              else {
                cstmt.setString(5, v);
              }
              cstmt.registerOutParameter(6, Types.INTEGER); 
              cstmt.executeUpdate();
            }
          }
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
  }
  
  public void find() {
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = con.prepareStatement(getSqlForFind());
      pstmt.setInt(1, _parentId.intValue());
      pstmt.setString(2, _detDataElement.getCui());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        Integer primaryKey = new Integer(rs.getInt("id"));
        Ade ade = _value.createAde();
        DetAdeElement[] detElements = _detDataElement.getAde().getAdeElements();
        for (int i = 0; i < detElements.length; i++) {
          DetAdeElement detElement = detElements[i];
          if (!detElement.isMultivalued()) {
            AdeElement adeElement = ade.createAdeElement(detElement.getCui());
            AdeElementValue adeElementValue = adeElement.createAdeElementValue();
            KcDaoUtils.populateValue(adeElementValue, detElement, rs);
          }
        }
        findMulti(ade, primaryKey);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  private String getSqlForFind() {
    StringBuffer buf = new StringBuffer(128);
    buf.append("SELECT * FROM ");
    buf.append(KcNamingUtils.getDatabaseTableNameConventional(_detDataElement.getAde()));
    buf.append(" WHERE parent_id = ?");
    buf.append(" AND data_element_cui = ?");
    return buf.toString();
  }

  private void findMulti(Ade ade, Integer parentId) {
    if (hasMultivaluedDataElements()) {
      StringBuffer buf = new StringBuffer(128);
      buf.append("SELECT * FROM ");
      buf.append(KcNamingUtils.getDatabaseTableNameMulti(_detDataElement.getAde()));
      buf.append(" WHERE parent_id = ?");
      buf.append(" ORDER BY id");

      DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
      Connection con = ApiFunctions.getDbConnection();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        pstmt = con.prepareStatement(buf.toString());
        pstmt.setInt(1, parentId.intValue());
        rs = pstmt.executeQuery();
        while (rs.next()) {
          String adeElementCui = rs.getString("data_element_cui");
          AdeElement adeElement = ade.getAdeElement(adeElementCui);
          if (adeElement == null) {
            adeElement = ade.createAdeElement(adeElementCui);
          }
          AdeElementValue adeElementValue = adeElement.createAdeElementValue();
          
          DetAdeElement detElement = det.getAdeElement(adeElementCui);
          DatabaseElement dbElement = detElement.getDatabaseElement();
          if (detElement.isDatatypeCv()) {
            String v = rs.getString(dbElement.getColumnDataValue());
            if (v != null) {
              adeElementValue.setValue(v);
            }
            if (detElement.isHasOtherValue()) {
              v = rs.getString(dbElement.getColumnDataValueOther());
              if (v != null) {
                adeElementValue.setValueOther(v);
              }            
            }
          }
          else {
            throw new ApiException("Multivalued ADE elements must have datatype CV");
          }
        }
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
  } 
  
  public void delete() {
    StringBuffer buf = new StringBuffer(128);
    buf.append("DELETE FROM ");
    buf.append(KcNamingUtils.getDatabaseTableNameConventional(_detDataElement.getAde()));
    buf.append(" WHERE parent_id = ?");
    buf.append(" AND data_element_cui = ?");

    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      if (hasMultivaluedDataElements()) {
        pstmt = con.prepareStatement(getSqlForFind());
        pstmt.setInt(1, _parentId.intValue());
        pstmt.setString(2, _detDataElement.getCui());
        rs = pstmt.executeQuery();
        if (rs.next()) {
          deleteMulti(new Integer(rs.getInt("id")));
        }
      }

      pstmt = con.prepareStatement(buf.toString());
      pstmt.setInt(1, _parentId.intValue());
      pstmt.setString(2, _detDataElement.getCui());
      rs = pstmt.executeQuery();
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }    
  }

  private void deleteMulti(Integer parentId) {
    if (hasMultivaluedDataElements()) {
      StringBuffer buf = new StringBuffer(128);
      buf.append("DELETE FROM ");
      buf.append(KcNamingUtils.getDatabaseTableNameMulti(_detDataElement.getAde()));
      buf.append(" WHERE parent_id = ?");

      Connection con = ApiFunctions.getDbConnection();
      PreparedStatement pstmt = null;
      try {
        pstmt = con.prepareStatement(buf.toString());
        pstmt.setInt(1, parentId.intValue());
        pstmt.executeUpdate();
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(pstmt);
        ApiFunctions.close(con);
      }
    }
  } 
  
  private boolean isAnyAdeValues() {
    Ade ade = _value.getAde();
    if (ade != null) {
      AdeElement[] adeElements = ade.getAdeElements();
      for (int i = 0; i < adeElements.length; i++) {
        AdeElement adeElement = adeElements[i];
        ElementValue[] values = adeElement.getElementValues();
        for (int j = 0; j < values.length; j++) {
          if (!values[j].isEmpty()) {
            return true;
          }
        }        
      }
    }
    return false;
  }
  
  private boolean hasMultivaluedDataElements() {
    DetAdeElement[] detElements = _detDataElement.getAde().getAdeElements();
    for (int i = 0; i < detElements.length; i++) {
      DetAdeElement detElement = detElements[i];
      if (detElement.isMultivalued()) {
        return true;
      }
    }
    return false;
  }
  
}
