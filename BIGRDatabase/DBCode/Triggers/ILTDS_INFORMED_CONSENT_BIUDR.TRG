CREATE OR REPLACE TRIGGER ILTDS_INFORMED_CONSENT_BIUDR
BEFORE INSERT OR UPDATE OR DELETE OF DISEASE_CONCEPT_ID, DISEASE_CONCEPT_ID_OTHER ON ILTDS_INFORMED_CONSENT
FOR EACH ROW
DECLARE
  v_diagnosis_cui	ILTDS_INFORMED_CONSENT.BEST_DIAGNOSIS_CUI%TYPE;
  v_diagnosis_other ILTDS_INFORMED_CONSENT.BEST_DIAGNOSIS_OTHER%TYPE;
BEGIN
  --Note: There is a stored procedure in CONSENT_TRIGGERS that is related to this functionality,
  --used for when the PDC_PATHOLOGY_REPORT for a consent is updated with diagnosis information.
  --That procedure cannot be used here, because it updates the iltds_informed_consent table and
  --calling it from here results in a mutating table error.

  --Note: When the best_diagnosis_cui and/or best_diagnosis_other values change, these values 
  --      need to be propagated down to the samples belonging to this consent.  The
  --      ILTDS_INFORMED_CONSENT_AIUDR trigger normally takes care of this.  However, when
  --      this trigger sets the best_diagnosis_cui and/or best_diagnosis_other values that 
  --      doesn't result in the ILTDS_INFORMED_CONSENT_AIUDR trigger firing.  As a result, we 
  --      need to manually make the calls the ILTDS_INFORMED_CONSENT_AIUDR trigger makes (see the
  --      Sample_Triggers.append_diagnosis_change calls below).

  --if a new record is being inserted into iltds_informed_consent, or an existing record is having
  --it's disease_concept_id or disease_concept_id other value(s) updated, then set the best_diagnosis
  --values.  We first try to get these values from the pdc_pathology_report for the consent, and if 
  --there is no information there then we use the row in iltds_informed_consent
  IF (INSERTING) THEN
     Sample_Triggers.append_diagnosis_change('I', 'consent', :new.consent_id);
  	 --try to get diagnosis information from the pdc_pathology_report table.  If there is no data
	 --a no_data_found exception will occur and the values on the iltds_informed_consent table will
	 --be used.
     select diagnosis_concept_id, diagnosis_other
     into v_diagnosis_cui, v_diagnosis_other
     from pdc_pathology_report
     where consent_id = :new.CONSENT_ID
       and diagnosis_concept_id is not null;
    :new.BEST_DIAGNOSIS_CUI := v_diagnosis_cui;
	:new.BEST_DIAGNOSIS_OTHER := v_diagnosis_other;
  ELSIF (UPDATING) THEN
    IF ((NVL(:new.DISEASE_CONCEPT_ID, 'x') != NVL(:old.DISEASE_CONCEPT_ID, 'x')) OR
	   (NVL(:new.DISEASE_CONCEPT_ID_OTHER, 'x') != NVL(:old.DISEASE_CONCEPT_ID_OTHER, 'x'))) THEN
       Sample_Triggers.append_diagnosis_change('U', 'consent', :new.consent_id);
  	   --try to get diagnosis information from the pdc_pathology_report table.  If there is no data
	   --a no_data_found exception will occur and the values on the iltds_informed_consent table will
	   --be used.
       select diagnosis_concept_id, diagnosis_other
       into v_diagnosis_cui, v_diagnosis_other
       from pdc_pathology_report
       where consent_id = :new.CONSENT_ID
         and diagnosis_concept_id is not null;
      :new.BEST_DIAGNOSIS_CUI := v_diagnosis_cui;
	  :new.BEST_DIAGNOSIS_OTHER := v_diagnosis_other;
	END IF;
  elsif (deleting) then
    Sample_Triggers.append_diagnosis_change('D', 'consent', :old.consent_id);
  END IF;
EXCEPTION
 WHEN no_data_found then
       :new.BEST_DIAGNOSIS_CUI := :new.DISEASE_CONCEPT_ID;
	   :new.BEST_DIAGNOSIS_OTHER := :new.DISEASE_CONCEPT_ID_OTHER; 
 WHEN OTHERS THEN
    raise_application_error(-20001, 'Error setting ILTDS_INFORMED_CONSENT.best_diagnosis_cui: '||SQLERRM);
END ILTDS_INF_CONS_SET_BESTDX_BIUR;
/