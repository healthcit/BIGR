CREATE OR REPLACE TRIGGER iltds_sample_aiur
AFTER INSERT OR UPDATE OF asm_id, consent_id
ON iltds_sample
FOR EACH ROW
BEGIN
  if (inserting) then
     Sample_Triggers.append_tissue_finding_change('I', 'sample', :new.sample_barcode_id);
     Sample_Triggers.append_appearance_change('I', 'sample', :new.sample_barcode_id);
     Sample_Triggers.append_diagnosis_change('I', 'sample', :new.sample_barcode_id);
     Sample_Triggers.append_tissue_origin_change('I', 'sample', :new.sample_barcode_id);
  elsif (updating) then
  	 -- both asm and consent id changes apply to tissue of origin
     Sample_Triggers.append_tissue_origin_change('U', 'sample', :new.sample_barcode_id);
     if (NVL(:new.asm_id, 'x') != NVL(:old.asm_id, 'x')) then
       Sample_Triggers.append_tissue_finding_change('U', 'sample', :new.sample_barcode_id);
       Sample_Triggers.append_appearance_change('U', 'sample', :new.sample_barcode_id);
     end if;
     if (NVL(:new.consent_id, 'x') != NVL(:old.consent_id, 'x')) then
       Sample_Triggers.append_diagnosis_change('U', 'sample', :new.sample_barcode_id);
     end if;
  end if;
EXCEPTION
  WHEN others then
    raise;  	  
END iltds_sample_aiur;
/


