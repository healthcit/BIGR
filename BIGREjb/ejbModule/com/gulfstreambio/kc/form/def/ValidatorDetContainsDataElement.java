package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetService;

/**
 * Validates that a data element is present within a specified DET. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The data element is not present in the DET.
 *   <ol>
 *   <li>The CUI of the data element, as set by {@link #setValue(java.lang.String) setValue}.
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorDetContainsDataElement extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.elementNotInDet";

  private String _value;
  private DataElementTaxonomy _det; 

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDetContainsDataElement v1 = (ValidatorDetContainsDataElement) v;
      v1.addErrorMessage(ValidatorDetContainsDataElement.ERROR_KEY1, v1.getValue());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDetContainsDataElement</code> validator.
   */
  public ValidatorDetContainsDataElement() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    DataElementTaxonomy det = getDet();
    if (det == null) {
      throw new ApiException("ValidatorDetContainsDataElement: DET was not specified");
    }
    
    String cui = getValue();
    if (det.getDataElement(cui) == null) {
      notifyValidatorErrorListener(ERROR_KEY1);
    }
    
    return getActionErrors();
  }

  public String getValue() {
    return _value;
  }

  public void setValue(String value) {
    _value = value;
  }

  public DataElementTaxonomy getDet() {
    return _det;
  }

  public void setDet(DataElementTaxonomy det) {
    _det = det;
  }

}
