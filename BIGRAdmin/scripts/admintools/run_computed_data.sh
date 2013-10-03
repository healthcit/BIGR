#!SHELLMARKER
#
# Invoke the BIGR web page that results in computed-data fields for pathology
# evaluations and samples being updated.
#

if [ $# != 0 ]; then
  echo "Usage: ${0}" 1>&2
  exit 1
fi

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

LOGFILE=${BIGR_LOG_DIR}/computed_data_log.txt
DATE=`date`

/bin/rm -f ${LOGFILE}

${BIGR_SCRIPTS}/getwebpage.sh "${HOST_URL}/BIGR/computedData/updatePathologyEvaluations.do?id=all&field=all"  "${BIGR_BOOTSTRAP_USERID}" "${BIGR_BOOTSTRAP_ACCOUNT}" "${BIGR_BOOTSTRAP_PASSWORD}" > ${LOGFILE} 2>&1

${BIGR_SCRIPTS}/getwebpage.sh "${HOST_URL}/BIGR/computedData/updateSamples.do?id=all"  "${BIGR_BOOTSTRAP_USERID}" "${BIGR_BOOTSTRAP_ACCOUNT}" "${BIGR_BOOTSTRAP_PASSWORD}" > ${LOGFILE} 2>&1

${BIGR_SCRIPTS}/postlog.sh "${LOGFILE}" "Computed Data shell log for ${DATE}"

/bin/rm -f ${LOGFILE}
