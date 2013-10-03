CREATE OR REPLACE TRIGGER ES_ARDAIS_USER_AIR
 AFTER INSERT OR UPDATE OF USER_LASTNAME, USER_FIRSTNAME ON ES_ARDAIS_USER
FOR EACH ROW
DECLARE
v_primary_location es_ardais_account.primary_location%TYPE;

BEGIN

   IF INSERTING THEN
   	  SELECT primary_location
	  INTO	 v_primary_location
	  FROM 	 es_ardais_account
	  WHERE  ardais_acct_key = :new.ardais_acct_key;

   	  INSERT INTO iltds_ardais_staff
	  (ARDAIS_STAFF_ID,
	  LOCATION_ADDRESS_ID,
	  ARDAIS_STAFF_LNAME,
	  ARDAIS_STAFF_FNAME,
	  ARDAIS_ACCT_KEY,
	  ARDAIS_USER_ID
	  )
	  VALUES
	  (:new.ardais_user_id,
	  v_primary_location,
	  :new.user_lastname,
	  :new.user_firstname,
	  :new.ardais_acct_key,
	  :new.ardais_user_id
	  );
   ELSIF UPDATING THEN
	UPDATE iltds_ardais_staff
	SET ardais_staff_lname = :new.user_lastname,
	    ardais_staff_fname = :new.user_firstname
	WHERE ardais_staff_id = :old.ardais_user_id;
   END IF;

   EXCEPTION
     WHEN OTHERS THEN
	    Raise_Application_Error(-20001, 'Error while inserting row in ES_ARDAIS_USER');
END ES_ARDAIS_USER_AIUR;
/
