package com.ardais.bigr.pdc.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.pdc.javabeans.ConsentData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * This BTXDetails class captures the creation of case profile notes for a consent.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setConsentData(ConsentData) consentData}: Bean holding the
 *      case profile notes.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BTXDetailsCreateCaseProfileNotes extends BTXDetails implements Serializable {
    private static final long serialVersionUID = 3402660137664281876L;
    private ConsentData _consentData = null;
    private String _donorAlias = null;
    private String _caseAlias = null;

    /**
     * Constructor for BTXDetailsHistoryNote.
     */
    public BTXDetailsCreateCaseProfileNotes() {
        super();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
     */
    public String getBTXType() {
        return BTXDetails.BTX_TYPE_CREATE_CASE_PROFILE_NOTES;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
     */
    public Set getDirectlyInvolvedObjects() {
      Set set = new HashSet();
      if (getConsentData() != null) {
        if (getConsentData().getConsentId() != null) {
          set.add(getConsentData().getConsentId());
        }
      }
      return set;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
     */
    protected String doGetDetailsAsHTML() {
      SecurityInfo securityInfo = getLoggedInUserSecurityInfo();
      StringBuffer sb = new StringBuffer(64);

      // The result has this form:
      // The raw pathology report for case <case id> (belonging to donor <donor id>) was entered.

      sb.append("Case profile notes for case ");
      sb.append(IcpUtils.prepareLink(_consentData.getConsentId(), securityInfo));
      if (!ApiFunctions.isEmpty(_caseAlias)) {
        sb.append(" (").append(_caseAlias).append(")");
      }
      sb.append(" belonging to donor ");
      sb.append(IcpUtils.prepareLink(_consentData.getArdaisId(), securityInfo));
      if (!ApiFunctions.isEmpty(_donorAlias)) {
        sb.append(" (").append(_donorAlias).append(")");
      }
      sb.append(" were entered.");
      return sb.toString();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
     */
    public void describeIntoHistoryRecord(BTXHistoryRecord history) {
      super.describeIntoHistoryRecord(history);
      history.setAttrib1(_consentData.getArdaisId());
      history.setAttrib2(_consentData.getConsentId());
      history.setAttrib3(getDonorAlias());
      history.setAttrib4(getCaseAlias());
      history.setClob1(_consentData.getDiCaseProfileNotes());
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
     */
    public void populateFromHistoryRecord(BTXHistoryRecord history) {
      super.populateFromHistoryRecord(history);
      ConsentData consentData = new ConsentData();
      consentData.setArdaisId(history.getAttrib1());
      consentData.setConsentId(history.getAttrib2());
      setDonorAlias(history.getAttrib3());
      setCaseAlias(history.getAttrib4());
      consentData.setDiCaseProfileNotes(history.getClob1());
      setConsentData(consentData);
    }

    /**
     * @return
     */
    public ConsentData getConsentData() {
      return _consentData;
    }

    /**
     * @param data
     */
    public void setConsentData(ConsentData data) {
      _consentData = data;
    }

    /**
     * @return Returns the _caseAlias.
     */
    public String getCaseAlias() {
      return _caseAlias;
    }
    /**
     * @param _caseAlias The _caseAlias to set.
     */
    public void setCaseAlias(String caseAlias) {
      _caseAlias = caseAlias;
    }
    /**
     * @return Returns the _donorAlias.
     */
    public String getDonorAlias() {
      return _donorAlias;
    }
    /**
     * @param _donorAlias The _donorAlias to set.
     */
    public void setDonorAlias(String donorAlias) {
      _donorAlias = donorAlias;
    }
}
