package com.ardais.bigr.kc.form;

import com.ardais.bigr.iltds.btx.BTXDetails;

public class BtxDetailsKcFormInstanceSummary extends BtxDetailsKcFormInstance {

  public BtxDetailsKcFormInstanceSummary() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_KC_FORM_SUMMARY;
  }

}
