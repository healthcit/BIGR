package com.ardais.bigr.validation;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.security.SecurityInfo;

/**
 * An abstract implementation of the <code>Validator</code> interface for validators that are
 * classified as validation services.  This provides some commmon functionality that can be 
 * subclassed by validation services.   
 */
public abstract class AbstractValidationService implements Validator {
  
  private BtxActionErrors _actionErrors;
  private SecurityInfo _securityInfo;
  private String _name;

  public AbstractValidationService() {
    super();
  }

  public AbstractValidationService(String name) {
    this();
    setName(name);
  }

  public String getName() {
    return _name;
  }
  
  /**
   * Assigns a name to this validation service.
   * 
   * @param name  the name
   */
  public void setName(String name) {
    _name = name;
  }

  /**
   * Returns the collection of errors for this validation service.  If there are no errors, then an
   * empty BtxActionErrors is returned.
   * 
   * @return The BtxActionErrors collection.
   */
  protected BtxActionErrors getActionErrors() {
    if (_actionErrors == null) {
      _actionErrors = new BtxActionErrors();
    }
    return _actionErrors;
  }
  
  protected SecurityInfo getSecurityInfo() {
    return _securityInfo;
  }

  /**
   * Sets the SecurityInfo of the user performing the transaction to be validated.  
   * 
   * @param  info  the SecurityInfo 
   */
  public void setSecurityInfo(SecurityInfo info) {
    if (info == null) {
      throw new ApiException("AbstractValidationService: No SecurityInfo specified.");
    }
    _securityInfo = info;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidationService#validate()
   */
  public abstract BtxActionErrors validate();

}
