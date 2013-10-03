package com.ardais.bigr.kc.form.def;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 */
public class BtxDetailsKcFormDefinitionCreate extends BtxDetailsKcFormDefinition {

  /**
   * Creates a new <code>BtxDetailsKcFormDefinitionCreate</code>.
   */
  public BtxDetailsKcFormDefinitionCreate() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_KC_FORM_DEF_CREATE;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    BigrFormDefinition def = getFormDefinition();
    Set set = new HashSet();
    set.add(def.getFormDefinitionId());
    set.add(def.getAccount());
    return set;
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */  
  protected String doGetDetailsAsHTML() {
    StringBuffer sb = new StringBuffer();
    sb.append("Created ");
    sb.append(super.doGetDetailsAsHTML()); 
    return sb.toString();
  }
 
}
