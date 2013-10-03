package com.gulfstreambio.kc.web.support;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueTypes;

public class QueryFormDataElementContext extends QueryFormElementContextBase {

  private QueryFormDefinitionDataElement _formDefinitionDataElement;
  private QueryFormDefinitionValueSet _valueSet; 
  private DetDataElement _detDataElement;
  
  public QueryFormDataElementContext(QueryFormDefinitionDataElement element) {
    super();
    _formDefinitionDataElement = element;
    _valueSet = element.getValueSet();
  }

  QueryFormDefinitionDataElement getQueryFormDefinitionDataElement() {
    return _formDefinitionDataElement;
  }

  protected QueryFormDefinitionValueSet getValueSet() {
    return _valueSet;
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContextBase#getDetElement()
   */
  protected DetElement getDetElement() {
    return getDetDataElement();
  }

  private DetDataElement getDetDataElement() {
    if (_detDataElement == null) {
      QueryFormDefinitionDataElement element = getQueryFormDefinitionDataElement();
      _detDataElement = 
        DetService.SINGLETON.getDataElementTaxonomy().getDataElement(element.getCui());
    }
    return _detDataElement;
  }
  
  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#isHasAde()
   */
  public boolean isHasAde() {
    DetDataElement element = getDetDataElement();
    return (element == null) ? false : element.isHasAde();
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.web.support.QueryFormElementContext#getDisplayName()
   */
  public String getDisplayName() {
    String displayName = "";
    QueryFormDefinitionDataElement element = getQueryFormDefinitionDataElement();
    DetDataElement detElement = getDetDataElement();

    // Get the display name from the query element definition if it was specified there.
    if ((element != null) && !ApiFunctions.isEmpty(element.getDisplayName())) {
      displayName = element.getDisplayName();
    }
    
    // Otherwise get the display name from the data element metadata, which is its description
    // in the DET.
    else if (detElement != null) {
      displayName = detElement.getDescription();
    }
    
    return displayName;
  }

  public boolean isRenderAsDiscrete() {
    QueryFormDefinitionDataElement dataElement = getQueryFormDefinitionDataElement();
    return (dataElement.getRollupValueSet() != null) || isDatatypeCv();
  }
  
  public boolean isRenderAsRange() {
    QueryFormDefinitionDataElement dataElement = getQueryFormDefinitionDataElement();
    if (dataElement.getRollupValueSet() != null) {
      return false;
    }
    else {
      return isDatatypeDate() || isDatatypeFloat() || isDatatypeInt() || isDatatypeVpd();      
    }
  }

  public QueryFormDefinitionRollupValueSet getValueSetRollup() {
    QueryFormDefinitionDataElement dataElement = getQueryFormDefinitionDataElement();
    return dataElement.getRollupValueSet();
  }
}
