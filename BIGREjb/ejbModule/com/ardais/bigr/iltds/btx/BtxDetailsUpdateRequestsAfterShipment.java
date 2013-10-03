package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;

public class BtxDetailsUpdateRequestsAfterShipment extends BtxDetailsShippingOperation {
  static final long serialVersionUID = 1259690903689353154L;

  public BtxDetailsUpdateRequestsAfterShipment() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BtxDetailsUpdateRequestsAfterBoxShipment.doGetDetailsAsHTML() method called in error!";
    return msg;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTX_TYPE_UPDATE_REQUESTS_AFTER_SHIPMENT;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    return set;
  }

}
