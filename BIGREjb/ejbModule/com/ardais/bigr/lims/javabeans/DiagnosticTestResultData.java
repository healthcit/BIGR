package com.ardais.bigr.lims.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.util.gen.DbAliases;

public class DiagnosticTestResultData implements Serializable {

  private String _testCid;
  private String _resultCid;

  public DiagnosticTestResultData() {
  }

  public DiagnosticTestResultData(DiagnosticTestResultData diagnosticTestResultData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, diagnosticTestResultData);
  }

  /**
   * Populates this <code>DiagnosticTestResultData</code> from  
   * the result set.
   * 
   * @param  columns  a <code>Map</code> containing a key for each column in
   *                   the <code>ResultSet</code>.  Each key must be one of the
   *                   column alias constants in {@link com.ardais.bigr.util.DbAliases}
   *                   and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public void populate(Map columns, ResultSet rs) {
    try {
      if (columns.containsKey(DbAliases.DIAGNOSTICS_CONCEPT_ID)) {
        setTestCid(ApiFunctions.safeString(rs.getString(DbAliases.DIAGNOSTICS_CONCEPT_ID)));
      }
      if (columns.containsKey(DbAliases.DIAGNOSTIC_RESULTS_CID)) {
        setResultCid(ApiFunctions.safeString(rs.getString(DbAliases.DIAGNOSTIC_RESULTS_CID)));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * @return
   */
  public String getResultCid() {
    return _resultCid;
  }

  /**
   * @return
   */
  public String getTestCid() {
    return _testCid;
  }

  /**
   * @param string
   */
  public void setResultCid(String string) {
    _resultCid = string;
  }

  /**
   * @param string
   */
  public void setTestCid(String string) {
    _testCid = string;
  }

}
