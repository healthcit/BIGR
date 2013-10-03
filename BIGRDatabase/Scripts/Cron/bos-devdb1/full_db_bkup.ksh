#!/bin/ksh
#============================================================================
# File:		full_db_bkup.ksh
# Type:		UNIX korn-shell script
# Author:	Phil DiPrima
# Date:		11/09/06
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
# Who do I email
export RCPT=dba@healthcit.com
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

# Connect to a target database without using a recovery catalog
# Configure RMAN specifically to use Flash Recovery Area features
rman target / nocatalog log $HOME/scripts/rman/logs/full_${ORACLE_SID}_${TIMESTAMP}.log << EOF
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
BACKUP CURRENT CONTROLFILE TAG = 'ctlfile_${TIMESTAMP}';
# Restore Validation confirms that a restore could be run, 
#  by confirming that all database files exist and are free of physical and logical corruption.
restore database validate;
# identify the backups required to complete a specific restore operation
restore database preview summary;
# test the most recent RMAN spfile backup
restore validate spfile;
# test the most recent RMAN controlfile backup
restore validate controlfile;
}
# get an uptodate listing
list backup of archivelog all completed after 'sysdate -1';

EXIT;
EOF

# Mail the log
cat $HOME/scripts/rman/logs/full_${ORACLE_SID}_${TIMESTAMP}.log|/bin/mailx -s "$ORACLE_SID - full_db_bkup.ksh" $RCPT
