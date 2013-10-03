package com.ardais.bigr.orm.databeans;

import java.util.List;

public class IrbProtocolDatabean implements java.io.Serializable {
  private int _irbId = 0;
  private String _irbName = null;
  private List _consentCollection = null;
  private String _policyId = null;
  private String _policyName = null;

  public IrbProtocolDatabean() {
    super();
  }

  public List getConsentCollection() {
    return _consentCollection;
  }

  public int getIrbId() {
    return _irbId;
  }

  public java.lang.String getIrbName() {
    return _irbName;
  }

  public void setConsentCollection(List newConsentCollection) {
    _consentCollection = newConsentCollection;
  }

  public void setIrbId(int newIrbId) {
    _irbId = newIrbId;
  }

  public void setIrbName(String newIrbName) {
    _irbName = newIrbName;
  }

  public String getPolicyId() {
    return _policyId;
  }

  public void setPolicyId(String string) {
    _policyId = string;
  }

  public String getPolicyName() {
    return _policyName;
  }

  public void setPolicyName(String string) {
    _policyName = string;
  }

}
