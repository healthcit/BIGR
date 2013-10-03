package com.ardais.bigr.library.btx;

import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;


public class BTXDetailsGetKcQueryForm extends BTXDetails {

  private String _formDefinitionId;
  private QueryFormDefinition _formDefinition;
  
  public BTXDetailsGetKcQueryForm() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_KC_QUERY_FORM;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    return "BTXDetailsGetKcQueryForm.doGetDetailsAsHTML() method called in error!";
  }

  public String getFormDefinitionId() {
    return _formDefinitionId;
  }
  
  public void setFormDefinitionId(String formDefinitionId) {
    _formDefinitionId = formDefinitionId;
  }
  
  public QueryFormDefinition getFormDefinition() {
    return _formDefinition;
  }
  
  public void setFormDefinition(QueryFormDefinition formDefinition) {
    _formDefinition = formDefinition;
  }
}
