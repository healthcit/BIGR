package com.ardais.bigr.query.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class SampleFilters extends ProductFilters {

  private static List _naFilters = new ArrayList();
  static {
    _naFilters.add(FilterConstants.KEY_RNAID);
    _naFilters.add(FilterConstants.KEY_EXCLUDE_IMPLICIT_RNA_FILTERS);
  }

  public SampleFilters(FilterSet fs) {
    fs.copyInto(this);
  }

  /**
  * Creates a new <code>SampleFilters</code>.
  */
  public SampleFilters() {
    super();
  }

  /**
   * Creates a new <code>SampleFilters</code> from the specified map of
   * filter objects.
   * 
   * @param  filters  a Map of filters, keyed by constants in 
   *		{@link com.ardais.bigr.query.filters.FilterConstants FilterConstants}
   */
  public SampleFilters(Map filters) {
    super(filters);

    // Make sure that the sample ids filter only has valid frozen and paraffin
    // sample ids.
    // moved to TissueSummaryQueryBuilder
    //		Map f = getFilters();
    //		FilterSampleId sampleIdFilter = 
    //			(FilterSampleId)f.get(FilterConstants.KEY_SAMPLEID);
    //		if (sampleIdFilter != null) {
    //			String[] ids = sampleIdFilter.getFilterValues();
    //            String[] sampleIds = IdValidator.validSampleIds(ids);
    //			if (sampleIds.length > 0) {
    //				f.put(FilterConstants.KEY_SAMPLEID, new FilterSampleId(sampleIds));
    //			}
    //			else {
    //				f.remove(FilterConstants.KEY_SAMPLEID);
    //			}
    //		}
  }

  public ProductFilters copy() {
    // copy the entries so other can add/remove without affecting this
    return new SampleFilters(new HashMap(getFilters()));
  }

  // Sample queryies grab all Filters, currently (
  protected List getNotApplicableFilterKeys() {
    return _naFilters;
  }
}
