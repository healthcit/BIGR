#!SHELLMARKER
#
# Start the BIGR Enterprise Application.  This only starts the BIGR
# application, not the entire WebSphere application server that BIGR
# runs inside.
#

if [ $# != 0 ]; then
  echo "Usage: ${0}" 1>&2
  exit 1
fi

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

${WAS_BIN}/wsadmin.sh -f ${BIGR_SCRIPT_LIB}/startBIGR.jacl
