package com.ardais.bigr.query.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.ProductSummary;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.filters.ProductFilters;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.VariablePrecisionDate;
import com.ardais.bigr.util.VariablePrecisionDateMinimum;
import com.gulfstreambio.gboss.GbossValue;
import com.gulfstreambio.gboss.GbossValueSet;
import com.gulfstreambio.kc.det.DatabaseElement;
import com.gulfstreambio.kc.det.DetElement;

/**
 * Provides for specification and execution of a partial query for products.
 * Partial (summary) results are returned based on a specification of filters,
 * where partial results consist of only IDs of the requested products and 
 * related counts.  This class is intended to be used by the EJB tier to 
 * perform queries.
 *
 * @see  SampleSelectionSummary
 * @see  com.ardais.bigr.query.filters.ProductFilters
 */
public abstract class ProductSummaryQueryBuilder implements InitializableFromFilter {

  ProductSummaryQueryBuilder secondaryPsqb = null;
  String _operation = null;

  private static final String QC_STATUS_NULL_OR_GROUP = "orQcStatusNull";
  private static final String BMS_YN_OR_GROUP = "orBmsYn";

  /**
   * The security info of the user requesting the query.
   */

  private String _accountName; // the account so we can show per-account info if requested

  /**
   * The generic query builder that is used to build this summary query.
   */
  private ProductQueryBuilder _query = new ProductQueryBuilder();

  /**
   * Logger for logging performance-related items.
   */
  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  /**
   * Creates a new <code>ProductSummaryQueryBuilder</code>.
   */
  public ProductSummaryQueryBuilder() {
    super();
  }

  /**
   * Creates a new <code>ProductSummaryQueryBuilder</code>, initializing
   * it from the specified filters.
   * 
   * @param  filters  the {@link com.ardais.bigr.query.filters.ProductFilters}
   */
  public ProductSummaryQueryBuilder(ProductFilters filters) {
    this();
    if (_perfLog.isDebugEnabled())
      _perfLog.debug("  1/4: START generate tissue/RNA summary query");

    filters.update(this);
  }

  protected ProductQueryBuilder getQueryBuilder() {
    return _query;
  }

  /**
   * Returns the sample query specified by this class.  Each 
   * PreparedStatement placeholder is replaced with its actual bind variable.  
   * Thus, the returned query can be directly executed.
   *
   * @return  The SQL statement for the sample query.
   */
  public String getSummaryQuery() {

    Connection con = null;
    BigrPreparedStatement pstmt = null;
    String result = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = new BigrPreparedStatement(con, doGetSummarySql());
      doBindAllParameters(pstmt);
      result = pstmt.toString();
    }
    catch (Exception e) {
      ApiLogger.log(e);
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    return result;
  }

  /**
   * Performs the tissue sample query specified by this class and returns 
   * partial results.  Partial results include just the IDs of the matching
   * tissue samples, along with the count of matching donors, cases, ASMs, 
   * restricted samples and samples.  One or more filters must be specified 
   * before calling this method.
   *
   * @return  The {@link SampleSelectionSummary} containing the partial 
   * 			 		 results of the tissue sample query.
   */
  public SampleSelectionSummary getSummary() {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ProductSummary summary = null;
    long t0 = 0;
    try {
      String querySql = getSummaryQuery();
      con = ApiFunctions.getDbConnection();
      pstmt = new BigrPreparedStatement(con, querySql);

      if (_perfLog.isDebugEnabled()) {
        _perfLog.debug("    1: END   generate tissue summary query");
        _perfLog.debug("    2: START perform tissue summary query");
        t0 = System.currentTimeMillis();
      }

      rs = pstmt.executeQuery();

      if (_perfLog.isDebugEnabled()) {
        long elapsedTime = System.currentTimeMillis() - t0;
        _perfLog.debug("    2: END   perform tissue summary query (" + elapsedTime + " ms)");
        _perfLog.debug("    3: START process tissue query results");
        t0 = System.currentTimeMillis();
      }
      
      summary = new ProductSummary(rs);

      if (_perfLog.isDebugEnabled()) {
        long elapsedTime = System.currentTimeMillis() - t0;
        _perfLog.debug("    3: END   process tissue query results (" + elapsedTime + " ms)");
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    if (_perfLog.isDebugEnabled()) {
      t0 = System.currentTimeMillis();
      try {
        java.io.ByteArrayOutputStream bstream = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream objects = new java.io.ObjectOutputStream(bstream);
        objects.writeObject(summary);
        objects.flush();
        bstream.close();
        long elapsedTime = System.currentTimeMillis() - t0;
        _perfLog.debug(
          "    3: SIZE  size of object graph: "
            + String.valueOf(bstream.size())
            + " ("
            + elapsedTime
            + " ms)");
      }
      catch (java.io.IOException e) {
        throw new com.ardais.bigr.api.ApiException("IOException in performance code");
      }
    }

    return summary;
  }

  /**
   * return the SQL corresponding to this Query Builder, without looking at
   * the "secondary" query builder which is used in minus and intersects queries.
   */
  protected abstract String doGetSummarySingleQuerySql();
  protected abstract String doGetSummarySelectClause();
  protected abstract String doGetSummaryOrderBy();

  protected String doGetSummarySql() {
    String mainSql = doGetSummarySingleQuerySql();
    mainSql = doGetSummarySelectClause() + mainSql;

    ProductSummaryQueryBuilder soldQb = getSecondaryPsqb();

    if (soldQb != null) {
      ProductSummaryQueryBuilder holdQb = soldQb.getSecondaryPsqb();
      String soldSql = soldQb.doGetSummarySingleQuerySql();
      if (holdQb != null) {
        String holdSql = holdQb.doGetSummarySingleQuerySql();
        mainSql = mainSql + ' ' + _operation + " (";
        mainSql = mainSql + soldSql;
        mainSql = mainSql + ' ' + soldQb._operation + ' ';
        mainSql = mainSql + holdSql + ")";
      }
    }
    mainSql = mainSql + " \r\n";
    mainSql = mainSql + doGetSummaryOrderBy();
    mainSql = mainSql + " \r\n";
  
    return mainSql;
  }

  private void doBindAllParameters(BigrPreparedStatement ps) throws SQLException {
    getQueryBuilder().bindAllParameters(ps);
    ProductSummaryQueryBuilder soldQb = getSecondaryPsqb();
    if (soldQb != null) {
      soldQb.getQueryBuilder().bindAllParameters(ps);
      ProductSummaryQueryBuilder holdQb = soldQb.getSecondaryPsqb();
      if (holdQb != null) {
        holdQb.getQueryBuilder().bindAllParameters(ps);
      }
    }
  }

  public void addFilterAgeAtCollection(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_AGE_AT_COLLECTION, new String[] { from, to });
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }


  /**
   * Adds the ASM tissue filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterAsmTissue(String[] tissueCodes, String orGroupCode) {
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_ASM_TISSUE, tissueCodes);
    addJoinSampleAsm();
  }

  /**
   * Adds the not ASM tissue filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterAsmTissueNot(String[] tissueCodes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_ASM_TISSUE_NOT, tissueCodes);
    addJoinSampleAsm();
  }

  /**
   * Adds the "best" case diagnosis filter for the specified diagnoses.
   * 
   * @param  diagnosisCodes  the diagnosis codes for the desired diagnoses
   */
  public void addFilterBestCaseDiagnosis(String[] diagnosisCodes, String orGroupCode) {
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_BEST_CASE_DX, diagnosisCodes);
    // join both consent and path, because we use "best" value via NVL() operator (path, then consent)
    // if no path record, path value will be null, and we'll use ASM value

    //MR7468 - Tissue of finding can be found in ILTDS_SAMPLE.
    //MR7468 - Best case diagnosis can be found in ILTDS_INFORMED_CONSENT.
    addJoinSampleConsent();
  }

  /**
   * Adds the "best" case diagnosis like filter for the specified string.
   * 
   * @param  likeString  the string to use in the LIKE query.  This string must
   * 											have the appropriate SQL wildcard characters 
   * 											(% and/or _)
   */
  public void addFilterBestCaseDiagnosisLike(String likeString, String orGroupCode) {
    String[] heirarchyCodes = matchingDiagnosisCodes(likeString);
    getQueryBuilder().addFilterOr(
      orGroupCode,
      FilterMetaData.KEY_BEST_CASE_DX_LIKE,
      heirarchyCodes);

    //MR7468 - Tissue of finding can be found in ILTDS_SAMPLE.
    //MR7468 - Best case diagnosis can be found in ILTDS_INFORMED_CONSENT.
    addJoinSampleConsent();
    //        getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_BEST_CASE_DX_LIKE);
    //        getQueryBuilder().addTable(TableMetaData.KEY_TABLE_HIERARCHY_CASE_DX, new String[] { likeString.toUpperCase()});
    //        addJoinAsmConsent();
    //        addJoinPathConsentOuter();
  }

  public void addFilterCompositionAcellularStroma(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_TAS, new String[] { from, to });
    addJoinLimsPeSample();
  }

  public void addFilterCompositionCellularStroma(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_TCS, new String[] { from, to });
    addJoinLimsPeSample();
  }

  public void addFilterCompositionLesion(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_LSN, new String[] { from, to });
    addJoinLimsPeSample();
  }

  public void addFilterCompositionNecrosis(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_NEC, new String[] { from, to });
    addJoinLimsPeSample();
  }

  public void addFilterCompositionNormal(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_NRM, new String[] { from, to });
    addJoinLimsPeSample();
  }

  public void addFilterCompositionTumor(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_TMR, new String[] { from, to });
    addJoinLimsPeSample();
  }

  /**
   * Adds the case/consent id filter for the specified case ids.
   * 
   * @param  consentIds  the case ids for the desired cases
   */
  public void addFilterConsentEqual(String[] consentIds, String orGroupCode) {
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_SAMPLE_CONSENT_ID, consentIds);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Adds the case/consent linked filter.
   * 
   * @param  consentIds  the case ids for the desired cases
   */
  public void addFilterConsentLinked(String linked) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_CONSENT_LINKED, linked);
    addJoinSampleConsent();
  }

  public void addFilterConsentNotPulled() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_CONSENT_NOT_PULLED);
    addJoinSampleConsent();
  }

  public void addFilterConsentNotPulledOrBmsYNYes() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_CONSENT_NOT_PULLED_OR_BMS_YN_YES);
    addJoinSampleConsent();
  }

  public void addFilterConsentNotRevoked() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_CONSENT_NOT_REVOKED);
    addJoinSampleConsent();
  }

  public void addFilterConsentNotRevokedOrBmsYNYes() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_CONSENT_NOT_REVOKED_OR_BMS_YN_YES);
    addJoinSampleConsent();
  }

  /**
   * Adds the DDC diagnosis filter for the specified diagnoses.  All diagnosis
   * codes will be ORed in the query, and this filter will be ORed with the 
   * DDC diagnosis like filter if specified.
   * 
   * @param  diagnosisCodes  the diagnosis codes for the desired diagnoses
   */
  public void addFilterDdcDiagnosis(String[] diagnosisCodes, String groupCode) {
    getQueryBuilder().addFilterOr(groupCode, FilterMetaData.KEY_PATH_DX, diagnosisCodes);
    addJoinPathSample();
   
  }

  public void addFilterDdcDiagnosisNot(String diagnosisCode) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_PATH_DX_NOT, diagnosisCode);
    addJoinPathSample();
    
  }

  public void addFilterDdcDiagnosisExists() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_PATH_DX_EXISTS);
    addJoinPathSample();
  
  }

  public void addFilterDdcDiagnosisExistsOrBmsYNYes() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_PATH_DX_EXISTS_OR_BMS_YN_YES);
    addJoinPathConsentOuter();
   
  }

  /**
   * Adds the DDC diagnosis like filter for the specified string.  This filter
   * will be ORed with the DDC diagnosis filter if specified.
   * 
   * @param  likeString  the string to use in the LIKE query.  This string 
   * 											must have the appropriate SQL wildcard characters 
   * 											(% and/or _)
   */
  public void addFilterDdcDiagnosisLike(String likeString, String orGroupCode) {
    String[] matchingDiagnosisCodes = matchingDiagnosisCodes(likeString);
    getQueryBuilder().addFilterOr(
      orGroupCode,
      FilterMetaData.KEY_PATH_DX_LIKE,
      matchingDiagnosisCodes);
    addJoinPathSample();
   
  }

  /**
   * Adds the distant metastasis filter for the specified distant metastasis
   * value.
   * 
   * @param  distantMetastasis  the distant metastasis code
   */
  public void addFilterDistantMetastasis(String[] distantMetastasis) {
    GbossValueSet valueSet = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_DISTANT_METASTASIS);
    List children = new ArrayList();
    for (int i = 0; i < distantMetastasis.length; i++) {
      Iterator iterator = valueSet.getValues().iterator();
      GbossValue v = null;
      boolean found = false;
      while (iterator.hasNext() && !found) {
        v = (GbossValue)iterator.next();
        found = distantMetastasis[i].equalsIgnoreCase(v.getCui());
      }
      if (found) {
        Iterator concepts = v.getValues().iterator();
        while (concepts.hasNext()) {
          children.add(((GbossValue) concepts.next()).getCui());
        }
      }
    }

    if (!children.isEmpty()) {
      getQueryBuilder()
        .addFilter(FilterMetaData.KEY_SECTION_METASTASIS, (String[]) children.toArray(new String[] {
      }));
      addJoinPathSection();
    }
  }

  /**
   * Adds the donor id filter for the specified donor ids.
   * 
   * @param  sampleIds  the sample ids for the desired samples
   */
  public void addFilterDonorEqual(String[] donorIds, String orGroupCode) {
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_SAMPLE_DONOR_ID, donorIds);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Adds the donor id filter for the specified donor ids.
   * 
   * @param  sampleIds  the sample ids for the desired samples
   */
  public void addFilterSampleDateReceived(String[] params) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_DATE_RECEIVED, params);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleDateOfCollection(String[] params) {
	    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_DATE_COLLECTED, params);
	    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
	  }
  
  public void addFilterGender(String gender) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_DONOR_GENDER, gender);
    addJoinSampleDonor();
  }

  /**
   * Adds the histologic/nuclear grade filter for the specified 
   * histologic/nuclear grade value.
   * 
   * @param  hng  the histologic/nuclear grade code
   */
  public void addFilterHistologicNuclearGrade(String[] hng) {
    GbossValueSet valueSet = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_HIST_NUCLEAR_GRADE);
    List children = new ArrayList();
    for (int i = 0; i < hng.length; i++) {
      Iterator iterator = valueSet.getValues().iterator();
      GbossValue v = null;
      boolean found = false;
      while (iterator.hasNext() && !found) {
        v = (GbossValue)iterator.next();
        found = hng[i].equalsIgnoreCase(v.getCui());
      }
      if (found) {
        Iterator concepts = v.getValues().iterator();
        while (concepts.hasNext()) {
          children.add(((GbossValue) concepts.next()).getCui());
        }
      }
    }

    if (!children.isEmpty()) {
      getQueryBuilder()
        .addFilter(FilterMetaData.KEY_SECTION_HNG, (String[]) children.toArray(new String[] {
      }));
      addJoinPathSection();
    }
  }

  public void addFilterDiagnosticTest(String[] tests, String orGroupCode) {
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_DIAGNOSTIC_TEST, tests);
    addJoinPathDiagnostics();
    
  }

  public void addFilterDiagnosticTestResult(RepeatingFilterData repeatingValues, String orGroupCode) {
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_DIAGNOSTIC_TEST_RESULT, repeatingValues);
    addJoinPathDiagnostics();
   
  }

  /**
   * Add a filter specifiying the ids of the samples, of whatever product type
   */
  public abstract void addFilterIDsEqual(String[] rnaIds, String orGroupCode);

  /**
   * Add a filter specifying the logical repositories to which the items must belong.  Method
   * is abstract because tissue and rna have different means of implementation.
   */
  public abstract void addFilterLogicalRepository(RepeatingFilterData repositories);

  /**
   * Adds the ILTDS diagnosis filter for the specified diagnoses.  All diagnosis
   * codes will be ORed in the query, and this filter will be ORed with the 
   * ILTDS diagnosis like filter if specified.
   * 
   * @param  diagnosisCodes  the diagnosis codes for the desired diagnoses
   */
  public void addFilterIltdsDiagnosis(String[] diagnosisCodes, String orGroupCode) {
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_CONSENT_DX, diagnosisCodes);
    addJoinSampleConsent();
  }

  /**
   * Adds the ILTDS diagnosis like filter for the specified string.  This filter
   * will be ORed with the ILTDS diagnosis filter if specified.
   * 
   * @param  likeString  the string to use in the LIKE query.  This string must
   * 											have the appropriate SQL wildcard characters 
   * 											(% and/or _)
   */
  public void addFilterIltdsDiagnosisLike(String likeString, String orGroupCode) {
    String[] matchingDiagnosisCodes = matchingDiagnosisCodes(likeString);
    getQueryBuilder().addFilterOr(
      orGroupCode,
      FilterMetaData.KEY_CONSENT_DX_LIKE,
      matchingDiagnosisCodes);
    addJoinSampleConsent();
    //        getQueryBuilder().addJoinOr(orGroupCode, FilterMetaData.KEY_JOIN_CONSENT_HIERARCHY_CASE_PE_DX);
    //        getQueryBuilder().addTable(TableMetaData.KEY_TABLE_HIERARCHY_CASE_DX, new String[] { likeString.toUpperCase()});
    //        addJoinAsmConsent();
  }

  public void addFilterInArdaisRepository() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_IN_ARDAIS_REPOSITORY);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterInArdaisRepositoryOrBmsYNYes() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_IN_ARDAIS_REPOSITORY_OR_BMS_YN_YES);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Adds the LIMS diagnosis filter for the specified diagnoses.  All diagnosis
   * codes will be ORed in the query, and this filter will be ORed with the 
   * LIMS diagnosis like filter if specified.
   * 
   * @param  diagnosisCodes  the diagnosis codes for the desired diagnoses
   */
  public void addFilterLimsDiagnosis(String[] diagnosisCodes, String orGroupCode) {
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_LIMS_PE_DX, diagnosisCodes);
    addJoinLimsPeSample();
  }

  public void addFilterLimsDiagnosisNot(String diagnosisCode) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_DX_NOT, diagnosisCode);
    //addJoinLimsPeSample();
  }

  /**
   * Adds the LIMS diagnosis like filter for the specified string.  This filter
   * will be ORed with the LIMS diagnosis filter if specified.
   * 
   * @param  likeString  the string to use in the LIKE query.  This string must
   * 											have the appropriate SQL wildcard characters 
   * 											(% and/or _)
   */
  public void addFilterLimsDiagnosisLike(String likeString, String orGroupCode) {
    String[] matchingDxs = matchingDiagnosisCodes(likeString);
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_LIMS_PE_DX_LIKE, matchingDxs);
    addJoinLimsPeSample();
    //        addJoinLimsPeSample();
    //        getQueryBuilder().addTable(
    //            TableMetaData.KEY_TABLE_HIERARCHY_LIMS_PE_DX,
    //            new String[] { likeString.toUpperCase()});
    //        getQueryBuilder().addJoinOr(orGroupCode, FilterMetaData.KEY_JOIN_LIMS_PE_HIERARCHY_LIMS_PE_DX);
  }

  /**
   * Adds the LIMS tissue of finding filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterLimsTissueFinding(String[] tissueCodes, String orGroupCode) {
    getQueryBuilder().addFilterOr(
      orGroupCode,
      FilterMetaData.KEY_LIMS_PE_TISSUE_FINDING,
      tissueCodes);
    addJoinLimsPeSample();
  }

  /**
   * Adds the tissue of finding filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterTissueFinding(String[] tissueCodes, String orGroupCode) {
    getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_TISSUE_FINDING, tissueCodes);
    //MR7467 - remove joins.
    // join both asm, lims, because will use "best" via NVL() operator on Lims tissue, then ASM tissue
    //addJoinSampleAsm();
    //addJoinLimsPeSampleOuter(); // join sample to lims AND filter for reported rows
  }

  /**
   * Adds the not tissue of finding like filter for the specified string.
   * 
   * @param  likeString  the string to use in the LIKE query.  This string 
   * 											must have the appropriate SQL wildcard characters 
   * 											(% and/or _)
   */
  public void addFilterTissueFindingLike(String like, String orGroupCode) {
    String[] matchingTissueCodes = matchingTissueCodes(like);
    getQueryBuilder().addFilterOr(
      orGroupCode,
      FilterMetaData.KEY_TISSUE_FINDING_LIKE,
      matchingTissueCodes);
    //MR7467 - remove joins.
    // join both asm, lims, because will use "best" via NVL() operator on Lims tissue, then ASM tissue
    //addJoinSampleAsm();
    //addJoinLimsPeSampleOuter(); // join sample to lims AND filter for reported rows
  }

  /**
   * Adds the not LIMS tissue of finding filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterLimsTissueFindingNot(String[] tissueCodes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_TISSUE_FINDING_NOT, tissueCodes);
    addJoinLimsPeSample();
  }

  /**
  * Adds the not LIMS tissue of finding filter for the specified tissues.
  * 
  * @param  tissueCodes  the tissue codes for the desired tissues
  */
  public void addFilterTissueFindingNot(String[] tissueCodes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_TISSUE_FINDING_NOT, tissueCodes);
    //MR7467 - remove joins.
    // join both asm, lims, because will use "best" via NVL() operator on Lims tissue, then ASM tissue
    //addJoinSampleAsm();
    //addJoinLimsPeSampleOuter(); // use method, not individual joins
  }

  /**
   * Adds the tissue of finding like filter for the specified string.
   * 
   * @param  likeString  the string to use in the LIKE query.  This string 
   * 											must have the appropriate SQL wildcard characters 
   * 											(% and/or _)
   */
  public void addFilterTissueFindingNotLike(String like) {
    String[] matchingTissueCodes = matchingTissueCodes(like);
    getQueryBuilder().addFilter(FilterMetaData.KEY_TISSUE_FINDING_NOT_LIKE, matchingTissueCodes);
    //MR7467 - remove joins.
    // join both asm, lims, because will use "best" via NVL() operator on Lims tissue, then ASM tissue
    //addJoinSampleAsm();
    //addJoinLimsPeSampleOuter(); // use method, not individual joins
  }

  /**
   * Adds the LIMS tissue of origin filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterLimsTissueOrigin(String[] tissueCodes, String orGroupCode) {
    getQueryBuilder().addFilterOr(
      orGroupCode,
      FilterMetaData.KEY_LIMS_PE_TISSUE_ORIGIN,
      tissueCodes);
    addJoinLimsPeSample();
  }

  /**
   * Adds the tissue of origin filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterTissueOrigin(String[] tissueCodes, String orCodeGroup) {
    getQueryBuilder().addFilterOr(orCodeGroup, FilterMetaData.KEY_TISSUE_ORIGIN, tissueCodes);
  }

  /**
   * Adds the tissue of origin like filter for the specified string.
   * 
   * @param  likeString  the string to use in the LIKE query.  This string 
   *                                          must have the appropriate SQL wildcard characters 
   *                                          (% and/or _)
   */
  public void addFilterTissueOriginLike(String likeString, String orGroupCode) {
    String[] matchingTissues = matchingTissueCodes(likeString);
    getQueryBuilder().addFilterOr(
      orGroupCode,
      FilterMetaData.KEY_TISSUE_ORIGIN_LIKE,
      matchingTissues);
    //        getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE_PRODUCT);
    //        getQueryBuilder().addTable(TableMetaData.KEY_TABLE_PRODUCT);
    //          getQueryBuilder().addTable(TableMetaData.KEY_TABLE_HIERARCHY_PRODUCT_TISSUE, new String[] { likeString.toUpperCase()});
    //          getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_JOIN_PRODUCT_PRODUCT_TISSUE);
  }

  /**
   * Adds the not LIMS tissue of origin filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterLimsTissueOriginNot(String[] tissueCodes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_TISSUE_ORIGIN_NOT, tissueCodes);
    addJoinLimsPeSample();
  }

  /**
   * Adds the not LIMS tissue of origin filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterLimsTissueOriginNotOuter(String[] tissueCodes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_TISSUE_ORIGIN_NOT, tissueCodes);
    addJoinLimsPeSampleOuter();
  }

  /**
   * Adds the not tissue of origin filter for the specified tissues.
   * 
   * @param  tissueCodes  the tissue codes for the desired tissues
   */
  public void addFilterTissueOriginNot(String[] tissueCodes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_TISSUE_ORIGIN_NOT, tissueCodes);
  }

  /**
   * Adds the not tissue of origin like filter for the specified string.
   * 
   * @param  likeString  the string to use in the LIKE query.  This string 
   * 											must have the appropriate SQL wildcard characters 
   * 											(% and/or _)
   */
  public void addFilterTissueOriginNotLike(String likeString) {
    String[] matchingTissues = matchingTissueCodes(likeString);
    getQueryBuilder().addFilter(FilterMetaData.KEY_TISSUE_ORIGIN_NOT_LIKE, matchingTissues);
    //        getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE_PRODUCT);
    //        getQueryBuilder().addTable(TableMetaData.KEY_TABLE_PRODUCT);
    //    		getQueryBuilder().addTable(TableMetaData.KEY_TABLE_HIERARCHY_PRODUCT_TISSUE_NOT, new String[] { likeString.toUpperCase()});
    //    		getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_PRODUCT_PRODUCT_TISSUE);
  }

  /**
   * Adds the lymph node stage filter for the specified lymph node stage value.
   * 
   * @param  stage  the lymph node stage code
   */
  public void addFilterLymphNodeStage(String[] stage) {
    GbossValueSet valueSet = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_LYMPH_NODE_STAGE_DESC);
    List children = new ArrayList();
    for (int i = 0; i < stage.length; i++) {
      Iterator iterator = valueSet.getValues().iterator();
      GbossValue v = null;
      boolean found = false;
      while (iterator.hasNext() && !found) {
        v = (GbossValue)iterator.next();
        found = stage[i].equalsIgnoreCase(v.getCui());
      }
      if (found) {
        Iterator concepts = v.getValues().iterator();
        while (concepts.hasNext()) {
          children.add(((GbossValue) concepts.next()).getCui());
        }
      }
    }

    if (!children.isEmpty()) {
      getQueryBuilder()
        .addFilter(
          FilterMetaData.KEY_SECTION_LYMPH_STAGE,
          (String[]) children.toArray(new String[] {
      }));
      addJoinPathSection();
    }
  }

  public void addFilterMiRestricted(String account, String orGrpCode) {
    getQueryBuilder().addFilterOr(orGrpCode, FilterMetaData.KEY_SAMPLE_RESTRICTED_ACCOUNT, account);
    addFilterUnrestricted(orGrpCode);
  }

  public void addFilterPathNotesContains(String keyword) {
    String[] keywords = { keyword, keyword, keyword };
    getQueryBuilder().addFilter(FilterMetaData.KEY_PATH_NOTES_CONTAINS, keywords);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterAsciiReportContains(String keyword) {
    String[] keywords = { keyword };
    getQueryBuilder().addFilter(FilterMetaData.KEY_PATH_ASCII_REPORT_CONTAINS, keywords);
    addJoinPathSample();
   
  }

  public void addFilterRestricted(String orGrpCode) {
    getQueryBuilder().addFilterOr(orGrpCode, FilterMetaData.KEY_SAMPLE_RESTRICTED);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterUnrestricted(String orGrpCode) {
    getQueryBuilder().addFilterOr(orGrpCode, FilterMetaData.KEY_SAMPLE_UNRESTRICTED);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterDonorAliasLike(String[] patterns, String orGroupCode) {
    if (!ApiFunctions.isEmpty(patterns)) {
      getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_DONOR_ALIAS_ID, patterns);
      addJoinSampleDonor();
    }
  }

  public void addFilterConsentAliasLike(String[] patterns, String orGroupCode) {
    if (!ApiFunctions.isEmpty(patterns)) {
      getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_CONSENT_ALIAS_ID, patterns);
      addJoinSampleConsent();
    }
  }

  public void addFilterSampleAliasLike(String[] patterns, String orGroupCode) {
    if (!ApiFunctions.isEmpty(patterns)) {
      getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_SAMPLE_ALIAS_ID, patterns);
      getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
    }
  }

  public void addFilterSampleAccount(String account) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_ACCOUNT, account);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleAppearanceBest(String[] appearances) {
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_APPEARANCE_BEST, appearances);
  }

  public void addFilterSampleAppearanceBestNot(String[] appearances) {
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_APPEARANCE_BEST_NOT, appearances);
  }

  public void addFilterSampleInvStatusNot(String[] codes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_INV_STATUS_NOT, codes);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleInvStatusNotOrBmsYNYes(String[] codes) {
    getQueryBuilder().addFilterOr(BMS_YN_OR_GROUP, FilterMetaData.KEY_SAMPLE_INV_STATUS_NOT, codes);
    getQueryBuilder().addFilterOr(BMS_YN_OR_GROUP, FilterMetaData.KEY_SAMPLE_BMS_YN_YES);

    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleNotInProject() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_NOT_IN_PROJECT);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleNotInProjectOrBmsYNYes() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_NOT_IN_PROJECT_OR_BMS_YN_YES);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleNotPulled() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_NOT_PULLED);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleReceivedAtArdais() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_RECEIVED_AT_ARDAIS);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleSalesStatusExists() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_SALES_STATUS_EXISTS);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleSalesStatus(String[] codes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_SALES_STATUS, codes);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleSalesStatusNot(String[] codes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_SALES_STATUS_NOT, codes);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleQcStatus(String[] codes) {
    getQueryBuilder().addFilterOr(QC_STATUS_NULL_OR_GROUP, FilterMetaData.KEY_SAMPLE_QC_STATUS, codes);
    getQueryBuilder().addFilterOr(QC_STATUS_NULL_OR_GROUP, FilterMetaData.KEY_SAMPLE_QC_STATUS_IS_NULL);

    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleGenreleased() {
    addFilterSampleSalesStatus(new String[] { FormLogic.SMPL_GENRELEASED });
  }

  public void addFilterSampleGenreleasedOrNotNullAndBmsYNYes() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_SALES_STATUS_OR_NOT_NULL_AND_BMS_YN_YES, new String[] { FormLogic.SMPL_GENRELEASED });
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterSampleNotOnHold() {
    addFilterSampleSalesStatusNot(new String[] { FormLogic.SMPL_ADDTOCART });
  }

  public void addFilterSampleNotRndRequest() {
    addFilterSampleInvStatusNot(new String[] { FormLogic.SMPL_RNDREQUEST });
  }

  public void addFilterSampleQcInProcess() {
    addFilterSampleQcStatus(new String[] { FormLogic.SMPL_QCINPROCESS });
  }

  public void addFilterSampleQcInProcessNot() {
    addFilterSampleQcStatus(new String[] { FormLogic.SMPL_QCAWAITING, FormLogic.SMPL_QCVERIFIED });
  }

  public void addFilterSamplePathVerifiedStatus(String pvStatus) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_SAMPLE_QCVERIFIED, pvStatus);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Adds the stage grouping filter for the specified stage grouping value.
   * 
   * @param  grouping  the stage grouping code
   */
  public void addFilterStageGrouping(String[] grouping) {
    GbossValueSet valueSet = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_STAGE_GROUPINGS);
    List children = new ArrayList();
    for (int i = 0; i < grouping.length; i++) {
      Iterator iterator = valueSet.getValues().iterator();
      GbossValue v = null;
      boolean found = false;
      while (iterator.hasNext() && !found) {
        v = (GbossValue)iterator.next();
        found = grouping[i].equalsIgnoreCase(v.getCui());
      }
      if (found) {
        Iterator concepts = v.getValues().iterator();
        while (concepts.hasNext()) {
          children.add(((GbossValue) concepts.next()).getCui());
        }
      }
    }

    if (!children.isEmpty()) {
      getQueryBuilder()
        .addFilter(
          FilterMetaData.KEY_SECTION_STAGE_GROUPING,
          (String[]) children.toArray(new String[] {
      }));
      addJoinPathSection();
    }
  }

  /**
   * Adds the tumor stage filter for the specified tumor stage value.
   * 
   * @param  stage  the tumor stage code
   */
  public void addFilterTumorStage(String[] stage) {
    GbossValueSet valueSet = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_SS_SRCH_TUMOR_STAGE_DESC);
    List children = new ArrayList();
    for (int i = 0; i < stage.length; i++) {
      Iterator iterator = valueSet.getValues().iterator();
      GbossValue v = null;
      boolean found = false;
      while (iterator.hasNext() && !found) {
        v = (GbossValue)iterator.next();
        found = stage[i].equalsIgnoreCase(v.getCui());
      }
      if (found) {
        Iterator concepts = v.getValues().iterator();
        while (concepts.hasNext()) {
          children.add(((GbossValue) concepts.next()).getCui());
        }
      }
    }

    if (!children.isEmpty()) {
      getQueryBuilder()
        .addFilter(
          FilterMetaData.KEY_SECTION_TUMOR_STAGE,
          (String[]) children.toArray(new String[] {
      }));
      addJoinPathSection();
    }
  }

  public void addFilterUserSoldCases(String key) {
    addJoinSampleSample1();
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_ORDER);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_ORDER_LINE);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE1_ORDER);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_ARDAIS_USER);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_LINE_ORDER);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_ORDER_USER);

    getQueryBuilder().addFilter(FilterMetaData.KEY_HOLD_SOLD_USER, key);

  }

  public void addFilterAccountSoldCases(String key) {
    addJoinSampleSample1();

    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_ORDER);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_ORDER_LINE);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE1_ORDER);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_ARDAIS_USER);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_LINE_ORDER);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_ORDER_USER);

    getQueryBuilder().addFilter(FilterMetaData.KEY_HOLD_SOLD_ACCOUNT, key);

  }

  public void addFilterUserHoldCases(String key) {
    addJoinSampleSample1();

    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SHOPPING_CART_DETAIL);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE1_CART_DETAIL);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_ARDAIS_USER);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_CART_USER);

    getQueryBuilder().addFilter(FilterMetaData.KEY_HOLD_SOLD_USER, key);

  }

  public void addFilterAccountHoldCases(String key) {
    addJoinSampleSample1();

    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SHOPPING_CART_DETAIL);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE1_CART_DETAIL);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_ARDAIS_USER);
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_CART_USER);

    getQueryBuilder().addFilter(FilterMetaData.KEY_HOLD_SOLD_ACCOUNT, key);

  }

  public void addFilterDreExists() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_CONSENT_DRE_CID_EXISTS);
    addJoinSampleConsent();
  }

  public void addFilterDre(String[] codes) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_CONSENT_DRE_CID, codes);
    addJoinSampleConsent();
  }

  public void addFilterPsaExists() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_CONSENT_PSA_EXISTS);
    addJoinSampleConsent();
  }

  public void addFilterPsa(String[] mins, String[] maxs) {
    if (mins.length != maxs.length) {
      throw new ApiException("Attempt to addFilterPsa with different numbers of mins (" + String.valueOf(mins.length) + ") and maxs (" + String.valueOf(maxs.length) + ") arguments.");
    }
    String[] ranges = new String[mins.length + maxs.length];
    for (int i = 0; i < mins.length; i++) {
      ranges[i*2] = mins[i];
      ranges[i*2+1] = maxs[i];
    }
    getQueryBuilder().addFilter(FilterMetaData.KEY_CONSENT_PSA, ranges);
    addJoinSampleConsent();
  }

  public void addFilterKcComparison(DetElement element, 
                                    String domainObjectType, 
                                    String operator, 
                                    String value) {
    addFilterKcComparison(element, domainObjectType, operator, value, null);
  }
  
  public void addFilterKcComparison(DetElement element, 
                                    String domainObjectType, 
                                    String operator, 
                                    String value,
                                    String orGroup) {
    addFilterKcComparison(element, domainObjectType, operator, new String[] {value}, orGroup);
  }
  
  public void addFilterKcComparison(DetElement element, 
                                    String domainObjectType, 
                                    String operator, 
                                    String[] values) {
    addFilterKcComparison(element, domainObjectType, operator, values, null);
  }
  
  public void addFilterKcComparison(DetElement element, 
                                    String domainObjectType, 
                                    String operator, 
                                    String[] values,
                                    String orGroup) {
    String filterKey = getFilterKeyKcComparison(element, domainObjectType, operator);
    if (element.isDatatypeDate() || element.isDatatypeVpd()) {
      if (orGroup == null) {
        getQueryBuilder().addFilter(filterKey, convertKcDateValues(values));
      }
      else {
        getQueryBuilder().addFilterOr(orGroup, filterKey, convertKcDateValues(values));
      }
    }
    else {
      if (orGroup == null) {
        getQueryBuilder().addFilter(filterKey, values);
      }
      else {
        getQueryBuilder().addFilterOr(orGroup, filterKey, values);
      }
    }
  }
  
  private String getFilterKeyKcComparison(DetElement element, 
                                          String domainObjectType, 
                                          String operator) {
    if (!KcQueryOperators.isValidComparisonOperator(operator)) {
      throw new ApiException("Attempt to add comparison filter with non-comparison operator (" + operator + ").");      
    }
    DatabaseElement dbElement = element.getDatabaseElement();
    if (dbElement.isTableEav()) {
      addFilterKcEavElement(element, domainObjectType);
    }
    addJoinKcForm(element, domainObjectType);
    return ProductQueryMetaDataKc.getFilterKey(element, operator);
  }
  
  public void addFilterKcRange(DetElement element, 
                               String domainObjectType, 
                               String operator, 
                               String[] values) {
    addFilterKcRange(element, domainObjectType, operator, values, null);
  }

  public void addFilterKcRange(DetElement element, 
                               String domainObjectType, 
                               String operator, 
                               String[] values,
                               String orGroup) {
    if (!KcQueryOperators.isValidRangeOperator(operator)) {
      throw new ApiException("Attempt to add range filter with non-range operator (" + operator + ").");      
    }
    DatabaseElement dbElement = element.getDatabaseElement();
    String filterKey = ProductQueryMetaDataKc.getFilterKey(element, operator);
    if (element.isDatatypeDate() || element.isDatatypeVpd()) {
      if (orGroup == null) {
        getQueryBuilder().addFilter(filterKey, convertKcDateValues(values));        
      }
      else {
        getQueryBuilder().addFilterOr(orGroup, filterKey, convertKcDateValues(values));        
      }
    }
    else {
      if (orGroup == null) {
        getQueryBuilder().addFilter(filterKey, values);        
      }
      else {
        getQueryBuilder().addFilterOr(orGroup, filterKey, values);        
      }
    }
    if (dbElement.isTableEav()) {
      addFilterKcEavElement(element, domainObjectType);
    }
    addJoinKcForm(element, domainObjectType);
  }

  public void addFilterKcAny(DetElement element, String domainObjectType) {
    DatabaseElement dbElement = element.getDatabaseElement();
    String filterKey = ProductQueryMetaDataKc.getFilterKey(element, KcQueryOperators.ANY);
    getQueryBuilder().addFilter(filterKey);
    if (dbElement.isTableEav()) {
      addFilterKcEavElement(element, domainObjectType);
    }
    addJoinKcForm(element, domainObjectType);
  }
  
  protected void addFilterKcEavElement(DetElement element, String domainObjectType) {
    String filterKey = ProductQueryMetaDataKc.getFilterKeyEavElement(element);
    getQueryBuilder().addFilter(filterKey, element.getCui());
    addJoinKcForm(element, domainObjectType);
  }

  protected void addJoinKcForm(DetElement element, String domainObjectType) {
    String filterKey = ProductQueryMetaDataKc.getFilterKeyJoinFormUnique(element, domainObjectType);    
    getQueryBuilder().addJoin(filterKey);
    getQueryBuilder().addTable(ProductQueryMetaDataKc.getTableKeyUnique(element));
    getQueryBuilder().addTable(ProductQueryMetaDataKc.getTableKeyForm(domainObjectType));
    addJoinKcFormBigrDomainObject(domainObjectType);
  }

  protected void addJoinKcFormBigrDomainObject(String domainObjectType) {
    String filterKey = 
      ProductQueryMetaDataKc.getFilterKeyJoinFormBigrDomainObject(domainObjectType);    
    getQueryBuilder().addJoin(filterKey);
  }

  public void addFilterKcComparisonAde(DetElement dataElement, 
                                       DetElement adeElement, 
                                       String operator, 
                                       String value) {
    String filterKey = getFilterKeyKcComparisonAde(dataElement, adeElement, operator);
    if (adeElement.isDatatypeDate() || adeElement.isDatatypeVpd()) {
      getQueryBuilder().addFilter(filterKey, convertKcDateValue(value));
    }
    else {
      getQueryBuilder().addFilter(filterKey, value);
    }
  }
  
  public void addFilterKcComparisonAde(DetElement dataElement, 
                                       DetElement adeElement, 
                                       String operator, 
                                       String[] values) {
    String filterKey = getFilterKeyKcComparisonAde(dataElement, adeElement, operator);
    if (adeElement.isDatatypeDate() || adeElement.isDatatypeVpd()) {
      getQueryBuilder().addFilter(filterKey, convertKcDateValues(values));
    }
    else {
      getQueryBuilder().addFilter(filterKey, values);
    }
  }
  
  private String getFilterKeyKcComparisonAde(DetElement dataElement, 
                                             DetElement adeElement, 
                                             String operator) {
    if (!KcQueryOperators.isValidComparisonOperator(operator)) {
      throw new ApiException("Attempt to add comparison filter with non-comparison operator (" + operator + ").");      
    }
    addJoinKcAdeParent(dataElement, adeElement);
    addFilterKcDataElementAde(dataElement, adeElement);
    return ProductQueryMetaDataKc.getFilterKeyAde(dataElement, adeElement, operator);
  }
  
  public void addFilterKcRangeAde(DetElement dataElement, 
                                  DetElement adeElement, 
                                  String operator, 
                                  String[] values) {
    if (!KcQueryOperators.isValidRangeOperator(operator)) {
      throw new ApiException("Attempt to add range filter with non-range operator (" + operator + ").");      
    }
    String filterKey = ProductQueryMetaDataKc.getFilterKeyAde(dataElement, adeElement, operator);
    if (adeElement.isDatatypeDate() || adeElement.isDatatypeVpd()) {
      getQueryBuilder().addFilter(filterKey, convertKcDateValues(values));
    }
    else {
      getQueryBuilder().addFilter(filterKey, values);
    }
    addFilterKcDataElementAde(dataElement, adeElement);
    addJoinKcAdeParent(dataElement, adeElement);
  }

  public void addFilterKcAnyAde(DetElement dataElement, DetElement adeElement) {
    String filterKey = 
      ProductQueryMetaDataKc.getFilterKeyAde(dataElement, adeElement, KcQueryOperators.ANY);
    getQueryBuilder().addFilter(filterKey);
    addFilterKcDataElementAde(dataElement, adeElement);
    addJoinKcAdeParent(dataElement, adeElement);
  }
  
  protected void addFilterKcDataElementAde(DetElement dataElement, DetElement adeElement) {
    String filterKey = ProductQueryMetaDataKc.getFilterKeyAdeDataElement(dataElement, adeElement);
    if (adeElement.isMultivalued()) {
      getQueryBuilder().addFilter(filterKey, adeElement.getCui());
      filterKey = ProductQueryMetaDataKc.getFilterKeyAdeDataElementBase(dataElement, adeElement);
      getQueryBuilder().addFilter(filterKey, dataElement.getCui());
    }
    else {
      getQueryBuilder().addFilter(filterKey, dataElement.getCui());
    }
    addJoinKcAdeParent(dataElement, adeElement);
  }

  protected void addJoinKcAdeParent(DetElement dataElement, DetElement adeElement) {
    if (adeElement.isMultivalued()) {
      String joinKey = 
        ProductQueryMetaDataKc.getFilterKeyJoinAdeParentMulti(dataElement, adeElement);    
      getQueryBuilder().addJoin(joinKey);
      getQueryBuilder().addTable(ProductQueryMetaDataKc.getTableKeyAdeMulti(dataElement, adeElement));      
    }
    String joinKey = ProductQueryMetaDataKc.getFilterKeyJoinAdeParent(dataElement, adeElement);    
    getQueryBuilder().addJoin(joinKey);
    getQueryBuilder().addTable(ProductQueryMetaDataKc.getTableKeyUnique(dataElement));
    getQueryBuilder().addTable(ProductQueryMetaDataKc.getTableKeyAde(dataElement, adeElement));
  }

  private String convertKcDateValue(String date) { 
    VariablePrecisionDate vpd = new VariablePrecisionDateMinimum(date);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    return dateFormat.format(vpd.getDate());    
  }
  
  private String[] convertKcDateValues(String[] dates) {
    int numDates = dates.length;
    String[] convertedDates = new String[numDates]; 
    for (int i = 0; i < numDates; i++) {
      convertedDates[i] = convertKcDateValue(dates[i]);
    }
    return convertedDates;
  }
  
  /**
   * Adds a column in the sample table to the query, adding the sample table
   * as well.
   */
  protected void addColumnInSample(String key) {
    addColumnInSample(key, TableMetaData.KEY_TABLE_SAMPLE);
//    getQueryBuilder().addColumn(key);
//    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);  
  }
  
  /**
   * Adds a column in the sample table to the query, adding the sample table
   * as well.
   */
  protected void addColumnInSample(String key, String tableKey) {
    getQueryBuilder().addColumn(key);
    getQueryBuilder().addTable(tableKey);  
  }
  

  /**
   * Adds the sample to consent join to the query, adding the necessary tables 
   * as well.
   */
  protected void addJoinSampleConsent() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE_CONSENT);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_CONSENT);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  
  }

  /**
   * Adds the sample to sample1 join to the query, adding the necessary tables 
   * as well.
   */
  protected void addJoinSampleSample1() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE_SAMPLE1);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE1);
  }

  /**
   * Adds the consent to donor join to the query, adding the necessary tables 
   * as well.  In addition, adds the sample to consent join.
   */
  protected void addJoinSampleDonor() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE_DONOR);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_DONOR);
  }

  /**
   * Adds the LIMS pathology evaluation to sample consent join to the query, 
   * adding the necessary tables as well.  In addition, adds the filter to
   * ensure that the LIMS pathology evaluation records are only those that
   * are reported.
   */
  protected void addJoinLimsPeSample() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_LIMS_PE_SAMPLE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_LIMS_PE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_REPORTED);
  }

  protected void addJoinLimsPeSampleOuter() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_LIMS_PE_SAMPLE_OUTER);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_LIMS_PE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
    // for outer join, leave in "records" with no pe data, but filter pe records that are not "reported"
    getQueryBuilder().addFilter(FilterMetaData.KEY_LIMS_PE_REPORTED_OUTER);
  }

  /**
   * Adds the pathology report to sample join to the query, adding the 
   * necessary tables as well.
   */
  protected void addJoinPathSample() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_PATH_SAMPLE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_PATH);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  
  }

  /**
   * Adds the pathology report to consent join to the query, adding the 
   * necessary tables as well.  In addition, adds the sample to consent join.
   */
  protected void addJoinPathConsent() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_PATH_CONSENT);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_PATH);
    addJoinSampleConsent();
    
  }

  /**
   * Adds the pathology report to consent outer join to the query, adding the 
   * necessary tables as well.  In addition, adds the sample to consent join.
   * The outer join will add null path fields if the pathology row does not exist.
   * This is useful if we want to use a null check in the results to tell if there
   * is pathology information, without losing the whole row if there is no path data.
   */
  protected void addJoinPathConsentOuter() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_PATH_CONSENT_OUTER);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_PATH);
    addJoinSampleConsent();
  }

  /**
   * Adds the pathology report to pathology report section join to the query, 
   * adding the necessary tables as well.  In addition, adds the pathology
   * report to sample join.
   */
  protected void addJoinPathSection() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_PATH_SECTION);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SECTION);
    addJoinPathSample();
  
  }

  /**
   * Adds the pathology report to pathology diagnostic join to the query, 
   * adding the necessary tables as well.  In addition, adds the pathology
   * report to consent join.
   */
  protected void addJoinPathDiagnostics() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_PATH_DIAGNOSTICS);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_PATH_REPORT_DIAGNOSTICS);
    addJoinPathSample();
 
  }

  /**
   * Adds the sample to ASM join to the query, adding the necessary tables
   * as well.
   */
  protected void addJoinSampleAsm() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE_ASM);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_ASM);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Adds the sample to ard_logical_repos_item join to the query, adding the necessary tables
   * as well.
   */
  protected void addJoinSampleLogicalReposItem() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_SAMPLE_LOGICAL_REPOS_ITEM);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_LOGICAL_REPOS_ITEM);
  }
  
  private List findMatchingCodes(String searchString, GbossValueSet valueSet) {
    List returnValue = new ArrayList();
    if (ApiFunctions.isEmpty(searchString) || valueSet == null) {
      return returnValue;
    }
    //escape any characters in the search string that have special meaning in regular expressions,
    //with the exception of the character "*" as that is the character the user will be using as
    //a wildcard
    searchString = Escaper.regExpEscape(searchString, "*");
    //change any "*" in the search strign to ".*"
    searchString = searchString.replaceAll("\\*", ".*");
    //create a regular expression with which to compare the values in the value set
    Pattern pattern = Pattern.compile("^" + searchString + "$", Pattern.CASE_INSENSITIVE);
    Iterator iterator = valueSet.depthFirstIterator();
    while (iterator.hasNext()) {
      GbossValue value = (GbossValue)iterator.next();
      //ignore non-leaf nodes
      if (ApiFunctions.isEmpty(value.getValues())) {
        Matcher matcher = pattern.matcher(value.getDescription());
        if (matcher.find()) {
          returnValue.add(value.getCui());
        }
      }
    }
    return returnValue;
  }

  private String[] matchingDiagnosisCodes(String pattern) {
    GbossValueSet diagnosisValueSet = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY);
    List results = findMatchingCodes(pattern, diagnosisValueSet);
    if (results.isEmpty()) // consider a non-matching pattern an error like a bad diagnosis code
      throw new NoMatchingDiagnosisException(pattern);
    String[] matchingCodes = (String[]) results.toArray(new String[0]);
    return matchingCodes;
  }

  private String[] matchingTissueCodes(String pattern) {
    GbossValueSet tissueValueSet = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_TISSUE_HIERARCHY);
    List results = findMatchingCodes(pattern, tissueValueSet);
    if (results.isEmpty()) // consider a non-matching pattern an error like a bad diagnosis code
      throw new NoMatchingTissueException(pattern);
    String[] matchingCodes = (String[]) results.toArray(new String[0]);
    return matchingCodes;
  }

  // ----------------------------------------------------------------------------
  // ----------  Add Field-specific Filter entry methods ------------------------

  /** 
   *  This is called by a {@link FilterRestrictedStatus} object.  Do not call directly.
   * (Note that the code passed in is processed by this method and does not directly
   * correspond to database values.)
   * 
   *  @param code  one of Constants.RESTRICTED_XXX where XXX is MIR, MIUR, MIU, R or U.
   *  @param acctName  the account name for the current user
   */
  //restriction code looks a lot like a simple DB column to the outside world, but is not
  public void addRestrictedStatusFilter(String code, String acctName, String orGrpCode) {
    if (Constants.RESTRICTED_MIR.equals(code)) {
      addFilterRestricted(orGrpCode);
      addFilterSampleAccount(acctName);
    }
    else if (Constants.RESTRICTED_MIUR.equals(code)) {
      addFilterSampleAccount(acctName);
    }
    else if (Constants.RESTRICTED_MIU.equals(code)) {
      addFilterUnrestricted(orGrpCode);
    }
    else if (Constants.RESTRICTED_R.equals(code)) {
      addFilterRestricted(orGrpCode);
    }
    else if (Constants.RESTRICTED_U.equals(code)) {
      addFilterUnrestricted(orGrpCode);
    }
    else if (Constants.RESTRICTED_MIA.equals(code)) {
      addFilterMiRestricted(acctName, orGrpCode);
    }
    // @todo: put addfilterrestricted() and addfilterRNArestricted in subclasses
  }

  /*
   * Package visible method to add a clause to the generated SQL string that
   * will cause no results to be returned.  Should be rarely used - currently
   * the only place that uses this is addFilterLogicalRepository() when an
   * empty list of repository ids is passed in.  In that case the user doesn't
   * have access to any logical repository, and thus no results should be
   * returned.
   */
  void addFilterReturnNoResults() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RETURN_NO_RESULTS);
  }

  /**
   * Create a new instance of my same subclass
   */
  protected abstract ProductSummaryQueryBuilder newQueryBuilder();

  /**
   * Returns the psqb.
   * @return ProductSummaryQueryBuilder
   */
  public ProductSummaryQueryBuilder getSecondaryPsqb() {
    return secondaryPsqb;
  }

  /**
   * Sets the psqb.
   * @param psqb The psqb to set
   */
  public void setSecondaryPsqb(String operation) {
    _operation = operation;
    secondaryPsqb = newQueryBuilder();
  }

}
