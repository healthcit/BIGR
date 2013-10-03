package com.ardais.bigr.orm.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of modifying a user.
 * <p>
 * The transaction-specific fields are described below.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserData(UserDto) userData}: The user dto containing the information about the user to modify.</li>
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
public class BtxDetailsModifyUser extends BtxDetailsUserManagement implements Serializable {
  private static final long serialVersionUID = 5564187317928226192L;

  public BtxDetailsModifyUser() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MODIFY_USER;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    UserDto userData = getUserData();
    if (userData != null) {
      set.add(userData.getUserId());
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

    UserDto userDto = getUserData();
    LocationData address = userDto.getAddress();
    sb.append("Modified user ");
    
    sb.append(IcpUtils.prepareLink(userDto.getUserId(), securityInfo));
    sb.append(" in account ");
    sb.append(IcpUtils.prepareLink(userDto.getAccountId(), securityInfo));
    sb.append(".");

    boolean includeComma = false;
    StringBuffer more = new StringBuffer(200);
    if (!ApiFunctions.isEmpty(userDto.getFirstName())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a first name of ");
      Escaper.htmlEscape(userDto.getFirstName(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getLastName())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a last name of ");
      Escaper.htmlEscape(userDto.getLastName(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getEmail())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" an email address of ");
      Escaper.htmlEscape(userDto.getEmail(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getDepartment())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a department of ");
      Escaper.htmlEscape(userDto.getDepartment(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getTitle())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a title of ");
      Escaper.htmlEscape(userDto.getTitle(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getFunctionalTitle())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a functional role of ");
      Escaper.htmlEscape(userDto.getFunctionalTitle(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getAffiliation())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" an affiliation of ");
      Escaper.htmlEscape(userDto.getAffiliation(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getPhoneNumber())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a phone number of ");
      Escaper.htmlEscape(userDto.getPhoneNumber(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getExtension())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" an extension of ");
      Escaper.htmlEscape(userDto.getExtension(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getFaxNumber())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a fax number of ");
      Escaper.htmlEscape(userDto.getFaxNumber(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getMobileNumber())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a mobile number of ");
      Escaper.htmlEscape(userDto.getMobileNumber(), more);
    }

    String address1 = address.getAddress1();
    String address2 = address.getAddress2();
    if (!ApiFunctions.isEmpty(address1) || !ApiFunctions.isEmpty(address2)) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      String addressValue;
      if (!ApiFunctions.isEmpty(address1) && !ApiFunctions.isEmpty(address2)) {
        addressValue = address1 + "/" + address2;
      }
      else if (!ApiFunctions.isEmpty(address1)) {
        addressValue = address1;
      }
      else {
        addressValue = address2;
      }
      more.append(" an address of ");
      Escaper.htmlEscape(addressValue, more);
    }

    if (!ApiFunctions.isEmpty(address.getCity())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a city of ");
      Escaper.htmlEscape(address.getCity(), more);
    }

    if (!ApiFunctions.isEmpty(address.getState())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a state of ");
      Escaper.htmlEscape(address.getState(), more);
    }

    if (!ApiFunctions.isEmpty(address.getZipCode())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a postal code of ");
      Escaper.htmlEscape(address.getZipCode(), more);
    }

    if (!ApiFunctions.isEmpty(address.getCountry())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a country of ");
      Escaper.htmlEscape(address.getCountry(), more);
    }

    if (!ApiFunctions.isEmpty(userDto.getPasswordPolicy())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a password policy of ");
      Escaper.htmlEscape(userDto.getPasswordPolicy(), more);
    }

    if (more.length() > 0) {
      sb.append("  This user has");
      sb.append(more.toString());
      sb.append(".");
    }

    sb.append(" This user has been marked as ");
    if (FormLogic.DB_NO.equalsIgnoreCase(userDto.getActiveYN())) {
      sb.append("inactive");
    }
    else {
      sb.append("active");
    }
    sb.append(".");
    
    return sb.toString();
  }
}
