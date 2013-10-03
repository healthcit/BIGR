/* trigger to initialize the pl/sql table which holds the current
   row that's being modified */
   
create or replace trigger pdc_pathology_report_biuds
before insert or update or delete OF DIAGNOSIS_CONCEPT_ID, TISSUE_CONCEPT_ID, TISSUE_OTHER on pdc_pathology_report

begin 
  Sample_Triggers.init_tissue_origin_changes;
exception  
when others then
   	 raise;
end pdc_pathology_report_biuds;  
/ 