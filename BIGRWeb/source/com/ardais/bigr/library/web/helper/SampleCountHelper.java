package com.ardais.bigr.library.web.helper;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.BigrValidator;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Holds counts of samples -- the total number and the number that are selected in the GUI.
 */
public class SampleCountHelper {

  private String _sampleType;
  private int _total;
  private int _selected;
  
  public SampleCountHelper(String sampleType) {
    if (BigrValidator.isValidSampleType(sampleType)) {
      _sampleType = sampleType;
    }
    else {
      throw new ApiException("In SampleCountHelper constructor: invalid sample type " + sampleType);
    }
  }

  /**
   * @return the sample type CUI
   */
  public String getSampleType() {
    return _sampleType;
  }

  /**
   * @return how to label this count information for the user.
   */
  public String getDisplayName() {
    return GbossFactory.getInstance().getDescription(getSampleType());
  }

  /**
   * @return the total number of samples
   */
  public int getTotal() {
    return _total;
  }

  public void setTotal(int total) {
    _total = total;
  }

  /**
   * @return the number of samples that have been selected in the GUI layer
   */
  public int getSelected() {
    return _selected;
  }
 
  public void setSelected(int selected) {
    _selected = selected;
  }

}
