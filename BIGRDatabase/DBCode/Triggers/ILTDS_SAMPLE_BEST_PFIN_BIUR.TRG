-----------------------------------------------------------------------------------------------------
--	FILE:  ILTDS_SAMPLE_BEST_PFIN_BIUR.TRG
--	AUTHOR:  Dean Marsh
--	DATE:  12/17/2002
--
--	PURPOSE:  This trigger implements the business rules for setting the three BEST_XXXX columns.
--		  The business rule is as follows -
--			1.  Use the value in the corresponding HISTO_XXXX column if present.
--			2.  If no value is present in the HISTO_XXXX column, use the corresponding
--			    DI_XXXX column.
--			3.  If neither, then null.
--
-----------------------------------------------------------------------------------------------------


CREATE OR REPLACE TRIGGER iltds_sample_best_pfin_biur
BEFORE INSERT OR UPDATE OF 
	  di_min_thickness_pfin_cid,
	  di_max_thickness_pfin_cid,
	  di_width_across_pfin_cid,
	  histo_min_thickness_pfin_cid,
	  histo_max_thickness_pfin_cid,
	  histo_width_across_pfin_cid
ON iltds_sample
FOR EACH ROW
BEGIN
   if inserting 
   then
      :new.best_min_thickness_pfin_cid := :new.di_min_thickness_pfin_cid;
	  :new.best_max_thickness_pfin_cid := :new.di_max_thickness_pfin_cid;
	  :new.best_width_across_pfin_cid := :new.di_width_across_pfin_cid;
   elsif updating 
   then
      if (:new.histo_min_thickness_pfin_cid is not null or :new.di_min_thickness_pfin_cid is not null)
	  then
	     if :new.histo_min_thickness_pfin_cid is not null
	     then
		    :new.best_min_thickness_pfin_cid := :new.histo_min_thickness_pfin_cid;
		 elsif :old.histo_min_thickness_pfin_cid is not null
		 then
		    :new.best_min_thickness_pfin_cid := :old.histo_min_thickness_pfin_cid;
         else 
	        :new.best_min_thickness_pfin_cid := :new.di_min_thickness_pfin_cid;
	     end if;
	  end if;
	  
	  if (:new.histo_max_thickness_pfin_cid is not null or :new.di_max_thickness_pfin_cid is not null)
	  then
	     if :new.histo_max_thickness_pfin_cid is not null
		 then
	        :new.best_max_thickness_pfin_cid := :new.histo_max_thickness_pfin_cid;
		 elsif :old.histo_max_thickness_pfin_cid is not null
		 then
		    :new.best_max_thickness_pfin_cid := :old.histo_max_thickness_pfin_cid;
         else
	        :new.best_max_thickness_pfin_cid := :new.di_max_thickness_pfin_cid;
	     end if;
	  end if;
	  
	  if (:new.histo_width_across_pfin_cid is not null or :new.di_width_across_pfin_cid is not null)
	  then
	     if :new.histo_width_across_pfin_cid is not null
		 then
	        :new.best_width_across_pfin_cid := :new.histo_width_across_pfin_cid;
		 elsif :old.histo_width_across_pfin_cid is not null
		 then
		    :new.best_width_across_pfin_cid := :old.histo_width_across_pfin_cid;
         else
	        :new.best_width_across_pfin_cid := :new.di_width_across_pfin_cid;
	     end if;
	  end if;
   end if;	
   
EXCEPTION
   when others then
      raise_application_error (-20020, 'Error in ILTDS_SAMPLE_BEST_PFIN_BIUR setting BEST paraffin values: ' || sqlerrm);  	  
END iltds_sample_best_pfin_biur;
/


