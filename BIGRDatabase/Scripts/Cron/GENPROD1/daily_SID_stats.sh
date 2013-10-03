#!/usr/bin/ksh

#################################################################################################################
#	File:		daily_SID_stats.sh
#	Author:  	Dean Marsh
#	Date:	 	09/16/2002
#
#	Purpose:	Shell script to automatically run daily monitoring tasks such as:	
#			Gathering database statistics
#   			Gathering performance statistics
#			Checking disk utilization
# 
#			Interactively gather the SID to monitor and the sys password.
# 			SID and pwd for externally identified user of 'none' can be used instead, as long as
#			that externally identified user has DBA privileges.
#
##################################################################################################################

#  Check to see if script was called with 2 arguments and set up environment.

if [ $# -ne 4 ]
then
   echo "Please enter the SID to examine (SID must be in capital letters): "
   read ORACLE_SID
   echo "Please enter the sys user's password: "
   read SYS_PWD
else
ORACLE_SID=
SYS_PWD=NONE

while getopts s:p: name
do
   case $name in
      s)        ORACLE_SID="$OPTARG";;
      p)        SYS_PWD="$OPTARG";;
      ?)        printf  "Usage:  daily_SID_stats.sh -s <oracle_sid> -p <sys_pwd> "
                printf  "   <oracle_sid> = SID to gather stats on (ex: LEXQA1)"
                printf  "   <sys_pwd> = sys user password.  Use 'none' for external user login"
                exit 1;;
   esac
done
shift `expr $OPTIND - 1`
fi

export STATS_LOG=${ORACLE_SID}_daily_stats_`date '+%m%d%y'`.msglog
export ORACLE_HOME=/u01/app/oracle/product/9.2.0
export LOG_DIR=/u01/app/oracle/scripts/log
export ALERT_DIR=/u01/app/oracle/admin/${ORACLE_SID}/bdump
export MAINT_DIR=/u01/app/oracle/scripts/maint
export DATE=`date`

#  Log into the appropriate SID and run the requested scripts, outputting
#  the results to the file "daily_$ORACLE_SID.lst".

if test "$SYS_PWD" = "none"
then
   $ORACLE_HOME/bin/sqlplus /@${ORACLE_SID} << EOF
   spool ${MAINT_DIR}/daily_$ORACLE_SID.lst
   @${MAINT_DIR}/sga_stats.sql
   @${MAINT_DIR}/daily_mon.sql
   spool off
   exit
EOF
else
   $ORACLE_HOME/bin/sqlplus sys/${SYS_PWD}@${ORACLE_SID} << EOF
   spool ${MAINT_DIR}/daily_$ORACLE_SID.lst
   @${MAINT_DIR}/sga_stats.sql
   @${MAINT_DIR}/daily_mon.sql
   spool off
   exit
EOF
fi

cat ${MAINT_DIR}/daily_${ORACLE_SID}.lst >> ${LOG_DIR}/${STATS_LOG}

#rm ${MAINT_DIR}/daily_${ORACLE_SID}.lst

#  examine the output file and the alert logs.

#vi daily_$ORACLE_SID.lst

#vi $ORACLE_BASE/admin/$ORACLE_SID/bdump/alert_$ORACLE_SID.log

#  Get what OS the SID is on and execute the appropriate disk utilization command.

osver=`uname -a | cut -f1 -d' '`

{
echo ''
echo ''
echo '##################################  Disk Utilization Statistics ##################################'
echo ''
echo ''
case $osver in
   HP-UX)
      bdf
      ;;
   *)
      df -k
      ;;
esac
} >> ${LOG_DIR}/${STATS_LOG}
