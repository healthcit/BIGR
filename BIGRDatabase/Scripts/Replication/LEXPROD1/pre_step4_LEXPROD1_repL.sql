--------------------------------------------------------------------------------
--	PART 2 - Run on LEXPROD1 as REPADMIN_L
--------------------------------------------------------------------------------
begin
Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','RNA_RNA_LIST','TABLE');
Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','RNA_CHILDS','TABLE');
COMMIT;
end;
/

DROP SNAPSHOT LOG ON ARDAIS_OWNER.RNA_RNA_LIST;
DROP SNAPSHOT LOG ON ARDAIS_OWNER.RNA_CHILDS;
