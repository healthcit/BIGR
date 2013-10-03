CREATE OR REPLACE VIEW storage_v_e
AS 
SELECT LOCATION_ADDRESS_ID LOCATION_ID,
       room_id room, 
       unit_name unit,
       drawer_id drawer,
       slot_id slot,
       available_ind EMPTY_YN,
       storage_type_cid storage_type_cui,
       lk1.lookup_type_cd_desc storage_type
  FROM iltds_box_location, pdc_lookup lk1
 WHERE iltds_box_location.storage_type_cid = lk1.lookup_type_cd(+)
/
