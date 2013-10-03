package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Represent the details of maintaining a policy (create/edit/delete).
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setOperation(String) Operation}: The maintenance operation that is being
 *     performed.  This must be one of "Create", "Update", or "Delete" (case-sensitive).</li>
 * <li>{@link #Policy.setPolicyId(String) Policy id}: The policy id that we're
 *     operating on.  This is required for "Update" and "Delete" operations and must be
 *     null on input for "Create" operations (it is an output parameter for "Create").</li>
 * <li>{@link #Policy.setPolicyName(String) Policy name}: The name of the policy. This is required for
 *     "Create" and "Update" operations and is ignored on input for the "Delete" operation (it is
 *     an output parameter for "Delete"). Any whitespace at the beginning or end of the name
 *     will be trimmed.</li>
 * <li>{@link #Policy.setAllocationNumerator(int) The allocation numerator}: The allocation numerator
 *     of the policy. This a required field for "Update" and "Create" operations and is ignored
 *     on input for the "Delete" operation. The value must be greater than or equal to zero and
 *     cannot be greater than the numerator. In can only be zero if the denominator is zero as
 *     well.</li>
 * <li>{@link #Policy.setAllocationDenominator(int) The allocation denominator}: The allocation
 *     denominator of the policy. This a required field for "Update" and "Create" operations and
 *     is ignored on input for the "Delete" operation. The value must be greater than or equal to
 *     zero. In can only be zero if the numerator is zero as well.</li>
 * <li>{@link #Policy.setAllocationFormatCid(String) Allocation format}: The allocation format of the
 *     policy. This is required for "Create" and "Update" operations and is ignored on input for
 *     the "Delete" operation. The value must be a valid value in the "ALLOCATION_FORMAT" concept
 *     graph.</li>
 * <li>{@link #Policy.setDefaultLogicalReposId(String) The default logical repository}: The default
 *     logical repository of the policy. This is required for "Create" and "Update" operations and
 *     is ignored on input for the "Delete" operation.</li>
 * <li>{@link #Policy.setCaseRegistrationFormId(String) The case registration form id}: The id of the
 *     registration form to be used for cases. This is required for "Create" and "Update" operations 
 *     and is ignored on input for the "Delete" operation.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #Policy.setRestrictedLogicalReposId(String) The restricted logical repository}: The
 *     restricted logical repository of the policy. This is an optional input for "Create" and
 *     "Update" operations and is ignored on input for the "Delete" operation. This must be empty
 *     if the allocation format is NONE.</li>
 * <li>{@link #Policy.setPolicyDataEncoding(String) The encoding scheme}: The encoding scheme for the
 *     XML of the policy.  This is an optional input (it will default to the current encoding 
 *     scheme specified in {@link Constants.CURRENT_POLICY_ENCODING}). </li>
 * </ul>
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #Policy.setPolicyId(String) Policy id}: This is sometimes an output parameter, as
 *     described above.</li>
 * <li>{@link #Policy.setPolicyName(String) Policy name}: This is sometimes an output parameter, as
 *     described above.</li>
 * <li>{@link #Policy.setAllocationNumerator(String) The allocation numerator}: This is sometimes an
 *     output parameter, as described above.</li>
 * <li>{@link #Policy.setAllocationDenominator(String) The allocation denominator}: This is sometimes an
 *     output parameter, as described above.</li>
 * <li>{@link #Policy.setAllocationFormatCid(String) The allocation format}: This is sometimes an output
 *     parameter, as described above.</li>
 * <li>{@link #Policy.setDefaultLogicalReposId(String) The default logical repository id}: This is
 *     sometimes an output parameter, as described above.</li>
 * <li>{@link #Policy.setRestrictedLogicalReposId(String) The restricted logical repository id}: This is
 *     sometimes an output parameter, as described above.</li>
 * <li>{@link #Policy.setCaseRegistrationFormName(String) Case registration form name}: The name of
 *     the case registration form.</li>
 * <li>{@link #Policy.getSampleTypeConfiguration().getSampleType(cui).setRegistrationFormName(String) 
 *     Registration form name}: The name of the sample registration form.</li>
 * <li>{@link #setCaseRegistrationFormChoiceMap(Map) }: A Map of LVS containing the KC form 
 *     choices for the case registration form of a policy. The map is keyed by account id</li>
 * <li>{@link #setSampleRegistrationFormChoiceMap(Map) }: A Map of LVS containing the KC form 
 *     choices for the sample registration forms of a policy. The Map is keyed by account id</li>
 * </ul>
 */
public class BtxDetailsMaintainPolicy extends BTXDetails {
  static final long serialVersionUID = -1852947526051608131L;

  private String _operation = null;
  private String _associatedIrbs = null;
  
  private PolicyData _policy = null;
  
  // MR 8049
  private String _verifyRequiredOldValue = null;
  private String _releaseRequiredOldValue = null;

  private Map _caseRegistrationFormChoiceMap = null;
  private Map _sampleRegistrationFormChoiceMap = null;

  public BtxDetailsMaintainPolicy() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MAINTAIN_POLICY;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  public Set getDirectlyInvolvedObjects() {
    // Policy ids are just numbers and so can't be usefully distinguished as transaction
    // indexers.  So, here we use a "virtual" prefixed form of the policy id, not the
    // actual id as stored in the database.

    Set set = new HashSet();
    set.add(FormLogic.makePrefixedPolicyId(getPolicy().getPolicyId()));

    return set;
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

    history.setAttrib1(getOperation());
    history.setAttrib2(getPolicy().getPolicyId());
    history.setAttrib3(getPolicy().getPolicyName());
    history.setAttrib4(getPolicy().getAllocationNumerator());
    history.setAttrib5(getPolicy().getAllocationDenominator());
    history.setAttrib6(GbossFactory.getInstance().getDescription(getPolicy().getAllocationFormatCid()));
    history.setAttrib7(getPolicy().getDefaultLogicalReposId());
    history.setAttrib8(getPolicy().getRestrictedLogicalReposId());
    //ship to Ardais scheme is now obsolete, but was stored in attribute 9 prior to it's
    //removal.  This attribute should not be used for anything else, as there are records
    //in the database with a value for this attribute
    history.setAttrib10(getPolicy().getVerifyRequired());
    history.setAttrib11(getPolicy().getReleaseRequired());
    history.setAttrib12(getPolicy().getAdditionalQuestionsGrp());
    history.setAttrib13(getPolicy().getAccountId());
    history.setAttrib14(getPolicy().getAllowForUnlinkedCases());
    history.setAttrib15(getPolicy().getPolicyDataEncoding());
    history.setAttrib16(getPolicy().getCaseRegistrationFormId());
    history.setAttrib17(getPolicy().getCaseRegistrationFormName());
    history.setClob1(getPolicy().toXml());
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

    setOperation(history.getAttrib1());

    getPolicy().setPolicyId(history.getAttrib2());
    getPolicy().setPolicyName(history.getAttrib3());
    getPolicy().setAllocationNumerator(history.getAttrib4());
    getPolicy().setAllocationDenominator(history.getAttrib5());
    getPolicy().setAllocationFormatCid(history.getAttrib6());
    getPolicy().setDefaultLogicalReposId(history.getAttrib7());
    getPolicy().setRestrictedLogicalReposId(history.getAttrib8());
    getPolicy().setVerifyRequired(history.getAttrib10());
    getPolicy().setReleaseRequired(history.getAttrib11());
    getPolicy().setAdditionalQuestionsGrp(history.getAttrib12());
    getPolicy().setAccountId(history.getAttrib13());
    getPolicy().setAllowForUnlinkedCases(history.getAttrib14());
    getPolicy().setPolicyDataEncoding(history.getAttrib15());
    getPolicy().setCaseRegistrationFormId(history.getAttrib16());
    getPolicy().setCaseRegistrationFormName(history.getAttrib17());
    if (!ApiFunctions.isEmpty(history.getClob1())) {
      getPolicy().setPolicyData(history.getClob1());
    }

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    // <None>
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getOperation
    //   getPolicy().getPolicyId
    //   getPolicy().getPolicyName
    //   getPolicy().getAccountId
    //   getPolicy().getAllowForUnlinkedCases
    //   getPolicy().getAllocationNumerator
    //   getPolicy().getAllocationDenominator
    //   getPolicy().getAllocationFormatCid
    //   getPolicy().getDefaultLogicalReposId
    //   getPolicy().getRestrictedLogicalReposId
    //   getPolicy().getCaseRegistrationFormId()
    //   getPolicy().getCaseRegistrationFormName()

    StringBuffer sb = new StringBuffer(256);

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    // The form of the result depends on the operation.
    // Operation "Create":
    //    Created policy <policyName> (<policyId>) [for account (accountId)], with the following attributes: [attributes]
    // Operation "Update":
    //    Updated policy <policyName> (<policyId>) [for account (accountId)], with the following attributes: [attributes]
    // Operation "Delete":
    //    Removed policy <policyName> (<policyId>) [for account (accountId)], with the following attributes: [attributes]

    String operation = getOperation();

    if (Constants.OPERATION_CREATE.equals(operation)) {
      sb.append("Created policy ");
    }
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      sb.append("Updated policy ");
    }
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      sb.append("Removed policy ");
    }
    Escaper.htmlEscape(getPolicy().getPolicyName(), sb);
    sb.append(" (");
    sb.append(IcpUtils.prepareLink(FormLogic.makePrefixedPolicyId(getPolicy().getPolicyId()), securityInfo));
    
    String accountId = getPolicy().getAccountId();
    if (ApiFunctions.isEmpty(accountId)) {
      sb.append("), with the following attributes: ");
    }
    else {
      sb.append(") for account (");      sb.append(IcpUtils.prepareLink(accountId, getLoggedInUserSecurityInfo()));
      sb.append("), with the following attributes: ");
    }

    if (!ApiFunctions.isEmpty(getPolicy().getAllowForUnlinkedCases())) {
      sb.append("Allow For Unlinked Cases (");
      sb.append(getPolicy().getAllowForUnlinkedCases());
      sb.append("), ");
    }
    sb.append("Allocation Numerator (");
    sb.append(getPolicy().getAllocationNumerator());
    sb.append("), ");
    sb.append("Allocation Denominator (");
    sb.append(getPolicy().getAllocationDenominator());
    sb.append("), ");
    sb.append("Allocation Format (");
    Escaper.htmlEscape(getPolicy().getAllocationFormatCid(), sb);
    sb.append("), ");
    sb.append("Default Inventory Group (");
    String prefixedReposId = FormLogic.makePrefixedLogicalRepositoryId(getPolicy().getDefaultLogicalReposId());
    sb.append(IcpUtils.prepareLink(prefixedReposId, getLoggedInUserSecurityInfo()));
    sb.append(")");
    if (!ApiFunctions.isEmpty(getPolicy().getRestrictedLogicalReposId())) {
      sb.append(", ");
      sb.append("Restricted Inventory Group (");
      prefixedReposId = FormLogic.makePrefixedLogicalRepositoryId(getPolicy().getRestrictedLogicalReposId());
      sb.append(IcpUtils.prepareLink(prefixedReposId, getLoggedInUserSecurityInfo()));
      sb.append(")");
    }
    if (!ApiFunctions.isEmpty(getPolicy().getVerifyRequired())) {
      sb.append(", ");
      sb.append("Consent Verification Required (");
      sb.append(getPolicy().getVerifyRequired());
      sb.append(")");            
    }
    if (!ApiFunctions.isEmpty(getPolicy().getReleaseRequired())) {
      sb.append(", ");
      sb.append("Case Release Required (");
      sb.append(getPolicy().getReleaseRequired());
      sb.append(")");      
    }
    if (!ApiFunctions.isEmpty(getPolicy().getAdditionalQuestionsGrp())) {
      sb.append(", ");
      sb.append("Additional Questions Group (");
      sb.append(getPolicy().getAdditionalQuestionsGrp());
      sb.append(")");      
    }
    if (!ApiFunctions.isEmpty(getPolicy().getCaseRegistrationFormId())) {
      sb.append(", ");
      sb.append("Case Registration Form (");
      String formName = getPolicy().getCaseRegistrationFormName();
      if (ApiFunctions.isEmpty(formName)) {
        formName = getPolicy().getCaseRegistrationFormId();
      }
      sb.append(IcpUtils.prepareLink(getPolicy().getCaseRegistrationFormId(), formName, getLoggedInUserSecurityInfo()));
      sb.append(")");      
    }
    sb.append(".");

    if (getPolicy().getSampleTypeConfiguration() != null) {
      sb.append("<br>");
      sb.append(getPolicy().getSampleTypeConfiguration().toHtml(getPolicy().getPolicyDataEncoding(), getLoggedInUserSecurityInfo())); 
    }

    return sb.toString();
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
  public String getReleaseRequiredOldValue() {
    return _releaseRequiredOldValue;
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
   * @param string
   */
  public void setReleaseRequiredOldValue(String string) {
    _releaseRequiredOldValue = string;
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