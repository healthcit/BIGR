/*
 * Created on Dec 1, 2003
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
public class FilterNotIn extends Filter {

  public FilterNotIn() {
    setMultiOperator("OR");
  }
  private TableColumn _column;
  private SelectClause _selectClause;

  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.Filter#getSqlFragment()
   */
  public String getSqlFragment() {

    String sqlFragment = null;

    sqlFragment =
      getColumn().getTableAlias()
        + "."
        + getColumn().getColumnName()
        + " NOT IN ("
        + getSelectClause().getSqlFragment()
        + ")";
    return sqlFragment;
  }
  /**
   * @return
   */
  public TableColumn getColumn() {
    return _column;
  }

  /**
   * @param column
   */
  public void setColumn(TableColumn column) {
    _column = column;
  }

  public boolean validate(BigrLibraryMetaData data) {
    boolean verified = true;
    try {
      getColumn().validate(data);
      getSelectClause().validate(data);
    }
    catch (ApiException e) {
      verified = false;
      System.out.println("Warning: filterNotIn : " + e.getMessage());
    }
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
