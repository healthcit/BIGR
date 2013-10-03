package com.gulfstreambio.kc.web.support;

import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionAdeElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueTypes;

public class QueryFormAdeElementContext extends QueryFormElementContextBase {

  private DetAdeElement _detAdeElement;
  private QueryFormDefinitionAdeElement _adeElement; 
  private QueryFormDefinitionValueSet _valueSet; 

  public QueryFormAdeElementContext(DetAdeElement detAdeElement) {
    super();
    _detAdeElement = detAdeElement;
  }

  public QueryFormAdeElementContext(QueryFormDataElementContext context, 
                                    DetAdeElement detAdeElement) {
    this(detAdeElement);
    _adeElement = context.getQueryFormDefinitionDataElement().getAdeElement(detAdeElement.getCui());
    if (_adeElement != null) {      
      _valueSet = _adeElement.getValueSet();
    }
  }

  protected DetElement getDetElement() {
    return getDetAdeElement() ;
  }

  private DetAdeElement getDetAdeElement() {
    return _detAdeElement;
  }
  
  private QueryFormDefinitionAdeElement getQueryFormDefinitionAdeElement() {
    return _adeElement;
  }
  
  protected QueryFormDefinitionValueSet getValueSet() {
    return _valueSet;
  }

  public boolean isHasAde() {
    return false;
  }

  public String getDisplayName() {
    return getDetElement().getDescription();
  }

  public boolean isRenderAsDiscrete() {
    return isDatatypeCv();
  }

  public boolean isRenderAsRange() {
    return isDatatypeDate() || isDatatypeFloat() || isDatatypeInt() || isDatatypeVpd();
  }

  public QueryFormDefinitionRollupValueSet getValueSetRollup() {
    return null;
  }
}
