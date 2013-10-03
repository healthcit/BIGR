------------------------------------------------------------------------------
--	File:		drop_db_code.sql
--	Author:		Dean Marsh
--	Date:		03/25/2002
--	
--	Comments:	This file is used as part of the build process to drop
--			all database code in a release.
--
-- Required Parameters:
--   This script MUST be run with the following parameters:
--     con_string = the username and password of the OWNER schema for the
--         application in the form <username>/<password>.
--     con_db = the database that the OWNER schema exists in.
--     ads_con_string = the username and password of the ADS schema associated with
--         the OWNER schema in con_string, in the form <username>/<password>.
--
--  Example:  @drop_db_code.sql qa_owner/qa_password LEXDEV1 qa_ads_user/qa_ads_password
--
------------------------------------------------------------------------------

define con_string=&1
define con_db=&2
define ads_con_string=&3

set verify off feedback off echo off linesize 300 trimspool on pagesize 0 headsep off

connect &con_string@&con_db

spool drop_code.sql

select 'drop ' || object_type || ' ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','VIEW','TRIGGER')
  and object_name not like '%$%';

spool off

connect &ads_con_string@&con_db

spool drop_ads_code.sql

select 'drop ' || object_type || ' ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','VIEW','TRIGGER')
  and object_name not like '%$%';

spool off

-- Now run the generated scripts.

connect &con_string@&con_db

host rm -f drop_code.log
host echo "" >> drop_code.log
host echo "**** `date`: drop_code started." >> drop_code.log
host echo "" >> drop_code.log
set verify on feedback on echo on
spool drop_code2.log
@drop_code.sql
spool off
set verify off feedback off echo off
host cat drop_code2.log >> drop_code.log
host rm -f drop_code2.log
host echo "" >> drop_code.log
host echo "**** `date`: drop_code done." >> drop_code.log
host echo "" >> drop_code.log

connect &ads_con_string@&con_db

host echo "" >> drop_code.log
host echo "**** `date`: drop_ads_code started." >> drop_code.log
host echo "" >> drop_code.log
set verify on feedback on echo on
spool drop_ads_code2.log
@drop_ads_code.sql
spool off
set verify off feedback off echo off
host cat drop_ads_code2.log >> drop_code.log
host rm -f drop_ads_code2.log
host echo "" >> drop_code.log
host echo "**** `date`: drop_ads_code done." >> drop_code.log
host echo "" >> drop_code.log

host cat drop_code.log >> db_build_main.log

