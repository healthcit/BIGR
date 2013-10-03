package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.ardais.bigr.api.ApiFunctions;
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
 * <li>{@link #setPassword(String) Password}: The password of the user who is attemting
 *     to login.</li>
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
public class BTXDetailsLogin extends BTXDetails {
  private String _attemptUser;
  private String _attemptAccount;
  private String _password;
  private Vector _profile;
  private List _menuUrls;
  private boolean passwordExpired;

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_LOGIN;
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
    sb.append("Login succeeded by User: ");
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
   * Returns the password.
   * @return String
   */
  public String getPassword() {
    return _password;
  }

  /**
   * Sets the attemptAccount.
   * @param attemptAccount The attemptAccount to set
   */
  public void setAttemptAccount(String attemptAccount) {
    _attemptAccount = ApiFunctions.safeTrim(attemptAccount);
  }

  /**
   * Sets the attemptUser.
   * @param attemptUser The attemptUser to set
   */
  public void setAttemptUser(String attemptUser) {
    _attemptUser = ApiFunctions.safeTrim(attemptUser);
  }

  /**
   * Sets the password.
   * @param password The password to set
   */
  public void setPassword(String password) {
    _password = password;
  }
  /**
   * Returns the profile.
   * @return Vector
   */
  public Vector getProfile() {
    return _profile;
  }

  /**
   * Sets the profile.
   * @param profile The profile to set
   */
  public void setProfile(Vector profile) {
    _profile = profile;
  }

  /**
   * @return
   */
  public boolean isPasswordExpired() {
    return passwordExpired;
  }

  /**
   * @param b
   */
  public void setPasswordExpired(boolean b) {
    passwordExpired = b;
  }
  /**
   * @return
   */
  public List getMenuUrls() {
    return _menuUrls;
  }

  /**
   * @param list
   */
  public void setMenuUrls(List list) {
    _menuUrls = list;
  }

}
