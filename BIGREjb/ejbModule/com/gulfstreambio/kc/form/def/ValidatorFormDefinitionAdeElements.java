package com.gulfstreambio.kc.form.def;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.validation.ValidatorDerivativeOperationParentSamples;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionAdeElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.util.KcUtils;

/**
 * @author sthomashow
 *
 
 */
public class ValidatorFormDefinitionAdeElements extends AbstractValidator{

  /**
   * The key of the error that is returned from this validator if there are duplicated data
   * elements. 
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.adeDuplicatedInElement";
  public final static String ERROR_KEY2 = "kc.error.formdef.adeNotInDet";
  public final static String ERROR_KEY3 = "kc.error.formdef.adeNotForDataElem";
  public final static String ERROR_KEY4 = "kc.error.formdef.adeNotInGivenDataElem";

    
   
  private DataFormDefinitionDataElement _dataElement;

  private List _duplicatedValues;
  private List _notInDetValues;
  private List _notInThisAde;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      
      ValidatorFormDefinitionAdeElements v1 = (ValidatorFormDefinitionAdeElements) v;
      if (errorKey.equals(ValidatorFormDefinitionAdeElements.ERROR_KEY1)) {
        String dups =
          ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedValues()));
        v1.addErrorMessage(errorKey, dups);
        }
      else if (errorKey.equals(ValidatorFormDefinitionAdeElements.ERROR_KEY2)) {
        String valsNotFound =
          ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getNotInDetValues()));
        v1.addErrorMessage(errorKey, valsNotFound);
        }
      else if (errorKey.equals(ValidatorFormDefinitionAdeElements.ERROR_KEY3)) {
        v1.addErrorMessage(errorKey, v1.getDataElement().getDisplayName());
      }
      else if (errorKey.equals(ValidatorFormDefinitionAdeElements.ERROR_KEY4)) {
        String wrongAdeVals =
          ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getNotInThisAde()));

        v1.addErrorMessage(errorKey, wrongAdeVals, v1.getDataElement().getDisplayName());
      }
 
      return true;
    }
  }

  
  
  /**
   * Creates a new <code>ValidatorDataElements</code> validator.
   */
  public ValidatorFormDefinitionAdeElements() {
    super();
    addErrorKey(ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addErrorKey(ERROR_KEY3);
    addErrorKey(ERROR_KEY4);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY2);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY3);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY4);

  }  
  
  public BtxActionErrors validate() {
    DetDataElement metadata;

    
    BtxActionErrors errors = getActionErrors();

    DataFormDefinitionDataElement dataElem = getDataElement();

    // if no ADE elements, simply return
    if (dataElem.getAdeElements().length == 0) {
      return errors;
    }
    
    // else there are ADE elements, so 
    // determine that Data Element has ADE according to DET
    else {
      metadata = DetService.SINGLETON.getDataElementTaxonomy().getDataElement(dataElem.getCui());
      if (!(metadata.isHasAde())) {
        notifyValidatorErrorListener(ERROR_KEY3);
        return errors;
      }
    }   
    
    Set uniqueCuis = new HashSet();
    List duplicatedValues = new ArrayList();
    List notInDetValues = new ArrayList();
    List notInThisDet = new ArrayList();
    
    //validate one adeElem at a time and gather any duplicated cuis
    DataFormDefinitionAdeElement[] elems = dataElem.getAdeElements();
    for (int i = 0; i < elems.length; i++) {
      
      // get the ADE
      DataFormDefinitionAdeElement ade = elems[i];
      
      // get the DET value
      DetElement adeMetadata = 
        DetService.SINGLETON.getDataElementTaxonomy().getAdeElement(ade.getCui());

      //determine if ADE element is in DET
      if (adeMetadata != null) {
        
        //determine duplicates
        String cui = adeMetadata.getCui();
        if (uniqueCuis.contains(cui)) {
          duplicatedValues.add(ade.getDisplayName());
        }
        else {
          uniqueCuis.add(cui);
        }
        
        //  determine if the ADE element specified
        //  is a member of the ADE for the given Data Element
        boolean bFoundInAde = false;
        DetAdeElement[] adeElements = metadata.getAde().getAdeElements();
        for (int j = 0; j < adeElements.length; j++) {
          DetAdeElement detAdeElem = adeElements[j];
          if (cui.equals(detAdeElem.getCui())) {
            bFoundInAde = true;
          }
        }
        if (!bFoundInAde) {
          notInThisDet.add(cui);
        }
       
        
      }
      else {  // could not find ADE value in DET
        notInDetValues.add(ade.getDisplayName());
      }
    } // loop for each data element
    
    if (!duplicatedValues.isEmpty()) {
      setDuplicatedValues(duplicatedValues);
      notifyValidatorErrorListener(ERROR_KEY1);
    }
    
    if (!notInDetValues.isEmpty()){
      setNotInDetValues(notInDetValues);
      notifyValidatorErrorListener(ERROR_KEY2);
    }
    
    if (!notInThisDet.isEmpty()) {
      setNotInThisAde(notInThisDet);
      notifyValidatorErrorListener(ERROR_KEY4);
    }
    return errors;
  }


  public List getDuplicatedValues() {
    return _duplicatedValues;
  }
  public void setDuplicatedValues(List duplicatedValues) {
    _duplicatedValues = duplicatedValues;
  }
 
  /**
   * @return Returns the notInDetValues.
   */
  public List getNotInDetValues() {
    return _notInDetValues;
  }
  /**
   * @param notInDetValues The notInDetValues to set.
   */
  public void setNotInDetValues(List notInDetValues) {
    _notInDetValues = notInDetValues;
  }
  
  /**
   * @return Returns the dataElement.
   */
  public DataFormDefinitionDataElement getDataElement() {
    return _dataElement;
  }
  /**
   * @param dataElement The dataElement to set.
   */
  public void setDataElement(DataFormDefinitionDataElement dataElement) {
    _dataElement = dataElement;
  }
  /**
   * @return Returns the notInThisAde.
   */
  public List getNotInThisAde() {
    return _notInThisAde;
  }
  /**
   * @param notInThisAde The notInThisAde to set.
   */
  public void setNotInThisAde(List notInThisAde) {
    _notInThisAde = notInThisAde;
  }
}
