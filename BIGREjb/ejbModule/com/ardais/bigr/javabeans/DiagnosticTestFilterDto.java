package com.ardais.bigr.javabeans;

import java.io.Serializable;

public class DiagnosticTestFilterDto implements Serializable {


  private String[] _testShow;
  private String[] _testResult;
  private String[] _performed;
  private String[] _diagnosticConceptId;
  
  private String _showPsa;
  private String _showDre;
 
  private boolean _showResultDiag;
  private boolean _showResultClinLab;
  private boolean _showResultClinFindings;
  

  public DiagnosticTestFilterDto() {
    super();
  }

  /**
   * @return
   */
  public boolean isShowResultDiag() {
    return _showResultDiag;
  }

  /**
   * @param b
   */
  public void setShowResultDiag(boolean b) {
    _showResultDiag = b;
  }

  public boolean isShowResultClinLab() {
    return _showResultClinLab;
  }

  public void setShowResultClinLab(boolean b) {
    _showResultClinLab = b;
  }

  public boolean isShowResultClinFindings() {
    return _showResultClinFindings;
  }

  public void setShowResultClinFindings(boolean b) {
    _showResultClinFindings = b;
  }

  /**
   * @return
   */
  public String[] getTestShow() {
    return _testShow;
  }

  /**
   * @param strings
   */
  public void setTestShow(String[] strings) {
    _testShow = strings;
  }

  /**
   * @return
   */
  public String[] getTestResult() {
    return _testResult;
  }

  /**
   * @param strings
   */
  public void setTestResult(String[] strings) {
    _testResult = strings;
  }

  /**
   * @return
   */
  public String[] getPerformed() {
    return _performed;
  }

  /**
   * @param strings
   */
  public void setPerformed(String[] strings) {
    _performed = strings;
  }

  /**
   * @return
   */
  public String[] getDiagnosticConceptId() {
    return _diagnosticConceptId;
  }

  /**
   * @param strings
   */
  public void setDiagnosticConceptId(String[] strings) {
    _diagnosticConceptId = strings;
  }

  public String getShowDre() {
    return _showDre;
  }

  public String getShowPsa() {
    return _showPsa;
  }

  public void setShowDre(String string) {
    _showDre = string;
  }

  public void setShowPsa(String string) {
    _showPsa = string;
  }

  public void clearDiagnosticTests() {
    setDiagnosticConceptId(null);
    setPerformed(null);
    setTestResult(null);
    setTestShow(null);
    setShowResultDiag(false);
  }

  public void clearClinLab() {
    setShowPsa(null);
    setShowResultClinLab(false);
  }

  public void clearClinFindings() {
    setShowDre(null);
    setShowResultClinFindings(false);
  }

}
