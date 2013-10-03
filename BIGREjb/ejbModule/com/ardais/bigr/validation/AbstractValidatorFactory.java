package com.ardais.bigr.validation;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Abstract class from which concrete validator factory objects can be subclassed.
 */
public abstract class AbstractValidatorFactory implements ValidatorFactory {
  
  private SecurityInfo _securityInfo;

  public AbstractValidatorFactory() {
    super();
  }

  public AbstractValidatorFactory(SecurityInfo securityInfo) {
    this();
    setSecurityInfo(securityInfo);
  }

  protected SecurityInfo getSecurityInfo() {
    if (_securityInfo == null) {
      throw new ApiException("AbstractValidatorFactory: No SecurityInfo specified.");
    }
    return _securityInfo;
  }

  private void setSecurityInfo(SecurityInfo info) {
    _securityInfo = info;
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorFactory#getInstance(String identifier)
   */
  public abstract Validator getInstance(String identifier);
  
}
