CREATE OR REPLACE TRIGGER iltds_informed_consent_aiudr
AFTER insert or update or delete OF best_diagnosis_cui, best_diagnosis_other
ON ILTDS_INFORMED_CONSENT
FOR EACH ROW
DECLARE

  --Note: IF YOU ALTER THIS TRIGGER, make sure you make any corresponding changes in the 
  --      ILTDS_INFORMED_CONSENT_BIUDR trigger!!! See the note at the top of that
  --      trigger for details...

BEGIN
  if (inserting) then
    Sample_Triggers.append_diagnosis_change('I', 'consent', :new.consent_id);
  elsif (updating) then
    if ((NVL(:new.best_diagnosis_cui, 'x') != NVL(:old.best_diagnosis_cui, 'x')) or
	    (NVL(:new.best_diagnosis_other, 'x') != NVL(:old.best_diagnosis_other, 'x'))) then
      Sample_Triggers.append_diagnosis_change('U', 'consent', :new.consent_id);
    end if;
  elsif (deleting) then
    Sample_Triggers.append_diagnosis_change('D', 'consent', :old.consent_id);
  end if;

EXCEPTION
 WHEN OTHERS THEN
  raise;
END ;
/ 
