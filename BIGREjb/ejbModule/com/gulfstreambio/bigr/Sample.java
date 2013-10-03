package com.gulfstreambio.bigr;

import java.io.Serializable;

public class Sample implements Serializable {
  
  private String _sampleId;
  private String _sampleTypeCui;

  public Sample() {
  }
  
  /**
   * @return Returns the sampleId.
   */
  public String getSampleId() {
    return _sampleId;
  }
  
  /**
   * @return Returns the sampleTypeCui.
   */
  public String getSampleTypeCui() {
    return _sampleTypeCui;
  }
  
  /**
   * @param sampleId The sampleId to set.
   */
  public void setSampleId(String sampleId) {
    _sampleId = sampleId;
  }
  
  /**
   * @param sampleTypeCui The sampleTypeCui to set.
   */
  public void setSampleTypeCui(String sampleTypeCui) {
    _sampleTypeCui = sampleTypeCui;
  }
  
}
