CREATE OR REPLACE TRIGGER lims_path_evaluation_biuds
before insert or update or delete on LIMS_PATHOLOGY_EVALUATION
begin
  Sample_Triggers.init_tissue_finding_changes;
  Sample_Triggers.init_appearance_changes;
  Sample_Triggers.init_diagnosis_changes;
  Sample_Triggers.init_tissue_origin_changes;
  Sample_Triggers.init_qc_verified_changes;
exception
when others then
   raise;
end lims_path_evaluation_biuds;
/