create or replace trigger pdc_path_report_history_audr
after update or delete of
   PATH_REPORT_ID,
   PRIMARY_PATH_REPORT_SECTION_ID,
   CONSENT_ID,
   ARDAIS_ID,
   DISEASE_CONCEPT_ID,
   DIAGNOSIS_CONCEPT_ID,
   DIAGNOSIS_OTHER,
   TISSUE_CONCEPT_ID,
   TISSUE_OTHER,
   PROCEDURE_CONCEPT_ID,
   OTHER_PR_NAME,
   PATH_CHECK_DATE_MM,
   PATH_CHECK_DATE_YYYY,
   CREATE_USER,
   CREATE_DATE,
   LAST_UPDATE_USER,
   LAST_UPDATE_DATE
on pdc_pathology_report
for each row
declare
   aud_type	 	varchar2(6);
   v_disease_desc    PDC_PATHOLOGY_REPORT_HISTORY.DISEASE_CONCEPT_ID_DESC%TYPE; 
   v_diagnosis_desc  PDC_PATHOLOGY_REPORT_HISTORY.DIAGNOSIS_CONCEPT_ID_DESC%TYPE; 
   v_procedure_desc  PDC_PATHOLOGY_REPORT_HISTORY.PROCEDURE_CONCEPT_ID_DESC%TYPE; 
   v_tissue_desc     PDC_PATHOLOGY_REPORT_HISTORY.TISSUE_CONCEPT_ID_DESC%TYPE; 
begin
   --get the descriptions for the ARTS enabled columns
   v_disease_desc := GET_DESC_FROM_PDC_LOOKUP(:old.DISEASE_CONCEPT_ID);
   --DBMS_OUTPUT.Put_Line('Description for ' || :old.DISEASE_CONCEPT_ID || ' is ' || v_disease_desc);
   v_diagnosis_desc := GET_DESC_FROM_DX_TC_HIERARCHY(:old.DIAGNOSIS_CONCEPT_ID);
   --DBMS_OUTPUT.Put_Line('Description for ' || :old.DIAGNOSIS_CONCEPT_ID || ' is ' || v_diagnosis_desc);
   v_procedure_desc := GET_DESC_FROM_DX_TC_HIERARCHY(:old.PROCEDURE_CONCEPT_ID);
   --DBMS_OUTPUT.Put_Line('Description for ' || :old.PROCEDURE_CONCEPT_ID || ' is ' || v_procedure_desc);
   v_tissue_desc := GET_DESC_FROM_DX_TC_HIERARCHY(:old.TISSUE_CONCEPT_ID);
   --DBMS_OUTPUT.Put_Line('Description for ' || :old.TISSUE_CONCEPT_ID || ' is ' || v_tissue_desc);
   if (deleting)
   then
      aud_type := 'DELETE';
   elsif (updating)
   then
      aud_type := 'UPDATE';
   end if;
   insert into pdc_pathology_report_history
   (AUDIT_HISTORY_ID,
   AUDIT_DATE,
   AUDIT_TYPE,
   PATH_REPORT_ID,
   PRIMARY_PATH_REPORT_SECTION_ID,
   CONSENT_ID,
   ARDAIS_ID,
   DISEASE_CONCEPT_ID,
   DISEASE_CONCEPT_ID_DESC,
   DIAGNOSIS_CONCEPT_ID,
   DIAGNOSIS_CONCEPT_ID_DESC,
   DIAGNOSIS_OTHER,
   TISSUE_CONCEPT_ID,
   TISSUE_CONCEPT_ID_DESC,
   TISSUE_OTHER,
   PROCEDURE_CONCEPT_ID,
   PROCEDURE_CONCEPT_ID_DESC,
   OTHER_PR_NAME,
   PATH_CHECK_DATE_MM,
   PATH_CHECK_DATE_YYYY,
   CREATE_USER,
   CREATE_DATE,
   LAST_UPDATE_USER,
   LAST_UPDATE_DATE)
   values
   (pdc_pathology_report_hist_seq.nextval,
   sysdate,
   aud_type,
   :old.PATH_REPORT_ID,
   :old.PRIMARY_PATH_REPORT_SECTION_ID,
   :old.CONSENT_ID,
   :old.ARDAIS_ID,
   :old.DISEASE_CONCEPT_ID,
   v_disease_desc,
   :old.DIAGNOSIS_CONCEPT_ID,
   v_diagnosis_desc,
   :old.DIAGNOSIS_OTHER,
   :old.TISSUE_CONCEPT_ID,
   v_tissue_desc,
   :old.TISSUE_OTHER,
   :old.PROCEDURE_CONCEPT_ID,
   v_procedure_desc,
   :old.OTHER_PR_NAME,
   :old.PATH_CHECK_DATE_MM,
   :old.PATH_CHECK_DATE_YYYY,
   :old.CREATE_USER,
   :old.CREATE_DATE,
   :old.LAST_UPDATE_USER,
   :old.LAST_UPDATE_DATE);
EXCEPTION
  WHEN OTHERS THEN
    raise_application_error (-20007, 'An error occured while creating pdc_pathology_report_history record: ' || sqlerrm);
end pdc_path_report_history_audr;
/
