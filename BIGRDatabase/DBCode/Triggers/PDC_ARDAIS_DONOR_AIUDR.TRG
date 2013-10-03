CREATE OR REPLACE TRIGGER pdc_ardais_donor_aiudr
AFTER INSERT OR UPDATE OR DELETE OF yyyy_dob, ardais_id on pdc_ardais_donor
FOR EACH ROW
BEGIN
     Age_At_Collection.append_yyyy_dob_change('I', :old.ardais_id, :new.ardais_id, :old.yyyy_dob, :new.yyyy_dob);
END pdc_ardais_donor_aiudr;
/
