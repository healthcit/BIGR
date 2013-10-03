package com.ardais.bigr.pdc.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * This BTXDetails class captures the editing of an abstracted path report for a consent.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setPathReportData(PathReportData) pathReportData}: Bean holding the
 *      information for the path report.</li>
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
public class BTXDetailsUpdatePathReport extends BTXDetailsPathReport implements Serializable {
    private static final long serialVersionUID = 1704303883471603331L;

    /**
     * Constructor for BTXDetailsCreatePathReport.
     */
    public BTXDetailsUpdatePathReport() {
        super();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
     */
    public String getBTXType() {
        return BTXDetails.BTX_TYPE_UPDATE_PATH_REPORT;
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
    // The pathology report for case <case id> (belonging to donor <donor id>) 
    // was edited with the following attributes: <attribute value pairs>.

    sb.append("The pathology report for case ");
    sb.append(IcpUtils.prepareLink(getPathReportData().getConsentId(), securityInfo));
    sb.append(" (belonging to donor ");
    sb.append(IcpUtils.prepareLink(getPathReportData().getArdaisId(), securityInfo));
    sb.append(") was edited");
    StringBuffer more = new StringBuffer(200);
    if (!ApiFunctions.isEmpty(getPathReportData().getDiagnosis())) {
      more.append("<li>");
      more.append("Diagnosis: ");
      Escaper.htmlEscape(getPathReportData().getDiagnosis(), more);
      more.append("</li>");
    }
    if (!ApiFunctions.isEmpty(getPathReportData().getDisease())) {
      more.append("<li>");
      more.append("Disease: ");
      Escaper.htmlEscape(getPathReportData().getDisease(), more);
      more.append("</li>");
    }
    if (!ApiFunctions.isEmpty(getPathReportData().getPathReportMonth()) ||
        !ApiFunctions.isEmpty(getPathReportData().getPathReportYear())) {
      more.append("<li>");
      more.append("Path Report Date: ");
      if (!ApiFunctions.isEmpty(getPathReportData().getPathReportMonth())) {
        Escaper.htmlEscape(getPathReportData().getPathReportMonth(), more);
      }
      else {
        more.append("(none)");
      }
      more.append("/");
      if (!ApiFunctions.isEmpty(getPathReportData().getPathReportYear())) {
        Escaper.htmlEscape(getPathReportData().getPathReportYear(), more);
      }
      else {
        more.append("(none)");
      }
      more.append("</li>");
    }
    if (!ApiFunctions.isEmpty(getPathReportData().getProcedure())) {
      more.append("<li>");
      more.append("Procedure: ");
      Escaper.htmlEscape(getPathReportData().getProcedure(), more);
      if (!ApiFunctions.isEmpty(getPathReportData().getProcedureOther())) {
        more.append(" (");
        Escaper.htmlEscape(getPathReportData().getProcedureOther(), more);
        more.append(")");
      }
      more.append("</li>");
    }
    if (!ApiFunctions.isEmpty(getPathReportData().getTissue())) {
      more.append("<li>");
      more.append("Tissue: ");
      Escaper.htmlEscape(getPathReportData().getTissue(), more);
      more.append("</li>");
    }
    if (more.length() > 0) {
      sb.append(" with the following attributes: ");
      sb.append("<ul>");
      sb.append(more.toString());
      sb.append("</ul>");
    }
    else {
      sb.append(".");
    }
    return sb.toString();
  }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
     */
    public void describeIntoHistoryRecord(BTXHistoryRecord history) {
      super.describeIntoHistoryRecord(history);
      history.setAttrib1(getPathReportData().getArdaisId());
      history.setAttrib2(getPathReportData().getConsentId());
      if (!ApiFunctions.isEmpty(getPathReportData().getDiagnosis())) {
        history.setAttrib3(BigrGbossData.getDiagnosisDescription(getPathReportData().getDiagnosis()));
      }
      if (!ApiFunctions.isEmpty(getPathReportData().getDisease())) {
        history.setAttrib4(GbossFactory.getInstance().getDescription(getPathReportData().getDisease()));
      }
      history.setAttrib5(getPathReportData().getPathReportMonth());
      history.setAttrib6(getPathReportData().getPathReportYear());
      if (!ApiFunctions.isEmpty(getPathReportData().getProcedure())) {
        history.setAttrib7(BigrGbossData.getProcedureDescription(getPathReportData().getProcedure()));
      }
      history.setAttrib8(getPathReportData().getProcedureOther());
      if (!ApiFunctions.isEmpty(getPathReportData().getTissue())) {
        history.setAttrib9(BigrGbossData.getTissueDescription(getPathReportData().getTissue()));
      }
      history.setAttrib10(getPathReportData().getPathReportId());
      history.setClob1(getPathReportData().getAdditionalNote());
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
     */
    public void populateFromHistoryRecord(BTXHistoryRecord history) {
      super.populateFromHistoryRecord(history);
      PathReportData pathReportData = new PathReportData();
      pathReportData.setArdaisId(history.getAttrib1());
      pathReportData.setConsentId(history.getAttrib2());
      pathReportData.setDiagnosis(history.getAttrib3());
      pathReportData.setDisease(history.getAttrib4());
      pathReportData.setPathReportMonth(history.getAttrib5());
      pathReportData.setPathReportYear(history.getAttrib6());
      pathReportData.setProcedure(history.getAttrib7());
      pathReportData.setProcedureOther(history.getAttrib8());
      pathReportData.setTissue(history.getAttrib9());
      pathReportData.setPathReportId(history.getAttrib10());
      pathReportData.setAdditionalNote(history.getClob1());
      setPathReportData(pathReportData);
    }

}
