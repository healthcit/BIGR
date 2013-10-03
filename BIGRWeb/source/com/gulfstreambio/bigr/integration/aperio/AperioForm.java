package com.gulfstreambio.bigr.integration.aperio;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class AperioForm extends BigrActionForm {

  private String _sampleId;
  private String _imageId;
  
  private String _username;
  private String _password;
  
  private String _dataServerBaseUrl;
  
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _sampleId = null;
    _imageId = null;
    _username = null;
    _password = null;
    _dataServerBaseUrl = null;
  }

  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  public String getBigrSampleId() {
    return _sampleId;
  }
  public void setBigrSampleId(String sampleId) {
    _sampleId = sampleId;
  }

  public String getSpectrumImageId() {
    return _imageId;
  }
  public void setSpectrumImageId(String imageId) {
    _imageId = imageId;
  }

  public String getSpectrumPassword() {
    return (_password == null) ? "" : _password;
  }
  public void setSpectrumPassword(String password) {
    _password = password;
  }

  public String getSpectrumUsername() {
    return (_username == null) ? "" : _username;
  }
  public void setSpectrumUsername(String username) {
    _username = username;
  }

  public String getDataServerBaseUrl() {
    return _dataServerBaseUrl;
  }
  public void setDataServerBaseUrl(String dataServerBaseUrl) {
    _dataServerBaseUrl = dataServerBaseUrl;
  }
}
