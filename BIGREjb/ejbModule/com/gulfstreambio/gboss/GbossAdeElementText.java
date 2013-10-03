package com.gulfstreambio.gboss;

import com.gulfstreambio.kc.det.DetElementDatatypes;

public class GbossAdeElementText extends GbossAdeElement {
  
  /**
   * Create a new GbossAdeElementText object.
   */
  public GbossAdeElementText() {
    super();
  }
  
  public String getDatatype() {
    return DetElementDatatypes.TEXT; 
   }  
}
