---------------------------------------------------------------------------------------
--	File: 	full_db_release_upgrade.sql
--	Author:	GSB
--	Date:	08/19/2008 (Nikita)
--	
--	Purpose: This script will be run to upgrade an existing database to
--		 the next release.  It can only upgrade a database from one release to the
--		 next and cannot do multiple releases.
--
--
--      SWP-312 -  Implementation of a new transaction in BIGR                   - 09/17/08
--      SWP-956 -  Changes for sample id generation.                             - 09/23/08
--  
--
-----------------------------------------------------------------------------------------------------------------
--
-----------------------------------------------------------------------------------------------------------------
-- SQLPlus setup
--
set linesize 200 echo off feedback on verify on 
-- Some DML uses an ampersand so lose the define
SET DEFINE OFF
spool full_db_release_upgrade.log
--
-----------------------------------------------------------------------------------------------------------------
--  DDL
-----------------------------------------------------------------------------------------------------------------
prompt ==========   SWP-XXX  ========== 
prompt 


prompt ==========   END SWP-XXX ========== 
prompt 

-----------------------------------------------------------------------------------------------------------------
--  DML
-----------------------------------------------------------------------------------------------------------------
prompt ==========   SWP-312  ========== 
prompt 
prompt 
INSERT INTO ARD_OBJECTS (OBJECT_ID, OBJECT_DESCRIPTION, CREATE_DATE, CREATED_BY, UPDATE_DATE, UPDATED_BY, LONG_DESCRIPTION, URL, TOP_MENU, SUB_MENU,OBJECT_TYPE, PRIVILEGE_ID)
VALUES('50_IMP_90_PRNTLBLS','Print Labels', sysdate, 'JEsielionis', sysdate, 'JEsielionis', 'Sample Registration', '/dataImport/printLabelsStart.do','Sample Registration', 'SUB_MENU', 'M', '50_IMP_90_PRNTLBLS');
prompt ==========   END SWP-312 ========== 
prompt 


prompt COMMIT
commit
/

prompt ==========   SSWP-956  ========== 
prompt 
alter table iltds_assigned_sample_ids add (sample_id_next varchar2(12))
/
update iltds_assigned_sample_ids s set s.sample_id_next = 'SX'||TRIM(LEADING ' ' FROM upper(to_char(to_number(substr(s.sample_id_end, 3, 8), 'xxxxxxxx') + 1,'0xxxxxxx')))
/
commit
/
alter table iltds_assigned_sample_ids modify sample_id_next not null
/
prompt ==========   END SWP-956 ========== 
prompt 

-- ==========   RUN FINISH SCRIPTS   ========== 

-- @Application_grants.sql ARDAIS_OWNER/ARDAIS_OWNER GENPROD1 ardais_

-- @create_new_priv_syns.sql ARDAIS_OWNER/ARDAIS_OWNER GENPROD1 ardais_

-- Prompt Note
-- prompt compile all triggers if needed
-----------------------------------------------------------------------------------------------------------------
-- SQLPlus finish
--
-- Compile Schema                                                                  
-- BEGIN
--  DBMS_UTILITY.COMPILE_SCHEMA (USER);
-- END;
-- /

spool off
-----------------------------------------------------------------------