package com.ardais.bigr.validation;

/**
 * Indicates that the implementing class is a factory that creates Validator instances.
 */
public interface ValidatorFactory {

  /**
   * Returns a validator instance.
   * 
   * @param  identifier  a String indicating the type of Validator to be returned
   * @return  The Validator.
   */
  public Validator getInstance(String identifier);
}
