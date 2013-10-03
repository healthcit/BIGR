package com.ardais.bigr.pdc.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;

/**
 * This class represents the data for a pathology report diagnostic.
 */
public class PathReportDiagnosticData implements Serializable {
  private String _diagnostic;
  private String _note;	
  private String _pathReportId;
  private String _result;
  private String _type;
  private Integer _id;
  private String _consentId;
/**
 * Creates a new <code>PathReportDiagnosticData</code>.
 */
public PathReportDiagnosticData() {
	super();
}
  
/**
 * Creates a new <code>PathReportDiagnosticData</code>, initialized from
 * the data in the PathReportDiagnosticData passed in.
 *
 * @param  pathReportDiagnosticData  the <code>PathReportDiagnosticData</code>
 */
public PathReportDiagnosticData(PathReportDiagnosticData pathReportDiagnosticData) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, pathReportDiagnosticData);
}

/**
 * Creates a new <code>PathReportDiagnosticData</code>, initialized from
 * the data in the current row of the result set.
 */
public PathReportDiagnosticData(ResultSet rs) {
    this();
    try {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        HashMap lookup = new HashMap();

        for (int i = 0; i < columnCount; i++) {
            lookup.put(meta.getColumnName(i + 1).toLowerCase(), null);
        }

        if (lookup.containsKey("diagnostics_concept_id"))
            setDiagnostic(rs.getString("diagnostics_concept_id"));

        if (lookup.containsKey("path_report_id"))
            setPathReportId(rs.getString("path_report_id"));

        if (lookup.containsKey("diagnostic_type"))
            setType(rs.getString("diagnostic_type"));

        if (lookup.containsKey("diagnostic_results_cid"))
            setResult(rs.getString("diagnostic_results_cid"));

        if (lookup.containsKey("diagnostic_note"))
            setNote(rs.getString("diagnostic_note"));
            
        if (lookup.containsKey("id"))
            {
            Integer theInt = new Integer(rs.getInt("id"));
            setId(theInt);  
            } 

    } catch (SQLException e) {
        throw new ApiException(e);
    }
}
/**
 * Returns the diagnostic.
 *
 * @return  The diagnostic.
 */
public String getDiagnostic() {
	return _diagnostic;
}

/**
 * Returns the id.
 *
 * @return  The id.
 */
public Integer getId() {
  return _id;
}


/**
 * Returns the diagnostic note.
 *
 * @return  The diagnostic note.
 */
public String getNote() {
	return _note;
}
/**
 * Returns the id of the path report with which this diagnostic
 * is associated.
 *
 * @return  the path report id
 */
public String getPathReportId() {
	return _pathReportId;
}
/**
 * Returns the diagnostic result.
 *
 * @return  The diagnostic result.
 */
public String getResult() {
	return _result;
}
/**
 * Returns the diagnostic type.
 *
 * @return  The diagnostic type.
 */
public String getType() {
	return _type;
}
/**
 * Sets the diagnostic.
 *
 * @param  diagnostic  the diagnostic
 */
public void setDiagnostic(String diagnostic) {
	_diagnostic = diagnostic;
}
/**
 * Sets the id.
 *
 * @param  id  the id
 */
public void setId(Integer id) {
  _id = id;
}
/**
 * Sets the diagnostic note.
 *
 * @param  note  the diagnostic note
 */
public void setNote(String note) {
	_note = note;
}
/**
 * Sets the id of the path report with which this diagnostic
 * is associated.
 *
 * @param  id  the path report id
 */
public void setPathReportId(String id) {
	_pathReportId = id;
}
/**
 * Sets the diagnostic result.
 *
 * @param  result  the diagnostic result
 */
public void setResult(String result) {
	_result = result;
}
/**
 * Sets the diagnostic type.
 *
 * @param  type  the diagnostic type
 */
public void setType(String type) {
	_type = type;
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
