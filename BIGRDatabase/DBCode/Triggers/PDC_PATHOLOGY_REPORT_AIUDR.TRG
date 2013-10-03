CREATE OR REPLACE TRIGGER PDC_PATHOLOGY_REPORT_AIUDR
 AFTER INSERT OR UPDATE OR DELETE OF DIAGNOSIS_CONCEPT_ID, TISSUE_CONCEPT_ID, TISSUE_OTHER, PROCEDURE_CONCEPT_ID,
 PATH_CHECK_DATE_MM, PATH_CHECK_DATE_YYYY 
 ON PDC_PATHOLOGY_REPORT
FOR EACH ROW
DECLARE

BEGIN   						    
  if (inserting) then
    Sample_Triggers.append_tissue_origin_change('I','ddcpath', :new.path_report_id);
	--Set DDC check flag	 
  	ddc.set_ddc_check_flag(:new.consent_id, :new.DIAGNOSIS_CONCEPT_ID, :new.PROCEDURE_CONCEPT_ID, 
					       :new.PATH_CHECK_DATE_MM, :new.PATH_CHECK_DATE_YYYY, :new.PRIMARY_PATH_REPORT_SECTION_ID );	
  elsif (updating) then
    if ((nvl(:new.tissue_concept_id, 'x') != nvl(:old.tissue_concept_id, 'x')) or
        (nvl(:new.tissue_other, 'x') != nvl(:old.tissue_other, 'x')) or
        (nvl(:new.diagnosis_concept_id, 'x') != nvl(:old.diagnosis_concept_id, 'x'))) then
      Sample_Triggers.append_tissue_origin_change('U','ddcpath', :new.path_report_id);	  
	end if;
	--Set DDC check flag
	if ((nvl(:new.procedure_concept_id, 'x') != nvl(:old.procedure_concept_id, 'x')) or
	    (nvl(:new.path_check_date_mm, 'x') != nvl(:old.path_check_date_mm, 'x')) or 
		(nvl(:new.path_check_date_yyyy, 'x') != nvl(:old.path_check_date_yyyy, 'x'))) then
      	ddc.set_ddc_check_flag(:new.consent_id, :new.DISEASE_CONCEPT_ID, :new.PROCEDURE_CONCEPT_ID, 
					       :new.PATH_CHECK_DATE_MM, :new.PATH_CHECK_DATE_YYYY, :new.PRIMARY_PATH_REPORT_SECTION_ID );
	end if;
  elsif (deleting) then
    Sample_Triggers.append_tissue_origin_change('D', 'ddcpath', :old.path_report_id);
  end if;
EXCEPTION
 WHEN OTHERS THEN
	Raise_Application_Error(-20004, 'Error in PDC_PATHOLOGY_REPORT_AIUDR: '||sqlerrm);
END PDC_PATHOLOGY_REPORT_AIUDR;
/ 
