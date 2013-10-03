package com.ardais.bigr.iltds.btx;

import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.javabeans.ConsentData;

/**
 * Represent the details of generating a picklist for an imported case.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields: One of the following</h4>
 * <ul>
 * <li>{@link #setConsentId(String) consent id}: The Ardais consent id.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setConsentData(ConsentData) consentData}: A <code>ConsentData</code> bean, containing information about the consent.</li>
 * <li>{@link #setSamplesInRepository(List) samples}: The samples belonging to the case in the repository, if any.</li>
 * <li>{@link #setSamplesInTransit(List) samples}: The samples belonging to the case on manifests, if any.</li>
 * <li>{@link #setSamplesCheckedOut(List) samples}: The samples belonging to the case checked out, if any.</li>
 * <li>{@link #setLocations(List) locations}: The various locations at which the samples belonging to the case are stored, if any.</li>
 * <li>{@link #setReportGeneratedByName(String) name}: The name of the user generating the picklist.</li>
 * </ul>
 */
public class BtxDetailsGenerateCasePickList extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = -3119402365132041803L;
  
  private String _consentId;
  private ConsentData _consentData;
  private List _samplesInRepository;
  private List _samplesInTransit;
  private List _samplesCheckedOut;
  private List _locations;
  private String _reportGeneratedByName;
  private String _txType;
  private String _caseAction;
  private String _revokeStaffName;

  public BtxDetailsGenerateCasePickList() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GENERATE_IMPORTED_CASE_PICKLIST;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    throw new ApiException("doGetDetailsAsHTML should not have been called, this class does not support transaction logging.");
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("describeIntoHistoryRecord should not have been called, this class does not support transaction logging.");

    //super.describeIntoHistoryRecord(history);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("populateFromHistoryRecord should not have been called, this class does not support transaction logging.");

    //super.populateFromHistoryRecord(history);
  }
  /**
   * @return
   */
  public ConsentData getConsentData() {
    return _consentData;
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
  public List getLocations() {
    return _locations;
  }

  /**
   * @return
   */
  public String getReportGeneratedByName() {
    return _reportGeneratedByName;
  }

  /**
   * @return
   */
  public List getSamplesCheckedOut() {
    return _samplesCheckedOut;
  }

  /**
   * @return
   */
  public List getSamplesInRepository() {
    return _samplesInRepository;
  }

  /**
   * @return
   */
  public List getSamplesInTransit() {
    return _samplesInTransit;
  }

  /**
   * @param data
   */
  public void setConsentData(ConsentData data) {
    _consentData = data;
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = string;
  }

  /**
   * @param list
   */
  public void setLocations(List list) {
    _locations = list;
  }

  /**
   * @param string
   */
  public void setReportGeneratedByName(String string) {
    _reportGeneratedByName = string;
  }

  /**
   * @param list
   */
  public void setSamplesCheckedOut(List list) {
    _samplesCheckedOut = list;
  }

  /**
   * @param list
   */
  public void setSamplesInRepository(List list) {
    _samplesInRepository = list;
  }

  /**
   * @param list
   */
  public void setSamplesInTransit(List list) {
    _samplesInTransit = list;
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
   * @return
   */
  public String getCaseAction() {
    return _caseAction;
  }

  /**
   * @param string
   */
  public void setCaseAction(String string) {
    _caseAction = string;
  }

  /**
   * @return
   */
  public String getRevokeStaffName() {
    return _revokeStaffName;
  }

  /**
   * @param string
   */
  public void setRevokeStaffName(String string) {
    _revokeStaffName = string;
  }

}