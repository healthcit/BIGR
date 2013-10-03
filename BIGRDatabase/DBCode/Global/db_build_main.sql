------------------------------------------------------------------------------
--	File:	db_build_main.sql
--	Author:	Dean Marsh
--	Date:	04/09/02
--
--	Purpose:  This script is the main driver to do an automated build of all
--    database code and soft objects for the ardais BIGR application.
--
--	Parameters:
--    con_string = the username and password of the OWNER schema for the
--        application in the form <username>/<password>.
--    con_db = the database that the OWNER schema exists in.
--	  rel_type = what type of build this is.  Valid values are:
--		    <full> for a full release build
--        <inc> for an incremental (e.g., integration to qa)
--        <none> to not use a schema upgrade file (e.g., development to
--               integration)
--    ads_password = the password of the ADS schema associated with the
--        specified OWNER schema
--
--	Example:   @db_build_main.sql qa_owner/qa_password LEXQA1 NONE ads_password
--
------------------------------------------------------------------------------

prompt 'Output is being logged to db_build_main.log.'

host rm -f db_build_main.log
host echo "" >> db_build_main.log
host echo "**** `date`: db_build_main started." >> db_build_main.log
host echo "" >> db_build_main.log

spool db_build_main.log

set termout off verify off feedback off echo off linesize 300 trimspool on pagesize 0 headsep off

define con_string=&1
define con_db=&2
define rel_type=&3
define ads_password=&4

column target_owner_group noprint new_value owner_group
column build_type noprint new_value which_rel_script
column target_ads_group noprint new_value ads_con_string

select lower(substr('&con_string',1,instr('&con_string','_'))) target_owner_group
from dual;

select decode(upper(substr('&con_string',1,instr('&con_string','_')-1)),
		'ARDAIS','',
		lower(substr('&con_string',1,instr('&con_string','_')))) || 'ads_user/&ads_password' as target_ads_group
from dual;

select decode(upper('&rel_type'),
  'FULL', 'full_db_release_upgrade.sql',
	'INC', 'inc_db_release_upgrade.sql',
	'NONE', 'no_db_release_upgrade.sql') build_type
from dual;

@&which_rel_script &con_string@&con_db

@drop_db_code.sql &con_string &con_db &ads_con_string

connect &con_string@&con_db

host rm -f compile_db_code.log
host echo "" >> compile_db_code.log
host echo "**** `date`: compile_db_code started." >> compile_db_code.log
host echo "" >> compile_db_code.log
set verify on feedback on echo on
spool compile_db_code2.log
@compile_db_code.sql &con_string@&con_db
BEGIN
  DBMS_UTILITY.COMPILE_SCHEMA (USER);
END;
/
spool off
set verify off feedback off echo off
host cat compile_db_code2.log >> compile_db_code.log
host rm -f compile_db_code2.log
host echo "" >> compile_db_code.log
host echo "**** `date`: compile_db_code done." >> compile_db_code.log
host echo "" >> compile_db_code.log
host cat compile_db_code.log >> db_build_main.log

connect &ads_con_string@&con_db

host rm -f compile_ads_db_code.log
host echo "" >> compile_ads_db_code.log
host echo "**** `date`: compile_ads_db_code started." >> compile_ads_db_code.log
host echo "" >> compile_ads_db_code.log
set verify on feedback on echo on
spool compile_ads_db_code2.log
@compile_ads_db_code.sql &ads_con_string@&con_db
BEGIN
  DBMS_UTILITY.COMPILE_SCHEMA (USER);
END;
/
spool off
set verify off feedback off echo off
host cat compile_ads_db_code2.log >> compile_ads_db_code.log
host rm -f compile_ads_db_code2.log
host echo "" >> compile_ads_db_code.log
host echo "**** `date`: compile_ads_db_code done." >> compile_ads_db_code.log
host echo "" >> compile_ads_db_code.log
host cat compile_ads_db_code.log >> db_build_main.log

connect &con_string@&con_db

@Application_grants.sql &con_string &con_db &owner_group &ads_password

connect &con_string@&con_db

@create_new_priv_syns.sql &con_string &con_db &owner_group

host echo "" >> db_build_main.log
host echo "**** `date`: db_build_main done." >> db_build_main.log
host echo "" >> db_build_main.log

exit
