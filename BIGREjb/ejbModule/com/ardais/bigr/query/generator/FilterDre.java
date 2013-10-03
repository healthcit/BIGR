package com.ardais.bigr.query.generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.gboss.GbossValue;

/**
 * Filter for samples with a particular Digital Rectal Exam value.
 */
public class FilterDre extends FilterStringsEqual {

  private Map _displayMap;
  
  public FilterDre(String[] dreValues) {
    super(FilterConstants.KEY_DRE, dreValues);
  }

  public String displayName() {
    return "Digital Rectal Exam";
  }

  public Map codeDisplayMap() {
    if (_displayMap == null) {
      _displayMap = new HashMap();
      Iterator dreResults = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_DRE_RESULTS).getValues().iterator();
      while (dreResults.hasNext()) {
        GbossValue dreResult = (GbossValue) dreResults.next();
        _displayMap.put(dreResult.getCui(), dreResult.getDescription());       
      }
    }
    return _displayMap;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterDre(getFilterValues());
  }

}
