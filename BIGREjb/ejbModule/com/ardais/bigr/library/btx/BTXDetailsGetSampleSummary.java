package com.ardais.bigr.library.btx;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.query.SampleSelectionSummary;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BTXDetailsGetSampleSummary extends BTXDetailsForSampleSummary {

  private SampleSelectionSummary _sampleSelectionSummary = null;
  private int _count = 0;
  
  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_SAMPLE_SUMMARY;
  }

  /**
   * Returns the sampleSelectionSummary.
   * @return SampleSelectionSummary
   */
  public SampleSelectionSummary getSampleSelectionSummary() {
    return _sampleSelectionSummary;
  }

  /**
   * Sets the sampleSelectionSummary.
   * @param sampleSelectionSummary The sampleSelectionSummary to set
   */
  public void setSampleSelectionSummary(SampleSelectionSummary sampleSelectionSummary) {
    this._sampleSelectionSummary = sampleSelectionSummary;
    this._count = sampleSelectionSummary.getTotalSampleCount();
  }

  protected String doGetDetailsAsHTML() {
    StringBuffer sb = new StringBuffer();
    sb.append("Query for available products of type ");
    Escaper.htmlEscape(getProductDescription(), sb);
    sb.append(" found ");
    sb.append(Integer.toString(_count));
    sb.append("items.");
    return sb.toString();
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
    
    history.setAttrib1(Integer.toString(_count));
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
    
    _count = Integer.parseInt(history.getAttrib1());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    _sampleSelectionSummary = null;
  }

}
