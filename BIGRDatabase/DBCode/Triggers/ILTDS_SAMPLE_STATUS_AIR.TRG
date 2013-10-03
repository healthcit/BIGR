CREATE OR REPLACE TRIGGER ILTDS_SAMPLE_STATUS_AIR
 AFTER INSERT ON ILTDS_SAMPLE_STATUS
 FOR EACH ROW
 CALL Sample_Status_TRG_PKG.on_sample_status_air(:new.sample_barcode_id, :new.status_type_code, :new.sample_status_datetime)
/
