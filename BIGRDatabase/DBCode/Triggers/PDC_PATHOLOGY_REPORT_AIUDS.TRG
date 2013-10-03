/* trigger to process the pl/sql table which holds the information that the AFTER row trigger 
   inserted */
   
create or replace trigger pdc_pathology_report_aiuds
after insert or update or delete OF DIAGNOSIS_CONCEPT_ID, TISSUE_CONCEPT_ID, TISSUE_OTHER on pdc_pathology_report

begin 
  Sample_Triggers.process_tissue_origin_changes;
exception  
  when others then
   	 raise;
end pdc_pathology_report_aiuds;  
/ 