package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.StringList;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.LogicalRepositoryUtils;

/**
 * Represent the details of logging a sample allocation.  This is designed to represent
 * details of an allocation that has already, happened so that we can log it in the transaction
 * history.  It does not actually perform the allocation.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleBarcodeId(String) Sample id}: The sample that was allocated.</li>
 * <li>{@link #setAllocationInd(String) Allocation_ind}: The allocation indicator assigned
 *     to the sample.</li>
 * <li>{@link #setLogicalRepositoryIds(IdList) Repository ids}: The ids of the logical
 *     repositories that the sample was assigned to.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setPolicyName(String) Policy name}: The name of the policy used to allocate
 *     the sample.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setLogicalRepositoryNames(IdList) Repository names}: The short names of the
 *     repositories in the LogicalRepositoryIds input parameter.</li>
 * <li>{@link #setSampleAlias(String) Sample Alias}: The sample alias.</li>
 * </ul>
 */
public class BtxDetailsLogAllocateSingle extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = -2629975377636355346L;

  private String _sampleBarcodeId = null;
  private String _allocationInd = null;
  private String _policyName = null;
  private IdList _logicalRepositoryIds = null;
  private StringList _logicalRepositoryNames = null;
  private String _sampleAlias = null;

  public BtxDetailsLogAllocateSingle() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_LOG_ALLOCATE_SINGLE;
  }

  public Set getDirectlyInvolvedObjects() {
    // We don't include the logical repository ids in the involved objects because their
    // ids are just numbers, not prefixed, identifiable ids of the sort that we assign to
    // inventory items like samples.

    Set set = new HashSet();

    set.add(getSampleBarcodeId());

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

    history.setAttrib1(getSampleBarcodeId());
    history.setAttrib2(getAllocationInd());
    history.setAttrib3(getPolicyName());
    history.setAttrib4(getSampleAlias());
    history.setIdList1(getLogicalRepositoryIds());
    history.setClob1(getLogicalRepositoryNamesPacked());
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

    setSampleBarcodeId(history.getAttrib1());
    setAllocationInd(history.getAttrib2());
    setPolicyName(history.getAttrib3());
    setSampleAlias(history.getAttrib4());
    setLogicalRepositoryIds(history.getIdList1());
    setLogicalRepositoryNames(new StringList(history.getClob1()));

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    // <NONE>
  }

  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getSampleBarcodeId
    //   getAllocationInd
    //   getLogicalRepositoryIds
    //   getLogicalRepositoryNames

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(512);

    // The result has this form:
    //    Sample <sampleBarcodeId> is <restricted/unrestricted/allocationInd>.
    //    It was allocated using the <policyName> policy
    //    and has been assigned to: <logicalRepositoryNames>.

    String allocInd = getAllocationInd();

    sb.append("Sample ");
    sb.append(IcpUtils.prepareLink(getSampleBarcodeId(), securityInfo));
    if (!ApiFunctions.isEmpty(ApiFunctions.safeTrim(getSampleAlias()))) {
      sb.append(" (");
      Escaper.htmlEscape(getSampleAlias(), sb);
      sb.append(")");
    }
    if (FormLogic.ALL_RESTRICTED_IND.equals(allocInd)) {
      sb.append(" is restricted");
    }
    else if (FormLogic.ALL_UNRESTRICTED_IND.equals(allocInd)) {
      sb.append(" is unrestricted");
    }
    else if (ApiFunctions.isEmpty(allocInd)) {
      // This shouldn't ever really happen, since we're logging the fact the we just allocated it.
      sb.append(" has not has its restriction status determined");
    }
    else {
      // This shouldn't ever really happen, since since allocation assigns either R or U, but
      // we put this here to be safe.
      sb.append(" has allocation = '");
      Escaper.htmlEscape(allocInd, sb);
      sb.append("'");
    }

    String policyName = getPolicyName();
    if (!ApiFunctions.isEmpty(policyName)) {
      sb.append(".  It was allocated using the ");
      Escaper.htmlEscape(policyName, sb);
      sb.append(" policy");
    }

    IdList prefixedReposIds = LogicalRepositoryUtils.makePrefixedLogicalRepositoryIds(getLogicalRepositoryIds());
    StringList reposNames = getLogicalRepositoryNames();
    if (reposNames != null && reposNames.size() > 0) {
      sb.append(" and has been assigned to");
      if (reposNames.size() > 1) {
        sb.append(':');
      }
      sb.append(' ');
      prefixedReposIds.appendICPHTML(sb, reposNames.getList(), securityInfo);
      sb.append('.');
    }
    else {
      sb.append(".  It was not assigned to any inventory group.");
    }

    return sb.toString();
  }

  public String getAllocationInd() {
    return _allocationInd;
  }

  public IdList getLogicalRepositoryIds() {
    return _logicalRepositoryIds;
  }

  public StringList getLogicalRepositoryNames() {
    return _logicalRepositoryNames;
  }

  private String getLogicalRepositoryNamesPacked() {
    return ((_logicalRepositoryNames == null) ? "" : _logicalRepositoryNames.pack());
  }

  public String getPolicyName() {
    return _policyName;
  }

  public String getSampleBarcodeId() {
    return _sampleBarcodeId;
  }

  public String getSampleAlias() {
    return _sampleAlias;
  }

  public void setAllocationInd(String string) {
    _allocationInd = string;
  }

  public void setLogicalRepositoryIds(IdList list) {
    _logicalRepositoryIds = list;
  }

  public void setLogicalRepositoryNames(StringList list) {
    _logicalRepositoryNames = list;
  }

  public void setPolicyName(String string) {
    _policyName = string;
  }

  public void setSampleBarcodeId(String string) {
    _sampleBarcodeId = string;
  }

  public void setSampleAlias(String sampleAlias) {
    _sampleAlias = sampleAlias;
  }
}