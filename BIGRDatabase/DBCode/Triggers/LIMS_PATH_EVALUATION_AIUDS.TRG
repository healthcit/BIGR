CREATE OR REPLACE TRIGGER lims_path_evaluation_aiuds
after insert or update or delete
on LIMS_PATHOLOGY_EVALUATION
begin
  Sample_Triggers.process_tissue_finding_changes;
  Sample_Triggers.process_appearance_changes;
  Sample_Triggers.process_diagnosis_changes;
  Sample_Triggers.process_tissue_origin_changes;
  Sample_Triggers.process_qc_verified_changes;
exception
  when others then
   	raise;
end lims_path_evaluation_aiuds;
/