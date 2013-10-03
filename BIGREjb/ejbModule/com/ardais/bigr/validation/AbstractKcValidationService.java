package com.ardais.bigr.validation;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * An abstract implementation of the <code>Validator</code> interface for validators that are
 * classified as kc validation services.  This provides some commmon functionality that can be 
 * subclassed by validation services.   
 */
public abstract class AbstractKcValidationService implements Validator {
  
  private BtxActionErrors _actionErrors;
  
  public AbstractKcValidationService() {
    super();
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
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidationService#validate()
   */
  public abstract BtxActionErrors validate();

}
