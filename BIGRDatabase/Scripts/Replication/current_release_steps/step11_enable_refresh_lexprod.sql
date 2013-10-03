set linesize 200
spool step11_enable_refresh_lexprod.log
SELECT SUBSTR(job,1,4) "Job", SUBSTR(log_user,1,5) "User",
  SUBSTR(schema_user,1,5) "Schema",
  SUBSTR(TO_CHAR(last_date,'DD.MM.YYYY HH24:MI'),1,16) "Last Date",
  SUBSTR(TO_CHAR(next_date,'DD.MM.YYYY HH24:MI'),1,16) "Next Date",
  SUBSTR(broken,1,2) "B", SUBSTR(failures,1,6) "Failed",
  SUBSTR(what,1,50) "Command"
   FROM dba_jobs
   where schema_user not in ('CTXSYS')
   order by log_user
/

BEGIN
    DBMS_REFRESH.CHANGE(
     name => '"ARDAIS_OWNER"."GEN_TO_LEX_ARDAIS_RFSH_GRP"',
     next_date => SYSDATE,
     interval => '/*5:Mins*/ sysdate + 5/(60*24)',
     implicit_destroy => FALSE,
     rollback_seg => 'NULL',
     push_deferred_rpc => FALSE,
     refresh_after_errors => FALSE,
     purge_option => 0,
     parallelism => 0,
     heap_size => 0);
END;
/

commit
/

SELECT ID, REQUEST, STATUS, MASTER FROM DBA_REPCATLOG
/

SELECT SUBSTR(job,1,4) "Job", SUBSTR(log_user,1,5) "User",
  SUBSTR(schema_user,1,5) "Schema",
  SUBSTR(TO_CHAR(last_date,'DD.MM.YYYY HH24:MI'),1,16) "Last Date",
  SUBSTR(TO_CHAR(next_date,'DD.MM.YYYY HH24:MI'),1,16) "Next Date",
  SUBSTR(broken,1,2) "B", SUBSTR(failures,1,6) "Failed",
  SUBSTR(what,1,50) "Command"
   FROM dba_jobs
   where schema_user not in ('CTXSYS')
   order by log_user
/
spool off
