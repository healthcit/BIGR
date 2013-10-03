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
public class FilterJoin extends Filter {

  public FilterJoin() {
    setMultiOperator("OR");
    _joinType = "inner";
  }

  private TableColumn _leftColumn;
  private TableColumn _rightColumn;
  private String _joinType;

  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.Filter#getSqlFragment()
   */
  public String getSqlFragment() {
    String sqlFragment = null;
    String leftFragment = null;
    String rightFragment = null;

    leftFragment = _leftColumn.getTableAlias() + "." + _leftColumn.getColumnName();
    rightFragment = _rightColumn.getTableAlias() + "." + _rightColumn.getColumnName();

    if (_joinType.equalsIgnoreCase("inner")) {
      sqlFragment =  leftFragment + " = " + rightFragment;
    }
    else if (_joinType.equalsIgnoreCase("left")) {
      sqlFragment =  leftFragment + "(+) = " + rightFragment;
    }
    else if (_joinType.equalsIgnoreCase("right")) {
      sqlFragment =  leftFragment + " = " + rightFragment + "(+)";
    }
    return sqlFragment;
  }

  public boolean validate(BigrLibraryMetaData data) {
    boolean verified = true;
    try {
      getLeftColumn().validate(data);
      getRightColumn().validate(data);
    }
    catch (ApiException e) {
      verified = false;
      System.out.println("Warning: filterJoin : " + e.getMessage());
    }
    return verified;
  }

  /**
   * @return
   */
  public TableColumn getLeftColumn() {
    return _leftColumn;
  }

  /**
   * @return
   */
  public TableColumn getRightColumn() {
    return _rightColumn;
  }

  /**
   * @param column
   */
  public void setLeftColumn(TableColumn column) {
    _leftColumn = column;
  }

  /**
   * @param column
   */
  public void setRightColumn(TableColumn column) {
    _rightColumn = column;
  }

  /**
   * @return
   */
  public String getJoinType() {
    return _joinType;
  }

  /**
   * @param string
   */
  public void setJoinType(String string) {
    _joinType = string;
  }

}
