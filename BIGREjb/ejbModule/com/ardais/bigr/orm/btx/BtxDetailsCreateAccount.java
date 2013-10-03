package com.ardais.bigr.orm.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of creating an account.
 * <p>
 * The transaction-specific fields are described below.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setAccountData(AccountDto) accountData}: The account dto containing the information about the new account to create.</li>
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
public class BtxDetailsCreateAccount extends BtxDetailsAccountManagement implements Serializable {
  private static final long serialVersionUID = 2459279220955682052L;

  public BtxDetailsCreateAccount() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_ACCOUNT;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    AccountDto accountData = getAccountData();
    if (accountData != null) {
      set.add(accountData.getId());
    }
    return set;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    StringBuffer sb = new StringBuffer(300);

    AccountDto accountDto = getAccountData();
    sb.append("Created account ");
    sb.append(Escaper.htmlEscape(accountDto.getName()));
    sb.append(" (");
    sb.append(IcpUtils.prepareLink(accountDto.getId(), securityInfo));
    sb.append(").  ");
    sb.append(super.getAccountDetailsAsHTML());

    return sb.toString();
  }
}
