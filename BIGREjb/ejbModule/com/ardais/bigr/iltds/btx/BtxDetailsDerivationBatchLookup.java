package com.ardais.bigr.iltds.btx;

/**
 * Holds the details for the transaction that fetches the parents of a batch of 
 * derivation operations.
 */
public class BtxDetailsDerivationBatchLookup extends BtxDetailsDerivationBatch {

  public BtxDetailsDerivationBatchLookup() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_DERIVATION_BATCH_LOOKUP;
  }

}
