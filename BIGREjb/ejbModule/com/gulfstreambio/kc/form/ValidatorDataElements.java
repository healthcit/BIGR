package com.gulfstreambio.kc.form;

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
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.util.KcUtils;

/**
 * Validates all of the data elements that comprise a form instance. 
 * <p>
 * This validator may directly return one error as follows, with insertion strings listed below 
 * the error.  This validator also delegates much of its validation to
 * {@link ValidatorDataElement}.
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
public class ValidatorDataElements extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are duplicated data
   * elements. 
   */
  public final static String ERROR_KEY1 = "kc.error.forminst.elementDuplicated";
  
  private FormInstance _formInst; 
  private DataFormDefinition _formDef;

  private List _duplicatedValues;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDataElements v1 = (ValidatorDataElements) v;
      String dups =
        ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedValues()));
      v1.addErrorMessage(ValidatorDataElements.ERROR_KEY1, dups);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDataElements</code> validator.
   */
  public ValidatorDataElements() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();
    
    Set uniqueCuis = new HashSet();
    List duplicatedValues = new ArrayList();
    
    ValidatorDataElement devService =
      new ValidatorDataElement();
    devService.setFormDefinition(getFormDefinition());

    //validate one dataElem at a time and gather any duplicated cuis
    FormInstance formInstance = getFormInstance();
    DataElement[] elements = formInstance.getDataElements();
    for (int i = 0; i < elements.length; i++) {
      DataElement elem = elements[i];
      devService.setDataElement(elem);
      errors.addErrors(devService.validate());
      DetElement metadata = 
        DetService.SINGLETON.getDataElementTaxonomy().getDataElement(elem.getCuiOrSystemName());
      String cui = metadata.getCui();
      if (uniqueCuis.contains(cui)) {
        duplicatedValues.add(KcUtils.getDescription(elem, getFormDefinition()));
      }
      else {
        uniqueCuis.add(cui);
      }
    }
    
    if (!duplicatedValues.isEmpty()) {
      setDuplicatedValues(duplicatedValues);
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

  public List getDuplicatedValues() {
    return _duplicatedValues;
  }
  public void setDuplicatedValues(List duplicatedValues) {
    _duplicatedValues = duplicatedValues;
  }
}
