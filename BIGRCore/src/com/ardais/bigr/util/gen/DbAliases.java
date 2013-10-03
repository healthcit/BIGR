
package com.ardais.bigr.util.gen;

/**
 * Contains static constants that contain aliases used for database columns
 * and tables.
 * <p>
 * <b>Developer's Notes</b>
 * </p>
 * <p>
 * This is a generated file. DO NOT MANUALLY EDIT THIS FILE, AS YOUR CHANGES WILL
 * BE LOST THE NEXT TIME THIS FILE IS GENERATED.  To make changes, modify the 
 * xml file holding the Bigr Library information
 *
 * Generated using version 1.0 of the BigrLibraryMetaData xml file.
 * </p>
 */
 
public class DbAliases {

    private DbAliases() {
    }

    //************************************************************
    //  Table aliases.
    //************************************************************
    public final static String TABLE_ASM = "asm";
    public final static String TABLE_ASM_FORM = "asm_form";
    public final static String TABLE_ATTACHEMTNS ="attachments";
    public final static String TABLE_BOX_LOCATION = "box_location";
    public final static String TABLE_ARDAIS_STAFF = "staff";
    public final static String TABLE_CONSENT = "consent";
    public final static String TABLE_CONSENT_REVOKED = "revoked_consent";
    public final static String TABLE_SAMPLE = "sample";
    public final static String TABLE_SAMPLE1 = "sample1";
    public final static String TABLE_LIMS_PE = "lims_pe";
    public final static String TABLE_LIMS_INCIDENTS = "lims_incidents";
    public final static String TABLE_SLIDE = "slide";
    public final static String TABLE_DONOR = "donor";
    public final static String TABLE_PATH = "path";
    public final static String TABLE_SECTION = "path_section";
    public final static String TABLE_TMA_BLOCK_SAMPLES = "tma_block_samples";
    public final static String TABLE_RNA_BATCH_DETAIL = "rna_batch_detail";
    public final static String TABLE_RNA_GROSS_EXTRACTION = "rna_gross_extraction";
    public final static String TABLE_RNA_IMAGES = "rna_images";
    public final static String TABLE_RNA_LIST = "rna_rna_list";
    public final static String TABLE_RNA_POOL_TISSUE = "rna_pool_tissue";
    public final static String TABLE_RNA_HOLD_AMOUNT = "rna_hold_amount";
    public final static String TABLE_RNA_PROJECT = "rna_project";
    public final static String TABLE_RNA_BIOANALYSER = "rna_bioanalyser";
    public final static String TABLE_IMAGES = "ads_imaget_syn";
    public final static String TABLE_PTS_PROJECT = "pts_project";
    public final static String TABLE_PTS_SAMPLE = "pts_sample";
    public final static String TABLE_SHOPPING_CART_DETAIL = "shopping_cart_detail";
    public final static String TABLE_SHOPPING_CART = "shopping_cart";
    public final static String TABLE_ORDER = "es_ardais_order";
    public final static String TABLE_ORDER_LINE = "es_order_line";
    public final static String TABLE_ARDAIS_USER = "es_ardais_user";
    public final static String TABLE_LOGICAL_REPOS = "ard_logical_repos";
    public final static String TABLE_LOGICAL_REPOS_ITEM = "ard_logical_repos_item";
    public final static String TABLE_LOGICAL_REPOS_USER = "ard_logical_repos_user";
    public final static String TABLE_ILTDS_REQUEST = "iltds_request";
    public final static String TABLE_ILTDS_REQUEST_BOX = "iltds_request_box";
    public final static String TABLE_ILTDS_REQUEST_ITEM = "iltds_request_item";
    public final static String TABLE_ARD_URLS = "ard_urls";
    public final static String TABLE_PATH_REPORT_DIAGNOSTICS = "path_diagnostics";
    public final static String TABLE_ARD_POLICY = "policy";
    public final static String TABLE_IRB = "irb";
    public final static String TABLE_BOX_LAYOUT = "ly";
    public final static String TABLE_ACCOUNT_BOX_LAYOUT = "acct_ly";
    public final static String TABLE_ARDAIS_ACCOUNT = "account";
    public final static String TABLE_ARDAIS_ADDRESS = "address";
    public final static String TABLE_ILTDS_SHIPPING_PARTNERS = "isp";
    public final static String TABLE_ILTDS_GEOGRAPHY_LOCATION = "igl";
    public final static String TABLE_ILTDS_SAMPLE_BOX = "sample_box";
    public final static String TABLE_ES_ARDAIS_CONSENTVER = "consent_versions";
    public final static String TABLE_BIGR_ROLE = "bigr_role";
    public final static String TABLE_BIGR_ROLE_PRIVILEGE = "bigr_role_privilege";
    public final static String TABLE_BIGR_ROLE_USER = "bigr_role_user";

    //************************************************************
    //  Column aliases.
    //************************************************************

    //------------------------------------------------------------
    // iltds_asm columns
    //------------------------------------------------------------
    public final static String ASM_APPEARANCE = "gross_appearance";
    public final static String ASM_DONOR_ID = "asm_ardais_id";
    public final static String ASM_CONSENT_ID = "asm_consent_id";
    public final static String ASM_ID = "asm_id";
    public final static String ASM_FORM_ID = "asm_form_id";
    public final static String ASM_TISSUE = "asm_tissue";
    public final static String ASM_TISSUE_OTHER = "asm_tissue_other";
    public final static String ASM_MODULE_COMMENTS = "module_comments";

    //------------------------------------------------------------
    // iltds_asm_form columns
    //------------------------------------------------------------
    public final static String ASM_FORM_DONOR_ID = "asm_form_ardais_id";
    public final static String ASM_FORM_CONSENT_ID = "asm_form_consent_id";
    public final static String ASM_FORM_FORM_ID = "asm_form_form_id";
    public final static String ASM_FORM_PREPARED_BY = "asm_form_prepared_by";
    public final static String ASM_FORM_PROCEDURE = "asm_form_procedure";
    public final static String ASM_FORM_PROCEDURE_OTHER = "asm_form_procedure_other";
    
    //---------------------------------------------------------------------------
    //iltds_attachments columns
    //---------------------------------------------------------------------------
    public final static String ATTACHMENTS_ID ="attach_id";
    public final static String ATTACHMENTS_ADS_ACCT_KEY ="attach_ardais_acct_key";
    public final static String ATTACHMENTS_DONOR_ID ="attach_ardais_id";
    public final static String ATTACHMENTS_CONSENT_ID ="attach_consent_id";
    public final static String ATTACHMENTS_SAMPLE_BARCODE_ID ="attach_sample_barcode_id";
    public final static String ATTACHMENTS_ISPHI ="attach_is_phi_yn";
    public final static String ATTACHMENTS_COMMENTS ="attach_comments";
    public final static String ATTACHMENTS_NAME ="attach_name";
    public final static String ATTACHMENTS_ATTACHED ="attach_attachment";
    public final static String ATTACHMENTS_CREATED_BY ="attach_created_by";
    public final static String ATTACHMENTS_CREATED_DATE ="attach_created_date"; 
    

    //------------------------------------------------------------
    // iltds_box_location columns
    //------------------------------------------------------------
    public final static String BOX_LOCATION_BOX_BARCODE_ID = "box_location_box_barcode_id";
    public final static String BOX_LOCATION_ADDRESS_ID = "box_location_id";
    public final static String BOX_LOCATION_ROOM_ID = "box_location_room_id";
    public final static String BOX_LOCATION_DRAWER_ID = "box_location_drawer_id";
    public final static String BOX_LOCATION_SLOT_ID = "box_location_slot_id";
    public final static String BOX_LOCATION_UNIT_NAME = "box_location_unit_name";
    public final static String BOX_LOCATION_STORAGE_TYPE_CID = "box_location_storage_type_cid";

    //------------------------------------------------------------
    // iltds_ardais_staff columns
    //------------------------------------------------------------
    public final static String ARDAIS_STAFF_STAFF_ID = "staff_id";
    public final static String STAFF_LOCATION_ADDRESS_ID = "staff_location_id";
    public final static String ARDAIS_STAFF_STAFF_LNAME = "ardais_staff_staff_lname";
    public final static String ARDAIS_STAFF_STAFF_FNAME = "ardais_staff_staff_fname";
    public final static String ARDAIS_STAFF_ACCT_KEY = "ardais_staff_acct_key";
    public final static String ARDAIS_STAFF_USER_ID = "ardais_staff_user_id";

    //------------------------------------------------------------
    // iltds_informed_consent columns
    //------------------------------------------------------------
    public final static String CONSENT_ID = "consent_id";
    public final static String DONOR_CONSENT_COUNT = "consent_count";
    public final static String CONSENT_DX = "consent_dx";
    public final static String CONSENT_DX_OTHER = "consent_dx_other";
    public final static String CONSENT_LOCATION = "consent_location";
    public final static String CONSENT_PULL_DT = "consent_pull_datetime";
    public final static String DONOR_ID = "ardais_id";
    public final static String CONSENT_BEST_DIAGNOSIS_CUI = "best_diagnosis_cui";
    public final static String CONSENT_BEST_DIAGNOSIS_OTHER = "best_diagnosis_other";
    public final static String CONSENT_ARDAIS_ACCT_KEY = "consent_ardais_acct_key";
    public final static String CONSENT_IMPORTED_YN = "consent_imported_yn";
    public final static String CONSENT_CUSTOMER_ID = "consent_customer_id";
    public final static String CONSENT_PSA = "consent_psa";
    public final static String CONSENT_DRE_CID = "consent_dre_cid";
    public final static String CONSENT_CLINICAL_FINDING_NOTES = "consent_clinical_finding_notes";
    public final static String CONSENT_POLICY_ID = "consent_policy_id";
    public final static String CONSENT_REG_FORM = "consent_reg_form";

    //------------------------------------------------------------
    // iltds_revoked_consent_archive columns
    //------------------------------------------------------------
    public final static String REVOKED_CONSENT_ID = "revoked_consent_id";

    //------------------------------------------------------------
    // iltds_sample columns
    //------------------------------------------------------------
    public final static String SAMPLE_ACCOUNT = "sample_ardais_acct_key";
    public final static String SAMPLE_ASM_ID = "sample_asm_id";
    public final static String SAMPLE_ASM_POSITION = "asm_position";
    public final static String SAMPLE_DI_MAX_THICKNESS = "di_max_thickness_pfin_cid";
    public final static String SAMPLE_DI_MIN_THICKNESS = "di_min_thickness_pfin_cid";
    public final static String SAMPLE_DI_WIDTH = "di_width_across_pfin_cid";
    public final static String SAMPLE_DISCORDANT = "discordant_yn";
    public final static String SAMPLE_FORMAT_DETAIL = "format_detail_cid";
    public final static String SAMPLE_FIXATIVE_TYPE = "type_of_fixative";
    public final static String SAMPLE_HISTO_MAX_THICKNESS = "histo_max_thickness_pfin_cid";
    public final static String SAMPLE_HISTO_MIN_THICKNESS = "histo_min_thickness_pfin_cid";
    public final static String SAMPLE_HISTO_NOTES = "histo_notes";
    public final static String SAMPLE_HISTO_REEMBED_REASON = "histo_reembed_reason_cid";
    public final static String SAMPLE_HISTO_SUBDIVIDABLE = "histo_subdividable";
    public final static String SAMPLE_HISTO_WIDTH = "histo_width_across_pfin_cid";
    public final static String SAMPLE_ID = "sample_barcode_id";
    public final static String SAMPLE_INV_STATUS = "inv_status";
    public final static String SAMPLE_INV_STATUS_DATE = "inv_status_date";
    public final static String SAMPLE_LOCATION = "cell_ref_location";
    public final static String SAMPLE_OTHER_HISTO_REEMBED_REASON = "other_histo_reembed_reason";
    public final static String SAMPLE_PROJECT_STATUS = "project_status";
    public final static String SAMPLE_PROJECT_STATUS_DATE = "project_status_date";
    public final static String SAMPLE_PULLED_REASON = "pull_reason";
    public final static String SAMPLE_PULL_DATE = "pull_date";
    public final static String SAMPLE_QCSTATUS = "qc_status";
    public final static String SAMPLE_QCSTATUS_DATE = "qc_status_date";
    public final static String SAMPLE_QCVERIFIED = "qc_verified";
    public final static String SAMPLE_QCPOSTED = "qcposted_yn";
    public final static String SAMPLE_PULLED = "pull_yn";
    public final static String SAMPLE_RELEASED = "released_yn";
    public final static String SAMPLE_RECEIPT_DATE = "receipt_date";
    public final static String SAMPLE_RESTRICTED = "restricted";
    public final static String SAMPLE_SALES_STATUS = "sales_status";
    public final static String SAMPLE_SALES_STATUS_DATE = "sales_status_date";
    public final static String SAMPLE_PARENT_ID = "parent_barcode_id";
    public final static String SAMPLE_SECTION_COUNT = "section_count";
    public final static String SAMPLE_PARAFFIN_FEATURE_CID = "paraffin_feature_cid";
    public final static String SAMPLE_OTHER_PARAFFIN_FEATURE = "other_paraffin_feature";
    public final static String SAMPLE_SUBDIVISION_DATE = "subdivision_date";
    public final static String SAMPLE_BOX_BARCODE_ID = "box_barcode_id";
    public final static String SAMPLE_ASM_NOTE = "asm_note";
    public final static String SAMPLE_BMS_YN = "bms_yn";
    public final static String SAMPLE_CONSENT_ID = "sample_consent_id";
    public final static String SAMPLE_ARDAIS_ID = "sample_ardais_id";
    public final static String SAMPLE_TISSUE_FINDING_CUI = "tissue_finding_cui";
    public final static String SAMPLE_TISSUE_FINDING_OTHER = "tissue_finding_other";
    public final static String SAMPLE_IMPORTED_YN = "sample_imported_yn";
    public final static String SAMPLE_CUSTOMER_ID = "sample_customer_id";
    public final static String SAMPLE_SAMPLE_TYPE_CID = "sample_type_cid";
    public final static String SAMPLE_COLLECTION_DATETIME = "sample_collection_datetime";
    public final static String SAMPLE_COLLECTION_DATETIME_DPC = "sample_collection_datetime_dpc";
    public final static String SAMPLE_PRESERVATION_DATETIME = "sample_preservation_datetime";
    public final static String SAMPLE_PRESERVE_DATETIME_DPC = "sample_preserve_datetime_dpc";
    public final static String SAMPLE_VOLUME = "sample_volume";
    public final static String SAMPLE_VOLUME_UNIT_CUI = "sample_volume_unit_cui";
    public final static String SAMPLE_VOLUME_UL = "sample_volume_ul";
    public final static String SAMPLE_WEIGHT = "sample_weight";
    public final static String SAMPLE_WEIGHT_UNIT_CUI = "sample_weight_unit_cui";
    public final static String SAMPLE_WEIGHT_MG = "sample_weight_mg";
    public final static String SAMPLE_AGE_AT_COLLECTION = "sample_age_at_collection";
    public final static String SAMPLE_APPEARANCE_BEST = "sample_appearance_best";
    public final static String SAMPLE_DIAGNOSIS_CUI_BEST = "sample_diagnosis_cui_best";
    public final static String SAMPLE_DIAGNOSIS_OTHER_BEST = "sample_diagnosis_other_best";
    public final static String SAMPLE_TISSUE_ORIGIN_CUI = "tissue_origin_cui";
    public final static String SAMPLE_TISSUE_ORIGIN_OTHER = "tissue_origin_other";
    public final static String SAMPLE_BUFFER_TYPE_CUI = "sample_buffer_type_cui";
    public final static String SAMPLE_BUFFER_TYPE_OTHER = "sample_buffer_type_other";
    public final static String SAMPLE_TOTAL_NUM_OF_CELLS = "sample_total_num_of_cells";
    public final static String SAMPLE_TOTAL_NUM_OF_CELLS_EX_REP_CUI = "sample_tnoc_ex_rep_cui";
    public final static String SAMPLE_CELLS_PER_ML = "sample_cells_per_ml";
    public final static String SAMPLE_PERCENT_VIABILITY = "sample_percent_viability";
    public final static String SAMPLE_SOURCE = "sample_source";
    public final static String SAMPLE_CONCENTRATION = "sample_concentration";
    public final static String SAMPLE_YIELD = "sample_yield";
    public final static String SAMPLE_REG_FORM = "sample_reg_form";

    //------------------------------------------------------------
    // lims_pathology_evaluation columns
    //------------------------------------------------------------
    public final static String PE_ID = "pe_id";
    public final static String LIMS_PE_SLIDE_ID = "lims_pe_slide_id";
    public final static String LIMS_PE_SAMPLE_ID = "lims_pe_sample_id";
    public final static String LIMS_PE_APPEARANCE = "microscopic_appearance";
    public final static String LIMS_PE_REPORTED = "reported_yn";
    public final static String LIMS_PE_CREATE_TYPE = "create_type";
    public final static String LIMS_PE_TMR = "tumor_cells";
    public final static String LIMS_PE_NRM = "normal_cells";
    public final static String LIMS_PE_TAS = "hypoacellularstroma_cells";
    public final static String LIMS_PE_NEC = "necrosis_cells";
    public final static String LIMS_PE_LSN = "lesion_cells";
    public final static String LIMS_PE_DX = "diagnosis_concept_id";
    public final static String LIMS_PE_TISSUE_ORIGIN = "tissue_origin_concept_id";
    public final static String LIMS_PE_MIGRATED = "migrated_yn";
    public final static String LIMS_PE_TISSUE_FINDING = "tissue_finding_concept_id";
    public final static String LIMS_PE_OTHER_TISSUE_FINDING = "other_tissue_finding_name";
    public final static String LIMS_PE_OTHER_DIAGNOSIS = "other_diagnosis_name";
    public final static String LIMS_PE_OTHER_TISSUE_ORIGIN = "other_tissue_origin_name";
    public final static String LIMS_PE_EXTERNAL_COMMENTS = "external_comments";
    public final static String LIMS_PE_INTERNAL_COMMENTS = "internal_comments";
    public final static String LIMS_PE_SOURCE_ID = "source_pe_id";
    public final static String LIMS_PE_TCS = "cellularstroma_cells";
    public final static String LIMS_PE_REPORTED_DATE = "reported_date";
    public final static String LIMS_PE_CREATE_DATE = "lims_pe_create_date";
    public final static String LIMS_PE_CREATE_USER = "lims_pe_create_user";

    //------------------------------------------------------------
    // lims_incidents columns
    //------------------------------------------------------------
    public final static String INCIDENT_ID = "incident_id";
    public final static String LIMS_INCIDENT_CREATE_USER = "lims_incident_create_user";
    public final static String LIMS_INCIDENT_CREATE_DATE = "lims_incident_create_date";
    public final static String LIMS_INCIDENT_SAMPLE_ID = "lims_incident_sample_id";
    public final static String LIMS_INCIDENT_CONSENT_ID = "lims_incident_consent_id";
    public final static String LIMS_INCIDENT_ACTION = "lims_incident_action";
    public final static String LIMS_INCIDENT_REASON = "lims_incident_reason";
    public final static String LIMS_INCIDENT_SLIDE_ID = "lims_incident_slide_id";
    public final static String LIMS_INCIDENT_PATHOLOGIST = "lims_incident_pathologist";
    public final static String LIMS_INCIDENT_RE_PV_REQUESTOR_CODE = "lims_incident_requestor_code";
    public final static String LIMS_INCIDENT_ACTION_OTHER = "lims_incident_action_other";
    public final static String LIMS_INCIDENT_COMMENTS = "lims_incident_comments";

    //------------------------------------------------------------
    // lims_slide columns
    //------------------------------------------------------------
    public final static String SLIDE_ID = "slide_id";
    public final static String SLIDE_SAMPLE_ID = "lims_slide_sample_id";
    public final static String CREATE_DATE = "create_date";
    public final static String DESTROY_DATE = "destroy_date";
    public final static String SLIDE_SECTION_NUMBER = "section_number";
    public final static String SLIDE_SECTION_LEVEL = "section_level";
    public final static String SLIDE_CURRENT_LOCATION = "current_location";
    public final static String CREATE_USER = "create_user";
    public final static String SLIDE_SECTION_PROCEDURE = "section_proc";

    //------------------------------------------------------------
    // pdc_ardais_donor columns
    //------------------------------------------------------------
    public final static String DONOR_GENDER = "gender";
    public final static String DONOR_PROFILE_NOTES = "donor_profile_notes";
    public final static String DONOR_ARDAIS_ID = "donor_ardais_id";
    public final static String DONOR_ARDAIS_ACCT_KEY = "donor_ardais_acct_key";
    public final static String DONOR_IMPORTED_YN = "donor_imported_yn";
    public final static String DONOR_CUSTOMER_ID = "donor_customer_id";
    public final static String DONOR_REG_FORM = "donor_reg_form";
    public final static String DONOR_RACE = "donor_race";

    //------------------------------------------------------------
    // pdc_pathology_report columns
    //------------------------------------------------------------
    public final static String PATH_ADDITIONAL_NOTE = "additional_note";
    public final static String PATH_CONSENT_ID = "path_consent_id";
    public final static String PATH_DISEASE = "path_disease_concept_id";
    public final static String PATH_ID = "path_report_id";
    public final static String PATH_ASCII_REPORT = "pathology_ascii_report";

    //------------------------------------------------------------
    // pdc_path_report_section columns
    //------------------------------------------------------------
    public final static String SECTION_DX = "ddc_dx";
    public final static String SECTION_DX_OTHER = "ddc_dx_other";
    public final static String SECTION_HNG = "hng";
    public final static String SECTION_ID = "path_section_id";
    public final static String SECTION_GLEASON_PRIMARY = "gleason_primary";
    public final static String SECTION_GLEASON_SECONDARY = "gleason_secondary";
    public final static String SECTION_GLEASON_TOTAL = "gleason_total";
    public final static String SECTION_LYMPH_STAGE = "lymph_node_stage";
    public final static String SECTION_METASTASIS = "distant_metastasis";
    public final static String SECTION_PATH_REPORT_ID = "section_path_report_id";
    public final static String SECTION_PRIMARY_IND = "primary_ind";
    public final static String SECTION_SECTION_NOTES = "section_notes";
    public final static String SECTION_STAGE_GROUPING = "stage_grouping";
    public final static String SECTION_TISSUE_FINDING = "ddc_tissue_finding";
    public final static String SECTION_TISSUE_FINDING_OTHER = "ddc_tissue_finding_other";
    public final static String SECTION_TISSUE_ORIGIN = "ddc_tissue_origin";
    public final static String SECTION_TISSUE_ORIGIN_OTHER = "ddc_tissue_origin_other";
    public final static String SECTION_TUMOR_STAGE = "tumor_stage";
    public final static String SECTION_TUMOR_STAGE_TYPE = "tumor_stage_type";
    public final static String SECTION_TUMOR_SIZE = "tumor_size";
    public final static String SECTION_TUMOR_WEIGHT = "tumor_weight";

    //------------------------------------------------------------
    // tma_block_samples columns
    //------------------------------------------------------------
    public final static String TMA_BLOCK_SAMPLE_ID = "tma_block_sample_id";

    //------------------------------------------------------------
    // rna_batch_detail columns
    //------------------------------------------------------------
    public final static String RNA_CONSENT_ID = "rna_consent_id";
    public final static String RNA_RNAVIALID = "rnavialid";
    public final static String RNA_PREP = "prep";
    public final static String RNA_SAMPLE_ID = "rna_sample_id";
    public final static String RNA_STATUS = "rna_status";
    public final static String RNA_REMAINING_VOLUME = "remaining_volume";
    public final static String RNA_CONCENTRATION = "concentration";
    public final static String RNA_POOLED_TISSUE = "pooledtissue";
    public final static String RNA_CASE_EXHAUSTED = "case_exhausted";
    public final static String TMA_BLOCKSETID = "blocksetid";
    public final static String RNA_REP_SAMPLE = "rep_sample";
    public final static String RNA_PE_TMR = "rna_tumor_cells";
    public final static String RNA_PE_LSN = "rna_lesion_cells";
    public final static String RNA_PE_NRM = "rna_normal_cells";
    public final static String RNA_PE_TCS = "rna_cellularstroma_cells";
    public final static String RNA_PE_TAS = "rna_hypoacellularstroma_cells";
    public final static String RNA_PE_NEC = "rna_necrosis_cells";
    public final static String RNA_EXTERNAL_COMMENTS = "rna_external_comments";
    public final static String RNA_INTERNAL_COMMENTS = "rna_internal_comments";
    public final static String RNA_BMS_YN = "rna_bms_yn";
    public final static String RNA_NOTES = "rna_notes";

    //------------------------------------------------------------
    // rna_grossextraction columns
    //------------------------------------------------------------
    public final static String RNA_QUALITY = "rnaquality";
    public final static String RNA_GROSS_VIAL_ID = "rna_gross_vial_id";

    //------------------------------------------------------------
    // rna_images columns
    //------------------------------------------------------------
    public final static String RNA_IMAGES_IMAGE_FILE_NAME = "imagefilename";
    public final static String RNA_IMAGES_IMAGE_TYPE = "imagetype";

    //------------------------------------------------------------
    // rna_rna_list columns
    //------------------------------------------------------------
    public final static String RNA_LIST_REQUEST_ID = "rna_list_request_id";
    public final static String RNA_LIST_VIALTOSEND = "vialtosend";
    public final static String RNA_LIST_VIALTOUSE = "vialtouse";
    public final static String RNA_LIST_VOLUME = "volume";

    //------------------------------------------------------------
    // rna_pool_tissue columns
    //------------------------------------------------------------
    public final static String RNA_POOL_SAMPLE_ID = "rna_pool_sample_id";
    public final static String RNA_POOL_VIAL_ID = "rna_pool_vial_id";

    //------------------------------------------------------------
    // rna_hold_amount_v columns
    //------------------------------------------------------------
    public final static String RNA_HOLD_AMOUNT = "hold_amt";
    public final static String RNA_HOLD_VIAL_ID = "rna_hold_vial_id";

    //------------------------------------------------------------
    // rna_project columns
    //------------------------------------------------------------
    public final static String RNA_PROJECT = "rna_project";
    public final static String RNA_REQUEST_ID = "rna_request_id";

    //------------------------------------------------------------
    // rna_bioanalyser columns
    //------------------------------------------------------------
    public final static String RNA_BIORATIO = "bio_ratio";
    public final static String RNA_BIO_VIAL_ID = "rna_bio_vial_id";

    //------------------------------------------------------------
    // ads_imaget_syn columns
    //------------------------------------------------------------
    public final static String IMAGES_IMAGE_ID = "images_imageid";
    public final static String IMAGES_IMAGE_FILE_NAME = "images_imagefilename";
    public final static String IMAGES_SLIDE_ID = "images_slideid";
    public final static String IMAGES_USERNAME = "images_username";
    public final static String IMAGES_IMAGE_TYPE = "images_imagetype";
    public final static String IMAGES_MAGNIFICATION = "images_magnification";
    public final static String IMAGES_BEST_IMAGE = "images_bestimage";
    public final static String IMAGES_CAPTURE_DATE = "images_capturedate";
    public final static String IMAGES_IMAGE_NOTES = "images_imagenotes";
    public final static String IMAGES_IMAGE_PATH = "images_imagepath";
    public final static String IMAGES_THUMBNAIL_PATH = "images_tnailpath";
    public final static String IMAGES_OVERLAY = "images_overlay";
    public final static String IMAGES_IMAGE_ON_REPORT = "images_imageonreport";
    public final static String IMAGES_SEARCH_QRY = "images_searchqry";
    public final static String IMAGES_PATHOLOGIST_ID = "images_pathologist_id";

    //------------------------------------------------------------
    // pts_project columns
    //------------------------------------------------------------
    public final static String PROJECT_NAME = "projectname";
    public final static String PROJECT_ID = "projectid";
    public final static String PROJECT_DATE_REQUESTED = "project_date_requested";

    //------------------------------------------------------------
    // pts_sample columns
    //------------------------------------------------------------
    public final static String PTS_SAMPLE_ID = "pts_sample_barcode_id";
    public final static String PTS_SAMPLE_PROJECT_ID = "pts_sample_projectid";

    //------------------------------------------------------------
    // es_shopping_cart_detail columns
    //------------------------------------------------------------
    public final static String SHOPPING_CART_ID = "shopping_cart_id";
    public final static String SHOPPING_CART_SAMPLE_ID = "shopping_cart_sample_id";
    public final static String SHOPPING_CART_USER_ID = "shopping_cart_user_id";
    public final static String SHOPPING_CART_CREATION_DATE = "shopping_cart_creation_dt";
    public final static String SHOPPING_CART_AMOUNT = "shopping_cart_amount";

    //------------------------------------------------------------
    // es_ardais_order columns
    //------------------------------------------------------------
    public final static String ORDER_NUMBER = "order_number";
    public final static String ORDER_DESCRIPTION = "order_description";
    public final static String ORDER_STATUS = "order_status";
    public final static String ORDER_USER_ID = "order_user_id";

    //------------------------------------------------------------
    // es_order_line columns
    //------------------------------------------------------------
    public final static String LINE_ORDER_NUMBER = "line_order_number";
    public final static String LINE_SAMPLE_ID = "line_barcode_id";

    //------------------------------------------------------------
    // es_ardais_user columns
    //------------------------------------------------------------
    public final static String ARDAIS_USER_ID = "ardais_user_id";
    public final static String USER_ACCT_KEY = "user_acct_key";
    public final static String ARDAIS_USER_USER_FIRSTNAME = "ardais_user_user_firstname";
    public final static String ARDAIS_USER_USER_LASTNAME = "ardais_user_user_lastname";
    public final static String ARDAIS_USER_USER_ACTIVE_IND = "ardais_user_user_active_ind";

    //------------------------------------------------------------
    // ard_logical_repos columns
    //------------------------------------------------------------
    public final static String LOGICAL_REPOS_ID = "logical_repos_id";
    public final static String LOGICAL_REPOS_FULL_NAME = "logical_repos_full_name";
    public final static String LOGICAL_REPOS_SHORT_NAME = "logical_repos_short_name";
    public final static String LOGICAL_REPOS_BMS_YN = "logical_repos_bms_yn";

    //------------------------------------------------------------
    // ard_logical_repos_item columns
    //------------------------------------------------------------
    public final static String LOGICAL_REPOS_ITEM_ID = "logical_repos_item_id";
    public final static String LOGICAL_REPOS_ITEM_REPOSITORY_ID = "logical_repos_item_repos_id";
    public final static String LOGICAL_REPOS_ITEM_ITEM_ID = "logical_repos_item_item_id";
    public final static String LOGICAL_REPOS_ITEM_ITEM_TYPE = "logical_repos_item_item_type";

    //------------------------------------------------------------
    // ard_logical_repos_user columns
    //------------------------------------------------------------
    public final static String LOGICAL_REPOS_USER_ID = "logical_repos_user_id";
    public final static String LOGICAL_REPOS_USER_REPOSITORY_ID = "logical_repos_user_repos_id";
    public final static String LOGICAL_REPOS_USER_USER_ID = "logical_repos_user_user_id";

    //------------------------------------------------------------
    // iltds_request columns
    //------------------------------------------------------------
    public final static String REQUEST_REQUEST_ID = "request_request_id";
    public final static String REQUEST_CREATE_DATE = "request_create_date";
    public final static String REQUEST_STATE = "request_state";
    public final static String REQUEST_CLOSED_YN = "request_closed_yn";
    public final static String REQUEST_REQUESTER_USER_ID = "request_requester_user_id";
    public final static String REQUEST_REQUESTER_COMMENTS = "request_requester_comments";
    public final static String REQUEST_DESTINATION_ID = "request_destination_id";
    public final static String REQUEST_REQUEST_MGR_COMMENTS = "request_request_mgr_comments";
    public final static String REQUEST_REQUEST_TYPE = "request_request_type";
    public final static String REQUEST_DELIVER_TO_USER_ID = "request_deliver_to_user_id";
    public final static String REQUEST_SHIP_TO_ADDR_ID = "request_ship_to_addr_id";

    //------------------------------------------------------------
    // iltds_request_box columns
    //------------------------------------------------------------
    public final static String REQUEST_BOX_ID = "request_box_id";
    public final static String REQUEST_BOX_REQUEST_ID = "request_box_request_id";
    public final static String REQUEST_BOX_BOX_BARCODE_ID = "request_box_box_barcode_id";
    public final static String REQUEST_BOX_BOX_ORDER = "request_box_box_order";
    public final static String REQUEST_BOX_SHIPPED_YN = "request_box_shipped_yn";
    public final static String REQUEST_BOX_BOX_CONTENTS = "request_box_box_contents";

    //------------------------------------------------------------
    // iltds_request_item columns
    //------------------------------------------------------------
    public final static String REQUEST_ITEM_ID = "request_item_id";
    public final static String REQUEST_ITEM_REQUEST_ID = "request_item_request_id";
    public final static String REQUEST_ITEM_ITEM_ID = "request_item_item_id";
    public final static String REQUEST_ITEM_ITEM_TYPE = "request_item_item_type";
    public final static String REQUEST_ITEM_ITEM_ORDER = "request_item_item_order";
    public final static String REQUEST_ITEM_FULFILLED_YN = "request_item_fulfilled_yn";
    public final static String REQUEST_ITEM_BOX_BARCODE_ID = "request_item_box_barcode_id";

    //------------------------------------------------------------
    // ard_urls columns
    //------------------------------------------------------------
    public final static String ARD_URLS_ID = "ard_urls_id";
    public final static String ARD_URLS_ACCT_KEY = "ard_urls_acct_key";
    public final static String ARD_URLS_OBJECT_TYPE = "ard_urls_object_type";
    public final static String ARD_URLS_URL = "ard_urls_url";
    public final static String ARD_URLS_LABEL = "ard_urls_label";
    public final static String ARD_URLS_TARGET = "ard_urls_target";

    //------------------------------------------------------------
    // pdc_path_report_diagnostics columns
    //------------------------------------------------------------
    public final static String DIAGNOSTICS_PATH_REPORT_ID = "diagnostics_path_report_id";
    public final static String DIAGNOSTICS_CONCEPT_ID = "path_diagnostics_concept_id";
    public final static String DIAGNOSTIC_RESULTS_CID = "diagnostic_results_cid";
    public final static String DIAGNOSTIC_TYPE = "diagnostic_type";

    //------------------------------------------------------------
    // ard_policy columns
    //------------------------------------------------------------
    public final static String POLICY_ID = "policy_id";
    public final static String POLICY_NAME = "policy_name";
    public final static String POLICY_ALLOCATION_DENOMINATOR = "policy_allocation_denominator";
    public final static String POLICY_ALLOCATION_NUMERATOR = "policy_allocation_numerator";
    public final static String POLICY_ALLOCATION_FORMAT_CID = "policy_allocation_format_cid";
    public final static String POLICY_DEFAULT_LOGICAL_REPOS_ID = "policy_default_log_repos_id";
    public final static String POLICY_RESTRICTED_LOGICAL_REPOS_ID = "policy_restricted_log_repos_id";
    public final static String POLICY_CONSENT_VERIFY_REQUIRED = "policy_consent_verify_required";
    public final static String POLICY_CASE_RELEASE_REQUIRED = "policy_case_release_required";
    public final static String POLICY_ADDITIONAL_QUESTIONS_GRP = "policy_additional_questions_grp";
    public final static String POLICY_ARDAIS_ACCT_KEY = "policy_ardais_acct_key";
    public final static String POLICY_ALLOW_FOR_UNLINKED_YN = "policy_allow_for_unlinked_yn";
    public final static String POLICY_POLICY_DATA_ENCODING = "policy_policy_data_encoding";
    public final static String POLICY_POLICY_DATA = "policy_policy_data";
    public final static String POLICY_CASE_REGISTRATION_FORM = "policy_case_registration_form";

    //------------------------------------------------------------
    // es_ardais_irb columns
    //------------------------------------------------------------
    public final static String IRB_ARDAIS_ACCT_KEY = "irb_ardais_acct_key";
    public final static String IRB_PROTOCOL_ID = "irb_protocol_id";
    public final static String IRB_PROTOCOL = "irb_protocol";
    public final static String IRB_POLICY_ID = "irb_policy_id";

    //------------------------------------------------------------
    // iltds_box_layout columns
    //------------------------------------------------------------
    public final static String LY_BOX_LAYOUT_ID = "ly_box_layout_id";
    public final static String LY_NUMBER_OF_COLUMNS = "ly_number_of_columns";
    public final static String LY_NUMBER_OF_ROWS = "ly_number_of_rows";
    public final static String LY_X_AXIS_LABEL_CID = "ly_x_axis_label_cid";
    public final static String LY_Y_AXIS_LABEL_CID = "ly_y_axis_label_cid";
    public final static String LY_TAB_INDEX_CID = "ly_tab_index_cid";

    //------------------------------------------------------------
    // iltds_account_box_layout columns
    //------------------------------------------------------------
    public final static String ACCT_LY_ID = "acct_ly_id";
    public final static String ACCT_LY_BOX_LAYOUT_NAME = "acct_ly_box_layout_name";
    public final static String ACCT_LY_ARDAIS_ACCT_KEY = "acct_ly_ardais_acct_key";
    public final static String ACCT_LY_BOX_LAYOUT_ID = "acct_ly_box_layout_id";

    //------------------------------------------------------------
    // es_ardais_account columns
    //------------------------------------------------------------
    public final static String ACCOUNT_KEY = "acct_key";
    public final static String ACCOUNT_TYPE = "acct_type";
    public final static String ACCOUNT_STATUS = "acct_status";
    public final static String ACCOUNT_NAME = "acct_name";
    public final static String ACCOUNT_PARENT_NAME = "acct_parent_name";
    public final static String ACCOUNT_OPEN_DATE = "acct_open_date";
    public final static String ACCOUNT_ACTIVE_DATE = "acct_active_date";
    public final static String ACCOUNT_CLOSE_DATE = "acct_close_date";
    public final static String ACCOUNT_DEACTIVATE_DATE = "acct_deactivate_date";
    public final static String ACCOUNT_DEACTIVATE_REASON = "acct_deactivate_reason";
    public final static String ACCOUNT_REACTIVATE_DATE = "acct_reactivate_date";
    public final static String ACCOUNT_PRIMARY_LOCATION = "acct_primary_location";
    public final static String ACCOUNT_BRAND_ID = "acct_brand_id";
    public final static String ACCOUNT_REQ_MGR_EMAIL = "acct_req_mgr_email";
    public final static String ACCOUNT_LINKED_CASES_ONLY_YN = "acct_linked_cases_only_yn";
    public final static String ACCOUNT_PASSWORD_POLICY_CID = "acct_password_policy_cid";
    public final static String ACCOUNT_PASSWORD_LIFE_SPAN = "acct_password_life_span";
    public final static String ACCOUNT_DEFAULT_BOX_LAYOUT_ID = "acct_default_box_layout_id";
    public final static String ACCOUNT_ACCOUNT_DATA = "acct_account_data";
    public final static String ACCOUNT_ACCOUNT_DATA_ENCODING = "acct_account_data_encoding";
    public final static String ACCOUNT_CONTACT_PHONE_NUMBER = "acct_contact_phone_number";
    public final static String ACCOUNT_CONTACT_PHONE_EXT = "acct_contact_phone_ext";
    public final static String ACCOUNT_CONTACT_FAX_NUMBER = "acct_contact_fax_number";
    public final static String ACCOUNT_CONTACT_EMAIL_ADDRESS = "acct_contact_email_address";
    public final static String ACCOUNT_CONTACT_ADDRESS_ID = "acct_contact_address_id";
    public final static String ACCOUNT_CONTACT_MOBILE_NUMBER = "acct_contact_mobile_number";
    public final static String ACCOUNT_CONTACT_PAGER_NUMBER = "acct_contact_pager_number";
    public final static String ACCOUNT_DONOR_REGISTRATION_FORM = "acct_donor_registration_form";
    public final static String ACCOUNT_SAMPLE_ALIASES_UNIQUE_YN = "acct_sample_aliases_unique_yn";
    public final static String ACCOUNT_SAMPLE_ALIASES_REQUIRED_YN = "acct_sample_aliases_required_yn";

    //------------------------------------------------------------
    // ardais_address columns
    //------------------------------------------------------------
    public final static String ADDRESS_ADDRESS_ID = "address_address_id";
    public final static String ADDRESS_ACCOUNT_KEY = "address_acct_key";
    public final static String ADDRESS_ADDRESS_TYPE = "address_address_type";
    public final static String ADDRESS_ADDRESS_1 = "address_address_1";
    public final static String ADDRESS_ADDRESS_2 = "address_address_2";
    public final static String ADDRESS_CITY = "address_city";
    public final static String ADDRESS_STATE = "address_state";
    public final static String ADDRESS_ZIP_CODE = "address_zip_code";
    public final static String ADDRESS_COUNTRY = "address_country";
    public final static String ADDRESS_FIRST_NAME = "address_first_name";
    public final static String ADDRESS_LAST_NAME = "address_last_name";
    public final static String ADDRESS_MIDDLE_NAME = "address_middle_name";

    //------------------------------------------------------------
    // iltds_shipping_partners columns
    //------------------------------------------------------------
    public final static String SHIPPING_PARTNER_ID = "sp_id";
    public final static String SHIPPING_PARTNER_ARDAIS_ACCT_KEY = "sp_ardais_acct_key";
    public final static String SHIPPING_PARTNER_DESTINATION_ID = "sp_destination_id";

    //------------------------------------------------------------
    // iltds_geography_location columns
    //------------------------------------------------------------
    public final static String LOCATION_ADDRESS_ID = "gl_location_address_id";
    public final static String LOCATION_NAME = "gl_location_name";
    public final static String LOCATION_ADDRESS_1 = "gl_location_address_1";
    public final static String LOCATION_ADDRESS_2 = "gl_location_address_2";
    public final static String LOCATION_CITY = "gl_location_city";
    public final static String LOCATION_STATE = "gl_location_state";
    public final static String LOCATION_ZIP = "gl_location_zip";
    public final static String LOCATION_COUNTRY = "gl_location_country";
    public final static String LOCATION_PHONE = "gl_location_phone";

    //------------------------------------------------------------
    // iltds_sample_box columns
    //------------------------------------------------------------
    public final static String SAMPLE_BOX_BOX_BARCODE_ID = "sample_box_box_barcode_id";
    public final static String SAMPLE_BOX_MANIFEST_NUMBER = "sample_box_manifest_number";
    public final static String SAMPLE_BOX_CHECK_IN_DATE = "sample_box_check_in_date";
    public final static String SAMPLE_BOX_CHECK_OUT_DATE = "sample_box_check_out_date";
    public final static String SAMPLE_BOX_CHECK_OUT_REASON = "sample_box_check_out_reason";
    public final static String SAMPLE_BOX_CHECK_OUT_STAFF_ID = "sample_box_check_out_staff_id";
    public final static String SAMPLE_BOX_STATUS = "sample_box_status";
    public final static String SAMPLE_BOX_NOTE = "sample_box_note";
    public final static String SAMPLE_BOX_MANIFEST_ORDER = "sample_box_manifest_order";
    public final static String SAMPLE_BOX_LAYOUT_ID = "sample_box_layout_id";
    public final static String SAMPLE_BOX_STORAGE_TYPE_CID = "sample_box_storage_type_cid";

    //------------------------------------------------------------
    // es_ardais_consentver columns
    //------------------------------------------------------------
    public final static String CONSENTVER_IRBPROTOCOL_ID = "consentver_irbprotocol_id";
    public final static String CONSENT_VERSION_ID = "consent_version_id";
    public final static String CONSENT_VERSION = "consent_version";

    //------------------------------------------------------------
    // bigr_role columns
    //------------------------------------------------------------
    public final static String BIGR_ROLE_ID = "bigr_role_id";
    public final static String BIGR_ROLE_NAME = "bigr_role_name";
    public final static String BIGR_ROLE_ACCOUNT_ID = "bigr_role_account_id";

    //------------------------------------------------------------
    // bigr_role_privilege columns
    //------------------------------------------------------------
    public final static String BIGR_ROLE_PRIVILEGE_ID = "bigr_role_privilege_id";
    public final static String BIGR_ROLE_PRIVILEGE_ROLE_ID = "bigr_role_privilege_role_id";
    public final static String BIGR_ROLE_PRIVILEGE_PRIVILEGE_ID = "bigr_role_privilege_privilege_id";

    //------------------------------------------------------------
    // bigr_role_user columns
    //------------------------------------------------------------
    public final static String BIGR_ROLE_USER_ID = "bigr_role_user_id";
    public final static String BIGR_ROLE_USER_ROLE_ID = "bigr_role_user_role_id";
    public final static String BIGR_ROLE_USER_USER_ID = "bigr_role_user_user_id";

}