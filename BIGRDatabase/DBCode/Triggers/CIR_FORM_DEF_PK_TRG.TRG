CREATE OR REPLACE TRIGGER cir_form_def_pk_trg
before insert on cir_form_definition
for each row
declare
	seq_val number;
begin
	if :new.form_definition_id is null
	then
		select cir_form_definition_seq.nextval into seq_val from dual;
		:new.form_definition_id := 'FD'||LPAD(TO_CHAR(seq_val), 8, '0');
	end if;
exception
	when others then
		raise_application_error(-20001, 'Primary key violation on cir_form_definition: '||SQLERRM);
end cir_form_def_pk_trg;
/
