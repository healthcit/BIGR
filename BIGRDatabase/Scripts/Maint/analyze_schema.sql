define v_user=&1
define v_sid=&2

set pages 0 head off echo off verify off feedback off lines 100

spool &v_sid._&v_user._analyze.sql

select 'analyze table &v_user' || '.' || table_name || ' compute statistics;'
from all_tables where table_name not like '%$%'
and owner = upper('&v_user');

select 'analyze index &v_user' || '.' || index_name || ' compute statistics;'
from all_indexes 
where owner = upper('&v_user');

spool off

@&v_sid._&v_user._analyze.sql

exit
