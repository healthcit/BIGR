package com.ardais.bigr.validation;

/**
 * The listener interface for receiving notification of errors that are detected by validators
 * implementing the {@link Validator} interface.  When an error is detected, the 
 * <code>Validator</code> will notify all listeners by calling the 
 * {@link #validatorError(Validator, java.lang.String) validatorError} method.  A listener
 * will then generally create a <code>BtxActionError</code> and add it to the 
 * <code>Validator</code>, returning <code>true</code> to indicate that it handled the error.
 * A listener may also choose not to handle the error, in which case it should return
 * <code>false</code> from the <code>validatorError</code> method.
 * <p>
 * See the documentation for the <code>Validator</code> interface for more conventions
 * concerning listeners. 
 */
public interface ValidatorErrorListener {

  /**
   * Invoked when an error is detected in a validator.
   * 
   * @param  v  the <code>Validator</code> instance that detected the error  
   * @param  errorKey  the error key of the error that was detected
   * @return  <code>true</code> if this listener handled the error; <code>false</code> otherwise.
   */
  public boolean validatorError(Validator v, String errorKey);
}
