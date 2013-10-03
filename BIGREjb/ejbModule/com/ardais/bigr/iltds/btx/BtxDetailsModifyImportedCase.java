package com.ardais.bigr.iltds.btx;

import java.util.Date;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.kc.form.FormInstanceHistoryObject;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of modifying an imported case.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setConsentId(String) consent id}: The Ardais consent id.</li>
 * <li>{@link #setArdaisId(String) Ardais id}: The Ardais donor id.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>{@link #setCustomerId(String) customer id}: The customer's consent id.</li>
 * <li>{@link #setConsentDate(Date) consent date}: The date of consent.</li>
 * <li>{@link #setDiagnosis(String) diagnosis}: The consent diagnosis.</li>
 * <li>{@link #setDiagnosisOther(String) diagnosis other}: The consent diagnosis other value if Other is chosen.</li>
 * <li>{@link #setComments(String) comments}: Comments.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setArdaisCustomerId(String) Ardais customer id}: The customer id for the donor to
 * whom the case belongs.</li>
 * </ul>
 */
public class BtxDetailsModifyImportedCase extends BtxDetailsCreateImportedCase implements java.io.Serializable {
  private static final long serialVersionUID = 6959009211971683096L;

  public BtxDetailsModifyImportedCase() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MODIFY_IMPORTED_CASE;
  }

  protected String doGetDetailsAsHTML() {
    // For this object type, the fields we can use here are:
    //   getConsentId
    //   getArdaisId
    //   getLinkedIndicator
    //   getPolicyId
    //   getPolicyName
    //   getConsentVersionId
    //   getIrbProtocolAndVersionName
    //   getConsentDateString
    //   getComments
    //   getArdaisAcctKey
    //   getImportedYN
    //   getCustomerId
    //   getDiagnosis
    //   getDiagnosisOther
    //   getArdaisCustomerId

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    StringBuffer sb = new StringBuffer(512);

    // The result has this form:
    //    Modified case <consentId> (<customerId>) for donor <ardaisId> (<ardaisCustomerId>)
    //    with policy '<policyName>' [and diagnosis <diagnosis> (<diagnosisOther>)].
    //    [Consent was obtained on <consentDateString> under IRB version
    //    <irbProtocolAndVersionName>.]
    //    [Comments: <comments>]

    boolean linked = "Y".equalsIgnoreCase(getLinkedIndicator());

    sb.append("Modified case ");
    sb.append(IcpUtils.prepareLink(getConsentId(), securityInfo));
    sb.append(" (");
    Escaper.htmlEscape(getCustomerId(), sb);
    sb.append(") for donor ");
    sb.append(IcpUtils.prepareLink(getArdaisId(), securityInfo));
    if (!ApiFunctions.isEmpty(getArdaisCustomerId())) {
      sb.append(" (");
      Escaper.htmlEscape(getArdaisCustomerId(), sb);
      sb.append(")");
    }
    sb.append(".  This case now has ");
    if (linked) {
      if (ApiFunctions.isEmpty(getConsentDateString())) {
        sb.append("a blank consent date");
      }
      else {
        sb.append("a consent date of ");
        Escaper.htmlEscape(getConsentDateString(), sb);
      }
      sb.append(" and ");
    }
    if (ApiFunctions.isEmpty(getDiagnosis())) {
      sb.append("a blank diagnosis");
    }
    else {
      sb.append("a diagnosis of '");
      Escaper.htmlEscape(getDiagnosis(), sb);
      if (!ApiFunctions.isEmpty(getDiagnosisOther())) {
        sb.append(" (");
        Escaper.htmlEscape(getDiagnosisOther(), sb);
        sb.append(")");
      }
      sb.append("'");
    }
    sb.append(".");
    
    if (_historyObject != null) {
      sb.append(" The following attributes were captured: ");
      FormInstanceHistoryObject historyObject = new FormInstanceHistoryObject();
      sb.append("<ul>");
      historyObject.doGetDetailsAsHTMLDataElementsOnly(_historyObject, sb);      
      sb.append("</ul>");
    }

    String comments = getComments();
    if (!ApiFunctions.isEmpty(comments)) {
      sb.append("<br>Comments:<br>");
      Escaper.htmlEscapeAndPreserveWhitespace(comments, sb);
    }

    return sb.toString();
  }

}