package com.ardais.bigr.pdc.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.pdc.javabeans.ClinicalDataData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * This BTXDetails class captures the editing of clinical data for a consent.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setClinicalDataData(ClinicalDataData) clinicalDataData}: Bean holding the
 *      clinical data.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setDonorAlias(String) donorAlias}: Alias of the donor to which the clinical
 * data belongs.</li>
 * <li>{@link #setCaseAlias(String) caseAlias}: Alias of the case to which the clinical
 * data belongs.</li>
 * </ul>
 */
public class BTXDetailsUpdateClinicalData extends BTXDetails implements Serializable {
    private static final long serialVersionUID = -6397290107548424182L;
    private ClinicalDataData _clinicalDataData = null;
    private String _donorAlias = null;
    private String _caseAlias = null;

    /**
     * Constructor for BTXDetailsHistoryNote.
     */
    public BTXDetailsUpdateClinicalData() {
        super();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
     */
    public String getBTXType() {
        return BTXDetails.BTX_TYPE_UPDATE_CLINICAL_DATA;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
     */
    public Set getDirectlyInvolvedObjects() {
      Set set = new HashSet();
      if (getClinicalDataData() != null) {
        if (getClinicalDataData().getConsentId() != null) {
          set.add(getClinicalDataData().getConsentId());
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
      // '<category>' clinical data for case <case id> (<case alias>) belonging to donor 
      // <donor id> (<donor alias>) was edited.

      sb.append("'");
      Escaper.htmlEscape(_clinicalDataData.getCategory(), sb);
      sb.append("' clinical data for case ");
      sb.append(IcpUtils.prepareLink(_clinicalDataData.getConsentId(), securityInfo));
      if (!ApiFunctions.isEmpty(getCaseAlias())) {
        sb.append(" (");
        sb.append(Escaper.htmlEscapeAndPreserveWhitespace(getCaseAlias()));
        sb.append(")");
      }
      sb.append(" belonging to donor ");
      sb.append(IcpUtils.prepareLink(_clinicalDataData.getArdaisId(), securityInfo));
      if (!ApiFunctions.isEmpty(getDonorAlias())) {
        sb.append(" (");
        sb.append(Escaper.htmlEscapeAndPreserveWhitespace(getDonorAlias()));
        sb.append(")");
      }
      sb.append(" was edited.");
      return sb.toString();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
     */
    public void describeIntoHistoryRecord(BTXHistoryRecord history) {
      super.describeIntoHistoryRecord(history);
      history.setAttrib1(_clinicalDataData.getArdaisId());
      history.setAttrib2(_clinicalDataData.getConsentId());
      history.setAttrib3(GbossFactory.getInstance().getDescription(_clinicalDataData.getCategory()));
      history.setAttrib4(_clinicalDataData.getClinicalDataId());
      history.setAttrib5(getDonorAlias());
      history.setAttrib6(getCaseAlias());
      history.setClob1(_clinicalDataData.getClinicalData());
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
     */
    public void populateFromHistoryRecord(BTXHistoryRecord history) {
      super.populateFromHistoryRecord(history);
      ClinicalDataData clinicalData = new ClinicalDataData();
      clinicalData.setArdaisId(history.getAttrib1());
      clinicalData.setConsentId(history.getAttrib2());
      clinicalData.setCategory(history.getAttrib3());
      clinicalData.setClinicalDataId(history.getAttrib4());
      clinicalData.setClinicalData(history.getClob1());
      setClinicalDataData(clinicalData);
      setDonorAlias(history.getAttrib5());
      setCaseAlias(history.getAttrib6());
    }

    /**
     * @return
     */
    public ClinicalDataData getClinicalDataData() {
      return _clinicalDataData;
    }

    /**
     * @param data
     */
    public void setClinicalDataData(ClinicalDataData data) {
      _clinicalDataData = data;
    }

    /**
     * @return Returns the caseAlias.
     */
    public String getCaseAlias() {
      return _caseAlias;
    }
    /**
     * @return Returns the donorAlias.
     */
    public String getDonorAlias() {
      return _donorAlias;
    }
    /**
     * @param caseAlias The caseAlias to set.
     */
    public void setCaseAlias(String caseAlias) {
      _caseAlias = caseAlias;
    }
    /**
     * @param donorAlias The donorAlias to set.
     */
    public void setDonorAlias(String donorAlias) {
      _donorAlias = donorAlias;
    }
}
