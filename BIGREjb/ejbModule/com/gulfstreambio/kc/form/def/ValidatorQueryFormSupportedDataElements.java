package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.form.def.ValidatorDetContainsDataElement.DefaultErrorListener;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;

import java.util.List;


public class ValidatorQueryFormSupportedDataElements extends AbstractValidator {

  private DataElementTaxonomy _det;  
  private String  _cui;
  private List  _notSupportedDataElements;
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.datatypesSupportedForQuery";


  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorQueryFormSupportedDataElements v1 = (ValidatorQueryFormSupportedDataElements) v;
      v1.addErrorMessage(ValidatorQueryFormSupportedDataElements.ERROR_KEY1, getCui());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDetContainsDataElement</code> validator.
   */
  public ValidatorQueryFormSupportedDataElements() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
   
    // get the DET information for this data element
    DetDataElement detElem = this.getDet().getDataElement(this.getCui());

    // determine if supported datatype
    if (! ( (detElem.isDatatypeCv()) || (detElem.isDatatypeDate()) ||
            (detElem.isDatatypeFloat()) || (detElem.isDatatypeInt()) ||
            (detElem.isDatatypeVpd()) )) {
      notifyValidatorErrorListener(ERROR_KEY1);
    }
      
    
    return getActionErrors();
  }



  /**
   * @return Returns the notSupportedDataElements.
   */
  public List getNotSupportedDataElements() {
    return _notSupportedDataElements;
  }
  /**
   * @param notSupportedDataElements The notSupportedDataElements to set.
   */
  public void setNotSupportedDataElements(List notSupportedDataElements) {
    _notSupportedDataElements = notSupportedDataElements;
  }
  /**
   * @return Returns the det.
   */
  public DataElementTaxonomy getDet() {
    return _det;
  }
  /**
   * @param det The det to set.
   */
  public void setDet(DataElementTaxonomy det) {
    _det = det;
  }
  /**
   * @return Returns the cui.
   */
  public String getCui() {
    return _cui;
  }
  /**
   * @param cui The cui to set.
   */
  public void setCui(String cui) {
    _cui = cui;
  }
}
