CREATE OR REPLACE TRIGGER iltds_sample_collec_date_bius
before insert or update of collection_datetime on iltds_sample
begin
  Age_At_Collection.init_collec_date_changes;
end iltds_sample_collec_date_bius;
/
