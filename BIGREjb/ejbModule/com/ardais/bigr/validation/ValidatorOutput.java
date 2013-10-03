package com.ardais.bigr.validation;

/**
 * Represents a validator output that is supplied to some {@link ValidatorContext}.  A validator
 * output consists of the name of the validator property to get, as specified in  
 * {@link #setValidatorProperty(java.lang.String) setValidatorProperty} and the name of the
 * context property to set as specified in
 * {@link #setContextProperty(java.lang.String) setContextProperty}.  The property names can be
 * specified using the full nested, indexed, mapped property syntax as supported by the
 * <code>org.apache.commons.beanutils.PropertyUtils</code> class of the Jakarta Commons BeanUtils
 * utilities.  
 */
public class ValidatorOutput {

  private String _validatorProperty;
  private String _contextProperty;  

  /**
   * Creates a new <code>ValidatorOutput</code> with the specified validator property and context
   * property. 
   * 
   * @param  validatorProperty the validator property 
   * @param  contextProperty  the context property
   */
  public ValidatorOutput(String validatorProperty, String contextProperty) {
    super();
    setValidatorProperty(validatorProperty);
    setContextProperty(contextProperty);
  }

  /**
   * Returns the context property.
   * 
   * @return  The context property.
   */
  public String getContextProperty() {
    return _contextProperty;
  }

  /**
   * Sets the context property.
   * 
   * @param  property  the context property
   */
  public void setContextProperty(String property) {
    _contextProperty = property;
  }

  /**
   * Returns the validator property.
   * 
   * @return  The validator property.
   */
  public String getValidatorProperty() {
    return _validatorProperty;
  }

  /**
   * Sets the validator property.
   * 
   * @param  property  the validator property
   */
  public void setValidatorProperty(String property) {
    _validatorProperty = property;
  }
}
