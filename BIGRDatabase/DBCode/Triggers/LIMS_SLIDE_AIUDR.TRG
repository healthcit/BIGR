CREATE OR REPLACE TRIGGER LIMS_SLIDE_AIUDR
 AFTER INSERT OR UPDATE OR DELETE ON LIMS_SLIDE
FOR EACH ROW
DECLARE
v_section_count NUMBER;
v_old_section_count NUMBER;
/******************************************************************************
   NAME:       LIMS_SLIDE_AIUDR
   PURPOSE:    To update ILTDS_SAMPLE.section_count value with number of
               slides available for each sample.
******************************************************************************/
BEGIN
   v_section_count := 0;
   v_old_section_count := 0;
   --Get section count.
   if (deleting <> true) then 
	   SELECT SECTION_COUNT
	   INTO v_section_count
	   FROM ILTDS_SAMPLE
	   WHERE SAMPLE_BARCODE_ID = :new.sample_barcode_id;
	   --check for null
	   if (v_section_count is null) then
	   	  v_section_count := 0;
	   end if;	   
   end if;

   if (inserting) then
      --In this case new slide added for sample.
	  --Increment section_count by one.
   	  UPDATE ILTDS_SAMPLE
	  SET SECTION_COUNT = v_section_count + 1
	  WHERE SAMPLE_BARCODE_ID = :new.sample_barcode_id;
   elsif (deleting) then   
       SELECT SECTION_COUNT
	   INTO v_section_count
	   FROM ILTDS_SAMPLE
	   WHERE SAMPLE_BARCODE_ID = :old.sample_barcode_id;
      --In this case new slide deleted for sample.
	  --Decrement section_count by one.
	  
   	  UPDATE ILTDS_SAMPLE
	  SET SECTION_COUNT = v_section_count - 1
	  WHERE SAMPLE_BARCODE_ID = :old.sample_barcode_id;
   elsif (updating) then
      --Check whether sample_barcode_id is updated or not.
      if (:old.sample_barcode_id <> :new.sample_barcode_id) then
	     --Get section count for old value of sample.
	     SELECT SECTION_COUNT
		 INTO v_old_section_count
		 FROM ILTDS_SAMPLE
		 WHERE SAMPLE_BARCODE_ID = :old.sample_barcode_id;
		 --In this case increment section_count
		 --for new value of sample_barcode_id.
		 UPDATE ILTDS_SAMPLE
	     SET SECTION_COUNT = v_section_count + 1
	     WHERE SAMPLE_BARCODE_ID = :new.sample_barcode_id;
		 --In this case decrement section_count
		 --for old value of sample_barcode_id.
		 UPDATE ILTDS_SAMPLE
	     SET SECTION_COUNT = v_old_section_count - 1
	     WHERE SAMPLE_BARCODE_ID = :old.sample_barcode_id;

	  end if;
   end if;

   EXCEPTION
     WHEN OTHERS THEN
       raise_Application_Error(-20003, 'Error while executing trigger LIMS_SLIDE_AIUDR: '||SQLERRM);
END LIMS_SLIDE_AIUDR;

/ 
