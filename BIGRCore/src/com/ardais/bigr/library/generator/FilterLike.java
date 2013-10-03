/*
 * Created on Nov 24, 2003
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
public class FilterLike extends ColumnFilter {

  public FilterLike() {
    setMultiOperator("OR");
  }

  private String _value;
  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.ColumnFilter#getSqlFragment()
   */
  public String getSqlFragment() {
    String sqlFragment = null;

    if (ApiFunctions.isEmpty(_value)) {
      sqlFragment = getTableAlias() + "." + getColumnName() + " LIKE ?" + " ESCAPE '/'";
    }
    else {
      sqlFragment = getTableAlias() + "." + getColumnName() + " LIKE " + getValue() + " ESCAPE '/'";
    }
    return sqlFragment;
  }

  /**
   * @return
   */
  public String getValue() {
    return _value;
  }

  /**
   * @param string
   */
  public void setValue(String string) {
    _value = string;
  }

}
