package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;

/**
 * Validates that the data element's datatype supports value sets.
 * 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - non-CV data element is specified with a value set.
 *   <ol>
 *   <li>The CUI of the data element, as set in 
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorNoValueSetWithNonCV extends AbstractValidator{
  /**
   * The key of the error that is returned from this validator if a non-CV data element
   * is specified with a value set.  
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.novaluesetwithnoncv";

  private DataFormDefinitionDataElement _deDef;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorNoValueSetWithNonCV v1 = (ValidatorNoValueSetWithNonCV) v;
      v1.addErrorMessage(ValidatorNoValueSetWithNonCV.ERROR_KEY1, v1.getDataElement().getCui());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorStandardIfNarrow</code> validator.
   */
  public ValidatorNoValueSetWithNonCV() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    DataFormDefinitionDataElement deDef = getDataElement();
    
    // need to get metadata because need datatype
    DetDataElement metadata = 
      DetService.SINGLETON.getDataElementTaxonomy().getDataElement(deDef.getCui());
    
      // determine if datatype is not CV
      if (!metadata.isDatatypeCv()) {
        // if not CV, then determine if there is a value set; this is an error
        if ((deDef.getNarrowValueSet() != null) || (deDef.getStandardValueSet() != null)) {       
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
