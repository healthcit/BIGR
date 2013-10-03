package com.ardais.bigr.iltds.btx;

/**
 * Holds the details for the transaction that selects the operation, date, etc. of a batch of 
 * derivation operations.
 */
public class BtxDetailsDerivationBatchSelect extends BtxDetailsDerivationBatch {

  public BtxDetailsDerivationBatchSelect() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_DERIVATION_BATCH_SELECT;
  }

}
