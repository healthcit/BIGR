/* trigger to initialize the pl/sql table which holds the current
   row that's being modified */
   
create or replace trigger iltds_informed_consent_biuds
before insert or update or delete on iltds_informed_consent

begin 
  Sample_triggers.init_diagnosis_changes;
exception  
when others then
   	 raise;
end;  
/ 