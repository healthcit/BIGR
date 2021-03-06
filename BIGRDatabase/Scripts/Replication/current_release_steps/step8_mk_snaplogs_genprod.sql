set linesize 500
spool step8_mk_snaplogs_genprod.log

-- Always create the snap log before the snapshot

show user
select * from global_name;

CREATE MATERIALIZED VIEW LOG ON ARDAIS_OWNER.ES_SHOPPING_CART_DETAIL
TABLESPACE ES_TBL_TBS
NOCACHE
LOGGING
NOPARALLEL
WITH PRIMARY KEY
EXCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON ARDAIS_OWNER.ES_ARDAIS_ACCOUNT
TABLESPACE ES_TBL_TBS
NOCACHE
LOGGING
NOPARALLEL
WITH PRIMARY KEY
EXCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON ARDAIS_OWNER.ARD_OBJECTS
TABLESPACE OTHER_TBL_TBS
NOCACHE
LOGGING
NOPARALLEL
WITH PRIMARY KEY
EXCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON ARDAIS_OWNER.ES_ARDAIS_USER
TABLESPACE ES_TBL_TBS
NOCACHE
LOGGING
NOPARALLEL
WITH PRIMARY KEY
EXCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON ARDAIS_OWNER.ARD_USER_ACCESS_MODULE
TABLESPACE USERS
NOCACHE
LOGGING
NOPARALLEL
WITH PRIMARY KEY
EXCLUDING NEW VALUES;

CREATE MATERIALIZED VIEW LOG ON ARDAIS_OWNER.ARD_LOOKUP_ALL
TABLESPACE OTHER_TBL_TBS
NOCACHE
LOGGING
NOPARALLEL
WITH PRIMARY KEY
EXCLUDING NEW VALUES;

--CREATE MATERIALIZED VIEW LOG ON ARDAIS_OWNER.ARD_CONCEPT_GRAPH
--TABLESPACE OTHER_TBL_TBS
--NOCACHE
--LOGGING
--NOPARALLEL
--WITH PRIMARY KEY
--EXCLUDING NEW VALUES;

begin
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ES_SHOPPING_CART_DETAIL','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ES_ARDAIS_ACCOUNT','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ARD_OBJECTS','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ES_ARDAIS_USER','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ARD_USER_ACCESS_MODULE','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ARD_LOOKUP_ALL','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
--Dbms_repcat.create_master_repobject('ARDAIS_OWNER','ARD_CONCEPT_GRAPH','TABLE',null,null,null,null,null,'GENUITY_ARDAIS_OWNER');
commit;
end;
/


begin
dbms_repcat.generate_replication_support('ARDAIS_OWNER','ES_SHOPPING_CART_DETAIL','TABLE');
dbms_repcat.generate_replication_support('ARDAIS_OWNER','ES_ARDAIS_ACCOUNT','TABLE');
Dbms_repcat.generate_replication_support('ARDAIS_OWNER','ARD_OBJECTS','TABLE');
dbms_repcat.generate_replication_support('ARDAIS_OWNER','ES_ARDAIS_USER','TABLE');
dbms_repcat.generate_replication_support('ARDAIS_OWNER','ARD_USER_ACCESS_MODULE','TABLE');
dbms_repcat.generate_replication_support('ARDAIS_OWNER','ARD_LOOKUP_ALL','TABLE');
--dbms_repcat.generate_replication_support('ARDAIS_OWNER','ARD_CONCEPT_GRAPH','TABLE');
commit;
end;
/

spool off
