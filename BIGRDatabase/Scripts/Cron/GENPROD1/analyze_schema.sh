#!/usr/bin/ksh

########################################################################################
#	File:	analyze_schema.sh
#	Author:	Dean Marsh, Ardais Corp.
#	Date:	06/04/2002
#	
#	Purpose:	This shell script can be run with the appropriate command-line
#			arguments to do an analyze of a database schema.  This script
#			is generally run from the cron.
#
#	Example:	./analyze_schema.sh ardais_owner GENPROD1
#
#	Comments:
#
########################################################################################

export ORACLE_HOME=/u01/app/oracle/product/9.2.0
export ORACLE_BASE=/u01/app/oracle
export TNS_ADMIN=/u01/app/oracle/product/9.2.0
ORACLE_SID=$2
export $ORACLE_SID

$ORACLE_HOME/bin/sqlplus /@$ORACLE_SID <<EOF
@$ORACLE_BASE/scripts/maint/analyze_schema.sql '$1' '$ORACLE_SID'
exit;
EOF

