------------------------------------------------------------------------------
-- File_name:  Application_grants.sql
-- Author:	Dean Marsh
-- Date: 02/04/02
--
-- Modified: Phil DiPrima
-- Date: 01/15/09
--       Added a new schema to the script
--
-- Purpose:  Running this script will revoke and then re-generate all of the
--    appropriate application grants required between each of the
--    different users.
--
-- Required Parameters:
--   This script MUST be run with the following parameters:
--     con_string = the username and password of the OWNER schema for the
--         application in the form <username>/<password>.
--     con_db = the database that the OWNER schema exists in.
--     owner_group = the group of application users that is getting the
--         grants (e.g., for qa, it is "qa_", for patch, it is "patch_".)
--     ads_password = the password of the ADS schema associated with the
--         specified OWNER schema
--
--  Example:  @Application_grants.sql qa_owner/qa_password LEXDEV1 qa_ ads_password
--
------------------------------------------------------------------------------

define con_string=&1
define con_db=&2
define owner_group=&3
define ads_password=&4

set verify off feedback off echo off linesize 300 trimspool on pagesize 0 headsep off

column which_ads noprint new_value vads

select decode(lower('&owner_group'),'ardais_','',lower('&owner_group')) which_ads
from dual;

-------------------------------ARDAIS_OWNER-----------------------------------

connect &con_string@&con_db

spool &owner_group.owner_revoke.sql

select 'revoke ' || privilege || ' on ' || table_name || ' from ' || grantee || ';'
from user_tab_privs_made
where table_name not like '%$%';

spool off

spool &owner_group.owner_grants.sql

select 'grant select, insert, update, delete on ' || table_name || ' to &owner_group.user;'
from user_tables
where table_name not like 'RNA%'
  and table_name not like '%BTX%'
  and table_name not like 'TMA%'
  and table_name not like '%$%';

select 'grant select on ' || table_name || ' to &owner_group.user;'
from user_tables
where (table_name like 'RNA%' or table_name like '%BTX%' or table_name like 'TMA%')
  and table_name not like '%$%';

select 'grant select on ' || view_name || ' to &owner_group.user;'
from user_views
where view_name not like '%$%';

select 'grant select on ' || sequence_name || ' to &owner_group.user;'
from user_sequences
where sequence_name not like 'RNA%'
  and sequence_name not like 'TMA%'
  and sequence_name not like '%BTX%'
  and sequence_name not like '%$%';

-- We don't create synonyms for the BIGR_USE_ID_NAMESPACE procedure because
-- only GSBio should ever run it, and it could create major problems if it
-- was used incorrectly in a way that resulted in more than one installation
-- using the same namespace id.

select 'grant execute on ' || object_name || ' to &owner_group.user;'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE')
  and object_name <> 'BIGR_USE_ID_NAMESPACE'
  and object_name not like 'RNA%'
  and object_name not like 'TMA%'
  and object_name not like '%$%';

select 'grant select, insert, update, delete on ' || table_name || ' to &owner_group.deriv;'
from user_tables
where (table_name like 'RNA%' or table_name like 'TMA%')
  and table_name not like '%$%';

select 'grant select on ' || table_name || ' to &owner_group.deriv;'
from user_tables
where table_name not like 'RNA%'
  and table_name not like 'TMA%'
  and table_name not like '%$%';

select 'grant select on ' || view_name || ' to &owner_group.deriv;'
from user_views
where view_name not like '%$%';
  
select 'grant select on ' || sequence_name || ' to &owner_group.deriv;'
from user_sequences
where (sequence_name like 'RNA%' or sequence_name like 'TMA%')
  and sequence_name not like '%$%';

select 'grant select on ' || table_name || ' to &owner_group.reader;'
from user_tables
where table_name not like '%$%';

select 'grant select on ' || view_name || ' to &owner_group.reader;'
from user_views
where view_name not like '%$%';

select 'grant select on ' || table_name || ' to &owner_group.util;'
from user_tables
where table_name not like '%$%';

select 'grant select on ' || view_name || ' to &owner_group.util;'
from user_views
where view_name not like '%$%';

select 'grant select on ' || view_name || ' to &owner_group.views;'
from user_views
where view_name like '%_V%'
and view_name not in('LIMS_ADS_ILTDS_V','LIMS_BIGR_LIBRARY_V','RNA_HOLD_AMOUNT_V');

select 'grant execute on btx to &vads.ads_user;' from dual;

select 'grant select on lims_ads_iltds_v to &vads.ads_user;' from dual;

select 'grant select on lims_bigr_library_v to &vads.ads_user;' from dual;

select 'grant select on iltds_sample to &vads.ads_user;' from dual;

select 'grant select on pdc_dx_tc_hierarchy to &vads.ads_user;' from dual;

select 'grant select on lims_pathology_evaluation to &vads.ads_user;' from dual;

select 'grant select on lims_slide to &vads.ads_user;' from dual;

select 'grant select on lims_pe_features to &vads.ads_user;' from dual;

select 'grant insert on iltds_btx_involved_object to &owner_group.user;' from dual;

select 'grant execute on FN_LAST_DATETIME to &owner_group.deriv;' from dual;

select 'grant execute on FN_LAST_STATUS to &owner_group.deriv;' from dual;

select 'grant execute on HEXTODEC to &owner_group.deriv;' from dual;

select 'grant execute on BIGR_REMOVE_FROM_HOLD to &owner_group.deriv;' from dual;

select 'grant execute on FN_LAST_DATETIME to &owner_group.reader;' from dual;

select 'grant execute on FN_LAST_STATUS to &owner_group.reader;' from dual;

select 'grant execute on HEXTODEC to &owner_group.reader;' from dual;

select 'grant execute on BIGR_DATA_UTILS to &owner_group.util;' from dual;

select 'grant execute on FIND_CONCEPT_EXCEPT_CIR to &owner_group.reader;' from dual;

spool off

-------------------------------END_ARDAIS_OWNER-------------------------------

-------------------------------ADS_USER---------------------------------------

connect &vads.ads_user/&ads_password@&con_db

spool &vads.ads_user_revoke.sql

select 'revoke ' || privilege || ' on ' || table_name || ' from ' || grantee || ';'
from user_tab_privs_made
where table_name not like '%$%';

spool off

spool &vads.ads_user_grants.sql

select 'grant select on ads_imaget to &owner_group.owner;' from dual;

select 'grant select on ads_imaget to &owner_group.user;' from dual;

select 'grant select on ads_imaget to &owner_group.deriv;' from dual;

select 'grant select on ads_imaget to &owner_group.reader;' from dual;

select 'grant select on ads_imaget to &owner_group.util;' from dual;

spool off

-------------------------------END_ADS_USER-----------------------------------

----- Run all generated scripts now ------------------------------------------

host rm -f application_grants.log

-- First, do the ARDAIS_OWNER parts
--
connect &con_string@&con_db

host echo "" >> application_grants.log
host echo "**** `date`: owner_revoke started." >> application_grants.log
host echo "" >> application_grants.log
set verify on feedback on echo on
spool application_grants2.log
@&owner_group.owner_revoke.sql
spool off
set verify off feedback off echo off
host cat application_grants2.log >> application_grants.log
host rm -f application_grants2.log
host echo "" >> application_grants.log
host echo "**** `date`: owner_revoke done." >> application_grants.log
host echo "" >> application_grants.log


host echo "" >> application_grants.log
host echo "**** `date`: owner_grants started." >> application_grants.log
host echo "" >> application_grants.log
set verify on feedback on echo on
spool application_grants2.log
@&owner_group.owner_grants.sql
spool off
set verify off feedback off echo off
host cat application_grants2.log >> application_grants.log
host rm -f application_grants2.log
host echo "" >> application_grants.log
host echo "**** `date`: owner_grants done." >> application_grants.log
host echo "" >> application_grants.log

-- Next, do the ADS_USER parts
--
connect &vads.ads_user/&ads_password@&con_db

host echo "" >> application_grants.log
host echo "**** `date`: ads_user_revoke started." >> application_grants.log
host echo "" >> application_grants.log
set verify on feedback on echo on
spool application_grants2.log
@&vads.ads_user_revoke.sql
spool off
set verify off feedback off echo off
host cat application_grants2.log >> application_grants.log
host rm -f application_grants2.log
host echo "" >> application_grants.log
host echo "**** `date`: ads_user_revoke done." >> application_grants.log
host echo "" >> application_grants.log


host echo "" >> application_grants.log
host echo "**** `date`: ads_user_grants started." >> application_grants.log
host echo "" >> application_grants.log
set verify on feedback on echo on
spool application_grants2.log
@&vads.ads_user_grants.sql
spool off
set verify off feedback off echo off
host cat application_grants2.log >> application_grants.log
host rm -f application_grants2.log
host echo "" >> application_grants.log
host echo "**** `date`: ads_user_grants done." >> application_grants.log
host echo "" >> application_grants.log


host cat application_grants.log >> db_build_main.log
