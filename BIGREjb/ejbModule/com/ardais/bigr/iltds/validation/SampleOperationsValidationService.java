package com.ardais.bigr.iltds.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.ValidatorFactory;

/**
 * The validation service for sample operations.  To use this service, set the SecurityInfo
 * and either a list of samples to be validated or a single sample, call one or more 
 * setCheck* methods to indicate which validations are to be performed, and then call the validate 
 * method to perform validation. 
 */
public class SampleOperationsValidationService extends AbstractValidationService {

  /**
   * A list of samples to be validated.
   */
  private List _samples;

  /**
   * A flag that indicates validation for creating an imported sample should be run.
   */
  private boolean _checkCreateImportedSample;

  /**
   * A flag that indicates validation for modifying an imported sample should be run.
   */
  private boolean _checkModifyImportedSample;

  /**
   * A flag that indicates validation for modifying a non-imported sample should be run.
   */
  private boolean _checkModifyNonImportedSample;

  /**
   * A flag that indicates validation is happening due to a derivative operation.
   */
  private boolean _derivativeOperationAction = false;

  /**
   * Creates a new SampleOperationsValidationService.
   */
  public SampleOperationsValidationService() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidationService#validate()
   */
  public BtxActionErrors validate() {

    boolean didValidation = false;
    SecurityInfo securityInfo = getSecurityInfo();
    BtxActionErrors errors = getActionErrors();
    Iterator samples = getSamples().iterator();
    while (samples.hasNext()) {
      SampleData sample = (SampleData) samples.next();
      ValidatorFactory factory = new SampleOperationsValidatorFactory(getSecurityInfo(), sample, isDerivativeOperationAction());

      if (isCheckCreateImportedSample()) {
        didValidation = true;
        errors.addErrors(
          factory.getInstance(SampleOperationsValidatorFactory.CREATE_IMPORTED_SAMPLE).validate());
      }
      if (isCheckModifyImportedSample()) {
        didValidation = true;
        errors.addErrors(
          factory.getInstance(SampleOperationsValidatorFactory.MODIFY_IMPORTED_SAMPLE).validate());
      }
      if (isCheckModifyNonImportedSample()) {
        didValidation = true;
        errors.addErrors(
          factory.getInstance(SampleOperationsValidatorFactory.MODIFY_NONIMPORTED_SAMPLE).validate());
      }
    }

    // If we did not do any validations, then throw an exception. 
    if (!didValidation) {
      throw new ApiException("SampleOperationsValidationService: No validation checks requested.");
    }

    return getActionErrors();
  }

  private List getSamples() {
    if (_samples == null) {
      _samples = new ArrayList();
    }
    return _samples;
  }

  /**
   * Sets the sample to be validated.
   * 
   * @param  sample  the {@link com.ardais.bigr.javabeans.SampleData SampleData} instance 
   */
  public void setSample(SampleData sample) {
    getSamples().add(sample);
  }

  /**
   * Sets the list of samples to be validated.
   * 
   * @param  samples  a list of samples, each of which is a 
   * {@link com.ardais.bigr.javabeans.SampleData SampleData} instance. 
   */
  public void setSamples(List samples) {
    if (samples == null) {
      throw new ApiException("SampleOperationsValidationService: No list of samples provided.");
    }
    _samples = samples;
  }

  private boolean isCheckCreateImportedSample() {
    return _checkCreateImportedSample;
  }

  private boolean isCheckModifyImportedSample() {
    return _checkModifyImportedSample;
  }

  private boolean isCheckModifyNonImportedSample() {
    return _checkModifyNonImportedSample;
  }

  public void setCheckCreateImportedSample(boolean b) {
    _checkCreateImportedSample = b;
  }

  public void setCheckModifyImportedSample(boolean b) {
    _checkModifyImportedSample = b;
  }

  public void setCheckModifyNonImportedSample(boolean b) {
    _checkModifyNonImportedSample = b;
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
