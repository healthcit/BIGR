package com.ardais.bigr.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class Constants {

    public final static String CONTEXTROOT = "/BIGR";
    public final static String STRUTS_URL_SUFFIX = ".do";
    
    public final static String BIGR = "BIGR";
    
    //ID Lengths (do not add ID Length constants here, please add in ValidateIds)
    public final static int LENGTH_RNAID = 10;

    public final static String ANY = "any";
    public final static String PERFORMED = "performed";
    public final static String SHOW = "show";
    public final static String DEFAULT_ALL = "All (Default)";
    
    //constant holding display value for out of network destination
    public static final String OUT_OF_NETWORK_DISPLAY = "Out of network";
    
    //constants to store/retrieve last used donor, case, and sample ids
    //for DDC and Sample Intake transactions
    public final static String MOST_RECENT_DONOR_ID = "mostRecentDonorId";
    public final static String MOST_RECENT_DONOR_ALIAS = "mostRecentDonorAlias";
    public final static String MOST_RECENT_CASE_ID = "mostRecentCaseId";
    public final static String MOST_RECENT_CASE_ALIAS = "mostRecentCaseAlias";
    public final static String MOST_RECENT_SAMPLE_ID = "mostRecentSampleId";
    public final static String MOST_RECENT_SAMPLE_ALIAS = "mostRecentSampleAlias";

    public final static String BEST = "BEST";
    public final static String ILTDS = "ILTDS";
    public final static String DDC = "DDC";

    public final static String APPEARANCE_MICRO_NORMAL = "R";
    public final static String APPEARANCE_MICRO_LESION = "L";
    public final static String APPEARANCE_MICRO_TUMOR = "T";
    public final static String APPEARANCE_MICRO_OTHER = "O";
    public final static String APPEARANCE_GROSS_NORMAL = "N";
    public final static String APPEARANCE_GROSS_MIXED = "M";
    public final static String APPEARANCE_GROSS_DISEASED = "D";
    public final static String APPEARANCE_GROSS_UNKNOWN = "U";

    public final static String GENDER_MALE = "M";
    public final static String GENDER_FEMALE = "F";
    public final static String GENDER_OTHER = "O";
    public final static String GENDER_UNKNOWN = "U";

    public final static String LINKED_LINKED = "Y";
    public final static String LINKED_UNLINKED = "N";
    
    public final static String PV_STATUS_PVED = "Y";
    public final static String PV_STATUS_UNPVED = "N";

    public final static String HOLDSOLD_ALL_SAMPLES ="ALL";
    public final static String HOLDSOLD_USER_CASE = "USERCASE";
    public final static String HOLDSOLD_USER_CASE_NOT = "USERCASENOT";
    public final static String HOLDSOLD_ACCOUNT_CASE = "ACCOUNTCASE";
    public final static String HOLDSOLD_ACCOUNT_CASE_NOT = "ACCOUNTCASENOT";

    public final static String QC_NOTVER = "qcNotVerifd";
    public final static String QC_VER_ONLY= "qcVerifdOnly";
    public final static String QC_REL_ONLY = "qcRelsdOnly"; 
    public final static String QC_POST = "qcPosted";
    public final static String QC_PULL = "qcPulled";

    public final static String QC_INPROCESS = "qcInProcess";
    public final static String QC_INPROCESS_NOT = "qcInProcessNot";

//    public final static String RESTRICTED_ALL = "any";
    public final static String RESTRICTED_U = "U";
    public final static String RESTRICTED_R = "R";
    public final static String RESTRICTED_MIA = "MIA"; // MI - all
    public final static String RESTRICTED_MIU = "MIU";
    public final static String RESTRICTED_MIR = "MIR";
    public final static String RESTRICTED_MIUR = "MIUR"; 

    public final static String NOT_SPECIFIED = "NS";
    
    // Consent ID prefixes
    public final static String LINKED_CONSENT_PREFIX_INITIAL = "CI0";
    public final static String LINKED_CONSENT_PREFIX_REPEAT = "CI7";

    // ID KEYS
    public final static String KEY_ID_FORM = "0199";

    public final static String KEY_ATTRIBUTES = "0200";
    public final static String KEY_ATTRIBUTES_FORM = "0299";
    public final static String KEY_ATTRIBUTES_RESTRICTED = "0205";

    public final static String KEY_APPEARANCE = "0300";
    public final static String KEY_APPEARANCE_FORM = "0399";

    public final static String KEY_DIAGNOSIS = "0400";
    public final static String KEY_DIAGNOSIS_FORM = "0499";

    public final static String KEY_TISSUE = "0500";
    public final static String KEY_TISSUE_FORM = "0599";
    
    public final static String OBJECT_TYPE_USER = "User";
    public final static String OBJECT_TYPE_ACCOUNT = "Account";
    
    public final static String EMPTY_TO_NONEMPTY = "EmptyToNonEmpty";
    public final static String NONEMPTY_TO_EMPTY = "NonEmptyToEmpty";
    public final static String NONEMPTY_TO_NONEMPTY = "NonEmptyToNonEmpty";
    
    public final static String PASSWORD_CHANGE_REASON_EXPIRED = "Password expired";

    //constants used by the computed data functionality
    public final static String COMPUTED_DATA_OBJECT_TYPE_EVALUATION = "PathologyEvaluation";
    public final static String COMPUTED_DATA_OBJECT_TYPE_SAMPLE = "Sample";
    public final static Collection VALID_COMPUTED_DATA_OBJECT_TYPES = new Vector();
    static {
        VALID_COMPUTED_DATA_OBJECT_TYPES.add(COMPUTED_DATA_OBJECT_TYPE_EVALUATION);
        VALID_COMPUTED_DATA_OBJECT_TYPES.add(COMPUTED_DATA_OBJECT_TYPE_SAMPLE);
    }
    public final static String COMPUTED_DATA_EVAL_FIELD_MICROAPPEARANCE = "MicroAppearance";
    public final static String COMPUTED_DATA_EVAL_FIELD_COMMENTS = "Comments";
    public final static Collection VALID_COMPUTED_DATA_EVAL_FIELDS = new Vector();
    static {
        VALID_COMPUTED_DATA_EVAL_FIELDS.add(COMPUTED_DATA_EVAL_FIELD_MICROAPPEARANCE);
        VALID_COMPUTED_DATA_EVAL_FIELDS.add(COMPUTED_DATA_EVAL_FIELD_COMMENTS);
    }
    public final static String COMPUTED_DATA_EVAL_FIELD_INT_COMMENTS = "Internal_Comments";
    public final static String COMPUTED_DATA_EVAL_FIELD_EXT_COMMENTS = "External_Comments";

    public final static String DIAGNOSTIC_TEST_RESULT_FILTER = "DiagnosticFilter";

    public final static String DATA_TYPE_DATE = "Date";
    public final static String DATA_TYPE_INTEGER = "Integer";
    public final static String DATA_TYPE_BIG_DECIMAL = "BigDecimal";
    
    public final static String TRANSACTION_ILTDS = "ILTDS";
    public final static String TRANSACTION_SR = "SR"; // Sample Registration
    
    //account types
    public final static String ACCOUNT_TYPE_SYSTEM_OWNER = "SO";
    public final static String ACCOUNT_TYPE_SYSTEM_OWNER_DESC = "System Owner";
    public final static String ACCOUNT_TYPE_DONOR_AND_CONSUMER = "DC";
    public final static String ACCOUNT_TYPE_DONOR_AND_CONSUMER_DESC = "Donor and Consumer";
    public final static String ACCOUNT_TYPE_CONSUMER = "CN";
    public final static String ACCOUNT_TYPE_CONSUMER_DESC = "Consumer";
    public final static Map ACCOUNT_TYPE_MAP = new TreeMap();
    static {
      ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_SYSTEM_OWNER, ACCOUNT_TYPE_SYSTEM_OWNER_DESC);
      ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_DONOR_AND_CONSUMER, ACCOUNT_TYPE_DONOR_AND_CONSUMER_DESC);
      ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_CONSUMER, ACCOUNT_TYPE_CONSUMER_DESC);
    }
    
    //account statuses
    public final static String ACCOUNT_STATUS_ACTIVE = "A";
    public final static String ACCOUNT_STATUS_ACTIVE_DESC = "Active";
    public final static String ACCOUNT_STATUS_INACTIVE = "I";
    public final static String ACCOUNT_STATUS_INACTIVE_DESC = "Inactive";
    public final static Map ACCOUNT_STATUS_MAP = new TreeMap();
    static {
      ACCOUNT_STATUS_MAP.put(ACCOUNT_STATUS_ACTIVE, ACCOUNT_STATUS_ACTIVE_DESC);
      ACCOUNT_STATUS_MAP.put(ACCOUNT_STATUS_INACTIVE, ACCOUNT_STATUS_INACTIVE_DESC);
    }
    
    //locations for box scan functionality
    public final static String LOCATION_ARDAIS = "AR";
    public final static String LOCATION_MEDICAL_INSTITUTION = "MI";
    
    public final static String CASE_PULL = "pullCase";
    public final static String CASE_REVOKE = "revokeCase";
    
    public final static String YEAR = "YEAR";
    public final static String MONTH = "MONTH";
    public final static String DAY = "DAY";
    public final static String MERIDIAN = "MERIDIAN";
    public final static String HOUR = "HOUR";
    public final static String MINUTE = "MINUTE";
    
    /**
     * In dropdowns that let the user select the year of consent, this is the number of years
     * prior to the current year that we include in the list of choices.
     */
    public final static int CONSENT_DATE_LOOKBACK_YEARS = 30;

    /**
     * Version numbers of our scheme for encoding a Policy Object, account data, etc
     * as an XML string.
     */
    public static final String ENCODING_SCHEME_XML1 = "XML1";
    public static final String ENCODING_SCHEME_XML2 = "XML2";
    public static final String CURRENT_POLICY_ENCODING = ENCODING_SCHEME_XML2;
    

  public static String[] HOURS = 
    {
      "1",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "11",
      "12"};
      
  public static String[] MINUTES =
    {
      "00",
      "01",
      "02",
      "03",
      "04",
      "05",
      "06",
      "07",
      "08",
      "09",
      "10",
      "11",
      "12",
      "13",
      "14",
      "15",
      "16",
      "17",
      "18",
      "19",
      "20",
      "21",
      "22",
      "23",
      "24",
      "25",
      "26",
      "27",
      "28",
      "29",
      "30",
      "31",
      "32",
      "33",
      "34",
      "35",
      "36",
      "37",
      "38",
      "39",
      "40",
      "41",
      "42",
      "43",
      "44",
      "45",
      "46",
      "47",
      "48",
      "49",
      "50",
      "51",
      "52",
      "53",
      "54",
      "55",
      "56",
      "57",
      "58",
      "59"};


  public static java.lang.String getGenderText(String genderCode) {
    if (genderCode == null)
      return "";
    if (genderCode.equalsIgnoreCase(Constants.GENDER_MALE))
      return "Male";
    if (genderCode.equalsIgnoreCase(Constants.GENDER_FEMALE))
      return "Female";
    if (genderCode.equalsIgnoreCase(Constants.GENDER_OTHER))
      return "Other";
    if (genderCode.equalsIgnoreCase(Constants.GENDER_UNKNOWN))
      return "Unknown";
    return "";
  }

  // This constant is used to define maximum number of colums per line that will be displayed
  // on the screen for manifest printing. 
  public final static int BOX_LAYOUT_MAX_COLUMN = 9;


  public static final String OPERATION_CREATE = "Create";
  public static final String OPERATION_UPDATE = "Update";
  public static final String OPERATION_DELETE = "Delete";
  public static final String OPERATION_ADD = "Add";
  public static final String OPERATION_REMOVE = "Remove";
  public static final String OPERATION_LOCATE = "Locate";
  
  public final static String ATTRIBUTE_RELEVANCE_REQUIRED = "required";
  public final static String ATTRIBUTE_RELEVANCE_OPTIONAL = "optional";
  public final static String ATTRIBUTE_RELEVANCE_PROHIBITED = "prohibited";
  
  public final static Map SAMPLE_ATTRIBUTES_WITH_OTHER = new HashMap();
  static {
    SAMPLE_ATTRIBUTES_WITH_OTHER.put(ArtsConstants.ATTRIBUTE_PROCEDURE, ArtsConstants.ATTRIBUTE_PROCEDURE);
    SAMPLE_ATTRIBUTES_WITH_OTHER.put(ArtsConstants.ATTRIBUTE_TISSUE, ArtsConstants.ATTRIBUTE_TISSUE);
    SAMPLE_ATTRIBUTES_WITH_OTHER.put(ArtsConstants.ATTRIBUTE_BUFFER_TYPE, ArtsConstants.ATTRIBUTE_BUFFER_TYPE);
  }
  
  private final static Map SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME = new HashMap();
  static {
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_BUFFER_TYPE, "bufferTypeCui");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_CELLS_PER_ML, "cellsPerMlAsString");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_COMMENT, "note");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_CONCENTRATION, "concentrationAsString");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION, "collectionDateTime.date");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION, "preservationDateTime.date");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_FIXATIVE_TYPE, "fixativeType");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_FORMAT_DETAIL, "formatDetail");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_GROSS_APPEARANCE, "grossAppearance");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_PERCENT_VIABILITY, "percentViabilityAsString");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_PREPARED_BY, "preparedBy");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_PROCEDURE, "procedure");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_SOURCE, "source");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_TISSUE, "tissue");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS, "totalNumOfCellsAsString");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_VOLUME, "volumeAsString");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_YIELD, "yieldAsString");
    SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.put(ArtsConstants.ATTRIBUTE_WEIGHT, "weightAsString");
  } 
  
  public static String getPropertyName(String attributeCui) {
    return (String)SAMPLE_ATTRIBUTES_TO_PROPERTY_NAME.get(attributeCui);
  }
  
  private final static Map VOLUME_UNIT_CONVERSION_TO_UL = new HashMap();
  static {
    VOLUME_UNIT_CONVERSION_TO_UL.put(ArtsConstants.VOLUME_UNIT_ML, new BigDecimal("1000"));
    VOLUME_UNIT_CONVERSION_TO_UL.put(ArtsConstants.VOLUME_UNIT_UL, new BigDecimal("1"));
    VOLUME_UNIT_CONVERSION_TO_UL.put(ArtsConstants.VOLUME_UNIT_NL, new BigDecimal(".001"));
  }
  public static BigDecimal getVolumeInUlConversionFactor(String attributeCui) {
    return (BigDecimal) VOLUME_UNIT_CONVERSION_TO_UL.get(attributeCui);
}
  private final static Map WEIGHT_UNIT_CONVERSION_TO_MG = new HashMap();
  static {
  WEIGHT_UNIT_CONVERSION_TO_MG.put(ArtsConstants.WEIGHT_UNIT_G, new BigDecimal("1000"));
  WEIGHT_UNIT_CONVERSION_TO_MG.put(ArtsConstants.WEIGHT_UNIT_MG, new BigDecimal("1"));
  WEIGHT_UNIT_CONVERSION_TO_MG.put(ArtsConstants.WEIGHT_UNIT_UG, new BigDecimal("0.001"));
  WEIGHT_UNIT_CONVERSION_TO_MG.put(ArtsConstants.WEIGHT_UNIT_NG, new BigDecimal("0.000001"));
  }
  public static BigDecimal getWeightInMgConversionFactor(String attributeCui) {
      return (BigDecimal)WEIGHT_UNIT_CONVERSION_TO_MG.get(attributeCui);
  }
  //system configuration information related constants
  public final static String SYSTEM_CONFIGURATION_FILENAME = "systemConfiguration.xml";
  public final static String SYSTEM_CONFIGURATION_DTD = "systemConfigurationXML1.dtd";
  public final static String DEFAULT_RESULTS_VIEW_FILENAME = "defaultResultsView.xml";
  public final static String DEFAULT_RESULTS_LOCALVIEW_FILENAME = "defaultResultsViewLocal.xml";  
  public final static String DEFAULT_RESULTS_VIEW_ID = "defaultResultsView";
  
  //label printing configuration information related constants
  public final static String LABEL_PRINTING_CONFIGURATION_FILENAME = "labelPrintingConfiguration.xml";
  public final static String LABEL_PRINTING_CONFIGURATION_DTD = "labelPrintingConfigurationXML1.dtd";
  public final static String LABEL_PRINTING_OBJECT_TYPE_SAMPLE = "sample";
  public final static String LABEL_PRINTING_OBJECT_TYPE_DONOR = "donor";
  public final static String LABEL_PRINTING_OBJECT_TYPE_CASE = "case";
  public final static String LABEL_PRINTING_ACTION_PREPRINT = "preprint";
  public final static String LABEL_PRINTING_ACTION_REPRINT = "reprint";
  public final static String LABEL_PRINTING_FILE_DIRECTORY = "labelFiles";
  public final static String LABEL_PRINTING_TRANSFER_COMMAND_FILE_INSERTION_STRING = "{fileName}";
  public final static String LABEL_PRINTING_INSERTION_STRING_LABEL_TEMPLATE = "{labelTemplate}";
  public final static String LABEL_PRINTING_INSERTION_STRING_LABEL_PRINTER = "{labelPrinter}";
  public final static Map LABEL_PRINTING_ACTIONS = new HashMap();
  static {
    LABEL_PRINTING_ACTIONS.put(LABEL_PRINTING_ACTION_PREPRINT, LABEL_PRINTING_ACTION_PREPRINT);
    LABEL_PRINTING_ACTIONS.put(LABEL_PRINTING_ACTION_REPRINT, LABEL_PRINTING_ACTION_REPRINT);
  }
  public final static Map LABEL_PRINTING_OBJECT_TYPES = new TreeMap();
  static {
    LABEL_PRINTING_OBJECT_TYPES.put(LABEL_PRINTING_OBJECT_TYPE_SAMPLE, LABEL_PRINTING_OBJECT_TYPE_SAMPLE);
    LABEL_PRINTING_OBJECT_TYPES.put(LABEL_PRINTING_OBJECT_TYPE_DONOR, LABEL_PRINTING_OBJECT_TYPE_DONOR);
    LABEL_PRINTING_OBJECT_TYPES.put(LABEL_PRINTING_OBJECT_TYPE_CASE, LABEL_PRINTING_OBJECT_TYPE_CASE);
  }
}
