
package com.ardais.bigr.util.gen;

/**
 * Contains static constants that contain actual names of database columns
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
 
public class DbConstants {

    private DbConstants() {
    }

    //----------------------------------------------------------------
    // Table names.  These are the actual table names in the database.
    //----------------------------------------------------------------
    public final static String TABLE_ASM = "iltds_asm";
    public final static String TABLE_ASM_FORM = "iltds_asm_form";
    public final static String TABLE_BOX_LOCATION = "iltds_box_location";
    public final static String TABLE_ARDAIS_STAFF = "iltds_ardais_staff";
    public final static String TABLE_CONSENT = "iltds_informed_consent";
    public final static String TABLE_CONSENT_REVOKED = "iltds_revoked_consent_archive";
    public final static String TABLE_SAMPLE = "iltds_sample";
    public final static String TABLE_ATTACHMENTS ="iltds_attachments";
    public final static String TABLE_LIMS_PE = "lims_pathology_evaluation";
    public final static String TABLE_LIMS_INCIDENTS = "lims_incidents";
    public final static String TABLE_SLIDE = "lims_slide";
    public final static String TABLE_DONOR = "pdc_ardais_donor";
    public final static String TABLE_PATH = "pdc_pathology_report";
    public final static String TABLE_SECTION = "pdc_path_report_section";
    public final static String TABLE_TMA_BLOCK_SAMPLES = "tma_block_samples";
    public final static String TABLE_RNA_BATCH_DETAIL = "rna_batch_detail";
    public final static String TABLE_RNA_GROSS_EXTRACTION = "rna_grossextraction";
    public final static String TABLE_RNA_IMAGES = "rna_images";
    public final static String TABLE_RNA_LIST = "rna_rna_list";
    public final static String TABLE_RNA_POOL_TISSUE = "rna_pool_tissue";
    public final static String TABLE_RNA_HOLD_AMOUNT = "rna_hold_amount_v";
    public final static String TABLE_RNA_PROJECT = "rna_project";
    public final static String TABLE_RNA_BIOANALYSER = "rna_bioanalyser";
    public final static String TABLE_IMAGES = "ads_imaget_syn";
    public final static String TABLE_PTS_PROJECT = "pts_project";
    public final static String TABLE_PTS_SAMPLE = "pts_sample";
    public final static String TABLE_SHOPPING_CART_DETAIL = "es_shopping_cart_detail";
    public final static String TABLE_SHOPPING_CART = "es_shopping_cart";
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
    public final static String TABLE_PATH_REPORT_DIAGNOSTICS = "pdc_path_report_diagnostics";
    public final static String TABLE_ARD_POLICY = "ard_policy";
    public final static String TABLE_IRB = "es_ardais_irb";
    public final static String TABLE_BOX_LAYOUT = "iltds_box_layout";
    public final static String TABLE_ACCOUNT_BOX_LAYOUT = "iltds_account_box_layout";
    public final static String TABLE_ARDAIS_ACCOUNT = "es_ardais_account";
    public final static String TABLE_ARDAIS_ADDRESS = "ardais_address";
    public final static String TABLE_ILTDS_SHIPPING_PARTNERS = "iltds_shipping_partners";
    public final static String TABLE_ILTDS_GEOGRAPHY_LOCATION = "iltds_geography_location";
    public final static String TABLE_ILTDS_SAMPLE_BOX = "iltds_sample_box";
    public final static String TABLE_ES_ARDAIS_CONSENTVER = "es_ardais_consentver";
    public final static String TABLE_BIGR_ROLE = "bigr_role";
    public final static String TABLE_BIGR_ROLE_PRIVILEGE = "bigr_role_privilege";
    public final static String TABLE_BIGR_ROLE_USER = "bigr_role_user";

    //------------------------------------------------------------
    // Column names.  These are the actual column names in the database.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // iltds_asm columns
    //------------------------------------------------------------
    public final static String ASM_APPEARANCE = "specimen_type";
    public final static String ASM_DONOR_ID = "ardais_id";
    public final static String ASM_CONSENT_ID = "consent_id";
    public final static String ASM_ID = "asm_id";
    public final static String ASM_FORM_ID = "asm_form_id";
    public final static String ASM_TISSUE = "organ_site_concept_id";
    public final static String ASM_TISSUE_OTHER = "organ_site_concept_id_other";
    public final static String ASM_MODULE_COMMENTS = "module_comments";

    //------------------------------------------------------------
    // iltds_asm_form columns
    //------------------------------------------------------------
    public final static String ASM_FORM_DONOR_ID = "ardais_id";
    public final static String ASM_FORM_CONSENT_ID = "consent_id";
    public final static String ASM_FORM_FORM_ID = "asm_form_id";
    public final static String ASM_FORM_PREPARED_BY = "ardais_staff_id";
    public final static String ASM_FORM_PROCEDURE = "surgical_specimen_id";
    public final static String ASM_FORM_PROCEDURE_OTHER = "surgical_specimen_id_other";
    
    //---------------------------------------------------------------------------
    //iltds_attachments columns
    //---------------------------------------------------------------------------
    public final static String ATTACHMENTS_ID ="id";
    public final static String ATTACHMENTS_ADS_ACCT_KEY ="ardais_acct_key";
    public final static String ATTACHMENTS_DONOR_ID ="ardais_id";
    public final static String ATTACHMENTS_CONSENT_ID ="consent_id";
    public final static String ATTACHMENTS_SAMPLE_BARCODE_ID ="sample_barcode_id";
    public final static String ATTACHMENTS_ISPHI ="is_phi_yn";
    public final static String ATTACHMENTS_COMMENTS ="comments";
    public final static String ATTACHMENTS_NAME ="name";
    public final static String ATTACHMENTS_ATTACHED ="attachment";
    public final static String ATTACHMENTS_CREATED_BY ="created_by";
    public final static String ATTACHMENTS_CREATED_DATE ="created_date";

    //------------------------------------------------------------
    // iltds_box_location columns
    //------------------------------------------------------------
    public final static String BOX_LOCATION_BOX_BARCODE_ID = "box_barcode_id";
    public final static String BOX_LOCATION_ADDRESS_ID = "location_address_id";
    public final static String BOX_LOCATION_ROOM_ID = "room_id";
    public final static String BOX_LOCATION_DRAWER_ID = "drawer_id";
    public final static String BOX_LOCATION_SLOT_ID = "slot_id";
    public final static String BOX_LOCATION_UNIT_NAME = "unit_name";
    public final static String BOX_LOCATION_STORAGE_TYPE_CID = "storage_type_cid";

    //------------------------------------------------------------
    // iltds_ardais_staff columns
    //------------------------------------------------------------
    public final static String ARDAIS_STAFF_STAFF_ID = "ardais_staff_id";
    public final static String STAFF_LOCATION_ADDRESS_ID = "location_address_id";
    public final static String ARDAIS_STAFF_STAFF_LNAME = "ardais_staff_lname";
    public final static String ARDAIS_STAFF_STAFF_FNAME = "ardais_staff_fname";
    public final static String ARDAIS_STAFF_ACCT_KEY = "ardais_acct_key";
    public final static String ARDAIS_STAFF_USER_ID = "ardais_user_id";

    //------------------------------------------------------------
    // iltds_informed_consent columns
    //------------------------------------------------------------
    public final static String CONSENT_ID = "consent_id";
    public final static String CONSENT_DX = "disease_concept_id";
    public final static String CONSENT_DX_OTHER = "disease_concept_id_other";
    public final static String CONSENT_LINKED = "linked";
    public final static String CONSENT_LOCATION = "consent_location_address_id";
    public final static String CONSENT_PULL_DT = "consent_pull_datetime";
    public final static String DONOR_ID = "ardais_id";
    public final static String CONSENT_BEST_DIAGNOSIS_CUI = "best_diagnosis_cui";
    public final static String CONSENT_BEST_DIAGNOSIS_OTHER = "best_diagnosis_other";
    public final static String CONSENT_ARDAIS_ACCT_KEY = "ardais_acct_key";
    public final static String CONSENT_IMPORTED_YN = "imported_yn";
    public final static String CONSENT_CUSTOMER_ID = "customer_id";
    public final static String CONSENT_PSA = "psa";
    public final static String CONSENT_DRE_CID = "dre_cid";
    public final static String CONSENT_CLINICAL_FINDING_NOTES = "clinical_finding_notes";
    public final static String CONSENT_POLICY_ID = "policy_id";
    public final static String CONSENT_REG_FORM = "case_registration_form";

    //------------------------------------------------------------
    // iltds_revoked_consent_archive columns
    //------------------------------------------------------------
    public final static String REVOKED_CONSENT_ID = "consent_id";

    //------------------------------------------------------------
    // iltds_sample columns
    //------------------------------------------------------------
    public final static String SAMPLE_ACCOUNT = "ardais_acct_key";
    public final static String SAMPLE_ASM_ID = "asm_id";
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
    public final static String SAMPLE_RESTRICTED = "allocation_ind";
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
    public final static String SAMPLE_CONSENT_ID = "consent_id";
    public final static String SAMPLE_ARDAIS_ID = "ardais_id";
    public final static String SAMPLE_TISSUE_FINDING_CUI = "tissue_finding_cui";
    public final static String SAMPLE_TISSUE_FINDING_OTHER = "tissue_finding_other";
    public final static String SAMPLE_IMPORTED_YN = "imported_yn";
    public final static String SAMPLE_CUSTOMER_ID = "customer_id";
    public final static String SAMPLE_SAMPLE_TYPE_CID = "sample_type_cid";
    public final static String SAMPLE_COLLECTION_DATETIME = "collection_datetime";
    public final static String SAMPLE_COLLECTION_DATETIME_DPC = "collection_datetime_dpc";
    public final static String SAMPLE_PRESERVATION_DATETIME = "preservation_datetime";
    public final static String SAMPLE_PRESERVE_DATETIME_DPC = "preservation_datetime_dpc";
    public final static String SAMPLE_VOLUME = "volume";
    public final static String SAMPLE_VOLUME_UNIT_CUI = "volume_unit_cui";
    public final static String SAMPLE_VOLUME_UL = "volume_ul";
    public final static String SAMPLE_WEIGHT = "weight";
    public final static String SAMPLE_WEIGHT_UNIT_CUI = "weight_unit_cui";
    public final static String SAMPLE_WEIGHT_MG = "weight_mg";
    public final static String SAMPLE_AGE_AT_COLLECTION = "age_at_collection";
    public final static String SAMPLE_APPEARANCE_BEST = "appearance_best";
    public final static String SAMPLE_DIAGNOSIS_CUI_BEST = "diagnosis_cui_best";
    public final static String SAMPLE_DIAGNOSIS_OTHER_BEST = "diagnosis_other_best";
    public final static String SAMPLE_TISSUE_ORIGIN_CUI = "tissue_origin_cui";
    public final static String SAMPLE_TISSUE_ORIGIN_OTHER = "tissue_origin_other";
    public final static String SAMPLE_BUFFER_TYPE_CUI = "buffer_type_cui";
    public final static String SAMPLE_BUFFER_TYPE_OTHER = "buffer_type_other";
    public final static String SAMPLE_TOTAL_NUM_OF_CELLS = "total_num_of_cells";
    public final static String SAMPLE_TOTAL_NUM_OF_CELLS_EX_REP_CUI = "total_num_of_cells_ex_rep_cui";
    public final static String SAMPLE_CELLS_PER_ML = "cells_per_ml";
    public final static String SAMPLE_PERCENT_VIABILITY = "percent_viability";
    public final static String SAMPLE_SOURCE = "source";
    public final static String SAMPLE_CONCENTRATION = "concentration";
    public final static String SAMPLE_YIELD = "yield";
    public final static String SAMPLE_REG_FORM = "sample_registration_form";

    //------------------------------------------------------------
    // lims_pathology_evaluation columns
    //------------------------------------------------------------
    public final static String PE_ID = "pe_id";
    public final static String LIMS_PE_SLIDE_ID = "slide_id";
    public final static String LIMS_PE_SAMPLE_ID = "sample_barcode_id";
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
    public final static String LIMS_PE_CREATE_DATE = "create_date";
    public final static String LIMS_PE_CREATE_USER = "create_user";

    //------------------------------------------------------------
    // lims_incidents columns
    //------------------------------------------------------------
    public final static String INCIDENT_ID = "incident_id";
    public final static String LIMS_INCIDENT_CREATE_USER = "create_user";
    public final static String LIMS_INCIDENT_CREATE_DATE = "create_date";
    public final static String LIMS_INCIDENT_SAMPLE_ID = "sample_barcode_id";
    public final static String LIMS_INCIDENT_CONSENT_ID = "consent_id";
    public final static String LIMS_INCIDENT_ACTION = "action";
    public final static String LIMS_INCIDENT_REASON = "reason";
    public final static String LIMS_INCIDENT_SLIDE_ID = "slide_id";
    public final static String LIMS_INCIDENT_PATHOLOGIST = "pathologist";
    public final static String LIMS_INCIDENT_RE_PV_REQUESTOR_CODE = "re_pv_requestor_code";
    public final static String LIMS_INCIDENT_ACTION_OTHER = "action_other";
    public final static String LIMS_INCIDENT_COMMENTS = "comments";

    //------------------------------------------------------------
    // lims_slide columns
    //------------------------------------------------------------
    public final static String SLIDE_ID = "slide_id";
    public final static String SLIDE_SAMPLE_ID = "sample_barcode_id";
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
    public final static String DONOR_ARDAIS_ID = "ardais_id";
    public final static String DONOR_ARDAIS_ACCT_KEY = "ardais_acct_key";
    public final static String DONOR_IMPORTED_YN = "imported_yn";
    public final static String DONOR_CUSTOMER_ID = "customer_id";
    public final static String DONOR_REG_FORM = "donor_registration_form";
    public final static String DONOR_RACE ="race";

    //------------------------------------------------------------
    // pdc_pathology_report columns
    //------------------------------------------------------------
    public final static String PATH_ADDITIONAL_NOTE = "additional_note";
    public final static String PATH_CONSENT_ID = "consent_id";
    public final static String PATH_DX = "diagnosis_concept_id";
    public final static String PATH_DISEASE = "disease_concept_id";
    public final static String PATH_ID = "path_report_id";
    public final static String PATH_PRIMARY_SECTION_ID = "primary_path_report_section_id";
    public final static String PATH_ASCII_REPORT = "pathology_ascii_report";

    //------------------------------------------------------------
    // pdc_path_report_section columns
    //------------------------------------------------------------
    public final static String SECTION_DX = "diagnosis_concept_id";
    public final static String SECTION_DX_OTHER = "other_dx_name";
    public final static String SECTION_HNG = "histological_nuclear_grade";
    public final static String SECTION_ID = "path_report_section_id";
    public final static String SECTION_GLEASON_PRIMARY = "primary_gleason_score";
    public final static String SECTION_GLEASON_SECONDARY = "secondary_gleason_score";
    public final static String SECTION_GLEASON_TOTAL = "total_gleason_score";
    public final static String SECTION_LYMPH_STAGE = "lymph_node_stage";
    public final static String SECTION_METASTASIS = "distant_metastasis";
    public final static String SECTION_PATH_REPORT_ID = "path_report_id";
    public final static String SECTION_PRIMARY_IND = "primary_ind";
    public final static String SECTION_SECTION_NOTES = "path_section_notes";
    public final static String SECTION_STAGE_GROUPING = "stage_grouping";
    public final static String SECTION_TISSUE_FINDING = "tissue_finding_concept_id";
    public final static String SECTION_TISSUE_FINDING_OTHER = "other_finding_tc_name";
    public final static String SECTION_TISSUE_ORIGIN = "tissue_origin_concept_id";
    public final static String SECTION_TISSUE_ORIGIN_OTHER = "other_origin_tc_name";
    public final static String SECTION_TUMOR_STAGE = "tumor_stage_desc";
    public final static String SECTION_TUMOR_STAGE_TYPE = "tumor_stage_type";
    public final static String SECTION_TUMOR_SIZE = "tumor_size";
    public final static String SECTION_TUMOR_WEIGHT = "tumor_weight";

    //------------------------------------------------------------
    // tma_block_samples columns
    //------------------------------------------------------------
    public final static String TMA_BLOCK_SAMPLE_ID = "sample_barcode_id";

    //------------------------------------------------------------
    // rna_batch_detail columns
    //------------------------------------------------------------
    public final static String RNA_CONSENT_ID = "consent_id";
    public final static String RNA_RNAVIALID = "rnavialid";
    public final static String RNA_PREP = "prep";
    public final static String RNA_SAMPLE_ID = "sample_barcode_id";
    public final static String RNA_STATUS = "rnastatus";
    public final static String RNA_REMAINING_VOLUME = "remainingvolume";
    public final static String RNA_CONCENTRATION = "concentration";
    public final static String RNA_PRODUCT_TYPE = "producttype";
    public final static String RNA_POOLED_TISSUE = "pooledtissue";
    public final static String RNA_CASE_EXHAUSTED = "case_exhausted";
    public final static String TMA_BLOCKSETID = "blocksetid";
    public final static String RNA_REP_SAMPLE = "rep_sample";
    public final static String RNA_PE_TMR = "tumor_cells";
    public final static String RNA_PE_LSN = "lesion_cells";
    public final static String RNA_PE_NRM = "normal_cells";
    public final static String RNA_PE_TCS = "cellularstroma_cells";
    public final static String RNA_PE_TAS = "hypoacellularstroma_cells";
    public final static String RNA_PE_NEC = "necrosis_cells";
    public final static String RNA_EXTERNAL_COMMENTS = "external_comments";
    public final static String RNA_INTERNAL_COMMENTS = "internal_comments";
    public final static String RNA_BMS_YN = "bms_yn";
    public final static String RNA_NOTES = "notes";

    //------------------------------------------------------------
    // rna_grossextraction columns
    //------------------------------------------------------------
    public final static String RNA_QUALITY = "rnaquality";
    public final static String RNA_GROSS_VIAL_ID = "rnavialid";

    //------------------------------------------------------------
    // rna_images columns
    //------------------------------------------------------------
    public final static String RNA_IMAGES_IMAGE_FILE_NAME = "imagefilename";
    public final static String RNA_IMAGES_IMAGE_TYPE = "imagetype";

    //------------------------------------------------------------
    // rna_rna_list columns
    //------------------------------------------------------------
    public final static String RNA_LIST_REQUEST_ID = "requestid";
    public final static String RNA_LIST_VIALTOSEND = "vialtosend";
    public final static String RNA_LIST_VIALTOUSE = "vialtouse";
    public final static String RNA_LIST_VOLUME = "volume";

    //------------------------------------------------------------
    // rna_pool_tissue columns
    //------------------------------------------------------------
    public final static String RNA_POOL_SAMPLE_ID = "sample_barcode_id";
    public final static String RNA_POOL_VIAL_ID = "rnavialid";

    //------------------------------------------------------------
    // rna_hold_amount_v columns
    //------------------------------------------------------------
    public final static String RNA_HOLD_AMOUNT = "hold_amt";
    public final static String RNA_HOLD_VIAL_ID = "rnavialid";

    //------------------------------------------------------------
    // rna_project columns
    //------------------------------------------------------------
    public final static String RNA_PROJECT = "project";
    public final static String RNA_REQUEST_ID = "requestid";

    //------------------------------------------------------------
    // rna_bioanalyser columns
    //------------------------------------------------------------
    public final static String RNA_BIORATIO = "ratio";
    public final static String RNA_BIO_VIAL_ID = "rnavialid";

    //------------------------------------------------------------
    // ads_imaget_syn columns
    //------------------------------------------------------------
    public final static String IMAGES_IMAGE_ID = "imageid";
    public final static String IMAGES_IMAGE_FILE_NAME = "imagefilename";
    public final static String IMAGES_SLIDE_ID = "slideid";
    public final static String IMAGES_USERNAME = "username";
    public final static String IMAGES_IMAGE_TYPE = "imagetype";
    public final static String IMAGES_MAGNIFICATION = "magnification";
    public final static String IMAGES_BEST_IMAGE = "bestimage";
    public final static String IMAGES_CAPTURE_DATE = "capturedate";
    public final static String IMAGES_IMAGE_NOTES = "imagenotes";
    public final static String IMAGES_IMAGE_PATH = "imagepath";
    public final static String IMAGES_THUMBNAIL_PATH = "tnailpath";
    public final static String IMAGES_OVERLAY = "overlay";
    public final static String IMAGES_IMAGE_ON_REPORT = "imageonreport";
    public final static String IMAGES_SEARCH_QRY = "searchqry";
    public final static String IMAGES_PATHOLOGIST_ID = "pathologist_id";

    //------------------------------------------------------------
    // pts_project columns
    //------------------------------------------------------------
    public final static String PROJECT_NAME = "projectname";
    public final static String PROJECT_ID = "projectid";
    public final static String PROJECT_DATE_REQUESTED = "daterequested";

    //------------------------------------------------------------
    // pts_sample columns
    //------------------------------------------------------------
    public final static String PTS_SAMPLE_ID = "sample_barcode_id";
    public final static String PTS_SAMPLE_PROJECT_ID = "projectid";

    //------------------------------------------------------------
    // es_shopping_cart_detail columns
    //------------------------------------------------------------
    public final static String SHOPPING_CART_ID = "shopping_cart_id";
    public final static String SHOPPING_CART_SAMPLE_ID = "barcode_id";
    public final static String SHOPPING_CART_USER_ID = "ardais_user_id";
    public final static String SHOPPING_CART_CREATION_DATE = "creation_dt";
    public final static String SHOPPING_CART_AMOUNT = "quantity";

    //------------------------------------------------------------
    // es_ardais_order columns
    //------------------------------------------------------------
    public final static String ORDER_NUMBER = "order_number";
    public final static String ORDER_DESCRIPTION = "order_description";
    public final static String ORDER_STATUS = "order_status";
    public final static String ORDER_USER_ID = "ardais_user_id";

    //------------------------------------------------------------
    // es_order_line columns
    //------------------------------------------------------------
    public final static String LINE_ORDER_NUMBER = "order_number";
    public final static String LINE_SAMPLE_ID = "barcode_id";

    //------------------------------------------------------------
    // es_ardais_user columns
    //------------------------------------------------------------
    public final static String ARDAIS_USER_ID = "ardais_user_id";
    public final static String USER_ACCT_KEY = "ardais_acct_key";
    public final static String ARDAIS_USER_USER_FIRSTNAME = "user_firstname";
    public final static String ARDAIS_USER_USER_LASTNAME = "user_lastname";
    public final static String ARDAIS_USER_USER_ACTIVE_IND = "user_active_ind";

    //------------------------------------------------------------
    // ard_logical_repos columns
    //------------------------------------------------------------
    public final static String LOGICAL_REPOS_ID = "id";
    public final static String LOGICAL_REPOS_FULL_NAME = "full_name";
    public final static String LOGICAL_REPOS_SHORT_NAME = "short_name";
    public final static String LOGICAL_REPOS_BMS_YN = "bms_yn";

    //------------------------------------------------------------
    // ard_logical_repos_item columns
    //------------------------------------------------------------
    public final static String LOGICAL_REPOS_ITEM_ID = "id";
    public final static String LOGICAL_REPOS_ITEM_REPOSITORY_ID = "repository_id";
    public final static String LOGICAL_REPOS_ITEM_ITEM_ID = "item_id";
    public final static String LOGICAL_REPOS_ITEM_ITEM_TYPE = "item_type";

    //------------------------------------------------------------
    // ard_logical_repos_user columns
    //------------------------------------------------------------
    public final static String LOGICAL_REPOS_USER_ID = "id";
    public final static String LOGICAL_REPOS_USER_REPOSITORY_ID = "repository_id";
    public final static String LOGICAL_REPOS_USER_USER_ID = "ardais_user_id";

    //------------------------------------------------------------
    // iltds_request columns
    //------------------------------------------------------------
    public final static String REQUEST_REQUEST_ID = "request_id";
    public final static String REQUEST_CREATE_DATE = "create_date";
    public final static String REQUEST_STATE = "state";
    public final static String REQUEST_CLOSED_YN = "closed_yn";
    public final static String REQUEST_REQUESTER_USER_ID = "requester_user_id";
    public final static String REQUEST_REQUESTER_COMMENTS = "requester_comments";
    public final static String REQUEST_DESTINATION_ID = "destination_id";
    public final static String REQUEST_REQUEST_MGR_COMMENTS = "request_mgr_comments";
    public final static String REQUEST_REQUEST_TYPE = "request_type";
    public final static String REQUEST_DELIVER_TO_USER_ID = "deliver_to_user_id";
    public final static String REQUEST_SHIP_TO_ADDR_ID = "ship_to_addr_id";

    //------------------------------------------------------------
    // iltds_request_box columns
    //------------------------------------------------------------
    public final static String REQUEST_BOX_ID = "id";
    public final static String REQUEST_BOX_REQUEST_ID = "request_id";
    public final static String REQUEST_BOX_BOX_BARCODE_ID = "box_barcode_id";
    public final static String REQUEST_BOX_BOX_ORDER = "box_order";
    public final static String REQUEST_BOX_SHIPPED_YN = "shipped_yn";
    public final static String REQUEST_BOX_BOX_CONTENTS = "box_contents";

    //------------------------------------------------------------
    // iltds_request_item columns
    //------------------------------------------------------------
    public final static String REQUEST_ITEM_ID = "id";
    public final static String REQUEST_ITEM_REQUEST_ID = "request_id";
    public final static String REQUEST_ITEM_ITEM_ID = "item_id";
    public final static String REQUEST_ITEM_ITEM_TYPE = "item_type";
    public final static String REQUEST_ITEM_ITEM_ORDER = "item_order";
    public final static String REQUEST_ITEM_FULFILLED_YN = "fulfilled_yn";
    public final static String REQUEST_ITEM_BOX_BARCODE_ID = "box_barcode_id";

    //------------------------------------------------------------
    // ard_urls columns
    //------------------------------------------------------------
    public final static String ARD_URLS_ID = "id";
    public final static String ARD_URLS_ACCT_KEY = "ardais_acct_key";
    public final static String ARD_URLS_OBJECT_TYPE = "object_type";
    public final static String ARD_URLS_URL = "url";
    public final static String ARD_URLS_LABEL = "label";
    public final static String ARD_URLS_TARGET = "target";

    //------------------------------------------------------------
    // pdc_path_report_diagnostics columns
    //------------------------------------------------------------
    public final static String DIAGNOSTICS_PATH_REPORT_ID = "path_report_id";
    public final static String DIAGNOSTICS_CONCEPT_ID = "diagnostics_concept_id";
    public final static String DIAGNOSTIC_RESULTS_CID = "diagnostic_results_cid";
    public final static String DIAGNOSTIC_TYPE = "diagnostic_type";

    //------------------------------------------------------------
    // ard_policy columns
    //------------------------------------------------------------
    public final static String POLICY_ID = "id";
    public final static String POLICY_NAME = "name";
    public final static String POLICY_ALLOCATION_DENOMINATOR = "allocation_denominator";
    public final static String POLICY_ALLOCATION_NUMERATOR = "allocation_numerator";
    public final static String POLICY_ALLOCATION_FORMAT_CID = "allocation_format_cid";
    public final static String POLICY_DEFAULT_LOGICAL_REPOS_ID = "default_logical_repos_id";
    public final static String POLICY_RESTRICTED_LOGICAL_REPOS_ID = "restricted_logical_repos_id";
    public final static String POLICY_CONSENT_VERIFY_REQUIRED = "consent_verify_required_yn";
    public final static String POLICY_CASE_RELEASE_REQUIRED = "case_release_required_yn";
    public final static String POLICY_ADDITIONAL_QUESTIONS_GRP = "additional_questions_grpcode";
    public final static String POLICY_ARDAIS_ACCT_KEY = "ardais_acct_key";
    public final static String POLICY_ALLOW_FOR_UNLINKED_YN = "allow_for_unlinked_yn";
    public final static String POLICY_POLICY_DATA_ENCODING = "policy_data_encoding";
    public final static String POLICY_POLICY_DATA = "policy_data";
    public final static String POLICY_CASE_REGISTRATION_FORM = "case_registration_form";

    //------------------------------------------------------------
    // es_ardais_irb columns
    //------------------------------------------------------------
    public final static String IRB_ARDAIS_ACCT_KEY = "ardais_acct_key";
    public final static String IRB_PROTOCOL_ID = "irbprotocol_id";
    public final static String IRB_PROTOCOL = "irbprotocol";
    public final static String IRB_POLICY_ID = "policy_id";

    //------------------------------------------------------------
    // iltds_box_layout columns
    //------------------------------------------------------------
    public final static String LY_BOX_LAYOUT_ID = "box_layout_id";
    public final static String LY_NUMBER_OF_COLUMNS = "number_of_columns";
    public final static String LY_NUMBER_OF_ROWS = "number_of_rows";
    public final static String LY_X_AXIS_LABEL_CID = "x_axis_label_cid";
    public final static String LY_Y_AXIS_LABEL_CID = "y_axis_label_cid";
    public final static String LY_TAB_INDEX_CID = "tab_index_cid";

    //------------------------------------------------------------
    // iltds_account_box_layout columns
    //------------------------------------------------------------
    public final static String ACCT_LY_ID = "id";
    public final static String ACCT_LY_BOX_LAYOUT_NAME = "box_layout_name";
    public final static String ACCT_LY_ARDAIS_ACCT_KEY = "ardais_acct_key";
    public final static String ACCT_LY_BOX_LAYOUT_ID = "box_layout_id";

    //------------------------------------------------------------
    // es_ardais_account columns
    //------------------------------------------------------------
    public final static String ACCOUNT_KEY = "ardais_acct_key";
    public final static String ACCOUNT_TYPE = "ardais_acct_type";
    public final static String ACCOUNT_STATUS = "ardais_acct_status";
    public final static String ACCOUNT_NAME = "ardais_acct_company_desc";
    public final static String ACCOUNT_PARENT_NAME = "ardais_parent_acct_comp_desc";
    public final static String ACCOUNT_OPEN_DATE = "ardais_acct_open_date";
    public final static String ACCOUNT_ACTIVE_DATE = "ardais_acct_active_date";
    public final static String ACCOUNT_CLOSE_DATE = "ardais_acct_close_date";
    public final static String ACCOUNT_DEACTIVATE_DATE = "ardais_acct_deactivate_date";
    public final static String ACCOUNT_DEACTIVATE_REASON = "ardais_acct_deactivate_reason";
    public final static String ACCOUNT_REACTIVATE_DATE = "ardais_acct_reactivate_date";
    public final static String ACCOUNT_PRIMARY_LOCATION = "primary_location";
    public final static String ACCOUNT_BRAND_ID = "brand_id";
    public final static String ACCOUNT_REQ_MGR_EMAIL = "request_mgr_email_address";
    public final static String ACCOUNT_LINKED_CASES_ONLY_YN = "linked_cases_only_yn";
    public final static String ACCOUNT_PASSWORD_POLICY_CID = "password_policy_cid";
    public final static String ACCOUNT_PASSWORD_LIFE_SPAN = "password_life_span";
    public final static String ACCOUNT_DEFAULT_BOX_LAYOUT_ID = "default_box_layout_id";
    public final static String ACCOUNT_ACCOUNT_DATA = "account_data";
    public final static String ACCOUNT_ACCOUNT_DATA_ENCODING = "account_data_encoding";
    public final static String ACCOUNT_CONTACT_PHONE_NUMBER = "contact_phone_number";
    public final static String ACCOUNT_CONTACT_PHONE_EXT = "contact_phone_ext";
    public final static String ACCOUNT_CONTACT_FAX_NUMBER = "contact_fax_number";
    public final static String ACCOUNT_CONTACT_EMAIL_ADDRESS = "contact_email_address";
    public final static String ACCOUNT_CONTACT_ADDRESS_ID = "contact_address_id";
    public final static String ACCOUNT_CONTACT_MOBILE_NUMBER = "contact_mobile_number";
    public final static String ACCOUNT_CONTACT_PAGER_NUMBER = "contact_pager_number";
    public final static String ACCOUNT_DONOR_REGISTRATION_FORM = "donor_registration_form";
    public final static String ACCOUNT_SAMPLE_ALIASES_UNIQUE_YN = "sample_aliases_unique_yn";
    public final static String ACCOUNT_SAMPLE_ALIASES_REQUIRED_YN = "sample_aliases_required_yn";

    //------------------------------------------------------------
    // ardais_address columns
    //------------------------------------------------------------
    public final static String ADDRESS_ADDRESS_ID = "address_id";
    public final static String ADDRESS_ACCOUNT_KEY = "ardais_acct_key";
    public final static String ADDRESS_ADDRESS_TYPE = "address_type";
    public final static String ADDRESS_ADDRESS_1 = "address_1";
    public final static String ADDRESS_ADDRESS_2 = "address_2";
    public final static String ADDRESS_CITY = "addr_city";
    public final static String ADDRESS_STATE = "addr_state";
    public final static String ADDRESS_ZIP_CODE = "addr_zip_code";
    public final static String ADDRESS_COUNTRY = "addr_country";
    public final static String ADDRESS_FIRST_NAME = "first_name";
    public final static String ADDRESS_LAST_NAME = "last_name";
    public final static String ADDRESS_MIDDLE_NAME = "middle_name";

    //------------------------------------------------------------
    // iltds_shipping_partners columns
    //------------------------------------------------------------
    public final static String SHIPPING_PARTNER_ID = "id";
    public final static String SHIPPING_PARTNER_ARDAIS_ACCT_KEY = "ardais_acct_key";
    public final static String SHIPPING_PARTNER_DESTINATION_ID = "destination_id";

    //------------------------------------------------------------
    // iltds_geography_location columns
    //------------------------------------------------------------
    public final static String LOCATION_ADDRESS_ID = "location_address_id";
    public final static String LOCATION_NAME = "location_name";
    public final static String LOCATION_ADDRESS_1 = "location_address_1";
    public final static String LOCATION_ADDRESS_2 = "location_address_2";
    public final static String LOCATION_CITY = "location_city";
    public final static String LOCATION_STATE = "location_state";
    public final static String LOCATION_ZIP = "location_zip";
    public final static String LOCATION_COUNTRY = "location_country";
    public final static String LOCATION_PHONE = "location_phone";

    //------------------------------------------------------------
    // iltds_sample_box columns
    //------------------------------------------------------------
    public final static String SAMPLE_BOX_BOX_BARCODE_ID = "box_barcode_id";
    public final static String SAMPLE_BOX_MANIFEST_NUMBER = "manifest_number";
    public final static String SAMPLE_BOX_CHECK_IN_DATE = "box_check_in_date";
    public final static String SAMPLE_BOX_CHECK_OUT_DATE = "box_check_out_date";
    public final static String SAMPLE_BOX_CHECK_OUT_REASON = "box_check_out_reason";
    public final static String SAMPLE_BOX_CHECK_OUT_STAFF_ID = "box_check_out_request_staff_id";
    public final static String SAMPLE_BOX_STATUS = "box_status";
    public final static String SAMPLE_BOX_NOTE = "box_note";
    public final static String SAMPLE_BOX_MANIFEST_ORDER = "manifest_order";
    public final static String SAMPLE_BOX_LAYOUT_ID = "box_layout_id";
    public final static String SAMPLE_BOX_STORAGE_TYPE_CID = "storage_type_cid";

    //------------------------------------------------------------
    // es_ardais_consentver columns
    //------------------------------------------------------------
    public final static String CONSENTVER_IRBPROTOCOL_ID = "irbprotocol_id";
    public final static String CONSENT_VERSION_ID = "consent_version_id";
    public final static String CONSENT_VERSION = "consent_version";

    //------------------------------------------------------------
    // bigr_role columns
    //------------------------------------------------------------
    public final static String BIGR_ROLE_ID = "id";
    public final static String BIGR_ROLE_NAME = "name";
    public final static String BIGR_ROLE_ACCOUNT_ID = "account_id";

    //------------------------------------------------------------
    // bigr_role_privilege columns
    //------------------------------------------------------------
    public final static String BIGR_ROLE_PRIVILEGE_ID = "id";
    public final static String BIGR_ROLE_PRIVILEGE_ROLE_ID = "role_id";
    public final static String BIGR_ROLE_PRIVILEGE_PRIVILEGE_ID = "privilege_id";

    //------------------------------------------------------------
    // bigr_role_user columns
    //------------------------------------------------------------
    public final static String BIGR_ROLE_USER_ID = "id";
    public final static String BIGR_ROLE_USER_ROLE_ID = "role_id";
    public final static String BIGR_ROLE_USER_USER_ID = "user_id";

}