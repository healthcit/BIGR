package com.ardais.bigr.iltds.validation;

import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateImportedSample;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.validation.AbstractValidator;

/**
 * Validates a sample that is to be created as an imported sample.
 */
public class ValidatorSampleOperationCreateImportedSample extends AbstractValidator  {
  
  private SampleData _sample;
  private boolean _derivativeOperationAction;

  public ValidatorSampleOperationCreateImportedSample() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    BtxDetailsCreateImportedSample createSampleDetails = new BtxDetailsCreateImportedSample();
    //createSampleDetails.setBeginTimestamp(btxDetails.getBeginTimestamp());
    createSampleDetails.setLoggedInUserSecurityInfo(getSecurityInfo());
    createSampleDetails.setTransactionType("iltds_create_imported_sample");        
    createSampleDetails.setDerivativeOperationAction(isDerivativeOperationAction());
    createSampleDetails.setSampleData(getSample());
    //mark this transaction as validation only
    createSampleDetails.setValidationOnly(true);
    createSampleDetails = (BtxDetailsCreateImportedSample) Btx.perform(createSampleDetails);
    BtxActionErrors errors = createSampleDetails.getActionErrors();
    getActionErrors().addErrors(errors);
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
