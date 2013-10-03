package com.ardais.bigr.query.generator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringEquals;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filters for Donor Inst
 */
public class FilterDonorInst extends FilterStringEquals {
  private String _donorInst;
  /**
   * Constructor for FilterGender.
   * @param gender   Constants values for male, female
   */
  public FilterDonorInst(String donorInst) {
    super(FilterConstants.KEY_DONOR_INST, donorInst);
    _donorInst = donorInst;
  }

  protected String displayName() {
    return "Donor Institution";
  }

  private boolean hasDonorInst() {
    return _donorInst != null;
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter initFromFilt) {
    if (!hasDonorInst())
      throw new ApiException();
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) initFromFilt;
    qb.addFilterSampleAccount(_donorInst);
  }

}
