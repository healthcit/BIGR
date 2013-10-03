package com.ardais.bigr.dataImport.web.form;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedCase;
import com.ardais.bigr.iltds.btx.BtxDetailsModifyImportedCase;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;
import com.gulfstreambio.kc.web.support.KcUiContext;
import com.gulfstreambio.kc.web.support.WebUtils;

/**
 * Struts ActionForm class for creating a new case.
 */
public class CaseForm extends BigrActionForm {
  private String _customerId;
  private String _consentId;
  private String _consentId2;
  private String _ardaisId;
  private String _ardaisCustomerId;
  private String _linkedIndicator;
  private String _consentVersionId; // used only for linked cases
  private String _irbProtocolAndVersionName = null; // used only for linked cases
  private String _year; // used only for linked cases
  private String _month; // used only for linked cases; range from 1 to 12
  private String _hours; // used only for linked cases
  private String _minutes; // used only for linked cases
  private String _ampm; // used only for linked cases
  private Date _consentDate; // used only for linked cases, computed from year/month/etc.
  private String _policyId;
  private String _policyName = null;
  private String _diagnosis;
  private String _diagnosisOther = null;
  private String _comments;
  private String _ardaisAcctKey;
  private String _importedYN;
  private List _consentChoices;
  private List _policyChoices;
  private LegalValueSet _pullReasonChoices;
  private String _pullReason;
  private LegalValueSet _pullRequestedByChoices;
  private String _pullRequestedBy;
  private String _pullNote;
  private String _txType; // transaction type. is it Constants.TRANSACTION_ILTDS or Constants.TRANSACTION_SR
  private boolean _readOnly;
  
  private BigrFormDefinition[] _formDefinitions;
  private BigrFormInstance[] _formInstances;
  
  private boolean _createForm;

  private DataFormDefinition _registrationForm;
  private Map _caseRegistrationFormIds;
  private Map _consentPolicyIds;
  private String _form; 

  private boolean _printCaseLabel;

  //label printing information (available templates, printers, etc)
  private Map _labelPrintingData;
  
  //number of times each label should be printed
  private String _labelCount;
  
  //selected template definition to use for printing the labels
  private String _templateDefinitionName;
  
  //selected printer to use for printing the labels
  private String _printerName;
  
  private List _attachments;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _customerId = null; //customer id for the consent
    _consentId = null;   //ardais id for the consent
    _consentId2 = null;   // confirm ardais id for the consent
    _ardaisId = null;
    _ardaisCustomerId = null;
    _linkedIndicator = null;
    _consentVersionId = null;
    _year = null;
    _month = null;
    _hours = null;
    _minutes = null;
    _ampm = null;
    _consentDate = null;
    _policyId = null;
    _diagnosis = null;
    _diagnosisOther = null;
    _comments = null;
    _ardaisAcctKey = null;
    _importedYN = null;
    _consentChoices = null;
    _policyChoices = null;
    _pullReasonChoices = null;
    _pullReason = null;
    _pullRequestedByChoices = null;
    _pullRequestedBy = null;
    _pullNote = null;
    _txType = null;
    _formDefinitions = null;
    _formInstances = null;
    _createForm = false;
    _readOnly = false;
    _registrationForm = null;
    _caseRegistrationFormIds = null;
    _consentPolicyIds = null;
    _form = null;
    _printCaseLabel = false;
    _labelPrintingData = null;
    _labelCount = "1";
    _templateDefinitionName = null;
    _printerName = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Here we just make sure we can turn the year/month/hours/minutes/ampm into a timestamp,
    // and make sure the double-entered donor ids (or customer ids) match.
    // All other validation is done in the BTX performer.
    ActionErrors errors = new ActionErrors();

    String myTag = ApiFunctions.safeString(mapping.getTag());
    //if the tag on the action = "CreateCaseSuccess", then we've successfully
    //created a case and now want to return to an empty Create Case page (so
    //the user can create another case without having to erase the data from
    //the case we just created).  So, reset the form.  We can also skip 
    //validation since there is nothing to validate in this case 
    if ("CreateCaseSuccess".equalsIgnoreCase(myTag)) {
      doReset(mapping,request);
      return errors;
    }
    return errors;
  }

  public void describeIntoBtxDetails(BTXDetails btxDetails0, BigrActionMapping mapping,
                                     HttpServletRequest request) {
    
    super.describeIntoBtxDetails(btxDetails0, mapping, request);
    
    //populate the registration form if this is a create imported case situation
    if (btxDetails0 instanceof BtxDetailsCreateImportedCase) {
      
      // Create a new form instance.
      String jsonForm = getForm();
      FormInstance form = ApiFunctions.isEmpty(jsonForm) ? 
          new FormInstance() : WebUtils.convertToFormInstance(jsonForm);
  
      // Populate the BTX details with the form instance.
      BtxDetailsCreateImportedCase myDetails = (BtxDetailsCreateImportedCase)btxDetails0;
      myDetails.setRegistrationFormInstance(form);
    }
  }

  /* 
   * Store necessary session-based information
   * @see com.ardais.bigr.web.form.PopulatesRequestFromBtxDetails#populateRequestFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void populateRequestFromBtxDetails(BTXDetails btxDetails, 
                                            BigrActionMapping mapping,
                                            HttpServletRequest request) {
    // Always set up the KC form context, whether an error occurred or not, so that the
    // place we forward to can at least find the registration form.  If the transaction did
    // not get the registration form, then this setup will not do any harm.
    BtxDetailsCreateImportedCase details = (BtxDetailsCreateImportedCase)btxDetails;
    setupKcFormContext(request, details);

    //if the transaction was successful, store the donor and case information in the session
    if (btxDetails.isTransactionCompleted() && !btxDetails.isHasException()) {
      //note - this is applicable only to creating a new imported case.  Modifying an imported case
      //is handled below. 
      if (btxDetails instanceof BtxDetailsCreateImportedCase &&
          !(btxDetails instanceof BtxDetailsModifyImportedCase)) {
        BtxDetailsCreateImportedCase myDetails = (BtxDetailsCreateImportedCase)btxDetails;
        String donorId = myDetails.getArdaisId();
        String donorAlias = myDetails.getArdaisCustomerId();
        String caseId = myDetails.getConsentId();
        String caseAlias = myDetails.getCustomerId();
        //because we're creating a new case, pass false as the last parameter to ensure that any
        //saved sample id is cleared out.  Any such sample id will be cleared out by virtue of the
        //fact we are passing in a case id that is guaranteed to be different from any that's 
        //currently saved in the session, but passing "false" provides additional protection.
        PdcUtils.storeLastUsedDonorCaseAndSample(request, donorId, donorAlias, caseId, caseAlias, 
            null, null, false);
      }
      //note - this handles both modify imported case start and modify imported case
      else if (btxDetails instanceof BtxDetailsModifyImportedCase) {
        BtxDetailsModifyImportedCase myDetails = (BtxDetailsModifyImportedCase)btxDetails;
        String donorId = myDetails.getArdaisId();
        String donorAlias = myDetails.getArdaisCustomerId();
        String caseId = myDetails.getConsentId();
        String caseAlias = myDetails.getCustomerId();
        //we pass "true" for the last parameter because if the user is modifying the same
        //case as the one currently saved in the session we don't want to erase any saved 
        //sample id.  If the user is modifying a different case then any sample information 
        //will be erased regardless of the value of the last parameter.
        PdcUtils.storeLastUsedDonorCaseAndSample(request, donorId, donorAlias, caseId, caseAlias,
            null, null, true);
      }
    }    
  }

  /**
   * @return
   */
  public String getAmpm() {
    return _ampm;
  }

  /**
   * @return
   */
  public String getArdaisAcctKey() {
    return _ardaisAcctKey;
  }

  /**
   * @return
   */
  public String getArdaisCustomerId() {
    return _ardaisCustomerId;
  }

  /**
   * @return
   */
  public String getArdaisId() {
    return _ardaisId;
  }

  /**
   * @return
   */
  public String getComments() {
    return _comments;
  }

  /**
   * @return
   */
  public Date getConsentDate() {
    return _consentDate;
  }

  /**
   * @return
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * @return
   */
  public String getConsentVersionId() {
    return _consentVersionId;
  }

  /**
   * @return
   */
  public String getCustomerId() {
    return _customerId;
  }

  /**
   * @return
   */
  public String getDiagnosis() {
    return _diagnosis;
  }

  /**
   * @return
   */
  public String getHours() {
    return _hours;
  }

  /**
   * @return
   */
  public String getImportedYN() {
    return _importedYN;
  }

  /**
   * @return
   */
  public String getLinkedIndicator() {
    return _linkedIndicator;
  }

  /**
   * @return
   */
  public String getMinutes() {
    return _minutes;
  }

  /**
   * @return
   */
  public String getMonth() {
    return _month;
  }

  /**
   * @return
   */
  public String getPolicyId() {
    return _policyId;
  }

  /**
   * @return
   */
  public String getYear() {
    return _year;
  }

 
  /**
   * Returns the list of attachments (<code>AttachmentData</code>s).  If there are no attachments,
   * an empty list is returned.
   *
   * @return  The list of <code>AttachmentData</code>s.
   */
  public List getAttachments() {
    return _attachments;
  }
  
  /**
   * 
   * @param attachments
   */
  public void setAttachments (List attachments) {
     _attachments = attachments;
    
  }
  
  /**
   * @param string
   */
  public void setAmpm(String string) {
    _ampm = string;
  }

  /**
   * @param string
   */
  public void setArdaisAcctKey(String string) {
    _ardaisAcctKey = string;
  }

  /**
   * @param string
   */
  public void setArdaisCustomerId(String string) {
    _ardaisCustomerId = string;
  }

  /**
   * @param string
   */
  public void setArdaisId(String string) {
    _ardaisId = string;
  }

  /**
   * @param string
   */
  public void setComments(String string) {
    _comments = string;
  }

  /**
   * @param date
   */
  public void setConsentDate(Date date) {
    _consentDate = date;
    //if we're setting this value and the individual fields are not populated (year, month, etc)
    //set them.  This will be the case, for example, when modifying a case.  We retrieve the
    //consent date and set it, but now need to populate the individual fields for the jsp
    if (date != null && ApiFunctions.isEmpty(getYear())) {
      Calendar myCalendar = Calendar.getInstance();
      myCalendar.setLenient(false);
      myCalendar.setTime(getConsentDate());
      int myYear = myCalendar.get(Calendar.YEAR);
      int myMonth = myCalendar.get(Calendar.MONTH) + 1;
      int myHours = myCalendar.get(Calendar.HOUR_OF_DAY);
      int myMinutes = myCalendar.get(Calendar.MINUTE);
      String amPm;
      //convert hours from 24 hour format and set am/pm
      if (myHours > 12) {
        myHours = myHours - 12;
        amPm = "pm";
      }
      else if (myHours == 12) {
        amPm = "pm";
      }
      else {
        amPm = "am";
        if (myHours == 0) {
          myHours = 12;
        }
      }
      setYear(myYear + "");
      setMonth(myMonth + "");
      setHours(myHours + "");
      setMinutes(myMinutes + "");
      setAmpm(amPm);
    }
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = string;
  }

  /**
   * @param string
   */
  public void setConsentVersionId(String string) {
    _consentVersionId = string;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = string;
  }

  /**
   * @param string
   */
  public void setDiagnosis(String string) {
    _diagnosis = string;
  }

  /**
   * @param string
   */
  public void setHours(String string) {
    _hours = string;
  }

  /**
   * @param string
   */
  public void setImportedYN(String string) {
    _importedYN = string;
  }

  /**
   * @param string
   */
  public void setLinkedIndicator(String linkedIndicator) {
    _linkedIndicator = linkedIndicator;
  }

  /**
   * @param string
   */
  public void setMinutes(String string) {
    _minutes = string;
  }

  /**
   * @param string
   */
  public void setMonth(String string) {
    _month = string;
  }

  /**
   * @param string
   */
  public void setPolicyId(String string) {
    _policyId = string;
  }

  /**
   * @param string
   */
  public void setYear(String string) {
    _year = string;
  }

  /**
   * @return
   */
  public List getConsentChoices() {
    return _consentChoices;
  }

  /**
   * @return
   */
  public List getPolicyChoices() {
    return _policyChoices;
  }

  /**
   * @param list
   */
  public void setConsentChoices(List list) {
    _consentChoices = list;
  }

  /**
   * @param list
   */
  public void setPolicyChoices(List list) {
    _policyChoices = list;
  }

  /**
   * @return
   */
  public String getDiagnosisOther() {
    return _diagnosisOther;
  }

  /**
   * @param string
   */
  public void setDiagnosisOther(String string) {
    _diagnosisOther = string;
  }

  /**
   * @return
   */
  public String getIrbProtocolAndVersionName() {
    return _irbProtocolAndVersionName;
  }

  /**
   * @return
   */
  public String getPolicyName() {
    return _policyName;
  }

  /**
   * @param string
   */
  public void setIrbProtocolAndVersionName(String string) {
    _irbProtocolAndVersionName = string;
  }

  /**
   * @param string
   */
  public void setPolicyName(String string) {
    _policyName = string;
  }

  /**
   * @return
   */
  public String getPullReason() {
    return _pullReason;
  }

  /**
   * @return
   */
  public LegalValueSet getPullReasonChoices() {
    return _pullReasonChoices;
  }

  /**
   * @param string
   */
  public void setPullReason(String string) {
    _pullReason = string;
  }

  /**
   * @param set
   */
  public void setPullReasonChoices(LegalValueSet set) {
    _pullReasonChoices = set;
  }

  /**
   * @return
   */
  public String getPullNote() {
    return _pullNote;
  }

  /**
   * @return
   */
  public String getPullRequestedBy() {
    return _pullRequestedBy;
  }

  /**
   * @param string
   */
  public void setPullNote(String string) {
    _pullNote = string;
  }

  /**
   * @param string
   */
  public void setPullRequestedBy(String string) {
    _pullRequestedBy = string;
  }

  /**
   * @return
   */
  public LegalValueSet getPullRequestedByChoices() {
    return _pullRequestedByChoices;
  }

  /**
   * @param list
   */
  public void setPullRequestedByChoices(LegalValueSet lvs) {
    _pullRequestedByChoices = lvs;
  }

  /**
   * @return
   */
  public String getConsentId2() {
    return _consentId2;
  }

  /**
   * @param string
   */
  public void setConsentId2(String string) {
    _consentId2 = string;
  }

  /**
   * @return
   */
  public String getTxType() {
    return _txType;
  }

  /**
   * @param string
   */
  public void setTxType(String string) {
    _txType = string;
  }
  
  /**
   * @return Returns the formDefinitions.
   */
  public BigrFormDefinition[] getFormDefinitions() {
    return _formDefinitions;
  }
  
  /**
   * @return Returns the formInstances.
   */
  public BigrFormInstance[] getFormInstances() {
    return _formInstances;
  }
  
  /**
   * @param formDefinitions The formDefinitions to set.
   */
  public void setFormDefinitions(BigrFormDefinition[] formDefinitions) {
    _formDefinitions = formDefinitions;
  }
  
  /**
   * @param formInstances The formInstances to set.
   */
  public void setFormInstances(BigrFormInstance[] formInstances) {
    _formInstances = formInstances;
  }

  public String getFormDefinitionId() {
    return "";
  }
  
  public LegalValueSet getFormDefinitionsAsLegalValueSet() {
    LegalValueSet lvs = new LegalValueSet();
    BigrFormDefinition[] formDefs = getFormDefinitions();
    if (formDefs != null) {
      for (int i = 0; i < formDefs.length; i++) {
        BigrFormDefinition formDef = formDefs[i];
        lvs.addLegalValue(formDef.getFormDefinitionId(), formDef.getName());
      }
    }
    return lvs;
  }

  public boolean isCreateForm() {
    return _createForm;
  }

  public void setCreateForm(boolean createForm) {
    _createForm = createForm;
  }

  public boolean isReadOnly() {
    return _readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    _readOnly = readOnly;
  }
  public DataFormDefinition getRegistrationForm() {
    return _registrationForm;
  }
  public void setRegistrationForm(DataFormDefinition form) {
    _registrationForm = form;
  }

  public Map getCaseRegistrationFormIds() {
    return _caseRegistrationFormIds;
  }

  public void setCaseRegistrationFormIds(Map ids) {
    _caseRegistrationFormIds = ids;
  }

  public Map getConsentPolicyIds() {
    return _consentPolicyIds;
  }

  public void setConsentPolicyIds(Map ids) {
    _consentPolicyIds = ids;
  }

  private void setupKcFormContext(HttpServletRequest request, BtxDetailsCreateImportedCase btxDetails) {
    FormContextStack stack = FormContextStack.getFormContextStack(request);
    FormContext formContext = stack.peek();
    DataFormDefinition formDef = btxDetails.getRegistrationForm();
    if (formDef != null) {
      formContext.setDataFormDefinition(formDef);
    }
    FormInstance form = btxDetails.getRegistrationFormInstance();
    if (form != null) {
      formContext.setFormInstance(form);          
    }
    formContext.setDomainObjectId(btxDetails.getConsentId());
    stack.push(formContext);

    KcUiContext kcUiContext = KcUiContext.getKcUiContext(request);
    String contextPath = request.getContextPath();
    kcUiContext.setAdePopupUrl(contextPath + "/kc/ade/popup.do");
  }
  
  public void setForm(String form) {
    _form = form;
  }
  
  public String getForm() {
    return _form;
  }
  
  /**
   * @return Returns the printCaseLabel.
   */
  public boolean isPrintCaseLabel() {
    return _printCaseLabel;
  }
  
  /**
   * @return Returns the labelPrintingData.
   */
  public Map getLabelPrintingData() {
    return _labelPrintingData;
  }
  
  /**
   * @return Returns the labelCount.
   */
  public String getLabelCount() {
    return _labelCount;
  }
  
  /**
   * @return Returns the printerName.
   */
  public String getPrinterName() {
    return _printerName;
  }
  
  /**
   * @return Returns the templateDefinitionName.
   */
  public String getTemplateDefinitionName() {
    return _templateDefinitionName;
  }
  
  /**
   * @param printCaseLabel The printCaseLabel to set.
   */
  public void setPrintCaseLabel(boolean printCaseLabel) {
    _printCaseLabel = printCaseLabel;
  }
  
  /**
   * @param labelPrintingData The labelPrintingData to set.
   */
  public void setLabelPrintingData(Map labelPrintingData) {
    _labelPrintingData = labelPrintingData;
  }
  
  /**
   * @param labelCount The labelCount to set.
   */
  public void setLabelCount(String labelCount) {
    _labelCount = labelCount;
  }
  
  /**
   * @param printerName The printerName to set.
   */
  public void setPrinterName(String printerName) {
    _printerName = printerName;
  }
  
  /**
   * @param templateDefinitionName The templateDefinitionName to set.
   */
  public void setTemplateDefinitionName(String templateDefinitionName) {
    _templateDefinitionName = templateDefinitionName;
  }
}
