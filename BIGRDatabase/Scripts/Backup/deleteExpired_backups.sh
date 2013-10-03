#!/usr/bin/ksh

###################################################################################################################
#       File:           deleteExpired_backups.sh
#       Author:         Dean Marsh
#       Date:           06/03/2002
#
#       Purpose:        The purpose of this file is to automate the deletion process of backups in the catalog 
#			that have been deleted completely from the Ardais backup system.  This process will delete
#			all backups that have been marked as EXPIRED and are greater than 30 days old.  This is in
#			keeping with the bioethics policy that Ardais follows regarding the storage of patient
#			data in archive for longer than 30 days.
#
#       Parameter:      The SID to for the target database to which the backups being deleted belong to. 
#
#       Example:        ./deleteExpired_backups.sh LEXPROD1
#
#       Comments:
#
####################################################################################################################

#  Check to see if SID argument was supplied.

if [ $# -ne 1 ]
then
   echo "Usage:  resync_db.sh <SID>"
   echo "   <SID> = SID for which the expired backups are being deleted for."
   exit 1
fi

export ORACLE_HOME=/u01/app/oracle/product/9.2.0

ORACLE_SID=$1
export ORACLE_SID

export LOG_DIR=/u01/app/oracle/scripts/log

$ORACLE_HOME/bin/rman << EOF > ${LOG_DIR}/deleteExpired_${ORACLE_SID}_`date '+%m%d%y'`.msglog
connect catalog rman/rman@RCVCAT
connect target rman/siadra1@$ORACLE_SID
allocate channel for maintenance type disk;
delete noprompt expired backup completed before 'SYSDATE - 31';
delete noprompt expired backup of archivelog all completed before 'SYSDATE - 31';
delete noprompt expired backup of controlfile completed before 'SYSDATE - 31';
delete noprompt obsolete recovery window of 30 days;
release channel;
EOF

