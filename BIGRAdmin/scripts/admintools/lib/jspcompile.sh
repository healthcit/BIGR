##!SHELLMARKER

if [ $# != 1 ]; then
  echo "Usage: ${0} <BIGR-build-tag>" 1>&2
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

# This script takes a build tag as a parameter, for example TASHA_3_9_15.
# However, this is just used in the name of the compiled JSP zip file and log
# file, it doesn't actually retrieve a build with that tag from CVS and
# compile it.  The JSPs that are compiled are those that are currently
# installed in the enterprise application named ${BIGR_APPNAME} on server
# ${BIGR_SERVER} in ${WAS_CELL}/${WAS_NODE}.
#
BUILD_REV=${1}

JSP_ZIP_TARGET_DIR=${BIGR_BUILD_TEMP_DIR}
compiledJspBaseName=${BUILD_REV}_compiled_jsps_was${BIGR_WAS_MAJOR_VERSION}_${PLATFORM}
JSP_ZIP_FILE=${JSP_ZIP_TARGET_DIR}/${compiledJspBaseName}.zip
JSP_BUILD_LOG=${BIGR_LOG_DIR}/${compiledJspBaseName}.log

buildFailed () {
  echo ">>>> JSP COMPILATION FAILED.  See ${JSP_BUILD_LOG}." 1>&2
  exit 1
}

DATESTART=`date`

mkdir -p ${JSP_ZIP_TARGET_DIR}

echo "Compiling JSPs.  Check ${JSP_BUILD_LOG} for compilation errors (errors may not be displayed on the console)."

# We send the complete stdout/stderr from the JSP compilation to the log
# file, and we send a subset of that to the stdout (console) to try to give
# the person running the script a sense of whether the compile succeeded or
# failed.  We do this by piping the output through egrep, looking for either
# JSPG0033I/JSPG0168I (the message codes for a successful compile in WAS5/WAS6)
# or codes that end in either an E or W, which is what error and warning codes
# seem to end in.
#
# For WAS6, added -compileToWebInf false and -jspCompileClasspath.
#
if [ ${BIGR_WAS_MAJOR_VERSION} -ge 6 ]; then
  ${WAS_BIN}/JspBatchCompiler.sh -enterpriseapp.name ${BIGR_APPNAME} -server.name ${BIGR_SERVER} -compileToWebInf false -jspCompileClasspath "lib/ivjejb35.jar" -keepgenerated true -verbose false -deprecation false 2>&1 | tee ${JSP_BUILD_LOG} | egrep '^JSPG0168I|^JSPG0033I|^[^:]*[EW]:'
else
  # WAS5...
  ${WAS_BIN}/JspBatchCompiler.sh -enterpriseapp.name ${BIGR_APPNAME} -server.name ${BIGR_SERVER} -keepgenerated true -verbose false -deprecation false 2>&1 | tee ${JSP_BUILD_LOG} | egrep '^JSPG0168I|^JSPG0033I|^[^:]*[EW]:'
fi
if [ "X${PIPESTATUS[0]}" != "X0" ]; then
  buildFailed
fi

echo "Zipping compiled JSPs..."

cd ${BIGR_COMPILED_JSPS_DIR}
zip -qr ${JSP_ZIP_FILE} *

# We also put the compiled JSPs in the director where the build-deployment
# scripts look for deployable builds.
#
mkdir -p ${BIGR_BUILD_DROP_DIR}
mv ${JSP_ZIP_FILE} ${BIGR_BUILD_DROP_DIR}

DATEEND=`date `
echo "Start compile: ${DATESTART}"
echo "End compile: ${DATEEND}"
