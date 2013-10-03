package com.ardais.bigr.kc.form;

import com.ardais.bigr.iltds.btx.BTXDetails;

public class BtxDetailsKcFormInstanceDomainObjectSummary extends BtxDetailsKcFormInstance {

  public BtxDetailsKcFormInstanceDomainObjectSummary() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_KC_FORM_DOMAIN_OBJECT_SUMMARY;
  }

}
