package com.gulfstreambio.gboss;

import com.gulfstreambio.kc.det.DetElementDatatypes;

public class GbossDataElementText extends GbossDataElement {
  
  /**
   * Create a new GbossDataElementText object.
   */
  public GbossDataElementText() {
    super();
  }
  
  public String getDatatype() {
    return DetElementDatatypes.TEXT; 
   }
}
