
package com.ardais.bigr.query.generator.gen;

import java.sql.Types;

import com.ardais.bigr.api.ApiFunctions;

/**
 * Holds metadata about a single filter or join, where a filter is defined as a 
 * fragment of a SQL WHERE clause. <code>FilterMetaData</code> can return a 
 * SQL WHERE clause fragment that is appropriate for inclusion in a generated 
 * query.  Also holds constants that can be used as keys into data structures
 * that contain <code>FilterMetaData</code> objects.
 * <p>
 * <b>Developer's Notes</b>
 * </p>
 * <p>
 * This is a generated file. DO NOT MANUALLY EDIT THIS FILE, AS YOUR CHANGES WILL
 * BE LOST THE NEXT TIME THIS FILE IS GENERATED.  To make changes, modify the 
 * xml file holding the Bigr Library information
 * </p>
 *
 * Generated using version 1.0 of the BigrLibraryMetaData xml file.
 * 
 * @see ColumnMetaData
 * @see ProductQueryMetaData        
 * @see TableMetaData
 * @see com.ardais.bigr.util.DbAliases
 */
public class FilterMetaData {

	public final static String KEY_SAMPLE_ACCOUNT = "samp305";
	public final static String KEY_SAMPLE_APPEARANCE_BEST = "samp218";
	public final static String KEY_SAMPLE_ID = "samp214";
	public final static String KEY_SAMPLE_CONSENT_ID = "samp215";
	public final static String KEY_SAMPLE_DONOR_ID = "samp221";
	public final static String KEY_SAMPLE_NOT_PULLED = "samp225";
	public final static String KEY_SAMPLE_QCVERIFIED = "samp220";
	public final static String KEY_SAMPLE_RESTRICTED = "samp300";
	public final static String KEY_SAMPLE_SALES_STATUS = "samp311";
	public final static String KEY_SAMPLE_UNRESTRICTED = "samp301";
	public final static String KEY_SAMPLE_QC_STATUS = "samp315";
	public final static String KEY_SAMPLE_QCPOSTED = "samp253";
	public final static String KEY_SAMPLE_NOT_QCPOSTED = "506";
	public final static String KEY_SAMPLE_PULLED = "samp254";
	public final static String KEY_SAMPLE_NOT_RELEASED = "504";
	public final static String KEY_SAMPLE_RELEASED = "505";
	public final static String KEY_SAMPLE_BMS_YN = "515";
	public final static String KEY_SAMPLE_BMS_YN_YES = "516";
	public final static String KEY_SAMPLE_SAMPLE_TYPE_CID = "517";
	public final static String KEY_TISSUE_ORIGIN = "330";
	public final static String KEY_TISSUE_ORIGIN_LIKE = "376";
	public final static String KEY_CONSENT_ID = "212";
	public final static String KEY_CONSENT_DX = "220";
	public final static String KEY_CONSENT_DX_LIKE = "221";
	public final static String KEY_CONSENT_LINKED = "223";
	public final static String KEY_CONSENT_DRE_CID = "353";
	public final static String KEY_DONOR_ID = "211";
	public final static String KEY_ASM_ID = "213";
	public final static String KEY_ASM_TISSUE = "391";
	public final static String KEY_ASM_TISSUE_CONTAINS = "390";
	public final static String KEY_PATH_CONSENT_ID = "232";
	public final static String KEY_PATH_DX = "230";
	public final static String KEY_PATH_DX_LIKE = "231";
	public final static String KEY_SECTION_HNG = "240";
	public final static String KEY_SECTION_LYMPH_STAGE = "241";
	public final static String KEY_SECTION_METASTASIS = "242";
	public final static String KEY_SECTION_STAGE_GROUPING = "243";
	public final static String KEY_SECTION_TUMOR_STAGE = "244";
	public final static String KEY_LIMS_PE_DX = "251";
	public final static String KEY_LIMS_PE_DX_LIKE = "252";
	public final static String KEY_LIMS_PE_REPORTED = "250";
	public final static String KEY_LIMS_PE_SAMPLE_ID = "253";
	public final static String KEY_LIMS_PE_TISSUE_FINDING = "260";
	public final static String KEY_LIMS_PE_TISSUE_ORIGIN = "265";
	public final static String KEY_LIMS_PE_TISSUE_ORIGIN_CONTAINS = "266";
	public final static String KEY_RNA_RNAVIALID = "270";
	public final static String KEY_RNA_STATUS = "271";
	public final static String KEY_RNA_PRODUCT_TYPE = "275";
	public final static String KEY_RNA_BMS_YN_YES = "277";
	public final static String KEY_PTS_SAMPLE_ID = "401";
	public final static String KEY_SHOPPING_CART_SAMPLE_ID = "410";
	public final static String KEY_SLIDE_SAMPLE_ID = "450";
	public final static String KEY_LINE_SAMPLE_ID = "420";
	public final static String KEY_ORDER_SHIPPED = "421";
	public final static String KEY_DONOR_GENDER = "280";
	public final static String KEY_RNA_LIST_VIALTOUSE = "431";
	public final static String KEY_HOLD_SOLD_USER = "341";
	public final static String KEY_HOLD_SOLD_ACCOUNT = "342";
	public final static String KEY_CART_USER_ID = "511";
	public final static String KEY_LOGICAL_REPOS_ITEM_ITEM_ID = "770";
	public final static String KEY_LOGICAL_REPOS_ITEM_REPOSITORY_ID = "771";
	public final static String KEY_STAFF_ID = "772";
	public final static String KEY_DIAGNOSTIC_TEST = "355";
	public final static String KEY_BEST_CASE_DX = "228";
	public final static String KEY_BEST_CASE_DX_LIKE = "229";
	public final static String KEY_TISSUE_FINDING = "332";
	public final static String KEY_TISSUE_FINDING_LIKE = "333";
	public final static String KEY_SAMPLE_ACCOUNT_NOT = "514";
	public final static String KEY_SAMPLE_APPEARANCE_BEST_NOT = "samp219";
	public final static String KEY_SAMPLE_INV_STATUS_NOT = "samp303";
	public final static String KEY_SAMPLE_SALES_STATUS_NOT = "samp312";
	public final static String KEY_TISSUE_ORIGIN_NOT = "331";
	public final static String KEY_TISSUE_ORIGIN_NOT_LIKE = "377";
	public final static String KEY_ASM_TISSUE_NOT = "294";
	public final static String KEY_ASM_TISSUE_NOT_CONTAINS = "394";
	public final static String KEY_PATH_DX_NOT = "235";
	public final static String KEY_LIMS_PE_TISSUE_FINDING_NOT = "262";
	public final static String KEY_LIMS_PE_TISSUE_ORIGIN_NOT = "267";
	public final static String KEY_LIMS_PE_TISSUE_ORIGIN_NOT_CONTAINS = "268";
	public final static String KEY_SAMPLE_NOT_PRJADDED = "500";
	public final static String KEY_TISSUE_FINDING_NOT = "334";
	public final static String KEY_TISSUE_FINDING_NOT_LIKE = "336";
	public final static String KEY_RNA_QUALITY_GREATER = "274";
	public final static String KEY_LIMS_PE_TISSUE_FINDING_CONTAINS = "261";
	public final static String KEY_DONOR_ALIAS_ID = "samp224";
	public final static String KEY_CONSENT_ALIAS_ID = "samp223";
	public final static String KEY_SAMPLE_ALIAS_ID = "samp222";
	public final static String KEY_LIMS_PE_TISSUE_FINDING_NOT_CONTAINS = "263";
	public final static String KEY_CONSENT_NOT_PULLED = "227";
	public final static String KEY_RNA_STOCK_ON_HOLD = "432";
	public final static String KEY_SAMPLE_NULL_PROJECT = "501";
	public final static String KEY_SAMPLE_QC_STATUS_IS_NULL = "519";
	public final static String KEY_SAMPLE_RECEIVED_AT_ARDAIS = "samp306";
	public final static String KEY_PATH_DX_EXISTS = "233";
	public final static String KEY_RNA_SAMPLE_CREATED = "430";
	public final static String KEY_SAMPLE_SALES_STATUS_EXISTS = "samp317";
	public final static String KEY_CONSENT_DRE_CID_EXISTS = "350";
	public final static String KEY_CONSENT_PSA_EXISTS = "352";
	public final static String KEY_PATH_ASCII_REPORT_CONTAINS = "236";
	public final static String KEY_LIMS_PE_EXTERNAL_COMMENTS_CONTAINS = "269";
	public final static String KEY_PATH_NOTE = "507";
	public final static String KEY_DONOR_NOTE = "508";
	public final static String KEY_SECTION_NOTE = "509";
	public final static String KEY_AGE_AT_COLLECTION = "224";
	public final static String KEY_LIMS_PE_LSN = "255";
	public final static String KEY_LIMS_PE_NEC = "259";
	public final static String KEY_LIMS_PE_NRM = "254";
	public final static String KEY_LIMS_PE_TAS = "258";
	public final static String KEY_LIMS_PE_TCS = "257";
	public final static String KEY_LIMS_PE_TMR = "256";
	public final static String KEY_RNA_PE_LSN = "320";
	public final static String KEY_RNA_PE_NEC = "321";
	public final static String KEY_RNA_PE_NRM = "322";
	public final static String KEY_RNA_PE_TMR = "323";
	public final static String KEY_RNA_PE_TAS = "324";
	public final static String KEY_RNA_PE_TCS = "325";
	public final static String KEY_CONSENT_PSA = "354";
	public final static String KEY_SAMPLE_DATE_RECEIVED = "samp316";
	public final static String KEY_SAMPLE_DATE_COLLECTED = "samp318";
	public final static String KEY_JOIN_CART_RNA_BATCH = "510";
	public final static String KEY_JOIN_CONSENT_CONSENT_REVOKED = "512";
	public final static String KEY_JOIN_SAMPLE_BOX_LOCATION = "513";
	public final static String KEY_JOIN_BOX_LOCATION_STAFF = "524";
	public final static String KEY_JOIN_ASM_ASM_FORM = "021";
	public final static String KEY_JOIN_SAMPLE_ASM = "001";
	public final static String KEY_JOIN_SAMPLE_CONSENT = "077";
	public final static String KEY_JOIN_SAMPLE_SAMPLE1 = "078";
	public final static String KEY_JOIN_ASM_CONSENT = "002";
	public final static String KEY_JOIN_SAMPLE_DONOR = "004";
	public final static String KEY_JOIN_LIMS_PE_SAMPLE = "003";
	public final static String KEY_JOIN_LIMS_PE_SAMPLE_OUTER = "118";
	public final static String KEY_JOIN_PATH_SAMPLE = "011";
	public final static String KEY_JOIN_PATH_CONSENT = "005";
	public final static String KEY_JOIN_PATH_CONSENT_OUTER = "010";
	public final static String KEY_JOIN_RNA_POOL_SAMPLE = "008";
	public final static String KEY_JOIN_PATH_SECTION = "006";
	public final static String KEY_JOIN_IMAGES_SLIDE = "116";
	public final static String KEY_JOIN_RNA_GROSS_EXTRACTION = "119";
	public final static String KEY_JOIN_RNA_POOL_BATCH_DETAIL = "120";
	public final static String KEY_JOIN_RNA_BIOANALYSER = "122";
	public final static String KEY_JOIN_RNA_REP_SAMPLE = "030";
	public final static String KEY_JOIN_SAMPLE_TMA_BLOCK_SAMPLES_OUTER = "126";
	public final static String KEY_JOIN_SAMPLE_RNA_POOL_OUTER = "125";
	public final static String KEY_JOIN_RNA_POOL_BATCH_DETAIL_OUTER = "127";
	public final static String KEY_OUTER_JOIN_CONSENT_DONOR = "104";
	public final static String KEY_OUTER_JOIN_PATH_SECTION = "106";
	public final static String KEY_OUTER_JOIN_RNA_RNA_HOLD = "009";
	public final static String KEY_JOIN_PTS_SAMPLE_PROJECT = "017";
	public final static String KEY_JOIN_LINE_ORDER = "018";
	public final static String KEY_JOIN_RNA_PROJECT_LIST = "019";
	public final static String KEY_JOIN_ORDER_USER = "020";
	public final static String KEY_JOIN_SAMPLE1_ORDER = "024";
	public final static String KEY_JOIN_SAMPLE1_CART_DETAIL = "025";
	public final static String KEY_JOIN_CART_USER = "027";
	public final static String KEY_JOIN_LOGICAL_REPOS_ITEM_LOGICAL_REPOS = "028";
	public final static String KEY_JOIN_RNA_LOGICAL_REPOS_ITEM = "031";
	public final static String KEY_JOIN_SAMPLE_LOGICAL_REPOS_ITEM = "029";
	public final static String KEY_JOIN_PATH_DIAGNOSTICS = "351";
	public final static String KEY_IN_ARDAIS_REPOSITORY = "290";
	public final static String KEY_NO_EXISTING_SHOPPING_CART_ENTRY = "415";
	public final static String KEY_CONSENT_NOT_REVOKED = "226";
	public final static String KEY_LIMS_PE_DX_NOT = "299";
	public final static String KEY_RNA_RESTRICTED_DI = "273";
	public final static String KEY_RNA_NOT_RESTRICTED = "276";
	public final static String KEY_PATH_NOTES_CONTAINS = "234";
	public final static String KEY_RNA_AMOUNT_AVAILABLE = "272";
	public final static String KEY_LIMS_PE_REPORTED_OUTER = "250b";
	public final static String KEY_SAMPLE_IN_LOGICAL_REPOSITORY = "300";
	public final static String KEY_RNA_IN_LOGICAL_REPOSITORY = "301";
	public final static String KEY_RETURN_NO_RESULTS = "999";
	public final static String KEY_ONLY_LOCAL_SAMPLES = "302";
	public final static String KEY_DIAGNOSTIC_TEST_RESULT = "356";
	public final static String KEY_SAMPLE_NOT_IN_PROJECT = "samp304";
	public final static String KEY_SAMPLE_QCVERIFIED_NOT = "samp250";
	public final static String KEY_SAMPLE_QCVERIFIED_ONLY = "samp251";
	public final static String KEY_SAMPLE_QCRELEASED_ONLY = "samp252";
	public final static String KEY_SAMPLE_RESTRICTED_ACCOUNT = "samp302";
	public final static String KEY_SAMPLE_NOT_PULLED_OR_BMS_YN_YES = "520";
	public final static String KEY_SAMPLE_NOT_IN_PROJECT_OR_BMS_YN_YES = "522";
	public final static String KEY_CONSENT_NOT_REVOKED_OR_BMS_YN_YES = "523";
	public final static String KEY_CONSENT_NOT_PULLED_OR_BMS_YN_YES = "525";
	public final static String KEY_IN_ARDAIS_REPOSITORY_OR_BMS_YN_YES = "526";
	public final static String KEY_PATH_DX_EXISTS_OR_BMS_YN_YES = "529";
	public final static String KEY_SAMPLE_SALES_STATUS_NOT_NULL_AND_BMS_YN_YES = "531";
	public final static String KEY_SAMPLE_SALES_STATUS_OR_NOT_NULL_AND_BMS_YN_YES = "532";
	public final static String KEY_NO_EXISTING_SHOPPING_CART_ENTRY_OR_BMS_RNA_YN_YES = "533";

    /**
     * The data type of the input parameters.
     */
    private int _dataType = Types.VARCHAR;

    /**
     * The operator used to concatenate multiples of this filter.
     */
    private String _multiOp = "OR";

    /**
     * The number of input parameters for the filter.
     */
    private int _numberParameters = 0;

    /**
     * The number of input parameters for the prefix for a filter with repeat segment.
     */
    private int _numberParametersInPrefix = 0;

    /**
     * The number of input parameters for the repeat for a filter with repeat segment.
     */
    private int _numberParametersInRepeat = 0;

    /**
     * The number of input parameters for the suffix for a filter with repeat segment.
     */
    private int _numberParametersInSuffix = 0;

    /**
     * Holds the priority for the current hint.
     */
    private int _hintPriority = 0;
  
    /**
     * Holds the value for the highest priority hint.
     */
    private String _hintText = null;
    
    /**
     * The SQL WHERE fragment for the filter.
     */
    private String _whereFragment;

    /**
     * Some SQL to append after the filter, or after the "multi op" group
     * of filters.  For example, if we have a list of excluded values separated
     * by the _multiOp of AND, we may add 'AND value IS NOT NULL' once at the end.
     */
    private String _additionalClause;
    
    /**
     * Creates a new <code>FilterMetaData</code> from the given WHERE fragment.
     * The datatype for the filter is assumed to be <code>Types.VARCHAR</code>,
     * and the multiple operator is assumed to be <code>OR</code>.
     * 
     * @param  fragment  the database table's alias 
     */
    public FilterMetaData(String fragment) {
        this(fragment, Types.VARCHAR, "OR");
    }

    /**
     * Creates a new <code>FilterMetaData</code> from the given WHERE fragment
     * and datatype.  The multiple operator for the filter is assumed to be 
     * <code>OR</code>.
     * 
     * @param  fragment  the database table's alias 
     * @param  dataType  the data type.  Must be one of the constants specified
     * 										in <code>java.sql.Types</code>
     */
    public FilterMetaData(String fragment, int dataType) {
        this(fragment, dataType, "OR");
    }

    /**
     * Creates a new <code>FilterMetaData</code> from the given WHERE fragment,
     * datatype and multiple operator.
     * 
     * @param  fragment  the database table's alias 
     * @param  dataType  the data type.  Must be one of the constants specified
     * 										in <code>java.sql.Types</code>
     * @param  multiOperator  the multiple operator.  Must be one of 
     * 												 <code>"OR"</code> or <code>"AND"</code>.
     */
    public FilterMetaData(String fragment, int dataType, String multiOperator) {
        _whereFragment = fragment;
        _dataType = dataType;
        _multiOp = multiOperator;
        if (ApiFunctions.hasRepeatSegment(fragment)) {
          String[] segments = ApiFunctions.separateRepeatFragmentParts(fragment);
          _numberParametersInPrefix = ApiFunctions.occurrencesOf('?', segments[0]); 
          _numberParametersInRepeat = ApiFunctions.occurrencesOf('?', segments[1]); 
          _numberParametersInSuffix = ApiFunctions.occurrencesOf('?', segments[2]);  
        }
        else {
          _numberParameters = ApiFunctions.occurrencesOf('?', fragment);
        }
    }

    /**
     * Returns the data type of the parameters in this filter.
     * 
     * @return  The parameter's data type.  Will be one of the constants
     * 					 specified in <code>java.sql.Types</code>.
     */
    public int getDataType() {
        return _dataType;
    }

    /**
     * Returns the operator to use if multiple of this filter are used in a query.
     * 
     * @return  The operator. Will be either <code>"OR"</code> or 
     * 					 <code>"AND"</code>.
    
     */
    public String getMultipleFilterOperator() {
        return _multiOp;
    }

    /**
     * Returns the number of parameters in this filter.
     * 
     * @return  The number of parameters.
     */
    public int getNumberParameters() {
        return _numberParameters;
    }

    /**
     * Returns the WHERE fragment corresponding to this filter.
     * 
     * @return  The WHERE fragment.
     */
    public String getWhereFragment() {
        return _whereFragment;
    }
    /**
     * Returns the additionalClause.
     * @return String
     */
    public String getAdditionalClause() {
      return _additionalClause;
    }

    /**
     * Sets the additionalClause.
     * @param additionalClause The additionalClause to set
     */
    public void setAdditionalClause(String additionalClause) {
      _additionalClause = additionalClause;
    }

    /**
     * Returns hint priority
     * @return int
     */
    public int getHintPriority() {
      return _hintPriority;
    }

    /**
     * Returns the text to be used in query for hint
     * @return String
     */
    public String getHintText() {
      return _hintText;
    }

    /**
     * Sets the priority for the hint
     * @param i The priority valure for the hint.
     */
    public void setHintPriority(int i) {
      _hintPriority = i;
    }

    /**
     * Sets the text for the hint to be used in query
     * @param string The text value to set for the hint
     */
    public void setHintText(String string) {
      _hintText = string;
    }    
    
    /**
     * @return
     */
    public int getNumberParametersInPrefix() {
      return _numberParametersInPrefix;
    }

    /**
     * @return
     */
    public int getNumberParametersInRepeat() {
      return _numberParametersInRepeat;
    }

    /**
     * @return
     */
    public int getNumberParametersInSuffix() {
      return _numberParametersInSuffix;
    }

}