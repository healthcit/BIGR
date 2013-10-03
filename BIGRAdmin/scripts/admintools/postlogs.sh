#!SHELLMARKER
#
# Publish a collection of BIGR logs to the web server and send an email
# with URL links to the logs to appropriate administrators.  If the
# -rotate switch is specified, then after logs are published, logs file
# contents will be cleaned up (depending on which logs file is involved,
# cleanup may involved zeroing the file length, deleting files, or compressing
# files).
#

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

#################### Parse command-line arguments.

usage () {
  echo "Usage: ${0} [-rotate]" 1>&2
  exit 1
}

# Define variables that get set during command-line parsing.

# By default, we do not rotate (clean up) log file contents after posting
# the logs.
#
DO_ROTATE=false

while [ ! -z "$1" ];
  do case x"$1" in
    x-rotate) DO_ROTATE=true ;;
    x-*) echo "Unknown option $1" 1>&2; usage ;;
    *) break ;;
  esac
  shift
done

if [ $# != 0 ]; then
  usage
fi

#################### Done parsing command-line arguments.

DATE=`date`
WAS_LOGS_TMP_FILE="${BIGR_LOG_DIR}/was_logs.txt"
if [ ${BIGR_WAS_MAJOR_VERSION} -ge 6 ]; then
  WAS_LOG_DIR="${WAS_HOME}/profiles/default/logs"
else
  # WAS5...
  WAS_LOG_DIR="${WAS_HOME}/logs"
fi
BIGR_WAS_LOG_DIR="${WAS_LOG_DIR}/${BIGR_SERVER}"
HOST_NAME=`hostname`

#################### Function definitions

appendFile () {
  local fileToAppend=${1}
  local appendToFile=${2}
  if [ $# -gt 2 ]; then
    local title=${3}
  else
    local title=`basename "${fileToAppend}"`
  fi

  echo "${title}" >> $appendToFile
  echo "--------------------------------" >> $appendToFile
  if [ -f $fileToAppend ]; then
    cat "$fileToAppend" >> $appendToFile
  else
    echo "This file does not exist." >> $appendToFile
  fi
  echo "--------------------------------" >> $appendToFile

}

clearFile () {
  local fileToClear=${1}
  if [ -f "${fileToClear}" ]; then
    cat < /dev/null > "${fileToClear}"
  fi
}

clearLogs () {
  # Clear out log files.  This clears some log files that we don't post
  # during the log-publication phase, since we're not very interested in
  # the contents of some logs but need to make sure they don't just grow
  # indefinitely.

  clearFile "${WAS_LOG_DIR}/activity.log"
  clearFile "${WAS_LOG_DIR}/http_plugin.log"
  clearFile "${WAS_LOG_DIR}/wsadmin.traceout"
  clearFile "${WAS_LOG_DIR}/wsadmin.valout"
  clearFile "${WAS_LOG_DIR}/ffdc/${BIGR_SERVER}_exception.log"

  find "${WAS_LOG_DIR}/ffdc" -name "${BIGR_SERVER}_*.txt" -mtime +7 -exec /bin/rm -f {} \; > /dev/null 2>&1

  clearFile "${BIGR_WAS_LOG_DIR}/SystemErr.log"
  clearFile "${BIGR_WAS_LOG_DIR}/native_stderr.log"
  clearFile "${BIGR_WAS_LOG_DIR}/SystemOut.log"
  clearFile "${BIGR_WAS_LOG_DIR}/native_stdout.log"
  clearFile "${BIGR_WAS_LOG_DIR}/startServer.log"
  clearFile "${BIGR_WAS_LOG_DIR}/stopServer.log"

  clearFile "${BIGR_LOG_DIR}/ApilogFile.log"
  clearFile "${BIGR_LOG_DIR}/ComputedDataLog.log"
  clearFile "${BIGR_LOG_DIR}/arts.log"
  clearFile "${BIGR_LOG_DIR}/PerfLog.log"
  clearFile "${BIGR_LOG_DIR}/HttpRequest.log"

  clearFile "${IHS_HOME}/logs/access_log"
  clearFile "${IHS_HOME}/logs/error_log"
  clearFile "${IHS_HOME}/logs/https_rewrite_log"

  clearFile "${BIGR_LOG_DIR}/systemReport"
  clearFile "${BIGR_LOG_DIR}/newlogs.txt"

  # Compress old published log files
  #
  find "${POSTLOG_PUBLICATION_DIR}" -name "*.html" -mtime +7 -exec gzip -f {} \; > /dev/null 2>&1
}

postLogs() {
  /bin/rm -f "${WAS_LOGS_TMP_FILE}"

  appendFile "${BIGR_LOG_DIR}/ApilogFile.log" "${WAS_LOGS_TMP_FILE}"
  appendFile "${BIGR_LOG_DIR}/ComputedDataLog.log" "${WAS_LOGS_TMP_FILE}"
  appendFile "${BIGR_LOG_DIR}/arts.log" "${WAS_LOGS_TMP_FILE}"
  appendFile "${IHS_HOME}/logs/error_log" "${WAS_LOGS_TMP_FILE}" "IHS error_log"
  appendFile "${BIGR_WAS_LOG_DIR}/SystemErr.log" "${WAS_LOGS_TMP_FILE}"
  appendFile "${BIGR_WAS_LOG_DIR}/native_stderr.log" "${WAS_LOGS_TMP_FILE}"
  appendFile "${BIGR_WAS_LOG_DIR}/SystemOut.log" "${WAS_LOGS_TMP_FILE}"
  appendFile "${BIGR_WAS_LOG_DIR}/native_stdout.log" "${WAS_LOGS_TMP_FILE}"
  appendFile "${BIGR_LOG_DIR}/PerfLog.log" "${WAS_LOGS_TMP_FILE}"
  appendFile "${BIGR_LOG_DIR}/HttpRequest.log" "${WAS_LOGS_TMP_FILE}"

  # Publish the log files to the web server.
  #
  ${BIGR_SCRIPTS}/postlog.sh "${WAS_LOGS_TMP_FILE}" "WebSphere logs for ${DATE}"
  ${BIGR_SCRIPTS}/postlog.sh "${BIGR_LOG_DIR}/systemReport" "Daily Access and Usage Log for ${DATE}"
  ${BIGR_SCRIPTS}/postlog.sh "${IHS_HOME}/logs/access_log" "IHS access_log for ${DATE}"

  # Send mail with hyperlinks to the log files.
  #
  if [ "X$EMAIL_DEVADMIN" != "X" -a "NONE" != "${EMAIL_DEVADMIN}" ]; then
    ${MAILX} -s "${HOST_NAME} logs for ${DATE}" -v "${EMAIL_DEVADMIN}" < "${BIGR_LOG_DIR}/newlogs.txt" > /dev/null
  fi

  /bin/rm -f "${WAS_LOGS_TMP_FILE}"
}

#################### End of function definitions.

postLogs

if [ "Xtrue" = "X$DO_ROTATE" ]; then
  clearLogs
fi
