CREATE OR REPLACE TRIGGER iltds_sample_genealogy_pk_trg
before insert on iltds_sample_genealogy
for each row
declare
	seq_val number;
begin
	if :new.id is null
	then
		select iltds_sample_genealogy_seq.nextval into seq_val from dual;
		:new.id := seq_val;
	end if;
exception
	when others then
		raise_application_error(-20001, 'Primary key violation on iltds_sample_genealogy: '||SQLERRM);
end iltds_sample_genealogy_pk_trg;
/
