CREATE OR REPLACE TRIGGER ARD_BRANDING_PK_TRG
BEFORE INSERT ON ARD_BRANDING
FOR EACH ROW
declare
   seq_val	  number;
begin
   if :new.brand_id is null
   then
      select ard_branding_pk_seq.nextval into seq_val from dual;
      :new.brand_id := seq_val;
   end if;
exception
  when others then
    raise_application_error(-20001, 'Primary key violation on ard_branding: '||SQLERRM);
end ard_branding_pk_trg;
/




