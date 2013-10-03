package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of logging a sample creation.  This is designed to represent
 * details of a sample creation that has already happened, so that we can log it in
 * the transaction history.  It does not actually perform the sample creation.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setSampleBarcodeId(String) Sample id}: The sample that was created.</li>
 * <li>{@link #setAsmId(String) ASM id}: The sample's ASM id.</li>
 * <li>{@link #setAsmPosition(String) ASM position}: The sample's ASM position.</li>
 * <li>{@link #setAccountId(String) Account id}: The id of the donor institution account
 *     that the sample came from.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setSampleAlias(String) Sample Alias}: The alias of the sample that was created.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setAccountName(String) Account name}: The name of the donor institution account
 *     that the sample came from.</li>
 * <li>{@link #setConsentId(String) Case id}: The sample's case id.  This will be null if
 *     the sample's ASM doesn't exist yet.</li>
 * </ul>
 */
public class BtxDetailsLogCreateSample extends BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = -2600846721636355346L;

  private String _accountId = null;
  private String _accountName = null;
  private String _asmId = null;
  private String _asmPosition = null;
  private String _consentId = null;
  private String _sampleBarcodeId = null;
  private String _importedYN = null;
  private String _sampleAlias = null;

  public BtxDetailsLogCreateSample() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_LOG_CREATE_SAMPLE;
  }

  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();

    set.add(getSampleBarcodeId());

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

    history.setAttrib1(getSampleBarcodeId());
    history.setAttrib2(getAsmId());
    history.setAttrib3(getAsmPosition());
    history.setAttrib4(getConsentId());
    history.setAttrib5(getAccountId());
    history.setAttrib6(getAccountName());
    history.setAttrib7(getImportedYN());
    history.setAttrib8(getSampleAlias());
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

    setSampleBarcodeId(history.getAttrib1());
    setAsmId(history.getAttrib2());
    setAsmPosition(history.getAttrib3());
    setConsentId(history.getAttrib4());
    setAccountId(history.getAttrib5());
    setAccountName(history.getAttrib6());
    setImportedYN(history.getAttrib7());
    setSampleAlias(history.getAttrib8());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    // <NONE>
  }

  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getSampleBarcodeId
    //   getAsmId
    //   getAsmPosition
    //   getConsentId
    //   getAccountId
    //   getAccountName
    //   getImportedYN
    //   getSampleAlias

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(128);

    // The result has this form:
    // If the sample was imported,
    //    Created sample <sampleBarcodeId> (alias).
    // If the sample was not imported,
    //    Created sample <sampleBarcodeId> (<consentId> <asmPosition>).
    // Or, when the consentId is unknown:
    //    Created sample <sampleBarcodeId> (<asmId> <asmPosition>).  The ASM hasn't been
    //    linked to a case yet.

    String consentId = getConsentId();

    sb.append("Created sample ");
    StringBuffer linkText = new StringBuffer(50);
    String sampleAlias = getSampleAlias();
    linkText.append(getSampleBarcodeId());
    if (!ApiFunctions.isEmpty(sampleAlias)) {
      linkText.append(" (");
      linkText.append(sampleAlias);
      linkText.append(")");
    }
    sb.append(IcpUtils.prepareLink(getSampleBarcodeId(), linkText.toString(), securityInfo));
    if (FormLogic.DB_YES.equalsIgnoreCase(getImportedYN())) {
      sb.append(".");
    }
    else {
      sb.append(" (");
      if (ApiFunctions.isEmpty(consentId)) {
        sb.append(IcpUtils.prepareLink(getAsmId(), securityInfo));
      }
      else {
        sb.append(IcpUtils.prepareLink(consentId, securityInfo));
      }
      sb.append(' ');
      Escaper.htmlEscape(getAsmPosition(), sb);
      sb.append(").");
      if (ApiFunctions.isEmpty(consentId)) {
        sb.append("  The ASM hasn't been linked to a case yet.");
      }
    }

    return sb.toString();
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
  public String getAccountName() {
    return _accountName;
  }

  /**
   * @return
   */
  public String getAsmId() {
    return _asmId;
  }

  /**
   * @return
   */
  public String getAsmPosition() {
    return _asmPosition;
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
  public String getSampleBarcodeId() {
    return _sampleBarcodeId;
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
  public void setAccountName(String string) {
    _accountName = string;
  }

  /**
   * @param string
   */
  public void setAsmId(String string) {
    _asmId = string;
  }

  /**
   * @param string
   */
  public void setAsmPosition(String string) {
    _asmPosition = string;
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
  public void setSampleBarcodeId(String string) {
    _sampleBarcodeId = string;
  }

  /**
   * @return
   */
  public String getImportedYN() {
    return _importedYN;
  }

  /**
   * @param string
   */
  public void setImportedYN(String string) {
    _importedYN = string;
  }

  /**
   * @return Returns the sampleAlias.
   */
  public String getSampleAlias() {
    return _sampleAlias;
  }
  
  /**
   * @param sampleAlias The sampleAlias to set.
   */
  public void setSampleAlias(String sampleAlias) {
    _sampleAlias = sampleAlias;
  }
}