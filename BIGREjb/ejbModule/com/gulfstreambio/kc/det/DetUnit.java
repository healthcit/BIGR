package com.gulfstreambio.kc.det;

import java.io.Serializable;

public class DetUnit implements DetConcept, Serializable {

  private String _cui;
  private String _description;
  
  DetUnit(String cui, String description) {
    super();
    _cui = cui;
    _description = description;
  }

  public String getCui() {
    return _cui;
  }
  
  public String getDescription() {
    return _description;
  }

}
