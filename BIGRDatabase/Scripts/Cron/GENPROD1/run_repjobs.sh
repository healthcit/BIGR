#!/usr/bin/ksh

###################################################################################################################
#       File:           run_repjobs.sh
#       Author:         Sattanathan Ponnuswamy 
#       Date:           10/15/2004
#
#       Purpose:        The purpose of this file is to automate the 
#                       database. This file is generally scheduled to run from a cron, but can be run at the command
#                       line.
#
#       Parameter:      One parameter, the SID of the database the logs belongs to, is required.
#
#       Example:        ./run_repjobs.sh GENPROD1
#
#       Comments:
#
####################################################################################################################

#  Check to see if SID argument was supplied.

if [ $# -ne 1 ]
then
   echo "Usage:  run_repjobs.sh <SID>"
   echo "   <SID> = SID for the ."
   exit 1
fi

export ORACLE_HOME=/u01/app/oracle/product/9.2.0

ORACLE_SID=$1
export ORACLE_SID

export LOG_DIR=/u01/app/oracle/scripts/log

$ORACLE_HOME/bin/sqlplus /nolog << EOF > ${LOG_DIR}/satt_rep_${ORACLE_SID}_`date '+%m%d%y'`.msglog
connect repadmin_g/repadmin@$ORACLE_SID
select to_char(sysdate,'MM/DD/YYYY HH24:MI:ss') from dual;
declare rc binary_integer; begin rc := sys.dbms_defer_sys.purge( delay_seconds=>0); end;
/
exec dbms_repcat.do_deferred_repcat_admin('"GENUITY_ARDAIS_OWNER"', FALSE);
commit;
connect snapadmin_g/snapadmin@$ORACLE_SID 
declare rc binary_integer; begin rc := sys.dbms_defer_sys.push(destination=>'LEXPROD1', stop_on_error=>FALSE, delay_seconds=>0, parallelism=>0); end;
/
exec dbms_repcat.do_deferred_repcat_admin('"GENUITY_ADS_USER"', FALSE);
commit;
connect ardais_owner/17fB2piH@$ORACLE_SID
exec dbms_refresh.refresh('"ARDAIS_OWNER"."LEX_TO_GEN_ARDAIS_RFSH_GRP"');
commit;
EOF
