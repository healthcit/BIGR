##!SHELLMARKER

if [ $# != 2 ]; then
  echo "Usage: ${0} <BIGR-build-tag> <SVN-branch>" 1>&2
  exit 1
fi

# This script is meant to be invoked only from other scripts in the
# BIGR_SCRIPTS directory and so it doesn't call setupCmdLine to set up
# environment variables (it assumes that they have already been set up by
# the calling script).  If this ever needs to be called directly, it should
# be moved to the BIGR_SCRIPTS directory and have the setupCmdLine code added.
# Because of the way setupCmdLine is written, calling it from a script that
# isn't in BIGR_SCRIPTS will result in environment variables being set
# incorrectly -- see setupCmdLine.sh for details (the comments on the
# BIGR_SCRIPTS variable).

# Variables for Branch Tags and Build Revisions.  If you change these
# variable names, please be aware that BIGR's build.xml file in SVN
# references these variables.
#
export BUILD_REV=${1}
export BRANCH=${2}

export ANT_HOME=/usr/local/ant
ANT=${ANT_HOME}/bin/ant
export ANT_OPTS="-mx256m -Xss3m"

# SOURCES_DIR is where we check out files from version control to
SOURCES_DIR=${BIGR_BUILD_TEMP_DIR}/SVN
# ZIP_DIR is where we create the build zip files.
ZIP_DIR=${BIGR_BUILD_TEMP_DIR}

export CVS_RSH=ssh
export CVSROOT=:ext:bigrbuild@cvs:/opt/cvs/gsbio
export SVNREPO=https://svn.healthcit.com/repo

MAIN_BUILD_LOG=${BIGR_LOG_DIR}/${BUILD_REV}.txt
JAVADOC_BUILD_LOG=${BIGR_LOG_DIR}/${BUILD_REV}_javadoc_log.txt

buildFailed () {
  echo ">>>> **** BUILD FAILED.  See ${MAIN_BUILD_LOG}." 1>&2
  exit 1
}

doSvnCheckout() {
	echo "Checking out ${1}..."
    rm -rf ${1}
	svnCheckoutPath=${SVNREPO}/BIGR/tags/${BUILD_REV}/${1}
      
    svn checkout ${svnCheckoutPath} ${1} | tee -a $MAIN_BUILD_LOG
	
	if [ "X${PIPESTATUS[0]}" != "X0" ]; then
		exit ${PIPESTATUS[0]}
	fi
}

echo "[`date`]: Building from ${BRANCH}: ${BUILD_REV}"

if [ ! -e "${SOURCES_DIR}" ] ; then
  # We simply test for existence of SOURCES_DIR before creating it, rather than
  # explicitly testing that it is a directory, since in some configurations
  # it is a symbolic link to a different directory.
  #
  mkdir -p ${SOURCES_DIR}
fi
mkdir -p ${ZIP_DIR}

#echo "Updating BIGRBuild directory..."

#cd ${SOURCES_DIR}
#rm -rf ${SOURCES_DIR}/BIGRBuild
#svn checkout ${SVNREPO}/BIGR/tags/${BUILD_REV} .
#if [ "X$?" != "X0" ]; then
#  buildFailed
#fi

echo "Purging BIGRBuild Output Directory and Module Directories..."

if [ -e "${SOURCES_DIR}/BIGRBuild" ] ; then
   cd ${SOURCES_DIR}/BIGRBuild
   ${ANT} clean > $MAIN_BUILD_LOG 2>&1
   if [ "X$?" != "X0" ]; then
     buildFailed
   fi
fi

echo "Output directory purged, starting Ant build.  Check $MAIN_BUILD_LOG for status."

echo "Checking out and tagging code in branch ${BRANCH} for ${BUILD_REV} build..."

# cd ${SOURCES_DIR}/BIGRBuild
# ${ANT} cvsPrep >> $MAIN_BUILD_LOG 2>&1

cd ${SOURCES_DIR}
for ckoutProject in BIGR BIGRAdmin BIGRCore BIGRDatabase BIGREjb BIGRServer BIGRWeb BIGRBuild
do
	doSvnCheckout ${ckoutProject}
	if [ "X$?" != "X0" ]; then
	  buildFailed
	fi
done

echo "Code tagged as ${BUILD_REV} from branch ${BRANCH}"

echo "[`date`]: Starting BIGR Ant build..."
cd ${SOURCES_DIR}/BIGRBuild
${ANT} 2>&1 | tee -a $MAIN_BUILD_LOG
if [ "X${PIPESTATUS[0]}" != "X0" ]; then
  buildFailed
fi

echo "[`date`]: Code build complete."

echo "Zipping BIGR.ear, db_code, and admintools files..."

rm -rf ${ZIP_DIR}/bigr
mkdir -p ${ZIP_DIR}/bigr/db_code
rm -rf ${ZIP_DIR}/installableApps
mkdir -p ${ZIP_DIR}/installableApps
rm -rf ${ZIP_DIR}/admintools
mkdir -p ${ZIP_DIR}/admintools

cp ${SOURCES_DIR}/BIGRBuild/output/BIGR.ear ${ZIP_DIR}/installableApps

cp ${SOURCES_DIR}/BIGRAdmin/config/*.* ${ZIP_DIR}/bigr

cp ${SOURCES_DIR}/BIGRDatabase/DBCode/Functions/*.FNC ${ZIP_DIR}/bigr/db_code
cp ${SOURCES_DIR}/BIGRDatabase/DBCode/Global/*.sql ${ZIP_DIR}/bigr/db_code
cp ${SOURCES_DIR}/BIGRDatabase/DBCode/Packages/*.pkg ${ZIP_DIR}/bigr/db_code
cp ${SOURCES_DIR}/BIGRDatabase/DBCode/Procedures/*.PRC ${ZIP_DIR}/bigr/db_code
cp ${SOURCES_DIR}/BIGRDatabase/DBCode/Triggers/*.TRG ${ZIP_DIR}/bigr/db_code
cp ${SOURCES_DIR}/BIGRDatabase/DBCode/Views/*.sql ${ZIP_DIR}/bigr/db_code
cp ${SOURCES_DIR}/BIGRDatabase/SchemaCreateScripts/*.sql ${ZIP_DIR}/bigr/db_code

cp -r ${SOURCES_DIR}/BIGRAdmin/scripts/admintools ${ZIP_DIR}
#find ${ZIP_DIR}/admintools -name 'CVS' -type d -exec rm -rf '{}' \; > /dev/null 2>&1

# Create the build ZIP file and copy it to the directory where the main
# build install script expects to find installable build zip files.
#
cd ${ZIP_DIR}
zip -qr ${BUILD_REV}.zip bigr installableApps admintools
mkdir -p ${BIGR_BUILD_DROP_DIR}
cp -p ${BUILD_REV}.zip ${BIGR_BUILD_DROP_DIR}

echo "[`date`]: Zip file complete."

# The javadoc build output itself all goes to JAVADOC_BUILD_LOG.
# We don't stop the main build with a failure if the javadoc build has problems,
# but we do report it to the user.
#
echo "Starting BIGR Javadoc Ant build for ${BUILD_REV} in branch ${BRANCH}..."
cd ${SOURCES_DIR}/BIGRBuild
${ANT} javadoc > $JAVADOC_BUILD_LOG 2>&1
if [ "X$?" != "X0" ]; then
  echo "[`date`]: **** BUILD FAILED for BIGR Javadoc Ant build for ${BUILD_REV} in branch ${BRANCH}.  See ${JAVADOC_BUILD_LOG}." 1>&2
else
  cp $JAVADOC_BUILD_LOG ${SOURCES_DIR}/BIGRBuild/output/javadoc
  echo "[`date`]: Finished BIGR Javadoc Ant build for ${BUILD_REV} in branch ${BRANCH}.  To see whether it succeeded, check ${JAVADOC_BUILD_LOG}."
fi

echo "[`date`]: Done building in branch ${BRANCH}: ${BUILD_REV}"
