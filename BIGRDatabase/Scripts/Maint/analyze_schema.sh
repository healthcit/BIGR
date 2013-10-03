#!/usr/bin/ksh

export ORACLE_HOME=/u01/app/oracle/product/9.2.0
export ORACLE_BASE=/u01/app/oracle
export TNS_ADMIN=/u01/app/oracle/product/9.2.0
ORACLE_SID=$2
export $ORACLE_SID

$ORACLE_HOME/bin/sqlplus /@$ORACLE_SID <<EOF
@$ORACLE_BASE/scripts/maint/analyze_schema.sql '$1' '$ORACLE_SID'
exit;
EOF
