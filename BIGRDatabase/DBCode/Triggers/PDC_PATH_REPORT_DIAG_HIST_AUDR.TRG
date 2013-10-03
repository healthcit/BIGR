CREATE OR REPLACE TRIGGER pdc_path_report_diag_hist_audr
after update or delete of
  ID,
  DIAGNOSTICS_CONCEPT_ID,
  PATH_REPORT_ID,
  DIAGNOSTIC_TYPE,
  DIAGNOSTIC_NOTE,
  DIAGNOSTIC_RESULTS_CID
on pdc_path_report_diagnostics
for each row
declare
   aud_type	 	varchar2(6);
   v_diag_concept_desc  PDC_PATH_REPORT_DIAG_HIST.DIAGNOSTICS_CONCEPT_ID_DESC%TYPE; 
   v_diag_results_desc  PDC_PATH_REPORT_DIAG_HIST.DIAGNOSTIC_RESULTS_CID_DESC%TYPE; 
   v_diag_type_desc     PDC_PATH_REPORT_DIAG_HIST.DIAGNOSTIC_TYPE_DESC%TYPE; 
begin
   --get the descriptions for the ARTS enabled columns
   v_diag_concept_desc := GET_DESC_FROM_PDC_LOOKUP(:old.DIAGNOSTICS_CONCEPT_ID);
   --DBMS_OUTPUT.Put_Line('Description for ' || :old.DIAGNOSTICS_CONCEPT_ID || ' is ' || v_diag_concept_desc);
   v_diag_results_desc := GET_DESC_FROM_PDC_LOOKUP(:old.DIAGNOSTIC_RESULTS_CID);
   --DBMS_OUTPUT.Put_Line('Description for ' || :old.DIAGNOSTIC_RESULTS_CID || ' is ' || v_diag_results_desc);
   v_diag_type_desc := GET_DESC_FROM_PDC_LOOKUP(:old.DIAGNOSTIC_TYPE);
   --DBMS_OUTPUT.Put_Line('Description for ' || :old.DIAGNOSTIC_TYPE || ' is ' || v_diag_type_desc);
   if (deleting)
   then
      aud_type := 'DELETE';
   elsif (updating)
   then
      aud_type := 'UPDATE';
   end if;
   insert into pdc_path_report_diag_hist
   (AUDIT_HISTORY_ID,
   AUDIT_DATE,
   AUDIT_TYPE,
   ID,
   DIAGNOSTICS_CONCEPT_ID,
   DIAGNOSTICS_CONCEPT_ID_DESC,
   PATH_REPORT_ID,
   DIAGNOSTIC_TYPE,
   DIAGNOSTIC_TYPE_DESC,
   DIAGNOSTIC_NOTE,
   DIAGNOSTIC_RESULTS_CID,
   DIAGNOSTIC_RESULTS_CID_DESC)
   values
   (pdc_path_report_diag_hist_seq.nextval,
   sysdate,
   aud_type,
   :old.ID,
   :old.DIAGNOSTICS_CONCEPT_ID,
   v_diag_concept_desc,
   :old.PATH_REPORT_ID,
   :old.DIAGNOSTIC_TYPE,
   v_diag_type_desc,
   :old.DIAGNOSTIC_NOTE,
   :old.DIAGNOSTIC_RESULTS_CID,
   v_diag_results_desc);
EXCEPTION
  WHEN OTHERS THEN
    raise_application_error (-20008, 'An error occured while creating pdc_path_report_diag_hist record: ' || sqlerrm);
end pdc_path_report_diag_hist_audr;
/

