package com.ardais.bigr.kc.form.def;

import java.util.ArrayList;
import java.util.List;

import org.apache.xpath.FoundIndex;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.kc.form.def.ValidatorRegistrationFormNoCategories.DefaultErrorListener;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.FormDefinitionHostElement;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;

/**
 * @author sthomashow
 *
 * Validator to implement rules for tags for 
 *    core host attributes and KC data elements
 *    for both annotation and registration forms
 */
public class ValidatorDataFormTags extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are 
   * violations of tag rules
   */
  public final static String ERROR_KEY1 
    = "orm.error.formcreator.badtagsdataelements";
  public final static String ERROR_KEY2 
    = "orm.error.formcreator.notagsannotation";
  public final static String ERROR_KEY3 
  = "orm.error.formcreator.multimustignoretag";
  public final static String ERROR_KEY4 
  = "orm.error.formcreator.multinorequire";
  public final static String ERROR_KEY5 
  = "orm.error.formcreator.defaultsnotandignore";
  public final static String ERROR_KEY6 
  = "orm.error.formcreator.notignoreandrequire";

  
  
  
  

  private BigrFormDefinition _formDef; 

  private List _badAttrs = new ArrayList();
  private List _multiIgnore = new ArrayList();
  private List _multiRequired = new ArrayList();
  private List _defaultIgnore = new ArrayList();
  private List _ignoreRequire = new ArrayList();
  

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      String dups;
      ValidatorDataFormTags v1 = (ValidatorDataFormTags) v;
      if (errorKey.equals(ERROR_KEY1)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getBadAttrs()));
        v1.addErrorMessage(ValidatorDataFormTags.ERROR_KEY1, dups);
        }
      else if (errorKey.equals(ERROR_KEY2)) {
        v1.addErrorMessage(ValidatorDataFormTags.ERROR_KEY2);     
      }
      else if (errorKey.equals(ERROR_KEY3)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getMultiIgnore()));
        v1.addErrorMessage(ValidatorDataFormTags.ERROR_KEY3, dups);       
      }
      else if (errorKey.equals(ERROR_KEY4)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getMultiRequired()));
        v1.addErrorMessage(ValidatorDataFormTags.ERROR_KEY4, dups);        
       }
      else if (errorKey.equals(ERROR_KEY5)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDefaultIgnore()));
        v1.addErrorMessage(ValidatorDataFormTags.ERROR_KEY5, dups);        
       }
      else if (errorKey.equals(ERROR_KEY6)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getIgnoreRequire()));
        v1.addErrorMessage(ValidatorDataFormTags.ERROR_KEY6, dups);        
       }
      

      return true;
    }
  }

  public ValidatorDataFormTags() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY2);
    addErrorKey(ERROR_KEY3);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY3);
    addErrorKey(ERROR_KEY4);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY4);
    addErrorKey(ERROR_KEY5);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY5);
    addErrorKey(ERROR_KEY6);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY6);



  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();
    
  
    DataFormDefinition formDef = (DataFormDefinition) getFormDefinition().getKcFormDefinition();
    if (formDef == null) // validator cannot proceed
        return errors;
    // must loop thru all data elements and examine the tags...
    FormDefinitionDataElement[] dataElements = formDef.getDataElements();
    DataFormDefinitionHostElement[] hostElements = formDef.getDataHostElements();
 
    
    if ((getFormDefinition().getUses() == null) || (getFormDefinition().getObjectType() == null)) { // validator cannot proceed
        return errors;
    }
    // annotation forms, donor reg forms, and case reg forms
    // cannot have tags...so check them.
    if ((getFormDefinition().getUses().equals(TagTypes.USES_VALUE_ANNOTATION))
            || (getFormDefinition().getObjectType().equals(TagTypes.DOMAIN_OBJECT_VALUE_CASE))
            || (getFormDefinition().getObjectType().equals(TagTypes.DOMAIN_OBJECT_VALUE_DONOR))){
      for (int i = 0; i < dataElements.length; i++) {
        FormDefinitionDataElement def = dataElements[i];
        if (def.getTags().length >= 1) { // cannot have any tags in annotation form
          notifyValidatorErrorListener(ERROR_KEY2);
          return errors;
        }
      }
      // if we didn't find them in the data elements, they may be in the host elements
      for (int i=0; i < hostElements.length; i++) {
       FormDefinitionHostElement def = hostElements[i];
       if (def.getTags().length >= 1) { // cannot have any tags in annotation form
        notifyValidatorErrorListener(ERROR_KEY2);
        return errors;
        }
      }
    }

    else { //Sample Registration Forms may have tags subject to rules...
    	  
        
        // check data elements...
        for (int i = 0; i < dataElements.length; i++) {
            FormDefinitionDataElement def = dataElements[i];
            // first, determine if there is a tag. if not, that is fine...
            if ((def.getTags().length >= 1) && (def.getTags().length <=2) ) {
              // implement the rules for tags...
              DataFormDefinitionDataElement defData = (DataFormDefinitionDataElement) def;
              examineTags(def.getTags(), def.getCui(), defData.isRequired());
            }
            else if (def.getTags().length > 2) {
              addBadAttrs(def.getCui());  //cannot have more than two tags for a data element
            }
            // additional rules for multi-valued and multi-level data elements...
            // note that these rules can only be applied if the data element in
            // question is in the DET; if it is not, then do not try to apply the rules
            if (DetService.SINGLETON.getDataElementTaxonomy().getDataElement(def.getCui()) != null) {
                DetDataElement metadata = DetService.SINGLETON.getDataElementTaxonomy().getDataElement(def.getCui());
                
                if ((metadata.isMultilevelValueSet()) || (metadata.isMultivalued())) {
                    // they must have derivativeIgnores tag 
                    if (!findIgnoresTag(def.getTags())) { 
                        addMultiIgnore(def.getCui());
                    }
                    // they may not be required
                    DataFormDefinitionDataElement defData = (DataFormDefinitionDataElement) def;
                    if (defData.isRequired()) {
                        addMultiRequired(def.getCui());
                    }
                }
            }

        }
        // check host elements...
        for (int i = 0; i < hostElements.length; i++) {
            FormDefinitionHostElement def = hostElements[i];
          // first, determine if there is a tag. if not, that is fine...
          if ((def.getTags().length >= 1) && (def.getTags().length <=2) ) {
            // implement the rules for tags...
            DataFormDefinitionHostElement defData = (DataFormDefinitionHostElement) def;
            examineTags(def.getTags(), def.getHostId(), defData.isRequired());
          }
          else if (def.getTags().length > 2) {
            addBadAttrs(def.getHostId());  //cannot have more than two tags for a data element
          }  
      }
        
        
      if (!getBadAttrs().isEmpty()) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }
 
      if (!getMultiIgnore().isEmpty()) {
        notifyValidatorErrorListener(ERROR_KEY3);       
      }

      if (!getMultiRequired().isEmpty()) {
        notifyValidatorErrorListener(ERROR_KEY4);       
      }
      
      if (!getDefaultIgnore().isEmpty()) {
        notifyValidatorErrorListener(ERROR_KEY5);       
      }
      
      if (!getIgnoreRequire().isEmpty()) {
        notifyValidatorErrorListener(ERROR_KEY6);       
      }

      
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
  
  public void addBadAttrs(String value){
   _badAttrs.add(value); 
  }
  
  
  private void examineTags(Tag tags[], String label, boolean required) {
    boolean foundDerivDefaults = false;
    boolean foundDerivIgnores = false;
    boolean foundDerivInherits = false;
    for (int j = 0; j < tags.length; j++) {
      Tag tag = tags[j];
      if (tag.getType().equals(TagTypes.DERIV_DEFAULTS)) {
       foundDerivDefaults = true; 
      }
      else if (tag.getType().equals(TagTypes.DERIV_IGNORES)) {
       foundDerivIgnores = true; 
      }
      else if (tag.getType().equals(TagTypes.DERIV_INHERITS)) {
        foundDerivInherits = true; 
      }
      // checking for valid tag types...
      if (!((tag.getType() != null) && (tag.getValue() != null))) {
        addBadAttrs(label);             
      }
      else if (!(tag.getType().equals(TagTypes.DERIV_DEFAULTS) || 
                 tag.getType().equals(TagTypes.DERIV_INHERITS) ||
                 tag.getType().equals(TagTypes.DERIV_IGNORES))) {
        addBadAttrs(label);
      }
      // checking for true or false tag values -- anything else is an error
      else if (!((tag.getValue().equals("true")) || (tag.getValue().equals("false")))) {
        addBadAttrs(label);
      }
    }
    // enforce that derivativeDefaults not paired with derivativeIgnores
    if (foundDerivDefaults) {
      for (int j = 0; j < tags.length; j++) {
         Tag tag = tags[j]; 
      	 if (tag.getType().equals(TagTypes.DERIV_IGNORES)) {
          addDefaultIgnore(label);      	 	
         }
      }        
    }
    // enforce that derivativeIgnores is not found with required
    if (foundDerivIgnores && required) {
     addIgnoreRequire(label);   
    }
    
    // enforce special rules for date of collection, date of preservation, prepared by
    //  these fields may not have inherits or defaults tags (MR 9600)...also prohibit
    //  ignore tag
    if ( (label.equals(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION)) 
          || (label.equals(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION))
					|| (label.equals(ArtsConstants.ATTRIBUTE_PREPARED_BY))) {
        if (foundDerivDefaults || foundDerivInherits || foundDerivIgnores) {
         addBadAttrs(label);   
        }
    }
  }

private boolean findIgnoresTag(Tag tags[]) {
  for (int j = 0; j < tags.length; j++) {
    Tag tag = tags[j];
    if (tag.getType().equals(TagTypes.DERIV_IGNORES)) {
        return true;
    }
  }
  return false;
}
  
/**
 * @return Returns the _multiIgnore.
 */
public List getMultiIgnore() {
	return _multiIgnore;
}
/**
 * @param ignore The _multiIgnore to set.
 */
public void setMultiIgnore(List ignore) {
	_multiIgnore = ignore;
}

public void addMultiIgnore(String value){
  _multiIgnore.add(value); 
 }


/**
 * @return Returns the _multiRequired.
 */
public List getMultiRequired() {
	return _multiRequired;
}
/**
 * @param required The _multiRequired to set.
 */
public void setMultiRequired(List required) {
	_multiRequired = required;
}
public void addMultiRequired(String value){
  _multiRequired.add(value); 
 }

/**
 * @return Returns the _defaultIgnore.
 */
public List getDefaultIgnore() {
    return _defaultIgnore;
}
/**
 * @param ignore The _defaultIgnore to set.
 */
public void setDefaultIgnore(List ignore) {
    _defaultIgnore = ignore;
}

public void addDefaultIgnore(String value){
  _defaultIgnore.add(value); 
 }


/**
 * @return Returns the _defaultIgnore.
 */
public List getIgnoreRequire() {
    return _ignoreRequire;
}
/**
 * @param ignore The _defaultIgnore to set.
 */
public void setIgnoreRequire(List ignore) {
    _ignoreRequire = ignore;
}

public void addIgnoreRequire(String value){
  _ignoreRequire.add(value); 
 }

}