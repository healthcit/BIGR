package com.ardais.bigr.query.kc.filters;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.generator.KcQueryOperators;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.validation.ValidatorFloat;
import com.ardais.bigr.validation.ValidatorInt;
import com.ardais.bigr.validation.ValidatorRequired;
import com.ardais.bigr.validation.ValidatorVpd;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueAny;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;
import com.gulfstreambio.kc.util.KcUtils;

public class FilterKcComparison extends FilterKc {

  private String _operator;
  private String _value;
  
  /**
   * @param dataElement
   * @param operator
   * @param value
   */
  public FilterKcComparison(QueryFormDefinitionDataElement dataElement, String operator,
                            String value) {
    super(dataElement);
    _value = value;
    _operator = operator;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterKcComparison(getDetDataElement(), getDomainObjectType(), _operator, _value);
  }

  public void populateDataElement(QueryFormDefinition queryForm) {
    QueryFormDefinitionDataElement dataElement = 
      queryForm.getQueryDataElement(getDetDataElement().getCui());
    if (dataElement != null) {
      QueryFormDefinitionValueSet valueSet = new QueryFormDefinitionValueSet();
      QueryFormDefinitionValueComparison value = new QueryFormDefinitionValueComparison();
      value.setOperator(_operator);
      value.setValue(_value);
      valueSet.addValue(value);
      dataElement.setValueSet(valueSet);
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
