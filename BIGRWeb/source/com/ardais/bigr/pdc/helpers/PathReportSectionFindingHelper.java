package com.ardais.bigr.pdc.helpers;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.beans.DDCPathology;
import com.ardais.bigr.pdc.beans.DDCPathologyHome;
import com.ardais.bigr.pdc.beans.DDCPathologyHome;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData;
import com.ardais.bigr.util.EjbHomes;

/**
 */
public class PathReportSectionFindingHelper extends JspHelper {
	private String _diagnosis;
	private String _diagnosisOther;
	private String _findingId;
	private String _note;	
	private String _pathReportSectionId;
	private String _tissue;
	private String _tissueOther;

	// The parent pathology report section.
	private PathReportSectionHelper _pathReportSection;

	// The ancestor pathology report id and pathology report.
	private String _pathReportId;
	private PathReportHelper _pathReport;

	private String _defaultString;

	// The path report section finding data bean that holds data to be displayed
	// through this helper.
	private PathReportSectionFindingData _dataBean;	
  
  private String _donorImportedYN;
  private String _consentId;

/**
 * Creates a <code>PathReportSectionFindingHelper</code>, initializing its properties 
 * from a <code>PathReportSectionFindingData</code> bean.
 *
 * @param  dataBean  a <code>PathReportSectionFindingData</code> bean
 */
public PathReportSectionFindingHelper(PathReportSectionFindingData dataBean) {
	this(dataBean, null);
}
/**
 * Creates a <code>PathReportSectionFindingHelper</code>, initializing its properties 
 * from a <code>PathReportSectionFindingData</code> bean.
 *
 * @param  dataBean  a <code>PathReportSectionFindingData</code> bean
 */
public PathReportSectionFindingHelper(PathReportSectionFindingData dataBean, String defaultString) {
	_dataBean = dataBean;
	_defaultString = defaultString;
}
/**
 * Creates a <code>PathReportSectionFindingHelper</code> from an HTTP request,
 * initializing its properties from the request parameters.
 */
public PathReportSectionFindingHelper(HttpServletRequest request) {
	_findingId = ApiFunctions.safeTrim(request.getParameter("findingId"));
	if (ApiFunctions.isEmpty(_findingId)) _findingId = null;
	_diagnosis = ApiFunctions.safeTrim(request.getParameter("diagnosis"));
	_diagnosisOther = ApiFunctions.safeTrim(request.getParameter("diagnosisOther"));
	_note = ApiFunctions.safeTrim(request.getParameter("note"));
	_pathReportSectionId = ApiFunctions.safeTrim(request.getParameter("pathReportSectionId"));
	_tissue = ApiFunctions.safeTrim(request.getParameter("tissue"));
	_tissueOther = ApiFunctions.safeTrim(request.getParameter("tissueOther"));

	_pathReportId = ApiFunctions.safeTrim(request.getParameter("pathReportId"));
  _donorImportedYN = safeTrim(request.getParameter("donorImportedYN"));
  _consentId = safeTrim(request.getParameter("consentId"));
}
/**
 * Returns the <code>PathReportSectionFindingData</code> bean that contains
 * the path report section additional finding data fields associated with this 
 * <code>PathReportSectionFindingHelper</code>.
 * 
 * @return  The <code>PathReportSectionFindingData</code> bean.
 */
public PathReportSectionFindingData getDataBean() {
	if (_dataBean == null) {
		_dataBean = new PathReportSectionFindingData();
		_dataBean.setDiagnosis(_diagnosis);
		if ((_diagnosis != null) && (_diagnosis.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_DX)))
			_dataBean.setDiagnosisOther(_diagnosisOther);
		_dataBean.setFindingId(_findingId);
		_dataBean.setNote(_note);
		_dataBean.setPathReportSectionId(_pathReportSectionId);
		_dataBean.setTissue(_tissue);
		if ((_tissue != null) && (_tissue.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_TISSUE)))
			_dataBean.setTissueOther(_tissueOther);
    _dataBean.setConsentId(_consentId);
	}
	return _dataBean;
}
/**
 * Returns the diagnosis.
 *
 * @return  The diagnosis.
 */
public String getDiagnosis() {
	if (_diagnosis != null) 
		return _diagnosis;
	if (_dataBean != null) 
		return _dataBean.getDiagnosis();
	return null;
}
/**
 * Returns the display value of the diagnosis of this additional finding.
 *
 * @return  The diagnosis display value.
 */
public String getDiagnosisDisplay() {
	String code = getDiagnosis();
	return (code != null) ? BigrGbossData.getDiagnosisDescription(code) : _defaultString;
}
/**
 * Returns the "other" diagnosis.
 *
 * @return  The "other" diagnosis.
 */
public String getDiagnosisOther() {
	if (_diagnosisOther != null) 
		return _diagnosisOther;
	if (_dataBean != null) 
		return _dataBean.getDiagnosisOther();
	return null;
}
/**
 * Returns the additional finding id.
 *
 * @return  The additional finding id.
 */
public String getFindingId() {
	if (_findingId != null) 
		return _findingId;
	if (_dataBean != null) 
		return _dataBean.getFindingId();
	return null;
}
/**
 * Returns the additional finding note.
 *
 * @return  The additional finding note.
 */
public String getNote() {
	if (_note != null) 
		return _note;
	if (_dataBean != null) 
		return _dataBean.getNote();
	return _defaultString;
}
/**
 * Returns the {@link PathReportHelper} for the ancestor pathology report of this
 * additional finding.
 *
 * @return  The ancestor {@link PathReportHelper}.
 */
public PathReportHelper getPathReport() {
	if (_pathReport == null) {
		String id = getPathReportId();
		if (id == null) {
			_pathReport = new PathReportHelper(new PathReportData());
		}
		else {
			try {
				PathReportData pathData = new PathReportData();
				pathData.setPathReportId(id);
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathOperation = pathHome.create();
				pathData = pathOperation.getPathReport(pathData);
				setPathReport(new PathReportHelper(pathData));
			}
			catch (Exception e) {
				throw new ApiException(e);
			}
		}
	}		
	return _pathReport;
}
/**
 * Returns the id of the path report with which this additional finding
 * is associated.
 *
 * @return  The path report id.
 */
public String getPathReportId() {
	return _pathReportId;
}
/**
 * Returns the {@link PathReportSectionHelper} for the parent pathology report section of this
 * additional finding.
 *
 * @return  The parent {@link PathReportSectionHelper}.
 */
public PathReportSectionHelper getPathReportSection() {
	if (_pathReportSection == null) {
		String id = getPathReportSectionId();
		if (id == null) {
			_pathReportSection = new PathReportSectionHelper(new PathReportSectionData());
		}
		else {
			try {
				PathReportSectionData sectionData = new PathReportSectionData();
				sectionData.setPathReportSectionId(id);
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathOperation = pathHome.create();
				sectionData = pathOperation.getPathReportSection(sectionData);
				setPathReportSection(new PathReportSectionHelper(sectionData));
			}
			catch (Exception e) {
				throw new ApiException(e);
			}
		}
    //the section helper needs to inherit this objects donorImportedYN value 
    getPathReportSection().setDonorImportedYN(getDonorImportedYN());
	}		
	return _pathReportSection;
}
/**
 * Returns the id of the path report section with which this
 * additional finding is associated.
 *
 * @return  The path report section id.
 */
public String getPathReportSectionId() {
	if (_pathReportSectionId != null) 
		return _pathReportSectionId;
	if (_dataBean != null) 
		return _dataBean.getPathReportSectionId();
	return null;
}
/**
 * Returns the tissue.
 *
 * @return  The tissue.
 */
public String getTissue() {
	if (_tissue != null) 
		return _tissue;
	if (_dataBean != null) 
		return _dataBean.getTissue();
	return null;
}
/**
 * Returns the display value of the tissue of this additional finding.
 *
 * @return  The tissue display value.
 */
public String getTissueDisplay() {
	String code = getTissue();
	return (code != null) ? BigrGbossData.getTissueDescription(code) : _defaultString;
}
/**
 * Returns the "other" tissue.
 *
 * @return  The "other" tissue.
 */
public String getTissueOther() {
	if (_tissueOther != null) 
		return _tissueOther;
	if (_dataBean != null) 
		return _dataBean.getTissueOther();
	return null;
}
/**
 * Returns <code>true</code> if this helper is for a new
 * path report section additional finding; <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this additional finding is new.
 */
public boolean isNew() {
	return (isEmpty(getFindingId()));
}
/**
 * Returns <code>true</code> if the additional finding data associated 
 * with this helper is valid; otherwise returns <code>false</code>.
 * If validation fails, the {@link #getMessages()} method can be called to
 * display a message to the user indicating the problem(s).
 *
 * @return  <code>true</code> if all parameters are valid;
 *			<code>false</code> otherwise.
 */
public boolean isValid() {

	boolean isValid = true;

	// Diagnosis must be specified, and if "other" diagnosis is specified,
	// then the other diagnosis field must be specified.
	String diagnosis = getDiagnosis();
	if (ApiFunctions.isEmpty(diagnosis)) {
		getMessageHelper().addMessage("Additional Pathology Finding must be specified");
		isValid = false;
	}
	else if (diagnosis.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_DX)
			&& ApiFunctions.isEmpty(getDiagnosisOther())) {
		getMessageHelper().addMessage("Other Additional Pathology Finding be specified");
		isValid = false;
	}

	// Tissue must be specified, and if "other" tissue is specified,
	// then the other tissue field must be specified.
	String tissue = getTissue();
	if (ApiFunctions.isEmpty(tissue)) {
		getMessageHelper().addMessage("Tissue must be specified");
		isValid = false;
	}
	else if (tissue.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_TISSUE)
			&& ApiFunctions.isEmpty(getTissueOther())) {
		getMessageHelper().addMessage("Other Tissue must be specified");
		isValid = false;
	}

	// The combination of diagnosis and tissue cannot already exist for this path report
	// section, unless at least one of them is "other".
	if (isNew()
		&& !ApiFunctions.isEmpty(diagnosis)
		&& !ApiFunctions.isEmpty(tissue)
		&& !diagnosis.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_DX)
		&& !tissue.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_TISSUE)) {
		try {
      PathReportSectionFindingData data = getDataBean();
      DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      DDCPathology pathOperation = pathHome.create();
			if (pathOperation.isExistsPathReportSectionFinding(data)) {
				getMessageHelper().addMessage("The specified Additional Pathology Finding and Tissue already exists for this pathology report section.");
				isValid = false;
			}
		}
		catch (Exception e) {
			throw new ApiException(e);
		}
	}
	
				
	// The note field cannot be > 500 characters.
	String note = getNote();
	if (!ApiFunctions.isEmpty(note) && (note.length() > 500)) {
		getMessageHelper().addMessage("Additional Finding Notes must not be greater than 500 characters");
		isValid = false;
	}

	if (!isValid) {
		getMessageHelper().setError(true);
	}
	return isValid;
}
/**
 * Sets the {@link PathReportHelper} of the ancestor pathology report of this
 * additional finding.
 *
 * @param  helper  the ancestor {@link PathReportHelper}.
 */
public void setPathReport(PathReportHelper helper) {
	_pathReport = helper;
}
/**
 * Sets the id of the ancestor pathology report of this additional finding.
 *
 * @param  id  the id
 */
public void setPathReportId(String id) {
	_pathReportId = id;
}
/**
 * Sets the {@link PathReportSectionHelper} of the parent pathology report section of this
 * additional finding.
 *
 * @param  helper  the parent {@link PathReportSectionHelper}.
 */
public void setPathReportSection(PathReportSectionHelper helper) {
	_pathReportSection = helper;
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
  public String getConsentId() {
    return _consentId;
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = string;
  }

}
