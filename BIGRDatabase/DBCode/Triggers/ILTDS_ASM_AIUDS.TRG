/* trigger to process the pl/sql table which holds the information that the AFTER row trigger 
   inserted for this table*/
   
create or replace trigger iltds_asm_aiuds
after insert or update or delete on iltds_asm

begin 
  Sample_Triggers.process_tissue_finding_changes;
  Sample_Triggers.process_appearance_changes;
  Sample_Triggers.process_tissue_origin_changes;
exception  
  when others then
   	raise;
end;  
/