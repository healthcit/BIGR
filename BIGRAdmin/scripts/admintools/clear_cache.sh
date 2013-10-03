#!SHELLMARKER
#
# Invoke the BIGR web page that results in refreshing the cached information
# such as ARTS data.
#

if [ $# != 0 ]; then
  echo "Usage: ${0}" 1>&2
  exit 1
fi

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

LOGFILE="${BIGR_LOG_DIR}/clear_cache_log.txt"
DATE=`date`

/bin/rm -f ${LOGFILE}

${BIGR_SCRIPTS}/getwebpage.sh "${HOST_URL}/BIGR/orm/DispatchLogin?op=RefreshCaches&arg=refresh" > ${LOGFILE} 2>&1

${BIGR_SCRIPTS}/postlog.sh "${LOGFILE}" "Clear Cache log for ${DATE}"

/bin/rm -f ${LOGFILE}
