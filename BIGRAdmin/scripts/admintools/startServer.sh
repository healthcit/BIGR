##!SHELLMARKER
#
# Start the WebSphere application server that BIGR runs in.  This starts
# the entire WebSphere application server, not just the BIGR application.
# See the startBIGR.sh script to start just the BIGR application.
#

if [ $# != 0 ]; then
  echo "Usage: ${0}" 1>&2
  exit 1
fi

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

${WAS_BIN}/startServer.sh ${BIGR_SERVER}
