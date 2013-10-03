package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.def.ValidatorStandardSubsetBroad.DefaultErrorListener;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet;

/**
 * Validates that the data element's narrow value set is a subset of its standard value set.  If
 * one or both of the value sets are not present, then validation will quietly succeed. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The narrow value set is not a subset of the standard value set.
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorNarrowSubsetStandard extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if the narrow value set is not
   * a subset of the standard value set.  
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.narrowNotSubsetStandard";
  public final static String ERROR_KEY2 = "kc.error.formdef.narrowEqualsStandard";


  private DataFormDefinitionDataElement _deDef;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorNarrowSubsetStandard v1 = (ValidatorNarrowSubsetStandard) v;
      if (errorKey.equals(ERROR_KEY1)) {
        v1.addErrorMessage(ValidatorNarrowSubsetStandard.ERROR_KEY1, v1.getDataElement().getCui());
      }
      else if (errorKey.equals(ERROR_KEY2)) {
        v1.addErrorMessage(ValidatorNarrowSubsetStandard.ERROR_KEY2);
      }
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorNarrowSubsetStandard</code> validator.
   */
  public ValidatorNarrowSubsetStandard() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY2);    
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    DataFormDefinitionDataElement deDef = getDataElement();
    DataFormDefinitionValueSet narrow = deDef.getNarrowValueSet();
    if (narrow != null) {
      DataFormDefinitionValueSet standard = deDef.getStandardValueSet();      
      if (standard != null) {
        // check to determine if narrow is subset of standard
        if (!(ValidatorUtils.subsetOf(narrow, standard))) {
          notifyValidatorErrorListener(ERROR_KEY1);
          return getActionErrors();
        }
        DetValueSet standardValueSet = new DetValueSet(standard);
        DetValueSet narrowValueSet = new DetValueSet(narrow);
        // check to determine if narrow equals standard
        if (narrowValueSet.equals(standardValueSet)) {
          notifyValidatorErrorListener(ERROR_KEY2);
        }        
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
