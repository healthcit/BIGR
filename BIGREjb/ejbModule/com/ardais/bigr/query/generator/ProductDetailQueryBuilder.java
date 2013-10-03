package com.ardais.bigr.query.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Provides for specification and execution of one or more detail queries for 
 * products.  Full results are returned, based on a specification of columns of 
 * information to return and a list of IDs.  This class is intended to be used 
 * by the EJB tier to perform queries.
 *
 * @see  RnaColumns
 * @see  SampleColumns
 * @see  com.ardais.bigr.javabeans.ProductDto
 */
public class ProductDetailQueryBuilder {

  /**
   *  The maximum number of IDs for which we'll get details in a single query.
   */
  private final static int CHUNK_SIZE = 64;

  // Keys for the separate queries.  Their values must sort to the order in
  // which the queries will be run.
  //    private final static String KEY_RNA = "001"; // rna
  //    private final static String KEY_RNA_PROJECT = "002"; // rna project hold/sold
  private final static String KEY_SAMPLE = "010"; // sample and product
  private final static String KEY_LIMS = "011"; // lims
  private final static String KEY_IMAGES = "012"; //ads_imaget_syn
  private final static String KEY_PROJECT = "013"; // pts project and sample
  private final static String KEY_SHOPPING_CART = "014"; // pts project and sample
  private final static String KEY_ORDER = "015"; // pts project and sample
  private final static String KEY_ASM = "016"; // asm
  private final static String KEY_CONSENT = "017"; // consent 
  private final static String KEY_PATH = "018"; // path and path section
  private final static String KEY_DONOR = "019"; // ardais donor
  private final static String KEY_LOGICAL_REPOSITORY = "020"; // logical repository
  private final static String KEY_SAMPLE_LOCATION = "021"; // sample storage location (local)
  private final static String KEY_DIAGNOSTIC = "022"; // diagnostic test
  private final static String KEY_SAMPLE_KC = "023"; // sample kc data
  private final static String KEY_CONSENT_KC = "024"; // case kc data
  private final static String KEY_DONOR_KC = "025"; // donor kc data

  /**
   * Holds ProductQueryBuilder objects representing each query, keyed by the 
   * <code>KEY_Q*</code> constants defined in this class.
   */
  private SortedMap _queries = new TreeMap();

  /**
   * Logger for logging performance-related items.
   */
  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  SecurityInfo _securityInfo;

  //  public static void main(String[] args) {
  //    ProductDetailQueryBuilder qb = new ProductDetailQueryBuilder(new RnaColumns());
  //    List queries = qb.getDetails(new String[] {"id1", "id2", "id3"}, false);
  //    for (int i=0; i<queries.size(); i++) {
  //        String qq;
  //        qq = queries.get(0).toString();
  //    }
  //  }

  /**
   * Creates a new <code>ProductDetailQueryBuilder</code>.
   */
  public ProductDetailQueryBuilder(SecurityInfo securityInfo) {
    super();
    _securityInfo = securityInfo;
  }

  /**
   * Returns the tissue sample detail queries specified by this class.
   * 
   * @param  sampleIds  the array of sample IDs
   * @return  A <code>List</code> of strings, each of which is a SQL statement.
   */
  public List getDetailQueries(String[] sampleIds) {
    return null;
  }

  /**
   * Returns the full details of the tissue samples with the specified IDs.
  	 * One or more columns must be specified before calling this method.  The
   * only filters are the sample IDs that are passed to this method.
   * 
   * @param  sampleIds  the array of sample IDs
   * @return  A <code>List</code> of 
   * 					 {@link com.ardais.bigr.javabeans.SampleData} beans.
   */
  public List getDetails(String[] sampleIds) {
    List sampleDataBeans = new ArrayList();
    Map sampleMap = new HashMap();
    int numIds = sampleIds.length;
    for (int i = 0; i < numIds; i++) {
      String id = sampleIds[i];
      SampleData sample = new SampleData();
      sample.setSampleId(id);
      sampleMap.put(id, sample);
      sampleDataBeans.add(sample);
    }
    getDetails(sampleMap);
    return sampleDataBeans;
  }

  /**
   * Populates the full details of the tissue sample data beans specified
   * in the input <code>Map</code>.  One or more columns must be specified 
   * before calling this method.  The only filters are the sample IDs that are 
   * specified in the <code>Map</code>.
   * 
   * @param  sampleDataBeans  a <code>Map</code> of 
   * 						{@link com.ardais.bigr.javabeans.SampleData} data beans, keyed
   * 						by the ids of the samples
   */
  public void getDetails(Map sampleDataBeans) {

    if (sampleDataBeans.isEmpty()) {
      return;
    }

    long tStart = 0;
    String myClassName = null;
    if (_perfLog.isDebugEnabled()) {
      myClassName = ApiFunctions.shortClassName(getClass().getName());
      _perfLog.debug("    C: START " + myClassName + ".getDetails");
      tStart = System.currentTimeMillis();
    }

    // Create the Maps that will hold the parent data beans.
    Map asmMap = new HashMap();
    Map consentMap = new HashMap();
    Map donorMap = new HashMap();

    // Iterate over all queries.
    Iterator queries = _queries.entrySet().iterator();
    while (queries.hasNext()) {
      long tLoopStart = System.currentTimeMillis();
      Map.Entry entry = (Map.Entry) queries.next();
      String queryKey = (String) entry.getKey();
      if (queryKey.equals(KEY_SAMPLE)) {
        TissueDetailQueryBuilder query = (TissueDetailQueryBuilder) entry.getValue();
        query.getDetails(sampleDataBeans);
        asmMap = query.getAsmData();
      }
      else if (queryKey.equals(KEY_LIMS)) {
        PathologyEvaluationDetailQueryBuilder query =
          (PathologyEvaluationDetailQueryBuilder) entry.getValue();
        query.getDetailsForSamples(sampleDataBeans);
      }
      else if (queryKey.equals(KEY_IMAGES)) {
        ImageDetailQueryBuilder query = (ImageDetailQueryBuilder) entry.getValue();
        query.getDetailsForSamples(sampleDataBeans);
      }
      else if (queryKey.equals(KEY_PROJECT)) {
        ProjectDetailQueryBuilder query = (ProjectDetailQueryBuilder) entry.getValue();
        query.getDetailsForSamples(sampleDataBeans);
      }
      else if (queryKey.equals(KEY_ORDER)) {
        OrderDetailQueryBuilder query = (OrderDetailQueryBuilder) entry.getValue();
        query.getDetailsForSamples(sampleDataBeans);
      }
      else if (queryKey.equals(KEY_SHOPPING_CART)) {
        ShoppingCartDetailQueryBuilder query = (ShoppingCartDetailQueryBuilder) entry.getValue();
        query.getDetailsForSamples(sampleDataBeans);
      }
      else if (queryKey.equals(KEY_ASM)) {
        AsmDetailQueryBuilder query = (AsmDetailQueryBuilder) entry.getValue();
        query.getDetails(asmMap);
        consentMap = query.getConsentData();
      }
      else if (queryKey.equals(KEY_CONSENT)) {
        ConsentDetailQueryBuilder query = (ConsentDetailQueryBuilder) entry.getValue();
        query.getDetails(consentMap);
        donorMap = query.getDonorData();
      }
      else if (queryKey.equals(KEY_DONOR)) {
        DonorDetailQueryBuilder query = (DonorDetailQueryBuilder) entry.getValue();
        query.getDetails(donorMap);
      }
      else if (queryKey.equals(KEY_PATH)) {
        PathologyReportDetailQueryBuilder query =
          (PathologyReportDetailQueryBuilder) entry.getValue();
        query.getDetailsForConsents(consentMap);
      }
      else if (queryKey.equals(KEY_LOGICAL_REPOSITORY)) {
        LogicalRepositoryDetailQueryBuilder query =
          (LogicalRepositoryDetailQueryBuilder) entry.getValue();
        query.getDetails(sampleDataBeans);
      }
      else if (queryKey.equals(KEY_SAMPLE_LOCATION)) {
        SampleLocationDetailQueryBuilder query =
          (SampleLocationDetailQueryBuilder) entry.getValue();
        query.getDetails(sampleDataBeans);
      }
      else if (queryKey.equals(KEY_DIAGNOSTIC)) {
        DiagnosticTestDetailQueryBuilder query =
          (DiagnosticTestDetailQueryBuilder) entry.getValue();
        query.getDetailsForConsents(consentMap);
      }
      else if (queryKey.equals(KEY_SAMPLE_KC)) {
        KcDetailQueryBuilder query = (KcDetailQueryBuilder) entry.getValue();
        query.getDetails(sampleDataBeans);
      }
      else if (queryKey.equals(KEY_CONSENT_KC)) {
        KcDetailQueryBuilder query = (KcDetailQueryBuilder) entry.getValue();
        query.getDetails(consentMap);
      }
      else if (queryKey.equals(KEY_DONOR_KC)) {
        KcDetailQueryBuilder query = (KcDetailQueryBuilder) entry.getValue();
        query.getDetails(donorMap);
      }
      else {
        throw new ApiException(
          "Unrecognized query key in ProductDetailQueryBuilder.getDetails:" + queryKey);
      }

      if (_perfLog.isDebugEnabled()) {
        long elapsedTime = System.currentTimeMillis() - tLoopStart;
        _perfLog.debug("    D: do detail query " + queryKey + " (" + elapsedTime + " ms)");
      }
    } // while next query

    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStart;
      _perfLog.debug("    C: END   " + myClassName + ".getDetails" + " (" + elapsedTime + " ms)");
    }
  }

  /**
   * Specifies that the age at collection column be returned in this detail query.
   */
  public void addColumnAgeAtCollection() {
    getQuerySample().addColumnAgeAtCollection();
  }

  /**
   * Specifies that the appearance best column from the sample table be returned in this 
   * detail query.
   */
  public void addColumnSampleAppearanceBest() {
    getQuerySample().addColumnSampleAppearanceBest();
  }

  /**
   * Specifies that the consent id column from ASM be returned in this 
   * detail query.
   */
  public void addColumnAsmConsentId() {
    getQueryAsm().addColumnAsmConsentId();
  }

  /**
   * Specifies that the asm module comments column from ASM be returned in this 
   * detail query.
   */
  public void addColumnAsmModuleComments() {
    getQueryAsm().addColumnAsmModuleComment();
  }

  /**
   * Specifies that the ASM id column be returned in this 
   * detail query.
   */
  public void addColumnAsmId() {
    getQueryAsm().addColumnAsmId();
  }

  /**
   * Specifies that the ASM position column be returned in this 
   * detail query.
   */
  public void addColumnAsmPosition() {
    getQuerySample().addColumnAsmPosition();
  }

  /**
   * Specifies that the ASM tissue column be returned in this 
   * detail query.
   */
  public void addColumnAsmTissue() {
    getQueryAsm().addColumnAsmTissue();
  }

  /**
   * Specifies that the ASM tissue column be returned in this 
   * detail query.
   */
  public void addColumnAsmTissueOther() {
    getQueryAsm().addColumnAsmTissueOther();
  }

  /**
   * Specifies that the ASM procedure column be returned in this 
   * detail query.
   */
  public void addColumnAsmProcedure() {
    getQueryAsm().addColumnAsmProcedure();
  }

  /**
   * Specifies that the ASM procedure other column be returned in this 
   * detail query.
   */
  public void addColumnAsmProcedureOther() {
    getQueryAsm().addColumnAsmProcedureOther();
  }

  /**
   * Specifies that the ASM prepared by column be returned in this 
   * detail query.
   */
  public void addColumnAsmPreparedBy() {
    getQueryAsm().addColumnAsmPreparedBy();
  }

  /**
   * Specifies that the acellular stroma composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionAcellularStroma() {
    getQueryLims().addColumnCompositionAcellularStroma();
  }

  /**
   * Specifies that the cellular stroma composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionCellularStroma() {
    getQueryLims().addColumnCompositionCellularStroma();
  }

  /**
   * Specifies that the lesion composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionLesion() {
    getQueryLims().addColumnCompositionLesion();
  }

  /**
   * Specifies that the necrosis composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionNecrosis() {
    getQueryLims().addColumnCompositionNecrosis();
  }

  /**
   * Specifies that the normal composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionNormal() {
    getQueryLims().addColumnCompositionNormal();
  }

  /**
   * Specifies that the tumor composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionTumor() {
    getQueryLims().addColumnCompositionTumor();
  }

  /**
   * Specifies that the consent id column be returned in this 
   * detail query.
   */
  public void addColumnConsentId() {
    getQueryConsent().addColumnConsentId();
  }

  /**
   * Specifies that the consent id column be returned in this 
   * detail query.
   */
  public void addColumnConsentLocation() {
    getQueryConsent().addColumnConsentLocation();
  }

  /**
   * Specifies that the DDC diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnDdcDiagnosis() {
    getQueryPath().addColumnDdcDiagnosis();
  }

  /**
   * Specifies that the other DDC diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnDdcDiagnosisOther() {
    getQueryPath().addColumnDdcDiagnosisOther();
  }

  /**
   * Specifies that the DDC finding tissue column be returned in this 
   * detail query.
   */
  public void addColumnDdcTissueFinding() {
    getQueryPath().addColumnDdcTissueFinding();
  }

  /**
   * Specifies that the other DDC finding tissue column be returned in this 
   * detail query.
   */
  public void addColumnDdcTissueFindingOther() {
    getQueryPath().addColumnDdcTissueFindingOther();
  }

  /**
   * Specifies that the DDC origin tissue column be returned in this 
   * detail query.
   */
  public void addColumnDdcTissueOrigin() {
    getQueryPath().addColumnDdcTissueOrigin();
  }

  /**
   * Specifies that the other DDC origin tissue column be returned in this 
   * detail query.
   */
  public void addColumnDdcTissueOriginOther() {
    getQueryPath().addColumnDdcTissueOriginOther();
  }

  /**
   * Specifies that the distant metastasis column be returned in this 
   * detail query.
   */
  public void addColumnDistantMetastasis() {
    getQueryPath().addColumnDistantMetastasis();
  }

  /**
   * Specifies that the donor id column be returned in this 
   * detail query.
   */
  public void addColumnDonorId() {
    getQueryConsent().addColumnDonorId();
  }

  /**
   * Specifies that the gender column be returned in this 
   * detail query.
   */
  public void addColumnGender() {
    getQueryConsent().addColumnGender();
  }

  /**
   * Specifies that the Race column be returned in this 
   * detail query.
   */
  public void addColumnRace() {
    getQueryConsent().addColumnRace();
  }
  
  /**
   * Specifies that the gleason score columns be returned in this 
   * detail query.  This reuturn the primary, secondary and total gleason score
   * columns.
   */
  public void addColumnGleasonScore() {
    getQueryPath().addColumnGleasonScore();
  }

  /**
   * Specifies that the gross appearance column be returned in this 
   * detail query.
   */
  public void addColumnGrossAppearance() {
    getQueryAsm().addColumnGrossAppearance();
  }

  /**
   * Specifies that the histologic/nuclear grade column be returned in this 
   * detail query.
   */
  public void addColumnHistologicNuclearGrade() {
    getQueryPath().addColumnHistologicNuclearGrade();
  }

  /**
   * Specifies that the ILTDS diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnIltdsDiagnosis() {
    getQueryConsent().addColumnIltdsDiagnosis();
  }

  /**
   * Specifies that the other ILTDS diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnIltdsDiagnosisOther() {
    getQueryConsent().addColumnIltdsDiagnosisOther();
  }

  /**
   * Specifies that the sample's inventory status column be returned in this 
   * detail query.
   */
  public void addColumnInventoryStatus() {
    getQuerySample().addColumnInventoryStatus();
  }

  /**
   * Specifies that the sample's inventory status date column be returned in 
   * this detail query.
   */
  public void addColumnInventoryStatusDate() {
    getQuerySample().addColumnInventoryStatusDate();
  }

  /**
   * Specifies that the LIMS diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnLimsDiagnosis() {
    getQueryLims().addColumnDiagnosis();
  }

  /**
   * Specifies that the LIMS diagnosis other column be returned in this 
   * detail query.
   */
  public void addColumnLimsDiagnosisOther() {
    getQueryLims().addColumnDiagnosisOther();
  }

  /**
   * Specifies that the LIMS finding tissue column be returned in this 
   * detail query.
   */
  public void addColumnLimsTissueFinding() {
    getQueryLims().addColumnTissueFinding();
  }

  /**
   * Specifies that the LIMS finding tissue other column be returned in this 
   * detail query.
   */
  public void addColumnLimsTissueFindingOther() {
    getQueryLims().addColumnTissueFindingOther();
  }

  /**
   * Specifies that the LIMS origin tissue column be returned in this 
   * detail query.
   */
  public void addColumnLimsTissueOrigin() {
    getQueryLims().addColumnTissueOrigin();
  }

  /**
   * Specifies that the LIMS origin tissue other column be returned in this 
   * detail query.
   */
  public void addColumnLimsTissueOriginOther() {
    getQueryLims().addColumnTissueOriginOther();
  }

  /**
   * Specifies that the lymph node stage column be returned in this 
   * detail query.
   */
  public void addColumnLymphNodeStage() {
    getQueryPath().addColumnLymphNodeStage();
  }

  /**
   * Specifies that the microscopic appearance column be returned in this 
   * detail query.
   */
  public void addColumnMicroscopicAppearance() {
    getQueryLims().addColumnMicroscopicAppearance();
  }

  /**
   * Specifies that the pathology report id column be returned in this 
   * detail query.
   */
  public void addColumnPathId() {
    getQueryPath().addColumnPathReportId();
  }

  /**
   * Specifies that the origin tissue from the sample table be returned 
   * in this detail query.
   */
  public void addColumnSampleTissueOrigin() {
    getQuerySample().addColumnSampleTissueOrigin();
  }

  /**
   * Specifies that the other origin tissue from the sample table be returned 
   * in this detail query.
   */
  public void addColumnSampleTissueOriginOther() {
    getQuerySample().addColumnSampleTissueOriginOther();
  }

  /**
   * Specifies that the sample's project status column be returned in this 
   * detail query.
   */
  public void addColumnProjectStatus() {
    getQuerySample().addColumnProjectStatus();
  }

  /**
   * Specifies that the sample's project status date column be returned in 
   * this detail query.
   */
  public void addColumnProjectStatusDate() {
    getQuerySample().addColumnProjectStatusDate();
  }

  /**
   * Specifies that the LIMS pv notes column be returned in this 
   * detail query.
   */
  public void addColumnPvNotes() {
    getQueryLims().addColumnPvNotes();
  }

  /**
   * Specifies that the internal LIMS pv notes column be returned in this 
   * detail query.
   */
  public void addColumnPvNotesInternal() {
    getQueryLims().addColumnPvNotesInternal();
  }

  /**
   * Specifies that the sample's QC status column be returned in this 
   * detail query.
   */
  public void addColumnHistoNotes() {
    getQuerySample().addColumnHistoNotes();
  }

  /**
   * Specifies that the sample's QC status column be returned in this 
   * detail query.
   */
  public void addColumnQcStatus() {
    getQuerySample().addColumnQcStatus();
  }

  /**
   * Specifies that the sample's QC status date column be returned in 
   * this detail query.
   */
  public void addColumnQcStatusDate() {
    getQuerySample().addColumnQcStatusDate();
  }

  /**
   * Specifies that the qc verified column be returned in this 
   * detail query.
   */
  public void addColumnQcVerified() {
    getQuerySample().addColumnQcVerified();
  }

  /**
   * Specifies that the restricted column be returned in this 
   * detail query.
   */
  public void addColumnRestricted() {
    getQuerySample().addColumnRestricted();
  }

  /**
   * Specifies that the sample's sales status column be returned in this 
   * detail query.
   */
  public void addColumnSalesStatus() {
    getQuerySample().addColumnSalesStatus();
  }

  /**
   * Specifies that the sample's sales status date column be returned in 
   * this detail query.
   */
  public void addColumnSalesStatusDate() {
    getQuerySample().addColumnSalesStatusDate();
  }

  /**
   * Specifies that the ASM id column from sample be returned 
   * in this detail query.
   */
  public void addColumnSampleAsmId() {
    getQuerySample().addColumnSampleAsmId();
  }

  /**
   * Specifies that the ASM note column from sample be returned 
   * in this detail query.
   */
  public void addColumnSampleAsmNote() {
    getQuerySample().addColumnSampleAsmNote();
  }

  /**
   * Specifies that the sample id column from be returned in this 
   * detail query.
   */
  public void addColumnSampleId() {
    getQuerySample().addColumnSampleId();
  }

  /**
   * Specifies that the path report section id column be returned in this 
   * detail query.
   */
  public void addColumnSectionId() {
    getQueryPath().addColumnSectionId();
  }

  /**
   * Specifies that the samples format detail column be returned in this 
   * detail query.
   */
  public void addColumnSampleFormatDetail() {
    getQuerySample().addColumnFormatDetail();
  }

  /**
   * Specifies that the samples fixative type column be returned in this 
   * detail query.
   */
  public void addColumnSampleFixativeType() {
    getQuerySample().addColumnFixativeType();
  }
  
  /**
   * Specifies that the sample type column be returned in this 
   * detail query.
   */
  public void addColumnSampleType() {
    getQuerySample().addColumnSampleType();
  }
  
  /**
   * Specifies that the sample source column be returned in this 
   * detail query.
   */
  public void addColumnSampleSource() {
    getQuerySample().addColumnSampleSource();
  }

  /**
   * Specifies that the samples format detail column be returned in this 
   * detail query.
   */
  public void addColumnSampleSubdivisionDate() {
    getQuerySample().addColumnSampleSubdivisionDate();
  }

  /**
   * Specifies that the samples box barcode id column be returned in this 
   * detail query.
   */
  public void addColumnSampleBoxBarcodeId() {
    getQuerySample().addColumnSampleBoxBarcodeId();
  }
  
  /**
   * Specifies that the samples volume column be returned in this 
   * detail query.
   */
  public void addColumnSampleVolume() {
    getQuerySample().addColumnSampleVolume();
  }
  /**
   * Specifies that the samples weight column be returned in this 
   * detail query.
   */
  public void addColumnSampleWeight() {
    getQuerySample().addColumnSampleWeight();
  }
  /**
   * Specifies that the samples concentration column be returned in this 
   * detail query.
   */
  public void addColumnSampleConcentration() {
    getQuerySample().addColumnSampleConcentration();
  }
  
  /**
   * Specifies that the samples yield column be returned in this 
   * detail query.
   */
  public void addColumnSampleYield() {
    getQuerySample().addColumnSampleYield();
  }

  /**
   * Specifies that the samples buffer type column be returned in this 
   * detail query.
   */
  public void addColumnSampleBufferType() {
    getQuerySample().addColumnSampleBufferType();
  }

  /**
   * Specifies that the samples total number of cells column be returned in this 
   * detail query.
   */
  public void addColumnSampleTotalNumOfCells() {
    getQuerySample().addColumnSampleTotalNumOfCells();
  }

  /**
   * Specifies that the samples cells per ml column be returned in this 
   * detail query.
   */
  public void addColumnSampleCellsPerMl() {
    getQuerySample().addColumnSampleCellsPerMl();
  }

  /**
   * Specifies that the samples percent viability column be returned in this 
   * detail query.
   */
  public void addColumnSamplePercentViability() {
    getQuerySample().addColumnSamplePercentViability();
  }
  
  /**
   * Specifies that the date of collection column be returned in this detail query.
   */
  public void addColumnSampleDateOfCollection() {
    getQuerySample().addColumnSampleDateOfCollection();
  }
  
  /**
   * Specifies that the date of preservation column be returned in this detail query.
   */
  public void addColumnSampleDateOfPreservation() {
    getQuerySample().addColumnSampleDateOfPreservation();
  }
  
  /**
   * Specifies that the elapsed time column be returned in this detail query.
   */
  public void addColumnSampleElapsedTime() {
    getQuerySample().addColumnSampleElapsedTime();
  }

  /**
   * Specifies that the stage grouping column be returned in this 
   * detail query.
   */
  public void addColumnStageGrouping() {
    getQueryPath().addColumnStageGrouping();
  }

  /**
   * Specifies that the tumor stage column be returned in this 
   * detail query.
   */
  public void addColumnTumorStage() {
    getQueryPath().addColumnTumorStage();
  }

  /**
   * Specifies that the tumor stage type column be returned in this 
   * detail query.
   */
  public void addColumnTumorStageType() {
    getQueryPath().addColumnTumorStageType();
  }

  /**
   * Specifies that the pull_yn column be returned in this 
   * detail query.
   */
  public void addColumnPullYN() {
    getQuerySample().addColumnPull();
  }

  /**
   * Specifies that the pull_date column be returned in this 
   * detail query.
   */
  public void addColumnPullDate() {
    getQuerySample().addColumnPullDate();
  }

  /**
   * Specifies that the pulled reason column be returned in this 
   * detail query.
   */
  public void addColumnPulledReason() {
    getQuerySample().addColumnPulledReason();
  }

  /**
   * Specifies that the released_yn column be returned in this 
   * detail query.
   */
  public void addColumnReleasedYN() {
    getQuerySample().addColumnReleased();
  }

  /**
   * Specifies that the discordant_yn column be returned in this 
   * detail query.
   */
  public void addColumnDiscordantYN() {
    getQuerySample().addColumnDiscordant();
  }

  /**
   * Specifies that the qcposted_yn column be returned in this 
   * detail query.
   */
  public void addColumnQCPostedYN() {
    getQuerySample().addColumnQcPosted();
  }

  /**
   * Specifies that the LIMS pv create_user column be returned in this 
   * detail query.
   */
  public void addColumnPvCreateUser() {
    getQueryLims().addColumnPvCreateUser();
  }

  /**
   * Specifies that the LIMS pv slide id column be returned in this 
   * detail query.
   */
  public void addColumnPvSlideId() {
    getQueryLims().addColumnSlideId();
  }

  /**
   * Specifies that the LIMS pv create date column be returned in this 
   * detail query.
   */
  public void addColumnPvCreateDate() {
    getQueryLims().addColumnPvCreateDate();
  }

  /**
   * Specifies that the LIMS pv reported date column be returned in this 
   * detail query.
   */
  public void addColumnPvReportedDate() {
    getQueryLims().addColumnPvReportedDate();
  }

  /**
   * Specifies that the LIMS pv id column be returned in this 
   * detail query.
   */
  public void addColumnPvId() {
    getQueryLims().addColumnPvId();
  }

  /**
   * Specifies that the tumor size column be returned in this 
   * detail query.
   */
  public void addColumnTumorSize() {
    getQueryPath().addColumnTumorSize();
  }

  /**
   * Specifies that the tumor weight column be returned in this 
   * detail query.
   */
  public void addColumnTumorWeight() {
    getQueryPath().addColumnTumorWeight();
  }

  /** Specifies that the image id for any images associated with
   *  the sample be returned in this detail query */
  public void addColumnSampleImagesId() {
    getQueryImages().addColumnImageId();
  }

  /** Specifies that the image filename for any images associated with
   *  the sample be returned in this detail query */
  public void addColumnSampleImagesFilename() {
    getQueryImages().addColumnImageFilename();
  }

  /** Specifies that the image slide id for any images associated with
   *  the sample be returned in this detail query */
  public void addColumnSampleImagesSlideId() {
    getQueryImages().addColumnSlideId();
  }

  /** Specifies that the image name for any images associated with
   *  the sample be returned in this detail query */
  public void addColumnSampleImagesMagnification() {
    getQueryImages().addColumnImageMagnification();
  }

  /** Specifies that the image capture date for any images associated with
   *  the sample be returned in this detail query */
  public void addColumnSampleImagesCaptureDate() {
    getQueryImages().addColumnImageCaptureDate();
  }

  /**
   * Specifies that the sample section count column from be returned in this 
   * detail query.
   */
  public void addColumnSampleSectionCount() {
    getQuerySample().addColumnSampleSectionCount();
  }

  /**
   * Specifies that the project name column be returned in this 
   * detail query.
   */
  public void addColumnProject() {
    getQueryProject().addColumnProjectName();
  }

  /**
   * Specifies that the project date requested be returned in this 
   * detail query.
   */
  public void addColumnProjectDateRequested() {
    getQueryProject().addColumnProjectDateRequested();
  }

  /**
   * Specifies that the shopping cart user id column be returned in this 
   * detail query.
   */
  public void addColumnShoppingCartUser() {
    getQueryShoppingCart().addColumnShoppingCartUser();
  }

  /**
   * Specifies that the shopping cart creation date column be returned in this 
   * detail query.
   */
  public void addColumnShoppingCartCreationDate() {
    getQueryShoppingCart().addColumnShoppingCartCreationDate();
  }

  /**
   * Specifies that the order description column be returned in this 
   * detail query.
   */
  public void addColumnOrderDescription() {
    getQueryOrder().addColumnOrderDescription();
  }

  /**
   * Specifies that the donor consent count column be returned in this 
   * detail query.
   */
  public void addColumnDonorConsentCount() {
    getQueryDonor().addColumnConsentCount();
  }

  /**
   * Specifies that the donor consent count column be returned in this 
   * detail query.
   */
  public void addColumnDonorComments() {
    getQueryConsent().addColumnDonorComments();
  }

  public void addColumnRnaId() {
    getQuerySample().addColumnRnaId();
  }

  public void addColumnTmaId() {
    getQuerySample().addColumnTmaId();
  }

  /**
   * Add any logical repository columns that are mandatory for proper system function.  These
   * columns must be included in the result details, for example, even if the information in them
   * isn't being shown to the user.  This is public so you can call it explicitly, but this class's
   * constructor also calls this.
   * <b>This only returns information on logical repositories that the current user is permitted
   * to see.  So, for example, if you use this to get the list of logical repositories that
   * a sample is in, it will include only the ones that the current user can see, which may not
   * be all of the repositories that the sample is actually in.  There are places that currently
   * depend on LogicalRepositoryDetailQueryBuilder behaving this way, see
   * {@link #getQueryUserLogicalRepository()} for more details.</b>
   */
  public void addMandatoryLogicalRepositoryColumns() {
    getQueryUserLogicalRepository().addMandatoryColumns();
  }

  /**
   * Specifies that the logical repository short name column be returned in this 
   * detail query.
   * <b>This only returns information on logical repositories that the current user is permitted
   * to see.  So, for example, if you use this to get the list of logical repositories that
   * a sample is in, it will include only the ones that the current user can see, which may not
   * be all of the repositories that the sample is actually in.  There are places that currently
   * depend on LogicalRepositoryDetailQueryBuilder behaving this way, see
   * {@link #getQueryUserLogicalRepository()} for more details.</b>
   */
  public void addColumnUserLogicalRepositoryShortNames() {
    getQueryUserLogicalRepository().addColumnRepositoryShortName();
  }

  /**
   * Specifies that the bms_yn column be returned in this 
   * detail query.
   */
  public void addColumnBmsYN() {
    getQuerySample().addColumnBmsYN();
  }

  /**
   * Specifies that the sample's customer_id column be returned in this 
   * detail query.
   */
  public void addColumnCustomerId() {
    getQuerySample().addColumnCustomerId();
  }

  /**
   * Specifies that the case's customer_id column be returned in this 
   * detail query.
   */
  public void addColumnConsentCustomerId() {
    getQueryConsent().addColumnCustomerId();
  }

  /**
   * Specifies that the donor's customer_id column be returned in this 
   * detail query.
   */
  public void addColumnDonorCustomerId() {
    getQueryConsent().addColumnDonorCustomerId();
  }

  /**
   * Specifies that the diagnostic cocept column be returned in this 
   * detail query.
   */
  public void addDiagnosticTest(DiagnosticTestFilterDto dto) {
    if (dto != null) {
      if (dto.isShowResultDiag()) {
        getQueryDiagnostic(dto);
      }
    }
  }

  /**
   * Specifies that the cell_ref_location column be returned in this 
   * detail query.
   */
  public void addColumnCellRefLocation() {
    getQuerySample().addColumnCellRefLocation();
  }

  /**
   * Specifies that the local column be returned in this 
   * detail query.
   */
  public void addColumnSampleLocation() {
    getQuerySampleLocation().addSampleLocationAddressId();
  }

  /**
   * Specifies that the PSA column be returned in this detail query.
   */
  public void addColumnPsa(DiagnosticTestFilterDto dto) {
    if (dto != null) {
      if (dto.isShowResultClinLab()) {
        getQueryConsent().addColumnPsa();
      }
    }
  }

  /**
   * Specifies that the DRE column be returned in this detail query.
   */
  public void addColumnDre(DiagnosticTestFilterDto dto) {
    if (dto != null) {
      if (dto.isShowResultClinFindings()) {
        getQueryConsent().addColumnDre();
      }
    }
  }
  
  /**
   * Adds a kc column
   */
  public void addColumnKcData(String cui, String domainObjectType) {
    if (ApiFunctions.isEmpty(cui)) {
      throw new ApiException("Null cui encountered");
    }
    else if (ApiFunctions.isEmpty(domainObjectType)) {
      throw new ApiException("Null domain object type encountered");      
    }
    else {
      if (TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE.equalsIgnoreCase(domainObjectType)) {
        getQuerySampleKcData().addColumn(cui);
      }
      else if (TagTypes.DOMAIN_OBJECT_VALUE_CASE.equalsIgnoreCase(domainObjectType)) {
        getQueryConsentKcData().addColumn(cui);        
      }
      else if (TagTypes.DOMAIN_OBJECT_VALUE_DONOR.equalsIgnoreCase(domainObjectType)) {
        getQueryDonorKcData().addColumn(cui);        
      }
      else {
        throw new ApiException("Unknown domain object type encountered: " + domainObjectType);
      }
    }
  }

  /**
   * Returns the <code>AsmDetailQueryBuilder</code> for the ASM query, creating 
   * it if necessary.
   */
  private AsmDetailQueryBuilder getQueryAsm() {
    AsmDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_ASM) == null) {
      queryBuilder = new AsmDetailQueryBuilder();
      queryBuilder.addColumnAsmId();
      _queries.put(KEY_ASM, queryBuilder);
      getQuerySample().setCreateAsmData(true);
    }
    else {
      queryBuilder = (AsmDetailQueryBuilder) _queries.get(KEY_ASM);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>ConsentDetailQueryBuilder</code> for the ASM query, 
   * creating it if necessary.
   */
  private ConsentDetailQueryBuilder getQueryConsent() {
    ConsentDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_CONSENT) == null) {
      queryBuilder = new ConsentDetailQueryBuilder();
      queryBuilder.addColumnConsentId();
      _queries.put(KEY_CONSENT, queryBuilder);
      getQueryAsm().setCreateConsentData(true);
    }
    else {
      queryBuilder = (ConsentDetailQueryBuilder) _queries.get(KEY_CONSENT);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>ConsentDetailQueryBuilder</code> for the ASM query, 
   * creating it if necessary.
   */
  private DonorDetailQueryBuilder getQueryDonor() {
    DonorDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_DONOR) == null) {
      queryBuilder = new DonorDetailQueryBuilder();
      queryBuilder.addColumnDonorId();
      _queries.put(KEY_DONOR, queryBuilder);
      getQueryConsent().setCreateDonorData(true);
    }
    else {
      queryBuilder = (DonorDetailQueryBuilder) _queries.get(KEY_DONOR);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>PathologyEvaluationQueryBuilder</code> for the 
   * LIMS query, creating it if necessary.
   */
  private PathologyEvaluationDetailQueryBuilder getQueryLims() {
    PathologyEvaluationDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_LIMS) == null) {
      queryBuilder = new PathologyEvaluationDetailQueryBuilder();
      queryBuilder.addColumnSlideId();
      queryBuilder.addFilterReportedOnly();
      _queries.put(KEY_LIMS, queryBuilder);
    }
    else {
      queryBuilder = (PathologyEvaluationDetailQueryBuilder) _queries.get(KEY_LIMS);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>PathologyReportDetailQueryBuilder</code> for the 
   * path report query, creating it if necessary.
   */
  private PathologyReportDetailQueryBuilder getQueryPath() {
    PathologyReportDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_PATH) == null) {
      queryBuilder = new PathologyReportDetailQueryBuilder();
      _queries.put(KEY_PATH, queryBuilder);
      addColumnAsmConsentId();
    }
    else {
      queryBuilder = (PathologyReportDetailQueryBuilder) _queries.get(KEY_PATH);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>TissueDetailQueryBuilder</code> for the sample query, 
   * creating it if necessary.
   */
  private TissueDetailQueryBuilder getQuerySample() {
    TissueDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_SAMPLE) == null) {
      queryBuilder = new TissueDetailQueryBuilder();
      queryBuilder.addColumnSampleId();
      _queries.put(KEY_SAMPLE, queryBuilder);
    }
    else {
      queryBuilder = (TissueDetailQueryBuilder) _queries.get(KEY_SAMPLE);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>DiagnosticTestQueryBuilder</code> for the sample query, 
   * creating it if necessary.
   */
  private DiagnosticTestDetailQueryBuilder getQueryDiagnostic(DiagnosticTestFilterDto dto) {
    DiagnosticTestDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_DIAGNOSTIC) == null) {
      queryBuilder = new DiagnosticTestDetailQueryBuilder(dto);
      queryBuilder.addColumnDiagnosticConceptId();
      _queries.put(KEY_DIAGNOSTIC, queryBuilder);
    }
    else {
      queryBuilder = (DiagnosticTestDetailQueryBuilder) _queries.get(KEY_DIAGNOSTIC);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>ImageDetailQueryBuilder</code> for the images query, 
   * creating it if necessary.
   */
  private ImageDetailQueryBuilder getQueryImages() {
    ImageDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_IMAGES) == null) {
      queryBuilder = new ImageDetailQueryBuilder();
      _queries.put(KEY_IMAGES, queryBuilder);
    }
    else {
      queryBuilder = (ImageDetailQueryBuilder) _queries.get(KEY_IMAGES);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>PathologyEvaluationQueryBuilder</code> for the 
   * LIMS query, creating it if necessary.
   */
  private ProjectDetailQueryBuilder getQueryProject() {
    ProjectDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_PROJECT) == null) {
      queryBuilder = new ProjectDetailQueryBuilder();
      _queries.put(KEY_PROJECT, queryBuilder);
    }
    else {
      queryBuilder = (ProjectDetailQueryBuilder) _queries.get(KEY_PROJECT);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>ShoppingCartDetailQueryBuilder</code> for the 
   * shopping cart query, creating it if necessary.
   */
  private ShoppingCartDetailQueryBuilder getQueryShoppingCart() {
    ShoppingCartDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_SHOPPING_CART) == null) {
      queryBuilder = new ShoppingCartDetailQueryBuilder();
      _queries.put(KEY_SHOPPING_CART, queryBuilder);
    }
    else {
      queryBuilder = (ShoppingCartDetailQueryBuilder) _queries.get(KEY_SHOPPING_CART);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>OrderDetailQueryBuilder</code> for the 
   * order query, creating it if necessary.
   */
  private OrderDetailQueryBuilder getQueryOrder() {
    OrderDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_ORDER) == null) {
      queryBuilder = new OrderDetailQueryBuilder();
      queryBuilder.addFilterShippedOnly();
      _queries.put(KEY_ORDER, queryBuilder);
    }
    else {
      queryBuilder = (OrderDetailQueryBuilder) _queries.get(KEY_ORDER);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>LogicalRepositoryDetailQueryBuilder</code> for the 
   * logical repository query, creating it if necessary.
   * <b>The LogicalRepositoryDetailQueryBuilder adds implicit filters to the query that cause
   * it to only return information on logical repositories that the current user is permitted
   * to see.  So, for example, if you use this to get the list of logical repositories that
   * a sample is in, it will include only the ones that the current user can see, which may not
   * be all of the repositories that the sample is actually in.  There are places that currently
   * depend on LogicalRepositoryDetailQueryBuilder behaving this way, see
   * {@link LogicalRepositoryDetailQueryBuilder} for more details.</b>
   * 
   */
  private LogicalRepositoryDetailQueryBuilder getQueryUserLogicalRepository() {
    LogicalRepositoryDetailQueryBuilder queryBuilder =
      (LogicalRepositoryDetailQueryBuilder) _queries.get(KEY_LOGICAL_REPOSITORY);
    if (queryBuilder == null) {
      queryBuilder = new LogicalRepositoryDetailQueryBuilder(_securityInfo);
      _queries.put(KEY_LOGICAL_REPOSITORY, queryBuilder);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>SampleLocationDetailQueryBuilder</code> for the 
   * sample location query, creating it if necessary.
   */
  private SampleLocationDetailQueryBuilder getQuerySampleLocation() {
    SampleLocationDetailQueryBuilder queryBuilder = null;
    if (_queries.get(KEY_SAMPLE_LOCATION) == null) {
      queryBuilder = new SampleLocationDetailQueryBuilder(_securityInfo);
      _queries.put(KEY_SAMPLE_LOCATION, queryBuilder);
    }
    else {
      queryBuilder = (SampleLocationDetailQueryBuilder) _queries.get(KEY_SAMPLE_LOCATION);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>KcDetailQueryBuilder</code> for the 
   * sample kc data query, creating it if necessary.
   */
  private KcDetailQueryBuilder getQuerySampleKcData() {
    KcDetailQueryBuilder queryBuilder = (KcDetailQueryBuilder)_queries.get(KEY_SAMPLE_KC);
    if (queryBuilder == null) {
      queryBuilder = new KcDetailQueryBuilder(TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE);
      _queries.put(KEY_SAMPLE_KC, queryBuilder);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>KcDetailQueryBuilder</code> for the 
   * consent kc data query, creating it if necessary.
   */
  private KcDetailQueryBuilder getQueryConsentKcData() {
    KcDetailQueryBuilder queryBuilder = (KcDetailQueryBuilder)_queries.get(KEY_CONSENT_KC);
    if (queryBuilder == null) {
      queryBuilder = new KcDetailQueryBuilder(TagTypes.DOMAIN_OBJECT_VALUE_CASE);
      _queries.put(KEY_CONSENT_KC, queryBuilder);
      getQueryAsm().setCreateConsentData(true);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>KcDetailQueryBuilder</code> for the 
   * donor kc data query, creating it if necessary.
   */
  private KcDetailQueryBuilder getQueryDonorKcData() {
    KcDetailQueryBuilder queryBuilder = (KcDetailQueryBuilder)_queries.get(KEY_DONOR_KC);
    if (queryBuilder == null) {
      queryBuilder = new KcDetailQueryBuilder(TagTypes.DOMAIN_OBJECT_VALUE_DONOR);
      _queries.put(KEY_DONOR_KC, queryBuilder);
      getQueryConsent().setCreateDonorData(true);
    }
    return queryBuilder;
  }

}
