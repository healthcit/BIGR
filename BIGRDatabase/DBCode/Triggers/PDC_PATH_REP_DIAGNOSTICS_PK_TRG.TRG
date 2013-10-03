CREATE OR REPLACE TRIGGER pdc_path_rep_diagnostic_pk_trg
before insert on pdc_path_report_diagnostics
for each row
declare
   seq_val	  number;
begin
   if :new.id is null
   then
      select pdc_path_rep_diagnostic_pk_seq.nextval into seq_val from dual;
      :new.id := seq_val;
   end if;
exception
  when others then
    raise_application_error(-20001, 'Primary key violation on pdc_path_report_diagnostics: '||SQLERRM);
end pdc_path_rep_diagnostic_pk_trg;
/