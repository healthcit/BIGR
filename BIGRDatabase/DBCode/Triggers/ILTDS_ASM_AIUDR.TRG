CREATE OR REPLACE TRIGGER iltds_asm_aiudr
AFTER INSERT OR UPDATE OR DELETE OF organ_site_concept_id, organ_site_concept_id_other, specimen_type
ON iltds_asm
FOR EACH ROW
BEGIN
  --if this is an insert, an update with values actually changed, or a delete then for
  --every sample belonging to this asm update the tissue of finding and appearance values
  if (inserting) then
    Sample_Triggers.append_tissue_finding_change('I', 'asm', :new.asm_id);
    Sample_Triggers.append_appearance_change('I', 'asm', :new.asm_id);
    Sample_Triggers.append_tissue_origin_change('I', 'asm', :new.asm_id);
  elsif (updating) then 
      if ((NVL(:new.organ_site_concept_id, 'x') != NVL(:old.organ_site_concept_id, 'x')) or
	      (NVL(:new.organ_site_concept_id_other, 'x') != NVL(:old.organ_site_concept_id_other, 'x'))) then
         Sample_Triggers.append_tissue_finding_change('U', 'asm', :new.asm_id);
      end if;
      if (NVL(:new.specimen_type, 'x') != NVL(:old.specimen_type, 'x')) then
         Sample_Triggers.append_appearance_change('U', 'asm', :new.asm_id);
      end if;
      --all 3 fields apply to tissue of origin
      if ((NVL(:new.organ_site_concept_id, 'x') != NVL(:old.organ_site_concept_id, 'x')) or
	      (NVL(:new.organ_site_concept_id_other, 'x') != NVL(:old.organ_site_concept_id_other, 'x')) or
	      (NVL(:new.specimen_type, 'x') != NVL(:old.specimen_type, 'x'))) then
         Sample_Triggers.append_tissue_origin_change('U', 'asm', :new.asm_id);
      end if;
  elsif (deleting) then
    Sample_Triggers.append_tissue_finding_change('D', 'asm', :old.asm_id);
    Sample_Triggers.append_appearance_change('D', 'asm', :old.asm_id);
    Sample_Triggers.append_tissue_origin_change('D', 'asm', :old.asm_id);
  end if;
EXCEPTION
  WHEN others then
    raise;  	  
END iltds_asm_aiudr;
/


