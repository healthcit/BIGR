package com.ardais.bigr.query.kc.filters;

import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.generator.KcQueryOperators;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.validation.ValidatorFloat;
import com.ardais.bigr.validation.ValidatorInt;
import com.ardais.bigr.validation.ValidatorVpd;
import com.gulfstreambio.kc.det.DatabaseElement;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.ValidatorElementValueInBroadValueSet;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.util.KcUtils;

public abstract class FilterKc extends Filter {

  private static final String KEY_PREFIX = "kc.";
  
  private static final Map _comparisonOperators;
  static {
    _comparisonOperators = new HashMap();
    _comparisonOperators.put(KcQueryOperators.EQ, "=");
    _comparisonOperators.put(KcQueryOperators.NE, "<>");
    _comparisonOperators.put(KcQueryOperators.LT, "<");
    _comparisonOperators.put(KcQueryOperators.LE, "<=");
    _comparisonOperators.put(KcQueryOperators.GT, ">");
    _comparisonOperators.put(KcQueryOperators.GE, ">=");   
  }

  private static final Map _rangeOperatorsOr;
  static {
    _rangeOperatorsOr = new HashMap();
    _rangeOperatorsOr.put(KcQueryOperators.LT + KcQueryOperators.GT, KcQueryOperators.ORLTGT);
    _rangeOperatorsOr.put(KcQueryOperators.GT + KcQueryOperators.LT, KcQueryOperators.ORGTLT);
    _rangeOperatorsOr.put(KcQueryOperators.LT + KcQueryOperators.GE, KcQueryOperators.ORLTGE);
    _rangeOperatorsOr.put(KcQueryOperators.GE + KcQueryOperators.LT, KcQueryOperators.ORGELT);
    _rangeOperatorsOr.put(KcQueryOperators.LE + KcQueryOperators.GT, KcQueryOperators.ORLEGT);
    _rangeOperatorsOr.put(KcQueryOperators.GT + KcQueryOperators.LE, KcQueryOperators.ORGTLE);
    _rangeOperatorsOr.put(KcQueryOperators.LE + KcQueryOperators.GE, KcQueryOperators.ORLEGE);
    _rangeOperatorsOr.put(KcQueryOperators.GE + KcQueryOperators.LE, KcQueryOperators.ORGELE);
  }

  private static final Map _rangeOperatorsAnd;
  static {
    _rangeOperatorsAnd = new HashMap();
    _rangeOperatorsAnd.put(KcQueryOperators.LT + KcQueryOperators.GT, KcQueryOperators.ANDLTGT);
    _rangeOperatorsAnd.put(KcQueryOperators.GT + KcQueryOperators.LT, KcQueryOperators.ANDGTLT);
    _rangeOperatorsAnd.put(KcQueryOperators.LT + KcQueryOperators.GE, KcQueryOperators.ANDLTGE);
    _rangeOperatorsAnd.put(KcQueryOperators.GE + KcQueryOperators.LT, KcQueryOperators.ANDGELT);
    _rangeOperatorsAnd.put(KcQueryOperators.LE + KcQueryOperators.GT, KcQueryOperators.ANDLEGT);
    _rangeOperatorsAnd.put(KcQueryOperators.GT + KcQueryOperators.LE, KcQueryOperators.ANDGTLE);
    _rangeOperatorsAnd.put(KcQueryOperators.LE + KcQueryOperators.GE, KcQueryOperators.ANDLEGE);
    _rangeOperatorsAnd.put(KcQueryOperators.GE + KcQueryOperators.LE, KcQueryOperators.ANDGELE);
  }

  private static int _uniqueIdCounter = 0;

  private DetElement _detElement;
  private QueryFormDefinitionDataElement _dataElement;
  private DatabaseElement _databaseElement;
  private DetElement _detAdeElement;
  private DatabaseElement _databaseAdeElement;
  private String _domainObjectType;
  
  /**
   * @param dataElement
   */
  public FilterKc(QueryFormDefinitionDataElement dataElement) {
    super(KEY_PREFIX + dataElement.getCui());
    initialize(dataElement, null);
  }
  
  /**
   * @param dataElement
   */
  public FilterKc(QueryFormDefinitionDataElement dataElement, DetElement adeElement) {
    super(KEY_PREFIX + dataElement.getCui() + "." + adeElement.getCui());
    initialize(dataElement, adeElement);
  }

  private void initialize(QueryFormDefinitionDataElement dataElement, DetElement adeElement) {
    _dataElement = dataElement;
    _detElement = 
      DetService.SINGLETON.getDataElementTaxonomy().getDataElement(dataElement.getCui());
    _databaseElement = _detElement.getDatabaseElement();
    _domainObjectType = getDomainObjectType(dataElement);
    if (adeElement != null) {
      _detAdeElement = adeElement;
      _databaseAdeElement = _detAdeElement.getDatabaseElement();      
    }
  }
  
  protected QueryFormDefinitionDataElement getQueryFormDefinitionDataElement() {
    return _dataElement;
  }
  
  protected DetElement getDetDataElement() {
    return _detElement;
  }
  
  protected DetElement getDetAdeElement() {
    return _detAdeElement;
  }
  
  protected DatabaseElement getDatabaseDataElement() {
    return _databaseElement;
  }
  
  protected DatabaseElement getDatabaseAdeElement() {
    return _databaseAdeElement;
  }
  
  protected String getDomainObjectType() {
    return _domainObjectType;
  }
  
  protected String getElementDisplay() {
    StringBuffer buf = new StringBuffer(64);
    buf.append(KcUtils.getDescription(getQueryFormDefinitionDataElement()));
    DetElement detElement = getDetAdeElement();
    if (detElement != null) {
      buf.append(": ");
      buf.append(detElement.getDescription());
    }
    return buf.toString();      
  }
  
  protected String getValueDisplay(String value) {
    DetElement detElement = getDetAdeElement();
    if (detElement == null) {
      detElement = getDetDataElement();      
    }
    return (detElement.isDatatypeCv()) ?
        DetService.SINGLETON.getDataElementTaxonomy().getCuiDescription(value) : value;
  }
  
  protected static String getComparisonOperatorDisplayName(String operator) {
    return (String)_comparisonOperators.get(operator);
  }

  protected static String determineRangeOperator(String logOp, String op1, String op2) {
    if (KcQueryOperators.OR.equals(logOp)) {
      return (String) _rangeOperatorsOr.get(op1 + op2);
    }
    else {
      return (String) _rangeOperatorsAnd.get(op1 + op2);
    }
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#appendVals(java.lang.StringBuffer)
   */
  protected void appendVals(StringBuffer buf) {
  }

  private String getDomainObjectType(QueryFormDefinitionDataElement dataElement) {
    String domainObjectType = null; 
    Tag[] tags = dataElement.getTags();
    for (int i = 0; i < tags.length; i++) {
      Tag tag = tags[i];
      if (tag.getType().equals(TagTypes.DOMAIN_OBJECT)) {
        domainObjectType = tag.getValue();
      }
    }
    if (domainObjectType == null) {      
      throw new ApiException("Could not determine domain object type of data element " + dataElement.getCui()+ " in query form definition.");
    }
    return domainObjectType;
  }
  
  /**
   * Populates the data element corresponding to this filter in the specified query form with the
   * values of this filter.  If the data element cannot be found in the query form, then this
   * method silently does nothing.
   * 
   * @param queryForm  the <code>QueryFormDefinition</code> that holds the data element.
   */
  public abstract void populateDataElement(QueryFormDefinition queryForm);
  
  /**
   * Validates the values contained in this filter.  Adds errors to the supplied BTXDetails 
   * if any violations are found.
   * 
   * @param btx the <code>BTXDetails</code>
   */
  public abstract void validate(BTXDetails btxDetails);
  
  protected void validateDataType(BTXDetails btxDetails, String value) {
    if (ApiFunctions.isEmpty(value)) {
      btxDetails.addActionError(new BtxActionError("kc.error.queryform.missingValue", 
          getElementDisplay()));
      return;
    }
    DetElement detElement = getDetAdeElement();
    if (detElement == null) {
      detElement = getDetDataElement();      
    }
    String elementDisplay = getElementDisplay();

    if (detElement.isDatatypeCv()) {
      ValidatorElementValueInBroadValueSet v = new ValidatorElementValueInBroadValueSet();
      v.setPropertyDisplayName(elementDisplay);
      v.setValue(value);
      v.setBroadValueSet(detElement.getBroadValueSet());
      v.addValidatorErrorListener(new ValidatorErrorListener() {
        public boolean validatorError(Validator v, String errorKey) {
          ValidatorElementValueInBroadValueSet v1 = (ValidatorElementValueInBroadValueSet) v;
          v1.addErrorMessage("kc.error.queryform.valueNotInBroadValueSet", v1.getValue(), v1.getPropertyDisplayName());
          return true;
        }
      }, ValidatorElementValueInBroadValueSet.ERROR_KEY_NOT_IN_BROAD_VALUE_SET);
      btxDetails.addActionErrors(v.validate());      
    }
    else if (detElement.isDatatypeFloat()) {
      ValidatorFloat v = new ValidatorFloat();
      v.setPropertyDisplayName(elementDisplay);
      v.setFloat(value);
      v.addValidatorErrorListener(new ValidatorErrorListener() {
        public boolean validatorError(Validator v, String errorKey) {
          ValidatorFloat v1 = (ValidatorFloat) v;
          v1.addErrorMessage("kc.error.queryform.valueNotFloat", v1.getFloat(), v1.getPropertyDisplayName());
          return true;
        }
      }, ValidatorFloat.ERROR_KEY1);
      btxDetails.addActionErrors(v.validate());      
    }
    else if (detElement.isDatatypeInt()) {
      ValidatorInt v = new ValidatorInt();
      v.setPropertyDisplayName(elementDisplay);
      v.setInt(value);
      v.addValidatorErrorListener(new ValidatorErrorListener() {
        public boolean validatorError(Validator v, String errorKey) {
          ValidatorInt v1 = (ValidatorInt) v;
          v1.addErrorMessage("kc.error.queryform.valueNotInt", v1.getInt(), v1.getPropertyDisplayName());
          return true;
        }
      }, ValidatorInt.ERROR_KEY1);
      btxDetails.addActionErrors(v.validate());
    }
    else if (detElement.isDatatypeDate() || detElement.isDatatypeVpd()) {
      ValidatorVpd v = new ValidatorVpd();
      v.setPropertyDisplayName(elementDisplay);
      v.setVpd(value);
      v.addValidatorErrorListener(new ValidatorErrorListener() {
        public boolean validatorError(Validator v, String errorKey) {
          ValidatorVpd v1 = (ValidatorVpd) v;
          v1.addErrorMessage("kc.error.queryform.valueNotVpd", v1.getVpd(), v1.getPropertyDisplayName());
          return true;
        }
      }, ValidatorVpd.ERROR_KEY1);
      btxDetails.addActionErrors(v.validate());
    }
  }
  
  protected void validateComparisonOperator(BTXDetails btxDetails, String operator) {
    if (!KcQueryOperators.isValidComparisonOperator(operator)) {
      // Check for the string "null" as well, since the JSON API used upstream may return
      // "null" for null operators.
      if (ApiFunctions.isEmpty(operator) || (operator.equals("null"))) {
        btxDetails.addActionError(new BtxActionError("kc.error.queryform.missingOperator", 
            getElementDisplay()));        
      }
      else {
        btxDetails.addActionError(new BtxActionError("kc.error.queryform.badOperator", 
            getComparisonOperatorDisplayName(operator), getElementDisplay()));
      }
    }    
  }

  protected void validateRangeOperators(BTXDetails btxDetails, 
                                        String logicalOperator, 
                                        String operator1, 
                                        String operator2) {
    if (determineRangeOperator(logicalOperator, operator1, operator2) == null) {
      if (ApiFunctions.isEmpty(operator1) || ApiFunctions.isEmpty(operator2)) {
        btxDetails.addActionError(new BtxActionError("kc.error.queryform.missingOperator", 
            getElementDisplay()));        
      }
      else {
        btxDetails.addActionError(new BtxActionError("kc.error.queryform.badRangeOperators", 
            getComparisonOperatorDisplayName(operator1), 
            getComparisonOperatorDisplayName(operator2),
            getElementDisplay()));
      }
    }        
  }

  protected void validateDiscreteOperator(BTXDetails btxDetails, String operator) {
    if (!KcQueryOperators.isValidDiscreteOperator(operator)) {
      // Check for the string "null" as well, since the JSON API used upstream may return
      // "null" for null operators.
      if (ApiFunctions.isEmpty(operator) || (operator.equals("null"))) {
        btxDetails.addActionError(new BtxActionError("kc.error.queryform.missingOperator", 
            getElementDisplay()));
      }
      else {
        btxDetails.addActionError(new BtxActionError("kc.error.queryform.badOperator", 
            getComparisonOperatorDisplayName(operator), getElementDisplay()));        
      }
    }    
  }

  protected void validateLogicalOperator(BTXDetails btxDetails, String operator) {
    if (!KcQueryOperators.isValidLogicalOperator(operator)) {
      // Check for the string "null" as well, since the JSON API used upstream may return
      // "null" for null operators.
      if (ApiFunctions.isEmpty(operator) || (operator.equals("null"))) {
        btxDetails.addActionError(new BtxActionError("kc.error.queryform.missingOperator", 
            getElementDisplay()));
      }
      else {
        btxDetails.addActionError(new BtxActionError("kc.error.queryform.badOperator", 
            getComparisonOperatorDisplayName(operator), getElementDisplay()));        
      }
    }    
  }

  /**
   * Returns an indication of whether the specified key is the key of a KC filter.
   * 
   * @param key the key
   * @return <code>true</code> if the specified key is the key of a KC filter; <code>false</code>
   * otherwise.  
   */
  public static boolean isKcFilterKey(String key) {
    return (key == null) ? false : key.startsWith(KEY_PREFIX);
  }
  
  protected synchronized static String getUniqueOrGroupKey() {
    return "kc.or" + String.valueOf(_uniqueIdCounter++);
  }
}
