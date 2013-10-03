package com.ardais.bigr.iltds.web.form;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for creating a new case.
 */
public class CreateCaseForm extends BigrActionForm {
  private boolean _resetForm;
  private boolean _validateOnly;
  private boolean _confirmCanceled;
  private boolean _linked;
  private String _ardaisId;
  private String _ardaisId_2;
  private String _consentId;
  private String _consentId_2;
  private String _policyId;
  private String _policyName;
  private String _consentVersionId; // used only for linked cases
  private String _irbProtocolAndVersionName; // used only for linked cases
  private String _year; // used only for linked cases
  private String _month; // used only for linked cases; range from 1 to 12
  private String _hours; // used only for linked cases
  private String _minutes; // used only for linked cases
  private String _ampm; // used only for linked cases
  private Date _consentDate; // used only for linked cases, computed from year/month/etc.
  private String _comments;
  private String _bloodSampleYN; // used only for linked cases
  private String _additionalNeedleStickYN; // used only for linked cases
  private String _futureContactYN; // used only for linked cases

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _resetForm = false;
    _validateOnly = false;
    _confirmCanceled = false;
    _linked = false;
    _ardaisId = null;
    _ardaisId_2 = null;
    _consentId = null;
    _consentId_2 = null;
    _policyId = null;
    _policyName = null;
    _consentVersionId = null;
    _irbProtocolAndVersionName = null;
    _comments = null;
    _bloodSampleYN = null;
    _additionalNeedleStickYN = null;
    _futureContactYN = null;

    {
      Calendar currentDate = java.util.Calendar.getInstance();
      _year = String.valueOf(currentDate.get(java.util.Calendar.YEAR));
      // Default to current month, add one to convert from rango 0-11 to 1-12.
      _month = String.valueOf(currentDate.get(java.util.Calendar.MONTH) + 1);
      _hours = null;
      _minutes = null;
      _ampm = null;
      _consentDate = null;
    }
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Here we just make sure we can turn the year/month/hours/minutes/ampm into a timestamp,
    // and make sure the double-entered case and donor ids match.
    // All other validation is done in the BTX performer.

    // The isResetForm() "field" is an instruction that the form fields are to be reset to their
    // default values before proceeding.  The only fields we don't reset are:
    //   - The "linked" fields, since it is sometimes used when isResetForm is true.
    //     For example, when we have just completed creating a case, we return the user to
    //     a blank form to create another case, but we want to send them back to the same kind
    //     of case-creation screen they just used (linked vs. unlinked).  In this situation,
    //     isResetForm is true but we also need to preserve the value in "linked" so that we
    //     indicate the right kind of case to create next.
    //
    // When we reset the form, we don't do any further validation.
    //
    if (isResetForm()) {
      boolean linked = isLinked();
      doReset(mapping, request);
      setLinked(linked);
      return null;
    }

    // If the action is createCaseStart, we don't want to validate.
    // It will either be a blank form, or a form that the user was sent back to because
    // we already know that the input is invalid.
    //
    if ("/iltds/consent/createCaseStart".equals(mapping.getPath())) {
      return null;
    }

    ActionErrors errors = new ActionErrors();

    if (!ApiFunctions.safeString(getArdaisId()).equals(getArdaisId_2())) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.doubleEntryMismatchArdaisId"));
    }

    if (!ApiFunctions.safeString(getConsentId()).equals(getConsentId_2())) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.doubleEntryMismatchConsentId"));
    }

    if (isLinked()) {
      validateConsentDateTime(errors);
    }

    return errors;
  }

  /**
   * Validate the consent date that is specified in the year/month/hours/minutes/ampm
   * fields.  If we don't detect any validation errors on the year/month/hours/minutes/ampm
   * fields, we'll set the consentDate field to the indicated date.  Otherwise the consentDate
   * field will be set to null and we'll add one or more ActionError objects to the errors
   * collection that was passed in as a parameter.
   * 
   * This procedure only validates the consent date to the extent that is required to convert
   * it into a Date object.  Any more specific validation rules are checked in the BTX performer
   * class for the business transaction.
   * 
   * @param errors the errors collection to add any validation errors to.  This must be non-null.
   * @return true if the validation succeeds, otherwise false.
   */
  private boolean validateConsentDateTime(ActionErrors errors) {
    boolean result = true;

    setConsentDate(null);

    String myYear = getYear();
    String myMonth = getMonth();
    String myHours = getHours();
    String myMinutes = getMinutes();
    String myAmpm = getAmpm();

    if (ApiFunctions.isEmpty(myYear)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateMissingYear"));
      result = false;
    }

    if (ApiFunctions.isEmpty(myMonth)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateMissingMonth"));
      result = false;
    }

    if (ApiFunctions.isEmpty(myHours)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateMissingHours"));
      result = false;
    }

    if (ApiFunctions.isEmpty(myMinutes)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateMissingMinutes"));
      result = false;
    }

    if (ApiFunctions.isEmpty(myAmpm)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidAmpm"));
      result = false;
    }

    if (!result) {
      return result;
    }

    if (!ApiFunctions.isPositiveInteger(myYear)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidYear"));
      result = false;
    }

    if (!ApiFunctions.isPositiveInteger(myMonth)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidMonth"));
      result = false;
    }

    if (!ApiFunctions.isPositiveInteger(myHours)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidHours"));
      result = false;
    }

    if (!ApiFunctions.isPositiveInteger(myMinutes)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidMinutes"));
      result = false;
    }

    if (!("am".equals(myAmpm) || "pm".equals(myAmpm))) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidAmpm"));
      result = false;
    }

    if (!result) {
      return result;
    }

    int myIntYear = Integer.parseInt(myYear);
    int myIntMonth = Integer.parseInt(myMonth) - 1;
    int myIntHours = Integer.parseInt(myHours);
    int myIntMinutes = Integer.parseInt(myMinutes);

    // Change time to 24 hour format.
    if (myAmpm.equals("pm") && myIntHours != 12) {
      myIntHours += 12;
    }
    else if (myAmpm.equals("am") && myIntHours == 12) {
      myIntHours = 0;
    }

    if (myIntYear < 0) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidYear"));
      result = false;
    }

    if (!(myIntMonth >= Calendar.JANUARY && myIntMonth <= Calendar.DECEMBER)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidMonth"));
      result = false;
    }

    if (!(myIntHours >= 0 && myIntHours <= 23)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidHours"));
      result = false;
    }

    if (!(myIntMinutes >= 0 && myIntMinutes <= 59)) {
      errors.add(
        ActionErrors.GLOBAL_ERROR,
        new ActionError("iltds.error.createCase.consentDateInvalidMinutes"));
      result = false;
    }

    if (result) {
      // Create a current Timestamp and a Timestamp from above values
      Calendar myCalendar = Calendar.getInstance();
      myCalendar.setLenient(false);
      myCalendar.set(myIntYear, myIntMonth, 1, myIntHours, myIntMinutes, 0);

      setConsentDate(myCalendar.getTime());
    }

    return result;
  }

  /**
   * @return
   */
  public String getAmpm() {
    return _ampm;
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
  public String getArdaisId_2() {
    return _ardaisId_2;
  }

  /**
   * @return
   */
  public boolean isConfirmCanceled() {
    return _confirmCanceled;
  }

  /**
   * Return the consent date.  This is only used for linked cases and isn't set
   * directly, it is computed in the validate() method from the values in the
   * year/month/hours/minutes/ampm fields.
   * 
   * @return the consent date
   */
  public Date getConsentDate() {
    return _consentDate;
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
  public String getConsentId_2() {
    return _consentId_2;
  }

  /**
   * @return
   */
  public String getConsentVersionId() {
    return _consentVersionId;
  }

  /**
   * @return
   */
  public String getHours() {
    return _hours;
  }

  /**
   * @return
   */
  public String getIrbProtocolAndVersionName() {
    return _irbProtocolAndVersionName;
  }

  /**
   * @return
   */
  public boolean isLinked() {
    return _linked;
  }

  /**
   * @return
   */
  public String getMinutes() {
    return _minutes;
  }

  /**
   * @return the consent month as a number from 1 to 12.
   */
  public String getMonth() {
    return _month;
  }

  /**
   * @return
   */
  public String getPolicyId() {
    return _policyId;
  }

  /**
   * @return
   */
  public String getPolicyName() {
    return _policyName;
  }

  /**
   * @return
   */
  public boolean isResetForm() {
    return _resetForm;
  }

  /**
   * @return
   */
  public boolean isValidateOnly() {
    return _validateOnly;
  }

  /**
   * @return
   */
  public String getYear() {
    return _year;
  }

  /**
   * @return
   */
  public String getAdditionalNeedleStickYN() {
    return _additionalNeedleStickYN;
  }

  /**
   * @return
   */
  public String getBloodSampleYN() {
    return _bloodSampleYN;
  }

  /**
   * @return
   */
  public String getComments() {
    return _comments;
  }

  /**
   * @return
   */
  public String getFutureContactYN() {
    return _futureContactYN;
  }


  /**
   * @param string
   */
  public void setAmpm(String string) {
    _ampm = string;
  }

  /**
   * @param string
   */
  public void setArdaisId(String string) {
    _ardaisId = string;
  }

  /**
   * @param string
   */
  public void setArdaisId_2(String string) {
    _ardaisId_2 = string;
  }

  /**
   * @param b
   */
  public void setConfirmCanceled(boolean b) {
    _confirmCanceled = b;
  }

  /**
   * Set the consent date.  This is only used for linked cases and isn't set
   * directly, it is computed in the validate() method from the values in the
   * year/month/hours/minutes/ampm fields.
   * 
   * @param date the consent date.
   */
  private void setConsentDate(Date date) {
    _consentDate = date;
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
  public void setConsentId_2(String string) {
    _consentId_2 = string;
  }

  /**
   * @param string
   */
  public void setConsentVersionId(String string) {
    _consentVersionId = string;
  }

  /**
   * @param integer
   */
  public void setHours(String integer) {
    _hours = integer;
  }

  /**
   * @param string
   */
  public void setIrbProtocolAndVersionName(String string) {
    _irbProtocolAndVersionName = string;
  }

  /**
   * @param b
   */
  public void setLinked(boolean b) {
    _linked = b;
  }

  /**
   * @param integer
   */
  public void setMinutes(String integer) {
    _minutes = integer;
  }

  /**
   * @param newValue the consent month as a number from 1 to 12.
   */
  public void setMonth(String newValue) {
    _month = newValue;
  }

  /**
   * @param string
   */
  public void setPolicyId(String string) {
    _policyId = string;
  }

  /**
   * @param string
   */
  public void setPolicyName(String string) {
    _policyName = string;
  }

  /**
   * @param b
   */
  public void setResetForm(boolean b) {
    _resetForm = b;
  }

  /**
   * @param b
   */
  public void setValidateOnly(boolean b) {
    _validateOnly = b;
  }

  /**
   * @param integer
   */
  public void setYear(String integer) {
    _year = integer;
  }
  /**
   * @param string
   */
  public void setAdditionalNeedleStickYN(String string) {
    _additionalNeedleStickYN = string;
  }

  /**
   * @param string
   */
  public void setBloodSampleYN(String string) {
    _bloodSampleYN = string;
  }

  /**
   * @param string
   */
  public void setComments(String string) {
    _comments = string;
  }

  /**
   * @param string
   */
  public void setFutureContactYN(String string) {
    _futureContactYN = string;
  }

}
