#!/bin/ksh
#============================================================================
# File:         chk_oem.ksh
# Type:         UNIX korn-shell script
# Author:       Phil DiPrima
# Date:         11/30/06
#
# Description:
#       Check to see if the processes for OEM are running
#       * Loops through oratab and looks for the bos-devdb1.ardais.com_$ORACLE_SID
#         process per database.  At this time it does not check for OEM on RHLEXDEV and
#         RHLEXTRN databases.
#
#        emctl start dbconsole will start the OEM per database process.
#
# Syntax:
#       chk_oem.ksh 
#============================================================================
# Configuration
# Who do I email
export RCPT=dba@healthcit.com

#============================================================================
# Configure the script here
#
# Oracle env
. /home/oracle/.oracle.profile

#***********************************************************************
#  Here is where we loop through each SID in /etc/oratab . . .
#***********************************************************************

for SID in `cat /etc/oratab | grep :N|cut -d":" -f1`
do
 if [[ $SID != "RHLEXDEV" && $SID != "RHLEXTRN" ]];then
   export ORACLE_SID=$SID
   HOST=`hostname`
   if [[ $(ps -ef|grep bos-devdb1.ardais.com_$ORACLE_SID|wc -l) != 3 ]];then
      echo "Use emctl start dbconsole"|mailx -s "OEM on $HOST for $ORACLE_SID is not running." $RCPT
   fi
 fi
done
