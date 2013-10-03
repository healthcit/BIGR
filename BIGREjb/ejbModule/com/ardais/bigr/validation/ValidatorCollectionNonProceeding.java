package com.ardais.bigr.validation;


/**
 * Implementation of a non-proceeding validator collection.  A <i>non-proceeding</i> validator 
 * collection is one in which validators are executed until an error is found, and then no
 * subsequent validators are executed and the error is returned. 
 */
public class ValidatorCollectionNonProceeding extends ValidatorCollectionProceeding {

  /**
   * Creates a new <code>ValidatorCollectionNonProceeding</code>.
   */
  public ValidatorCollectionNonProceeding() {
    super();
  }

  /**
   * Creates a new <code>ValidatorCollectionNonProceeding</code> with the specified name.
   * 
   * @param  name  the name
   */
  public ValidatorCollectionNonProceeding(String name) {
    super(name);
  }

  /**
   * Always returns <code>false</code> since that is the definition of a non-proceeding validator
   * collection.
   *  
   * @return  <code>false</code>
   * @see ValidatorCollectionProceeding#isProceedOnError() 
   */
  protected boolean isProceedOnError() {
    return false;
  }

}
