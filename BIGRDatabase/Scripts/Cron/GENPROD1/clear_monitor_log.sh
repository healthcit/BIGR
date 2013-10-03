#!/bin/ksh

# Script to post the number of connections to the GENPROD1 database.
# This script logs the connections to a text file, e-mails the text file, then rotates it for the next day
# Dan Kern, August 9, 2002

DATE=$(date)
NAME1=$(date '+GENPROD1_monitor_logs%h%d%H%M')

if [ -f /home/app/oracle/scripts/maint/logs/connection_report ]
then
  cp /home/app/oracle/scripts/maint/logs/connection_report /home/app/oracle/scripts/maint/logs/$NAME1 
fi 

#mailx -s "GENPROD1 User Connections for $DATE" sysadmin@healthcit.com </home/app/oracle/scripts/maint/logs/$NAME1 > /dev/null 

cat < /dev/null >  /home/app/oracle/scripts/maint/logs/connection_report
