create or replace
TRIGGER ILTDS_ATTACHMENTS_PK_TRG 
BEFORE INSERT ON ILTDS_ATTACHMENTS 
REFERENCING OLD AS old NEW AS new 
FOR EACH ROW 
declare
	seq_val number;
begin
	if :new.id is null
	then
		select iltds_attachments_seq.nextval into seq_val from dual;    
		:new.id := seq_val;
	end if;
exception
	when others then
		raise_application_error(-20001, 'Primary key violation on ILTDS_ATTACHMENTS: '||SQLERRM);
end ILTDS_ATTACHMENTS_PK_TRG;
/