package com.ardais.bigr.library.btx;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * This is a non-logged version of {@link BTXDetailsGetSampleSummary}.
 */
public class BTXDetailsGetSampleSummaryNoHistory extends BTXDetailsGetSampleSummary {

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_SAMPLE_SUMMARY_NO_HISTORY;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // This type isn't logged.
    throw new ApiException("BTXDetailsGetSampleSummaryNoHistory.doGetDetailsAsHTML() method called in error!");
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
    // This type isn't logged.
    throw new ApiException("BTXDetailsGetSampleSummaryNoHistory.describeIntoHistoryRecord() method called in error!");
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
    // This type isn't logged.
    throw new ApiException("BTXDetailsGetSampleSummaryNoHistory.populateFromHistoryRecord() method called in error!");
  }

}
