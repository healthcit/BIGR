--------------------------------------------------------------------------------
--	PART 1 - Run on LEXPROD1 as SNAPADMIN_L
--------------------------------------------------------------------------------
begin

--Dbms_refresh.subtract('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARD_CONCEPT_GRAPH');
Dbms_refresh.subtract('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARD_DX_GROUPS');
Dbms_refresh.subtract('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ARD_POLICY');
Dbms_refresh.subtract('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ES_ARDAIS_USER');
Dbms_refresh.subtract('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ILTDS_BLOOD_COLLECTION');
Dbms_refresh.subtract('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ILTDS_BTX_HISTORY');
Dbms_refresh.subtract('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','ILTDS_SAMPLE');
Dbms_refresh.subtract('ARDAIS_owner.GEN_TO_LEX_ARDAIS_RFSH_GRP','PDC_LOOKUP');
commit;
end;
/

begin

--Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','ARD_CONCEPT_GRAPH','SNAPSHOT');
Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','ARD_DX_GROUPS','SNAPSHOT');
Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','ARD_POLICY','SNAPSHOT');
Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','ES_ARDAIS_USER','SNAPSHOT');
Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','ILTDS_BLOOD_COLLECTION','SNAPSHOT');
Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','ILTDS_BTX_HISTORY','SNAPSHOT');
Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','ILTDS_SAMPLE','SNAPSHOT');
Dbms_repcat.drop_snapshot_repobject('ARDAIS_OWNER','PDC_LOOKUP','SNAPSHOT');
commit;
end;
/


--DROP SNAPSHOT ARDAIS_OWNER.ARD_CONCEPT_GRAPH;
DROP SNAPSHOT ARDAIS_OWNER.ARD_DX_GROUPS;
DROP SNAPSHOT ARDAIS_OWNER.ARD_POLICY;
DROP SNAPSHOT ARDAIS_OWNER.ES_ARDAIS_USER;
DROP SNAPSHOT ARDAIS_OWNER.ILTDS_BLOOD_COLLECTION;
DROP SNAPSHOT ARDAIS_OWNER.ILTDS_BTX_HISTORY;
DROP SNAPSHOT ARDAIS_OWNER.ILTDS_SAMPLE;
DROP SNAPSHOT ARDAIS_OWNER.PDC_LOOKUP;

