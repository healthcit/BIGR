package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of a delete sample business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleData(SampleData) SampleData}: A SampleData bean holding the id (and
 * optionally the case Id) of the sample that was deleted.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setDeleteReason(String) reason}: The reason for deleting the sample.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BtxDetailsLogDeleteSample extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = 1302071273686719924L;

  private SampleData _sampleData;
  private String _deleteReason;
  
  /** 
   * Constructor
   */
  public BtxDetailsLogDeleteSample() {
    super();
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
    history.setAttrib1(getSampleData().getSampleId());
    history.setAttrib2(getSampleData().getConsentId());
    history.setAttrib3(getDeleteReason());
  }
  
  /**
   * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
   * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
   * method. This method must not make use of any fields that aren't set by the 
   * populateFromHistoryRecord method. For this object type, the fields we can use here 
   * are:
   *  getSampleId()
   *  getDeleteReason()
   *
   * @return an HTML string that defines how the transaction details are presented
   * in a transaction-history web page
   */
  protected String doGetDetailsAsHTML() {
       
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();        
    StringBuffer sb = new StringBuffer(128);

    // If no reason was provided, the result has this form:
    //    Sample <sampleId> was deleted.
    // If a reason was provided, the result has this form:
    //    Sample <sampleId> was deleted for the following reason: <reason>.

    sb.append("Sample ");
    sb.append(IcpUtils.prepareLink(getSampleData().getSampleId(), securityInfo));
    sb.append(" was deleted");
    if (!ApiFunctions.isEmpty(getDeleteReason())) {
      sb.append(" for the following reason: ");
      Escaper.htmlEscape(getDeleteReason(),sb);
    }
    sb.append(".");

    return sb.toString();
  }

  
  /**
   * Return the business transaction type code for the transaction that this
   * class represents.
   *
   * @return the transaction type code.
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_LOG_DELETE_SAMPLE;
  }

  /**
   * Return a set of the object ids of the objects that are directly involved
   * in this transaction.  This set does not contain the ids of objects that
   * are considered to be indirectly involved in the transaction, and it does
   * not include the user id of the user who performed the transaction.
   * <p>
   * For example, a transaction that scans a box of samples directly involves the box
   * object and each of the sample objects, and indirectly involves the
   * asm, asm form, case and donor objects for each sample.
   *
   * @return the set of directly involved object ids.
   */
  public java.util.Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(getSampleData().getSampleId());
    //if the case id has been specified, include it so this transaction appears for
    //the case.  Normally sample operations are not included in the case history,
    //but the deletion of the sample seems like it should appear under the case history.
    if (!ApiFunctions.isEmpty(getSampleData().getConsentId())) {
      set.add(getSampleData().getConsentId());
    }
    return set;
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
    SampleData sampleData = new SampleData();
    sampleData.setSampleId(history.getAttrib1());
    sampleData.setConsentId(history.getAttrib2());
    setSampleData(sampleData);
    setDeleteReason(history.getAttrib3());
  }

  /**
   * @return
   */
  public String getDeleteReason() {
    return _deleteReason;
  }

  /**
   * @param string
   */
  public void setDeleteReason(String string) {
    _deleteReason = string;
  }

  /**
   * @return
   */
  public SampleData getSampleData() {
    return _sampleData;
  }

  /**
   * @param data
   */
  public void setSampleData(SampleData data) {
    _sampleData = data;
  }

}