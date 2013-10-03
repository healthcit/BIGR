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
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSets;


public class ValidatorQueryFormDefinitionRollups extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator 
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.rollupValueSetDup";
  public final static String ERROR_KEY2 = "kc.error.formdef.rollupValueDupDispName";
  public final static String ERROR_KEY3 = "kc.error.formdef.rollupValueSetUnused";
  public final static String ERROR_KEY4 = "kc.error.formdef.rollupNameIsCui";

  
  private DataElementTaxonomy _det; 
  private QueryFormDefinition _formDef;
  private List _duplicatedRollupValueSet; 
  private List _duplicatedRollupValueDisplayNames; 
  private List _unusedRollupValueSet;
  private List _sameAsCuiRollupValueDisplayName;



  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorQueryFormDefinitionRollups v1 = (ValidatorQueryFormDefinitionRollups) v; 

      String dups;
      if (errorKey.equalsIgnoreCase(ERROR_KEY1)) {
          dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedRollupValueSet()));
          v1.addErrorMessage(ValidatorQueryFormDefinitionRollups.ERROR_KEY1, dups);
      }
      else if (errorKey.equalsIgnoreCase(ERROR_KEY2)) {
          dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedRollupValueDisplayNames()));
          v1.addErrorMessage(ValidatorQueryFormDefinitionRollups.ERROR_KEY2, dups);

      }
      else if (errorKey.equalsIgnoreCase(ERROR_KEY3)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getUnusedRollupValueSet()));
        v1.addErrorMessage(ValidatorQueryFormDefinitionRollups.ERROR_KEY3, dups);

      } 
      else if (errorKey.equalsIgnoreCase(ERROR_KEY4)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getSameAsCuiRollupValueDisplayName()));
        v1.addErrorMessage(ValidatorQueryFormDefinitionRollups.ERROR_KEY4, dups);

      } 

      
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorQueryFormDefinitionRollups</code> validator.
   */
  public ValidatorQueryFormDefinitionRollups() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY2);
    addErrorKey(ERROR_KEY3);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY3);
    addErrorKey(ERROR_KEY4);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY4);



  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();
    
    Set uniqueRollupValueSet = new HashSet();   
    List duplicatedRollupValueSet = new ArrayList();
    List duplicatedRollupValue = new ArrayList(); 
    List rollupSameAsCui = new ArrayList();
         
    QueryFormDefinition formDef = getFormDefinition();
    // start with the rollup value sets 
    QueryFormDefinitionRollupValueSets rollupValueSets =  formDef.getRollupValueSets();
    if (rollupValueSets != null) {
      QueryFormDefinitionRollupValueSet[] rollupValueSet =  rollupValueSets.getValueSets(); 
      for (int i = 0; i < rollupValueSet.length; i++) {

        String idRVS = rollupValueSet[i].getId();
        // collect the list of unique and duplicated RollupValueSet ids
        if (uniqueRollupValueSet.contains(idRVS)) {
          duplicatedRollupValueSet.add(idRVS);
        }
        else {
          uniqueRollupValueSet.add(idRVS);
        }
        
        //collect the list of unique and duplicated RollupValue ids in each RollupValue set
        Set uniqueRollupValue = new HashSet();          
        QueryFormDefinitionRollupValue[] rollupValue = rollupValueSet[i].getValues();
        for (int j = 0; j < rollupValue.length; j++) {          
          String idRV = rollupValue[j].getDisplayName();
          // determine if there are duplicate rollup Value ids
          //  within this rollup value set
          if (uniqueRollupValue.contains(idRV)) {
            duplicatedRollupValue.add(idRV);
          }
          else {
            uniqueRollupValue.add(idRV);
          } 
          
          //also want to make sure rollup value display names are
          // not the same as CUIs; this likely would cause internal grief
          if (GbossFactory.getInstance().isCode(idRV)) {
            rollupSameAsCui.add(idRV);
          }
          
        } // for j
      } // for i      
    }
    
    // determine if there are duplicate rollup Value set ids
    if (!duplicatedRollupValueSet.isEmpty()) {
      setDuplicatedRollupValueSet(duplicatedRollupValueSet);
      notifyValidatorErrorListener(ERROR_KEY1);
    }
    // determine if there are duplicate rollup Value ids
    //  within any of the rollup value set
    if (!duplicatedRollupValue.isEmpty()) {
      setDuplicatedRollupValueDisplayNames(duplicatedRollupValue);
      notifyValidatorErrorListener(ERROR_KEY2);
    } 

    Set allDataElementValuesets = new HashSet();
    List unusedValueSets = new ArrayList();
    // determine if any of the rollup value sets were not used
    // need to loop thru data elements to do this...
    FormDefinitionDataElement[] dataElements = getFormDefinition().getDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      // for each data element...
      QueryFormDefinitionDataElement def = (QueryFormDefinitionDataElement) dataElements[i];
      
      // ...the rollup value set used 
      allDataElementValuesets.add(def.getRollupValueSetId());
    }
    
    // now, compare the rollup value sets used to those specified
    Iterator valSets = uniqueRollupValueSet.iterator();
    while (valSets.hasNext()) {
      String valSetName = (String) valSets.next();
      if (!allDataElementValuesets.contains(valSetName)) {
        unusedValueSets.add(valSetName);
      }
    }
 
    // if there are unused rollup value sets, then note the error... 
    if (!unusedValueSets.isEmpty()) {
      setUnusedRollupValueSet(unusedValueSets);
      notifyValidatorErrorListener(ERROR_KEY3);
    }   

    // determine if there are rollup values whose display names
    // are the same as any CUI
    if (!rollupSameAsCui.isEmpty()) {
      setSameAsCuiRollupValueDisplayName(rollupSameAsCui);
      notifyValidatorErrorListener(ERROR_KEY4);
    }


    
    return errors;
  }

  /**
   * @return Returns the duplicatedRollupValueDisplayNames.
   */
  public List getDuplicatedRollupValueDisplayNames() {
    return _duplicatedRollupValueDisplayNames;
  }
  /**
   * @param duplicatedRollupValueDisplayNames The duplicatedRollupValueDisplayNames to set.
   */
  public void setDuplicatedRollupValueDisplayNames(List duplicatedRollupValueDisplayNames) {
    _duplicatedRollupValueDisplayNames = duplicatedRollupValueDisplayNames;
  }
  /**
   * @return Returns the duplicatedRollupValueSet.
   */
  public List getDuplicatedRollupValueSet() {
    return _duplicatedRollupValueSet;
  }
  /**
   * @param duplicatedRollupValueSet The duplicatedRollupValueSet to set.
   */
  public void setDuplicatedRollupValueSet(List duplicatedRollupValueSet) {
    _duplicatedRollupValueSet = duplicatedRollupValueSet;
  }
  /**
   * @return Returns the sameAsCuiRollupValueDisplayName.
   */
  public List getSameAsCuiRollupValueDisplayName() {
    return _sameAsCuiRollupValueDisplayName;
  }
  /**
   * @param sameAsCuiRollupValueDisplayName The sameAsCuiRollupValueDisplayName to set.
   */
  public void setSameAsCuiRollupValueDisplayName(List sameAsCuiRollupValueDisplayName) {
    _sameAsCuiRollupValueDisplayName = sameAsCuiRollupValueDisplayName;
  }
  /**
   * @return Returns the unusedRollupValueSet.
   */
  public List getUnusedRollupValueSet() {
    return _unusedRollupValueSet;
  }
  /**
   * @param unusedRollupValueSet The unusedRollupValueSet to set.
   */
  public void setUnusedRollupValueSet(List unusedRollupValueSet) {
    _unusedRollupValueSet = unusedRollupValueSet;
  }

  public QueryFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(QueryFormDefinition definition) {
    _formDef = definition;
  }

  public DataElementTaxonomy getDet() {
    return _det;
  }

  public void setDet(DataElementTaxonomy det) {
    _det = det;
  } 
  
  
  

}

