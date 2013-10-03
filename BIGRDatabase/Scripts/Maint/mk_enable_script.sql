set showmode off echo off
set heading off pagesize 0 timing off feedback off
set termout off verify off
set linesize 130
SPOOL enable.sql
select 'alter table '||table_name||' enable constraint '||constraint_name||';'
from user_constraints
where TABLE_NAME not in(select TABLE_NAME from user_tables where IOT_TYPE is not null)
order by decode(constraint_type,'R',2,'U',1,'P',0)
/
SPOOL OFF
set termout on
select 'FINISHED' from dual
/
