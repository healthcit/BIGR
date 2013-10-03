package com.ardais.bigr.iltds.btx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of creating a case.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setConsentId(String) Case id}: The case id for the new case.</li>
 * <li>{@link #setArdaisId(String) Ardais id}: The Ardais id (donor id) for the new case.</li>
 * <li>{@link #setPolicyId(String) Policy id}: The id of the policy to be associated with
 *     the case.  This is required for unlinked cases and must be null for linked cases.
 *     This is an input parameter for unlinked cases but an output parameter
 *     for linked cases.</li>
 * <li>{@link #setConsentVersionId(String) Consent version id}: The id of the consent version
 *     to be associated with the case.  This is required for linked cases and must be null for
 *     unlinked cases.</li>
 * <li>{@link #setConsentDate(Date) Consent date}: The date the consent was taken.
 *     This is required for linked cases and must be null for unlinked cases.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setLinked(boolean) Linked}: Indicates whether the case is linked or not.</li>
 * <li>{@link #setValidateOnly(boolean) Validate only}: If true, the case won't be created,
 *     but the input data will be validated and output fields that can be set will be set.
 *     The transaction will be marked as incomplete and the ActionForward will be set to
 *     "requestConfirmation".  This field defaults to false.</li>
 * <li>{@link #setConfirmCanceled(boolean) Confirm canceled}: If true and the validateOnly
 *     field is false, the case won't be created, but the input data will be validated and
 *     output fields that can be set will be set.  The transaction will be marked as incomplete
 *     and the ActionForward will be set to a retry action forward.  This field defaults to
 *     false.</li>
 * <li>{@link #setComments(String) comments}: Any comments the user has entered.</li>
 * <li>{@link #setBloodSampleYN(String) bloodSampleYN}: A Y/N value for the additional question on the 
 *     consent about blood collection.</li>
 * <li>{@link #setAdditionalNeedleStickYN(String) additionalNeedleStickYN}: A Y/N value for the 
 *     additional question on the consent about an additional needle stick.</li>
 * <li>{@link #setFutureContactYN(String) futureContactYN}: A Y/N value for the 
 *     additional question on the consent about future contact.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setPolicyId(String) Policy id}: The id of the policy that was associated with
 *     the case.  This is an input parameter for unlinked cases but an output parameter
 *     for linked cases.</li>
 * <li>{@link #setPolicyName(String) Policy name}: The name of the policy that was associated with
 *     the case.</li>
 * <li>{@link #setIrbProtocolAndVersionName(String) IRB and consent version name}: The combined
 *     name of the consent's IRB and consent version.  This will be defined only for linked
 *     cases, it will be null for unlinked cases.</li>
 * </ul>
 */
public class BtxDetailsCreateCase extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = -2629975377630677831L;

  // **** DON'T CHANGE THIS! ****  This is the format that we use to store the consent
  // date in the BTX history record, and it is also the format that we use to display
  // the date to the user in formatted history records.  If you change it, it could create
  // problems if we ever need to parse dates from history records back into Date objects,
  // since we'd have dates stored in multiple formats.  A better approach if you're just trying
  // to display the date to the user in a different format would be to change
  // populateFromHistoryRecord to parse the stored date and set the result into the consentDate
  // property, then in doGetDetailsAsHTML format the consentDate field into the format
  // that you want the user to see.
  //
  private static final String CONSENT_DATE_FORMAT = "M/yyyy h:mm a";

  private boolean _validateOnly = false;
  private boolean _confirmCanceled = false;
  private boolean _linked = false;
  private String _ardaisId = null;
  private String _consentId = null;
  private String _policyId = null; // input if unlinked, out if linked (set from consentVersionId)
  private String _policyName = null;
  private String _consentVersionId = null; // used only for linked cases
  private String _irbProtocolAndVersionName = null; // used only for linked cases
  private Date _consentDate = null; // used only for linked cases
  private String _consentDateString = null; // used only for linked cases
  private String _year; // used only for linked cases
  private String _month; // used only for linked cases; range from 1 to 12
  private String _hours; // used only for linked cases
  private String _minutes; // used only for linked cases
  private String _ampm; // used only for linked cases  
  private String _comments = null;
  private String _bloodSampleYN = null; // used only for linked cases
  private String _additionalNeedleStickYN = null; // used only for linked cases
  private String _futureContactYN = null; // used only for linked cases
  
  public BtxDetailsCreateCase() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_CASE;
  }

  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();

    set.add(getConsentId());
    set.add(getArdaisId());

    return set;
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set to
   *    the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    history.setAttrib1(getConsentId());
    history.setAttrib2(getArdaisId());
    history.setAttrib3(isLinked() ? "Y" : "N");
    history.setAttrib4(getPolicyId());
    history.setAttrib5(getPolicyName());
    history.setAttrib6(getConsentVersionId());
    history.setAttrib7(getIrbProtocolAndVersionName());
    history.setAttrib8(getConsentDateString());
    history.setAttrib9(getComments());
    //note - BtxDetailsCreateImportedCase extends this class, so if this method must
    //be altered in any way make sure that is done in a way that doesn't
    //break BtxDetailsCreateImportedCase (e.g. new attributes should start at an 
    //Attrib# higher than the highest one used by BtxDetailsCreateImportedCase, etc)
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);

    // Currently there's no need to spend time parsing the consent date string stored
    // in a history record to convert it to a real date, so we just set it to null.
    // See the comments below for why we do this here rather than at the end of the procedure.
    //
    setConsentDate(null);

    setConsentId(history.getAttrib1());
    setArdaisId(history.getAttrib2());
    setLinked("Y".equals(history.getAttrib3()));
    setPolicyId(history.getAttrib4());
    setPolicyName(history.getAttrib5());
    setConsentVersionId(history.getAttrib6());
    setIrbProtocolAndVersionName(history.getAttrib7());
    setConsentDateString(history.getAttrib8());
    setComments(history.getAttrib9());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    setBloodSampleYN(null);
    setAdditionalNeedleStickYN(null);
    setFutureContactYN(null);
    //
    // Normally we set these fields at the end, but we need to set the
    // consentDate to null before we set consentDateString, because setting the
    // consentDate to null sets consentDateString to null as a side effect.
    //
    setConfirmCanceled(false);
    setValidateOnly(false);
    
    //note - BtxDetailsCreateImportedCase extends this class, so if this method must
    //be altered in any way make sure that is done in a way that doesn't
    //break BtxDetailsCreateImportedCase (e.g. new attributes should start at an 
    //Attrib# higher than the highest one used by BtxDetailsCreateImportedCase, etc)
  }

  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getConsentId
    //   getArdaisId
    //   isLinked
    //   getPolicyId
    //   getPolicyName
    //   getConsentVersionId
    //   getIrbProtocolAndVersionName
    //   getConsentDateString
    //   getComments

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(512);

    // The result has this form:
    //    Created <linked/unlinked> case <consentId> (<ardaisId>) with policy '<policyName>'.
    //    [Consent was obtained on <consentDateString> under IRB version
    //    <irbProtocolAndVersionName>.]
    //    [Comments: <comments>]

    boolean linked = isLinked();

    sb.append("Created ");
    sb.append(linked ? "linked" : "unlinked");
    sb.append(" case ");
    sb.append(IcpUtils.prepareLink(getConsentId(), securityInfo));
    sb.append(" (");
    sb.append(IcpUtils.prepareLink(getArdaisId(), securityInfo));
    sb.append(") with policy '");

    String policyId = getPolicyId();
    if (ApiFunctions.isEmpty(policyId)) {
      sb.append(Escaper.htmlEscape(getPolicyName()));
    }
    else {
      String prefixedPolicyId = FormLogic.makePrefixedPolicyId(policyId);
      sb.append(IcpUtils.prepareLink(prefixedPolicyId, getPolicyName(), securityInfo));
    }
    sb.append("'.");

    if (linked) {
      sb.append("  Consent was obtained on ");
      Escaper.htmlEscape(getConsentDateString(), sb);
      sb.append(" under IRB version ");
      Escaper.htmlEscape(getIrbProtocolAndVersionName(), sb);
      sb.append('.');
    }
    
    String comments = getComments();
    if (!ApiFunctions.isEmpty(comments)) {
      sb.append("  Comments:<br>");
      Escaper.htmlEscapeAndPreserveWhitespace(comments, sb);
    }

    return sb.toString();
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
  public boolean isConfirmCanceled() {
    return _confirmCanceled;
  }

  /**
   * @return
   */
  public Date getConsentDate() {
    return _consentDate;
  }

  /**
   * The consent date string is denormalized from the consentDate field.  This is only
   * meant to be used internally by the class for the purpose of writing/reading the
   * date to/from a transaction history record.
   */
  public String getConsentDateString() {
    return _consentDateString;
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
  public String getConsentVersionId() {
    return _consentVersionId;
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
  public boolean isValidateOnly() {
    return _validateOnly;
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
  public String getFutureContactYN() {
    return _futureContactYN;
  }

  /**
   * @param string
   */
  public void setArdaisId(String string) {
    _ardaisId = string;
  }

  /**
   * @param b
   */
  public void setConfirmCanceled(boolean b) {
    _confirmCanceled = b;
  }

  /**
   * @param date
   */
  public void setConsentDate(Date date) {
    _consentDate = date;

    if (date == null) {
      setConsentDateString(null);
    }
    else {
      SimpleDateFormat formatter = new SimpleDateFormat(CONSENT_DATE_FORMAT);
      setConsentDateString(formatter.format(date));

      //if we're setting this value and the individual fields are not populated (year, month, etc)
      //set them.  This will be the case, for example, when modifying a case.  We retrieve the
      //consent date and set it, but now need to populate the individual fields.
      if (date != null && ApiFunctions.isEmpty(getYear())) {
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.setLenient(false);
        myCalendar.setTime(getConsentDate());
        int myYear = myCalendar.get(Calendar.YEAR);
        int myMonth = myCalendar.get(Calendar.MONTH) + 1;
        int myHours = myCalendar.get(Calendar.HOUR_OF_DAY);
        int myMinutes = myCalendar.get(Calendar.MINUTE);
        String amPm;
        //convert hours from 24 hour format and set am/pm
        if (myHours > 12) {
          myHours = myHours - 12;
          amPm = "pm";
        }
        else if (myHours == 12) {
          amPm = "pm";
        }
        else {
          amPm = "am";
          if (myHours == 0) {
            myHours = 12;
          }
        }
        setYear(myYear + "");
        setMonth(myMonth + "");
        setHours(myHours + "");
        setMinutes(myMinutes + "");
        setAmpm(amPm);
      }
    }
  }

  /**
   * The consent date string is denormalized from the consentDate field.  This is only
   * meant to be used internally by the class for the purpose of writing/reading the
   * date to/from a transaction history record.
   */
  public void setConsentDateString(String string) {
    _consentDateString = string;
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = ApiFunctions.safeTrim(string);
  }

  /**
   * @param string
   */
  public void setConsentVersionId(String string) {
    _consentVersionId = string;
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
  public void setValidateOnly(boolean b) {
    _validateOnly = b;
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
  public void setFutureContactYN(String string) {
    _futureContactYN = string;
  }

  /**
   * @return Returns the ampm.
   */
  public String getAmpm() {
    return _ampm;
  }
  /**
   * @param ampm The ampm to set.
   */
  public void setAmpm(String ampm) {
    _ampm = ampm;
  }
  /**
   * @return Returns the hours.
   */
  public String getHours() {
    return _hours;
  }
  /**
   * @param hours The hours to set.
   */
  public void setHours(String hours) {
    _hours = hours;
  }
  /**
   * @return Returns the minutes.
   */
  public String getMinutes() {
    return _minutes;
  }
  /**
   * @param minutes The minutes to set.
   */
  public void setMinutes(String minutes) {
    _minutes = minutes;
  }
  /**
   * @return Returns the month.
   */
  public String getMonth() {
    return _month;
  }
  /**
   * @param month The month to set.
   */
  public void setMonth(String month) {
    _month = month;
  }
  /**
   * @return Returns the year.
   */
  public String getYear() {
    return _year;
  }
  /**
   * @param year The year to set.
   */
  public void setYear(String year) {
    _year = year;
  }
}
