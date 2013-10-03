/*
 * Created on Jun 16, 2004
 *
 */
package com.ardais.bigr.iltds.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.StringList;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.LogicalRepositoryUtils;

/**
 * Represent the details of a transfer samples to inventory group business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 * 
 * NOTE: This BTXDetails class is different from the others in that a database trigger is used to populate 
 * the ILTDS_BTX_HISTORY and ILTDS_BTX_INVOLVED_OBJECT tables - this class is never used for anything other
 * than to display the details of the transaction.  As such, there are no required input or output fields.
 * This is likely to change when we have a UI that allows for this functionality, but for now this is
 * the case.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>None.
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BtxDetailsTransferSamplesToInventoryGroup extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = 8883133136497627873L;

  private IdList _sampleList = null;
  private String _allocationInd;
  private String _oldInventoryGroupId;
  private String _oldInventoryGroupName;
  private String _newInventoryGroupId;
  private String _newInventoryGroupName;
  private String _reason;

  /** 
   * Constructor
   */
  public BtxDetailsTransferSamplesToInventoryGroup() {
    super();
  }

  /**
   * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
   * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
   * method. This method must not make use of any fields that aren't set by the 
   * populateFromHistoryRecord method. For this object type, the fields we can use here 
   * are:
    getAllocationInd()
    getOldInventoryGroupId()
    getOldInventoryGroupName()
    getNewInventoryGroupId()
    getNewInventoryGroupName()
    getReason()
    getSampleList()
   *
   * @return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page
   */
  protected String doGetDetailsAsHTML() {

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(2048);

    // The result has this form:
    //    Moved (and marked as <restricted/unrestricted> if they weren't already)
    //    the following samples from inventory group <old group name> (<old group id>)  
    //    to inventory group <new group name> (<new group id>): <sample list>.
    //    This was done for the following reason: <reason>

    sb.append("Moved (and marked as ");
    if (FormLogic.ALL_RESTRICTED_IND.equalsIgnoreCase(getAllocationInd())) {
      sb.append("restricted");
    }
    else if (FormLogic.ALL_UNRESTRICTED_IND.equalsIgnoreCase(getAllocationInd())) {
      sb.append("unrestricted");
    }
    else {
      sb.append(Escaper.htmlEscapeAndPreserveWhitespace("<unrecognized allocation_ind value>"));
    }
    sb.append(" if they were not already)");
    sb.append(" the following samples from inventory group ");
    LogicalRepository lr = new LogicalRepository();
    lr.setId(getOldInventoryGroupId());
    lr.setFullName(getOldInventoryGroupName());
    addIcpLink(lr,sb);
    sb.append(" to inventory group ");
    lr = new LogicalRepository();
    lr.setId(getNewInventoryGroupId());
    lr.setFullName(getNewInventoryGroupName());
    addIcpLink(lr,sb);
    sb.append(": ");
    getSampleList().appendICPHTML(sb, securityInfo);
    sb.append(".  This was done for the following reason: ");
    Escaper.htmlEscape(getReason(), sb);

    return sb.toString();
  }

  private void addIcpLink(LogicalRepository lr, StringBuffer sb) {
    IdList ilist = new IdList();
    ilist.add(lr.getId());
    ilist = LogicalRepositoryUtils.makePrefixedLogicalRepositoryIds(ilist);
    StringList sList = new StringList();
    sList.add(lr.getFullName());
    ilist.appendICPHTML(sb, sList.getList(), getLoggedInUserSecurityInfo());
    sb.append(" (");
    sb.append(FormLogic.makePrefixedLogicalRepositoryId(lr.getId()));
    sb.append(")");
  }

  /**
   * Return the business transaction type code for the transaction that this
   * class represents.
   *
   * @return the transaction type code.
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_TRANSFER_SAMPLES_TO_INV_GROUP;
  }

  /**
   * This method is here only because it's required (the method declaration on the parent is abstract).
   * It should never be called (see the comment at the top of this class), since this class is never
   * used to record data in the database.
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
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

    setAllocationInd(history.getAttrib1());
    setOldInventoryGroupId(history.getAttrib2());
    setOldInventoryGroupName(history.getAttrib3());
    setNewInventoryGroupId(history.getAttrib4());
    setNewInventoryGroupName(history.getAttrib5());
    setReason(history.getAttrib6());
    setSampleList(history.getIdList1());
  }
  
  /**
   * @return
   */
  public String getNewInventoryGroupId() {
    return _newInventoryGroupId;
  }

  /**
   * @return
   */
  public String getNewInventoryGroupName() {
    return _newInventoryGroupName;
  }

  /**
   * @return
   */
  public String getOldInventoryGroupId() {
    return _oldInventoryGroupId;
  }

  /**
   * @return
   */
  public String getOldInventoryGroupName() {
    return _oldInventoryGroupName;
  }

  /**
   * @return
   */
  public String getReason() {
    return _reason;
  }

  /**
   * @return
   */
  public IdList getSampleList() {
    return _sampleList;
  }

  /**
   * @return
   */
  public String getAllocationInd() {
    return _allocationInd;
  }

  /**
   * @param string
   */
  public void setNewInventoryGroupId(String string) {
    _newInventoryGroupId = string;
  }

  /**
   * @param string
   */
  public void setNewInventoryGroupName(String string) {
    _newInventoryGroupName = string;
  }

  /**
   * @param string
   */
  public void setOldInventoryGroupId(String string) {
    _oldInventoryGroupId = string;
  }

  /**
   * @param string
   */
  public void setOldInventoryGroupName(String string) {
    _oldInventoryGroupName = string;
  }

  /**
   * @param string
   */
  public void setReason(String string) {
    _reason = string;
  }

  /**
   * @param list
   */
  public void setSampleList(IdList list) {
    _sampleList = list;
  }

  /**
   * @param string
   */
  public void setAllocationInd(String string) {
    _allocationInd = string;
  }

}