CREATE OR REPLACE TRIGGER iltds_derivation_pk_trg
before insert on iltds_derivation
for each row
declare
	seq_val number;
begin
	if :new.derivation_id is null
	then
		select iltds_derivation_seq.nextval into seq_val from dual;
		:new.derivation_id := 'DV'||LPAD(TO_CHAR(seq_val), 8, '0');
	end if;
exception
	when others then
		raise_application_error(-20001, 'Primary key violation on iltds_derivation: '||SQLERRM);
end iltds_derivation_pk_trg;
/
