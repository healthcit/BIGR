package com.ardais.bigr.query.kc.filters;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.generator.KcQueryOperators;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;

public class FilterKcDiscrete extends FilterKc {

  private String _logicalOperator;
  private String _operator;
  private String[] _values;

  public FilterKcDiscrete(QueryFormDefinitionDataElement dataElement, String logicalOperator,
                          String operator, String[] values) {
    super(dataElement);
    _logicalOperator = logicalOperator;
    _operator = operator;
    _values = values;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterKcComparison(getDetDataElement(), getDomainObjectType(), _operator, _values);      
  }

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

  public void validate(BTXDetails btxDetails) {
    for (int i = 0; i < _values.length; i++) {
      validateDataType(btxDetails, _values[i]);      
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
      buf.append(getValueDisplay(_values[i]));
    }
    return buf.toString();
  }
}
