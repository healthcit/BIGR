package com.ardais.bigr.pdc.helpers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.javabeans.ConsentData;
import com.ardais.bigr.pdc.javabeans.PathReportData;

/**
 */
public class ConsentHelper extends JspHelper {

	// The consent data bean that holds data to be displayed
	// through this helper.
	private ConsentData _dataBean;

	// The path report for this consent
	private PathReportHelper _pathReport;
	
	private String _ardaisId;
  private String _donorCustomerId;
	private String _consentId;
	private String _context;
	private String _diCaseProfileNotes;
  private String _customerId;
  private String _donorImportedYN;
	
	private Map _donorLinkParams;

    // The default String to return
    private String _defaultString;
	

/**
 * Creates a <code>ConsentHelper</code>, initializing its data
 * from the specified data bean.
 *
 * @param  dataBean  the <code>ConsentData</code> bean holding the
 *		case data
 */
public ConsentHelper(ConsentData dataBean) {
	this(dataBean, null);
}
/**
 * Creates a <code>ConsentHelper</code>, initializing its data
 * from the specified data bean.
 *
 * @param  dataBean  the <code>ConsentData</code> bean holding the
 *		case data
 */
public ConsentHelper(ConsentData dataBean, String defaultString) {
	_dataBean = dataBean;
	_defaultString = defaultString;

	PathReportData pathReport = dataBean.getPathReport();
	if (pathReport != null) {
		PathReportHelper helper = new PathReportHelper(pathReport);
    	setPathReport(helper);
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (7/24/2002 1:24:02 PM)
 * @param request javax.servlet.http.HttpServletRequest
 */
public ConsentHelper(HttpServletRequest request) {
	_consentId = JspHelper.safeTrim(request.getParameter("consentId"));
	_ardaisId = JspHelper.safeTrim(request.getParameter("ardaisId"));
	_diCaseProfileNotes = JspHelper.safeTrim(request.getParameter("diCaseProfileNotes"));
	if (isEmpty(_consentId)) _consentId = null;
	_context = JspHelper.safeTrim(request.getParameter("context"));
  _donorImportedYN = JspHelper.safeTrim(request.getParameter("donorImportedYN"));
	
}

public String getAgeAtCollection() {
  String ageAtCollection = "";
  String minAge = IltdsUtils.getCaseMinAgeAtCollection(getConsentId());;
  String maxAge = IltdsUtils.getCaseMaxAgeAtCollection(getConsentId());
  String dashString = "";
  
  if (!ApiFunctions.isEmpty(minAge) && (!ApiFunctions.isEmpty(maxAge))) {
    dashString = "-";
  }
  
  if (minAge.equals(maxAge)) {
    ageAtCollection = minAge;
  }
  else {
    ageAtCollection = minAge + dashString + maxAge;
  }
  
  return ageAtCollection;  
}
/**
 * Insert the method's description here.
 * Creation date: (7/24/2002 2:36:12 PM)
 * @return java.lang.String
 */
public String getArdaisId() {
    String ardaisId = null;

    if (_ardaisId != null)
        ardaisId = _ardaisId;
    else if (_dataBean != null && _dataBean.getArdaisId() != null)
        ardaisId = _dataBean.getArdaisId();

    return ardaisId;
}
/**
 * Returns this case's id.
 *
 * @return  The case id.
 */
public String getConsentId() {
    String consentId = null;

    if (_consentId != null)
        consentId = _consentId;
    else if (_dataBean != null && _dataBean.getConsentId() != null)
        consentId = _dataBean.getConsentId();

    return consentId;
}
/**
 * Insert the method's description here.
 * Creation date: (7/24/2002 1:43:11 PM)
 * @return com.ardais.bigr.pdc.javabeans.ConsentData
 */
public ConsentData getDataBean() {
	if (_dataBean == null) {
		_dataBean = new ConsentData();
		_dataBean.setConsentId(_consentId);
    _dataBean.setArdaisId(_ardaisId);
		_dataBean.setDiCaseProfileNotes(_diCaseProfileNotes);
	}
	return _dataBean;
}
/**
 * Returns this case's diagnosis.  This is the diagnosis of the
 * primary pathology report section for the case.
 *
 * @return  The primary diagnosis.
 */
public String getDiagnosis() {
	String dx = null;
	if (_dataBean != null) dx = _dataBean.getDiagnosis();
	return (dx == null) ? _defaultString : BigrGbossData.getDiagnosisDescription(dx);
}
/**
 * Insert the method's description here.
 * Creation date: (7/24/2002 12:32:13 PM)
 * @return java.lang.String
 */
public String getDiCaseProfileNotes() {
    if (_diCaseProfileNotes != null)
        return _diCaseProfileNotes;
    if (_dataBean != null)
        return _dataBean.getDiCaseProfileNotes();
    return _defaultString;

}
/**
 * Returns a string indicating whether path report
 * abstraction is complete for this case.
 *
 * @return  "yes" if path report abstraction is complete; "no" otherwise
 */
public String getIsAbstracted() {
	if (_dataBean != null) {
		Boolean isComplete = _dataBean.isAbstractionComplete();
		if (isComplete != null) {
			return (isComplete.booleanValue()) ? "yes" : "no";
		}
	}
	return "no";
}
/**
 * Returns a string indicating whether the raw path report
 * has been entered for this case.
 *
 * @return  "yes" if the raw path report has been entered; "no" otherwise
 */
public String getIsRawPathReportEntered() {
	if (_dataBean != null) {
		Boolean isEntered = _dataBean.isRawPathReportEntered();
		if (isEntered != null) {
			return (isEntered.booleanValue()) ? "yes" : "no";
		}
	}
	return "no";
}
/**
 * Returns this case's pathreport.
 *
 * @return  The case path report.
 */
public PathReportHelper getPathReport() {
    return _pathReport;
}
/**
 * Returns the if of the path report associated with this case.
 *
 * @return  The path report id.
 */
public String getPathReportId() {
	if (_dataBean != null) return _dataBean.getPathReportId();
	return null;
}
/**
 * Sets this case's pathreport.
 *
 */
public void setPathReport(PathReportHelper pathReport) {
    _pathReport = pathReport;
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
		_donorLinkParams.put("pathOp", "CaseProfileNotesPrepare");
		_donorLinkParams.put("ardaisId", getArdaisId());
    _donorLinkParams.put("importedYN", getDonorImportedYN());
	}
	return _donorLinkParams;
}
/**
 * Returns this case's customer id.
 *
 * @return  The customer id.
 */
public String getCustomerId() {
    String customerId = null;

    if (_customerId != null)
      customerId = _customerId;
    else if (_dataBean != null && _dataBean.getCustomerId() != null)
      customerId = _dataBean.getCustomerId();

    return customerId;
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
  public String getDonorCustomerId() {
    return _donorCustomerId;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = string;
  }

  /**
   * @param string
   */
  public void setDonorCustomerId(String string) {
    _donorCustomerId = string;
  }

}
