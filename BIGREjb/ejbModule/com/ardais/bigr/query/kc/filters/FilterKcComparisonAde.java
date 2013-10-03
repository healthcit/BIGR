package com.ardais.bigr.query.kc.filters;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionAdeElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;

public class FilterKcComparisonAde extends FilterKc {

  private String _operator;
  private String _value;

  /**
   * @param dataElement
   * @param adeElementId
   */
  public FilterKcComparisonAde(QueryFormDefinitionDataElement dataElement, DetElement adeElement, 
                               String operator, String value) {
    super(dataElement, adeElement);
    _value = value;
    _operator = operator;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterKcComparisonAde(getDetDataElement(), getDetAdeElement(), _operator, _value);
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
      QueryFormDefinitionValueComparison value = new QueryFormDefinitionValueComparison();
      value.setOperator(_operator);
      value.setValue(_value);
      valueSet.addValue(value);
      adeElement.setValueSet(valueSet);
    }
  }

  public void validate(BTXDetails btxDetails) {
    validateDataType(btxDetails, _value);
    validateComparisonOperator(btxDetails, _operator);
  }
  
  public String toString() {
    StringBuffer buf = new StringBuffer(64);
    buf.append(getElementDisplay());
    buf.append(" ");
    buf.append(getComparisonOperatorDisplayName(_operator));
    buf.append(" ");
    buf.append(getValueDisplay(_value));
    return buf.toString();
  }
}
