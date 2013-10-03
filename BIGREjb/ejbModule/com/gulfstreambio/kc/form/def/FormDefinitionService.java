package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.shared.SharedViewService;
import org.springframework.util.CollectionUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides all of the services necessary to manipulate and retrieve form definitions. 
 */
public class FormDefinitionService {

  private static final String ENCODING = "XML1";
  
  private FormDefinitionCache _cache = new FormDefinitionCache();

  /**
   * The singleton instance for this class.
   */
  public static FormDefinitionService SINGLETON = new FormDefinitionService();

  /**
   * Creates a data form definition from the specified input <code>DataFormDefinition</code>.  If any tags 
   * are specified in the input they are associated with the form definition.  Before creating the
   * form definition, 
   * {@link #validateCreateDataFormDefinition(DataFormDefinition) validateCreateDataFormDefinition} is called
   * to perform all necessary validations.  
   * 
   * @param  formDefinition  the <code>DataFormDefinition</code>
   * @return  The response, which contains the <code>DataFormDefinition</code> representing the form
   * definition that was created, including its id.  If the validate method detects any errors, 
   * they are returned in the response instead of the <code>DataFormDefinition</code>.
   */
  public FormDefinitionServiceResponse createDataFormDefinition(DataFormDefinition formDefinition) {
    // Perform validation first, and don't proceed if there are any errors.
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateCreateDataFormDefinition(formDefinition);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    if (formDefinition.getTags().length > 0) {
      errors = validateAddFormDefinitionTags(formDefinition);
      if (!errors.empty()) {
        response.setErrors(errors);
        return response;
      }
    }
    
    //create the form definition
    createFormDefinition(formDefinition);

    // Return the response with the new form definition.
    response.addFormDefinition(formDefinition);    
    return response;
  }

  /**
   * Performs validation for creating a data form definition from the specified input 
   * <code>DataFormDefinition</code>.  This is called by 
   * {@link #createDataFormDefinition(DataFormDefinition) createDataFormDefinition} before actually creating
   * a new data form definition to determine whether there are any errors in the input, and can also
   * be called by other clients for similar purposes. 
   * 
   * @param  formDefinition  the <code>DataFormDefinition</code> that is to be used to create a new
   *                         form definition 
   * @return  A <code>BtxActionErrors</code> instance indicating whether any validation errors
   *          were found.  If no errors were found, then an empty <code>BtxActionErrors</code>
   *          is returned. 
   */
  public BtxActionErrors validateCreateDataFormDefinition(DataFormDefinition formDefinition) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinition);
    validator.setCheckCreateable(true);
    return validator.validate(); 
  }

  /**
   * Creates a results form definition from the specified input <code>ResultsFormDefinition</code>.  If any tags 
   * are specified in the input they are associated with the form definition.  Before creating the
   * form definition, 
   * {@link #validateCreateResultsFormDefinition(ResultsFormDefinition) validateCreateResultsFormDefinition} is called
   * to perform all necessary validations.  
   * 
   * @param  formDefinition  the <code>ResultsFormDefinition</code>
   * @return  The response, which contains the <code>ResultsFormDefinition</code> representing the form
   * definition that was created, including its id.  If the validate method detects any errors, 
   * they are returned in the response instead of the <code>ResultsFormDefinition</code>.
   */
  public FormDefinitionServiceResponse createResultsFormDefinition(ResultsFormDefinition formDefinition) {
    // Perform validation first, and don't proceed if there are any errors.
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateCreateResultsFormDefinition(formDefinition);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    if (formDefinition.getTags().length > 0) {
      errors = validateAddFormDefinitionTags(formDefinition);
      if (!errors.empty()) {
        response.setErrors(errors);
        return response;
      }
    }
    
    //create the form definition
    createFormDefinition(formDefinition);

    // Return the response with the new form definition.
    response.addFormDefinition(formDefinition);    
    return response;
  }

  /**
   * Performs validation for creating a results form definition from the specified input 
   * <code>ResultsFormDefinition</code>.  This is called by 
   * {@link #createResultsFormDefinition(ResultsFormDefinition) createResultsFormDefinition} before actually creating
   * a new form definition to determine whether there are any errors in the input, and can also
   * be called by other clients for similar purposes. 
   * 
   * @param  formDefinition  the <code>ResultsFormDefinition</code> that is to be used to create a new
   *                         results form definition 
   * @return  A <code>BtxActionErrors</code> instance indicating whether any validation errors
   *          were found.  If no errors were found, then an empty <code>BtxActionErrors</code>
   *          is returned. 
   */
  public BtxActionErrors validateCreateResultsFormDefinition(ResultsFormDefinition formDefinition) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinition);
    validator.setCheckCreateable(true);
    return validator.validate(); 
  }
  
  /**
   * Creates a query form definition from the specified input <code>QueryFormDefinition</code>.  
   * If any tags are specified in the input they are associated with the form definition.  Before 
   * creating the form definition, 
   * {@link #validateCreateQueryFormDefinition(QueryFormDefinition) validateCreateQueryFormDefinition} 
   * is called to perform all necessary validations.  
   * 
   * @param  formDefinition  the <code>QueryFormDefinition</code>
   * @return  The response, which contains the <code>QueryFormDefinition</code> representing the 
   * form definition that was created, including its id.  If the validate method detects any errors, 
   * they are returned in the response instead of the <code>QueryFormDefinition</code>.
   */
  public FormDefinitionServiceResponse createQueryFormDefinition(QueryFormDefinition formDefinition) {
    // Perform validation first, and don't proceed if there are any errors.
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateCreateQueryFormDefinition(formDefinition);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    if (formDefinition.getTags().length > 0) {
      errors = validateAddFormDefinitionTags(formDefinition);
      if (!errors.empty()) {
        response.setErrors(errors);
        return response;
      }
    }
    
    //create the form definition
    createFormDefinition(formDefinition);

    // Return the response with the new form definition.
    response.addFormDefinition(formDefinition);    
    return response;
  }

  /**
   * Performs validation for creating a query form definition from the specified input 
   * <code>QueryFormDefinition</code>.  This is called by 
   * {@link #createQueryFormDefinition(QueryFormDefinition) createQueryFormDefinition} before 
   * actually creating a new query form definition to determine whether there are any errors in the 
   * input, and can also be called by other clients for similar purposes. 
   * 
   * @param  formDefinition  the <code>QueryFormDefinition</code> that is to be used to create a 
   *                         new form definition 
   * @return  A <code>BtxActionErrors</code> instance indicating whether any validation errors
   *          were found.  If no errors were found, then an empty <code>BtxActionErrors</code>
   *          is returned. 
   */
  public BtxActionErrors validateCreateQueryFormDefinition(QueryFormDefinition formDefinition) {
      KcFormDefinitionValidationService validator = 
          new KcFormDefinitionValidationService(formDefinition);
      validator.setCheckCreateable(true);
      return validator.validate(); 
  }

  private void createFormDefinition(FormDefinition formDefinition) {
    // Perform the insert to create the form definition.
    String insertString =
      "{ call insert"
      + " into cir_form_definition"
      + " (form_name, form_definition, form_definition_encoding, enabled_yn, form_type)"
      + " values (?, ?, ?, ?, ?) returning form_definition_id into ? }";

    String formDefinitionId = null;
    Connection con = null;
    TemporaryClob clob = null;
    CallableStatement cstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      cstmt = con.prepareCall(insertString);

      cstmt.setString(1, formDefinition.getName());
      clob = new TemporaryClob(con, formDefinition.toXml());
      cstmt.setClob(2, clob.getSQLClob());
      cstmt.setString(3, ENCODING);
      cstmt.setString(4, formDefinition.isEnabled() ? "Y" : "N");
      cstmt.setString(5, formDefinition.getType());
      cstmt.registerOutParameter(6, Types.VARCHAR);
      cstmt.execute();
      formDefinitionId = cstmt.getString(6);     
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(clob, con);
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }

    // Set the id of the new form definition.
    formDefinition.setFormDefinitionId(formDefinitionId);
    
    // If there are tags, then associated them with the new form.
    if (formDefinition.getTags().length > 0) {
      FormDefinitionServiceResponse tagsResponse = addFormDefinitionTags(formDefinition);
    }

  }

  public FormDefinitionServiceResponse addFormDefinitionTags(FormDefinition formDefinition) {
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateAddFormDefinitionTags(formDefinition);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    String insertString =
      "{ call insert into cir_form_definition_tags (form_definition_id, tag, tag_type)"
      + " values (?, ?, ?) returning id into ? }";

    String formDefinitionId = formDefinition.getFormDefinitionId();
    Connection con = null;
    CallableStatement cstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      cstmt = con.prepareCall(insertString);

      Tag[] tags = formDefinition.getTags();
      for (int i = 0; i < tags.length; i++) {
        Tag tag = tags[i];
        cstmt.setString(1, formDefinitionId);
        cstmt.setString(2, tag.getValue());
        cstmt.setString(3, tag.getType());
        cstmt.registerOutParameter(4, Types.VARCHAR);
        cstmt.execute();
        String id = cstmt.getString(4);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }
    
    // Remove the updated form definition from the cache.
    _cache.remove(formDefinitionId);

    return response;
  }

  public BtxActionErrors validateAddFormDefinitionTags(FormDefinition formDefinition) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinition);
    validator.setCheckTagsSpecified(true);
    return validator.validate(); 
  }

  public FormDefinitionServiceResponse deleteFormDefinitionTags(FormDefinition formDefinition) {
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateDeleteFormDefinitionTags(formDefinition);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    String deleteString =
      "delete from cir_form_definition_tags where form_definition_id = ? and " +
      "tag_type = ? and tag = ?";

    String formDefinitionId = formDefinition.getFormDefinitionId();
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareCall(deleteString);

      Tag[] tags = formDefinition.getTags();
      for (int i = 0; i < tags.length; i++) {
        Tag tag = tags[i];
        if (!ApiFunctions.isEmpty(formDefinitionId) && 
            !ApiFunctions.isEmpty(tag.getType()) && 
            !ApiFunctions.isEmpty(tag.getValue())) {
          pstmt.setString(1, formDefinitionId);
          pstmt.setString(2, tag.getType());
          pstmt.setString(3, tag.getValue());
          pstmt.execute();
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
    
    // Remove the updated form definition from the cache.
    _cache.remove(formDefinitionId);

    return response;
  }

  public BtxActionErrors validateDeleteFormDefinitionTags(FormDefinition formDefinition) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinition);
    validator.setFormDefinitionId(formDefinition.getFormDefinitionId());
    validator.setCheckIdSpecified(true);
    validator.setCheckTagsSpecified(true);
    return validator.validate(); 
  }

  public FormDefinitionServiceResponse updateDataFormDefinition(DataFormDefinition formDefinition) {
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateUpdateDataFormDefinition(formDefinition);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }
    
    //update the form definition
    updateFormDefinition(formDefinition);

    _cache.removeResultsForms();
    
    response.addFormDefinition(formDefinition);    
    return response;
  }

  /**
   * Performs validation for updating a data form definition from the specified input 
   * <code>DataFormDefinition</code>.  This is called by 
   * {@link #updateDataFormDefinition(DataFormDefinition) updateDataFormDefinition} before actually updating
   * a form definition to determine whether there are any errors in the input, and can also
   * be called by other clients for similar purposes. 
   * 
   * @param  formDefinition  the <code>DataFormDefinition</code> that is to be used to update a 
   *                         form definition 
   * @return  A <code>BtxActionErrors</code> instance indicating whether any validation errors
   *          were found.  If no errors were found, then an empty <code>BtxActionErrors</code>
   *          is returned. 
   */
  public BtxActionErrors validateUpdateDataFormDefinition(DataFormDefinition formDefinition) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinition);
    validator.setCheckUpdateable(true);
    return validator.validate(); 
  }

  public FormDefinitionServiceResponse updateResultsFormDefinition(ResultsFormDefinition formDefinition) {
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateUpdateResultsFormDefinition(formDefinition);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }
    
    //update the form definition
    updateFormDefinition(formDefinition);

    response.addFormDefinition(formDefinition);    
    return response;
  }

  /**
   * Performs validation for updating a results form definition from the specified input 
   * <code>ResultsFormDefinition</code>.  This is called by 
   * {@link #updateResultsFormDefinition(ResultsFormDefinition) updateResultsFormDefinition} before actually updating
   * a results form definition to determine whether there are any errors in the input, and can also
   * be called by other clients for similar purposes. 
   * 
   * @param  formDefinition  the <code>ResultsFormDefinition</code> that is to be used to update a 
   *                         form definition 
   * @return  A <code>BtxActionErrors</code> instance indicating whether any validation errors
   *          were found.  If no errors were found, then an empty <code>BtxActionErrors</code>
   *          is returned. 
   */
  public BtxActionErrors validateUpdateResultsFormDefinition(ResultsFormDefinition formDefinition) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinition);
    validator.setCheckUpdateable(true);
    return validator.validate(); 
  }
  
  public FormDefinitionServiceResponse updateQueryFormDefinition(QueryFormDefinition formDefinition) {
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateUpdateQueryFormDefinition(formDefinition);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }
    
    //update the form definition
    updateFormDefinition(formDefinition);

    response.addFormDefinition(formDefinition);    
    return response;
  }

  /**
   * Performs validation for updating a data form definition from the specified input 
   * <code>DataFormDefinition</code>.  This is called by 
   * {@link #updateDataFormDefinition(DataFormDefinition) updateDataFormDefinition} before actually updating
   * a form definition to determine whether there are any errors in the input, and can also
   * be called by other clients for similar purposes. 
   * 
   * @param  formDefinition  the <code>DataFormDefinition</code> that is to be used to update a 
   *                         form definition 
   * @return  A <code>BtxActionErrors</code> instance indicating whether any validation errors
   *          were found.  If no errors were found, then an empty <code>BtxActionErrors</code>
   *          is returned. 
   */
  public BtxActionErrors validateUpdateQueryFormDefinition(QueryFormDefinition formDefinition) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinition);
        validator.setCheckUpdateable(true);
    return validator.validate(); 
  }

  private void updateFormDefinition(FormDefinition formDefinition) {
    String updateString =
      "update cir_form_definition"
      + " set form_name = ?, form_definition = ?, form_definition_encoding = ?, enabled_yn = ?, form_type = ?"
      + " where form_definition_id = ?";

    String formDefinitionId = formDefinition.getFormDefinitionId();
    Connection con = null;
    TemporaryClob clob = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(updateString);

      pstmt.setString(1, formDefinition.getName());
      clob = new TemporaryClob(con, formDefinition.toXml());
      pstmt.setClob(2, clob.getSQLClob());
      pstmt.setString(3, ENCODING);
      pstmt.setString(4, formDefinition.isEnabled() ? "Y" : "N");
      pstmt.setString(5, formDefinition.getType());
      pstmt.setString(6, formDefinitionId);
      int numRows = pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(clob, con);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    if (formDefinition.getTags().length > 0) {
      deleteAllFormDefinitionTags(formDefinitionId);
      addFormDefinitionTags(formDefinition);
    }
    
    // Remove the updated form definition from the cache.
    _cache.remove(formDefinitionId);
  }

  public FormDefinitionServiceResponse deleteDataFormDefinition(String formDefinitionId) {
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateDeleteDataFormDefinition(formDefinitionId);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }
    
    // First find the form definition so it can be returned.
    response = findFormDefinitionById(formDefinitionId);
    
    //delete the form definition
    deleteFormDefinition(formDefinitionId);
    
    _cache.removeResultsForms();

    return response;
  }

  public BtxActionErrors validateDeleteDataFormDefinition(String formDefinitionId) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinitionId);
    validator.setCheckDeleteable(true);
    return validator.validate(); 
  }

  public FormDefinitionServiceResponse deleteQueryFormDefinition(String formDefinitionId) {
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateDeleteQueryFormDefinition(formDefinitionId);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }
    
    // First find the form definition so it can be returned.
    response = findFormDefinitionById(formDefinitionId);
    
    //delete the form definition
    deleteFormDefinition(formDefinitionId);
    
    return response;
  }

  public BtxActionErrors validateDeleteQueryFormDefinition(String formDefinitionId) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinitionId);
    validator.setCheckDeleteable(true);
    return validator.validate(); 
  }

  public FormDefinitionServiceResponse deleteResultsFormDefinition(String formDefinitionId) {
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateDeleteResultsFormDefinition(formDefinitionId);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }
    
    // First find the form definition so it can be returned.
    response = findFormDefinitionById(formDefinitionId);
    
    //delete the form definition
    deleteFormDefinition(formDefinitionId);
    
    return response;
  }

  public BtxActionErrors validateDeleteResultsFormDefinition(String formDefinitionId) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinitionId);
    validator.setCheckDeleteable(true);
    return validator.validate(); 
  }
  
  private void deleteFormDefinition(String formDefinitionId) {
    deleteAllFormDefinitionTags(formDefinitionId);
	deleteAllShareViewRecords(formDefinitionId);
        
    String deleteFormString = "delete from cir_form_definition where form_definition_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(deleteFormString);
      pstmt.setString(1, formDefinitionId);
      int numRows = pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    // Remove the deleted form definition from the cache.
    _cache.remove(formDefinitionId);    
  }

	private void deleteAllShareViewRecords(String formDefinitionId)
	{
		String deleteString = "delete from SHARED_VIEWS where FORM_DEFINITION_ID = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement(deleteString);
			pstmt.setString(1, formDefinitionId);
			int numRows = pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			ApiFunctions.throwAsRuntimeException(e);
		}
		finally {
			ApiFunctions.close(pstmt);
			ApiFunctions.close(con);
		}
	}

  private void deleteAllFormDefinitionTags(String formDefinitionId) {
    String deleteTagsString = "delete from cir_form_definition_tags where form_definition_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(deleteTagsString);    
      pstmt.setString(1, formDefinitionId);
      int numRows = pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  public FormDefinitionServiceResponse findFormDefinitionById(String formDefinitionId) {
    FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
    BtxActionErrors errors = validateFindFormDefinitionById(formDefinitionId);
    if (!errors.empty()) {
      response.setErrors(errors);
      return response;
    }

    // First check the cache, and return the form definition from the cache if it is present.
    FormDefinition formDef = _cache.get(formDefinitionId);
    if (formDef != null) {
      response.addFormDefinition(formDef);
      return response;
    }    
    
    // If the form definition is not in the cache, then query for it.
    StringBuffer sql = new StringBuffer();
    sql.append("select d.form_definition_id,");
    sql.append(" d.form_name,");
    sql.append(" d.form_definition,");
    sql.append(" d.form_definition_encoding,");
    sql.append(" d.enabled_yn,");
    sql.append(" d.form_type,");
    sql.append(" t.tag_type,");
    sql.append(" t.tag");
    sql.append(" from cir_form_definition d, cir_form_definition_tags t");
    sql.append(" where d.form_definition_id = ?");
    sql.append(" and t.form_definition_id = d.form_definition_id");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, formDefinitionId);

      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        if (formDef == null) {
          String formDefXml = ApiFunctions.getStringFromClob(rs.getClob("form_definition"));
          String formType = rs.getString("form_type");
          formDef = FormDefinitionFactory.SINGLETON.createFromXml(formType, formDefXml);
          formDef.setFormDefinitionId(rs.getString("form_definition_id"));
          formDef.setName(rs.getString("form_name"));
          String enabled = rs.getString("enabled_yn");          
          formDef.setEnabled((enabled.equals("Y")) ? true : false);
		  SharedViewService.SINGLETON.populateSharedViews(formDef);
		  response.addFormDefinition(formDef);
          _cache.add(formDef);
        }
        formDef.addTag(new Tag(rs.getString("tag_type"),rs.getString("tag")));
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

  public BtxActionErrors validateFindFormDefinitionById(String formDefinitionId) {
    KcFormDefinitionValidationService validator = 
      new KcFormDefinitionValidationService(formDefinitionId);
    validator.setCheckIdSpecified(true);
    return validator.validate(); 
  }

  public FormDefinitionServiceResponse findFormDefinitions(FormDefinitionQueryCriteria criteria) {
    // A map that will contain all of the requested form definitions, keyed by form definition id.
    // This will be filled from the cache or by querying the database, and then be added to
    // the repsonse.
    Map forms = new HashMap();
    
    // First perform a query that only returns the form definition ids.  This relatively 
    // inexpensive query will allow us to check if the forms are in the cache before querying
    // for all form definition data.  Note that the ids are in the proper order in which they
    // should be returned, so that order must be preserved.
    final Set<String> ids = findFormDefinitionIds(criteria);
	  final Set<String> sharedViewIds = new HashSet<String>();
	  if (!CollectionUtils.isEmpty(criteria.getRoleIds()))
	  {
		  sharedViewIds.addAll(SharedViewService.SINGLETON.findSharedFormDefinitionIds(criteria));
	  }
    int numIds = ids.size() + sharedViewIds.size();
    if (numIds > 0) {

      // Create a set of the ids of the form definitions that we'll have to query for.  For now,
      // assume that we'll have to query for all of the form definitions.
      final Set<String> idsToQuery = new HashSet<String>(ids);
	  idsToQuery.addAll(sharedViewIds);

      // Get as many of the form definitions from the cache as possible.  Put those found in the
      // map and remove their ids from the set of ids to be queried.
      List cachedForms = getFormDefinitionsFromCache(ids);
      for (int i = 0; i < cachedForms.size(); i++) {
        FormDefinition form = (FormDefinition) cachedForms.get(i);
        String id = form.getFormDefinitionId();
        forms.put(id, form);
        idsToQuery.remove(id);
      }
      
      // If there are any ids left to query, then perform the query to get the form definitions
      // with those ids, and put them in the map.
      if (idsToQuery.size() > 0) {
        FormDefinitionQueryCriteria idsOnlyCriteria = new FormDefinitionQueryCriteria();
        Iterator i = idsToQuery.iterator();
        while (i.hasNext()) {
          idsOnlyCriteria.addFormDefinitionId((String) i.next());
        }
        Map queriedForms = findFormDefinitionsFull(idsOnlyCriteria);
        i = queriedForms.keySet().iterator();
        while (i.hasNext()) {
          String id = (String) i.next();
          forms.put(id, queriedForms.get(id));          
        }
      }
    }
    
      // Add all of the form definitions to the response and return the response.
      final FormDefinitionServiceResponse response = new FormDefinitionServiceResponse();
	  for (String id : ids)
	  {
		  final FormDefinition formDefinition = (FormDefinition)forms.get(id);
		  if (sharedViewIds.contains(id))
		  {
			  SharedViewService.SINGLETON.populateSharedViews(formDefinition);
		  }
		  response.addFormDefinition(formDefinition);
	  }

	  for (String id : sharedViewIds)
	  {
		  if (!ids.contains(id))
		  {
			  final FormDefinition formDefinition = (FormDefinition)forms.get(id);
			  response.addFormDefinition(formDefinition);
		  }
	  }
	  return response;
  }
  
  /**
   * Queries for and returns only the ids of the form definitions with the specified criteria.
   * 
   * @param criteria  the query criteria
   * @return  The list of ids. 
   */
  private Set<String> findFormDefinitionIds(FormDefinitionQueryCriteria criteria) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    final Set<String> ids = new HashSet<String>();
    try {
      con = ApiFunctions.getDbConnection();
      String sql = buildQuery(criteria, true);
      pstmt = con.prepareStatement(sql);
      bindQuery(criteria, pstmt);
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        ids.add(rs.getString("form_definition_id"));
      }      
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
    return ids;
  }
  
  /**
   * Queries for and returns full information on the form definitions with the specified criteria.
   * 
   * @param criteria  the query criteria
   * @return  A Map of form definitions, keyed by form definition id.  
   */
  private Map findFormDefinitionsFull(FormDefinitionQueryCriteria criteria) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Map forms = new HashMap();
    try {
      con = ApiFunctions.getDbConnection();
      String sql = buildQuery(criteria, false);
      pstmt = con.prepareStatement(sql.toString());
      bindQuery(criteria, pstmt);
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        String id = rs.getString("form_definition_id");
        FormDefinition form = (FormDefinition) forms.get(id);
        if (form == null) {
          String formType = rs.getString("form_type");
          String formXml = ApiFunctions.getStringFromClob(rs.getClob("form_definition"));
          form = FormDefinitionFactory.SINGLETON.createFromXml(formType, formXml);
          form.setFormDefinitionId(id);
          form.setName(rs.getString("form_name"));
          String enabled = rs.getString("enabled_yn");          
          form.setEnabled((enabled.equals("Y")) ? true : false);
          forms.put(id, form);
          _cache.add(form);
        }
        form.addTag(new Tag(rs.getString("tag_type"),rs.getString("tag")));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
    return forms;
  }

  /**
   * Returns a list of form definitions corresponding to the specified ids that are in the cache.
   * 
   * @param ids the ids of the requested form definitions
   * @return The List of form definitions.  If none of the form definitions are in the cache,
   * then an empty list is returned. 
   */
  private List<FormDefinition> getFormDefinitionsFromCache(Set<String> ids) {
    List<FormDefinition> forms = new ArrayList<FormDefinition>();

	  for (String id : ids)
	  {
		  FormDefinition form = _cache.get(id);
		  if (form != null)
		  {
			  forms.add(form);
		  }
	  }
    return forms;
  }
  
  private String buildQuery(FormDefinitionQueryCriteria criteria, boolean idsOnly) {
    // First add all of the columns to return in the query.
    StringBuffer sql = new StringBuffer();
    sql.append("select d.form_definition_id");
    if (!idsOnly) {
      sql.append(", d.form_name,");
      sql.append(" d.form_definition,");
      sql.append(" d.form_definition_encoding,");
      sql.append(" d.enabled_yn,");
      sql.append(" d.form_type,");
      sql.append(" t.tag_type,");
      sql.append(" t.tag");      
    }
    sql.append(" from cir_form_definition d");
    if (!idsOnly) {
      sql.append(", cir_form_definition_tags t");      
    }

    // Next process the tags.  Add a from clause for the tag table for each tag.
    Iterator tags = criteria.getTags().iterator();
    for (int i = 0; tags.hasNext(); tags.next(), i++) {
      sql.append(", cir_form_definition_tags t");
      sql.append(String.valueOf(i));
    }
    
    // Start the where clause and add the join to the tag table to get all of the tags to be
    // returned.
    boolean needWhere = true;
    if (!idsOnly) {
      sql.append(" where t.form_definition_id = d.form_definition_id");
      needWhere = false;
    }
    
    // Finish processing the tags.  Add a where clause for each tag type and value that are 
    // specified.
    tags = criteria.getTags().iterator();
    for (int i = 0; tags.hasNext(); i++) {
      Tag tag = (Tag) tags.next();
      sql.append(needWhere ? " where" : " and");
      needWhere = false;
      sql.append(" t");
      sql.append(i);
      sql.append(".form_definition_id = d.form_definition_id");
      String tagType = tag.getType(); 
      if (!ApiFunctions.isEmpty(tagType)) {
        sql.append(" and t");
        sql.append(i);
        sql.append(".tag_type = ?");        
      }
      String tagValue = tag.getValue(); 
      if (!ApiFunctions.isEmpty(tagValue)) {
        sql.append(" and t");
        sql.append(i);
        sql.append(".tag = ?");        
      }
    }
    
    // Next process the names.  Add a where clause to OR all of the specified names.
    Iterator names = criteria.getFormNames().iterator();
    if (names.hasNext()) {
      sql.append(needWhere ? " where" : " and");
      needWhere = false;
      sql.append(" (");
      for (int i = 0; names.hasNext(); i++) {
        String name = (String) names.next();
        if (i > 0) {
          sql.append(" or ");          
        }
        sql.append("d.form_name = ?");
      }
      sql.append(")");
    }
    
    // Next process the form types.  Add a where clause to OR all of the specified types.
    Iterator types = criteria.getFormTypes().iterator();
    if (types.hasNext()) {
      sql.append(needWhere ? " where" : " and");
      needWhere = false;
      sql.append(" (");
      for (int i = 0; types.hasNext(); i++) {
        String type = (String) types.next();
        if (i > 0) {
          sql.append(" or ");          
        }
        sql.append("d.form_type = ?");
      }
      sql.append(")");
    }

    // Next process the form ids.  Add a where clause to OR all of the specified ids.
    Iterator ids = criteria.getFormDefinitionIds().iterator();
    if (ids.hasNext()) {
      sql.append(needWhere ? " where" : " and");
      needWhere = false;
      sql.append(" (");
      for (int i = 0; ids.hasNext(); i++) {
        String id = (String) ids.next();
        if (i > 0) {
          sql.append(" or ");          
        }
        sql.append("d.form_definition_id = ?");
      }
      sql.append(")");
    }
    
    // Next process the enabled flag.
    Boolean isEnabled = criteria.isEnabled();
    if (isEnabled != null) {
      sql.append(needWhere ? " where" : " and");
      needWhere = false;
      sql.append(" d.enabled_yn = ?");        
    }
    
    // Order by form name, case-insensitive.
    sql.append(" order by upper(d.form_name)");

    return sql.toString();
  }
  
  private void bindQuery(FormDefinitionQueryCriteria criteria, PreparedStatement pstmt) 
    throws SQLException {
    
    int i = 1;

    // Bind tag variables.
    Iterator tags = criteria.getTags().iterator();
    while (tags.hasNext()) {
      Tag tag = (Tag) tags.next();
      String tagType = tag.getType(); 
      if (!ApiFunctions.isEmpty(tagType)) {
        pstmt.setString(i++, tagType);
      }
      String tagValue = tag.getValue(); 
      if (!ApiFunctions.isEmpty(tagValue)) {
        pstmt.setString(i++, tagValue);
      }
    }

    // Bind names.
    Iterator names = criteria.getFormNames().iterator();
    while (names.hasNext()) {
      pstmt.setString(i++, (String) names.next());
    }

    // Bind types.
    Iterator types = criteria.getFormTypes().iterator();
    while (types.hasNext()) {
      pstmt.setString(i++, (String) types.next());
    }

    // Bind ids.
    Iterator ids = criteria.getFormDefinitionIds().iterator();
    while (ids.hasNext()) {
      pstmt.setString(i++, (String) ids.next());
    }

    // Bind enabled.
    if (criteria.isEnabled() != null) {
      if (criteria.isEnabled().booleanValue()) {
        pstmt.setString(i++, "Y");
      }
      else {
        pstmt.setString(i++, "N");
      }
    }    
  }
  
  private FormDefinitionService() {
    super();
  }

}
