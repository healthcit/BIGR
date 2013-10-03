package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.es.beans.ArdaisaddressAccessBean;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * Represents the data of an address.
 */
public class AddressDto implements Serializable {

	private String _addressId;
	private String _accountId;
  private String _type;
	private String _address1;
	private String _address2;
	private String _city;
  private String _state;
	private String _zipCode;
  private String _country;
  private String _firstName;
  private String _lastName;
  private String _middleName;

	/**
	 * Creates a new <code>AddressDto</code>.
	 */
	public AddressDto() {
	}

  /**
   * Creates a new <code>AddressDto</code>, initialized from
   * the data in the ArdaisaddressAccessBean.
   *
   * @param  accessBean  a <code>ArdaisaddressAccessBean</code> containing information about 
   * the location.
   */
  public AddressDto(ArdaisaddressAccessBean accessBean) {
    this();
    populate(accessBean);
  }
  /**
   * Create a new <code>AddressDto</code> initialized from the data in the current row of 
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   or {@link com.ardais.bigr.util.DbConstants}
   *                   or the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public AddressDto(Map columnNames, ResultSet rs) {
    this();
    populate(columnNames, rs);
  }
  
  public void populate(ArdaisaddressAccessBean accessBean) {
    if (accessBean == null) {
      return;
    }
    try {
      setType(accessBean.getAddress_type());
      setAddress1(accessBean.getAddress_1());
      setAddress2(accessBean.getAddress_2());
      setCity(accessBean.getAddr_city());
      setState(accessBean.getAddr_state());
      setZipCode(accessBean.getAddr_zip_code());
      setCountry(accessBean.getAddr_country());
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
  }
  /**
   * Populates this <code>AddressDto</code> from the data in the current row of 
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   or {@link com.ardais.bigr.util.DbConstants}
   *                   or the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbConstants.ADDRESS_ACCOUNT_KEY)) {
        setAccountId(rs.getString(DbConstants.ADDRESS_ACCOUNT_KEY));
      }
      if (columns.containsKey(DbConstants.ADDRESS_ADDRESS_1)) {
        setAddress1(rs.getString(DbConstants.ADDRESS_ADDRESS_1));
      }
      if (columns.containsKey(DbConstants.ADDRESS_ADDRESS_2)) {
        setAddress2(rs.getString(DbConstants.ADDRESS_ADDRESS_2));
      }
      if (columns.containsKey(DbConstants.ADDRESS_ADDRESS_ID)) {
        setAddressId(rs.getString(DbConstants.ADDRESS_ADDRESS_ID));
      }
      if (columns.containsKey(DbConstants.ADDRESS_CITY)) {
        setCity(rs.getString(DbConstants.ADDRESS_CITY));
      }
      if (columns.containsKey(DbConstants.ADDRESS_COUNTRY)) {
        setCountry(rs.getString(DbConstants.ADDRESS_COUNTRY));
      }
      if (columns.containsKey(DbConstants.ADDRESS_FIRST_NAME)) {
        setFirstName(rs.getString(DbConstants.ADDRESS_FIRST_NAME));
      }
      if (columns.containsKey(DbConstants.ADDRESS_LAST_NAME)) {
        setLastName(rs.getString(DbConstants.ADDRESS_LAST_NAME));
      }
      if (columns.containsKey(DbConstants.ADDRESS_MIDDLE_NAME)) {
        setMiddleName(rs.getString(DbConstants.ADDRESS_MIDDLE_NAME));
      }
      if (columns.containsKey(DbConstants.ADDRESS_STATE)) {
        setState(rs.getString(DbConstants.ADDRESS_STATE));
      }
      if (columns.containsKey(DbConstants.ADDRESS_ADDRESS_TYPE)) {
        setType(rs.getString(DbConstants.ADDRESS_ADDRESS_TYPE));
      }
      if (columns.containsKey(DbConstants.ADDRESS_ZIP_CODE)) {
        setZipCode(rs.getString(DbConstants.ADDRESS_ZIP_CODE));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ACCOUNT_KEY)) {
        setAccountId(rs.getString(DbAliases.ADDRESS_ACCOUNT_KEY));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ADDRESS_1)) {
        setAddress1(rs.getString(DbAliases.ADDRESS_ADDRESS_1));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ADDRESS_2)) {
        setAddress2(rs.getString(DbAliases.ADDRESS_ADDRESS_2));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ADDRESS_ID)) {
        setAddressId(rs.getString(DbAliases.ADDRESS_ADDRESS_ID));
      }
      if (columns.containsKey(DbAliases.ADDRESS_CITY)) {
        setCity(rs.getString(DbAliases.ADDRESS_CITY));
      }
      if (columns.containsKey(DbAliases.ADDRESS_COUNTRY)) {
        setCountry(rs.getString(DbAliases.ADDRESS_COUNTRY));
      }
      if (columns.containsKey(DbAliases.ADDRESS_FIRST_NAME)) {
        setFirstName(rs.getString(DbAliases.ADDRESS_FIRST_NAME));
      }
      if (columns.containsKey(DbAliases.ADDRESS_LAST_NAME)) {
        setLastName(rs.getString(DbAliases.ADDRESS_LAST_NAME));
      }
      if (columns.containsKey(DbAliases.ADDRESS_MIDDLE_NAME)) {
        setMiddleName(rs.getString(DbAliases.ADDRESS_MIDDLE_NAME));
      }
      if (columns.containsKey(DbAliases.ADDRESS_STATE)) {
        setState(rs.getString(DbAliases.ADDRESS_STATE));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ADDRESS_TYPE)) {
        setType(rs.getString(DbAliases.ADDRESS_ADDRESS_TYPE));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ZIP_CODE)) {
        setZipCode(rs.getString(DbAliases.ADDRESS_ZIP_CODE));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }
  
  /**
   * @return Returns the accountId.
   */
  public String getAccountId() {
    return _accountId;
  }
  
  /**
   * @return Returns the address1.
   */
  public String getAddress1() {
    return _address1;
  }
  
  /**
   * @return Returns the address2.
   */
  public String getAddress2() {
    return _address2;
  }
  
  /**
   * @return Returns the addressId.
   */
  public String getAddressId() {
    return _addressId;
  }
  
  /**
   * @return Returns the city.
   */
  public String getCity() {
    return _city;
  }
  
  /**
   * @return Returns the country.
   */
  public String getCountry() {
    return _country;
  }
  
  /**
   * @return Returns the firstName.
   */
  public String getFirstName() {
    return _firstName;
  }
  
  /**
   * @return Returns the lastName.
   */
  public String getLastName() {
    return _lastName;
  }
  
  /**
   * @return Returns the middleName.
   */
  public String getMiddleName() {
    return _middleName;
  }
  
  /**
   * @return Returns the state.
   */
  public String getState() {
    return _state;
  }
  
  /**
   * @return Returns the type.
   */
  public String getType() {
    return _type;
  }
  
  /**
   * @return Returns the zipCode.
   */
  public String getZipCode() {
    return _zipCode;
  }
  
  /**
   * @param accountId The accountId to set.
   */
  public void setAccountId(String accountId) {
    _accountId = accountId;
  }
  
  /**
   * @param address1 The address1 to set.
   */
  public void setAddress1(String address1) {
    _address1 = address1;
  }
  
  /**
   * @param address2 The address2 to set.
   */
  public void setAddress2(String address2) {
    _address2 = address2;
  }
  
  /**
   * @param addressId The addressId to set.
   */
  public void setAddressId(String addressId) {
    _addressId = addressId;
  }
  
  /**
   * @param city The city to set.
   */
  public void setCity(String city) {
    _city = city;
  }
  
  /**
   * @param country The country to set.
   */
  public void setCountry(String country) {
    _country = country;
  }
  
  /**
   * @param firstName The firstName to set.
   */
  public void setFirstName(String firstName) {
    _firstName = firstName;
  }
  
  /**
   * @param lastName The lastName to set.
   */
  public void setLastName(String lastName) {
    _lastName = lastName;
  }
  
  /**
   * @param middleName The middleName to set.
   */
  public void setMiddleName(String middleName) {
    _middleName = middleName;
  }
  
  /**
   * @param state The state to set.
   */
  public void setState(String state) {
    _state = state;
  }
  
  /**
   * @param type The type to set.
   */
  public void setType(String type) {
    _type = type;
  }
  
  /**
   * @param zipCode The zipCode to set.
   */
  public void setZipCode(String zipCode) {
    _zipCode = zipCode;
  }
}