package com.ardais.bigr.query.filters;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FilterConstants {

  public final static String KEY_ACCOUNT = "acctname";

  // ID KEYS
  //    public final static String KEY_ID = "0100";
  public final static String KEY_DONORID = "donorId";
  public final static String KEY_CASEID = "caseId";
  public final static String KEY_TISSUEID = "tissueId"; // really just TISSUE code
  public final static String KEY_RNAID = "rnaId";
  public final static String KEY_DONOR_INST = "donorInst";
  public final static String KEY_DONOR_ALIAS_ID = "donorAliasId";
  public final static String KEY_CONSENT_ALIAS_ID = "consentAliasId";
  public final static String KEY_SAMPLE_ALIAS_ID = "sampleAliasId";

  public final static String KEY_GENDER = "gender";
  public final static String KEY_SAMPLE_TYPE = "sample_type";
  public final static String KEY_FORMAT_DETAIL_NOT = "formatDetailNot";
  public final static String KEY_LINKED = "linked";
  public final static String KEY_AGEATCOLLECTION = "ageAtCollection";
  public final static String KEY_PATHVERIFIED_STATUS = "pathVerifiedStatus";
  public final static String KEY_RESTRICTED_FOR_DI = "restrictedForDI";
  public final static String KEY_RESTRICTED_RNA_FOR_DI = "restrictedRnaForDI";

  public final static String KEY_SAMPLE_APPEARANCE_BEST = "sampleAppearanceBest";
  public final static String KEY_COMP_NORMAL = "compNormal";
  public final static String KEY_COMP_LESION = "compLesion";
  public final static String KEY_COMP_TUMOR = "compTumor";
  public final static String KEY_COMP_ACS = "compAcellStroma";
  public final static String KEY_COMP_CS = "compCellStroma";
  public final static String KEY_COMP_NECROSIS = "compNecrosis";

  public final static String KEY_DDCCASEDIAGNOSIS = "ddcCaseDiag";
  public final static String KEY_DDCCASEDIAGNOSISLIKE = "ddcCaseDiagLike";
  public final static String KEY_ILTDSCASEDIAGNOSIS = "iltdsCaseDiag";
  public final static String KEY_ILTDSCASEDIAGNOSISLIKE = "iltdsCaseDiagLike";
  public final static String KEY_BESTCASEDIAGNOSIS = "bestCaseDiag";
  public final static String KEY_BESTCASEDIAGNOSISLIKE = "bestCaseDiagLike";
  public final static String KEY_SAMPLEPATHOLOGY = "sampPath";
  public final static String KEY_SAMPLEPATHOLOGYLIKE = "sampPathLike";
  public final static String KEY_STAGE = "stage";
  public final static String KEY_TUMORSTAGE = "tumorStage";
  public final static String KEY_LYMPHNODESTAGE = "lymphStage";
  public final static String KEY_DISTANTMETASTASIS = "distMetas";
  public final static String KEY_HNG = "hng";
  public final static String KEY_PATHNOTESCONTAINS = "pathNotesContains";
  public final static String KEY_ASCIIREPORTCONTAINS = "asciiReportContains";
  public final static String KEY_PVNOTESCONTAINS = "pvNotesContains";
  public final static String KEY_NOT_OTHER_DDC_DIAGNOSIS = "notOtherDdcDiag";
  public final static String KEY_NOT_OTHER_LIMS_DIAGNOSIS = "notOtherLimsDiag";

  public final static String KEY_TISSUEORIGIN = "tissueOrigin";
  public final static String KEY_TISSUEORIGINNOT = "tissueOriginNot";
  public final static String KEY_TISSUEORIGINLIKE = "tissueOriginLike";
  public final static String KEY_TISSUEORIGINLIKENOT = "tissueOriginLikeNot";
  public final static String KEY_TISSUEFINDING = "tissueFinding";
  public final static String KEY_TISSUEFINDINGNOT = "tissueFindingNot";
  public final static String KEY_TISSUEFINDINGLIKE = "tissueFindingLike";
  public final static String KEY_TISSUEFINDINGLIKENOT = "tissueFindingLikeNot";

  public final static String KEY_RNA_STATUS = "rnaStatus";
  public final static String KEY_RNA_STATUS_OR_BMS_YN_YES = "rnaStatusOrBmsYNYes";
  public final static String KEY_RECEIVED_AT_ARDAIS = "receivedAtArdais";
  public final static String KEY_RNA_NOT_RESTRICTED = "rnaNotRestricted";
  public final static String KEY_RNA_AMOUNT_AVAILABLE = "rnaAmountAvailable";
  public final static String KEY_RNA_QUALITY = "rnaQuality";
  public final static String KEY_NOT_IN_PROJECT = "notInProject";
  public final static String KEY_NOT_IN_PROJECT_OR_BMS_YN_YES = "notInProjectOrBmsYNYes";
  public final static String KEY_NOT_PULLED = "notPulled";
  public final static String KEY_INVENTORY_STATUS = "inventoryStatus";
  public final static String KEY_INVENTORY_STATUS_OR_BMS_YN_YES = "inventoryStatusOrBmsYNYes";
  public final static String KEY_QC_INPROCESS = "qcInProcess";
  public final static String KEY_QC_INPROCESS_NOT = "qcInProcessNot";
  public final static String KEY_QC_STATUS = "qcStatus";
  public final static String KEY_GENRELEASED = "genreleased";
  public final static String KEY_GENRELEASED_OR_NOT_NULL_AND_BMS_YN_YES = "genreleasedOrNotNullAndBmsYNYes";
  public final static String KEY_NOT_ON_HOLD = "notOnHold";
  public final static String KEY_NOT_ON_HOLD_FOR_USER = "notOnHoldForUser";
  public final static String KEY_CONSENT_NOT_PULLED = "notConsentPulled";
  public final static String KEY_CONSENT_NOT_PULLED_OR_BMS_YN_YES = "notConsentPulledOrBmsYNYes";
  public final static String KEY_CONSENT_NOT_REVOKED = "notConsentRevoked";
  public final static String KEY_CONSENT_NOT_REVOKED_OR_BMS_YN_YES = "notConsentRevokedOrBmsYNYes";
  public final static String KEY_IN_ARDAIS_REPOSITORY = "inArdaisRepository";
  public final static String KEY_IN_ARDAIS_REPOSITORY_OR_BMS_YN_YES = "inArdaisRepositoryOrBmsYNYes";
  public final static String KEY_DDC_DIAGNOSIS_EXISTS = "ddcDiagnosisExists";
  public final static String KEY_DDC_DIAGNOSIS_EXISTS_OR_BMS_YN_YES = "ddcDiagnosisExistsOrBmsYNYes";
  public final static String KEY_SAMPLE_APPEARANCE_BEST_NOT = "sampleAppearanceBestNot";
  public final static String KEY_PRODUCT_ID_EXISTS = "productIdExists";
  public final static String KEY_PRODUCT_ID_EXISTS_OR_BMS_YN_YES = "productIdExistsOrBmsYNYes";
  public final static String KEY_HOLD_SOLD_STATUS = "holdSoldStatus";
  public final static String KEY_SAMPLE_DATE_RECEIVED = "dateReceived";
  public final static String KEY_SAMPLE_DATE_COLLECTED = "dateCollected";
  public final static String KEY_SALES_STATUS = "salestatus";
  public final static String KEY_SALES_STATUS_EXISTS = "salesStatusExists";
  public final static String KEY_PRODUCT_ID_EXISTS_OR_RNA_BMS_YN_YES = "productIdExistsOrRnaBmsYNYes";

  public final static String KEY_EXCLUDE_IMPLICIT_RNA_FILTERS = "excludeImplicitRNAfilters";
  public final static String KEY_LOGICAL_REPOSITORY = "logicalRepository";
  public final static String KEY_BMS_YN = "bmsYN";
  public final static String KEY_BMS_YN_YES = "bmsYNYes";
  public final static String KEY_NOT_PULLED_OR_BMS_YN_YES =
    "notPulledOrBmsYNYes";
  public final static String KEY_LOCAL_SAMPLES_ONLY = "localOnly";

  public final static String KEY_DIAGNOSTIC_TEST = "diagnosticTest";
  public final static String KEY_DIAGNOSTIC_TEST_RESULT = "diagnosticTestResult";
  public final static String KEY_NOT_ON_HOLD_FOR_USER_OR_BMS_RNA_YN_YES = "notOnHoldForUserOrBmsRnaYnYes";
  
  public final static String KEY_DRE = "dre";  
  public final static String KEY_DRE_EXISTS = "dreExists";
  public final static String KEY_PSA = "psa";
  public final static String KEY_PSA_EXISTS = "psaExists";

}
