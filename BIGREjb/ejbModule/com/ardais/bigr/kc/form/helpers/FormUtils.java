package com.ardais.bigr.kc.form.helpers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.BigrFormDefinitionQueryCriteria;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionLookup;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.security.SecurityInfo;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionQueryCriteria;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.Tag;

public class FormUtils {
  
  public static final String CALCULATION_OPERATION_ADD = "add";
  public static final String CALCULATION_OPERATION_SUBTRACT = "subtract";
  public static final String CALCULATION_OPERATION_MULTIPLY = "multiply";
  public static final String CALCULATION_OPERATION_DIVIDE = "divide";
  public static final String CALCULATION_DATATYPE_INTEGER = "int";
  public static final String CALCULATION_DATATYPE_FLOAT ="float";
  public static final String CALCULATION_DATATYPE_DATE = "date";
  public static final String CALCULATION_DATE_LITERAL_TODAY = "today";

  public static List VALID_CALCULATION_OPERATIONS;
  public static List VALID_CALCULATION_DATATYPES;
  
  static {
    VALID_CALCULATION_OPERATIONS = new ArrayList();
    VALID_CALCULATION_OPERATIONS.add(CALCULATION_OPERATION_ADD);
    VALID_CALCULATION_OPERATIONS.add(CALCULATION_OPERATION_SUBTRACT);
    VALID_CALCULATION_OPERATIONS.add(CALCULATION_OPERATION_MULTIPLY);
    VALID_CALCULATION_OPERATIONS.add(CALCULATION_OPERATION_DIVIDE);
    VALID_CALCULATION_OPERATIONS = Collections.unmodifiableList(VALID_CALCULATION_OPERATIONS);
    
    VALID_CALCULATION_DATATYPES = new ArrayList();
    VALID_CALCULATION_DATATYPES.add(CALCULATION_DATATYPE_INTEGER);
    VALID_CALCULATION_DATATYPES.add(CALCULATION_DATATYPE_FLOAT);
    VALID_CALCULATION_DATATYPES.add(CALCULATION_DATATYPE_DATE);
    VALID_CALCULATION_DATATYPES = Collections.unmodifiableList(VALID_CALCULATION_DATATYPES);
  }

  public static BigrFormDefinition getFormDefinition(SecurityInfo securityInfo, String formDefinitionId) {
    BtxDetailsKcFormDefinitionLookup btxDetails = new BtxDetailsKcFormDefinitionLookup();
    BigrFormDefinition formDefinition = new BigrFormDefinition();
    formDefinition.setFormDefinitionId(formDefinitionId);
    btxDetails.setFormDefinition(formDefinition);
    btxDetails.setTransactionType("kc_form_def_lookup");
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails = (BtxDetailsKcFormDefinitionLookup)Btx.perform(btxDetails);
    return btxDetails.getFormDefinition();
  }
  
  public static List getFormDefinitionsByIdAsList(SecurityInfo securityInfo, Collection formDefinitionIds) {
    List returnValue = new ArrayList();
    BtxDetailsKcFormDefinitionLookup btxDetails = new BtxDetailsKcFormDefinitionLookup();
    BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
    Iterator idIterator = formDefinitionIds.iterator();
    while (idIterator.hasNext()) {
      criteria.addFormDefinitionId((String)idIterator.next());
    }
    btxDetails.setQueryCriteria(criteria);
    btxDetails.setTransactionType("kc_form_defs_lookup");
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails = (BtxDetailsKcFormDefinitionLookup)Btx.perform(btxDetails);
    BigrFormDefinition[] formDefs = btxDetails.getFormDefinitions();
    for (int i=0; i<formDefs.length; i++) {
      returnValue.add(formDefs[i]);
    }
    return returnValue;
  }
  
  /**
   * Return the specified form definitions as a map where each key is a form definition id and
   * the corresponding entry is a BigrFormDefinition.
   * 
   * @param securityInfo the security info of the logged-in user.
   * @param formDefinitionIds the ids of the form definitions to return.  It is not an error
   *    for this list to contain ids that aren't the ids of existing forms.  The result will
   *    only include entries for ids that correspond to existing forms.
   * @return the specified form definitions as a map where each key is a form definition id and
   *    the corresponding entry is a BigrFormDefinition.
   */
  public static Map getFormDefinitionsByIdAsMap(SecurityInfo securityInfo, Collection formDefinitionIds) {
    List formList = getFormDefinitionsByIdAsList(securityInfo, formDefinitionIds);
    Map formMap = new HashMap();
    Iterator formIterator = formList.iterator();
    while (formIterator.hasNext()) {
      BigrFormDefinition form = (BigrFormDefinition) formIterator.next();
      formMap.put(form.getFormDefinitionId(), form);
    }
    return formMap;
  }
  
  public static List getResultsFormDefinitionsForUser(SecurityInfo securityInfo, boolean includeSystemDefault) {
    List returnValue = new ArrayList();
    BtxDetailsKcFormDefinitionLookup btxDetails = new BtxDetailsKcFormDefinitionLookup();
    BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
    criteria.addFormType(FormDefinitionTypes.RESULTS);
    criteria.setUser(securityInfo.getUsername());
	criteria.setRoleIds(securityInfo.getRoleIds());
    btxDetails.setQueryCriteria(criteria);
    btxDetails.setTransactionType("kc_form_defs_lookup");
    btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails = (BtxDetailsKcFormDefinitionLookup)Btx.perform(btxDetails);
    BigrFormDefinition[] formDefs = btxDetails.getFormDefinitions();
    for (int i=0; i<formDefs.length; i++) {
      returnValue.add(formDefs[i]);
    }
    if (includeSystemDefault) {
      returnValue.add(SystemConfiguration.getDefaultResultsView());
    }
    return returnValue;
  }
  
  public static LegalValueSet getFormsAsLVS(List forms) {
    return getFormsAsLVS(forms, false);
  }
  
  public static LegalValueSet getFormsAsLVS(List forms, boolean includeSelectChoice) {
    LegalValueSet returnValue = new LegalValueSet();
    if (!ApiFunctions.isEmpty(forms)) {
      if (includeSelectChoice) {
        returnValue.addLegalValue("", "Select");
      }
      Iterator iterator = forms.iterator();
      while (iterator.hasNext()) {
        BigrFormDefinition form = (BigrFormDefinition)iterator.next();
        returnValue.addLegalValue(form.getFormDefinitionId(), form.getName());
      }
    }
    return returnValue;
  }
  
  public static List getRegistrationFormDefinitions(String accountId, String domainObjectType) {
    List returnValue = new ArrayList();
    FormDefinitionQueryCriteria criteria = new FormDefinitionQueryCriteria();
    criteria.addTag(new Tag(TagTypes.USES, TagTypes.USES_VALUE_REGISTRATION));
    if (!ApiFunctions.isEmpty(accountId)) {
      criteria.addTag(new Tag(TagTypes.ACCOUNT, accountId));
    }
    criteria.addTag(new Tag(TagTypes.DOMAIN_OBJECT, domainObjectType));
    criteria.setEnabled(true);
    FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON.findFormDefinitions(criteria);
    FormDefinition[] formDefs = response.getFormDefinitions();
    for (int i=0; i<formDefs.length; i++) {
      returnValue.add(new BigrFormDefinition(formDefs[i]));
    }
    return returnValue;
  }
  
  public static List getAnnotationFormDefinitions(String accountId, String domainObjectType) {
    List returnValue = new ArrayList();
    FormDefinitionQueryCriteria criteria = new FormDefinitionQueryCriteria();
    criteria.addTag(new Tag(TagTypes.USES, TagTypes.USES_VALUE_ANNOTATION));
    if (!ApiFunctions.isEmpty(accountId)) {
      criteria.addTag(new Tag(TagTypes.ACCOUNT, accountId));
    }
    criteria.addTag(new Tag(TagTypes.DOMAIN_OBJECT, domainObjectType));
    criteria.setEnabled(true);
    FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON.findFormDefinitions(criteria);
    FormDefinition[] formDefs = response.getFormDefinitions();
    for (int i=0; i<formDefs.length; i++) {
      returnValue.add(new BigrFormDefinition(formDefs[i]));
    }
    return returnValue;
  }
  
  public static boolean isRegistrationFormValid(FormDefinition formDef, 
                                               String accountId, String domainObjectType) {
    boolean returnValue = true;
    
    //if the form is not enabled, return false
    if (!formDef.isEnabled()) {
      returnValue = false;
    }

    //make sure the form is a registration form, is for the specified account, and is for
    //the specified domain object type
    Tag[] tags = formDef.getTags();
    if (tags == null || tags.length <= 0) {
      returnValue = false;
    }
    else {
      boolean isRegistrationForm = false;
      boolean isForAccount = false;
      boolean isForObject = false;
      for (int i=0; i<tags.length; i++) {
        Tag tag = tags[i];
        if (TagTypes.USES.equalsIgnoreCase(tag.getType())) {
          isRegistrationForm = TagTypes.USES_VALUE_REGISTRATION.equalsIgnoreCase(tag.getValue());
        }
        else if (TagTypes.ACCOUNT.equalsIgnoreCase(tag.getType())) {
          isForAccount = accountId.equalsIgnoreCase(tag.getValue());
        }
        else if (TagTypes.DOMAIN_OBJECT.equalsIgnoreCase(tag.getType())) {
          isForObject = domainObjectType.equalsIgnoreCase(tag.getValue());
        }
      }
      if (!isRegistrationForm || !isForAccount || !isForObject) {
        returnValue = false;
      }
    }
    
    return returnValue;
  }
  
}
