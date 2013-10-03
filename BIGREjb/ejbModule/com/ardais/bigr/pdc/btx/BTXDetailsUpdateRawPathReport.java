package com.ardais.bigr.pdc.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * This BTXDetails class captures the editing of the raw path report for a consent.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setPathReportData(PathReportData) pathReportData}: Bean holding the
 *      information for the raw path report.</li>
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
public class BTXDetailsUpdateRawPathReport extends BTXDetailsPathReport implements Serializable {
    private static final long serialVersionUID = -1614299763078729460L;

    /**
     * Constructor for BTXDetailsHistoryNote.
     */
    public BTXDetailsUpdateRawPathReport() {
        super();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
     */
    public String getBTXType() {
        return BTXDetails.BTX_TYPE_UPDATE_RAW_PATH_REPORT;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
     */
    public Set getDirectlyInvolvedObjects() {
      Set set = new HashSet();
      if (getPathReportData() != null) {
        if (getPathReportData().getConsentId() != null) {
          set.add(getPathReportData().getConsentId());
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
      // The raw pathology report for case <case id> (<case alias>) belonging to 
      //donor <donor id> (<donor alias>) was edited.

      sb.append("The raw pathology report for case ");
      sb.append(IcpUtils.prepareLink(getPathReportData().getConsentId(), securityInfo));
      if (!ApiFunctions.isEmpty(getPathReportData().getConsentCustomerId())) {
        sb.append(" (");
        sb.append(Escaper.htmlEscapeAndPreserveWhitespace(getPathReportData().getConsentCustomerId()));
        sb.append(")");
      }
      sb.append(" belonging to donor ");
      sb.append(IcpUtils.prepareLink(getPathReportData().getArdaisId(), securityInfo));
      if (!ApiFunctions.isEmpty(getPathReportData().getDonorCustomerId())) {
        sb.append(" (");
        sb.append(Escaper.htmlEscapeAndPreserveWhitespace(getPathReportData().getDonorCustomerId()));
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
      history.setAttrib1(getPathReportData().getArdaisId());
      history.setAttrib2(getPathReportData().getConsentId());
      history.setAttrib3(getPathReportData().getPathReportId());
      history.setAttrib4(getPathReportData().getDonorCustomerId());
      history.setAttrib5(getPathReportData().getConsentCustomerId());
      history.setClob1(getPathReportData().getRawPathReport());
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
     */
    public void populateFromHistoryRecord(BTXHistoryRecord history) {
      super.populateFromHistoryRecord(history);
      PathReportData pathReportData = new PathReportData();
      pathReportData.setArdaisId(history.getAttrib1());
      pathReportData.setConsentId(history.getAttrib2());
      pathReportData.setPathReportId(history.getAttrib3());
      pathReportData.setRawPathReport(history.getClob1());
      pathReportData.setDonorCustomerId(history.getAttrib4());
      pathReportData.setConsentCustomerId(history.getAttrib5());
      setPathReportData(pathReportData);
    }

}
