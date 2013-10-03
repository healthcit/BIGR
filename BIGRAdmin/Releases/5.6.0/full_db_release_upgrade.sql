---------------------------------------------------------------------------------------
--	File: 	full_db_release_upgrade.sql
--	Author:	GSB
--	Date:	10/08/2008 
--	
--	Purpose: This script will be run to upgrade an existing database to
--		 the next release.  It can only upgrade a database from one release to the
--		 next and cannot do multiple releases.
--
--
--      SWP-937  Moving a menu item out of BIGR Administration and into Informatics. - 10/14/2008
--      SWP-987  Removing an obsolete privilege.				     - 10/16/2008
--      SWP-988  We need to change KC notes fields from 1000 to 4000.		     - 10/24/2008 
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


-----------------------------------------------------------------------------------------------------------------
--  DML
-----------------------------------------------------------------------------------------------------------------
prompt ==========   SWP-937  ========== 
prompt  Moving a menu item
--temporarily disable the constraint ensuring the object id exists in
--ard_objects
alter table ard_user_access_module disable constraint FK_ARD_OBJS_USER_ACCESS_MODULE;

--update ard_user_access_module to use the new object_id
update ard_user_access_module set object_id = '30_ORM_070_VIEWGBOSS' where object_id = '40_ORM_070_VIEWGBOSS';

--update ard_objects to use the new object_id, top_menu. And
--privilege_id
update ard_objects set object_id = '30_ORM_070_VIEWGBOSS', top_menu = 'Informatics', privilege_id = '30_ORM_070_VIEWGBOSS'where object_id = '40_ORM_070_VIEWGBOSS';

--reenable the constraint ensuring the object id exists in
--ard_objects
alter table ard_user_access_module enable constraint FK_ARD_OBJS_USER_ACCESS_MODULE;


prompt ==========   END SWP-937 ========== 
prompt 
prompt ==========   SWP-987  ========== 
prompt  Removing an obsolete privilege.

delete from ard_user_access_module where object_id = '10_ILTDS_EDIT_PROPS';
delete from ard_objects where object_id = '10_ILTDS_EDIT_PROPS';

prompt ==========   END SWP-987 ========== 
prompt 
prompt 
prompt ==========  SWP-988   ========== 
prompt change KC notes fields from 1000 to 4000
ALTER TABLE CIR_ANCILLARY_STUDIES_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_BLANK_FIELDS_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_BLOO_GASE_ARTE_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_CASE_INFO_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_CHEMISTRY_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_CHEMISTRY_SURVEY_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_CHROMOSOME_STUDIES_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_CLIN_DIS_PROG_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_CLIN_PRES_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_COAGULATION_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_CYTOLOGY_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_FOLLOWUP_INFO_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_GENE_STUDIES_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_HEMATOLOGY_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_HEMATOLOGY_SURVEY_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_HISTOCHEMISTRY_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_HOSPITALIZATION_INFO_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_IMAGING_STUDIES_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_IMMUNOHISTOCHEMISTRY_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_IMMUNOPHENOTYPE_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_MICROBIOLOGY_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_PAST_MEDI_HIST_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_PATH_VERIFICATION_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_PROBLEM_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_PT_INFO_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_SKIN_TESTS_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_STAINS_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_SURGICAL_PATHOLOGY_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_SURGICAL_STUDIES_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_TREATMENT_NOTE MODIFY NOTE VARCHAR2(4000);
ALTER TABLE CIR_URINALYSIS_NOTE MODIFY NOTE VARCHAR2(4000);

prompt ==========   END SWP-988 ========== 
prompt 
commit
/
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