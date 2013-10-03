CREATE OR REPLACE TRIGGER ES_ARDAIS_ACCOUNT_AUR
 AFTER UPDATE OF primary_location ON ES_ARDAIS_ACCOUNT
FOR EACH ROW
DECLARE

BEGIN
   IF nvl(:old.primary_location,'x') != nvl(:new.primary_location,'x') THEN
   	  UPDATE iltds_ardais_staff
	  SET	 location_address_id = :new.primary_location
	  WHERE	 ardais_acct_key = :old.ardais_acct_key;
   END IF;
   EXCEPTION
     WHEN OTHERS THEN
       Raise_Application_Error(-20002, 'Error while updating ES_ARDAIS_ACCOUNT table');
END ES_ARDAIS_ACCOUNT;

/ 
