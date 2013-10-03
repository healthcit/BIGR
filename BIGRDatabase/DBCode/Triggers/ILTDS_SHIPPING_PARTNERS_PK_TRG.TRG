/* Trigger to assign primary key if not specified in an INSERT. */

CREATE OR REPLACE TRIGGER ILTDS_SHIPPING_PARTNERS_PK_TRG
before insert on ILTDS_SHIPPING_PARTNERS
for each row
declare
	seq_val number;
begin
	if :new.id is null
	then
		select ILTDS_SHIPPING_PARTNERS_SEQ.nextval into seq_val from dual;
		:new.id := seq_val;
	end if;
exception
	when others then
		raise_application_error(-20001, 'Primary key violation on ILTDS_SHIPPING_PARTNERS: '||SQLERRM);
end ILTDS_SHIPPING_PARTNERS_PK_TRG;
/
