package com.ardais.bigr.javabeans;

import java.io.Serializable;

/**
 * Represents the raw data of a clinical finding.
 */
public class ClinicalFindingData implements Serializable{
  private String _consentId;
  private String _psa;
  private String _dre;
  private String _clinicalFindingNotes;

  /**
   * @return
   */
  public String getClinicalFindingNotes() {
    return _clinicalFindingNotes;
  }

  /**
   * @return
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * @return
   */
  public String getDre() {
    return _dre;
  }

  /**
   * @return
   */
  public String getPsa() {
    return _psa;
  }

  /**
   * @param string
   */
  public void setClinicalFindingNotes(String string) {
    _clinicalFindingNotes = string;
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = string;
  }

  /**
   * @param string
   */
  public void setDre(String string) {
    _dre = string;
  }

  /**
   * @param string
   */
  public void setPsa(String string) {
    _psa = string;
  }

}
