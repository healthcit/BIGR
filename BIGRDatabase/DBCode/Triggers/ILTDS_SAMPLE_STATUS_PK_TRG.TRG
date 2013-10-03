---------------------------------------------------------------------------------------
--	File: 	ILTDS_SAMPLE_STATUS_PK_TRG.TRG
--	Author:	Dean Marsh
--	Date:	05/31/2002
--
---------------------------------------------------------------------------------------

create or replace TRIGGER iltds_sample_status_pk_trg
before insert on iltds_sample_status
for each row
declare
   seq_val	  number;
begin
   if :new.id is null
   then
      select iltds_sample_status_pk_seq.nextval into seq_val from dual;
      :new.id := seq_val;
   end if;
exception
  when others then
    raise_application_error(-20001, 'Primary key violation on iltds_sample_status: '||SQLERRM);
end iltds_sample_status_pk_trg;
/

