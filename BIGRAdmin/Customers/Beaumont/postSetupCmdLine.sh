#!/bin/bash --norc
##!SHELLMARKER
#
# This script will be executed after everything else in the setupCmdLine.sh
# script.  It is used to override default environment variable settings
# to make them appropriate for this machine.
#
# Some of the more likely variables to need to be changed in this file
# have lines to set them as comments below.  See the comments in
# setupCmdLine.sh for descriptions of the variables and their possible values.

# The URL prefix used to access resources on this server.
#
export HOST_URL=https://asp-beaumont.ardais.com

# The userid, account and password for the BIGR bootstrap user.
# Various admin scripts need these.
#
export BIGR_BOOTSTRAP_USERID=ituser
export BIGR_BOOTSTRAP_ACCOUNT=GSBio
export BIGR_BOOTSTRAP_PASSWORD=7xNrEiTd

# The BIGR database that this server connects to.
# This variable doesn't have a default value so you MUST provide one here.
#
export BIGR_DATASOURCE_DB="ASPDB1"
export ORACLE_SID=${BIGR_DATASOURCE_DB}

# The name of the BIGR "owner" schema to use on this server.
# This variable doesn't have a default value so you MUST provide one here.
#
export BIGR_DATASOURCE_OWNER_USERNAME="beaumont_owner"

# The password for the BIGR "owner" schema to use on this server.
# This variable doesn't have a default value so you MUST provide one here.
#
export BIGR_DATASOURCE_OWNER_PASSWORD="DajnKgf4"

# The password for the BIGR "ads" schema to use on this server.
# This variable doesn't have a default value so you MUST provide one here.
#
export BIGR_DATASOURCE_ADS_PASSWORD="kMBpC8A8"

# This controls whether BIGR's database code will be installed as part of
# installing BIGR in the dobuild.sh script.  Valid values are true and false.
# This should only be set to false in special situations where database
# code needs to be set up manually in a special way, for example to
# accomodate a replicated database setup.
#
export BIGR_DEPLOY_DB_CODE=true

# Oracle environment variables.
#
export ORACLE_HOME="/usr/lib/oracle/11.1.0.1/client"
export PATH="$PATH:${ORACLE_HOME}/bin"
export LD_LIBRARY_PATH="${ORACLE_HOME}/lib:/lib:/usr/lib:/usr/local/lib"
export LD_ASSUME_KERNEL="2.4.19"

