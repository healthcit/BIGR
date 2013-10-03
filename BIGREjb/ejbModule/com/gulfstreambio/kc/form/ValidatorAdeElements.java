package com.gulfstreambio.kc.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetAde;

/**
 * Validates all of the ade elements that comprise an Ade. 
 * <p>
 * This validator may directly return one error as follows, with insertion strings listed below 
 * the error.  This validator also delegates much of its validation to
 * {@link ValidatorAdeElement}.
 * <ul>
 * <li>{@link #ERROR_KEY1} - One or more ade elements are specified more than once.
 *   <ol>
 *   <li>A list of the CUIs of the duplicated ade element, as returned by
 *       {@link #getDuplicatedValues() getDuplicatedValues}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorAdeElements extends AbstractValidator {

  
  /**
   * The key of the error that is returned from this validator if there are duplicated data
   * elements. 
   */
  public final static String ERROR_KEY1 = "kc.error.forminst.adeDuplicated";
  
  private AdeElement[] _adeElements;
  private DetAde _adeMetadata;  
  
  private List _duplicatedValues;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorAdeElements v1 = (ValidatorAdeElements) v;
        String dups =
          ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedValues()));
        v1.addErrorMessage(ValidatorAdeElements.ERROR_KEY1, dups);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorAdeElements</code> validator.
   */
  public ValidatorAdeElements() {
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
    List duplicatedCuis = new ArrayList();
    
    ValidatorAdeElement adeElementValidator =
      new ValidatorAdeElement();
    adeElementValidator.setAdeMetadata(getAdeMetadata());

    //validate one dataElem at a time and gather any duplicated cuis
    AdeElement[] elems = getAdeElements();
    for (int i = 0; i < elems.length; i++) {
      AdeElement elem = elems[i];
      adeElementValidator.setAdeElement(elem);
      errors.addErrors(adeElementValidator.validate());
      String cui = elem.getCuiOrSystemName();
      if (uniqueCuis.contains(cui)) {
        duplicatedCuis.add(cui);
      }
      else {
        uniqueCuis.add(cui);
      }
    }
    
    if (!duplicatedCuis.isEmpty()) {
      setDuplicatedValues(duplicatedCuis);
      notifyValidatorErrorListener(ERROR_KEY1);
    }
        
    return errors;
  }

  public List getDuplicatedValues() {
    return _duplicatedValues;
  }
  public void setDuplicatedValues(List duplicatedValues) {
    _duplicatedValues = duplicatedValues;
  }
  public DetAde getAdeMetadata() {
    return _adeMetadata;
  }
  public void setAdeMetadata(DetAde adeMetadata) {
    this._adeMetadata = adeMetadata;
  }
  public AdeElement[] getAdeElements() {
    return _adeElements;
  }
  public void setAdeElements(AdeElement[] adeElements) {
    this._adeElements = adeElements;
  }
}
