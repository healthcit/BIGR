package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * Represents the details of a find by id business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setArdaisId(String) ardaisId}: The Ardais id to look for.  Note that either this field
 * or the customerId field must be populated, but not both.</li>
 * <li>{@link #setCustomerId(String) customerId}: The customer id to look for.  Note that either this field
 * or the ardaisId field must be populated, but not both.</li>
 * <li>{@link #setFindDonors(String) findDonors}: A String indicating if donors matching the
 * specified id should be located.</li>
 * <li>{@link #setFindCases(String) findCases}: A String indicating if cases matching the
 * specified id should be located.</li>
 * <li>{@link #setFindSamples(String) findSamples}: A String indicating if samples matching the
 * specified id should be located.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setDonorsSearched(boolean) boolean}: True if donors were searched for, 
 * false otherwise.</li>
 * <li>{@link #setDonors(List) list}: A list of {@link DonorData DonorData} objects, one
 * per donor matching the id value.</li>
 * <li>{@link #setCasesSearched(boolean) boolean}: True if cases were searched for, 
 * false otherwise.</li>
 * <li>{@link #setCases(List) list}: A list of {@link ConsentData ConsentData} objects, one
 * per case matching the id value.</li>
 * <li>{@link #setSamplesSearched(boolean) boolean}: True if samples were searched for, 
 * false otherwise.</li>
 * <li>{@link #setSamples(List) list}: A list of {@link SampleData SampleData} objects, one
 * per sample matching the id value.</li>
 * </ul>
 */
public class BtxDetailsFindById extends BTXDetails implements Serializable {
  private static final long serialVersionUID = 7479015265990020200L;

  private String _ardaisId;
  private String _customerId;
  private String _findDonors;
  private String _findCases;
  private String _findSamples;
  private boolean _donorsSearched;
  private List _donors;
  private boolean _casesSearched;
  private List _cases;
  private boolean _samplesSearched;
  private List _samples;
  
  /** 
   * Constructor
   */
  public BtxDetailsFindById() {
    super();
  }


  public String getBTXType() {
    return BTXDetails.BTX_TYPE_FIND_BY_ID;
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
  public String getArdaisId() {
    return _ardaisId;
  }

  /**
   * @return
   */
  public List getCases() {
    return _cases;
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
  public List getDonors() {
    return _donors;
  }

  /**
   * @return
   */
  public String getFindCases() {
    return _findCases;
  }

  /**
   * @return
   */
  public String getFindDonors() {
    return _findDonors;
  }

  /**
   * @return
   */
  public String getFindSamples() {
    return _findSamples;
  }

  /**
   * @return
   */
  public List getSamples() {
    return _samples;
  }

  /**
   * @param string
   */
  public void setArdaisId(String string) {
    _ardaisId = string;
  }

  /**
   * @param list
   */
  public void setCases(List list) {
    _cases = list;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = ApiFunctions.safeTrim(string);
  }

  /**
   * @param list
   */
  public void setDonors(List list) {
    _donors = list;
  }

  /**
   * @param string
   */
  public void setFindCases(String string) {
    _findCases = string;
  }

  /**
   * @param string
   */
  public void setFindDonors(String string) {
    _findDonors = string;
  }

  /**
   * @param string
   */
  public void setFindSamples(String string) {
    _findSamples = string;
  }

  /**
   * @param list
   */
  public void setSamples(List list) {
    _samples = list;
  }

  /**
   * @return
   */
  public boolean isCasesSearched() {
    return _casesSearched;
  }

  /**
   * @return
   */
  public boolean isDonorsSearched() {
    return _donorsSearched;
  }

  /**
   * @return
   */
  public boolean isSamplesSearched() {
    return _samplesSearched;
  }

  /**
   * @param b
   */
  public void setCasesSearched(boolean b) {
    _casesSearched = b;
  }

  /**
   * @param b
   */
  public void setDonorsSearched(boolean b) {
    _donorsSearched = b;
  }

  /**
   * @param b
   */
  public void setSamplesSearched(boolean b) {
    _samplesSearched = b;
  }

}
