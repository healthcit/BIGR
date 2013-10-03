REM ***********************************************************
REM * File:  crdb3.sql
REM * Author:  Dean Marsh
REM * Date:  02/25/2002
REM *
REM * Purpose:   This script creates the required application
REM * tablespaces.  This script is designed to be used with
REM * create_newdb.sh and should be edited appropriately if used
REM * independantly.
REM *
REM * Comments:
REM *
REM ***********************************************************

spool $ORACLE_BASE/admin/$ORACLE_SID/create/crdb3.log

CREATE TABLESPACE ES_TBL_TBS
DATAFILE '/u03/oradata/LEXPROD1/es_tbl_tbs_01.dbf' size 100M
default storage (initial 1M next 1M pctincrease 0);

CREATE TABLESPACE ES_IDX_TBS
DATAFILE '/u04/oradata/LEXPROD1/es_idx_tbs_01.dbf' size 100M
default storage (initial 1M next 1M pctincrease 0);

CREATE TABLESPACE ILTDS_TBL_TBS
DATAFILE '/u03/oradata/LEXPROD1/iltds_tbl_tbs_01.dbf' size 200M
default storage (initial 1M next 1M pctincrease 0);

CREATE TABLESPACE ILTDS_IDX_TBS
DATAFILE '/u04/oradata/LEXPROD1/iltds_idx_tbs_01.dbf' size 100M
default storage (initial 1M next 1M pctincrease 0);

CREATE TABLESPACE LIMS_TBL_TBS
DATAFILE '/u03/oradata/LEXPROD1/lims_tbl_tbs_01.dbf' size 100M
default storage (initial 1M next 1M pctincrease 0);

CREATE TABLESPACE LIMS_IDX_TBS
DATAFILE '/u04/oradata/LEXPROD1/lims_idx_tbs_01.dbf' size 100M
default storage (initial 1M next 1M pctincrease 0);

CREATE TABLESPACE OTHER_TBL_TBS
DATAFILE '/u03/oradata/LEXPROD1/other_tbl_tbs_01.dbf' size 100M
default storage (initial 1M next 1M pctincrease 0);

CREATE TABLESPACE OTHER_IDX_TBS
DATAFILE '/u04/oradata/LEXPROD1/other_idx_tbs_01.dbf' size 100M
default storage (initial 1M next 1M pctincrease 0);

spool off;
