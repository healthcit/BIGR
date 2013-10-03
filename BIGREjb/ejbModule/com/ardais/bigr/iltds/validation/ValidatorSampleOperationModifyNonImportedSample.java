package com.ardais.bigr.iltds.validation;

import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxDetailsModifyImportedSample;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.validation.AbstractValidator;

/**
 * Validates a sample that is to be modified as a non-imported sample.
 */
public class ValidatorSampleOperationModifyNonImportedSample extends AbstractValidator  {
  
  private SampleData _sample;
  private boolean _derivativeOperationAction;

  public ValidatorSampleOperationModifyNonImportedSample() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    BtxDetailsModifyImportedSample sampleDetails = new BtxDetailsModifyImportedSample();
    sampleDetails.setLoggedInUserSecurityInfo(getSecurityInfo());
    sampleDetails.setTransactionType("iltds_modify_nonimported_sample");
    sampleDetails.setDerivativeOperationAction(isDerivativeOperationAction());
    sampleDetails.setSampleData(getSample());
    sampleDetails.setValidationOnly(true);
    sampleDetails = (BtxDetailsModifyImportedSample) Btx.perform(sampleDetails);
    getActionErrors().addErrors(sampleDetails.getActionErrors());
    return getActionErrors();
  }

  private SampleData getSample() {
    return _sample;
  }

  public void setSample(SampleData sample) {
    _sample = sample;
  }

  /**
   * @return
   */
  public boolean isDerivativeOperationAction() {
    return _derivativeOperationAction;
  }

  /**
   * @param b
   */
  public void setDerivativeOperationAction(boolean b) {
    _derivativeOperationAction = b;
  }

}
