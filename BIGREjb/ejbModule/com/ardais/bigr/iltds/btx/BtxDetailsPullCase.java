package com.ardais.bigr.iltds.btx;

import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Represent the details of pulling an imported case.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setConsentId(String) consent id}: The id for the case being pulled.</li>
 * <li>{@link #setPullReason(String) pull reason}: The reason the case is being pulled.</li>
 * <li>{@link #setPullRequestedBy(String) requested by}: The id of the person requesting that the case
 * be pulled.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setPullNote(String) pull note}: A note in which the user can record comments.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setArdaisId(String) ardais id}: The id of the donor to whom the case being pulled belongs.</li>
 * <li>{@link #setPullRequestedByChoices(List) Requested by choices}: A list of people who can request
 * the pulling of a case.  This is a list of the employees in the same account as the
 * current user.</li>
 * </ul>
 */
public class BtxDetailsPullCase extends BtxDetailsRevokeCase implements java.io.Serializable {
  private static final long serialVersionUID = -2629975377630677831L;


  private String _customerId = null;
  private String _pullReason = null;
  private String _pullNote = null;

  public BtxDetailsPullCase() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_PULL_CASE;
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
    history.setAttrib2(GbossFactory.getInstance().getDescription(getPullReason()));
    String requestedBy = null;
    try {
      ArdaisstaffAccessBean staff;
      staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(getPullRequestedBy().trim()));
      requestedBy = staff.getArdais_staff_fname()
          + " "
          + staff.getArdais_staff_lname();
    }
    catch (Exception e) {
      requestedBy = "Unknown";
    }
    history.setAttrib3(requestedBy);
    history.setAttrib4(getPullNote());
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
    setConsentId(history.getAttrib1());
    setPullReason(history.getAttrib2());
    setPullRequestedBy(history.getAttrib3());
    setPullNote(history.getAttrib4());
  }

  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getConsentId
    //   getPullReason
    //   getPullRequestedBy
    //   getPullNote

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(512);

    // The result has this form:
    //    Case <consentId> was pulled with a reason of <reason> at the request of <requestedByDisplayName>.]
    //    [Comments: <pullNote>]

    sb.append("Case ");
    sb.append(IcpUtils.prepareLink(getConsentId(), securityInfo));
    sb.append(" was pulled with a reason of '");
    Escaper.htmlEscape(getPullReason(), sb);
    sb.append("' at the request of ");
    Escaper.htmlEscape(getPullRequestedBy(), sb);
    sb.append(".");

    String comments = getPullNote();
    if (!ApiFunctions.isEmpty(comments)) {
      sb.append("  Comments:<br>");
      Escaper.htmlEscapeAndPreserveWhitespace(comments, sb);
    }

    return sb.toString();
  }

  /**
   * @return
   */
  public String getPullNote() {
    return _pullNote;
  }

  /**
   * @return
   */
  public String getPullReason() {
    return _pullReason;
  }

  /**
   * @param string
   */
  public void setPullNote(String string) {
    _pullNote = string;
  }

  /**
   * @param string
   */
  public void setPullReason(String string) {
    _pullReason = string;
  }

  /**
   * @return
   */
  public String getCustomerId() {
    return _customerId;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = string;
  }

}
