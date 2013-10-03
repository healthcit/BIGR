package com.gulfstreambio.kc.form.def;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.ValidatorNarrowSubsetStandard.DefaultErrorListener;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValue;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet;

/**
 * Validates that the value definition display name 
 *    are not used more than once in this value set 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The narrow value set is not a subset of the standard value set.
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorValueSetCuis extends AbstractValidator {

  /**
   * The key of the error that is returned if any value definition display name 
   *    was used more than once in this value set
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.valuesetDispNameDup";

  private DataFormDefinitionValueSet _vsDef;
  private List _duplicatedDisplayNames;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorValueSetCuis v1 = (ValidatorValueSetCuis) v;
      String dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedDisplayNames()));
      v1.addErrorMessage(ValidatorValueSetCuis.ERROR_KEY1, dups);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorValueSetCuis</code> validator.
   */
  public ValidatorValueSetCuis() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {

    BtxActionErrors errors = getActionErrors();
    
    Set uniqueDisplayNames = new HashSet();
    List duplicatedDisplayNames = new ArrayList();
    
    //  MR 8688, rule 2. flag any value definition display name 
    //   that was used more than once in this value set
    
    DataFormDefinitionValueSet vsDef = getValueSet();
    
    DataFormDefinitionValue[] values = vsDef.getValues();
    
    // for each value definition...
    for (int i = 0; i < values.length; i++) {
      DataFormDefinitionValue valDef = values[i];
      
      // get the display string
      String dispName = valDef.getDisplayName();
      
      if (dispName != null )  {
        // determine if previously used or not...
        if (uniqueDisplayNames.contains(dispName)) {
          duplicatedDisplayNames.add(dispName);
        }
        else {
          uniqueDisplayNames.add(dispName);
        }   
      }
    }
    
    if (!duplicatedDisplayNames.isEmpty()) {
      setDuplicatedDisplayNames(duplicatedDisplayNames);
      notifyValidatorErrorListener(ERROR_KEY1);
    }
  
    return errors;
  }

  public DataFormDefinitionValueSet getValueSet() {
    return _vsDef;
  }

  public void setValueSet(DataFormDefinitionValueSet definition) {
    _vsDef = definition;
  }

  /**
   * @return Returns the duplicatedDisplayNames.
   */
  public List getDuplicatedDisplayNames() {
    return _duplicatedDisplayNames;
  }
  /**
   * @param duplicatedDisplayNames The duplicatedDisplayNames to set.
   */
  public void setDuplicatedDisplayNames(List duplicatedDisplayNames) {
    _duplicatedDisplayNames = duplicatedDisplayNames;
  }
}