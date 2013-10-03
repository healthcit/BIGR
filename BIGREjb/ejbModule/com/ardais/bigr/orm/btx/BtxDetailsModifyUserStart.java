package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of starting to modify a user.
 * <p>
 * The transaction-specific fields are described below.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserData(UserDto) userData}: The user dto containing the information about the user to modify.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BtxDetailsModifyUserStart extends BtxDetailsModifyUser implements Serializable {
  private static final long serialVersionUID = -8305417356849870176L;

  public BtxDetailsModifyUserStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MODIFY_USER_START;
  }
}
