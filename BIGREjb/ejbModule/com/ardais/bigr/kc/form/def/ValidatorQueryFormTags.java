package com.ardais.bigr.kc.form.def;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.validation.ValidatorValueInCollection;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;


public class ValidatorQueryFormTags extends AbstractValidator {  

  public final static String ERROR_KEY1 = "errors.cirTagRequiredForQuery";
  public final static String ERROR_KEY2 = "errors.cirOnlyOneTagForQuery";
  
  private QueryFormDefinition _formDefinition;
  
  private List _noTags = new ArrayList();
  private List _badAttrs = new ArrayList();
  
 
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorQueryFormTags v1 = (ValidatorQueryFormTags) v;
      String dups;
      if (errorKey.equals(ERROR_KEY1)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getBadAttrs()));
        v1.addErrorMessage(ValidatorQueryFormTags.ERROR_KEY1, dups);
      }
      else if (errorKey.equals(ERROR_KEY2)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getNoTags()));
        v1.addErrorMessage(ValidatorQueryFormTags.ERROR_KEY2, dups);
      }      
      return true;
    }
  }
  

  /**
   * Creates a new <code>ValidatorDataFormDefinitionDataElements</code> validator.
   */
  public ValidatorQueryFormTags() {
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
    
    List noTags = new ArrayList();
    List badAttrs = new ArrayList();
    
    BtxActionErrors errors = getActionErrors();

    QueryFormDefinition formDef = getFormDefinition();
    // must loop thru all data elements and examine the tags...
    QueryFormDefinitionDataElement[] dataElements = formDef.getQueryDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      QueryFormDefinitionDataElement def = dataElements[i];
      // first, make sure there is a tag...
      if (def.getTags().length == 0) {
        noTags.add(def.getCui());
      }
      else if (def.getTags().length > 1) {
        noTags.add(def.getCui());
      }
      else {  // if there is one tag, apply rules
        // type must be correct
        Tag tags[] = def.getTags();
        if (!(tags[0].getType().equals(TagTypes.DOMAIN_OBJECT))) {
          badAttrs.add(def.getCui());
        }
        else { // check value field
          String tagValue = tags[0].getValue();
          if (!BigrFormDefinition.isValidObjectType(tagValue)) {
            badAttrs.add(def.getCui());            
          }
        }
      }
      
    }

    
    if (!badAttrs.isEmpty()) {
      setBadAttrs(badAttrs);
      notifyValidatorErrorListener(ERROR_KEY1);
    }
    
    if (!noTags.isEmpty()) {
      setNoTags(noTags);
      notifyValidatorErrorListener(ERROR_KEY2);
    }

    return errors;
  }
  
  
 
  /**
   * @return Returns the noTags.
   */
  public List getNoTags() {
    return _noTags;
  }
  /**
   * @param noTags The noTags to set.
   */
  public void setNoTags(List noTags) {
    _noTags = noTags;
  }
  /**
   * @return Returns the formDefinition.
   */
  public QueryFormDefinition getFormDefinition() {
    return _formDefinition;
  }
  /**
   * @param formDefinition The formDefinition to set.
   */
  public void setFormDefinition(QueryFormDefinition formDefinition) {
    _formDefinition = formDefinition;
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
