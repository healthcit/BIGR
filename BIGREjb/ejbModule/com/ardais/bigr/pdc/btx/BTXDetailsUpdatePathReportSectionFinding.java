package com.ardais.bigr.pdc.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * This BTXDetails class captures the editing of a path report section finding.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setPathReportSectionFindingData(PathReportSectionFindingData) pathReportSectionFindingData}: Bean holding the
 *      information for the path report section finding.</li>
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
public class BTXDetailsUpdatePathReportSectionFinding extends BTXDetails implements Serializable {
    private static final long serialVersionUID = -7889456456724274237L;
    private PathReportSectionFindingData _pathReportSectionFindingData = null;

    /**
     * Constructor for BTXDetailsUpdatePathReportSectionFinding.
     */
    public BTXDetailsUpdatePathReportSectionFinding() {
        super();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
     */
    public String getBTXType() {
        return BTXDetails.BTX_TYPE_UPDATE_PATH_REPORT_SECTION_FINDING;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
     */
    public Set getDirectlyInvolvedObjects() {
      Set set = new HashSet();
      if (getPathReportSectionFindingData() != null) {
        if (getPathReportSectionFindingData().getConsentId() != null) {
          set.add(getPathReportSectionFindingData().getConsentId());
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
      // A section finding belonging to the pathology report for case <case id> was edited. 
      // This finding now has the following attributes: <attribute value pairs>.

      sb.append("An additional finding belonging to");
      if (!ApiFunctions.isEmpty(_pathReportSectionFindingData.getPathReportSectionName())) {
        sb.append(" section ");
        Escaper.htmlEscape(_pathReportSectionFindingData.getPathReportSectionName(), sb);
        sb.append(" of");
      }
      sb.append(" the pathology report for case ");
      sb.append(IcpUtils.prepareLink(_pathReportSectionFindingData.getConsentId(), securityInfo));
      sb.append(" was edited.");
      StringBuffer more = new StringBuffer(200);
      if (!ApiFunctions.isEmpty(_pathReportSectionFindingData.getDiagnosis())) {
        more.append("<li>");
        more.append("Additional Pathology Finding: ");
        Escaper.htmlEscape(_pathReportSectionFindingData.getDiagnosis(), more);
        if (!ApiFunctions.isEmpty(_pathReportSectionFindingData.getDiagnosisOther())) {
          more.append(" (");
          Escaper.htmlEscape(_pathReportSectionFindingData.getDiagnosisOther(), more);
          more.append(")");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(_pathReportSectionFindingData.getTissue())) {
        more.append("<li>");
        more.append("Tissue: ");
        Escaper.htmlEscape(_pathReportSectionFindingData.getTissue(), more);
        if (!ApiFunctions.isEmpty(_pathReportSectionFindingData.getTissueOther())) {
          more.append(" (");
          Escaper.htmlEscape(_pathReportSectionFindingData.getTissueOther(), more);
          more.append(")");
        }
        more.append("</li>");
      }
      if (more.length() > 0) {
        sb.append(" This finding now has the following attributes: ");
        sb.append("<ul>");
        sb.append(more.toString());
        sb.append("</ul>");
      }
      return sb.toString();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(BTXHistoryRecord)
     */
    public void describeIntoHistoryRecord(BTXHistoryRecord history) {
      super.describeIntoHistoryRecord(history);
      history.setAttrib1(_pathReportSectionFindingData.getConsentId());
      history.setAttrib2(_pathReportSectionFindingData.getPathReportSectionId());
      history.setAttrib3(_pathReportSectionFindingData.getFindingId());
      if (!ApiFunctions.isEmpty(_pathReportSectionFindingData.getDiagnosis())) {
        history.setAttrib4(BigrGbossData.getDiagnosisDescription(_pathReportSectionFindingData.getDiagnosis()));
      }
      history.setAttrib5(_pathReportSectionFindingData.getDiagnosisOther());
      if (!ApiFunctions.isEmpty(_pathReportSectionFindingData.getTissue())) {
        history.setAttrib6(BigrGbossData.getTissueDescription(_pathReportSectionFindingData.getTissue()));
      }
      history.setAttrib7(_pathReportSectionFindingData.getTissueOther());
      history.setAttrib8(_pathReportSectionFindingData.getNote());
      history.setAttrib9(_pathReportSectionFindingData.getPathReportSectionName());
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
     */
    public void populateFromHistoryRecord(BTXHistoryRecord history) {
      super.populateFromHistoryRecord(history);
      PathReportSectionFindingData pathReportSectionFindingData = new PathReportSectionFindingData();
      pathReportSectionFindingData.setConsentId(history.getAttrib1());
      pathReportSectionFindingData.setPathReportSectionId(history.getAttrib2());
      pathReportSectionFindingData.setFindingId(history.getAttrib3());
      pathReportSectionFindingData.setDiagnosis(history.getAttrib4());
      pathReportSectionFindingData.setDiagnosisOther(history.getAttrib5());
      pathReportSectionFindingData.setTissue(history.getAttrib6());
      pathReportSectionFindingData.setTissueOther(history.getAttrib7());
      pathReportSectionFindingData.setNote(history.getAttrib8());
      pathReportSectionFindingData.setPathReportSectionName(history.getAttrib9());
      setPathReportSectionFindingData(pathReportSectionFindingData);
    }

    /**
     * @return
     */
    public PathReportSectionFindingData getPathReportSectionFindingData() {
      return _pathReportSectionFindingData;
    }

    /**
     * @param data
     */
    public void setPathReportSectionFindingData(PathReportSectionFindingData data) {
      _pathReportSectionFindingData = data;
    }

}
