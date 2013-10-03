package com.ardais.bigr.query.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.query.generator.FilterSampleAppearanceBest;
import com.ardais.bigr.util.Constants;

/**
 */
public class RnaFilters extends ProductFilters {

  // entire filters that RNA ignores
  private static List _naFilters = new ArrayList();
  static {
    _naFilters.add(FilterConstants.KEY_SAMPLE_TYPE);
    _naFilters.add(FilterConstants.KEY_PVNOTESCONTAINS);
    _naFilters.add(FilterConstants.KEY_TISSUEID);
    _naFilters.add(FilterConstants.KEY_NOT_IN_PROJECT);
    // currently, "project" is an iltds_sample field
    _naFilters.add(FilterConstants.KEY_NOT_PULLED);
    _naFilters.add(FilterConstants.KEY_PATHVERIFIED_STATUS);
    _naFilters.add(FilterConstants.KEY_QC_STATUS);
    _naFilters.add(FilterConstants.KEY_QC_INPROCESS);
    _naFilters.add(FilterConstants.KEY_QC_INPROCESS_NOT);
    _naFilters.add(FilterConstants.KEY_SAMPLE_DATE_RECEIVED);
    _naFilters.add(FilterConstants.KEY_BMS_YN);
    _naFilters.add(FilterConstants.KEY_BMS_YN_YES);
    _naFilters.add(FilterConstants.KEY_NOT_PULLED_OR_BMS_YN_YES);
    _naFilters.add(FilterConstants.KEY_LOCAL_SAMPLES_ONLY);
  }

  // Appearance codes that RNA ignores
  private static List _naAppearanceCodes = new ArrayList();
  static {
    _naAppearanceCodes.add(Constants.APPEARANCE_GROSS_DISEASED);
    _naAppearanceCodes.add(Constants.APPEARANCE_GROSS_MIXED);
    _naAppearanceCodes.add(Constants.APPEARANCE_GROSS_NORMAL);
    _naAppearanceCodes.add(Constants.APPEARANCE_GROSS_UNKNOWN);
  }

  public RnaFilters(FilterSet fs) {
    fs.copyInto(this);
  }

  public RnaFilters() {
    super();
  }

  /**
   * Creates a new <code>RnaFilters</code> from the specified map of
   * filter objects.
   * 
   * @param  filters  a Map of filters, keyed by constants in 
   *		{@link com.ardais.bigr.query.filters.FilterConstants FilterConstants}
   */
  public RnaFilters(Map filters) {
    super(filters);
  }

  /**
   * See superclass addFilter, however this method modifies the Appearance filter, if any.
   */
  protected void addFilterProtected(String key, Filter f) {
    // check for FilterSampleAppearanceBest, and remove GROSS appearances for RNA.
    if (f instanceof FilterSampleAppearanceBest) {
      FilterSampleAppearanceBest fa = (FilterSampleAppearanceBest) f;
      String[] appearanceCodes = fa.getFilterValues();
      List newCodes = new ArrayList();
      for (int i = 0; i < appearanceCodes.length; i++) {
        String code = appearanceCodes[i];
        if (!_naAppearanceCodes.contains(code))
          newCodes.add(code);
      }
      if (!newCodes.isEmpty())
        super.addFilterProtected(
          key,
          new FilterSampleAppearanceBest((String[]) newCodes.toArray(new String[0])));
    }
    else {
      super.addFilterProtected(key, f); // not a special case filter
    }
  }

  public ProductFilters copy() {
    // copy the entries so others can remove without affecting this
    return new RnaFilters(new HashMap(getFilters()));
  }

  protected List getNotApplicableFilterKeys() {
    return _naFilters;
  }
}
