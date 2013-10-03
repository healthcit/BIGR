CREATE OR REPLACE TRIGGER pdc_ardais_donor_aiuds
after insert or update or delete of yyyy_dob, ardais_id on pdc_ardais_donor
begin
  Age_At_Collection.process_yyyy_dob_changes;
end pdc_ardais_donor_aiuds;
/
