##!SHELLMARKER

if [ $# != 1 ]; then
  echo "Usage: ${0} <compiled-jsp-zip-file>" 1>&2
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

COMPILED_ZIP_FILE=${1}

echo "Unzipping compiled JSPs to ${BIGR_COMPILED_JSPS_DIR}..."

mkdir -p ${BIGR_COMPILED_JSPS_DIR}
cd ${BIGR_COMPILED_JSPS_DIR}
unzip -qo ${COMPILED_ZIP_FILE}
