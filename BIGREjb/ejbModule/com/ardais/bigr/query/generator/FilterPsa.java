package com.ardais.bigr.query.generator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNumericRangeDecimal;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter for samples with a particular PSA value.  This filter is a bit different than other 
 * numeric tange filters in that it can take more than one range.
 */
public class FilterPsa extends FilterNumericRangeDecimal {

  private List _filters;
  private List _mins = new ArrayList(); 
  private List _maxs = new ArrayList(); 
  private boolean _isMultipleRanges = false;

  public FilterPsa(BigDecimal min, BigDecimal max) {
    super(FilterConstants.KEY_PSA, min, max, 0.0, 999.99);
    _mins.add(min);
    _maxs.add(max);
  }

  public FilterPsa(BigDecimal[] min, BigDecimal[] max) {
    super(FilterConstants.KEY_PSA);
    if (min.length != max.length) {
      throw new ApiException("Attempt to instantiate a FilterPsa with different numbers of min (" + String.valueOf(min.length) + ") and max (" + String.valueOf(max.length) + ") arguments.");
    }
    else {
      if (min.length == 1) {
        setMin(min[0]);        
        setMax(max[0]);
        _mins.add(min[0]);
        _maxs.add(max[0]);
      }
      else {
        _filters = new ArrayList();
        for (int i = 0; i < min.length; i++) {
          _filters.add(new FilterPsa(min[i], max[i]));
          _mins.add(min[i]);
          _maxs.add(max[i]);
        }
        _isMultipleRanges = true;
      }
    }
  }

  /**
   * Returns the list of filters that comprise this filter.  If this filter consists of only a 
   * single filter, then an empty iterator is returned. 
   * 
   * @return  The iterator of filters.
   */
  public Iterator getFilters() {
    return (_filters == null) ? Collections.EMPTY_LIST.iterator() : _filters.iterator();
  }

  public String displayName() {
    return "Prostate Specific Antigen, Free Serum";
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterPsa(ApiFunctions.toStringArray(_mins), ApiFunctions.toStringArray(_maxs));
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#appendVals(java.lang.StringBuffer)
   */
  protected void appendVals(StringBuffer buf) {
    if (_isMultipleRanges) {
      boolean first = true;
      Iterator i = _filters.iterator();
      while (i.hasNext()) {
        FilterPsa filter = (FilterPsa) i.next();
        if (!first) {
          buf.append(';');
        }
        filter.appendVals(buf);
        first = false;
      }
    }
    else {
      super.appendVals(buf);
    }
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.FilterNumericRange#isEmpty()
   */
  public boolean isEmpty() {
    if (_isMultipleRanges) {
      boolean empty = true;
      Iterator i = _filters.iterator();
      while (i.hasNext()) {
        FilterPsa filter = (FilterPsa) i.next();
        empty = empty && filter.isEmpty();
      }
      return empty;
    }
    else {
      return super.isEmpty();
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    if (_isMultipleRanges) {
      boolean first = true;
      StringBuffer buf = new StringBuffer();
      Iterator i = _filters.iterator();
      while (i.hasNext()) {
        FilterPsa filter = (FilterPsa) i.next();
        if (!first) {
          buf.append(" OR ");
        }
        buf.append(filter.toString());
        first = false;
      }
      return buf.toString();
    }
    else {
      return super.toString();
    }
  }

}
