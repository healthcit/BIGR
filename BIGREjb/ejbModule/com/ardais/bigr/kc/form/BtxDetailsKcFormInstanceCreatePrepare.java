package com.ardais.bigr.kc.form;

import com.ardais.bigr.iltds.btx.BTXDetails;

public class BtxDetailsKcFormInstanceCreatePrepare extends BtxDetailsKcFormInstance {

  public BtxDetailsKcFormInstanceCreatePrepare() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_KC_FORM_CREATE_PREPARE;
  }

}
