WHENEVER SQLERROR EXIT SQL.SQLCODE ROLLBACK
WHENEVER OSERROR EXIT FAILURE ROLLBACK

prompt 'Output is being logged to populate_tables.log.'

set termout on verify off feedback off echo off linesize 300 trimspool on pagesize 0 headsep off

DECLARE

NUM_RETURNED INTEGER;
ALREADY_RUN  EXCEPTION;

BEGIN

  -- Do not run the script if it seems to have already been run.
  -- The check is to see if there are any records in ARD_OBJECTS.
  -- Tf there are any, then we assume the db has been initialized;
  -- if not, then we can proceed.

  select count(*) INTO NUM_RETURNED from ARD_OBJECTS;

  if NUM_RETURNED > 0 then
    raise ALREADY_RUN;
  end if;

EXCEPTION
   when ALREADY_RUN then
     dbms_output.put_line ('There are records in ARD_OBJECTS, therefore assuming that this script has already been run.  Exiting program.');
     rollback;
     raise_application_error(-20004, 'Calling populate_tables, already records in ARD_OBJECTS');
     
   when others then
     dbms_output.put_line ('An error occured: ' || sqlerrm);
     rollback;
	  raise_application_error(-20004, 'Error while calling populate_tables: sqlerrm ' || sqlerrm );

END;
/

set termout off verify on feedback on echo on
spool populate_tables.log

define bootstrap_userid='&1'
define bootstrap_account='&2'
define location_string='&3'
define brand_string='&4'
define company_string='&5'

prompt 'Populating ARD_OBJECTS...'

@@standard_populate_ard_objects.sql

prompt 'Populating PDC_DX_TC_HIERARCHY...'

@@populate_pdc_dx_tc_hierarchy.sql

prompt 'Populating PDC_LOOKUP...'

@@populate_pdc_lookup.sql

prompt 'Populating ARD_LOOKUP_ALL...'

@@populate_ard_lookup_all.sql

prompt 'Calling BIGR_INITIALIZE_CLEAN_DATABASE...'

set define on

BEGIN
  BIGR_INITIALIZE_CLEAN_DATABASE('&bootstrap_userid', '&bootstrap_account', '&location_string', '&brand_string', '&company_string');
END;
/

commit;

prompt 'populate_tables completed successfully.'

spool off

exit
