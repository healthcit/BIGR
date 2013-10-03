------------------------------------------------------------------------------
--      File_name:  create_new_priv_syns.sql
--      Author: Dean Marsh
--      Date: 04/09/02
--
-- Modified: Phil DiPrima
-- Date: 01/15/09
--       Added a new schema to the script
--
--  Purpose:  Running this script will create necessary private synonyms
--    in all of the application user schemas.
--	Required permissions:
--	  The application schema OWNER must have the following specific
--    permissions:
--			create any synonym
--			drop any synonym
--
--  Required Parameters:
--    This script MUST be run with the following variables:
--      var1 = username and password of the OWNER schema
--      var2 = DB to connect to
--      var3 = the group of application users that is getting the
--             grants (e.g., for qa, it is "qa_", for patch, it is "patch_".)
--  Example:  @create_priv_syns.sql qa_owner/qa_password LEXDEV1 qa_
--
------------------------------------------------------------------------------

set verify off feedback off echo off linesize 300 trimspool on pagesize 0 headsep off

define var1 = &1
define var2 = &2
define var3 = &3

column which_ads noprint new_value vads

select decode(lower('&var3'),'ardais_','',lower('&var3')) which_ads
from dual;

connect &var1@&var2

spool &var3.drop_syns.sql

select 'drop synonym ' || owner || '.' || synonym_name || ';'
from all_synonyms
where (upper(table_owner) = upper('&var3.OWNER')
       OR upper(table_owner) = upper('&vads.ADS_USER'))
  and owner != 'PUBLIC';

spool off

spool &var3.create_syns.sql

-- We don't create synonyms for the BIGR_USE_ID_NAMESPACE procedure because
-- only GSBio should ever run it, and it could create major problems if it
-- was used incorrectly in a way that resulted in more than one installation
-- using the same namespace id.

select 'create synonym &var3.user.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','SEQUENCE','TABLE','VIEW')
  and object_name not like '%BTX%'
  and object_name <> 'BIGR_USE_ID_NAMESPACE'
  and object_name not like '%$%';

select 'create synonym &var3.deriv.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','SEQUENCE','TABLE','VIEW')
  and object_name not like '%BTX%'
  and object_name <> 'BIGR_USE_ID_NAMESPACE'
  and object_name not like '%$%';

select 'create synonym &var3.reader.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','SEQUENCE','TABLE','VIEW')
  and object_name not like '%BTX%'
  and object_name <> 'BIGR_USE_ID_NAMESPACE'
  and object_name not like '%$%';

select 'create synonym &var3.util.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','SEQUENCE','TABLE','VIEW')
  and object_name not like '%BTX%'
  and object_name <> 'BIGR_USE_ID_NAMESPACE'
  and object_name not like '%$%';

select 'create synonym &var3.views.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('VIEW')
  and object_name not like '%BTX%'
  and object_name <> 'BIGR_USE_ID_NAMESPACE'
  and object_name not in('LIMS_ADS_ILTDS_V','LIMS_BIGR_LIBRARY_V','RNA_HOLD_AMOUNT_V')
  and object_name like '%_V%';
  
select 'create synonym &vads.ads_user.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','TABLE','VIEW')
  and object_name not like '%BTX%'
  and object_name <> 'BIGR_USE_ID_NAMESPACE'
  and object_name not like '%$%';

-- For BTX objects, we don't include synonyms for sequences, since generally
-- access to BTX is through the BTX package.

select 'create synonym &var3.user.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','TABLE','VIEW')
  and object_name like '%BTX%'
  and object_name not like '%$%';

select 'create synonym &var3.deriv.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','TABLE','VIEW')
  and object_name like '%BTX%'
  and object_name not like '%$%';

select 'create synonym &var3.reader.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','TABLE','VIEW')
  and object_name like '%BTX%'
  and object_name not like '%$%';

select 'create synonym &var3.util.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','TABLE','VIEW')
  and object_name like '%BTX%'
  and object_name not like '%$%';

select 'create synonym &vads.ads_user.' || object_name || ' for ' || object_name || ';'
from user_objects
where object_type in ('FUNCTION','PACKAGE','PROCEDURE','TABLE','VIEW')
  and object_name like '%BTX%'
  and object_name not like '%$%';

select 'create synonym &var3.user.ads_imaget_syn for &vads.ads_user.ads_imaget;' from dual;

select 'create synonym &var3.deriv.ads_imaget_syn for &vads.ads_user.ads_imaget;' from dual;

select 'create synonym &var3.reader.ads_imaget_syn for &vads.ads_user.ads_imaget;' from dual;

select 'create synonym &var3.owner.ads_imaget_syn for &vads.ads_user.ads_imaget;' from dual;

select 'create synonym &var3.util.ads_imaget_syn for &vads.ads_user.ads_imaget;' from dual;

select 'create synonym &vads.ads_user.lims_ads_v for lims_ads_iltds_v;' from dual;

spool off

----- Run all generated scripts now ------------------------------------------

host rm -f create_new_priv_syns.log


host echo "" >> create_new_priv_syns.log
host echo "**** `date`: drop_syns started." >> create_new_priv_syns.log
host echo "" >> create_new_priv_syns.log
set verify on feedback on echo on
spool create_new_priv_syns2.log
@&var3.drop_syns.sql
spool off
set verify off feedback off echo off
host cat create_new_priv_syns2.log >> create_new_priv_syns.log
host rm -f create_new_priv_syns2.log
host echo "" >> create_new_priv_syns.log
host echo "**** `date`: drop_syns done." >> create_new_priv_syns.log
host echo "" >> create_new_priv_syns.log


host echo "" >> create_new_priv_syns.log
host echo "**** `date`: create_syns started." >> create_new_priv_syns.log
host echo "" >> create_new_priv_syns.log
set verify on feedback on echo on
spool create_new_priv_syns2.log
@&var3.create_syns.sql
spool off
set verify off feedback off echo off
host cat create_new_priv_syns2.log >> create_new_priv_syns.log
host rm -f create_new_priv_syns2.log
host echo "" >> create_new_priv_syns.log
host echo "**** `date`: create_syns done." >> create_new_priv_syns.log
host echo "" >> create_new_priv_syns.log


host cat create_new_priv_syns.log >> db_build_main.log
