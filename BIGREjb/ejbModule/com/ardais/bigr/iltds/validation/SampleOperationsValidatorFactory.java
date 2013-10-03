package com.ardais.bigr.iltds.validation;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.validation.AbstractValidatorFactory;
import com.ardais.bigr.validation.Validator;

/**
 * Factory that creates and returns Validator instances for sample operations.  This factory is
 * intended only to serve the {@link SampleOperationsValidationService} class.
 */
class SampleOperationsValidatorFactory extends AbstractValidatorFactory {

  public static final String CREATE_IMPORTED_SAMPLE = "createImportedSample";
  public static final String MODIFY_IMPORTED_SAMPLE = "modifyImportedSample";
  public static final String MODIFY_NONIMPORTED_SAMPLE = "modifyNonImportedSample";

  private SampleData _sample;

  /**
   * A flag that indicates validation is happening due to a derivative operation.
   */
  private boolean _derivativeOperationAction = false;

  /**
   * Creates a new DerivativeOperationsValidatorFactory.
   * 
   * @param securityInfo  the SecurityInfo
   * @param sample a SampleData that provides the context for creating the requested validator
   */
  public SampleOperationsValidatorFactory(SecurityInfo securityInfo, SampleData sample) {
    this(securityInfo, sample, false);
  }

  /**
   * Creates a new DerivativeOperationsValidatorFactory.
   * 
   * @param securityInfo  the SecurityInfo
   * @param sample a SampleData that provides the context for creating the requested validator
   */
  public SampleOperationsValidatorFactory(SecurityInfo securityInfo, SampleData sample, boolean derivativeOperationAction) {
    super(securityInfo);
    setSample(sample);
    setDerivativeOperationAction(derivativeOperationAction);
  }

  /**
   * Returns a Validator instance based on the specified identifier.  
   * 
   * @param  identifier  one of the constants defined in this class  
   */
  public Validator getInstance(String identifier) {
    if (CREATE_IMPORTED_SAMPLE.equalsIgnoreCase(identifier)) {
      return createCreateImportedSampleValidator();
    }
    else if (MODIFY_IMPORTED_SAMPLE.equalsIgnoreCase(identifier)) {
      return createModifyImportedSampleValidator();
    }
    else if (MODIFY_NONIMPORTED_SAMPLE.equalsIgnoreCase(identifier)) {
      return createModifyNonImportedSampleValidator();
    }
    else {
      throw new ApiException(
        "SampleOperationsValidatorFactory: Unrecognized Validator identifier ("
          + identifier
          + ").");
    }
  }

  private Validator createCreateImportedSampleValidator() {

    ValidatorSampleOperationCreateImportedSample v =
      new ValidatorSampleOperationCreateImportedSample();
    v.setSecurityInfo(getSecurityInfo());
    v.setPropertyDisplayName("New sample");
    v.setSample(getSample());
    v.setDerivativeOperationAction(isDerivativeOperationAction());
    
    return v;
  }

  private Validator createModifyImportedSampleValidator() {

    ValidatorSampleOperationModifyImportedSample v =
      new ValidatorSampleOperationModifyImportedSample();
    v.setSecurityInfo(getSecurityInfo());
    v.setPropertyDisplayName("Existing sample");
    v.setSample(getSample());
    v.setDerivativeOperationAction(isDerivativeOperationAction());

    return v;
  }

  private Validator createModifyNonImportedSampleValidator() {

    ValidatorSampleOperationModifyNonImportedSample v =
      new ValidatorSampleOperationModifyNonImportedSample();
    v.setSecurityInfo(getSecurityInfo());
    v.setPropertyDisplayName("Existing sample");
    v.setSample(getSample());
    v.setDerivativeOperationAction(isDerivativeOperationAction());

    return v;
  }

  private SampleData getSample() {
    return _sample;
  }

  private void setSample(SampleData sample) {
    if (sample == null) {
      throw new ApiException("SampleOperationsValidatorFactory: No sample data specified.");
    }
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
