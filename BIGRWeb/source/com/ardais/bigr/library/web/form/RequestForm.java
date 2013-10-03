package com.ardais.bigr.library.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.library.web.helper.LibraryValuesHelper;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RequestForm extends BigrActionForm {

  private String[] _samples;
  private String[] _delivType;
  private String _name;
  private String _email;
  private String _phone;
  private String _details;
  private String _txType;
  private boolean _useSingleCategory = false;

  public RequestForm() {
    reset();
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    reset();
  }

  private void reset() {
    _samples = null;
    _delivType = null;
    _name = null;
    _email = null;
    _phone = null;
    _details = null;
    _txType = null;
    _useSingleCategory = false;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();

    if (ApiFunctions.isEmpty(_email)) {
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.noValue", "E-Mail"));
    }
    if (ApiFunctions.isEmpty(_name)) {
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.noValue", "Name"));
    }
    if (ApiFunctions.isEmpty(_phone)) {
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.noValue", "Phone"));
    }
    if (ApiFunctions.isEmpty(_details)) {
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.noValue", "Details"));
    }

    return errors;
  }

  /**
   * Returns the sampless.
   * @return String[]
   */
  public String[] getSamples() {
    return _samples;
  }

  /**
   * Sets the sampless.
   * @param sampless The sampless to set
   */
  public void setSamples(String[] samples) {
    _samples = samples;
  }

  /**
   * Returns the delivType.
   * @return String[]
   */
  public String[] getDelivType() {
    return _delivType;
  }

  /**
   * Returns the details.
   * @return String
   */
  public String getDetails() {
    return _details;
  }

  /**
   * Returns the email.
   * @return String
   */
  public String getEmail() {
    return _email;
  }

  /**
   * Returns the name.
   * @return String
   */
  public String getName() {
    return _name;
  }

  /**
   * Returns the phone.
   * @return String
   */
  public String getPhone() {
    return _phone;
  }

  /**
   * Sets the delivType.
   * @param delivType The delivType to set
   */
  public void setDelivType(String[] delivType) {
    _delivType = delivType;
  }

  /**
   * Sets the details.
   * @param details The details to set
   */
  public void setDetails(String details) {
    _details = details;
  }

  /**
   * Sets the email.
   * @param email The email to set
   */
  public void setEmail(String email) {
    _email = email;
  }

  /**
   * Sets the name.
   * @param name The name to set
   */
  public void setName(String name) {
    _name = name;
  }

  /**
   * Sets the phone.
   * @param phone The phone to set
   */
  public void setPhone(String phone) {
    _phone = phone;
  }

  /**
   * Returns the txType.
   * @return String
   */
  public String getTxType() {
    return _txType;
  }

  /**
   * Sets the txType.
   * @param txType The txType to set
   */
  public void setTxType(String txType) {
    _txType = txType;
  }

  /**
   * An order request may be invoked from either a single-category or multi-category hold list
   * page.  When we get here from a single-category page, then all of the items on the hold
   * list are to be placed in the order request.  However, when we get here from a
   * multi-category hold list, then only the items in the "Ardais Items" category are to be
   * placed on the order request.  By default, we assume a multi-category hold list.
   *
   * @return
   */
  public boolean isUseSingleCategory() {
    return _useSingleCategory;
  }

  /**
   * @see #isUseSingleCategory()
   */
  public void setUseSingleCategory(boolean b) {
    _useSingleCategory = b;
  }

}
