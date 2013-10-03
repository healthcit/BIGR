package com.ardais.bigr.iltds.btx;

import java.io.Serializable;
import java.util.Set;

import com.ardais.bigr.api.ApiException;

/**
 * Represent the details of a scan tracking number business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setNewTrackingNumber(String) Tracking number}: The new tracking number to process.
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BtxDetailsShipmentReceiptVerification extends BTXDetails implements Serializable {

  private static final long serialVersionUID = -5305349142689817719L;

  private String _newTrackingNumber = null;

  /**
   * BtxDetailsShipmentReceiptVerification constructor comment.
   */
  public BtxDetailsShipmentReceiptVerification() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_SHIPMENT_RECEIPT_VERIFICATION;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    throw new ApiException("Called in error, this BTXDetails type is not logged.");
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    throw new ApiException("Called in error, this BTXDetails type is not logged.");
  }

  /**
   * Return the shipper's tracking number for the shipment.
   *
   * @return the new shipment tracking number.
   */
  public String getNewTrackingNumber() {
    return _newTrackingNumber;
  }

  /**
   * Set the new tracking number to process.
   *
   * @param newTrackingNumber the new tracking number to process.
   */
  public void setNewTrackingNumber(String newTrackingNumber) {
    _newTrackingNumber = newTrackingNumber;
  }
}
