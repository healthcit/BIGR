package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 */
public class BtxDetailsKcFormDefinitionStart extends BtxDetailsKcFormDefinition {

  /**
   * Creates a new <code>BtxDetailsKcFormDefinitionStart</code>.
   */
  public BtxDetailsKcFormDefinitionStart() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_KC_FORM_DEF_START;
  }

}
