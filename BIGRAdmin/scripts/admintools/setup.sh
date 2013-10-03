#!/bin/sh
#
# Perform many of the tasks that are involved in installing BIGR on an
# application server.  This doesn't do everything, manual steps that must be
# performed are described separately as part of the migration and installation
# documentation.  This script tries to detect whether it has already been run
# to prevent problems that could occur if this was run again on a server that
# was already set up and in use (for example, there would be a risk of
# overwriting changes to some files inappropriately).

#################### Helper Functions

# Print out status messages
#   $1 = Message Importance (0=debug 1=info 2=warning 3=error)
#   $2 = Message to Print
log () {
  dat=`date +"%m/%d/%Y %H:%M:%S"`
  if [ $# -lt 2 ] ; then
    echo 'Bad Log Args!'
  fi
  if [ $1 = "0" ] ; then
    if [ $VERBOSE = "1" ] ; then
      echo "Debug: $2"
    fi
    if [ "X${LOG_FILE}" != "X" ] ; then
      echo "${dat}: Debug: $2" >> $LOG_FILE
    fi
  fi
  if [ $1 = "1" ] ; then
    echo "$2"
    if [ "X${LOG_FILE}" != "X" ] ; then
      echo "${dat}: $2" >> $LOG_FILE
    fi
  fi
  if [ $1 = "2" ] ; then
    echo "Warning: $2" >&2
    if [ "X${LOG_FILE}" != "X" ] ; then
      echo "${dat}: Warning: $2" >> $LOG_FILE
    fi
  fi
  if [ $1 = "3" ] ; then
    echo "Error: $2" >&2
    if [ "X${LOG_FILE}" != "X" ] ; then
      echo "${dat}: Error: $2" >> $LOG_FILE
    fi
  fi
}

# Terminate the script
#   $1 = Message to print
#   $2 = Exit code
die () {
  if [ $# -gt 0 ] ; then 
    echo
    log 3 "${1}"
    echo
  fi
  ex=0
  if [ $# -gt 1 ] ; then
    ex=$2
  fi
  exit $ex
}

# Print out help text for this script
#   No Arguments
usage () {
  log 1 "Usage: ${0} [-help]"
  log 1 "         [-config-file <file>]"
  log 1 "         [-websphere <WebSphere-home-dir>]" 
  log 1 "         [-httpserver <IBM-Http-Server-home-dir>]" 
  log 1 "         [-database <BIGR-database>]"
  log 1 "         [-db-owner <db-owner-schema>]" 
  log 1 "         [-db-password <db-owner-password>]" 
  log 1 "         [-db-ads-password <db-ads-password>]" 
  log 1 "         [-url <host-url>]"
  log 1 "         [-bootstrap-userid   <BIGR-bootstrap-userid>]" 
  log 1 "         [-bootstrap-account  <BIGR-bootstrap-account>]" 
  log 1 "         [-bootstrap-password <BIGR-bootstrap-password>]" 
  log 1 "         [-oracle <oracle-home>]"
  log 1 "         [-email-server <outgoing-smtp>]"
  log 1 "         [-autoStartServicesOnBoot]"
  log 1 "         [-verbose]"
  log 1 "         [-logfile <log-file>]" 
  log 1 ""
  exit 1
}

#################### End Helper Functions.

#################### Parse command-line arguments.

# Print debugging info to the console?  Set this before any calls to "log" or "die".
#
VERBOSE=0

binDir=`dirname $0`
log 0 "Setting binDir to $binDir"

# Define variables that get set during command-line parsing.

while [ ! -z "$1" ];
  do case x"$1" in
    x-websphere) shift; WAS_SETUP_COMMAND_LINE_DIR="${1}/bin" ;;
    x-httpserver) shift; IHS_HOME="${1}" ;;
    x-database) shift; BIGR_DATASOURCE_DB_ARG="${1}" ;;
    x-db-owner) shift; BIGR_DATASOURCE_OWNER_USERNAME_ARG="${1}" ;;
    x-db-password) shift; BIGR_DATASOURCE_OWNER_PASSWORD_ARG="${1}" ;;
    x-db-ads-password) shift; BIGR_DATASOURCE_ADS_PASSWORD_ARG="${1}" ;;
    x-url) shift; HOST_URL_ARG="${1}" ;;
    x-bootstrap-userid) shift; BIGR_BOOTSTRAP_USERID_ARG="${1}" ;;
    x-bootstrap-account) shift; BIGR_BOOTSTRAP_ACCOUNT_ARG="${1}" ;;
    x-bootstrap-password) shift; BIGR_BOOTSTRAP_PASSWORD_ARG="${1}" ;;
    x-oracle) shift; ORACLE_HOME_ARG="${1}" ;;
    x-verbose) VERBOSE=1 ;;
    x-logfile) shift; LOG_FILE="${1}" ;;
    x-email-server) shift; EMAIL_SERVER_ARG="${1}" ;;
    x-autoStartServicesOnBoot) AUTO_START_SERVICES_ON_SYSTEM_START=true ;;
    x-config-file) shift; . ${1} ;;
    x-help) usage ;;
    x-*) echo "Unknown option $1" 1>&2; usage ;;
    *) usage ;;
  esac
  shift
done

# WAS_SETUP_COMMAND_LINE_DIR and IHS_HOME must be set before calling
# setupCmdLine.sh.

log 0 "Done parsing args."

#################### Done parsing command-line arguments.

# Do some platform checking right up front.  We don't support BIGR setups on
# WebSphere in Windows.
#
if (/bin/uname | grep -i cygwin > /dev/null); then
  IS_WINDOWS=true
else
  IS_WINDOWS=false
fi

if [ "Xtrue" = "X${IS_WINDOWS}" ]; then
  die "Setting up a BIGR WebSphere server in Windows is not supported." 1
fi

# Check to see if this script has already been run.  If it has, there
# will be a "local" subdirectory.  We don't want to let this run more
# than once since it could overwrite important files in unexpected ways.
#
if [ -d "${binDir}/local" ]; then
  die "The ${0} script has already been run on this machine.  It cannot be run again." 1
fi

#################### Gather Information.

log 0 "Begin gathering information."

SERVEROS=`uname`
HOSTNAME=`hostname`

BASH=`which bash 2>&1`
if [ ! -f "$BASH" ] ; then
  die 'Cannot find bash!' 1
fi
log 0 "Configuring BIGR for $SERVEROS on $HOSTNAME"
log 0 "Found bash in $BASH"

## Locate WebSphere if not specified

if [ "X${WAS_SETUP_COMMAND_LINE_DIR}" = "X" ] ; then
  d="/opt/IBM/WebSphere/AppServer"
  # Make the user enter the directory
  echo
  echo "Please enter the WebSphere installation directory:"
  echo -n "[${d}] "
  read inp
  echo
  WAS_SETUP_COMMAND_LINE_DIR="${inp}/bin"
  if [ $WAS_SETUP_COMMAND_LINE_DIR = "/bin" ] ; then
    WAS_SETUP_COMMAND_LINE_DIR="${d}/bin"
  fi
fi

if [ "X${WAS_SETUP_COMMAND_LINE_DIR}" = "X" ] ; then
  die "Invalid WebSphere installation directory. Please re-run setup." 1
fi

# Make sure the directory exists
if [ ! -d $WAS_SETUP_COMMAND_LINE_DIR ] ; then
  die "Cannot find WebSphere installation directory. Make sure WebSphere is installed and run setup again." 1
fi

log 0 "Found WebSphere programs in $WAS_SETUP_COMMAND_LINE_DIR"

## Locate IBM Http Server if not specified

if [ "X${IHS_HOME}" = "X" ] ; then
  d="/opt/IBMIHS"
  # Make the user enter the directory
  echo
  echo "Please enter the IBM Http Server installation directory:"
  echo -n "[${d}] "
  read inp
  echo
  IHS_HOME=$inp
  if [ "X${IHS_HOME}" = "X" ] ; then
    IHS_HOME=$d
  fi
fi

if [ "X${IHS_HOME}" = "X" ] ; then
  die "Invalid IBM Http Server installation directory. Please re-run setup." 1
fi

# Make sure the directory exists
if [ ! -d $IHS_HOME ] ; then
  die "Cannot find IBM Http Server installation directory. Make sure that IBM Http Server is installed and run setup again." 1
fi

log 0 "Found IBM Http Server in $IHS_HOME"

## Get Database instance

if [ "X${BIGR_DATASOURCE_DB_ARG}" = "X" ] ; then
  # No default.
  d=""
  # Make the user enter the value
  echo
  echo "Please enter the database name. This should be the"
  echo "ORACLE-INSTANCE-NAME that you used previously when configuring"
  echo "a data source."
  echo -n "[${d}] "
  read BIGR_DATASOURCE_DB_ARG
  echo
  if [ "X${BIGR_DATASOURCE_DB_ARG}" = "X" ] ; then
    BIGR_DATASOURCE_DB_ARG=$d
  fi
fi

if [ "X${BIGR_DATASOURCE_DB_ARG}" = "X" ] ; then
  die "Invalid database. Please re-run setup." 1
fi

log 0 "Database instance is: $BIGR_DATASOURCE_DB_ARG"

## Get Database user

if [ "X${BIGR_DATASOURCE_OWNER_USERNAME_ARG}" = "X" ] ; then
  d="ardais_owner"
  # Make the user enter the value
  echo
  echo "Please enter the schema name. This should be the"
  echo "_owner user for the database that you just specified."
  echo -n "[${d}] "
  read BIGR_DATASOURCE_OWNER_USERNAME_ARG
  echo
  if [ "X${BIGR_DATASOURCE_OWNER_USERNAME_ARG}" = "X" ] ; then
    BIGR_DATASOURCE_OWNER_USERNAME_ARG=$d
  fi
fi

if [ "X${BIGR_DATASOURCE_OWNER_USERNAME_ARG}" = "X" ] ; then
  die "Invalid database owner user. Please re-run setup." 1
fi

log 0 "Database owner user is: $BIGR_DATASOURCE_OWNER_USERNAME_ARG"

## Get Database "owner" password

if [ "X${BIGR_DATASOURCE_OWNER_PASSWORD_ARG}" = "X" ] ; then
  # No default.
  d=""
  # Make the user enter the value
  echo
  echo "Please enter the OWNER database password. This should be the"
  echo "password for the _owner user that you just entered."
  echo -n "[${d}] "
  read BIGR_DATASOURCE_OWNER_PASSWORD_ARG
  echo
  if [ "X${BIGR_DATASOURCE_OWNER_PASSWORD_ARG}" = "X" ] ; then
    BIGR_DATASOURCE_OWNER_PASSWORD_ARG=$d
  fi
fi

if [ "X${BIGR_DATASOURCE_OWNER_PASSWORD_ARG}" = "X" ] ; then
  die "Invalid database owner password. Please re-run setup." 1
fi

## Get Database "ads" password

if [ "X${BIGR_DATASOURCE_ADS_PASSWORD_ARG}" = "X" ] ; then
  # No default.
  d=""
  # Make the user enter the value
  echo
  echo "Please enter the ADS database password. This should be the"
  echo "password for the ADS schema in the database instance that"
  echo "you entered above."
  echo -n "[${d}] "
  read BIGR_DATASOURCE_ADS_PASSWORD_ARG
  echo
  if [ "X${BIGR_DATASOURCE_ADS_PASSWORD_ARG}" = "X" ] ; then
    BIGR_DATASOURCE_ADS_PASSWORD_ARG=$d
  fi
fi

if [ "X${BIGR_DATASOURCE_ADS_PASSWORD_ARG}" = "X" ] ; then
  die "Invalid database ADS password. Please re-run setup." 1
fi

## Get host URL

if [ "X${HOST_URL_ARG}" = "X" ] ; then
  d="https://${HOSTNAME}"
  # Make the user enter the value
  echo
  echo "Please enter the fully qualified host URL for this"
  echo "BIGR installation. Include https:// in the beginning"
  echo "for web servers that have an SSL certificate."
  echo -n "[${d}] "
  read HOST_URL_ARG
  echo
  if [ "X${HOST_URL_ARG}" = "X" ] ; then
    HOST_URL_ARG=$d
  fi
fi

if [ "X${HOST_URL_ARG}" = "X" ] ; then
  die "Invalid host URL. Please re-run setup." 1
fi

log 0 "Host url is: $HOST_URL_ARG"

## Get all bootstrap user info -- userid, account, password
if [ "X${BIGR_BOOTSTRAP_USERID_ARG}" = "X" ] ; then
  d="ituser"
  # Make the user enter the value
  echo
  echo "Please enter the bootstrap userid for this"
  echo "BIGR installation."
  echo -n "[${d}] "
  read BIGR_BOOTSTRAP_USERID_ARG
  echo
  if [ "X${BIGR_BOOTSTRAP_USERID_ARG}" = "X" ] ; then
    BIGR_BOOTSTRAP_USERID_ARG=$d
  fi
fi

if [ "X${BIGR_BOOTSTRAP_USERID_ARG}" = "X" ] ; then
  die "Invalid bootstrap userid. Please re-run setup." 1
fi

log 0 "Bootstrap userid is: $BIGR_BOOTSTRAP_USERID_ARG"

if [ "X${BIGR_BOOTSTRAP_ACCOUNT_ARG}" = "X" ] ; then
  # No default.
  d=""
  # Make the user enter the value
  echo
  echo "Please enter the bootstrap account for this BIGR installation."
  echo -n "[${d}] "
  read BIGR_BOOTSTRAP_ACCOUNT_ARG
  echo
  if [ "X${BIGR_BOOTSTRAP_ACCOUNT_ARG}" = "X" ] ; then
    BIGR_BOOTSTRAP_ACCOUNT_ARG=$d
  fi
fi

if [ "X${BIGR_BOOTSTRAP_ACCOUNT_ARG}" = "X" ] ; then
  die "Invalid bootstrap account. Please re-run setup." 1
fi

log 0 "Bootstrap user account is: $BIGR_BOOTSTRAP_ACCOUNT_ARG"

if [ "X${BIGR_BOOTSTRAP_PASSWORD_ARG}" = "X" ] ; then
  # No default.
  d=""
  # Make the user enter the value
  echo
  echo "Please enter the bootstrap user password for this BIGR installation."
  echo -n "[${d}] "
  read BIGR_BOOTSTRAP_PASSWORD_ARG
  echo
  if [ "X${BIGR_BOOTSTRAP_PASSWORD_ARG}" = "X" ] ; then
    BIGR_BOOTSTRAP_PASSWORD_ARG=$d
  fi
fi

if [ "X${BIGR_BOOTSTRAP_PASSWORD_ARG}" = "X" ] ; then
  die "Invalid bootstrap user password. Please re-run setup." 1
fi

## Get ORACLE_HOME location

if [ "X${ORACLE_HOME_ARG}" = "X" ] ; then
  d="/u01/app/oracle/product/9.2.0.4"
  # Make the user enter the value
  echo
  echo "Please enter the absolute path name for the"
  echo "Oracle Install Directory."
  echo -n "[${d}] "
  read ORACLE_HOME_ARG
  echo
  if [ "X${ORACLE_HOME_ARG}" = "X" ] ; then
    ORACLE_HOME_ARG=$d
  fi
fi

if [ "X${ORACLE_HOME_ARG}" = "X" -o ! -d "${ORACLE_HOME_ARG}" ] ; then
  die "Invalid Oracle Install Directory. Please re-run setup." 1
fi

log 0 "ORACLE_HOME is: $ORACLE_HOME_ARG"

## Get email (SMTP) server address

if [ "X${EMAIL_SERVER_ARG}" = "X" ] ; then
  # No default
  d=""
  # Make the user enter the value
  echo
  echo "Please enter the TCP/IP address or full hostname of the SMTP server"
  echo "that BIGR should use when sending email."
  echo -n "[${d}] "
  read EMAIL_SERVER_ARG
  echo
  if [ "X${EMAIL_SERVER_ARG}" = "X" ] ; then
    EMAIL_SERVER_ARG=$d
  fi
fi

if [ "X${EMAIL_SERVER_ARG}" = "X" ] ; then
  die "Invalid SMTP server name or address. Please re-run setup." 1
fi

log 0 "SMTP server is: $EMAIL_SERVER_ARG"

## Get whether to automatically start web and app servers on system boot

if [ "X${AUTO_START_SERVICES_ON_SYSTEM_START}" = "X" ] ; then
  d="No"
  # Make the user enter the value
  echo
  echo "Do you want to automatically start the web and application servers"
  echo "when the system boots?  Please enter yes or no."
  echo -n "[${d}] "
  read TEMP_ANSWER
  echo
  if [ "X${TEMP_ANSWER}" = "X" ] ; then
    TEMP_ANSWER=$d
  fi
  TEMP_ANSWER=${TEMP_ANSWER:0:1}
  if [ "Xn" = "X${TEMP_ANSWER}" -o "XN" = "X${TEMP_ANSWER}" ]; then
    AUTO_START_SERVICES_ON_SYSTEM_START=false
  elif [ "Xy" = "X${TEMP_ANSWER}" -o "XY" = "X${TEMP_ANSWER}" ]; then
    AUTO_START_SERVICES_ON_SYSTEM_START=true
  else
    die "Invalid auto-start answer. Please re-run setup." 1
  fi
fi

log 0 "Auto-start services on boot is: $AUTO_START_SERVICES_ON_SYSTEM_START"

log 0 "Done gathering information."

#################### Done gathering information.

#################### Run setupCmdLine.sh and begin setup.

log 0 "Running setupCmdLine.sh"
. $binDir/lib/setupCmdLine.sh

mkdir -p "${BIGR_LOCAL_LIB}"
chmod 755 ${BIGR_LOCAL_LIB}
# Create the directory that BIGR places label printing files in.
mkdir -p "${BIGR_LOCAL_LIB}/limsreport"
chmod 755 ${BIGR_LOCAL_LIB}/limsreport
mkdir -p "${BIGR_LOCAL_LIB}/web_images"
chmod 755 "${BIGR_LOCAL_LIB}/web_images"
log 0 "Created ${BIGR_LOCAL_LIB} directory"

mkdir -p "${BIGR_TEMP_DIR}"
chmod 700 ${BIGR_TEMP_DIR}
log 0 "Created ${BIGR_TEMP_DIR} directory"

mkdir -p "${BIGR_LOG_DIR}"
chmod 755 ${BIGR_LOG_DIR}
log 0 "Created ${BIGR_LOG_DIR} directory"

# The release history file accumulates a list of builds that have been
# installed on this machine.  The "touch" command creates it if it doesn't
# already exist.
#
RELEASE_HISTORY_FILE="${BIGR_LOCAL_LIB}/release_history.txt"
touch "${RELEASE_HISTORY_FILE}"
chown root:sys "${RELEASE_HISTORY_FILE}"
chmod 644 "${RELEASE_HISTORY_FILE}"
log 0 "Release history file is ${RELEASE_HISTORY_FILE}"

# Create the local configuration directory and copy default configuration
# files into it.  The local configuration files are:
#
#   - Api-properties-local-edits.txt
#   - log4j-properties-local-edits.txt
#   - preSetupCmdLine.sh
#   - postSetupCmdLine.sh
#   - wsadmin_profile.jacl

log 0 "Creating local configuration directory..."
if [ ! -f "${BIGR_LOCAL_LIB}/Api-properties-local-edits.txt" ]; then
  cp -p "${BIGR_SCRIPT_LIB}/templates/Api-properties-local-edits.txt" "${BIGR_LOCAL_LIB}/Api-properties-local-edits.txt"
fi

# Make some edits to the Api-properties-local-edits file
if [ "X$EMAIL_SERVER_ARG" != "X" ] ; then
  echo "s/^[ 	]*\(api\.mail\.smtp\)[ 	]*=.*/\1=$EMAIL_SERVER_ARG/">>"${BIGR_LOCAL_LIB}/Api-properties-local-edits.txt"
fi

# Set permissions on Api-properties-local-edits
chown root:sys "${BIGR_LOCAL_LIB}/Api-properties-local-edits.txt"
chmod 600 "${BIGR_LOCAL_LIB}/Api-properties-local-edits.txt"

if [ ! -f "${BIGR_LOCAL_LIB}/log4j-properties-local-edits.txt" ]; then
  cp -p "${BIGR_SCRIPT_LIB}/templates/log4j-properties-local-edits.txt" "${BIGR_LOCAL_LIB}/log4j-properties-local-edits.txt"
fi
chown root:sys "${BIGR_LOCAL_LIB}/log4j-properties-local-edits.txt"
chmod 600 "${BIGR_LOCAL_LIB}/log4j-properties-local-edits.txt"

if [ ! -f "${BIGR_LOCAL_LIB}/preSetupCmdLine.sh" ]; then
  cp -p "${BIGR_SCRIPT_LIB}/templates/preSetupCmdLine.sh" "${BIGR_LOCAL_LIB}/preSetupCmdLine.sh"
  cat >> "${BIGR_LOCAL_LIB}/preSetupCmdLine.sh" <<EOF

# The bin directory under the directory where WebSphere is installed.
# This must be the directory that contains WebSphere's version of the
# setupCmdLine.sh script.
#
export WAS_SETUP_COMMAND_LINE_DIR="${WAS_SETUP_COMMAND_LINE_DIR}"

# The directory where IBM HTTP Server is installed.
#
export IHS_HOME="${IHS_HOME}"

EOF
fi
chown root:sys "${BIGR_LOCAL_LIB}/preSetupCmdLine.sh"
chmod 700 "${BIGR_LOCAL_LIB}/preSetupCmdLine.sh"

if [ ! -f "${BIGR_LOCAL_LIB}/postSetupCmdLine.sh" ]; then
  cp -p "${BIGR_SCRIPT_LIB}/templates/postSetupCmdLine.sh" "${BIGR_LOCAL_LIB}/postSetupCmdLine.sh"

  # Oracle related things
  ORACLE_HOME_LINE="export ORACLE_HOME=\"${ORACLE_HOME_ARG}\""
  ORACLE_PATH_LINE="export PATH=\"\$PATH:\${ORACLE_HOME}/bin\""
  LD_LIBRARY_PATH_LINE="export LD_LIBRARY_PATH=\"\${ORACLE_HOME}/lib:/lib:/usr/lib:/usr/local/lib\""
#  LD_ASSUME_KERNEL_LINE="export LD_ASSUME_KERNEL=\"2.4.19\""

  cat >> "${BIGR_LOCAL_LIB}/postSetupCmdLine.sh" <<EOF

# The URL prefix used to access resources on this server.
#
export HOST_URL=${HOST_URL_ARG}

# The userid, account and password for the BIGR bootstrap user.
# Various admin scripts need these.
#
export BIGR_BOOTSTRAP_USERID=$BIGR_BOOTSTRAP_USERID_ARG
export BIGR_BOOTSTRAP_ACCOUNT=$BIGR_BOOTSTRAP_ACCOUNT_ARG
export BIGR_BOOTSTRAP_PASSWORD=$BIGR_BOOTSTRAP_PASSWORD_ARG

# The BIGR database that this server connects to.
# This variable doesn't have a default value so you MUST provide one here.
#
export BIGR_DATASOURCE_DB="${BIGR_DATASOURCE_DB_ARG}"
export ORACLE_SID=\${BIGR_DATASOURCE_DB}

# The name of the BIGR "owner" schema to use on this server.
# This variable doesn't have a default value so you MUST provide one here.
#
export BIGR_DATASOURCE_OWNER_USERNAME="${BIGR_DATASOURCE_OWNER_USERNAME_ARG}"

# The password for the BIGR "owner" schema to use on this server.
# This variable doesn't have a default value so you MUST provide one here.
#
export BIGR_DATASOURCE_OWNER_PASSWORD="${BIGR_DATASOURCE_OWNER_PASSWORD_ARG}"

# The password for the BIGR "ads" schema to use on this server.
# This variable doesn't have a default value so you MUST provide one here.
#
export BIGR_DATASOURCE_ADS_PASSWORD="${BIGR_DATASOURCE_ADS_PASSWORD_ARG}"

# This controls whether BIGR's database code will be installed as part of
# installing BIGR in the dobuild.sh script.  Valid values are true and false.
# This should only be set to false in special situations where database
# code needs to be set up manually in a special way, for example to
# accomodate a replicated database setup.
#
export BIGR_DEPLOY_DB_CODE=true

# Oracle environment variables.
#
${ORACLE_HOME_LINE}
${ORACLE_PATH_LINE}
${LD_LIBRARY_PATH_LINE}
${LD_ASSUME_KERNEL_LINE}

EOF
fi
chown root:sys "${BIGR_LOCAL_LIB}/postSetupCmdLine.sh"
chmod 700 "${BIGR_LOCAL_LIB}/postSetupCmdLine.sh"
log 0 "Created postSetupCmdLine.sh"

if [ ! -f "${BIGR_LOCAL_LIB}/wsadmin_profile.jacl" ]; then
  cp -p "${BIGR_SCRIPT_LIB}/templates/wsadmin_profile.jacl" "${BIGR_LOCAL_LIB}/wsadmin_profile.jacl"
fi
chown root:sys "${BIGR_LOCAL_LIB}/wsadmin_profile.jacl"
chmod 600 "${BIGR_LOCAL_LIB}/wsadmin_profile.jacl"
log 0 "Created wsadmin_profile.jacl"

# Copy an index.html to the web server document root that will redirect to
# the BIGR login page.
#
log 0 "Copying template index.html."
if [ -f "${IHS_HOME}/htdocs/index.html" ]; then
  cp -p "${IHS_HOME}/htdocs/index.html" "${IHS_HOME}/htdocs/index.html.backup`date '+%Y%m%d%H%M%S'`"
fi
cp "${BIGR_SCRIPT_LIB}/templates/index.html.login.jsp" "${IHS_HOME}/htdocs/index.html"

# Put a new cacerts file into the WebSphere Java JRE.  This is needed to get
# requests for web pages over SSL to work correctly, in particular for the
# getwebpage.sh script to be able to access our servers, which have
# Thawte SGC (SuperCert) certificates.  The file that ships with WebSphere
# has some missing and expired certificates on the signing chain for Thawte
# certificates in WAS5.  We first create a backup copy of the original file.
# WAS6 does not have this problem.
#
if [ ${BIGR_WAS_MAJOR_VERSION} -eq 5 ]; then
  CACERTS_DIR="${WAS_HOME}/java/jre/lib/security"
  cp -p "${CACERTS_DIR}/cacerts" "${CACERTS_DIR}/cacerts.backup`date '+%Y%m%d%H%M%S'`"
  cp -p "${BIGR_SCRIPT_LIB}/templates/cacerts.was5" "${CACERTS_DIR}/cacerts" 
  log 0 "Replaced cacerts file.."
fi

# Put the Oracle JDBC driver in the WebSphere library directory.
#
cp -p "${BIGR_SCRIPT_LIB}/templates/ojdbc14.jar" "${WAS_HOME}/lib" 

HTTPD_CONF="${IHS_HOME}/conf/httpd.conf"
HTTPD_CONF_TEMPLATE="${BIGR_SCRIPT_LIB}/templates/httpd.conf-template.was${BIGR_WAS_MAJOR_VERSION}"
HTTPD_CONF_TEMPLATE_TARGET="${IHS_HOME}/conf/httpd.conf-GulfStreamBio-template"
HTTPD_CONF_BACKUP="${HTTPD_CONF}.backup`date '+%Y%m%d%H%M%S'`"
cp -p "${HTTPD_CONF}" "${HTTPD_CONF_BACKUP}"
cp "${HTTPD_CONF_TEMPLATE}" "${HTTPD_CONF_TEMPLATE_TARGET}" 
log 1 ""
log 1 "You will need to supply an appropriate httpd.conf file manually for"
log 1 "the web server.  This setup script has created a backup copy of"
log 1 "the current http.conf file in ${HTTPD_CONF_BACKUP}."
log 1 "It has also placed a template for a new httpd.conf file that supports"
log 1 "BIGR in ${HTTPD_CONF_TEMPLATE_TARGET}."
log 1 "Please see the BIGR installation instructions for details on how to"
log 1 "edit this template and other information related to setting up the"
log 1 "web server."
log 1 ""

# Create the WebSphere and IHS startup and shutdown scripts.  These are
# very basic scripts and don't do things like PID monitoring to detect
# whether a service is already started.

if [ "SunOS" = "${PLATFORM}" ]; then
  RC_FILE_SOURCE_FUNCTIONS_LINE=
  RC_FILE_STATUS_LINE=
elif [ "Linux" = "${PLATFORM}" ]; then
  RC_FILE_SOURCE_FUNCTIONS_LINE='. /etc/init.d/functions'
  RC_FILE_STATUS_LINE='[ "$RETVAL" -eq 0 ] && success $"$prog startup" || failure $"$prog startup"'
fi

IBMHTTPD_RC_FILE="/etc/init.d/ibmhttpd"
if [ -f "${IBMHTTPD_RC_FILE}" ]; then
  IBMHTTPD_RC_BACKUP="${IBMHTTPD_RC_FILE}.backup`date '+%Y%m%d%H%M%S'`"
  cp -p "${IBMHTTPD_RC_FILE}" "${IBMHTTPD_RC_BACKUP}"
  rm -f "${IBMHTTPD_RC_FILE}"
fi

  cat > "${IBMHTTPD_RC_FILE}" <<EOF
#!${BASH}
#
# ibmhttpd  This shell script takes care of starting and stopping
#           the IBM HTTP Server.

${RC_FILE_SOURCE_FUNCTIONS_LINE}

IHS_HOME=${IHS_HOME}

RETVAL=0
prog="IBM HTTP Server"

start() {
  echo -n \$"Starting \$prog: "
  \${IHS_HOME}/bin/apachectl start
  RETVAL=\$?
  ${RC_FILE_STATUS_LINE}
  echo
  return \$RETVAL
}

stop() {
  echo -n \$"Stopping \$prog: "
  \${IHS_HOME}/bin/apachectl stop
  RETVAL=\$?
  ${RC_FILE_STATUS_LINE}
  echo
  return \$RETVAL
}

# See how we were called.
case "\$1" in
  start)
	start
	;;
  stop)
	stop
	;;
  restart|reload)
	stop
	sleep 5
	start
	RETVAL=\$?
	;;
  *)
	echo \$"Usage: \$0 {start|stop|restart}"
	exit 1
esac

exit \$RETVAL
EOF

WAS_RC_FILE="/etc/init.d/gsbwebsphere"
if [ -f "${WAS_RC_FILE}" ]; then
  WAS_RC_BACKUP="${WAS_RC_FILE}.backup`date '+%Y%m%d%H%M%S'`"
  cp -p "${WAS_RC_FILE}" "${WAS_RC_BACKUP}"
  rm -f "${WAS_RC_FILE}"
fi

cat > "${WAS_RC_FILE}" <<EOF
#!${BASH}
#
# gsbwebsphere  This shell script takes care of starting and stopping
#               the HealthCare IT Corporation BIGR WebSphere server.

${RC_FILE_SOURCE_FUNCTIONS_LINE}

BIGR_SCRIPTS=${BIGR_SCRIPTS}

RETVAL=0
prog="GSB WebSphere"

start() {
  echo -n \$"Starting \$prog: "
  \${BIGR_SCRIPTS}/startServer.sh | grep -v '^ADM[^:]*I:' | grep -v startServer.log
  RETVAL=\${PIPESTATUS[0]}
  ${RC_FILE_STATUS_LINE}
  echo
  return \$RETVAL
}

stop() {
  echo -n \$"Stopping \$prog: "
  \${BIGR_SCRIPTS}/stopServer.sh
  RETVAL=\$?
  ${RC_FILE_STATUS_LINE}
  echo
  return \$RETVAL
}

# See how we were called.
case "\$1" in
  start)
	start
	;;
  stop)
	stop
	;;
  restart|reload)
	stop
	sleep 5
	start
	RETVAL=\$?
	;;
  *)
	echo \$"Usage: \$0 {start|stop|restart}"
	exit 1
esac

exit \$RETVAL
EOF

chmod 0744 "${IBMHTTPD_RC_FILE}"
chown root:sys "${IBMHTTPD_RC_FILE}"
chmod 0744 "${WAS_RC_FILE}"
chown root:sys "${WAS_RC_FILE}"
if [ "Xtrue" = "X${AUTO_START_SERVICES_ON_SYSTEM_START}" ]; then
  if [ "SunOS" = "${PLATFORM}" ]; then
    rm -f /etc/rc2.d/S99ibmhttpd
    ln "${IBMHTTPD_RC_FILE}" /etc/rc2.d/S99ibmhttpd
    rm -f /etc/rc2.d/S99gsbwebsphere
    ln "${WAS_RC_FILE}" /etc/rc2.d/S99gsbwebsphere
  elif [ "Linux" = "${PLATFORM}" ]; then
    rm -f /etc/rc3.d/S99ibmhttpd
    ln -s "${IBMHTTPD_RC_FILE}" /etc/rc3.d/S99ibmhttpd
    rm -f /etc/rc5.d/S99ibmhttpd
    ln -s "${IBMHTTPD_RC_FILE}" /etc/rc5.d/S99ibmhttpd
    rm -f /etc/rc3.d/S99gsbwebsphere
    ln -s "${WAS_RC_FILE}" /etc/rc3.d/S99gsbwebsphere
    rm -f /etc/rc5.d/S99gsbwebsphere
    ln -s "${WAS_RC_FILE}" /etc/rc5.d/S99gsbwebsphere
  fi
fi

# Set permissions.  We have to wait until the end to do this since some
# of the files there don't exist until this script is done.
#
chmod 600 ${BIGR_SCRIPTS}/*.*
chmod 600 ${BIGR_SCRIPT_LIB}/*.*
chmod 600 ${BIGR_SCRIPT_LIB}/templates/*
chmod 700 ${BIGR_SCRIPTS}/*.sh
chmod 700 ${BIGR_SCRIPT_LIB}/*.sh
chmod 700 ${BIGR_SCRIPT_LIB}
chmod 700 ${BIGR_SCRIPT_LIB}/templates
chmod 600 ${BIGR_LOCAL_LIB}/*.*
chmod 700 ${BIGR_LOCAL_LIB}/*.sh
chmod 755 ${BIGR_LOCAL_LIB}
chmod 755 ${BIGR_LOCAL_LIB}/limsreport
chmod 700 ${BIGR_TEMP_DIR}
chmod 755 ${BIGR_LOG_DIR}

# Make sure that the Jacl enviroment variables are synchronized.
#
${BIGR_SCRIPTS}/exportEnvToJacl.sh

log 0 "$0 finished."
log 0 ""
