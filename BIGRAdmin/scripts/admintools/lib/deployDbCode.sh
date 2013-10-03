##!SHELLMARKER

if [ $# != 1 ]; then
  echo "Usage: ${0} <db-code-directory>" 1>&2
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

dbCodeDir=${1}

cd "${dbCodeDir}"

db=${BIGR_DATASOURCE_DB}
username=${BIGR_DATASOURCE_OWNER_USERNAME}
password=${BIGR_DATASOURCE_OWNER_PASSWORD}
credentials="${username}/${password}"
ads_password=${BIGR_DATASOURCE_ADS_PASSWORD}

sqlplus "$credentials@$db" @db_build_main.sql "$credentials" "$db" NONE "$ads_password"

dbDeployLog="${dbCodeDir}/db_build_main.log"
if [ ! -f "${dbDeployLog}" ]; then
  echo "Error: Problem deploying database code.  Deployment log not found: ${dbDeployLog}"
  exit 1
else
  DATETIME=`date +'%Y%m%d%H%M'`
  cp -p "${dbDeployLog}" "${BIGR_LOG_DIR}/deploy_db_code_at_${DATETIME}.log"
fi
if (egrep '^ORA-|ERROR at' "${dbDeployLog}" > /dev/null); then
  echo "Error: Problem deploying database code.  See ${dbDeployLog}."
  exit 1
fi

exit 0
