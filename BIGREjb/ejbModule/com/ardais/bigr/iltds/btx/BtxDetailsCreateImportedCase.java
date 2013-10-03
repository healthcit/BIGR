package com.ardais.bigr.iltds.btx;

import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.kc.form.FormInstanceHistoryObject;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * Represent the details of creating a case.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BtxDetailsCreateCase BtxDetailsCreateCase} for additional fields this class inherits.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setArdaisAcctKey(String) Account key}: The account of the user importing the new case.</li>
 * <li>{@link #setImportedYN(String) ImportedYN}: An indicator as to whether the case is imported (should be Y).</li>
 * <li>{@link #setCustomerId(String) Customer id}: The customer's id for the new case.</li>
 * <li>{@link #setLinkedIndicator(String) LinkedIndicator}: "Y" if case is linked, "N" if not.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setDiagnosis(String) diagnosis}: The case diagnosis.</li>
 * <li>{@link #setDiagnosisOther(String) diagnosisOther}: The text for the diagnosis when the 
 * case diagnosis is Other.</li>
 * <li>{@link #setCreateForm(boolean) createForm}: An indicator as to whether the user wishes
 * to navigate to the form creation page when the case is created or not.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setArdaisCustomerId(String) Ardais customer id}: The customer id for the donor to
 * whom the case belongs.</li>
 * </ul>
 */
public class BtxDetailsCreateImportedCase extends BtxDetailsCreateCase implements java.io.Serializable {
  private static final long serialVersionUID = 84861234315858620L;

  protected BtxHistoryAttributes _historyObject;
  private String _ardaisAcctKey = null;
  private String _importedYN = null;
  private String _customerId = null;
  private String _diagnosis = null;
  private String _diagnosisOther = null;
  private String _linkedIndicator = null;
  private String _ardaisId2 = null;
  private String _ardaisCustomerId = null;
  private String _ardaisCustomerId2 = null;
  private boolean _createForm = false;
  private DataFormDefinition _registrationForm;
  private Map _caseRegistrationFormIds;
  private Map _consentPolicyIds;
  private FormInstance _registrationFormInstance;
  private List _consentChoices = null;
  private List _policyChoices = null;
  private BigrFormDefinition[] _formDefinitions;
  
  //field to indicate that a case label should be printed
  private boolean _printCaseLabel = false;
  
  //label printing information (available templates, printers, etc)
  private Map _labelPrintingData;
  
  //number of times each label should be printed
  private String _labelCount;
  
  //selected template definition to use for printing the labels
  private String _templateDefinitionName;
  
  //selected printer to use for printing the labels
  private String _printerName;
  

  public BtxDetailsCreateImportedCase() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_IMPORTED_CASE;
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set to
   *    the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    //note - because we're extending the BtxDetailsCreateCase class, we only need to
    //store the additional attributes this class carries.  See 
    //BtxDetailsCreateCase.describeIntoHistoryRecord for details)

    //because this class uses a string to indicate linked/unlinked, use that string
    //to set attrib3 (super class uses attrib3 to capture linked/unlinked info)
    history.setAttrib3(getLinkedIndicator());
    history.setAttrib10(getArdaisAcctKey());
    history.setAttrib11(getImportedYN());
    history.setAttrib12(getCustomerId());
    history.setAttrib13(BigrGbossData.getDiagnosisDescription(getDiagnosis()));
    history.setAttrib14(getDiagnosisOther());
    history.setAttrib15(getArdaisCustomerId());

    FormInstance form = getRegistrationFormInstance();
    FormDefinition formDef = getRegistrationForm();
    if ((form != null) && (form.getDataElements().length > 0) && (formDef != null)) {
      FormInstanceHistoryObject historyObject = new FormInstanceHistoryObject();
      historyObject.setFormInstance(form);
      historyObject.setFormDefinition(formDef);
      history.setHistoryObject(historyObject.describeAsHistoryObject());      
    }
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    //note - because we're extending the BtxDetailsCreateCase class, we only need to
    //retrieve the additional attributes this class carries.  See 
    //BtxDetailsCreateCase.populateFromHistoryRecord for details)

    //because this class uses a string to indicate linked/unlinked, use attrib3 to set 
    //that string (super class uses attrib3 to capture linked/unlinked info)
    setLinkedIndicator(history.getAttrib3());
    setArdaisAcctKey(history.getAttrib10());
    setImportedYN(history.getAttrib11());
    setCustomerId(history.getAttrib12());
    setDiagnosis(history.getAttrib13());
    setDiagnosisOther(history.getAttrib14());
    setArdaisCustomerId(history.getAttrib15());

    _historyObject = (BtxHistoryAttributes)history.getHistoryObject();
  }

  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getConsentId
    //   getArdaisId
    //   getLinkedIndicator
    //   getPolicyId
    //   getPolicyName
    //   getConsentVersionId
    //   getIrbProtocolAndVersionName
    //   getConsentDateString
    //   getComments
    //   getArdaisAcctKey
    //   getImportedYN
    //   getCustomerId
    //   getDiagnosis
    //   getDiagnosisOther
    //   getArdaisCustomerId

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(512);

    // The result has this form:
    //    Created <linked/unlinked> case <consentId> (<customerId>) for donor 
    //    <ardaisId> (<ardaisCustomerId>)
    //    with policy '<policyName>' [and diagnosis <diagnosis> (<diagnosisOther>)].
    //    [Consent was obtained on <consentDateString> under IRB version
    //    <irbProtocolAndVersionName>.]
    //    [Comments: <comments>]

    boolean linked = "Y".equalsIgnoreCase(getLinkedIndicator());

    sb.append("Created ");
    sb.append(linked ? "linked" : "unlinked");
    sb.append(" case ");
    sb.append(IcpUtils.prepareLink(getConsentId(), securityInfo));
    sb.append(" (");
    Escaper.htmlEscape(getCustomerId(), sb);
    sb.append(") for donor ");
    sb.append(IcpUtils.prepareLink(getArdaisId(), securityInfo));
    if (!ApiFunctions.isEmpty(getArdaisCustomerId())) {
      sb.append(" (");
      Escaper.htmlEscape(getArdaisCustomerId(), sb);
      sb.append(")");
    }
    sb.append(" with policy '");

    String policyId = getPolicyId();
    if (ApiFunctions.isEmpty(policyId)) {
      Escaper.htmlEscape(getPolicyName(), sb);
    }
    else {
      String prefixedPolicyId = FormLogic.makePrefixedPolicyId(policyId);
      sb.append(IcpUtils.prepareLink(prefixedPolicyId, getPolicyName(), securityInfo));
    }
    sb.append("'");
    if (!ApiFunctions.isEmpty(getDiagnosis())) {
      sb.append(" and diagnosis '");
      Escaper.htmlEscape(getDiagnosis(),sb);
      if (!ApiFunctions.isEmpty(getDiagnosisOther())) {
        sb.append(" (");
        Escaper.htmlEscape(getDiagnosisOther(),sb);
        sb.append(")");
      }
    }
    sb.append("'.");

    if (linked) {
      sb.append("  Consent was obtained");
      if (!ApiFunctions.isEmpty(getConsentDateString())) {
        sb.append(" on ");
        Escaper.htmlEscape(getConsentDateString(), sb);
      }
      sb.append(" under IRB version ");
      Escaper.htmlEscape(getIrbProtocolAndVersionName(), sb);
      sb.append('.');
    }
    
    if (_historyObject != null) {
      sb.append(" The following attributes were captured: ");
      FormInstanceHistoryObject historyObject = new FormInstanceHistoryObject();
      sb.append("<ul>");
      historyObject.doGetDetailsAsHTMLDataElementsOnly(_historyObject, sb);      
      sb.append("</ul>");
    }

    String comments = getComments();
    if (!ApiFunctions.isEmpty(comments)) {
      sb.append("<br>Comments:<br>");
      Escaper.htmlEscapeAndPreserveWhitespace(comments, sb);
    }

    return sb.toString();
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
  public String getDiagnosisOther() {
    return _diagnosisOther;
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
   * @param string
   */
  public void setArdaisAcctKey(String string) {
    _ardaisAcctKey = string;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = ApiFunctions.safeTrim(string);
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
  public void setDiagnosisOther(String string) {
    _diagnosisOther = string;
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
  public void setLinkedIndicator(String string) {
    _linkedIndicator = string;
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
  public String getArdaisCustomerId2() {
    return _ardaisCustomerId2;
  }

  /**
   * @return
   */
  public String getArdaisId2() {
    return _ardaisId2;
  }

  /**
   * @param string
   */
  public void setArdaisCustomerId(String string) {
    _ardaisCustomerId = ApiFunctions.safeTrim(string);
  }

  /**
   * @param string
   */
  public void setArdaisCustomerId2(String string) {
    _ardaisCustomerId2 = ApiFunctions.safeTrim(string);
  }

  /**
   * @param string
   */
  public void setArdaisId2(String string) {
    _ardaisId2 = string;
  }

  public boolean isCreateForm() {
    return _createForm;
  }

  public void setCreateForm(boolean createForm) {
    _createForm = createForm;
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

  public FormInstance getRegistrationFormInstance() {
    return _registrationFormInstance;
  }
  public void setRegistrationFormInstance(FormInstance form) {
    _registrationFormInstance = form;
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
   * @return Returns the formDefinitions.
   */
  public BigrFormDefinition[] getFormDefinitions() {
    return _formDefinitions;
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
   * @param formDefinitions The formDefinitions to set.
   */
  public void setFormDefinitions(BigrFormDefinition[] formDefinitions) {
    _formDefinitions = formDefinitions;
  }
  
  
  public Map getLabelPrintingData() {
    return _labelPrintingData;
  }
  
  public void setLabelPrintingData(Map labelPrintingData) {
    _labelPrintingData = labelPrintingData;
  }
  
  /**
   * @return Returns the printCaseLabel.
   */
  public boolean isPrintCaseLabel() {
    return _printCaseLabel;
  }
  
  /**
   * @param printCaseLabel The printCaseLabel to set.
   */
  public void setPrintCaseLabel(boolean printCaseLabel) {
    _printCaseLabel = printCaseLabel;
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
