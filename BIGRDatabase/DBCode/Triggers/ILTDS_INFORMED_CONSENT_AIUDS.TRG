/* trigger to process the pl/sql table which holds the information that the AFTER row trigger 
   inserted for this table*/
   
create or replace trigger iltds_informed_consent_aiuds
AFTER insert or update or delete ON ILTDS_INFORMED_CONSENT

begin 
  Sample_Triggers.process_diagnosis_changes;
exception  
  when others then
   	 raise;
end;  
/ 