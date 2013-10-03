package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.es.beans.ArdaisuserAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * A Data Transfer Object that represents an Ardais user.
 */
public class UserDto implements Serializable {
  static final long serialVersionUID = 4077452342978881694L;
  
  private String _userId;
  private String _accountId;
  private String _firstName;
  private String _lastName;
  private String _title;
  private String _functionalTitle;
  private String _affiliation;
  private String _phoneNumber;
  private String _extension;
  private String _faxNumber;
  private String _mobileNumber;
  private String _email;
  private LocationData _address;
  private String _password1;
  private String _password2;
  private String _passwordPolicy;
  private String _passwordQuestion;
  private String _passwordAnswer;
  private String _activeYN;
  private String _department;

  /**
   * Creates a new UserDto.
   */
  public UserDto() {
    super();
  }

  /**
   * Creates a new <code>UserDto</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public UserDto(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
  }

  /**
   * Creates a new <code>UserDto</code>, initialized from
   * the data in the ArdaisuserAccessBean.
   *
   * @param  accessBean  a <code>ArdaisuserAccessBean</code> containing information about the user.
   */
  public UserDto(ArdaisuserAccessBean accessBean) {
    this();
    populate(accessBean);
  }

  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbConstants.ARDAIS_USER_ID)) {
        setUserId(rs.getString(DbConstants.ARDAIS_USER_ID));
      }
      if (columns.containsKey(DbConstants.USER_ACCT_KEY)) {
        setAccountId(rs.getString(DbConstants.USER_ACCT_KEY));
      }
      if (columns.containsKey(DbConstants.ARDAIS_USER_USER_FIRSTNAME)) {
        setFirstName(rs.getString(DbConstants.ARDAIS_USER_USER_FIRSTNAME));
      }
      if (columns.containsKey(DbConstants.ARDAIS_USER_USER_LASTNAME)) {
        setLastName(rs.getString(DbConstants.ARDAIS_USER_USER_LASTNAME));
      }
      if (columns.containsKey(DbConstants.ARDAIS_USER_USER_ACTIVE_IND)) {
        setActiveYN(rs.getString(DbConstants.ARDAIS_USER_USER_ACTIVE_IND));
      }
      if (columns.containsKey(DbAliases.ARDAIS_USER_ID)) {
        setUserId(rs.getString(DbAliases.ARDAIS_USER_ID));
      }
      if (columns.containsKey(DbAliases.USER_ACCT_KEY)) {
        setAccountId(rs.getString(DbAliases.USER_ACCT_KEY));
      }
      if (columns.containsKey(DbAliases.ARDAIS_USER_USER_FIRSTNAME)) {
        setFirstName(rs.getString(DbAliases.ARDAIS_USER_USER_FIRSTNAME));
      }
      if (columns.containsKey(DbAliases.ARDAIS_USER_USER_LASTNAME)) {
        setLastName(rs.getString(DbAliases.ARDAIS_USER_USER_LASTNAME));
      }
      if (columns.containsKey(DbAliases.ARDAIS_USER_USER_ACTIVE_IND)) {
        setActiveYN(rs.getString(DbAliases.ARDAIS_USER_USER_ACTIVE_IND));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }
  
  public void populate(ArdaisuserAccessBean accessBean) {
    if (accessBean == null) {
      return;
    }
    try {
      setUserId(((ArdaisuserKey) accessBean.__getKey()).ardais_user_id);
      setAccountId(accessBean.getArdaisaccountKey().ardais_acct_key);
      setFirstName(accessBean.getUser_firstname());
      setLastName(accessBean.getUser_lastname());
      setTitle(accessBean.getUser_title());
      setFunctionalTitle(accessBean.getUser_functional_title());
      setAffiliation(accessBean.getUser_affiliation());
      setPhoneNumber(accessBean.getUser_phone_number());
      setExtension(accessBean.getUser_phone_ext());
      setFaxNumber(accessBean.getUser_fax_number());
      setMobileNumber(accessBean.getUser_mobile_number());
      setEmail(accessBean.getUser_email_address());
      setPasswordPolicy(accessBean.getPasswordPolicyCid());
      setActiveYN(accessBean.getUser_active_ind());
      setPasswordQuestion(accessBean.getUser_verif_question());
      setPasswordAnswer(accessBean.getUser_verif_answer());
      setDepartment(accessBean.getUser_department());
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }
  
  public void populate(UserDto user) {
    if (user == null) {
      return;
    }
    setUserId(user.getUserId());
    setAccountId(user.getAccountId());
    setFirstName(user.getFirstName());
    setLastName(user.getLastName());
    setTitle(user.getTitle());
    setFunctionalTitle(user.getFunctionalTitle());
    setAffiliation(user.getAffiliation());
    setPhoneNumber(user.getPhoneNumber());
    setExtension(user.getExtension());
    setFaxNumber(user.getFaxNumber());
    setMobileNumber(user.getMobileNumber());
    setEmail(user.getEmail());
    setPasswordPolicy(user.getPasswordPolicy());
    setActiveYN(user.getActiveYN());
    setPasswordQuestion(user.getPasswordQuestion());
    setPasswordAnswer(user.getPasswordAnswer());
    setDepartment(user.getDepartment());
  }

  /**
   * @return
   */
  public String getAccountId() {
    return _accountId;
  }

  /**
   * @return
   */
  public String getFirstName() {
    return _firstName;
  }

  /**
   * @return
   */
  public String getLastName() {
    return _lastName;
  }

  /**
   * @return
   */
  public String getUserId() {
    return _userId;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param string
   */
  public void setFirstName(String string) {
    _firstName = string;
  }

  /**
   * @param string
   */
  public void setLastName(String string) {
    _lastName = string;
  }

  /**
   * @param string
   */
  public void setUserId(String string) {
    _userId = string;
  }
  /**
   * @return
   */
  public LocationData getAddress() {
    if (_address == null) {
      _address = new LocationData();
    }
    return _address;
  }

  /**
   * @return
   */
  public String getEmail() {
    return _email;
  }

  /**
   * @return
   */
  public String getExtension() {
    return _extension;
  }

  /**
   * @return
   */
  public String getFaxNumber() {
    return _faxNumber;
  }

  /**
   * @return
   */
  public String getPassword1() {
    return _password1;
  }

  /**
   * @return
   */
  public String getPassword2() {
    return _password2;
  }

  /**
   * @return
   */
  public String getPasswordPolicy() {
    return _passwordPolicy;
  }

  /**
   * @return
   */
  public String getPhoneNumber() {
    return _phoneNumber;
  }

  /**
   * @param data
   */
  public void setAddress(LocationData data) {
    _address = data;
  }

  /**
   * @param string
   */
  public void setEmail(String string) {
    _email = string;
  }

  /**
   * @param string
   */
  public void setExtension(String string) {
    _extension = string;
  }

  /**
   * @param string
   */
  public void setFaxNumber(String string) {
    _faxNumber = string;
  }

  /**
   * @param string
   */
  public void setPassword1(String string) {
    _password1 = string;
  }

  /**
   * @param string
   */
  public void setPassword2(String string) {
    _password2 = string;
  }

  /**
   * @param string
   */
  public void setPasswordPolicy(String string) {
    _passwordPolicy = string;
  }

  /**
   * @param string
   */
  public void setPhoneNumber(String string) {
    _phoneNumber = string;
  }

  /**
   * @return
   */
  public String getActiveYN() {
    return _activeYN;
  }

  /**
   * @param string
   */
  public void setActiveYN(String string) {
    _activeYN = string;
  }

  /**
   * @return
   */
  public String getAffiliation() {
    return _affiliation;
  }

  /**
   * @return
   */
  public String getFunctionalTitle() {
    return _functionalTitle;
  }

  /**
   * @return
   */
  public String getMobileNumber() {
    return _mobileNumber;
  }

  /**
   * @return
   */
  public String getTitle() {
    return _title;
  }

  /**
   * @param string
   */
  public void setAffiliation(String string) {
    _affiliation = string;
  }

  /**
   * @param string
   */
  public void setFunctionalTitle(String string) {
    _functionalTitle = string;
  }

  /**
   * @param string
   */
  public void setMobileNumber(String string) {
    _mobileNumber = string;
  }

  /**
   * @param string
   */
  public void setTitle(String string) {
    _title = string;
  }

  /**
   * @return
   */
  public String getPasswordAnswer() {
    return _passwordAnswer;
  }

  /**
   * @return
   */
  public String getPasswordQuestion() {
    return _passwordQuestion;
  }

  /**
   * @param string
   */
  public void setPasswordAnswer(String string) {
    _passwordAnswer = string;
  }

  /**
   * @param string
   */
  public void setPasswordQuestion(String string) {
    _passwordQuestion = string;
  }

  /**
   * @return Returns the department.
   */
  public String getDepartment() {
    return _department;
  }
  
  /**
   * @param department The department to set.
   */
  public void setDepartment(String department) {
    _department = department;
  }

}
