#!/bin/ksh
#============================================================================
# File:		full_db_bkup.ksh
# Type:		UNIX korn-shell script
# Author:	Phil DiPrima
# Date:		01/10/07
#
# Description:
#	Do a full database backup with RMAN 
#       * configure needed params
#       * backup and create a TAG with the date
#       * check logical for logical DB corruption
#       * filesperset 1 so each datafile is its own backup piece
#       * archivelog backup plus delete obsolete logs
#       * crosscheck to make sure everything is up-to-date
#       * backup control file so it knows about this backup
#
# Syntax:
#	full_db_bkup.ksh <ORACLE_SID>
# restore database validate;
#============================================================================
# Configuration
# Who do I email, etc
export RCPT=dba@healthcit.com
export MAILER=/bin/mailx
export DIR=/home/oracle/scripts/rman/logs
export ADMINDIR=/u01/app/oracle/admin/ASPDB2/backups
#============================================================================
function HELPMSG {
  echo " "
  echo "Usage: full_db_bkup.ksh <ORACLE_SID> "
  echo " "
  echo "Where:            SID - Oracle SID to backup"
  echo " "
  echo " "
  #
  return 0
}
#
# Check command line arguments
#
if [ $# -ne 1 ]
 then HELPMSG
 exit 1
fi

# Oracle Env
. /home/oracle/.oracle.profile

# Process command line arguments
#
# Set SID to first argument
THESID=${1}
export ORACLE_SID=$THESID

# Create a timestamp for the TAG name
export TIMESTAMP=`date +"%m%d%y"`

# Simplify the file name
export THEFILE=$HOME/scripts/rman/logs/full_${ORACLE_SID}_bkup_${TIMESTAMP}.log


# This is the point in the redo stream
#  where the rman backup commences.
sqlplus / as sysdba <<- EOF
alter system switch logfile;
exit
EOF

# Connect to a target database without using a recovery catalog
# Configure RMAN specifically to use Flash Recovery Area features
rman target / catalog rman/rman1@rman log $THEFILE << EOF
# Another log file switch, unlike the first, done inside rman.
sql 'alter system archive log current';
# Checks for existence of archived redo logs in the instance-specified location. 
#  They are marked AVAILABLE or EXPIRED depending on the success or failure of the
#  check on disk for their existence.
change archivelog all crosscheck;
# Allocation of disk channel for the ensuing maintenance activity.
allocate channel for maintenance type disk;
# Catalog records of archived redo logs found to be expired are wiped from the rman repository.
delete noprompt expired archivelog all;
# Configure RMAN specifically to use Flash Recovery Area features
CONFIGURE RETENTION POLICY TO REDUNDANCY 3; #max of 3 backup/copies of data/control file retained. 
CONFIGURE BACKUP OPTIMIZATION ON;           # Backup all transient files in db_file_recovery_dest
CONFIGURE CONTROLFILE AUTOBACKUP ON;        # controlfile and SPFILE only enabled
CONFIGURE CHANNEL device type 'SBT_TAPE' clear;
run
{
# check for logical corruption within a block as well as the normal head/tail checksumming while backing up
backup TAG = 'full_${ORACLE_SID}_${TIMESTAMP}' check logical database filesperset 1 plus archivelog delete input; 
crosscheck backup;
# Multiple backups of the controlfile 
BACKUP CURRENT CONTROLFILE TAG = 'ctlfile_${TIMESTAMP}';
copy current controlfile to '$ADMINDIR/full_${ORACLE_SID}_${TIMESTAMP}_control.bak';
backup current controlfile for standby format '$ADMINDIR/%d_STDBY_CF_%U.bak';
# Restore Validation confirms that a restore could be run, 
#  by confirming that all database files exist and are free of physical and logical corruption.
restore database validate;
# identify the backups required to complete a specific restore operation
restore database preview summary;
# test the most recent RMAN spfile backup
restore validate spfile;
# test the most recent RMAN controlfile backup
restore validate controlfile;
resync catalog;
}
# get an up-to-date listing
list backup of archivelog all completed after 'sysdate -1';

EXIT;
EOF

# Mail the log
# cat $HOME/scripts/rman/logs/full_${ORACLE_SID}_bkup_${TIMESTAMP}.log|/bin/mailx -s "$ORACLE_SID - full_db_bkup.ksh" $RCPT
if [[ $(grep -c "00569" $THEFILE) -gt 0 ]];then
 echo cat $THEFILE|$MAILER -s "ASPDB2 - ==> RMAN ERROR in $THEFILE" $RCPT
else
 echo $THEFILE is error free.|$MAILER -s "ASPDB2 - RMAN" $RCPT
fi
# End
