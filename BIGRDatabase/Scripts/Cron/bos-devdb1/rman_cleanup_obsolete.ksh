#!/bin/ksh
#============================================================================
# File:		rman_cleanup_obsolete.ksh
# Type:		UNIX korn-shell script
# Author:	Phil DiPrima
# Date:		11/10/06
#
# Description:
#	Clean up obsolete RMAN backups to save disk space 
#
# Syntax:
#	rman_cleanup_obsolete.ksh <ORACLE_SID>
# restore database validate;
#============================================================================
# Configuration
# Who do I email
export RCPT=dba@healthcit.com
#============================================================================
function HELPMSG {
  echo " "
  echo "Usage: rman_cleanup_obsolete.ksh <ORACLE_SID> "
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
# Configure RMAN specifically to clean up
rman target / nocatalog log $HOME/scripts/rman/logs/cleanup_${ORACLE_SID}_obsolete_${TIMESTAMP}.log << EOF
# Run the LIST output to obtain primary keys of backups and copies.
LIST BACKUP OF DATABASE ARCHIVELOG ALL; # lists backups of db files and logs
LIST COPY;

# Configure RMAN
ALLOCATE CHANNEL FOR MAINTENANCE DEVICE TYPE DISK;
CONFIGURE CHANNEL device type 'SBT_TAPE' clear;
run
{
# CrossCheck before deleteing
crosscheck copy;
crosscheck backup of database;
crosscheck backup of controlfile;
crosscheck archivelog all;
report obsolete;
# Deleting Backups and Copies Rendered Obsolete by the Retention Policy
delete noprompt obsolete redundancy 3;
}
# Get an uptodate listing
list backup of archivelog all completed after 'sysdate -1';

EXIT;
EOF

# Mail the log
cat $HOME/scripts/rman/logs/cleanup_${ORACLE_SID}_obsolete_${TIMESTAMP}.log|/bin/mailx -s "$ORACLE_SID - rman_cleanup_obsolete.ksh" $RCPT
