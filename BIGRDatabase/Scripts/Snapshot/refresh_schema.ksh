#!/usr/bin/ksh
#-----------------------------------------------------------------------
# PROGRAM       refresh_schema.ksh
# USAGE         ./refresh_schema.ksh <ORACLE_SID>_<SCHEMA_NAME>.env
# FUNCTION      Refreshes a schema set from a 10g datapump export
#         
# CALLED BY     user or crontab(for automated refreshes)
#
# NOTES         the parfile is the only requried argument for this script 
#               the basic steps are found at the bottom of this script in
#                   the form of function calls
#
#
# AUTHOR        Phil DiPrima
# Date          Mar 2007
#-----------------------------------------------------------------------
# Source Oracle env
. /home/oracle/.oracle.profile

#
# set -n # Uncomment to check command syntax without any execution
# set -x # Uncomment to debug this script
#
#-----------------------------------------------------------------------
# Function definitions
#  
#  
#-----------------------------------------------------------------------
#-----------------------------------------------------------------------
# usage
#  Explain how do use the program if user submits wrong parameters
#
#-----------------------------------------------------------------------
function usage {
echo "\n\tUSAGE: $THIS_SCRIPT <file_to_process>\n"
echo
echo "\t<file_to_process> is the parameter parfile that defines the"
echo " \t\tdatabase and users, etc. to refresh."
echo
echo "\tFor example, you may have created a parfile called GSBDB_det13.env"
echo "\tthat refreshes the det13_owner, etc on GSBDB database."
echo "\tcreate or edit the <ORACLE_SID>_<SCHEMA_NAME>.env and run it like"
echo "\tthis: $THIS_SCRIPT GSBDB_det13.env\n "
exit 1
}
#-----------------------------------------------------------------------
# get_time
#  Gets the system clock time.  Just to see how fast it is running
#
#-----------------------------------------------------------------------
function get_time {
TIME=$(date '+%m/%d/%y-%H:%M:%S')
}
#-----------------------------------------------------------------------
# welcome_screen 
#  Displays startup screen
#
#-----------------------------------------------------------------------
function welcome_screen {
# Welcome screen
echo
echo '*************************************************************' 
echo '    The HealthCare IT Corporation Database Refresh schema utiltiy'
echo ''
echo '        © Copyright 2009 HealthCare IT Corporation'
echo '                  www.healthcit.com'
echo ''
echo '*************************************************************'
echo
echo
echo Step / Action
echo --------------------------------------
}
#-----------------------------------------------------------------------
# process_parfile
# Process parfile <ORACLE_SID>_<schema name>.env
#  Cat the file, looking for each know variable and then use sed to Delete all
#  leading blank lines at top of file (only).
#-----------------------------------------------------------------------
function process_parfile {
echo " 1. Processing parfile"

# Get the database to refresh
export ORACLE_SID=`cat $FILENAME | gawk -F "=" '/DATABASE/ {print $2}'|sed '/./,$!d'`
if [[ -z $ORACLE_SID ]];then
    echo "The ORACLE_SID paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# Get the schema prefixs
export DESTINATION_SCHEMA_PREFIX=`cat $FILENAME | gawk -F "=" '/DESTINATION_SCHEMA_PREFIX/ {print $2}'|sed '/./,$!d'`
if [[ -z $DESTINATION_SCHEMA_PREFIX ]];then
    echo "The DESTINATION_SCHEMA_PREFIX paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi
export SOURCE_SCHEMA_PREFIX=`cat $FILENAME | gawk -F "=" '/SOURCE_SCHEMA_PREFIX/ {print $2}'|sed '/./,$!d'`
if [[ -z $SOURCE_SCHEMA_PREFIX ]];then
    echo "The SOURCE_SCHEMA_PREFIX paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# ardais_owner
export THE_OWNE_PWD=`cat $FILENAME | gawk -F "=" '/THE_OWNE_PWD/ {print $2}'|sed '/./,$!d'`
if [[ -z $THE_OWNE_PWD ]];then
    echo "The THE_OWNE_PWD paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# ardais_reader
export THE_READ_PWD=`cat $FILENAME | gawk -F "=" '/THE_READ_PWD/ {print $2}'|sed '/./,$!d'`
if [[ -z $THE_READ_PWD ]];then
    echo "The THE_READ_PWD paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# ardais_util
export THE_UTIL_PWD=`cat $FILENAME | gawk -F "=" '/THE_UTIL_PWD/ {print $2}'|sed '/./,$!d'`
if [[ -z $THE_READ_PWD ]];then
    echo "The THE_READ_PWD paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# ardais_user
export THE_USER_PWD=`cat $FILENAME | gawk -F "=" '/THE_USER_PWD/ {print $2}'|sed '/./,$!d'`
if [[ -z $THE_USER_PWD ]];then
    echo "The THE_USER_PWD paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi
# ardais_deriv
export THE_DERI_PWD=`cat $FILENAME | gawk -F "=" '/THE_DERI_PWD/ {print $2}'|sed '/./,$!d'`
if [[ -z $THE_DERI_PWD ]];then
    echo "The THE_DERI_PWD paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi
# ardais_views
export THE_VIEW_PWD=`cat $FILENAME | gawk -F "=" '/THE_VIEW_PWD/ {print $2}'|sed '/./,$!d'`
if [[ -z $THE_VIEW_PWD ]];then
    echo "The THE_VIEW_PWD paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# ardais_ads_user
export THE_ADSU_PWD=`cat $FILENAME | gawk -F "=" '/THE_ADSU_PWD/ {print $2}'|sed '/./,$!d'`
if [[ -z $THE_ADSU_PWD ]];then
    echo "The THE_ADSU_PWD paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# Oracle system 
export SYSTEM_PWD=`cat $FILENAME | gawk -F "=" '/SYSTEM/ {print $2}'|sed '/./,$!d'`
if [[ -z $SYSTEM_PWD ]];then
    echo "The SYSTEM_PWD paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# The dump file
export THE_EXPORT=`cat $FILENAME | gawk -F "=" '/THE_EXPORT/ {print $2}'|sed '/./,$!d'`
if [[ -z $THE_EXPORT ]];then
    echo "The THE_EXPORT paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# The dump directory
export DATAPUMP_DIR=`cat $FILENAME | gawk -F "=" '/DATAPUMP_DIR/ {print $2}'|sed '/./,$!d'`
if [[ -z $DATAPUMP_DIR ]];then
    echo "The DATAPUMP_DIR paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

# Create a system login
export SYSLOGIN=system/$SYSTEM_PWD@$ORACLE_SID
if [[ -z $SYSLOGIN ]];then
    echo "The SYSLOGIN paramter is missing from $FILENAME.\nexiting..."
    exit 1
fi

echo
echo "    Using $THE_EXPORT as the source file."
echo
}
#-----------------------------------------------------------------------
# drop_users 
#   Drops users "cascade" if they exist.
#-----------------------------------------------------------------------
function drop_users {
echo " 2. Dropping users..."
echo "    ${DESTINATION_SCHEMA_PREFIX}_OWNER..."
x=`sqlplus -s /nolog << EOF | grep '^ORA-' | cut -d: -f1
set pagesize 0 feedback off verify off heading off echo off term off
WHENEVER SQLERROR EXIT 1 ROLLBACK
WHENEVER OSERROR EXIT 1 ROLLBACK
connect $SYSLOGIN
DROP USER ${DESTINATION_SCHEMA_PREFIX}_OWNER CASCADE;
exit sql.sqlcode;
EOF`
if [[ $? -ne 0 ]];then
   echo $x
   echo "Schema ${DESTINATION_SCHEMA_PREFIX}_OWNER was not found on database $ORACLE_SID."
fi

echo " "
echo "    ${DESTINATION_SCHEMA_PREFIX}_READER..."
x=`sqlplus -s /nolog << EOF | grep '^ORA-' | cut -d: -f1
set pagesize 0 feedback off verify off heading off echo off term off
WHENEVER SQLERROR EXIT 1 ROLLBACK
WHENEVER OSERROR EXIT 1 ROLLBACK
connect $SYSLOGIN
DROP USER ${DESTINATION_SCHEMA_PREFIX}_READER CASCADE;
exit sql.sqlcode;
EOF`
if [[ $? -ne 0 ]];then
   echo $x
   echo "Schema ${DESTINATION_SCHEMA_PREFIX}_READER was not found on database $ORACLE_SID."
fi

echo " "
echo "    ${DESTINATION_SCHEMA_PREFIX}_DERIV..."
x=`sqlplus -s /nolog << EOF | grep '^ORA-' | cut -d: -f1
set pagesize 0 feedback off verify off heading off echo off term off
WHENEVER SQLERROR EXIT 1 ROLLBACK
WHENEVER OSERROR EXIT 1 ROLLBACK
connect $SYSLOGIN
DROP USER ${DESTINATION_SCHEMA_PREFIX}_DERIV CASCADE;
exit sql.sqlcode;
EOF`
if [[ $? -ne 0 ]];then
   echo $x
   echo "Schema ${DESTINATION_SCHEMA_PREFIX}_DERIV was not found on database $ORACLE_SID."
fi

echo " "
echo "    ${DESTINATION_SCHEMA_PREFIX}_ADS_USER..."
x=`sqlplus -s /nolog << EOF | grep '^ORA-' | cut -d: -f1
set pagesize 0 feedback off verify off heading off echo off term off
WHENEVER SQLERROR EXIT 1 ROLLBACK
WHENEVER OSERROR EXIT 1 ROLLBACK
connect $SYSLOGIN
DROP USER ${DESTINATION_SCHEMA_PREFIX}_ADS_USER CASCADE;
exit sql.sqlcode;
EOF`
if [[ $? -ne 0 ]];then
   echo $x
   echo "Schema ${DESTINATION_SCHEMA_PREFIX}_ADS_USER was not found on database $ORACLE_SID."
fi

echo " "
echo "    ${DESTINATION_SCHEMA_PREFIX}_UTIL..."
x=`sqlplus -s /nolog << EOF | grep '^ORA-' | cut -d: -f1
set pagesize 0 feedback off verify off heading off echo off term off
WHENEVER SQLERROR EXIT 1 ROLLBACK
WHENEVER OSERROR EXIT 1 ROLLBACK
connect $SYSLOGIN
DROP USER ${DESTINATION_SCHEMA_PREFIX}_UTIL CASCADE;
exit sql.sqlcode;
EOF`
if [[ $? -ne 0 ]];then
   echo $x
   echo "Schema ${DESTINATION_SCHEMA_PREFIX}_UTIL was not found on database $ORACLE_SID."
fi

echo " "
echo "    ${DESTINATION_SCHEMA_PREFIX}_VIEWS..."
x=`sqlplus -s /nolog << EOF | grep '^ORA-' | cut -d: -f1
set pagesize 0 feedback off verify off heading off echo off term off
WHENEVER SQLERROR EXIT 1 ROLLBACK
WHENEVER OSERROR EXIT 1 ROLLBACK
connect $SYSLOGIN
DROP USER ${DESTINATION_SCHEMA_PREFIX}_VIEWS CASCADE;
exit sql.sqlcode;
EOF`
if [[ $? -ne 0 ]];then
   echo $x
   echo "Schema ${DESTINATION_SCHEMA_PREFIX}_VIEWS was not found on database $ORACLE_SID."
fi

echo " "
echo "    ${DESTINATION_SCHEMA_PREFIX}_USER..."
x=`sqlplus -s /nolog << EOF | grep '^ORA-' | cut -d: -f1
set pagesize 0 feedback off verify off heading off echo off term off
WHENEVER SQLERROR EXIT 1 ROLLBACK
WHENEVER OSERROR EXIT 1 ROLLBACK
connect $SYSLOGIN
DROP USER ${DESTINATION_SCHEMA_PREFIX}_USER CASCADE;
exit sql.sqlcode;
EOF`
if [[ $? -ne 0 ]];then
   echo $x
   echo "Schema ${DESTINATION_SCHEMA_PREFIX}_USER was not found on database $ORACLE_SID."
fi
}
#-----------------------------------------------------------------------
# do_the_import
#  Do the 10g import based on the parameters given
#-----------------------------------------------------------------------
function do_the_import {
echo " "
echo " 3. Importing schemas..."

# Be very careful here:
# There are two different imports because of the old history for ARDAIS.  That schema only had "ADS_USER"
# The only differences between these two statements are
#    remap_schema=ADS_USER:${DESTINATION_SCHEMA_PREFIX}_ADS_USER
# and
#    remap_schema=${SOURCE_SCHEMA_PREFIX}_ADS_USER:${DESTINATION_SCHEMA_PREFIX}_ADS_USER
# and
#    schemas= ...,ADS_USER
# and
#    schemas= ...,${DESTINATION_SCHEMA_PREFIX}_ADS_USER
#
if [[ ${SOURCE_SCHEMA_PREFIX} = "ARDAIS" ]];then
   impdp $SYSLOGIN DIRECTORY=$DATAPUMP_DIR LOGFILE=${DESTINATION_SCHEMA_PREFIX}_refresh.log DUMPFILE=$THE_EXPORT schemas=${SOURCE_SCHEMA_PREFIX}_OWNER,${SOURCE_SCHEMA_PREFIX}_DERIV,${SOURCE_SCHEMA_PREFIX}_READER,${SOURCE_SCHEMA_PREFIX}_USER,${SOURCE_SCHEMA_PREFIX}_UTIL,${SOURCE_SCHEMA_PREFIX}_VIEWS,ADS_USER job_name=Refresh_Schema remap_schema=${SOURCE_SCHEMA_PREFIX}_DERIV:${DESTINATION_SCHEMA_PREFIX}_DERIV remap_schema=${SOURCE_SCHEMA_PREFIX}_OWNER:${DESTINATION_SCHEMA_PREFIX}_OWNER remap_schema=${SOURCE_SCHEMA_PREFIX}_USER:${DESTINATION_SCHEMA_PREFIX}_USER remap_schema=${SOURCE_SCHEMA_PREFIX}_UTIL:${DESTINATION_SCHEMA_PREFIX}_UTIL remap_schema=${SOURCE_SCHEMA_PREFIX}_VIEWS:${DESTINATION_SCHEMA_PREFIX}_VIEWS remap_schema=${SOURCE_SCHEMA_PREFIX}_READER:${DESTINATION_SCHEMA_PREFIX}_READER remap_schema=ADS_USER:${DESTINATION_SCHEMA_PREFIX}_ADS_USER
   if [[ $? -ne 0 ]];then
      echo "The import from ${SOURCE_SCHEMA_PREFIX} to ${DESTINATION_SCHEMA_PREFIX} failed on database $ORACLE_SID."
      echo Exiting...
      exit 1
   fi
else
   impdp $SYSLOGIN DIRECTORY=$DATAPUMP_DIR LOGFILE=${DESTINATION_SCHEMA_PREFIX}_refresh.log DUMPFILE=$THE_EXPORT schemas=${SOURCE_SCHEMA_PREFIX}_OWNER,${SOURCE_SCHEMA_PREFIX}_DERIV,${SOURCE_SCHEMA_PREFIX}_READER,${SOURCE_SCHEMA_PREFIX}_USER,${SOURCE_SCHEMA_PREFIX}_UTIL,${SOURCE_SCHEMA_PREFIX}_VIEWS,${SOURCE_SCHEMA_PREFIX}_ADS_USER job_name=Refresh_Schema remap_schema=${SOURCE_SCHEMA_PREFIX}_DERIV:${DESTINATION_SCHEMA_PREFIX}_DERIV remap_schema=${SOURCE_SCHEMA_PREFIX}_OWNER:${DESTINATION_SCHEMA_PREFIX}_OWNER remap_schema=${SOURCE_SCHEMA_PREFIX}_USER:${DESTINATION_SCHEMA_PREFIX}_USER remap_schema=${SOURCE_SCHEMA_PREFIX}_UTIL:${DESTINATION_SCHEMA_PREFIX}_UTIL remap_schema=${SOURCE_SCHEMA_PREFIX}_VIEWS:${DESTINATION_SCHEMA_PREFIX}_VIEWS remap_schema=${SOURCE_SCHEMA_PREFIX}_READER:${DESTINATION_SCHEMA_PREFIX}_READER remap_schema=${SOURCE_SCHEMA_PREFIX}_ADS_USER:${DESTINATION_SCHEMA_PREFIX}_ADS_USER
   if [[ $? -ne 0 ]];then
      echo "The import from ${SOURCE_SCHEMA_PREFIX} to ${DESTINATION_SCHEMA_PREFIX} failed on database $ORACLE_SID."
      echo Exiting...
      exit 1
   fi
fi
}
#-----------------------------------------------------------------------
# reset_the_passwords
# Reset the passwords to what was given in the parameter file
#-----------------------------------------------------------------------
function reset_the_passwords {
echo " 4. Resetting ${DESTINATION_SCHEMA_PREFIX}_% passwords..."
sqlplus -s /nolog << EOF
set pagesize 0 feedback off verify off heading off echo off term off
connect $SYSLOGIN
ALTER USER ${DESTINATION_SCHEMA_PREFIX}_OWNER IDENTIFIED BY $THE_OWNE_PWD;
ALTER USER ${DESTINATION_SCHEMA_PREFIX}_READER IDENTIFIED BY $THE_READ_PWD;
ALTER USER ${DESTINATION_SCHEMA_PREFIX}_UTIL IDENTIFIED BY $THE_UTIL_PWD;
ALTER USER ${DESTINATION_SCHEMA_PREFIX}_DERIV IDENTIFIED BY $THE_DERI_PWD;
ALTER USER ${DESTINATION_SCHEMA_PREFIX}_USER IDENTIFIED BY $THE_USER_PWD;
ALTER USER ${DESTINATION_SCHEMA_PREFIX}_VIEWS IDENTIFIED BY $THE_VIEW_PWD;
ALTER USER ${DESTINATION_SCHEMA_PREFIX}_ADS_USER IDENTIFIED BY $THE_ADSU_PWD;
exit sql.sqlcode;
EOF
if [[ $? -ne 0 ]];then
   echo "Altering user[s]passwords caused an error on database $ORACLE_SID."
fi
}
#-----------------------------------------------------------------------
# reset_the_app_passwords
# Reset the app passwords to non-production ones.
#-----------------------------------------------------------------------
function reset_the_app_passwords {
echo " 5. Resetting  es_ardais_user passwords..."
sqlplus -s /nolog << EOF
set pagesize 0 feedback off verify off heading off echo off term off
connect ${DESTINATION_SCHEMA_PREFIX}_OWNER/$THE_OWNE_PWD@$ORACLE_SID
UPDATE es_ardais_user SET
     password = 'W6ph5Mm5Pz8GgiULbPgzG37mj9g',
     user_verif_question = 'The Ultimate answer to Life, the Universe and Everything is?...', 
     user_verif_answer = '42',
     user_email_address = 'qa@healthcit.com';
COMMIT;
exit sql.sqlcode;
EOF
if [[ $? -ne 0 ]];then
   echo "Error changing passwords for es_ardais_user on database $ORACLE_SID."
fi

echo " 6. Resetting  es_ardais_account passwords..."
sqlplus -s /nolog << EOF
set pagesize 0 feedback off verify off heading off echo off term off
connect ${DESTINATION_SCHEMA_PREFIX}_OWNER/$THE_OWNE_PWD@$ORACLE_SID
UPDATE es_ardais_account SET
     request_mgr_email_address ='qa@healthcit.com';
COMMIT;
exit sql.sqlcode;
EOF
if [[ $? -ne 0 ]];then
   echo "Error changing passwords for es_ardais_account on database $ORACLE_SID."
fi
}
#-----------------------------------------------------------------------
# create_oracletext_preferences
# Preferences tell Oracle Text how my index should be stored and filtered
#-----------------------------------------------------------------------
function create_oracletext_preferences {
echo " 7. Create the Oracle Text index preferences...."
# Grant privs as system
sqlplus -s /nolog << EOF
set pagesize 0 feedback off verify off heading off echo off term off
connect $SYSLOGIN
GRANT EXECUTE ON CTXSYS.CTX_DDL TO ${DESTINATION_SCHEMA_PREFIX}_OWNER;
exit sql.sqlcode;
EOF
if [[ $? -ne 0 ]];then
   echo "Error on [GRANT EXECUTE ON CTXSYS.CTX_DDL TO ${DESTINATION_SCHEMA_PREFIX}_OWNER] on database $ORACLE_SID."
fi
# Build the preferences
sqlplus -s /nolog << EOF
set pagesize 0 feedback off verify off heading off echo off term off
connect ${DESTINATION_SCHEMA_PREFIX}_OWNER/$THE_OWNE_PWD@$ORACLE_SID
declare
begin
   ctx_ddl.create_preference('lims_intermedia','BASIC_STORAGE');
   ctx_ddl.set_attribute('lims_intermedia', 'I_TABLE_CLAUSE',
                            'tablespace lims_tbl_tbs storage (initial 2M)');
   ctx_ddl.set_attribute('lims_intermedia', 'K_TABLE_CLAUSE',
                            'tablespace lims_tbl_tbs storage (initial 2M)');
   ctx_ddl.set_attribute('lims_intermedia', 'R_TABLE_CLAUSE',
                            'tablespace lims_tbl_tbs storage (initial 2M) lob (data) store as (cache)');
   ctx_ddl.set_attribute('lims_intermedia', 'N_TABLE_CLAUSE',
                            'tablespace lims_tbl_tbs storage (initial 2M)');
   ctx_ddl.set_attribute('lims_intermedia', 'I_INDEX_CLAUSE',
                            'tablespace lims_idx_tbs storage (initial 2M) compress 2');
   ctx_ddl.create_preference('ddc_intermedia','BASIC_STORAGE');
   ctx_ddl.set_attribute('ddc_intermedia', 'I_TABLE_CLAUSE',
                            'tablespace other_tbl_tbs storage (initial 2M)');
   ctx_ddl.set_attribute('ddc_intermedia', 'K_TABLE_CLAUSE',
                            'tablespace other_tbl_tbs storage (initial 2M)');
   ctx_ddl.set_attribute('ddc_intermedia', 'R_TABLE_CLAUSE',
                            'tablespace other_tbl_tbs storage (initial 2M) lob (data) store as (cache)');
   ctx_ddl.set_attribute('ddc_intermedia', 'N_TABLE_CLAUSE',
                            'tablespace other_tbl_tbs storage (initial 2M)');
   ctx_ddl.set_attribute('ddc_intermedia', 'I_INDEX_CLAUSE',
                            'tablespace other_idx_tbs storage (initial 2M) compress 2');
end;
/
COMMIT;
exit sql.sqlcode;
EOF
if [[ $? -ne 0 ]];then
   echo "Error creating the Oracle Text index preferences on database $ORACLE_SID."
fi
}

#-----------------------------------------------------------------------
#--------------------------------  MAIN  -------------------------------
#-----------------------------------------------------------------------
# 
# Run all the functions above in the correct order to refresh a database
#-----------------------------------------------------------------------
FILENAME="$1"
THIS_SCRIPT=$(basename $0)
# Test the Input
# Looking for exactly one parameter
(( $# == 1 )) || usage

# Begining TimeStamp
get_time
export STARTT=$TIME

welcome_screen

process_parfile

drop_users

do_the_import

reset_the_passwords

reset_the_app_passwords

create_oracletext_preferences

# Ending TimeStamp
get_time
export FINISHT=$TIME

echo $THIS_SCRIPT was started $STARTT and completed $FINISHT

echo "********************** COMPLETED ****************************"
exit 0
