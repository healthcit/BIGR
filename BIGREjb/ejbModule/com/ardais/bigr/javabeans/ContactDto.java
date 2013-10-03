package com.ardais.bigr.javabeans;

import java.io.Serializable;

/**
 * Represents the raw data of a contact.
 */
public class ContactDto implements Serializable {

  private String _phoneNumber;
  private String _extension;
  private String _faxNumber;
  private String _mobileNumber;
  private String _pagerNumber;
  private String _email;
  private AddressDto _address;

  /**
	 * Creates a new <code>ContactDto</code>.
	 */
	public ContactDto() {
	}
  
  /**
   * @return Returns the email.
   */
  public String getEmail() {
    return _email;
  }
  
  /**
   * @return Returns the extension.
   */
  public String getExtension() {
    return _extension;
  }
  
  /**
   * @return Returns the faxNumber.
   */
  public String getFaxNumber() {
    return _faxNumber;
  }
  
  /**
   * @return Returns the mobileNumber.
   */
  public String getMobileNumber() {
    return _mobileNumber;
  }
  
  /**
   * @return Returns the pagerNumber.
   */
  public String getPagerNumber() {
    return _pagerNumber;
  }
  
  /**
   * @return Returns the phoneNumber.
   */
  public String getPhoneNumber() {
    return _phoneNumber;
  }

  /**
   * @return Returns the address.
   */
  public AddressDto getAddress() {
    if (_address == null) {
      _address = new AddressDto();
    }
    return _address;
  }
  
  /**
   * @param email The email to set.
   */
  public void setEmail(String email) {
    _email = email;
  }
  
  /**
   * @param extension The extension to set.
   */
  public void setExtension(String extension) {
    _extension = extension;
  }
  
  /**
   * @param faxNumber The faxNumber to set.
   */
  public void setFaxNumber(String faxNumber) {
    _faxNumber = faxNumber;
  }
  
  /**
   * @param mobileNumber The mobileNumber to set.
   */
  public void setMobileNumber(String mobileNumber) {
    _mobileNumber = mobileNumber;
  }
  
  /**
   * @param pagerNumber The pagerNumber to set.
   */
  public void setPagerNumber(String pagerNumber) {
    _pagerNumber = pagerNumber;
  }
  
  /**
   * @param phoneNumber The phoneNumber to set.
   */
  public void setPhoneNumber(String phoneNumber) {
    _phoneNumber = phoneNumber;
  }
  
  /**
   * @param address The address to set.
   */
  public void setAddress(AddressDto address) {
    _address = address;
  }
  
}
