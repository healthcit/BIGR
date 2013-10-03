/*
  This trigger gets invoked if either the consent or
  ardais ids get modified through an update statement. If
  invoked, update all associated sample records with the
  new values.
*/
CREATE OR REPLACE TRIGGER iltds_asm_cid_aid_bur
BEFORE UPDATE OF consent_id, ardais_id ON iltds_asm
FOR EACH ROW
BEGIN
  if ((nvl(:new.consent_id, 'x') != nvl(:old.consent_id, 'x')) or
      (nvl(:new.ardais_id, 'x') != nvl(:old.ardais_id, 'x'))) then
    UPDATE iltds_sample
      SET consent_id = :new.consent_id, ardais_id = :new.ardais_id
      WHERE asm_id = :new.asm_id;
  end if;
END;
/
