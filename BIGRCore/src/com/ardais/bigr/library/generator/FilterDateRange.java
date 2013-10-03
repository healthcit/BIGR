/*
 * Created on Nov 25, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * @author sislam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FilterDateRange extends ColumnFilter {

  public FilterDateRange() {
    super.setMultiOperator("OR");
  }

  private String _lowerLimit;
  private String _upperLimit;

  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.ColumnFilter#getSqlFragment()
   */
  public String getSqlFragment() {
    String sqlFragment = null;
    String lowerFragment = null;
    String upperFragment = null;

    lowerFragment = "(" + getTableAlias() + "." + getColumnName() + " >= ";
    if (ApiFunctions.isEmpty(_lowerLimit)) {
      lowerFragment = lowerFragment + "to_date(?, 'MM/DD/YY HH24:MI'))";
    }
    else {
      lowerFragment = lowerFragment + "to_date(" + getLowerLimit() + ", 'MM/DD/YY HH24:MI'))";
    }

    upperFragment = "(" + getTableAlias() + "." + getColumnName() + " <= ";
    if (ApiFunctions.isEmpty(_upperLimit)) {
      upperFragment = upperFragment + "to_date(?, 'MM/DD/YY HH24:MI'))";
    }
    else {
      upperFragment = upperFragment + "to_date(" + getUpperLimit() + ", 'MM/DD/YY HH24:MI'))";
    }
    sqlFragment = "(" + lowerFragment + " AND " + upperFragment + ")";

    return sqlFragment;
  }

  /**
   * @return
   */
  public String getLowerLimit() {
    return _lowerLimit;
  }

  /**
   * @return
   */
  public String getUpperLimit() {
    return _upperLimit;
  }

  /**
   * @param string
   */
  public void setLowerLimit(String string) {
    _lowerLimit = string;
  }

  /**
   * @param string
   */
  public void setUpperLimit(String string) {
    _upperLimit = string;
  }

}
