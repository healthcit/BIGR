package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of modifying an account location.
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
public class BtxDetailsModifyAccountLocationStart extends BtxDetailsModifyAccountLocation implements Serializable {
  private static final long serialVersionUID = -4957011304321037925L;

  public BtxDetailsModifyAccountLocationStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MODIFY_ACCOUNT_LOCATION_START;
  }
}
