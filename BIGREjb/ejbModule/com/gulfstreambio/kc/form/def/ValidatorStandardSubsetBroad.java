package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.def.ValidatorQueryFormDefinitionDataElements.DefaultErrorListener;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet;

/**
 * Validates that the data element's standard value set is a subset of its broad value set.  If
 * the standard value set is not present or the broad value set could not be found, then 
 * validation will quietly succeed.  
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The standard value set is not a subset of the broad value set.
 *   <ol>
 *   <li>The CUI of the data element, as set in 
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorStandardSubsetBroad extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if the standard value set is not
   * a subset of the broad value set.  
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.standardNotSubsetBroad";
  public final static String ERROR_KEY2 = "kc.error.formdef.standardEqualsBroad";

  private DataFormDefinitionDataElement _deDef;
  private String _formDefId;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorStandardSubsetBroad v1 = (ValidatorStandardSubsetBroad) v;
      if (errorKey.equals(ERROR_KEY1)) {
        v1.addErrorMessage(ValidatorStandardSubsetBroad.ERROR_KEY1, v1.getDataElement().getCui());
      }
      else if (errorKey.equals(ERROR_KEY2)) {
        v1.addErrorMessage(ValidatorStandardSubsetBroad.ERROR_KEY2);
      }
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorStandardSubsetBroad</code> validator.
   */
  public ValidatorStandardSubsetBroad() {
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
    DataFormDefinitionValueSet standard = deDef.getStandardValueSet();      
    if (standard != null) {
      DetDataElement metadata = 
        DetService.SINGLETON.getDataElementTaxonomy().getDataElement(deDef.getCui());
      if (metadata != null) {
        DetValueSet broad = metadata.getBroadValueSet();
        if (broad != null) {
          // check to determine if standard is subset of broad
          if (!ValidatorUtils.subsetOf(standard,broad)) {
            notifyValidatorErrorListener(ERROR_KEY1);
            return getActionErrors();
          }
          DetValueSet standardValueSet = new DetValueSet(standard);
          // check to determine if standard equals broad
          if (broad.equals(standardValueSet)) {
            notifyValidatorErrorListener(ERROR_KEY2);
          }
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

  public String getFormDefinitionId() {
    return _formDefId;
  }

  public void setFormDefinitionId(String id) {
    _formDefId = id;
  }
}
