package com.ardais.bigr.iltds.assistants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;

public class Address implements java.io.Serializable {
  private String _addressID;
  private String _addressType;
  private String _addressAccountId;
  private String _addressName;
  private String _locationAddress1;
  private String _locationAddress2;
  private String _locationCity;
  private String _locationState;
  private String _locationZip;
  private String _country;
  private String _firstName;
  private String _lastName;
  private String _middleName;

  public Address() {
    super();
  }

  public Address(Address address) {
    super();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, address);
  }

  public void populateFromResultSet(Map columns, ResultSet rs) throws SQLException {
    if (columns.containsKey("address_id")) {
      setAddressID(rs.getString("address_id"));
    }
    if (columns.containsKey("address_type")) {
      setAddressType(rs.getString("address_type"));
    }
    if (columns.containsKey("ardais_acct_key")) {
      setAddressAccountId(rs.getString("ardais_acct_key"));
    }
    if (columns.containsKey("ardais_acct_company_desc")) {
      setAddressName(rs.getString("ardais_acct_company_desc"));
    }
    if (columns.containsKey("address_1")) {
      setLocationAddress1(rs.getString("address_1"));
    }
    if (columns.containsKey("address_2")) {
      setLocationAddress2(rs.getString("address_2"));
    }
    if (columns.containsKey("addr_city")) {
      setLocationCity(rs.getString("addr_city"));
    }
    if (columns.containsKey("addr_state")) {
      setLocationState(rs.getString("addr_state"));
    }
    if (columns.containsKey("addr_zip_code")) {
      setLocationZip(rs.getString("addr_zip_code"));
    }
    if (columns.containsKey("addr_country")) {
      setCountry(rs.getString("addr_country"));
    }
    if (columns.containsKey("first_name")) {
      setFirstName(rs.getString("first_name"));
    }
    if (columns.containsKey("middle_name")) {
      setMiddleName(rs.getString("middle_name"));
    }
    if (columns.containsKey("last_name")) {
      setLastName(rs.getString("last_name"));
    }
  }

  /**
   * @return
   */
  public String getAddressAccountId() {
    return _addressAccountId;
  }

  /**
   * @return
   */
  public String getAddressID() {
    return _addressID;
  }

  /**
   * @return
   */
  public String getAddressName() {
    return _addressName;
  }

  /**
   * @return
   */
  public String getAddressType() {
    return _addressType;
  }

  /**
   * @return
   */
  public String getContactName() {
    String contactFirstName = ApiFunctions.safeString(getFirstName());
    String contactMiddleName = ApiFunctions.safeString(getMiddleName());
    String contactLastName = ApiFunctions.safeString(getLastName());

    StringBuffer contactName = new StringBuffer(64);
    contactName.append(contactFirstName);
    if (!ApiFunctions.isEmpty(contactMiddleName)) {
      if (contactName.length() > 0) {
        contactName.append(' ');
      }
      contactName.append(contactMiddleName);
    }
    if (!ApiFunctions.isEmpty(contactLastName)) {
      int nameLen = contactName.length();
      if (nameLen > 0 && ' ' != contactName.charAt(nameLen - 1)) {
        contactName.append(' ');
      }
      contactName.append(contactLastName);
    }
    return contactName.toString();
  }

  /**
   * @return
   */
  public String getCountry() {
    return _country;
  }

  /**
   * @return
   */
  public String getLocationAddress1() {
    return _locationAddress1;
  }

  /**
   * @return
   */
  public String getLocationAddress2() {
    return _locationAddress2;
  }

  /**
   * @return
   */
  public String getLocationCity() {
    return _locationCity;
  }

  /**
   * @return
   */
  public String getLocationState() {
    return _locationState;
  }

  /**
   * @return
   */
  public String getLocationZip() {
    return _locationZip;
  }

  /**
   * @param string
   */
  public void setAddressAccountId(String string) {
    _addressAccountId = string;
  }

  /**
   * @param string
   */
  public void setAddressID(String string) {
    _addressID = string;
  }

  /**
   * @param string
   */
  public void setAddressName(String string) {
    _addressName = string;
  }

  /**
   * @param string
   */
  public void setAddressType(String string) {
    _addressType = string;
  }

  /**
   * @param string
   */
  public void setCountry(String string) {
    _country = string;
  }

  /**
   * @param string
   */
  public void setLocationAddress1(String string) {
    _locationAddress1 = string;
  }

  /**
   * @param string
   */
  public void setLocationAddress2(String string) {
    _locationAddress2 = string;
  }

  /**
   * @param string
   */
  public void setLocationCity(String string) {
    _locationCity = string;
  }

  /**
   * @param string
   */
  public void setLocationState(String string) {
    _locationState = string;
  }

  /**
   * @param string
   */
  public void setLocationZip(String string) {
    _locationZip = string;
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
  public String getMiddleName() {
    return _middleName;
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
  public void setMiddleName(String string) {
    _middleName = string;
  }

}
