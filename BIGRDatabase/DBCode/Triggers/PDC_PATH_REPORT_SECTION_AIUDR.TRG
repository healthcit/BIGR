CREATE OR REPLACE TRIGGER PDC_PATH_REPORT_SECTION_AIUDR
 AFTER INSERT OR UPDATE OR DELETE ON PDC_PATH_REPORT_SECTION
FOR EACH ROW
DECLARE
  n_primary_section_id pdc_pathology_report.primary_path_report_section_id%type;
BEGIN
  -- Copy the diagnosis concept id, other dx name, origin tissue concept id, 
  -- other origin tc name, and path section id to the parent pathology report, 
  -- and set the ddc check flag in ILTDS informed consent, if this is the primary 
  -- path section.
  -- The following logic applies:
  --   1. If the row is being inserted and it is the primary section, then
  --      copy its values to the parent and set the ddc check flag.
  --   2. If the row is being updated and it is the primary section, then
  --      set the ddc check flag.
  --   3. If the row is being updated and it is the primary section, then
  --      copy its values to the parent, only if at least one of them has changed.
  --   4. If the row is being updated and it is not the primary section but was
  --      previously the primary section, or the row is being deleted and it
  --      is the primary section:
  --      4a. If the path report's primary section id is the id of the row, then
  --          set the path report's values to null and update the ddc check flag to 'N'.
  --      4b. If the path report's primary section id is not the id of the row, then
  --          do nothing.

  IF (:new.primary_ind = 'Y') THEN

    -- 1.
    IF (INSERTING) THEN
      ddc.replicate_primary_dx_tc(:new.path_report_id, :new.path_report_section_id, :new.diagnosis_concept_id, :new.other_dx_name, :new.tissue_origin_concept_id, :new.other_origin_tc_name);	  
	  
	  ddc.set_ddc_check_flag(:new.path_report_id,
							 :new.diagnosis_concept_id,
               				 :new.tissue_origin_concept_id,
							 :new.tissue_finding_concept_id,
							 :new.tumor_size,
							 :new.tumor_weight,
							 :new.histological_type,
							 :new.histological_nuclear_grade,
							 :new.tumor_stage_type,
							 :new.tumor_stage_desc,
							 :new.lymph_node_stage,
							 :new.total_nodes_positive,
							 :new.total_nodes_examined,
							 :new.extensive_intraductal_comp,
							 :new.primary_gleason_score,
							 :new.secondary_gleason_score,
							 :new.total_gleason_score,
							 :new.perineural_invasion_ind,
							 :new.joint_component,
							 :new.distant_metastasis,
							 :new.stage_grouping);
								 
	ELSIF (UPDATING) THEN
	  -- 2.	  
	  ddc.set_ddc_check_flag(:new.path_report_id,
							 :new.diagnosis_concept_id,
               				 :new.tissue_origin_concept_id,
							 :new.tissue_finding_concept_id,
							 :new.tumor_size,
							 :new.tumor_weight,
							 :new.histological_type,
							 :new.histological_nuclear_grade,
							 :new.tumor_stage_type,
							 :new.tumor_stage_desc,
							 :new.lymph_node_stage,
							 :new.total_nodes_positive,
							 :new.total_nodes_examined,
							 :new.extensive_intraductal_comp,
							 :new.primary_gleason_score,
							 :new.secondary_gleason_score,
							 :new.total_gleason_score,
							 :new.perineural_invasion_ind,
							 :new.joint_component,
							 :new.distant_metastasis,
							 :new.stage_grouping);
	  							 
    -- 3.
	  IF ((NVL(:new.primary_ind, 'x') != NVL(:old.primary_ind, 'x')) OR
		    (NVL(:new.diagnosis_concept_id, 'x') != NVL(:old.diagnosis_concept_id, 'x')) OR
		    (NVL(:new.other_dx_name, 'x') != NVL(:old.other_dx_name, 'x')) OR
		    (NVL(:new.tissue_origin_concept_id, 'x') != NVL(:old.tissue_origin_concept_id, 'x')) OR
		    (NVL(:new.other_origin_tc_name, 'x') != NVL(:old.other_origin_tc_name, 'x'))) THEN
		  ddc.replicate_primary_dx_tc(:new.path_report_id, :new.path_report_section_id, :new.diagnosis_concept_id, :new.other_dx_name, :new.tissue_origin_concept_id, :new.other_origin_tc_name);
    END IF;
	END IF;

  -- 4.
  ELSIF (:old.primary_ind = 'Y') THEN
	    SELECT r.primary_path_report_section_id
	    INTO n_primary_section_id
	    FROM pdc_pathology_report r
	    WHERE r.path_report_id = :old.path_report_id;

	    -- 4a.
      IF (n_primary_section_id = :old.path_report_section_id) THEN
	      ddc.replicate_primary_dx_tc(:old.path_report_id, NULL, NULL, NULL, NULL, NULL);

        UPDATE iltds_informed_consent
        SET ddc_check_flag = 'N'
        WHERE	consent_id = :new.consent_id;		
	  END IF;
  END IF;

EXCEPTION
 WHEN OTHERS THEN
	Raise_Application_Error(-20004, 'Error in PDC_PATH_REPORT_SECTION_AIUDR trigger: '||sqlerrm);
END PDC_PATH_REPORT_SECTION_AIUDR;

/ 
