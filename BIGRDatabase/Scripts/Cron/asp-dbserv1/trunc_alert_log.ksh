#!/bin/ksh
# ******************************************************************
#
#     File: trunc_alert_log.ksh
#
#  Purpose: This script is used to truncate the Oracle alert log
#           file.  
#
#    Notes: Reads oratab to determine how many databases and names
#           copies log, truncs log and then zips copy
#           does some basic error checking.
#
#   Limits:
#
# Modified:
#
#    Date    Who            Change/reason
#  --------  -----------    --------------------------
#  04/13/04  Phil DiPrima   Original for GulfStream
#
# ******************************************************************
#
# Set Debug mode if needed
# DEBUG=1 # ON
DEBUG=0 # OFF
if [ $DEBUG -ne 0 ]; then set -x; fi

####################################
# Set up section
####################################
# Oracle env variables
. /home/oracle/.oracle.profile

# Directories
DIR=/tmp

# Files
OTAB=/etc/oratab
ERRLOG=$DIR/err.log

# constants
MAILTO="pdiprima@healthcit.com"
NODE=`hostname`
SUBJB="Truncate Alert log ERROR"
SUBJG="Truncate Alert log SUCCESS"
TIMESTAMP=`date +%m%d%y`

# Programs
ZIP=/bin/gzip
EMAIL="/bin/mailx -s "

####### End of user Set up section #########

touch $ERRLOG
ERRNUM=0

##################### Main ##################################
#
# Set up Functions
#

cleanup() {    # Used to clean up temp files
 rm $ERRLOG
}

# Timing Function
#
# Total_Time : Print interval time in HH:MM:SS format
# Arg1 = End time in seconds, default $SECONDS
# Arg2 = Start time in seconds, default 0 (start of script)
#
Total_Time() {
etime=${1:-SECONDS}
stime=${2:-0}
ss=`expr $etime - $stime`
mm=`expr $ss / 60`
ss=`expr $ss % 60`
hh=`expr $mm / 60`
mm=`expr $mm % 60`
TIME="Elapsed Time(H:M:S) $hh:$mm:$ss"
} 

#############

#
# Cycle through each database on the system.
#

# Get Start time
start_time=$SECONDS

for THEDB in $(cat $OTAB|grep -v '#'|awk '{ FS = ":" } ; {print $1}');do
   THEFILE=$ORACLE_BASE/admin/$THEDB/bdump/alert_$THEDB.log
      # for debugging print "Alert log is: $THEFILE"
   if [ -a $THEFILE ]; then
 
         ERRNUM=0;

          #
          # Copy log to a save file and zip the saved file.
          #
         if [ -a $THEFILE.$TIMESTAMP ] || [ -a $THEFILE.$TIMESTAMP.gz ]; then
            echo "$THEFILE.$TIMESTAMP(.gz) already exisits and was NOT overwritten.\n" >>$ERRLOG
            ERRNUM=1
         else
            cp --reply=no $THEFILE $THEFILE.$TIMESTAMP 2>>$ERRLOG || {
                 ERRNUM=1
                 }
         fi

          #
          # Truncate the log file
          #
         if [ $ERRNUM -gt 0 ]; then
            echo "Log was NOT truncated.\n" >>$ERRLOG
         else
            :>$THEFILE 2>>$ERRLOG || {
               ERRNUM=1
               }
             # Zip the saved log file
         $ZIP $THEFILE.$TIMESTAMP 2>&1
         echo $THEFILE.$TIMESTAMP >>$ERRLOG
         fi

   else
         ERRNUM=1;
         echo "Alert log $THEFILE does not exist\n" >>$ERRLOG
   fi
done

# Get End time
end_time=$SECONDS
# Calculate the total time
Total_Time $end_time $start_time

     # Were there errors? Then email somebody and cleanup files
     if [ $ERRNUM -gt 0 ]; then
         echo $TIME >>$ERRLOG
         cat $ERRLOG|$EMAIL "$SUBJB (For $THEDB on $NODE)" $MAILTO
         cleanup
     else
         # Build a "success" email and mail it to somebody 
         echo $TIME >>$ERRLOG
         cat $ERRLOG|$EMAIL "$SUBJG (on $NODE)" $MAILTO
         cleanup
     fi

exit 0
