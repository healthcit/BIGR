package com.ardais.bigr.query.generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ardais.bigr.concept.BigrConcept;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter for samples with a particular sample type.
 */
public class FilterSampleType extends FilterStringsEqual {

  private Map _displayMap;

  public FilterSampleType(String[] sampleTypes) {
    super(FilterConstants.KEY_SAMPLE_TYPE, sampleTypes);
  }

  public String displayName() {
    return "Sample Type";
  }

  public Map codeDisplayMap() {
    if (_displayMap == null) {
      _displayMap = new HashMap();
      Iterator sampleTypes = 
        SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_SAMPLE_TYPES).iterator();
      while (sampleTypes.hasNext()) {
        BigrConcept sampleType = (BigrConcept) sampleTypes.next();
        _displayMap.put(sampleType.getCode(), sampleType.getDescription());       
      }
    }
    return _displayMap;
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
    qb.addFilterSampleType(getFilterValues());
  }

}
