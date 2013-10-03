package com.ardais.bigr.validation;

/**
 * An interface that contains constants to be used as input and output properties for validators,
 * that is, classes implementing the {@link Validator} interface.  <code>Validator</code>'s should
 * endeavor to use the values of these constants to name their properties to gain consistent
 * <code>Validator</code> interfaces.  If a property name being used in a <code>Validator</code>
 * is not specified here, it should be added.
 * <p>
 * One of the main reasons for using these constants is to gain consistency in validation services
 * that may wish to create <code>Validator</code>s that take inputs from and supply outputs to 
 * a {@link ValidatorContext}.  Such services should use these constants to specify the validator
 * property in {@link ValidatorInput}s and {@link ValidatorOutput}s.
 * <p>
 * To use this interface, a class can simply declare that it implements this interface and then
 * use the constants without having to qualify each constant with 
 * "ValidatorInputOutputProperties.".   
 */
public interface ValidatorInputOutputProperties {

  public static final String INPUT_DATA_ELEMENT = "dataElement";
  public static final String INPUT_DET = "det";
  public static final String INPUT_FORM_DEFINITION_ID = "formDefinitionId";  
  public static final String INPUT_FORM_DEFINITION = "formDefinition";  
  public static final String INPUT_FORM_DEFINITION1 = "formDefinition1";
  public static final String INPUT_FORM_DEFINITION2 = "formDefinition2";
  public static final String INPUT_FORM_INSTANCE = "formInstance";  
  public static final String INPUT_VALUE = "value";
  public static final String INPUT_VALUE1 = "value1";
  public static final String INPUT_VALUE2 = "value2";
  public static final String INPUT_VALUES = "values";
  public static final String INPUT_ACCOUNT = "account";
  public static final String INPUT_USERNAME = "userId";
  public static final String INPUT_SECURITY_INFO = "securityInfo";
  public static final String INPUT_QUERY_CRITERIA = "queryCriteria";

  public static final String OUTPUT_DET = "det";
  public static final String OUTPUT_FORM_DEFINITION = "formDefinition";
  public static final String OUTPUT_FORM_INSTANCE = "formInstance"; 
}
