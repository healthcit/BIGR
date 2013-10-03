#!SHELLMARKER
#
# Set the web server and app server to maintenance mode.  In this mode, the
# web server is accessible but WebSphere isn't, and the web server's
# root index.html page displays a message saying that the system is in
# maintenance mode.
#
# See also IHS_offline.sh and IHS_prod.sh.
#

if [ $# != 0 ]; then
  echo "Usage: ${0}" 1>&2
  exit 1
fi

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

HTTPD_CONF_PROD="${IHS_HOME}/conf/httpd.conf.prod"

if [ ! -f "${HTTPD_CONF_PROD}" ]; then
  echo "This server is not set up to support production/maintenance/offline modes.  The IHS configuration template file does not exist (${HTTPD_CONF_PROD})." 1>&2
  exit 1
fi

${IHS_HOME}/bin/apachectl stop

# We get the "maintenance" version of httpd.conf by starting with the
# httpd.conf.prod file as a template and making some changes to it.
# Several lines need to be commented out so that the WebSphere modules aren't
# loaded into the web server.
# 
HTTPD_CONF_MAINT="${IHS_HOME}/conf/httpd.conf.maint"
sed -f "${BIGR_SCRIPT_LIB}/editHttpdConfMaint.txt" < "${HTTPD_CONF_PROD}" > "${HTTPD_CONF_MAINT}"

cp "${HTTPD_CONF_MAINT}" "${IHS_HOME}/conf/httpd.conf"

# Replace the web server's index.html page, creating a backup of the current
# version first.
#
INDEX_HTML_DIR="${IHS_HOME}/htdocs/en_US"
INDEX_HTML_FILE="${INDEX_HTML_DIR}/index.html"
if [ -f "${INDEX_HTML_FILE}" ]; then
  cp -p "${INDEX_HTML_FILE}" "${INDEX_HTML_DIR}/index.html.backup`date '+%Y%m%d%H%M%S'`"
fi
cp "${BIGR_SCRIPT_LIB}/templates/maint_index.html" "${INDEX_HTML_FILE}"

# The sleep is necessary, sometimes the stop command returns before the
# processes are actually dead and the pid file removed, so that the start
# command thinks it is still running when in fact it is shutting down.
#
sleep 5

${IHS_HOME}/bin/apachectl start
