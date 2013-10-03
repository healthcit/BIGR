
 
package com.ardais.bigr.query.generator.gen;

/**
 * Holds metadata about a single column, where a column is defined as a 
 * fragment of a SQL SELECT clause. <code>ColumnMetaData</code> can return a 
 * SQL SELECT clause fragment that is appropriate for inclusion in a generated 
 * query.  Also holds constants that can be used as keys into data structures
 * that contain <code>ColumnMetaData</code> objects.
 * <p>
 * <p>
 * <b>Developer's Notes</b>
 * </p>
 * <p>
 * This is a generated file. DO NOT MANUALLY EDIT THIS FILE, AS YOUR CHANGES WILL
 * BE LOST THE NEXT TIME THIS FILE IS GENERATED.  To make changes, modify the 
 * xml file holding the Bigr Library information
 *
 * Generated using version 1.0 of the BigrLibraryMetaData xml file.
 *
 * 
 * @see FilterMetaData
 * @see ProductQueryMetaData
 * @see TableMetaData
 * @see com.ardais.bigr.util.DbAliases
 */
 
 public class ColumnMetaData {


  //------------------------------------------------------------
  // sample keys.
  //------------------------------------------------------------
    public final static String KEY_SAMPLE_ASM_ID = "0002";    
    public final static String KEY_SAMPLE_ASM_POSITION = "0006";    
    public final static String KEY_SAMPLE_ID = "0001";    
    public final static String KEY_SAMPLE_FORMAT_DETAIL = "0033";    
    public final static String KEY_SAMPLE_FIXATIVE_TYPE = "0034";    
    public final static String KEY_SAMPLE_QCVERIFIED = "0007";    
    public final static String KEY_SAMPLE_RESTRICTED = "0005";    
    public final static String KEY_SAMPLE_PULLED = "0008";    
    public final static String KEY_SAMPLE_PULL_DATE = "0010";    
    public final static String KEY_SAMPLE_PULLED_REASON = "0039";    
    public final static String KEY_SAMPLE_RELEASED = "0009";    
    public final static String KEY_SAMPLE_QCPOSTED = "0011";    
    public final static String KEY_SAMPLE_DISCORDANT = "0028";    
    public final static String KEY_SAMPLE_INV_STATUS = "0020";    
    public final static String KEY_SAMPLE_INV_STATUS_DATE = "0021";    
    public final static String KEY_SAMPLE_PROJECT_STATUS = "0022";    
    public final static String KEY_SAMPLE_PROJECT_STATUS_DATE = "0023";    
    public final static String KEY_SAMPLE_QCSTATUS = "0024";    
    public final static String KEY_SAMPLE_HISTO_NOTES = "0032";    
    public final static String KEY_SAMPLE_QCSTATUS_DATE = "0025";    
    public final static String KEY_SAMPLE_SALES_STATUS = "0026";    
    public final static String KEY_SAMPLE_SALES_STATUS_DATE = "0027";    
    public final static String KEY_SAMPLE_SECTION_COUNT = "0030";    
    public final static String KEY_SAMPLE_BOX_BARCODE_ID = "0037";    
    public final static String KEY_SAMPLE_SUBDIVISION_DATE = "0036";    
    public final static String KEY_SAMPLE_ASM_NOTE = "0038";    
    public final static String KEY_SAMPLE_BMS_YN = "0040";    
    public final static String KEY_SAMPLE_LOCATION = "0041";    
    public final static String KEY_SAMPLE_CONSENT_ID = "0003";    
    public final static String KEY_SAMPLE_ARDAIS_ID = "0004";    
    public final static String KEY_SAMPLE_TISSUE_FINDING_CUI = "0042";    
    public final static String KEY_SAMPLE_TISSUE_FINDING_OTHER = "0043";    
    public final static String KEY_SAMPLE_IMPORTED_YN = "0164";    
    public final static String KEY_SAMPLE_CUSTOMER_ID = "0169";    
    public final static String KEY_SAMPLE_SAMPLE_TYPE_CID = "0165";    
    public final static String KEY_SAMPLE_AGE_AT_COLLECTION = "0166";    
    public final static String KEY_SAMPLE_APPEARANCE_BEST = "0777";    
    public final static String KEY_SAMPLE_DIAGNOSIS_CUI_BEST = "0778";    
    public final static String KEY_SAMPLE_DIAGNOSIS_OTHER_BEST = "0779";    
    public final static String KEY_SAMPLE_TISSUE_ORIGIN_CUI = "0776";    
    public final static String KEY_SAMPLE_TISSUE_ORIGIN_OTHER = "0775";    
    public final static String KEY_SAMPLE_WEIGHT = "0845";    
    public final static String KEY_SAMPLE_WEIGHT_UNIT_CUI = "0846";    
    public final static String KEY_SAMPLE_WEIGHT_MG = "0847";    
    public final static String KEY_SAMPLE_VOLUME = "0850";    
    public final static String KEY_SAMPLE_VOLUME_UNIT_CUI = "0849";    
    public final static String KEY_SAMPLE_VOLUME_UL = "0848";    
    public final static String KEY_SAMPLE_BUFFER_TYPE_CUI = "0851";    
    public final static String KEY_SAMPLE_BUFFER_TYPE_OTHER = "0852";    
    public final static String KEY_SAMPLE_TOTAL_NUM_OF_CELLS = "0853";    
    public final static String KEY_SAMPLE_TOTAL_NUM_OF_CELLS_EX_REP_CUI = "0854";    
    public final static String KEY_SAMPLE_CELLS_PER_ML = "0855";    
    public final static String KEY_SAMPLE_PERCENT_VIABILITY = "0856";    
    public final static String KEY_SAMPLE_COLLECTION_DATETIME = "0857";    
    public final static String KEY_SAMPLE_COLLECTION_DATETIME_DPC = "0858";    
    public final static String KEY_SAMPLE_PRESERVATION_DATETIME = "0859";    
    public final static String KEY_SAMPLE_PRESERVE_DATETIME_DPC = "0860";    
    public final static String KEY_SAMPLE_CONCENTRATION = "0861";    
    public final static String KEY_SAMPLE_YIELD = "0862";    
    public final static String KEY_SAMPLE_SOURCE = "0863";    

  //------------------------------------------------------------
  // tma_block_samples keys.
  //------------------------------------------------------------
    public final static String KEY_TMA_BLOCKSETID = "0035";    

  //------------------------------------------------------------
  // asm keys.
  //------------------------------------------------------------
    public final static String KEY_ASM_APPEARANCE = "0102";    
    public final static String KEY_ASM_CONSENT_ID = "0101";    
    public final static String KEY_ASM_ID = "0100";    
    public final static String KEY_ASM_TISSUE = "0103";    
    public final static String KEY_ASM_TISSUE_OTHER = "0104";    
    public final static String KEY_ASM_MODULE_COMMENTS = "0105";    
    public final static String KEY_ASM_DONOR_ID = "0106";    

  //------------------------------------------------------------
  // asm_form keys.
  //------------------------------------------------------------
    public final static String KEY_ASM_FORM_PROCEDURE = "0107";    
    public final static String KEY_ASM_FORM_PROCEDURE_OTHER = "0108";    
    public final static String KEY_ASM_FORM_PREPARED_BY = "0109";    

  //------------------------------------------------------------
  // consent keys.
  //------------------------------------------------------------
    public final static String KEY_CONSENT_ID = "0150";    
    public final static String KEY_CONSENT_DX = "0152";    
    public final static String KEY_CONSENT_DX_OTHER = "0153";    
    public final static String KEY_DONOR_ID = "0151";    
    public final static String KEY_CONSENT_PULL_DT = "0156";    
    public final static String KEY_CONSENT_LOCATION = "0157";    
    public final static String KEY_CONSENT_BEST_DIAGNOSIS_CUI = "0158";    
    public final static String KEY_CONSENT_BEST_DIAGNOSIS_OTHER = "0159";    
    public final static String KEY_CONSENT_ARDAIS_ACCT_KEY = "0160";    
    public final static String KEY_CONSENT_IMPORTED_YN = "0161";    
    public final static String KEY_CONSENT_CUSTOMER_ID = "0162";    
    public final static String KEY_CONSENT_PSA = "0167";    
    public final static String KEY_CONSENT_DRE_CID = "0168";    

  //------------------------------------------------------------
  // donor keys.
  //------------------------------------------------------------
    public final static String KEY_DONOR_GENDER = "0170";    
    public final static String KEY_DONOR_PROFILE_NOTES = "0172";    
    public final static String KEY_DONOR_ARDAIS_ACCT_KEY = "0173";    
    public final static String KEY_DONOR_IMPORTED_YN = "0174";    
    public final static String KEY_DONOR_CUSTOMER_ID = "0175";    
    public final static String KEY_DONOR_RACE = "0176";    

  //------------------------------------------------------------
  // path keys.
  //------------------------------------------------------------
    public final static String KEY_PATH_CONSENT_ID = "0201";    
    public final static String KEY_PATH_ID = "0200";    
    public final static String KEY_PATH_DISEASE = "0202";    

  //------------------------------------------------------------
  // path_section keys.
  //------------------------------------------------------------
    public final static String KEY_SECTION_DX = "0221";    
    public final static String KEY_SECTION_DX_OTHER = "0222";    
    public final static String KEY_SECTION_HNG = "0233";    
    public final static String KEY_SECTION_ID = "0220";    
    public final static String KEY_SECTION_GLEASON_PRIMARY = "0230";    
    public final static String KEY_SECTION_GLEASON_SECONDARY = "0231";    
    public final static String KEY_SECTION_GLEASON_TOTAL = "0232";    
    public final static String KEY_SECTION_LYMPH_STAGE = "0234";    
    public final static String KEY_SECTION_METASTASIS = "0235";    
    public final static String KEY_SECTION_STAGE_GROUPING = "0236";    
    public final static String KEY_SECTION_TISSUE_FINDING = "0223";    
    public final static String KEY_SECTION_TISSUE_FINDING_OTHER = "0224";    
    public final static String KEY_SECTION_TISSUE_ORIGIN = "0225";    
    public final static String KEY_SECTION_TISSUE_ORIGIN_OTHER = "0226";    
    public final static String KEY_SECTION_TUMOR_STAGE = "0237";    
    public final static String KEY_SECTION_TUMOR_STAGE_TYPE = "0238";    
    public final static String KEY_SECTION_TUMOR_SIZE = "0240";    
    public final static String KEY_SECTION_TUMOR_WEIGHT = "0241";    

  //------------------------------------------------------------
  // lims_pe keys.
  //------------------------------------------------------------
    public final static String KEY_PE_ID = "0321";    
    public final static String KEY_LIMS_PE_APPEARANCE = "0304";    
    public final static String KEY_LIMS_PE_DX = "0301";    
    public final static String KEY_LIMS_PE_OTHER_DIAGNOSIS = "0322";    
    public final static String KEY_LIMS_PE_LSN = "0312";    
    public final static String KEY_LIMS_PE_NEC = "0316";    
    public final static String KEY_LIMS_PE_NRM = "0311";    
    public final static String KEY_LIMS_PE_EXTERNAL_COMMENTS = "0305";    
    public final static String KEY_LIMS_PE_INTERNAL_COMMENTS = "0306";    
    public final static String KEY_LIMS_PE_TAS = "0315";    
    public final static String KEY_LIMS_PE_TCS = "0314";    
    public final static String KEY_LIMS_PE_TISSUE_FINDING = "0302";    
    public final static String KEY_LIMS_PE_OTHER_TISSUE_FINDING = "0323";    
    public final static String KEY_LIMS_PE_TISSUE_ORIGIN = "0303";    
    public final static String KEY_LIMS_PE_OTHER_TISSUE_ORIGIN = "0324";    
    public final static String KEY_LIMS_PE_TMR = "0313";    
    public final static String KEY_LIMS_PE_SAMPLE_ID = "0300";    
    public final static String KEY_LIMS_PE_CREATE_USER = "0317";    
    public final static String KEY_LIMS_PE_SLIDE_ID = "0318";    
    public final static String KEY_LIMS_PE_CREATE_DATE = "0319";    
    public final static String KEY_LIMS_PE_REPORTED_DATE = "0320";    

  //------------------------------------------------------------
  // rna_pool_tissue keys.
  //------------------------------------------------------------
    public final static String KEY_RNA_POOL_SAMPLE_ID = "0420";    

  //------------------------------------------------------------
  // rna_batch_detail keys.
  //------------------------------------------------------------
    public final static String KEY_RNA_CONSENT_ID = "0400";    
    public final static String KEY_RNA_PREP = "0401";    
    public final static String KEY_RNA_RNAVIALID = "0402";    
    public final static String KEY_RNA_SAMPLE_ID = "0403";    
    public final static String KEY_RNA_REMAINING_VOLUME = "0406";    
    public final static String KEY_RNA_STATUS = "0404";    
    public final static String KEY_RNA_CONCENTRATION = "0407";    
    public final static String KEY_RNA_POOLED_TISSUE = "0408";    
    public final static String KEY_RNA_REP_SAMPLE = "0410";    
    public final static String KEY_RNA_CASE_EXHAUSTED = "0409";    
    public final static String KEY_RNA_PE_TAS = "0455";    
    public final static String KEY_RNA_PE_TCS = "0454";    
    public final static String KEY_RNA_PE_LSN = "0452";    
    public final static String KEY_RNA_PE_NEC = "0456";    
    public final static String KEY_RNA_PE_NRM = "0453";    
    public final static String KEY_RNA_PE_TMR = "0451";    
    public final static String KEY_RNA_EXTERNAL_COMMENTS = "0457";    
    public final static String KEY_RNA_INTERNAL_COMMENTS = "0458";    
    public final static String KEY_RNA_BMS_YN = "0459";    
    public final static String KEY_RNA_NOTES = "0460";    

  //------------------------------------------------------------
  // rna_gross_extraction keys.
  //------------------------------------------------------------
    public final static String KEY_RNA_QUALITY = "0440";    

  //------------------------------------------------------------
  // rna_bioanalyser keys.
  //------------------------------------------------------------
    public final static String KEY_RNA_BIORATIO = "0445";    

  //------------------------------------------------------------
  // rna_hold_amount keys.
  //------------------------------------------------------------
    public final static String KEY_RNA_HOLD_AMOUNT = "0405";    

  //------------------------------------------------------------
  // ads_imaget_syn keys.
  //------------------------------------------------------------
    public final static String KEY_IMAGES_IMAGE_ID = "0600";    
    public final static String KEY_IMAGES_IMAGE_FILE_NAME = "0601";    
    public final static String KEY_IMAGES_SLIDE_ID = "0602";    
    public final static String KEY_IMAGES_USERNAME = "0603";    
    public final static String KEY_IMAGES_IMAGE_TYPE = "0604";    
    public final static String KEY_IMAGES_MAGNIFICATION = "0605";    
    public final static String KEY_IMAGES_BEST_IMAGE = "0606";    
    public final static String KEY_IMAGES_CAPTURE_DATE = "0607";    
    public final static String KEY_IMAGES_IMAGE_NOTES = "0608";    
    public final static String KEY_IMAGES_IMAGE_PATH = "0609";    
    public final static String KEY_IMAGES_THUMBNAIL_PATH = "0610";    
    public final static String KEY_IMAGES_OVERLAY = "0611";    
    public final static String KEY_IMAGES_IMAGE_ON_REPORT = "0612";    
    public final static String KEY_IMAGES_SEARCH_QRY = "0613";    
    public final static String KEY_IMAGES_PATHOLOGIST_ID = "0614";    

  //------------------------------------------------------------
  // slide keys.
  //------------------------------------------------------------
    public final static String KEY_CREATE_DATE = "0503";    
    public final static String KEY_SLIDE_ID = "0500";    
    public final static String KEY_DESTROY_DATE = "0504";    
    public final static String KEY_SLIDE_SECTION_NUMBER = "0505";    
    public final static String KEY_SLIDE_SECTION_LEVEL = "0506";    
    public final static String KEY_SLIDE_CURRENT_LOCATION = "0507";    
    public final static String KEY_SLIDE_SAMPLE_ID = "0501";    
    public final static String KEY_CREATE_USER = "0502";    
    public final static String KEY_SLIDE_SECTION_PROCEDURE = "0508";    

  //------------------------------------------------------------
  // rna_images keys.
  //------------------------------------------------------------
    public final static String KEY_RNA_IMAGES_IMAGE_FILE_NAME = "0430";    
    public final static String KEY_RNA_IMAGES_IMAGE_TYPE = "0431";    

  //------------------------------------------------------------
  // pts_project keys.
  //------------------------------------------------------------
    public final static String KEY_PROJECT_ID = "0700";    
    public final static String KEY_PROJECT_NAME = "0701";    
    public final static String KEY_PROJECT_DATE_REQUESTED = "0702";    

  //------------------------------------------------------------
  // pts_sample keys.
  //------------------------------------------------------------
    public final static String KEY_PTS_SAMPLE_ID = "0711";    

  //------------------------------------------------------------
  // shopping_cart_detail keys.
  //------------------------------------------------------------
    public final static String KEY_SHOPPING_CART_USER_ID = "0802";    
    public final static String KEY_SHOPPING_CART_CREATION_DATE = "0803";    
    public final static String KEY_SHOPPING_CART_AMOUNT = "0804";    
    public final static String KEY_SHOPPING_CART_SAMPLE_ID = "0801";    

  //------------------------------------------------------------
  // es_ardais_order keys.
  //------------------------------------------------------------
    public final static String KEY_ORDER_NUMBER = "0720";    
    public final static String KEY_ORDER_DESCRIPTION = "0721";    

  //------------------------------------------------------------
  // es_order_line keys.
  //------------------------------------------------------------
    public final static String KEY_LINE_SAMPLE_ID = "0730";    

  //------------------------------------------------------------
  // rna_project keys.
  //------------------------------------------------------------
    public final static String KEY_RNA_PROJECT = "0740";    
    public final static String KEY_RNA_REQUEST_ID = "0741";    

  //------------------------------------------------------------
  // rna_rna_list keys.
  //------------------------------------------------------------
    public final static String KEY_RNA_LIST_VIALTOUSE = "751";    
    public final static String KEY_RNA_LIST_REQUEST_ID = "750";    
    public final static String KEY_RNA_LIST_VIALTOSEND = "752";    

  //------------------------------------------------------------
  // ard_logical_repos keys.
  //------------------------------------------------------------
    public final static String KEY_LOGICAL_REPOS_ID = "0770";    
    public final static String KEY_LOGICAL_REPOS_FULL_NAME = "0771";    
    public final static String KEY_LOGICAL_REPOS_SHORT_NAME = "0772";    
    public final static String KEY_LOGICAL_REPOS_BMS_YN = "0773";    

  //------------------------------------------------------------
  // ard_logical_repos_item keys.
  //------------------------------------------------------------
    public final static String KEY_LOGICAL_REPOS_ITEM_ID = "0780";    
    public final static String KEY_LOGICAL_REPOS_ITEM_REPOSITORY_ID = "0781";    
    public final static String KEY_LOGICAL_REPOS_ITEM_ITEM_ID = "0782";    
    public final static String KEY_LOGICAL_REPOS_ITEM_ITEM_TYPE = "0783";    

  //------------------------------------------------------------
  // ard_logical_repos_user keys.
  //------------------------------------------------------------
    public final static String KEY_LOGICAL_REPOS_USER_ID = "0790";    
    public final static String KEY_LOGICAL_REPOS_USER_REPOSITORY_ID = "0791";    
    public final static String KEY_LOGICAL_REPOS_USER_USER_ID = "0792";    

  //------------------------------------------------------------
  // box_location keys.
  //------------------------------------------------------------
    public final static String KEY_BOX_LOCATION_ADDRESS_ID = "800";    
    public final static String KEY_BOX_LOCATION_UNIT_NAME = "801";    
    public final static String KEY_BOX_LOCATION_STORAGE_TYPE_CID = "802";    
    public final static String KEY_BOX_LOCATION_ROOM_ID = "803"; 
    public final static String KEY_BOX_LOCATION_DRAWER_ID = "804"; 
    public final static String KEY_BOX_LOCATION_SLOT_ID = "805"; 
    public final static String KEY_BOX_LOCATION_BOX_ID = "806"; 
  //------------------------------------------------------------
  // staff keys.
  //------------------------------------------------------------
    public final static String KEY_STAFF_LOCATION_ADDRESS_ID = "810";    
    public final static String KEY_ARDAIS_STAFF_STAFF_ID = "811";    

  //------------------------------------------------------------
  // path_diagnostics keys.
  //------------------------------------------------------------
    public final static String KEY_DIAGNOSTICS_CONCEPT_ID = "0242";    
    public final static String KEY_DIAGNOSTIC_RESULTS_CID = "0243";    

  //------------------------------------------------------------
  // policy keys.
  //------------------------------------------------------------
    public final static String KEY_POLICY_ID = "0900";    
    public final static String KEY_POLICY_NAME = "0901";    
    public final static String KEY_POLICY_ALLOCATION_DENOMINATOR = "0902";    
    public final static String KEY_POLICY_ALLOCATION_NUMERATOR = "0903";    
    public final static String KEY_POLICY_ALLOCATION_FORMAT_CID = "0904";    
    public final static String KEY_POLICY_DEFAULT_LOGICAL_REPOS_ID = "0905";    
    public final static String KEY_POLICY_RESTRICTED_LOGICAL_REPOS_ID = "0906";    
    public final static String KEY_POLICY_CONSENT_VERIFY_REQUIRED = "0908";    
    public final static String KEY_POLICY_CASE_RELEASE_REQUIRED = "0909";    
    public final static String KEY_POLICY_ADDITIONAL_QUESTIONS_GRP = "0910";    
    public final static String KEY_POLICY_ARDAIS_ACCT_KEY = "0911";    
    public final static String KEY_POLICY_ALLOW_FOR_UNLINKED_YN = "0912";    
    public final static String KEY_POLICY_POLICY_DATA_ENCODING = "0913";    
    public final static String KEY_POLICY_POLICY_DATA = "0914";    
    public final static String KEY_POLICY_CASE_REGISTRATION_FORM = "0915";    

  //------------------------------------------------------------
  // irb keys.
  //------------------------------------------------------------
    public final static String KEY_IRB_ARDAIS_ACCT_KEY = "0970";    
    public final static String KEY_IRB_PROTOCOL_ID = "0971";    
    public final static String KEY_IRB_PROTOCOL = "0972";    
    public final static String KEY_IRB_POLICY_ID = "0973";    

  //------------------------------------------------------------
  // ly keys.
  //------------------------------------------------------------
    public final static String KEY_LY_BOX_LAYOUT_ID = "0980";    
    public final static String KEY_LY_NUMBER_OF_COLUMNS = "0981";    
    public final static String KEY_LY_NUMBER_OF_ROWS = "0982";    
    public final static String KEY_LY_X_AXIS_LABEL_CID = "0983";    
    public final static String KEY_LY_Y_AXIS_LABEL_CID = "0984";    
    public final static String KEY_LY_TAB_INDEX_CID = "0985";    

  //------------------------------------------------------------
  // acct_ly keys.
  //------------------------------------------------------------
    public final static String KEY_ACCT_LY_ID = "0990";    
    public final static String KEY_ACCT_LY_BOX_LAYOUT_NAME = "0991";    
    public final static String KEY_ACCT_LY_ARDAIS_ACCT_KEY = "0992";    
    public final static String KEY_ACCT_LY_BOX_LAYOUT_ID = "0993";    

  //------------------------------------------------------------
  // account keys.
  //------------------------------------------------------------
    public final static String KEY_ACCOUNT_KEY = "1010";    
    public final static String KEY_ACCOUNT_TYPE = "1011";    
    public final static String KEY_ACCOUNT_STATUS = "1012";    
    public final static String KEY_ACCOUNT_NAME = "1013";    
    public final static String KEY_ACCOUNT_PARENT_NAME = "1014";    
    public final static String KEY_ACCOUNT_OPEN_DATE = "1015";    
    public final static String KEY_ACCOUNT_ACTIVE_DATE = "1016";    
    public final static String KEY_ACCOUNT_CLOSE_DATE = "1017";    
    public final static String KEY_ACCOUNT_DEACTIVATE_DATE = "1018";    
    public final static String KEY_ACCOUNT_DEACTIVATE_REASON = "1019";    
    public final static String KEY_ACCOUNT_REACTIVATE_DATE = "1020";    
    public final static String KEY_ACCOUNT_PRIMARY_LOCATION = "1021";    
    public final static String KEY_ACCOUNT_BRAND_ID = "1022";    
    public final static String KEY_ACCOUNT_REQ_MGR_EMAIL = "1023";    
    public final static String KEY_ACCOUNT_LINKED_CASES_ONLY_YN = "1024";    
    public final static String KEY_ACCOUNT_PASSWORD_POLICY_CID = "1025";    
    public final static String KEY_ACCOUNT_PASSWORD_LIFE_SPAN = "1026";    
    public final static String KEY_ACCOUNT_DONOR_REGISTRATION_FORM = "1028";    
    public final static String KEY_ACCOUNT_DEFAULT_BOX_LAYOUT_ID = "1027";    

  //------------------------------------------------------------
  // isp keys.
  //------------------------------------------------------------
    public final static String KEY_SHIPPING_PARTNER_ID = "1030";    
    public final static String KEY_SHIPPING_PARTNER_ARDAIS_ACCT_KEY = "1031";    
    public final static String KEY_SHIPPING_PARTNER_DESTINATION_ID = "1032";    

  //------------------------------------------------------------
  // igl keys.
  //------------------------------------------------------------
    public final static String KEY_LOCATION_ADDRESS_ID = "1040";    
    public final static String KEY_LOCATION_NAME = "1041";    
    public final static String KEY_LOCATION_ADDRESS_1 = "1042";    
    public final static String KEY_LOCATION_ADDRESS_2 = "1043";    
    public final static String KEY_LOCATION_CITY = "1044";    
    public final static String KEY_LOCATION_STATE = "1045";    
    public final static String KEY_LOCATION_ZIP = "1046";    
    public final static String KEY_LOCATION_COUNTRY = "1047";    
    public final static String KEY_LOCATION_PHONE = "1048";    

  //------------------------------------------------------------
  // sample_box keys.
  //------------------------------------------------------------
    public final static String KEY_SAMPLE_BOX_BOX_BARCODE_ID = "1050";    
    public final static String KEY_SAMPLE_BOX_MANIFEST_NUMBER = "1051";    
    public final static String KEY_SAMPLE_BOX_CHECK_IN_DATE = "1053";    
    public final static String KEY_SAMPLE_BOX_CHECK_OUT_DATE = "1054";    
    public final static String KEY_SAMPLE_BOX_CHECK_OUT_REASON = "1055";    
    public final static String KEY_SAMPLE_BOX_CHECK_OUT_STAFF_ID = "1056";    
    public final static String KEY_SAMPLE_BOX_STATUS = "1057";    
    public final static String KEY_SAMPLE_BOX_NOTE = "1058";    
    public final static String KEY_SAMPLE_BOX_MANIFEST_ORDER = "1059";    
    public final static String KEY_SAMPLE_BOX_LAYOUT_ID = "1060";    
    public final static String KEY_SAMPLE_BOX_STORAGE_TYPE_CID = "1061";    

  //------------------------------------------------------------
  // bigr_role keys.
  //------------------------------------------------------------
    public final static String KEY_BIGR_ROLE_ID = "1062";    
    public final static String KEY_BIGR_ROLE_NAME = "1063";    
    public final static String KEY_BIGR_ROLE_ACCOUNT_ID = "1064";    

  //------------------------------------------------------------
  // bigr_role_privilege keys.
  //------------------------------------------------------------
    public final static String KEY_BIGR_ROLE_PRIVILEGE_ID = "1065";    
    public final static String KEY_BIGR_ROLE_PRIVILEGE_ROLE_ID = "1066";    
    public final static String KEY_BIGR_ROLE_PRIVILEGE_PRIVILEGE_ID = "1067";    

  //------------------------------------------------------------
  // bigr_role_user keys.
  //------------------------------------------------------------
    public final static String KEY_BIGR_ROLE_USER_ID = "1068";    
    public final static String KEY_BIGR_ROLE_USER_ROLE_ID = "1069";    
    public final static String KEY_BIGR_ROLE_USER_USER_ID = "1070";    


  //------------------------------------------------------------
  // consent keys.
  //------------------------------------------------------------
    public final static String KEY_DONOR_CONSENT_COUNT = "0171";    
  /**
  	 * The table alias.
  	 */
  private String _tableAlias;

  /**
   * The actual column name.
   */
  private String _column;

  /**
   * The column alias.  This may be the same as the actual column name.
   */
  private String _columnAlias;

  /**
   * The SQL SELECT fragment for this column.
   */
  private String _selectFragment;

  /**
   * Creates a new <code>ColumnMetaData</code> from the given table alias,
   * column name and column alias.
   * 
   * @param  tableAlias  the database table's alias 
   * @param  column  the actual column name
   * @param  columnAlias  the column's alias
   */
  public ColumnMetaData(String tableAlias, String column, String columnAlias) {
    _tableAlias = tableAlias;
    _column = column;
    _columnAlias = columnAlias;
    _selectFragment =
      (_column.equals(columnAlias))
        ? tableAlias + "." + column
        : tableAlias + "." + column + " AS \"" + columnAlias + "\"";
  }

  /**
   * Creates a new <code>ColumnMetaData</code> from the given table alias and
   * column name.  The column will not be aliased.
   * 
   * @param  tableAlias  the database table's alias 
   * @param  column  the actual column name
   */
  ColumnMetaData(String tableAlias, String column) {
    this(tableAlias, column, column);
  }

  /**
   * Returns the table's alias.
   * 
   * @return  The table's alias.
   */
  public String getTableAlias() {
    return _tableAlias;
  }

  /**
   * Returns the column's name.
   * 
   * @return  The column's name.
   */
  public String getColumn() {
    return _column;
  }

  /**
   * Returns the column's alias.
   * 
   * @return  The column's alias.
   */
  public String getColumnAlias() {
    return _columnAlias;
  }

  /**
   * Returns the SELECT fragment.
   * 
   * @return  The SELECT fragment.
   */
  public String getSelectFragment() {
    return _selectFragment;
  }

  /**
   * Sets the selectFragment.
   * @param selectFragment The selectFragment to set
   */
  protected void setSelectFragment(String selectFragment) {
    _selectFragment = selectFragment;
  }

}
 