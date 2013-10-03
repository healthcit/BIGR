package com.ardais.bigr.orm.btx;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.UserDto;

/**
 * Represent the details of finding a user.
 * <p>
 * The transaction-specific fields are described below.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setAccountData(AccountDto) accountData}: An AccountDto containing the search criteria.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setMatchingAccounts(List) matchingAccounts}: A list containing the accounts matching the
 * specified search criteria.</li>
 * </ul>
 */
public class BtxDetailsFindAccounts extends BtxDetailsAccountManagement implements Serializable {
  private static final long serialVersionUID = -5207340567194701410L;
  private List _matchingAccounts;

  public BtxDetailsFindAccounts() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_FIND_ACCOUNTS;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    throw new ApiException("doGetDetailsAsHTML should not have been called, this class does not support transaction logging.");
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("describeIntoHistoryRecord should not have been called, this class does not support transaction logging.");
    //super.describeIntoHistoryRecord(history);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    throw new ApiException("populateFromHistoryRecord should not have been called, this class does not support transaction logging.");
    //super.populateFromHistoryRecord(history);
  }
  
  /**
   * @return
   */
  public List getMatchingAccounts() {
    return _matchingAccounts;
  }

  /**
   * @param list
   */
  public void setMatchingAccounts(List list) {
    _matchingAccounts = list;
  }

}
