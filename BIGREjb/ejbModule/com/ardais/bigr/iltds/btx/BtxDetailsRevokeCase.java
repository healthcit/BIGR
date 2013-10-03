package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

public class BtxDetailsRevokeCase extends BTXDetails {
  private String _ardaisId = null;
  private String _consentId = null;
  private String _pullRequestedBy = null;
  private String _consentId2 = null; // used to confirm _consentId for revoke.
  private String _txType = null; // is it iltds  or sample registration

  public BtxDetailsRevokeCase() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_REVOKE_CASE;
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
    history.setAttrib2(requestedBy);
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
    setPullRequestedBy(history.getAttrib2());
  }

  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getConsentId
    //   getRevokeRequestedBy

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(512);

    sb.append("Case ");
    sb.append(IcpUtils.prepareLink(getConsentId(), securityInfo));
    sb.append(" was revoked ");
    sb.append(" at the request of ");
    Escaper.htmlEscape(getPullRequestedBy(), sb);
    sb.append(".");

    return sb.toString();
  }


  /**
   * @return
   */
  public String getConsentId2() {
    return _consentId2;
  }

  /**
   * @return
   */
  public String getPullRequestedBy() {
    return _pullRequestedBy;
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
  public void setConsentId2(String string) {
    _consentId2 = string;
  }

  /**
   * @param string
   */
  public void setPullRequestedBy(String string) {
    _pullRequestedBy = string;
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
  public String getArdaisId() {
    return _ardaisId;
  }

  /**
   * @return
   */
  public String getConsentId() {
    return _consentId;
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
  public void setConsentId(String string) {
    _consentId = string;
  }

}
