package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * 
 */
public class BtxDetailsKcFormDefinitionLookup extends BtxDetailsKcFormDefinition {

  /**
   * Creates a new <code>BtxDetailsKcFormDefinitionLookup</code>.
   */
  public BtxDetailsKcFormDefinitionLookup() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_KC_FORM_DEF_LOOKUP;
  }

}
