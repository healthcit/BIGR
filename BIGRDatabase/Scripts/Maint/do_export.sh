#!/usr/bin/ksh

export ORACLE_HOME=/u01/app/oracle/product/9.2.0
export ORACLE_SID=$1
export EXP_DEST=$2
export EXP_DT=`date '+%m%d%y'`

$ORACLE_HOME/bin/exp system/siadra1@${ORACLE_SID} file=${EXP_DEST}/${ORACLE_SID}_export_${EXP_DT}.dmp log=${EXP_DEST}/${ORACLE_SID}.log buffer=5000000 grants=n owner=ardais_owner ads_user record=n statistics=none > /dev/null
