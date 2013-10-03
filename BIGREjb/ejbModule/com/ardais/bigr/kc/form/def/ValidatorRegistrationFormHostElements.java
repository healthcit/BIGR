package com.ardais.bigr.kc.form.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConcept;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.kc.form.def.ValidatorDataFormTags.DefaultErrorListener;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.FormDefinitionHostElement;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;

/**
 * @author sthomashow
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ValidatorRegistrationFormHostElements extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are host elements
   * that do not match the object type
   */
  public final static String ERROR_KEY1 
    = "orm.error.formcreator.mismatchregformannotation";

  private BigrFormDefinition _formDef; 

  private List _badAttrs = new ArrayList();
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      String dups;
      ValidatorRegistrationFormHostElements v1 = (ValidatorRegistrationFormHostElements) v;
      if (errorKey.equals(ERROR_KEY1)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getBadAttrs()));
        v1.addErrorMessage(ValidatorRegistrationFormHostElements.ERROR_KEY1, dups);
        }
      return true;
    }
  }

  public ValidatorRegistrationFormHostElements() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();
    
    List badAttrs = new ArrayList();
    
    BigrConceptList validListAttrs;
    
    // set up the object type to validate
    String objectType = getFormDefinition().getObjectType();
    if (objectType == null) {
      return errors;
    }
    if (objectType.equals(TagTypes.DOMAIN_OBJECT_VALUE_DONOR)) {
      validListAttrs = SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_DONOR_ATTRIBUTES);
    }
    else if (objectType.equals(TagTypes.DOMAIN_OBJECT_VALUE_CASE)) {
      validListAttrs = SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_CASE_ATTRIBUTES);
    }
    else  { // TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE 
      validListAttrs = SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_ATTRIBUTES);
    }
      
    
    DataFormDefinition formDef = (DataFormDefinition) getFormDefinition().getKcFormDefinition();
    // must loop thru all host elements and verify that they are valid...
    DataFormDefinitionHostElement[] hostElements = formDef.getDataHostElements();
    for (int i = 0; i < hostElements.length; i++) {
        FormDefinitionHostElement def = hostElements[i];
        String hostId = def.getHostId();

        // determine if host id can be found for the object type; if not, flag as error 
        Iterator iterator = validListAttrs.iterator();
        boolean foundIt = false;
        while (iterator.hasNext()) {
          String attrCui = ((BigrConcept)iterator.next()).getCode();
          if (hostId.equals(attrCui)) {
            foundIt = true;
          }
        }
        if (!foundIt) {
          badAttrs.add(hostId);
        }
      }
      
    if (!badAttrs.isEmpty()) {
        setBadAttrs(badAttrs);
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    
    return errors;
  }

  public BigrFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(BigrFormDefinition definition) {
    _formDef = definition;
  }


  /**
   * @return Returns the badAttrs.
   */
  public List getBadAttrs() {
    return _badAttrs;
  }
  /**
   * @param badAttrs The badAttrs to set.
   */
  public void setBadAttrs(List badAttrs) {
    _badAttrs = badAttrs;
  }
}
