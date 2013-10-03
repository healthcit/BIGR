##!SHELLMARKER

# This script is meant to be invoked only from other scripts in the
# BIGR_SCRIPTS directory and so it doesn't call setupCmdLine to set up
# environment variables (it assumes that they have already been set up by
# the calling script).  If this ever needs to be called directly, it should
# be moved to the BIGR_SCRIPTS directory and have the setupCmdLine code added.
# Because of the way setupCmdLine is written, calling it from a script that
# isn't in BIGR_SCRIPTS will result in environment variables being set
# incorrectly -- see setupCmdLine.sh for details (the comments on the
# BIGR_SCRIPTS variable).

#################### Parse command-line arguments.

usage () {
  echo "Usage: `basename ${0}` [-noinstall] [-build [-branch <SVN-branch>]] <BIGR-build-tag>" 1>&2
  exit 1
}

# Define variables that get set during command-line parsing.

# By default, we install a build (into the WebSphere that is running on
# this machine).
#
DO_INSTALL=true

# By default, we don't create a new build.
#
DO_BUILD=false

# The variable BUILD represents the BIGR-build-tag from the command line.
#
BUILD=

# When doing a build (DO_BUILD=true), BUILD_BRANCH defaults to the trunk.
#
BUILD_BRANCH=trunk

while [ ! -z "$1" ];
  do case x"$1" in
    x-noinstall) DO_INSTALL=false ;;
    x-build) DO_BUILD=true ;;
    x-branch) shift; BUILD_BRANCH=$1 ;;
    x-*) echo "Unknown option $1" 1>&2; usage ;;
    *) break ;;
  esac
  shift
done

if [ $# != 1 ]; then
  echo "Missing BIGR-build-tag." 1>&2; usage;
fi

# The BIGR-build-tag argument is supposed to just be a SVN build tag name,
# but if someone specifies a build ZIP file instead we accept it, stripping
# the ".zip" suffix off to get the build tag.
#
BUILD=`basename ${1} .zip`

#################### Done parsing command-line arguments.

#################### Function definitions

# Do a little checking for common problems related to interacting with Oracle.
#
doDatabaseChecks () {
  tnsFile=${ORACLE_HOME}/network/admin/tnsnames.ora
  ldapFile=${ORACLE_HOME}/network/admin/ldap.ora
  
  # Some installations use ldap for database authentication, in which
  # case the tnsnames checks we do here should be skipped.
  #
  if [ ! -f "${ldapFile}" ]; then
	  if [ ! -f "${tnsFile}" ]; then
	    echo "Error: $tnsFile does not exist."
	    exit 1
	  fi
	  if [ ! -r "${tnsFile}" ]; then
	    echo "Error: $tnsFile is not readable."
	    exit 1
	  fi
	
	  # Make sure that the end-of-lines are Unix-style, Oracle is unhappy with
	  # Windows-style.
	  #
	  if [ "Linux" = "${PLATFORM}" ]; then
	    dos2unix --keepdate --quiet "${tnsFile}"
	  elif [ "SunOS" = "${PLATFORM}" ]; then
	    dos2unix -ascii "${tnsFile}" "${tnsFile}" > /dev/null 2>&1
	  fi
	
	  # Make sure that BIGR_DATASOURCE_DB appears in tnsnames.ora.  This isn't
	  # a perfect check (the string could appear and still not be defined
	  # from Oracle's perspective, for example it could be commented out), but
	  # it is better than nothing.
	  #
	  if (! (grep "${BIGR_DATASOURCE_DB}" "${tnsFile}" > /dev/null)); then
	    echo "Error: ${BIGR_DATASOURCE_DB} not found in $tnsFile."
	    exit 1
	  fi
	fi
}

# Install a build into WebSphere on this machine.
#
doInstall () {
  echo "[`date`]: Starting to install build ${BUILD}."

  # Stop the BIGR Enterprise Application
  #
  echo "Stopping the BIGR Enterprise Application..."
  ${BIGR_SCRIPTS}/stopBIGR.sh

  # Uninstall the BIGR Enterprise Application
  #
  echo "Uninstalling the BIGR Enterprise Application..."
  echo "WAS_BIN: ${WAS_BIN}"
  ${WAS_BIN}/wsadmin.sh -f ${BIGR_SCRIPT_LIB}/uninstallBIGR.jacl

  # Delete logs, compiled JSPs, db_code files, and so forth.
  #
  echo "Removing logs..."
  # Logs and db_code were placed into BIGR_CONFIG_DIR at one time.  They
  # are now placed under BIGR_LOG_DIR and BIGR_TEMP_DIR.  To facilitate
  # migration, we clean up both new and old locations here.  The lines
  # the delete logs and db_code from BIGR_CONFIG_DIR can be removed once
  # all systems have been migrated to the new scripts.
  #
  /bin/rm -f ${BIGR_LOG_DIR}/*.log
  /bin/rm -f ${BIGR_CONFIG_DIR}/*.log
  /bin/rm -rf ${BIGR_WAS_TEMP_DIR}
  /bin/rm -rf ${BIGR_CONFIG_DIR}/db_code
  /bin/rm -rf ${BIGR_TEMP_DIR}/db_code
  /bin/rm -rf ${BIGR_TEMP_DIR}/tempbigr

  # Unzip the build file.  Different parts go into different places (e.g.
  # the installable EAR file vs. BIGR config files and deployable database
  # code).
  #
  echo "Unpacking build ${BUILD}..."
  if [ ${BIGR_WAS_MAJOR_VERSION} -ge 6 ]; then
    installableAppsParentDir=${WAS_HOME}/profiles/AppSrv01
  else
    # WAS5...
    installableAppsParentDir=${WAS_HOME}
  fi
  bigrInstallableEarFile=${installableAppsParentDir}/installableApps/${BIGR_APPNAME}.ear
  unzip -qao -d "${installableAppsParentDir}" "${BUILD_ZIP_FILE}" 'installableApps/*'
  mkdir -p "${BIGR_TEMP_DIR}/tempbigr"
  unzip -qa -d "${BIGR_TEMP_DIR}/tempbigr" "${BUILD_ZIP_FILE}" 'bigr/*'
  mv ${BIGR_TEMP_DIR}/tempbigr/bigr/* "${BIGR_CONFIG_DIR}"
  mv "${BIGR_CONFIG_DIR}/db_code" "${BIGR_TEMP_DIR}"
  /bin/rm -rf "${BIGR_TEMP_DIR}/tempbigr"

  # Deploy the database code
  #
  if [ "Xtrue" = "X$BIGR_DEPLOY_DB_CODE" ]; then
    echo "Deploying database code to ${BIGR_DATASOURCE_OWNER_USERNAME}@${BIGR_DATASOURCE_DB}..."
    ${BIGR_SCRIPT_LIB}/deployDbCode.sh "${BIGR_TEMP_DIR}/db_code"
    if [ "X$?" != "X0" ]; then
      echo "**** BUILD FAILED.  Aborting." 1>&2
      exit 1
    fi
  else
    echo ">>>> Database code is NOT being deployed (BIGR_DEPLOY_DB_CODE != \"true\")."
  fi
  /bin/rm -rf "${BIGR_TEMP_DIR}/db_code"

  # Edit properties files such as Api.properties to make them appropriate
  # for the local machine.
  #
  echo "Editing properties files..."
  ${WAS_BIN}/wsadmin.sh -f ${BIGR_SCRIPT_LIB}/editProperties.jacl
  
  # Edit systemConfiguration.xml for local environment, if the script
  #   for editing has been added to the local environment
  if [ -f ${BIGR_LOCAL_LIB}/${EDIT_SYSTEM_CONFIG} ]; then
  	echo "Found a version of ${EDIT_SYSTEM_CONFIG}, invoking it..."
  	${BIGR_LOCAL_LIB}/${EDIT_SYSTEM_CONFIG}
  fi

  # Install the BIGR Enterprise Application
  #
  echo "Installing the BIGR Enterprise Application..."
  ${WAS_BIN}/wsadmin.sh -f ${BIGR_SCRIPT_LIB}/installBIGR.jacl ${bigrInstallableEarFile}

  # Install precompiled JSPs.  We first look for a zip file containing the
  # precompiled JSPs for the correct platform in the same directory as
  # the main build zip file is in, and if we find it we just install the
  # files from there.  Otherwise we call the JSP precompiler.
  #
  if [ -f ${JSP_ZIP_FILE} ]; then
    ${BIGR_SCRIPT_LIB}/unzip_compiled_jsps.sh ${JSP_ZIP_FILE}
  else
    # Precompile the JSP pages and create a platform-specific ZIP file of the
    # results.  The ZIP file can be unpacked onto other machines of the same
    # platform using the ${BIGR_SCRIPTS}/unzip_compiled_jsps.sh script,
    # this script does above.
    #
    ${BIGR_SCRIPT_LIB}/jspcompile.sh ${BUILD}
    if [ "X$?" != "X0" ]; then
      echo "**** BUILD FAILED.  Aborting." 1>&2
      exit 1
    fi
  fi

  # Regenerate the web server plug-in
  #
  echo "Regenerating the web server plugin..."
  if [ ${BIGR_WAS_MAJOR_VERSION} -ge 6 ]; then
    ${WAS_BIN}/GenPluginCfg.sh -node.name ${BIGR_WEB_SERVER_NODE} -webserver.name ${BIGR_WEB_SERVER_NAME}
  else
    # WAS5...
    ${WAS_BIN}/GenPluginCfg.sh
  fi

  echo "Restarting the web server..."
  # The sleep is necessary, sometimes the stop command returns before the
  # processes are actually dead and the pid file removed, so that the start
  # command thinks it is still running when in fact it is shutting down.
  #
  ${IHS_HOME}/bin/apachectl stop
  sleep 5
  ${IHS_HOME}/bin/apachectl start

  # Generate build.html and release.html
  #
  echo "Generating build.html and release.html..."

  # Need date to include in the HTML files we're generating here.
  DATEEND=`date`
  
  # Include DET version from gboss.xml in build.html
  DETVERSION=$(grep "Version: DET" ${BIGR_LOCAL_LIB}/gboss.xml)
  DETVERSION=${DETVERSION:14:5}
  
  TARGET_DIR=${BIGR_INSTALLED_EAR_DIR}/BIGRWeb.war

  /bin/rm -f ${TARGET_DIR}/build.html

  cat > ${TARGET_DIR}/build.html <<EOF
  <html>
  <head>
  <meta http-equiv="Pragma" CONTENT="no-cache">
  <meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate">
  <meta http-equiv="max-age" CONTENT="0">
  <meta http-equiv="Expires" CONTENT="0">
  <title>${WAS_NODE} ${BIGR_APPNAME} Build and Patch Revision</title>
  </head>
  <body>
   ${BUILD}
   ${DETVERSION}
   ${DATEEND}
  </body>
  </html>
EOF

  echo "${BUILD} ${DATEEND}" >> ${BIGR_LOCAL_LIB}/release_history.txt

  /bin/rm -f ${TARGET_DIR}/release.html

  cat > ${TARGET_DIR}/release.html <<EOF
  <html>
  <head>
  <meta http-equiv="Pragma" CONTENT="no-cache">
  <meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate">
  <meta http-equiv="max-age" CONTENT="0">
  <meta http-equiv="Expires" CONTENT="0">
  <title>${WAS_NODE} ${BIGR_APPNAME} Build and Patch Revision List</title>
  </head>
  <body>
  <PRE>
EOF

  cat ${BIGR_LOCAL_LIB}/release_history.txt >> ${TARGET_DIR}/release.html

  cat >> ${TARGET_DIR}/release.html <<EOF
  </PRE>
  </body>
  </html>
EOF

  # Set some permissions
  #
  echo "Setting permissions..."
  chmod 600 ${BIGR_CONFIG_DIR}/Api.properties
  # check to see if there is a lib/web_images dir
  # if so, copy all those images over to the webserver's images dir.
  if [ -d ${BIGR_LOCAL_LIB}/web_images ] ; then
    echo "Copying webserver images..."
    if [ `ls ${BIGR_LOCAL_LIB}/web_images|wc -l` -ne '0' ]; then
      cp -f ${BIGR_LOCAL_LIB}/web_images/* ${BIGR_INSTALLED_EAR_DIR}/BIGRWeb.war/images/
    fi
  fi

  # Stop and restart the application server.  Formerly this just restarted the
  # BIGR enterprise application, but we found that we often got exceptions
  # during the restart, so now we restart the entire application server instead.
  # Since stopServer sometimes fails to actually stop things, we also print
  # out the running Java processes that are still around after stopServer.
  # There shouldn't be any that correspond to WebSphere server processes.
  # The sleep is there to make sure the java processes have time to really
  # die before we do the "ps -ef".
  #
  echo "Stopping WebSphere..."
  ${BIGR_SCRIPTS}/stopServer.sh
  sleep 5
  echo "=================================================================="
  echo "Current Java processes.  There should NOT still be any WebSphere"
  echo "Java processes in the list.  If there are, it means that WebSphere"
  echo "didn't stop correctly and it will need to be stopped manually and"
  echo "then restarted."
  echo "------------------------------------------------------------------"
  ps -ef | grep java | grep -v grep
  echo "=================================================================="
  echo "Starting WebSphere..."
  ${BIGR_SCRIPTS}/startServer.sh

  echo "[`date`]: Done installing build ${BUILD}."
}
# End of function doInstall

#################### End of function definitions.

BUILD_ZIP_FILE=${BIGR_BUILD_DROP_DIR}/${BUILD}.zip
JSP_ZIP_FILE=${BIGR_BUILD_DROP_DIR}/${BUILD}_compiled_jsps_was${BIGR_WAS_MAJOR_VERSION}_${PLATFORM}.zip

# If we're installing and not creating a new build, then the build zip
# file must already exist and be in the correct directory.
#
if [ "Xtrue" = "X$DO_INSTALL" -a "Xfalse" = "X$DO_BUILD" ]; then
  if [ ! -f ${BUILD_ZIP_FILE} ]; then
    echo "Build file does not exist: ${BUILD_ZIP_FILE}" 1>&2; usage;
  fi

  if [ "X${BUILD}" != "X${BIGR_CURRENTLY_INSTALLED_ADMINTOOLS_VERSION:-}" ]; then
    echo "" 1>&2
    echo "**** ERROR ****" 1>&2
    echo "You must install the administrative tools for ${BUILD}" 1>&2
    echo "before you can install that version of BIGR.  You can do this" 1>&2
    echo "using the following command:" 1>&2
    echo "${BIGR_SCRIPTS}/unpackScripts.sh -buildfile \"${BUILD_ZIP_FILE}\"" 1>&2
    echo "" 1>&2
    exit 1
  fi
fi

# Do a little checking for common problems related to interacting with Oracle.
#
if [ "Xtrue" = "X$DO_INSTALL" -a "Xtrue" = "X$BIGR_DEPLOY_DB_CODE" ]; then
  doDatabaseChecks
fi

# Summarize what we'll be doing.
#
echo ""
echo "Summary of actions to be performed:"
if [ "Xfalse" = "X$DO_BUILD" ]; then
  echo "  - No new build will be created."
else
  echo "  - Will create build \"${BUILD}\" in SVN branch \"${BUILD_BRANCH}\"."
fi
if [ "Xfalse" = "X$DO_INSTALL" ]; then
  echo "  - No build will be installed on this machine."
else
  echo "  - Will install build \"${BUILD}\" on this machine."
  if [ -f ${JSP_ZIP_FILE} ]; then
    echo "  - Compiled JSPs will be unpacked from an existing ZIP file."
  else
    echo "  - JSP pages will be recompiled because there is no pre-existing ZIP file of compiled JSPs for this platform (${PLATFORM})."
  fi
fi
echo ""

date

DATESTART=`date`

DATE=`date +'%Y%m%d'`

if [ "Xtrue" = "X$DO_BUILD" ]; then
  ${BIGR_SCRIPT_LIB}/build.sh ${BUILD} ${BUILD_BRANCH}
  if [ "X$?" != "X0" ]; then
    echo "**** BUILD FAILED.  No build will be installed." 1>&2
    exit 1
  fi
fi

if [ "Xtrue" = "X$DO_INSTALL" ]; then
  doInstall
fi

if [ "Xtrue" = "X$DO_INSTALL" -a "Xtrue" = "X$DO_BUILD" ]; then
    echo ""
    echo "**** PLEASE NOTE ****"
    echo "To keep your administrative scripts up to date, you should install"
    echo "the administrative tools for ${BUILD} now."
    echo "You can do this using the following command:"
    echo "${BIGR_SCRIPTS}/unpackScripts.sh -buildfile \"${BUILD_ZIP_FILE}\""
    echo ""
fi

DATEEND=`date`
echo "TOTAL START:  $DATESTART "
echo "TOTAL END:  $DATEEND "

echo "dobuild done."
