#!/usr/bin/ksh

###################################################################################################################
#       File:           catalog_crosscheck.sh
#       Author:         Dean Marsh
#       Date:           06/03/2002
#
#       Purpose:        The purpose of this file is to automate the crosscheck process of the catalog database to
#                       validate current backupsets that are still on disk.  Backup set that it does not find on 
#                       disk will be marked as EXPIRED.
#
#       Parameter:      The SID to crosscheck agains for backups is required.
#
#       Example:        ./crosscheck_catalog.sh LEXPROD1
#
#       Comments:
#
####################################################################################################################

#  Check to see if SID argument was supplied.

if [ $# -ne 1 ]
then
   echo "Usage:  resync_db.sh <SID>"
   echo "   <SID> = SID for the target database to crosscheck against."
   exit 1
fi

export ORACLE_HOME=/u01/app/oracle/product/9.2.0

ORACLE_SID=$1
export ORACLE_SID

export LOG_DIR=/u01/app/oracle/scripts/log

$ORACLE_HOME/bin/rman << EOF > ${LOG_DIR}/crosscheck_${ORACLE_SID}_`date '+%m%d%y'`.msglog
connect catalog rman/rman@RCVCAT
connect target rman/siadra1@$ORACLE_SID
allocate channel for maintenance type disk;
crosscheck backup;
crosscheck backup of archivelog all;
crosscheck backup of controlfile;
release channel;
EOF

