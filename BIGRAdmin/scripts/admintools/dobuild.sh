##!SHELLMARKER

binDir=`dirname ${0}`
. $binDir/lib/setupCmdLine.sh

# This is just a shell for the real dobuild.sh script in the BIGR_SCRIPT_LIB
# directory.  All it does is invoke that script in a way that results in
# all of the output going to both the console and a timestamped log file.

# This deliberately doesn't create its log file in BIGR_LOG_DIR, because
# one of the things that this script does is clean up the log directory,
# and we don't want to remove our own log file.  It also doesn't use .log
# as the extension because we used to store log files in BIGR_LOCAL_LIB
# and for a transition period we'll still be deleting log files from there
# as well to get things completed cleaned up.

DATETIME=`date +'%Y%m%d%H%M'`
LOGFILE=${BIGR_LOCAL_LIB}/dobuild_at_${DATETIME}.txt

echo "dobuild log: ${LOGFILE}"
echo ${WAS_HOME}
${BIGR_SCRIPT_LIB}/dobuild.sh "$@" 2>&1 | tee "${LOGFILE}"

${BIGR_SCRIPTS}/postlog.sh "${LOGFILE}" "dobuild log for `date`"

/bin/rm -f "${LOGFILE}"
