#!/bin/ksh
#
#     file: do_full_dpexp.ksh
#  purpose: Oracle Data Pump is a new feature of Oracle Database 10g 
#         : that provides high speed, parallel, bulk data and metadata movement.
#         : It is being used here to take a full logical DB backup,
#         : this is in addition to the nightly RMAN backups
#       by: Phil DiPrima 11/07/06
# modified: 04/29/08
#         :  Added FLASHBACK_SCN to get consistent BU as of a specified point in time.
#         :   The export operation is performed with data that is consistent as of this SCN.
#    notes: Make sure that the working database has DATA_PUMP_DIR set
#         :  I have been using $ORACLE_BASE/admin/$THESID/dpdump by default.
#         : The flashback query mechanism relies on automatic undo management to maintain
#         :  the necessary undo data.
#         : If you want to use FLASHBACK_TIME (not as precise), use this... FLASHBACK_TIME=\"$EXPDT\"
#         :  where EXPDT= export EXPDT=`date +"%G-%m-%d %H:%M:%S"`
#
#
# Configure the script here
#
# Create a timestamp
export CURDT=`date +"%m%d%y"`
# Oracle env
. /home/oracle/.oracle.profile
# For emailing results
export RCPT=dba@healthcit.com

#########################################################################################
#
# HELPMSG - Display utility syntax
#
#########################################################################################
function HELPMSG {
  echo " "
  echo "Usage: do_full_dpexp.ksh  SID  <system_password> "
  echo " "
  echo "Where:            SID - Oracle SID to datapump export, defaults to $ORACLE_SID"
  echo " "
  echo "                  system_password "
  echo " "
  #
  return 0
}
#
# Check command line arguments
#
if [ $# -ne 2 ]
 then HELPMSG
 exit 1
fi

# Process command line arguments
#
# Set SID to first argument
THESID=${1}
export ORACLE_SID=$THESID
export DP_DIR=$ORACLE_BASE/admin/$THESID/dpdump
# Set system password to second argument
shift
SYSPWD="$*"
#
# Get the SCN for a consistent backup,
# SCN used to set session snapshot
#
SCN=`sqlplus -silent system/$SYSPWD <<END
set pagesize 0 feedback off verify off heading off echo off
SELECT dbms_flashback.get_system_change_number
  FROM DUAL
/
exit;
END`
if [ -z "$SCN" ]; then
  echo "Error: Can not retrieve SCN from database."|mailx -s "$THESID - export ERROR" $RCPT
  exit 0
fi
# Remove the whitepace
SCN=`echo $SCN | sed -e 's/^ *//' -e 's/ *$//'`

#################
# Do the export #
#################
expdp system/$SYSPWD FLASHBACK_SCN=$SCN full=y directory=DATA_PUMP_DIR dumpfile=dp_full_$CURDT.dmp LOGFILE=dp_full_$CURDT.log

# Check the log and email
tail -3 $DP_DIR/dp_full_$CURDT.log|mailx -s "$THESID - export (SCN:$SCN)" $RCPT
