package com.ardais.bigr.pdc.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * This class represents the case-level data for a pathology report.
 */
public class PathReportData implements Serializable {
	private String _pathReportId;
	private String _additionalNote;
	private String _ardaisId;
  private String _donorCustomerId;
	private String _consentId;
  private String _consentCustomerId;
	private String _createUser;
	private String _diagnosis;
	private String _disease;
	private String _lastUpdateUser;
	private String _pathReportMonth;
	private String _pathReportYear;
	private String _primarySectionId;
	private String _procedure;
	private String _procedureOther;
	private String _rawPathReport;
	private String _tissue;

	// The primary section for the path report.
	private PathReportSectionData _primarySection;

	// The list of sections for the path report.  Each item in the
	// list is a PathReportSectionData bean.
	private List _sections;

	// The list of diagnsotics for the path report.  Each item in the
	// list is a PathReportDiagnosticData bean.
	private List _diagnostics;

/**
 * Creates a new, empty <code>PathReportData</code>.
 */
public PathReportData() {
	super();
}

/**
 * Creates a new <code>PathReportData</code>, initialized from
 * the data in the PathReportData passed in.
 *
 * @param  pathReportData  the <code>PathReportData</code>
 */
public PathReportData(PathReportData pathReportData) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, pathReportData);
  //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
  //does a shallow copy.
  if (pathReportData.getPrimarySectionData() != null) {
    setPrimarySectionData(new PathReportSectionData(pathReportData.getPrimarySectionData()));
  }
  if (!ApiFunctions.isEmpty(pathReportData.getSections())) {
    _sections.clear();
    Iterator iterator = pathReportData.getSections().iterator();
    while (iterator.hasNext()) {
      _sections.add(new PathReportSectionData((PathReportSectionData)iterator.next()));
    }
  }
  if (!ApiFunctions.isEmpty(pathReportData.getDiagnostics())) {
    _diagnostics.clear();
    Iterator iterator = pathReportData.getDiagnostics().iterator();
    while (iterator.hasNext()) {
      _diagnostics.add(new PathReportDiagnosticData((PathReportDiagnosticData)iterator.next()));
    }
  }
}


/**
 * Creates a new <code>PathReportData</code>, initializing it from
 * the current row in the <code>ResultSet</code>.
 */
public PathReportData(ResultSet rs) {
    this();
    try {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        HashMap lookup = new HashMap();

        for (int i = 0; i < columnCount; i++) {
            lookup.put(meta.getColumnName(i + 1).toLowerCase(), null);
        }

        if (lookup.containsKey("additional_note"))
            setAdditionalNote(ApiFunctions.getStringFromClob(rs.getClob("additional_note")));

        if (lookup.containsKey("ardais_id"))
            setArdaisId(rs.getString("ardais_id"));

        if (lookup.containsKey("consent_id"))
            setConsentId(rs.getString("consent_id"));

        if (lookup.containsKey("diagnosis_concept_id"))
            setDiagnosis(rs.getString("diagnosis_concept_id"));

        if (lookup.containsKey("disease_concept_id"))
            setDisease(rs.getString("disease_concept_id"));

        if (lookup.containsKey("path_report_id"))
            setPathReportId(rs.getString("path_report_id"));

        if (lookup.containsKey("path_check_date_mm"))
            setPathReportMonth(rs.getString("path_check_date_mm"));

        if (lookup.containsKey("path_check_date_yyyy"))
            setPathReportYear(rs.getString("path_check_date_yyyy"));

        if (lookup.containsKey("primary_path_report_section_id"))
            setPrimarySectionId(rs.getString("primary_path_report_section_id"));

        if (lookup.containsKey("procedure_concept_id"))
            setProcedure(rs.getString("procedure_concept_id"));

        if (lookup.containsKey("other_pr_name"))
            setProcedureOther(rs.getString("other_pr_name"));

        if (lookup.containsKey("pathology_ascii_report"))
            setRawPathReport(ApiFunctions.getStringFromClob(rs.getClob("pathology_ascii_report")));

        if (lookup.containsKey("tissue_concept_id"))
            setTissue(rs.getString("tissue_concept_id"));
            
    } catch (SQLException e) {
        throw new ApiException(e);
    }
}

	/**
	 * Populates this <code>PathReportData</code> from the data in the 
	 * current row of the result set.
	 * 
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public void populate(Map columns, ResultSet rs) {
    try {
	    if (columns.containsKey(DbAliases.PATH_ID)) {
	    	setPathReportId(rs.getString(DbAliases.PATH_ID));
	    }
    } catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
    }
	}

/**
 * Adds a <code>PathReportDiagnosticData</code> bean to this <code>PathReportData</code> bean.
 * The diagnostic is added to the end of an ordered list.
 *
 * @param  dataBean  the <code>PathReportDiagnosticData</code> bean
 */
public void addDiagnostic(PathReportDiagnosticData dataBean) {
	if (_diagnostics == null) _diagnostics = new ArrayList();
	_diagnostics.add(dataBean);
}
/**
 * Adds a <code>PathReportSectionData</code> bean to this <code>PathReportData</code> bean.
 * The section is added to the end of an ordered list.
 *
 * @param  dataBean  the <code>PathReportSectionData</code> bean
 */
public void addSection(PathReportSectionData dataBean) {
	if (_sections == null) _sections = new ArrayList();
	_sections.add(dataBean);
}
/**
 * Returns the path report's additional note.
 *
 * @return  the additional note
 */
public String getAdditionalNote() {
	return _additionalNote;
}
/**
 * Returns this path report's donor id.
 *
 * @return  the donor id
 */
public String getArdaisId() {
	return _ardaisId;
}
/**
 * Returns this path report's consent id.
 *
 * @return  the consent id
 */
public String getConsentId() {
	return _consentId;
}
/**
 * Returns the user that created this path report.
 *
 * @return  The user.
 */
public String getCreateUser() {
	return _createUser;
}
/**
 * Returns the diagnosis of this path report's primary section.
 *
 * @return  the primary diagnosis
 */
public String getDiagnosis() {
	return _diagnosis;
}
/**
 * Returns the list of diagnostics (<code>PathReportDiagnosticData</code> beans) that were
 * added to this <code>PathReportData</code> bean.  If there are no diagnostics,
 * an empty list is returned.
 *
 * @return  The list of <code>PathReportDiagnosticData</code> beans.
 */
public List getDiagnostics() {
	if (_diagnostics == null) _diagnostics = new ArrayList();
	return _diagnostics;
}
/**
 * Returns the disease associated with this path report.
 *
 * @return  the disease
 */
public String getDisease() {
	return _disease;
}
/**
 * Returns the user that last updated this path report.
 *
 * @return  The user.
 */
public String getLastUpdateUser() {
	return _lastUpdateUser;
}
/**
 * Returns this path report's id.
 *
 * @return  the id
 */
public String getPathReportId() {
	return _pathReportId;
}
/**
 * Returns the month of the raw pathology report associated with this path report.
 *
 * @return  the path report month
 */
public String getPathReportMonth() {
	return _pathReportMonth;
}
/**
 * Returns the year of the raw pathology report associated with this path report.
 *
 * @return  the path report year
 */
public String getPathReportYear() {
	return _pathReportYear;
}
/**
 * Returns the id of this path report's primary section.
 *
 * @return  the primary section's id
 */
public String getPrimarySectionId() {
	return _primarySectionId;
}
/**
 * Returns this path report's procedure.
 *
 * @return  the procedure
 */
public String getProcedure() {
	return _procedure;
}
/**
 * Returns this path report's "other" procedure.
 *
 * @return  the "other" procedure
 */
public String getProcedureOther() {
	return _procedureOther;
}
/**
 * Returns this path report's raw pathology report.
 *
 * @return  the raw pathology report
 */
public String getRawPathReport() {
	return _rawPathReport;
}
/**
 * Returns the list of sections (<code>PathReportSectionData</code> beans) that were
 * added to this <code>PathReportData</code> bean.  If there are no sections,
 * an empty list is returned.
 *
 * @return  The list of <code>PathReportSectionData</code> beans.
 */
public List getSections() {
	if (_sections == null) _sections = new ArrayList();
	return _sections;
}
/**
 * Returns the origin tissue of this path report's primary section.
 *
 * @return  the origin tissue
 */
public String getTissue() {
	return _tissue;
}
/**
 * Sets the additional note for this path report.
 *
 * @param  note  the additional note
 */
public void setAdditionalNote(String note) {
	_additionalNote = note;
}
/**
 * Sets the donor id for this path report.
 *
 * @param  id  the donor id
 */
public void setArdaisId(String id) {
	_ardaisId = id;
}
/**
 * Sets the consent id for this path report.
 *
 * @param  id  the consent id
 */
public void setConsentId(String id) {
	_consentId = id;
}
/**
 * Sets the user that created this path report.
 *
 * @param  user  the user
 */
public void setCreateUser(String user) {
	_createUser = user;
}
/**
 * Sets this path report's diagnosis.
 *
 * @param  dx  the diagnosis
 */
public void setDiagnosis(String dx) {
	_diagnosis = dx;
}
/**
 * Sets this path report's diagnositcs.
 *
 * @param  diagnostics  the Diagnostics
 */
public void setDiagnostics(List diagnostics) {
	_diagnostics = diagnostics;
}
/**
 * Sets this path report's disease.
 *
 * @param  disease  the disease
 */
public void setDisease(String disease) {
	_disease = disease;
}
/**
 * Sets the user that last updated this path report.
 *
 * @param  user  the user
 */
public void setLastUpdateUser(String user) {
	_lastUpdateUser = user;
}
/**
 * Sets this path report's id.
 *
 * @param  id  the id
 */
public void setPathReportId(String id) {
	_pathReportId = id;
}
/**
 * Sets the month of this path report's raw pathology report.
 *
 * @param  month  the month
 */
public void setPathReportMonth(String month) {
	_pathReportMonth = month;
}
/**
 * Sets the year of this path report's raw pathology report.
 *
 * @param  year  the year
 */
public void setPathReportYear(String year) {
	_pathReportYear = year;
}
/**
 * Sets the id of this path report's primary section.
 *
 * @param  id  the primary section's id
 */
public void setPrimarySectionId(String id) {
	_primarySectionId = id;
}
/**
 * Sets this path report's procedure.
 *
 * @param  procedure  the procedure
 */
public void setProcedure(String procedure) {
	_procedure = procedure;
}
/**
 * Sets this path report's "other" procedure.
 *
 * @param  procedure  the "other" procedure
 */
public void setProcedureOther(String procedure) {
	_procedureOther = procedure;
}
/**
 * Sets this path report's raw pathology report.
 *
 * @param  report  the raw pathology report
 */
public void setRawPathReport(String report) {
	_rawPathReport = report;
}
/**
 * Sets this path report's sections.
 *
 * @param  sections  the sections
 */
public void setSections(List sections) {
	_sections = sections;
}
/**
 * Sets this path report's tissue.
 *
 * @param  tissue  the tissue
 */
public void setTissue(String tissue) {
	_tissue = tissue;
}

	/**
	 * Returns the primary path report section data bean for the primary path 
	 * report section for this path report consent.
	 * 
	 * @return  The PathReportSectionData bean.
	 */
	public PathReportSectionData getPrimarySectionData() {
		return _primarySection;
	}

	/**
	 * Sets the primary path report section data bean for the primary path 
	 * report section for this path report consent.
	 * 
	 * @param  primarySection  the PathReportSectionData bean for the primary
	 * 													section of this path report
	 */
	public void setPrimarySectionData(PathReportSectionData primarySection) {
		_primarySection = primarySection;
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
