/*
 * Created on Nov 28, 2003
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
public class FilterNvlEqual extends Filter {

  public FilterNvlEqual() {
    setMultiOperator("OR");
  }

  private String _value;
  private ColumnOrValue _leftNvlValue;
  private ColumnOrValue _rightNvlValue;

  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.Filter#getSqlFragment()
   */
  public String getSqlFragment() {
    String sqlFragment = null;
    String leftValue = null;
    String rightValue = null;

    if (ApiFunctions.isEmpty(_leftNvlValue.getValue())) {
      leftValue = _leftNvlValue.getTableAlias() + "." + _leftNvlValue.getColumnName();
    }
    else {
      leftValue = _leftNvlValue.getValue();
    }

    if (ApiFunctions.isEmpty(_rightNvlValue.getValue())) {
      rightValue = _rightNvlValue.getTableAlias() + "." + _rightNvlValue.getColumnName();
    }
    else {
      rightValue = _rightNvlValue.getValue();
    }

    sqlFragment = "NVL(" + leftValue + " , " + rightValue + ") = ";

    if (ApiFunctions.isEmpty(getValue())) {
      sqlFragment = sqlFragment + "?";
    }
    else {
      sqlFragment = sqlFragment + getValue();
    }
    return sqlFragment;
  }



  public boolean validate(BigrLibraryMetaData data) {
    boolean verified = true;
    if (_leftNvlValue.validate(data) == false) {
      verified = false;
    }
    if (_rightNvlValue.validate(data) == false) {
      verified = false;
    }
    return verified; 
  }

  /**
   * @return
   */
  public ColumnOrValue getLeftNvlValue() {
    return _leftNvlValue;
  }

  /**
   * @return
   */
  public ColumnOrValue getRightNvlValue() {
    return _rightNvlValue;
  }

  /**
   * @param value
   */
  public void setLeftNvlValue(ColumnOrValue value) {
    _leftNvlValue = value;
  }

  /**
   * @param value
   */
  public void setRightNvlValue(ColumnOrValue value) {
    _rightNvlValue = value;
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
