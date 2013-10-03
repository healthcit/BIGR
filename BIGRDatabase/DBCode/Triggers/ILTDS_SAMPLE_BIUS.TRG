create or replace trigger iltds_sample_bius
before insert or update of asm_id, consent_id on iltds_sample

begin 
  Sample_Triggers.init_tissue_finding_changes;
  Sample_Triggers.init_appearance_changes;
  Sample_Triggers.init_diagnosis_changes;
  Sample_Triggers.init_tissue_origin_changes;
exception  
when others then
   	 raise;
end;
/