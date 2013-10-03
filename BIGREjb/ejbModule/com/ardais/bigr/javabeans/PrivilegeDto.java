package com.ardais.bigr.javabeans;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;

/**
 * @author jesielionis
 *
 * Represents the data for a privilege
 */

public class PrivilegeDto implements Serializable{

  private String _id = null;
  private String _description = null;
  
  public PrivilegeDto() {
    super();
  }

  /**
   * @return
   */
  public String getDescription() {
    return _description;
  }

  /**
   * @return
   */
  public String getId() {
    return _id;
  }

  /**
   * @param string
   */
  public void setDescription(String string) {
    _description = string;
  }

  /**
   * @param string
   */
  public void setId(String string) {
    _id = string;
  }
  
  public boolean equals(Object obj) {
    boolean returnValue = false;
    PrivilegeDto that = (PrivilegeDto)obj;
    returnValue = ApiFunctions.safeString(this.getId()).equalsIgnoreCase(ApiFunctions.safeString(that.getId()));
    return returnValue;
  }
  
  public int hashCode() {
    return getId().hashCode();
  }

}
