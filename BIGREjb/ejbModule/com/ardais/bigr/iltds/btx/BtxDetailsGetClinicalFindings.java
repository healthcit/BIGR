package com.ardais.bigr.iltds.btx;

import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.javabeans.ClinicalFindingData;

/**
 * Represent the details of starting to edit clinical findings data.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields: at least one of the following</h4>
 * <ul>
 * <li>{@link #setArdaisId(String) ardais id}: The id of the donor.</li>
 * <li>{@link #setConsentId(String) consent id}: The id of the consent.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields: Either</h4>
 * <ul>
 * <li>{@link #setConsentsAndFindings(Map) consentsAndFindings}: A Map keyed by consent ids (one per
 * consent beloning to the donor) of <code>ClinicalFindingData</code> beans.  Each consent that has
 * clinical finding data will map to a ClinicalFindingData bean, and each consent that has no
 * clinical finding data will map to null.</li>
 * <li>{@link #setArdaisId(String) ardais id}: The id of the donor belonging to the specified case.</li>
 * OR
 * <li>{@link #setClinicalFinding(ClinicalFindingData) finding}: A <code>ClinicalFindingData</code> 
 * bean containing any clinical finding information for the specified consent.</li>
 * <li>{@link #setArdaisId(String) ardais id}: The id of the donor belonging to the specified case.</li>
 * <li>{@link #setNewFinding(boolean) newFinding}: An indicator as to whether the finding is new
 * or already exists in the database.</li>
 * </ul>
 */
public class BtxDetailsGetClinicalFindings extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = 6106753657425571715L;
  
  private String _ardaisId;
  private String _consentId;
  private Map _consentsAndFindings;
  private ClinicalFindingData _clinicalFinding;
  private boolean _newFinding;

  public BtxDetailsGetClinicalFindings() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_CLINICAL_FINDINGS;
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
  public ClinicalFindingData getClinicalFinding() {
    return _clinicalFinding;
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
  public Map getConsentsAndFindings() {
    return _consentsAndFindings;
  }

  /**
   * @param string
   */
  public void setArdaisId(String string) {
    _ardaisId = string;
  }

  /**
   * @param data
   */
  public void setClinicalFinding(ClinicalFindingData data) {
    _clinicalFinding = data;
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = string;
  }

  /**
   * @param map
   */
  public void setConsentsAndFindings(Map map) {
    _consentsAndFindings = map;
  }

  /**
   * @return
   */
  public boolean isNewFinding() {
    return _newFinding;
  }

  /**
   * @param b
   */
  public void setNewFinding(boolean b) {
    _newFinding = b;
  }

}