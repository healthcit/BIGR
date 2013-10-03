#!/usr/bin/ksh

###################################################################################################################
#       File:           resync_db.sh
#       Author:         Dean Marsh
#       Date:           06/03/2002
#
#       Purpose:        The purpose of this file is to automate the resync of the catalog database to the target
#			database. This file is generally scheduled to run from a cron, but can be run at the command
#			line.
#
#       Parameter:      One parameter, the SID of the database the logs belongs to, is required.
#
#       Example:        ./resync_db.sh LEXPROD1
#
#       Comments:
#
####################################################################################################################

#  Check to see if SID argument was supplied.

if [ $# -ne 1 ]
then
   echo "Usage:  resync_db.sh <SID>"
   echo "   <SID> = SID for the target db to resync to."
   exit 1
fi

export ORACLE_HOME=/u01/app/oracle/product/9.2.0

ORACLE_SID=$1
export ORACLE_SID

export LOG_DIR=/u01/app/oracle/scripts/log

$ORACLE_HOME/bin/rman << EOF > ${LOG_DIR}/resync_${ORACLE_SID}_`date '+%m%d%y'`.msglog
connect catalog rman/rman@rcvcat
connect target rman/siadra1@$ORACLE_SID
run 
{
resync catalog;
}
EOF

