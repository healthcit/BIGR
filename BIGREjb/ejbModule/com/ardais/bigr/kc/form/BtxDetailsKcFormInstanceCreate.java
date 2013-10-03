package com.ardais.bigr.kc.form;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;

public class BtxDetailsKcFormInstanceCreate extends BtxDetailsKcFormInstance {

  /**
   * Creates a new <code>BtxDetailsKcFormInstanceCreate</code>.
   */
  public BtxDetailsKcFormInstanceCreate() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_KC_FORM_CREATE;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    BigrFormInstance form = getFormInstance();
    Set set = new HashSet();
    if (!ApiFunctions.isEmpty(form.getFormInstanceId())) {
      set.add(form.getFormInstanceId());
    }
    if (!ApiFunctions.isEmpty(form.getDomainObjectId())) {
      set.add(form.getDomainObjectId());
    }
    return set;
  }

}
