package com.gulfstreambio.kc.form;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.gulfstreambio.gboss.GbossCategory;
import com.gulfstreambio.gboss.Gboss;
import com.gulfstreambio.gboss.GbossDataElement;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;

/**
 * Provides all of the services necessary to manipulate and retrieve form instances. 
 */

public class FormInstanceService {

  /**
   * The singleton instance for this class.
   */
  public static FormInstanceService SINGLETON = new FormInstanceService();

  public FormInstanceServiceResponse createFormInstance(FormInstance formInstance) {
    FormInstanceServiceResponse response = new FormInstanceServiceResponse();
    BtxActionErrors errors;
    errors = validateCreateFormInstance(formInstance);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    // Set the creation and modification date/time to be the same.
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    formInstance.setCreationDateTime(ts);
    formInstance.setModificationDateTime(ts);

    // Perform the insert into CIR_FORM.
    String insertString =
      "{ call insert"
      + " into cir_form"
      + " (domain_object_id, domain_object_type, creation_datetime,"
      + " modification_datetime, form_definition_id)"
      + " values (?, ?, ?, ?, ?) returning form_id into ? }";

    String formInstanceId = null;
    Connection con = null;
    CallableStatement cstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      cstmt = con.prepareCall(insertString);

      cstmt.setString(1, formInstance.getDomainObjectId());
      cstmt.setString(2, formInstance.getDomainObjectType());
      cstmt.setTimestamp(3, formInstance.getCreationDateTime());
      cstmt.setTimestamp(4, formInstance.getModificationDateTime());
      cstmt.setString(5, formInstance.getFormDefinitionId());
      cstmt.registerOutParameter(6, Types.VARCHAR);
      cstmt.execute();
      formInstanceId = cstmt.getString(6);     
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }

    // Get the form instance id which was set in a trigger. 
    formInstance.setFormInstanceId(formInstanceId);
    
    // Create the DAOs necessary to persist the contents of the form, and call the create
    // method of each.
    List daos = createDaos(formInstance);
    for (int i = 0; i < daos.size(); i++) {
      KcDataAccessObject dao = (KcDataAccessObject) daos.get(i);
      dao.create();
    }
    
    // Get the form instance to be returned.
    FormInstanceServiceResponse response1 = 
      FormInstanceService.SINGLETON.findFormInstanceById(formInstanceId);
    errors = response1.getErrors();
    if (!errors.empty()) {
      response.setErrors(errors);        
    }
    else {
      response.addFormInstance(response1.getFormInstance());               
    }
    
    return response;
  }
  
  
  public FormInstanceServiceResponse findFormInstanceById(String formInstanceId) {
    FormInstanceServiceResponse response = new FormInstanceServiceResponse();
    BtxActionErrors errors = validateFindFormInstanceById(formInstanceId);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    // First get just the form instance record from CIR_FORM.
    FormInstance formInstance = findFormInstanceRecord(formInstanceId);

    if (formInstance != null) {
      
      // Get the form definition associated with the form instance.
      String formDefinitionId = formInstance.getFormDefinitionId();
      FormDefinitionServiceResponse response1 = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(formDefinitionId);
      errors = response1.getErrors();
      if (!errors.empty()) {
        response.setErrors(errors);
        return response;
      }
      FormDefinition formDefinition = response1.getFormDefinition();
      
      // Put all of the form definition's data element CUIs into a set for easy lookup later.
      FormDefinitionDataElement[] formDefDataElements = formDefinition.getDataElements();
      Set formDefCuis = new HashSet();
      for (int i = 0; i < formDefDataElements.length; i++) {
        formDefCuis.add(formDefDataElements[i].getCui());
      }

      // Create all of the DAOs needed to query the appropriate instance tables for all of the
      // data elements in the form definition.
      List daos = createDaos(formDefinition, formInstanceId);

      // Call the finder on each DAO, and then add the data elements returned in the DAO
      // to the form instance to be returned.  Only add those data elements that are present in
      // form definition.  It is possible that the DAO will return data elements that are not
      // currently in the form definition.  For example if a data element was deleted from the 
      // form definition after some instance data was added for the data element, we do not delete 
      // the instance data so the DAO will return it.  But, we do not want to return it to the 
      // caller of this method since it is deleted from the form definition, and thus should not 
      // be visible to the caller of this method. 
      for (int i = 0; i < daos.size(); i++) {
        KcDataAccessObject dao = (KcDataAccessObject) daos.get(i);
        FormInstance daoFormInstance = dao.findByFormInstanceId();
        if (daoFormInstance != null) {
          DataElement[] dataElements = daoFormInstance.getDataElements();
          for (int j = 0; j < dataElements.length; j++) {
            DataElement dataElement = dataElements[j];
            if (formDefCuis.contains(dataElement.getCuiOrSystemName())) {
              formInstance.addDataElement(dataElement);
            }
          }
        }
      }
    }

    response.addFormInstance(formInstance);       
    return response;
  }

  public FormInstanceServiceResponse findFormInstances(FormInstanceQueryCriteria criteria) {
    FormInstanceServiceResponse response = new FormInstanceServiceResponse();

    // First add all of the columns to return in the query.
    StringBuffer sql = new StringBuffer();
    sql.append("select i.form_id,");
    sql.append(" i.domain_object_id,");
    sql.append(" i.domain_object_type,");
    sql.append(" i.creation_datetime,");
    sql.append(" i.modification_datetime,");
    sql.append(" i.form_definition_id");
    sql.append(" from cir_form i, cir_form_definition d");
    sql.append(" where d.form_definition_id = i.form_definition_id");

    // Add all of the form instance ids.
    Iterator i = criteria.getFormInstanceIds().iterator();
    if (i.hasNext()) {
      sql.append(" and (");
      for (boolean first = true; i.hasNext(); first = false, i.next()) {        
        if (!first) {
          sql.append(" or ");          
        }
        sql.append("i.form_id = ?");
      }
      sql.append(")");
    }
    
    // Add all of the form definition ids.
    i = criteria.getFormDefinitionIds().iterator();
    if (i.hasNext()) {
      sql.append(" and (");
      for (boolean first = true; i.hasNext(); first = false, i.next()) {        
        if (!first) {
          sql.append(" or ");          
        }
        sql.append("i.form_definition_id = ?");
      }
      sql.append(")");
    }
    
    // Add all of the domain object ids.
    i = criteria.getDomainObjectIds().iterator();
    if (i.hasNext()) {
      sql.append(" and (");
      for (boolean first = true; i.hasNext(); first = false, i.next()) {        
        if (!first) {
          sql.append(" or ");          
        }
        sql.append("i.domain_object_id = ?");
      }
      sql.append(")");
    }
    
    // Add all of the domain object types.
    i = criteria.getDomainObjectTypes().iterator();
    if (i.hasNext()) {
      sql.append(" and (");
      for (boolean first = true; i.hasNext(); first = false, i.next()) {        
        if (!first) {
          sql.append(" or ");          
        }
        sql.append("i.domain_object_type = ?");
      }
      sql.append(")");
    }

    // Add the order by clause.
    sql.append(" order by upper(d.form_name), i.creation_datetime ");
    
    // Prepare and perform the query.
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      int index = 1;

      // Bind all of the form instance ids.
      i = criteria.getFormInstanceIds().iterator();
      while (i.hasNext()) {
        pstmt.setString(index++, (String) i.next());
      }
      
      // Bind all of the form definition ids.
      i = criteria.getFormDefinitionIds().iterator();
      while (i.hasNext()) {
        pstmt.setString(index++, (String) i.next());
      }

      // Bind all of the domain object ids.
      i = criteria.getDomainObjectIds().iterator();
      while (i.hasNext()) {
        pstmt.setString(index++, (String) i.next());
      }

      // Bind all of the domain object types.
      i = criteria.getDomainObjectTypes().iterator();
      while (i.hasNext()) {
        pstmt.setString(index++, (String) i.next());
      }

      // Execute the query.
      rs = ApiFunctions.queryDb(pstmt, con);

      // Process the results.
      while (rs.next()) {
        FormInstance formInstance = new FormInstance();
        formInstance.setFormInstanceId(rs.getString("form_id"));
        formInstance.setDomainObjectId(rs.getString("domain_object_id"));
        formInstance.setDomainObjectType(rs.getString("domain_object_type"));
        formInstance.setCreationDateTime(rs.getTimestamp("creation_datetime"));
        formInstance.setModificationDateTime(rs.getTimestamp("modification_datetime"));        
        formInstance.setFormDefinitionId(rs.getString("form_definition_id"));
        response.addFormInstance(formInstance);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
    return response;
  }
  
  public FormInstanceServiceResponse updateFormInstance(FormInstance formInstance) {
    FormInstanceServiceResponse response = new FormInstanceServiceResponse();
    BtxActionErrors errors = validateUpdateFormInstance(formInstance);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    // Set the modification date and time.
    formInstance.setModificationDateTime(new Timestamp(System.currentTimeMillis()));    
    
    // Perform the UPDATE of CIR_FORM.  We only allow domain_object_id and domain_object_type
    // to be be changed.
    String updateString =
      "update cir_form set domain_object_id = ?, " +
      "domain_object_type = ?, modification_datetime = ? where form_id = ?";
    
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(updateString);

      pstmt.setString(1, formInstance.getDomainObjectId());
      pstmt.setString(2, formInstance.getDomainObjectType());
      pstmt.setTimestamp(3, formInstance.getModificationDateTime());
      pstmt.setString(4, formInstance.getFormInstanceId());
      ApiFunctions.queryDb(pstmt, con);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    // Create the DAOs necessary to persist the contents of the form, and call the update
    // method of each.
    List daos = createDaos(formInstance);
    for (int i = 0; i < daos.size(); i++) {
      KcDataAccessObject dao = (KcDataAccessObject) daos.get(i);
      dao.update();
    }

    // Get the form instance to be returned.
    FormInstanceServiceResponse response1 = 
      FormInstanceService.SINGLETON.findFormInstanceById(formInstance.getFormInstanceId());
    errors = response1.getErrors();
    if (!errors.empty()) {
      response.setErrors(errors);        
    }
    else {
      response.addFormInstance(response1.getFormInstance());               
    }
    return response;
  }

  public FormInstanceServiceResponse deleteFormInstance(String formInstanceId) {
    FormInstanceServiceResponse response = new FormInstanceServiceResponse();
    BtxActionErrors errors = validateDeleteFormInstance(formInstanceId);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    FormInstanceServiceResponse response1 = findFormInstanceById(formInstanceId);
    errors = response1.getErrors();
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    FormInstance formInstance = response1.getFormInstance();
    if (formInstance != null) {
      String formDefinitionId = formInstance.getFormDefinitionId();
      FormDefinitionServiceResponse response2 = 
        FormDefinitionService.SINGLETON.findFormDefinitionById(formDefinitionId);
      errors = response2.getErrors();
      if (!errors.empty()) {
        response.setErrors(errors);
        return response;
      }
      FormDefinition formDefinition = response2.getFormDefinition();

      List daos = createDaos(formDefinition, formInstanceId);
      for (int i = 0; i < daos.size(); i++) {
        KcDataAccessObject dao = (KcDataAccessObject) daos.get(i);
        dao.delete();
      }
    }

    // Delete the CIR_FORM record itself.
    String deleteString = "delete cir_form where form_id = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(deleteString);
      pstmt.setString(1, formInstanceId);
      ApiFunctions.queryDb(pstmt, con);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    response.addFormInstance(formInstance);       
    return response;
  }

  /**
   * Finds and returns the form instance record from CIR_FORM only.
   * 
   * @param formInstanceId  the id of the form instance
   * @return  the form instance
   */
  private FormInstance findFormInstanceRecord(String formInstanceId) {
    // Perform the SELECT From CIR_FORM to lookup the form instance.
    StringBuffer sql = new StringBuffer();
    sql.append("select form_id,");
    sql.append(" domain_object_id,"); 
    sql.append(" domain_object_type,");
    sql.append(" creation_datetime,");
    sql.append(" modification_datetime,");
    sql.append(" form_definition_id");
    sql.append(" from cir_form");
    sql.append(" where form_id = ?");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    FormInstance form = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, formInstanceId);

      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        form = new FormInstance();
        form.setFormInstanceId(rs.getString("form_id"));
        form.setDomainObjectId(rs.getString("domain_object_id"));
        form.setDomainObjectType(rs.getString("domain_object_type"));
        form.setCreationDateTime(rs.getTimestamp("creation_datetime"));
        form.setModificationDateTime(rs.getTimestamp("modification_datetime"));
        form.setFormDefinitionId(rs.getString("form_definition_id"));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return form;
  }

  /**
   * Performs validation for creating a form Instance from the specified input 
   * <code>FormInstance</code>.  This is called by 
   * {@link #createFormInstance(FormInstance) createFormInstance} before actually creating
   * a new form Instance to determine whether there are any errors in the input, and can also
   * be called by other clients for similar purposes. 
   * 
   * @param  formInstance  the <code>FormInstance</code> that is to be used to create a new
   *                         form Instance 
   * @return  A <code>BtxActionErrors</code> instance indicating whether any validation errors
   *          were found.  If no errors were found, then an empty <code>BtxActionErrors</code>
   *          is returned. 
   */
  public BtxActionErrors validateCreateFormInstance(FormInstance formInstance) {
    KcFormInstanceValidationService validator = 
      new KcFormInstanceValidationService(formInstance);
    validator.setCheckCreateable(true);
    return validator.validate(); 
  }
  
  /**
   * Performs validation for updating a form Instance from the specified input 
   * <code>FormInstance</code>.  This is called by 
   * {@link #updateFormInstance(FormInstance) updateFormInstance} before actually updating
   * a form instance to determine whether there are any errors in the input, and can also
   * be called by other clients for similar purposes. 
   * 
   * @param  formInstance  the <code>FormInstance</code> that is to be used to update a 
   *                         form instance 
   * @return  A <code>BtxActionErrors</code> instance indicating whether any validation errors
   *          were found.  If no errors were found, then an empty <code>BtxActionErrors</code>
   *          is returned. 
   */
  public BtxActionErrors validateUpdateFormInstance(FormInstance formInstance) {
    KcFormInstanceValidationService validator = 
      new KcFormInstanceValidationService(formInstance);
    validator.setCheckUpdateable(true);
    return validator.validate(); 
  }
  
  public BtxActionErrors validateDeleteFormInstance(String formInstanceId) {
    FormInstance formInstance = new FormInstance();
    formInstance.setFormInstanceId(formInstanceId);
    KcFormInstanceValidationService validator = 
      new KcFormInstanceValidationService(formInstance);
    validator.setCheckDeleteable(true);
    return validator.validate(); 
  }

  public BtxActionErrors validateFindFormInstanceById(String formInstanceId) {
    FormInstance formInstance = new FormInstance();
    formInstance.setFormInstanceId(formInstanceId);
    KcFormInstanceValidationService validator = 
      new KcFormInstanceValidationService(formInstance);
    validator.setCheckIdSpecified(true);
    return validator.validate(); 
  }  
  
  private List createDaos(FormInstance form) {
    List daos = new ArrayList();
    Map daoForms = new HashMap();
    Gboss gboss = GbossFactory.getInstance();
    
    // First separate the input form instance into a form instance per DAO.
    DataElement[] dataElements = form.getDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      DataElement dataElement = dataElements[i];
      GbossDataElement gbossDataElement = 
        gboss.getDataElement(dataElement.getCuiOrSystemName());
      GbossCategory dbCategory = gbossDataElement.getDatabaseCategory();
      FormInstance daoForm = (FormInstance) daoForms.get(dbCategory);
      if (daoForm == null) {
        daoForm = new FormInstance();
        daoForm.setFormInstanceId(form.getFormInstanceId());
        daoForms.put(dbCategory, daoForm);
      }
      daoForm.addDataElement(dataElement);
    }

    // Now get the DAOs and return them.
    Iterator dbCategorys = daoForms.keySet().iterator();
    while (dbCategorys.hasNext()) {
      GbossCategory dbCategory = (GbossCategory) dbCategorys.next();
      FormInstance daoForm = (FormInstance) daoForms.get(dbCategory);
      daos.add(new KcDataAccessObjectImpl(dbCategory, daoForm));
    }
    return daos;
  }
  
  private List createDaos(FormDefinition form, String formInstanceId) {
    List daos = new ArrayList();
    Map daoForms = new HashMap();
    Gboss gboss = GbossFactory.getInstance();
    
    // First separate the input form instance into a form instance per DAO.
    FormDefinitionDataElement[] dataElements = form.getDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      FormDefinitionDataElement dataElement = dataElements[i];
      GbossDataElement gbossDataElement = 
        gboss.getDataElement(dataElement.getCui());
      GbossCategory dbCategory = gbossDataElement.getDatabaseCategory();
      FormInstance daoForm = (FormInstance) daoForms.get(dbCategory);
      if (daoForm == null) {
        daoForm = new FormInstance();
        daoForm.setFormInstanceId(formInstanceId);
        daoForms.put(dbCategory, daoForm);
      }
    }

    // Now get the DAOs and return them.
    Iterator dbCategorys = daoForms.keySet().iterator();
    while (dbCategorys.hasNext()) {
      GbossCategory dbCategory = (GbossCategory) dbCategorys.next();
      FormInstance daoForm = (FormInstance) daoForms.get(dbCategory);
      daos.add(new KcDataAccessObjectImpl(dbCategory, daoForm));
    }
    return daos;
  }

  private FormInstanceService() {
    super();
  }
  
}
