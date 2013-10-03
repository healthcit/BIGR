package com.ardais.bigr.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.security.SecurityInfo;

/**
 * An abstract implementation of the {@link Validator} interface that can be used as a base class 
 * for concrete validators.  Provides functionality to add error messages, maintain a collection 
 * of <code>BtxActionError</code>s and register and notify {@link ValidatorErrorListener} 
 * listeners.
 */
public abstract class AbstractValidator implements Validator {
  
  private BtxActionErrors _actionErrors;
  private String _propertyDisplayName;  
  private SecurityInfo _securityInfo;
  private String _name;

  /**
   * The listeners.  Each entry is a List of ValidatorErrorListener instances and each key is
   * an error key of an error that is detected by this validator.
   */
  private Map _listeners;
  
  /**
   * The error keys of the errors that are detected by this validator.
   */
  private Set _errorKeys;

  /**
   * Creates a new <code>AbstractValidator</code>.
   */
  public AbstractValidator() {
    super();
  }

  /**
   * Creates a new <code>AbstractValidator</code> with the specified name.
   * 
   * @param  name  the name
   */
  public AbstractValidator(String name) {
    this();
    setName(name);
  }

  /**
   * Returns the collection of errors for this validator.  If there are no errors, then an
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
  
  /**
   * Adds the specified <code>BtxActionError</code> to the collection of errors in this validator.
   * 
   * @param error the <code>BtxActionError</code>
   */
  private void addError(BtxActionError error) {
    getActionErrors().add(getPropertyName(), error);
  }

  /**
   * Adds a <code>BtxActionError</code> with the specified error message and no insertion strings
   * to the collection of errors in this validator.
   * 
   * @param errorMessageKey the error message key
   */
  public void addErrorMessage(String errorMessageKey) {
    addError(new BtxActionError(errorMessageKey));
  }

  /**
   * Adds a <code>BtxActionError</code> with the specified error message and one insertion string
   * to the collection of errors in this validator.
   * 
   * @param errorMessageKey the error message key
   * @param iString0 the insertion string
   */
  public void addErrorMessage(String errorMessageKey, String iString0) {
    addError(new BtxActionError(errorMessageKey, iString0));
  }

  /**
   * Adds a <code>BtxActionError</code> with the specified error message and two insertion strings
   * to the collection of errors in this validator.
   * 
   * @param errorMessageKey the error message key
   * @param iString0 the first insertion string
   * @param iString1 the second insertion string
   */
  public void addErrorMessage(String errorMessageKey, String iString0, String iString1) {
    addError(new BtxActionError(errorMessageKey, iString0, iString1));
  }

  /**
   * Adds a <code>BtxActionError</code> with the specified error message and three insertion 
   * strings to the collection of errors in this validator.
   * 
   * @param errorMessageKey the error message key
   * @param iString0 the first insertion string
   * @param iString1 the second insertion string
   * @param iString2 the third insertion string
   */
  public void addErrorMessage(
    String errorMessageKey,
    String iString0,
    String iString1,
    String iString2) {
    addError(new BtxActionError(errorMessageKey, iString0, iString1, iString2));
  }

  /**
   * Adds a <code>BtxActionError</code> with the specified error message and four insertion 
   * strings to the collection of errors in this validator.
   * 
   * @param errorMessageKey the error message key
   * @param iString0 the first insertion string
   * @param iString1 the second insertion string
   * @param iString2 the third insertion string
   * @param iString3 the fourth insertion string
   */
  public void addErrorMessage(
    String errorMessageKey,
    String iString0,
    String iString1,
    String iString2,
    String iString3) {
    addError(new BtxActionError(errorMessageKey, iString0, iString1, iString2, iString3));
  }


  public String getName() {
    return _name;
  }
  
  /**
   * Assigns a name to this validator.
   * 
   * @param name  the name
   */
  public void setName(String name) {
    _name = name;
  }
  
  /**
   * Returns the property with which the error message(s) should be associated.  Defaults to 
   * BtxActionErrors.GLOBAL_ERROR.
   * 
   * @return The property.
   */
  protected String getPropertyName() {
    return BtxActionErrors.GLOBAL_ERROR;
  }

  /**
   * Returns the property display name.
   * 
   * @return  The property display name.
   */
  public String getPropertyDisplayName() {
    if (ApiFunctions.isEmpty(_propertyDisplayName)) {
      throw new ApiException("AbstractValidator: No displayName specified.");
    }
    return _propertyDisplayName;
  }

  /**
   * Sets the display name of the property being validated.  This display name is commonly used 
   * as an insertion string in error messages returned by this validator.
   * 
   * @param displayName the property display name
   */
  public void setPropertyDisplayName(String displayName) {
    _propertyDisplayName = displayName;
  }

  /**
   * Returns the <code>SecurityInfo</code> of the user performing the transaction that requires 
   * this validation.
   * 
   * @return  The <code>SecurityInfo</code>. 
   */
  protected SecurityInfo getSecurityInfo() {
    if (_securityInfo == null) {
      throw new ApiException("AbstractValidator: No securityInfo specified.");
    }
    return _securityInfo;
  }

  /**
   * Sets the <code>SecurityInfo</code> of the user performing the transaction that requires 
   * this validation.  
   * 
   * @param  info  the <code>SecurityInfo</code> object
   */
  public void setSecurityInfo(SecurityInfo info) {
    _securityInfo = info;
  }
  
  /**
   * Returns the Set of error keys, creating the Set if necessary.
   * 
   * @return  The error keys Set.
   */
  private Set getErrorKeys() {
    if (_errorKeys == null) {
      _errorKeys = new HashSet();
    }
    return _errorKeys;
  }

  /**
   * Adds the error key as one of the possible error keys that can be returned by this validator.
   * This is only intended to be used by subclasses to register all of the default error keys,
   * typically in their constructors.  An error key must be registered via this method before
   * registering a listener for the error key via the      
   * {@link #addValidatorErrorListener(ValidatorErrorListener, java.lang.String) addValidatorErrorListener}
   * method. 
   * 
   * @param  errorKey  the error key
   */
  protected void addErrorKey(String errorKey) {
    getErrorKeys().add(errorKey);
  }

  /**
   * Registers a listener for the specified error key of this validator.
   * 
   * @param  listener  the <code>ValidatorErrorListener</code>
   * @param  errorKey  the error key with which the listener is associated
   * @throws ApiException if the specified error key is not recognized by this validator or an
   *                      attempt is made to register more than 2 listeners for the same error key.
   */
  public void addValidatorErrorListener(ValidatorErrorListener listener, String errorKey) {
    // Make sure the error key was registered by the validator.
    if (!getErrorKeys().contains(errorKey)) { 
      throw new ApiException("In AbstractValidator.addValidatorErrorListener: attempt to register a listeners for an error key (" + errorKey + ") that was not registered by the validator.");
    }

    // If this is the first listener that has been added, then create the map of the lists of 
    // listeners per error key. 
    if (_listeners == null) {
      _listeners = new HashMap();
    }

    // If this is the first listener that has been added for this error key, then create the 
    // list of listeners for this error key. 
    List listenersForError = (List) _listeners.get(errorKey);
    if (listenersForError == null) {
      listenersForError = new ArrayList();      
      _listeners.put(errorKey, listenersForError);
    }

    // For now, do not allow more than 2 listeners to be registered for an error key.
    // It is generally assumed that the first registered listener will be that of the validator
    // itself, and the second will be from a caller of the validator, if the caller chooses to
    // register a listener.
    if (listenersForError.size() > 1) { 
      throw new ApiException("In AbstractValidator.addValidatorErrorListener: attempt to register more than 2 listeners for error key " + errorKey);
    }

    // Add the new listener as a listener for the specified error key.
    listenersForError.add(listener);
  }
  
  /**
   * Notifies all registered listeners for the specified error key that the corresponding error
   * has occurred.  This should be called by subclasses when the error condition corresponding \
   * to the error key is detected.
   * 
   * @param  errorKey  the error key of the error that has occurred
   * @throws ApiException if no listeners are registered for the specified error key, or none
   *                      of the registered listeners handles the error.
   */
  protected void notifyValidatorErrorListener(String errorKey) {
    if (_listeners == null) {
      throw new ApiException("In AbstractValidator.notifyValidatorErrorListener: no error listeners defined.");
    }

    List listenersForError = (List) _listeners.get(errorKey);
    if (listenersForError == null) {
      throw new ApiException("In AbstractValidator.notifyValidatorErrorListener: no error listeners defined for error key " + errorKey);
    }

    // Loop through the listeners in the reverse order in which they were added, and call
    // them only until one of the listeners indicates that it handled the error.  If no
    // listener handles the error, then throw an exception.   
    boolean handled = false;
    for (int i = listenersForError.size() - 1; i >= 0 && !handled; i--) {
      ValidatorErrorListener listener = (ValidatorErrorListener) listenersForError.get(i);
      handled = listener.validatorError(this, errorKey);
    }
    if (!handled) {
      throw new ApiException("In AbstractValidator.notifyValidatorErrorListener: no error listener handled error with error key " + errorKey);
    }
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.Validator#validate()
   */
  public abstract BtxActionErrors validate();

}
