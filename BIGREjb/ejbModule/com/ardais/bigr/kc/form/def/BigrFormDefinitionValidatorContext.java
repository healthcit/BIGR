package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.validation.ValidatorContext;
import com.gulfstreambio.kc.form.def.FormDefinition;

/**
 * A <code>ValidatorContext</code> for use with the validators that perform BIGR-specific 
 * validation on form definitions.
 */
public class BigrFormDefinitionValidatorContext implements ValidatorContext {

  private BigrFormDefinition _formDef;
  private BigrFormDefinition _persistedFormDef;
  private FormDefinition _kcFormDef;
  private String _account;
  private SecurityInfo _securityInfo;
  private BigrFormDefinitionQueryCriteria _queryCriteria;

  public BigrFormDefinitionValidatorContext() {
    super();
  }

  public BigrFormDefinition getFormDefinition() {
    return _formDef;
  }

  public FormDefinition getKcFormDefinition() {
    return _kcFormDef;
  }

  public BigrFormDefinition getPersistedFormDefinition() {
    return _persistedFormDef;
  }

  public void setFormDefinition(BigrFormDefinition definition) {
    _formDef = definition;
  }

  public void setKcFormDefinition(FormDefinition definition) {
    _kcFormDef = definition;
  }

  public void setPersistedFormDefinition(BigrFormDefinition definition) {
    _persistedFormDef = definition;
  }

  public SecurityInfo getSecurityInfo() {
    return _securityInfo;
  }
  public void setSecurityInfo(SecurityInfo securityInfo) {
    _securityInfo = securityInfo;
  }
  public String getAccount() {
    return _account;
  }
  public void setAccount(String account) {
    _account = account;
  }
  public BigrFormDefinitionQueryCriteria getQueryCriteria() {
    return _queryCriteria;
  }
  public void setQueryCriteria(BigrFormDefinitionQueryCriteria queryCriteria) {
    _queryCriteria = queryCriteria;
  }
}
