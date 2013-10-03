---------------------------------------------------------------------------------------------
--	PART 1 - Run on GENPROD1 as SNAPADMIN_G
---------------------------------------------------------------------------------------------
BEGIN
Dbms_refresh.subtract('ARDAIS_owner.LEX_TO_GEN_ARDAIS_RFSH_GRP','RNA_RNA_LIST');
Dbms_refresh.subtract('ARDAIS_owner.LEX_TO_GEN_ARDAIS_RFSH_GRP','RNA_CHILDS');
COMMIT;
END;
/

begin
Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','RNA_RNA_LIST','SNAPSHOT');
Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','RNA_CHILDS','SNAPSHOT');
COMMIT;
END;
/

DROP SNAPSHOT ARDAIS_OWNER.RNA_RNA_LIST;
DROP SNAPSHOT ARDAIS_OWNER.RNA_CHILDS;
