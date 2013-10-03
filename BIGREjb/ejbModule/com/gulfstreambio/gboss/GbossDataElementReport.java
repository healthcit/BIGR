package com.gulfstreambio.gboss;

import com.gulfstreambio.kc.det.DetElementDatatypes;

public class GbossDataElementReport extends GbossDataElement {
  
  /**
   * Create a new GbossDataElementReport object.
   */
  public GbossDataElementReport() {
    super();
  }
  
  public String getDatatype() {
    return DetElementDatatypes.REPORT; 
   }
}
