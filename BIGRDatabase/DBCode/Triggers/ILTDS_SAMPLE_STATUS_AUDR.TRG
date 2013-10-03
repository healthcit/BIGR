CREATE OR REPLACE TRIGGER ILTDS_SAMPLE_STATUS_AUDR
 AFTER UPDATE OR DELETE ON ILTDS_SAMPLE_STATUS
 FOR EACH ROW
BEGIN
   if (updating) then
       Sample_Status_TRG_PKG.on_sample_status_audr(
           'U',
           :old.sample_barcode_id, :old.status_type_code, :old.sample_status_datetime,
           :new.sample_barcode_id, :new.status_type_code, :new.sample_status_datetime);
   elsif (deleting) then
       Sample_Status_TRG_PKG.on_sample_status_audr(
           'D',
           :old.sample_barcode_id, :old.status_type_code, :old.sample_status_datetime,
           null, null, null);
   end if;
END ILTDS_SAMPLE_STATUS_AUDR;
/
