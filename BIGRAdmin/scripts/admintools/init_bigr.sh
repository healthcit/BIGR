##!SHELLMARKER
#
# init_bigr.sh
#
# This script calls the populate_tables.sql script and
# BIGR_INITIALIZE_CLEAN_DATABASE stored procedure
# that are used to pre-populate the BIGR database with bootstrap info
# so that system deployment can begin. It also tries to detect whether it
# has already been run to prevent problems that could occur if this was run
# again on a server that was already set up and in use 

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
  log 1 "Usage: ${0}" 
  log 1 "    [-account-location <account-location-string>]" 
  log 1 "    [-brand <brand-string>]" 
  log 1 "    [-company-name <company-name-string>]" 
  log 1 "    [-verbose]" 
  log 1 "    [-logfile <log-file>]" 
  log 1 "    [-help]" 
  log 1 ""
  exit 1
}

#################### End Helper Functions.

#################### Parse command-line arguments.

# Define variables that get set during command-line parsing.

# Print debugging info to the console?
#
VERBOSE=0

log 0 "starting init_bigr.sh"

while [ ! -z "$1" ];
  do case x"$1" in
    x-account-location) shift; BIGR_LOCATION_STRING="${1}" ;;
    x-brand) shift; BIGR_BRAND_STRING="${1}" ;;
    x-company-name) shift; BIGR_COMPANY_STRING="${1}" ;;    
    x-verbose) VERBOSE=1 ;;
    x-logfile) shift; LOG_FILE="${1}" ;;
    x-help) usage ;;
    x-*) echo "Unknown option $1" 1>&2; usage ;;
    *) usage ;;
  esac
  shift
done

log 0 "Done parsing args."

#################### Done parsing command-line arguments.

#################### Gather Information.

log 0 "Begin gathering information."

## Get account location

if [ "X${BIGR_LOCATION_STRING}" = "X" ] ; then
  # No default.
  d=""
  # Make the user enter the name
  echo
  echo "Please enter the location name string"
  echo -n "[${d}] "
  read BIGR_LOCATION_STRING
  echo
  if [ "X${BIGR_LOCATION_STRING}" = "X" ] ; then
    BIGR_LOCATION_STRING=$d
  fi
fi

if [ "X${BIGR_LOCATION_STRING}" = "X" ] ; then
  die "Invalid location string. Please re-run setup." 1
fi

log 0 "location string is : $BIGR_LOCATION_STRING"


## Get brand 

if [ "X${BIGR_BRAND_STRING}" = "X" ] ; then
  # No default.
  d=""
  # Make the user enter the name
  echo
  echo "Please enter the brand name string"
  echo -n "[${d}] "
  read BIGR_BRAND_STRING
  echo
  if [ "X${BIGR_BRAND_STRING}" = "X" ] ; then
    BIGR_BRAND_STRING=$d
  fi
fi

if [ "X${BIGR_BRAND_STRING}" = "X" ] ; then
  die "Invalid brand string. Please re-run setup." 1
fi

log 0 "brand string is : $BIGR_BRAND_STRING"

## Get company 

if [ "X${BIGR_COMPANY_STRING}" = "X" ] ; then
  # No default.
  d=""
  # Make the user enter the name
  echo
  echo "Please enter the company name string"
  echo -n "[${d}] "
  read BIGR_COMPANY_STRING
  echo
  if [ "X${BIGR_COMPANY_STRING}" = "X" ] ; then
    BIGR_COMPANY_STRING=$d
  fi
fi

if [ "X${BIGR_COMPANY_STRING}" = "X" ] ; then
  die "Invalid company string. Please re-run setup." 1
fi

log 0 "company string is : $BIGR_COMPANY_STRING"

log 0 "Done gathering information."

#################### Done gathering information.

#################### Fix scripts and run setupCmdLine.sh

binDir=`dirname $0`
log 0 "Setting binDir to $binDir"

log 0 "Running setupCmdLine.sh"
. $binDir/lib/setupCmdLine.sh

# OK, we have now set up everything 
# We are ready to run the script to populate ARD_OBJECTS, ARTS, ...

# NOTE: we do not want this script to run a second time so we put a check
# in the db procedure to make sure this does not happen.
# the check is to see if there are any records in ARD_OBJECTS
# If there are any, then we assume the tables have been populated; 
# if not, then we can populate the tables.

log 1 ""
log 1 "Initializing BIGR database on ${BIGR_DATASOURCE_OWNER_USERNAME}@${BIGR_DATASOURCE_DB}..."

db=${BIGR_DATASOURCE_DB}
username=${BIGR_DATASOURCE_OWNER_USERNAME}
password=${BIGR_DATASOURCE_OWNER_PASSWORD}
credentials="${username}/${password}"

# Connect to the log directory, since the SQL script we're about to run creates
# its log file in the current working directory.
#
cd "${BIGR_LOG_DIR}"

populateLog="${BIGR_LOG_DIR}/populate_tables.log"

/bin/rm -f "${populateLog}"

$ORACLE_HOME/bin/sqlplus "$credentials@$db" "@${BIGR_SCRIPT_LIB}/populate_tables.sql" "${BIGR_BOOTSTRAP_USERID}" "${BIGR_BOOTSTRAP_ACCOUNT}" "${BIGR_LOCATION_STRING}" "${BIGR_BRAND_STRING}" "${BIGR_COMPANY_STRING}"

if [ "X$?" != "X0" ]; then
  die "Database population failed.  See ${populateLog}." 1
fi

if [ ! -f "${populateLog}" ]; then
  die "Problem populating database tables.  Log file not found: ${populateLog}" 1
fi
if (egrep '^ORA-|ERROR at' "${populateLog}" > /dev/null); then
  die "Database population failed.  See ${populateLog}." 1
fi

log 1 ""
log 1 "Done initializing BIGR database on ${BIGR_DATASOURCE_OWNER_USERNAME}@${BIGR_DATASOURCE_DB}."
