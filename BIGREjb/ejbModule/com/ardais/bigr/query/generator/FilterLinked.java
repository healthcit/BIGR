package com.ardais.bigr.query.generator;

import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringEquals;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.Constants;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FilterLinked extends FilterStringEquals {

  static Map displayCodes = new HashMap();
  static {
    displayCodes.put(Constants.LINKED_LINKED, "Linked");
    displayCodes.put(Constants.LINKED_UNLINKED, "Unlinked");
  }

  /**
   * Constructor for FilterLinked.
   * @param key
   * @param filterValue
   */
  public FilterLinked(String filterValue) {
    super(FilterConstants.KEY_LINKED, filterValue);
  }

  /**
   * @see com.ardais.bigr.query.filters.FilterStringEquals#codeDisplayMap()
   */
  protected Map codeDisplayMap() {
    return displayCodes;
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "Linked";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterConsentLinked(getFilterValue());
  }
}
