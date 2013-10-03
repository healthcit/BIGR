package com.ardais.bigr.query.kc.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.generator.KcQueryOperators;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueTypes;

public class FilterKcRollup extends FilterKc {

  private String _logicalOperator;
  private String _operator;
  private String[] _values;

  public FilterKcRollup(QueryFormDefinitionDataElement dataElement, String logicalOperator,
                        String operator, String[] values) {
    super(dataElement);
    _logicalOperator = logicalOperator;
    _operator = operator;
    _values = values;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.kc.filters.FilterKc#populateDataElement(com.gulfstreambio.kc.form.def.query.QueryFormDefinition)
   */
  public void populateDataElement(QueryFormDefinition queryForm) {
    QueryFormDefinitionDataElement dataElement = 
      queryForm.getQueryDataElement(getDetDataElement().getCui());
    if (dataElement != null) {
      QueryFormDefinitionValueSet valueSet = new QueryFormDefinitionValueSet();
      valueSet.setOperator(_logicalOperator);
      for (int i = 0; i < _values.length; i++) {
        QueryFormDefinitionValueComparison value = new QueryFormDefinitionValueComparison();
        value.setOperator(_operator);
        value.setValue(_values[i]);
        valueSet.addValue(value);        
      }
      dataElement.setValueSet(valueSet);
    }
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    QueryFormDefinitionRollupValue[] v = getRollupValuesForQuery();
    
    Map actualValues = new HashMap();
    for (int i = 0; i < v.length; i++) {
      QueryFormDefinitionRollupValue rollupValue = v[i];        
      QueryFormDefinitionValueSet rollupValueValueSet = rollupValue.getValueSet();
      String logicalOperator = rollupValueValueSet.getOperator();
      boolean isAnd = QueryFormDefinitionValueSet.OPERATOR_AND.equals(logicalOperator);
      QueryFormDefinitionValue[] rollupValueValues = rollupValueValueSet.getValues();
      int numRollupValues = rollupValueValues.length;
      for (int j = 0; j < numRollupValues; j++) {
        QueryFormDefinitionValue rollupValueValue = rollupValueValues[j];
        String valueType = rollupValueValue.getValueType();
        if (valueType.equals(QueryFormDefinitionValueTypes.COMPARISON)) {
          QueryFormDefinitionValueComparison concreteRollupValueValue = 
            (QueryFormDefinitionValueComparison) rollupValueValue;
          String operator = concreteRollupValueValue.getOperator();
          String value = concreteRollupValueValue.getValue();
          if (KcQueryOperators.NE.equals(operator)) {
            if (isAnd) {
              addActualValueToMap(actualValues, operator, value);
            }
          }
          else if (KcQueryOperators.EQ.equals(operator)) {
            if (!isAnd) {              
              addActualValueToMap(actualValues, operator, value);
            }
          }
          else if (KcQueryOperators.GT.equals(operator)
                   || KcQueryOperators.GE.equals(operator)
                   || KcQueryOperators.LT.equals(operator)
                   || KcQueryOperators.LE.equals(operator)) {
            if (numRollupValues == 1) {
              addActualValueToMap(actualValues, operator, value);
            }
            else if (numRollupValues == 2) {
              rollupValueValue = rollupValueValues[++j];
              concreteRollupValueValue = (QueryFormDefinitionValueComparison) rollupValueValue;
              String operator2 = concreteRollupValueValue.getOperator();
              String value2 = concreteRollupValueValue.getValue();
              String rangeOp = determineRangeOperator(logicalOperator, operator, operator2);
              if (rangeOp != null) {
                addActualValueToMap(actualValues, rangeOp, value, value2);
              }
            }
          }
        }
      }
    }
    
    Set operators = actualValues.keySet();
    if (operators!= null) {
      DetElement detElement = getDetDataElement();
      String domainObj = getDomainObjectType();
      String orGroupKey = (operators.size() > 1) ? FilterKc.getUniqueOrGroupKey() : null;
      Iterator i = operators.iterator();
      while (i.hasNext()) {
        String operator = (String) i.next();
        String[] values = (String[]) ((List) actualValues.get(operator)).toArray(new String[0]);
        if (KcQueryOperators.isValidComparisonOperator(operator)) {
          if (orGroupKey != null) {
            qb.addFilterKcComparison(detElement, domainObj, operator, values, orGroupKey);
          }
          else {
            qb.addFilterKcComparison(detElement, domainObj, operator, values);
          }
        }
        else if (KcQueryOperators.isValidRangeOperator(operator)) {
          if (orGroupKey != null) {
            qb.addFilterKcRange(detElement, domainObj, operator, values, orGroupKey);
          }
          else {
            qb.addFilterKcRange(detElement, domainObj, operator, values);
          }
        }
      }
    }    
  }

  private void addActualValueToMap(Map map, String operator, String value) {
    addActualValueToMap(map, operator, value, null);
  }

  private void addActualValueToMap(Map map, String operator, String value1, String value2) {
    List valueList = (List) map.get(operator);
    if (valueList == null) {
      valueList = new ArrayList();
      map.put(operator, valueList);
    }
    valueList.add(value1);
    if (value2 != null) {
      valueList.add(value2);      
    }
  }
  
  private QueryFormDefinitionRollupValue[] getRollupValuesForQuery() {
    // Create the list of return rollup values. 
    List values = new ArrayList();

    // Get the array of all rollup values in the rollup value set of the data element.
    QueryFormDefinitionDataElement dataElement = getQueryFormDefinitionDataElement();
    QueryFormDefinitionRollupValueSet rollupValueSet = dataElement.getRollupValueSet();
    QueryFormDefinitionRollupValue[] rollupValueSetValues = rollupValueSet.getValues();
    
    // Look at each rollup value in the set of all rollup values.  For each, if it is one of the
    // filter values, and the logical operator is OR (i.e. the user selected "is one of"), then 
    // add it to the list of return values.  If it is not one of the filter values and the 
    // logical operator is AND (i.e. the user selected "is not one of"), then add it to the list 
    // of return values.  This logic effectively changes an "is not one of" to an "is one of"
    // with the opposite selections, which is logically what we want.
    for (int i = 0; i < rollupValueSetValues.length; i++) {
      QueryFormDefinitionRollupValue rollupValueSetValue = rollupValueSetValues[i];
      if (ApiFunctions.arrayContains(_values, rollupValueSetValue.getDisplayName())) {
        if (KcQueryOperators.OR.equals(_logicalOperator)) {
          values.add(rollupValueSetValue);
        }
      }
      else if (KcQueryOperators.AND.equals(_logicalOperator)) {
        values.add(rollupValueSetValue);
      }
    }
    
    return (QueryFormDefinitionRollupValue[]) values.toArray(new QueryFormDefinitionRollupValue[0]);
  }
  
  public void validate(BTXDetails btxDetails) {
    QueryFormDefinitionDataElement dataElement = getQueryFormDefinitionDataElement();
    QueryFormDefinitionRollupValueSet rollupValueSet = dataElement.getRollupValueSet();
    for (int i = 0; i < _values.length; i++) {
      if (rollupValueSet.getValue(_values[i]) == null) {
        btxDetails.addActionError(new BtxActionError("kc.error.queryform.valueNotInSet", 
          _values[i], getElementDisplay()));
      }
    }
    validateDiscreteOperator(btxDetails, _operator);
    validateLogicalOperator(btxDetails, _logicalOperator);
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(64);
    buf.append(getElementDisplay());
    if (KcQueryOperators.AND.equals(_logicalOperator)) {
      buf.append(" is not one of: ");
    }
    else {
      buf.append(" is one of: ");
    }
    for (int i = 0; i < _values.length; i++) {
      if (i > 0) {
        buf.append(", ");
      }
      buf.append(_values[i]);
    }
    return buf.toString();
  }
}
