package com.ardais.bigr.iltds.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.assistants.LegalValueSet;

/**
 * Holds the details for the transaction that starts of a batch of derivation operations.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li></li>
 * </ul>
 */
public class BtxDetailsDerivationBatchStart extends BtxDetailsDerivationBatch {

  private String[] _selectedSampleIds;

  public BtxDetailsDerivationBatchStart() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_DERIVATION_BATCH_START;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    throw new ApiException("Attempt to call doGetDetailsAsHTML in BtxDetailsDerivationBatch - should not have been called since this class does not support transaction logging.");
  }


  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("Attempt to call describeIntoHistoryRecord in BtxDetailsDerivationBatch - should not have been called since this class does not support transaction logging.");
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryObject(com.ardais.bigr.btx.framework.BtxHistoryAttributes)
   */
  protected void populateFromHistoryObject(BtxHistoryAttributes attributes) {
    throw new ApiException("Attempt to call populateFromHistoryObject in BtxDetailsDerivationBatch - should not have been called since this class does not support transaction logging.");
  }
  
  public String[] getSelectedSampleIds() {
    return _selectedSampleIds;
  }
  
  public void setSelectedSampleIds(String[] selectedSampleIds) {
    _selectedSampleIds = selectedSampleIds;
  }
}
