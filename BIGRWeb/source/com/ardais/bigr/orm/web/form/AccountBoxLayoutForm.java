package com.ardais.bigr.orm.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for a account box layout.
 */
public class AccountBoxLayoutForm extends BigrActionForm {

  private String _accountBoxLayoutId;
  private String _boxLayoutName;
  private String _accountId;
  private String _boxLayoutId;
  private boolean _currentDefaultAccountBoxLayout;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {

    _accountBoxLayoutId = null;
    _boxLayoutName = null;
    _accountId = null;
    _boxLayoutId = null;
    _currentDefaultAccountBoxLayout = false;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Nothing to validate here.
    return null;
  }

  /**
   * @return
   */
  public String getAccountBoxLayoutId() {
    return _accountBoxLayoutId;
  }

  /**
   * @return
   */
  public String getAccountId() {
    return _accountId;
  }

  /**
   * @return
   */
  public String getBoxLayoutId() {
    return _boxLayoutId;
  }

  /**
   * @return
   */
  public String getBoxLayoutName() {
    return _boxLayoutName;
  }

  /**
   * @return
   */
  public boolean isCurrentDefaultAccountBoxLayout() {
    return _currentDefaultAccountBoxLayout;
  }

  /**
   * @param string
   */
  public void setAccountBoxLayoutId(String string) {
    _accountBoxLayoutId = string;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param string
   */
  public void setBoxLayoutId(String string) {
    _boxLayoutId = string;
  }

  /**
   * @param string
   */
  public void setBoxLayoutName(String string) {
    _boxLayoutName = string;
  }

  /**
   * @param b
   */
  public void setCurrentDefaultAccountBoxLayout(boolean b) {
    _currentDefaultAccountBoxLayout = b;
  }
}
