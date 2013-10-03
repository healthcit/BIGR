------------------------------------------------------------------------------------------
--	PART 2 - Run on LEXPROD1 as REPADMIN_L
------------------------------------------------------------------------------------------
create snapshot log on ARDAIS_OWNER.RNA_RNA_LIST
tablespace ILTDS_TBL_TBS
storage (initial 1M next 1M pctincrease 0)
with primary key
excluding new values;

create snapshot log on ARDAIS_OWNER.RNA_CHILDS
tablespace ILTDS_TBL_TBS
storage (initial 1M next 1M pctincrease 0)
with primary key
excluding new values;

begin
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','RNA_RNA_LIST','TABLE',null,null,null,null,null,'LEXINGTON_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','RNA_CHILDS','TABLE',null,null,null,null,null,'LEXINGTON_ARDAIS_OWNER');
commit;
end;
/


begin
Dbms_repcat.generate_replication_support('ARDAIS_OWNER','RNA_RNA_LIST','TABLE');
Dbms_repcat.generate_replication_support('ARDAIS_OWNER','RNA_CHILDS','TABLE');
commit;
end;
/
