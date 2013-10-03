package com.ardais.bigr.library.web.form;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.filters.RepeatingSingleData;
import com.ardais.bigr.query.generator.FilterDiagnosticTest;
import com.ardais.bigr.query.generator.FilterDiagnosticTestResult;
import com.ardais.bigr.query.generator.FilterDre;
import com.ardais.bigr.query.generator.FilterDreExists;
import com.ardais.bigr.query.generator.FilterPsa;
import com.ardais.bigr.query.generator.FilterPsaExists;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.gboss.GbossValue;

public class QueryDiagnosticTestForm extends BigrActionForm {

  private static final String PSA_LOW = "CA11125C";
  private static final String PSA_SLIGHT = "CA11126C";
  private static final String PSA_MODERATE = "CA11127C";
  private static final String PSA_SIGNIFICANT = "CA11128C";

  private String[] _diagnosticConceptId;
  private String[] _diagnosticValueSet;
  private String[] _performed;
  private String[] _testResult;
  private String[] _startIndex;
  private String[] _testCount;
  private String[] _testResultHidden;
  private String[] _testShow;
  private String[] _testShowHidden;
  private String[] _performedHidden;
  
  private String _psaPerformed;
  private String[] _psaResult;
  private String _psaShow;

  private String _drePerformed;
  private String[] _dreResult;
  private String _dreShow;

  private boolean _showResultDiag;
  private boolean _showResultClinLab;
  private boolean _showResultClinFindings;
  
  public QueryDiagnosticTestForm() {
    reset();
  }

  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    reset();
  }

  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  private void reset() {
    resetDiagnosticTest();
    resetClinLab();
    resetClinFindings();
  }

  public void resetDiagnosticTest() {
    _diagnosticConceptId = null;
    _diagnosticValueSet = null;
    _performed = null;
    _testResult = null;
    _startIndex = null;
    _testCount = null;
    _testResultHidden = null;
    _testShow = null;
    _testShowHidden = null;
    _performedHidden = null;
    _showResultDiag = false;
  }

  public void resetClinLab() {
    _psaPerformed = null;
    _psaResult = null;
    _psaShow = null;
    _showResultClinLab = false;
  }

  public void resetClinFindings() {
    _drePerformed = null;
    _dreResult = null;
    _dreShow = null;
    _showResultClinFindings = false;
  }

  /**
   * Returns the map.
   * @return Map
   */
  public Map getFilters() {
    Map results = new HashMap();
    
    List concepts = new ArrayList();
    if (!ApiFunctions.isEmpty(getDiagnosticConceptId())) {
      for (int i=0; i < getDiagnosticConceptId().length; i++) {
        if (!ApiFunctions.isEmpty(getPerformedHiddenIndividual(i))) {
          concepts.add(getDiagnosticConceptIdIndividual(i));  
        }
      }
    }

    if (!concepts.isEmpty()) {
      Filter.addToMap(new FilterDiagnosticTest((String[]) concepts.toArray(new String[0])), results);
    }

    RepeatingFilterData resultData = new RepeatingFilterData();
    if (!ApiFunctions.isEmpty(getDiagnosticConceptId())) {
      for (int i=0; i < getDiagnosticConceptId().length; i++) {
        RepeatingSingleData singleData = new RepeatingSingleData(ApiFunctions.isEmpty(getTestResultIndividual(i))? null : new String[]{getTestResultIndividual(i)}, getAllTestResultCodes(i));
        singleData.setFilterName(GbossFactory.getInstance().getDescription(getDiagnosticConceptIdIndividual(i)));
        singleData.setSelectedValues(ApiFunctions.splitAndTrim(getTestResultHiddenIndividual(i), ","));
        resultData.add(singleData);    
      }
    }
    if (!resultData.isEmpty()) {
      Filter.addToMap(new FilterDiagnosticTestResult(resultData), results);
    }

    if (!ApiFunctions.isEmpty(getDrePerformed())) {
      Filter.addToMap(new FilterDreExists(), results);
    }
    else {
      Filter.addStringsEqualToMap(new FilterDre(getDreResult()), results);
    }

    if (!ApiFunctions.isEmpty(getPsaPerformed())) {
      Filter.addToMap(new FilterPsaExists(), results);
    }
    else {
      String[] psa = getPsaResult();
      if (!ApiFunctions.isEmpty(psa)) {
        int numPsas = psa.length;        
        BigDecimal[] mins = new BigDecimal[numPsas];
        BigDecimal[] maxs = new BigDecimal[numPsas];
        for (int i = 0; i < numPsas; i++) {
          if (PSA_LOW.equals(psa[i])) { 
            mins[i] = new BigDecimal(new BigInteger("0"), 2);
            maxs[i] = new BigDecimal(new BigInteger("250"), 2);
          }
          else if (PSA_SLIGHT.equals(psa[i])) {
            mins[i] = new BigDecimal(new BigInteger("251"), 2);
            maxs[i] = new BigDecimal(new BigInteger("1000"), 2);
          } 
          else if (PSA_MODERATE.equals(psa[i])) {
            mins[i] = new BigDecimal(new BigInteger("1001"), 2);
            maxs[i] = new BigDecimal(new BigInteger("2000"), 2);
          } 
          else if (PSA_SIGNIFICANT.equals(psa[i])) {
            mins[i] = new BigDecimal(new BigInteger("2001"), 2);
            maxs[i] = new BigDecimal(new BigInteger("99999"), 2);
          } 
        }
        Filter.addToMap(new FilterPsa(mins, maxs), results);
      }
    }

    return results;
  }

  public void setFilters(Map m) {
    if (m.containsKey(FilterConstants.KEY_PSA_EXISTS)) {
      setPsaPerformed("performed");
    }

    if (m.containsKey(FilterConstants.KEY_PSA)) {
      List psaResults = new ArrayList();
      FilterPsa f = (FilterPsa) m.get(FilterConstants.KEY_PSA);
      Iterator i = f.getFilters();
      if (i.hasNext()) {
        while (i.hasNext()) {
          FilterPsa subf = (FilterPsa) i.next();
          psaResults.add(getPsaFilterValueForSetFilters(subf.getMin()));             
        }
      }
      else {
        psaResults.add(getPsaFilterValueForSetFilters(f.getMin())); 
      }
      setPsaResult(ApiFunctions.toStringArray(psaResults));
    }

    if (m.containsKey(FilterConstants.KEY_DRE_EXISTS)) {
      setDrePerformed("performed");
    }

    if (m.containsKey(FilterConstants.KEY_DRE)) {
      FilterDre f = (FilterDre) m.get(FilterConstants.KEY_DRE);
      setDreResult(f.getFilterValues());
    }
  }

  private String getPsaFilterValueForSetFilters(BigDecimal min) {
    if (min.equals(new BigDecimal(new BigInteger("0"), 2))) {
      return PSA_LOW; 
    }
    else if (min.equals(new BigDecimal(new BigInteger("251"), 2))) {
      return PSA_SLIGHT; 
    }
    else if (min.equals(new BigDecimal(new BigInteger("1001"), 2))) {
      return PSA_MODERATE; 
    }
    else if (min.equals(new BigDecimal(new BigInteger("2001"), 2))) {
      return PSA_SIGNIFICANT; 
    }
    else {
      throw new ApiException("In QueryDiagnosticTestForm.getPsaFilterValueForSetFilters: Unexpected min value encountered: " + min.toString());
    }
  }

  public Iterator getCellProliferationList() {
    return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_CELL_PROLIFERATION).getValues().iterator();
  }

  public Iterator getImmunoHistoChemistryList() {
    return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOHISTOCHEMISTRY).getValues().iterator();
  }

  public Iterator getGenesList() {
    return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_GENES).getValues().iterator();
  }

  public Iterator getImmunoPhenoTypeList() {
    return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_IMMUNOPHENOTYPE).getValues().iterator();
  }

  public DiagnosticTestFilterDto describeIntoDto(HttpServletRequest request, String txType) {
    HttpSession session = request.getSession(false);
    DiagnosticTestFilterDto dto = new DiagnosticTestFilterDto();
    dto.setTestShow(getTestShowHidden());
    dto.setTestResult(getTestResultHidden());
    dto.setPerformed(getPerformedHidden());
    dto.setDiagnosticConceptId(getDiagnosticConceptId());

    dto.setShowResultDiag(isShowResultDiag());
    
    dto.setShowPsa(getPsaShow());
    dto.setShowResultClinLab(isShowResultClinLab());

    dto.setShowDre(getDreShow());
    dto.setShowResultClinFindings(isShowResultClinFindings());
    
    session.setAttribute(txType + Constants.DIAGNOSTIC_TEST_RESULT_FILTER, dto);
    return dto;  
  }

  public void populateFromDto(DiagnosticTestFilterDto dto) {
      setTestShowHidden(dto.getTestShow());
      setTestResultHidden(dto.getTestResult());
      setPerformedHidden(dto.getPerformed());

    setPsaShow(dto.getShowPsa());
    setDreShow(dto.getShowDre());
  }

  public String[] getPerformedCode(int index) {
    if (!ApiFunctions.isEmpty(getPerformedHiddenIndividual(index))) {
      return new String[] {getDiagnosticConceptIdIndividual(index)};
    }
    else {
      return null;
    }    
  }

  /**
   * @return
   */
  public boolean isShowResultDiag() {
    return _showResultDiag;
  }

  public boolean isShowResultClinLab() {
    return _showResultClinLab;
  }

  public boolean isShowResultClinFindings() {
    return _showResultClinFindings;
  }

  /**
   * @return
   */
  public String[] getDiagnosticValueSet() {
    return _diagnosticValueSet;
  }

  public String getDiagnosticValueSetIndividual(int index) {
    return _diagnosticValueSet[index];
  }

  /**
   * @return
   */
  public String[] getDiagnosticConceptId() {
    return _diagnosticConceptId;
  }

  public String getDiagnosticConceptIdIndividual(int index) {
    if (!ApiFunctions.isEmpty(_diagnosticConceptId)) {
      return _diagnosticConceptId[index];
    }
    else {
      return  "";
    }
  }

  /**
   * @param strings
   */
  public void setDiagnosticValueSet(String[] strings) {
    _diagnosticValueSet = strings;
  }

  /**
   * @param strings
   */
  public void setDiagnosticConceptId(String[] strings) {
    _diagnosticConceptId = strings;
  }

  /**
   * @return
   */
  public String[] getPerformed() {
    return _performed;
  }

  public String getPerformedHiddenIndividual(int index) {
    if (!ApiFunctions.isEmpty(_performedHidden)) {
      return ApiFunctions.safeString(_performedHidden[index]);
    }
    else {
      return "";  
    }
  }

  public String getTestResultIndividual(int index) {
    if (!ApiFunctions.isEmpty(_testResultHidden[index])) {
      return ApiFunctions.safeString(getDiagnosticConceptIdIndividual(index));
    }
    else {
      return "";  
    }
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
  public String[] getStartIndex() {
    return _startIndex;
  }

  /**
   * @param strings
   */
  public void setStartIndex(String[] strings) {
    _startIndex = strings;
  }

  /**
   * @return
   */
  public String[] getTestCount() {
    return _testCount;
  }

  /**
   * @param strings
   */
  public void setTestCount(String[] strings) {
    _testCount = strings;
  }

  /**
   * @return
   */
  public String[] getTestResultHidden() {
    return _testResultHidden;
  }

  public String getTestResultHiddenIndividual(int index) {
    if (!ApiFunctions.isEmpty(_testResultHidden)) {
      return ApiFunctions.safeString(_testResultHidden[index]);
    }
    else {
      return "";
    }
  }

  /**
   * @param strings
   */
  public void setTestResultHidden(String[] strings) {
    _testResultHidden = strings;
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
    if (!ApiFunctions.isEmpty(_testShow)) {
      _showResultDiag = true;
    }
  }

  /**
   * @return
   */
  public String[] getPerformedHidden() {
    return _performedHidden;
  }

  /**
   * @param strings
   */
  public void setPerformedHidden(String[] strings) {
    _performedHidden = strings;
  }

  /**
   * @return
   */
  public String[] getTestShowHidden() {
    return _testShowHidden;
  }

  public String getTestShowHiddenIndividual(int index) {
    if (!ApiFunctions.isEmpty(_testShowHidden)) {
      return ApiFunctions.safeString(_testShowHidden[index]);
    }
    else {
      return "";
    }
  }

  /**
   * @param strings
   */
  public void setTestShowHidden(String[] strings) {
    _testShowHidden = strings;
  }

  public String[] getAllTestResultCodes(int index) { 
    String[] testResult = ApiFunctions.splitAndTrim(getTestResultHiddenIndividual(index), ",");
    if (!ApiFunctions.isEmpty(testResult)) {
      List children = new ArrayList();
      for (int i = 0; i < testResult.length; i++) {
        Iterator iterator = BigrGbossData.getValueSet(getDiagnosticValueSetIndividual(index)).depthFirstIterator();
        GbossValue v = null;
        boolean found = false;
        while (iterator.hasNext() && !found) {
          v = (GbossValue)iterator.next();
          found = testResult[i].equalsIgnoreCase(v.getCui());
        }
        if (found) {
          Iterator concepts = v.getValues().iterator();
          while (concepts.hasNext()) {
            children.add(((GbossValue) concepts.next()).getCui());
          }
        }
      }
      return (String[]) children.toArray(new String[] {});
    }
    else {
      return null;
    }
  }

  public Iterator getDreResultList() {
      return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_DRE_RESULTS).getValues().iterator();
  }

  public String getDrePerformed() {
    return _drePerformed;
  }

  public String[] getDreResult() {
    return _dreResult;
  }

  public String getDreShow() {
    return _dreShow;
  }

  public Iterator getPsaResultList() {
      return BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_PSA_RESULTS).getValues().iterator();
  }

  public String getPsaPerformed() {
    return _psaPerformed;
  }

  public String[] getPsaResult() {
    return _psaResult;
  }

  public String getPsaShow() {
    return _psaShow;
  }

  public void setDrePerformed(String string) {
    _drePerformed = string;
  }

  public void setDreResult(String[] string) {
    _dreResult = string;
  }

  public void setDreShow(String string) {
    _dreShow = string;
    if (!ApiFunctions.isEmpty(string)) {
      _showResultClinFindings = true;
    }
  }

  public void setPsaPerformed(String string) {
    _psaPerformed = string;
  }

  public void setPsaResult(String[] string) {
    _psaResult = string;
  }

  public void setPsaShow(String string) {
    _psaShow = string;
    if (!ApiFunctions.isEmpty(string)) {
      _showResultClinLab = true;
    }
  }

}
