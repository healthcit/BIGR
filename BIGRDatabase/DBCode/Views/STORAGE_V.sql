CREATE OR REPLACE VIEW storage_v
AS 
SELECT LOCATION_ADDRESS_ID LOCATION_ID,
       room_id room, 
       unit_name unit,
       drawer_id drawer,
       slot_id slot,
       available_ind EMPTY_YN,
       storage_type_cid storage_type_cui
  FROM ILTDS_BOX_LOCATION
/
