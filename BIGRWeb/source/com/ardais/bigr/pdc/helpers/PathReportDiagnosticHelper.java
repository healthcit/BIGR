package com.ardais.bigr.pdc.helpers;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.beans.DDCPathology;
import com.ardais.bigr.pdc.beans.DDCPathologyHome;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData;
import com.ardais.bigr.util.EjbHomes;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * <code>PathReportDiagnosticHelper</code> aids in the generation of JSPs
 * that allow viewing and editing of pathology report diagnostics.
 */
public class PathReportDiagnosticHelper extends JspHelper {
	private String _diagnostic;
	private String _note;	
	private String _pathReportId;
	private String _result;
	private String _type;
  private Integer _id;
  private String _consentId;

	// A flag that indicates whether the diagnostic is new.
	private String _new;
	
	// The string value to return from the getters in this class
	// when the value of the corresponding property is null.
	private String _defaultString;

	// The path report diagnostic data bean that holds data to be displayed
	// through this helper.
	private PathReportDiagnosticData _dataBean;	

	// The parent pathology report.
	private PathReportHelper _pathReport;
  
  private String _donorImportedYN;
/**
 * Creates a <code>PathReportDiagnosticHelper</code>, initializing its properties 
 * from a <code>PathReportDiagnosticData</code> bean.
 *
 * @param  dataBean  a <code>PathReportDiagnosticData</code> bean
 */
public PathReportDiagnosticHelper(PathReportDiagnosticData dataBean) {
	this(dataBean, null);
}
/**
 * Creates a <code>PathReportDiagnosticHelper</code>, initializing its properties 
 * from a <code>PathReportDiagnosticData</code> bean, and initializing its
 * default return string.
 *
 * @param  dataBean  a <code>PathReportDiagnosticData</code> bean
 * @param  defaultString  the value that should be returned from the getters of
 *			this class when the corresponding property is <code>null</code>
 */
public PathReportDiagnosticHelper(PathReportDiagnosticData dataBean, String defaultString) {
	_dataBean = dataBean;
	_defaultString = defaultString;
}
/**
 * Creates a <code>PathReportDiagnosticHelper</code> from an HTTP request,
 * initializing its properties from the request parameters.
 */
public PathReportDiagnosticHelper(HttpServletRequest request) {
	_diagnostic = JspHelper.safeTrim(request.getParameter("diagnostic"));
	_note = JspHelper.safeTrim(request.getParameter("note"));
	_pathReportId = JspHelper.safeTrim(request.getParameter("pathReportId"));
	_result = JspHelper.safeTrim(request.getParameter("result"));
	_type = JspHelper.safeTrim(request.getParameter("type"));
	_new = JspHelper.safeTrim(request.getParameter("new"));
  if (request.getParameter("id") != null)
    _id = new Integer(JspHelper.safeTrim(request.getParameter("id")));
  _donorImportedYN = safeTrim(request.getParameter("donorImportedYN"));
  _consentId = JspHelper.safeTrim(request.getParameter("consentId"));
 
}
/**
 * Returns the <code>PathReportDiagnosticData</code> bean that contains
 * the path report diagnostic data fields associated with this 
 * <code>PathReportDiagnosticHelper</code>.
 * 
 * @return  The <code>PathReportDiagnosticData</code> bean.
 */
public PathReportDiagnosticData getDataBean() {
	if (_dataBean == null) {
		_dataBean = new PathReportDiagnosticData();
		_dataBean.setDiagnostic(_diagnostic);
		_dataBean.setNote(_note);
		_dataBean.setPathReportId(_pathReportId);
		_dataBean.setResult(_result);
		_dataBean.setType(_type);
    _dataBean.setId(_id);
    _dataBean.setConsentId(_consentId);
	}
	return _dataBean;
}
/**
 * Returns the diagnostic code.
 *
 * @return  The diagnostic code.
 */
public String getDiagnostic() {
	if (_diagnostic != null) 
		return _diagnostic;
	if (_dataBean != null) 
		return _dataBean.getDiagnostic();
	return null;
}
/**
 * Returns the diagnostic description.
 *
 * @return  The diagnostic description.
 */
public String getDiagnosticDisplay() {
	String code = getDiagnostic();
	if (code != null) 
		return GbossFactory.getInstance().getDescription(/*"DIAGNOSTICS",*/ code);
	return _defaultString;
}
/**
 * Returns the list of diagnostic names.  This actually returns a
 * <code>LegalValueSet</code> with sub-<code>LegalValueSet</code>s,
 * where the parent legal values are the diagnostic types, and the
 * sub legal values are the diagnostic names.
 *
 * @return  The list of diagnostic names.
 */
public LegalValueSet getDiagnosticList() {

  return BigrGbossData.getDiagnosticTests();

}

public Integer getId() {
  return _dataBean.getId();
}

/**
 * Returns the diagnostic note.
 *
 * @return  The diagnostic note.
 */
public String getNote() {
	if (_note != null) 
		return _note;
	if (_dataBean != null) 
		return _dataBean.getNote();
	return _defaultString;
}
/**
 * Returns the {@link PathReportHelper} for the parent pathology report of this
 * diagnostic.
 *
 * @return  The parent {@link PathReportHelper}.
 */
public PathReportHelper getPathReport() {
	if (_pathReport == null) {
		if (getPathReportId() == null) {
			setPathReport(new PathReportHelper(new PathReportData()));			
		}
		else {
			try {
				PathReportData pathData = new PathReportData();
				pathData.setPathReportId(getPathReportId());
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathOperation = pathHome.create();
				pathData = pathOperation.getPathReport(pathData);
				setPathReport(new PathReportHelper(pathData));
			}
			catch (Exception e) {
				throw new ApiException(e);
			}
		}
    //the helper needs to inherit this objects donorImportedYN value 
    getPathReport().setDonorImportedYN(getDonorImportedYN());
	}		
	return _pathReport;
}
/**
 * Returns the id of the path report with which this diagnostic
 * is associated.
 *
 * @return  the path report id
 */
public String getPathReportId() {
	if (_pathReportId != null)
		return _pathReportId;
	if (_dataBean != null) 
		return _dataBean.getPathReportId();
	return null;
}
/**
 * Returns the diagnostic result code.
 *
 * @return  The diagnostic result code (ARTS).
 */
public String getResult() {
	if (_result != null) 
		return _result;
	if (_dataBean != null) 
		return _dataBean.getResult();
	return _defaultString;
}
/**
 * Returns the diagnostic result description.
 *
 * @return  The diagnostic description.
 */
public String getResultDisplay() {
  String code = getResult();
  if (code != null) 
    return GbossFactory.getInstance().getDescription(code);
  return _defaultString;
}

/**
 * Returns the list of result names.  This actually returns a
 * <code>LegalValueSet</code> with sub-<code>LegalValueSet</code>s,
 * where the parent legal values are the diagnostic tests, and the
 * sub legal values are the result names.
 *
 * @return  The list of diagnostic tests.
 */
public LegalValueSet getResultList() {
  return BigrGbossData.getDiagnosticResults();
}

/**
 * Returns the diagnostic type.
 *
 * @return  The diagnostic type.
 */
public String getType() {
	if (_type != null)
		return _type;
	if (_dataBean != null) 
		return _dataBean.getType();
	return null;
}
/**
 * Returns the type description.
 *
 * @return  The type description.
 */
public String getTypeDisplay() {
	String code = getType();
	if (code != null) 
		return GbossFactory.getInstance().getDescription(/*"DIAGNOSTIC_TYPE",*/ code);
	return _defaultString;
}
/**
 * Returns the list of diagnostic types.
 *
 * @return  The list of diagnostic types.
 */
public LegalValueSet getTypeList() {

  return BigrGbossData.getDiagnosticTypes();

}
/**
 * Returns <code>true</code> if this helper is for a new
 * diagnostic; <code>false</code> otherwise.
 * 
 * @return  A boolean indicating whether this diagnostic is new.
 */
public boolean isNew() {
	if ((_new != null) && (_new.equals("true")))
		return true;
	if ((_dataBean != null) && !isEmpty(_dataBean.getDiagnostic()))
		return false;
	return true;
}
/**
 * Returns <code>true</code> if the data associated 
 * with this helper is valid; otherwise returns <code>false</code>.
 *
 * @return  <code>true</code> if all parameters are valid;
 *			<code>false</code> otherwise.
 */
public boolean isValid() {
	boolean isValid = true;
	
	// Make sure that all required fields are filled in.
	if (ApiFunctions.isEmpty(getType())) {
		getMessageHelper().addMessage("Test Type must be specified");
		isValid = false;
	}
	if (ApiFunctions.isEmpty(getDiagnostic())) {
		getMessageHelper().addMessage("Test Name must be specified");
		isValid = false;
	}

	// Make sure that no fields are too long.
	if (!isEmpty(getNote()) && (getNote().length() > 500)) {
		getMessageHelper().addMessage("Note must not be more than 500 characters");
		isValid = false;
	}
    
  // A result must be selected (MR 7558)
  if (isEmpty(getResult())) {
    getMessageHelper().addMessage("Result must be specified");
    isValid = false;
  }
  
	if (!isEmpty(getResult()) && (getResult().length() > 200)) {
		getMessageHelper().addMessage("Result must not be more than 200 characters");
		isValid = false;
	}

	if (!isValid)
		getMessageHelper().setError(true);

	return isValid;
}
/**
 * Sets the {@link PathReportHelper} of the parent pathology report of this
 * diagnostic.
 *
 * @param  helper  the parent {@link PathReportHelper}.
 */
public void setPathReport(PathReportHelper helper) {
	_pathReport = helper;
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
