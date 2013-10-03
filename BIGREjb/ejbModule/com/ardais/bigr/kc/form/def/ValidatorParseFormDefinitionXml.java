package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionFactory;

/**
 * Validates that the supplied form definition XML document can be parsed using a validating
 * parser.
 * <p>
 * Inputs:
 * <ul>
 * <li>{@link #setValue1(java.lang.String) setValue1} - The form definition XML document. 
 * </li>
 * <li>{@link #setValue2(java.lang.String) setValue2} - The form definition type. 
 * </li>
 * </ul> 
 * <p>
 * Outputs (only valid if errors are not reported):
 * <ul>
 * <li>{@link #getFormDefinition() getFormDefinition} - The 
 * {@link com.gulfstreambio.kc.form.def.FormDefinition FormDefinition} resulting from parsing 
 * the XML document. 
 * </li>
 * </ul> 
 * <p>
 * Error Outputs (only valid if errors are reported):
 * <ul>
 * <li>{@link #getParserErrorMessage() getParserErrorMessage} - The error message reported by
 * the XML parser. 
 * </li>
 * </ul> 
 * <p>
 * This validator may return one possible error as follows.  It has a single 
 * insertion string.
 * <ul>
 * <li>{@link #ERROR_KEY1} - There was an error parsing the XML document.
 *   <ol>
 *   <li>The error message returned by the parser, as returned by
 *       {@link #getParserErrorMessage() getParserErrorMessage}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorParseFormDefinitionXml extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "orm.error.formcreator.xmldocparseerror";

  private String _value1;
  private String _value2;
  private FormDefinition _formDef;
  private String _parserErrorMessage;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorParseFormDefinitionXml v1 = (ValidatorParseFormDefinitionXml) v;
      v1.addErrorMessage(ValidatorParseFormDefinitionXml.ERROR_KEY1, v1.getParserErrorMessage());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorParseFormDefinitionXml</code> validator.
   */
  public ValidatorParseFormDefinitionXml() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    String xml = getValue1();
    if (!ApiFunctions.isEmpty(xml)) {
      try {
        String type = getValue2();
        setFormDefinition(FormDefinitionFactory.SINGLETON.createFromXml(type, xml));
      }
      catch (ApiException e) {
        setParserErrorMessage(e.getMessage());
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    }
    return getActionErrors();
  }

  /**
   * Returns the XML to be parsed.
   * 
   * @return  The XML document.
   */
  public String getValue1() {
    return _value1;
  }

  /**
   * Sets the XML to be parsed.
   * 
   * @param  value  the XML document
   */
  public void setValue1(String value) {
    _value1 = value;
  }

  /**
   * Returns the type of form definition that the XML represents.
   * 
   * @return  The form type.
   */
  public String getValue2() {
    return _value2;
  }

  /**
   * Sets the type of form definition that the XML represents.
   * 
   * @param  value  the form type
   */
  public void setValue2(String value) {
    _value2 = value;
  }

  /**
   * Returns the XML parser error if parsing the XML document resulted in an error.  This can be
   * useful in validator error listeners.
   * 
   * @return  The XML parser error. 
   */
  public String getParserErrorMessage() {
    return _parserErrorMessage;
  }

  private void setParserErrorMessage(String string) {
    _parserErrorMessage = string;
  }

  /**
   * Returns the <code>FormDefinition</code> that resulted from parsing the XML document.
   * 
   * @return  The <code>FormDefinition</code>, if there were no errors. 
   */
  public FormDefinition getFormDefinition() {
    return _formDef;
  }

  private void setFormDefinition(FormDefinition form) {
    _formDef = form;
  }

}
