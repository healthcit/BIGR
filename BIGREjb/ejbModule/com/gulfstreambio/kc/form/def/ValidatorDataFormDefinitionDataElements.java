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
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;

/**
 * Validates all of the data elements that comprise a data form definition. 
 * <p>
 * This validator may directly return one error as follows, with insertion strings listed below 
 * the error.  This validator also delegates much of its validation to
 * {@link KcDataFormDataElementValidationService}.
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
public class ValidatorDataFormDefinitionDataElements extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are duplicated data
   * elements. 
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.elementDuplicated";
  public final static String ERROR_KEY2 = "kc.error.formdef.dataElementDispNameDup";
  
  
  private DataElementTaxonomy _det; 
  private DataFormDefinition _formDef;
  private List _duplicatedValues; 
  private List _duplicatedDisplayNames; 


  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDataFormDefinitionDataElements v1 = (ValidatorDataFormDefinitionDataElements) v;
      String dups;
      if (errorKey.equals(ERROR_KEY1)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedValues()));
        v1.addErrorMessage(ValidatorDataFormDefinitionDataElements.ERROR_KEY1, dups);
      }
      else if (errorKey.equals(ERROR_KEY2)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedDisplayNames()));
        v1.addErrorMessage(ValidatorDataFormDefinitionDataElements.ERROR_KEY2, dups);
      }
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDataFormDefinitionDataElements</code> validator.
   */
  public ValidatorDataFormDefinitionDataElements() {
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
    
    Set uniqueCuis = new HashSet();
    List duplicatedCuis = new ArrayList();
    
    Set uniqueDisplayNames = new HashSet();
    List duplicatedDisplayNames = new ArrayList();
        
    KcDataFormDataElementValidationService deService =
      new KcDataFormDataElementValidationService();
    deService.setDet(getDet());
    deService.setCheckDataElementInDet(true);
    deService.setCheckNarrowValueSet(true);
    deService.setCheckStandardValueSet(true);
    deService.setCheckStandardIfNarrowValueSet(true);
    deService.setCheckNoValueSetWithNonCV(true);
    deService.setCheckValueSetNoOthersNarrowStandard(true);
    deService.setCheckAdeElements(true);
    deService.setCheckCalculation(true);

    DataFormDefinition formDef = getFormDefinition();
    DataFormDefinitionDataElement[] dataElements = formDef.getDataDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      DataFormDefinitionDataElement dataElement = dataElements[i];
      deService.setDataElementDefinition(dataElement);
      errors.addErrors(deService.validate());
      String cui = dataElement.getCui();
      
      // collect the list of unique and duplicated CUIs
      if (uniqueCuis.contains(cui)) {
        duplicatedCuis.add(cui);
      }
      else {
        uniqueCuis.add(cui);
      }
      
      // for MR 8688, rule 1  determine if any of the duplicated display names
      // are within the same category.  if so, this is an error.    
      String displayName = dataElement.getDisplayName();
      if (displayName != null) {        
        DataFormDefinitionCategory parentCategory = dataElement.getParentDataCategory();
        String categoryName = (parentCategory == null) ? "" : parentCategory.getDisplayName();
        // store as categoryName.displayName to determine duplicates
        String possibleDup = categoryName + "." + displayName; 
        
        //collect the list of duplicated display names...
        if (uniqueDisplayNames.contains(possibleDup)) {
          duplicatedDisplayNames.add(possibleDup);
        }
        else {
          uniqueDisplayNames.add(possibleDup);
        }
      }
    }
      
    // cannot have duplicated CUIs; this is an error
    if (!duplicatedCuis.isEmpty()) {
      setDuplicatedValues(duplicatedCuis);
      notifyValidatorErrorListener(ERROR_KEY1);
    }  
      
    // cannot have duplicated display names; this is an error
    if (!duplicatedDisplayNames.isEmpty()) {
      setDuplicatedDisplayNames(duplicatedDisplayNames);
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

  public DataElementTaxonomy getDet() {
    return _det;
  }

  public void setDet(DataElementTaxonomy det) {
    _det = det;
  }

  public List getDuplicatedValues() {
    return _duplicatedValues;
  }

  private void setDuplicatedValues(List values) {
    _duplicatedValues = values;
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
