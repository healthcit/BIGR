#!/bin/sh
#
# Unpack the package of administration scripts.
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
  log 1 "         -buildfile <BIGR-build-zip-file>"
  log 1 "         [-targetdir <target-script-dir>]"
  log 1 "         [-help]"
  log 1 ""
  log 1 "<target-script-dir> is the directory to unpack the scripts into."
  log 1 "It defaults to the same directory that unpackScripts.sh is in."
  exit 1
}

#################### End Helper Functions.

#################### Parse command-line arguments.

# This deliberately doesn't run setupCmdLine.sh or depend on any of the
# environment that setupCmdLine.sh setups up, since it sometimes has to
# be run on machines that do not yet have the HealthCare IT scripting
# environment set up.

binDir=`dirname $0`

# Define variables that get set during command-line parsing.

# Print debugging info to the console?
#
VERBOSE=0

BUILD_ZIP_FILE=
NOBOOTSTRAP="false"
UNPACK_TARGET_DIR=

# The -nobootstrap command-line parameter is undocumented in "usage",
# since it is only to be used by this script itself.
#
while [ ! -z "$1" ];
  do case x"$1" in
    x-buildfile) shift; BUILD_ZIP_FILE="${1}" ;;
    x-nobootstrap) NOBOOTSTRAP="true" ;;
    x-targetdir) shift; UNPACK_TARGET_DIR="${1}" ;;
    x-help) usage ;;
    x-*) echo "Unknown option $1" 1>&2; usage ;;
    *) usage ;;
  esac
  shift
done

if [ "X${BUILD_ZIP_FILE}" = "X" ]; then
  usage
fi

if [ ! -f "${BUILD_ZIP_FILE}" ]; then
  die "File does not exist: ${BUILD_ZIP_FILE}" 1
fi

if [ "X${UNPACK_TARGET_DIR}" = "X" ]; then
  UNPACK_TARGET_DIR="${binDir}"
fi

# Expand UNPACK_TARGET_DIR to a full path
#
mkdir -p "${UNPACK_TARGET_DIR}"
savedir=`pwd`
cd "${UNPACK_TARGET_DIR}"
UNPACK_TARGET_DIR=`pwd`
cd "${savedir}"

#################### Done parsing command-line arguments.

#################### Begin bootstrap section.

# First see if we were invoked in "bootstrap" mode (the default).
# Since one of the files that unpackScripts.sh overwrites is
# unpackScripts.sh itself, we're extra careful on how we do the unpacking.
# So, the normal procedure is for the original invocation of this script
# to unpack the version of unpackScripts.sh from BUILD_ZIP_FILE into a
# temporary "bootstrap" directory and run that copy of unpackScripts.sh
# to do the bulk of the work.  When we run that script, we pass it an extra
# parameter (-nobootstrap) to keep it from doing this bootstrap unpacking
# a second time.

if [ "X${NOBOOTSTRAP}" = "Xfalse" ]; then
  boottemp=/tmp/bigrUnpackBootstrap
  /bin/rm -rf "${boottemp}"
  mkdir -p "${boottemp}"

  # The -a flag is important, it makes sure end-of-line conversions are done
  # if necessary.  Without it, some of the scripts don't run correctly.
  # The -j flag tells unzip to ignore the paths stored in the zip file and
  # place the extracted files directly in the target directory.
  #
  unzip -qaj -d "${boottemp}" "${BUILD_ZIP_FILE}" 'admintools/unpackScripts.sh'

  chmod 700 "${boottemp}/unpackScripts.sh"

  # By the time the next command completes, it may have replaced the copy
  # of unpackScripts.sh that launched it.  To avoid confusion involving
  # trying to read commands from a script file that no longer exists,
  # we put the exit command on the same shell line so that once the
  # call to unpackScript.sh below completes no more commands need to be read
  # from the original copy of the script file.
  #
  "${boottemp}/unpackScripts.sh" -nobootstrap -buildfile "${BUILD_ZIP_FILE}" -targetdir "${UNPACK_TARGET_DIR}"; exit $?
fi

#################### End of bootstrap section.

# If we get here, we're no longer bootstrapping and we're ready to do the real
# work of unpacking the scripts.

# First, unpack the files into an empty temporary directory and make some
# 

UNPACK_TEMP_DIR=/tmp/bigrUnpackTemp
UNPACK_TEMP_SCRIPTS_DIR="${UNPACK_TEMP_DIR}/admintools"

/bin/rm -rf "${UNPACK_TEMP_DIR}"
mkdir -p "${UNPACK_TEMP_DIR}"

# The -a flag is important, it makes sure end-of-line conversions are done
# if necessary.  Without it, some of the scripts don't run correctly.
#
unzip -qa -d "${UNPACK_TEMP_DIR}" "${BUILD_ZIP_FILE}" 'admintools/*'

savedir=`pwd`
cd "${UNPACK_TEMP_SCRIPTS_DIR}"

# Different operating systems have bash in different places.
# We replace the string SHELLMARKER in our various scripts with
# the actual location, and also insert some standard shell
# options.  SHELLMARKER is intended to be used only in the first
# line of shell scripts where the shell to use to execute that
# script is declared.
#
oldCmd="SHELLMARKER"
newCmd="`which bash|sed -e 's/\\//\\\\\//g'` --norc"
for x in `find . -name '*.sh'` ; do  
  if [ "$x" != "./setup.sh" -a "$x" != "./unpackScripts.sh" ] ; then
    cp "$x" "$x.bak"
    # The following sed script replaces oldCmd with newCmd, but keeps
    # a commented-out copy of the template line (with the SHELLMARKER in it)
    # to make it easier for us to keep track of which files were generated
    # from templates by replacing template markers.
    sed -e "{
h
s/${oldCmd}/__EdItEd__${newCmd}/g
}
/__EdItEd__/ {
s/__EdItEd__//g
p
x
s/.*/#&/
}" <"$x" >"$x.n"
    mv -f "$x.n" "$x" 
    chmod 700 "$x"
    /bin/rm -f "$x.bak"
  fi
done

# Replace markers in the wsadmin.properties file with the correct directory
# names for this installation.
#
x=${UNPACK_TEMP_SCRIPTS_DIR}/lib/wsadmin.properties
log 0 "Editing $x"
escapedBIGRScriptLib=`echo ${UNPACK_TARGET_DIR}/lib|sed -e 's/\\//\\\\\//g'`
escapedBIGRLocalLib=`echo ${UNPACK_TARGET_DIR}/local|sed -e 's/\\//\\\\\//g'`
cp "$x" "$x.bak"  
# The following sed script replaces markers with their replacements, but
# keeps a commented-out copy of the template line (with the MARKER in it)
# to make it easier for us to keep track of which files were generated
# from templates by replacing template markers.
sed -e "{
h
s/BIGRSCRIPTLIBMARKER/__EdItEd__${escapedBIGRScriptLib}/g
s/BIGRLOCALLIBMARKER/__EdItEd__${escapedBIGRLocalLib}/g
}
/__EdItEd__/ {
s/__EdItEd__//g
p
x
s/.*/#&/
}" <"$x" >"$x.n"
mv -f "$x.n" "$x"
chmod 600 "$x"
/bin/rm -f "$x.bak"

# Next, clean up files from the target directory before moving the new files
# there.  We try to be safe about this, only removing files if we've
# first done some checking to make sure that the target directory looks like
# a directory that already has a previous version of the scripts in it.  If
# it doesn't, we assume that we're unpacking into a folder that didn't
# previously have the scripts in it (as we would be doing when unpacking
# scripts prior to running setup.sh for a new installation), and don't remove
# anything to avoid damage that could occur if someone supplied an incorrect
# target directory.

if [ -d "${UNPACK_TARGET_DIR}/lib" -a -d "${UNPACK_TARGET_DIR}/lib/templates" -a -f "${UNPACK_TARGET_DIR}/lib/setupCmdLine.sh" ]; then
  /bin/rm -f ${UNPACK_TARGET_DIR}/*.sh
  /bin/rm -rf ${UNPACK_TARGET_DIR}/lib
fi

# Now copy the new files from the temporary directory to the target directory.
#
cp -rf ${UNPACK_TEMP_SCRIPTS_DIR}/* "${UNPACK_TARGET_DIR}"

# Set permissions.
#
chmod 600 ${UNPACK_TARGET_DIR}/*.*
chmod 600 ${UNPACK_TARGET_DIR}/lib/*.*
chmod 600 ${UNPACK_TARGET_DIR}/lib/templates/*
chmod 700 ${UNPACK_TARGET_DIR}/*.sh
chmod 700 ${UNPACK_TARGET_DIR}/lib/*.sh
chmod 700 ${UNPACK_TARGET_DIR}/lib
chmod 700 ${UNPACK_TARGET_DIR}/lib/templates

# Make sure that the Jacl enviroment variables are synchronized.  However,
# this should only be done in a server that is already set up (one where
# setup.sh has already created and configured the full scripting environment).
# Sometime we're unpacking files in preparation for running setup.sh.  We
# do this by looking for a subdirectory that setup.sh creates.
#
if [ -d "${UNPACK_TARGET_DIR}/local" ]; then
  ${UNPACK_TARGET_DIR}/exportEnvToJacl.sh
fi

# Write the BIGR build tag of the version of the scripts that we've just
# unpacked to a file so that we can know in other scripts what version we're
# using.  Using our standard naming convention for BIGR build zip files, the
# version tag is the base name of the zip file.  For example, the build
# zip file for BIGR build CARA_5_1_0_36 is by convention named
# CARA_5_1_0_36.zip.
#
BUILD=`basename ${BUILD_ZIP_FILE} .zip`
bigrAdmintoolsVersionFile="${UNPACK_TARGET_DIR}/lib/admintoolsVersion.txt"
/bin/rm -f "$bigrAdmintoolsVersionFile"
echo "${BUILD}" > "$bigrAdmintoolsVersionFile"
chmod 744 "$bigrAdmintoolsVersionFile"

cd "${savedir}"

# Clean up temporary files.
#
/bin/rm -rf "${UNPACK_TEMP_DIR}"

exit 0
