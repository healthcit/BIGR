package com.ardais.bigr.library.performers;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.btx.framework.BtxTransactionPerformerBase;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.RnaData;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.library.btx.BTXDetailsForSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetKcQueryForm;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummaryNoHistory;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummaryStart;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.btx.BTXDetailsGetSummarySql;
import com.ardais.bigr.library.helpers.ImplicitFilterContext;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterSet;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.filters.RepeatingSingleData;
import com.ardais.bigr.query.filters.RnaFilters;
import com.ardais.bigr.query.filters.SampleFilters;
import com.ardais.bigr.query.generator.FilterConsentNotPulled;
import com.ardais.bigr.query.generator.FilterConsentNotPulledOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterConsentNotRevoked;
import com.ardais.bigr.query.generator.FilterConsentNotRevokedOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterDdcDiagnosisExists;
import com.ardais.bigr.query.generator.FilterDdcDiagnosisExistsOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterExcludeInventoryStatus;
import com.ardais.bigr.query.generator.FilterExcludeInventoryStatusOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterGenreleased;
import com.ardais.bigr.query.generator.FilterGenreleasedOrNotNullAndBmsYNYes;
import com.ardais.bigr.query.generator.FilterHoldSoldStatus;
import com.ardais.bigr.query.generator.FilterInArdaisRepository;
import com.ardais.bigr.query.generator.FilterInArdaisRepositoryOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterLinked;
import com.ardais.bigr.query.generator.FilterLogicalRepository;
import com.ardais.bigr.query.generator.FilterNotInProject;
import com.ardais.bigr.query.generator.FilterNotInProjectOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterNotOnHoldForUser;
import com.ardais.bigr.query.generator.FilterNotOnHoldForUserOrBmsRnaYnYes;
import com.ardais.bigr.query.generator.FilterNotPulled;
import com.ardais.bigr.query.generator.FilterNotPulledOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterPathVerifiedStatus;
import com.ardais.bigr.query.generator.FilterQcInProcessNot;
import com.ardais.bigr.query.generator.FilterRestrictedStatus;
import com.ardais.bigr.query.generator.FilterRnaStatusOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterSalesStatus;
import com.ardais.bigr.query.generator.FilterSalesStatusExists;
import com.ardais.bigr.query.generator.FilterSampleId;
import com.ardais.bigr.query.generator.NoMatchTissueOrDiagnosisException;
import com.ardais.bigr.query.generator.NoMatchingTissuesAndDiagnosesException;
import com.ardais.bigr.query.generator.ProductDetailQueryBuilder;
import com.ardais.bigr.query.generator.ProductSummaryQueryBuilder;
import com.ardais.bigr.query.generator.RnaDetailQueryBuilder;
import com.ardais.bigr.query.generator.RnaSummaryQueryBuilder;
import com.ardais.bigr.query.generator.TissueSummaryQueryBuilder;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.userprofile.QueryProfile;
import com.ardais.bigr.userprofile.UserProf;
import com.ardais.bigr.userprofile.UserProfHome;
import com.ardais.bigr.userprofile.ViewProfile;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.IdValidator;
import com.ardais.bigr.util.UrlUtils;
import com.gulfstreambio.kc.form.def.FormDefinitionQueryCriteria;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BtxPerformerSampleSelection extends BtxTransactionPerformerBase {

  /**
   * Logger for logging performance-related items.
   */
  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  public BtxPerformerSampleSelection() {
    super();
  }

  /**
   * Invoke the specified method on this class. This is only meant to be called from
   * BtxTransactionPerformerBase, please don't call it from anywhere else as a mechanism to gain
   * access to private methods in this class. Every object that the BTX framework dispatches to must
   * contain this method definition, and its implementation should be exactly the same in each
   * class. Please don't alter this method or its implementation in any way.
   */
  protected BTXDetails invokeBtxEntryPoint(Method method, BTXDetails btxDetails) throws Exception {

    // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
    return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
  }

  private TissueSummaryQueryBuilder createTissueSummaryQueryBuilder(
                                                                    BTXDetailsForSampleSummary btxDetails) {
    TissueSummaryQueryBuilder builder = null;

    FilterSet filt = btxDetails.getFilterSet();
    ImplicitFilterContext filterContext = btxDetails.getFilterContext();

    SampleFilters sFilt = new SampleFilters(filt);
    sFilt.validate(btxDetails);
    sFilt = getSecureTissueFilters(btxDetails.getLoggedInUserSecurityInfo(), sFilt, filterContext);
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setTransactionCompleted(false);
      return null;
      // validation errors -- do not process request, return errors inside BTXDetails object
    }
    try { // merge with above error checks later. not now for stability reasons
        builder = new TissueSummaryQueryBuilder(sFilt);
		builder.setSortColumns(btxDetails.getSortColumns());
    }
    catch (NoMatchingTissuesAndDiagnosesException e) {
      updateBtxForNoMatch(btxDetails, e);
      return null;
    }

    return builder;
  }

  private RnaSummaryQueryBuilder createRnaSummaryQueryBuilder(BTXDetailsForSampleSummary btxDetails) {
    RnaSummaryQueryBuilder builder = null;

    FilterSet filt = btxDetails.getFilterSet();
    ImplicitFilterContext filterContext = btxDetails.getFilterContext();

    RnaFilters rFilt = new RnaFilters(filt);
    rFilt.validate(btxDetails);
    rFilt = getSecureRnaFilters(btxDetails.getLoggedInUserSecurityInfo(), rFilt, filterContext);
    if (!btxDetails.getActionErrors().empty()) {
      btxDetails.setTransactionCompleted(false);
      return null;
      // validation errors -- do not process request, return errors inside BTXDetails object
    }

    try {
      builder = new RnaSummaryQueryBuilder(rFilt);
    }
    catch (NoMatchingTissuesAndDiagnosesException e) {
      updateBtxForNoMatch(btxDetails, e);
      return null;
    }

    return builder;
  }

  private BTXDetails performGetKcQueryForm(BTXDetailsGetKcQueryForm btx) {
    FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
        .findFormDefinitionById(btx.getFormDefinitionId());
    btx.setFormDefinition(response.getQueryFormDefinition());

    btx.setActionForwardTXCompleted("success");
    return btx;
  }

  private BTXDetails performGetSampleSummaryStart(BTXDetailsGetSampleSummaryStart btx) {
    SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();
    String account = securityInfo.getAccount();
    
    FormDefinitionQueryCriteria criteria = new FormDefinitionQueryCriteria();
    criteria.addFormType(FormDefinitionTypes.QUERY);
    criteria.addTag(new Tag(TagTypes.ACCOUNT, account));
    criteria.setEnabled(true);

    FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
        .findFormDefinitions(criteria);
    btx.setQueryFormDefinitions(response.getQueryFormDefinitions());

    //get the results form definition id for the tx, so we can default the selected
    //view correctly
    int transaction = 0;
    String txType = btx.getTxType();
    if ("TxSampSel".equalsIgnoreCase(txType)) {
      transaction = ColumnPermissions.TX_SAMP_SELECTION;
    }
    else if ("TxRequestSamples".equalsIgnoreCase(txType)) {
      transaction = ColumnPermissions.TX_REQUEST_SAMP;
    }
    ViewParams vp = new ViewParams(securityInfo, ColumnPermissions.PROD_TISSUE,
        ColumnPermissions.SCRN_SELECTION, transaction);
    ViewProfile viewProfile = null;
    try {
      UserProfHome home = (UserProfHome) EjbHomes.getHome(UserProfHome.class);
      UserProf remote = home.create();
      viewProfile = remote.getViewProfile(securityInfo, vp);
    }
    catch (Exception e) {
      throw new ApiException("Could not get view profile for user", e);
    }
    btx.setResultsFormDefinitionId(viewProfile.getResultsFormDefinitionId());

    //get the list of results form definitions for the user
    btx.setResultsFormDefinitions(FormUtils.getResultsFormDefinitionsForUser(securityInfo, true));

    btx.setActionForwardTXCompleted("success");
    
    if("SelectFromDropdown".equals(btx.getSubActionType()))
      btx.setActionForwardTXCompleted("dropdownSuccess");
    
    return btx;
  }

  private BTXDetails performGetSampleSummary(BTXDetailsGetSampleSummary btx) {
    btx.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

    ProductSummaryQueryBuilder builder = createTissueSummaryQueryBuilder(btx);
    if (builder == null) {
      return btx;
    }

    SampleSelectionSummary ss = builder.getSummary();
    btx.setSampleSelectionSummary(ss);

    return btx;
  }

  private BTXDetails performGetSampleSummarySql(BTXDetailsGetSummarySql btx) {
    btx.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

    ProductSummaryQueryBuilder builder = createTissueSummaryQueryBuilder(btx);
    if (builder == null) {
      return btx;
    }

    String sql = builder.getSummaryQuery();
    btx.setSqlResult(sql);
    return btx;
  }

  private BTXDetails performGetRnaSummary(BTXDetailsGetSampleSummary btx) { // use sample class for
    // RNA also
    btx.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

    ProductSummaryQueryBuilder builder = createRnaSummaryQueryBuilder(btx);
    if (builder == null) {
      return btx;
    }

    SampleSelectionSummary ss = builder.getSummary();
    btx.setSampleSelectionSummary(ss);
    return btx;
  }

  private BTXDetails performGetRnaSummarySql(BTXDetailsGetSummarySql btx) {
    btx.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

    ProductSummaryQueryBuilder builder = createRnaSummaryQueryBuilder(btx);
    if (builder == null) {
      return btx;
    }

    String sql = builder.getSummaryQuery();
    btx.setSqlResult(sql);
    return btx;
  }

  private BTXDetails performGetSummaryNoImplied(BTXDetailsGetSampleSummary btx) {

    SampleSelectionSummary tissSumm = null;
    SampleSelectionSummary rnaSumm = null;

    btx.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));

    FilterSet filt = btx.getFilterSet();

    {
      SampleFilters sFilt = new SampleFilters(filt);
      sFilt.validate(btx);
      if (!btx.getActionErrors().empty()) {
        btx.setTransactionCompleted(false);
        return btx;
        // validation errors -- do not process request, return errors inside BTXDetails object
      }

      if (!sFilt.isEmpty()) {
        TissueSummaryQueryBuilder builder = null;
        try {
          builder = new TissueSummaryQueryBuilder(sFilt);
        }
        catch (NoMatchingTissuesAndDiagnosesException e) {
          updateBtxForNoMatch(btx, e);
          return btx;
        }
        tissSumm = builder.getSummary();
      }
    }

    {
      RnaFilters rFilt = new RnaFilters(filt);
      rFilt.validate(btx);
      if (!btx.getActionErrors().empty()) {
        btx.setTransactionCompleted(false);
        return btx;
        // validation errors -- do not process request, return errors inside BTXDetails object
      }

      if (!rFilt.isEmpty()) {
        RnaSummaryQueryBuilder builder = null;
        try {
          builder = new RnaSummaryQueryBuilder(rFilt);
        }
        catch (NoMatchingTissuesAndDiagnosesException e) {
          updateBtxForNoMatch(btx, e);
          return btx;
        }
        rnaSumm = builder.getSummary();
      }
    }

    // combine the two summaries, ignoring null summaries (from empty queries)
    SampleSelectionSummary ss;
    if (rnaSumm == null && tissSumm != null)
      ss = tissSumm;
    else if (rnaSumm != null && tissSumm == null)
      ss = rnaSumm;
    else
      ss = tissSumm.plus(rnaSumm);

    btx.setSampleSelectionSummary(ss);
    return btx;
  }

  private List getSamples(String[] sampleIds, SecurityInfo securityInfo,
                          DiagnosticTestFilterDto dto, ViewProfile viewProfile) {
    // Create the query builder and add all of the columns.
    //        SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();
    ProductDetailQueryBuilder qb = new ProductDetailQueryBuilder(securityInfo);
    qb.addColumnAgeAtCollection();
    qb.addColumnSampleAppearanceBest();
    qb.addColumnAsmId();
    qb.addColumnAsmPosition();
    qb.addColumnDdcDiagnosis();
    qb.addColumnDdcDiagnosisOther();
    qb.addColumnConsentId();
    qb.addColumnCompositionLesion();
    qb.addColumnCompositionNecrosis();
    qb.addColumnCompositionNormal();
    qb.addColumnCompositionAcellularStroma();
    qb.addColumnCompositionCellularStroma();
    qb.addColumnCompositionTumor();
    qb.addColumnConsentCustomerId();
    qb.addColumnCustomerId();
    qb.addColumnDonorCustomerId();
    qb.addColumnDonorId();
    qb.addColumnGender();
    qb.addColumnRace();
    qb.addColumnGleasonScore();
    qb.addColumnHistologicNuclearGrade();
    qb.addColumnTumorStageType();
    qb.addColumnTumorStage();
    qb.addColumnLymphNodeStage();
    qb.addColumnDistantMetastasis();
    qb.addColumnStageGrouping();
    qb.addColumnPathId();
    qb.addColumnPvNotes();
    qb.addColumnQcVerified();
    qb.addColumnRestricted();
    qb.addColumnSampleId();
    qb.addColumnSampleFormatDetail();
    qb.addColumnSampleFixativeType();
    qb.addColumnSampleType();
    qb.addColumnSampleSource();
    qb.addColumnSampleTissueOrigin();
    qb.addColumnSampleTissueOriginOther();
    qb.addColumnSampleSubdivisionDate();
    qb.addColumnSampleBoxBarcodeId();
    qb.addColumnSampleVolume();
    qb.addColumnSampleWeight();
    qb.addColumnSampleBufferType();
    qb.addColumnSampleTotalNumOfCells();
    qb.addColumnSampleCellsPerMl();
    qb.addColumnSampleConcentration();
    qb.addColumnSampleYield();
    qb.addColumnSamplePercentViability();
    qb.addColumnSampleDateOfCollection();
    qb.addColumnSampleDateOfPreservation();
    qb.addColumnSampleElapsedTime();
    qb.addColumnLimsDiagnosis();
    qb.addColumnLimsDiagnosisOther();
    qb.addColumnAsmTissue();
    qb.addColumnAsmModuleComments();
    qb.addColumnAsmTissueOther();
    qb.addColumnDdcTissueFinding();
    qb.addColumnDdcTissueFindingOther();
    qb.addColumnLimsTissueFinding();
    qb.addColumnLimsTissueFindingOther();
    qb.addColumnDdcTissueOrigin();
    qb.addColumnDdcTissueOriginOther();
    qb.addColumnLimsTissueOrigin();
    qb.addColumnLimsTissueOriginOther();
    qb.addMandatoryLogicalRepositoryColumns();
    qb.addColumnUserLogicalRepositoryShortNames();
    qb.addColumnBmsYN();
    qb.addDiagnosticTest(dto);
    qb.addColumnAsmProcedure();
    qb.addColumnAsmProcedureOther();
    qb.addColumnAsmPreparedBy();

    //because pulled samples can now be returned by Donor Institutions (see MR6723 for details)
    //the following 3 pull related columns should be returned if the user is an Ardais user or
    //a donor institution user.
    //Also the sample cell_ref_location should be returned for these roles, so that picklists
    //for requests can be generated. The cell_ref_location doesn't appear in sample selection, but
    //this code is called by the RequestFinder class to get information about the items on a
    // request.
    if (securityInfo.isInRoleSystemOwnerOrDi()) {
      qb.addColumnPullYN();
      qb.addColumnPulledReason();
      qb.addColumnPullDate();
      qb.addColumnCellRefLocation();
      qb.addColumnInventoryStatus();
      qb.addColumnSalesStatus();
      qb.addColumnProject();
      qb.addColumnProjectDateRequested();
      qb.addColumnProjectStatus();
      qb.addColumnShoppingCartUser();
      qb.addColumnShoppingCartCreationDate();
      qb.addColumnRnaId();
      qb.addColumnTmaId();
      qb.addColumnIltdsDiagnosis();
      qb.addColumnIltdsDiagnosisOther();
      qb.addColumnSampleAsmNote(); // MR 8390
      qb.addColumnDonorComments(); //MR 8390
    }

    if (securityInfo.isInRoleSystemOwner()) {
      qb.addColumnConsentLocation();
      qb.addColumnPvNotesInternal();
      qb.addColumnQcStatus();
      qb.addColumnHistoNotes();
      qb.addColumnDiscordantYN();
      qb.addColumnQCPostedYN();
      qb.addColumnReleasedYN();
      qb.addColumnOrderDescription();
      qb.addColumnSampleSectionCount();
      qb.addColumnDonorConsentCount();
      qb.addColumnDonorComments();
      qb.addColumnSampleAsmNote();
      qb.addColumnAsmModuleComments();
    }

    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_PSA_DRE)) {
      qb.addColumnPsa(dto);
      qb.addColumnDre(dto);
    }

    // for users that are local to repositories, we want to get the local info
    if (IltdsUtils.isUserLocaltoStorageUnit(securityInfo.getUsername())) {
      qb.addColumnSampleLocation();
    }

    long tStart = 0;
    if (_perfLog.isDebugEnabled()) {
      _perfLog.debug("   B1: START BTXSS.getSamples KC Form Processing");
      tStart = System.currentTimeMillis();
    }

    //handle the KC data to be retrieved, if any
    BigrFormDefinition bigrResultsFormDef = viewProfile.getResultsFormDefinition();
    if (bigrResultsFormDef != null) {
      ResultsFormDefinition resultsFormDefinition = (ResultsFormDefinition) bigrResultsFormDef.getKcFormDefinition();
      ResultsFormDefinitionDataElement[] dataElements = resultsFormDefinition.getResultsDataElements();
      if (dataElements.length > 0) {
        // Walk all the data elements, and for each one do the following:
        //1) determine the id of it's parent data form definition
        //2) retrieve the parent form definition if we haven't already done so
        //3) get the domain object type from the parent form definition
        //4) add the data element to the appropriate kc detail query builder
        Map parentFormDefMap = new HashMap();
        for (int i = 0; i < dataElements.length; i++) {
          ResultsFormDefinitionDataElement dataElement = dataElements[i];
          String cui = dataElement.getCui();
          //1) determine the id of it's parent data form definition
          String parentFormDefinitionId = null;
          Tag[] tags = dataElement.getTags();
          for (int j = 0; j < tags.length; j++) {
            Tag tag = tags[j];
            if (TagTypes.PARENT.equalsIgnoreCase(tag.getType())) {
              parentFormDefinitionId = tag.getValue();
              break;
            }
          }
          //2) retrieve the parent form definition if we haven't already done so
          BigrFormDefinition dataFormDef = (BigrFormDefinition) parentFormDefMap.get(parentFormDefinitionId);
          if (dataFormDef == null) {
            dataFormDef = FormUtils.getFormDefinition(securityInfo, parentFormDefinitionId);
            parentFormDefMap.put(parentFormDefinitionId, dataFormDef);
          }
          //3) get the domain object type from the parent form definition
          //4) add the data element to the appropriate kc detail query builder
          qb.addColumnKcData(cui, dataFormDef.getObjectType());
        }
      }
    }

    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStart;
      _perfLog.debug("   B1: END   BTXSS.getSamples KC Form Processing (" + elapsedTime + " ms)");
    }

    // For each id, create a ProductDto bean that is keyed by the
    // id, and insert the data bean into a List to preserve the order and
    // Map for the call to the query builder.
    //        String[] sampleIds = btx.getSampleIds();
    List sampleSelectionBeans = new ArrayList();
    Map sampleMap = new HashMap();
    int numIds = sampleIds.length;
    for (int i = 0; i < numIds; i++) {
      String id = sampleIds[i];

      SampleData sample = new SampleData();
      sample.setSampleId(id);
      sampleMap.put(id, sample);

      ProductDto dataBean = new ProductDto();
      dataBean.setSampleData(sample);
      sampleSelectionBeans.add(dataBean);
    }

    qb.getDetails(sampleMap);

    return sampleSelectionBeans;
  }

  private List getRna(String[] rnaIds, SecurityInfo securityInfo, DiagnosticTestFilterDto dto) { // BTXDetailsGetRna
    // btx)
    // {
    //        btx.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));

    // Create the query builder and add all of the columns.
    //        SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();
    RnaDetailQueryBuilder qb = new RnaDetailQueryBuilder(securityInfo);
    qb.addColumnAgeAtCollection();
    qb.addColumnSampleAppearanceBest();
    qb.addColumnAsmId();
    qb.addColumnAsmPosition();
    qb.addColumnBmsYN();
    qb.addColumnRnaBmsYN();
    qb.addColumnDdcDiagnosis();
    qb.addColumnDdcDiagnosisOther();
    qb.addColumnConsentId();
    qb.addColumnConsentCustomerId();
    qb.addColumnDonorId();
    qb.addColumnDonorCustomerId();
    qb.addColumnRnaPrep();
    qb.addColumnRnaRatio();
    qb.addColumnGender();
    qb.addColumnGleasonScore();
    qb.addColumnCompositionLesion();
    qb.addColumnCompositionNecrosis();
    qb.addColumnCompositionNormal();
    qb.addColumnCompositionAcellularStroma();
    qb.addColumnCompositionCellularStroma();
    qb.addColumnCompositionTumor();
    qb.addColumnConsentCustomerId();
    qb.addColumnCustomerId();
    qb.addColumnDonorCustomerId();
    qb.addColumnHistologicNuclearGrade();
    qb.addColumnTumorStageType();
    qb.addColumnTumorStage();
    qb.addColumnLymphNodeStage();
    qb.addColumnDistantMetastasis();
    qb.addColumnStageGrouping();
    qb.addColumnPathId();
    qb.addColumnPvNotes();
    qb.addColumnQcVerified();
    qb.addColumnRestricted();
    qb.addColumnSampleFormatDetail();
    qb.addColumnSampleFixativeType();
    qb.addColumnLimsDiagnosis();
    qb.addColumnLimsDiagnosisOther();
    qb.addColumnAsmTissue();
    qb.addColumnAsmModuleComments();
    qb.addColumnAsmTissueOther();
    qb.addColumnDdcTissueFinding();
    qb.addColumnDdcTissueFindingOther();
    qb.addColumnLimsTissueFinding();
    qb.addColumnLimsTissueFindingOther();
    qb.addColumnDdcTissueOrigin();
    qb.addColumnDdcTissueOriginOther();
    qb.addColumnLimsTissueOrigin();
    qb.addColumnLimsTissueOriginOther();
    qb.addColumnSampleTissueOrigin();
    qb.addColumnSampleTissueOriginOther();
    qb.addColumnRnaRemainingVolume(); // not visible, but needed to validate hold requests
    qb.addColumnRnaConcentration(); // not visible, but needed to validate hold requests
    qb.addColumnRnaHoldAmount(); // also used to compute amtRemaining for hold req.
    qb.addColumnRnaRepSample();
    qb.addMandatoryLogicalRepositoryColumns();
    qb.addColumnUserLogicalRepositoryShortNames();
    qb.addDiagnosticTest(dto);

    if (securityInfo.isInRoleSystemOwner()) {
      qb.addColumnConsentLocation();
      qb.addColumnIltdsDiagnosis();
      qb.addColumnIltdsDiagnosisOther();
      qb.addColumnPvNotesInternal();
      qb.addColumnNotes();
      qb.addColumnRnaQuality();
      qb.addColumnRnaStatus();
      qb.addColumnRnaProject();
      qb.addColumnRnaVialId();
      qb.addColumnRnaPooledTissue();
      qb.addColumnRnaCaseExhausted();
      qb.addColumnShoppingCartUser();
      qb.addColumnShoppingCartCreationDate();
      qb.addColumnDonorConsentCount();
      qb.addColumnDonorComments();
    }

    // MR 8053
    if (securityInfo.isInRoleDi()) {
      qb.addColumnShoppingCartUser();
      qb.addColumnShoppingCartCreationDate();
      qb.addColumnNotes();
    }

    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_PSA_DRE)) {
      qb.addColumnPsa(dto);
      qb.addColumnDre(dto);
    }

    // For each id, create a ProductDto bean that is keyed by the
    // id, and insert the data bean into a List to preserve the order and
    // Map for the call to the query builder.
    //        String[] rnaIds = btx.getIds();
    List sampleSelectionBeans = new ArrayList();
    Map rnaMap = new HashMap();
    int numIds = rnaIds.length;
    for (int i = 0; i < numIds; i++) {
      String id = rnaIds[i];

      RnaData rna = new RnaData();
      rna.setRnaVialId(id);
      Object oldval = rnaMap.put(id, rna);
      // if we are erasing an existing map value, it will not get data populated
      if (oldval != null)
          throw new ApiException("Attempt to retrieve duplicate data for RNA: " + id);

      ProductDto dataBean = new ProductDto();
      dataBean.setRnaData(rna);
      sampleSelectionBeans.add(dataBean);
    }
    qb.getDetails(rnaMap);
    return sampleSelectionBeans;
    //        btx.setRnaDetailsResult(sampleSelectionBeans);
    //        return btx;
  }

  /**
   * Retreive the full data for the ids specified.
   */
  private BTXDetailsGetSamples performGetDetails(BTXDetailsGetSamples btx) {

    btx.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

    String[] ids = btx.getSampleIds();
    SecurityInfo secInfo = btx.getLoggedInUserSecurityInfo();

    // divide up ids into separate lists for the Tissue and RNA queryies
    String[] tissIds = IdValidator.validSampleIds(ids);
    String[] rnaIds = IdValidator.validRnaIds(ids);

    List results = new ArrayList();

    //Determine what view will be used to retrieve/display the data.
    //if a results form definition was explicitly provided, use that.  This is currently only
    //used by label printing functionality and request picklist functionality and intentionally 
    //does not persist the view to the user profile.  The results forms used by label printing are 
    //maintained in a configuration file, and thus are not recognizable as persisted forms by BIGR.
    //The request pick list functionality should not impact any persisted choice of results form.
    ViewProfile viewProfile = null;
    if (btx.getResultsFormDefinition() != null) {
      viewProfile = new ViewProfile(secInfo);
      viewProfile.setResultsFormDefinition(btx.getResultsFormDefinition());
    }
    //if no results form definition was specified, see if a results form definition id was
    //specified, otherwise use the last view used.
    else  {
      //if a view id was specified, and that id corresponds to an existing results form for the
      //current user, store it as the one to use going forward and use it to retrieve/display the
      //data. If the product being displayed is RNA then ignore the id passed in because for RNA
      //the user cannot specify the view (we use a canned view for RNA)
      long tSetFormDef = 0;
      long tSetViewProfile = 0;
      ViewParams vp = btx.getViewParams();
      boolean isRNA = (vp != null && vp.isProduct(ColumnPermissions.PROD_RNA));
      String resultsFormDefinitionId = btx.getResultsFormDefinitionId();
      if (!ApiFunctions.isEmpty(resultsFormDefinitionId) && !isRNA) {
        tSetFormDef = System.currentTimeMillis();
        viewProfile = new ViewProfile(secInfo);
        viewProfile.setResultsFormDefinitionId(resultsFormDefinitionId);
        // ViewProfile.getResultsFormDefinition returning null indicates
        // that the resultsFormDefinitionId in the view profile is invalid.)
        BigrFormDefinition resultsForm = viewProfile.getResultsFormDefinition();
        tSetFormDef = System.currentTimeMillis() - tSetFormDef;
        if (resultsForm == null) {
          // Failed to get specified view, presumably because the specified resultsFormDefinitionId
          // is no longer valid.  This can happen, for example, because the view referenced data
          // elements that no longer exist.
          viewProfile = null;
        }
        else {
          // Save the last view used to the user's profile.
          try {
            tSetViewProfile = System.currentTimeMillis();
            UserProfHome home = (UserProfHome) EjbHomes.getHome(UserProfHome.class);
            UserProf remote = home.create();
            remote.setViewProfile(secInfo, vp, viewProfile);
            tSetViewProfile = System.currentTimeMillis() - tSetViewProfile;
          }
          catch (Exception e) {
            throw new ApiException("Could not set view for user", e);
          }
        }
      }
  
      if (_perfLog.isDebugEnabled()) {
        _perfLog.debug("       setFD " + tSetFormDef + ", setVP "
            + tSetViewProfile);
      }
      
      //otherwise, determine the view to use from the information in the user profile
      if (viewProfile == null) {
        try {
          UserProfHome home = (UserProfHome) EjbHomes.getHome(UserProfHome.class);
          UserProf remote = home.create();
          viewProfile = remote.getViewProfile(secInfo, vp);
        }
        catch (Exception e) {
          throw new ApiException("Could not get view profile for user", e);
        }
      }
    }

    //return the view profile, so it can used to render the results
    btx.setViewProfile(viewProfile);

    if (tissIds.length > 0) {
      results = getSamples(tissIds, secInfo, btx.getDiagnosticTestFilterDto(), viewProfile);
    }

    if (rnaIds.length > 0) {
      List rnaResults = getRna(rnaIds, secInfo, btx.getDiagnosticTestFilterDto());
      results.addAll(rnaResults);
    }

    //get any urls defined for the account to which the user belongs that
    //we will show in the sample selection results window. This consists of
    //all urls defined for the account that are not menu based.
    List desiredTypes = new ArrayList();
    Iterator iterator = UrlUtils.VALID_OBJECT_TYPES.iterator();
    while (iterator.hasNext()) {
      String val = (String) iterator.next();
      if (!val.equalsIgnoreCase(UrlUtils.OBJECT_TYPE_MENU)) {
        desiredTypes.add(val);
      }
    }
    List urls = UrlUtils.getUrlsByAccountId(btx.getUserAccount(), desiredTypes);

    // return the combined list of results from the tissue and RNA queries
    btx.setSampleDetailsResult(results);
    btx.setUrls(urls);
    return btx;
  }

  /**
   * Set the Unavailable Sample ID collection on the btx to the sample IDs that are not available
   * for Pick List generation.
   */
  private BTXDetailsAddToHoldList performSetUnavailableForPickList(BTXDetailsAddToHoldList btx)
      throws Exception {

    SecurityInfo securityInfo = btx.getLoggedInUserSecurityInfo();
    // verify that we have some Tissue samples to screen for.
    String[] tissIds = btx.getSamples(); // must be all tissue IDs
    if (tissIds.length == 0) return btx;

    FilterSet filters = new QueryProfile();
    Filter filt = new FilterSampleId(tissIds);
    filters.addFilter(filt);

    filters.addFilter(new FilterConsentNotPulledOrBmsYNYes());
    filters.addFilter(new FilterConsentNotRevokedOrBmsYNYes());
    filters.addFilter(new FilterInArdaisRepository());
    filters.addFilter(new FilterSalesStatusExists());
    filters.addFilter(new FilterQcInProcessNot());
    filters.addFilter(new FilterExcludeInventoryStatus(new String[] { FormLogic.SMPL_RNDREQUEST,
        FormLogic.SMPL_REQUESTED }));
    //MR7027 - instead of disallowing all pulled samples, allow pulled BMS samples
    filters.addFilter(new FilterNotPulledOrBmsYNYes());

    BTXDetailsGetSampleSummaryNoHistory btxSumm = new BTXDetailsGetSampleSummaryNoHistory();
    btxSumm.setFilterSet(filters);
    btxSumm.setProductDescription("HoldTissueSummary");
    btxSumm.setLoggedInUserSecurityInfo(securityInfo, true);
    btxSumm = (BTXDetailsGetSampleSummaryNoHistory) Btx.perform(btxSumm,
        "library_get_sample_summary");

    String[] availIds = btxSumm.getSampleSelectionSummary().getIds();
    String[] unavailIds = ApiFunctions.copyExcept(tissIds, availIds);

    btx.setUnavailSamples(unavailIds);
    return btx;
  }

  // -----------------------------------------------------------------------------
  // -- Methods to add implied filters and security checks to summary queries ----
  // -----------------------------------------------------------------------------

  /**
   * Return a version of the SampleFilters that have so-called implied filters added. These filters
   * add security restrictions, based on roles, and also business rule restrictions, such as only
   * showing samples that have not been pulled.
   * 
   * @param securityInfo the SecurityInfo with the Role to determine what should be returned
   * @param tf the tissue filters (SampleFilters) passed in
   * @return a copy of tf with additional filters set. Note this method does not modify the tf
   *         object passed in.
   */
  private SampleFilters getSecureTissueFilters(SecurityInfo securityInfo, SampleFilters tissFilt,
                                               ImplicitFilterContext filterContext) {

    if (filterContext == null) {
      filterContext = ImplicitFilterContext.DEFAULT;
    }

    // If the user has not already specified a set of logical repositories to restrict the
    // results by, and they don't have the "View All Logical Repositories" privilege,
    // restrict the samples to only those samples belonging to repositories to which the user
    // has access.
    if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)
        && !tissFilt.hasFilter(FilterConstants.KEY_LOGICAL_REPOSITORY)) {
      RepeatingSingleData singleData = new RepeatingSingleData(securityInfo
          .getLogicalRepositoriesIdByFullName());
      RepeatingFilterData filterData = new RepeatingFilterData();
      filterData.add(singleData);
      tissFilt.addFilter(new FilterLogicalRepository(filterData)); // GIF #1 (aka DRK #7)
    }

    String userId = securityInfo.getUsername();
    String account = securityInfo.getAccount();

    // Supply account info if this filter does not have its account
    FilterRestrictedStatus fres = (FilterRestrictedStatus) tissFilt
        .getFilter(FilterConstants.KEY_RESTRICTED_FOR_DI);
    // GIF#2
    if (fres != null && !fres.hasAccount()) {
      fres.setAccount(account);
    }

    // Supply user id and account key
    FilterHoldSoldStatus fHoldSold = (FilterHoldSoldStatus) tissFilt
        .getFilter(FilterConstants.KEY_HOLD_SOLD_STATUS);
    if (fHoldSold != null) {
      if (!fHoldSold.hasUserId()) {
        fHoldSold.setUserId(userId);
      }
      if (!fHoldSold.hasAccountKey()) {
        fHoldSold.setAccountKey(account);
      }

      if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_HOLDSOLD_ACCOUNT)) {
        fHoldSold.setAccountPrivilege(true);
      }
      if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_HOLDSOLD_USER)) {
        fHoldSold.setUserPrivilege(true);
      }
    }

    // If we're in the ON_HOLD filter context, then we're assuming that we're operating on
    // subsets of samples that the user already has on hold. In this situation, we add
    // an implicit filter that the sales status is ADDTOCART (on hold). It would
    // be better to add a filter that says it is on the current user's cart (not just any cart),
    // but as long as this context is used as intended (for subsets of samples ids that are
    // known to be on hold by the current user already), it is ok.
    // In other contexts, we add an implicit filter that samples are GENRELEASED for non-Ardais
    // users.
    //
    if (ImplicitFilterContext.ON_HOLD.equals(filterContext)) {
      tissFilt.addFilter(new FilterSalesStatus(new String[] { FormLogic.SMPL_ADDTOCART }));
      // GIF#3
    }

    // If the user is restricted to linked cases only (MR7376), enforce that
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_LINKED_CASES_ONLY)) {
      Filter linkedCases = new FilterLinked(Constants.LINKED_LINKED); // GIF #4
      tissFilt.addFilter(linkedCases);
    }

    addImplicitTissueFilterArdais(securityInfo, tissFilt, filterContext);
    addImplicitTissueFilterDonorInstitution(securityInfo, tissFilt, filterContext);
    addImplicitTissueFilterCustomer(securityInfo, tissFilt, filterContext);

    return tissFilt;
  }

  private void addImplicitTissueFilterArdais(SecurityInfo securityInfo, SampleFilters tissFilt,
                                             ImplicitFilterContext filterContext) {
    if (securityInfo.isInRoleSystemOwner()) {
      // MR7179. We can no longer rely on samples having arrived at Ardais before they can
      // be visible in Sample Selection - BMS customers may not want their samples to ever come
      // to Ardais. Therefore, remove the "Received At Ardais" filter and replace it with a filter
      // on the existance of a sales status. Samples will not have a sales status until a) they
      // arrive at Ardais or b) the case they belong to is released (and verified if linked) and
      // that case has apolicy of no samples being sent to Ardais. Since the non-Ardais roles are
      // already looking for eithe a GENRELEASED or ADDTOCART sales status, make this filter
      // to Ardais users only.
      tissFilt.addFilter(new FilterSalesStatusExists());
    }
  }

  private void addImplicitTissueFilterDonorInstitution(SecurityInfo securityInfo,
                                                       SampleFilters tissFilt,
                                                       ImplicitFilterContext filterContext) {
    if (securityInfo.isInRoleDi()) {

      // If the user is a DI user, allow pulled BMS samples
      tissFilt.addFilter(new FilterNotPulledOrBmsYNYes()); // DRK #8

      tissFilt.addFilter(new FilterExcludeInventoryStatusOrBmsYNYes(new String[] {
          FormLogic.SMPL_RNDREQUEST, FormLogic.SMPL_CORND, FormLogic.SMPL_REQUESTED,
          FormLogic.SMPL_CHECKEDOUT, FormLogic.SMPL_ARCOCASEPULL, FormLogic.SMPL_ARCOCONSREV,
          FormLogic.SMPL_ARCOOTHER, FormLogic.SMPL_COCONSUMED }));
      // DRK #11
      // No filter on GENRELEASED in the ON_HOLD filter context. See below for more details.
      if (!ImplicitFilterContext.ON_HOLD.equals(filterContext)) {
        //due to the point in time that we assign a sample a sales_status value,
        //only return those samples that either have a GENRELEASED sales_status value
        //or have a non-null sales_status value and are BMS.
        //WE DON'T WANT TO RETURN ANY NON-IMPORTED SAMPLES THAT BELONG TO A CASE
        //THAT HAS NOT BEEN CASE RELEASED (AND VERIFIED IF LINKED), SO DO NOT CHANGE THIS FILTER
        //WITHOUT ENSURING THIS IS STILL THE CASE (PROBABLY BY CHANGING THE CODE IN
        //IltdsUtils.applyPolicyToSample)!!!
        tissFilt.addFilter(new FilterGenreleasedOrNotNullAndBmsYNYes()); // DRK #13
      }

      tissFilt.addFilter(new FilterNotInProjectOrBmsYNYes()); // DRK #12
      tissFilt.addFilter(new FilterConsentNotRevokedOrBmsYNYes()); // DRK #1
      tissFilt.addFilter(new FilterConsentNotPulledOrBmsYNYes()); // DRK #2
      tissFilt.addFilter(new FilterInArdaisRepositoryOrBmsYNYes()); // DRK #5
      tissFilt.addFilter(new FilterDdcDiagnosisExistsOrBmsYNYes()); // DRK #3
    }
  }

  private void addImplicitTissueFilterCustomer(SecurityInfo securityInfo, SampleFilters tissFilt,
                                               ImplicitFilterContext filterContext) {
    if (securityInfo.isInRoleCustomer()) {

      // If the user is a customer, filter out pulled samples.
      tissFilt.addFilter(new FilterNotPulled());

      tissFilt.addFilter(new FilterExcludeInventoryStatus(new String[] { FormLogic.SMPL_RNDREQUEST,
          FormLogic.SMPL_CORND, FormLogic.SMPL_REQUESTED, FormLogic.SMPL_CHECKEDOUT,
          FormLogic.SMPL_ARCOCASEPULL, FormLogic.SMPL_ARCOCONSREV, FormLogic.SMPL_ARCOOTHER,
          FormLogic.SMPL_COCONSUMED }));

      // No filter on GENRELEASED in the ON_HOLD filter context. See below for more details.
      if (!ImplicitFilterContext.ON_HOLD.equals(filterContext)) {
        tissFilt.addFilter(new FilterGenreleased());
      }

      tissFilt.addFilter(new FilterNotInProject());
      tissFilt.addFilter(new FilterConsentNotRevoked());
      tissFilt.addFilter(new FilterConsentNotPulled());
      tissFilt.addFilter(new FilterInArdaisRepository());
      tissFilt.addFilter(new FilterDdcDiagnosisExists());

      String account = securityInfo.getAccount();
      tissFilt.addFilter(new FilterRestrictedStatus(Constants.RESTRICTED_U, account));
    }
  }

  /**
   * Return a version of the RnaFilters that have so-called implied filters added. These filters add
   * security restrictions, based on roles, and also business rule restrictions, such as only
   * showing samples that have not been pulled.
   * 
   * @param securityInfo the SecurityInfo with the Role to determine what should be returned
   * @param oldRf the RnaFilters passed in
   * @return a new copy of oldRf with additional filters set. Note this method does not modify the
   *         oldRf object passed in.
   */
  private RnaFilters getSecureRnaFilters(SecurityInfo securityInfo, RnaFilters rnaFilt,
                                         ImplicitFilterContext filterContext) {

    // MR 7869 specified various implicit filter changes for RNA.

    if (filterContext == null) {
      filterContext = ImplicitFilterContext.DEFAULT;
    }

    String accountName = securityInfo.getAccount();

    //if the user has not already specified a set of logical repositories to restrict the
    //results by, and they don't have the "View All Logical Repositories" privilege,
    //restrict the rna to only those vials belonging to repositories to which the user
    //has access
    if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)
        && !rnaFilt.hasFilter(FilterConstants.KEY_LOGICAL_REPOSITORY)) {
      RepeatingSingleData singleData = new RepeatingSingleData(securityInfo
          .getLogicalRepositoriesIdByFullName());
      RepeatingFilterData filterData = new RepeatingFilterData();
      filterData.add(singleData);
      rnaFilt.addFilter(new FilterLogicalRepository(filterData)); // RGIF#3
    }

    rnaFilt.addFilter(new FilterPathVerifiedStatus(Constants.PV_STATUS_PVED)); // RGIF#6

    // if restricted status needs an account, supply it
    FilterRestrictedStatus fres = (FilterRestrictedStatus) rnaFilt
        .getFilter(FilterConstants.KEY_RESTRICTED_FOR_DI);
    // RGIF#4
    if (fres != null && !fres.hasAccount()) {
      fres.setAccount(accountName);
    }

    // Supply user id and account key
    String userId = securityInfo.getUsername();
    String account = securityInfo.getAccount();
    FilterHoldSoldStatus fHoldSold = (FilterHoldSoldStatus) rnaFilt
        .getFilter(FilterConstants.KEY_HOLD_SOLD_STATUS);
    if (fHoldSold != null && !fHoldSold.hasUserId()) {
      fHoldSold.setUserId(userId);
    }
    if (fHoldSold != null && !fHoldSold.hasAccountKey()) {
      fHoldSold.setAccountKey(account);
    }

    // Add implicit filters on inventory status.
    // Only do this if the "exclude implicit RNA filters" filter is not in the filter list (MR6393)
    // This filter doesn't exclude all implicit filters, so it isn't really well-named.
    // It just excludes the ones described in MR6393 relating to inventory status.
    if (!rnaFilt.hasFilter(FilterConstants.KEY_EXCLUDE_IMPLICIT_RNA_FILTERS)) {
      Filter rnaStatus = new FilterRnaStatusOrBmsYNYes(new String[] { "Available", "Residual",
          "Low Yield" });
      // GIF#1
      rnaFilt.addFilter(rnaStatus);
    }

    //if the user is restricted to linked cases only (MR7376), enforce that
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_LINKED_CASES_ONLY)) {
      Filter linkedCases = new FilterLinked(Constants.LINKED_LINKED); // RGIF#7
      rnaFilt.addFilter(linkedCases);
    }

    addImplicitRnaFilterArdais(securityInfo, rnaFilt, filterContext);
    addImplicitRnaFilterDonorInstitution(securityInfo, rnaFilt, filterContext);
    addImplicitRnaFilterCustomer(securityInfo, rnaFilt, filterContext);

    return rnaFilt;
  }

  private void addImplicitRnaFilterArdais(SecurityInfo securityInfo, RnaFilters rnaFilt,
                                          ImplicitFilterContext filterContext) {
    if (securityInfo.isInRoleSystemOwner()) {
    }
  }

  private void addImplicitRnaFilterDonorInstitution(SecurityInfo securityInfo, RnaFilters rnaFilt,
                                                    ImplicitFilterContext filterContext) {
    if (securityInfo.isInRoleDi()) {
      rnaFilt.addFilter(new FilterNotOnHoldForUserOrBmsRnaYnYes(securityInfo.getUsername()));
    }
  }

  private void addImplicitRnaFilterCustomer(SecurityInfo securityInfo, RnaFilters rnaFilt,
                                            ImplicitFilterContext filterContext) {
    if (securityInfo.isInRoleCustomer()) {
      rnaFilt.addFilter(new FilterNotOnHoldForUser(securityInfo.getUsername())); // RDIIF#4
    }
  }

  private void updateBtxForNoMatch(BTXDetails btx, NoMatchingTissuesAndDiagnosesException e) {
    btx.setTransactionCompleted(false);
    List errs = e.getExceptions();
    for (int i = 0; i < errs.size(); i++) {
      NoMatchTissueOrDiagnosisException err = (NoMatchTissueOrDiagnosisException) errs.get(i);
      btx.addActionError(new BtxActionError(err.getErrorCode(), err.getFilterDescription(), err
          .getPattern()));
    }
  }

}
