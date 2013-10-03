CREATE OR REPLACE TRIGGER iltds_sample_collec_date_aius
after insert or update of collection_datetime on iltds_sample
begin
  Age_At_Collection.process_collec_date_changes;
end iltds_sample_collec_date_aius;
/
