package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of starting to alter a users profile.
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
public class BtxDetailsChangeProfileStart extends BtxDetailsChangeProfile implements Serializable {
  private static final long serialVersionUID = 682558827565583267L;

  public BtxDetailsChangeProfileStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CHANGE_PROFILE_START;
  }
}
