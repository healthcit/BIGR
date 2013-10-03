package com.ardais.bigr.query.kc.filters;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.generator.KcQueryOperators;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionAdeElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;

public class FilterKcDiscreteAde extends FilterKc {

  private String _logicalOperator;
  private String _operator;
  private String[] _values;

  /**
   * @param dataElement
   * @param adeElementId
   */
  public FilterKcDiscreteAde(QueryFormDefinitionDataElement dataElement, DetElement adeElement, 
                             String logicalOperator, String operator, String[] values) {
    super(dataElement, adeElement);
    _logicalOperator = logicalOperator;
    _operator = operator;
    _values = values;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterKcComparisonAde(getDetDataElement(), getDetAdeElement(), _operator, _values);      
  }

  public void populateDataElement(QueryFormDefinition queryForm) {
    QueryFormDefinitionDataElement dataElement = 
      queryForm.getQueryDataElement(getDetDataElement().getCui());
    if (dataElement != null) {
      String adeElementCui = getDetAdeElement().getCui();
      QueryFormDefinitionAdeElement adeElement = dataElement.getAdeElement(adeElementCui);
      if (adeElement == null) {
        adeElement = new QueryFormDefinitionAdeElement();
        adeElement.setCui(adeElementCui);
        dataElement.addAdeElement(adeElement);
      }

      QueryFormDefinitionValueSet valueSet = new QueryFormDefinitionValueSet();
      valueSet.setOperator(_logicalOperator);
      for (int i = 0; i < _values.length; i++) {
        QueryFormDefinitionValueComparison value = new QueryFormDefinitionValueComparison();
        value.setOperator(_operator);
        value.setValue(_values[i]);
        valueSet.addValue(value);        
      }
      adeElement.setValueSet(valueSet);
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
