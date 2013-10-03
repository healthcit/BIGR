/*
 * Created on Dec 1, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

/**
 * @author sislam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FilterOther extends Filter {

  public FilterOther() {
    setMultiOperator("OR");
  }

  private String _sqlString;
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.Filter#getSqlFragment()
   */
  public String getSqlFragment() {
    return getSqlString();
  }

  public boolean validate(BigrLibraryMetaData data) {
    return true;
  }

  /**
   * @return
   */
  public String getSqlString() {
    return _sqlString;
  }

  /**
   * @param string
   */
  public void setSqlString(String string) {
    _sqlString = string;
  }

}
