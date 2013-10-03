package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of creating an account location.
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
public class BtxDetailsCreateAccountLocationStart extends BtxDetailsCreateAccountLocation implements Serializable {
  private static final long serialVersionUID = 2283155691621612272L;

  public BtxDetailsCreateAccountLocationStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_ACCOUNT_LOCATION_START;
  }
}
