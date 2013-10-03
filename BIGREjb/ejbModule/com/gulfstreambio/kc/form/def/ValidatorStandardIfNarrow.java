package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;

/**
 * Validates that the data element's standard value set is present if the narrow value set is
 * specified. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The standard value set does not exist.
 *   <ol>
 *   <li>The CUI of the data element, as set in 
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorStandardIfNarrow extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if the standard value set does
   * not exist.  
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.standardValueSetMissing";

  private DataFormDefinitionDataElement _deDef;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorStandardIfNarrow v1 = (ValidatorStandardIfNarrow) v;
      v1.addErrorMessage(ValidatorStandardIfNarrow.ERROR_KEY1, v1.getDataElement().getCui());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorStandardIfNarrow</code> validator.
   */
  public ValidatorStandardIfNarrow() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    DataFormDefinitionDataElement deDef = getDataElement();
    if (!ApiFunctions.isEmpty(deDef.getNarrowValueSetId())) {
      if (ApiFunctions.isEmpty(deDef.getStandardValueSetId())) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    }
    return getActionErrors();
  }

  public DataFormDefinitionDataElement getDataElement() {
    return _deDef;
  }

  public void setDataElement(DataFormDefinitionDataElement definition) {
    _deDef = definition;
  }

}
