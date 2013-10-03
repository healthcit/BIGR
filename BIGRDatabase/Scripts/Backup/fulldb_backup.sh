#!/usr/bin/ksh

###################################################################################################################
#	File:		fulldb_backup.sh
#	Author:		Dean Marsh
#	Date:		05/30/2002
#
#	Purpose:	The purpose of this file is to automate the full backups of a database using the Oracle
#			rman functionality.  This file is generally scheduled to run from a cron, but can be run
#			at the command line.
#
#	Parameter:	One parameter, the SID of the database to back up, is required.
#
#	Example:	./fulldb_backup.sh LEXPROD1
#
#	Comments:
#
####################################################################################################################

#  Check to see if SID argument was supplied.

if [ $# -ne 1 ]
then
   echo "Usage:  fulldb_backup.sh <SID>"
   echo "   <SID> = SID for the database being backed up."
   exit 1
fi

export ORACLE_HOME=/u01/app/oracle/product/9.2.0

ORACLE_SID=$1
export ORACLE_SID

export LOG_DIR=/u01/app/oracle/scripts/log

$ORACLE_HOME/bin/rman << EOF > ${LOG_DIR}/fulldb_${ORACLE_SID}_`date '+%m%d%y'`.msglog
connect catalog rman/rman@rcvcat
connect target rman/siadra1@$ORACLE_SID
run {execute script fulldb_$ORACLE_SID;}
EOF

