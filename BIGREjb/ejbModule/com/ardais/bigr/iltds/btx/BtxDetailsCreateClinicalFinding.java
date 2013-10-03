package com.ardais.bigr.iltds.btx;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.javabeans.ClinicalFindingData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Represent the details of creating a clinical finding.
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
 */
public class BtxDetailsCreateClinicalFinding extends BTXDetails implements java.io.Serializable {
  private String _ardaisId;
  private String _consentId;
  private ClinicalFindingData _clinicalFinding;
  private boolean _newFinding;
  
  public BtxDetailsCreateClinicalFinding() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_CREATE_CLINICAL_FINDING;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(getConsentId());
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
    history.setHistoryObject(describeAsHistoryObject());
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
    this.setClinicalFinding(new ClinicalFindingData());
    populateFromHistoryObject((BtxHistoryAttributes)history.getHistoryObject());
  }

  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
    StringBuffer sb = new StringBuffer(100);
    sb.append("Created clinical finding for consent ");
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
  
  protected StringBuffer getFindingValues() {
    boolean includeComma = false;
    StringBuffer more = new StringBuffer(200);
    if (!ApiFunctions.isEmpty(getClinicalFinding().getPsa())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a Prostate Specific Antigen value of ");
      Escaper.htmlEscape(getClinicalFinding().getPsa(),more);
    }
    if (!ApiFunctions.isEmpty(getClinicalFinding().getDre())) {
      if (includeComma) {
        more.append(", ");
      }
      includeComma = true;
      more.append(" a Digital Rectal Exam value of ");
      Escaper.htmlEscape(getClinicalFinding().getDre(),more);
    }
    return more;
  }

  private Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = new BtxHistoryAttributes();
    if (!ApiFunctions.isEmpty(getConsentId())) {
      attributes.setAttribute("consentId", getConsentId());
    }
    if (!ApiFunctions.isEmpty(getClinicalFinding().getClinicalFindingNotes())) {
      attributes.setAttribute("clinicalFinding.clinicalFindingNotes", getClinicalFinding().getClinicalFindingNotes());
    }
    if (!ApiFunctions.isEmpty(getClinicalFinding().getDre())) {
      attributes.setAttribute("clinicalFinding.dre", GbossFactory.getInstance().getDescription(getClinicalFinding().getDre()));
    }
    if (!ApiFunctions.isEmpty(getClinicalFinding().getPsa())) {
      attributes.setAttribute("clinicalFinding.psa", getClinicalFinding().getPsa());
    }
    return attributes;
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
  public ClinicalFindingData getClinicalFinding() {
    return _clinicalFinding;
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
   * @param data
   */
  public void setClinicalFinding(ClinicalFindingData data) {
    _clinicalFinding = data;
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = string;
  }

  /**
   * @return
   */
  public boolean isNewFinding() {
    return _newFinding;
  }

  /**
   * @param b
   */
  public void setNewFinding(boolean b) {
    _newFinding = b;
  }

}
