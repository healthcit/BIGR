CREATE OR REPLACE FORCE VIEW box_v_e (box_id,
                                    number_of_columns,
                                    number_of_rows,
                                    location_id,
                                    room,
                                    unit,
                                    drawer,
                                    slot,
                                    storage_type_cui,
                                    storage_type
                                    )
AS
   SELECT iltds_sample_box.box_barcode_id box_id,
          iltds_box_layout.number_of_columns, 
          iltds_box_layout.number_of_rows,
          iltds_box_location.location_address_id location_id,
          iltds_box_location.room_id,
          iltds_box_location.unit_name, 
          iltds_box_location.drawer_id,
          iltds_box_location.slot_id,
          iltds_box_location.storage_type_cid,
          lk1.lookup_type_cd_desc storage_type
     FROM iltds_box_location,
          iltds_box_layout,
          iltds_sample_box,
          pdc_lookup lk1
    WHERE iltds_sample_box.box_layout_id = iltds_box_layout.box_layout_id(+)
      AND iltds_sample_box.box_barcode_id = iltds_box_location.box_barcode_id(+)
      AND iltds_box_location.storage_type_cid = lk1.lookup_type_cd(+)
/
