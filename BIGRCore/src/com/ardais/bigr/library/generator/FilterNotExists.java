/*
 * Created on Nov 28, 2003
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
public class FilterNotExists extends Filter {

  public FilterNotExists() {
    setMultiOperator("OR");
  }

  private SelectClause _selectClause;

  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.Filter#getSqlFragment()
   */
  public String getSqlFragment() {

    String sqlFragment = null;

    sqlFragment = "NOT EXISTS " + getSelectClause().getSqlFragment();
    return sqlFragment;
  }

  public boolean validate(BigrLibraryMetaData data) {
    boolean verified = true;
    verified = getSelectClause().validate(data);
    return verified;
  }

  /**
   * @return
   */
  public SelectClause getSelectClause() {
    return _selectClause;
  }

  /**
   * @param clause
   */
  public void setSelectClause(SelectClause clause) {
    _selectClause = clause;
  }

}
