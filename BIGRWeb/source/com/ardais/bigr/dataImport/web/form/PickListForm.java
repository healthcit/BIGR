package com.ardais.bigr.dataImport.web.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for generating a pick list for a case.
 */
public class PickListForm extends BigrActionForm {
  private String _consentId;
  private ConsentData _consentData;
  private List _samplesInRepository;
  private List _samplesInTransit;
  private List _samplesCheckedOut;
  private List _locations;
  private String _reportGeneratedByName;
  private String _txType;
  private String _caseAction;
  private String _revokeStaffName;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _consentId = null;
    _consentData = null;
    _samplesInRepository = null;
    _samplesInTransit = null;
    _samplesCheckedOut = null;
    _locations = null;
    _reportGeneratedByName = null;
    _txType = null;
    _caseAction = null;
    _revokeStaffName = null;
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
  public ConsentData getConsentData() {
    return _consentData;
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
  public List getLocations() {
    return _locations;
  }

  /**
   * @return
   */
  public String getReportGeneratedByName() {
    return _reportGeneratedByName;
  }

  /**
   * @return
   */
  public List getSamplesCheckedOut() {
    return _samplesCheckedOut;
  }

  /**
   * @return
   */
  public List getSamplesInRepository() {
    return _samplesInRepository;
  }

  /**
   * @return
   */
  public List getSamplesInTransit() {
    return _samplesInTransit;
  }

  /**
   * @param data
   */
  public void setConsentData(ConsentData data) {
    _consentData = data;
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = string;
  }

  /**
   * @param list
   */
  public void setLocations(List list) {
    _locations = list;
  }

  /**
   * @param string
   */
  public void setReportGeneratedByName(String string) {
    _reportGeneratedByName = string;
  }

  /**
   * @param list
   */
  public void setSamplesCheckedOut(List list) {
    _samplesCheckedOut = list;
  }

  /**
   * @param list
   */
  public void setSamplesInRepository(List list) {
    _samplesInRepository = list;
  }

  /**
   * @param list
   */
  public void setSamplesInTransit(List list) {
    _samplesInTransit = list;
  }

  /**
   * @return
   */
  public String getTxType() {
    return _txType;
  }

  /**
   * @param string
   */
  public void setTxType(String string) {
    _txType = string;
  }

  /**
   * @return
   */
  public String getCaseAction() {
    return _caseAction;
  }

  /**
   * @param string
   */
  public void setCaseAction(String string) {
    _caseAction = string;
  }

  /**
   * @return
   */
  public String getRevokeStaffName() {
    return _revokeStaffName;
  }

  /**
   * @param string
   */
  public void setRevokeStaffName(String string) {
    _revokeStaffName = string;
  }

}
