package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.es.beans.ArdaisaddressAccessBean;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * Represents the raw data of a geolocation.
 */
public class LocationData implements Serializable {

	private String _addressId;
	private String _name;
	private String _address1;
	private String _address2;
	private String _city;
  private String _state;
	private String _zipCode;
  private String _country;
  private String _phoneNumber;
  private AddressDto _shipToAddress;
  private List _existingStorageUnits;
  private List _newStorageUnits;

	/**
	 * Creates a new <code>LocationData</code>.
	 */
	public LocationData() {
	}

  /**
   * Creates a new <code>LocationData</code>, initialized from
   * the data in the ArdaisaddressAccessBean.
   *
   * @param  accessBean  a <code>ArdaisaddressAccessBean</code> containing information about 
   * the location.
   */
  public LocationData(ArdaisaddressAccessBean accessBean) {
    this();
    populate(accessBean);
  }

  /**
   * Create a new <code>LocationData</code> initialized from the data in the current row of 
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   or {@link com.ardais.bigr.util.DbConstants}
   *                   or the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public LocationData(Map columnNames, ResultSet rs) {
    this();
    populate(columnNames, rs);
  }
  
  public void populate(ArdaisaddressAccessBean accessBean) {
    if (accessBean == null) {
      return;
    }
    try {
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
   * Populates this <code>LocationData</code> from the data in the current row of 
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
      if (columns.containsKey(DbConstants.LOCATION_ADDRESS_ID)) {
        setAddressId(rs.getString(DbConstants.LOCATION_ADDRESS_ID));
      }
      if (columns.containsKey(DbConstants.LOCATION_NAME)) {
        setName(rs.getString(DbConstants.LOCATION_NAME));
      }
      if (columns.containsKey(DbConstants.LOCATION_ADDRESS_1)) {
        setAddress1(rs.getString(DbConstants.LOCATION_ADDRESS_1));
      }
      if (columns.containsKey(DbConstants.LOCATION_ADDRESS_2)) {
        setAddress2(rs.getString(DbConstants.LOCATION_ADDRESS_2));
      }
      if (columns.containsKey(DbConstants.LOCATION_CITY)) {
        setCity(rs.getString(DbConstants.LOCATION_CITY));
      }
      if (columns.containsKey(DbConstants.LOCATION_STATE)) {
        setState(rs.getString(DbConstants.LOCATION_STATE));
      }
      if (columns.containsKey(DbConstants.LOCATION_ZIP)) {
        setZipCode(rs.getString(DbConstants.LOCATION_ZIP));
      }
      if (columns.containsKey(DbConstants.LOCATION_COUNTRY)) {
        setCountry(rs.getString(DbConstants.LOCATION_COUNTRY));
      }
      if (columns.containsKey(DbConstants.LOCATION_PHONE)) {
        setPhoneNumber(rs.getString(DbConstants.LOCATION_PHONE));
      }
      if (columns.containsKey(DbAliases.LOCATION_ADDRESS_ID)) {
        setAddressId(rs.getString(DbAliases.LOCATION_ADDRESS_ID));
      }
      if (columns.containsKey(DbAliases.LOCATION_NAME)) {
        setName(rs.getString(DbAliases.LOCATION_NAME));
      }
      if (columns.containsKey(DbAliases.LOCATION_ADDRESS_1)) {
        setAddress1(rs.getString(DbAliases.LOCATION_ADDRESS_1));
      }
      if (columns.containsKey(DbAliases.LOCATION_ADDRESS_2)) {
        setAddress2(rs.getString(DbAliases.LOCATION_ADDRESS_2));
      }
      if (columns.containsKey(DbAliases.LOCATION_CITY)) {
        setCity(rs.getString(DbAliases.LOCATION_CITY));
      }
      if (columns.containsKey(DbAliases.LOCATION_STATE)) {
        setState(rs.getString(DbAliases.LOCATION_STATE));
      }
      if (columns.containsKey(DbAliases.LOCATION_ZIP)) {
        setZipCode(rs.getString(DbAliases.LOCATION_ZIP));
      }
      if (columns.containsKey(DbAliases.LOCATION_COUNTRY)) {
        setCountry(rs.getString(DbAliases.LOCATION_COUNTRY));
      }
      if (columns.containsKey(DbAliases.LOCATION_PHONE)) {
        setPhoneNumber(rs.getString(DbAliases.LOCATION_PHONE));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }
  
  /**
   * @return
   */
  public String getAddress1() {
    return _address1;
  }

  /**
   * @return
   */
  public String getAddress2() {
    return _address2;
  }

  /**
   * @return
   */
  public String getAddressId() {
    return _addressId;
  }

  /**
   * @return
   */
  public String getCity() {
    return _city;
  }

  /**
   * @return
   */
  public String getName() {
    return _name;
  }

  /**
   * @return
   */
  public String getState() {
    return _state;
  }

  /**
   * @return
   */
  public String getZipCode() {
    return _zipCode;
  }

  /**
   * @param string
   */
  public void setAddress1(String string) {
    _address1 = string;
  }

  /**
   * @param string
   */
  public void setAddress2(String string) {
    _address2 = string;
  }

  /**
   * @param string
   */
  public void setAddressId(String string) {
    _addressId = string;
  }

  /**
   * @param string
   */
  public void setCity(String string) {
    _city = string;
  }

  /**
   * @param string
   */
  public void setName(String string) {
    _name = string;
  }

  /**
   * @param string
   */
  public void setState(String string) {
    _state = string;
  }

  /**
   * @param string
   */
  public void setZipCode(String string) {
    _zipCode = string;
  }

  /**
   * @return
   */
  public String getCountry() {
    return _country;
  }

  /**
   * @param string
   */
  public void setCountry(String string) {
    _country = string;
  }

  /**
   * @return Returns the existingStorageUnits.
   */
  public List getExistingStorageUnits() {
    if (_existingStorageUnits == null) {
      _existingStorageUnits = new ArrayList();
    }
    return _existingStorageUnits;
  }
  
  /**
   * @param existingStorageUnits The existingStorageUnits to set.
   */
  public void setExistingStorageUnits(List existingStorageUnits) {
    _existingStorageUnits = existingStorageUnits;
  }
  
  /**
   * @return Returns the phoneNumber.
   */
  public String getPhoneNumber() {
    return _phoneNumber;
  }
  
  /**
   * @param phoneNumber The phoneNumber to set.
   */
  public void setPhoneNumber(String phoneNumber) {
    _phoneNumber = phoneNumber;
  }
  
  /**
   * @return Returns the newStorageUnits.
   */
  public List getNewStorageUnits() {
    if (_newStorageUnits == null) {
      _newStorageUnits = new ArrayList();
    }
    return _newStorageUnits;
  }

  public StorageUnitDto getNewStorageUnit(int index) {    
    return (StorageUnitDto) getNewStorageUnits().get(index);
  }

  public void addNewStorageUnit(StorageUnitDto dto) {    
    getNewStorageUnits().add(dto); 
  }

  public void addExistingStorageUnit(StorageUnitSummaryDto dto) {    
    getExistingStorageUnits().add(dto); 
  }
  
  /**
   * @return Returns the shipToAddress.
   */
  public AddressDto getShipToAddress() {
    if (_shipToAddress == null) {
      _shipToAddress = new AddressDto();
    }
    return _shipToAddress;
  }
  
  /**
   * @param shipToAddress The shipToAddress to set.
   */
  public void setShipToAddress(AddressDto shipToAddress) {
    _shipToAddress = shipToAddress;
  }
}
