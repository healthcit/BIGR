-------------------------------------------------------------------------------------------------------
--      Filename:  switch_release.sql
--      Author:  Dean Marsh
--      Date:  03/20/02
--
--      Comments:  Use this script in the development environment to switch from one
--      release to another.  Just log into the database as the user you want to change and
--      execute the script from the command line.
--      Answer the questions posed and new private synonyms will be created as required.  The use of
--	thes private synonyms along with the explicit granting of access to schema objects will allow
--	the developer to have all access to objects in the common schema without having DDL access to
--	that schema.
-------------------------------------------------------------------------------------------------------

accept newuser prompt 'Please enter the release to point to: '
accept myuser prompt 'Please enter your username: '
accept mypwd prompt 'Please enter your password: '
define newuser = &newuser._OWNER

set pages 1000 head off termout off lines 200 verify off echo off trimspool on feedback off

set termout on
connect &myuser/&mypwd@RHLEXDEV
prompt
prompt 'Dropping current private synonyms.  Please wait...'
prompt
set termout off

connect &myuser/&mypwd@RHLEXDEV
spool drop_private_syns.sql
select 'drop synonym ' || synonym_name || ';' from user_synonyms
where table_owner != upper('&myuser');
spool off
set termout on
@./drop_private_syns.sql

prompt
prompt 'Creating new private synonyms.  Please wait...'
prompt
set termout off

connect &myuser/&mypwd@RHLEXDEV
spool create_private_syns.sql
select 'create synonym ' || table_name || ' for &newuser' || '.' || table_name || ';'
from all_tables where owner = upper('&newuser')
and table_name not like '%SQLN%'
and table_name not like '%SQLAB%'
and table_name not like '%PLSQL_%'
or (owner = upper('&newuser'));

select 'create synonym ' || view_name || ' for &newuser' || '.' || view_name || ';'
from all_views 
where owner = upper('&newuser');

select 'create synonym ' || sequence_name || ' for &newuser' || '.' || sequence_name || ';'
from all_sequences
where sequence_owner = upper('&newuser');

select 'create synonym ' || object_name || ' for &newuser' || '.' || object_name || ';'
from all_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE')
and owner = upper('&newuser');

select 'create synonym ads_imaget_syn for ' || substr('&newuser',1,instr('&newuser','_')) || 'ads_user.ads_imaget;'
from dual;

spool off
set termout on
@./create_private_syns.sql

prompt
prompt 'You have successfully switched your access to schema &newuser'
prompt

set pages 14 head on termout on lines 80 verify on echo on trimspool off feedback on
