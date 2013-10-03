package com.ardais.bigr.orm.web.form;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConcept;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.iltds.databeans.SampleTypeConfiguration;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainPolicy;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainPolicyStart;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for maintaining polices.
 */
public class MaintainPolicyForm extends BigrActionForm {
  private boolean _resetForm;
  private boolean _retry;

  private String _operation; // "Create", "Update", or "Delete"
  private String _associatedIrbs;
  private List _policies;
  private PolicyData _policy;
  
  // MR 8049
  private String _verifyRequiredOldValue;
  private String _releaseRequiredOldValue;

  private Map _caseRegistrationFormChoiceMap = null;
  private Map _sampleRegistrationFormChoiceMap = null;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _resetForm = false;
    _retry = false;
    _operation = Constants.OPERATION_CREATE;
    _associatedIrbs = null;
    _policies = null;
    _policy = null;
    _verifyRequiredOldValue = null;
    _releaseRequiredOldValue = null;
    _caseRegistrationFormChoiceMap = null;
    _sampleRegistrationFormChoiceMap = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Note: only primitive validations occur here. Any business rule or
    // business validation should occur in the BTX performer class.

    ActionErrors errors = new ActionErrors();
    ActionError error = null;

    boolean doNotValidate = false;

    // If retry is true, it means we're coming back to this form to display validation errors.
    // In most cases we don't have to do anything special, but the "Delete" operation is a
    // special case since the form fields aren't used to gather input for Delete operations.
    // So, when the operation is "Delete" and we're retrying, we reset the form.  The user
    // will still see the validation errors, but the form will otherwise look like a fresh
    // "Create" form.
    //
    // If we're retrying we don't do any further validation, since we can assume in this
    // case that there are problems and we're just trying to report them.
    //
    if (isRetry()) {
      if (Constants.OPERATION_DELETE.equals(getOperation())) {
        setResetForm(true);
      }
    }

    // The isResetForm() "field" is an instruction that the form fields are to be reset to their
    // default values before proceeding.
    //
    // When we reset the form, we don't do any further validation.
    //
    if (isResetForm()) {
      doNotValidate = true;
      doReset(mapping, request);
    }

    if (doNotValidate) {
      return null;
    }
    else {
      // No further validations here.  All validations are done in the BTX business logic bean.
      return errors;
    }
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    super.describeIntoBtxDetails(btxDetails0, mapping, request);
    
    // If we're retrying and the btxDetails object is the "Start" version, set an extra
    // flag on the btxDetails object telling it not to populate some of its usual output
    // fields.  This is useful when the user attempted an update but got a validation
    // error, and we want to keep their data fields as-is for them to correct instead of
    // resetting them to the values in the database, which is what would happen by default.
    if (isRetry() && btxDetails0 instanceof BtxDetailsMaintainPolicyStart) {
      BtxDetailsMaintainPolicyStart btxDetails =
        (BtxDetailsMaintainPolicyStart) btxDetails0;
      btxDetails.setPopulatePolicyOutputFields(false);
    }
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);
    //make sure the sample type configuration object for the policy has an entry for every known
    //sample type (except for "Unknown").  This is to ensure that as new sample types are added
    //to the system, policies will display that type when they are rendered.  If we just displayed
    //the sample types contained in the policy this wouldn't happen.
    SampleTypeConfiguration stc = ((BtxDetailsMaintainPolicy)btxDetails).getPolicy().getSampleTypeConfiguration();
    BigrConceptList allSampleTypes = 
      SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES);
    Iterator typeIterator = allSampleTypes.iterator();
    String sampleTypeCode = null;
    while (typeIterator.hasNext()) {
      BigrConcept sampleTypeConcept = (BigrConcept) typeIterator.next();
      sampleTypeCode = sampleTypeConcept.getCode();
      //ignore "Unknown" sample type
      if (!ArtsConstants.SAMPLE_TYPE_UNKNOWN.equalsIgnoreCase(sampleTypeCode)) {
        SampleType st = stc.getSampleType(sampleTypeCode);
        if (st == null || ApiFunctions.isEmpty(st.getCui())) {
          st = new SampleType();
          st.setCui(sampleTypeCode);
          st.setSupported(false);
          st.setRegistrationFormId(null);
          stc.addSampleType(st);
        }
      }
    }
  }

  /**
   * Return the legal value set for the allocation formats.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getAllocationFormatChoices() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_ALLOCATION_FORMAT);
  }

  /**
   * Return the legal value set for all the logicl repositories.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getLogicalRepositoryChoices() {
    List logicalRepositories = LogicalRepositoryUtils.getAllLogicalRepositories();
    Iterator iterator = logicalRepositories.iterator();
    LegalValueSet lvs = new LegalValueSet();
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository)iterator.next();
      lvs.addLegalValue(lr.getId(), lr.getFullName());
    }
    return lvs;
  }

  /**
   * Return the legal value set for the ship to Ardais schemes.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getAdditionalQuestionsGroupChoices() {
    LegalValueSet lvs = new LegalValueSet();
    lvs.addLegalValue(FormLogic.ADDITIONAL_QUESTIONS_GRP_DBS, FormLogic.ADDITIONAL_QUESTIONS_GRP_DBS);
    return lvs;
  }
  
  /**
   * Return the legal value set of ardais accounts.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getAccountChoices() {
    return IltdsUtils.getAccountList();
  }
  
  public String getAccountName() {
    String accountName = IltdsUtils.getAccountName(getPolicy().getAccountId()) + " (" + getPolicy().getAccountId() + ")";
    return accountName;
  }

  /**
   * @return
   */
  public String getAssociatedIrbs() {
    return _associatedIrbs;
  }

  /**
   * @return
   */
  public String getOperation() {
    return _operation;
  }

  /**
   * @return
   */
  public List getPolicies() {
    return _policies;
  }

  /**
   * @return
   */
  public String getReleaseRequiredOldValue() {
    return _releaseRequiredOldValue;
  }
  
  /**
   * @return
   */
  public boolean isResetForm() {
    return _resetForm;
  }

  /**
   * @return
   */
  public boolean isRetry() {
    return _retry;
  }

  /**
   * @return
   */
  public String getVerifyRequiredOldValue() {
    return _verifyRequiredOldValue;
  }

  /**
   * @param string
   */
  public void setAssociatedIrbs(String string) {
    _associatedIrbs = string;
  }

  /**
   * @param string
   */
  public void setOperation(String string) {
    _operation = string;
  }

  /**
   * @param list
   */
  public void setPolicies(List list) {
    _policies = list;
  }

  /**
   * @param string
   */
  public void setReleaseRequiredOldValue(String string) {
    _releaseRequiredOldValue = string;
  }

  /**
   * @param b
   */
  public void setResetForm(boolean b) {
    _resetForm = b;
  }

  /**
   * @param b
   */
  public void setRetry(boolean b) {
    _retry = b;
  }

  /**
   * @param string
   */
  public void setVerifyRequiredOldValue(String string) {
    _verifyRequiredOldValue = string;
  }
  
  public PolicyData getPolicy() {
    if (_policy == null) {
      _policy = new PolicyData();
      //initialize the policy data sample type configuration to have a sample type object for
      //each known sample type in the system.  This is done so when the form is populated from
      //the request the sample type configuration object on the policy will not return null for
      //any sample type.
      SampleTypeConfiguration stc = _policy.getSampleTypeConfiguration();
      BigrConceptList allSampleTypes = 
        SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES);
      Iterator typeIterator = allSampleTypes.iterator();
      String sampleTypeCode = null;
      boolean isSupported = false;
      while (typeIterator.hasNext()) {
        BigrConcept sampleTypeConcept = (BigrConcept) typeIterator.next();
        sampleTypeCode = sampleTypeConcept.getCode();
        //ignore "Unknown" sample type
        if (!ArtsConstants.SAMPLE_TYPE_UNKNOWN.equalsIgnoreCase(sampleTypeCode)) {
          SampleType st = new SampleType();
          st.setCui(sampleTypeCode);
          st.setSupported(false);
          st.setRegistrationFormId(null);
          stc.addSampleType(st);
        }
      }
    }
    return _policy;
  }
  
  public void setPolicy(PolicyData policy) {
    _policy = policy;
  }
  
  public Map getCaseRegistrationFormChoiceMap() {
    return _caseRegistrationFormChoiceMap;
  }
  
  public Map getSampleRegistrationFormChoiceMap() {
    return _sampleRegistrationFormChoiceMap;
  }
  
  public void setCaseRegistrationFormChoiceMap(Map caseRegistrationFormChoiceMap) {
    _caseRegistrationFormChoiceMap = caseRegistrationFormChoiceMap;
  }
  
  public void setSampleRegistrationFormChoiceMap(Map sampleRegistrationFormChoiceMap) {
    _sampleRegistrationFormChoiceMap = sampleRegistrationFormChoiceMap;
  }
}
