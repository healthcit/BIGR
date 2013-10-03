package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of creating an account.
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
public class BtxDetailsCreateAccountStart extends BtxDetailsCreateAccount implements Serializable {
  private static final long serialVersionUID = 6585297339064338104L;

  public BtxDetailsCreateAccountStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_ACCOUNT_START;
  }
}
