/* Trigger to assign primary key if not specified in an INSERT. */

CREATE OR REPLACE TRIGGER ard_policy_pk_trg
before insert on ard_policy
for each row
declare
   seq_val	  number;
begin
   if :new.id is null
   then
      select ard_policy_pk_seq.nextval into seq_val from dual;
      :new.id := seq_val;
   end if;
exception
  when others then
    raise_application_error(-20001, 'Primary key violation on ard_policy: '||SQLERRM);
end ard_policy_pk_trg;
/
