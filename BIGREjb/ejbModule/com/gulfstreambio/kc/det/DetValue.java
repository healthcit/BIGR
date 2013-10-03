package com.gulfstreambio.kc.det;

import java.io.Serializable;

import com.ardais.bigr.api.ApiException;
import com.gulfstreambio.gboss.GbossValue;

public class DetValue implements DetConcept, Serializable{

  private String _cui;
  private String _description;
  private boolean _noValue = false;
  private boolean _otherValue = false;

  DetValue(GbossValue value) {
    super();

    _cui = value.getCui();
    _description = value.getDescription();
    _noValue = value.isNoValue();
    _otherValue = value.isOtherValue();
  }

  public String getCui() {
    return _cui;
  }
  
  public String getDescription() {
    return _description;
  }
  
  public boolean isNoValue() {
    return _noValue;
  }
  
  public boolean isOtherValue() {
    return _otherValue;
  }
  
}
