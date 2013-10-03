set termout off feedback off heading off verify off echo off pagesize 0 linesize 200
spool mk_rep_sup_genprod.sql
select 'PROMPT generate_replication_support for '||table_name||chr(10)||'exec dbms_repcat.generate_replication_support('||'''ARDAIS_OWNER'''||','||''''||table_name||''''||','||'''TABLE'''||');' from user_tables where table_name not like 'RNA%' and table_name not like 'TMA%' and table_name not like '%CIR_%' and table_name not like '%$%'
/
spool off
set termout on feedback on heading on
spool mk_rep_sup_genprod.log
connect REPADMIN_G/REPADMIN
@mk_rep_sup_genprod.sql
spool off
