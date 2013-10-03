#!/usr/bin/ksh

###################################################################################################################
#       File:           run_ctxjobs.sh
#       Author:         Sattanathan Ponnuswamy 
#       Date:           10/15/2004
#
#       Purpose:        The purpose of this file is to automate the 
#                       database. This file is generally scheduled to run from a cron, but can be run at the command
#                       line.
#
#       Parameter:      One parameter, the SID of the database the logs belongs to, is required.
#
#       Example:        ./run_ctxjobs.sh GENPROD1
#
#       Comments:
#
####################################################################################################################

#  Check to see if SID argument was supplied.

if [ $# -ne 1 ]
then
   echo "Usage:  run_ctxjobs.sh <SID>"
   echo "   <SID> = SID for the ."
   exit 1
fi

export ORACLE_HOME=/u01/app/oracle/product/9.2.0

ORACLE_SID=$1
export ORACLE_SID

export LOG_DIR=/u01/app/oracle/scripts/log

$ORACLE_HOME/bin/sqlplus /nolog << EOF > ${LOG_DIR}/satt_ctx_${ORACLE_SID}_`date '+%m%d%y'`.msglog
connect ctxsys/ctxsys@$ORACLE_SID
select to_char(sysdate,'MM/DD/YYYY HH24:MI:ss') from dual;
exec ctxsys.ctx_ddl.sync_index('ARDAIS_OWNER.LIMS_PATH_EVALUATION_IN6');
exec ctxsys.ctx_ddl.optimize_index('ARDAIS_OWNER.LIMS_PATH_EVALUATION_IN6', 'FAST');
exec ctxsys.ctx_ddl.sync_index('ARDAIS_OWNER.PDC_PATHOLOGY_REPORT_IN2');
exec ctxsys.ctx_ddl.optimize_index('ARDAIS_OWNER.PDC_PATHOLOGY_REPORT_IN2', 'FAST');
exec ctxsys.ctx_ddl.sync_index('ARDAIS_OWNER.PDC_PATHOLOGY_REPORT_IN4');
exec ctxsys.ctx_ddl.optimize_index('ARDAIS_OWNER.PDC_PATHOLOGY_REPORT_IN4', 'FAST');
exec ctxsys.ctx_ddl.sync_index('ARDAIS_OWNER.PDC_PATH_REP_SECTION_IN3');
exec ctxsys.ctx_ddl.optimize_index('ARDAIS_OWNER.PDC_PATH_REP_SECTION_IN3', 'FAST');
exec ctxsys.ctx_ddl.sync_index('ARDAIS_OWNER.PDC_ARDAIS_DONOR_IN1');
exec ctxsys.ctx_ddl.optimize_index('ARDAIS_OWNER.PDC_ARDAIS_DONOR_IN1', 'FAST');
commit;
EOF
