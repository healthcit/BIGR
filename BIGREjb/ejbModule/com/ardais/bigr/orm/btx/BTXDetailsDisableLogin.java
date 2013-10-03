package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * Represent the details of a login transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setAttemptUser(String) Attempted User}: The user who is attempting
 *      to login to the system</li>
 * <li>{@link #setAttemptAccount(String) Attempted Account}: The acccount id of the user who is 
 *     trying to login.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None</li>
 * </ul>
 */
public class BTXDetailsDisableLogin extends BTXDetails {
  private String _attemptUser;
  private String _attemptAccount;

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_DISABLE_LOGIN;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(_attemptUser);
    set.add(_attemptAccount);
    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    StringBuffer sb = new StringBuffer(512);
    sb.append("Login disabled for User: ");
    Escaper.htmlEscape(_attemptUser, sb);
    sb.append(" Account: ");
    Escaper.htmlEscape(_attemptAccount, sb);
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    history.setAttrib1(_attemptUser);
    history.setAttrib2(_attemptAccount);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    _attemptUser = history.getAttrib1();
    _attemptAccount = history.getAttrib2();
  }

  /**
   * Returns the attemptAccount.
   * @return String
   */
  public String getAttemptAccount() {
    return _attemptAccount;
  }

  /**
   * Returns the attemptUser.
   * @return String
   */
  public String getAttemptUser() {
    return _attemptUser;
  }

  /**
   * Sets the attemptAccount.
   * @param attemptAccount The attemptAccount to set
   */
  public void setAttemptAccount(String attemptAccount) {
    _attemptAccount = attemptAccount;
  }

  /**
   * Sets the attemptUser.
   * @param attemptUser The attemptUser to set
   */
  public void setAttemptUser(String attemptUser) {
    _attemptUser = attemptUser;
  }
}
