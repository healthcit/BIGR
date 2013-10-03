#!/bin/sh
#This is a script which provides a hourly report on the number of connections
# to the GENPROD1 database 
#
REP_FILE=/home/app/oracle/scripts/maint/logs/connection_report
CURRENT_ORACLE_PROCESSES=`ps -ef |grep oracleGENPROD1 |wc -l`
echo "\n************\n" >> $REP_FILE
echo "Number of User Connections to GENPROD1 Database" >> $REP_FILE
echo "for `date`" >> $REP_FILE
echo "\n************\n" >> $REP_FILE
echo " $CURRENT_ORACLE_PROCESSES Users currently connected" >> $REP_FILE
echo "\n************\n" >> $REP_FILE
