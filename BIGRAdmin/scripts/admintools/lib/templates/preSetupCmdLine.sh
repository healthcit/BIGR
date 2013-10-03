#!SHELLMARKER
#
# This script will be executed before the setupCmdLine.sh script sets
# any environment variables except BIGR_SCRIPTS, BIGR_SCRIPT_LIB, 
# BIGR_LOCAL_LIB, IHS_HOME, and WAS_SETUP_COMMAND_LINE_DIR.
# The pre-setup script is useful, for example, if you
# need to override WAS_SETUP_COMMAND_LINE_DIR because WebSphere is
# installed in a different directory than usual on some machine.
#
# Some of the more likely variables to need to be changed in this file
# have lines to set them as comments below.  See the comments in
# setupCmdLine.sh for descriptions of the variables and their possible values.
