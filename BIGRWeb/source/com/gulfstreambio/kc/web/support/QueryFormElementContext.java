package com.gulfstreambio.kc.web.support;

import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue;

public interface QueryFormElementContext {

  public String getCui();
  public String getSystemName();
  public boolean isDatatypeDate();
  public boolean isDatatypeInt();
  public boolean isDatatypeFloat();
  public boolean isDatatypeReport();
  public boolean isDatatypeVpd();
  public boolean isDatatypeCv();
  public boolean isMlvs();
  public boolean isHasAde();
  public String getUnitCui();
  public String getUnitDescription();
  
  public String getDisplayName();
  public String getDisplayNameWithUnits();
  public String getSummary();

  public boolean isRenderAsDiscrete();
  public boolean isRenderAsRange();  

  public String getPropertyAny();
  public String getPropertyLogicalOperator();
  public String getPropertyComparisonOperator1();
  public String getPropertyComparisonOperator2();
  public String getPropertyValue();
  public String getPropertyValue1();
  public String getPropertyValue2();  
  public String getPropertyButtonAddAde();
  public String getPropertyButtonClear();
  public String getPropertyAdeSummary();
  public String getJavascriptReference();
  public String getJavascriptReferenceQueryElements();
  
  public DetValueSet getValueSetBroad();
  public QueryFormDefinitionRollupValueSet getValueSetRollup();
  
  public boolean isValueAny();
  public String getValueLogicalOperator();
  public String getValueComparisonOperator1();
  public String getValueComparisonOperator2();
  public String getValue1();
  public String getValue2();  
  public QueryFormDefinitionValue[] getValues();
}
