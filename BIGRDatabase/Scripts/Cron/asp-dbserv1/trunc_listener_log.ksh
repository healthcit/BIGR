#!/bin/ksh
# ******************************************************************
#
#     File: trunc_listener_log.ksh
#
#  Purpose: This script is used to truncate the Oracle listener log
#           file.  
#
#    Notes: 1)Determines how many listeners are running
#             2)Process each listener
#              3)build an exe string
#              4)pick out listener name
#              5)build a listener batch script to get info
#              6)run batch script
#              7)load variables
#              8)check for logging
#                9)stop logging
#                10)check dir exists
#                  11)mv log file
#                12)start logging
#                13)zip log file
#              14)email errors or success
#            15)email if no TNS or exit
#
#   Limits: Unable to trap errors when inside lsnrctl
#
# Modified:
#
#    Date    Who            Change/reason
#  --------  -----------    --------------------------
#  04/12/04  Phil DiPrima   Original for GulfStream
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
TEMPFILE=$DIR/deleteme1
CMDFILE=$DIR/deleteme2
OUTFILE=$DIR/deleteme3
ERRLOG=$DIR/err.log

# constants
MAILTO="pdiprima@healthcit.com"
NODE=`hostname`
SUBJB="Truncate Listener log ERROR"
SUBJG="Truncate Listener log SUCCESS"

# Programs
ZIP=/bin/gzip
EMAIL="/bin/mailx -s "

####### End of user Set up section #########

##################### Main ##################################
#
# Set up Functions
#

cleanup() {    # Used to clean up temp files
 rm $CMDFILE
 rm $OUTFILE
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

# Get Start time
start_time=$SECONDS

#
# First determine if listener is running.
#

ISTNSTHERE=`ps -ef | grep -i tns | grep -v grep | wc -l`

if [ $ISTNSTHERE -gt 0 ]; then
  #
  # Determine the listener processes and create a temp file
  #   the "-o args" option eliminates useless information
  #
 
  ps -eo args| grep -i tns | grep -v grep > $TEMPFILE

  #
  # Loop through the file and process each line (LISTENER) in turn
  #
  { while read myline;do

      ERRNUM=0

      ###Build a command string###
      BINDIR=`echo $myline | awk '{print $1}'` #1st part of string
      BINDIR=${BINDIR%%\/tnslsnr}              #remove slash and exe
      OHOME=${BINDIR%%\/bin}                   #remove bin
      LSNRCTL=$BINDIR/lsnrctl                  #add executable
      OLDDIR=$OHOME/network/log                #archive destination

      ###Get the name of the LISTENER###
      LISTENER_NAME=`echo $myline | awk '{print $2}'`

      # To set the "current_listener" variable, you must be in lsnrctl
      # That is why we will create a lsnrctl batch script per listener
      # and then get our needed information from there.

      ###Build a LISTENER batch script###
      echo set current_listener $LISTENER_NAME > $CMDFILE
      echo show log_file >> $CMDFILE
      echo show log_directory >> $CMDFILE
      echo show log_status >> $CMDFILE

      ###Set ORACLE_HOME and TNS_ADMIN first###
      ORACLE_HOME=$OHOME
      export ORACLE_HOME
      TNS_ADMIN=$OHOME/network/admin
      export TNS_ADMIN

      ###Run the program calling the batch commands###
      $LSNRCTL @$CMDFILE > $OUTFILE 2>>$ERRLOG || {
          echo ERROR RUNNING LSNRCTL COMMAND FILE $CMDFILE>>$ERRLOG
          ERRNUM=1
          }

     # Get listener log dir from lsnrctl
      LDIR=`cat $OUTFILE | grep log_directory|awk '{print $6}'`
   
     # Get listener log file name from lsnrctl
      LLFILE=`cat $OUTFILE | grep log_file|awk '{print $6}'` 

     # Get listener log status from lsnrctl
      LSTAT=`cat $OUTFILE | grep log_status | awk '{print $6}'`

     # Now do the work only if logging is turned on
     if [ "$LSTAT" = "ON" ]; then
         # Run the version specific program and
         # set the correct listener
         # stop logging
         $LSNRCTL << EOF
            set current_listener $LISTENER_NAME
            set log_status off
EOF

         # Check that we're moving to an existing
         # file system
         if test -d $OLDDIR ; then
            # Move the log file and add a timestamp
            TIMESTAMP=`date +%m%d%y`
            mv $LDIR/$LLFILE $OLDDIR/$LLFILE.$TIMESTAMP 2>>$ERRLOG || {
               ERRNUM=1
               }
         else
            echo "Directory $OLDDIR does not exist" >>$ERRLOG
            ERRNUM=1 
         fi

         # start logging back up
         $LSNRCTL << EOF
           set current_listener $LISTENER_NAME
           set log_status on
EOF

         # Zip the saved log file
         $ZIP $OLDDIR/$LLFILE.$TIMESTAMP 2>>$ERRLOG || {
               ERRNUM=1
              }
     
     fi

     # Get End time
     end_time=$SECONDS
     Total_Time $end_time $start_time

     # Were there errors? Then email somebody and cleanup files
     if [ $ERRNUM -gt 0 ]; then
         echo $TIME >>$ERRLOG
         cat $ERRLOG|$EMAIL "$SUBJB ($LISTENER_NAME on $NODE)" $MAILTO
         cleanup
     else
         # Build a "success" email and mail it to somebody 
         echo "Archived $OLDDIR/$LLFILE.$TIMESTAMP : $TIME" |$EMAIL "$SUBJG ($LISTENER_NAME on $NODE)" $MAILTO
         cleanup
     fi

  done } < $TEMPFILE
else
  # If there was no listener running, tell somebody
  echo Warning: No LISTENER was available on $NODE at the time of execution.|$EMAIL "$SUBJB (on $NODE)" $MAILTO
  exit 1
fi

rm $TEMPFILE

exit 0
