package com.gulfstreambio.kc.form.def;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.ConversionException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.util.VariablePrecisionDate;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetElementBase;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.ValidatorTypeSafe;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionAdeElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueTypes;

public class ValidatorQueryFormDefinitionDataElements extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are duplicated data
   * elements. 
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.elementDuplicated";
  public final static String ERROR_KEY2 = "kc.error.formdef.dataElementDispNameDup";
  public final static String ERROR_KEY3 = "kc.error.formdef.dataElemNotInBroad";
  public final static String ERROR_KEY4 = "kc.error.formdef.cvOnlyEqualOrNotEqual";
  public final static String ERROR_KEY5 = "kc.error.formdef.valueBadDatatype";
  public final static String ERROR_KEY6 = "kc.error.formdef.elemNotInRollup";
  public final static String ERROR_KEY8 = "kc.error.formdef.valCompEqWithOr";
  public final static String ERROR_KEY9 = "kc.error.formdef.valCompNeWithAnd";
  public final static String ERROR_KEY10 = "kc.error.formdef.valCompEqNotNe";
  public final static String ERROR_KEY11 = "kc.error.formdef.valCompLessAndGreater";
  public final static String ERROR_KEY12 = "kc.error.formdef.operatorRequiredMultValComp";
  public final static String ERROR_KEY13 = "kc.error.forminst.yearMustBeYYYY";
  
  
  private DataElementTaxonomy _det; 
  private QueryFormDefinition _formDef;
  private List _duplicatedValues; 
  private List _duplicatedDisplayNames; 
  private List _valuesNotinBroadValueSet;
  private List _mismatchDatatypes;
  private List _invalidOperators;
  private List _valuesNotInRollup;
  private List _invalidYearFormat;
  private List valuesNotInBroad = new ArrayList(); 
  private List mismatchDatatypes = new ArrayList();
  private List invalidOperators = new ArrayList();
  private List valuesNotInRollup = new ArrayList();
  private List invalidYearFormat = new ArrayList();
  

  private List _equalsWithOr;
  private List _notEqualsWithAnd;
  private List _eqNotNe;
  private List _lessWithGreater; 
  private List _requireOperator;
  

  private List equalsWithOr = new ArrayList();
  private List notEqualsWithOr = new ArrayList();
  private List eqNotNe = new ArrayList();
  private List lessWithGreater = new ArrayList();
  private List requireOperator = new ArrayList();
  
  private boolean foundEqual;
  private boolean foundNotEqual;
  private boolean foundLessThan;
  private boolean foundGreaterThan;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorQueryFormDefinitionDataElements v1 = (ValidatorQueryFormDefinitionDataElements) v;
      String dups;
      if (errorKey.equals(ERROR_KEY1)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedValues()));
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY1, dups);
      }
      else if (errorKey.equals(ERROR_KEY2)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedDisplayNames()));
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY2, dups);
      }
      else if (errorKey.equals(ERROR_KEY3)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getValuesNotinBroadValueSet()));
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY3, dups);
      }   
      else if (errorKey.equals(ERROR_KEY4)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getInvalidOperators()));
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY4, dups);
      } 
      else if (errorKey.equals(ERROR_KEY5)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getMismatchDatatypes()));
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY5, dups);
      } 
      else if (errorKey.equals(ERROR_KEY6)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getValuesNotInRollup()));
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY6, dups);
      }

      else if (errorKey.equalsIgnoreCase(ERROR_KEY8)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getEqualsWithOr()));
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY8, dups);

      }
      else if (errorKey.equalsIgnoreCase(ERROR_KEY9)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getNotEqualsWithAnd()));        
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY9, dups);

      } 
      else if (errorKey.equalsIgnoreCase(ERROR_KEY10)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getEqNotNe()));                
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY10, dups);

      } 
      else if (errorKey.equalsIgnoreCase(ERROR_KEY11)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getLessWithGreater()));                        
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY11, dups);

      }

      else if (errorKey.equalsIgnoreCase(ERROR_KEY12)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getRequireOperator()));                        
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY12, dups);

      } 
 
      else if (errorKey.equalsIgnoreCase(ERROR_KEY13)) {
        dups = ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getInvalidYearFormat()));                        
        v1.addErrorMessage(ValidatorQueryFormDefinitionDataElements.ERROR_KEY13, dups);

      } 
      
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDataFormDefinitionDataElements</code> validator.
   */
  public ValidatorQueryFormDefinitionDataElements() {
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
    addErrorKey(ERROR_KEY8);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY8);
    addErrorKey(ERROR_KEY9);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY9);
    addErrorKey(ERROR_KEY10);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY10);
    addErrorKey(ERROR_KEY11);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY11);
    addErrorKey(ERROR_KEY12);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY12);   
    addErrorKey(ERROR_KEY13);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY13);    
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();
    
    Set uniqueCuis = new HashSet();
    List duplicatedCuis = new ArrayList();
    
    Set uniqueDisplayNames = new HashSet();
    List duplicatedDisplayNames = new ArrayList();
    
       
    KCQueryFormDataElementValidationService deService =
      new KCQueryFormDataElementValidationService();
    deService.setDet(getDet());
    deService.setCheckDataElementInDet(true);
    deService.setCheckSupportedDatatypes(true);

    QueryFormDefinition formDef = getFormDefinition();
    QueryFormDefinitionDataElement[] dataElements = formDef.getQueryDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      QueryFormDefinitionDataElement def = dataElements[i];
      deService.setDataElementDefinition(def);
      errors.addErrors(deService.validate());
      String cui = def.getCui();
      
      // collect the list of unique and duplicated CUIs
      if (uniqueCuis.contains(cui)) {
        duplicatedCuis.add(cui);
      }
      else {
        uniqueCuis.add(cui);
      }
      
      // determine if any of the duplicated display names
      // are within the same category.  if so, this is an error.    
      String displayName = def.getDisplayName();
      if (displayName != null) {        
        QueryFormDefinitionCategory parentCategory = def.getParentQueryCategory();
        String categoryName = (parentCategory == null) ? "" : parentCategory.getDisplayName();
        // store as categoryName.displayName to determine duplicates
        String possibleDup = categoryName + "." + displayName; 
        
        //collect the list of duplicated display names...
        if (uniqueDisplayNames.contains(possibleDup)) {
          duplicatedDisplayNames.add(possibleDup);
        }
        else {
          uniqueDisplayNames.add(possibleDup);
        }
      }
    
      
      // now, we are going to check value set rules related
      // to the data elements...
      
      // start with the DET information for this data element
      DetDataElement detElem = this.getDet().getDataElement(cui);

      // get the rollup value set for the data element, if any...
      QueryFormDefinitionRollupValueSet rollupValueSet = def.getRollupValueSet();
      if (rollupValueSet != null) {
       QueryFormDefinitionRollupValue[] rollupValues = rollupValueSet.getValues();
        for (int a = 0; a < rollupValues.length; a++) {   
          // get the value set for each rollup value
          QueryFormDefinitionValueSet valueSet = rollupValues[a].getValueSet();
          if (valueSet != null) {
              // check the value set rules
              valueSetValidation(detElem, valueSet);
              // check the value comparison rules
              valueSetValueComparisonRules(valueSet, rollupValues[a].getDisplayName());
            } 
          }   
        }

      // get the value set for the data element, if any...
      QueryFormDefinitionValueSet valueSet = def.getValueSet();
      if (valueSet != null) {
        if (rollupValueSet == null) {
          // if there is not a rollup value set, then apply 
          // value set validation that is similar to roll up
          // value set validation
          valueSetValidation(detElem, valueSet);
        }
          // if there are both rollup value sets and value sets for the data element,
          // then the validation is different.
          // all the values in the data elem value set must come from
          // the display names of the rollup value set
        else  {
            valueSetInRollup(valueSet, rollupValueSet);
        }
        // check the value comparison rules
        valueSetValueComparisonRules(valueSet, detElem.getCui());
      }
      
      // also need to check value sets in ADEs, if there are any
      if (def.getAdeElements().length > 0) {
        QueryFormDefinitionAdeElement[] adeElems = def.getAdeElements();
        for (int b = 0; b < adeElems.length; b++) {
          QueryFormDefinitionValueSet adeValueSet = adeElems[b].getValueSet();    
          if (adeValueSet != null) {
            // check the value comparison rules
            valueSetValueComparisonRules(adeValueSet, adeElems[b].getCui());
            // if adeElement is CV, check additional rules
            DetAdeElement adeDetElem = this.getDet().getAdeElement(adeElems[b].getCui());
            valueSetValidation(adeDetElem, adeValueSet);
          }
        }
      }
      
    } // for i, all data elements...
      
    // cannot have duplicated CUIs; this is an error
    if (!duplicatedCuis.isEmpty()) {
      setDuplicatedValues(duplicatedCuis);
      notifyValidatorErrorListener(ERROR_KEY1);
    }  
      
    // cannot have duplicated display names; this is an error
    if (!duplicatedDisplayNames.isEmpty()) {
      setDuplicatedDisplayNames(duplicatedDisplayNames);
      notifyValidatorErrorListener(ERROR_KEY2);
    }   
    
    // cannot have value set values that are not in the broad value set
    if (!valuesNotInBroad.isEmpty()) {
        setValuesNotinBroadValueSet(valuesNotInBroad);
        notifyValidatorErrorListener(ERROR_KEY3);
        }  
        
    // were there any invalid operators in a CV data element
    if (!invalidOperators.isEmpty()) {
        setInvalidOperators(invalidOperators);
        notifyValidatorErrorListener(ERROR_KEY4);            
        }    
    
    // cannot have value sets with values of a different datatype than the data element
    if (!mismatchDatatypes.isEmpty()) {
      setMismatchDatatypes(mismatchDatatypes);
      notifyValidatorErrorListener(ERROR_KEY5);
    } 
    
    // cannot have values within data element value set that are not in
    //  element rollup value set, if it exists
    if (!valuesNotInRollup.isEmpty()) {
      setValuesNotInRollup(valuesNotInRollup);
      notifyValidatorErrorListener(ERROR_KEY6);
    }
    
    
    //  may only have valueComparison EQ with value set operator OR
    if (!equalsWithOr.isEmpty()) {
      setEqualsWithOr(equalsWithOr);
      notifyValidatorErrorListener(ERROR_KEY8);
    }

    //  may only have valueComparison NE with value set operator AND
    if (!notEqualsWithOr.isEmpty()) {
      setNotEqualsWithAnd(notEqualsWithOr);
      notifyValidatorErrorListener(ERROR_KEY9);
    }
    
    //  may not mix EQ and NE
    if (!eqNotNe.isEmpty()) {
      setEqNotNe(eqNotNe);
      notifyValidatorErrorListener(ERROR_KEY10);
    }  

    // LT or LE may only mix with GT or GE
    if (!lessWithGreater.isEmpty()) {
      setLessWithGreater(lessWithGreater);
      notifyValidatorErrorListener(ERROR_KEY11);
    }
    
    // value set operator required if more than
    //  one value comparison tags
    if (!requireOperator.isEmpty()) {
      setRequireOperator(requireOperator);
      notifyValidatorErrorListener(ERROR_KEY12);
    }    

    // value set values with dates must be in valid format
    if (!invalidYearFormat.isEmpty()) {
      setInvalidYearFormat(invalidYearFormat);
      notifyValidatorErrorListener(ERROR_KEY13);
    }
    
    
    return errors;
  }

  /* 
   * Enforce the rules for value set element datatypes and DET rules
   */
  private void valueSetValidation(DetElementBase elem, QueryFormDefinitionValueSet valueSet) {

        QueryFormDefinitionValue[] values = valueSet.getValues();
        for (int b = 0; b < values.length; b++ ) {
          if (values[b].getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
            QueryFormDefinitionValueComparison vComp = (QueryFormDefinitionValueComparison) values[b];

            // for CV datatypes...
            if (elem.isDatatypeCv()) {
              DetValueSet broadValueSet = elem.getBroadValueSet();
              
              // ok, verify that this in the broad value set...
              if (!(broadValueSet.containsValue(( vComp.getValue())))) {
                  valuesNotInBroad.add(vComp.getValue());
                  }
              // check the comparison operator
              if (!((vComp.getOperator().equals(com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison.OPERATOR_EQ) 
                  || (vComp.getOperator().equals(com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison.OPERATOR_NE))))) {
                  invalidOperators.add(vComp.getValue());
                  }                        
              }         
            
            // ...for other datatypes, must check that 
            // all values specified in value set are appropriate for datatype        
            else if (elem.isDatatypeFloat()) {
              if (!ApiFunctions.isFloat(vComp.getValue())) {
                mismatchDatatypes.add(elem.getCui());
              }
            }
            else if (elem.isDatatypeInt()) {
              if (!ApiFunctions.isInteger(vComp.getValue())) {
                mismatchDatatypes.add(elem.getCui());
              }                  
            }
            else if ( (elem.isDatatypeDate()) || (elem.isDatatypeVpd()) ) {
              if (!isVpd(vComp.getValue())) {
                mismatchDatatypes.add(elem.getCui());
                }
              else { // all dates must have year in YYYY format
                ValidatorTypeSafe vSafe = new ValidatorTypeSafe();
                if (!(vSafe.checkYYYYDate(vComp.getValue()))) {
                  invalidYearFormat.add(elem.getCui());
                  }
                }
              }
          } // end if QueryFormDefinitionValueTypes.COMPARISON
        } // for b
            
  }

  /*
   *  Enforce the rules for value set comparison tags
   */
  private void valueSetValueComparisonRules (QueryFormDefinitionValueSet valueSet, String displayName) {
    String operator = valueSet.getOperator();
    QueryFormDefinitionValue[] values = valueSet.getValues();
    if (values.length > 1) {
      // if there is more than one value, then there must be an operator
      if (operator == null) {
        requireOperator.add(displayName);
      }
      // process all the detailed rules...
      foundEqual = false;
      foundNotEqual = false;
      foundLessThan = false;
      foundGreaterThan = false;
      for (int k=0; k < values.length; k++) {
        if (values[k].getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
          QueryFormDefinitionValueComparison vComp = (QueryFormDefinitionValueComparison) values[k];
          if (operator != null) {
            // may only have valueComparison EQ with value set operator OR
            if ( (vComp.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_EQ)) &&
                (!(operator.equals(QueryFormDefinitionValueSet.OPERATOR_OR)))  ) {
                if (!equalsWithOr.contains(displayName))  equalsWithOr.add(displayName);
            }
            // may only have valueComparison NE with value set operator AND
            if ( (vComp.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_NE)) &&
                (!(operator.equals(QueryFormDefinitionValueSet.OPERATOR_AND)))  ) {
                if (!notEqualsWithOr.contains(displayName)) notEqualsWithOr.add(displayName);
            } 
          }
          // may not mix EQ and NE
          if (vComp.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_EQ)) foundEqual = true;
          else if (vComp.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_NE)) foundNotEqual = true;
          // LT or LE may only mix with GT or GE
          if (vComp.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_LT) || 
              vComp.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_LE) ) {
            foundLessThan = true;
          }
          if (vComp.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_GT) || 
                vComp.getOperator().equals(QueryFormDefinitionValueComparison.OPERATOR_GE) ) {
              foundGreaterThan = true;  
          }
        }

      } // for k
      
      if (foundEqual && foundNotEqual) {
        eqNotNe.add(displayName);
      }

      // LT or LE may only mix with GT or GE and only 2 comparisons            
      if ( (foundLessThan) && (!foundGreaterThan))  {
        if (values.length >= 2)  lessWithGreater.add(displayName);
      }
      
      // LT or LE may only mix with GT or GE and only 2 comparisons            
      if ( (foundLessThan) && (foundGreaterThan))  {
        if (values.length != 2)  lessWithGreater.add(displayName);
      }
      
      //  GT or GE may only mix with LT or LE and only 2 comparisons            
      if ( (foundGreaterThan) && (!foundLessThan)) {
        if (values.length >= 2)  lessWithGreater.add(displayName);
      }
      
      //  GT or GE may only mix with LT or LE and only 2 comparisons            
      if ( (foundGreaterThan) && (foundLessThan)) {
        if (values.length != 2)  lessWithGreater.add(displayName);
      }      
      
    }  // values.length > 1
  }
  
  /*
   * Enforce the rule that the data element value set must be one of the
   *  display names from the rollup value set
   */
  private void valueSetInRollup(QueryFormDefinitionValueSet valueSet, QueryFormDefinitionRollupValueSet rollupValueSet) {

    if ((valueSet == null) || (rollupValueSet == null))
      return;
    
    QueryFormDefinitionValue[] values = valueSet.getValues();  
    QueryFormDefinitionRollupValue[] rollups = rollupValueSet.getValues();
    
    // for each value in the element value set...
    for (int b = 0; b < values.length; b++ ) {
      if (values[b].getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
        QueryFormDefinitionValueComparison vComp = (QueryFormDefinitionValueComparison) values[b];
            boolean foundIt = false;
            for (int c=0; c < rollups.length; c++) {
                // ...it must be one of the display names in the rollup value set
                if (rollups[c].getDisplayName().equals(vComp.getValue())) {
                  foundIt = true;
                }
            } 
            if (!foundIt) {
              valuesNotInRollup.add(vComp.getValue());
            }
          }
        }
    return;
    }

 
  private boolean isVpd (String s) {
    if (s == null) return false;
    try {
      VariablePrecisionDate date = new VariablePrecisionDate(s);
      return true;
    }
    catch (ConversionException e) {
      return false;
    }
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

  public List getDuplicatedValues() {
    return _duplicatedValues;
  }

  private void setDuplicatedValues(List values) {
    _duplicatedValues = values;
  }

  /**
   * @return Returns the duplicatedDisplayNames.
   */
  public List getDuplicatedDisplayNames() {
    return _duplicatedDisplayNames;
  }
  /**
   * @param duplicatedDisplayNames The duplicatedDisplayNames to set.
   */
  public void setDuplicatedDisplayNames(List duplicatedDisplayNames) {
    _duplicatedDisplayNames = duplicatedDisplayNames;
  }
  /**
   * @return Returns the rollupValuesNotinBroadValueSet.
   */
  public List getValuesNotinBroadValueSet() {
    return _valuesNotinBroadValueSet;
  }
  /**
   * @param rollupValuesNotinBroadValueSet The rollupValuesNotinBroadValueSet to set.
   */
  public void setValuesNotinBroadValueSet(List rollupValuesNotinBroadValueSet) {
    _valuesNotinBroadValueSet = rollupValuesNotinBroadValueSet;
  }
  /**
   * @return Returns the mismatchDatatypes.
   */
  public List getMismatchDatatypes() {
    return _mismatchDatatypes;
  }
  /**
   * @param mismatchDatatypes The mismatchDatatypes to set.
   */
  public void setMismatchDatatypes(List mismatchDatatypes) {
    _mismatchDatatypes = mismatchDatatypes;
  }
  
  /**
   * @return Returns the invalidOperators.
   */
  public List getInvalidOperators() {
    return invalidOperators;
  }
  /**
   * @param invalidOperators The invalidOperators to set.
   */
  public void setInvalidOperators(List invalidOperators) {
    this.invalidOperators = invalidOperators;
  }
  /**
   * @return Returns the valuesNotInRollup.
   */
  public List getValuesNotInRollup() {
    return _valuesNotInRollup;
  }
  /**
   * @param valuesNotInRollup The valuesNotInRollup to set.
   */
  public void setValuesNotInRollup(List valuesNotInRollup) {
    _valuesNotInRollup = valuesNotInRollup;
  }
  /**
   * @return Returns the eqNotNe.
   */
  public List getEqNotNe() {
    return eqNotNe;
  }
  /**
   * @param eqNotNe The eqNotNe to set.
   */
  public void setEqNotNe(List eqNotNe) {
    this.eqNotNe = eqNotNe;
  }
  /**
   * @return Returns the equalsWithOr.
   */
  public List getEqualsWithOr() {
    return equalsWithOr;
  }
  /**
   * @param equalsWithOr The equalsWithOr to set.
   */
  public void setEqualsWithOr(List equalsWithOr) {
    this.equalsWithOr = equalsWithOr;
  }
  /**
   * @return Returns the foundEqual.
   */
  public boolean isFoundEqual() {
    return foundEqual;
  }
  /**
   * @param foundEqual The foundEqual to set.
   */
  public void setFoundEqual(boolean foundEqual) {
    this.foundEqual = foundEqual;
  }
  /**
   * @return Returns the foundGreaterThan.
   */
  public boolean isFoundGreaterThan() {
    return foundGreaterThan;
  }
  /**
   * @param foundGreaterThan The foundGreaterThan to set.
   */
  public void setFoundGreaterThan(boolean foundGreaterThan) {
    this.foundGreaterThan = foundGreaterThan;
  }
  /**
   * @return Returns the foundLessThan.
   */
  public boolean isFoundLessThan() {
    return foundLessThan;
  }
  /**
   * @param foundLessThan The foundLessThan to set.
   */
  public void setFoundLessThan(boolean foundLessThan) {
    this.foundLessThan = foundLessThan;
  }
  /**
   * @return Returns the foundNotEqual.
   */
  public boolean isFoundNotEqual() {
    return foundNotEqual;
  }
  /**
   * @param foundNotEqual The foundNotEqual to set.
   */
  public void setFoundNotEqual(boolean foundNotEqual) {
    this.foundNotEqual = foundNotEqual;
  }
  /**
   * @return Returns the lessWithGreater.
   */
  public List getLessWithGreater() {
    return lessWithGreater;
  }
  /**
   * @param lessWithGreater The lessWithGreater to set.
   */
  public void setLessWithGreater(List lessWithGreater) {
    this.lessWithGreater = lessWithGreater;
  }
  /**
   * @return Returns the notEqualsWithOr.
   */
  public List getNotEqualsWithOr() {
    return notEqualsWithOr;
  }
  /**
   * @param notEqualsWithOr The notEqualsWithOr to set.
   */
  public void setNotEqualsWithOr(List notEqualsWithOr) {
    this.notEqualsWithOr = notEqualsWithOr;
  }
  /**
   * @return Returns the valuesNotInBroad.
   */
  public List getValuesNotInBroad() {
    return valuesNotInBroad;
  }
  /**
   * @param valuesNotInBroad The valuesNotInBroad to set.
   */
  public void setValuesNotInBroad(List valuesNotInBroad) {
    this.valuesNotInBroad = valuesNotInBroad;
  }
  
  /**
   * @return Returns the notEqualsWithAnd.
   */
  public List getNotEqualsWithAnd() {
    return _notEqualsWithAnd;
  }
  /**
   * @param notEqualsWithAnd The notEqualsWithAnd to set.
   */
  public void setNotEqualsWithAnd(List notEqualsWithAnd) {
    _notEqualsWithAnd = notEqualsWithAnd;
  }
  
  /**
   * @return Returns the requireOperator.
   */
  public List getRequireOperator() {
    return _requireOperator;
  }
  /**
   * @param requireOperator The requireOperator to set.
   */
  public void setRequireOperator(List requireOperator) {
    _requireOperator = requireOperator;
  }

  /**
   * @return Returns the invalidYearFormat.
   */
  public List getInvalidYearFormat() {
    return _invalidYearFormat;
  }
  /**
   * @param invalidYearFormat The invalidYearFormat to set.
   */
  public void setInvalidYearFormat(List invalidYearFormat) {
    _invalidYearFormat = invalidYearFormat;
  }
}

