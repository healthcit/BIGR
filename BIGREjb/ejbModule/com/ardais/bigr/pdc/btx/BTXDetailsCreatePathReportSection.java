package com.ardais.bigr.pdc.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;

/**
 * This BTXDetails class captures the creation of an abstracted path report section for a consent.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setPathReportSectionData(PathReportSectionData) pathReportSectionData}: Bean holding the
 *      information for the path report section.</li>
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
public class BTXDetailsCreatePathReportSection extends BTXDetails implements Serializable {
    private static final long serialVersionUID = 3426814996266594277L;
    private PathReportSectionData _pathReportSectionData = null;

    /**
     * Constructor for BTXDetailsCreatePathReportSection.
     */
    public BTXDetailsCreatePathReportSection() {
        super();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
     */
    public String getBTXType() {
        return BTXDetails.BTX_TYPE_CREATE_PATH_REPORT_SECTION;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
     */
    public Set getDirectlyInvolvedObjects() {
      Set set = new HashSet();
      if (getPathReportSectionData() != null) {
        if (getPathReportSectionData().getConsentId() != null) {
          set.add(getPathReportSectionData().getConsentId());
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
      // Section <section id> was added to the pathology report for case <case id>. 
      // This section had the following attributes: <attribute value pairs>.
      PathReportSectionData ps = getPathReportSectionData();
      sb.append("Section ");
      Escaper.htmlEscape(ps.getSectionIdentifier(), sb);
      sb.append(" was added to the pathology report for case ");
      sb.append(IcpUtils.prepareLink(_pathReportSectionData.getConsentId(), securityInfo));
      if (FormLogic.DB_YES.equalsIgnoreCase(ps.getPrimary())) {
        sb.append(" as the primary section");
      }
      sb.append(".");
      
      //add the attributes to a seperate StringBuffer, so we can easily tell
      //if we need to add a preface to them.
      StringBuffer more = new StringBuffer(200);
      if (!ApiFunctions.isEmpty(ps.getDiagnosis())) {
        more.append("<li>");
        more.append("Diagnosis: ");
        Escaper.htmlEscape(ps.getDiagnosis(), more);
        if (!ApiFunctions.isEmpty(ps.getDiagnosisOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getDiagnosisOther(), more);
          more.append(")");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getTissueOrigin())) {
        more.append("<li>");
        more.append("Tissue of Origin: ");
        Escaper.htmlEscape(ps.getTissueOrigin(), more);
        if (!ApiFunctions.isEmpty(ps.getTissueOriginOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getTissueOriginOther(), more);
          more.append(")");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getTissueFinding())) {
        more.append("<li>");
        more.append("Site of Finding: ");
        Escaper.htmlEscape(ps.getTissueFinding(), more);
        if (!ApiFunctions.isEmpty(ps.getTissueFindingOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getTissueFindingOther(), more);
          more.append(")");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getTumorSize())) {
        more.append("<li>");
        more.append("Diseased Tissue Size (cm X cm X cm): ");
        if ("NS".equalsIgnoreCase(ps.getTumorSize())) {
          more.append("Not Specified");
        }
        else {
          boolean firstToken = true;
          StringTokenizer tokens = new StringTokenizer(ps.getTumorSize(),",");
          while (tokens.hasMoreTokens()) {
            if (!firstToken) {
              more.append(" X ");
            }
            Escaper.htmlEscape(tokens.nextToken(), more);
            firstToken = false;
          }
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getTumorWeight())) {
        more.append("<li>");
        more.append("Diseased Tissue Weight: ");
        if ("NS".equalsIgnoreCase(ps.getTumorWeight())) {
          more.append("Not Specified");
        }
        else {
          Escaper.htmlEscape(ps.getTumorWeight(), more);
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getHistologicalType())) {
        more.append("<li>");
        more.append("Histologic Type: ");
        Escaper.htmlEscape(ps.getHistologicalType(), more);
        if (!ApiFunctions.isEmpty(ps.getHistologicalTypeOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getHistologicalTypeOther(), more);
          more.append(")");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getHistologicalNuclearGrade())) {
        more.append("<li>");
        more.append("Histologic/Nuclear Grade: ");
        Escaper.htmlEscape(ps.getHistologicalNuclearGrade(), more);
        if (!ApiFunctions.isEmpty(ps.getHistologicalNuclearGradeOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getHistologicalNuclearGradeOther(), more);
          more.append(")");
        }
        more.append("</li>");
      }
      if (ps.getGleasonPrimary() != null && !ApiFunctions.isEmpty(ps.getGleasonPrimary().toString())) {
        more.append("<li>");
        more.append("Gleason Primary: ");
        more.append(ps.getGleasonPrimary().toString());
        more.append("</li>");
      }
      if (ps.getGleasonSecondary() != null && !ApiFunctions.isEmpty(ps.getGleasonSecondary().toString())) {
        more.append("<li>");
        more.append("Gleason Secondary: ");
        more.append(ps.getGleasonSecondary().toString());
        more.append("</li>");
      }
      if (ps.getGleasonTotal() != null && !ApiFunctions.isEmpty(ps.getGleasonTotal().toString())) {
        more.append("<li>");
        more.append("Gleason Total: ");
        more.append(ps.getGleasonTotal().toString());
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getPerineuralInvasion())) {
        more.append("<li>");
        more.append("Perineural Invasion: ");
        Escaper.htmlEscape(ps.getPerineuralInvasion(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getLymphaticVascularInvasion())) {
        more.append("<li>");
        more.append("Lymphatic Vascular Invasion: ");
        Escaper.htmlEscape(ps.getLymphaticVascularInvasion(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getMarginsInvolved())) {
        more.append("<li>");
        more.append("Margins Involved by Tumor: ");
        Escaper.htmlEscape(ps.getMarginsInvolved(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getMarginsUninvolved())) {
        more.append("<li>");
        more.append("Margins Uninvolved: ");
        Escaper.htmlEscape(ps.getMarginsUninvolved(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getInflammCellInfiltrate())) {
        more.append("<li>");
        more.append("Type of Inflammatory Cell Infiltrate: ");
        Escaper.htmlEscape(ps.getInflammCellInfiltrate(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getCellInfiltrateAmount())) {
        more.append("<li>");
        more.append("Cell Infiltrate Amount: ");
        Escaper.htmlEscape(ps.getCellInfiltrateAmount(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getTumorStageType())) {
        more.append("<li>");
        more.append("Tumor Stage Classification: ");
        Escaper.htmlEscape(ps.getTumorStageType(), more);
        if (!ApiFunctions.isEmpty(ps.getTumorStageTypeOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getTumorStageTypeOther(), more);
          more.append(")");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getTumorStageDesc())) {
        more.append("<li>");
        more.append("Tumor Stage: ");
        Escaper.htmlEscape(ps.getTumorStageDesc(), more);
        if (!ApiFunctions.isEmpty(ps.getTumorStageDescOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getTumorStageDescOther(), more);
          more.append(")");
        }
        if ("D".equalsIgnoreCase(ps.getTumorStageDescInd())) {
          more.append(" (Derived)");
        }
        else if ("R".equalsIgnoreCase(ps.getTumorStageDescInd())) {
          more.append(" (Reported)");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getExtensiveIntraductalComp())) {
        more.append("<li>");
        more.append("Extensive Intraductal Component: ");
        Escaper.htmlEscape(ps.getExtensiveIntraductalComp(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getExtranodalSpread())) {
        more.append("<li>");
        more.append("Extranodal Spread: ");
        Escaper.htmlEscape(ps.getExtranodalSpread(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getSizeLargestNodalMets())) {
        more.append("<li>");
        more.append("Size of Largest Nodal Metastasis: ");
        Escaper.htmlEscape(ps.getSizeLargestNodalMets(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getLymphNodeNotes())) {
        more.append("<li>");
        more.append("Lymph Node Notes: ");
        Escaper.htmlEscapeAndPreserveWhitespace(ps.getLymphNodeNotes(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getTotalNodesExamined())) {
        more.append("<li>");
        more.append("Total Number of Nodes Examined: ");
        Escaper.htmlEscape(ps.getTotalNodesExamined(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getTotalNodesPositive())) {
        more.append("<li>");
        more.append("Total Number of Positive Nodes: ");
        Escaper.htmlEscape(ps.getTotalNodesPositive(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getLymphNodeStage())) {
        more.append("<li>");
        more.append("Lymph Node Stage: ");
        Escaper.htmlEscape(ps.getLymphNodeStage(), more);
        if (!ApiFunctions.isEmpty(ps.getLymphNodeStageOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getLymphNodeStageOther(), more);
          more.append(")");
        }
        if ("D".equalsIgnoreCase(ps.getLymphNodeStageInd())) {
          more.append(" (Derived)");
        }
        else if ("R".equalsIgnoreCase(ps.getLymphNodeStageInd())) {
          more.append(" (Reported)");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getDistantMetastasis())) {
        more.append("<li>");
        more.append("Distant Metastasis: ");
        Escaper.htmlEscape(ps.getDistantMetastasis(), more);
        if (!ApiFunctions.isEmpty(ps.getDistantMetastasisOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getDistantMetastasisOther(), more);
          more.append(")");
        }
        if ("D".equalsIgnoreCase(ps.getDistantMetastasisInd())) {
          more.append(" (Derived)");
        }
        else if ("R".equalsIgnoreCase(ps.getDistantMetastasisInd())) {
          more.append(" (Reported)");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getStageGrouping())) {
        more.append("<li>");
        more.append("Minimum Stage Grouping: ");
        Escaper.htmlEscape(ps.getStageGrouping(), more);
        if (!ApiFunctions.isEmpty(ps.getStageGroupingOther())) {
          more.append(" (");
          Escaper.htmlEscape(ps.getStageGroupingOther(), more);
          more.append(")");
        }
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getJointComponent())) {
        more.append("<li>");
        more.append("Joint Component: ");
        Escaper.htmlEscape(ps.getJointComponent(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getTumorConfig())) {
        more.append("<li>");
        more.append("Tumor Configuration: ");
        Escaper.htmlEscape(ps.getTumorConfig(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getVenousVesselInvasion())) {
        more.append("<li>");
        more.append("Venous Vessel Invasion: ");
        Escaper.htmlEscape(ps.getVenousVesselInvasion(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(ps.getSectionNotes())) {
        more.append("<li>");
        more.append("Section Notes: ");
        Escaper.htmlEscapeAndPreserveWhitespace(ps.getSectionNotes(), more);
        more.append("</li>");
      }
      
      //add the preface to the attributes, if necessary
      if (more.length() > 0) {
        sb.append(" This section has the following attributes: ");
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
      history.setHistoryObject(getPathReportSectionData().describeAsHistoryObject());
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
     */
    public void populateFromHistoryRecord(BTXHistoryRecord history) {
      super.populateFromHistoryRecord(history);
      setPathReportSectionData(new PathReportSectionData((BtxHistoryAttributes)history.getHistoryObject()));
    }

    /**
     * @return
     */
    public PathReportSectionData getPathReportSectionData() {
      return _pathReportSectionData;
    }

    /**
     * @param data
     */
    public void setPathReportSectionData(PathReportSectionData data) {
      _pathReportSectionData = data;
    }

}
