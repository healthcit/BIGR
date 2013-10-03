------------------------------------------------------------------------------------------
--	PART 2 - Run on GENPROD1 as SNAPADMIN_G
------------------------------------------------------------------------------------------

create snapshot ARDAIS_OWNER.RNA_RNA_LIST
tablespace OTHER_TBL_TBS
storage (initial 160K next 64K pctincrease 0)
build immediate
using index tablespace OTHER_IDX_TBS
storage (initial 128K next 128K pctincrease 0)
refresh force
on demand as
select * from ARDAIS_OWNER.RNA_RNA_LIST@LEXPROD1;

create snapshot ARDAIS_OWNER.RNA_CHILDS
tablespace OTHER_TBL_TBS
storage (initial 512K next 256K pctincrease 0)
build immediate
using index tablespace OTHER_IDX_TBS
storage (initial 128K next 64K pctincrease 0)
refresh force
on demand as
select * from ARDAIS_OWNER.RNA_CHILDS@LEXPROD1;


begin
Dbms_repcat.create_snapshot_repobject('ARDAIS_OWNER','RNA_RNA_LIST','SNAPSHOT',NULL,NULL,'LEXINGTON_ARDAIS_OWNER');
Dbms_repcat.create_snapshot_repobject('ARDAIS_OWNER','RNA_CHILDS','SNAPSHOT',NULL,NULL,'LEXINGTON_ARDAIS_OWNER');
commit;
end;
/

begin
Dbms_refresh.add('ARDAIS_owner.LEX_TO_GEN_ARDAIS_RFSH_GRP','RNA_RNA_LIST');
Dbms_refresh.add('ARDAIS_owner.LEX_TO_GEN_ARDAIS_RFSH_GRP','RNA_CHILDS');
commit;
end;
/


