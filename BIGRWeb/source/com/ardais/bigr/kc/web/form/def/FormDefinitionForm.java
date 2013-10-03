package com.ardais.bigr.kc.web.form.def;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.BigrFormDefinitionQueryCriteria;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinition;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;

/**
 * Struts Form that contains KnowledgeCapture form definition fields.  This supports the 
 * formdef.jsp JSP that allows both data form definitions and query form definitions to be 
 * created, updated and deleted, and also shows a list of data and query form definitions in a 
 * selected account.
 */
public class FormDefinitionForm extends BigrActionForm {

  /**
   * The ID of this form definition.  This is an input when a form is being updated, and an output
   * when a form is selected for updating. 
   */
  private String _formDefinitionId;

  /**
   * The type of this form definition.  This is an input when a form is being created or updated, 
   * and an output when a form is selected for updating.
   */
  private String _formType;
   
  /**
   * The name of this form definition.  This is never an input, since the name of the form is
   * embedded in the form definition XML document.  This is an output when a form definition is 
   * selected for updating, providing a convenient way to get the name of the form definition
   * to display in the UI.   
   */
  private String _name;
   
  /**
   * The form definition XML document.  This is an input when a form is being created or updated, 
   * and an output when a form is selected for updating. 
   */
  private String _formDefinitionXml;

  /**
   * The BIGR object type with which the form definition is to be associated.  This is an input 
   * when a form is being created or updated, and an output when a form is selected for updating. 
   */
  private String _objectType;
  
  /**
   * The BIGR account with which the form definition is to be associated.  This is an input 
   * when a form is being created or updated, and an output when a form is selected for updating. 
   */
  private String _account;

  /**
   * The account that is selected to determine the list of form definitions that should be shown.  
   * This is an input that is used to query for the list of form definitions to be shown for
   * the account, and is also an output to ensure that the account selected in the dropdown
   * remains selected.
   */
  private String _selectedAccount;

  /**
   * The list of accounts.  This is an output that is used to populate the account
   * dropdown list.
   */
  private LegalValueSet _accountList;

  /**
   * Whether the form definition is to be enabled or disabled.  This is an input when a form is 
   * being created or updated, and an output when a form definition is selected for updating. 
   */
  private boolean _enabled;

  /**
   * The context in which this form definition can be used.  This is an input when a form is being 
   * created or updated, and an output when a form is selected for updating.
   */
  private String _uses;

  /**
   * The list of form definitions associated with the selected account.  This is an output that is
   * populated when a list of form definitions for a selected account is requested. 
   */
  private BigrFormDefinition[] _formDefs;
  
  /**
   * If one or more data elements are being removed in an update operation, this indicates whether
   * the user confirmed this and the update should proceed.  
   */
  private boolean _confirmedRemove;

  /**
   * If one or more data elements are being removed in an update operation, this will contain a
   * confirm message.
   */
  private String _confirmRemoveMessage;

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _name = null;
    _selectedAccount = null;
    _formDefs = null;
    resetFormDefinitionInputFields();
  }
  
  /**
   * Resets the input fields on the associated JSP to their default values.
   */
  private void resetFormDefinitionInputFields() {
    _formType = null;
    _formDefinitionId = null;
    _formDefinitionXml = null;
    _objectType = null;
    _uses = null;
    _account = null;
    _enabled = false;
  }

  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {
    
    // Create a new BIGR form definition and populate it with the user's input.
    BigrFormDefinition form = new BigrFormDefinition();
    BigrBeanUtilsBean.SINGLETON.copyProperties(form, this);
    
    // Create a new query criteria and set the selected form types and account.  If an account
    // is not selected, then use the account of the logged in user by default.
    BigrFormDefinitionQueryCriteria criteria = new BigrFormDefinitionQueryCriteria();
    criteria.addFormType(FormDefinitionTypes.DATA);
    criteria.addFormType(FormDefinitionTypes.QUERY);
    String account = getSelectedAccount();
    if (ApiFunctions.isEmpty(account)) {
      account = btxDetails0.getLoggedInUserSecurityInfo().getAccount();
      setSelectedAccount(account);
    }
    criteria.setAccount(account);
    
    // Populate the BTX details with the form definition and query criteria.
    BtxDetailsKcFormDefinition formDefBtxDetails = (BtxDetailsKcFormDefinition) btxDetails0;
    formDefBtxDetails.setFormDefinition(form);
    formDefBtxDetails.setQueryCriteria(criteria);
    formDefBtxDetails.setConfirmedRemove(isConfirmedRemove());
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    String btxType = btxDetails.getBTXType();
    if (btxDetails.isTransactionCompleted()) {
      if (btxType.equals(BTXDetails.BTX_TYPE_KC_FORM_DEF_CREATE)
        || btxType.equals(BTXDetails.BTX_TYPE_KC_FORM_DEF_UPDATE)
        || btxType.equals(BTXDetails.BTX_TYPE_KC_FORM_DEF_DELETE)) {
        resetFormDefinitionInputFields();
      }
      else {
        BtxDetailsKcFormDefinition formDefBtxDetails = (BtxDetailsKcFormDefinition) btxDetails;
        BigrBeanUtilsBean.SINGLETON.copyProperties(this, formDefBtxDetails.getFormDefinition());
      }
    }
    
    // If the user attempted to delete a form definition but there was an error (for example 
    // form instances exist), then make sure to reset the input fields.  Otherwise the JSP
    // will think that the form definition that was to be deleted is now being edited.  Only do
    // this for delete though, since for create and update we want to keep the values that the
    // user has entered so they can be corrected by the user.
    else if (btxType.equals(BTXDetails.BTX_TYPE_KC_FORM_DEF_DELETE)) {
      resetFormDefinitionInputFields();
    }
  }

  public String getFormDefinitionId() {
    return _formDefinitionId;
  }

  public void setFormDefinitionId(String id) {
    _formDefinitionId = id;
  }

  public String getFormDefinitionXml() {
    return _formDefinitionXml;
  }

  public void setFormDefinitionXml(String xml) {
    _formDefinitionXml = xml;
  }

  public String getFormType() {
    return _formType;
  }

  public void setFormType(String formType) {
    _formType = formType;
  }

  public String getAccount() {
    return _account;
  }

  public void setAccount(String account) {
    _account = account;
  }

  public LegalValueSet getAccountList() {
    return _accountList;
  }

  public void setAccountList(LegalValueSet list) {
    _accountList = list;
  }

  public boolean isEnabled() {
    return _enabled;
  }

  public void setEnabled(boolean enabled) {
    _enabled = enabled;
  }

  public String getObjectType() {
    return _objectType;
  }

  public void setObjectType(String type) {
    _objectType = type;
  }

  public LegalValueSet getObjectTypeList() {
    return SystemConfiguration.getLegalValueSet(SystemConfiguration.LEGAL_VALUE_SET_KC_ANNOTATED_OBJECTS);
  }

  public String getUses() {
    return _uses;
  }

  public void setUses(String uses) {
    _uses = uses;
  }

  public LegalValueSet getUsesList() {
    return SystemConfiguration.getLegalValueSet(SystemConfiguration.LEGAL_VALUE_SET_DATA_FORM_USES);
  }
  
  public BigrFormDefinition[] getFormDefinitions() {
    if (_formDefs == null) {
      _formDefs = new BigrFormDefinition[0];
    }
    return _formDefs;
  }

  public void setFormDefinitions(BigrFormDefinition[] definitions) {
    _formDefs = definitions;
  }

  public String getName() {
    return _name;
  }

  public void setName(String name) {
    _name = name;
  }

  public String getSelectedAccount() {
    return _selectedAccount;
  }

  public void setSelectedAccount(String account) {
    _selectedAccount = account;
  }

  public boolean isConfirmedRemove() {
    return _confirmedRemove;
  }
  
  public void setConfirmedRemove(boolean confirmedRemove) {
    _confirmedRemove = confirmedRemove;
  }

  public String getConfirmRemoveMessage() {
    return _confirmRemoveMessage;
  }

  public void setConfirmRemoveMessage(String confirmRemoveMessage) {
    _confirmRemoveMessage = confirmRemoveMessage;
  }
}
