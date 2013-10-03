CREATE OR REPLACE TRIGGER lims_path_evaluation_aiudr
AFTER INSERT OR UPDATE OR DELETE OF reported_yn, tissue_finding_concept_id, other_tissue_finding_name, 
microscopic_appearance, diagnosis_concept_id, other_diagnosis_name, tissue_origin_concept_id, other_tissue_origin_name
ON lims_pathology_evaluation
FOR EACH ROW
BEGIN
  --if this is an insert, an update with values actually changed, or a delete then
  --update the tissue of finding values on the sample to which this evaluation belongs
  if (inserting) then
    Sample_Triggers.append_tissue_finding_change('I', 'limspath', :new.sample_barcode_id);
    Sample_Triggers.append_appearance_change('I', 'limspath', :new.sample_barcode_id);
    Sample_Triggers.append_diagnosis_change('I', 'limspath', :new.sample_barcode_id);
    Sample_Triggers.append_tissue_origin_change('I', 'limspath', :new.sample_barcode_id);
    Sample_Triggers.append_qc_verified_change('I', 'limspath', :new.sample_barcode_id);
  elsif (updating) then
     if ((NVL(:new.reported_yn, 'x') != NVL(:old.reported_yn, 'x')) or
         (NVL(:new.tissue_finding_concept_id, 'x') != NVL(:old.tissue_finding_concept_id, 'x')) or
	     (NVL(:new.other_tissue_finding_name, 'x') != NVL(:old.other_tissue_finding_name, 'x'))) then
        Sample_Triggers.append_tissue_finding_change('U', 'limspath', :new.sample_barcode_id);
     end if;
     if ((NVL(:new.reported_yn, 'x') != NVL(:old.reported_yn, 'x')) or
         (NVL(:new.microscopic_appearance, 'x') != NVL(:old.microscopic_appearance, 'x'))) then
        Sample_Triggers.append_appearance_change('U', 'limspath', :new.sample_barcode_id);
     end if;
     if ((NVL(:new.reported_yn, 'x') != NVL(:old.reported_yn, 'x')) or
         (NVL(:new.diagnosis_concept_id, 'x') != NVL(:old.diagnosis_concept_id, 'x')) or
	     (NVL(:new.other_diagnosis_name, 'x') != NVL(:old.other_diagnosis_name, 'x'))) then
        Sample_Triggers.append_diagnosis_change('U', 'limspath', :new.sample_barcode_id);
     end if;
     if ((NVL(:new.reported_yn, 'x') != NVL(:old.reported_yn, 'x')) or
         (NVL(:new.tissue_origin_concept_id, 'x') != NVL(:old.tissue_origin_concept_id, 'x')) or
	     (NVL(:new.other_tissue_origin_name, 'x') != NVL(:old.other_tissue_origin_name, 'x'))) then
        Sample_Triggers.append_tissue_origin_change('U', 'limspath', :new.sample_barcode_id);
     end if;
     if (NVL(:new.reported_yn, 'x') != NVL(:old.reported_yn, 'x')) then
       Sample_Triggers.append_qc_verified_change('U', 'limspath', :new.sample_barcode_id);
     end if;
  elsif (deleting) then
    Sample_Triggers.append_tissue_finding_change('D', 'limspath', :old.sample_barcode_id);
    Sample_Triggers.append_appearance_change('D', 'limspath', :old.sample_barcode_id);
    Sample_Triggers.append_diagnosis_change('D', 'limspath', :old.sample_barcode_id);
    Sample_Triggers.append_tissue_origin_change('D', 'limspath', :old.sample_barcode_id);
    Sample_Triggers.append_qc_verified_change('D', 'limspath', :new.sample_barcode_id);
  end if;
EXCEPTION
  WHEN others then
    raise;  	  
END lims_path_evaluation_aiudr;
/


