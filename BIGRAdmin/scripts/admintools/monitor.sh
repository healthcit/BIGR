#!SHELLMARKER
#
# This script provides a daily report of various system activities.
#

if [ $# != 0 ]; then
  echo "Usage: ${0}" 1>&2
  exit 1
fi

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

REPORT_FILE="${BIGR_LOG_DIR}/systemReport"

if [ "${PLATFORM}" = "Linux" ]; then
  SYS_LOG=`tail /var/log/messages`
else
  # This works at least for Solaris.
  SYS_LOG=`dmesg | tail`
fi

ECHO="echo -e"

${ECHO} "Good Day - Here's the hourly news from `uname -n`" >> $REPORT_FILE
${ECHO} "for `date`" >> $REPORT_FILE
${ECHO} "\n************\n" >> $REPORT_FILE
${ECHO} "The last entries in the syslog are:\n \n$SYS_LOG" >> $REPORT_FILE

UPTIME_STRING=`uptime`
RE_BOOT=`who -b`
${ECHO} "\n*******\n" >> $REPORT_FILE
${ECHO} "Uptime: $UPTIME_STRING" >> $REPORT_FILE
${ECHO} "The system was last rebooted:"$RE_BOOT >> $REPORT_FILE

#Local People
CURRENT_USERS=`who`
if [ "${PLATFORM}" = "Linux" ]; then
  WHO_SU=`grep " su(" /var/log/messages | tail`
else
  # This works at least for Solaris.
  WHO_SU=`tail /var/adm/sulog`
fi
LAST_10=`last -10`
${ECHO} "\n*******\n" >> $REPORT_FILE
${ECHO} "The users currently logged in are:\n \n$CURRENT_USERS" | cat >> $REPORT_FILE
${ECHO} "\n*******\n" >> $REPORT_FILE
${ECHO} "The last 10 logins to the system were:\n \n$LAST_10" | cat >> $REPORT_FILE
${ECHO} "\n*******\n" >> $REPORT_FILE
${ECHO} "The last 10 messages related to the su command were:\n \n$WHO_SU" | cat >> $REPORT_FILE
${ECHO} "\n \n" >> $REPORT_FILE
${ECHO} "\n \n" >> $REPORT_FILE

# Now post the hourly report to the web server.  We don't use the postlog.sh
# script to do this since we don't want the hourly reports to accumulate
# as separate files, we just want to repeatedly overwrite the same file
# in the web server's directory.

TITLE="`hostname`: Hourly Report at `date`"

cat > ${POSTLOG_PUBLICATION_DIR}/hourly_report.html <<EOF
<html>
<head>
<meta http-equiv="Pragma" CONTENT="no-cache">
<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta http-equiv="max-age" CONTENT="0">
<meta http-equiv="Expires" CONTENT="0">
<title>${TITLE}</title>
</head>
<body>
<h3>${TITLE}</h3>
<pre>
EOF

# Append the file into the HTML file, escaping special HTML characters.
#
sed -e 's/\&/\&amp\;/g' < "${REPORT_FILE}" | sed -e 's/>/\&gt\;/g' -e 's/</\&lt\;/g' >> "${POSTLOG_PUBLICATION_DIR}/hourly_report.html"

cat >> ${POSTLOG_PUBLICATION_DIR}/hourly_report.html <<EOF
</pre>
</body>
</html>
EOF
