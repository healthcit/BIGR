package com.gulfstreambio.kc.form;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.util.BigrCallableStatement;
import com.ardais.bigr.util.VariablePrecisionDate;
import com.gulfstreambio.gboss.GbossCategory;
import com.gulfstreambio.gboss.Gboss;
import com.gulfstreambio.gboss.GbossDataElement;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DatabaseDataElement;
import com.gulfstreambio.kc.det.DatabaseElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.util.KcNamingUtils;

/**
 * Implementation of the KnowledgeCapture (KC) Data Access Object (DAO).
 */
class KcDataAccessObjectImpl implements KcDataAccessObject {

  private FormInstance _form;
  private GbossCategory _category;
  private Map _pks;

  /**
   * Creates a new <code>KcDataAccessObjectImpl</code> for the specified GBOSS database category.
   * 
   * @param  category  the GBOSS category, which is presumed to be a database category
   * @param  form  the form instance that contains the data to persist or the form instance id
   *               of the form instance to find or delete
   */
  public KcDataAccessObjectImpl(GbossCategory category, FormInstance form) {
    super();
    _form = form;
    _category = category;
  }

  public void create() {
    if (_category.hasSingleValuedDataElements()) {
      if (_category.isDatabaseTypeEav()) {
        createSingleEav();
      }
      else {
        createSingleConventional();
      }
    }
    if (_category.hasMultivaluedDataElements()) {
      createMulti();
    }
    createNotes();
  }

  private void createSingleConventional() {
    Connection con = ApiFunctions.getDbConnection();
    BigrCallableStatement cstmt = null;
    try {
      cstmt = new BigrCallableStatement(con, getSqlForCreateSingleConventional());
      bindAndExecuteCreateSingleConventional(cstmt);
      Integer primaryKey = new Integer(cstmt.getInt(cstmt.getNumBindVariables()));      
      createSingleConventionalAde(primaryKey);
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
  }

  private String getSqlForCreateSingleConventional() {
    StringBuffer buf = new StringBuffer(1024);
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    Gboss gboss = GbossFactory.getInstance();
    buf.append("{ CALL INSERT INTO ");
    buf.append(KcNamingUtils.getDatabaseTableNameConventional(_category));
    buf.append(" (id, form_id");
    
    int numPlaceholders = 1;
    List dataElements = _category.getDataElementsAllDescendants();
    for (int i = 0; i < dataElements.size(); i++) {
      GbossDataElement dataElement = 
        (GbossDataElement) dataElements.get(i);
      DetDataElement detElement = det.getDataElement(dataElement.getCui());
      DatabaseElement dbElement = detElement.getDatabaseElement();
      if (!detElement.isMultivalued()) {
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
        if (!detElement.isDatatypeCv()) {
          buf.append(", ");
          buf.append(dbElement.getColumnDataValueSystemStandard());
          numPlaceholders++;
        }
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

  private void bindAndExecuteCreateSingleConventional(CallableStatement cstmt) throws SQLException {
    List tempClobs = new ArrayList();
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();

    try {
      int paramIndex = 1;
      cstmt.setString(paramIndex++, _form.getFormInstanceId());

      List dataElements = _category.getDataElementsAllDescendants();
      for (int i = 0; i < dataElements.size(); i++) {
        GbossDataElement gbossDataElement = 
          (GbossDataElement) dataElements.get(i);
        DetDataElement detElement = det.getDataElement(gbossDataElement.getCui());
        if (detElement.isMultivalued()) {
          continue;
        }

        DataElement dataElement = _form.getDataElement(detElement.getCui());
        DataElementValue[] values = 
          (dataElement == null) ? new DataElementValue[0] : dataElement.getDataElementValues();
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
          if (!detElement.isDatatypeCv()) {
            cstmt.setNull(paramIndex++, Types.VARCHAR);              
          }            
        }
        else {
          DataElementValue value = values[0];
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
            if (det.getSystemStandardValues().containsValue(v)) {
              cstmt.setNull(paramIndex++, KcDaoUtils.getSqlType(detElement));
              if (detElement.isDatatypeVpd()) {
                cstmt.setNull(paramIndex++, Types.VARCHAR);
              }
              cstmt.setString(paramIndex++, v);
            }
            else {
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
                throw new ApiException("In bindAndExecuteCreateSingleConventional: unknown datatype: " + detElement.getDatatype());
              }
              cstmt.setNull(paramIndex++, Types.VARCHAR);
            }
          }
        }
      }  // for all data elements

      cstmt.registerOutParameter(paramIndex, Types.INTEGER);  
      cstmt.execute();
    }
    finally {
      // Clean up any temporary clobs
      Iterator iterator = tempClobs.iterator();
      while (iterator.hasNext()) {
        TemporaryClob clob = (TemporaryClob) iterator.next();
        ApiFunctions.close(clob, cstmt.getConnection());
      }
    }
    
  }

  private void createSingleConventionalAde(Integer parentId) {
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();

    DataElement[] dataElements = _form.getDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      DataElement dataElement = dataElements[i];
      DetDataElement detElement = det.getDataElement(dataElement.getCuiOrSystemName());
      if (detElement.isMultivalued() || !detElement.isHasAde()) {
        continue;
      }

      DataElementValue[] values = 
        (dataElement == null) ? new DataElementValue[0] : dataElement.getDataElementValues();
      if (KcDaoUtils.isValueNull(values)) {
        continue;
      }

      DataElementValue value = values[0];
      Ade ade = value.getAde();
      if (ade == null) {
        continue;
      }
      
      KcAdeDataAccessObjectImpl adeDao = 
        new KcAdeDataAccessObjectImpl(detElement, value, _form.getFormInstanceId(), parentId);
      adeDao.create();
    }
  }

  private void createSingleEav() {
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    Connection con = ApiFunctions.getDbConnection();
    BigrCallableStatement cstmt = null;
    try {
      cstmt = new BigrCallableStatement(con, getSqlForCreateSingleEav());

      DataElement[] dataElements = _form.getDataElements();
      for (int i = 0; i < dataElements.length; i++) {
        DataElement dataElement = dataElements[i];
        DetDataElement detElement = det.getDataElement(dataElement.getCuiOrSystemName());
        if (!detElement.isMultivalued()) {
          DataElementValue[] vs = dataElement.getDataElementValues();
          if (vs.length > 0) {
            DataElementValue v = vs[0];
            String value = v.getValue();
            String otherValue = v.getValueOther();
            if (ApiFunctions.isEmpty(value) && !ApiFunctions.isEmpty(otherValue)) {
              v.setValue(detElement.getOtherValueCui());
            }
            if (!ApiFunctions.isEmpty(value)) {
              bindAndExecuteCreateSingleEav(cstmt, v, detElement);
              Integer primaryKey = new Integer(cstmt.getInt(cstmt.getNumBindVariables()));
              if (detElement.isHasAde()) {
                KcAdeDataAccessObjectImpl adeDao = 
                  new KcAdeDataAccessObjectImpl(detElement, v, _form.getFormInstanceId(), primaryKey);
                adeDao.create();
              }
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

  private String getSqlForCreateSingleEav() {
    StringBuffer buf = new StringBuffer(256);
    buf.append("{ CALL INSERT INTO ");
    buf.append(KcNamingUtils.getDatabaseTableNameEav(_category));
    buf.append(" (id, form_id, data_element_cui, data_value_cui, data_value_date, data_value_date_dpc, data_value_clob, data_value_text, data_value, other_data_value)");
    buf.append(" VALUES (CIR_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    buf.append(" RETURNING id INTO ? }");
    return buf.toString();
  }
  
  private void bindAndExecuteCreateSingleEav(CallableStatement cstmt, ElementValue value, 
                                             DetDataElement detElement) throws SQLException {
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    TemporaryClob tempClob = null;
    try {
      cstmt.setString(1, _form.getFormInstanceId());
      cstmt.setString(2, detElement.getCui());

      if (det.getSystemStandardValues().containsValue(value.getValue())) {
        cstmt.setString(3, value.getValue());
        cstmt.setNull(4, Types.DATE);
        cstmt.setNull(5, Types.VARCHAR);
        cstmt.setNull(6, Types.CLOB);
        cstmt.setNull(7, Types.VARCHAR);
        cstmt.setNull(8, Types.NUMERIC);
        cstmt.setNull(9, Types.VARCHAR);
      }
      else {
        if (detElement.isDatatypeCv()) {
          cstmt.setString(3, value.getValue());
          cstmt.setNull(4, Types.DATE);
          cstmt.setNull(5, Types.VARCHAR);
          cstmt.setNull(6, Types.CLOB);
          cstmt.setNull(7, Types.VARCHAR);
          cstmt.setNull(8, Types.NUMERIC);
          String otherValue = value.getValueOther();
          if (ApiFunctions.isEmpty(otherValue)) {
            cstmt.setNull(9, Types.VARCHAR);
          }
          else {
            cstmt.setString(9, value.getValueOther());
          }
        }
        else if (detElement.isDatatypeDate()) {
          cstmt.setNull(3, Types.VARCHAR);
          cstmt.setDate(4, KcDaoUtils.getValueDate(value));
          cstmt.setNull(5, Types.VARCHAR);
          cstmt.setNull(6, Types.CLOB);
          cstmt.setNull(7, Types.VARCHAR);
          cstmt.setNull(8, Types.NUMERIC);
          cstmt.setNull(9, Types.VARCHAR);
        }
        else if (detElement.isDatatypeVpd()) {
          VariablePrecisionDate vpd = KcDaoUtils.getValueVpd(value);
          cstmt.setNull(3, Types.VARCHAR);
          cstmt.setDate(4, vpd.getDate());
          cstmt.setString(5, vpd.getPrecision());
          cstmt.setNull(6, Types.CLOB);
          cstmt.setNull(7, Types.VARCHAR);
          cstmt.setNull(8, Types.NUMERIC);
          cstmt.setNull(9, Types.VARCHAR);
        }
        else if (detElement.isDatatypeReport()) {
          cstmt.setNull(3, Types.VARCHAR);
          cstmt.setNull(4, Types.DATE);
          cstmt.setNull(5, Types.VARCHAR);
          tempClob = new TemporaryClob(cstmt.getConnection(), value.getValue());
          cstmt.setClob(6, tempClob.getSQLClob());
          cstmt.setNull(7, Types.VARCHAR);
          cstmt.setNull(8, Types.NUMERIC);
          cstmt.setNull(9, Types.VARCHAR);
        }
        else if (detElement.isDatatypeText()) {
          cstmt.setNull(3, Types.VARCHAR);
          cstmt.setNull(4, Types.DATE);
          cstmt.setNull(5, Types.VARCHAR);
          cstmt.setNull(6, Types.CLOB);
          cstmt.setString(7, value.getValue());
          cstmt.setNull(8, Types.NUMERIC);
          cstmt.setNull(9, Types.VARCHAR);
        }
        else if (detElement.isDatatypeFloat()) {
          cstmt.setNull(3, Types.VARCHAR);
          cstmt.setNull(4, Types.DATE);
          cstmt.setNull(5, Types.VARCHAR);
          cstmt.setNull(6, Types.CLOB);
          cstmt.setNull(7, Types.VARCHAR);
          cstmt.setBigDecimal(8, KcDaoUtils.getValueBigDecimal(value));
          cstmt.setNull(9, Types.VARCHAR);
        }
        else if (detElement.isDatatypeInt()) {
          cstmt.setNull(3, Types.VARCHAR);
          cstmt.setNull(4, Types.DATE);
          cstmt.setNull(5, Types.VARCHAR);
          cstmt.setNull(6, Types.CLOB);
          cstmt.setNull(7, Types.VARCHAR);
          cstmt.setInt(8, KcDaoUtils.getValueInt(value).intValue());
          cstmt.setNull(9, Types.VARCHAR);
        }
        else {
          throw new ApiException("In bindAndExecuteCreateSingleEav: unknown datatype: " + detElement.getDatatype());
        }
      }
      
      cstmt.registerOutParameter(10, Types.INTEGER); 
      cstmt.execute();
    }
    finally {
      if (tempClob != null) {
        ApiFunctions.close(tempClob, cstmt.getConnection());
      }
    }
  }
  
  private void createMulti() {
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    Connection con = ApiFunctions.getDbConnection();
    BigrCallableStatement cstmt = null;
    try {
      cstmt = new BigrCallableStatement(con, getSqlForCreateMulti());

      DataElement[] dataElements = _form.getDataElements();
      for (int i = 0; i < dataElements.length; i++) {
        DataElement dataElement = dataElements[i];
        DetDataElement detElement = det.getDataElement(dataElement.getCuiOrSystemName());
        if (detElement.isMultivalued()) {
          DataElementValue[] vs = dataElement.getDataElementValues();
          for (int j = 0; j < vs.length; j++) {
            DataElementValue v = vs[j];
            String value = v.getValue();
            String otherValue = v.getValueOther();
            if (ApiFunctions.isEmpty(value) && !ApiFunctions.isEmpty(otherValue)) {
              v.setValue(detElement.getOtherValueCui());
            }
            if (!ApiFunctions.isEmpty(value)) {
              bindAndExecuteCreateMulti(cstmt, v, detElement);
              Integer primaryKey = new Integer(cstmt.getInt(cstmt.getNumBindVariables()));      
              if (detElement.isHasAde()) {
                KcAdeDataAccessObjectImpl adeDao = 
                  new KcAdeDataAccessObjectImpl(detElement, v, _form.getFormInstanceId(), primaryKey);
                adeDao.create();
              }
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

  private String getSqlForCreateMulti() {
    StringBuffer buf = new StringBuffer(256);
    buf.append("{ CALL INSERT INTO ");
    buf.append(KcNamingUtils.getDatabaseTableNameMulti(_category));
    buf.append(" (id, form_id, data_element_cui, data_value_cui, other_data_value)");
    buf.append(" VALUES (CIR_SEQ.NEXTVAL, ?, ?, ?, ?)");
    buf.append(" RETURNING id INTO ? }");
    return buf.toString();
  }

  private void bindAndExecuteCreateMulti(CallableStatement cstmt, ElementValue v, 
                                         DetDataElement detElement) throws SQLException {
    cstmt.setString(1, _form.getFormInstanceId());
    cstmt.setString(2, detElement.getCui());
    cstmt.setString(3, v.getValue());
    String other = v.getValueOther();
    if (ApiFunctions.isEmpty(other)) {
      cstmt.setNull(4, Types.VARCHAR);
    }
    else {
      cstmt.setString(4, other);
    }
    cstmt.registerOutParameter(5, Types.INTEGER); 
    cstmt.executeUpdate();
  }

  private void createNotes() {
    StringBuffer buf = new StringBuffer(128);
    buf.append("INSERT INTO ");
    buf.append(KcNamingUtils.getDatabaseTableNameNote(_category));
    buf.append(" (id, data_element_cui, note, form_id)");
    buf.append(" VALUES (CIR_SEQ.NEXTVAL, ?, ?, ?)");

    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement(buf.toString());        
      DataElement[] dataElements = _form.getDataElements();
      for (int i = 0; i < dataElements.length; i++) {
        DataElement dataElement = dataElements[i];
        String note = dataElement.getValueNote();
        if (!ApiFunctions.isEmpty(note)) {
          pstmt.setString(1, det.getDataElement(dataElement.getCuiOrSystemName()).getCui());
          pstmt.setString(2, note);
          pstmt.setString(3, _form.getFormInstanceId());
          pstmt.executeUpdate();
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  public FormInstance delete() {
    FormInstance form = findByFormInstanceId();
    if (form != null) {
      deleteAde();
      deleteNotes();
      if (_category.hasMultivaluedDataElements()) {
        deleteMulti();
      }
      if (_category.hasSingleValuedDataElements()) {
        if (_category.isDatabaseTypeEav()) {
          deleteSingleEav();
        }
        else {
          deleteSingleConventional();
        }
      }
    }
    _pks = null;
    return form;
  }

  private void deleteAde() {
    if (_pks != null) {
      DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
      String formId = _form.getFormInstanceId();

      Iterator dataElementCuis = _pks.keySet().iterator();
      while (dataElementCuis.hasNext()) {
        String dataElementCui = (String) dataElementCuis.next();
        DetDataElement detElement = det.getDataElement(dataElementCui);
        if (detElement.isHasAde()) {
          Iterator pks = ((List) _pks.get(dataElementCui)).iterator();
          while (pks.hasNext()) {
            KcAdeDataAccessObject adeDao = new KcAdeDataAccessObjectImpl(detElement, null, formId, (Integer) pks.next());
            adeDao.delete();
          }
        }
      }
    }
  }

  private void deleteNotes() {
    StringBuffer buf = new StringBuffer(256);
    buf.append("DELETE FROM ");
    buf.append(KcNamingUtils.getDatabaseTableNameNote(_category));
    buf.append(" WHERE form_id = ?");
    
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement(buf.toString());
      pstmt.setString(1, _form.getFormInstanceId());
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
  
  private void deleteMulti() {
    StringBuffer buf = new StringBuffer(256);
    buf.append("DELETE FROM ");
    buf.append(KcNamingUtils.getDatabaseTableNameMulti(_category));
    buf.append(" WHERE form_id = ?");
    
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement(buf.toString());
      pstmt.setString(1, _form.getFormInstanceId());
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

  private void deleteSingleEav() {
    StringBuffer buf = new StringBuffer(256);
    buf.append("DELETE FROM ");
    buf.append(KcNamingUtils.getDatabaseTableNameEav(_category));
    buf.append(" WHERE form_id = ?");
    
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement(buf.toString());
      pstmt.setString(1, _form.getFormInstanceId());
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

  private void deleteSingleConventional() {
    StringBuffer buf = new StringBuffer(256);
    buf.append("DELETE FROM ");
    buf.append(KcNamingUtils.getDatabaseTableNameConventional(_category));
    buf.append(" WHERE form_id = ?");
    
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement(buf.toString());
      pstmt.setString(1, _form.getFormInstanceId());
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

  public FormInstance findByFormInstanceId() {
    _pks = new HashMap();
    boolean foundForm = false;
    FormInstance form = new FormInstance();
    form.setFormInstanceId(_form.getFormInstanceId());
    if (_category.hasSingleValuedDataElements()) {
      if (_category.isDatabaseTypeEav()) {
        foundForm = findSingleEav(form) || foundForm;
      }
      else {
        foundForm = findSingleConventional(form) || foundForm;
      }
    }

    if (_category.hasMultivaluedDataElements()) {
      foundForm = findMulti(form) || foundForm;
    }

    foundForm = findNotes(form) || foundForm;
    
    return (foundForm) ? form : null;
  }

  private boolean findSingleEav(FormInstance form) {
    boolean foundDataElements = false;
    String tableName = KcNamingUtils.getDatabaseTableNameEav(_category);
    StringBuffer buf = new StringBuffer(256);
    buf.append("SELECT ");
    buf.append(tableName);
    buf.append(".*, ");
    buf.append(tableName);
    buf.append(".id as \"eav_id\" FROM ");
    buf.append(tableName);
    buf.append(" WHERE ");
    buf.append(tableName);
    buf.append(".form_id = ?");
    
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    String formId = _form.getFormInstanceId();
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = con.prepareStatement(buf.toString());
      pstmt.setString(1, formId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        foundDataElements = true;
        String dataElementCui = rs.getString(DatabaseDataElement.COLUMN_DATA_ELEMENT);
        DataElement dataElement = new DataElement(dataElementCui);
        form.addDataElement(dataElement);

        DetDataElement detElement = det.getDataElement(dataElementCui);

        DataElementValue value = dataElement.createDataElementValue();
        KcDaoUtils.populateValue(value, detElement, rs);
        
        DatabaseElement dbElement = detElement.getDatabaseElement();
        Integer pk = new Integer(rs.getInt(dbElement.getColumnPrimaryKey()));
        addPkToMap(dataElementCui, pk);
        
        if (detElement.isHasAde()) {
          KcAdeDataAccessObject adeDao = new KcAdeDataAccessObjectImpl(detElement, value, formId, pk);
          adeDao.find();
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return foundDataElements;
  }

  private boolean findSingleConventional(FormInstance form) {
    boolean foundDataElements = false;
    StringBuffer buf = new StringBuffer(256);
    buf.append("SELECT * FROM ");
    buf.append(KcNamingUtils.getDatabaseTableNameConventional(_category));
    buf.append(" WHERE form_id = ?");

    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    String formId = _form.getFormInstanceId();
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = con.prepareStatement(buf.toString());
      pstmt.setString(1, formId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        foundDataElements = true;
        Integer pk = null;
        List dataElements = _category.getDataElementsAllDescendants();
        for (int i = 0; i < dataElements.size(); i++) {
          GbossDataElement gbossDataElement = 
            (GbossDataElement) dataElements.get(i);
          String dataElementCui = gbossDataElement.getCui();
          DetDataElement detElement = det.getDataElement(dataElementCui);
          if (!detElement.isMultivalued()) {
            DataElement dataElement = new DataElement(dataElementCui);
            DataElementValue value = dataElement.createDataElementValue();
            KcDaoUtils.populateValue(value, detElement, rs);

            if (pk == null) {
              DatabaseElement dbElement = detElement.getDatabaseElement();
              pk = new Integer(rs.getInt(dbElement.getColumnPrimaryKey()));
            }

            if (detElement.isHasAde()) {
              KcAdeDataAccessObject adeDao = new KcAdeDataAccessObjectImpl(detElement, value, formId, pk);
              adeDao.find();
            }
            
            if (!value.isEmpty() || (value.getAde() != null)) {
              form.addDataElement(dataElement);
              addPkToMap(dataElementCui, pk);
            }
          }
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return foundDataElements;
  }
  
  private boolean findMulti(FormInstance form) {
    boolean foundDataElements = false;
    StringBuffer buf = new StringBuffer(256);
    buf.append("SELECT * FROM ");
    buf.append(KcNamingUtils.getDatabaseTableNameMulti(_category));
    buf.append(" WHERE form_id = ? ORDER BY id");

    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    String formId = _form.getFormInstanceId();
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = con.prepareStatement(buf.toString());
      pstmt.setString(1, formId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        foundDataElements = true;
        String dataElementCui = rs.getString(DatabaseDataElement.COLUMN_DATA_ELEMENT);
        DataElement dataElement = form.getDataElement(dataElementCui);
        if (dataElement == null) {
          dataElement = new DataElement(dataElementCui);
          form.addDataElement(dataElement);
        }
        DetDataElement detElement = det.getDataElement(dataElementCui);

        DataElementValue value = dataElement.createDataElementValue();
        KcDaoUtils.populateValue(value, detElement, rs);
        
        DatabaseElement dbElement = detElement.getDatabaseElement();
        Integer pk = new Integer(rs.getInt(dbElement.getColumnPrimaryKey()));
        addPkToMap(dataElementCui, pk);

        if (detElement.isHasAde()) {
          KcAdeDataAccessObject adeDao = new KcAdeDataAccessObjectImpl(detElement, value, formId, pk);
          adeDao.find();
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return foundDataElements;
  }
  
  private boolean findNotes(FormInstance form) {
    boolean foundNotes = false;
    StringBuffer buf = new StringBuffer(256);
    buf.append("SELECT * FROM ");
    buf.append(KcNamingUtils.getDatabaseTableNameNote(_category));
    buf.append(" WHERE form_id = ?");
    
    String formId = _form.getFormInstanceId();
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = con.prepareStatement(buf.toString());
      pstmt.setString(1, formId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        foundNotes = true;
        String v = rs.getString("NOTE");
        if (v != null) {
          String dataElementCui = rs.getString(DatabaseDataElement.COLUMN_DATA_ELEMENT);
          DataElement dataElement = form.getDataElement(dataElementCui);
          if (dataElement == null) {
            dataElement = new DataElement(dataElementCui);
            form.addDataElement(dataElement);
          }
          dataElement.setValueNote(v);
        }            
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return foundNotes;
  }
  
  public void update() {
    FormInstance dbForm = delete();

    if (dbForm != null) {
      DataElement[] dbDataElements = dbForm.getDataElements();
      for (int i = 0; i < dbDataElements.length; i++) {
        DataElement dbDataElement = dbDataElements[i];
        if (_form.getDataElement(dbDataElement.getCuiOrSystemName()) == null) {
          _form.addDataElement(dbDataElement);
        }
      }
    }
    
    create();
  }

  private void addPkToMap(String dataElementCui, Integer pk) {
    List pkList = (List) _pks.get(dataElementCui);
    if (pkList == null) {
      pkList = new ArrayList();
      _pks.put(dataElementCui, pkList);
    }
    pkList.add(pk);
  }
}
