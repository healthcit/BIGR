package com.gulfstreambio.bigr.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds a collection of BIGR validation errors (@{link BigrValidationError})s.  Duplicate 
 * <code>BigrValidationError</code>s cannot be added to this collection. 
 */
public class BigrValidationErrors implements Serializable {

  private List _errors;
  
  public BigrValidationErrors() {
    _errors = new ArrayList();
  }

  /**
   * Adds a error to the collection of errors.  This method won't add the <code>BigrValidationError</code> 
   * to the collection if it is already contained in the collection.  Note that this is determined
   * through {@link BigrValidationError#equals(Object)}, so an error with the same key but different
   * replacement values can be added.
   *
   * @param error the error to add
   */
  public void add(BigrValidationError error) {
    if (!_errors.contains(error)) {
      _errors.add(error);    
    }
  }

  /**
   * Adds all of the errors from the specified <code>BigrValidationErrors</code> collection to this 
   * <code>BigrValidationErrors</code> collection.
   * 
   * @param errors the <code>BigrValidationErrors</code> to add.  If this is null or empty, then
   *               no action is taken.
   */
  public void addErrors(BigrValidationErrors errors) {
    if (errors != null) {
      _errors.addAll(errors._errors);
    }
  }

  /**
   * Return the the collection of <code>BigrValidationError</code>s.  
   * 
   * @return The collection of errors.  If there are no errors in this collection, an empty array 
   *         is returned.
   */
  public BigrValidationError[] getErrors() {
    return (BigrValidationError[]) _errors.toArray(new BigrValidationError[0]);
  }
}
