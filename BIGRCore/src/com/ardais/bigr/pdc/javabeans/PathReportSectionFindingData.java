package com.ardais.bigr.pdc.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;

/**
 * This class represents the data for a pathology report section additional finding.
 */
public class PathReportSectionFindingData implements Serializable {
  private String _diagnosis;
  private String _diagnosisOther;
  private String _findingId;
  private String _note;	
  private String _pathReportSectionId;
  private String _pathReportSectionName;
  private String _tissue;
  private String _tissueOther;

  private String _editUser;		//for OCE 
  private String _consentId;
/**
 * Creates a new <code>PathReportSectionFindingData</code>.
 */
public PathReportSectionFindingData() {
	super();
}

/**
 * Creates a new <code>PathReportSectionFindingData</code>, initialized from
 * the data in the PathReportSectionFindingData passed in.
 *
 * @param  pathReportSectionFindingData  the <code>PathReportSectionFindingData</code>
 */
public PathReportSectionFindingData(PathReportSectionFindingData pathReportSectionFindingData) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, pathReportSectionFindingData);
}

/**
 * Creates a new <code>PathReportSectionFindingData</code>, initialized from
 * the data in the current row of the result set.
 *
 * @param  rs  the <code>ResultSet</code>
 */
public PathReportSectionFindingData(ResultSet rs) {
    this();
    try {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        HashMap lookup = new HashMap();

        for (int i = 0; i < columnCount; i++) {
            lookup.put(meta.getColumnName(i + 1).toLowerCase(), null);
        }

        if (lookup.containsKey("finding_line_id"))
            setFindingId(rs.getString("finding_line_id"));

        if (lookup.containsKey("path_dx_concept_id"))
            setDiagnosis(rs.getString("path_dx_concept_id"));

        if (lookup.containsKey("path_tc_concept_id"))
            setTissue(rs.getString("path_tc_concept_id"));

        if (lookup.containsKey("other_path_dx"))
            setDiagnosisOther(rs.getString("other_path_dx"));

        if (lookup.containsKey("other_path_tissue"))
            setTissueOther(rs.getString("other_path_tissue"));

        if (lookup.containsKey("path_report_section_id"))
            setPathReportSectionId(rs.getString("path_report_section_id"));

        if (lookup.containsKey("finding_notes"))
            setNote(rs.getString("finding_notes"));

    } catch (SQLException e) {
        throw new ApiException(e);
    }
}
/**
 * Returns the diagnosis.
 *
 * @return  The diagnosis.
 */
public String getDiagnosis() {
	return _diagnosis;
}
/**
 * Returns the "other" diagnosis.
 *
 * @return  The "other" diagnosis.
 */
public String getDiagnosisOther() {
	return _diagnosisOther;
}
/**
 * Returns the user that edited this path report section additional finding.
 *
 * @return  The edit user.
 */
public String getEditUser() {
	return _editUser;
}
/**
 * Returns the finding id.
 *
 * @return  The finding id.
 */
public String getFindingId() {
	return _findingId;
}
/**
 * Returns the finding notes.
 *
 * @return  The finding notes.
 */
public String getNote() {
	return _note;
}
/**
 * Returns the path report section id with which this finding is associated.
 *
 * @return  The path report section id.
 */
public String getPathReportSectionId() {
	return _pathReportSectionId;
}
/**
 * Returns the tissue.
 *
 * @return  The tissue.
 */
public String getTissue() {
	return _tissue;
}
/**
 * Returns the "other" tissue.
 *
 * @return  The "other" tissue.
 */
public String getTissueOther() {
	return _tissueOther;
}
/**
 * Sets the diagnosis.
 *
 * @param  diagnosis  the diagnosis
 */
public void setDiagnosis(String diagnosis) {
	_diagnosis = diagnosis;
}
/**
 * Sets the "other" diagnosis.
 *
 * @param  diagnosis  the "other" diagnosis
 */
public void setDiagnosisOther(String diagnosis) {
	_diagnosisOther = diagnosis;
}
/**
 * Sets the user that edited this path report section additional finding.
 *
 * @param  user  the edit user
 */
public void setEditUser(String user) {
	_editUser = user;
}
/**
 * Sets the finding id.
 *
 * @param  id  the finding id
 */
public void setFindingId(String id) {
	_findingId = id;
}
/**
 * Sets the finding note.
 *
 * @param  note  the finding note
 */
public void setNote(String note) {
	_note = note;
}
/**
 * Sets the path report section id with which this finding is associated.
 *
 * @param  id  the path report section id
 */
public void setPathReportSectionId(String id) {
	_pathReportSectionId = id;
}
/**
 * Sets the tissue.
 *
 * @param  tissue  the tissue
 */
public void setTissue(String tissue) {
	_tissue = tissue;
}
/**
 * Sets the "other" tissue.
 *
 * @param  tissue  the "other" tissue
 */
public void setTissueOther(String tissue) {
	_tissueOther = tissue;
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

  /**
   * @return
   */
  public String getPathReportSectionName() {
    return _pathReportSectionName;
  }

  /**
   * @param string
   */
  public void setPathReportSectionName(String string) {
    _pathReportSectionName = string;
  }

}
