package com.ardais.bigr.query.kc.filters;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionAdeElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueAny;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;


public class FilterKcAnyAde extends FilterKc {

  /**
   * @param dataElement
   * @param adeElement
   */
  public FilterKcAnyAde(QueryFormDefinitionDataElement dataElement, DetElement adeElement) {
    super(dataElement, adeElement);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterKcAnyAde(getDetDataElement(), getDetAdeElement());
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
      valueSet.addValue(new QueryFormDefinitionValueAny());      
      adeElement.setValueSet(valueSet);
    }
  }

  public void validate(BTXDetails btxDetails) {
    // intentionally do nothing since there's nothing to validate
  }
  
  public String toString() {
    return getElementDisplay() + " is any value"; 
  }
}
