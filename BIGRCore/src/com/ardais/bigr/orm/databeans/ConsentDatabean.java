package com.ardais.bigr.orm.databeans;

import java.sql.Date;

public class ConsentDatabean implements java.io.Serializable {
  private int _consentVersionID;
  private String _consentVersionName;
  private String _expirationDate;
  private boolean _active;

  public ConsentDatabean() {
    super();
  }

  public int getConsentVersionID() {
    return _consentVersionID;
  }

  public java.lang.String getConsentVersionName() {
    return _consentVersionName;
  }

  public boolean isActive() {
    return _active;
  }
  
  public void setConsentVersionID(int newConsentVersionID) {
    _consentVersionID = newConsentVersionID;
  }

  public void setConsentVersionName(String newConsentVersionName) {
    _consentVersionName = newConsentVersionName;
  }

  public void setActive(boolean newActive) {
    _active = newActive;
  }
  
  /**
   * @return
   */
  public String getExpirationDate() {
    return _expirationDate;
  }

  /**
   * @param string
   */
  public void setExpirationDate(String string) {
    _expirationDate = string;
  }

}
