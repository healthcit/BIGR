package com.ardais.bigr.query.filters;

import java.util.List;

import com.gulfstreambio.gboss.GbossFactory;

public abstract class FilterRepeating extends Filter {

  RepeatingFilterData _repeatingValues = null;
  public FilterRepeating(String key, RepeatingFilterData repeatingValues) {
    super(key);
    _repeatingValues = repeatingValues;
  }


  public FilterRepeating(String key, String[] repeatingValues) {
    super(key);
    RepeatingSingleData singledata = new RepeatingSingleData(repeatingValues);
    RepeatingFilterData filterData = new RepeatingFilterData();
    filterData.add(singledata);
    _repeatingValues = filterData;
  }

  public FilterRepeating(String key, List repeatingValues) {
    super(key);
    RepeatingSingleData singledata = new RepeatingSingleData((String[]) repeatingValues.toArray(new String[0]));
    RepeatingFilterData filterData = new RepeatingFilterData();
    filterData.add(singledata);
    _repeatingValues = filterData;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#appendVals(java.lang.StringBuffer)
   */
  protected void appendVals(StringBuffer buf) {
  }

  protected String remap(String s) {
    return GbossFactory.getInstance().getDescription(s);
  }

  /**
   * Return a human-readable snippet, suitable for inclusion in a summary.
   * @see ProductFilters.toString()
   */
  public String toString() {
    return null;
  }

  /**
   * @return
   */
  public RepeatingFilterData getRepeatingValues() {
    return _repeatingValues;
  }

  /**
   * @param data
   */
  public void setRepeatingValues(RepeatingFilterData data) {
    _repeatingValues = data;
  }

}
