package com.ardais.bigr.pdc.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.ConsentOperation;
import com.ardais.bigr.iltds.beans.ConsentOperationHome;
import com.ardais.bigr.iltds.helpers.DateHelper;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;
import com.gulfstreambio.gboss.GbossFactory;

/**
 */
public class PathReportHelper extends JspHelper {
  // The properties of the path report.
  private String _pathReportId;
  private String _additionalNote;
  private String _ardaisId;
  private String _donorCustomerId;
  private String _consentId;
  private String _consentCustomerId;
  private String _diagnosis;
  private String _disease;
  private String _pathReportMonth;
  private String _pathReportYear;
  private String _primarySectionId;
  private String _procedure;
  private String _procedureOther;
  private String _rawPathReport;
  private String _tissue;

  // The variable to store the number of sections
  private int _totalSections = 0;

  // The context under which the path report is being saved.  This
  // dictates property validation.  One of: "disease" or "case".
  private String _context;

  // The string that should be returned by the getters of this class when the
  // value of the property is null.  By default this is null, and thus the
  // getters will return null.
  private String _defaultString;

  // The account associated with the consent.  This is necessary since
  // the lookup lists for many of the DDC fields are based on the consent's
  // account.
  private String _consentAccount;

  // The path report data bean that holds data to be displayed
  // through this helper.
  private PathReportData _dataBean;

  // The list of sections for the path report.  Each item in the
  // list is a PathReportSectionHelper.
  private List _sections;

  // The list of diagnostics for the path report.  Each item in the
  // list is a PathReportDiagnosticHelper.
  private List _diagnostics;

  private Map _donorLinkParams;
  private Map _donorRawLinkParams;
  private Map _consentLinkParams;
  private Map _rawPopupLinkParams;
  private Map _editPathLinkParams;
  private Map _newSectionLinkParams;
  private Map _editSectionLinkParams;
  private int _sectionsIndex;
  private Map _newDiagnosticLinkParams;
  private Map _editDiagnosticLinkParams;
  private int _diagnosticsIndex;
  
  private String _donorImportedYN;

  /**
   * Creates a <code>PathReportHelper</code>, initializing its properties 
   * from a <code>PathReportData</code> bean.
   *
   * @param  dataBean  a <code>PathReportData</code> bean
   */
  public PathReportHelper(PathReportData dataBean) {
    this(dataBean, null);

  }
  /**
   * Creates a <code>PathReportHelper</code>, initializing its properties 
   * from a <code>PathReportData</code> bean.
   *
   * @param  dataBean  a <code>PathReportData</code> bean
   */
  public PathReportHelper(PathReportData dataBean, String defaultString) {
    _dataBean = dataBean;
    _defaultString = defaultString;

    List sections = _dataBean.getSections();

    _totalSections = sections.size();

    for (int i = 0; i < sections.size(); i++) {
      PathReportSectionData section = (PathReportSectionData) sections.get(i);
      addSection(new PathReportSectionHelper(section, _defaultString));
    }

    List diagnostics = _dataBean.getDiagnostics();
    for (int i = 0; i < diagnostics.size(); i++) {
      PathReportDiagnosticData diagnostic = (PathReportDiagnosticData) diagnostics.get(i);
      addDiagnostic(new PathReportDiagnosticHelper(diagnostic, _defaultString));
    }
  }
  /**
   * Creates a <code>PathReportHelper</code> from an HTTP request,
   * initializing its properties from the request parameters.
   */
  public PathReportHelper(HttpServletRequest request) {
    _pathReportId = JspHelper.safeTrim(request.getParameter("pathReportId"));
    if (isEmpty(_pathReportId))
      _pathReportId = null;
    _additionalNote = JspHelper.safeTrim(request.getParameter("additionalNote"));
    _ardaisId = JspHelper.safeTrim(request.getParameter("ardaisId"));
    _consentId = JspHelper.safeTrim(request.getParameter("consentId"));
    _disease = JspHelper.safeTrim(request.getParameter("disease"));
    _pathReportMonth = JspHelper.safeTrim(request.getParameter("pathReportMonth"));
    _pathReportYear = JspHelper.safeTrim(request.getParameter("pathReportYear"));
    _primarySectionId = JspHelper.safeTrim(request.getParameter("primarySectionId"));
    _procedure = JspHelper.safeTrim(request.getParameter("procedure"));
    _procedureOther = JspHelper.safeTrim(request.getParameter("procedureOther"));
    _rawPathReport = JspHelper.safeTrim(request.getParameter("rawPathReport"));

    _context = JspHelper.safeTrim(request.getParameter("context"));
    
    _donorImportedYN = JspHelper.safeTrim(request.getParameter("donorImportedYN"));

  }
  /**
   * Adds a <code>PathReportDiagnosticHelper</code> to this <code>PathReportHelper</code>.
   * The diagnostic is added to the end of an ordered list.
   *
   * @param  helper  the <code>PathReportDiagnosticHelper</code>
   */
  public void addDiagnostic(PathReportDiagnosticHelper helper) {
    if (_diagnostics == null)
      _diagnostics = new ArrayList();
    _diagnostics.add(helper);
  }
  /**
   * Adds a <code>PathReportSectionHelper</code> to this <code>PathReportHelper</code>.
   * The section is added to the end of an ordered list.
   *
   * @param  helper  the <code>PathReportSectionHelper</code>
   */
  public void addSection(PathReportSectionHelper helper) {
    if (_sections == null)
      _sections = new ArrayList();
    _sections.add(helper);
  }
  /**
   * Returns the path report's additional note.
   *
   * @return  The path report's additional note.
   */
  public String getAdditionalNote() {
    if (_additionalNote != null)
      return _additionalNote;
    else if (_dataBean != null)
      return _dataBean.getAdditionalNote();
    else
      return null;
  }
  /**
   * Returns the path report's donor (Ardais) id.
   *
   * @return  The path report's donor id.
   */
  public String getArdaisId() {
    if (_ardaisId != null)
      return _ardaisId;
    else if (_dataBean != null)
      return _dataBean.getArdaisId();
    else
      return null;
  }
  /**
   * Returns the account associated with the path report's consent id.
   *
   * @return  The consent's account.
   */
  public String getConsentAccount() {
    if (_consentAccount == null) {
      try {
        String consentId = getConsentId();
        ConsentOperationHome home = (ConsentOperationHome) EjbHomes.getHome(ConsentOperationHome.class);
        ConsentOperation lookup = home.create();
        _consentAccount = lookup.getCaseOrigin(consentId);
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return _consentAccount;
  }
  /**
   * Returns the path report's consent id.
   *
   * @return  The path report's consent id.
   */
  public String getConsentId() {
    if (_consentId != null)
      return _consentId;
    else if (_dataBean != null)
      return _dataBean.getConsentId();
    else
      return null;
  }
  /**
   * Returns the context under which this helper is being used.
   *
   * @return  The context.
   */
  public String getContext() {
    return _context;
  }
  /**
   * Returns the <code>PathReportData</code> bean that contains
   * the path report data fields associated with this 
   * <code>PathReportHelper</code>.
   * 
   * @return  The <code>PathReportData</code> bean.
   */
  public PathReportData getDataBean() {
    if (_dataBean == null) {
      _dataBean = new PathReportData();
      if (!isNew()) {
        _dataBean.setPathReportId(_pathReportId);
      }
      _dataBean.setAdditionalNote(_additionalNote);
      _dataBean.setArdaisId(_ardaisId);
      _dataBean.setConsentId(_consentId);
      _dataBean.setDisease(_disease);
      _dataBean.setPathReportMonth(_pathReportMonth);
      _dataBean.setPathReportYear(_pathReportYear);
      _dataBean.setProcedure(_procedure);
      if ((_procedure != null)
        && (_procedure.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_PX)))
        _dataBean.setProcedureOther(_procedureOther);
      _dataBean.setRawPathReport(_rawPathReport);
    }
    return _dataBean;
  }
  /**
   * Returns this pathology report's diagnosis.  This is the diagnosis of the
   * the pathology report's primary section.
   *
   * @return  The pathology report diagnosis.
   */
  public String getDiagnosis() {
    String dx = null;
    if (_diagnosis != null)
      dx = _diagnosis;
    else if (_dataBean != null)
      dx = _dataBean.getDiagnosis();
    return (dx == null) ? null : BigrGbossData.getDiagnosisDescription(dx);
  }
  /**
   * Returns the list of diagnostics (<code>PathReportDiagnosticHelper</code>s) that were
   * added to this <code>PathReportHelper</code>.  If there are no diagnostics,
   * an empty list is returned.
   *
   * @return  The list of <code>PathReportDiagnosticHelper</code>s.
   */
  public List getDiagnostics() {
    return _diagnostics;
  }
  /**
   * Returns this pathology report's disease type.
   *
   * @return  The disease type.
   */
  public String getDisease() {
    if (_disease != null)
      return _disease;
    else if (_dataBean != null)
      return _dataBean.getDisease();
    return null;
  }
  /**
   * Returns the list of disease types.
   *
   * @return  The list of disease types.
   */
  public LegalValueSet getDiseaseList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_DISEASE_TYPE);
  }
  /**
   * Returns the name of this pathology report's disease type.
   *
   * @return  The disease type name.
   */
  public String getDiseaseName() {
    String code = getDisease();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"DISEASE_TYPE",*/
    code) : null;
  }
  /**
   * Returns the list of months that can be entered for this pathology report's month.
   *
   * @return  A <code>LegalValueSet</code> that holds the list of months.
   */
  public LegalValueSet getMonthList() {
    LegalValueSet months = new LegalValueSet();
    months.addLegalValue("", "MM");
    months.addLegalValue("01", "Jan");
    months.addLegalValue("02", "Feb");
    months.addLegalValue("03", "Mar");
    months.addLegalValue("04", "Apr");
    months.addLegalValue("05", "May");
    months.addLegalValue("06", "Jun");
    months.addLegalValue("07", "Jul");
    months.addLegalValue("08", "Aug");
    months.addLegalValue("09", "Sep");
    months.addLegalValue("10", "Oct");
    months.addLegalValue("11", "Nov");
    months.addLegalValue("12", "Dec");
    return months;
  }
  /**
   * Returns the path report id.
   *
   * @return  The path report id.
   */
  public String getPathReportId() {
    if (_pathReportId != null)
      return _pathReportId;
    if (_dataBean != null) {
      return _dataBean.getPathReportId();
    }
    return null;
  }
  /**
   * Returns this pathology report's month.
   *
   * @return  The month.
   */
  public String getPathReportMonth() {
    if (_pathReportMonth != null)
      return _pathReportMonth;
    else if (_dataBean != null)
      return _dataBean.getPathReportMonth();
    else
      return null;
  }
  /**
   * Returns this pathology report's year.
   *
   * @return  The year.
   */
  public String getPathReportYear() {
    if (_pathReportYear != null)
      return _pathReportYear;
    else if (_dataBean != null)
      return _dataBean.getPathReportYear();
    else
      return null;
  }
  /**
   * Returns the id of this path report's primary section.
   *
   * @return  The primary section id.
   */
  public String getPrimarySectionId() {
    if (_primarySectionId != null)
      return _primarySectionId;
    if (_dataBean != null) {
      return _dataBean.getPrimarySectionId();
    }
    return null;
  }
  /**
   * Returns this pathology report's procedure code.
   *
   * @return  The procedure code.
   */
  public String getProcedure() {
    if (_procedure != null)
      return _procedure;
    if (_dataBean != null)
      return _dataBean.getProcedure();
    return _defaultString;
  }
  /**
   * Returns the name of this pathology report's procedure.
   *
   * @return  The procedure name.
   */
  public String getProcedureName() {
    String code = getProcedure();
    return (code == null) ? _defaultString : BigrGbossData.getProcedureDescription(code);
  }
  /**
   * Returns the pathology report's "other" procedure.
   *
   * @return  The pathology report's "other" procedure.
   */
  public String getProcedureOther() {
    if (_procedureOther != null)
      return _procedureOther;
    else if (_dataBean != null)
      return _dataBean.getProcedureOther();
    else
      return null;
  }
  /**
   * Returns this pathology report's raw pathology report.
   *
   * @return  The pathology report's raw pathology report.
   */
  public String getRawPathReport() {
    if (_rawPathReport != null)
      return _rawPathReport;
    else if (_dataBean != null)
      return _dataBean.getRawPathReport();
    else
      return null;
  }
  /**
   * Returns the list of sections (<code>PathReportSectionHelper</code>s) that were
   * added to this <code>PathReportHelper</code>.  If there are no sections,
   * an empty list is returned.
   *
   * @return  The list of <code>PathReportSectionHelper</code>s.
   */
  public List getSections() {
    return _sections;
  }
  /**
   * Returns this pathology report's tissue.  This is the origin tissue of the
   * the pathology report's primary section.
   *
   * @return  The pathology report tissue.
   */
  public String getTissue() {
    String tc = null;
    if (_tissue != null)
      tc = _tissue;
    else if (_dataBean != null)
      tc = _dataBean.getTissue();
    return (tc == null) ? null : BigrGbossData.getTissueDescription(tc);
  }
  /**
   * Insert the method's description here.
   * Creation date: (8/8/2002 5:21:01 PM)
   * @return int
   */
  public int getTotalSections() {
    return _totalSections;
  }
  /**
   * Returns the list of years that can be entered for this pathology report's year.
   *
   * @return  A <code>LegalValueSet</code> that contains the list of years.
   */
  public LegalValueSet getYearList() {
    return DateHelper.getYearList(1900, 2010);
  }
  /**
   * Returns a <code>Map</code> of URL parameters for the donor
   * link, in support of the Struts html:link tag.
   * 
   * @return  The URL parameters.
   */
  public Map getDonorLinkParams() {
    if (_donorLinkParams == null) {
      _donorLinkParams = new HashMap();
      _donorLinkParams.put("op", "CaseListPrepare");
      _donorLinkParams.put("pathOp", "PathAbstractSummaryPrepare");
      _donorLinkParams.put("ardaisId", getArdaisId());
      _donorLinkParams.put("importedYN", getDonorImportedYN());
    }
    return _donorLinkParams;
  }
  /**
   * Returns a <code>Map</code> of URL parameters for the donor
   * link for the raw path report transaction, in support of 
   * the Struts html:link tag.
   * 
   * @return  The URL parameters.
   */
  public Map getDonorRawLinkParams() {
    if (_donorRawLinkParams == null) {
      _donorRawLinkParams = new HashMap();
      _donorRawLinkParams.put("op", "CaseListPrepare");
      _donorRawLinkParams.put("pathOp", "PathRawPrepare");
      _donorRawLinkParams.put("ardaisId", getArdaisId());
      _donorRawLinkParams.put("importedYN", getDonorImportedYN());
    }
    return _donorRawLinkParams;
  }
  /**
   * Returns a <code>Map</code> of URL parameters for the consent
   * link, in support of the Struts html:link tag.
   * 
   * @return  The URL parameters.
   */
  public Map getConsentLinkParams() {
    if (_consentLinkParams == null) {
      _consentLinkParams = new HashMap();
      _consentLinkParams.put("op", "PathAbstractSummaryPrepare");
      _consentLinkParams.put("pathReportId", getPathReportId());
      _consentLinkParams.put("donorImportedYN", getDonorImportedYN());
    }
    return _consentLinkParams;
  }
  /**
   * Returns a <code>Map</code> of URL parameters for the link to
   * the raw path report popup, in support of the Struts html:link tag.
   * 
   * @return  The URL parameters.
   */
  public Map getRawPopupLinkParams() {
    if (_rawPopupLinkParams == null) {
      _rawPopupLinkParams = new HashMap();
      _rawPopupLinkParams.put("op", "PathRawPrepare");
      _rawPopupLinkParams.put("pathReportId", getPathReportId());
      _rawPopupLinkParams.put("donorImportedYN", getDonorImportedYN());
      _rawPopupLinkParams.put("popup", "true");
    }
    return _rawPopupLinkParams;
  }
  /**
   */
  public Map getEditPathLinkParams() {
    if (_editPathLinkParams == null) {
      _editPathLinkParams = new HashMap();
      _editPathLinkParams.put("op", "PathCasePrepare");
      _editPathLinkParams.put("pathReportId", getPathReportId());
      _editPathLinkParams.put("donorImportedYN", getDonorImportedYN());
    }
    return _editPathLinkParams;
  }
  /**
   */
  public Map getNewSectionLinkParams() {
    if (_newSectionLinkParams == null) {
      _newSectionLinkParams = new HashMap();
      _newSectionLinkParams.put("op", "PathSectionPrepare");
      _newSectionLinkParams.put("pathReportId", getPathReportId());
      _newSectionLinkParams.put("donorImportedYN", getDonorImportedYN());
    }
    return _newSectionLinkParams;
  }
  /**
   */
  public String getStartSections() {
    _sectionsIndex = 0;
    _editSectionLinkParams = new HashMap();
    _editSectionLinkParams.put("op", "PathSectionSummaryPrepare");
    _editSectionLinkParams.put("pathReportId", getPathReportId());
    return null;
  }
  /**
   */
  public String getNextSection() {
    _sectionsIndex++;
    return null;
  }
  /**
   */
  private PathReportSectionHelper getCurrentSection() {
    return (PathReportSectionHelper) _sections.get(_sectionsIndex);
  }
  public Map getCurrentSectionLinkParams() {
    PathReportSectionHelper section = getCurrentSection();
    _editSectionLinkParams.put("pathReportSectionId", section.getPathReportSectionId());
    _editSectionLinkParams.put("donorImportedYN", getDonorImportedYN());
    return _editSectionLinkParams;
  }
  /**
   */
  public Map getNewDiagnosticLinkParams() {
    if (_newDiagnosticLinkParams == null) {
      _newDiagnosticLinkParams = new HashMap();
      _newDiagnosticLinkParams.put("op", "PathDiagnosticPrepare");
      _newDiagnosticLinkParams.put("pathReportId", getPathReportId());
      _newDiagnosticLinkParams.put("new", "true");
      _newDiagnosticLinkParams.put("donorImportedYN", getDonorImportedYN());
      _newDiagnosticLinkParams.put("consentId", getConsentId());
    }
    return _newDiagnosticLinkParams;
  }
  /**
   */
  public String getStartDiagnostics() {
    _diagnosticsIndex = 0;
    _editDiagnosticLinkParams = new HashMap();
    _editDiagnosticLinkParams.put("op", "PathDiagnosticPrepare");
    _editDiagnosticLinkParams.put("pathReportId", getPathReportId());
    return null;
  }
  /**
   */
  public String getNextDiagnostic() {
    _diagnosticsIndex++;
    return null;
  }
  /**
   */
  private PathReportDiagnosticHelper getCurrentDiagnostic() {
    return (PathReportDiagnosticHelper) _diagnostics.get(_diagnosticsIndex);
  }
  public Map getCurrentDiagnosticLinkParams() {
    PathReportDiagnosticHelper diagnostic = getCurrentDiagnostic();
    _editDiagnosticLinkParams.put("diagnostic", diagnostic.getDiagnostic());
    _editDiagnosticLinkParams.put("id", diagnostic.getId().toString());
    _editDiagnosticLinkParams.put("donorImportedYN", getDonorImportedYN());
    _editDiagnosticLinkParams.put("consentId", getConsentId());
    return _editDiagnosticLinkParams;
  }

  /**
   * Returns <code>true</code> if this helper is for a new
   * path report; <code>false</code> otherwise.
   * 
   * @return  A boolean indicating whether this path report is new.
   */
  public boolean isNew() {
    return (isEmpty(getPathReportId()));
  }
  /**
   * Returns an indication of whether this path report has a primary section.
   *
   * @return  <code>true</code> if this path report has a primary section;
   * 			<code>false</code> otherwise.
   */
  public boolean isPrimarySectionChosen() {
    return (!ApiFunctions.isEmpty(getPrimarySectionId()));
  }
  /**
   * Returns <code>true</code> if the pathology data associated 
   * with this helper is valid; otherwise returns <code>false</code>.
   * If validation fails, the {@link #getMessages()} method can be called to
   * display a message to the user indicating the problem(s).
   *
   * @return  <code>true</code> if all parameters are valid;
   *			<code>false</code> otherwise.
   */
  public boolean isValid() {

    boolean isValid = true;
    String context = getContext();

    if (context != null) {
      if (context.equals("disease")) {
        if (ApiFunctions.isEmpty(getDisease())) {
          getMessageHelper().addMessage("Disease Type must be specified");
          isValid = false;
        }
      }

      else if (context.equals("case")) {
        if (ApiFunctions.isEmpty(getPathReportMonth())
          || ApiFunctions.isEmpty(getPathReportYear())) {
          getMessageHelper().addMessage("Pathology Report Date must be specified");
          isValid = false;
        }

        else { // MR 5027 -- do not continue if failed previous test.  null exceptions result...

          //MR 4894 verify that the path report
          // date is less than current time
          Date currentDate = new Date();
          // note that DateHelper is 0-based for month...
          int monthInt = Integer.parseInt(getPathReportMonth()) - 1; // values need to be 0-based
          DateHelper pathReportDate =
            new DateHelper("01", Integer.toString(monthInt), getPathReportYear());
          if (currentDate.before(pathReportDate.getDate())) {
            getMessageHelper().addMessage("Path Report Date cannot be in the future");
            isValid = false;
          }

          //MR 4894, PART II.  verify that the path report date
          // is >= 1/1/1999
          DateHelper firstDate = new DateHelper("01", "00", "1999");
          if (pathReportDate.getDate().before(firstDate.getDate())) {
            getMessageHelper().addMessage("Path Report Date cannot be before 1999");
            isValid = false;
          }

          String procedure = getProcedure();
          if (ApiFunctions.isEmpty(procedure)) {
            getMessageHelper().addMessage("Procedure must be specified");
            isValid = false;
          }
          else if (
            procedure.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_PX)
              && ApiFunctions.isEmpty(getProcedureOther())) {
            getMessageHelper().addMessage("Other Procedure must be specified");
            isValid = false;
          }

          procedure = getProcedureOther();
          if (!ApiFunctions.isEmpty(procedure) && (procedure.length() > 200)) {
            getMessageHelper().addMessage("Procedure must not be greater than 200 characters");
            isValid = false;
          }

        } // else MR 5027
      }
    }

    if (!isValid) {
      getMessageHelper().setError(true);
    }
    return isValid;
  }
  /**
   * @return
   */
  public String getDonorImportedYN() {
    return _donorImportedYN;
  }

  /**
   * @param string
   */
  public void setDonorImportedYN(String string) {
    _donorImportedYN = string;
  }

  /**
   * @return
   */
  public String getConsentCustomerId() {
    return _consentCustomerId;
  }

  /**
   * @return
   */
  public String getDonorCustomerId() {
    return _donorCustomerId;
  }

  /**
   * @param string
   */
  public void setConsentCustomerId(String string) {
    _consentCustomerId = string;
  }

  /**
   * @param string
   */
  public void setDonorCustomerId(String string) {
    _donorCustomerId = string;
  }

}
