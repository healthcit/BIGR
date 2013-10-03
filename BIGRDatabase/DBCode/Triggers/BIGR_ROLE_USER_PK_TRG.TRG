/* Trigger to assign primary key if not specified in an INSERT. */

CREATE OR REPLACE TRIGGER BIGR_ROLE_USER_PK_TRG
before insert on BIGR_ROLE_USER
for each row
declare
	seq_val number;
begin
	if :new.id is null
	then
		select BIGR_ROLE_USER_SEQ.nextval into seq_val from dual;
		:new.id := seq_val;
	end if;
exception
	when others then
		raise_application_error(-20001, 'Primary key violation on BIGR_ROLE_USER: '||SQLERRM);
end BIGR_ROLE_USER_PK_TRG;
/
