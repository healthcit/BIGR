package com.gulfstreambio.gboss;

import com.gulfstreambio.kc.det.DetElementDatatypes;

public class GbossAdeElementReport extends GbossAdeElement {
  
  /**
   * Create a new GbossAdeElementReport object.
   */
  public GbossAdeElementReport() {
    super();
  }

  public String getDatatype() {
    return DetElementDatatypes.REPORT; 
   }
  
}
