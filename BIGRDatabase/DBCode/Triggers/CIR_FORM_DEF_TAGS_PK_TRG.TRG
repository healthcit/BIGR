CREATE OR REPLACE TRIGGER cir_form_def_tags_pk_trg
before insert on cir_form_definition_tags
for each row
declare
	seq_val number;
begin
	if :new.id is null
	then
		select cir_form_definition_tags_seq.nextval into seq_val from dual;
		:new.id := seq_val;
	end if;
exception
	when others then
		raise_application_error(-20001, 'Primary key violation on cir_form_definition_tags: '||SQLERRM);
end cir_form_def_tags_pk_trg;
/
