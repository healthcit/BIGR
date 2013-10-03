/*
 * Created on Nov 30, 2003
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
public class Join extends FilterJoin {

  private String _keyName;
  private String _ordering;

  /**
   * @return
   */
  public String getKeyName() {
    return _keyName;
  }

  /**
   * @return
   */
  public String getOrdering() {
    return _ordering;
  }

  /**
   * @param string
   */
  public void setKeyName(String string) {
    _keyName = string;
  }

  /**
   * @param string
   */
  public void setOrdering(String string) {
    _ordering = string;
  }

  public String getSqlFragment() {
     return super.getSqlFragment();
  }
}
