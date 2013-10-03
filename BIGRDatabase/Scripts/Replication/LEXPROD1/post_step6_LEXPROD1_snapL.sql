---------------------------------------------------------------------------------
--	PART 1 - Run on LEXPROD1 as SNAPADMIN_L
---------------------------------------------------------------------------------
--create snapshot ARDAIS_OWNER.ARD_CONCEPT_GRAPH
--tablespace OTHER_TBL_TBS
--storage (initial 1M next 1M pctincrease 0)
--build immediate
--using index tablespace OTHER_IDX_TBS
--storage (initial 512K next 128K pctincrease 0)
--refresh force
--on demand as
--select * from ARDAIS_OWNER.ARD_CONCEPT_GRAPH@GENPROD1;

create snapshot ARDAIS_OWNER.ARD_POLICY
tablespace OTHER_TBL_TBS
storage (initial 1M next 1M pctincrease 0)
build immediate
using index tablespace OTHER_IDX_TBS
storage (initial 512K next 128K pctincrease 0)
refresh force
on demand as
select * from ARDAIS_OWNER.ARD_POLICY@GENPROD1;

create snapshot ARDAIS_OWNER.ES_ARDAIS_USER
tablespace ILTDS_TBL_TBS
storage (initial 512K next  256K pctincrease 0)
build immediate
using index tablespace ILTDS_IDX_TBS
storage (initial 256K next  128K pctincrease 0)
refresh force
on demand as
select * from ARDAIS_OWNER.ES_ARDAIS_USER@GENPROD1;

create snapshot ARDAIS_OWNER.ILTDS_BLOOD_COLLECTION
tablespace OTHER_TBL_TBS
storage (initial 1M next 1M pctincrease 0)
build immediate
using index tablespace OTHER_IDX_TBS
storage (initial 1M next 1M pctincrease 0)
refresh force
on demand as
select * from ARDAIS_OWNER.ILTDS_BLOOD_COLLECTION@GENPROD1;

create snapshot ARDAIS_OWNER.ILTDS_BTX_HISTORY
tablespace ILTDS_TBL_TBS
storage (initial 1M next 1M pctincrease 0)
build immediate
using index tablespace ILTDS_IDX_TBS
storage (initial 1M next 1M pctincrease 0)
refresh force
on demand as
select * from ARDAIS_OWNER.ILTDS_BTX_HISTORY@GENPROD1;

create snapshot ARDAIS_OWNER.ILTDS_SAMPLE
tablespace OTHER_TBL_TBS
storage (initial 32M next 8M pctincrease 0)
build immediate
using index tablespace OTHER_IDX_TBS
storage (initial 4M next 2M pctincrease 0)
refresh force
on demand as
select * from ARDAIS_OWNER.ILTDS_SAMPLE@GENPROD1;

create snapshot ARDAIS_OWNER.PDC_LOOKUP
tablespace OTHER_TBL_TBS
storage (initial 2M next 1M pctincrease 0)
build immediate
using index tablespace OTHER_IDX_TBS
storage (initial 512K next 128K pctincrease 0)
refresh force
on demand as
select * from ARDAIS_OWNER.PDC_LOOKUP@GENPROD1;

begin
--Dbms_repcat.create_snapshot_repobject('ARDAIS_OWNER','ARD_CONCEPT_GRAPH','SNAPSHOT',NULL,NULL,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_snapshot_repobject('ARDAIS_OWNER','ARD_POLICY','SNAPSHOT',NULL,NULL,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_snapshot_repobject('ARDAIS_OWNER','ES_ARDAIS_USER','SNAPSHOT',NULL,NULL,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_snapshot_repobject('ARDAIS_OWNER','ILTDS_BLOOD_COLLECTION','SNAPSHOT',NULL,NULL,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_snapshot_repobject('ARDAIS_OWNER','ILTDS_BTX_HISTORY','SNAPSHOT',NULL,NULL,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_snapshot_repobject('ARDAIS_OWNER','ILTDS_SAMPLE','SNAPSHOT',NULL,NULL,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_snapshot_repobject('ARDAIS_OWNER','PDC_LOOKUP','SNAPSHOT',NULL,NULL,'GENUITY_ARDAIS_OWNER');
commit;
end;
/


begin
--Dbms_refresh.add('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARDAIS_OWNER.ARD_CONCEPT_GRAPH');
Dbms_refresh.add('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARDAIS_OWNER.ARD_POLICY');
Dbms_refresh.add('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARDAIS_OWNER.ES_ARDAIS_USER');
Dbms_refresh.add('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARDAIS_OWNER.ILTDS_BLOOD_COLLECTION');
Dbms_refresh.add('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARDAIS_OWNER.ILTDS_BTX_HISTORY');
Dbms_refresh.add('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARDAIS_OWNER.ILTDS_SAMPLE');
Dbms_refresh.add('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARDAIS_OWNER.PDC_LOOKUP');
commit;
end;
/

