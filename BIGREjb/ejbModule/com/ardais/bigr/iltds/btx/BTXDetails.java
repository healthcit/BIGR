package com.ardais.bigr.iltds.btx;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.BTXDetailsHistoryNote;
import com.ardais.bigr.btx.BTXDetailsUpdateComputedData;
import com.ardais.bigr.btx.BTXDetailsUpdateComputedDataSingle;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.ddc.donor.BTXDetailsCreateDonor;
import com.ardais.bigr.ddc.donor.BTXDetailsUpdateDonor;
import com.ardais.bigr.id.btx.BtxDetailsGenerateSampleId;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstanceCreate;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstanceCreatePrepare;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstanceDomainObjectSummary;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstanceSummary;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstanceUpdate;
import com.ardais.bigr.kc.form.BtxDetailsKcFormInstanceUpdatePrepare;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionCreate;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionDelete;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionLookup;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionStart;
import com.ardais.bigr.kc.form.def.BtxDetailsKcFormDefinitionUpdate;
import com.ardais.bigr.kc.form.def.BtxDetailsKcResultsFormDefinitionCreateOrUpdate;
import com.ardais.bigr.library.btx.BTXDetailsAddToHoldList;
import com.ardais.bigr.library.btx.BTXDetailsAddToRequestSample;
import com.ardais.bigr.library.btx.BTXDetailsGetKcQueryForm;
import com.ardais.bigr.library.btx.BTXDetailsGetRna;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummaryNoHistory;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummaryStart;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.btx.BTXDetailsGetSummarySql;
import com.ardais.bigr.library.btx.BTXDetailsRemoveFromHoldList;
import com.ardais.bigr.lims.btx.BTXDetailsCreateImage;
import com.ardais.bigr.lims.btx.BTXDetailsCreateIncidents;
import com.ardais.bigr.lims.btx.BTXDetailsCreateIncidentsSingle;
import com.ardais.bigr.lims.btx.BTXDetailsCreatePathologyEvaluation;
import com.ardais.bigr.lims.btx.BTXDetailsCreatePathologyEvaluationPrepare;
import com.ardais.bigr.lims.btx.BTXDetailsCreateSlides;
import com.ardais.bigr.lims.btx.BTXDetailsCreateSlidesPrepare;
import com.ardais.bigr.lims.btx.BTXDetailsCreateSlidesSingle;
import com.ardais.bigr.lims.btx.BTXDetailsGetPathQCSampleDetails;
import com.ardais.bigr.lims.btx.BTXDetailsGetPathQCSampleSummary;
import com.ardais.bigr.lims.btx.BTXDetailsGetSamplePathologyInfo;
import com.ardais.bigr.lims.btx.BTXDetailsHistoQCSamples;
import com.ardais.bigr.lims.btx.BTXDetailsHistoQCSamplesSingle;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleDiscordant;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleNondiscordant;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSamplePulled;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleQCPosted;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleReleased;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnQCPosted;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnpulled;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnreleased;
import com.ardais.bigr.lims.btx.BTXDetailsPathLabSlides;
import com.ardais.bigr.lims.btx.BTXDetailsPathLabSlidesSingle;
import com.ardais.bigr.lims.btx.BTXDetailsPrintSlides;
import com.ardais.bigr.lims.btx.BTXDetailsPrintSlidesValidation;
import com.ardais.bigr.lims.btx.BTXDetailsReportPathologyEvaluation;
import com.ardais.bigr.lims.btx.BTXDetailsSetSlideLocations;
import com.ardais.bigr.lims.btx.BTXDetailsSetSlideLocationsSingle;
import com.ardais.bigr.lims.btx.BTXDetailsSubdivide;
import com.ardais.bigr.lims.btx.BTXDetailsSubdivideSample;
import com.ardais.bigr.lims.btx.BTXDetailsViewSubdivide;
import com.ardais.bigr.orm.btx.BTXDetailsDisableLogin;
import com.ardais.bigr.orm.btx.BTXDetailsLogin;
import com.ardais.bigr.orm.btx.BtxDetailsAccountManagement;
import com.ardais.bigr.orm.btx.BtxDetailsChangePassword;
import com.ardais.bigr.orm.btx.BtxDetailsChangeProfile;
import com.ardais.bigr.orm.btx.BtxDetailsChangeProfileStart;
import com.ardais.bigr.orm.btx.BtxDetailsCreateAccount;
import com.ardais.bigr.orm.btx.BtxDetailsCreateAccountLocation;
import com.ardais.bigr.orm.btx.BtxDetailsCreateAccountLocationStart;
import com.ardais.bigr.orm.btx.BtxDetailsCreateAccountStart;
import com.ardais.bigr.orm.btx.BtxDetailsCreateUser;
import com.ardais.bigr.orm.btx.BtxDetailsCreateUserStart;
import com.ardais.bigr.orm.btx.BtxDetailsFailedLogin;
import com.ardais.bigr.orm.btx.BtxDetailsFindAccounts;
import com.ardais.bigr.orm.btx.BtxDetailsFindUsers;
import com.ardais.bigr.orm.btx.BtxDetailsGetDisplayBanner;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainAccountUrl;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainAccountUrlStart;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainBoxLayout;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainBoxLayoutStart;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainLogicalRepository;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainLogicalRepositoryStart;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainPolicy;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainPolicyStart;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainRole;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainRoleStart;
import com.ardais.bigr.orm.btx.BtxDetailsManageAccountBoxLayout;
import com.ardais.bigr.orm.btx.BtxDetailsManageAccountBoxLayoutStart;
import com.ardais.bigr.orm.btx.BtxDetailsManageAccountLocations;
import com.ardais.bigr.orm.btx.BtxDetailsManageInventoryGroups;
import com.ardais.bigr.orm.btx.BtxDetailsManageInventoryGroupsStart;
import com.ardais.bigr.orm.btx.BtxDetailsManagePrivileges;
import com.ardais.bigr.orm.btx.BtxDetailsManagePrivilegesStart;
import com.ardais.bigr.orm.btx.BtxDetailsManageRoles;
import com.ardais.bigr.orm.btx.BtxDetailsManageRolesStart;
import com.ardais.bigr.orm.btx.BtxDetailsManageShippingPartners;
import com.ardais.bigr.orm.btx.BtxDetailsModifyAccount;
import com.ardais.bigr.orm.btx.BtxDetailsModifyAccountLocation;
import com.ardais.bigr.orm.btx.BtxDetailsModifyAccountLocationStart;
import com.ardais.bigr.orm.btx.BtxDetailsModifyAccountStart;
import com.ardais.bigr.orm.btx.BtxDetailsModifyUser;
import com.ardais.bigr.orm.btx.BtxDetailsModifyUserStart;
import com.ardais.bigr.orm.btx.BtxDetailsMoveByInventoryGroup;
import com.ardais.bigr.orm.btx.BtxDetailsMoveByInventoryGroupStart;
import com.ardais.bigr.orm.btx.BtxDetailsMoveSample;
import com.ardais.bigr.orm.btx.BtxDetailsUserManagement;
import com.ardais.bigr.pdc.btx.BTXDetailsCreateCaseProfileNotes;
import com.ardais.bigr.pdc.btx.BTXDetailsCreateClinicalData;
import com.ardais.bigr.pdc.btx.BTXDetailsCreatePathReport;
import com.ardais.bigr.pdc.btx.BTXDetailsCreatePathReportDiagnostic;
import com.ardais.bigr.pdc.btx.BTXDetailsCreatePathReportSection;
import com.ardais.bigr.pdc.btx.BTXDetailsCreatePathReportSectionFinding;
import com.ardais.bigr.pdc.btx.BTXDetailsCreateRawPathReport;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdateCaseProfileNotes;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdateClinicalData;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdatePathReport;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdatePathReportDiagnostic;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdatePathReportSection;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdatePathReportSectionFinding;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdateRawPathReport;
import com.ardais.bigr.security.SecurityInfo;

/**
 * This is the abstract base class for all of the classes that describe
 * the details of a business transaction.  The details include the
 * transaction's input parameters, its results, and possibly additional
 * details that are accumulated during the course of performing the transaction.
 * 
 * <p>Every transaction has certain properties, and those common properties are
 * defined in this base class.  These properties include the transaction id,
 * the transaction type code, the user who performs the transaction, and begin
 * and end timestamps.
 * 
 * <p>Derived classes must define any additional properties that are specific
 * to the transaction they represent.  Derived classes must:
 * <ul>
 * <li>Define {@link #getBTXType() getBTXType} to define the transaction
 *     type code;</li>
 * <li>Define {@link #doGetDetailsAsHTML() getDetailsAsHTML} to define how the
 *     transaction details are presented in a transaction-history web page;</li>
 * <li>Define {@link #getDirectlyInvolvedObjects() getDirectlyInvolvedObjects}
 *     to define the set of object ids that are directly involved in the
 *     transaction.</li>
 * <li>Override {@link #describeIntoHistoryRecord(BTXHistoryRecord)
 *     describeIntoHistoryRecord} to define how to represent the transaction
 *     details in a transaction history record.</li>
 * <li>Override {@link #populateFromHistoryRecord(BTXHistoryRecord)
 *     populateFromHistoryRecord} to define how to reconstruct the transaction
 *     details from a transaction history record.</li>
 * </ul>
 * 
 * <p>This class also provides some utility methods and constants that are
 * useful in manipulating business transaction details.
 * 
 * <p>All transactions have the following input and output fields.  When a
 * transaction throws an exception, the output fields may or may not be set.
 * 
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setBeginTimestamp(Timestamp) Beginning timestamp}: The
 *     date/time that the transaction began.  Transactions must set this as
 *     soon as possible when processing begins so that we can accurately
 *     track transaction durations.</li>
 * <li>{@link #setLoggedInUserSecurityInfo(SecurityInfo) Security info}:
 *     The user id of the user who is currently logged in to BIGR.  This
 *     implicitly sets the UserId and UserAccount properties.</li>
 * <li>{@link #setTransactionType(String) Transaction type}:
 *     The transaction to be performed on the information in this BTXDetails object.
 *     This must be set to a valid transaction type prior to performing the transaction.
 *     You can think of setting this property as binding the transaction data to a particular
 *     runtime transaction to be performed.  Having this property allows the same BTXDetails
 *     class to be passed to multiple transaction types.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setTransactionCompleted(boolean) setTransactionCompleted}: Set
 *     a flag indicating whether or not the transaction was completed (this
 *     field is true by default).  A transaction is completed when the actions
 *     normally involved in performing it are finished.  An incomplete
 *     transaction does not necessarily mean there was a system error or even
 *     a user error.  For example, a transaction might decide that it needs
 *     additional input or confirmation before completing the transaction, in
 *     which case the transaction would set this flag to false.
 * </li>
 * <li>{@link #addActionError(String, BtxActionError) addActionError}:  Add a
 *     user error message to the transaction.  This is meant to record user
 *     errors, not system errors.  For example, it is typically used when
 *     a transaction detects invalid user input.
 *     </li>
 * <li>{@link #setActionForward(BTXActionForward) Action forward}: The
 *     description of what URI to transfer control to following the
 *     transaction.  Transactions must set this, otherwise an exception may
 *     be thrown or other unexpected behavior may occur.
 *     There are several convenience methods that set the BTXActionForward
 *     in special situations, such as
 *     {@link #setActionForwardTXCompleted(String)} and
 *     {@link #setActionForwardRetry(String)}.
 *     </li>
 * <li>{@link #setTransactionId(long) Transaction id}: The unique id of this
 *     transaction.  It is assigned while performing the transaction and will
 *     be non-null for transactions that succeed.  Transactions must not set
 *     this explicitly, it will be set by
 *     {@link com.ardais.bigr.btx.framework.Btx#perform(BTXDetails, String)
 *       Btx.perform}.
 *     </li>
 * <li>{@link #setEndTimestamp(Timestamp) Ending timestamp}: The date/time
 *     that the transaction ended.  Transactions must not set this explicitly,
 *     it will be set by 
 *     {@link com.ardais.bigr.btx.framework.Btx#perform(BTXDetails, String)
 *       Btx.perform}.
 *     </li>
 * <li>{@link #setExceptionText(String) Exception text}: A brief message
 *     describing an exception that occurred while performing the transaction.
 *     This may be truncated to fit in the corresponding database column.
 *     It is assigned while performing the transaction and will be non-null
 *     for transactions that fail (and null for transactions that succeed).
 *     Transactions must not set this explicitly, it will be set by
 *     {@link com.ardais.bigr.btx.framework.Btx#perform(BTXDetails, String)
 *       Btx.perform}.
 *     </li>
 * <li>{@link #setExceptionStackTrace(String) Exception stack trace}: The
 *     stack trace describing an exception that occurred while performing the
 *     transaction.  This may be truncated to fit in the corresponding
 *     database column.  It is assigned while performing the transaction and
 *     will be non-null for transactions that fail (and null for transactions
 *     that succeed).  Transactions must not set this explicitly, it will be
 *     set by
 *     {@link com.ardais.bigr.btx.framework.Btx#perform(BTXDetails, String)
 *       Btx.perform}.
 *     </li>
 * </ul>
 */
public abstract class BTXDetails implements java.io.Serializable {
  private static final long serialVersionUID = 276669853438158678L;

  //********* NOTE TO DEVELOPERS ***************************************
  // When you add new BTX_TYPE_* constants below, also add new
  // entries to:
  //
  //   (1) _transactionTypeDisplayNames in the static
  //       initialization block below to define the user-friendly
  //       display string for the new transaction type code.
  //
  //   (2) _transactionTypeClass in the static initialization block
  //       below to define the class of the BTXDetails subclass that
  //       represents transactions of that type.
  //
  // Also, please make sure that the values of the BTX_TYPE_* constants
  // have no more than 15 characters (the size of the corresponding
  // database column).
  //********************************************************************

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * box scan transaction.
   */
  public static final String BTX_TYPE_BOX_SCAN = "BoxScan";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * sample check-out transaction.
   */
  public static final String BTX_TYPE_CHECK_OUT_SAMPLES = "CKOUTSamp";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * box scan-and-store transaction.
   */
  public static final String BTX_TYPE_SCAN_AND_STORE = "ScanStore";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * get sample subdivision transaction.
   */
  public static final String BTX_TYPE_SUBDIVIDE_SAMPLE = "SubdivideSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * sample subdivision transaction.
   */
  public static final String BTX_TYPE_SUBDIVIDE = "Subdivide";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * view sample subdivision relationship transaction.
   */
  public static final String BTX_TYPE_VIEW_SUBDIVIDE = "ViewSubdivide";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * box location update transaction.
   */
  public static final String BTX_TYPE_UPDATE_BOX_LOCATION = "UpdBoxLoc";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * receipt verification transaction.
   */
  public static final String BTX_TYPE_RECEIPT_VERIFICATION = "RecVerif";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * pathology sample request transaction.
   */
  public static final String BTX_TYPE_PATH_SAMPLE_REQUEST = "PathRequest";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * R&D sample request transaction.
   */
  public static final String BTX_TYPE_RND_SAMPLE_REQUEST = "RNDRequest";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * quality control transaction.
   */
  public static final String BTX_TYPE_QUALITY_CONTROL = "QualityControl";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * create slides prepare transaction.
   */
  public static final String BTX_TYPE_CREATE_SLIDES_PREPARE = "LabelGensPrp";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * create slides transaction.
   */
  public static final String BTX_TYPE_CREATE_SLIDES = "LabelGens";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * create slides single transaction.
   */
  public static final String BTX_TYPE_CREATE_SLIDES_SINGLE = "LabelGen";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * path lab slides transaction.
   */
  public static final String BTX_TYPE_PATHLAB_SLIDES = "PathLabs";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * path lab slides single transaction.
   */
  public static final String BTX_TYPE_PATHLAB_SLIDES_SINGLE = "PathLab";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * set slide locations transaction.
   */
  public static final String BTX_TYPE_SET_SLIDE_LOCATIONS = "SetSlideLocs";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * set slide locations single transaction.
   */
  public static final String BTX_TYPE_SET_SLIDE_LOCATIONS_SINGLE = "SetSlideLoc";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * mark sample pulled transaction.
   */
  public static final String BTX_TYPE_MARK_SAMPLE_PULLED = "PullSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * mark sample unpulled transaction.
   */
  public static final String BTX_TYPE_MARK_SAMPLE_UNPULLED = "UnpullSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * mark sample released transaction.
   */
  public static final String BTX_TYPE_MARK_SAMPLE_RELEASED = "ReleaseSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * mark sample unreleased transaction.
   */
  public static final String BTX_TYPE_MARK_SAMPLE_UNRELEASED = "UnreleaseSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * mark sample discordant transaction.
   */
  public static final String BTX_TYPE_MARK_SAMPLE_DISCORDANT = "DiscSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * mark sample nondiscordant transaction.
   */
  public static final String BTX_TYPE_MARK_SAMPLE_NONDISCORDANT = "NondiscSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * create pathology evaluation prepare new transaction.
   */
  public static final String BTX_TYPE_CREATE_PATH_EVAL_PREPARE = "CreateEvalPrep";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * create pathology evaluation transaction.
   */
  public static final String BTX_TYPE_CREATE_PATH_EVAL = "PathVer";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * report pathology evaluation transaction.  
   */
  public static final String BTX_TYPE_REPORT_PATH_EVAL = "ReportEval";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * manage pathology evaluations transaction.  
   */
  public static final String BTX_TYPE_MANAGE_PATH_EVAL = "ManageEval";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * path QC transaction.  
   */
  public static final String BTX_TYPE_PATH_QC_SUMMARY = "PathQCSum";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * path QC transaction.  
   */
  public static final String BTX_TYPE_PATH_QC_DETAILS = "PathQCDetails";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * mark sample QCPosted transaction.
   */
  public static final String BTX_TYPE_MARK_SAMPLE_QCPOSTED = "QCPostSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * mark sample unQCPosted transaction.
   */
  public static final String BTX_TYPE_MARK_SAMPLE_UNQCPOSTED = "UnQCPostSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * performGetKcQueryForm transaction.
   */
  public static final String BTX_TYPE_GET_KC_QUERY_FORM = "GetKcQueryForm";
  
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * getSampleSummaryStart transaction.
   */
  public static final String BTX_TYPE_GET_SAMPLE_SUMMARY_START = "GetSampSumStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * getSampleSummary transaction.
   */
  public static final String BTX_TYPE_GET_SAMPLE_SUMMARY = "GetSampSummary";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * getSampleSummaryNoHistory transaction.
   */
  public static final String BTX_TYPE_GET_SAMPLE_SUMMARY_NO_HISTORY = "GetSampSumNoHis";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * getSampleSummary transaction.
   */
  public static final String BTX_TYPE_GET_SAMPLE_SUMMARY_SQL = "GetSampSumSql";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * getSampleDetails transaction.
   */
  public static final String BTX_TYPE_GET_SAMPLES = "GetSamples";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * getRnaDetails transaction.
   */
  public static final String BTX_TYPE_GET_RNA = "GetRna";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for an
   * update computed data transaction.
   */
  public static final String BTX_TYPE_UPDATE_COMPUTED_DATA = "UpdCompDatas";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for an
   * update computed data single transaction.
   */
  public static final String BTX_TYPE_UPDATE_COMPUTED_DATA_SINGLE = "UpdCompData";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * GetHistoQCSample transaction.
   */
  public static final String BTX_TYPE_HISTO_QC_SAMPLES = "HistoQCSamples";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * UpdateHistoQCSamplesSingle transaction.
   */
  public static final String BTX_TYPE_HISTO_QC_SAMPLES_SINGLE = "HistoQCSingle";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * HistoryNote transaction.
   */
  public static final String BTX_TYPE_HISTORY_NOTE = "TXHNote";

  public static final String BTX_TYPE_ADD_TO_HOLD_LIST = "AddToHoldList";

  public static final String BTX_TYPE_ADD_TO_REQUEST_SAMPLE = "AddToRequestSample";

  public static final String BTX_TYPE_REMOVE_FROM_HOLD_LIST = "RemFrmHoldList";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * print slides validation transaction.
   */
  public static final String BTX_TYPE_PRINT_SLIDES_VALIDATION = "PrintSlideValid";
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * print slides transaction.
   */
  public static final String BTX_TYPE_PRINT_SLIDES = "PrintSlides";
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * create image transaction.
   */
  public static final String BTX_TYPE_CREATE_IMAGE = "CreateImage";
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * login
   */
  public static final String BTX_TYPE_LOGIN = "Login";
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * disable login
   */
  public static final String BTX_TYPE_DISABLE_LOGIN = "DisableLogin";
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * login
   */
  public static final String BTX_TYPE_FAILED_LOGIN = "FailedLogin";
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a Create
   * Incident Reports transaction.
   */
  public static final String BTX_TYPE_CREATE_INCIDENTS = "CreateIncidents";
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a Create
   * Incident Reports transaction.
   */
  public static final String BTX_TYPE_CREATE_INCIDENTS_SINGLE = "CreateIncident";
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * create or update donor transaction.
   */
  public static final String BTX_TYPE_CREATE_DONOR = "CreateDonor";
  public static final String BTX_TYPE_UPDATE_DONOR = "UpdateDonor";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * create, get, update and delete Cir Category transaction.
   */
  public static final String BTX_TYPE_CIR_CATEGORY_CREATE = "CirCatCreate";
  public static final String BTX_TYPE_CIR_CATEGORY_GET = "CirCatGet";
  public static final String BTX_TYPE_CIR_CATEGORY_UPDATE = "CirCatUpdate";
  public static final String BTX_TYPE_CIR_CATEGORY_DELETE = "CirCatDelete";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to display a concept graph.
   */
  public static final String BTX_TYPE_DISPLAY_CONCEPT_GRAPH = "DispConGr";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to retrieve the banner information.
   */
  public static final String BTX_TYPE_DISPLAY_BANNER = "DispBanner";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start to manage the inventory groups for a user
   * or account.
   */
  public static final String BTX_TYPE_MANAGE_INVENTORY_GROUPS_START = "MngIGStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to manage the inventory groups for a user or account.
   */
  public static final String BTX_TYPE_MANAGE_INVENTORY_GROUPS = "MngIG";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to log sample allocation.
   */
  public static final String BTX_TYPE_LOG_ALLOCATE_SINGLE = "AllocSingle";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to log sample creation.
   */
  public static final String BTX_TYPE_LOG_CREATE_SAMPLE = "CreateSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a new case.
   */
  public static final String BTX_TYPE_CREATE_CASE = "CreateCase";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to maintain a logical repository (create/edit/delete).
   */
  public static final String BTX_TYPE_MAINTAIN_LOGICAL_REPOSITORY = "MaintainLR";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start the process of maintaining a logical repository.
   */
  public static final String BTX_TYPE_MAINTAIN_LOGICAL_REPOSITORY_START = "MaintLRStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * create manifest transaction.
   */
  public static final String BTX_TYPE_CREATE_MANIFEST = "CreateManifest";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * print manifest transaction.
   */
  public static final String BTX_TYPE_PRINT_MANIFEST = "PrintManifest";

  public static final String BTX_TYPE_GET_MANIFEST = "GetManifest";
  public static final String BTX_TYPE_CONFIRM_BOX_ON_MANIFEST = "ConfBoxOnMnft";
  public static final String BTX_TYPE_SHIP_MANIFEST = "ShipManifest";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a new research request.
   */
  public static final String BTX_TYPE_CREATE_RESEARCH_REQUEST = "CreateResReq";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a new transfer request.
   */
  public static final String BTX_TYPE_CREATE_TRANSFER_REQUEST = "CreateTranReq";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * find requests transaction.  
   */
  public static final String BTX_TYPE_FIND_REQUESTS = "FindReqs";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * find request details transaction.  
   */
  public static final String BTX_TYPE_FIND_REQUEST_DETAILS = "FindReqDet";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * reject request transaction.  
   */
  public static final String BTX_TYPE_REJECT_REQUEST = "RejectReq";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * fulfill request transaction.  
   */
  public static final String BTX_TYPE_FULFILL_REQUEST = "FulfillReq";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * request items boxed transaction.
   */
  public static final String BTX_TYPE_REQUEST_ITEMS_BOXED = "ReqItemsBoxed";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * receipt verification transaction.
   */
  public static final String BTX_TYPE_UPDATE_REQUESTS_AFTER_SHIPMENT = "UpdtReqAftShip";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * receipt verification transaction.
   */
  public static final String BTX_TYPE_SHIPMENT_RECEIPT_VERIFICATION = "ShipRecVerif";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * blood collection transaction.
   */
  public static final String BTX_TYPE_BLOOD_COLLECTION = "BloodCollection";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * check out box transaction.
   */
  public static final String BTX_TYPE_CHECK_OUT_BOX = "CheckOutBox";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * check out box transaction.
   */
  public static final String BTX_TYPE_CREATE_BOX = "CreateBox";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * check out box transaction.
   */
  public static final String BTX_TYPE_SET_BOX_LOCATION = "SetBoxLocation";
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transfer samples to inventory group transaction.
   */
  public static final String BTX_TYPE_TRANSFER_SAMPLES_TO_INV_GROUP = "XferSamplesToIG";
  
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start the process of maintaining a policy.
   */
  public static final String BTX_TYPE_MAINTAIN_POLICY_START = "MaintainPYStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to maintain a policy(create/edit/delete).
   */
  public static final String BTX_TYPE_MAINTAIN_POLICY = "MaintainPY";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start moving samples by inventory groups.
   */
  public static final String BTX_TYPE_MOVE_BY_INVENTORY_GROUP_START = "MoveByIGStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to move samples by inventory groups.
   */
  public static final String BTX_TYPE_MOVE_BY_INVENTORY_GROUP = "MoveByIG";


  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to move samples by inventory groups.
   */
  public static final String BTX_TYPE_MOVE_SAMPLE_BY_INVENTORY_GROUP = "MoveSampleByIG";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start the process of maintaining account urls.
   */
  public static final String BTX_TYPE_MAINTAIN_ACCOUNT_URL_START = "MaintainURLStrt";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to maintain an account url(create/edit/delete).
   */
  public static final String BTX_TYPE_MAINTAIN_ACCOUNT_URL = "MaintainURL";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * delete samples transaction.
   */
  public static final String BTX_TYPE_DELETE_SAMPLES = "DeleteSamples";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * delete sample single transaction.
   */
  public static final String BTX_TYPE_LOG_DELETE_SAMPLE = "DeleteSample";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * delete sample single transaction.
   */
  public static final String BTX_TYPE_CHANGE_PASSWORD = "ChangePassword";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start the process of maintaining a box layout.
   */
  public static final String BTX_TYPE_MAINTAIN_BOX_LAYOUT_START = "MaintainLYStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to maintain a box layout(create/edit/delete).
   */
  public static final String BTX_TYPE_MAINTAIN_BOX_LAYOUT = "MaintainLY";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start the process of managing account box layouts.
   */
  public static final String BTX_TYPE_MANAGE_ACCOUNT_BOX_LAYOUT_START = "MngAcctLYStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to manage an account box layout(create/edit/delete).
   */
  public static final String BTX_TYPE_MANAGE_ACCOUNT_BOX_LAYOUT = "MngAcctLY";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a raw path report.
   */
  public static final String BTX_TYPE_CREATE_RAW_PATH_REPORT = "CreateRawPath";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update a raw path report.
   */
  public static final String BTX_TYPE_UPDATE_RAW_PATH_REPORT = "UpdateRawPath";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create case profile notes.
   */
  public static final String BTX_TYPE_CREATE_CASE_PROFILE_NOTES = "CreateCasePrfNt";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update case profile notes.
   */
  public static final String BTX_TYPE_UPDATE_CASE_PROFILE_NOTES = "UpdateCasePrfNt";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create clinical data.
   */
  public static final String BTX_TYPE_CREATE_CLINICAL_DATA = "CreateClinData";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update clinical data.
   */
  public static final String BTX_TYPE_UPDATE_CLINICAL_DATA = "UpdateClinData";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create an abstracted path report.
   */
  public static final String BTX_TYPE_CREATE_PATH_REPORT = "CreatePathRep";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update an abstracted path report.
   */
  public static final String BTX_TYPE_UPDATE_PATH_REPORT = "UpdatePathRep";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create an abstracted path report section.
   */
  public static final String BTX_TYPE_CREATE_PATH_REPORT_SECTION = "CreatePathRepSx";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update an abstracted path report section.
   */
  public static final String BTX_TYPE_UPDATE_PATH_REPORT_SECTION = "UpdatePathRepSx";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create an abstracted path report diagnostic.
   */
  public static final String BTX_TYPE_CREATE_PATH_REPORT_DIAGNOSTIC = "CreatePathRepDx";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update an abstracted path report diagnostic.
   */
  public static final String BTX_TYPE_UPDATE_PATH_REPORT_DIAGNOSTIC = "UpdatePathRepDx";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a path report section finding.
   */
  public static final String BTX_TYPE_CREATE_PATH_REPORT_SECTION_FINDING = "CreatePathRepSF";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update a path report section finding.
   */
  public static final String BTX_TYPE_UPDATE_PATH_REPORT_SECTION_FINDING = "UpdatePathRepSF";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the process of creating an imported case.
   */
  public static final String BTX_TYPE_CREATE_IMPORTED_CASE_START = "CreateImpCaseS";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create an imported case.
   */
  public static final String BTX_TYPE_CREATE_IMPORTED_CASE = "CreateImpCase";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to get the existing forms for a case.
   */
  public static final String BTX_TYPE_GET_CASE_FORM_SUMMARY = "GetCaseFormSumm";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the process of modifying an imported case.
   */
  public static final String BTX_TYPE_MODIFY_IMPORTED_CASE_START = "ModifyImpCaseS";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to modify an imported case.
   */
  public static final String BTX_TYPE_MODIFY_IMPORTED_CASE = "ModifyImpCase";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to get the default registration form for a case.
   */
  public static final String BTX_TYPE_GET_CASE_REG_FORM = "GetCaseRegForm";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the process of pulling an imported case.
   */
  public static final String BTX_TYPE_PULL_CASE_START = "PullCaseS";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the process of revoking a case.
   */
  public static final String BTX_TYPE_REVOKE_CASE_START = "RevokeCaseS";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to pull an imported case.
   */
  public static final String BTX_TYPE_PULL_CASE = "PullCase";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to revoke a case.
   */
  public static final String BTX_TYPE_REVOKE_CASE = "RevokeCase";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to generate a pick list for an imported case.
   */
  public static final String BTX_TYPE_GENERATE_IMPORTED_CASE_PICKLIST = "ImpCasePickList";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the process of creating an imported case.
   */
  public static final String BTX_TYPE_CREATE_IMPORTED_SAMPLE_START = "CreateImpSampS";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create an imported sample.
   */
  public static final String BTX_TYPE_CREATE_IMPORTED_SAMPLE = "CreateImpSamp";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to get the existing forms for a sample.
   */
  public static final String BTX_TYPE_GET_SAMPLE_FORM_SUMMARY = "GetSampFormSumm";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to get the default registration form for a sample.
   */
  public static final String BTX_TYPE_GET_SAMPLE_REG_FORM = "GetSmplRegForm";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the process of modifying an imported sample.
   */
  public static final String BTX_TYPE_MODIFY_IMPORTED_SAMPLE_START = "ModifyImpSampS";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to modify an imported sample.
   */
  public static final String BTX_TYPE_MODIFY_IMPORTED_SAMPLE = "ModifyImpSamp";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to find objects by id.
   */
  public static final String BTX_TYPE_FIND_BY_ID = "FindById";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to manage account policies.
   */
  public static final String BTX_TYPE_MANAGE_SHIPPING_PARTNERS = "MngShipPartners";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to gather information for editing clinical findings.
   */
  public static final String BTX_TYPE_GET_CLINICAL_FINDINGS = "GetClinFind";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a clinical finding.
   */
  public static final String BTX_TYPE_CREATE_CLINICAL_FINDING = "CrtClinFind";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to modify a clinical finding.
   */
  public static final String BTX_TYPE_MODIFY_CLINICAL_FINDING = "ModClinFind";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction that starts a batch of derivation operations.
   */
  public static final String BTX_TYPE_DERIVATION_BATCH_START = "DerivBatchStart";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction that selects the derivation operation for a batch of derivation operations.
   */
  public static final String BTX_TYPE_DERIVATION_BATCH_SELECT = "DerivBatchSelec";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction that finds all parent samples based on their barcodes for a batch of 
   * derivation operations.
   */
  public static final String BTX_TYPE_DERIVATION_BATCH_LOOKUP = "DerivBatchLook";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to record a batch of derivation operations.
   */
  public static final String BTX_TYPE_DERIVATION_BATCH = "DerivBatchOp";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to record a derivation operation.
   */
  public static final String BTX_TYPE_DERIVATION = "DerivOp";  
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create or update a KnowledgeCapture results form definition.
   */
  public static final String BTX_TYPE_KC_RESULTS_FORM_DEF_CREATE_OR_UPDATE = "CUKCResFormDef";  
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a KnowledgeCapture form definition.
   */
  public static final String BTX_TYPE_KC_FORM_DEF_CREATE = "CreateKCFormDef";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update a KnowledgeCapture form definition.
   */
  public static final String BTX_TYPE_KC_FORM_DEF_UPDATE = "UpdateKCFormDef";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update a KnowledgeCapture form definition.
   */
  public static final String BTX_TYPE_KC_FORM_DEF_DELETE = "DeleteKCFormDef";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to retrieve a KnowledgeCapture form definition.
   */
  public static final String BTX_TYPE_KC_FORM_DEF_LOOKUP = "GetKCFormDef";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start the transaction to create/edit/delete KnowledgeCapture form definitions.
   */
  public static final String BTX_TYPE_KC_FORM_DEF_START = "StartKCFormDef";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to perform a summary of a domain object annotated using KnowledgeCapture.
   */
  public static final String BTX_TYPE_KC_FORM_DOMAIN_OBJECT_SUMMARY = "KCFormDomObjSum";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to perform a summary of a KnowledgeCapture for instance.
   */
  public static final String BTX_TYPE_KC_FORM_SUMMARY = "KCFormSummary";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to prepare for creating a KnowledgeCapture form instance.
   */
  public static final String BTX_TYPE_KC_FORM_CREATE_PREPARE = "CreateKCFormPre";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a KnowledgeCapture form instance.
   */
  public static final String BTX_TYPE_KC_FORM_CREATE = "CreateKCForm";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to prepare to update a KnowledgeCapture form instance.
   */
  public static final String BTX_TYPE_KC_FORM_UPDATE_PREPARE = "UpdateKCFormPre";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to update a KnowledgeCapture form instance.
   */
  public static final String BTX_TYPE_KC_FORM_UPDATE = "UpdateKCForm";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to manage user actions.
   */
  public static final String BTX_TYPE_USER_MANAGEMENT = "UserMngmnt";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to find users.
   */
  public static final String BTX_TYPE_FIND_USERS = "FindUsers";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the creation of a user.
   */
  public static final String BTX_TYPE_CREATE_USER_START = "CreateUserStart";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a user.
   */
  public static final String BTX_TYPE_CREATE_USER = "CreateUser";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the modification of a user.
   */
  public static final String BTX_TYPE_MODIFY_USER_START = "ModifyUserStart";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to modify a user.
   */
  public static final String BTX_TYPE_MODIFY_USER = "ModifyUser";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to manage account actions.
   */
  public static final String BTX_TYPE_ACCOUNT_MANAGEMENT = "AcctMngmnt";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to find accounts.
   */
  public static final String BTX_TYPE_FIND_ACCOUNTS = "FindAccounts";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the creation of an account.
   */
  public static final String BTX_TYPE_CREATE_ACCOUNT_START = "CreateAcctStart";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create an account.
   */
  public static final String BTX_TYPE_CREATE_ACCOUNT = "CreateAcct";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the modification of an account.
   */
  public static final String BTX_TYPE_MODIFY_ACCOUNT_START = "ModifyAcctStart";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to modify an account.
   */
  public static final String BTX_TYPE_MODIFY_ACCOUNT = "ModifyAcct";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to manage locations for an account.
   */
  public static final String BTX_TYPE_MANAGE_ACCOUNT_LOCATIONS = "MngAcctLoc";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the creation of a location for an account.
   */
  public static final String BTX_TYPE_CREATE_ACCOUNT_LOCATION_START = "CrtAcctLocStrt";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to create a location for an account.
   */
  public static final String BTX_TYPE_CREATE_ACCOUNT_LOCATION = "CrtAcctLoc";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the modification of a location for an account.
   */
  public static final String BTX_TYPE_MODIFY_ACCOUNT_LOCATION_START = "ModAcctLocStrt";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to modify a location for an account.
   */
  public static final String BTX_TYPE_MODIFY_ACCOUNT_LOCATION = "ModAcctLoc";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the modification of privileges.
   */
  public static final String BTX_TYPE_MANAGE_PRIVILEGES_START = "MngPrivStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to modify privileges.
   */
  public static final String BTX_TYPE_MANAGE_PRIVILEGES = "MngPriv";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to begin the modification of a user's profile.
   */
  public static final String BTX_TYPE_CHANGE_PROFILE_START = "ChngProfStart";  

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to modify a user's profile.
   */
  public static final String BTX_TYPE_CHANGE_PROFILE = "ChngProf";  
  
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to generate a sample id.
   */
  public static final String BTX_TYPE_GENERATE_SAMPLE_ID = "GenSampleId";  
  
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start the process of maintaining a role.
   */
  public static final String BTX_TYPE_MAINTAIN_ROLE_START = "MaintRoleStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to maintain a role(create/edit/delete).
   */
  public static final String BTX_TYPE_MAINTAIN_ROLE = "MaintRole";
  
  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to start the process of managing roles for a user.
   */
  public static final String BTX_TYPE_MANAGE_ROLES_START = "MngRolesStart";

  /**
   * The value returned by {@link #getBTXType() getBTXType} for a
   * transaction to manage roles for a user.
   */
  public static final String BTX_TYPE_MANAGE_ROLES = "MngRoles";

  //********* NOTE TO DEVELOPERS ***************************************
  // When you add new BTX_TYPE_* constants above, also add new
  // entries to:
  //
  //   (1) _transactionMetaData HashMap in the static
  //       initialization block below.
  //
  // Also, please make sure that the values of the BTX_TYPE_* constants
  // have no more than 15 characters (the size of the corresponding
  // database column).
  //********************************************************************

  /**
   * This stores the mapping of transaction type codes (BTX_TYPE_*) to
   * some meta-data information regarding that transaction type.
   * It is initialized in the static initialization block for this class.
   * The entries in the map are BTXDetailsMetaData objects (which is an
   * inner class of BTXDetails).
   */
  private static final transient Map _transactionMetaData;

  //********* NOTE TO DEVELOPERS ***************************************
  // When you add new fields below, please also update
  // describeIntoHistoryRecord and populateFromHistoryRecord.
  //********************************************************************
  //
  private long _transactionId = 0;
  private String _userId = null;
  private String _userAccount = null;
  private SecurityInfo _securityInfo = null;
  private Timestamp _beginTimestamp = null;
  private Timestamp _endTimestamp = null;
  private String _exceptionText = null;
  private String _exceptionStackTrace = null;
  private BTXActionForward _actionForward = null;
  private BtxActionErrors _actionErrors = new BtxActionErrors();
  private BtxActionMessages _actionMessages = new BtxActionMessages();
  private boolean _isTransactionCompleted = true;
  private String _transactionType;
  private String _subActionType;
  //
  //********* NOTE TO DEVELOPERS ***************************************
  // When you add new fields above, please also update
  // describeIntoHistoryRecord and populateFromHistoryRecord.
  //********************************************************************
  
  //flag to indicate if this BTXDetails object is to be used for validation only
  private boolean _validationOnly = false;

  // Static class initialization:
  static {
    _transactionMetaData = new HashMap();

    _transactionMetaData.put(
      BTX_TYPE_BOX_SCAN,
      new BTXDetailsMetaData("Box Scan", BTXDetailsBoxScan.class, true));
    _transactionMetaData.put(
      BTX_TYPE_CHECK_OUT_SAMPLES,
      new BTXDetailsMetaData("Check Out Samples", BTXDetailsCheckOutSamples.class, true));
    _transactionMetaData.put(
      BTX_TYPE_SCAN_AND_STORE,
      new BTXDetailsMetaData("Scan and Store", BtxDetailsScanAndStore.class, true));
    _transactionMetaData.put(
      BTX_TYPE_SUBDIVIDE_SAMPLE,
      new BTXDetailsMetaData("Get Subdivide Sample", BTXDetailsSubdivideSample.class, false));
    _transactionMetaData.put(
      BTX_TYPE_SUBDIVIDE,
      new BTXDetailsMetaData("Subdivide Sample", BTXDetailsSubdivide.class, true));
    _transactionMetaData.put(
      BTX_TYPE_VIEW_SUBDIVIDE,
      new BTXDetailsMetaData("View Subdivide Relationship", BTXDetailsViewSubdivide.class, false));
    _transactionMetaData.put(
      BTX_TYPE_UPDATE_BOX_LOCATION,
      new BTXDetailsMetaData("Update Box Location", BTXDetailsUpdateBoxLocation.class, true));
    _transactionMetaData.put(
      BTX_TYPE_PATH_SAMPLE_REQUEST,
      new BTXDetailsMetaData("Pathology Sample Request", BTXDetailsPickListRequest.class, true));
    _transactionMetaData.put(
      BTX_TYPE_QUALITY_CONTROL,
      new BTXDetailsMetaData("Quality Control", BTXDetailsQualityControl.class, true));
    _transactionMetaData.put(
      BTX_TYPE_RECEIPT_VERIFICATION,
      new BTXDetailsMetaData("Receipt Verification", BtxDetailsReceiptVerification.class, true));
    _transactionMetaData.put(
      BTX_TYPE_RND_SAMPLE_REQUEST,
      new BTXDetailsMetaData("R&D Sample Request", BTXDetailsPickListRequest.class, true));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_SLIDES_PREPARE,
      new BTXDetailsMetaData("Generate Slide Labels", BTXDetailsCreateSlidesPrepare.class, false));
    //Note - both the BTXDetailsCreateSlides and BTXDetailsCreateSlidesSingle
    //entries have a value of "Label Generation" as their description, so that
    //the entry looks appropriate when displayed.  Nobody using the app would
    //know what "Label Generation Single" meant
    _transactionMetaData.put(
      BTX_TYPE_CREATE_SLIDES,
      new BTXDetailsMetaData("Label Generation", BTXDetailsCreateSlides.class, false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_SLIDES_SINGLE,
      new BTXDetailsMetaData("Label Generation", BTXDetailsCreateSlidesSingle.class, true));
    //Note - both the BTXDetailsPathLabSlides and BTXDetailsPathLabSlidesSingle
    //entries have a value of "Path Lab" as their description, so that
    //the entry looks appropriate when displayed.  Nobody using the app would
    //know what "Path Lab Single" meant
    _transactionMetaData.put(
      BTX_TYPE_PATHLAB_SLIDES,
      new BTXDetailsMetaData("Path Lab", BTXDetailsPathLabSlides.class, false));
    _transactionMetaData.put(
      BTX_TYPE_PATHLAB_SLIDES_SINGLE,
      new BTXDetailsMetaData("Path Lab", BTXDetailsPathLabSlidesSingle.class, true));
    //Note - both the BTXDetailsSetSlideLocations and BTXDetailsSetSlideLocationsSingle
    //entries have a value of "Set Slide Location" as their description, so that
    //the entry looks appropriate when displayed.  Nobody using the app would
    //know what "Set Slide Loc Single" meant
    _transactionMetaData.put(
      BTX_TYPE_SET_SLIDE_LOCATIONS,
      new BTXDetailsMetaData("Set Slide Location", BTXDetailsSetSlideLocations.class, false));
    _transactionMetaData.put(
      BTX_TYPE_SET_SLIDE_LOCATIONS_SINGLE,
      new BTXDetailsMetaData("Set Slide Location", BTXDetailsSetSlideLocationsSingle.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MARK_SAMPLE_PULLED,
      new BTXDetailsMetaData("Sample Pulled", BTXDetailsMarkSamplePulled.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MARK_SAMPLE_UNPULLED,
      new BTXDetailsMetaData("Sample Unpulled", BTXDetailsMarkSampleUnpulled.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MARK_SAMPLE_RELEASED,
      new BTXDetailsMetaData("Sample Released", BTXDetailsMarkSampleReleased.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MARK_SAMPLE_UNRELEASED,
      new BTXDetailsMetaData("Sample Unreleased", BTXDetailsMarkSampleUnreleased.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MARK_SAMPLE_DISCORDANT,
      new BTXDetailsMetaData("Sample Discordant", BTXDetailsMarkSampleDiscordant.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MARK_SAMPLE_NONDISCORDANT,
      new BTXDetailsMetaData(
        "Sample Nondiscordant",
        BTXDetailsMarkSampleNondiscordant.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_PATH_EVAL_PREPARE,
      new BTXDetailsMetaData(
        "Pathology Evaluation Prep",
        BTXDetailsCreatePathologyEvaluationPrepare.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_PATH_EVAL,
      new BTXDetailsMetaData(
        "Pathology Evaluation",
        BTXDetailsCreatePathologyEvaluation.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_REPORT_PATH_EVAL,
      new BTXDetailsMetaData("Report Evaluation", BTXDetailsReportPathologyEvaluation.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MANAGE_PATH_EVAL,
      new BTXDetailsMetaData("Manage Evaluations", BTXDetailsGetSamplePathologyInfo.class, false));
    //Note - both the BTXDetailsGetPathQCSampleSummary and BTXDetailsGetPathQCSampleDetails
    //entries have a value of "Path QC" as their description, so that
    //the entry looks appropriate when displayed.  Nobody using the app would
    //know what "Path QC Summary" and "Path QC Details" meant
    _transactionMetaData.put(
      BTX_TYPE_PATH_QC_SUMMARY,
      new BTXDetailsMetaData("Path QC", BTXDetailsGetPathQCSampleSummary.class, false));
    _transactionMetaData.put(
      BTX_TYPE_PATH_QC_DETAILS,
      new BTXDetailsMetaData("Path QC", BTXDetailsGetPathQCSampleDetails.class, false));
    _transactionMetaData.put(
      BTX_TYPE_MARK_SAMPLE_QCPOSTED,
      new BTXDetailsMetaData("Sample QCPosted", BTXDetailsMarkSampleQCPosted.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MARK_SAMPLE_UNQCPOSTED,
      new BTXDetailsMetaData("Sample UnQCPosted", BTXDetailsMarkSampleUnQCPosted.class, true));
    _transactionMetaData.put(
        BTX_TYPE_GET_KC_QUERY_FORM,
        new BTXDetailsMetaData("Get KC Query Form", BTXDetailsGetKcQueryForm.class, false));
    _transactionMetaData.put(
        BTX_TYPE_GET_SAMPLE_SUMMARY_START,
        new BTXDetailsMetaData("Start Sample Summary Query", BTXDetailsGetSampleSummaryStart.class, false));
    _transactionMetaData.put(
      BTX_TYPE_GET_SAMPLE_SUMMARY,
      new BTXDetailsMetaData("Sample Summary Query", BTXDetailsGetSampleSummary.class, true));
    _transactionMetaData.put(
      BTX_TYPE_GET_SAMPLE_SUMMARY_NO_HISTORY,
      new BTXDetailsMetaData(
        "Sample Summary Query No History",
        BTXDetailsGetSampleSummaryNoHistory.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_GET_SAMPLE_SUMMARY_SQL,
      new BTXDetailsMetaData("Sample Summary Query SQL", BTXDetailsGetSummarySql.class, false));
    _transactionMetaData.put(
      BTX_TYPE_GET_RNA,
      new BTXDetailsMetaData("RNA Detail Query", BTXDetailsGetRna.class, false));
    _transactionMetaData.put(
      BTX_TYPE_GET_SAMPLES,
      new BTXDetailsMetaData("Sample Detail Query", BTXDetailsGetSamples.class, false));
    _transactionMetaData.put(
      BTX_TYPE_UPDATE_COMPUTED_DATA,
      new BTXDetailsMetaData("Update Computed Data", BTXDetailsUpdateComputedData.class, false));
    _transactionMetaData.put(
      BTX_TYPE_UPDATE_COMPUTED_DATA_SINGLE,
      new BTXDetailsMetaData(
        "Update Computed Data",
        BTXDetailsUpdateComputedDataSingle.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_HISTO_QC_SAMPLES,
      new BTXDetailsMetaData("Histo QC", BTXDetailsHistoQCSamples.class, false));
    _transactionMetaData.put(
      BTX_TYPE_HISTO_QC_SAMPLES_SINGLE,
      new BTXDetailsMetaData("Histo QC", BTXDetailsHistoQCSamplesSingle.class, true));
    _transactionMetaData.put(
      BTX_TYPE_HISTORY_NOTE,
      new BTXDetailsMetaData("History Note", BTXDetailsHistoryNote.class, true));
    _transactionMetaData.put(
      BTX_TYPE_ADD_TO_HOLD_LIST,
      new BTXDetailsMetaData("Add To Hold List", BTXDetailsAddToHoldList.class, true));
    _transactionMetaData.put(
      BTX_TYPE_ADD_TO_REQUEST_SAMPLE,
      new BTXDetailsMetaData("Add To Request Sample", BTXDetailsAddToRequestSample.class, false));
    _transactionMetaData.put(
      BTX_TYPE_REMOVE_FROM_HOLD_LIST,
      new BTXDetailsMetaData("Remove From Hold List", BTXDetailsRemoveFromHoldList.class, true));

    _transactionMetaData.put(
      BTX_TYPE_PRINT_SLIDES,
      new BTXDetailsMetaData("Print Slides", BTXDetailsPrintSlides.class, false));
    //Note - this transaction never logged. BTXDetailsPrintSlidesValidation
    //is used for validation purpose.    
    _transactionMetaData.put(
      BTX_TYPE_PRINT_SLIDES_VALIDATION,
      new BTXDetailsMetaData(
        "Print Slides Validation",
        BTXDetailsPrintSlidesValidation.class,
        false));
    //Note - the create image transaction is marked as logged even though this logging is NOT
    //done via the BTX framework.  A database trigger is used to record the transaction.
    _transactionMetaData.put(
      BTX_TYPE_CREATE_IMAGE,
      new BTXDetailsMetaData("Create Image", BTXDetailsCreateImage.class, true));

    _transactionMetaData.put(
      BTX_TYPE_LOGIN,
      new BTXDetailsMetaData("Login", BTXDetailsLogin.class, true));
    _transactionMetaData.put(
      BTX_TYPE_DISABLE_LOGIN,
      new BTXDetailsMetaData("Disable Login", BTXDetailsDisableLogin.class, true));
    _transactionMetaData.put(
      BTX_TYPE_FAILED_LOGIN,
      new BTXDetailsMetaData("Failed Login", BtxDetailsFailedLogin.class, true));

    //Note - both the BTXDetailsCreateIncidents and BTXDetailsCreateIncidentsSingle
    //entries have a value of "Create Incident" as their description, so that
    //the entry looks appropriate when displayed.  Nobody using the app would
    //know what "Create Incidents" and "Create Incidents Single" meant
    _transactionMetaData.put(
      BTX_TYPE_CREATE_INCIDENTS,
      new BTXDetailsMetaData("Create Incident", BTXDetailsCreateIncidents.class, false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_INCIDENTS_SINGLE,
      new BTXDetailsMetaData("Create Incident", BTXDetailsCreateIncidentsSingle.class, true));

    _transactionMetaData.put(
      BTX_TYPE_CREATE_DONOR,
      new BTXDetailsMetaData("Register Donor", BTXDetailsCreateDonor.class, true));
    _transactionMetaData.put(
      BTX_TYPE_UPDATE_DONOR,
      new BTXDetailsMetaData("Update Donor", BTXDetailsUpdateDonor.class, true));
    _transactionMetaData.put(
      BTX_TYPE_DISPLAY_BANNER,
      new BTXDetailsMetaData("Get Display Banner", BtxDetailsGetDisplayBanner.class, false));
    _transactionMetaData.put(
    BTX_TYPE_MANAGE_INVENTORY_GROUPS_START,
      new BTXDetailsMetaData(
        "Manage Inventory Groups Start",
        BtxDetailsManageInventoryGroupsStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_MANAGE_INVENTORY_GROUPS,
      new BTXDetailsMetaData(
        "Manage Inventory Groups",
        BtxDetailsManageInventoryGroups.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_LOG_ALLOCATE_SINGLE,
      new BTXDetailsMetaData("Allocate Sample", BtxDetailsLogAllocateSingle.class, true));
    _transactionMetaData.put(
      BTX_TYPE_LOG_CREATE_SAMPLE,
      new BTXDetailsMetaData("Create Sample", BtxDetailsLogCreateSample.class, true));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_CASE,
      new BTXDetailsMetaData("Create Case", BtxDetailsCreateCase.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MAINTAIN_LOGICAL_REPOSITORY,
      new BTXDetailsMetaData(
        "Maintain Inventory Group",
        BtxDetailsMaintainLogicalRepository.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_MAINTAIN_LOGICAL_REPOSITORY_START,
      new BTXDetailsMetaData(
        "Start Maintain Inventory Group",
        BtxDetailsMaintainLogicalRepositoryStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_MANIFEST,
      new BTXDetailsMetaData("Create Manifest", BtxDetailsCreateManifest.class, true));
    _transactionMetaData.put(
      BTX_TYPE_PRINT_MANIFEST,
      new BTXDetailsMetaData("Print Manifest", BtxDetailsPrintManifest.class, false));
    _transactionMetaData.put(
      BTX_TYPE_GET_MANIFEST,
      new BTXDetailsMetaData("Get Manifest", BtxDetailsGetManifest.class, false));
    _transactionMetaData.put(
      BTX_TYPE_CONFIRM_BOX_ON_MANIFEST,
      new BTXDetailsMetaData(
        "Confirm Box On Manifest",
        BtxDetailsConfirmBoxOnManifest.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_SHIP_MANIFEST,
      new BTXDetailsMetaData("Ship Manifest", BtxDetailsShipManifest.class, true));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_RESEARCH_REQUEST,
      new BTXDetailsMetaData(
        "Create Research Request",
        BtxDetailsCreateResearchRequest.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_TRANSFER_REQUEST,
      new BTXDetailsMetaData(
        "Create Transfer Request",
        BtxDetailsCreateTransferRequest.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_FIND_REQUESTS,
      new BTXDetailsMetaData("Find Requests", BtxDetailsFindRequests.class, false));
    _transactionMetaData.put(
      BTX_TYPE_FIND_REQUEST_DETAILS,
      new BTXDetailsMetaData("Find Request Details", BtxDetailsFindRequestDetails.class, false));
    _transactionMetaData.put(
      BTX_TYPE_REJECT_REQUEST,
      new BTXDetailsMetaData("Reject Request", BtxDetailsRejectRequest.class, true));
    _transactionMetaData.put(
      BTX_TYPE_FULFILL_REQUEST,
      new BTXDetailsMetaData("Fulfill Request", BtxDetailsFulfillRequest.class, true));
    _transactionMetaData.put(
      BTX_TYPE_REQUEST_ITEMS_BOXED,
      new BTXDetailsMetaData("Request Items Boxed", BtxDetailsRequestItemsBoxed.class, true));
    _transactionMetaData.put(
      BTX_TYPE_UPDATE_REQUESTS_AFTER_SHIPMENT,
      new BTXDetailsMetaData(
        "Update Requests After Shipment",
        BtxDetailsUpdateRequestsAfterShipment.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_SHIPMENT_RECEIPT_VERIFICATION,
      new BTXDetailsMetaData(
        "Shipment Receipt Verification",
        BtxDetailsShipmentReceiptVerification.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_CHECK_OUT_BOX,
      new BTXDetailsMetaData("Check Out Box", BtxDetailsCheckOutBox.class, false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_BOX,
      new BTXDetailsMetaData("Create Box", BtxDetailsCreateBox.class, false));
    _transactionMetaData.put(
      BTX_TYPE_SET_BOX_LOCATION,
      new BTXDetailsMetaData("Set Box Location", BtxDetailsSetBoxLocation.class, false));
    //Note - the transfer samples to inventory group transaction is marked as logged even though 
    //this logging is NOT done via the BTX framework.  A database stored procedure is user
    //to create the history record.
    _transactionMetaData.put(
      BTX_TYPE_TRANSFER_SAMPLES_TO_INV_GROUP,
      new BTXDetailsMetaData("Transfer Samples To Inventory Group", BtxDetailsTransferSamplesToInventoryGroup.class, true));
    _transactionMetaData.put(
      BTX_TYPE_MAINTAIN_POLICY_START,
      new BTXDetailsMetaData(
        "Start Maintain Policy",
        BtxDetailsMaintainPolicyStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_MAINTAIN_POLICY,
      new BTXDetailsMetaData(
        "Maintain Policy",
        BtxDetailsMaintainPolicy.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_MAINTAIN_ACCOUNT_URL_START,
      new BTXDetailsMetaData(
        "Start Maintain Account URL",
        BtxDetailsMaintainAccountUrlStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_MAINTAIN_ACCOUNT_URL,
      new BTXDetailsMetaData(
        "Maintain Url",
        BtxDetailsMaintainAccountUrl.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_DELETE_SAMPLES,
      new BTXDetailsMetaData(
        "Delete Samples",
        BtxDetailsDeleteSamples.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_LOG_DELETE_SAMPLE,
      new BTXDetailsMetaData(
        "Delete Sample",
        BtxDetailsLogDeleteSample.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_CHANGE_PASSWORD,
      new BTXDetailsMetaData(
        "Change Password",
        BtxDetailsChangePassword.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_MAINTAIN_BOX_LAYOUT_START,
      new BTXDetailsMetaData(
        "Start Maintain Box Layout",
        BtxDetailsMaintainBoxLayoutStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_MAINTAIN_BOX_LAYOUT,
      new BTXDetailsMetaData(
        "Maintain Box Layout",
        BtxDetailsMaintainBoxLayout.class,
        true));
    _transactionMetaData.put( 
      BTX_TYPE_MANAGE_ACCOUNT_BOX_LAYOUT_START,
      new BTXDetailsMetaData(
        "Start Manage Account Box Layout",
        BtxDetailsManageAccountBoxLayoutStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_MANAGE_ACCOUNT_BOX_LAYOUT,
      new BTXDetailsMetaData(
        "Manage Account Box Layout",
        BtxDetailsManageAccountBoxLayout.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_RAW_PATH_REPORT,
      new BTXDetailsMetaData(
        "Create Raw Path Report",
        BTXDetailsCreateRawPathReport.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_UPDATE_RAW_PATH_REPORT,
      new BTXDetailsMetaData(
        "Edit Raw Path Report",
        BTXDetailsUpdateRawPathReport.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_CASE_PROFILE_NOTES,
      new BTXDetailsMetaData(
        "Create Case Profile Notes",
        BTXDetailsCreateCaseProfileNotes.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_UPDATE_CASE_PROFILE_NOTES,
      new BTXDetailsMetaData(
        "Edit Case Profile Notes",
        BTXDetailsUpdateCaseProfileNotes.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_CLINICAL_DATA,
      new BTXDetailsMetaData(
        "Create Clinical Data",
        BTXDetailsCreateClinicalData.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_UPDATE_CLINICAL_DATA,
      new BTXDetailsMetaData(
        "Edit Clinical Data",
        BTXDetailsUpdateClinicalData.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_PATH_REPORT,
      new BTXDetailsMetaData(
        "Create Path Report",
        BTXDetailsCreatePathReport.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_UPDATE_PATH_REPORT,
      new BTXDetailsMetaData(
        "Edit Path Report",
        BTXDetailsUpdatePathReport.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_PATH_REPORT_SECTION,
      new BTXDetailsMetaData(
        "Create Path Report Section",
        BTXDetailsCreatePathReportSection.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_UPDATE_PATH_REPORT_SECTION,
      new BTXDetailsMetaData(
        "Edit Path Report Section",
        BTXDetailsUpdatePathReportSection.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_PATH_REPORT_DIAGNOSTIC,
      new BTXDetailsMetaData(
        "Create Path Report Diagnostic Test",
        BTXDetailsCreatePathReportDiagnostic.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_UPDATE_PATH_REPORT_DIAGNOSTIC,
      new BTXDetailsMetaData(
        "Edit Path Report Diagnostic Test",
        BTXDetailsUpdatePathReportDiagnostic.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_PATH_REPORT_SECTION_FINDING,
      new BTXDetailsMetaData(
        "Create Path Report Section Finding",
        BTXDetailsCreatePathReportSectionFinding.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_UPDATE_PATH_REPORT_SECTION_FINDING,
      new BTXDetailsMetaData(
        "Edit Path Report Section Finding",
        BTXDetailsUpdatePathReportSectionFinding.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_IMPORTED_CASE_START,
      new BTXDetailsMetaData(
        "Register Imported Case Start",
        BtxDetailsCreateImportedCaseStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_IMPORTED_CASE,
      new BTXDetailsMetaData(
        "Register Case",
        BtxDetailsCreateImportedCase.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_GET_CASE_FORM_SUMMARY,
      new BTXDetailsMetaData(
        "Get Case Form Summary",
        BtxDetailsGetCaseFormSummary.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_MODIFY_IMPORTED_CASE_START,
      new BTXDetailsMetaData(
        "Modify Imported Case Start",
        BtxDetailsModifyImportedCaseStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_MODIFY_IMPORTED_CASE,
      new BTXDetailsMetaData(
        "Modify Case",
        BtxDetailsModifyImportedCase.class,
        true));
    _transactionMetaData.put(
        BTX_TYPE_GET_CASE_REG_FORM,
          new BTXDetailsMetaData(
            "Get Case Default Registration Form",
            BtxDetailsGetCaseRegistrationForm.class,
            false));
    _transactionMetaData.put(
    BTX_TYPE_PULL_CASE_START,
      new BTXDetailsMetaData(
        "Pull Case Start",
        BtxDetailsPullCaseStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_PULL_CASE,
      new BTXDetailsMetaData(
        "Pull Case",
        BtxDetailsPullCase.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_REVOKE_CASE_START,
      new BTXDetailsMetaData(
        "Revoke Case Start",
        BtxDetailsRevokeCaseStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_REVOKE_CASE,
      new BTXDetailsMetaData(
        "Revoke Case",
        BtxDetailsRevokeCase.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_GENERATE_IMPORTED_CASE_PICKLIST,
      new BTXDetailsMetaData(
        "Generate Imported Case Pick List",
        BtxDetailsGenerateCasePickList.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_IMPORTED_SAMPLE_START,
      new BTXDetailsMetaData(
        "Register Imported Sample Start",
        BtxDetailsCreateImportedSampleStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_IMPORTED_SAMPLE,
      new BTXDetailsMetaData(
        "Register Sample",
        BtxDetailsCreateImportedSample.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_GET_SAMPLE_FORM_SUMMARY,
      new BTXDetailsMetaData(
        "Get Sample Form Summary",
        BtxDetailsGetSampleFormSummary.class,
        false));
    _transactionMetaData.put(
        BTX_TYPE_GET_SAMPLE_REG_FORM,
          new BTXDetailsMetaData(
            "Get Sample Default Registration Form",
            BtxDetailsGetSampleRegistrationForm.class,
            false));
    _transactionMetaData.put(
    BTX_TYPE_MODIFY_IMPORTED_SAMPLE_START,
      new BTXDetailsMetaData(
        "Modify Imported Sample Start",
        BtxDetailsModifyImportedSampleStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_MODIFY_IMPORTED_SAMPLE,
      new BTXDetailsMetaData(
        "Modify Sample",
        BtxDetailsModifyImportedSample.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_FIND_BY_ID,
      new BTXDetailsMetaData(
        "Find By Id",
        BtxDetailsFindById.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_MANAGE_SHIPPING_PARTNERS,
      new BTXDetailsMetaData(
        "Manage Shipping Partners",
        BtxDetailsManageShippingPartners.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_GET_CLINICAL_FINDINGS,
      new BTXDetailsMetaData(
        "Get Clinical Findings",
        BtxDetailsGetClinicalFindings.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_CLINICAL_FINDING,
      new BTXDetailsMetaData(
        "Create Clinical Finding",
        BtxDetailsCreateClinicalFinding.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_MODIFY_CLINICAL_FINDING,
      new BTXDetailsMetaData(
        "Modify Clinical Finding",
        BtxDetailsModifyClinicalFinding.class,
        true));

    _transactionMetaData.put(
      BTX_TYPE_MOVE_BY_INVENTORY_GROUP_START,
      new BTXDetailsMetaData(
        "Move By Inventory Group Start",
        BtxDetailsMoveByInventoryGroupStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_MOVE_BY_INVENTORY_GROUP,
      new BTXDetailsMetaData(
        "Move By Inventory Group",
        BtxDetailsMoveByInventoryGroup.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_MOVE_SAMPLE_BY_INVENTORY_GROUP,
      new BTXDetailsMetaData(
        "Move Sample",
        BtxDetailsMoveSample.class,
        true));

    _transactionMetaData.put(
    BTX_TYPE_DERIVATION,
      new BTXDetailsMetaData(
        "Derivation Operation",
        BtxDetailsDerivation.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_DERIVATION_BATCH,
      new BTXDetailsMetaData(
        "Batch Derivation Operation",
        BtxDetailsDerivationBatch.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_DERIVATION_BATCH_START,
      new BTXDetailsMetaData(
        "Batch Derivation Operation Start",
        BtxDetailsDerivationBatchStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_DERIVATION_BATCH_SELECT,
      new BTXDetailsMetaData(
        "Batch Derivation Operation Select Operation",
        BtxDetailsDerivationBatchSelect.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_DERIVATION_BATCH_LOOKUP,
      new BTXDetailsMetaData(
        "Batch Derivation Operation Lookup Parents",
        BtxDetailsDerivationBatchLookup.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_KC_RESULTS_FORM_DEF_CREATE_OR_UPDATE,
      new BTXDetailsMetaData(
        "Create or Update KnowledgeCapture Results Form Definition",
        BtxDetailsKcResultsFormDefinitionCreateOrUpdate.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_KC_FORM_DEF_CREATE,
      new BTXDetailsMetaData(
        "Create KnowledgeCapture Form Definition",
        BtxDetailsKcFormDefinitionCreate.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_KC_FORM_DEF_UPDATE,
      new BTXDetailsMetaData(
        "Update KnowledgeCapture Form Definition",
        BtxDetailsKcFormDefinitionUpdate.class,
        true));  
    _transactionMetaData.put(
    BTX_TYPE_KC_FORM_DEF_DELETE,
      new BTXDetailsMetaData(
        "Delete KnowledgeCapture Form Definition",
        BtxDetailsKcFormDefinitionDelete.class,
        true));  
    _transactionMetaData.put(
    BTX_TYPE_KC_FORM_DEF_LOOKUP,
      new BTXDetailsMetaData(
        "Get KnowledgeCapture Form Definitions",
        BtxDetailsKcFormDefinitionLookup.class,
        false));  
    _transactionMetaData.put(
    BTX_TYPE_KC_FORM_DEF_START,
      new BTXDetailsMetaData(
        "Start KnowledgeCapture Form Definitions",
        BtxDetailsKcFormDefinitionStart.class,
        false));  
    _transactionMetaData.put(
        BTX_TYPE_KC_FORM_DOMAIN_OBJECT_SUMMARY,
          new BTXDetailsMetaData(
            "Summary of Domain Object Annotated Using KnowledgeCapture",
            BtxDetailsKcFormInstanceDomainObjectSummary.class,
            false));
    _transactionMetaData.put(
        BTX_TYPE_KC_FORM_SUMMARY,
          new BTXDetailsMetaData(
            "Summary of a KnowledgeCapture Form Instance",
            BtxDetailsKcFormInstanceSummary.class,
            false));
    _transactionMetaData.put(
        BTX_TYPE_KC_FORM_CREATE_PREPARE,
          new BTXDetailsMetaData(
            "Prepare for creating Form",
            BtxDetailsKcFormInstanceCreatePrepare.class,
            false));
    _transactionMetaData.put(
    BTX_TYPE_KC_FORM_CREATE,
      new BTXDetailsMetaData(
        "Create Form",
        BtxDetailsKcFormInstanceCreate.class,
        true));
    _transactionMetaData.put(
        BTX_TYPE_KC_FORM_UPDATE_PREPARE,
          new BTXDetailsMetaData(
            "Prepare for updating Form",
            BtxDetailsKcFormInstanceUpdatePrepare.class,
            false));  
    _transactionMetaData.put(
    BTX_TYPE_KC_FORM_UPDATE,
      new BTXDetailsMetaData(
        "Update Form",
        BtxDetailsKcFormInstanceUpdate.class,
        true));  
    _transactionMetaData.put(
    BTX_TYPE_USER_MANAGEMENT,
      new BTXDetailsMetaData(
        "Generic user management",
        BtxDetailsUserManagement.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_FIND_USERS,
      new BTXDetailsMetaData(
        "Find User",
        BtxDetailsFindUsers.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_USER_START,
      new BTXDetailsMetaData(
        "Create User Start",
        BtxDetailsCreateUserStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_CREATE_USER,
      new BTXDetailsMetaData(
        "Create User",
        BtxDetailsCreateUser.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_MODIFY_USER_START,
      new BTXDetailsMetaData(
        "Modify User Start",
        BtxDetailsModifyUserStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_MODIFY_USER,
      new BTXDetailsMetaData(
        "Modify User",
        BtxDetailsModifyUser.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_ACCOUNT_MANAGEMENT,
      new BTXDetailsMetaData(
        "Generic account management",
        BtxDetailsAccountManagement.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_FIND_ACCOUNTS,
      new BTXDetailsMetaData(
        "Find Account",
        BtxDetailsFindAccounts.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_ACCOUNT_START,
      new BTXDetailsMetaData(
        "Create Account Start",
        BtxDetailsCreateAccountStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_ACCOUNT,
      new BTXDetailsMetaData(
        "Create Account",
        BtxDetailsCreateAccount.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_MODIFY_ACCOUNT_START,
      new BTXDetailsMetaData(
        "Modify Account Start",
        BtxDetailsModifyAccountStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_MODIFY_ACCOUNT,
      new BTXDetailsMetaData(
        "Modify Account",
        BtxDetailsModifyAccount.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_MANAGE_ACCOUNT_LOCATIONS,
      new BTXDetailsMetaData(
        "Manage Account Locations",
        BtxDetailsManageAccountLocations.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_ACCOUNT_LOCATION_START,
      new BTXDetailsMetaData(
        "Create Account Location Start",
        BtxDetailsCreateAccountLocationStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_CREATE_ACCOUNT_LOCATION,
      new BTXDetailsMetaData(
        "Create Account Location",
        BtxDetailsCreateAccountLocation.class,
        true));
    _transactionMetaData.put(
      BTX_TYPE_MODIFY_ACCOUNT_LOCATION_START,
      new BTXDetailsMetaData(
        "Modify Account Location Start",
        BtxDetailsModifyAccountLocationStart.class,
        false));
    _transactionMetaData.put(
      BTX_TYPE_MODIFY_ACCOUNT_LOCATION,
      new BTXDetailsMetaData(
        "Modify Account Location",
        BtxDetailsModifyAccountLocation.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_MANAGE_PRIVILEGES_START,
      new BTXDetailsMetaData(
        "Manage Privileges Start",
        BtxDetailsManagePrivilegesStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_MANAGE_PRIVILEGES,
      new BTXDetailsMetaData(
        "Manage Privileges",
        BtxDetailsManagePrivileges.class,
        true));
    _transactionMetaData.put(
    BTX_TYPE_CHANGE_PROFILE_START,
      new BTXDetailsMetaData(
        "Change Profile Start",
        BtxDetailsChangeProfileStart.class,
        false));
    _transactionMetaData.put(
    BTX_TYPE_CHANGE_PROFILE,
      new BTXDetailsMetaData(
        "Change Profile",
        BtxDetailsChangeProfile.class,
        true));
    _transactionMetaData.put(
        BTX_TYPE_GENERATE_SAMPLE_ID,
          new BTXDetailsMetaData(
            "Generate Sample Id",
            BtxDetailsGenerateSampleId.class,
            false));
    _transactionMetaData.put(
        BTX_TYPE_MAINTAIN_ROLE_START,
        new BTXDetailsMetaData(
          "Start Maintain Role",
          BtxDetailsMaintainRoleStart.class,
          false));
      _transactionMetaData.put(
        BTX_TYPE_MAINTAIN_ROLE,
        new BTXDetailsMetaData(
          "Maintain Role",
          BtxDetailsMaintainRole.class,
          true));
      _transactionMetaData.put(
          BTX_TYPE_MANAGE_ROLES_START,
          new BTXDetailsMetaData(
            "Start Manage Roles",
            BtxDetailsManageRolesStart.class,
            false));
        _transactionMetaData.put(
          BTX_TYPE_MANAGE_ROLES,
          new BTXDetailsMetaData(
            "Manage Roles",
            BtxDetailsManageRoles.class,
            true));
}
      
  /**
   * Represent a variety of meta-data information about a business transaction.  The
   * information this represents for each business transaction type is:
   * <ul>
   * <li>displayName: A short user-friendly string describing the transaction type.</li>
   * <li>detailsSubclass: The subclass of BTXDetails that represents details for that
   *     type of transaction..</li>
   * <li>isLogged: A flag indicating whether this transaction type is logged in the
   *     business transaction history tables.</li>
   * </ul>
   */
  private static class BTXDetailsMetaData {
    // None of these fields have defaults, they are must all be explicitly passed
    // as arguments to the constructor.  We do this so that nobody will forget to
    // explicitly assign these on a transaction-by-transaction basis.
    //
    private String _displayName;
    private Class _detailsSubclass;
    private boolean _isLogged;

    BTXDetailsMetaData(String displayName, Class detailsSubclass, boolean isLogged) {
      super();

      if (displayName == null || displayName.length() == 0) {
        throw new IllegalArgumentException("BTXDetailsMetaData: The display name must not be null or empty.");
      }

      if (detailsSubclass == null) {
        throw new IllegalArgumentException("BTXDetailsMetaData: The details subclass must not be null.");
      }

      _displayName = displayName;
      _detailsSubclass = detailsSubclass;
      _isLogged = isLogged;
    }

    public String getDisplayName() {
      return _displayName;
    }

    public Class getDetailsSubclass() {
      return _detailsSubclass;
    }

    public boolean isLogged() {
      return _isLogged;
    }
  }

  public BTXDetails() {
    super();
  }

  /**
   * Return the business transaction type code for the transaction that this
   * class represents.  Derived classes must override this method.
   *
   * @return the transaction type code.
   */
  public abstract String getBTXType();

  /**
   * Return a set of the object ids of the objects that are directly involved
   * in this transaction.  This set does not contain the ids of objects that
   * are considered to be indirectly involved in the transaction, and it does
   * not include the user id of the user who performed the transaction.
   * 
   * <p>For example, a transaction that scans a box of samples directly
   * involves the box object and each of the sample objects, and indirectly
   * involves the asm, asm form, case and donor objects for each sample.
   * 
   * <p>The BTXDetails class has a special BeanInfo object that hides this
   * method from reflection.  See {@link BTXDetailsBeanInfo} for details.
   *
   * @return the set of directly involved object ids.
   */
  public abstract Set getDirectlyInvolvedObjects();

  /**
   * Return an HTML string that defines how the transaction details are
   * presented in a transaction-history web page.  Derived classes must
   * override this method.
   * 
   * <p>This method is protected, the corresponding public method is
   * {@link #getDetailsAsHTML() getDetailsAsHTML}, which calls this method.
   * getDetailsAsHTML handles common tasks such as returning the
   * details in the case that the transaction represents a failed transaction
   * (it has a non-null exceptionText property).  For such a transaction, the
   * doGetDetailsAsHTML method will not be called.  This framework is intended
   * to make it easier to implement doGetDetailsAsHTML in derived classes, as
   * the code there may assume that the transaction succeeded and that the
   * transaction's data fields aren't malformed.
   * 
   * <p><b>Implementations of this method must only make use of fields that
   * are populated by the populateFromHistory method.</b>
   *
   * @return the HTML detail string.
   */
  protected abstract String doGetDetailsAsHTML();

  /**
   * Return an HTML string that defines how the transaction details are
   * presented in a transaction-history web page.
   * 
   * <p>This method calls {@link #doGetDetailsAsHTML() doGetDetailsAsHTML},
   * an abstract method that derived classes must implement.  getDetailsAsHTML
   * handles common tasks such as returning the details in the case that the
   * transaction represents a failed transaction (it has a non-null
   * exceptionText property).  For such a transaction, the doGetDetailsAsHTML
   * method will not be called.  This framework is intended to make it easier
   * to implement doGetDetailsAsHTML in derived classes, as the code there
   * may assume that the transaction succeeded and that the transaction's
   * data fields aren't malformed.
   * 
   * <p>The BTXDetails class has a special BeanInfo object that hides this
   * method from reflection.  See {@link BTXDetailsBeanInfo} for details.
   *
   * @return the HTML detail string.
   */
  public final String getDetailsAsHTML() {
    if (!isHasException()) {
      // This is not a failed transaction, call doGetDetailsAsHTML
      // to get the result.
      //
      return doGetDetailsAsHTML();
    }
    else {
      // This is a failed transaction, return a brief message describing
      // the failure.

      // At this point, this deliberately doesn't contain any exception
      // details, as the Java exception text or stack trace is really
      // only meaningful to an IT person investigating the problem,
      // and they can get it by looking it up in the ILTDS_BTX_HISTORY
      // table using the BTX_ID to identify the right row.

      StringBuffer buf = new StringBuffer(128);

      buf.append("<font color=\"red\">TRANSACTION FAILED</font>.  ");
      buf.append("A system error occurred while processing the transaction.");

      return buf.toString();
    }
  }

  /**
   * Return the date/time when the transaction began.
   * 
   * <p>This is an input field.
   *
   * @return the starting date/time.
   */
  public Timestamp getBeginTimestamp() {
    return _beginTimestamp;
  }

  /**
   * Return the indicator of what to do next once the transaction has been
   * performed.
   * 
   * <p>This is an output field.
   *
   * @return the object describing the next action to take.
   */
  public BTXActionForward getActionForward() {
    return _actionForward;
  }

  /**
   * Get the flag that indicates whether the transaction completed or not.
   * An incomplete transaction doesn't necessarily indicate that there was
   * a true application error.  It might mean there was user error detected
   * when validating input data, or that confirmation or additional input
   * is needed, or some other non-error condition.  Transactions that are
   * flagged as being incomplete aren't logged in the transaction history
   * tables.
   * 
   * <p>This is an output field.
   *
   * @return true if the transaction was completed.
   */
  public boolean isTransactionCompleted() {
    return _isTransactionCompleted;
  }

  /**
   * Return the meta-data information corresponding to the
   * specified business transaction type code.
   * 
   * @param btxType the business transaction type code
   * @return the business transaction meta-data object.
   */
  private static final BTXDetailsMetaData getBTXMetaData(String btxType) {
    if (btxType == null) {
      throw new IllegalArgumentException("BTXDetails.getBTXMetaData: type code must not be null.");
    }

    BTXDetailsMetaData metaData = (BTXDetailsMetaData) _transactionMetaData.get(btxType);

    if (metaData == null) {
      throw new RuntimeException(
        "BTXDetails.getBTXMetaData: unexpected type code '" + btxType + "'.");
    }

    return metaData;
  }

  /**
   * Return a user-friendly human-readable name corresponding to the
   * specified business transaction type code.
   * 
   * @param btxType the business transaction type code
   * @return the display string for the specified code
   */
  public static final String getBTXTypeDisplayName(String btxType) {
    return getBTXMetaData(btxType).getDisplayName();
  }

  /**
   * Return a user-friendly human-readable name corresponding to
   * this instance's business transaction type code.
   * 
   * @return the display string for the specified code
   */
  public final String getBTXTypeDisplayName() {
    return getBTXTypeDisplayName(getBTXType());
  }

  /**
   * Return the date/time at which the business transaction completed.
   * This is a required field.
   * 
   * <p>This is an output field.
   *
   * @return the ending date/time.
   */
  public Timestamp getEndTimestamp() {
    return _endTimestamp;
  }

  /**
   * Return the stack trace describing an exception that occurred while
   * performingthe transaction
   * 
   * <p>This is an output field.
   *
   * @return the exception stack trace.
   */
  public String getExceptionStackTrace() {
    return _exceptionStackTrace;
  }

  /**
   * Return the text describing an exception that occurred while performing
   * the transaction
   * 
   * <p>This is an output field.
   *
   * @return the exception text.
   */
  public String getExceptionText() {
    return _exceptionText;
  }

  /**
   * Return true if either the ExceptionText or ExceptionStackTrace
   * property is non-empty.
   * 
   * @return the exception indicator
   * @see #getExceptionText()
   * @see #getExceptionStackTrace()
   */
  public boolean isHasException() {
    return (
      (!ApiFunctions.isEmpty(getExceptionStackTrace()))
        || (!ApiFunctions.isEmpty(getExceptionText())));
  }

  /**
   * Return an instance of the BTXDetails subclass that respresents the
   * transaction described by the specified transaction history record.
   * A runtime exception is thrown if the specified record is null, if
   * there's no class registered to correspond to the record's transaction
   * type, or if the registered class isn't a subclass of BTXDetails.
   * 
   * <p>This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   * 
   * @param history the business transaction history record.
   * @param securityInfo the security credentials of the user who is
   *     currently logged in to BIGR.
   * @return the BTXDetails subclass instance.
   */
  public static final BTXDetails getInstance(BTXHistoryRecord history, SecurityInfo securityInfo) {

    if (history == null) {
      throw new IllegalArgumentException("BTXDetails.getInstance: history record must not be null.");
    }

    BTXDetails instance = getInstance(history.getBTXType());

    // Set the security info now, in case anything in
    // populateFromHistoryRecord depends on it.  In the situations when
    // this is called, we don't set the UserId and UserAccount properties
    // based on the user in the SecurityInfo, since those properties are
    // meant to always represent who performed the transaction, not to
    // represent who is currently logged in.  Those properties are set
    // based on information in the history record in this situation.
    //
    instance.setLoggedInUserSecurityInfo(securityInfo, false);

    instance.populateFromHistoryRecord(history);

    return instance;
  }

  /**
   * Return an instance of the BTXDetails subclass that respresents
   * transactions of the specified type.  The instance is constructed
   * using the type's zero-argument constructor.  A runtime exception
   * is thrown if the specified transaction type code is null, if
   * there's no class registered to correspond to the specified type code,
   * or if the registered class isn't a subclass of BTXDetails.
   * 
   * @param btxType the business transaction type code
   * @return the BTXDetails subclass instance.
   */
  public static final BTXDetails getInstance(String btxType) {
    Class clazz = getBTXMetaData(btxType).getDetailsSubclass();

    if (clazz == null) {
      throw new RuntimeException("BTXDetails.getInstance: unexpected type code '" + btxType + "'.");
    }

    BTXDetails instance = null;
    try {
      instance = (BTXDetails) clazz.newInstance();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return instance;
  }

  /**
   * Returns the transaction id of this transaction.  This gets filled in
   * by the {@link com.ardais.bigr.btx.framework.Btx#perform(BTXDetails, String) perform}
   * method, and will be null prior to that or if an exception occurs when
   * <code>perform</code> tries to get and assign a new transaction id.
   * 
   * <p>This is an output field.
   *
   * @return the transaction id.
   */
  public long getTransactionId() {
    return _transactionId;
  }

  /**
   * Return the transaction type to be performed on the information in this BTXDetails object.
   * This must be set to a valid transaction type prior to performing the transaction.
   * You can think of setting this property as binding the transaction data to a particular
   * runtime transaction to be performed.  Having this property allows the same BTXDetails
   * class to be passed to multiple transaction types.
   * 
   * <p>This is a required input parameter.
   * 
   * @return The transaction type.
   */
  public String getTransactionType() {
    return _transactionType;
  }

  /**
   * Return subactionType which belongs to a transaction, there could be multiple subactions under a 
   * particular trsaction
   * @return The subActionType
   */
  public String getSubActionType() {
    return _subActionType;
  }
  
  /**
   * Return the account id of the user performing the transaction.
   * 
   * <p>This is an input field.
   *
   * @return the user account id.
   */
  public String getUserAccount() {
    return _userAccount;
  }

  /**
   * Return the user id of the user performing the transaction.
   * This is a required field.  This is sometimes different from the
   * user represented by the SecurityInfo property, which always represents
   * the user that is currently logged in to BIGR.  See
   * {@link #getLoggedInUserSecurityInfo()} for details on when these
   * two users might not be the same.
   * 
   * <p>This is an input field.
   *
   * @return the user id.
   */
  public String getUserId() {
    return _userId;
  }

  /**
   * Return the user security info of the user that is currently logged into
   * BIGR.  <b>IMPORTANT</b>:  This is not necessarily the user who performed
   * the transaction.  While a transaction is being performed initially,
   * the user represented by SecurityInfo and the user represented by 
   * {@link #getUserId()} should always be the same.  However, in other
   * situations these two properties can represent different users.
   * For example, when a BTXDetails object is reconstituted from transaction
   * history, the SecurityInfo is set to the user who is currently
   * logged in (the user who is <i>viewing</i> the transaction history),
   * and the UserId property is set to the id of the user who performed
   * the transaction originally.  The bottom line is that the UserId
   * property always represents the user who performed the transaction,
   * and the SecurityInfo property always represents the users who is
   * currently logged in to BIGR.  Sometimes these are the same and sometimes
   * they are different.
   * 
   * <p>This is an input field.
   *
   * @return the user security information
   */
  public SecurityInfo getLoggedInUserSecurityInfo() {
    return _securityInfo;
  }

  /**
   * Return the {@link BtxActionErrors} collection for this transaction.
   * 
   * @return the action errors collection.
   */
  public BtxActionErrors getActionErrors() {
    return _actionErrors;
  }

  /**
   * Returns the actionMessages.
   * @return BtxActionMessages
   */
  public BtxActionMessages getActionMessages() {
    return _actionMessages;
  }

  /**
   * Add an error message to the set of errors for the specified property.
   *
   * @param property Property name (or BtxActionErrors.GLOBAL_ERROR)
   * @param error The error message to be added
   */
  public void addActionError(String property, BtxActionError error) {
    getActionErrors().add(property, error);
  }

  /**
   * Add an error message to the set of errors for the
   * BtxActionErrors.GLOBAL_ERROR property.
   *
   * @param property Property name (or BtxActionErrors.GLOBAL_ERROR)
   * @param error The error message to be added
   */
  public void addActionError(BtxActionError error) {
    addActionError(BtxActionErrors.GLOBAL_ERROR, error);
  }
  
  /**
   * Add action errors to the list of existing action errors.
   * @param errors
   */
  public void addActionErrors(BtxActionErrors errors) {
    Iterator properties = errors.properties();
    while (properties.hasNext()) {
      String property = (String) properties.next();
      Iterator errorsForProperty = errors.get(property);
      while (errorsForProperty.hasNext()) {
        addActionError((BtxActionError) errorsForProperty.next());
      }
    }
  }

  /**
   * Add a message to the set of messages for the
   * BtxActionMessages.GLOBAL_MESSAGES property.
   *
   * @param message The message to be added
   */
  public void addActionMessage(BtxActionMessage message) {
    getActionMessages().add(message);
  }
  
  /**
   * Add action messges to the list of existing action messages.
   * @param messages
   */
  public void addActionMessages(BtxActionMessages messages) {
    getActionMessages().addAll(messages);
  }

  /**
   * Return a flag indicating whether this type of business transaction is
   * logged in the transaction history tables.
   * 
   * @return true when the transaction type is logged.
   */
  public final boolean isLogged() {
    return getBTXMetaData(getBTXType()).isLogged();
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * 
   * <p>This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @return the history record object.
   */
  public final BTXHistoryRecord describeAsHistoryRecord() {
    BTXHistoryRecord history = new BTXHistoryRecord();
    describeIntoHistoryRecord(history);
    return history;
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * 
   * <p>Implementations of this method should take care to call the version
   * on the superclass to ensure that fields defined on the superclass are
   * also copied into the history record.
   * 
   * <p>This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set
   *    to the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    history.clear();

    history.setTransactionId(getTransactionId());
    history.setBTXType(getBTXType());
    history.setTransactionType(getTransactionType());
    history.setUserId(getUserId());
    history.setBeginTimestamp(getBeginTimestamp());
    history.setEndTimestamp(getEndTimestamp());
    history.setExceptionText(getExceptionText());
    history.setExceptionStackTrace(getExceptionStackTrace());
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set
   * <b>all</b> fields on this object, as if it had been newly created
   * immediately before this method was called.  A runtime exception is
   * thrown if the transaction type represented by the history record
   * doesn't match the transaction type represented by this object.
   * Implementations of this method should take care to call the version
   * on the superclass to ensure that fields defined on the superclass are
   * also populated from the history record.
   * 
   * <p>This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    if (history == null) {
      throw new IllegalArgumentException("BTXDetails.populateFromHistoryRecord: history record must not be null.");
    }

    if (!getBTXType().equals(history.getBTXType())) {
      throw new IllegalArgumentException(
        "BTXDetails.populateFromHistoryRecord: expected a history record of type "
          + getBTXType()
          + " but got a record of type "
          + history.getBTXType()
          + ".");
    }

    setTransactionId(history.getTransactionId());
    setTransactionType(history.getTransactionType());
    setUserId(history.getUserId());
    setBeginTimestamp(history.getBeginTimestamp());
    setEndTimestamp(history.getEndTimestamp());
    setExceptionText(history.getExceptionText());
    setExceptionStackTrace(history.getExceptionStackTrace());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    {
      // We don't set the SecurityInfo property here because it
      // represents the user who is currently logged in to BIGR, and
      // so does not depend on what is stored in the history record.

      setUserAccount(null);
      setActionForward(null);
      setTransactionCompleted(true);

      // Avoid extra work/memory-use by clearing the action errors
      // instead of allocating a new object.
      //
      _actionErrors.clear();
    }
  }

  /**
   * Set the date/time when the transaction began.
   * 
   * <p>This is an input field.
   *
   * @param newTimestamp the starting date/time.
   */
  public void setBeginTimestamp(Timestamp newTimestamp) {
    _beginTimestamp = newTimestamp;
  }

  /**
   * Set the indicator of what to do next once the transaction has been
   * performed.
   * 
   * <p>This is an output field.
   *
   * @param newActionForward the object describing the next action to take.
   */
  public void setActionForward(BTXActionForward newActionForward) {
    _actionForward = newActionForward;
  }

  /**
   * This is a convenience method to set the ActionForward property to
   * be a retry action.  It does the following:
   * <ul>
   * <li>Sets the BTXActionForward property to a retry action forward.</li>
   * <li>Sets the TransactionCompleted property to false.</li>
   * <li>Adds an ActionError with the specified retryMessageKey.</li>
   * </ul>
   *
   * @param retryMessageKey the Struts message key for the retry message to
   *     display
   * @return this BTXDetails object, to make coding a little easier in
   *    typical caller scenarios.
   */
  public BTXDetails setActionForwardRetry(String retryMessageKey) {
    if (retryMessageKey == null) {
      throw new IllegalArgumentException(
        "BTXDetails.setRetryActionForward: " + " retryMessageKey must not be null.");
    }

    return setActionForwardRetry(new BtxActionError(retryMessageKey));
  }

  /**
   * This is a convenience method to set the ActionForward property to
   * be a retry action.  It does the following:
   * <ul>
   * <li>Sets the BTXActionForward property to a retry action forward.</li>
   * <li>Sets the TransactionCompleted property to false.</li>
   * <li>Adds the specified BtxActionError to the errors collection.</li>
   * </ul>
   *
   * @param actionError the BtxActionError describing the retry message to
   *     display
   * @return this BTXDetails object, to make coding a little easier in
   *    typical caller scenarios.
   */
  public BTXDetails setActionForwardRetry(BtxActionError actionError) {
    if (actionError == null) {
      throw new IllegalArgumentException(
        "BTXDetails.setRetryActionForward: " + " actionError must not be null.");
    }

    setActionForwardRetry();

    addActionError(actionError);

    return this;
  }

  /**
   * This is a convenience method to set the ActionForward property to
   * be a retry action.  It does the following:
   * <ul>
   * <li>Sets the BTXActionForward property to a retry action forward.</li>
   * <li>Sets the TransactionCompleted property to false.</li>
   * </ul>
   *
   * @return this BTXDetails object, to make coding a little easier in
   *    typical caller scenarios.
   */
  public BTXDetails setActionForwardRetry() {
    setTransactionCompleted(false);

    setActionForward(new BtxActionForwardRetry());

    return this;
  }

  /**
   * This is a convenience method to set the ActionForward property to
   * the specified target name and set the TransactionCompleted property
   * to true at the same time.
   *
   * @param targetName the name of the Struts action forward mapping
   *     to forward control to.
   * @return this BTXDetails object, to make coding a little easier in
   *    typical caller scenarios.
   */
  public BTXDetails setActionForwardTXCompleted(String targetName) {
    BTXActionForward forward = new BTXActionForward(targetName);

    setTransactionCompleted(true);

    setActionForward(forward);

    return this;
  }

  /**
   * This is a convenience method to set the ActionForward property to
   * the specified target name and set the TransactionCompleted property
   * to false at the same time.  This would be used, for example, when
   * a transaction determines that it needs additional user confirmation or
   * input before proceeding, or that the user's input is invalid.
   *
   * @param targetName the name of the Struts action forward mapping
   *     to forward control to.
   * @return this BTXDetails object, to make coding a little easier in
   *    typical caller scenarios.
   */
  public BTXDetails setActionForwardTXIncomplete(String targetName) {
    BTXActionForward forward = new BTXActionForward(targetName);

    setTransactionCompleted(false);

    setActionForward(forward);

    return this;
  }

  /**
   * Set the flag that indicates whether the transaction completed or not.
   * An incomplete transaction doesn't necessarily indicate that there was
   * a true application error.  It might mean there was user error detected
   * when validating input data, or that confirmation or additional input
   * is needed, or some other non-error condition.  Transactions that are
   * flagged as being incomplete aren't logged in the transaction history
   * tables.
   * 
   * <p>This is an output field.
   *
   * @param completionFlag true if the transaction completed
   */
  public void setTransactionCompleted(boolean completionFlag) {
    _isTransactionCompleted = completionFlag;
  }

  /**
   * Set the date/time at which the business transaction completed.
   * This is a required field.
   * 
   * <p>This is an output field.
   * 
   * <p>NOTE:  This method is intended to be used only by the
   * <code>perform</code> method.
   *
   * @param newTimestamp java.sql.Timestamp
   */
  public void setEndTimestamp(Timestamp newTimestamp) {
    _endTimestamp = newTimestamp;
  }

  /**
   * This is a convenience method that takes an exception as an argument
   * and sets the exceptionText and exceptionStackTrace properties.
   * Setting an exception does NOT automatically set the transactionCompleted
   * property to false.
   * 
   * <p>This is an output field.
   * 
   * <p>NOTE:  This method is intended to be used only by the
   * <code>perform</code> method.
   *
   * @param theException the exception
   */
  public void setException(Exception theException) {

    setExceptionText(theException.toString());

    {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);

      theException.printStackTrace(pw);

      pw.flush();
      pw.close();

      setExceptionStackTrace(sw.toString());
    }
  }

  /**
   * Set the stack trace describing an exception that occurred while performing
   * the transaction
   * 
   * <p>This is an output field.
   * 
   * <p>NOTE:  This method is intended to be used only by the
   * <code>perform</code> method.
   *
   * @param newExceptionStackTrace the exception stack trace.
   */
  public void setExceptionStackTrace(String newExceptionStackTrace) {
    if (ApiFunctions.isEmpty(newExceptionStackTrace)) {
      _exceptionStackTrace = null;
    }
    else {
      _exceptionStackTrace = newExceptionStackTrace;
    }
  }

  /**
   * Set the text describing an exception that occurred while performing
   * the transaction
   * 
   * <p>This is an output field.
   * 
   * <p>NOTE:  This method is intended to be used only by the
   * <code>perform</code> method.
   *
   * @param newExceptionText the exception text.
   */
  public void setExceptionText(String newExceptionText) {
    if (ApiFunctions.isEmpty(newExceptionText)) {
      _exceptionText = null;
    }
    else {
      _exceptionText = newExceptionText;
    }
  }

  /**
   * Sets the transaction id of this transaction.  This gets filled in by the
   * {@link com.ardais.bigr.btx.framework.Btx#perform(BTXDetails, String) perform}
   * method, and will be null prior to that or if an exception occurs when
   * <code>perform</code> tries to get and assign a new transaction id.
   * 
   * <p>This is an output field.
   * 
   * <p>NOTE:  This method is intended to be used only by the
   * <code>perform</code> method.
   *
   * @return the transaction id.
   */
  public void setTransactionId(long newTransactionId) {
    _transactionId = newTransactionId;
  }

  /**
   * Set the transaction type to be performed on the information in this BTXDetails object.
   * This must be set to a valid transaction type prior to performing the transaction.
   * You can think of setting this property as binding the transaction data to a particular
   * runtime transaction to be performed.  Having this property allows the same BTXDetails
   * class to be passed to multiple transaction types.
   * 
   * <p>This is a required input parameter.
   * 
   * @param transactionType The transaction type.
   */
  public void setTransactionType(String transactionType) {
    _transactionType = transactionType;
  }

  
  public void setSubActionType(String action) {
    _subActionType = action;
  }
  
  /**
   * Set the account id of the user performing the transaction.
   * 
   * <p>This is an input field.
   *
   * @param newUserAccount the user account id.
   */
  public void setUserAccount(String newUserAccount) {
    _userAccount = newUserAccount;
  }

  /**
   * Set the user id of the user performing the transaction.
   * This is a required field.  This is sometimes different from the
   * user represented by the SecurityInfo property, which always represents
   * the user that is currently logged in to BIGR.  See
   * {@link #getLoggedInUserSecurityInfo()} for details on when these
   * two users might not be the same.
   * 
   * <p>This is an input field.
   *
   * @param newUserId the user id.
   */
  public void setUserId(String newUserId) {
    _userId = newUserId;
  }

  /**
   * Set the user security info of the user that is currently logged into
   * BIGR.  IMPORTANT:  This is not necessarily the user who performed
   * the transaction.  While a transaction is being performed initially,
   * the user represented by SecurityInfo and the user represented by
   * {@link #getUserId()} should always be the same.  However, in other
   * situations these two properties can represent different users.
   * For example, when a BTXDetails object is reconstituted from transaction
   * history, the SecurityInfo is set to the user who is currently
   * logged in (the user who is <i>viewing</i> the transaction history),
   * and the UserId property is set to the id of the user who performed
   * the transaction originally.  The bottom line is that the UserId
   * property always represents the user who performed the transaction,
   * and the SecurityInfo property always represents the users who is
   * currently logged in to BIGR.  Sometimes these are the same and sometimes
   * they are different.
   * 
   * <p>This is an input field.
   *
   * @param  securityInfo  the {@link com.ardais.bigr.security.SecurityInfo}
   * @param alsoSetUserIdAndUserAccount if this is true, then the
   *     {@link #setUserId(String) userId} and
   *     {@link #setUserAccount(String) userAccount} parameters will also
   *     be set based on information in the supplied SecurityInfo.
   */
  public void setLoggedInUserSecurityInfo(
    SecurityInfo securityInfo,
    boolean alsoSetUserIdAndUserAccount) {

    _securityInfo = securityInfo;

    if (alsoSetUserIdAndUserAccount) {
      if (securityInfo == null) {
        setUserId(null);
        setUserAccount(null);
      }
      else {
        setUserId(securityInfo.getUsername());
        setUserAccount(securityInfo.getAccount());
      }
    }
  }

  /**
   * This is a conveince method that calls
   * {@link #setLoggedInUserSecurityInfo(SecurityInfo, boolean)} with its second
   * parameter set to <code>true</code> (indicating that the UserId and UserAccount
   * properties should also be set based on the supplied SecurityInfo).
   *
   * @param  securityInfo  the {@link com.ardais.bigr.security.SecurityInfo}
   */
  public void setLoggedInUserSecurityInfo(SecurityInfo securityInfo) {

    setLoggedInUserSecurityInfo(securityInfo, true);
  }
  
  /**
   * Populates this <code>BTXDetails</code> object from the data in the 
   * <code>BtxHistoryAttributes</code> object.
   * 
   * @param  attributes  a <code>BtxHistoryAttributes</code>.
   */
  protected void populateFromHistoryObject(BtxHistoryAttributes attributes) {
    if (attributes == null) {
      return;
    }
    else {
      try {
        Map map = (Map) attributes.asMap();
        Iterator names = map.keySet().iterator();
        PropertyUtilsBean bean = new PropertyUtilsBean();
        while (names.hasNext()) {
          String name = (String) names.next();
          if (bean.isWriteable(this, name)) {
            Class[] params = bean.getPropertyDescriptor(this, name).getWriteMethod().getParameterTypes();
            if ("java.lang.String".equalsIgnoreCase(((Class)params[0]).getName())) {
              bean.setProperty(this, name, map.get(name));
            }
            else if ("java.lang.Integer".equalsIgnoreCase(((Class)params[0]).getName())) {
              bean.setProperty(this, name, new Integer((String)map.get(name)));
            }
            else {
              throw new ApiException("Unexpected class " + ((Class)params[0]).getName() + " encountered.");
            }
          }
        }
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
    }
  }

  /**
   * @return
   */
  public boolean isValidationOnly() {
    return _validationOnly;
  }

  /**
   * @param b
   */
  public void setValidationOnly(boolean b) {
    _validationOnly = b;
  }

}
