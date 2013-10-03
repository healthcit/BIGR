#!SHELLMARKER
#
# Set the web server and app server to offline mode.  In this mode, the
# servers are only accessible to requests coming from within the intranet
# include from the VPN).
#
# See also IHS_maint.sh and IHS_prod.sh.
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

# We get the "offline" version of httpd.conf by starting with the
# httpd.conf.prod file as a template and making some changes to it.
# 
HTTPD_CONF_OFFLINE="${IHS_HOME}/conf/httpd.conf.offline"
cp "${HTTPD_CONF_PROD}" "${HTTPD_CONF_OFFLINE}"
cat >> "${HTTPD_CONF_OFFLINE}" <<EOF

# In "offline" mode, prohibit access to anything served by the web server
# (including BIGR) to any requests that don't come from the intranet,
# whic we assume here is the 192.168.1/24 network.
# The intent of offline mode is to allow system administrators to test BIGR
# without opening it up to other users.   We use this, for example, as one
# of the stages when we roll out a new BIGR release.
#
<Location />
order allow,deny
allow from 192.168.1
</Location>
EOF

cp "${HTTPD_CONF_OFFLINE}" "${IHS_HOME}/conf/httpd.conf"

# Replace the web server's index.html page, creating a backup of the current
# version first.
#
INDEX_HTML_DIR="${IHS_HOME}/htdocs/en_US"
INDEX_HTML_FILE="${INDEX_HTML_DIR}/index.html"
if [ -f "${INDEX_HTML_FILE}" ]; then
  cp -p "${INDEX_HTML_FILE}" "${INDEX_HTML_DIR}/index.html.backup`date '+%Y%m%d%H%M%S'`"
fi
cp "${BIGR_SCRIPT_LIB}/templates/index.html.login.jsp" "${INDEX_HTML_FILE}"

# The sleep is necessary, sometimes the stop command returns before the
# processes are actually dead and the pid file removed, so that the start
# command thinks it is still running when in fact it is shutting down.
#
sleep 5

${IHS_HOME}/bin/apachectl start
