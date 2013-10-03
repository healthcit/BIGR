package com.ardais.bigr.id.btx;

import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.gulfstreambio.bigr.error.BigrValidationErrors;

public class BtxDetailsGenerateSampleId extends BTXDetails {

  private String _sampleId;
  private BigrValidationErrors _validationErrors;
  
  public BtxDetailsGenerateSampleId() {
    super();
  }

  public String getSampleId() {
    return _sampleId;
  }

  public void setSampleId(String sampleId) {
    _sampleId = sampleId;
  }

  public BigrValidationErrors getValidationErrors() {
    return _validationErrors;
  }

  public void setValidationErrors(BigrValidationErrors errors) {
    _validationErrors = errors;
  }
  
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GENERATE_SAMPLE_ID;
  }

  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  protected String doGetDetailsAsHTML() {
    throw new ApiException("doGetDetailsAsHTML should not have been called, this class does not support transaction logging.");
  }

}
