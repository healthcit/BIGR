#!SHELLMARKER
#
# Create a CVS branch for a BIGR feature.
# It prompts for:
#
#   - A JIRA issue number, without the JIRA project name or dashes, e.g. 123
#   - A build number, e.g. 5_2_0_52
#   - A branch date, in the format YYYYMMDD, e.g. 20070509
#
# It creates the branch against the pre-existing build tag for the specified
# build number, and creates a branch tag name in a standard format
# incorporating the specified information.
#

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
  exit 1
}

#################### End Helper Functions.

#################### Parse command-line arguments.

# Print debugging info to the console?  Set this before any calls to "log" or "die".
#
VERBOSE=0

if [ $# != 0 ]; then
  usage
fi

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

clear
export CVS_RSH=ssh
export CVSROOT=:ext:build@cvs:/opt/cvs/gsbio

echo "Please enter the JIRA issue number, entering just the number: e.g. 123"
# No default.
d=""
echo -n "[${d}]: "
read JIRA_ISSUE
if [ "X${JIRA_ISSUE}" = "X" ] ; then
  JIRA_ISSUE=${d}
fi
echo

if [ "X${JIRA_ISSUE}" = "X" ] ; then
  die "You must specify a JIRA issue number. Please re-run." 1
fi

echo "Please enter the build number to base the branch on: e.g. 5_1_0_49"
# No default.
d=""
echo -n "[${d}]: "
read BUILD_NUM
if [ "X${BUILD_NUM}" = "X" ] ; then
  BUILD_NUM=${d}
fi
echo

if [ "X${BUILD_NUM}" = "X" ] ; then
  die "You must specify a build number. Please re-run." 1
fi

d=`date "+%Y%m%d"`
echo "Please enter the date, YYYYMMDD, like so... ${d}"
echo -n "[${d}]: "
read BRANCH_DATE
if [ "X${BRANCH_DATE}" = "X" ] ; then
  BRANCH_DATE=${d}
fi
echo

if [ "X${BRANCH_DATE}" = "X" ] ; then
  die "You must specify a branch date. Please re-run." 1
fi

BRANCH_AGAINST_TAG="BUILD_${BUILD_NUM}"
BRANCH_TAG_NAME="SWP${JIRA_ISSUE}_${BRANCH_DATE}_${BUILD_NUM}"

echo
echo "This will create branch tag ${BRANCH_TAG_NAME} against the"
echo "pre-existing build tag ${BRANCH_AGAINST_TAG}."
echo
echo "Do you want to continue?  Please enter yes or no."
d="Yes"
# Make the user enter the value
echo -n "[${d}]: "
read TEMP_ANSWER
echo
if [ "X${TEMP_ANSWER}" = "X" ] ; then
  TEMP_ANSWER=${d}
fi
TEMP_ANSWER=${TEMP_ANSWER:0:1}
if [ "Xn" = "X${TEMP_ANSWER}" -o "XN" = "X${TEMP_ANSWER}" ]; then
  CREATE_BRANCH=false
elif [ "Xy" = "X${TEMP_ANSWER}" -o "XY" = "X${TEMP_ANSWER}" ]; then
  CREATE_BRANCH=true
else
  die "Invalid confirmation answer. Please re-run." 1
fi

#################### Done parsing command-line arguments.

if [ "Xfalse" = "X${CREATE_BRANCH}" ]; then
  log 1 "Branch tag NOT created."
  exit 0
fi

cvs rtag -r ${BRANCH_AGAINST_TAG} -b ${BRANCH_TAG_NAME} BIGR BIGRAdmin BIGRBuild BIGRCore BIGRDatabase BIGREjb BIGRServer BIGRWeb Other

echo
echo "Created branch tag ${BRANCH_TAG_NAME} against the"
echo "pre-existing build tag ${BRANCH_AGAINST_TAG}."
echo
echo "The branch was created using the following command, all typed on a single line:"
echo
echo "cvs rtag -r ${BRANCH_AGAINST_TAG} -b ${BRANCH_TAG_NAME} BIGR BIGRAdmin BIGRBuild BIGRCore BIGRDatabase BIGREjb BIGRServer BIGRWeb Other"
echo
