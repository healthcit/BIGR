package com.ardais.bigr.library.btx;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BTXDetailsGetSummarySql extends BTXDetailsForSampleSummary {

  private String _sqlResult;

  /**
  * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
  */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_SAMPLE_SUMMARY_SQL;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // This type isn't logged.
    throw new ApiException("BTXDetailsGetSummarySql.doGetDetailsAsHTML() method called in error!");
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
    throw new ApiException("BTXDetailsGetSummarySql.describeIntoHistoryRecord() method called in error!");
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
    throw new ApiException("BTXDetailsGetSummarySql.populateFromHistoryRecord() method called in error!");
  }

  /**
   * Returns the sqlResult.
   * @return String
   */
  public String getSqlResult() {
    return _sqlResult;
  }

  /**
   * Sets the sqlResult.
   * @param sqlResult The sqlResult to set
   */
  public void setSqlResult(String sqlResult) {
    _sqlResult = sqlResult;
  }

}
