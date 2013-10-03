CREATE OR REPLACE TRIGGER ard_urls_pk_trg
before insert on ard_urls
for each row
declare
	seq_val number;
begin
	if :new.id is null
	then
		select ard_urls_seq.nextval into seq_val from dual;
		:new.id := seq_val;
	end if;
exception
	when others then
		raise_application_error(-20001, 'Primary key violation on ard_urls: '||SQLERRM);
end ard_urls_pk_trg;
/