REM
REM hcit_purge_audit_trail.sql
REM
REM Purpose: Create a job to purge the aud$ table and keep only 8 days of info
REM
REM NOTE: Must GRANT EXECUTE ON dbms_system TO
REM       CREATE SYNONYM XXX.dbms_system FOR dbms_system;
REM       grant select, insert, update, delete on sys.aud$ to XXX;
REM       (because procedures don't respect roles)
REM
REM

REM Create the procedure 
CREATE OR REPLACE procedure hcit_purge_audit_trail (days in number) as
  purge_date date;
begin
  purge_date := trunc(sysdate-days);
  dbms_system.ksdwrt(2,'HCIT: Purging Audit Trail until ' || purge_date || ' started');
  delete from aud$ where ntimestamp# < purge_date;
  commit;
  dbms_system.ksdwrt(2,'HCIT: Purging Audit Trail until ' || purge_date || ' has completed');
end;
/


REM Schedule the job

BEGIN
  SYS.DBMS_SCHEDULER.DROP_JOB
    (job_name  => 'HCIT_AUDIT_PURGE');
END;
/

BEGIN
  SYS.DBMS_SCHEDULER.CREATE_JOB
    (
       job_name        => 'HCIT_AUDIT_PURGE'
      ,schedule_name   => 'SYS.MAINTENANCE_WINDOW_GROUP'
      ,job_class       => 'DEFAULT_JOB_CLASS'
      ,job_type        => 'PLSQL_BLOCK'
      ,job_action      => 'begin hcit_purge_audit_trail(8); end;'
      ,comments        => 'HCIT Audit Trail Purge'
    );
  SYS.DBMS_SCHEDULER.SET_ATTRIBUTE
    ( name      => 'HCIT_AUDIT_PURGE'
     ,attribute => 'RESTARTABLE'
     ,value     => FALSE);
  SYS.DBMS_SCHEDULER.SET_ATTRIBUTE
    ( name      => 'HCIT_AUDIT_PURGE'
     ,attribute => 'LOGGING_LEVEL'
     ,value     => SYS.DBMS_SCHEDULER.LOGGING_RUNS);
  SYS.DBMS_SCHEDULER.SET_ATTRIBUTE_NULL
    ( name      => 'HCIT_AUDIT_PURGE'
     ,attribute => 'MAX_FAILURES');
  SYS.DBMS_SCHEDULER.SET_ATTRIBUTE_NULL
    ( name      => 'HCIT_AUDIT_PURGE'
     ,attribute => 'MAX_RUNS');
  BEGIN
    SYS.DBMS_SCHEDULER.SET_ATTRIBUTE
      ( name      => 'HCIT_AUDIT_PURGE'
       ,attribute => 'STOP_ON_WINDOW_CLOSE'
       ,value     => FALSE);
  EXCEPTION
    -- could fail if program is of type EXECUTABLE...
    WHEN OTHERS THEN
      NULL;
  END;
  SYS.DBMS_SCHEDULER.SET_ATTRIBUTE
    ( name      => 'HCIT_AUDIT_PURGE'
     ,attribute => 'JOB_PRIORITY'
     ,value     => 3);
  SYS.DBMS_SCHEDULER.SET_ATTRIBUTE_NULL
    ( name      => 'HCIT_AUDIT_PURGE'
     ,attribute => 'SCHEDULE_LIMIT');
  SYS.DBMS_SCHEDULER.SET_ATTRIBUTE
    ( name      => 'HCIT_AUDIT_PURGE'
     ,attribute => 'AUTO_DROP'
     ,value     => FALSE);

  SYS.DBMS_SCHEDULER.ENABLE
    (name                  => 'HCIT_AUDIT_PURGE');
END;
/
