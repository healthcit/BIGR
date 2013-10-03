------------------------------------------------------------------------------------------
--	PART 1 - Run on GENPROD1 as REPADMIN_G
------------------------------------------------------------------------------------------
--create snapshot log on ARDAIS_owner.ARD_CONCEPT_GRAPH
--tablespace OTHER_TBL_TBS             
--storage (initial 1M next 1M pctincrease 0)
--with primary key                    
--excluding new values;

create snapshot log on ARDAIS_OWNER.ARD_POLICY
tablespace OTHER_TBL_TBS
storage (initial 1M next 1M pctincrease 0)
with primary key
excluding new values;

create snapshot log on ARDAIS_OWNER.ES_ARDAIS_USER
tablespace ES_TBL_TBS
storage (initial 1M next 1M pctincrease 0)
with primary key
excluding new values;

create snapshot log on ARDAIS_owner.ILTDS_BLOOD_COLLECTION
tablespace ILTDS_TBL_TBS             
storage (initial 1M next 1M pctincrease 0)
with primary key                    
excluding new values;

create snapshot log on ARDAIS_owner.ILTDS_BTX_HISTORY
tablespace ILTDS_TBL_TBS             
storage (initial 1M next 1M pctincrease 0)
with primary key                    
excluding new values;

create snapshot log on ARDAIS_owner.ILTDS_SAMPLE
tablespace ILTDS_TBL_TBS             
storage (initial 1M next 1M pctincrease 0)
with primary key                    
excluding new values;

create snapshot log on ARDAIS_owner.PDC_LOOKUP
tablespace OTHER_TBL_TBS             
storage (initial 1M next 1M pctincrease 0)
with primary key                    
excluding new values;


begin
--Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ARD_CONCEPT_GRAPH','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ARD_POLICY','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ES_ARDAIS_USER','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ILTDS_BLOOD_COLLECTION','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ILTDS_BTX_HISTORY','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ILTDS_SAMPLE','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','PDC_LOOKUP','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
commit;
end;
/


begin
--dbms_repcat.generate_replication_support('ARDAIS_OWNER','ARD_CONCEPT_GRAPH','TABLE');
dbms_repcat.generate_replication_support('ARDAIS_OWNER','ARD_POLICY','TABLE');
Dbms_repcat.generate_replication_support('ARDAIS_OWNER','ES_ARDAIS_USER','TABLE');
dbms_repcat.generate_replication_support('ARDAIS_OWNER','ILTDS_BLOOD_COLLECTION','TABLE');
dbms_repcat.generate_replication_support('ARDAIS_OWNER','ILTDS_BTX_HISTORY','TABLE');
dbms_repcat.generate_replication_support('ARDAIS_OWNER','ILTDS_SAMPLE','TABLE');
dbms_repcat.generate_replication_support('ARDAIS_OWNER','PDC_LOOKUP','TABLE');
commit;
end;
/


