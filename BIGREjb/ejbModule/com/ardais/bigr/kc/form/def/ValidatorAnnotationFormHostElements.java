package com.ardais.bigr.kc.form.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConcept;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.kc.form.def.ValidatorRegistrationFormHostElements.DefaultErrorListener;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.FormDefinitionHostElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;

/**
 * @author sthomashow
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ValidatorAnnotationFormHostElements extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are any
   * host elements specified in the annotation form
   */
  public final static String ERROR_KEY1 
    = "orm.error.formcreator.nohostelementsannotation";


  private BigrFormDefinition _formDef; 
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorAnnotationFormHostElements v1 = (ValidatorAnnotationFormHostElements) v;
      if (errorKey.equals(ERROR_KEY1)) {
        v1.addErrorMessage(ValidatorAnnotationFormHostElements.ERROR_KEY1);
        }
      return true;
    }
  }

  public ValidatorAnnotationFormHostElements() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();
       
    DataFormDefinition formDef = (DataFormDefinition) getFormDefinition().getKcFormDefinition();
    DataFormDefinitionHostElement[] hostElements = formDef.getDataHostElements();
    // there should not be any host elements
    if (hostElements.length >= 1) {
        notifyValidatorErrorListener(ERROR_KEY1);
        return errors;
        }
    
    return errors;
  }

  public BigrFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(BigrFormDefinition definition) {
    _formDef = definition;
  }



}
