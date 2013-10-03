package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;

/**
 * Represent the details of a failed login transaction.
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
public class BtxDetailsFailedLogin extends BTXDetails {
  static final long serialVersionUID = 2732902025899409984L;

  private String _attemptUser;
  private String _attemptAccount;

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_FAILED_LOGIN;
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
    sb.append("Login failed by Attempted User: ");
    Escaper.htmlEscape(_attemptUser, sb);
    sb.append(" Attempted Account: ");
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
   * @return
   */
  public String getAttemptAccount() {
    return _attemptAccount;
  }

  /**
   * @return
   */
  public String getAttemptUser() {
    return _attemptUser;
  }

  /**
   * @param string
   */
  public void setAttemptAccount(String string) {
    _attemptAccount = string;
  }

  /**
   * @param string
   */
  public void setAttemptUser(String string) {
    _attemptUser = string;
  }
}
