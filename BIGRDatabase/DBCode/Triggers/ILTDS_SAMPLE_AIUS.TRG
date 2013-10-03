/* trigger to process the pl/sql table which holds the information that the AFTER row trigger 
   inserted for this table*/
   
create or replace trigger iltds_sample_aius
after insert or update of asm_id, consent_id on iltds_sample

begin 
  Sample_Triggers.process_tissue_finding_changes;
  Sample_Triggers.process_appearance_changes;
  Sample_Triggers.process_diagnosis_changes;
  Sample_Triggers.process_tissue_origin_changes;
exception  
  when others then
   	 raise;
end;  
/ 