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
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet;

/**
 * Validates all of the value sets that comprise a form definition. 
 * <p>
 * This validator may directly return one error as follows, with insertion strings listed below 
 * the error.  This validator also delegates much of its validation to
 * {@link KcValueSetDefinitionValidationService}.
 * <ul>
 * <li>{@link #ERROR_KEY1} - One or more data elements are specified more than once.
 *   <ol>
 *   <li>A list of the CUIs of the duplicated data elements, as returned by
 *       {@link #getDuplicatedValues() getDuplicatedValues}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorFormDefinitionValueSets extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are duplicated data
   * elements. 
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.valuesetDuplicated";
  public final static String ERROR_KEY2 = "kc.error.formdef.valuesetNotUsed";

 
  private DataElementTaxonomy _det; 
  private DataFormDefinition _formDef;
  private List _duplicatedValues; 
  private List _unusedValues;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFormDefinitionValueSets v1 = (ValidatorFormDefinitionValueSets) v;
      String dups;
      if (errorKey.equalsIgnoreCase(ERROR_KEY1)) {
          dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedValues()));
          v1.addErrorMessage(ValidatorFormDefinitionValueSets.ERROR_KEY1, dups);
      }
      else if (errorKey.equalsIgnoreCase(ERROR_KEY2)) {
          dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getUnusedValues()));
          v1.addErrorMessage(ValidatorFormDefinitionValueSets.ERROR_KEY2, dups);

      }
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorFormDefinitionValueSets</code> validator.
   */
  public ValidatorFormDefinitionValueSets() {
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
    BtxActionErrors errors = getActionErrors();
    
    Set uniqueValuesets = new HashSet();
    List duplicatedValueSets = new ArrayList();
        
    KcValueSetDefinitionValidationService vsService =
      new KcValueSetDefinitionValidationService();

    // set rules to validate
    vsService.setDet(getDet());
    vsService.setCheckValueSetCuis(true);

    DataFormDefinition formDef = getFormDefinition();
    DataFormDefinitionValueSet[] valueSets = formDef.getValueSets().getValueSets();
    for (int i = 0; i < valueSets.length; i++) {
      DataFormDefinitionValueSet def = valueSets[i];
      vsService.setValueSetDefinition(def);
      errors.addErrors(vsService.validate());
      
      // collect value set names
      String vsName = def.getId();
      if (uniqueValuesets.contains(vsName)) {
        duplicatedValueSets.add(vsName);
      }
      else {
        uniqueValuesets.add(vsName);
      }
       
    }
    
    // MR 8681, rule 2. determine if more than one value sets were given the same name
    if (!duplicatedValueSets.isEmpty()) {
      setDuplicatedValues(duplicatedValueSets);
      notifyValidatorErrorListener(ERROR_KEY1);
    }
   
 
    Set allDataElementValuesets = new HashSet();
    List unusedValueSets = new ArrayList();
     
    // MR 8681, rule 1. determine if any of the value sets were not used
    // need to loop thru data elements to do this...
    DataFormDefinitionDataElement[] dataElements = formDef.getDataDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      // for each data element...
      DataFormDefinitionDataElement def = dataElements[i];
      
      // ...get the narrow and standard value sets 
      allDataElementValuesets.add(def.getNarrowValueSetId());
      allDataElementValuesets.add(def.getStandardValueSetId());  
    }
    
    // now, compare the value sets used to those specified
    Iterator valSets = uniqueValuesets.iterator();
    while (valSets.hasNext()) {
      String valSetName = (String) valSets.next();
      if (!allDataElementValuesets.contains(valSetName)) {
        unusedValueSets.add(valSetName);
      }
    }
 
    // if there are unused, then note the error... 
    if (!unusedValueSets.isEmpty()) {
      setUnusedValues(unusedValueSets);
      notifyValidatorErrorListener(ERROR_KEY2);
    }   

    
    return errors;
  }

  public DataFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(DataFormDefinition definition) {
    _formDef = definition;
  }

  public List getDuplicatedValues() {
    return _duplicatedValues;
  }

  private void setDuplicatedValues(List values) {
    _duplicatedValues = values;
  }

  public DataElementTaxonomy getDet() {
    return _det;
  }

  public void setDet(DataElementTaxonomy det) {
    _det = det;
  }
  
  
  /**
   * @return Returns the unusedValues.
   */
  public List getUnusedValues() {
    return _unusedValues;
  }
  /**
   * @param unusedValues The unusedValues to set.
   */
  public void setUnusedValues(List unusedValues) {
    _unusedValues = unusedValues;
  }
}
