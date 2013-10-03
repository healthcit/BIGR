---------------------------------------------------------------------------------------
--	File: 	full_db_release_upgrade.sql
--	Author:	GSB
--	Date:	01/21/2009 
--	
--	Purpose: This script will be run to upgrade an existing database to
--		 the next release.  It can only upgrade a database from one release to the
--		 next and cannot do multiple releases.
--
--
--      SWP-962 - 	KC View Layer as GSB reporting solution			- 01/15/2009
--                 NOTE: use sqlplus /nolog and then @full_db_release_upgrade.sql since this upgrade
--                       needs to be run as system
--
-----------------------------------------------------------------------------------------------------------------
--
-----------------------------------------------------------------------------------------------------------------
-- SQLPlus setup
--
set linesize 200 echo off feedback on verify on echo off
-- Some DML uses an ampersand so lose the define
spool full_db_release_upgrade.log
--
-----------------------------------------------------------------------------------------------------------------
--  DDL
-----------------------------------------------------------------------------------------------------------------
undefine v1
prompt ==========   SWP-962  ========== 
prompt
prompt Log in as system to complete the upgrade
prompt
connect system
show user
prompt
accept v1 char prompt 'What is the prefix name of the VIEWS (no underscore)? '


CREATE USER &v1._VIEWS
  IDENTIFIED BY &v1._VIEWS
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 2 System Privileges for &v1._VIEWS 
  GRANT CREATE SESSION TO &v1._VIEWS;
  GRANT UNLIMITED TABLESPACE TO &v1._VIEWS;
  GRANT CREATE SYNONYM TO &v1._VIEWS;
prompt ==========   END SWP-962 ========== 
prompt 

commit
/

prompt
prompt
set feedback on
-----------------------------------------------------------------------------------------------------------------
--  DML
-----------------------------------------------------------------------------------------------------------------
--  prompt ==========   SWP-  ========== 
--  prompt  

--  prompt ==========   END SWP- ========== 
--  prompt 

--  commit
--  /
-- ==========   RUN FINISH SCRIPTS   ========== 

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
