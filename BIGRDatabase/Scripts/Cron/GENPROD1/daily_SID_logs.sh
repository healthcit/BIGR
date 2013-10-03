#!/usr/bin/ksh

###############################################################################################################################
#	Filename:	daily_SID_logs.sh
#	Author:		Dean Marsh, Ardais Corp.
#	Date:		09/11/2002
#
#	Purpose:	This shell script is used to get all of the daily output logs for nightly processes and put them into
#			an HTML formatted file for viewing from the internal Ardais server.
#
#			This file takes only one argument at this time and that is the SID that logs are being gathered for.
#
#	Example:	./daily_SID_logs.sh LEXPROD1
#
###############################################################################################################################

#	Check to see if there are enough arguments.  There should only be 1.

if [ $# -ne 1 ]
then
   echo "Usage: ./daily_SID_logs.sh <SID> " 
   echo "   <SID> = database instance to gather logs for."
   exit 1
fi

#	Set up the environment variables.

export ORACLE_SID=$1
export LOG_DIR=/u01/app/oracle/scripts/log
export ARCHIVEONLY=${LOG_DIR}/archiveOnly_${ORACLE_SID}_`date '+%m%d%y'`.msglog
export CROSSCHECK=${LOG_DIR}/crosscheck_${ORACLE_SID}_`date '+%m%d%y'`.msglog
export DELETEEXPIRED=${LOG_DIR}/deleteExpired_${ORACLE_SID}_`date '+%m%d%y'`.msglog
export FULLDB=/opt/tlobk/logs/obk.GENPROD1.log
export RESYNC=${LOG_DIR}/resync_${ORACLE_SID}_`date '+%m%d%y'`.msglog
export STATS_LOG=${LOG_DIR}/${ORACLE_SID}_daily_stats_`date '+%m%d%y'`.msglog
export ALERT_DIR=/u01/app/oracle/admin/${ORACLE_SID}/bdump
export TODAYS_LOG=${ORACLE_SID}_logs_`date '+%m%d%y'`.html
export DATE=`date`

#	Check for the presence of an offset log used for outputting the alert log to the file.  If it exists, export, otherwise, create and export.

if [ -f ${ALERT_DIR}/${ORACLE_SID}_alertlog.offset ]
then
   export ALERT_OFFSET=`sed 's/ //g' ${ALERT_DIR}/${ORACLE_SID}_alertlog.offset`
else
   cat ${ALERT_DIR}/alert_${ORACLE_SID}.log | wc -l > ${ALERT_DIR}/${ORACLE_SID}_alertlog.offset
   export ALERT_OFFSET=`sed 's/ //g' ${ALERT_DIR}/${ORACLE_SID}_alertlog.offset`
fi

#	Begin the outputting of the HTML and the logs to the actual file being created.

cat > ${LOG_DIR}/${TODAYS_LOG} << EOF
<HTML>
<HEAD>
   <TITLE>Database log files for ${ORACLE_SID} - ${DATE}</TITLE>
</HEAD>

<BODY lang=EN-US style='tab-interval:.5in'>

<CENTER><H1><FONT COLOR=BLUE>Database log files for ${ORACLE_SID} - ${DATE}</FONT></H1></CENTER>

<H2><B><FONT COLOR=BLUE>1.  Automated Analyze Output</FONT></B></H2>

<PRE>
EOF

{
echo
echo '<H3><B><FONT COLOR=BLACK>1.1  ------------------ADS_USER ANALYZE LOG----------------------</FONT></B></H3>'
echo
if [ -f ${LOG_DIR}/analyze_ads_user_${ORACLE_SID}.log ]
then
   cat $LOG_DIR/analyze_ads_user_${ORACLE_SID}.log
else
   echo 'File not found: ${LOG_DIR}/analyze_ads_user_${ORACLE_SID}.log' 
fi
echo
echo
echo '<H3><B><FONT COLOR=BLACK>1.2  ------------------ARDAIS_OWNER ANALYZE LOG -----------------</FONT></B></H3>'
echo
if [ -f ${LOG_DIR}/analyze_ardais_owner_${ORACLE_SID}.log ]
then
   cat ${LOG_DIR}/analyze_ardais_owner_${ORACLE_SID}.log
else
   echo 'File not found:  ${LOG_DIR}/analyze_ardais_owner_${ORACLE_SID}.log'
fi
echo
echo
} >> ${LOG_DIR}/${TODAYS_LOG}

cat >> ${LOG_DIR}/${TODAYS_LOG} << EOF
</PRE>

<H2><B><FONT COLOR=BLUE>2.  Backup Results</FONT></B></H2>
<PRE>
EOF

{
echo
echo '<H3><B><FONT COLOR=BLACK>2.1  --------------------ARCHIVE BACKUP LOG------------------------</FONT></B></H3>' 
echo
if [ -f $ARCHIVEONLY ]
then
   cat $ARCHIVEONLY 
else
   echo 'File not found:  ${ARCHIVEONLY}'
fi
echo
echo '<H3><B><FONT COLOR=BLACK>2.2  --------------------CROSSCHECK LOG----------------------------</FONT></B></H3>'
echo
if [ -f $CROSSCHECK ]
then
   cat $CROSSCHECK
else
   echo 'File not found:  ${CROSSCHECK}'
fi
echo
echo '<H3><B><FONT COLOR=BLACK>2.3  --------------------DELETE EXPIRED LOG------------------------</FONT></B></H3>'
echo
if [ -f $DELETEEXPIRED ]
then
   cat $DELETEEXPIRED
else
   echo 'File not found:  ${DELETEEXPIRED}'
fi
echo
echo '<H3><B><FONT COLOR=BLACK>2.4  --------------------FULLDB BACKUP LOG-------------------------</FONT></B></H3>'
echo
if [ -f $FULLDB ]
then
   cat $FULLDB
else
   echo 'File not found:  ${FULLDB}'
fi
echo
} >> ${LOG_DIR}/${TODAYS_LOG}

cat >> ${LOG_DIR}/${TODAYS_LOG} << EOF
</PRE>

<H2><B><FONT COLOR=BLUE>3.  Nightly SGA and SID Statistics</FONT></B></H2>
<PRE>
EOF
if [ -f $STATS_LOG ]
then
   cat ${STATS_LOG} >> ${LOG_DIR}/${TODAYS_LOG}
else
   echo 'File not found:  ${STATS_LOG}'
fi

cat >> ${LOG_DIR}/${TODAYS_LOG} << EOF
</PRE>
<H2><B><FONT COLOR=BLUE>4.  Alert Logs</FONT></B></H2>
<PRE>
EOF
if [ -f ${ALERT_DIR}/alert_${ORACLE_SID}.log ]
then
   tail +${ALERT_OFFSET} ${ALERT_DIR}/alert_${ORACLE_SID}.log >> ${LOG_DIR}/${TODAYS_LOG}
fi

cat >> ${LOG_DIR}/${TODAYS_LOG} << EOF
</PRE>

</BODY>

</HTML>

EOF

cat ${ALERT_DIR}/alert_${ORACLE_SID}.log | wc -l > ${ALERT_DIR}/${ORACLE_SID}_alertlog.offset
