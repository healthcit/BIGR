set linesize 200
spool step7_full_db_release2.log
DROP TRIGGER LIMS_PATH_EVAL_UPDATE_RNA
/
prompt ==========   MR8419   ==========   

declare
cursor user_groups_to_process is
  select aua.ardais_acct_key, aua.ardais_user_id, aua.wkgp_id
  from ard_user_access aua
  order by aua.ardais_acct_key, aua.ardais_user_id, aua.wkgp_id;
cursor privs_to_grant(p_workgroup_id in varchar2) is
  select awo.object_id
  from ard_workgroup_objects awo
  where
  awo.wkgp_id = p_workgroup_id
  order by awo.object_id;
v_account      ard_user_access.ardais_acct_key%TYPE;
v_username     ard_user_access.ardais_user_id%TYPE;
v_workgroup_id ard_user_access.wkgp_id%TYPE;
v_privilege_id ard_workgroup_objects.object_id%TYPE;
v_count        INTEGER;

begin
  --for every row in ard_user_access, grant to the user every privilege in the workgroup as long as they don't
  --already have that privilege
  for ugrec in user_groups_to_process loop
    v_account := ugrec.ardais_acct_key;
    v_username := ugrec.ardais_user_id;
    v_workgroup_id := ugrec.wkgp_id;
    dbms_output.put_line('Processing ' || v_workgroup_id || ' for ' || v_account || ':' || v_username || '...');
    for prec in privs_to_grant(v_workgroup_id) loop
      v_privilege_id := prec.object_id;
      select count(*) into v_count from ard_user_access_module where ardais_acct_key = v_account 
        and ardais_user_id = v_username and object_id = v_privilege_id;
      if (v_count = 0) then
        insert into ard_user_access_module(ARDAIS_ACCT_KEY, ARDAIS_USER_ID, OBJECT_ID, CREATE_DATE, CREATED_BY, UPDATE_DATE, UPDATED_BY) 
        values(v_account, v_username, v_privilege_id , sysdate, 'JEsielionis', sysdate, 'JEsielionis');
        dbms_output.put_line('Granted privilege ' || v_privilege_id || ' to ' || v_account || ':' || v_username);
      else
        dbms_output.put_line(v_account || ':' || v_username || ' already had privilege ' || v_privilege_id);
      end if;
    end loop;
  end loop;
  commit;
end;
/
drop table ard_user_access
/
drop table ard_workgroup_objects
/
drop table ard_workgroup
/
commit
/

prompt ==========   END MR8419   ==========  
prompt
prompt ==========   MR8603  ========== 

delete from ard_user_access_module where object_id = '15_ES_DEMO_CDA';

delete from ard_objects where object_id = '15_ES_DEMO_CDA';

delete from ard_user_access_module where object_id = '15_ES_DEMO_CDE';

delete from ard_objects where object_id = '15_ES_DEMO_CDE';

commit
/

prompt ==========   END MR8603 ==========
prompt
prompt ==========   MR8374   ========== 
-- DML
-- DDL See Above

--privilege stuff

update ard_objects set url = '/orm/users/findUsersStart.do' where object_id = '40_ORM_010_USERMNG';

delete from ard_user_access_module where object_id='15_ES_045_USRMANAG';

delete from ard_objects where object_id='15_ES_045_USRMANAG';

update ard_objects set url = '/orm/users/changeProfileStart.do' where object_id = '15_ES_050_PROFILE';

--clean up some privilege group stuff left behind

delete from ard_user_Access_module where object_id='40_ORM_020_PGMNG';

delete from ard_objects where object_id='40_ORM_020_PGMNG';

--make privilege_id the new primary key on ard_objects

update ard_objects set privilege_id = object_id;

alter table ard_objects modify (privilege_id not null);

alter table ard_objects add constraint priv_id_unique unique(privilege_id);

--refactoring of inventory group management history records

update iltds_btx_history set attrib_2 = attrib_1 where btx_type = 'MngUserLRs';

update iltds_btx_history set attrib_1 = 'User' where btx_type = 'MngUserLRs';

update iltds_btx_history h set attrib_3 = (select ardais_acct_key from es_Ardais_user where ardais_user_id = h.attrib_2) where btx_type = 'MngUserLRs';

update iltds_btx_history set btx_type = 'MngIG' where btx_type = 'MngUserLRs';

--Ardais to System Owner related changes

delete from ard_lookup_all where category='ES_ARDAIS_ACCOUNT' and ref = 'Ardais_Acct_Type';

update es_ardais_account set ardais_parent_acct_comp_desc = 'System Owner'where ardais_acct_type = 'AR';

update es_Ardais_account set ardais_acct_type = 'SO' where ardais_Acct_type = 'AR';

commit
/

prompt ==========   END MR8374   ========== 

prompt ==========   MR8434 DATA   ========== 
insert into ard_objects 
(object_id, object_description, create_date, created_by, update_date, updated_by, long_description, url, top_menu, sub_menu, object_type,PRIVILEGE_ID)
values ('40_ORM_062_FORMCREATE', 'Form Creator', sysdate, 'mcacciapouti', sysdate, 'mcacciapouti', 'ORM', '/kc/formdef/start.do', 'BIGR Administration', 'SUB_MENU', 'M','40_ORM_062_FORMCREATE');

insert into ard_user_access_module 
(ardais_acct_key, ardais_user_id, object_id, create_date, created_by, update_date, updated_by)
values ('ARD0000001', 'Ardais', '40_ORM_062_FORMCREATE', sysdate, 'mcacciapouti', sysdate, 'mcacciapouti');

commit
/

update ard_objects set object_description = 'Form Designer' where object_id = '40_ORM_062_FORMCREATE'
/
commit
/
prompt ==========   END MR8434 DATA   ========== 
prompt
prompt
prompt ==========   MR8453 DATA   ========== 
--DELETE FROM ARD_CONCEPT_GRAPH WHERE GRAPH_CONTEXT = 'STORAGE_TYPES';

--DELETE FROM ARD_CONCEPT_GRAPH WHERE GRAPH_CONTEXT = 'SAMPLE_TYPES';

--DELETE FROM ARD_CONCEPT_GRAPH WHERE GRAPH_CONTEXT = 'SAMPLE_ATTRIBUTES';

--DELETE FROM ARD_CONCEPT_GRAPH WHERE GRAPH_CONTEXT = 'SAMPLE_TYPE_STORAGE_MAPPING';

--DELETE FROM ARD_CONCEPT_GRAPH WHERE GRAPH_CONTEXT = 'SAMPLE_TYPE_ATTRIBUTE_MAPPING';

commit
/
prompt ==========   END MR8453 DATA   ========== 
prompt
prompt
prompt ==========   MR8418 DATA   ========== 
UPDATE ILTDS_BTX_HISTORY hist
SET ATTRIB_5 = 
  (SELECT CONCAT(usr.USER_FIRSTNAME, CONCAT(' ', usr.USER_LASTNAME))
  FROM  ES_ARDAIS_USER usr 
  WHERE hist.ATTRIB_5 = usr.ARDAIS_USER_ID)
Where hist.BTX_TYPE = 'DerivOp' 
	  AND hist.ATTRIB_5 IS NOT NULL
/
commit
/
prompt ==========   END MR8418 DATA   ========== 
prompt
prompt
prompt ==========   MR8440 DATA   ========== 
UPDATE 	ARD_OBJECTS
SET	OBJECT_DESCRIPTION = REPLACE(OBJECT_DESCRIPTION, 'Ardais', 'Owner'), 
	URL = REPLACE(URL, 'Ardais', 'Owner'),
	TOP_MENU = REPLACE(TOP_MENU, 'Ardais', 'Owner')
WHERE OBJECT_DESCRIPTION LIKE '%Ardais%' OR 
	  URL LIKE '%Ardais%' OR
	  TOP_MENU LIKE '%Ardais%'
/
commit
/
prompt ==========   END MR8440 DATA   ========== 
prompt
prompt
--prompt ==========   MR8305 DATA   ========== 

--delete from ARD_CONCEPT_GRAPH where GRAPH_CONTEXT = 'SHIP_TO_ARDAIS_SCHEME';

--commit
--/
--prompt ==========   END MR8305 DATA   ========== 
--prompt
--prompt

prompt
prompt ==========   MR8514 DATA   ========== 

insert into ard_objects (object_id, object_description, create_date, created_by, update_date, updated_by, long_description, url, top_menu, sub_menu, object_type,privilege_id)
values('10_ILTDS_147_ICP_SUSR', 'Privilege: ICP Super User', sysdate, 'JEsielionis', sysdate, 'JEsielionis', 'ILTDS', null, null, null, 'P', '10_ILTDS_147_ICP_SUSR');

commit
/

prompt ==========   END MR8514 DATA   ========== 
prompt
prompt
--prompt ==========   MR8206 DATA   ========== 
--delete from ARD_CONCEPT_GRAPH where GRAPH_CONTEXT like 'IHC%';
--commit
--/

--prompt ==========   END MR8206 DATA   ==========

prompt ==========   MR8519 DATA   ========== 
insert into ard_objects (object_id, object_description, create_date, created_by, update_date, updated_by, long_description, url, top_menu, sub_menu, object_type,privilege_id) 
values('12_ILTDS_20_PA_RD_REQ', 'Privilege: Create Pathology, R and D Requests', sysdate, 'JEsielionis', sysdate, 'JEsielionis', 'ILTDS', null, null, null, 'P', '12_ILTDS_20_PA_RD_REQ');

commit
/

prompt ==========   END MR8519 DATA   ==========
prompt 
prompt ==========   MR8558 DATA   ========== 

update ard_objects set object_description = 'Donor Information' where object_id = '25_PDC_020_DNRPRF'
/
commit
/

prompt ==========   END MR8558 DATA   ==========
prompt 
prompt ==========   MR8461 DATA   ========== 

update ard_objects set top_menu = 'BIGR Administration' where object_id ='40_ORM_065_VIEWSYSCFG';



insert into ard_objects values ('50_IMP_70_DERIV',
				 'Derivative Operations',
				  sysdate,
				 'sthomashow',
				  sysdate,
				 'sthomashow',
				 'Sample Registration',
				 '/iltds/derivation/derivationBatchStart.do',
				 'Sample Registration',
				 'SUB_MENU',
				 'M',
				 '50_IMP_70_DERIV');


update ard_user_access_module set object_id = '50_IMP_70_DERIV' where object_id ='60_ILTDS_50_DERIV';

delete from ard_objects where object_id ='60_ILTDS_50_DERIV';

insert into ard_objects values ('30_ORM_040_OTHER',
				 'Other Code Editor',
				  sysdate,
				 'sthomashow',
				  sysdate,
				 'sthomashow',
				 'ORM',
				 '/ddc/Dispatch?op=OcePrepare',
				 'Informatics',
				 'SUB_MENU',
				 'M',
				 '30_ORM_040_OTHER');
update ard_user_access_module set object_id = '30_ORM_040_OTHER' where object_id ='25_PDC_010_OTHER';

delete from ard_objects where object_id ='25_PDC_010_OTHER';

commit
/

prompt ==========   END MR8461 DATA   ==========
prompt 
prompt ==========   MR8606 DATA   ========== 

delete from ard_user_access_module where object_id = '25_PDC_055_CRF';

delete from ard_objects where object_id = '25_PDC_055_CRF';

commit
/

prompt ==========   END MR8606 DATA   ==========
prompt 
prompt ==========   MR8592 DATA   ========== 

delete from ard_user_access_module where object_id = '25_PDC_045_DP2';

delete from ard_objects where object_id = '25_PDC_045_DP2';

commit
/

prompt ==========   END MR8592 DATA   ==========
prompt 
prompt ==========   RUN FINISH SCRIPTS   ========== 

-- @Application_grants.sql ARDAIS_OWNER/ARDAIS_OWNER GENPROD1 ardais_

-- @create_new_priv_syns.sql ARDAIS_OWNER/ARDAIS_OWNER GENPROD1 ardais_

Prompt 
prompt Compile Schema
-----------------------------------------------------------------------------------------------------------------
-- SQLPlus finish
--
-- Compile Schema                                                                  
BEGIN
  DBMS_UTILITY.COMPILE_SCHEMA (USER);
END;
/

spool off
