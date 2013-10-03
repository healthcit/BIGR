package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * A Data Transfer Object that represents an account.
 */
public class AccountDto implements Serializable {
  static final long serialVersionUID = -2199791400576452537L;
  
  private String _id;
  private String _type;
  private String _status;
  private String _name;
  private String _parentName;
  private Date _openDate;
  private Date _activeDate;
  private Date _closeDate;
  private Date _deactivateDate;
  private String _deactivateReason;
  private Date _reactivateDate;
  private String _primaryLocation;
  private String _brandId;
  private String _requestManagerEmail;
  private String _viewLinkedCasesOnly;
  private String _passwordPolicy;
  private String _passwordLifeSpan;
  private String _defaultBoxLayoutId;
  private String _accountData;
  private String _accountDataEncoding;
  private String _contactAddressId;
  private ContactDto _contact;
  private LocationData _location;
  private String _donorRegistrationFormId = null;
  private String _donorRegistrationFormName = null;
  private String _requireUniqueSampleAliases;
  private String _requireSampleAliases;
  
  /**
   * Creates a new AccountDto.
   */
  public AccountDto() {
    super();
  }

  /**
   * Creates a new <code>AccountDto</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public AccountDto(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
  }

  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbConstants.ACCOUNT_KEY)) {
        setId(rs.getString(DbConstants.ACCOUNT_KEY));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_TYPE)) {
        setType(rs.getString(DbConstants.ACCOUNT_TYPE));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_STATUS)) {
        setStatus(rs.getString(DbConstants.ACCOUNT_STATUS));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_NAME)) {
        setName(rs.getString(DbConstants.ACCOUNT_NAME));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_PARENT_NAME)) {
        setParentName(rs.getString(DbConstants.ACCOUNT_PARENT_NAME));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_OPEN_DATE)) {
        setOpenDate(rs.getDate(DbConstants.ACCOUNT_OPEN_DATE));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_ACTIVE_DATE)) {
        setActiveDate(rs.getDate(DbConstants.ACCOUNT_ACTIVE_DATE));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_CLOSE_DATE)) {
        setCloseDate(rs.getDate(DbConstants.ACCOUNT_CLOSE_DATE));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_DEACTIVATE_DATE)) {
        setDeactivateDate(rs.getDate(DbConstants.ACCOUNT_DEACTIVATE_DATE));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_DEACTIVATE_REASON)) {
        setDeactivateReason(rs.getString(DbConstants.ACCOUNT_DEACTIVATE_REASON));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_REACTIVATE_DATE)) {
        setReactivateDate(rs.getDate(DbConstants.ACCOUNT_REACTIVATE_DATE));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_PRIMARY_LOCATION)) {
        setPrimaryLocation(rs.getString(DbConstants.ACCOUNT_PRIMARY_LOCATION));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_BRAND_ID)) {
        setBrandId(rs.getString(DbConstants.ACCOUNT_BRAND_ID));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_REQ_MGR_EMAIL)) {
        setRequestManagerEmail(rs.getString(DbConstants.ACCOUNT_REQ_MGR_EMAIL));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_LINKED_CASES_ONLY_YN)) {
        setViewLinkedCasesOnly(rs.getString(DbConstants.ACCOUNT_LINKED_CASES_ONLY_YN));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_PASSWORD_POLICY_CID)) {
        setPasswordPolicy(rs.getString(DbConstants.ACCOUNT_PASSWORD_POLICY_CID));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_PASSWORD_LIFE_SPAN)) {
        setPasswordLifeSpan(rs.getString(DbConstants.ACCOUNT_PASSWORD_LIFE_SPAN));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_DONOR_REGISTRATION_FORM)) {
        setDonorRegistrationFormId(rs.getString(DbConstants.ACCOUNT_DONOR_REGISTRATION_FORM));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_DEFAULT_BOX_LAYOUT_ID)) {
        setDefaultBoxLayoutId(rs.getString(DbConstants.ACCOUNT_DEFAULT_BOX_LAYOUT_ID));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_ACCOUNT_DATA)) {
        setAccountData(rs.getString(DbConstants.ACCOUNT_ACCOUNT_DATA));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_ACCOUNT_DATA_ENCODING)) {
        setAccountDataEncoding(rs.getString(DbConstants.ACCOUNT_ACCOUNT_DATA_ENCODING));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_CONTACT_ADDRESS_ID)) {
        setContactAddressId(rs.getString(DbConstants.ACCOUNT_CONTACT_ADDRESS_ID));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_SAMPLE_ALIASES_UNIQUE_YN)) {
        setRequireUniqueSampleAliases(rs.getString(DbConstants.ACCOUNT_SAMPLE_ALIASES_UNIQUE_YN));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_SAMPLE_ALIASES_REQUIRED_YN)) {
        setRequireSampleAliases(rs.getString(DbConstants.ACCOUNT_SAMPLE_ALIASES_REQUIRED_YN));
      }

      if (columns.containsKey(DbAliases.ACCOUNT_KEY)) {
        setId(rs.getString(DbAliases.ACCOUNT_KEY));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_TYPE)) {
        setType(rs.getString(DbAliases.ACCOUNT_TYPE));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_STATUS)) {
        setStatus(rs.getString(DbAliases.ACCOUNT_STATUS));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_NAME)) {
        setName(rs.getString(DbAliases.ACCOUNT_NAME));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_PARENT_NAME)) {
        setParentName(rs.getString(DbAliases.ACCOUNT_PARENT_NAME));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_OPEN_DATE)) {
        setOpenDate(rs.getDate(DbAliases.ACCOUNT_OPEN_DATE));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_ACTIVE_DATE)) {
        setActiveDate(rs.getDate(DbAliases.ACCOUNT_ACTIVE_DATE));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_CLOSE_DATE)) {
        setCloseDate(rs.getDate(DbAliases.ACCOUNT_CLOSE_DATE));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_DEACTIVATE_DATE)) {
        setDeactivateDate(rs.getDate(DbAliases.ACCOUNT_DEACTIVATE_DATE));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_DEACTIVATE_REASON)) {
        setDeactivateReason(rs.getString(DbAliases.ACCOUNT_DEACTIVATE_REASON));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_REACTIVATE_DATE)) {
        setReactivateDate(rs.getDate(DbAliases.ACCOUNT_REACTIVATE_DATE));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_PRIMARY_LOCATION)) {
        setPrimaryLocation(rs.getString(DbAliases.ACCOUNT_PRIMARY_LOCATION));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_BRAND_ID)) {
        setBrandId(rs.getString(DbAliases.ACCOUNT_BRAND_ID));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_REQ_MGR_EMAIL)) {
        setRequestManagerEmail(rs.getString(DbAliases.ACCOUNT_REQ_MGR_EMAIL));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_LINKED_CASES_ONLY_YN)) {
        setViewLinkedCasesOnly(rs.getString(DbAliases.ACCOUNT_LINKED_CASES_ONLY_YN));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_PASSWORD_POLICY_CID)) {
        setPasswordPolicy(rs.getString(DbAliases.ACCOUNT_PASSWORD_POLICY_CID));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_PASSWORD_LIFE_SPAN)) {
        setPasswordLifeSpan(rs.getString(DbAliases.ACCOUNT_PASSWORD_LIFE_SPAN));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_DONOR_REGISTRATION_FORM)) {
        setDonorRegistrationFormId(rs.getString(DbAliases.ACCOUNT_DONOR_REGISTRATION_FORM));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_DEFAULT_BOX_LAYOUT_ID)) {
        setDefaultBoxLayoutId(rs.getString(DbAliases.ACCOUNT_DEFAULT_BOX_LAYOUT_ID));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_ACCOUNT_DATA)) {
        setAccountData(rs.getString(DbAliases.ACCOUNT_ACCOUNT_DATA));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_ACCOUNT_DATA_ENCODING)) {
        setAccountDataEncoding(rs.getString(DbAliases.ACCOUNT_ACCOUNT_DATA_ENCODING));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_CONTACT_ADDRESS_ID)) {
        setContactAddressId(rs.getString(DbAliases.ACCOUNT_CONTACT_ADDRESS_ID));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_SAMPLE_ALIASES_UNIQUE_YN)) {
        setRequireUniqueSampleAliases(rs.getString(DbAliases.ACCOUNT_SAMPLE_ALIASES_UNIQUE_YN));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_SAMPLE_ALIASES_REQUIRED_YN)) {
        setRequireSampleAliases(rs.getString(DbAliases.ACCOUNT_SAMPLE_ALIASES_REQUIRED_YN));
      }
      
      populateContactInfo(columns, rs);

    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  public void populateContactInfo(Map columns, ResultSet rs) {
    ContactDto contactDto = getContact();
    AddressDto addressDto = contactDto.getAddress();
    
    try {
      if (columns.containsKey(DbConstants.ACCOUNT_CONTACT_PHONE_NUMBER)) {
        contactDto.setPhoneNumber(rs.getString(DbConstants.ACCOUNT_CONTACT_PHONE_NUMBER));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_CONTACT_PHONE_EXT)) {
        contactDto.setExtension(rs.getString(DbConstants.ACCOUNT_CONTACT_PHONE_EXT));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_CONTACT_FAX_NUMBER)) {
        contactDto.setFaxNumber(rs.getString(DbConstants.ACCOUNT_CONTACT_FAX_NUMBER));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_CONTACT_EMAIL_ADDRESS)) {
        contactDto.setEmail(rs.getString(DbConstants.ACCOUNT_CONTACT_EMAIL_ADDRESS));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_CONTACT_MOBILE_NUMBER)) {
        contactDto.setMobileNumber(rs.getString(DbConstants.ACCOUNT_CONTACT_MOBILE_NUMBER));
      }
      if (columns.containsKey(DbConstants.ACCOUNT_CONTACT_PAGER_NUMBER)) {
        contactDto.setPagerNumber(rs.getString(DbConstants.ACCOUNT_CONTACT_PAGER_NUMBER));
      }
      if (columns.containsKey(DbConstants.ADDRESS_FIRST_NAME)) {
        addressDto.setFirstName(rs.getString(DbConstants.ADDRESS_FIRST_NAME));
      }
      if (columns.containsKey(DbConstants.ADDRESS_LAST_NAME)) {
        addressDto.setLastName(rs.getString(DbConstants.ADDRESS_LAST_NAME));
      }
      if (columns.containsKey(DbConstants.ADDRESS_MIDDLE_NAME)) {
        addressDto.setMiddleName(rs.getString(DbConstants.ADDRESS_MIDDLE_NAME));
      }
      if (columns.containsKey(DbConstants.ADDRESS_ADDRESS_ID)) {
        addressDto.setAddressId(rs.getString(DbConstants.ADDRESS_ADDRESS_ID));
      }
      if (columns.containsKey(DbConstants.ADDRESS_ADDRESS_1)) {
        addressDto.setAddress1(rs.getString(DbConstants.ADDRESS_ADDRESS_1));
      }
      if (columns.containsKey(DbConstants.ADDRESS_ADDRESS_2)) {
        addressDto.setAddress2(rs.getString(DbConstants.ADDRESS_ADDRESS_2));
      }
      if (columns.containsKey(DbConstants.ADDRESS_CITY)) {
        addressDto.setCity(rs.getString(DbConstants.ADDRESS_CITY));
      }
      if (columns.containsKey(DbConstants.ADDRESS_STATE)) {
        addressDto.setState(rs.getString(DbConstants.ADDRESS_STATE));
      }
      if (columns.containsKey(DbConstants.ADDRESS_ZIP_CODE)) {
        addressDto.setZipCode(rs.getString(DbConstants.ADDRESS_ZIP_CODE));
      }
      if (columns.containsKey(DbConstants.ADDRESS_COUNTRY)) {
        addressDto.setCountry(rs.getString(DbConstants.ADDRESS_COUNTRY));
      }

      if (columns.containsKey(DbAliases.ACCOUNT_CONTACT_PHONE_NUMBER)) {
        contactDto.setPhoneNumber(rs.getString(DbAliases.ACCOUNT_CONTACT_PHONE_NUMBER));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_CONTACT_PHONE_EXT)) {
        contactDto.setExtension(rs.getString(DbAliases.ACCOUNT_CONTACT_PHONE_EXT));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_CONTACT_FAX_NUMBER)) {
        contactDto.setFaxNumber(rs.getString(DbAliases.ACCOUNT_CONTACT_FAX_NUMBER));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_CONTACT_EMAIL_ADDRESS)) {
        contactDto.setEmail(rs.getString(DbAliases.ACCOUNT_CONTACT_EMAIL_ADDRESS));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_CONTACT_MOBILE_NUMBER)) {
        contactDto.setMobileNumber(rs.getString(DbAliases.ACCOUNT_CONTACT_MOBILE_NUMBER));
      }
      if (columns.containsKey(DbAliases.ACCOUNT_CONTACT_PAGER_NUMBER)) {
        contactDto.setPagerNumber(rs.getString(DbAliases.ACCOUNT_CONTACT_PAGER_NUMBER));
      }
      if (columns.containsKey(DbAliases.ADDRESS_FIRST_NAME)) {
        addressDto.setFirstName(rs.getString(DbAliases.ADDRESS_FIRST_NAME));
      }
      if (columns.containsKey(DbAliases.ADDRESS_LAST_NAME)) {
        addressDto.setLastName(rs.getString(DbAliases.ADDRESS_LAST_NAME));
      }
      if (columns.containsKey(DbAliases.ADDRESS_MIDDLE_NAME)) {
        addressDto.setMiddleName(rs.getString(DbAliases.ADDRESS_MIDDLE_NAME));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ADDRESS_ID)) {
        addressDto.setAddressId(rs.getString(DbAliases.ADDRESS_ADDRESS_ID));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ADDRESS_1)) {
        addressDto.setAddress1(rs.getString(DbAliases.ADDRESS_ADDRESS_1));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ADDRESS_2)) {
        addressDto.setAddress2(rs.getString(DbAliases.ADDRESS_ADDRESS_2));
      }
      if (columns.containsKey(DbAliases.ADDRESS_CITY)) {
        addressDto.setCity(rs.getString(DbAliases.ADDRESS_CITY));
      }
      if (columns.containsKey(DbAliases.ADDRESS_STATE)) {
        addressDto.setState(rs.getString(DbAliases.ADDRESS_STATE));
      }
      if (columns.containsKey(DbAliases.ADDRESS_ZIP_CODE)) {
        addressDto.setZipCode(rs.getString(DbAliases.ADDRESS_ZIP_CODE));
      }
      if (columns.containsKey(DbAliases.ADDRESS_COUNTRY)) {
        addressDto.setCountry(rs.getString(DbAliases.ADDRESS_COUNTRY));
      }
    
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  public void populateLocationInfo(Map columns, ResultSet rs) {

    if (rs == null) {
      return;
    }
    LocationData location = new LocationData();
    location.populate(columns, rs);
    setLocation(location);
  }

  /**
   * @return
   */
  public String getId() {
    return _id;
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
  public String getType() {
    return _type;
  }

  /**
   * @return Returns the status.
   */
  public String getStatus() {
    return _status;
  }

  /**
   * @return Returns the passwordLifeSpan.
   */
  public String getPasswordLifeSpan() {
    return _passwordLifeSpan;
  }
  
  /**
   * @return Returns the passwordPolicy.
   */
  public String getPasswordPolicy() {
    return _passwordPolicy;
  }
  
  /**
   * @return Returns the viewLinkedCasesOnly.
   */
  public String getViewLinkedCasesOnly() {
    return _viewLinkedCasesOnly;
  }
  
  /**
   * @return Returns the requireUniqueSampleAliases.
   */
  public String getRequireUniqueSampleAliases() {
    return _requireUniqueSampleAliases;
  }

  /**
   * @return Returns the requireSampleAliases.
   */
  public String getRequireSampleAliases() {
    return _requireSampleAliases;
  }

  /**
   * @return Returns the contact.
   */
  public ContactDto getContact() {
    if (_contact == null) {
      _contact = new ContactDto();
    }
    return _contact;
  }

  /**
   * @param string
   */
  public void setId(String string) {
    _id = string;
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
  public void setType(String string) {
    _type = string;
  }
  
  /**
   * @param status The status to set.
   */
  public void setStatus(String status) {
    _status = status;
  }
  
  /**
   * @param passwordLifeSpan The passwordLifeSpan to set.
   */
  public void setPasswordLifeSpan(String passwordLifeSpan) {
    _passwordLifeSpan = passwordLifeSpan;
  }
  
  /**
   * @param passwordPolicy The passwordPolicy to set.
   */
  public void setPasswordPolicy(String passwordPolicy) {
    _passwordPolicy = passwordPolicy;
  }
  
  /**
   * @param viewLinkedCasesOnly The viewLinkedCasesOnly to set.
   */
  public void setViewLinkedCasesOnly(String viewLinkedCasesOnly) {
    _viewLinkedCasesOnly = viewLinkedCasesOnly;
  }
  
  /**
   * @param requireUniqueSampleAliases The requireUniqueSampleAliases to set.
   */
  public void setRequireUniqueSampleAliases(String requireUniqueSampleAliases) {
    _requireUniqueSampleAliases = requireUniqueSampleAliases;
  }
  
  /**
   * @param requireSampleAliases The requireSampleAliases to set.
   */
  public void setRequireSampleAliases(String requireSampleAliases) {
    _requireSampleAliases = requireSampleAliases;
  }
  
  /**
   * @param contact The contact to set.
   */
  public void setContact(ContactDto contact) {
    _contact = contact;
  }
  /**
   * @return Returns the accountData.
   */
  public String getAccountData() {
    return _accountData;
  }
  /**
   * @return Returns the accountDataEncoding.
   */
  public String getAccountDataEncoding() {
    return _accountDataEncoding;
  }
  /**
   * @return Returns the activeDate.
   */
  public Date getActiveDate() {
    return _activeDate;
  }
  /**
   * @return Returns the brandId.
   */
  public String getBrandId() {
    return _brandId;
  }
  /**
   * @return Returns the closeDate.
   */
  public Date getCloseDate() {
    return _closeDate;
  }
  /**
   * @return Returns the contactAddressId.
   */
  public String getContactAddressId() {
    return _contactAddressId;
  }
  /**
   * @return Returns the deactivateDate.
   */
  public Date getDeactivateDate() {
    return _deactivateDate;
  }
  /**
   * @return Returns the deactivateReason.
   */
  public String getDeactivateReason() {
    return _deactivateReason;
  }
  /**
   * @return Returns the defaultBoxLayoutId.
   */
  public String getDefaultBoxLayoutId() {
    return _defaultBoxLayoutId;
  }
  /**
   * @return Returns the openDate.
   */
  public Date getOpenDate() {
    return _openDate;
  }
  /**
   * @return Returns the parentName.
   */
  public String getParentName() {
    return _parentName;
  }
  /**
   * @return Returns the primaryLocation.
   */
  public String getPrimaryLocation() {
    return _primaryLocation;
  }
  /**
   * @return Returns the reactivateDate.
   */
  public Date getReactivateDate() {
    return _reactivateDate;
  }
  /**
   * @return Returns the requestManagerEmail.
   */
  public String getRequestManagerEmail() {
    return _requestManagerEmail;
  }
  /**
   * @param accountData The accountData to set.
   */
  public void setAccountData(String accountData) {
    _accountData = accountData;
  }
  /**
   * @param accountDataEncoding The accountDataEncoding to set.
   */
  public void setAccountDataEncoding(String accountDataEncoding) {
    _accountDataEncoding = accountDataEncoding;
  }
  /**
   * @param activeDate The activeDate to set.
   */
  public void setActiveDate(Date activeDate) {
    _activeDate = activeDate;
  }
  /**
   * @param brandId The brandId to set.
   */
  public void setBrandId(String brandId) {
    _brandId = brandId;
  }
  /**
   * @param closeDate The closeDate to set.
   */
  public void setCloseDate(Date closeDate) {
    _closeDate = closeDate;
  }
  /**
   * @param contactAddressId The contactAddressId to set.
   */
  public void setContactAddressId(String contactAddressId) {
    _contactAddressId = contactAddressId;
  }
  /**
   * @param deactivateDate The deactivateDate to set.
   */
  public void setDeactivateDate(Date deactivateDate) {
    _deactivateDate = deactivateDate;
  }
  /**
   * @param deactivateReason The deactivateReason to set.
   */
  public void setDeactivateReason(String deactivateReason) {
    _deactivateReason = deactivateReason;
  }
  /**
   * @param defaultBoxLayoutId The defaultBoxLayoutId to set.
   */
  public void setDefaultBoxLayoutId(String defaultBoxLayoutId) {
    _defaultBoxLayoutId = defaultBoxLayoutId;
  }
  /**
   * @param openDate The openDate to set.
   */
  public void setOpenDate(Date openDate) {
    _openDate = openDate;
  }
  /**
   * @param parentName The parentName to set.
   */
  public void setParentName(String parentName) {
    _parentName = parentName;
  }
  /**
   * @param primaryLocation The primaryLocation to set.
   */
  public void setPrimaryLocation(String primaryLocation) {
    _primaryLocation = primaryLocation;
  }
  /**
   * @param reactivateDate The reactivateDate to set.
   */
  public void setReactivateDate(Date reactivateDate) {
    _reactivateDate = reactivateDate;
  }
  /**
   * @param requestManagerEmail The requestManagerEmail to set.
   */
  public void setRequestManagerEmail(String requestManagerEmail) {
    _requestManagerEmail = requestManagerEmail;
  }
  
  /**
   * @return Returns the location.
   */
  public LocationData getLocation() {
    if (_location == null) {
      _location = new LocationData();
    }
    return _location;
  }
  /**
   * @param location The location to set.
   */
  public void setLocation(LocationData location) {
    _location = location;
  }
  
  public String getDonorRegistrationFormId() {
    return _donorRegistrationFormId;
  }
  
  public void setDonorRegistrationFormId(String donorRegistrationFormId) {
    _donorRegistrationFormId = donorRegistrationFormId;
  }
  
  public String getDonorRegistrationFormName() {
    return _donorRegistrationFormName;
  }
  
  public void setDonorRegistrationFormName(String donorRegistrationFormName) {
    _donorRegistrationFormName = donorRegistrationFormName;
  }
}
