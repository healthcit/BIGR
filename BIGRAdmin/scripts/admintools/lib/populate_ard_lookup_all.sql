SET DEFINE OFF;
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('FORMAT', 'FROZEN', 'FROZEN');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('FORMAT', 'PARAFFIN', 'PARAFFIN');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ORM', 'PDC', 'DDC');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ORM', 'ES', 'BIGR Library');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('SPECIMEN', 'N', 'Normal');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('SPECIMEN', 'M', 'Mixed');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('SPECIMEN', 'D', 'Diseased');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('SPECIMEN', 'U', 'Unknown');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ORM', 'ILTDS', 'ILTDS');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ORM', 'LIMS', 'LIMS');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ESORDER', 'PA', 'Pending Approval');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ESORDER', 'OP', 'Open');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ESORDER', 'IP', 'In Process');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ESORDER', 'WS', 'Waiting to be Shipped');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ESORDER', 'SH', 'Shipped');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ESORDER', 'PP', 'Pending Payment');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ESORDER', 'RC', 'Receipt Confirmed');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ESORDER', 'CL', 'Closed');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ESORDER', 'CA', 'Cancelled');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, REF)
 Values
   ('ES_ARDAIS_ACCOUNT', 'A', 'Active', 'Ardais_Acct_Status');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, REF)
 Values
   ('ES_ARDAIS_ACCOUNT', 'I', 'Inactive', 'Ardais_Acct_Status');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, REF)
 Values
   ('ES_ARDAIS_ACCOUNT', 'C', 'Cancel', 'Ardais_Acct_Status');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, REF)
 Values
   ('ARDAIS_ADDRESS', 'BT', 'Bill To', 'Address_Type');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, REF)
 Values
   ('ARDAIS_ADDRESS', 'ST', 'Ship To', 'Address_Type');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, REF)
 Values
   ('ARDAIS_ADDRESS', 'CT', 'Contact', 'Address_Type');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ORM', 'CO', 'Costing');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ORM', 'ORM', 'O&M');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('ORM', 'PC', 'Pricing');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('EMAIL', 'ILTDS_CR_FROM', 'webmaster@healthcit.com');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('EMAIL', 'ILTDS_CR_TO', 'terminology.manager@healthcit.com');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('EMAIL', 'PDC_CR_FROM', 'webmaster@healthcit.com');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('EMAIL', 'PDC_CR_TO', 'terminology.manager@healthcit.com');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'AD', 'Diagnosis');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'AP', 'Procedure');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'AT', 'Tissue');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'AM', 'Metastasis');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'AR', 'Treatment');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('EMAIL', 'ILTDS_PP_FROM', 'webmaster@healthcit.com');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('EMAIL', 'ILTDS_PP_TO', 'CustomerService@healthcit.com');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_PD', 'Project Definition', 1, 'WO_STATUS_PROJ_DEF');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_PV', 'Pathology Verification', 2, 'WO_STATUS_PV');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_DIV', 'Division', 3, 'WO_STATUS_DIV');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_TMA', 'TMA Construction', 4, 'WO_STATUS_TMA');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_RNA', 'RNA Construction', 5, 'WO_STATUS_RNA');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_SC', 'Slide Creation', 6, 'WO_STATUS_SLIDE');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_PR', 'Project Review', 7, 'WO_STATUS_PROJ_REV');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_POA', 'PO Assembly', 8, 'WO_STATUS_POA');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_SHIP', 'Shipment', 9, 'WO_STATUS_SHIP');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER, REF)
 Values
   ('WO_TYPE', 'WOTYPE_INV', 'Invoice', 10, 'WO_STATUS_INV');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('PROJECT_STATUS', 'PRJ_PRQ', 'Project Request', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('PROJECT_STATUS', 'PRJ_OC', 'Order Clarification', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('PROJECT_STATUS', 'PRJ_PD', 'Project Definition', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('PROJECT_STATUS', 'PRJ_BO', 'Biological Ops', 4);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('PROJECT_STATUS', 'PRJ_PRV', 'Project Review', 5);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('PROJECT_STATUS', 'PRJ_ACPO', 'Awaiting Customer PO', 6);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('PROJECT_STATUS', 'PRJ_POA', 'PO Assembly', 7);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('PROJECT_STATUS', 'PRJ_SHP', 'Shipment', 8);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('PROJECT_STATUS', 'PRJ_INV', 'Invoice', 9);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PROJ_DEF', 'WOPD_AD', 'Awaiting Definition', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PROJ_DEF', 'WOPD_IP', 'In Process', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PROJ_DEF', 'WOPD_ACF', 'Awaiting Client Feedback', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PROJ_DEF', 'WOPD_CONF', 'Confirmed', 4);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PROJ_DEF', 'WOPD_HLD', 'On Hold', 5);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PV', 'WOPV_SDH', 'Samples to be Delivered to Histology', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PV', 'WOPV_SDP', 'Slides to be Delivered to Pathology', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PV', 'WOPV_APV', 'Awaiting Pathology Verification', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PV', 'WOPV_AQCR', 'Awaiting QC Release', 4);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PV', 'WOPV_COMP', 'Complete', 5);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_DIV', 'WODIV_SDH', 'Samples to be Delivered to Histology', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_DIV', 'WODIV_SDP', 'Slides to be Delivered to Pathology', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_DIV', 'WODIV_SM', 'Slides to be Marked', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_DIV', 'WODIV_SRH', 'Slides to be Returned to Histology', 4);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_DIV', 'WODIV_SSUB', 'Samples to be Subdivided', 5);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_DIV', 'WODIV_SSB', 'Samples to be Sectioned and Barcoded', 6);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_DIV', 'WODIV_APV', 'Awaiting Pathology Verification', 7);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_DIV', 'WODIV_AQCR', 'Awaiting QC Release', 8);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_DIV', 'WODIV_COMP', 'Complete', 9);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_PS', 'Work Order Pending Subdivision', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_SDH', 'Samples to be Delivered to Histology', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_SDP', 'Slides to be Delivered to Pathology', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_SE', 'Slides to be Evaluated', 4);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_SRH', 'Slides to be Returned to Histology', 5);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_SRI', 'Samples Returned to Inventory', 6);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_AFQ', 'Awaiting Final Request', 7);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_DRD', 'Samples and Slides to be Delivered to R&D', 8);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_TMAC', 'TMA to be Created', 9);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_TMA', 'WOTMA_COMP', 'Complete', 10);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_RNA', 'WORNA_PPV', 'Work Order Pending Pathology Verification', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_RNA', 'WORNA_DRD', 'Samples to be Delivered to R&D', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_RNA', 'WORNA_RNAE', 'RNA to be Extracted', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_RNA', 'WORNA_COMP', 'Complete', 4);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_SLIDE', 'WOSLIDE_SDH', 'Samples to be Delivered to Histology', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_SLIDE', 'WOSLIDE_SLIDEC', 'Slides to be Created', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_SLIDE', 'WOSLIDE_COMP', 'Complete', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PROJ_REV', 'WOPR_AA', 'Awaiting Assembly', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PROJ_REV', 'WOPR_AQCR', 'Awaiting QC Review', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PROJ_REV', 'WOPR_AP', 'Awaiting Posting', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_PROJ_REV', 'WOPR_COMP', 'Complete', 4);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_POA', 'WOPO_ACA', 'Awaiting Client Approval', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_POA', 'WOPO_AA', 'Awaiting Assembly', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_POA', 'WOPO_COMP', 'Complete', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_SHIP', 'WOSHIP_AFC', 'Awaiting Final Check', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_SHIP', 'WOSHIP_AS', 'Awaiting Shipment', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_SHIP', 'WOSHIP_COMP', 'Complete', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_INV', 'WOINV_AIC', 'Awaiting Invoice Creation', 1);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_INV', 'WOINV_AP', 'Awaiting Payment', 2);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC, LOOKUP_CD_LIST_ORDER)
 Values
   ('WO_STATUS_INV', 'WOINV_COMP', 'Complete', 3);
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'GL', 'Lesion');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'GT', 'Tumor');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'GC', 'Cellular');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'GH', 'Hypo Acellular');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'GI', 'Inflammation');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'GS', 'Structure');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'CM', 'Distant Metastasis');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'CN', 'Histological Nuclear Grade');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'CH', 'Histological Type');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'CL', 'Lymph Node Stage Desc');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'CG', 'Stage Groupings');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'CS', 'Tumor Stage Desc');
Insert into ARD_LOOKUP_ALL
   (CATEGORY, LOOKUP_CD, LOOKUP_CD_DESC)
 Values
   ('TYPE_CODE', 'CT', 'Tumor Stage Type');