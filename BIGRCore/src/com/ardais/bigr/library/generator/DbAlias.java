/*
 * Created on Oct 22, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * @author jesielionis
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DbAlias {
  
  private String _aliasValue = null;
  private String _javaConstant = null;
  private Object _parent = null;

  /**
   * Get the alias value
   * @return String  the alias value
   */
  public String getAliasValue() {
    return _aliasValue;
  }

  /**
   * Get the java constant
   * @return String  the java constant
   */
  public String getJavaConstant() {
    //if the java constant for this alias is blank (which is often the case), get the
    //javaConstant from the parent of this alias (i.e. the DbColumn or DbTable)
    if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(_javaConstant))) {
      if (_parent == null) {
        throw new ApiException("DbAlias object with null parent encountered.");
      }
      if (_parent instanceof DbColumn) {
        _javaConstant = ((DbColumn)_parent).getJavaConstant();
      }
      else if (_parent instanceof DbTable) {
        _javaConstant = ((DbTable)_parent).getJavaConstant();
      }
      else {
        throw new ApiException("DbAlias object with unexpected parent type of " + _parent.getClass() + " encountered.");
      }
    }
    return _javaConstant;
  }

  /**
   * Set the alias value
   * @param string  alias value
   */
  public void setAliasValue(String string) {
    _aliasValue = ApiFunctions.safeTrim(string);
  }

  /**
   * Set the java constant
   * @param string  java constant
   */
  public void setJavaConstant(String string) {
    _javaConstant = ApiFunctions.safeTrim(string);
  }

  /**
   * @param object
   */
  public void setParent(Object object) {
    _parent = object;
  }

}
