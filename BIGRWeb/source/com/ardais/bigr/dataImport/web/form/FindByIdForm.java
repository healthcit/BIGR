package com.ardais.bigr.dataImport.web.form;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for finding entities.
 */
public class FindByIdForm extends BigrActionForm {
  private String _ardaisId;
  private String _customerId;
  private String _findDonors;
  private String _findCases;
  private String _findSamples;
  private boolean _donorsSearched;
  private List _donors;
  private boolean _casesSearched;
  private List _cases;
  private boolean _samplesSearched;
  private List _samples;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _ardaisId = null;
    _customerId = null;
    _findDonors = "Y";  //MR7923 - find donors by default
    _findCases = "Y";  //MR7923 - find cases by default
    _findSamples = "Y";  //MR7923 - find samples by default
    _donors = null;
    _donorsSearched = false;
    _cases = null;
    _casesSearched = false;
    _samples = null;
    _samplesSearched = false;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();
    return errors;
  }

  /**
   * @return
   */
  public String getArdaisId() {
    return _ardaisId;
  }

  /**
   * @return
   */
  public List getCases() {
    return _cases;
  }

  /**
   * @return
   */
  public String getCustomerId() {
    return _customerId;
  }

  /**
   * @return
   */
  public List getDonors() {
    return _donors;
  }

  /**
   * @return
   */
  public String getFindCases() {
    return _findCases;
  }

  /**
   * @return
   */
  public String getFindDonors() {
    return _findDonors;
  }

  /**
   * @return
   */
  public String getFindSamples() {
    return _findSamples;
  }

  /**
   * @return
   */
  public List getSamples() {
    return _samples;
  }

  /**
   * @param string
   */
  public void setArdaisId(String string) {
    _ardaisId = string;
  }

  /**
   * @param list
   */
  public void setCases(List list) {
    _cases = list;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = string;
  }

  /**
   * @param list
   */
  public void setDonors(List list) {
    _donors = list;
  }

  /**
   * @param string
   */
  public void setFindCases(String string) {
    _findCases = string;
  }

  /**
   * @param string
   */
  public void setFindDonors(String string) {
    _findDonors = string;
  }

  /**
   * @param string
   */
  public void setFindSamples(String string) {
    _findSamples = string;
  }

  /**
   * @param list
   */
  public void setSamples(List list) {
    _samples = list;
  }

  /**
   * @return
   */
  public boolean isCasesSearched() {
    return _casesSearched;
  }

  /**
   * @return
   */
  public boolean isDonorsSearched() {
    return _donorsSearched;
  }

  /**
   * @return
   */
  public boolean isSamplesSearched() {
    return _samplesSearched;
  }

  /**
   * @param b
   */
  public void setCasesSearched(boolean b) {
    _casesSearched = b;
  }

  /**
   * @param b
   */
  public void setDonorsSearched(boolean b) {
    _donorsSearched = b;
  }

  /**
   * @param b
   */
  public void setSamplesSearched(boolean b) {
    _samplesSearched = b;
  }

}
