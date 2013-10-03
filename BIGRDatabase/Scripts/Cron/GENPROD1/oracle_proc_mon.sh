#!/usr/bin/sh
# This script will monitor for unusual activity and send warnings and
# alerts if action is needed.
# Dan Kern August 09, 2002
# Fine tune the variables for each system. 
# Comment out sections not needed for Oracle or WebSphere Servers

HOSTNAME=`hostname`
#JAVA_MAX_PROCESSES=4
#JAVA_MIN_PROCESSES=2
#ORACLE_SYSTEM_MAX_PROCESSES=35
ORACLE_TOTAL_MAX_PROCESSES=25
#ORACLE_SYSTEM_MIN_PROCESSES=34
#HTTPD_MIN_PROCESSES=8
#WARNING_CPU=1
#DANGER_CPU=2

# This section will monitor for extra Java processes.
#CURRENT_JAVA_PROCESSES=`ps -e |grep java |wc -l`
#if [ "$CURRENT_JAVA_PROCESSES" -ge "$JAVA_MAX_PROCESSES" ]
#then
#echo "ALERT!!! - ${HOSTNAME}  Extra Java Processes - Java Process number is ${CURRENT_JAVA_PROCESSES}" | mailx -s "Warning!" sysadmin@healthcit.com
#fi

# This section will check for too few Java processes 
#if [ "$JAVA_MIN_PROCESSES" -ge "$CURRENT_JAVA_PROCESSES" ]
#then
#echo "ALERT!!! - ${HOSTNAME} Too Few Java Processes at DANGER LEVEL of ${CURRENT_JAVA_PROCESSES}" | mailx -s "ALERT!" sysadmin@healthcit.com 
#fi

# This section will monitor for Minimum number of Oracle System processes.
#CURRENT_ORACLE_PROCESSES=`ps -ef |grep ora_ |wc -l`
#if [ "$ORACLE_SYSTEM_MIN_PROCESSES" -ge "$CURRENT_ORACLE_PROCESSES" ]
#then
#echo "ALERT!!! - ${HOSTNAME} Too Few Oracle System Processes at DANGER LEVEL of ${CURRENT_ORACLE_PROCESSES}" | mailx -s "ALERT!" sysadmin@healthcit.com 
#fi

# This section will monitor for Maximum number of Oracle System processes.
CURRENT_ORACLE_PROCESSES=`ps -ef |grep oracleGENPROD1 |wc -l`
if [ "$CURRENT_ORACLE_PROCESSES" -ge "$ORACLE_TOTAL_MAX_PROCESSES" ]
then
echo "ALERT!!! - ${HOSTNAME} Too Many GENPROD1 User Connections at DANGER LEVEL of ${CURRENT_ORACLE_PROCESSES}" | mailx -s "ALERT!" sysadmin@healthcit.com,dba_alert@healthcit.com
fi

# This section will monitor for Minimum number of IHS httpd processes.
#CURRENT_HTTPD_PROCESSES=`ps -ef |grep httpd |wc -l`
#if [ "$HTTPD_MIN_PROCESSES" -ge "$CURRENT_HTTPD_PROCESSES" ]
#then
#echo "ALERT!!! - ${HOSTNAME} Too Few IHS httpd Processes at DANGER LEVEL of ${CURRENT_HTTPD_PROCESSES}" | mailx -s "ALERT!" sysadmin@healthcit.com
#fi

