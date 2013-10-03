package com.ardais.bigr.orm.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.UserDto;

/**
 * Represents the details of a business transaction to change password.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserData(UserDto) userData}: The user dto containing the information about the user 
 *      who's password is changing, as well as the new password information.</li>
 *  * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setOldPassword(String) Old Password}: The old password.</li>
 * <li>{@link #setReasonForChange(String) ReasonForChange}: The reason for the password change.</li>
 * <li>{@link #setRequireLogin(boolean) RequireLogin}: A boolean indicating if the user must be 
 *      logged into the system first.</li>
 * <li>{@link #setRequireOldPassword(boolean) RequireOldPassword}: A boolean indicating if the user 
 *      must provide the old password.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BtxDetailsChangePassword extends BTXDetails {
  static final long serialVersionUID = 2624451863239306562L;

  private UserDto _userData;
  private String _oldPassword;
  private String _reasonForChange;
  private boolean _requireLogin = true;
  private boolean _requireOldPassword = true;
  
  public BtxDetailsChangePassword() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CHANGE_PASSWORD;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(getUserData().getUserId());
    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    StringBuffer sb = new StringBuffer(512);
    sb.append("Password for user ");
    Escaper.htmlEscape(getUserData().getUserId(), sb);
    sb.append(" in account ");
    Escaper.htmlEscape(getUserData().getAccountId(), sb);
    sb.append(" was changed");
    if (!ApiFunctions.isEmpty(getReasonForChange())) {
      sb.append(" (reason: ");
      Escaper.htmlEscape(getReasonForChange(), sb);
      sb.append(")");
    }
    sb.append(".");
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    history.setAttrib1(getUserData().getUserId());
    history.setAttrib2(getUserData().getAccountId());
    history.setAttrib3(getReasonForChange());
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    UserDto userData = new UserDto();
    userData.setUserId(history.getAttrib1());
    userData.setAccountId(history.getAttrib2());
    setUserData(userData);
    setReasonForChange(history.getAttrib3());
    setOldPassword(null);
  }

  /**
   * @return
   */
  public String getOldPassword() {
    return _oldPassword;
  }

  /**
   * @return
   */
  public String getReasonForChange() {
    return _reasonForChange;
  }

  /**
   * @return
   */
  public boolean isRequireLogin() {
    return _requireLogin;
  }

  /**
   * @return
   */
  public boolean isRequireOldPassword() {
    return _requireOldPassword;
  }

  /**
   * @return
   */
  public UserDto getUserData() {
    if (_userData == null) {
      _userData = new UserDto();
    }
    return _userData;
  }

  /**
   * @param string
   */
  public void setOldPassword(String string) {
    _oldPassword = string;
  }

  /**
   * @param string
   */
  public void setReasonForChange(String string) {
    _reasonForChange = string;
  }

  /**
   * @param b
   */
  public void setRequireLogin(boolean b) {
    _requireLogin = b;
  }

  /**
   * @param b
   */
  public void setRequireOldPassword(boolean b) {
    _requireOldPassword = b;
  }

  /**
   * @param dto
   */
  public void setUserData(UserDto dto) {
    _userData = dto;
  }

}
