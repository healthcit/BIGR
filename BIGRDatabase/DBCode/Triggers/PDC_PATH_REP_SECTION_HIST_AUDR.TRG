CREATE OR REPLACE TRIGGER pdc_path_rep_section_hist_audr
after update or delete of
   PATH_REPORT_SECTION_ID,
   PATH_REPORT_ID,
   CONSENT_ID,
   PRIMARY_IND,
   SECTION_IDENTIFIER,
   CREATE_DATE,
   LAST_UPDATE_DATE,
   CREATE_USER,
   LAST_UPDATE_USER,
   TOTAL_NODES_POSITIVE,
   TOTAL_NODES_EXAMINED,
   TOTAL_GLEASON_SCORE,
   PRIMARY_GLEASON_SCORE,
   SECONDARY_GLEASON_SCORE,
   EXTRANODAL_SPREAD,
   PERINEURAL_INVASION_IND,
   DIAGNOSIS_CONCEPT_ID,
   TISSUE_ORIGIN_CONCEPT_ID,
   TISSUE_FINDING_CONCEPT_ID,
   LYMPH_NODE_STAGE,
   HISTOLOGICAL_NUCLEAR_GRADE,
   VENOUS_VESSEL_INVASION,
   LYMPHATIC_VASCULAR_INVASION,
   EXTENSIVE_INTRADUCTAL_COMP,
   INFLAMM_CELL_INFILTRATE,
   CELL_INFILTRATE_AMT,
   SIZE_LARGEST_NODAL_METS,
   JOINT_COMPONENT,
   TUMOR_CONFIGURATION,
   TUMOR_SIZE,
   TUMOR_WEIGHT,
   TUMOR_STAGE_DESC,
   TUMOR_STAGE_TYPE,
   MARGINS_INVOLVED_DESC,
   MARGINS_UNINVOLVED_DESC,
   HISTOLOGICAL_TYPE,
   OTHER_DX_NAME,
   OTHER_ORIGIN_TC_NAME,
   OTHER_FINDING_TC_NAME,
   LYMPH_NODE_NOTES,
   DISTANT_METASTASIS,
   DISTANT_METASTASIS_IND,
   LYMPH_NODE_STAGE_IND,
   TUMOR_STAGE_DESC_IND,
   STAGE_GROUPING,
   OTHER_STAGE_GROUPING,
   OTHER_LYMPH_NODE_STAGE,
   OTHER_DISTANT_METASTASIS,
   OTHER_HISTOLOGICAL_TYPE,
   OTHER_TUMOR_STAGE_TYPE,
   OTHER_TUMOR_STAGE_DESC,
   OTHER_HISTO_NUCLEAR_GRADE
on pdc_path_report_section
for each row
declare
   aud_type	 	varchar2(6);
   v_section_identifier_desc      PDC_PATH_REPORT_SECTION_HIST.SECTION_IDENTIFIER_DESC%TYPE; 
   v_total_nodes_positive_desc    PDC_PATH_REPORT_SECTION_HIST.TOTAL_NODES_POSITIVE_DESC%TYPE; 
   v_total_nodes_examined_desc    PDC_PATH_REPORT_SECTION_HIST.TOTAL_NODES_EXAMINED_DESC%TYPE; 
   v_extranodal_spread_desc       PDC_PATH_REPORT_SECTION_HIST.EXTRANODAL_SPREAD_DESC%TYPE; 
   v_perineural_invasion_ind_desc PDC_PATH_REPORT_SECTION_HIST.PERINEURAL_INVASION_IND_DESC%TYPE; 
   v_lymph_node_stage_desc        PDC_PATH_REPORT_SECTION_HIST.LYMPH_NODE_STAGE_DESC%TYPE; 
   v_hist_nuclear_grade_desc      PDC_PATH_REPORT_SECTION_HIST.HISTOLOGIC_NUCLEAR_GRADE_DESC%TYPE; 
   v_histological_type_desc       PDC_PATH_REPORT_SECTION_HIST.HISTOLOGICAL_TYPE_DESC%TYPE; 
   v_venous_vessel_invasion_desc  PDC_PATH_REPORT_SECTION_HIST.VENOUS_VESSEL_INVASION_DESC%TYPE; 
   v_lymph_vascular_invasion_desc PDC_PATH_REPORT_SECTION_HIST.LYMPH_VASCULAR_INVASION_DESC%TYPE; 
   v_exten_intraductal_comp_desc  PDC_PATH_REPORT_SECTION_HIST.EXTEN_INTRADUCTAL_COMP_DESC%TYPE; 
   v_inflamm_cell_infiltrate_desc PDC_PATH_REPORT_SECTION_HIST.INFLAMM_CELL_INFILTRATE_DESC%TYPE; 
   v_cell_infiltrate_amt_desc     PDC_PATH_REPORT_SECTION_HIST.CELL_INFILTRATE_AMT_DESC%TYPE; 
   v_joint_component_desc         PDC_PATH_REPORT_SECTION_HIST.JOINT_COMPONENT_DESC%TYPE; 
   v_tumor_configuration_desc     PDC_PATH_REPORT_SECTION_HIST.TUMOR_CONFIGURATION_DESC%TYPE; 
   v_stage_grouping_desc          PDC_PATH_REPORT_SECTION_HIST.STAGE_GROUPING_DESC%TYPE; 
   v_distant_metastasis_desc      PDC_PATH_REPORT_SECTION_HIST.DISTANT_METASTASIS_DESC%TYPE; 
   v_diagnosis_concept_id_desc    PDC_PATH_REPORT_SECTION_HIST.DIAGNOSIS_CONCEPT_ID_DESC%TYPE; 
   v_tissue_orig_concept_id_desc  PDC_PATH_REPORT_SECTION_HIST.TISSUE_ORIGIN_CONCEPT_ID_DESC%TYPE; 
   v_tissue_find_concept_id_desc  PDC_PATH_REPORT_SECTION_HIST.TISSUE_FINDING_CONCEPT_ID_DESC%TYPE; 
   v_tumor_stage_desc_desc        PDC_PATH_REPORT_SECTION_HIST.TUMOR_STAGE_DESC_DESC%TYPE; 
   v_tumor_stage_type_desc        PDC_PATH_REPORT_SECTION_HIST.TUMOR_STAGE_TYPE_DESC%TYPE; 
begin
   --get the descriptions for the ARTS enabled columns
   v_section_identifier_desc := GET_DESC_FROM_PDC_LOOKUP(:old.SECTION_IDENTIFIER);
   --DBMS_OUTPUT.Put_Line('Description for SECTION_IDENTIFIER (' || :old.SECTION_IDENTIFIER || ') is ' || v_section_identifier_desc);
   v_total_nodes_positive_desc := GET_DESC_FROM_PDC_LOOKUP(:old.TOTAL_NODES_POSITIVE);
   --DBMS_OUTPUT.Put_Line('Description for TOTAL_NODES_POSITIVE (' || :old.TOTAL_NODES_POSITIVE || ') is ' || v_total_nodes_positive_desc);
   v_total_nodes_examined_desc := GET_DESC_FROM_PDC_LOOKUP(:old.TOTAL_NODES_EXAMINED);
   --DBMS_OUTPUT.Put_Line('Description for TOTAL_NODES_EXAMINED (' || :old.TOTAL_NODES_EXAMINED || ') is ' || v_total_nodes_examined_desc);
   v_extranodal_spread_desc := GET_DESC_FROM_PDC_LOOKUP(:old.EXTRANODAL_SPREAD);
   --DBMS_OUTPUT.Put_Line('Description for EXTRANODAL_SPREAD (' || :old.EXTRANODAL_SPREAD || ') is ' || v_extranodal_spread_desc);
   v_perineural_invasion_ind_desc := GET_DESC_FROM_PDC_LOOKUP(:old.PERINEURAL_INVASION_IND);
   --DBMS_OUTPUT.Put_Line('Description for PERINEURAL_INVASION_IND (' || :old.PERINEURAL_INVASION_IND || ') is ' || v_perineural_invasion_ind_desc);
   v_lymph_node_stage_desc := GET_DESC_FROM_PDC_LOOKUP(:old.LYMPH_NODE_STAGE);
   --DBMS_OUTPUT.Put_Line('Description for LYMPH_NODE_STAGE (' || :old.LYMPH_NODE_STAGE || ') is ' || v_lymph_node_stage_desc);
   v_hist_nuclear_grade_desc := GET_DESC_FROM_PDC_LOOKUP(:old.HISTOLOGICAL_NUCLEAR_GRADE);
   --DBMS_OUTPUT.Put_Line('Description for HISTOLOGICAL_NUCLEAR_GRADE (' || :old.HISTOLOGICAL_NUCLEAR_GRADE || ') is ' || v_hist_nuclear_grade_desc);
   v_histological_type_desc := GET_DESC_FROM_PDC_LOOKUP(:old.HISTOLOGICAL_TYPE);
   --DBMS_OUTPUT.Put_Line('Description for HISTOLOGICAL_TYPE (' || :old.HISTOLOGICAL_TYPE || ') is ' || v_histological_type_desc);
   v_venous_vessel_invasion_desc := GET_DESC_FROM_PDC_LOOKUP(:old.VENOUS_VESSEL_INVASION);
   --DBMS_OUTPUT.Put_Line('Description for VENOUS_VESSEL_INVASION (' || :old.VENOUS_VESSEL_INVASION || ') is ' || v_venous_vessel_invasion_desc);
   v_lymph_vascular_invasion_desc := GET_DESC_FROM_PDC_LOOKUP(:old.LYMPHATIC_VASCULAR_INVASION);
   --DBMS_OUTPUT.Put_Line('Description for LYMPHATIC_VASCULAR_INVASION (' || :old.LYMPHATIC_VASCULAR_INVASION || ') is ' || v_lymph_vascular_invasion_desc);
   v_exten_intraductal_comp_desc := GET_DESC_FROM_PDC_LOOKUP(:old.EXTENSIVE_INTRADUCTAL_COMP);
   --DBMS_OUTPUT.Put_Line('Description for EXTENSIVE_INTRADUCTAL_COMP (' || :old.EXTENSIVE_INTRADUCTAL_COMP || ') is ' || v_exten_intraductal_comp_desc);
   v_inflamm_cell_infiltrate_desc := GET_DESC_FROM_PDC_LOOKUP(:old.INFLAMM_CELL_INFILTRATE);
   --DBMS_OUTPUT.Put_Line('Description for INFLAMM_CELL_INFILTRATE (' || :old.INFLAMM_CELL_INFILTRATE || ') is ' || v_inflamm_cell_infiltrate_desc);
   v_cell_infiltrate_amt_desc := GET_DESC_FROM_PDC_LOOKUP(:old.CELL_INFILTRATE_AMT);
   --DBMS_OUTPUT.Put_Line('Description for CELL_INFILTRATE_AMT (' || :old.CELL_INFILTRATE_AMT || ') is ' || v_cell_infiltrate_amt_desc);
   v_joint_component_desc := GET_DESC_FROM_PDC_LOOKUP(:old.JOINT_COMPONENT);
   --DBMS_OUTPUT.Put_Line('Description for JOINT_COMPONENT (' || :old.JOINT_COMPONENT || ') is ' || v_joint_component_desc);
   v_tumor_configuration_desc := GET_DESC_FROM_PDC_LOOKUP(:old.TUMOR_CONFIGURATION);
   --DBMS_OUTPUT.Put_Line('Description for TUMOR_CONFIGURATION (' || :old.TUMOR_CONFIGURATION || ') is ' || v_tumor_configuration_desc);
   v_stage_grouping_desc := GET_DESC_FROM_PDC_LOOKUP(:old.STAGE_GROUPING);
   --DBMS_OUTPUT.Put_Line('Description for STAGE_GROUPING (' || :old.STAGE_GROUPING || ') is ' || v_stage_grouping_desc);
   v_distant_metastasis_desc := GET_DESC_FROM_PDC_LOOKUP(:old.DISTANT_METASTASIS);
   --DBMS_OUTPUT.Put_Line('Description for DISTANT_METASTASIS (' || :old.DISTANT_METASTASIS || ') is ' || v_distant_metastasis_desc);
   v_diagnosis_concept_id_desc := GET_DESC_FROM_DX_TC_HIERARCHY(:old.DIAGNOSIS_CONCEPT_ID);
   --DBMS_OUTPUT.Put_Line('Description for DIAGNOSIS_CONCEPT_ID (' || :old.DIAGNOSIS_CONCEPT_ID || ') is ' || v_diagnosis_concept_id_desc);
   v_tissue_orig_concept_id_desc := GET_DESC_FROM_DX_TC_HIERARCHY(:old.TISSUE_ORIGIN_CONCEPT_ID);
   --DBMS_OUTPUT.Put_Line('Description for TISSUE_ORIGIN_CONCEPT_ID (' || :old.TISSUE_ORIGIN_CONCEPT_ID || ') is ' || v_tissue_orig_concept_id_desc);
   v_tissue_find_concept_id_desc := GET_DESC_FROM_DX_TC_HIERARCHY(:old.TISSUE_FINDING_CONCEPT_ID);
   --DBMS_OUTPUT.Put_Line('Description for TISSUE_FINDING_CONCEPT_ID (' || :old.TISSUE_FINDING_CONCEPT_ID || ') is ' || v_tissue_find_concept_id_desc);
   v_tumor_stage_desc_desc := GET_DESC_FROM_PDC_LOOKUP(:old.TUMOR_STAGE_DESC);
   --DBMS_OUTPUT.Put_Line('Description for TUMOR_STAGE_DESC (' || :old.TUMOR_STAGE_DESC || ') is ' || v_tumor_stage_desc_desc);
   v_tumor_stage_type_desc := GET_DESC_FROM_PDC_LOOKUP(:old.TUMOR_STAGE_TYPE);
   --DBMS_OUTPUT.Put_Line('Description for TUMOR_STAGE_TYPE (' || :old.TUMOR_STAGE_TYPE || ') is ' || v_tumor_stage_type_desc);
   if (deleting)
   then
      aud_type := 'DELETE';
   elsif (updating)
   then
      aud_type := 'UPDATE';
   end if;
   insert into pdc_path_report_section_hist
   (AUDIT_HISTORY_ID,
   AUDIT_DATE,
   AUDIT_TYPE,
   PATH_REPORT_SECTION_ID,
   PATH_REPORT_ID,
   CONSENT_ID,
   PRIMARY_IND,
   SECTION_IDENTIFIER,
   SECTION_IDENTIFIER_DESC,
   CREATE_DATE,
   LAST_UPDATE_DATE,
   CREATE_USER,
   LAST_UPDATE_USER,
   TOTAL_NODES_POSITIVE,
   TOTAL_NODES_POSITIVE_DESC,
   TOTAL_NODES_EXAMINED,
   TOTAL_NODES_EXAMINED_DESC,
   TOTAL_GLEASON_SCORE,
   PRIMARY_GLEASON_SCORE,
   SECONDARY_GLEASON_SCORE,
   EXTRANODAL_SPREAD,
   EXTRANODAL_SPREAD_DESC,
   PERINEURAL_INVASION_IND,
   PERINEURAL_INVASION_IND_DESC,
   DIAGNOSIS_CONCEPT_ID,
   DIAGNOSIS_CONCEPT_ID_DESC,
   TISSUE_ORIGIN_CONCEPT_ID,
   TISSUE_ORIGIN_CONCEPT_ID_DESC,
   TISSUE_FINDING_CONCEPT_ID,
   TISSUE_FINDING_CONCEPT_ID_DESC,
   LYMPH_NODE_STAGE,
   LYMPH_NODE_STAGE_DESC,
   HISTOLOGICAL_NUCLEAR_GRADE,
   HISTOLOGIC_NUCLEAR_GRADE_DESC,
   VENOUS_VESSEL_INVASION,
   VENOUS_VESSEL_INVASION_DESC,
   LYMPHATIC_VASCULAR_INVASION,
   LYMPH_VASCULAR_INVASION_DESC,
   EXTENSIVE_INTRADUCTAL_COMP,
   EXTEN_INTRADUCTAL_COMP_DESC,
   INFLAMM_CELL_INFILTRATE,
   INFLAMM_CELL_INFILTRATE_DESC,
   CELL_INFILTRATE_AMT,
   CELL_INFILTRATE_AMT_DESC,
   SIZE_LARGEST_NODAL_METS,
   JOINT_COMPONENT,
   JOINT_COMPONENT_DESC,
   TUMOR_CONFIGURATION,
   TUMOR_CONFIGURATION_DESC,
   TUMOR_SIZE,
   TUMOR_WEIGHT,
   TUMOR_STAGE_DESC,
   TUMOR_STAGE_DESC_DESC,
   TUMOR_STAGE_TYPE,
   TUMOR_STAGE_TYPE_DESC,
   MARGINS_INVOLVED_DESC,
   MARGINS_UNINVOLVED_DESC,
   HISTOLOGICAL_TYPE,
   HISTOLOGICAL_TYPE_DESC,
   OTHER_DX_NAME,
   OTHER_ORIGIN_TC_NAME,
   OTHER_FINDING_TC_NAME,
   LYMPH_NODE_NOTES,
   PATH_SECTION_NOTES,
   DISTANT_METASTASIS,
   DISTANT_METASTASIS_DESC,
   DISTANT_METASTASIS_IND,
   LYMPH_NODE_STAGE_IND,
   TUMOR_STAGE_DESC_IND,
   STAGE_GROUPING,
   STAGE_GROUPING_DESC,
   OTHER_STAGE_GROUPING,
   OTHER_LYMPH_NODE_STAGE,
   OTHER_DISTANT_METASTASIS,
   OTHER_HISTOLOGICAL_TYPE,
   OTHER_TUMOR_STAGE_TYPE,
   OTHER_TUMOR_STAGE_DESC,
   OTHER_HISTO_NUCLEAR_GRADE)
VALUES
   (pdc_path_rep_section_hist_seq.nextval,
   sysdate,
   aud_type,
   :old.PATH_REPORT_SECTION_ID,
   :old.PATH_REPORT_ID,
   :old.CONSENT_ID,
   :old.PRIMARY_IND,
   :old.SECTION_IDENTIFIER,
   v_section_identifier_desc,
   :old.CREATE_DATE,
   :old.LAST_UPDATE_DATE,
   :old.CREATE_USER,
   :old.LAST_UPDATE_USER,
   :old.TOTAL_NODES_POSITIVE,
   v_total_nodes_positive_desc,
   :old.TOTAL_NODES_EXAMINED,
   v_total_nodes_examined_desc,
   :old.TOTAL_GLEASON_SCORE,
   :old.PRIMARY_GLEASON_SCORE,
   :old.SECONDARY_GLEASON_SCORE,
   :old.EXTRANODAL_SPREAD,
   v_extranodal_spread_desc,
   :old.PERINEURAL_INVASION_IND,
   v_perineural_invasion_ind_desc,
   :old.DIAGNOSIS_CONCEPT_ID,
   v_diagnosis_concept_id_desc,
   :old.TISSUE_ORIGIN_CONCEPT_ID,
   v_tissue_orig_concept_id_desc,
   :old.TISSUE_FINDING_CONCEPT_ID,
   v_tissue_find_concept_id_desc,
   :old.LYMPH_NODE_STAGE,
   v_lymph_node_stage_desc,
   :old.HISTOLOGICAL_NUCLEAR_GRADE,
   v_hist_nuclear_grade_desc,
   :old.VENOUS_VESSEL_INVASION,
   v_venous_vessel_invasion_desc,
   :old.LYMPHATIC_VASCULAR_INVASION,
   v_lymph_vascular_invasion_desc,
   :old.EXTENSIVE_INTRADUCTAL_COMP,
   v_exten_intraductal_comp_desc,
   :old.INFLAMM_CELL_INFILTRATE,
   v_inflamm_cell_infiltrate_desc,
   :old.CELL_INFILTRATE_AMT,
   v_cell_infiltrate_amt_desc,
   :old.SIZE_LARGEST_NODAL_METS,
   :old.JOINT_COMPONENT,
   v_joint_component_desc,
   :old.TUMOR_CONFIGURATION,
   v_tumor_configuration_desc,
   :old.TUMOR_SIZE,
   :old.TUMOR_WEIGHT,
   :old.TUMOR_STAGE_DESC,
   v_tumor_stage_desc_desc,
   :old.TUMOR_STAGE_TYPE,
   v_tumor_stage_type_desc,
   :old.MARGINS_INVOLVED_DESC,
   :old.MARGINS_UNINVOLVED_DESC,
   :old.HISTOLOGICAL_TYPE,
   v_histological_type_desc,
   :old.OTHER_DX_NAME,
   :old.OTHER_ORIGIN_TC_NAME,
   :old.OTHER_FINDING_TC_NAME,
   :old.LYMPH_NODE_NOTES,
   :old.PATH_SECTION_NOTES,
   :old.DISTANT_METASTASIS,
   v_distant_metastasis_desc,
   :old.DISTANT_METASTASIS_IND,
   :old.LYMPH_NODE_STAGE_IND,
   :old.TUMOR_STAGE_DESC_IND,
   :old.STAGE_GROUPING,
   v_stage_grouping_desc,
   :old.OTHER_STAGE_GROUPING,
   :old.OTHER_LYMPH_NODE_STAGE,
   :old.OTHER_DISTANT_METASTASIS,
   :old.OTHER_HISTOLOGICAL_TYPE,
   :old.OTHER_TUMOR_STAGE_TYPE,
   :old.OTHER_TUMOR_STAGE_DESC,
   :old.OTHER_HISTO_NUCLEAR_GRADE);
EXCEPTION
  WHEN OTHERS THEN
    raise_application_error (-20011, 'An error occured while creating pdc_path_report_section_hist record: ' || sqlerrm);
end pdc_path_rep_section_hist_audr;
/
