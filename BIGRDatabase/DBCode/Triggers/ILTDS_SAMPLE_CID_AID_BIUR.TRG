/*
  This trigger will be invoked if the asm id is modified either
  through and insert or update statement on the sample record. Get
  the modified asm record and update this record accordingly.
*/
CREATE OR REPLACE TRIGGER iltds_sample_cid_aid_biur
BEFORE INSERT OR UPDATE OF asm_id ON iltds_sample
FOR EACH ROW
DECLARE
  asm_consent_id iltds_asm.consent_id%TYPE;
  asm_ardais_id iltds_asm.ardais_id%TYPE;
BEGIN
  -- Check if the asm id is null. This is needed in order to
  -- blank out the existing consent and ardais id fields on
  -- an update. This is not really needed for inserts, but
  -- checking the operation will result in a duplicate code
  -- section to handle the insert case.
  
  IF (:new.asm_id is null) THEN
    :new.consent_id := null;
	:new.ardais_id := null;
  ELSIF (inserting or
           (updating and (nvl(:new.asm_id, 'x') != nvl(:old.asm_id, 'x')))) THEN
    BEGIN
	  -- Select the asm record with matching asm id.
	  SELECT asm.ardais_id, asm.consent_id INTO asm_ardais_id, asm_consent_id
	  FROM iltds_asm asm WHERE asm.asm_id = :new.asm_id;
	  
	  -- Update the record with the values found from the asm table.
	  :new.consent_id := asm_consent_id;
	  :new.ardais_id := asm_ardais_id;

	-- Check for no data found exception. If this occurs, do
	-- nothing (NOOP), there will be a foreign key constraint
	-- violation.
	EXCEPTION
	  WHEN NO_DATA_FOUND
	  THEN
	    NULL;
	END;
  END IF;
END;
/
