package com.ardais.bigr.query.filters;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.query.generator.FilterAgeAtCollection;
import com.ardais.bigr.query.generator.FilterAsciiReportContains;
import com.ardais.bigr.query.generator.FilterBmsYN;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisBest;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisDdc;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisIltds;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisLikeBest;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisLikeDdc;
import com.ardais.bigr.query.generator.FilterCaseDiagnosisLikeIltds;
import com.ardais.bigr.query.generator.FilterCompositionAcellularStroma;
import com.ardais.bigr.query.generator.FilterCompositionCellularStroma;
import com.ardais.bigr.query.generator.FilterCompositionLesion;
import com.ardais.bigr.query.generator.FilterCompositionNecrosis;
import com.ardais.bigr.query.generator.FilterCompositionNormal;
import com.ardais.bigr.query.generator.FilterCompositionTumor;
import com.ardais.bigr.query.generator.FilterConsentAliasId;
import com.ardais.bigr.query.generator.FilterConsentId;
import com.ardais.bigr.query.generator.FilterConsentNotPulled;
import com.ardais.bigr.query.generator.FilterConsentNotPulledOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterConsentNotRevoked;
import com.ardais.bigr.query.generator.FilterConsentNotRevokedOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterDateOfCollection;
import com.ardais.bigr.query.generator.FilterDateReceived;
import com.ardais.bigr.query.generator.FilterDdcDiagnosisExists;
import com.ardais.bigr.query.generator.FilterDdcDiagnosisExistsOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterDiagnosticTest;
import com.ardais.bigr.query.generator.FilterDiagnosticTestResult;
import com.ardais.bigr.query.generator.FilterDonorAliasId;
import com.ardais.bigr.query.generator.FilterDonorId;
import com.ardais.bigr.query.generator.FilterDonorInst;
import com.ardais.bigr.query.generator.FilterDre;
import com.ardais.bigr.query.generator.FilterDreExists;
import com.ardais.bigr.query.generator.FilterExcludeImplicitRNAFilters;
import com.ardais.bigr.query.generator.FilterExcludeInventoryStatus;
import com.ardais.bigr.query.generator.FilterExcludeInventoryStatusOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterGender;
import com.ardais.bigr.query.generator.FilterGenreleased;
import com.ardais.bigr.query.generator.FilterGenreleasedOrNotNullAndBmsYNYes;
import com.ardais.bigr.query.generator.FilterHng;
import com.ardais.bigr.query.generator.FilterHoldSoldStatus;
import com.ardais.bigr.query.generator.FilterInArdaisRepository;
import com.ardais.bigr.query.generator.FilterInArdaisRepositoryOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterLinked;
import com.ardais.bigr.query.generator.FilterLocalSamples;
import com.ardais.bigr.query.generator.FilterLogicalRepository;
import com.ardais.bigr.query.generator.FilterNotInProject;
import com.ardais.bigr.query.generator.FilterNotInProjectOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterNotOnHold;
import com.ardais.bigr.query.generator.FilterNotOnHoldForUser;
import com.ardais.bigr.query.generator.FilterNotOtherDdcDiagnosis;
import com.ardais.bigr.query.generator.FilterNotOtherLimsDiagnosis;
import com.ardais.bigr.query.generator.FilterNotPulled;
import com.ardais.bigr.query.generator.FilterNotPulledOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterPathVerifiedStatus;
import com.ardais.bigr.query.generator.FilterPathologyNotesContains;
import com.ardais.bigr.query.generator.FilterPathologyVerificationNotesContains;
import com.ardais.bigr.query.generator.FilterPsa;
import com.ardais.bigr.query.generator.FilterPsaExists;
import com.ardais.bigr.query.generator.FilterQcInProcess;
import com.ardais.bigr.query.generator.FilterQcInProcessNot;
import com.ardais.bigr.query.generator.FilterQcStatus;
import com.ardais.bigr.query.generator.FilterReceivedAtArdais;
import com.ardais.bigr.query.generator.FilterRestrictedStatus;
import com.ardais.bigr.query.generator.FilterRnaAmountAvailable;
import com.ardais.bigr.query.generator.FilterRnaId;
import com.ardais.bigr.query.generator.FilterRnaNotRestricted;
import com.ardais.bigr.query.generator.FilterRnaQuality;
import com.ardais.bigr.query.generator.FilterRnaRestrictedForDi;
import com.ardais.bigr.query.generator.FilterRnaStatusOrBmsYNYes;
import com.ardais.bigr.query.generator.FilterSalesStatus;
import com.ardais.bigr.query.generator.FilterSampleAliasId;
import com.ardais.bigr.query.generator.FilterSampleAppearanceBest;
import com.ardais.bigr.query.generator.FilterSampleAppearanceBestNot;
import com.ardais.bigr.query.generator.FilterSampleId;
import com.ardais.bigr.query.generator.FilterSamplePathology;
import com.ardais.bigr.query.generator.FilterSamplePathologyLike;
import com.ardais.bigr.query.generator.FilterSampleType;
import com.ardais.bigr.query.generator.FilterStageGrouping;
import com.ardais.bigr.query.generator.FilterStageLymphNode;
import com.ardais.bigr.query.generator.FilterStageMetastasis;
import com.ardais.bigr.query.generator.FilterStageTumor;
import com.ardais.bigr.query.generator.FilterTissueFindingLike;
import com.ardais.bigr.query.generator.FilterTissueFindingNotLike;
import com.ardais.bigr.query.generator.FilterTissueOfFinding;
import com.ardais.bigr.query.generator.FilterTissueOfFindingNot;
import com.ardais.bigr.query.generator.FilterTissueOfOrigin;
import com.ardais.bigr.query.generator.FilterTissueOfOriginNot;
import com.ardais.bigr.query.generator.FilterTissueOriginLike;
import com.ardais.bigr.query.generator.FilterTissueOriginNotLike;

/**
 * Holds query parameters for a product query.  Since a product is
 * either a tissue sample or derived from a tissue sample, most of the
 * query parameters are related to tissue samples.  If a parameter is not 
 * specified, then it does not affect the query that is ultimately created 
 * from the parameters (i.e. in general, the parameter acts as if an "any" 
 * or "all" choice has been specified).
 */
public abstract class FilterSet implements Serializable {

  protected final static String OR_GROUP_CASE_DX = "001-orCaseDx";
  protected final static String OR_GROUP_IDS = "003-orIDs";
  protected final static String OR_GROUP_GROSS_FINDING = "005-orGrossTissFinding";
  protected final static String OR_GROUP_SAMPLE_FINDING = "007-orSampleTissFinding";
  protected final static String OR_GROUP_LIMS_DX = "002-orLimsDx";
  protected final static String OR_GROUP_GROSS_ORIGIN = "004-orGrossOrigin";
  protected final static String OR_GROUP_SAMPLE_ORIGIN = "006-orSampleTissOrigin";
  protected final static String OR_GROUP_RESTRICTED = "008-orRestricted";
  protected final static String OR_GROUP_TISSUE_FINDING = "009-orTissFinding";
  protected final static String OR_GROUP_TISSUE_ORIGIN = "010-orTissOrigin";
  protected final static String OR_GROUP_DIAGNOSTIC_TEST = "011-orDiagnosticTest";

  // a map that tells which or group, if any, each key goes in     
  // @todo:  have the filters form an and-or tree, or a DNF tree by adding ORFILTER, ANDFILTER
  // classes, with sub-filters
  private static Map _orGroupMap;
  private static Collection _orGroups;
  static {
    Map m = new HashMap();
    // case diagnosis group
    m.put(FilterConstants.KEY_BESTCASEDIAGNOSIS, OR_GROUP_CASE_DX);
    m.put(FilterConstants.KEY_BESTCASEDIAGNOSISLIKE, OR_GROUP_CASE_DX);
    m.put(FilterConstants.KEY_DDCCASEDIAGNOSIS, OR_GROUP_CASE_DX);
    m.put(FilterConstants.KEY_DDCCASEDIAGNOSISLIKE, OR_GROUP_CASE_DX);
    m.put(FilterConstants.KEY_ILTDSCASEDIAGNOSIS, OR_GROUP_CASE_DX);
    m.put(FilterConstants.KEY_ILTDSCASEDIAGNOSISLIKE, OR_GROUP_CASE_DX);

    // sample pathology
    m.put(FilterConstants.KEY_SAMPLEPATHOLOGY, OR_GROUP_LIMS_DX);
    m.put(FilterConstants.KEY_SAMPLEPATHOLOGYLIKE, OR_GROUP_LIMS_DX);

    // ids
    m.put(FilterConstants.KEY_TISSUEID, OR_GROUP_IDS);
    m.put(FilterConstants.KEY_RNAID, OR_GROUP_IDS);
    m.put(FilterConstants.KEY_DONORID, OR_GROUP_IDS);
    m.put(FilterConstants.KEY_CASEID, OR_GROUP_IDS);
    m.put(FilterConstants.KEY_DONOR_ALIAS_ID, OR_GROUP_IDS);
    m.put(FilterConstants.KEY_CONSENT_ALIAS_ID, OR_GROUP_IDS);
    m.put(FilterConstants.KEY_SAMPLE_ALIAS_ID, OR_GROUP_IDS);

    // Tissue of Finding
    m.put(FilterConstants.KEY_TISSUEFINDING, OR_GROUP_TISSUE_FINDING);
    m.put(FilterConstants.KEY_TISSUEFINDINGLIKE, OR_GROUP_TISSUE_FINDING);

    // Tissue of origin
    m.put(FilterConstants.KEY_TISSUEORIGIN, OR_GROUP_TISSUE_ORIGIN);
    m.put(FilterConstants.KEY_TISSUEORIGINLIKE, OR_GROUP_TISSUE_ORIGIN);

    // diagnostic test and result
    m.put(FilterConstants.KEY_DIAGNOSTIC_TEST, OR_GROUP_DIAGNOSTIC_TEST);
    m.put(FilterConstants.KEY_DIAGNOSTIC_TEST_RESULT, OR_GROUP_DIAGNOSTIC_TEST);
    
    _orGroupMap = m;
    _orGroups = _orGroupMap.values();

    // register all Filter classes with their keys (must be done before using filters)
    FilterManager fmgr = FilterManager.instance();
    fmgr.register(FilterConstants.KEY_AGEATCOLLECTION, FilterAgeAtCollection.class);
    fmgr.register(FilterConstants.KEY_SAMPLE_APPEARANCE_BEST, FilterSampleAppearanceBest.class);
    fmgr.register(FilterConstants.KEY_SAMPLE_APPEARANCE_BEST_NOT, FilterSampleAppearanceBestNot.class);
    fmgr.register(FilterConstants.KEY_ASCIIREPORTCONTAINS, FilterAsciiReportContains.class);
    fmgr.register(FilterConstants.KEY_BESTCASEDIAGNOSIS, FilterCaseDiagnosisBest.class);
    fmgr.register(FilterConstants.KEY_BESTCASEDIAGNOSISLIKE, FilterCaseDiagnosisLikeBest.class);
    fmgr.register(FilterConstants.KEY_BMS_YN, FilterBmsYN.class);
    fmgr.register(FilterConstants.KEY_CASEID, FilterConsentId.class);
    fmgr.register(FilterConstants.KEY_COMP_ACS, FilterCompositionAcellularStroma.class);
    fmgr.register(FilterConstants.KEY_COMP_CS, FilterCompositionCellularStroma.class);
    fmgr.register(FilterConstants.KEY_COMP_LESION, FilterCompositionLesion.class);
    fmgr.register(FilterConstants.KEY_COMP_NECROSIS, FilterCompositionNecrosis.class);
    fmgr.register(FilterConstants.KEY_COMP_NORMAL, FilterCompositionNormal.class);
    fmgr.register(FilterConstants.KEY_COMP_TUMOR, FilterCompositionTumor.class);
    fmgr.register(FilterConstants.KEY_CONSENT_ALIAS_ID, FilterConsentAliasId.class);
    fmgr.register(FilterConstants.KEY_CONSENT_NOT_PULLED, FilterConsentNotPulled.class);
    fmgr.register(FilterConstants.KEY_CONSENT_NOT_PULLED_OR_BMS_YN_YES, FilterConsentNotPulledOrBmsYNYes.class);
    fmgr.register(FilterConstants.KEY_CONSENT_NOT_REVOKED, FilterConsentNotRevoked.class);
    fmgr.register(FilterConstants.KEY_CONSENT_NOT_REVOKED_OR_BMS_YN_YES, FilterConsentNotRevokedOrBmsYNYes.class);
    fmgr.register(FilterConstants.KEY_DDC_DIAGNOSIS_EXISTS, FilterDdcDiagnosisExists.class);
    fmgr.register(FilterConstants.KEY_DDC_DIAGNOSIS_EXISTS_OR_BMS_YN_YES, FilterDdcDiagnosisExistsOrBmsYNYes.class);
    fmgr.register(FilterConstants.KEY_DDCCASEDIAGNOSIS, FilterCaseDiagnosisDdc.class);
    fmgr.register(FilterConstants.KEY_DDCCASEDIAGNOSISLIKE, FilterCaseDiagnosisLikeDdc.class);
    fmgr.register(FilterConstants.KEY_DIAGNOSTIC_TEST, FilterDiagnosticTest.class);
    fmgr.register(FilterConstants.KEY_DIAGNOSTIC_TEST_RESULT, FilterDiagnosticTestResult.class);
    fmgr.register(FilterConstants.KEY_DISTANTMETASTASIS, FilterStageMetastasis.class);
    fmgr.register(FilterConstants.KEY_DONOR_ALIAS_ID, FilterDonorAliasId.class);
    fmgr.register(FilterConstants.KEY_DONOR_INST, FilterDonorInst.class);
    fmgr.register(FilterConstants.KEY_DONORID, FilterDonorId.class);
    fmgr.register(FilterConstants.KEY_EXCLUDE_IMPLICIT_RNA_FILTERS, FilterExcludeImplicitRNAFilters.class);
    fmgr.register(FilterConstants.KEY_GENDER, FilterGender.class);
    fmgr.register(FilterConstants.KEY_GENRELEASED, FilterGenreleased.class);
    fmgr.register(FilterConstants.KEY_GENRELEASED_OR_NOT_NULL_AND_BMS_YN_YES, FilterGenreleasedOrNotNullAndBmsYNYes.class);
    fmgr.register(FilterConstants.KEY_HNG, FilterHng.class);
    fmgr.register(FilterConstants.KEY_HOLD_SOLD_STATUS, FilterHoldSoldStatus.class);
    fmgr.register(FilterConstants.KEY_ILTDSCASEDIAGNOSIS, FilterCaseDiagnosisIltds.class);
    fmgr.register(FilterConstants.KEY_ILTDSCASEDIAGNOSISLIKE, FilterCaseDiagnosisLikeIltds.class);
    fmgr.register(FilterConstants.KEY_IN_ARDAIS_REPOSITORY, FilterInArdaisRepository.class);
    fmgr.register(FilterConstants.KEY_IN_ARDAIS_REPOSITORY_OR_BMS_YN_YES, FilterInArdaisRepositoryOrBmsYNYes.class);
    fmgr.register(FilterConstants.KEY_INVENTORY_STATUS_OR_BMS_YN_YES, FilterExcludeInventoryStatusOrBmsYNYes.class);
    fmgr.register(FilterConstants.KEY_INVENTORY_STATUS, FilterExcludeInventoryStatus.class);
    fmgr.register(FilterConstants.KEY_LINKED, FilterLinked.class);
    fmgr.register(FilterConstants.KEY_LOGICAL_REPOSITORY, FilterLogicalRepository.class);
    fmgr.register(FilterConstants.KEY_LOCAL_SAMPLES_ONLY, FilterLocalSamples.class);
    fmgr.register(FilterConstants.KEY_LYMPHNODESTAGE, FilterStageLymphNode.class);
    fmgr.register(FilterConstants.KEY_NOT_IN_PROJECT, FilterNotInProject.class);
    fmgr.register(FilterConstants.KEY_NOT_IN_PROJECT_OR_BMS_YN_YES, FilterNotInProjectOrBmsYNYes.class);
    fmgr.register(FilterConstants.KEY_NOT_ON_HOLD, FilterNotOnHold.class);
    fmgr.register(FilterConstants.KEY_NOT_ON_HOLD_FOR_USER, FilterNotOnHoldForUser.class);
    fmgr.register(FilterConstants.KEY_NOT_OTHER_DDC_DIAGNOSIS, FilterNotOtherDdcDiagnosis.class);
    fmgr.register(FilterConstants.KEY_NOT_OTHER_LIMS_DIAGNOSIS, FilterNotOtherLimsDiagnosis.class);
    fmgr.register(FilterConstants.KEY_NOT_PULLED, FilterNotPulled.class);
    fmgr.register(FilterConstants.KEY_NOT_PULLED_OR_BMS_YN_YES, FilterNotPulledOrBmsYNYes.class);
    fmgr.register(FilterConstants.KEY_PATHNOTESCONTAINS, FilterPathologyNotesContains.class);
    fmgr.register(FilterConstants.KEY_PATHVERIFIED_STATUS, FilterPathVerifiedStatus.class);
    fmgr.register(FilterConstants.KEY_PVNOTESCONTAINS, FilterPathologyVerificationNotesContains.class);
    fmgr.register(FilterConstants.KEY_QC_INPROCESS, FilterQcInProcess.class);
    fmgr.register(FilterConstants.KEY_QC_INPROCESS_NOT, FilterQcInProcessNot.class);
    fmgr.register(FilterConstants.KEY_QC_STATUS, FilterQcStatus.class);
    fmgr.register(FilterConstants.KEY_RECEIVED_AT_ARDAIS, FilterReceivedAtArdais.class);
    fmgr.register(FilterConstants.KEY_RESTRICTED_FOR_DI, FilterRestrictedStatus.class);
    fmgr.register(FilterConstants.KEY_RESTRICTED_RNA_FOR_DI, FilterRnaRestrictedForDi.class);
    fmgr.register(FilterConstants.KEY_RNA_AMOUNT_AVAILABLE, FilterRnaAmountAvailable.class);
    fmgr.register(FilterConstants.KEY_RNA_NOT_RESTRICTED, FilterRnaNotRestricted.class);
    fmgr.register(FilterConstants.KEY_RNA_QUALITY, FilterRnaQuality.class);
    fmgr.register(FilterConstants.KEY_RNA_STATUS_OR_BMS_YN_YES, FilterRnaStatusOrBmsYNYes.class);
    fmgr.register(FilterConstants.KEY_RNAID, FilterRnaId.class);
    fmgr.register(FilterConstants.KEY_SALES_STATUS, FilterSalesStatus.class);
    fmgr.register(FilterConstants.KEY_SAMPLE_ALIAS_ID, FilterSampleAliasId.class);
    fmgr.register(FilterConstants.KEY_SAMPLE_DATE_RECEIVED, FilterDateReceived.class);
    fmgr.register(FilterConstants.KEY_SAMPLE_DATE_COLLECTED, FilterDateOfCollection.class);
    fmgr.register(FilterConstants.KEY_SAMPLEPATHOLOGY, FilterSamplePathology.class);
    fmgr.register(FilterConstants.KEY_SAMPLEPATHOLOGYLIKE, FilterSamplePathologyLike.class);
    fmgr.register(FilterConstants.KEY_SAMPLE_TYPE, FilterSampleType.class);
    fmgr.register(FilterConstants.KEY_STAGE, FilterStageGrouping.class);
    fmgr.register(FilterConstants.KEY_TISSUEFINDING, FilterTissueOfFinding.class);
    fmgr.register(FilterConstants.KEY_TISSUEFINDINGLIKE, FilterTissueFindingLike.class);
    fmgr.register(FilterConstants.KEY_TISSUEFINDINGLIKENOT, FilterTissueFindingNotLike.class);
    fmgr.register(FilterConstants.KEY_TISSUEFINDINGNOT, FilterTissueOfFindingNot.class);
    fmgr.register(FilterConstants.KEY_TISSUEID, FilterSampleId.class);
    fmgr.register(FilterConstants.KEY_TISSUEORIGIN, FilterTissueOfOrigin.class);
    fmgr.register(FilterConstants.KEY_TISSUEORIGINLIKE, FilterTissueOriginLike.class);
    fmgr.register(FilterConstants.KEY_TISSUEORIGINLIKENOT, FilterTissueOriginNotLike.class);
    fmgr.register(FilterConstants.KEY_TISSUEORIGINNOT, FilterTissueOfOriginNot.class);
    fmgr.register(FilterConstants.KEY_TUMORSTAGE, FilterStageTumor.class);
    fmgr.register(FilterConstants.KEY_DRE, FilterDre.class);
    fmgr.register(FilterConstants.KEY_DRE_EXISTS, FilterDreExists.class);
    fmgr.register(FilterConstants.KEY_PSA, FilterPsa.class);
    fmgr.register(FilterConstants.KEY_PSA_EXISTS, FilterPsaExists.class);
  }
  
  /**
   * this map is a bit screwy.  it holds both Filters and Maps of Filters.  the Filters
   * are those that are not in any Or_group (they're AND'ed).  The maps are the OR groups.
   */
  private Map _filters = new HashMap();

  // sometimes, we ignore some "not applicable" filters on the back end.  Keep the user-entered
  // set here, independent of backend use
  //    private Map _allInputFilters = new HashMap(); // @todo: remove, use QueryProfile for all filters

  public FilterSet() {
  }

  public FilterSet(Map filters) {
    addFiltersFromMap(filters);
  }

  public void copyInto(FilterSet fs) {
    fs.addFiltersFromMap(getFilters()); // was getAllInputFilters()
  }

  private void addFiltersFromMap(Map m) {
    Iterator it = m.values().iterator();
    while (it.hasNext()) {
      Object filterOrGroup = it.next();
      if (filterOrGroup instanceof Filter) { // simple filter?
        addFilter((Filter) filterOrGroup);
      }
      else { // it is a map representing a whole or group (recurse)
        addFiltersFromMap(((Map) filterOrGroup));
      }
    }
  }

  /**
   * Add the filter to the map, putting it in an internal OR group if appropriate.
   * @param f the filter
   */
  public final void addFilter(Filter f) {
    String key = f.getKey();
    addFilterProtected(key, f);
  }

  protected void addFilterProtected(String key, Filter f) {

    // ignore filters that do not apply.
    if (getNotApplicableFilterKeys().contains(key))
      return;

    String orGroup = getOrGroup(key);
    if (orGroup != null) {
      addFilterToOrGroup(orGroup, f); // add inside another map for OR groups
      return;
    }

    _filters.put(key, f); // if it is useful for backend processing, put it here
  }

  /**
   * Put the filter in the Map containing the or group corresponding to <code>orGroup</code>
   * @param orGroup  the or group code to put the filter in
   * @param f the filter
   */
  public void addFilterToOrGroup(String orGroup, Filter f) {
    f.setOrGroupCode(orGroup); // query builder organizes based on filters' or group codes.
    Map orGroupMap = (Map) _filters.get(orGroup);
    if (orGroupMap == null) {
      orGroupMap = new HashMap();
      _filters.put(orGroup, orGroupMap);
    }
    orGroupMap.put(f.getKey(), f);
  }

  /**
   * Returns true if the key represents a filter in some or group
   */
  public static boolean isInOrGrouop(String key) {
    return _orGroups.contains(key);
  }

  /**
   * Returns the or group for the filter key, or null if there is none.
   */
  public static String getOrGroup(String key) {
    return (String) _orGroupMap.get(key);
  }

  public boolean hasFilter(String key) {
    return _filters.containsKey(key);
  }
  /**
   * Returns the filter corresponding to the key, or null if there is no filter for that key
   */
  public Filter getFilter(String key) {
    // get it if it is not in any or group
    Filter f = (Filter) _filters.get(key);
    if (f != null)
      return f;

    // if it is in an or group, get that map and look for it in there
    String orGroupCode = (String) _orGroupMap.get(key);
    if (orGroupCode != null) {
      Map m = (Map) _filters.get(orGroupCode);
      if (m == null)
        return null;
      return (Filter) m.get(key);
    }
    return null; // no filter and no Or group for this key
  }

  /**
   * @return a Map associating filter keys with filters for the specified or group.
   */
  public Map getOrGroupMap(String orGroupKey) {
    Map m = (Map) _filters.get(orGroupKey);
    return m;
  }

  /**
   * @return the filters as a single map, without or-groups stored in nested maps.
   */
  public Map getFiltersAsFlatMap() {
    return getFiltersFromMap(_filters);
  }

  // recursively add filters and or-groups to a single map
  private Map getFiltersFromMap(Map fMap) {
    Map m = new HashMap();
    Iterator it = fMap.values().iterator();
    while (it.hasNext()) {
      Object filterOrGroup = it.next();
      if (filterOrGroup instanceof Filter) { // simple filter?
        Filter f = (Filter) filterOrGroup;
        m.put(f.getKey(), f);
      }
      else { // it is a map representing a whole or group (recurse)
        m.putAll(getFiltersFromMap((Map) filterOrGroup));
      }
    }
    return m;
  }

  /**
   * @return the filters in their nested form, where or-groups are stored as
   * maps within the overall map.
   */
  protected Map getFilters() {
    return Collections.unmodifiableMap(_filters);
  }

  protected abstract List getNotApplicableFilterKeys();

  /**
   * @return  <code>true</code> if at least one criteria is specified;
   * 					 <code>false</code> otherwise
   */
  public boolean isEmpty() {
    return getFilters().isEmpty();
  }

  public Filter removeFilter(String key) {
    // If the filter is part of an or group, then remove the filter from the or group.
    String orGroup = getOrGroup(key);
    if (orGroup != null) {
      return removeFilterFromOrGroup(orGroup, key);
    }

    // Otherwise remove the filter from the filter set.
    else {
      Filter f = (Filter) _filters.get(key);
      if (f != null) {
        _filters.remove(key);
      }
      return f;      
    }
  }
  
  private Filter removeFilterFromOrGroup(String orGroup, String key) {
    Map orGroupMap = (Map) _filters.get(orGroup);

    // If the or group does not exist then the filter does not either, so don't do anything.
    if (orGroupMap == null) {
      return null;
    }

    else {
      // Remove the filter from the or group if it exists.
      Filter f = (Filter) orGroupMap.get(key);
      if (f != null) {
        _filters.remove(key);
      }
      
      // If we remove the last filter from the or group, then remove the or group as well.
      if (orGroupMap.size() == 0) {
        _filters.remove(orGroup);        
      }
      return f;      
    }
  }

}
