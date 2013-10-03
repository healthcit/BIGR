CREATE OR REPLACE FORCE VIEW sample_location_v (sample_id,
                                                box_id,
                                                location_id,
                                                room,
                                                unit,
                                                drawer,
                                                slot,
                                                storage_type_cui
                                                )
AS
   SELECT isam.sample_barcode_id sample_id,
          ibl.box_barcode_id box_id, 
          ibl.location_address_id location_id,
          ibl.room_id room, 
          ibl.unit_name unit,
          ibl.drawer_id drawer, 
          ibl.slot_id slot,
          ibl.storage_type_cid storage_type_cui
     FROM iltds_box_location ibl, iltds_sample isam
    WHERE ibl.box_barcode_id = isam.BOX_BARCODE_ID
/
