#!/bin/bash --norc
# This script is meant to be invoked only from other scripts in the
# BIGR_SCRIPTS directory and so it doesn't call setupCmdLine to set up
# environment variables (it assumes that they have already been set up by
# the calling script).  If this ever needs to be called directly, it should
# be moved to the BIGR_SCRIPTS directory and have the setupCmdLine code added.
# Because of the way setupCmdLine is written, calling it from a script that
# isn't in BIGR_SCRIPTS will result in environment variables being set
# incorrectly -- see setupCmdLine.sh for details (the comments on the
# BIGR_SCRIPTS variable).
###############################################3333

BIGR_SYSTEM_CONFIG=systemConfiguration.xml

# save the system configuration file
cp ${BIGR_LOCAL_LIB}/${BIGR_SYSTEM_CONFIG} ${BIGR_LOCAL_LIB}/${BIGR_SYSTEM_CONFIG}.save

# create system configuration file using custom xslt transformation
/usr/bin/xsltproc ${BIGR_LOCAL_LIB}/asp-ardais.xsl ${BIGR_LOCAL_LIB}/${BIGR_SYSTEM_CONFIG}.save > ${BIGR_LOCAL_LIB}/${BIGR_SYSTEM_CONFIG}
