/* trigger to initialize the pl/sql table which holds the current
   row that's being modified */
   
create or replace trigger iltds_asm_biuds
before insert or update or delete on ILTDS_ASM

begin 
  Sample_Triggers.init_tissue_finding_changes;
  Sample_Triggers.init_appearance_changes;
  Sample_Triggers.init_tissue_origin_changes;
exception  
when others then
   	 raise;
end;  
/