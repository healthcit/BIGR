/*
 * Created on Nov 25, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import com.ardais.bigr.api.ApiException;

/**
 * @author sislam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FilterIsNull extends ColumnFilter {

  public FilterIsNull() {
    setMultiOperator("OR");
  }
  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.ColumnFilter#getSqlFragment()
   */
  public String getSqlFragment() {
    String sqlFragment = null;

    sqlFragment = getTableAlias() + "." + getColumnName() + " Is Null";
    return sqlFragment;
  }

}
