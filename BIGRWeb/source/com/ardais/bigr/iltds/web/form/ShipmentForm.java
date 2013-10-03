package com.ardais.bigr.iltds.web.form;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsShipmentReceiptVerification;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * A Struts ActionForm that represents a request.
 */
public class ShipmentForm extends BigrActionForm {

  private String _newTrackingNumber = null;
  private HashMap _trackingNumbers = new HashMap();

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _newTrackingNumber = null;
    _trackingNumbers.clear();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    super.describeIntoBtxDetails(btxDetails0, mapping, request);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    if (btxDetails instanceof BtxDetailsShipmentReceiptVerification) {
      BtxDetailsShipmentReceiptVerification btx = (BtxDetailsShipmentReceiptVerification) btxDetails;
      
      if (btx.isTransactionCompleted()) {
        // Find the next location to store the tracking number.
        setTrackingNumber(getTrackingNumberCount(), btx.getNewTrackingNumber());
        setNewTrackingNumber(null);
      }
    }
  }

  /**
   * @return
   */
  public String getNewTrackingNumber() {
    return _newTrackingNumber;
  }

  /**
   * @param string
   */
  public void setNewTrackingNumber(String string) {
    _newTrackingNumber = string;
  }

  /**
   * @return
   */
  public HashMap getTrackingNumbers() {
    return _trackingNumbers;
  }

  /**
   * @param map
   */
  public void setTrackingNumbers(HashMap map) {
    _trackingNumbers = map;
  }
  
  public int getTrackingNumberCount() {
    return _trackingNumbers.size();
  }

  /**
   * @return
   */
  public String getTrackingNumber(int index) {
    return (String)_trackingNumbers.get(new Integer(index));
  }

  /**
   * 
   * @param index
   * @param form
   */
  public void setTrackingNumber(int index, String trackingNumber) {
    _trackingNumbers.put(new Integer(index), trackingNumber);
  }
}
