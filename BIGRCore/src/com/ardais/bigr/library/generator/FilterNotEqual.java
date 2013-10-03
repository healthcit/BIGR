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
public class FilterNotEqual extends ColumnFilter {

  public FilterNotEqual() {
    setMultiOperator("AND");
  }
  private String _value;

  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.ColumnFilter#getSqlFragment()
   */
  public String getSqlFragment() {
    String sqlFragment = null;

    if (ApiFunctions.isEmpty(_value)) {
      sqlFragment = super.getTableAlias() + "." + super.getColumnName() + " <> ?";
    }
    else {
      sqlFragment = super.getTableAlias() + "." + super.getColumnName() + " <> " + getValue();
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
