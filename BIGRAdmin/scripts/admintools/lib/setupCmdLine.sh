#!SHELLMARKER

# Define environment variables for BIGR admin scripts.
#
# **** PLEASE NOTE: Much of the actual administrative tasks are done by
# **** wsadmin scripts (written in JACL) called from shell scripts.  Because
# **** JACL can't access shell environment variables, there's a separate
# **** wsadmin.properties file in the BIGR_SCRIPT_LIB directory.  In some
# **** cases, JACL scripts and shell scripts need the same properties, and
# **** so there may be variables that are defined in both files.  So, when
# **** you edit this file, please also edit wsadmin.properties if necessary.
# **** By convention, when variables exist
# **** in both places, they are named the same except that the ones in
# **** wsadmin.properties are all in lower case with name components separated
# **** by periods, and names in setupCmdLine.sh are all in upper case with
# **** name components separated by underscores.
#
# If you need to make changes to this script to customize it for a particular
# machine, don't edit this script.  This script is meant to be the same on
# all machine to make script maintenance easier.  Instead, there are several
# provisions for making local changes:
#
#   - Place a script named preSetupCmdLine.sh in BIGR_LOCAL_LIB (see below).
#     If it exists, that script will be executed before this script sets
#     any environment variables except BIGR_SCRIPTS, BIGR_SCRIPT_LIB, 
#     BIGR_LOCAL_LIB, IHS_HOME, and WAS_SETUP_COMMAND_LINE_DIR.
#     The pre-setup script is useful, for example, if you
#     need to override WAS_SETUP_COMMAND_LINE_DIR because WebSphere is
#     installed in a different directory than usual on some machine.
#
#   - Place a script named postSetupCmdLine.sh in BIGR_LOCAL_LIB (see below).
#     If it exists, that script will be executed after everything else in
#     this script.
#
#   - Place a JACL script named wsadmin_profile.jacl in BIGR_LOCAL_LIB (see
#     below).  If it exists, that script will be run whenever the wsadmin
#     tool is run, following the standard wsadmin_profile.jacl script that
#     is in BIGR_SCRIPT_LIB.

# The directory where BIGR command scripts live.  The complexity of cding
# to the dirname directory is needed to work correctly in the case where
# dirname $0 returns a relative path instead of an absolute one.  We need
# BIGR_SCRIPTS to end up being an absolute path.  Because we execute this
# file by including it rather than executing it, $0 will be the name of
# the script that invoked this, not the name of the setupCmdLine.sh script
# itself.  Because of this, calling setupCmdLine.sh from a script that
# isn't itself in the BIGR_SCRIPTS directory would result in BIGR_SCRIPTS
# being set incorrectly.  In this situation, the invoking script must set
# BIGR_SCRIPTS before calling setupCmdLine.sh.  If BIGR_SCRIPTS already has
# a value, it won't be reset here.
#
if [ "X" = "X${BIGR_SCRIPTS:-}" ]; then
  savedir=`pwd`
  cd `dirname $0`
  export BIGR_SCRIPTS=`pwd`
  cd $savedir
fi

# The directory where subsidiary GSBio/BIGR scripts live.  These are scripts
# and other files that are meant to be used by other scripts in BIGR_SCRIPTS
# rather than being called directly.
#
export BIGR_SCRIPT_LIB=${BIGR_SCRIPTS}/lib

# The directory where subsidiary GSBio/BIGR scripts and admin configuration
# files that are local to a specific machine live.  These are scripts
# and other files that are meant to be used by other scripts in BIGR_SCRIPTS
# rather than being called directly.
#
export BIGR_LOCAL_LIB=${BIGR_SCRIPTS}/local

# The directory where IBM HTTP Server is installed.  If this variable already
# has a value when we get here, we don't re-set it.
#
if [ "X" = "X${IHS_HOME:-}" ]; then
  export IHS_HOME=/opt/IBMIHS
fi

# The bin directory under the directory where WebSphere is installed.
# This must be the directory that contains WebSphere's version of the
# setupCmdLine.sh script.  If this variable already has a value when we
# get here, we don't re-set it.
#
# This isn't an exported variable.
#
if [ "X" = "X${WAS_SETUP_COMMAND_LINE_DIR:-}" ]; then
  WAS_SETUP_COMMAND_LINE_DIR=/opt/IBM/WebSphere/AppServer/bin
fi

# If the local pre-setup script exists, run it.
#
if [ -f ${BIGR_LOCAL_LIB}/preSetupCmdLine.sh ]; then
  . ${BIGR_LOCAL_LIB}/preSetupCmdLine.sh
fi

# Do the standard WebSphere command line setup.
# As of WebSphere 6 the standard WebSphere script must
# be run with the directory it is in as the current directory,
# otherwise it will set WAS_HOME and possibly other variables
# incorrectly.  In addition, if the script invoking WebSphere's
# setupCmdLine.sh script in WAS6 is not in the same directory
# as that file, WAS_HOME will be set incorrectly.  In order to
# work around this problem, IBM has provided another variable
# (REPLACE_WAS_HOME) that if defined will be used to set WAS_HOME
# instead of trying to auto-detect the value for WAS_HOME.
#
savedir=`pwd`
cd ${WAS_SETUP_COMMAND_LINE_DIR}/..
REPLACE_WAS_HOME=`pwd`
cd ${WAS_SETUP_COMMAND_LINE_DIR}
. ./setupCmdLine.sh
cd $savedir

# If we get here and the value of WAS_HOME isn't an existing directory,
# something is wrong and we just exit with an error.  We also check for
# a "properties" directory under WAS_HOME to do a bit better validation
# on whether WAS_HOME plausibly is a place where WebSphere is installed.
#
if [ ! -d "${WAS_HOME}" ]; then
  echo "Error: WAS_HOME directory does not exist (${WAS_HOME}).  Please check that WAS_HOME is set correctly." >&2
  exit 1
fi
if [ ! -d "${WAS_HOME}/properties" ]; then
  echo "Error: WAS_HOME/properties directory does not exist (${WAS_HOME}).  Please check that WAS_HOME is set correctly." >&2
  exit 1
fi

# Set up GSBio/BIGR variables next:

export PLATFORM=`/bin/uname`

# Set a variable that specifies the WAS version.  For version 5 or later
# at least major version information is in
# ${WAS_HOME}/properties/version/platform.websphere.  If
# ${WAS_HOME}/properties exists but ${WAS_HOME}/properties/version doesn't
# we assume WAS 4 or earlier, which these scripts don't support.
#
if [ ! -d "${WAS_HOME}/properties/version" ]; then
  echo "Error: The WebSphere in ${WAS_HOME} appears to be earlier than version 5 and is unsupported." >&2
  exit 1
else
  export BIGR_WAS_MAJOR_VERSION=`sed -ne "/websphere.*version/ {
s/^.*websphere.*version=\"\(.\).*$/\1/
p
}" < ${WAS_HOME}/properties/version/platform.websphere`
fi

# The directory where WebSphere command scripts live
export WAS_BIN=${WAS_HOME}/bin

# The wsadmin properties file to use.  Since JACL can't access shell
# environment variables, there may be duplicate variable definitions
# between the shell variables in setupCmdLine.sh and the properties
# defined in the wsadmin properties file.
#
export WSADMIN_PROPERTIES=${BIGR_SCRIPT_LIB}/wsadmin.properties

# The name of the BIGR application as it is installed in WebSphere.
#
export BIGR_APPNAME=BIGR

# The directory containing BIGR configuration files such as Api.properties.
#
export BIGR_CONFIG_DIR=${BIGR_LOCAL_LIB}

# The directory where BIGR places various log files.
export BIGR_LOG_DIR=${BIGR_CONFIG_DIR}/logs

# Directories where BIGR places various temporary files.
export BIGR_TEMP_DIR=${BIGR_CONFIG_DIR}/tmp
export BIGR_BUILD_TEMP_DIR=${BIGR_TEMP_DIR}/build

# The name of the server that BIGR is installed on.  This is the logical
# name of a "server" in the WebSphere admin console, not the name of the
# actual machine that BIGR is installed on.
#
export BIGR_SERVER=server1

# The name of the web server and web server node that provide access to BIGR.
# This is the web server that has the WebSphere plugin that points to BIGR.
# This is the local name of a "node" and "server" in the WebSphere admin
# console, not the name of the actual machine that the web server is installed
# on.  These are not used for websphere versions prior to WebSphere 6.
#
#export BIGR_WEB_SERVER_NODE=webserver1_node
export BIGR_WEB_SERVER_NODE=${WAS_NODE}
export BIGR_WEB_SERVER_NAME=webserver1

# The directory where the BIGR.ear file is expanded to upon installation.
#
if [ ${BIGR_WAS_MAJOR_VERSION} -ge 6 ]; then
  export BIGR_INSTALLED_EAR_DIR=${WAS_HOME}/profiles/AppSrv01/installedApps/${WAS_CELL}/${BIGR_APPNAME}.ear
else
  # WAS5...
  export BIGR_INSTALLED_EAR_DIR=${WAS_HOME}/installedApps/${WAS_NODE}/${BIGR_APPNAME}.ear
fi

# The root directory where WebSphere will store temporary files related to
# BIGR.  For example, BIGR's compiled JSP files will be under this directory
# (see the BIGR_COMPILED_JSPS_DIR variable below).
#
if [ ${BIGR_WAS_MAJOR_VERSION} -ge 6 ]; then
  export BIGR_WAS_TEMP_DIR=${WAS_HOME}/profiles/AppSrv01/temp/${WAS_NODE}/${BIGR_SERVER}/${BIGR_APPNAME}
else
  # WAS5...
  export BIGR_WAS_TEMP_DIR=${WAS_HOME}/temp/${WAS_NODE}/${BIGR_SERVER}/${BIGR_APPNAME}
fi

# The directory where BIGR's compiled JSPs are.
#
export BIGR_COMPILED_JSPS_DIR=${BIGR_WAS_TEMP_DIR}/BIGRWeb.war

# The directory where the BIGR build installer looks for build zip files,
# and where the builder places builds.
#
export BIGR_BUILD_DROP_DIR=${WAS_HOME}/builds

# The directory where the postlog.sh script publishes HTML files.
#
export POSTLOG_PUBLICATION_DIR=${IHS_HOME}/htdocs/en_US/logs

# This controls whether BIGR's database code will be installed as part of
# installing BIGR in the dobuild.sh script.  Valid values are true and false.
# The default is true.  In production, for example, we override this in the
# postSetupCmdLine.sh script to set it to false, since we install database
# code in a more complicated way in production.
#
export BIGR_DEPLOY_DB_CODE=true

# The name of the BIGR JDBC Data Source that is configured in WebSphere
#
export BIGR_DATASOURCE_NAME=BIGR_Data_Source

# Email addresses for system administrators.
#
export EMAIL_SYSADMIN="sysadmin@healthcit.com"

# Email addresses for development administrators.  This should generally
# include selected system administrators as well as development managers.
#
export EMAIL_DEVADMIN="sysadmin@healthcit.com"

# The MAILX variable stores the path and name of a mailer that accepts
# the message on stdin, a -s flag to specify the subject, and the
# to address on the command line. Currently, both mailx and mail are
# supported as long as they are in the PATH.

UNAME=`uname`
MAILX=`which mailx 2>&1 `
if [ ! -f "$MAILX" ] ; then
  MAILX=`which mail 2>&1 `
fi
export MAILX

# Read the verion of the currently-installed admintools and store it in
# a variable.
#
bigrAdmintoolsVersionFile="${BIGR_SCRIPT_LIB}/admintoolsVersion.txt"
if [ -f "${bigrAdmintoolsVersionFile}" ]; then
  export BIGR_CURRENTLY_INSTALLED_ADMINTOOLS_VERSION=`head -1 "${bigrAdmintoolsVersionFile}"`
else
  export BIGR_CURRENTLY_INSTALLED_ADMINTOOLS_VERSION=
fi

# The filename of the script, if it exists, that will be used to edit systemConfiguration.xml
export EDIT_SYSTEM_CONFIG=editSystemConfigurationXml.sh

################### THIS SHOULD COME LAST IN THIS SCRIPT.
#
# If the local post-setup script exists, run it.
#
if [ -f ${BIGR_LOCAL_LIB}/postSetupCmdLine.sh ]; then
  . ${BIGR_LOCAL_LIB}/postSetupCmdLine.sh
fi

# JACL scripts can't access the shell environment variables.  Se, we
# create a file that defines JACL environment variables that will be
# loaded whenever a JACL script is run from inside one of the BIGR admin
# shell scripts.  The file we create copies selected variables from the
# shell environment into the JACL script, to avoid having to manually
# maintain variables separately for the shell and for JACL.
#
# The file containing the JACL environment variables will be regenerated
# only if either it does not exist or it is older than one of the following
# files: lib/setupCmdLine.sh, local/preSetupCmdLine.sh, or
# local/postSetupCmdLine.sh.  So, you can force it to be recreated in a
# number of ways if necessary.

jaclEnvFile=${BIGR_LOCAL_LIB}/wsadmin_profile_generated.jacl
shellEnvFileMain=${BIGR_SCRIPT_LIB}/setupCmdLine.sh
shellEnvFilePre=${BIGR_LOCAL_LIB}/preSetupCmdLine.sh
shellEnvFilePost=${BIGR_LOCAL_LIB}/postSetupCmdLine.sh

# Because of difference in interpretation of the -ot operator between Solaris
# and Linux, we test for the jaclEnvFile not existing first, and then do the
# -ot tests only if it already exists.  This works correctly on both platforms.
# We also make sure that BIGR_LOCAL_LIB exists, to avoid problems when this is
# invoked from setup.sh at a point when BIGR_LOCAL_LIB has not already been
# created.
#
if [ ! -d "${BIGR_LOCAL_LIB}" ] ; then
  mkdir -p "${BIGR_LOCAL_LIB}"
  chmod 755 ${BIGR_LOCAL_LIB}
fi
updateJaclEnvFile=false
if [ ! -f "$jaclEnvFile" ]; then
  updateJaclEnvFile=true
elif [ "$jaclEnvFile" -ot "$shellEnvFileMain" -o "$jaclEnvFile" -ot "$shellEnvFilePre" -o "$jaclEnvFile" -ot "$shellEnvFilePost" ]; then
  updateJaclEnvFile=true
fi
if [ "Xtrue" = "X${updateJaclEnvFile}" ]; then
  /bin/rm -f "$jaclEnvFile"
  cat > "$jaclEnvFile" <<EOF
# DO NOT EDIT THIS FILE.
# IT IS AUTOMATICALLY GENERATED BY ../lib/setupCmdLine.sh

set env(BIGR_APPNAME) {$BIGR_APPNAME}
set env(BIGR_BOOTSTRAP_ACCOUNT) {$BIGR_BOOTSTRAP_ACCOUNT}
set env(BIGR_BOOTSTRAP_USERID) {$BIGR_BOOTSTRAP_USERID}
set env(BIGR_CONFIG_DIR) {$BIGR_CONFIG_DIR}
set env(BIGR_DATASOURCE_DB) {$BIGR_DATASOURCE_DB}
set env(BIGR_DATASOURCE_NAME) {$BIGR_DATASOURCE_NAME}
set env(BIGR_DATASOURCE_OWNER_PASSWORD) {$BIGR_DATASOURCE_OWNER_PASSWORD}
set env(BIGR_DATASOURCE_OWNER_USERNAME) {$BIGR_DATASOURCE_OWNER_USERNAME}
set env(BIGR_LOCAL_LIB) {$BIGR_LOCAL_LIB}
set env(BIGR_LOG_DIR) {$BIGR_LOG_DIR}
set env(BIGR_SCRIPT_LIB) {$BIGR_SCRIPT_LIB}
set env(BIGR_SCRIPTS) {$BIGR_SCRIPTS}
set env(BIGR_SERVER) {$BIGR_SERVER}
set env(BIGR_WAS_MAJOR_VERSION) {$BIGR_WAS_MAJOR_VERSION}
set env(BIGR_WEB_SERVER_NAME) {$BIGR_WEB_SERVER_NAME}
set env(BIGR_WEB_SERVER_NODE) {$BIGR_WEB_SERVER_NODE}
set env(WAS_CELL) {$WAS_CELL}
set env(WAS_NODE) {$WAS_NODE}
EOF
fi
chown root:sys "$jaclEnvFile"
chmod 600 "$jaclEnvFile"
#
###### Don't put anything after the lines above that run postSetupCmdLine.sh
###### and export variable values to the JACL environment.
