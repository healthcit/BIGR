package com.gulfstreambio.bigr.error;

import java.io.Serializable;

/**
 * A checked exception that holds one or more BIGR validation errors.  Callers that catch this
 * exception should call the {@link #getErrors()} method to get the list of validation errors.
 */
public class BigrValidationException extends Exception implements Serializable {

  private BigrValidationErrors _errors = null;
  
  public BigrValidationException(BigrValidationErrors errors) {
    super("One or more BIGR validation errors has occurred.  Call the getErrors() method of BigrValidationException to determine the exact validation errors.");
    _errors = errors;
  }
  
  /**
   * Return the errors.  If there are no errors recorded, an empty collection is returned.
   * 
   * @return The collection of BIGR errors.
   */
  public BigrValidationErrors getErrors() {
    if (_errors == null) {
      _errors = new BigrValidationErrors();
    }
    return _errors;
  }
}
