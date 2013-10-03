---------------------------------------------------------------------------------------------
--	PART 2 - Run on GENPROD1 as REPADMIN_G
---------------------------------------------------------------------------------------------

begin
--Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','ARD_CONCEPT_GRAPH','TABLE');
Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','ARD_DX_GROUPS','TABLE');
Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','ARD_POLICY','TABLE');
Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','ES_ARDAIS_USER','TABLE');
Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','ILTDS_BLOOD_COLLECTION','TABLE');
Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','ILTDS_BTX_HISTORY','TABLE');
Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','ILTDS_SAMPLE','TABLE');
Dbms_repcat.drop_master_repobject('ARDAIS_OWNER','PDC_LOOKUP','TABLE');
commit;
end;
/

--DROP SNAPSHOT LOG ON ARDAIS_OWNER.ARD_CONCEPT_GRAPH;
DROP SNAPSHOT LOG ON ARDAIS_OWNER.ARD_DX_GROUPS;
DROP SNAPSHOT LOG ON ARDAIS_OWNER.ARD_POLICY;
DROP SNAPSHOT LOG ON ARDAIS_OWNER.ES_ARDAIS_USER;
DROP SNAPSHOT LOG ON ARDAIS_OWNER.ILTDS_BLOOD_COLLECTION;
DROP SNAPSHOT LOG ON ARDAIS_OWNER.ILTDS_BTX_HISTORY;
DROP SNAPSHOT LOG ON ARDAIS_OWNER.ILTDS_SAMPLE;
DROP SNAPSHOT LOG ON ARDAIS_OWNER.PDC_LOOKUP;
