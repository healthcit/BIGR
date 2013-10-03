package com.gulfstreambio.kc.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.util.KcUtils;

/**
 * Validates that all of the data elements exist in form instance that are "required" by a form def. 
 * <p>
 * This validator may directly return one error as follows, with insertion strings listed below 
 * the error.  This validator also delegates much of its validation to
 * {@link ValidatorDataElement}.
 * <ul>
 * <li>{@link #ERROR_KEY1} - One or more required data elements are missing.
 *   <ol>
 *   <li>A list of the CUIs of the required but missing data elements, as returned by
 *       {@link #getMissingValues() getDuplicatedValues}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorRequiredDataElements extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are duplicated data
   * elements. 
   */
  public final static String ERROR_KEY1 = "kc.error.forminst.elementRequired";
    
  private FormInstance _formInst; 
  private DataFormDefinition _formDef;

  private List _missingValues;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorRequiredDataElements v1 = (ValidatorRequiredDataElements) v;
        String missing =
          ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getMissingValues()));
        v1.addErrorMessage(ValidatorRequiredDataElements.ERROR_KEY1, missing);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorRequiredDataElements</code> validator.
   */
  public ValidatorRequiredDataElements() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();
    
    List missingCuis = new ArrayList();    
    //For each DataFormDefinitionDataElement in DataFormDefinition  ensure that  
    //"required" DataElements are present in Form Instance
    DataFormDefinition formDef = getFormDefinition();
    DataFormDefinitionDataElement[] dataElements = formDef.getDataDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      DataFormDefinitionDataElement element = dataElements[i];      
      if (element.isRequired()) {
        boolean reqdElemFound = false;
        FormInstance formInst = getFormInstance();
        DataElement[] instanceElements = getFormInstance().getDataElements();
        for (int j = 0; j < instanceElements.length; j++) {
          DataElement elem = instanceElements[j];
          String elemCui = 
            DetService.SINGLETON.getDataElementTaxonomy().getDataElement(elem.getCuiOrSystemName()).getCui();
          if (elemCui.equals(element.getCui())
              && elem.isElementValueExists(0) ) { //atleast one value exists
            ElementValue elementValue = elem.getElementValue(0);
            if (!ApiFunctions.isEmpty(elementValue.getValue()) 
                || !ApiFunctions.isEmpty(elementValue.getValueOther())) {
              reqdElemFound = true;
              break;
            }
          }
        }
        if (!reqdElemFound) {
          missingCuis.add(KcUtils.getDescription(element));
        }
      }      
    }

    setMissingValues(missingCuis);
    if (!missingCuis.isEmpty()) {      
      notifyValidatorErrorListener(ERROR_KEY1);
    }   
    return errors;
  }

  public DataFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(DataFormDefinition definition) {
    _formDef = definition;
  }

  public FormInstance getFormInstance() {
    return _formInst;
  }

  public void setFormInstance(FormInstance formInst) {
    _formInst = formInst;
  }

  public List getMissingValues() {
    return _missingValues;
  }
  public void setMissingValues(List duplicatedValues) {
    _missingValues = duplicatedValues;
  }
}
