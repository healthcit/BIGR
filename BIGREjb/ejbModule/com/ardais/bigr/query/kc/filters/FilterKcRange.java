package com.ardais.bigr.query.kc.filters;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.generator.KcQueryOperators;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;

public class FilterKcRange extends FilterKc {

  private String _logicalOperator;
  private String _operator1;
  private String _value1;
  private String _operator2;
  private String _value2;

  /**
   * @param dataElement
   */
  public FilterKcRange(QueryFormDefinitionDataElement dataElement, String logicalOperator,
                       String operator1, String value1, String operator2, String value2) {
    super(dataElement);
    _logicalOperator = logicalOperator;
    _value1 = value1;
    _operator1 = operator1;
    _value2 = value2;
    _operator2 = operator2;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    String rangeOperator = determineRangeOperator(_logicalOperator, _operator1, _operator2);
    if (rangeOperator != null) {
      qb.addFilterKcRange(getDetDataElement(), getDomainObjectType(), rangeOperator,
          new String[] { _value1, _value2 });        
    }
    else {
      throw new ApiException("Could not add FilterKcRange to query builder since comparison operators are not a valid combination (" + _operator1 + " and " + _operator2 + ") for logical operator (" + _logicalOperator + ").");        
    }
  }

  public void populateDataElement(QueryFormDefinition queryForm) {
    QueryFormDefinitionDataElement dataElement = 
      queryForm.getQueryDataElement(getDetDataElement().getCui());
    if (dataElement != null) {
      QueryFormDefinitionValueSet valueSet = new QueryFormDefinitionValueSet();
      valueSet.setOperator(_logicalOperator);
      QueryFormDefinitionValueComparison value = new QueryFormDefinitionValueComparison();
      value.setOperator(_operator1);
      value.setValue(_value1);
      valueSet.addValue(value);
      value = new QueryFormDefinitionValueComparison();
      value.setOperator(_operator2);
      value.setValue(_value2);
      valueSet.addValue(value);
      dataElement.setValueSet(valueSet);
    }
  }

  public void validate(BTXDetails btxDetails) {
    validateDataType(btxDetails, _value1);
    validateDataType(btxDetails, _value2);
    validateComparisonOperator(btxDetails, _operator1);
    validateComparisonOperator(btxDetails, _operator2);
    validateRangeOperators(btxDetails, _logicalOperator, _operator1, _operator2);
    validateLogicalOperator(btxDetails, _logicalOperator);
  }

  public String toString() {
    StringBuffer buf = new StringBuffer(64);
    buf.append(getElementDisplay());
    buf.append(" ");
    buf.append(getComparisonOperatorDisplayName(_operator1));
    buf.append(" ");
    buf.append(getValueDisplay(_value1));
    buf.append(" ");
    buf.append(_logicalOperator);
    buf.append(" ");
    buf.append(getComparisonOperatorDisplayName(_operator2));
    buf.append(" ");
    buf.append(getValueDisplay(_value2));
    return buf.toString();
  }
}
