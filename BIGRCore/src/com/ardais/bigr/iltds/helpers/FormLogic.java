package com.ardais.bigr.iltds.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.databeans.LabelValueBean;
import com.ardais.bigr.util.ArtsConstants;

/**
 * Insert the type's description here.
 * Creation date: (1/23/2001 1:51:34 PM)
 * @author: Jake Thompson
 */
public class FormLogic {

  public final static int NUM_SAMPLE_PA = 3;
  public final static int NUM_SAMPLE_FR = 18;
  public final static String SMPL_PA_FORMAT = "PARAFFIN";
  public final static String SMPL_FR_FORMAT = "FROZEN";

  public final static int NUM_ASMENTRY = 3;
  public final static int ASM_NUM_LENGTH = 9;

  public final static String PRJ_ADDED = "PRJADDED";
  public final static String PRJ_REMOVED = "PRJREMOVED";

  // Formats of finished products
  public final static String FORMAT_DNA = "DNA";
  public final static String FORMAT_FROZEN = "FROZEN";
  public final static String FORMAT_PARAFFIN = "PARAFFIN";
  public final static String FORMAT_RNA = "RNA";
  public final static String FORMAT_SLIDES = "SLIDES";
  public final static String FORMAT_TMA = "TMA";

  //PTS KEYS
  public final static String SHOPPINGCART_KEY = "shoppingcart_key";

  //ASM numbers
  public final static int ASM_RANGE_ABC_START = 1;
  public final static int ASM_RANGE_ABC_END = 85000000;
  public final static int ASM_RANGE_DEF_START = 85000001;
  public final static int ASM_RANGE_DEF_END = 95000000;
  public final static int ASM_RANGE_GHI_START = 95000001;
  public final static int ASM_RANGE_GHI_END = 99000000;
  public final static int ASM_RANGE_JKL_START = 99000001;
  public final static int ASM_RANGE_JKL_END = 103000000;
  public final static int ASM_RANGE_MNO_START = 103000001;
  public final static int ASM_RANGE_MNO_END = 107000000;
  public final static int ASM_RANGE_PQR_START = 107000001;
  public final static int ASM_RANGE_PQR_END = 111000000;
  public final static int ASM_RANGE_STU_START = 111000001;
  public final static int ASM_RANGE_STU_END = 115000000;
  public final static int ASM_RANGE_VWX_START = 115000001;
  public final static int ASM_RANGE_VWX_END = 119000000;
  public final static int ASM_RANGE_PASUB_START = 119000001;
  public final static int ASM_RANGE_PASUB_END = 125000000;
  public final static int ASM_RANGE_LOWER_MIN = 1;
  public final static int ASM_RANGE_LOWER_MAX = 125000000;
  public final static int ASM_RANGE_UPPER_MIN = 1;
  public final static int ASM_RANGE_UPPER_MAX = 125000000;
  //end ASM numbers

  //Pseudo-status codes.  Though these concepts are no longer represented
  //by actual codes in the status table, these values are useful in some
  //code where we need a status-like indicator to represent these states.
  //
  public final static String STATE_SAMPLE_DISCORDANT = "SAMPLE_DISCORDANT";
  public final static String STATE_SAMPLE_PULLED = "SAMPLE_PULLED";
  public final static String STATE_SAMPLE_IN_REPOSITORY = "SAMPLE_IN_REPOSITORY";
  public final static String STATE_SAMPLE_BMS = "SAMPLE_BMS";

  //Sample Status Codes
  public final static String SMPL_ADDTOCART = "ADDTOCART";
  public final static String SMPL_ASMPRESENT = "ASMPRESENT";
  public final static String SMPL_TRANSFER = "TRANSFER";
  public final static String SMPL_QCAWAITING = "QCAWAITING";
  public final static String SMPL_QCINPROCESS = "QCINPROCESS";
  public final static String SMPL_QCVERIFIED = "QCVERIFIED";
  public final static String SMPL_ESSOLD = "ESSOLD";
  public final static String SMPL_ESSHIPPED = "ESSHIPPED";
  public final static String SMPL_RNDREQUEST = "RNDREQUEST";
  public final static String SMPL_ARCOCASEPULL = "ARCOCASEPULL";
  public final static String SMPL_ARCOCONSREV = "ARCOCONSREV";
  public final static String SMPL_ARCOOTHER = "ARCOOTHER";
  public final static String SMPL_ARMVTOPATH = "ARMVTOPATH";
  public final static String SMPL_COCONSUMED = "COCONSUMED";
  public final static String SMPL_CORND = "CORND";
  public final static String SMPL_MICOCASEPULL = "MICOCASEPULL";
  public final static String SMPL_MICOCONSREV = "MICOCONSREV";
  public final static String SMPL_MICOOTHER = "MICOOTHER";
  public final static String SMPL_GENRELEASED = "GENRELEASED";
  public final static String SMPL_GENRECALLED = "GENRECALLED";
  public final static String SMPL_REQUESTED = "REQUESTED";
  public final static String SMPL_CHECKEDOUT = "CHECKEDOUT";
  public final static String SMPL_BOXSCAN = "BOXSCAN";
  //Sample Status Codes

  //Manifest Status Codes
  public final static String MNFT_MFCREATED = "MFCREATED";
  public final static String MNFT_MFPACKAGED = "MFPACKAGED";
  public final static String MNFT_MFSHIPPED = "MFSHIPPED";
  public final static String MNFT_MFRECEIVED = "MFRECEIVED";
  public final static String MNFT_MFVERIFIED = "MFVERIFIED";
  //Manifest Status Codes

  //Allocation codes
  public final static String ALL_RESTRICTED_IND = "R";
  public final static String ALL_UNRESTRICTED_IND = "U";
  public final static String ALL_UNALLOCATED_IND = "X";
  //Allocation codes

  //Box Statuses
  public final static java.lang.String BX_CHECKEDIN = "CHECKEDIN";
  public final static java.lang.String BX_CHECKEDOUT = "CHECKEDOUT";
  public final static java.lang.String BX_SCANRECEIVED = "SCANRECEIVED";

  //Box Statuses

  //Storage Type codes
  public final static String STG_TYPE_FROZEN = "FREEZER";
  public final static String STG_TYPE_PARAFFIN = "CONTAINER";
  //Storage Type codes

  //Database Object Names referred to in the code
  public final static String ILTDS_BXBARCODE_SEQ = "ILTDS_BXBARCODE_SEQ";
  //Database Object Names referred to in the code

  //Prefixes for sequences for the Dummy Box Barcode
  public final static String ILTDS_DU_BCD = "DU";
  //Prefixes for sequences for the Dummy Box Barcode

  //Number of slots in a row of a Paraffin Drawer
  public final static int PA_SLOT = 30;

  //Specimen Type
  //NOTE: if you add any appearance codes, make sure you put them into the
  //HashMap of appearance codes in the static initialization block below!!!
  public final static String SPEC_DISEASED = "Gross Diseased";
  public final static String SPEC_GROSS_NORMAL = "Gross Normal";
  public final static String SPEC_MIXED = "Gross Mixed";
  public final static String SPEC_UNKNOWN = "Gross Unknown";
  public final static String SPEC_DISEASED_IND = "D";
  public final static String SPEC_GROSS_NORMAL_IND = "N";
  public final static String SPEC_MIXED_IND = "M";
  public final static String SPEC_UNKNOWN_IND = "U";

  public final static String SPEC_TUMOR = "Tumor";
  public final static String SPEC_TUMOR_IND = "T";

  public final static String SPEC_LESION = "Lesion";
  public final static String SPEC_LESION_IND = "L";

  public final static String SPEC_NORMAL = "Normal";
  public final static String SPEC_NORMAL_IND = "R";

  public final static String SPEC_OTHER = "Other";
  public final static String SPEC_OTHER_IND = "O";

  public final static String SPEC_NECROTIC = "Necrotic";
  public final static String SPEC_NECROTIC_IND = "C";

  public final static String SPEC_ACELLULAR_NECROTIC = "Acellular Necrotic";
  public final static String SPEC_ACELLULAR_NECROTIC_IND = "A";

  public final static String SPEC_TLN0 = "TLN0";
  public final static String SPEC_TLN0_IND = "Z";

  // ID Prefixes
  public final static String PREFIX_LINEITEM = "LI";
  public final static String PREFIX_PROJECT = "PR";
  public final static String PREFIX_WORKORDER = "WR";
  public final static String PREFIX_URL = "URL";

  public final static int LENGTH_WAYBILLID = 25;
  public final static int LENGTH_URL = 11;

  public final static String ARDAIS_STAFF_PREFIX = "ST";

  //Email Addresses
  public final static String CATEGORY = "EMAIL";
  public final static String CR_MESSAGE =
    "The following new Diagnosis Description was reported during Case Release:";
  public final static String PP_MESSAGE =
    "The following Sample has been pulled from inventory using Pathology Release:";
  public final static String ILTDS_ARDAIS_STAFF_SEQ = "ILTDS_ARDAIS_STAFF_SEQ";
  public final static String ILTDS_PATH_PICKLIST_SEQ = "ILTDS_PATH_PICKLIST_SEQ";
  public final static String ILTDS_SAMPLE_STATUS_PK_SEQ = "ILTDS_SAMPLE_STATUS_PK_SEQ";
  public final static String PATHOLOGY_PICKLIST_PREFIX = "PP";
  public final static String SUBJECT = "New Diagnosis Description Reported";
  public final static String PROCEDURE_SUBJECT = "New Procedure Description Reported";
  public final static String PATH_PULL_SUBJECT_ORDER = "Sample Pulled From Order";
  public final static String PATH_PULL_SUBJECT_SHOP = "Sample Pulled From Shopping Cart";

  //Box Checkout Reasons
  public final static String BOX_ARCONSREV = "ARCONSREV";
  public final static String BOX_ARCASEPULL = "ARCASEPULL";
  public final static String BOX_ARCONSUME = "ARCONSUME";
  public final static String BOX_ARMVTORND = "ARMVTORND";
  public final static String BOX_AROTHER = "AROTHER";
  public final static String BOX_LICENSED = "ARLICENSED";
  public final static String BOX_DESTROYED = "ARDESTROYED";
  public final static String BOX_MVTOPATH = "MVTOPATH";
  public final static String BOX_MICONSREV = "MICONSREV";
  public final static String BOX_MICASEPULL = "MICASEPULL";
  public final static String BOX_MIOTHER = "MIOTHER";
  public final static String BOX_EMPTY = "BXEMPTY";
  public final static String BOX_FULFILLREQUEST = "FULFILLREQUEST";

  // Codes for "other" dx, px, tissues and pdc_lookup values.
  public final static String OTHER_DX = "CA00038D^^";
  public final static String OTHER_PX = "CA00004P";
  public final static String OTHER_TISSUE = "91723000";
  public final static String OTHER_DISTANT_METASTASIS = "CA00431C";
  public final static String OTHER_HISTOLOGICAL_NUCLEAR_GRADE = "CA00356C";
  public final static String OTHER_HISTOLOGICAL_TYPE = "CA00447C";
  public final static String OTHER_LYMPH_NODE_STAGE_DESC = "CA00445C";
  public final static String OTHER_STAGE_GROUPING = "CA00418C";
  public final static String OTHER_TUMOR_STAGE_DESC = "CA00434C";
  public final static String OTHER_TUMOR_STAGE_TYPE = "CA00446C";
  public final static String OTHER_HISTO_REEMBED_REASON = "CA00934C";
  public final static String OTHER_PARAFFIN_FEATURE = "CA01032C";
  public final static String OTHER_IHC_ANTIBODY_TYPE = "CA01403C";
  public final static String OTHER_IHC_SPECIES = "CA01409C";
  public final static String OTHER_IHC_FIXATIVE = "CA01415C";
  public final static String OTHER_IHC_ENZYME = "CA01419C";
  public final static String OTHER_IHC_ANTIGEN_RETRIEVAL_METHOD = "CA01422C";
  public final static String OTHER_IHC_DETECTION_METHOD = "CA01427C";
  public final static String OTHER_IHC_CHROMOGEN = "CA01430C";
  public final static String OTHER_IHC_PROTEIN_BLOCKER = "CA01436C";
  public final static String OTHER_IHC_CELLULAR_LOCATION = "CA01453C";
  public final static String OTHER_IHC_NATURE_OF_STAINING = "CA01456C";
  public final static String OTHER_IHC_STAIN_INTENSITY = "CA01448C";
  public final static String OTHER_IHC_STAIN_SPECIFICITY = "CA01440C";
  public final static String OTHER_IHC_CONCENTRATION_UNIT = "CA02010C";

  // Codes for special "Not Reported" values.
  public final static String NOT_REPORTED_DISTANT_METASTASIS = "CA00430C";
  public final static String NOT_REPORTED_LYMPH_NODE_STAGE_DESC = "CA00346C";
  public final static String NOT_REPORTED_TUMOR_STAGE_DESC = "CA00592C";

  // Code to indicate metastasis
  public final static String METASTATIC_CODE = "^8707003";

  // Categories in the ARD_LOOKUP_ALL table.
  public final static String ARD_LOOKUP_PROJECT_STATUS = "PROJECT_STATUS";
  public final static String ARD_LOOKUP_WO_STATUS_DIV = "WO_STATUS_DIV";
  public final static String ARD_LOOKUP_WO_STATUS_INV = "WO_STATUS_INV";
  public final static String ARD_LOOKUP_WO_STATUS_POA = "WO_STATUS_POA";
  public final static String ARD_LOOKUP_WO_STATUS_PROJ_DEF = "WO_STATUS_PROJ_DEF";
  public final static String ARD_LOOKUP_WO_STATUS_PROJ_REV = "WO_STATUS_PROJ_REV";
  public final static String ARD_LOOKUP_WO_STATUS_PV = "WO_STATUS_PV";
  public final static String ARD_LOOKUP_WO_STATUS_RNA = "WO_STATUS_RNA";
  public final static String ARD_LOOKUP_WO_STATUS_SHIP = "WO_STATUS_SHIP";
  public final static String ARD_LOOKUP_WO_STATUS_SLIDE = "WO_STATUS_SLIDE";
  public final static String ARD_LOOKUP_WO_STATUS_TMA = "WO_STATUS_TMA";
  public final static String ARD_LOOKUP_WO_TYPE = "WO_TYPE";

  // Work Order types
  public final static String WOTYPE_DIV = "WOTYPE_DIV"; // Division
  public final static String WOTYPE_INV = "WOTYPE_INV"; // Invoice
  public final static String WOTYPE_PD = "WOTYPE_PD"; // Project Definition
  public final static String WOTYPE_POA = "WOTYPE_POA"; // PO Assembly
  public final static String WOTYPE_PR = "WOTYPE_PR"; // Project Review
  public final static String WOTYPE_PV = "WOTYPE_PV";
  // Pathology Verification
  public final static String WOTYPE_RNA = "WOTYPE_RNA"; // RNA Construction
  public final static String WOTYPE_SC = "WOTYPE_SC"; // Slide Creation
  public final static String WOTYPE_SHIP = "WOTYPE_SHIP"; // Shipment
  public final static String WOTYPE_TMA = "WOTYPE_TMA"; // TMA Construction

  // Sequences
  public final static String SEQ_PTS_LINEITEMID = "PTS_LINEITEMID_SEQ";
  public final static String SEQ_PTS_PROJECTID = "PTS_PROJECTID_SEQ";
  public final static String SEQ_PTS_WORKORDERID = "PTS_WORKORDERID_SEQ";

  // Values to indicate whether a sample can be divided or not
  public final static String DIVIDED_NO = "N";
  public final static String DIVIDED_UNKNOWN = "U";
  public final static String DIVIDED_YES = "Y";

  // Constants to identify microscopic appearance rules
  public final static int MICROSCOPIC_APPEARANCE_OK = 0;
  public final static int MICROSCOPIC_APPEARANCE_RULE_1 = 1;
  public final static int MICROSCOPIC_APPEARANCE_RULE_2 = 2;
  public final static int MICROSCOPIC_APPEARANCE_RULE_3 = 3;

  //Constants used for yes/no values in the database
  public final static String DB_YES = "Y";
  public final static String DB_YES_TEXT = "Yes";
  public final static String DB_NO = "N";
  public final static String DB_NO_TEXT = "No";

  public final static String LOCAL = "Y";
  public final static String ARDAIS_LOCATION = "LEX";

  //Request Item sorting values
  public static final String ITEM_SORT_ORDER_ADDED = "OrderAdded";
  public static final String ITEM_SORT_ORDER_ADDED_TEXT = "Order added to request";
  public static final String ITEM_SORT_CASE_ID = "CaseId";
  public static final String ITEM_SORT_CASE_ID_TEXT = "Case Id";
  public static final String ITEM_SORT_SAMPLE_ID = "SampleId";
  public static final String ITEM_SORT_SAMPLE_ID_TEXT = "Sample Id";
  public static final String ITEM_SORT_LOCATION = "Location";
  public static final String ITEM_SORT_LOCATION_TEXT = "Location (Room/Unit/Drawer/Slot/Cell)";

  public static final String ADDITIONAL_QUESTIONS_GRP_DBS = "Duke Breast SPORE";

  //put all appearance codes into a hashmap, and raise an error if there is any overlap.
  //The lookupAppearance method below handles both gross appearance and microscopic
  //appearance, so we cannot have any overlap between the codes
  private static HashMap _appearanceCodes = new HashMap();

  static {
    String errorMsg =
      "Error - appearance code overlap in com.ardais.bigr.iltds.helpers.FormLogic static initialization block";
    Object existingCode = _appearanceCodes.put(SPEC_DISEASED_IND, SPEC_DISEASED);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_MIXED_IND, SPEC_MIXED);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_GROSS_NORMAL_IND, SPEC_GROSS_NORMAL);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_UNKNOWN_IND, SPEC_UNKNOWN);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_TUMOR_IND, SPEC_TUMOR);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_LESION_IND, SPEC_LESION);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_NORMAL_IND, SPEC_NORMAL);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_OTHER_IND, SPEC_OTHER);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_NECROTIC_IND, SPEC_NECROTIC);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_ACELLULAR_NECROTIC_IND, SPEC_ACELLULAR_NECROTIC);
    if (existingCode != null)
      throw new ApiException(errorMsg);
    existingCode = _appearanceCodes.put(SPEC_TLN0_IND, SPEC_TLN0);
    if (existingCode != null)
      throw new ApiException(errorMsg);
  }

  /**
   * FormLogic constructor comment.
   */
  public FormLogic() {
    super();
  }

  /**
   * Insert the method's description here.
   * Creation date: (10/29/2001 10:27:28 AM)
   * @return boolean
   * @param asm1 java.lang.String
   * @param asm2 java.lang.String
   */
  public static boolean asmInSameRange(String asm1, String asm2) throws ApiException {
    asm1 = asm1.substring(3);
    asm2 = asm2.substring(3);
    int asmInt1 = (new Integer(asm1)).intValue();
    int asmInt2 = (new Integer(asm2)).intValue();
    int higher;
    int lower;

    if (asmInt1 <= asmInt2) {
      higher = asmInt2;
      lower = asmInt1;
    }
    else {
      higher = asmInt1;
      lower = asmInt2;
    }

    if (lower >= ASM_RANGE_ABC_START && lower <= ASM_RANGE_ABC_END) {
      if (higher >= ASM_RANGE_ABC_START && higher <= ASM_RANGE_ABC_END) {
        return true;
      }
    }
    else if (lower >= ASM_RANGE_DEF_START && lower <= ASM_RANGE_DEF_END) {
      if (higher >= ASM_RANGE_DEF_START && higher <= ASM_RANGE_DEF_END) {
        return true;
      }
    }
    else if (lower >= ASM_RANGE_GHI_START && lower <= ASM_RANGE_GHI_END) {
      if (higher >= ASM_RANGE_GHI_START && higher <= ASM_RANGE_GHI_END) {
        return true;
      }
    }
    else if (lower >= ASM_RANGE_JKL_START && lower <= ASM_RANGE_JKL_END) {
      if (higher >= ASM_RANGE_JKL_START && higher <= ASM_RANGE_JKL_END) {
        return true;
      }
    }
    else if (lower >= ASM_RANGE_MNO_START && lower <= ASM_RANGE_MNO_END) {
      if (higher >= ASM_RANGE_MNO_START && higher <= ASM_RANGE_MNO_END) {
        return true;
      }
    }
    else if (lower >= ASM_RANGE_PQR_START && lower <= ASM_RANGE_PQR_END) {
      if (higher >= ASM_RANGE_PQR_START && higher <= ASM_RANGE_PQR_END) {
        return true;
      }
    }
    else if (lower >= ASM_RANGE_STU_START && lower <= ASM_RANGE_STU_END) {
      if (higher >= ASM_RANGE_STU_START && higher <= ASM_RANGE_STU_END) {
        return true;
      }
    }
    else if (lower >= ASM_RANGE_VWX_START && lower <= ASM_RANGE_VWX_END) {
      if (higher >= ASM_RANGE_VWX_START && higher <= ASM_RANGE_VWX_END) {
        return true;
      }
    }

    return false;
  }
  /**
   * Convert the supplied string to an HTML string suitable for displaying
   * in a barcode font, including checksum, start and stop characters.
   *
   * @param param the string to encode
   * @return the HTML barcode string
   */
  public static String checkSum(String strBarcode) {

    final char c128Start = (char) 202;
    final char c128Stop = (char) 138;

    char c128CheckDigit = 'w';
    int CheckDigitValue = 0;

    strBarcode = strBarcode.trim();

    int weightedTotal = 104;
    int currentValue = 0;

    for (int i = 1; i <= strBarcode.length(); i++) {
      char currentChar = strBarcode.charAt(i - 1);
      if (((int) currentChar) < 135)
        currentValue = ((int) currentChar) - 32;
      if (((int) currentChar) > 134)
        currentValue = ((int) currentChar) - 100;

      currentValue = currentValue * i;

      weightedTotal = weightedTotal + currentValue;
    }

    CheckDigitValue = weightedTotal % 103;

    if ((CheckDigitValue < 95) && (CheckDigitValue > 0)) {
      c128CheckDigit = (char) (CheckDigitValue + 32);
    }
    if (CheckDigitValue > 94) {
      c128CheckDigit = (char) (CheckDigitValue + 100);
    }
    if (CheckDigitValue == 0) {
      c128CheckDigit = (char) 192;
    }

    // Earlier versions of this method didn't encode the start, stop and checksum
    // characters using HTML character escape code.  This was causing barcodes to
    // be displayed incorrectly in the web browser (and also printed incorrectly when
    // the web pages were printed).  The root of the problem was that sometimes, the
    // extra characters (such as the checksum character) can be outside the ordinary
    // ASCII printable range, and this was apparently confusing something along the
    // way.  Escaping the special characters seems to have solved the problem.
    //
    // NOTE: A previous version of this code just used "&#" + (int)c128Start + ";".
    // On some machines this worked, on others it appended the character rather
    // than its numeric value.  Using String.valueOf instead seems to work right
    // on all machines.
    //
    String printableString =
      "&#"
        + String.valueOf((int) c128Start)
        + ";"
        + strBarcode
        + "&#"
        + String.valueOf((int) c128CheckDigit)
        + ";"
        + "&#"
        + String.valueOf((int) c128Stop)
        + ";"
        + " ";

    return printableString;
  }
  /**
   * Insert the method's description here.
   * Creation date: (9/13/2001 9:30:51 AM)
   * @return boolean
   * @param beginDate java.util.Calendar
   * @param endDate java.util.Calendar
   */
  public static boolean dateRangeCorrect(Calendar beginDate, Calendar endDate) {

    return beginDate.before(endDate);

  }
  /**
   * Insert the method's description here.
   * Creation date: (2/16/01 4:20:41 PM)
   * @return boolean
   */
  public static boolean findObjDesc(Vector userprof, String str) {
    boolean found = false;
    Hashtable row = new Hashtable();
    for (int i = 0; i < userprof.size(); i++) {
      row = (Hashtable) userprof.elementAt(i);
      if (row.get("object_description").equals(str)) {
        found = true;
        break;
      }
    }
    return found;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/6/2001 9:00:22 PM)
   * @return java.lang.String
   * @param sampleBarcode java.lang.String
   */
  public static String genASMEntryID(String sampleBarcode) {

    String sampleID = sampleBarcode.substring(2);

    long sampleNum = hexDecode(sampleID);
    long asmFormNum = 0;
    int modNum = 0;

    if (sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_IMPORTED)) {
      return "";
    }
    else if (sampleBarcode.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {
      asmFormNum = ((sampleNum - 1) / NUM_SAMPLE_FR) + 1;
      modNum = NUM_SAMPLE_FR;
    }
    else if (sampleBarcode.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
      asmFormNum = ((sampleNum - 1) / NUM_SAMPLE_PA) + 1;
      modNum = NUM_SAMPLE_PA;
    }
    else {
      throw new IllegalArgumentException("Unknown Barcode");
    }

    StringBuffer asmFormStrBuf = new StringBuffer(20);
    String asmFormNumStr = String.valueOf(asmFormNum);
    for (int i = asmFormNumStr.length(); i < ASM_NUM_LENGTH; i++) {
      asmFormStrBuf.append('0');
    }
    asmFormStrBuf.append(asmFormNumStr);
    String asmFormStr = asmFormStrBuf.toString();

    Vector asmIDs = genASMEntryIDs("ASM" + asmFormStr);
    int temp = (int) ((sampleNum - 1) / (modNum / NUM_ASMENTRY)) % NUM_ASMENTRY;
    return (String) asmIDs.get(temp);

  }
  /**
   * Insert the method's description here.
   * Creation date: (1/23/2001 1:55:12 PM)
   * @return java.util.Vector
   * @param asmFormID java.lang.String
   */
  public static java.util.Vector genASMEntryIDs(String asmFormID) {
    String myasmFormID = asmFormID.substring(3);
    Long asmForm = new Long(myasmFormID);
    long asmFormLong = asmForm.longValue();
    Vector results = new Vector();
    String[] letters =
      {
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X" };

    if (asmFormLong >= ASM_RANGE_ABC_START && asmFormLong <= ASM_RANGE_ABC_END) {
      for (int i = 0; i < 3; i++) {
        results.add(asmFormID + "_" + letters[i]);
      }
    }
    else if (asmFormLong >= ASM_RANGE_DEF_START && asmFormLong <= ASM_RANGE_DEF_END) {
      for (int i = 3; i < 6; i++) {
        results.add(asmFormID + "_" + letters[i]);
      }
    }
    else if (asmFormLong >= ASM_RANGE_GHI_START && asmFormLong <= ASM_RANGE_GHI_END) {
      for (int i = 6; i < 9; i++) {
        results.add(asmFormID + "_" + letters[i]);
      }
    }
    else if (asmFormLong >= ASM_RANGE_JKL_START && asmFormLong <= ASM_RANGE_JKL_END) {
      for (int i = 9; i < 12; i++) {
        results.add(asmFormID + "_" + letters[i]);
      }
    }
    else if (asmFormLong >= ASM_RANGE_MNO_START && asmFormLong <= ASM_RANGE_MNO_END) {
      for (int i = 12; i < 15; i++) {
        results.add(asmFormID + "_" + letters[i]);
      }
    }
    else if (asmFormLong >= ASM_RANGE_PQR_START && asmFormLong <= ASM_RANGE_PQR_END) {
      for (int i = 15; i < 18; i++) {
        results.add(asmFormID + "_" + letters[i]);
      }
    }
    else if (asmFormLong >= ASM_RANGE_STU_START && asmFormLong <= ASM_RANGE_STU_END) {
      for (int i = 18; i < 21; i++) {
        results.add(asmFormID + "_" + letters[i]);
      }
    }
    else if (asmFormLong >= ASM_RANGE_VWX_START && asmFormLong <= ASM_RANGE_VWX_END) {
      for (int i = 21; i < 24; i++) {
        results.add(asmFormID + "_" + letters[i]);
      }
    }
    else if (asmFormLong >= ASM_RANGE_PASUB_START && asmFormLong <= ASM_RANGE_PASUB_END) {
      for (int i = 21; i < 24; i++) {
        results.add(asmFormID + "_" + letters[i]);
      }
    }
    else {
      throw new ApiException("Invalid ASM Form number");
    }
    return results;
  }
  /**
   * Insert the method's description here.
   * Creation date: (1/23/2001 1:56:44 PM)
   * @return java.util.Vector
   * @param asmFormID java.lang.String
   */
  public static java.util.Vector genFrozenIDs(String asmFormID) {

    String myasmFormID = asmFormID.substring(3);
    Long asmForm = new Long(myasmFormID);
    long asmFormLong = asmForm.longValue();
    Vector results = new Vector();

    for (int i = 0; i < NUM_SAMPLE_FR; i++) {
      long asmEntryLong = ((((asmFormLong * NUM_SAMPLE_FR) - NUM_SAMPLE_FR) + 1) + i);
      results.add(
        makeHexId(ValidateIds.PREFIX_SAMPLE_FROZEN, asmEntryLong, ValidateIds.LENGTH_SAMPLE_ID));
    }

    return results;
  }
  /**
   * Insert the method's description here.
   * Creation date: (1/23/2001 1:56:44 PM)
   * @return java.util.Vector
   * @param asmFormID java.lang.String
   */
  public static java.util.Vector genFrozenIDs(String asmFormID, int module) {

    String myasmFormID = asmFormID.substring(3);
    Long asmForm = new Long(myasmFormID);
    long asmFormLong = asmForm.longValue();
    Vector results = new Vector();

    //module is base 0
    for (int i = (0 + (module * NUM_SAMPLE_FR / NUM_SAMPLE_PA));
      i < ((module + 1) * NUM_SAMPLE_FR / NUM_SAMPLE_PA);
      i++) {
      long asmEntryLong = ((((asmFormLong * NUM_SAMPLE_FR) - NUM_SAMPLE_FR) + 1) + i);
      results.add(
        makeHexId(ValidateIds.PREFIX_SAMPLE_FROZEN, asmEntryLong, ValidateIds.LENGTH_SAMPLE_ID));
    }

    return results;
  }
  /**
   * Insert the method's description here.
   * Creation date: (4/30/2001 2:44:05 PM)
   * @return java.util.Vector
   * @param asmID java.lang.String
   */
  public static Vector genFrozenIDsFromASMID(String asmID) throws Exception {
    String asmFormID = asmID.substring(0, (asmID.length() - 2));

    Vector myFrozen = new Vector();

    String module = asmID.substring(asmID.length() - 1, asmID.length());

    if (module.equals("A")
      || module.equals("D")
      || module.equals("G")
      || module.equals("J")
      || module.equals("M")
      || module.equals("P")
      || module.equals("S")
      || module.equals("V")) {
      //module number is base 0
      myFrozen = genFrozenIDs(asmFormID, 0);
    }
    else if (
      module.equals("B")
        || module.equals("E")
        || module.equals("H")
        || module.equals("K")
        || module.equals("N")
        || module.equals("Q")
        || module.equals("T")
        || module.equals("W")) {
      //module number is base 0
      myFrozen = genFrozenIDs(asmFormID, 1);
    }
    else if (
      module.equals("C")
        || module.equals("F")
        || module.equals("I")
        || module.equals("L")
        || module.equals("O")
        || module.equals("R")
        || module.equals("U")
        || module.equals("X")) {
      //module number is base 0
      myFrozen = genFrozenIDs(asmFormID, 2);
    }
    else
      throw new Exception("Module not recognized");

    return myFrozen;
  }

  /**
   * Form an id from the specified prefix and number.
   * If the resulting id would be shorter than the specified length, the number part will
   * be padded on the left with zeros.
   *    
   * @param prefix the prefix to put on the id
   * @param number the number part of the id
   * @param length the total string length of the resulting id
   * @param useHex if true, the number part will be encoded with uppercase hex digits, otherwise
   *    the number part will be encoded in decimal digits.
   * @return the id
  
   * @see #makeDecimalId(String, long, int)
   * @see #makeHexId(String, long, int)
   */
  public static String makeId(String prefix, long number, int length, boolean useHex) {
    String numberPart;
    if (useHex) {
      numberPart = Long.toHexString(number).toUpperCase();
    }
    else {
      numberPart = Long.toString(number, 10);
    }

    StringBuffer sb = new StringBuffer(length);
    sb.append(prefix);
    int paddingNeeded = length - prefix.length() - numberPart.length();
    for (int i = 0; i < paddingNeeded; i++) {
      sb.append('0');
    }
    sb.append(numberPart);

    return sb.toString();
  }

  /**
   * Form an id from the specified prefix and number.
   * If the resulting id would be shorter than the specified length, the number part will
   * be padded on the left with zeros.  The number part will be encoded with decimal digits.
   *    
   * @param prefix the prefix to put on the id
   * @param number the number part of the id
   * @param length the total string length of the resulting id
   * @return the id
   * 
   * @see #makeId(String, long, int, boolean)
   * @see #makeHexId(String, long, int)
   */
  public static String makeDecimalId(String prefix, long number, int length) {
    return makeId(prefix, number, length, false);
  }

  /**
   * Form an id from the specified prefix and number.
   * If the resulting id would be shorter than the specified length, the number part will
   * be padded on the left with zeros.  The number part will be encoded with uppercase hex digits.
   *    
   * @param prefix the prefix to put on the id
   * @param number the number part of the id
   * @param length the total string length of the resulting id
   * @return the id
   * 
   * @see #makeId(String, long, int, boolean)
   * @see #makeDecimalId(String, long, int)
   */
  public static String makeHexId(String prefix, long number, int length) {
    return makeId(prefix, number, length, true);
  }

  /**
   * The logical repository id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by BTXDetails#getDirectlyInvolvedObjects() to index a transaction.
   * This function assumes that its argument is an id string that just contains the numeric
   * id, and returns a prefixed id representing the same logical repository.
   * 
   * @param numericId The numeric (real) repository id.
   * @return the prefixed logical repository id.  If the id passed in is null or empty, this
   *     returns the id that was passed in.
   * @see #makeRealLogicalRepositoryId(String)
   */
  public static String makePrefixedLogicalRepositoryId(String numericId) {
    if (ApiFunctions.isEmpty(numericId)) {
      return numericId;
    }
    else {
      return FormLogic.makeDecimalId(
        ValidateIds.PREFIX_LOGICAL_REPOSITORY,
        Long.parseLong(numericId),
        ValidateIds.LENGTH_LOGICAL_REPOSITORY_ID);
    }
  }

  /**
   * The logical repository id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by BTXDetails#getDirectlyInvolvedObjects() to index a transaction.
   * This function assumes that its argument is an id string that contains the prefixed
   * id, and returns the real logical repository id as it exists in the database.
   *
   * @param prefixedId The prefixed repository id.
   * @return the real logical repository id.  If the id passed in is null or empty, this
   *     returns the id that was passed in.
   * @see #makePrefixedLogicalRepositoryId(String)
   */
  public static String makeRealLogicalRepositoryId(String prefixedId) {
    if (ApiFunctions.isEmpty(prefixedId)) {
      return prefixedId;
    }
    else {
      String numberPart = prefixedId.substring(2);
      // Skip over leading zeros, the string we return will be the most natural
      // string representation of the id number (no leading zeros).

      char[] temp = numberPart.toCharArray();
      for (int i = 0; i < temp.length; i++) {
        if ('0' != temp[i]) {
          return numberPart.substring(i);
        }
      }
      // If we get here, the number was all zeros.
      return "0";
    }
  }

  /**
   * Return true if the specified string is a valid prefixed logical repository id.
   * See {@link #makePrefixedLogicalRepositoryId(String)} for an explanation of the
   * difference between a true logical repository id and a prefixed one.  Prefixed
   * ids are NOT the true ids as they are stored in the database.
   * 
   * @param s the string
   * @return true if the specified string is a valid prefixed logical repository id.
   */
  public static boolean validPrefixedLogicalRepositoryId(String s) {
    if (s == null
      || s.length() != ValidateIds.LENGTH_LOGICAL_REPOSITORY_ID
      || !s.startsWith(ValidateIds.PREFIX_LOGICAL_REPOSITORY)) {
      return false;
    }
    else {
      String numberPart = s.substring(2);
      return validNumbers(numberPart);
    }
  }

  /**
   * The policy id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by BTXDetails#getDirectlyInvolvedObjects() to index a transaction.
   * This function assumes that its argument is an id string that just contains the numeric
   * id, and returns a prefixed id representing the same logical repository.
   * 
   * @param numericId The numeric (real) policy id.
   * @return the prefixed policy id.  If the id passed in is null or empty, this
   *     returns the id that was passed in.
   * @see #makeRealPolicyId(String)
   */
  public static String makePrefixedPolicyId(String numericId) {
    if (ApiFunctions.isEmpty(numericId)) {
      return numericId;
    }
    else {
      return FormLogic.makeDecimalId(
        ValidateIds.PREFIX_POLICY,
        Long.parseLong(numericId),
        ValidateIds.LENGTH_POLICY_ID);
    }
  }

  /**
   * The policy id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by BTXDetails#getDirectlyInvolvedObjects() to index a transaction.
   * This function assumes that its argument is an id string that contains the prefixed
   * id, and returns the real policy id as it exists in the database.
   *
   * @param prefixedId The prefixed policy id.
   * @return the real logical repository id.  If the id passed in is null or empty, this
   *     returns the id that was passed in.
   * @see #makePrefixedPolicyId(String)
   */
  public static String makeRealPolicyId(String prefixedId) {
    if (ApiFunctions.isEmpty(prefixedId)) {
      return prefixedId;
    }
    else {
      String numberPart = prefixedId.substring(2);
      // Skip over leading zeros, the string we return will be the most natural
      // string representation of the id number (no leading zeros).

      char[] temp = numberPart.toCharArray();
      for (int i = 0; i < temp.length; i++) {
        if ('0' != temp[i]) {
          return numberPart.substring(i);
        }
      }
      // If we get here, the number was all zeros.
      return "0";
    }
  }

  /**
   * Return true if the specified string is a valid prefixed policy id.
   * See {@link #makePrefixedPolicyId(String)} for an explanation of the
   * difference between a true policy id and a prefixed one.  Prefixed
   * ids are NOT the true ids as they are stored in the database.
   * 
   * @param s the string
   * @return true if the specified string is a valid prefixed policy id.
   */
  public static boolean validPrefixedPolicyId(String s) {
    if (s == null
      || s.length() != ValidateIds.LENGTH_POLICY_ID
      || !s.startsWith(ValidateIds.PREFIX_POLICY)) {
      return false;
    }
    else {
      String numberPart = s.substring(2);
      return validNumbers(numberPart);
    }
  }

  /**
   * The url id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by BTXDetails#getDirectlyInvolvedObjects() to index a transaction.
   * This function assumes that its argument is an id string that just contains the numeric
   * id, and returns a prefixed id representing the same url.
   * 
   * @param numericId The numeric (real) url id.
   * @return the prefixed url id.  If the id passed in is null or empty, this
   *     returns the id that was passed in.
   * @see #makeRealUrlId(String)
   */
  public static String makePrefixedUrlId(String numericId) {
    if (ApiFunctions.isEmpty(numericId)) {
      return numericId;
    }
    else {
      return FormLogic.makeDecimalId(
        FormLogic.PREFIX_URL,
        Long.parseLong(numericId),
        FormLogic.LENGTH_URL);
    }
  }

  /**
   * The url id as stored in the database is just a number.  In some situations
   * we need a prefixed, fixed-length id -- for example to have an identifiable id type
   * that can be returned by BTXDetails#getDirectlyInvolvedObjects() to index a transaction.
   * This function assumes that its argument is an id string that contains the prefixed
   * id, and returns the real url id as it exists in the database.
   *
   * @param prefixedId The prefixed url id.
   * @return the real url id.  If the id passed in is null or empty, this
   *     returns the id that was passed in.
   * @see #makePrefixedUrlId(String)
   */
  public static String makeRealUrlId(String prefixedId) {
    if (ApiFunctions.isEmpty(prefixedId)) {
      return prefixedId;
    }
    else {
      String numberPart = prefixedId.substring(FormLogic.PREFIX_URL.length());
      // Skip over leading zeros, the string we return will be the most natural
      // string representation of the id number (no leading zeros).
      char[] temp = numberPart.toCharArray();
      for (int i = 0; i < temp.length; i++) {
        if ('0' != temp[i]) {
          return numberPart.substring(i);
        }
      }
      // If we get here, the number was all zeros.
      return "0";
    }
  }

  /**
   * Return true if the specified string is a valid prefixed url id.
   * See {@link #makePrefixedUrlId(String)} for an explanation of the
   * difference between a true url id and a prefixed one.  Prefixed
   * ids are NOT the true ids as they are stored in the database.
   * 
   * @param s the string
   * @return true if the specified string is a valid prefixed url id.
   */
  public static boolean validPrefixedUrlId(String s) {
    if (s == null || s.length() != FormLogic.LENGTH_URL || !s.startsWith(PREFIX_URL)) {
      return false;
    }
    else {
      String numberPart = s.substring(FormLogic.PREFIX_URL.length());
      return validNumbers(numberPart);
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (1/23/2001 1:57:29 PM)
   * @return java.util.Vector
   * @param asmFormID java.lang.String
   */
  public static java.util.Vector genParaffinIDs(String asmFormID) {

    String myasmFormID = asmFormID.substring(3);
    Long asmForm = new Long(myasmFormID);
    long asmFormLong = asmForm.longValue();
    Vector results = new Vector();

    for (int i = 0; i < NUM_SAMPLE_PA; i++) {
      long asmEntryLong = ((((asmFormLong * NUM_SAMPLE_PA) - NUM_SAMPLE_PA) + 1) + i);
      results.add(
        makeHexId(ValidateIds.PREFIX_SAMPLE_PARAFFIN, asmEntryLong, ValidateIds.LENGTH_SAMPLE_ID));
    }

    return results;
  }
  /**
   * Insert the method's description here.
   * Creation date: (1/23/2001 1:57:29 PM)
   * @return java.util.Vector
   * @param asmFormID java.lang.String
   */
  public static java.util.Vector genParaffinIDs(String asmFormID, int module) {

    String myasmFormID = asmFormID.substring(3);
    Long asmForm = new Long(myasmFormID);
    long asmFormLong = asmForm.longValue();
    Vector results = new Vector();

    for (int i = 0 + module; i < module + 1; i++) {
      long asmEntryLong = ((((asmFormLong * NUM_SAMPLE_PA) - NUM_SAMPLE_PA) + 1) + i);
      results.add(
        makeHexId(ValidateIds.PREFIX_SAMPLE_PARAFFIN, asmEntryLong, ValidateIds.LENGTH_SAMPLE_ID));
    }

    return results;
  }
  /**
   * Insert the method's description here.
   * Creation date: (4/30/2001 2:44:05 PM)
   * @return java.util.Vector
   * @param asmID java.lang.String
   */
  public static Vector genParaffinIDsFromASMID(String asmID) throws Exception {

    String asmFormID = asmID.substring(0, (asmID.length() - 2));

    Vector myParaffin = new Vector();

    String module = asmID.substring(asmID.length() - 1, asmID.length());

    if (module.equals("A")
      || module.equals("D")
      || module.equals("G")
      || module.equals("J")
      || module.equals("M")
      || module.equals("P")
      || module.equals("S")
      || module.equals("V")) {
      //module number is base 0
      myParaffin = genParaffinIDs(asmFormID, 0);
    }
    else if (
      module.equals("B")
        || module.equals("E")
        || module.equals("H")
        || module.equals("K")
        || module.equals("N")
        || module.equals("Q")
        || module.equals("T")
        || module.equals("W")) {
      //module number is base 0
      myParaffin = genParaffinIDs(asmFormID, 1);
    }
    else if (
      module.equals("C")
        || module.equals("F")
        || module.equals("I")
        || module.equals("L")
        || module.equals("O")
        || module.equals("R")
        || module.equals("U")
        || module.equals("X")) {
      //module number is base 0
      myParaffin = genParaffinIDs(asmFormID, 2);
    }
    else
      throw new Exception("Module not recognized");

    return myParaffin;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/19/2001 3:44:55 PM)
   * @return java.lang.String
   * @param sampleID int
   */
  public static String getASMLocation(String sampleID) {
    long sampleLong = hexDecode(sampleID.substring(2));
    long sampleTemp = 0;
    String samplePrefix = "";

    if (sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_IMPORTED)) {
      return "";
    }
    else if (sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
      sampleTemp = sampleLong % (NUM_SAMPLE_PA / NUM_ASMENTRY);
      if (sampleTemp == 0) {
        sampleTemp = 0;
      }
      samplePrefix = "P";
    }
    else if (sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {
      sampleTemp = sampleLong % (NUM_SAMPLE_FR / NUM_ASMENTRY);
      if (sampleTemp == 0) {
        sampleTemp = NUM_SAMPLE_FR / NUM_ASMENTRY;
      }
      samplePrefix = "F";
    }

    String asmID = "";
    try {
      asmID = genASMEntryID(sampleID);
    }
    catch (Exception e) {
    }
    String module = asmID.substring(asmID.length() - 1);

    String stringTemp = "";
    if (sampleTemp != 0) {
      stringTemp = sampleTemp + "";
    }

    return (module + samplePrefix + stringTemp);

  }
  /**
   * Insert the method's description here.
   * Creation date: (6/14/2002 10:43:46 AM)
   * @return java.lang.String
   * @param param int
   */
  public static String getFourCharacterString(int param) {
    String paramString = String.valueOf(param);
    if (paramString.length() < 4) {
      while (paramString.length() < 4) {
        paramString = "0" + paramString;
      }
    }
    return paramString;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/29/2002 8:29:47 AM)
   * @return java.util.Vector
   */
  public static Vector getHourIntVector() {
    Vector blah = new Vector();
    blah.add(new LabelValueBean("Hr", "-1"));
    for (int i = 1; i <= 12; i++) {
      blah.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
    }

    return blah;
  }
  /**
   * Insert the method's description here.
   * Creation date: (6/14/2002 10:36:30 AM)
   * @return java.lang.String
   * @param topLevel int
   * @param middleLevel int
   * @param bottomLevel int
   * @param tissue int
   */
  public static String getJSPID(int topLevel) {
    String top = getFourCharacterString(topLevel);

    String result = top;

    return result;
  }
  /**
   * Insert the method's description here.
   * Creation date: (6/14/2002 10:36:30 AM)
   * @return java.lang.String
   * @param topLevel int
   * @param middleLevel int
   * @param bottomLevel int
   * @param tissue int
   */
  public static String getJSPID(int topLevel, int middleLevel) {
    String top = getFourCharacterString(topLevel);
    String middle = getFourCharacterString(middleLevel);

    String result = top + "." + middle;

    return result;
  }
  /**
   * Insert the method's description here.
   * Creation date: (6/14/2002 10:36:30 AM)
   * @return java.lang.String
   * @param topLevel int
   * @param middleLevel int
   * @param bottomLevel int
   * @param tissue int
   */
  public static String getJSPID(int topLevel, int middleLevel, int bottomLevel) {
    String top = getFourCharacterString(topLevel);
    String middle = getFourCharacterString(middleLevel);
    String bottom = getFourCharacterString(bottomLevel);

    String result = top + "." + middle + "." + bottom;

    return result;
  }
  /**
   * Insert the method's description here.
   * Creation date: (6/14/2002 10:36:30 AM)
   * @return java.lang.String
   * @param topLevel int
   * @param middleLevel int
   * @param bottomLevel int
   * @param tissue int
   */
  public static String getJSPID(int topLevel, int middleLevel, int bottomLevel, int tissueLevel) {
    String top = getFourCharacterString(topLevel);
    String middle = getFourCharacterString(middleLevel);
    String bottom = getFourCharacterString(bottomLevel);
    String tissue = getFourCharacterString(tissueLevel);

    String result = top + "." + middle + "." + bottom + "." + tissue;

    return result;
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/29/2002 8:29:47 AM)
   * @return java.util.Vector
   */
  public static Vector getMinuteIntVector() {
    Vector blah = new Vector();
    blah.add(new LabelValueBean("Min", "-1"));
    for (int i = 0; i <= 59; i++) {
      if (i >= 0 && i <= 9) {
        blah.add(new LabelValueBean("0" + i, String.valueOf(i)));
      }
      else {
        blah.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
      }
    }

    return blah;
  }

  public static String getSampleTypeCid(String sampleId) {
    if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {
      return ArtsConstants.SAMPLE_TYPE_FROZEN_TISSUE;
    }
    else if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
      return ArtsConstants.SAMPLE_TYPE_PARAFFIN_TISSUE;
    }
    else {
      return getSampleTypeCidForSample(sampleId);
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (1/22/2001 6:58:58 PM)
   * @return byte[]
   * @param s java.lang.String
   */
  public static long hexDecode(String s) {
    return Long.decode("#" + s).longValue();
  }
  /**
   * Insert the method's description here.
   * Creation date: (4/18/2002 4:40:51 PM)
   * @return boolean
   * @param asm_form_id java.lang.String
   */
  public static boolean isConversion(String asm_form_id) {
    if (asm_form_id != null) {

      int value = 0;

      try {
        value = (new Integer(asm_form_id.substring(3))).intValue();
      }
      catch (NumberFormatException nfe) {
        return false;
      }

      if ((ASM_RANGE_ABC_START <= value) && (value <= ASM_RANGE_ABC_START + 4999)) {
        return true;
      }
      if ((ASM_RANGE_DEF_START <= value) && (value <= ASM_RANGE_DEF_START + 4999)) {
        return true;
      }
      if ((ASM_RANGE_GHI_START <= value) && (value <= ASM_RANGE_GHI_START + 4999)) {
        return true;
      }
      if ((ASM_RANGE_JKL_START <= value) && (value <= ASM_RANGE_JKL_START + 4999)) {
        return true;
      }
      if ((ASM_RANGE_MNO_START <= value) && (value <= ASM_RANGE_MNO_START + 4999)) {
        return true;
      }
      if ((ASM_RANGE_PQR_START <= value) && (value <= ASM_RANGE_PQR_START + 4999)) {
        return true;
      }
      if ((ASM_RANGE_STU_START <= value) && (value <= ASM_RANGE_STU_START + 4999)) {
        return true;
      }
      if ((ASM_RANGE_VWX_START <= value) && (value <= ASM_RANGE_VWX_START + 4999)) {
        return true;
      }
      if ((ASM_RANGE_PASUB_START <= value) && (value <= ASM_RANGE_PASUB_END)) {
        return true;
      }
    }
    return false;

  }

  /**
   * Insert the method's description here.
   * Creation date: (5/23/2001 11:19:10 AM)
   * @return java.lang.String
   * @param status java.lang.String
   */
  public static String lookupAppearance(String appearance) {
    if (appearance == null) {
      return "Any";
    }
    else {
      Object description = _appearanceCodes.get(appearance);
      if (description == null)
        return null;
      else
        return (String) description;
    }
  }

  public static String lookupCheckOutStatusOrReason(String status) {
    if (ApiFunctions.isEmpty(status)) {
      return "Other";
    }
    else if (status.equals(SMPL_ARCOCASEPULL)) {
      return "Case Pull at Ardais";
    }
    else if (status.equals(SMPL_ARCOCONSREV)) {
      return "Consent Revocation at Ardais";
    }
    else if (status.equals(SMPL_ARCOOTHER)) {
      return "Other at Ardais";
    }
    else if (status.equals(SMPL_ARMVTOPATH)) {
      return "Moved to Pathology";
    }
    else if (status.equals(SMPL_COCONSUMED)) {
      return "Consumed";
    }
    else if (status.equals(SMPL_CORND)) {
      return "Moved to R&D";
    }
    else if (status.equals(SMPL_ESSOLD)) {
      return "Sold";
    }
    else if (status.equals(SMPL_MICOCASEPULL)) {
      return "Case Pull at Medical Institution";
    }
    else if (status.equals(SMPL_MICOCONSREV)) {
      return "Consent Revocation at Medical Institution";
    }
    else if (status.equals(SMPL_MICOOTHER)) {
      return "Other at Medical Institution";
    }
    else if (status.equals(BOX_ARCONSREV) || status.equals(BOX_MICONSREV)) {
      return "Consent Revocation";
    }
    else if (status.equals(BOX_ARCASEPULL) || status.equals(BOX_MICASEPULL)) {
      return "Case Pull";
    }
    else if (status.equals(BOX_AROTHER) || status.equals(BOX_MIOTHER)) {
      return "Other";
    }
    else if (status.equals(BOX_ARCONSUME)) {
      return "Consumed";
    }
    else if (status.equals(BOX_ARMVTORND)) {
      return "Move to R&D";
    }
    else if (status.equals(BOX_MVTOPATH)) {
      return "Move to Pathology";
    }
    // MR 5089 7/31/03
    // added BOX_LICENSED and BOX_DESTROYED as a means of enhancing the checkout
    // reasons for transaction Ardais Check Samples Out of Inventory.
    // this is to be used for reporting purposes
    // IMPORTANT NOTE:  these new values are not statuses in terms of the rest of the system
    //  and business logic (in fact, this routine is actually used to process reasons
    //  and not statuses)
    else if (status.equals(BOX_LICENSED)) {
      return "Licensed";
    }
    else if (status.equals(BOX_DESTROYED)) {
      return "Destroyed";
    }
    else if (status.equals(SMPL_CHECKEDOUT)) {
      return "Checked Out";
    }

    return "Other";
  }
  /**
   * Insert the method's description here.
   * Creation date: (6/25/2001 2:04:52 PM)
   * @return java.lang.String
   * @param status java.lang.String
   */
  public static String lookupLIMSCertaintyStatus(String status) {
    if (status.equals("Y")) {
      return "Certain";
    }
    if (status.equals("N")) {
      return "Uncertain";
    }
    return "N/A";

  }
  /**
   * Insert the method's description here.
   * Creation date: (6/25/2001 2:04:52 PM)
   * @return java.lang.String
   * @param status java.lang.String
   */
  public static String lookupLIMSConcordantStatus(String status) {
    if (status.equals("M")) {
      return "Major Discordant vs. Microscopic Feature";
    }
    if (status.equals("A")) {
      return "Major Discordant vs. Gross Appearance";
    }
    if (status.equals("N")) {
      return "Minor Discordant";
    }
    if (status.equals("C")) {
      return "Concordant";
    }
    return "N/A";

  }

  /**
   * Convert a QC status to a display string.
   * 
   * @param status The QC status.
   * @return The display string for the status.
   */
  public static String lookupQCStatus(String status) {
    if (ApiFunctions.isEmpty(status)) {
      return "None";
    }
    else if (status.equals(SMPL_QCAWAITING)) {
      return "Awaiting Verification";
    }
    else if (status.equals(SMPL_QCVERIFIED)) {
      return "Verified";
    }
    else if (status.equals(SMPL_QCINPROCESS)) {
      return "In Process";
    }
    else {
      return status;
    }
  }

  /**
   * Convert a sales status to a display string.
   * 
   * @param status The sales status.
   * @return The display string for the status.
   */
  public static String lookupSalesStatus(String status) {
    if (ApiFunctions.isEmpty(status)) {
      return "Not Yet Available";
    }
    else if (status.equals(SMPL_GENRELEASED)) {
      return "Available";
    }
    else if (status.equals(SMPL_ADDTOCART)) {
      return "On Hold";
    }
    else if (status.equals(SMPL_ESSHIPPED)) {
      return "Shipped to Client";
    }
    else if (status.equals(SMPL_ESSOLD)) {
      return "Sold";
    }
    else if (status.equals(SMPL_GENRECALLED)) {
      return "Recalled";
    }
    else {
      return status;
    }
  }

  /**
   * Convert an inventory status to a display string.
   * 
   * @param status The inventory status.
   * @return The display string for the status.
   */
  public static String lookupInvStatus(String status) {
    if (ApiFunctions.isEmpty(status)) {
      return "Not In Active Inventory";
    }
    else if (status.equals(SMPL_ARMVTOPATH)) {
      return "Pathology";
    }
    else if (status.equals(SMPL_CORND)) {
      return "R&D";
    }
    else if (status.equals(SMPL_TRANSFER)) {
      return "In Transit";
    }
    else if (
      status.equals(SMPL_CHECKEDOUT)
        || status.equals(SMPL_ARCOOTHER)
        || status.equals(SMPL_MICOOTHER)) {
      return "Checked Out";
    }
    else if (status.equals(SMPL_REQUESTED) || status.equals(SMPL_RNDREQUEST)) {
      return "Requested";
    }
    else if (status.equals(SMPL_BOXSCAN)) {
      return "In Repository";
    }
    else if (status.equals(SMPL_ARCOCASEPULL) || status.equals(SMPL_MICOCASEPULL)) {
      return "Checked Out: Case Pulled";
    }
    else if (status.equals(SMPL_ARCOCONSREV) || status.equals(SMPL_MICOCONSREV)) {
      return "Checked Out: Consent Revoked";
    }
    else if (status.equals(SMPL_COCONSUMED)) {
      return "Consumed";
    }
    else {
      return status;
    }
  }

  public static boolean validBoxID(String boxID) {
    if (boxID == null || boxID.length() != ValidateIds.LENGTH_BOX_ID || !boxID.startsWith("BX")) {
      return false;
    }
    else if (!validNumbers(boxID.substring(2)))
      return false;
    else {
      return true;
    }
  }

  public static boolean validManifestId(String s) {
    if (s == null
      || s.length() != ValidateIds.LENGTH_MANIFEST_ID
      || !s.startsWith(ValidateIds.PREFIX_MANIFEST)) {
      return false;
    }
    else if (!validNumbers(s.substring(ValidateIds.PREFIX_MANIFEST.length()))) {
      return false;
    }

    return true;
  }

  public static boolean validRequestId(String s) {
    if (s == null
      || s.length() != ValidateIds.LENGTH_REQUEST_ID
      || !s.startsWith(ValidateIds.PREFIX_REQUEST)) {
      return false;
    }
    else if (!validNumbers(s.substring(ValidateIds.PREFIX_REQUEST.length()))) {
      return false;
    }

    return true;
  }

  /**
   * Insert the method's description here.
   * Creation date: (3/19/01 4:05:40 PM)
   * @return boolean
   * @param sampleId java.lang.String
   */
  public static boolean validHex(String ID) {
    try {
      Long.decode("#" + ID);
      return true;
    }
    catch (NumberFormatException e) {
      return false;
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (3/19/01 4:05:40 PM)
   * @return boolean
   * @param sampleId java.lang.String
   */
  public static boolean validNumbers(String ID) {
    boolean valid = true;
    char[] temp = ID.toCharArray();
    for (int i = 0; i < temp.length; i++) {
      if (!Character.isDigit(temp[i])) {
        valid = false;
        break;
      }
    }
    return valid;
  }

  /**
   * Insert the method's description here.
   * Creation date: (3/19/01 4:05:40 PM)
   * @return boolean
   * @param sampleId java.lang.String
   */
  public static boolean validSymbols(String ID) {
    boolean valid = true;
    char[] temp = ID.toCharArray();
    for (int i = 0; i < temp.length; i++) {
      if (!Character.isLetterOrDigit(temp[i])) {
        valid = false;
        break;
      }
    }
    return valid;
  }
  /**
   * Insert the method's description here.
   * Creation date: (9/19/2001 3:51:43 PM)
   * @return boolean
   * @param id java.lang.String
   */
  public static boolean validUpperCase(String id) {
    boolean valid = true;
    char[] temp = id.toCharArray();
    for (int i = 0; i < temp.length; i++) {
      if (!Character.isDigit(temp[i])) {
        if (!Character.isUpperCase(temp[i])) {
          valid = false;
          break;
        }
      }
    }
    return valid;
  }

  /**
   * validates sample for IHC project which can include client samples that start with CF or CP.
   * Creation date: (1/25/01 4:34:56 PM)
   * @return boolean
   * @param sampleID java.lang.String
   */
  public static boolean validIhcSampleID(String sampleID) {
    if (!sampleID.equals("")) {
      if (sampleID.length() != ValidateIds.LENGTH_SAMPLE_ID) {
        return false;
      }
      else if (
        !(sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)
          || sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)
          || sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_CUSTFROZEN)
          || sampleID.startsWith(ValidateIds.PREFIX_SAMPLE_CUSTPARAFFIN))) {
        return false;
      }
      else if (!validHex(sampleID.substring(2))) {
        return false;
      }
      else if (!validUpperCase(sampleID.substring(2))) {
        return false;
      }
      else {
        return true;
      }
    }
    else
      return true;
  }

  /**
  * Insert the method's description here.
  * Creation date: (1/25/01 4:34:56 PM)
  * @return boolean
  * @param boxID java.lang.String
  */
  public static boolean validIhcSlideID(String slideID) {
    boolean valid = true;

    if (!ApiFunctions.isEmpty(slideID)) {
      if (slideID.length() != ValidateIds.LENGTH_SLIDE_ID) {
        return !valid;
      }
      else if (
        !(slideID.startsWith("SF")
          || slideID.startsWith("SP")
          || slideID.startsWith("CSF")
          || slideID.startsWith("CSP"))) {
        return !valid;
      }
      else if ((slideID.startsWith("SF") || slideID.startsWith("SP"))) {
        if (!validHex(slideID.substring(2))) {
          return !valid;
        }
        else if (!validUpperCase(slideID.substring(2))) {
          return !valid;
        }
        else
          return valid;
      }
      else if ((slideID.startsWith("CSF") || slideID.startsWith("CSP"))) {
        if (!validHex(slideID.substring(3))) {
          return !valid;
        }
        else if (!validUpperCase(slideID.substring(3))) {
          return !valid;
        }
        else
          return valid;
      }
      else {
        return valid;
      }
    }
    else
      return !valid;
  }

  /** 
   * Retrieve all the distinct status values a sample has had.
   */
  //Note - I originally implemented this via the Sample EJB and it's association with
  //statuses, but occasionally ran into an odd IllegalStateException (Cannot access 
  //finder result outside transaction).  Couldn't find any solid information on how
  //to fix it, so I changed this to do a sql query.
  public static List getStatusValuesForSample(String sampleId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List statusList = new ArrayList();
    try {
      conn = ApiFunctions.getDbConnection();
      pstmt =
        conn.prepareStatement(
          "select distinct status_type_code from iltds_sample_status where sample_barcode_id = ?");
      pstmt.setString(1, sampleId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        statusList.add(rs.getString("status_type_code"));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(conn, pstmt, rs);
    }
    return statusList;
  }

  /** 
   * Retrieve the sample type code for a sample.
   */
  private static String getSampleTypeCidForSample(String sampleId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sampleTypeCid = null;
    try {
      conn = ApiFunctions.getDbConnection();
      pstmt =
        conn.prepareStatement(
          "select sample_type_cid from iltds_sample where sample_barcode_id = ?");
      pstmt.setString(1, sampleId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        sampleTypeCid = rs.getString(1);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(conn, pstmt, rs);
    }
    return ApiFunctions.safeString(sampleTypeCid);
  }
}
