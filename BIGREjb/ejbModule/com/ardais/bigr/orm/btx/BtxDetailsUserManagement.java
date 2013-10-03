package com.ardais.bigr.orm.btx;

import java.io.Serializable;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.util.Constants;
import com.gulfstreambio.gboss.GbossFactory;

public abstract class BtxDetailsUserManagement extends BTXDetails implements Serializable {
  
  private UserDto _userData;
  private BtxHistoryAttributes _attributes;
  private LegalValueSet _accountChoices;
  private String _oldPasswordQuestion = null;
  private String _oldPasswordAnswer = null;

  /**
   * 
   */
  public BtxDetailsUserManagement() {
    super();
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set to
   *    the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    history.setHistoryObject(describeAsHistoryObject());
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    UserDto user = new UserDto();
    user.setAddress(new LocationData());
    setUserData(user);
    _attributes = (BtxHistoryAttributes)history.getHistoryObject();
    if (_attributes != null) {
      Map attributeMap = _attributes.asMap();
      BigrBeanUtilsBean.SINGLETON.copyProperties(this,attributeMap);
    }
    _accountChoices = null;
  }

  private Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = new BtxHistoryAttributes();
    UserDto userData = getUserData();
    if (userData != null) {
      if (!ApiFunctions.isEmpty(userData.getUserId())) {
        attributes.setAttribute("userData.userId", userData.getUserId());
      }
      if (!ApiFunctions.isEmpty(userData.getAccountId())) {
        attributes.setAttribute("userData.accountId", userData.getAccountId());
      }
      if (!ApiFunctions.isEmpty(userData.getFirstName())) {
        attributes.setAttribute("userData.firstName", userData.getFirstName());
      }
      if (!ApiFunctions.isEmpty(userData.getLastName())) {
        attributes.setAttribute("userData.lastName", userData.getLastName());
      }
      if (!ApiFunctions.isEmpty(userData.getEmail())) {
        attributes.setAttribute("userData.email", userData.getEmail());
      }
      if (!ApiFunctions.isEmpty(userData.getDepartment())) {
        attributes.setAttribute("userData.department", userData.getDepartment());
      }
      if (!ApiFunctions.isEmpty(userData.getTitle())) {
        attributes.setAttribute("userData.title", userData.getTitle());
      }
      if (!ApiFunctions.isEmpty(userData.getFunctionalTitle())) {
        attributes.setAttribute("userData.functionalTitle", userData.getFunctionalTitle());
      }
      if (!ApiFunctions.isEmpty(userData.getAffiliation())) {
        attributes.setAttribute("userData.affiliation", userData.getAffiliation());
      }
      if (!ApiFunctions.isEmpty(userData.getPhoneNumber())) {
        attributes.setAttribute("userData.phoneNumber", userData.getPhoneNumber());
      }
      if (!ApiFunctions.isEmpty(userData.getExtension())) {
        attributes.setAttribute("userData.extension", userData.getExtension());
      }
      if (!ApiFunctions.isEmpty(userData.getFaxNumber())) {
        attributes.setAttribute("userData.faxNumber", userData.getFaxNumber());
      }
      if (!ApiFunctions.isEmpty(userData.getMobileNumber())) {
        attributes.setAttribute("userData.mobileNumber", userData.getMobileNumber());
      }
      if (!ApiFunctions.isEmpty(userData.getPasswordPolicy())) {
        attributes.setAttribute("userData.passwordPolicy", GbossFactory.getInstance().getDescription(userData.getPasswordPolicy()));
      }
      if (!ApiFunctions.isEmpty(getOldPasswordQuestion()) &&
           ApiFunctions.isEmpty(userData.getPasswordQuestion())) {
        attributes.setAttribute("userData.passwordQuestion", Constants.NONEMPTY_TO_EMPTY);
      }
      else if (ApiFunctions.isEmpty(getOldPasswordQuestion()) &&
               !ApiFunctions.isEmpty(userData.getPasswordQuestion())) {
        attributes.setAttribute("userData.passwordQuestion", Constants.EMPTY_TO_NONEMPTY);
      }
      else if (!ApiFunctions.isEmpty(getOldPasswordQuestion()) &&
               !ApiFunctions.isEmpty(userData.getPasswordQuestion()) &&
               !getOldPasswordQuestion().equalsIgnoreCase(userData.getPasswordQuestion())) {
        attributes.setAttribute("userData.passwordQuestion", Constants.NONEMPTY_TO_NONEMPTY);
      }
      if (!ApiFunctions.isEmpty(getOldPasswordAnswer()) &&
           ApiFunctions.isEmpty(userData.getPasswordAnswer())) {
        attributes.setAttribute("userData.passwordAnswer", Constants.NONEMPTY_TO_EMPTY);
      }
      else if (ApiFunctions.isEmpty(getOldPasswordAnswer()) &&
               !ApiFunctions.isEmpty(userData.getPasswordAnswer())) {
        attributes.setAttribute("userData.passwordAnswer", Constants.EMPTY_TO_NONEMPTY);
      }
      else if (!ApiFunctions.isEmpty(getOldPasswordAnswer()) &&
               !ApiFunctions.isEmpty(userData.getPasswordAnswer()) &&
               !getOldPasswordAnswer().equalsIgnoreCase(userData.getPasswordAnswer())) {
        attributes.setAttribute("userData.passwordAnswer", Constants.NONEMPTY_TO_NONEMPTY);
      }
      if (!ApiFunctions.isEmpty(userData.getActiveYN())) {
        attributes.setAttribute("userData.activeYN", userData.getActiveYN());
      }
      LocationData address = userData.getAddress();
      if (address != null) {
        if (!ApiFunctions.isEmpty(address.getAddress1())) {
          attributes.setAttribute("userData.address.address1", address.getAddress1());
        }
        if (!ApiFunctions.isEmpty(address.getAddress2())) {
          attributes.setAttribute("userData.address.address2", address.getAddress2());
        }
        if (!ApiFunctions.isEmpty(address.getCity())) {
          attributes.setAttribute("userData.address.city", address.getCity());
        }
        if (!ApiFunctions.isEmpty(address.getState())) {
          attributes.setAttribute("userData.address.state", address.getState());
        }
        if (!ApiFunctions.isEmpty(address.getZipCode())) {
          attributes.setAttribute("userData.address.zipCode", address.getZipCode());
        }
        if (!ApiFunctions.isEmpty(address.getCountry())) {
          attributes.setAttribute("userData.address.country", address.getCountry());
        }
      }
    }
    return attributes;
  }

  /**
   * @return
   */
  public LegalValueSet getAccountChoices() {
    return _accountChoices;
  }

  /**
   * @return
   */
  public BtxHistoryAttributes getAttributes() {
    return _attributes;
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
   * @return
   */
  public String getOldPasswordAnswer() {
    return _oldPasswordAnswer;
  }

  /**
   * @return
   */
  public String getOldPasswordQuestion() {
    return _oldPasswordQuestion;
  }

  /**
   * @param set
   */
  public void setAccountChoices(LegalValueSet set) {
    _accountChoices = set;
  }

  /**
   * @param attributes
   */
  public void setAttributes(BtxHistoryAttributes attributes) {
    _attributes = attributes;
  }

  /**
   * @param dto
   */
  public void setUserData(UserDto dto) {
    _userData = dto;
  }

  /**
   * @param string
   */
  public void setOldPasswordAnswer(String string) {
    _oldPasswordAnswer = string;
  }

  /**
   * @param string
   */
  public void setOldPasswordQuestion(String string) {
    _oldPasswordQuestion = string;
  }

}
