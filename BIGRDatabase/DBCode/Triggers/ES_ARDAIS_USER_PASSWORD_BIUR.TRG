CREATE OR REPLACE TRIGGER es_ardais_user_password_biur
BEFORE INSERT OR UPDATE OF password ON es_ardais_user 
FOR EACH ROW
BEGIN
  -- Check if the password has changed. Update the password last
  -- change date if password has changed.
  IF (inserting or (updating and (nvl(:new.password, 'x') != nvl(:old.password, 'x')))) THEN
    BEGIN
	  -- Update the password last change date.
	  :new.password_last_change_date := sysdate;
	  -- Also reset the number of failed login attempts and failed security answer 
	  -- attempts made for this user (MR8495)
	  :new.consecutive_failed_logins := 0;
	  :new.consecutive_failed_answers := 0;

    EXCEPTION
      WHEN OTHERS THEN
        -- Consider logging the error and then re-raise
         RAISE;
	END;
  END IF;
END ES_ARDAIS_USER_PASSWORD_BIUR;
/
