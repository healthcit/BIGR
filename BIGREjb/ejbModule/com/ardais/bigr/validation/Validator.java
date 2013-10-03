package com.ardais.bigr.validation;

import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Indicates that the implementing class performs validation.  The {@link AbstractValidator}
 * class is an abstract implementation of <code>Validator</code> that provides some generally
 * useful functionality, and can be used as a base class for <code>Validator</code>s wishing
 * to implement this interface.  
 * <p>
 * Each <code>Validator</code> should have a default listener that handles all errors, where
 * handling an error generally means creating a 
 * {@link com.ardais.bigr.iltds.btx.BtxActionError BtxActionError} and adding it to the list
 * of errors returned by the validator.  The listener must implement the 
 * {@link ValidatorErrorListener} interface.  Each validator should also allow default 
 * error handling to be overriden by allowing callers to register a listener.  This can be
 * accomplished by using the 
 * {@link AbstractValidator#addValidatorErrorListener(ValidatorErrorListener, java.lang.String) addValidatorErrorListener}
 * method of <code>AbstractValidator</code> to register listeners, and  
 * {@link AbstractValidator#notifyValidatorErrorListener(java.lang.String) notifyValidatorErrorListener}
 * to notify registered listeners when an error is detected.
 * <p>
 * Each <code>Validator</code> should make all possible insertion strings available by public
 * method calls so that a listener can use them when creating a <code>BtxActionError</code>.
 * This is especially important for values and data structures that are populated as a result
 * of the validation itself, and that are not specified when creating the validator.  For example,
 * a validator that checks for duplicates should make the duplicate items available via a method 
 * call since a listener may want to include the duplicate items in the error message it creates.
 *
 */
public interface Validator {

  /**
   * Performs validation and returns any errors found during validation.
   * 
   * @return  The <code>BtxActionErrors</code> containing the errors found during validation.  
   *          Implementors should return an empty <code>BtxActionErrors</code> if there were no 
   *          validation errors.
   */
  public BtxActionErrors validate();
  
  /**
   * Returns the name of this <code>Validator</code>, which is simply an optional string that is 
   * assigned to the validator by its creator.  The name is useful when debugging validators, and 
   * has no bearing on the validation itself.
   * 
   * @return  The name of the <code>Validator</code>, or <code>null</code> if it was never 
   *          assigned a name.
   */
  public String getName();
}
