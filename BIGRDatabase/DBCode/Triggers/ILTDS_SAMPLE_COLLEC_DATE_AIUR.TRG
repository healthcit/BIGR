CREATE OR REPLACE TRIGGER iltds_sample_collec_date_aiur
AFTER INSERT OR UPDATE OF collection_datetime on iltds_sample
FOR EACH ROW
BEGIN
     Age_At_Collection.append_collec_date_change('I', :new.sample_barcode_id, :new.ardais_id, :new.collection_datetime);
END iltds_sample_collec_date_aiur;
/