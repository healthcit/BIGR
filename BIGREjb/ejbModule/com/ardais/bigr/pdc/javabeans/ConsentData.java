package com.ardais.bigr.pdc.javabeans;

import java.util.*;
import com.ardais.bigr.api.*;
import com.ardais.bigr.iltds.helpers.IltdsUtils;

import java.sql.*;
import java.io.*;

/**
 * This class represents donor data.
 */
public class ConsentData implements Serializable {
	private String _ardaisId;
	private String _consentId;
	private String _diagnosis;
	private Boolean _isAbstractionComplete;
	private Boolean _isRawPathReportEntered;
	private String _pathReportId;
	private String _diCaseProfileNotes;
  private String _customerId;

	// The Pathology Report for this consent.
    private PathReportData _pathReport;
/**
 * Creates a new <code>ConsentData</code>.
 */
public ConsentData() {
	super();
}
/**
 * Creates a new <code>ConsentData</code>.
 */
public ConsentData(ResultSet rs) {
    super();
    try {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        HashMap lookup = new HashMap();

        for (int i = 0; i < columnCount; i++) {
            lookup.put(meta.getColumnName(i + 1).toLowerCase(), null);
        }

        if (lookup.containsKey("consent_id"))
            setConsentId(rs.getString("consent_id"));

      if (lookup.containsKey("customer_id"))
          setCustomerId(rs.getString("customer_id"));

        if (lookup.containsKey("ardais_id"))
            setArdaisId(rs.getString("ardais_id"));

        if (lookup.containsKey("path_report_id"))
        	setPathReportId(rs.getString("path_report_id"));

        if (lookup.containsKey("diagnosis_concept_id"))
            setDiagnosis(rs.getString("diagnosis_concept_id"));

        if (lookup.containsKey("di_case_profile_notes"))
            setDiCaseProfileNotes(ApiFunctions.getStringFromClob(rs.getClob("di_case_profile_notes")));
                        
        if (lookup.containsKey("ddc_check_flag")) {
            String ddcCheck = rs.getString("ddc_check_flag");
            if ((ddcCheck != null) && (ddcCheck.equals("Y")))
                setAbstractionComplete(new Boolean(true));
            else
                setAbstractionComplete(new Boolean(false));
        }

        if (lookup.containsKey("path_report")) {
            String pathReport = rs.getString("path_report");
            if ((pathReport != null) && (pathReport.equals("Y")))
                setRawPathReportEntered(new Boolean(true));
            else
                setRawPathReportEntered(new Boolean(false));
        }

    } catch (SQLException e) {
        throw new ApiException(e);
    }
}
/**
 * Insert the method's description here.
 * Creation date: (7/24/2002 2:39:30 PM)
 * @return java.lang.String
 */
public String getArdaisId() {
	return _ardaisId;
}
/**
 * Returns this case's id.
 *
 * @return  The case id.
 */
public String getConsentId() {
	return _consentId;
}
/**
 * Returns this case's diagnosis.  This is the diagnosis of the
 * primary pathology report section for the case.
 *
 * @return  The primary diagnosis.
 */
public String getDiagnosis() {
	return _diagnosis;
}
/**
 * Insert the method's description here.
 * Creation date: (7/24/2002 12:28:00 PM)
 * @return java.lang.String
 */
public String getDiCaseProfileNotes() {
	return _diCaseProfileNotes;
}
/**
 * Returns this patholgoy report.
 *
 * @return  The pathology report.
 */
public PathReportData getPathReport() {
	return _pathReport;
}
/**
 * Returns the id of the pathology report associated with this case.
 *
 * @return  The path report id.
 */
public String getPathReportId() {
	return _pathReportId;
}
/**
 * Returns <code>true</code> if pathology report abstraction is complete 
 * for this case.  This is determined via the setting of the DDC_CHECK_FLAG.
 *
 * @return  <code>true</code> if pathology report abstraction is complete
 */
public Boolean isAbstractionComplete() {
	return _isAbstractionComplete;
}
/**
 * Returns <code>true</code> if the raw pathology report has been 
 * entered for this case.
 *
 * @return  <code>true</code> if the raw pathology report has been entered.
 */
public Boolean isRawPathReportEntered() {
	return _isRawPathReportEntered;
}
/**
 * Sets whether pathology report abstraction is complete 
 * for this case.  This is determined via the setting of the DDC_CHECK_FLAG.
 *
 * @param  complete  <code>true</code> if pathology report abstraction is complete
 */
public void setAbstractionComplete(Boolean complete) {
	_isAbstractionComplete = complete;
}
/**
 * Insert the method's description here.
 * Creation date: (7/24/2002 2:38:43 PM)
 * @param id java.lang.String
 */
public void setArdaisId(String id) {
	_ardaisId = id;	
}
/**
 * Sets this case's id.
 *
 * @param  id  the case id
 */
public void setConsentId(String id) {
	_consentId = id;
}
/**
 * Sets this case's diagnosis.  This is the diagnosis of the
 * primary pathology report section for the case.
 *
 * @param  dx  the diagnosis
 */
public void setDiagnosis(String dx) {
	_diagnosis = dx;
}
/**
 * Insert the method's description here.
 * Creation date: (7/24/2002 12:29:38 PM)
 * @param notes java.lang.String
 */
public void setDiCaseProfileNotes(String notes) {
	_diCaseProfileNotes = notes;
}
/**
 * Returns this patholgoy report.
 *
 * @return  The pathology report.
 */
public void setPathReport(PathReportData pathReport) {
	_pathReport = pathReport;
}
/**
 * Sets the id of the pathology report associated with this case.
 *
 * @param  id  the path report id
 */
public void setPathReportId(String id) {
	_pathReportId = id;
}
/**
 * Sets whether the raw pathology report has been 
 * entered for this case.
 *
 * @param  entered  <code>true</code> if the raw pathology report has been entered
 */
public void setRawPathReportEntered(Boolean entered) {
	_isRawPathReportEntered = entered;
}
  /**
   * @return
   */
  public String getCustomerId() {
    return _customerId;
  }

  /**
   * @param string
   */
  public void setCustomerId(String string) {
    _customerId = string;
  }

}
