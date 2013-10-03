package com.ardais.bigr.orm.btx;

import java.io.Serializable;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.AccountDto;

/**
 * Represent the details of starting to modify an account.
 * <p>
 * The transaction-specific fields are described below.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setAccountData(AccountDto) accountData}: The account dto containing the information about the account to modify.</li>
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
public class BtxDetailsModifyAccountStart extends BtxDetailsModifyAccount implements Serializable {
  private static final long serialVersionUID = 7425993512042494028L;

  public BtxDetailsModifyAccountStart() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MODIFY_ACCOUNT_START;
  }
}
