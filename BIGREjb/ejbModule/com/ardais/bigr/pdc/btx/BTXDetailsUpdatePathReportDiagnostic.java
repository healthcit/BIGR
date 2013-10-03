package com.ardais.bigr.pdc.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * This BTXDetails class captures the editing of an abstracted path report diagnostic for a consent.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setPathReportDiagnosticData(PathReportDiagnosticData) pathReportDiagnosticData}: Bean holding the
 *      information for the path report diagnostic.</li>
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
public class BTXDetailsUpdatePathReportDiagnostic extends BTXDetails implements Serializable {
    private static final long serialVersionUID = -4266517510757729253L;
    private PathReportDiagnosticData _pathReportDiagnosticData = null;

    /**
     * Constructor for BTXDetailsCreatePathReportDiagnostic.
     */
    public BTXDetailsUpdatePathReportDiagnostic() {
        super();
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
     */
    public String getBTXType() {
        return BTXDetails.BTX_TYPE_UPDATE_PATH_REPORT_DIAGNOSTIC;
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
     */
    public Set getDirectlyInvolvedObjects() {
      Set set = new HashSet();
      if (getPathReportDiagnosticData() != null) {
        if (getPathReportDiagnosticData().getConsentId() != null) {
          set.add(getPathReportDiagnosticData().getConsentId());
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
      // A diagnostic test belonging to the pathology report for case <case id> was edited. 
      // This test now has the following attributes: <attribute value pairs>.

      sb.append("A diagnostic test belonging to the pathology report for case ");
      sb.append(IcpUtils.prepareLink(_pathReportDiagnosticData.getConsentId(), securityInfo));
      sb.append(" was edited.");
      StringBuffer more = new StringBuffer(200);
      if (!ApiFunctions.isEmpty(_pathReportDiagnosticData.getType())) {
        more.append("<li>");
        more.append("Type: ");
        Escaper.htmlEscape(_pathReportDiagnosticData.getType(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(_pathReportDiagnosticData.getDiagnostic())) {
        more.append("<li>");
        more.append("Name: ");
        Escaper.htmlEscape(_pathReportDiagnosticData.getDiagnostic(), more);
        more.append("</li>");
      }
      if (!ApiFunctions.isEmpty(_pathReportDiagnosticData.getResult())) {
        more.append("<li>");
        more.append("Result: ");
        Escaper.htmlEscape(_pathReportDiagnosticData.getResult(), more);
        more.append("</li>");
      }
      if (more.length() > 0) {
        sb.append(" This test now has the following attributes: ");
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
      history.setAttrib1(_pathReportDiagnosticData.getConsentId());
      history.setAttrib2(_pathReportDiagnosticData.getPathReportId());
      if (_pathReportDiagnosticData.getId() != null) {
        history.setAttrib3(_pathReportDiagnosticData.getId().toString());
      }
      history.setAttrib4(GbossFactory.getInstance().getDescription(_pathReportDiagnosticData.getDiagnostic()));
      history.setAttrib5(GbossFactory.getInstance().getDescription(_pathReportDiagnosticData.getResult()));
      history.setAttrib6(GbossFactory.getInstance().getDescription(_pathReportDiagnosticData.getType()));
      history.setAttrib7(_pathReportDiagnosticData.getNote());
    }

    /**
     * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(BTXHistoryRecord)
     */
    public void populateFromHistoryRecord(BTXHistoryRecord history) {
      super.populateFromHistoryRecord(history);
      PathReportDiagnosticData pathReportDiagnosticData = new PathReportDiagnosticData();
      pathReportDiagnosticData.setConsentId(history.getAttrib1());
      pathReportDiagnosticData.setPathReportId(history.getAttrib2());
      if (!ApiFunctions.isEmpty(history.getAttrib3())) {
        pathReportDiagnosticData.setId(new Integer(history.getAttrib3()));
      }
      pathReportDiagnosticData.setDiagnostic(history.getAttrib4());
      pathReportDiagnosticData.setResult(history.getAttrib5());
      pathReportDiagnosticData.setType(history.getAttrib6());
      pathReportDiagnosticData.setNote(history.getAttrib7());
      setPathReportDiagnosticData(pathReportDiagnosticData);
    }

    /**
     * @return
     */
    public PathReportDiagnosticData getPathReportDiagnosticData() {
      return _pathReportDiagnosticData;
    }

    /**
     * @param data
     */
    public void setPathReportDiagnosticData(PathReportDiagnosticData data) {
      _pathReportDiagnosticData = data;
    }

}
