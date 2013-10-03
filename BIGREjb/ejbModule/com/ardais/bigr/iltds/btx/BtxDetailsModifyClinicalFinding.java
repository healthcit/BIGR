package com.ardais.bigr.iltds.btx;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.javabeans.ClinicalFindingData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of modifying a clinical finding.
 * <p>
 * The transaction-specific fields are described below.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setConsentId(String) consentId}: The id of the consent to which the finding belongs.
 * <li>{@link #setClinicalFinding(ClinicalFindingData) finding}: A <code>ClinicalFindingData</code> 
 * bean containing any clinical finding information for the specified consent.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setArdaisId(String) ardais id}: The id of the donor belonging to the specified case.</li>
 * </ul>
 * </ul>
 */
public class BtxDetailsModifyClinicalFinding extends BtxDetailsCreateClinicalFinding implements java.io.Serializable {
  
  public BtxDetailsModifyClinicalFinding() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MODIFY_CLINICAL_FINDING;
  }

  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    StringBuffer sb = new StringBuffer(100);
    sb.append("Modified clinical finding for consent ");
    sb.append(IcpUtils.prepareLink(getConsentId(), securityInfo));
    sb.append(".");

    StringBuffer details = getFindingValues();
    if (details.length() > 0) {
      sb.append("  The finding has");
      sb.append(details.toString());
      sb.append(".");
    }
    
    String notes = getClinicalFinding().getClinicalFindingNotes();
    if (!ApiFunctions.isEmpty(notes)) {
      sb.append("  Notes:<br>");
      Escaper.htmlEscapeAndPreserveWhitespace(notes, sb);
    }
    return sb.toString();
  }

}
