package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of creating a user.
 * <p>
 * The transaction-specific fields are described below.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>None.</li>
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
public class BtxDetailsCreateUserStart extends BtxDetailsCreateUser implements Serializable {
  private static final long serialVersionUID = 6220502473111740162L;

  public BtxDetailsCreateUserStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_USER_START;
  }
}
