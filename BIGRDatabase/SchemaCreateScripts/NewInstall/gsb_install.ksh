#!/bin/ksh
#-----------------------------------------------------------------------
# PROGRAM       gsb_install.ksh
# USAGE         ./gsb_install.ksh <ORACLE_SID> <SCHEMA_OWNER> <system_password> <owner_password> <ads_password> <user_password> <reader_password> <util_password> <deriv_password> <views_password> <PATH_FOR_DATAFILES(no ending slash)>
# FUNCTION      Installs the GSB Oracle database schemas
#               A log file will be generated in the working dir
# CALLED BY     user
#
# NOTES         The script needs 11 parameters to run:
#                1) ORACLE_SID that will hold the schema
#                2) The customer defined name of the owner of the GSB schema (6-8 characters)
#                3) The password to the SYSTEM account
#                4) The password to the "owner" schema
#                5) The password to the "ads" schema
#                6) The password to the "user" schema
#                7) The password to the "reader" schema
#                8) The password to the "util" schema
#                9) The password to the "deriv" schema
#               10) The password to the "views" schema
#               11) The path where the GSB datafiles will reside if tablespaces need to be created
#                   All datafiles will be put in the same directory
#                   You will need approximately 4Gb of space
#
# AUTHOR        Phil DiPrima
# Date          Jan 2007
# Note          
#               05/05/08 - (SWP-906) Adjust privileges assigned to BIGR schema for security policy
#               07/17/08 - Added kc_generated_schema.sql to create KC tables
#               01/15/09 - Added "_views" schema
#               03/30/09 - ${10} was is needed to capture views password
#-----------------------------------------------------------------------
# Manual uninstall notes:
# In the event you need to uninstall or rerun the script...
# Perform the following steps:
# ------------------------------------------------------------
# ------------  Manual Uninstall Steps -----------------------
# ------------------------------------------------------------
# 1) log off all schema owners
#    or bounce the database if you can not find all connected 
#    sessions.
#      Here' how
#      connect to server as oracle user
#       sqlplus /nolog
#         connect / as sysdba
#         shutdown immediate
#         <wait>
#         startup
#         after Database Open message, exit
# 
# 2) Connect to database as system account...
#     TOAD->Schema Browser->Users
#       Highlight all users to delete with the control key
#         right click->Drop->select cascade option
# 
# 3) Remove old OracleText indexes...
#    First view the existing OracleText indexes, if any
#    SELECT OWNER,IDX_NAME 
#      FROM CTXSYS.DR$JOB_LIST
#    /
# 
#    Next delete existing OracleText indexes of the schemas
#    you have deleted from step 2
# 
#    DELETE FROM CTXSYS.dr$job_list 
#    WHERE owner = '<schemaname_goes_here>_OWNER'
#    /
#    commit
#    /
# 
# You are now ready to re-run gsb_install.ksh with the dropped schema_name
#
#-----------------------------------------------------------------------
# Load parameters or exit if not enough
#-----------------------------------------------------------------------

# Oracle database identifier
SID=$1
# Owner of the GSB schema.  Always make this uppercase for consistency and easy comparison.
SCHEMAOWNER=`echo "$2" | tr '[:lower:]' '[:upper:]'`
# password for the database system account
SYSTEMPASSWORD=$3
# password for the "owner" schema
OWNERPASSWORD=$4
# password for the "ads" schema
ADSPASSWORD=$5
# password for the "user" schema
USERPASSWORD=$6
# password for the "reader" schema
READERPASSWORD=$7
# password for the "util" schema
UTILPASSWORD=$8
# password for the "deriv" schema
DERIVPASSWORD=$9
# password for the "views" schema
VIEWSPASSWORD=${10}
# datafile path
ORADATA=${11}

PARAM=$#

# File to log output
LOGFILE=gsb_install.log

SERVERNAME=`uname -a | awk '{print $2}' | tr '[:lower:]' '[:upper:]'`

print
print
print

echo "#--- $(date)"  > $LOGFILE
echo "gsb_install started with params: $SID $SCHEMAOWNER ***passwords*** $ORADATA"  >> $LOGFILE
usage(){
        print
        print gsb_install.ksh - a wrapper to install the GSB schema
        print "Usage: ./gsb_install.ksh <ORACLE_SID> <SCHEMA_OWNER> <system_password> <owner_password> <ads_password> <user_password> <reader_password> <util_password> <deriv_password> <views_password> <PATH_FOR_DATAFILES(no ending slash)>"
        print
        print "  Please provide some information to the script:"
        print "      - The ORACLE_SID that will hold the schema"
        print "      - The name of the owner of the GSB schema (6-8 characters)"
        print "      - The password to the SYSTEM account"
        print "      - The password to the OWNER schema"
        print "      - The password to the ADS schema"
        print "      - The password to the USER schema"
        print "      - The password to the READER schema"
        print "      - The password to the UTIL schema"
        print "      - The password to the DERIV schema"
        print "      - The password to the VIEWS schema"
        print "      - The path where approximately 4Gb of GSB datafiles will reside"
        print "      -  (if tablespaces need to be created)"
        print
        echo "#--- $(date)"  >> $LOGFILE
        echo "parameters check failed"  >> $LOGFILE
}

echo '*************************************************************' 
echo '    Welcome to the HealthCare IT Corporation Database Install Package'
echo ''
echo '         © Copyright 2009, HealthCare IT Corporation'
echo '                  www.healthcit.com'
echo ''
echo '*************************************************************'

if [ $PARAM -ne 11 ]
then
   usage
   echo "#--- $(date)"  >> $LOGFILE
   echo "displayed usage requirements"  >> $LOGFILE
   echo "exiting..."  >> $LOGFILE
   exit 1
fi

#-----------------------------------------------------------------------
# Test that a connect can be made to the database before moving on
#-----------------------------------------------------------------------

sqlplus -s /nolog >> $LOGFILE <<EOF
whenever sqlerror exit failure
connect system/$SYSTEMPASSWORD@$SID
exit success
EOF

[ $? -ne 0 ] && { echo "Database connection failed";echo "#--- $(date)"  >> $LOGFILE; echo "connect to target database failed"  >> $LOGFILE; exit 1; }
echo "Connected to target database $SID"  >> $LOGFILE;echo "Connecting from $SERVERNAME"  >> $LOGFILE

#-----------------------------------------------------------------------
# Test that the username supplied does not exist before moving on
#-----------------------------------------------------------------------

# Build the user_GSB name
USER_OWNER=${SCHEMAOWNER}_OWNER
USER_USER=${SCHEMAOWNER}_USER
USER_READER=${SCHEMAOWNER}_READER
USER_UTIL=${SCHEMAOWNER}_UTIL
USER_DERIV=${SCHEMAOWNER}_DERIV
USER_VIEWS=${SCHEMAOWNER}_VIEWS
if [ "X${SCHEMAOWNER}" = "XARDAIS" ] ; then
  USER_ADS=ADS_USER
else
  USER_ADS=${SCHEMAOWNER}_ADS_USER
fi

# Return an integer
typeset -i OUTPUT=`sqlplus -s /nolog <<EOF | grep KEEP | sed 's/KEEP//;s/[     ]//g'
whenever sqlerror exit failure
whenever sqlerror exit sql.sqlcode
set echo on term on
connect system/$SYSTEMPASSWORD@$SID
select 'KEEP' , count(1) from  sys.dba_users where username in(upper('$USER_OWNER'),upper('$USER_ADS'))
/
EOF`

if [ $OUTPUT -gt 0 ]
then
   echo "#--- $(date)"  >> $LOGFILE
   echo "Oracle user $USER1 exits"  >> $LOGFILE
   echo " schema may have been previously installed???"  >> $LOGFILE
   echo " please remove all $USER_OWNER, $USER_ADS, etc and rerun the script."  >> $LOGFILE
   echo "exiting..."  >> $LOGFILE
   echo "Oracle user  $USER1 exits"
   echo " schema may have been previously installed???"
   echo " please remove all $USER_OWNER, $USER_ADS, etc and rerun the script."
   echo "exiting..."
   exit 1
fi

#-----------------------------------------------------------------------
# Test for tablespaces needed by GSB
# as of 09/06 GSB needs 10 tablespaces
# change the SQL and the case statement below as needed.
#-----------------------------------------------------------------------

# Return an integer
typeset -i OUTPUT=`sqlplus -s /nolog <<EOF | grep KEEP | sed 's/KEEP//;s/[     ]//g'
whenever sqlerror exit failure
whenever sqlerror exit sql.sqlcode
set echo on term on
connect system/$SYSTEMPASSWORD@$SID
select 'KEEP' , count(*)
  from dba_tablespaces
 where TABLESPACE_NAME in('CIR_IDX_TBS','CIR_TBL_TBS','ES_IDX_TBS',
                          'ES_TBL_TBS','ILTDS_IDX_TBS','ILTDS_TBL_TBS',
                          'LIMS_IDX_TBS','LIMS_TBL_TBS','OTHER_IDX_TBS',
                          'OTHER_TBL_TBS')
/
EOF`

case $OUTPUT in
	10)
            echo "#--- $(date)"  >> $LOGFILE
            echo "GSB TABLESPACE_NAME count = $OUTPUT"  >> $LOGFILE
            echo "Moving on to the next step."  >> $LOGFILE
	    ;;
	0)
            #-----------------------------------------------------------------------
            # Make tablespaces needed by GSB
            # as of 09/06 GSB needs 10 tablespaces
            #-----------------------------------------------------------------------

            # Log current disk space
            df -k >> $LOGFILE

            if  [  !  -d  $ORADATA  ] # True if file exists and is a directory
                then
        	echo "#--- $(date)"  >> $LOGFILE
                echo "The $ORADATA directory DOES NOT exist."  >> $LOGFILE
                echo "Please create this directory or choose another."  >> $LOGFILE
                echo "exiting..."  >> $LOGFILE
                echo "The $ORADATA DOES NOT exist."
                echo "Please create this directory or choose another,"
                echo " then rerun this script."
                echo "exiting..."
                exit 1
            fi

            echo "#--- $(date)"  >> $LOGFILE
            echo "Calling the mk_gsb_tablespaces SQL script."  >> $LOGFILE
            echo "Creating tablespaces..."  >> $LOGFILE
            echo "Creating tablespaces..."

            sqlplus -s /nolog >> $LOGFILE <<EOF
            whenever sqlerror exit 1
            connect system/$SYSTEMPASSWORD@$SID
            @mk_gsb_tablespaces.sql ${ORADATA}
EOF
            echo "Create tablespace section complete."  >> $LOGFILE
	    ;;
		
	*)
            echo "#--- $(date)"  >> $LOGFILE
            echo " Wrong number of GSB tablespaces found. Contact sysadmin"  >> $LOGFILE
            echo "exiting..."  >> $LOGFILE
            echo " Wrong number of GSB tablespaces found. Contact sysadmin"
            echo "exiting..."
            exit 1
	    ;;
esac

#-----------------------------------------------------------------------
# Make users needed by GSB
#
#-----------------------------------------------------------------------

echo "#--- $(date)"  >> $LOGFILE
echo "Creating users..."  >> $LOGFILE
echo "Creating users..."

sqlplus -s /nolog >> $LOGFILE <<EOF
whenever sqlerror exit 1
connect system/$SYSTEMPASSWORD@$SID
CREATE USER ${USER_OWNER}
  IDENTIFIED BY ${OWNERPASSWORD}
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 1 Roles
  GRANT CTXAPP to ${USER_OWNER};
  ALTER USER ${USER_OWNER} DEFAULT ROLE ALL;
  -- 14 System Privileges
  GRANT CREATE TYPE TO ${USER_OWNER};
  GRANT CREATE VIEW TO ${USER_OWNER};
  GRANT CREATE TABLE TO ${USER_OWNER};
  GRANT CREATE SESSION TO ${USER_OWNER};
  GRANT CREATE CLUSTER TO ${USER_OWNER};
  GRANT CREATE SYNONYM TO ${USER_OWNER};
  GRANT CREATE TRIGGER TO ${USER_OWNER};
  GRANT CREATE SEQUENCE TO ${USER_OWNER};
  GRANT CREATE PROCEDURE TO ${USER_OWNER};
  GRANT DROP ANY SYNONYM TO ${USER_OWNER};
  GRANT CREATE ANY SYNONYM TO ${USER_OWNER};
  GRANT CREATE DATABASE LINK TO ${USER_OWNER};
  GRANT UNLIMITED TABLESPACE TO ${USER_OWNER};
  -- Oracle Text
  GRANT execute on CTXSYS.ctx_ddl TO ${USER_OWNER};

CREATE USER ${USER_USER}
  IDENTIFIED BY ${USERPASSWORD}
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 0 Roles
  ALTER USER ${USER_USER} DEFAULT ROLE NONE;
  -- 2 System Privilege
  GRANT CREATE SESSION TO ${USER_USER};
  GRANT UNLIMITED TABLESPACE TO ${USER_USER};

CREATE USER ${USER_READER}
  IDENTIFIED BY ${READERPASSWORD}
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 0 Roles
  ALTER USER ${USER_READER} DEFAULT ROLE NONE;
  -- 2 System Privilege
  GRANT CREATE SESSION TO ${USER_READER};
  GRANT UNLIMITED TABLESPACE TO ${USER_READER};

CREATE USER ${USER_UTIL}
  IDENTIFIED BY ${UTILPASSWORD}
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 0 Roles 
  ALTER USER ${USER_UTIL} DEFAULT ROLE NONE;
  -- 2 System Privilege
  GRANT CREATE SESSION TO ${USER_UTIL};
  GRANT UNLIMITED TABLESPACE TO ${USER_UTIL};

CREATE USER ${USER_DERIV}
  IDENTIFIED BY ${DERIVPASSWORD}
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 0 Roles 
  ALTER USER ${USER_DERIV} DEFAULT ROLE NONE;
  -- 2 System Privilege 
  GRANT CREATE SESSION TO ${USER_DERIV};
  GRANT UNLIMITED TABLESPACE TO ${USER_DERIV};
  
CREATE USER ${USER_VIEWS}
  IDENTIFIED BY ${VIEWSPASSWORD}
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 0 Roles 
  ALTER USER ${USER_VIEWS} DEFAULT ROLE NONE;
  -- 2 System Privilege 
  GRANT CREATE SESSION TO ${USER_VIEWS};
  GRANT UNLIMITED TABLESPACE TO ${USER_VIEWS};    
EOF

#-----------------------------------------------------------------------
# The ADS_USER
#
#-----------------------------------------------------------------------

echo "#--- $(date)"  >> $LOGFILE
echo "Creating the ADS schema (${USER_ADS})"  >> $LOGFILE
sqlplus -s /nolog >> $LOGFILE <<EOF
whenever sqlerror exit 1
connect system/$SYSTEMPASSWORD@$SID
CREATE USER ${USER_ADS}
IDENTIFIED BY ${ADSPASSWORD}
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP
PROFILE DEFAULT
ACCOUNT UNLOCK;
  -- 0 Roles for ${USER_ADS}
  ALTER USER ${USER_ADS} DEFAULT ROLE NONE;
  -- 11 System Privileges for ${USER_ADS}
  GRANT CREATE TYPE TO ${USER_ADS};
  GRANT CREATE VIEW TO ${USER_ADS};
  GRANT CREATE TABLE TO ${USER_ADS};
  GRANT CREATE SESSION TO ${USER_ADS};
  GRANT CREATE CLUSTER TO ${USER_ADS};
  GRANT CREATE SYNONYM TO ${USER_ADS};
  GRANT CREATE TRIGGER TO ${USER_ADS};
  GRANT CREATE SEQUENCE TO ${USER_ADS};
  GRANT CREATE PROCEDURE TO ${USER_ADS};
  GRANT CREATE DATABASE LINK TO ${USER_ADS};
  GRANT UNLIMITED TABLESPACE TO ${USER_ADS};
EOF

#-----------------------------------------------------------------------
# Make schema needed by GSB
#
#-----------------------------------------------------------------------

echo "#--- $(date)"  >> $LOGFILE
echo "Creating OWNER schema..."  >> $LOGFILE
echo "Creating OWNER schema..."

sqlplus -s /nolog >> $LOGFILE <<EOF
whenever sqlerror exit 1
connect ${USER_OWNER}/${OWNERPASSWORD}@$SID
@kc_core.sql
@ardais_owner.sql
@kc_generated_schema.sql
EOF

echo "#--- $(date)"  >> $LOGFILE
echo "Creating ADS schema..."  >> $LOGFILE
echo "Creating ADS schema..."

echo " ${USER_ADS}"  >> $LOGFILE
sqlplus -s /nolog >> $LOGFILE <<EOF
whenever sqlerror exit 1
connect ${USER_ADS}/${ADSPASSWORD}@$SID
@ads_user.sql
EOF

#-----------------------------------------------------------------------
# Check for invalid objects
#
#-----------------------------------------------------------------------

echo "#--- $(date)"  >> $LOGFILE
echo "Checking for invalid objects..."  >> $LOGFILE
sqlplus -s /nolog >> $LOGFILE <<EOF
whenever sqlerror exit failure
whenever sqlerror exit sql.sqlcode
connect system/$SYSTEMPASSWORD@$SID
set echo on term on
col OWNER format a20
col OBJECT_NAME format a30
col OBJECT_TYPE format a15
  select owner, object_name, object_type, status
    from dba_objects
   where status = 'INVALID'
order by owner, object_type, object_name
/
EOF

#-----------------------------------------------------------------------
# Object counts
#
#-----------------------------------------------------------------------

echo "#--- $(date)"  >> $LOGFILE
echo "Checking for object.counts.."  >> $LOGFILE
sqlplus -s /nolog >> $LOGFILE <<EOF
whenever sqlerror exit failure
whenever sqlerror exit sql.sqlcode
connect system/$SYSTEMPASSWORD@$SID
set echo on term on linesize 200
col owner format a15
select owner,
sum(decode(object_type, 'TABLE', 1, 0)) Tbls,
sum(decode(object_type, 'INDEX', 1, 0)) Indx,
sum(decode(object_type, 'VIEW', 1, 0)) Vews,
sum(decode(object_type, 'TRIGGER', 1, 0)) Trgr,
sum(decode(object_type, 'FUNCTION', 1, 0)) Func,
sum(decode(object_type, 'PROCEDURE', 1, 0)) Proc,
sum(decode(object_type, 'PACKAGE', 1, 0)) Pkgs,
sum(decode(object_type, 'PACKAGE BODY', 1, 0)) Pkgb,
sum(decode(object_type, 'SEQUENCE', 1, 0)) Sequ,
sum(decode(object_type, 'SYNONYM', 1, 0)) Syno
from dba_objects
where owner like '%_OWNER'
  or owner like '%_USER'
  or owner like '%_READER'
  or owner like '%_UTIL'
  or owner like '%_DERIV'
  or owner like '%_ADS_USER'
  or owner like 'ADS_USER'
group by owner
order by owner
/
EOF

#-----------------------------------------------------------------------
# Completed
#
#-----------------------------------------------------------------------

echo "#--- $(date)"  >> $LOGFILE
echo "Creation of GSB Application schema completed."  >> $LOGFILE
echo "-----------------------------------------------"
print " Creation of GSB Application schema completed. "
print " Please check the file $LOGFILE for any errors."
echo "-----------------------------------------------"

 
OUTPUT=$?

