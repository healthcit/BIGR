package com.gulfstreambio.kc.form.def;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValue;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValue;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet;

/**
 * Validates that the data element's narrow value set and standard values sets do not
 * have any other values. 
 * <p>
 * This validator may return two errors as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The narrow value set may not have an other value.
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY2} - The standard value set may not have an other value.
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorNoOthersStandardNarrow extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if the narrow value set is not
   * a subset of the standard value set.  
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.noOthersNarrow";
  public final static String ERROR_KEY2 = "kc.error.formdef.noOthersStandard";


  private DataFormDefinitionDataElement _deDef;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorNoOthersStandardNarrow v1 = (ValidatorNoOthersStandardNarrow) v;
      v1.addErrorMessage(errorKey, v1.getDataElement().getCui());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorNoOthersStandardNarrow</code> validator.
   */
  public ValidatorNoOthersStandardNarrow() {
    super();
    addErrorKey(ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY2);
    }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
      
    DataFormDefinitionDataElement deDef = getDataElement();
    if (deDef != null) {
      DataFormDefinitionValueSet narrow = deDef.getNarrowValueSet();
      DataFormDefinitionValueSet standard = deDef.getStandardValueSet();  
    
      if (narrow != null) {
        checkForOtherValue(narrow.getValues(), ERROR_KEY1);
      }
      if (standard != null) {
        checkForOtherValue(standard.getValues(), ERROR_KEY2);
      }        
    }
    return getActionErrors();
  }
  
  
  private void checkForOtherValue(DataFormDefinitionValue[] values, String errorKey) {
    for (int i = 0; i < values.length; i++) {
      DataFormDefinitionValue valDef = values[i];
      String cui = valDef.getCui();
      DetValue elValMetaData = DetService.SINGLETON.getDataElementTaxonomy().getValue(cui);
      if ((elValMetaData != null) && (elValMetaData.isOtherValue())) {      
        notifyValidatorErrorListener(errorKey);  
      }
    }          
  }
    

  public DataFormDefinitionDataElement getDataElement() {
    return _deDef;
  }

  public void setDataElement(DataFormDefinitionDataElement definition) {
    _deDef = definition;
  }

}