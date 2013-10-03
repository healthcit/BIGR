REM ***********************************************************
REM * File:  crdb2.sql
REM * Author:  Dean Marsh
REM * Date:  02/25/2002
REM *
REM * Purpose:   This script takes care off all commands
REM * necessary to create an OFA compliant database after the 
REM * CREATE DATABASE command has succeeded.  This script is 
REM * designed to be used with create_newdb.sh and should be 
REM * edited appropriately if used independantly.
REM *
REM * Comments:  
REM *
REM ***********************************************************




REM * Create additional rollback segment in SYSTEM before creating tablespace.
REM *

create rollback segment r0 tablespace system
storage (initial 16k next 16k minextents 2 maxextents 20);

alter rollback segment r0 online;


REM * Create tablespace(s) for rollback segments.

create tablespace rbs01 datafile
	'/u02/oradata/LEXPROD1/rbs01.dbf' size 500M
default storage (
	initial		 1M
	next		 1M
	pctincrease	 0
	minextents	 5
);


REM * Create a tablespace for temporary segments.
REM * Temporary tablespace configuration guidelines:
REM *   Initial and next extent sizes = k * SORT_AREA_SIZE, k in {1,2,3,...}.
REM *

create tablespace temp datafile
	'/u02/oradata/LEXPROD1/temp01.dbf' size 500M
default storage (
	initial      1m
	next         1m
        pctincrease  0
) temporary;

REM * Create a tablespace for users.
REM *
create tablespace users datafile
   '/u02/oradata/LEXPROD1/users01.dbf' size 100M
default storage (
   initial 512K
   next 512K
   pctincrease 0);


REM * Create a tablespace for database tools.
REM *
create tablespace tools datafile
   '/u02/oradata/LEXPROD1/tools01.dbf' size 100M
default storage (
   initial 512K
   next 512K
   pctincrease 0);


REM * Create rollback segments.
REM *

create rollback segment r01 tablespace rbs01 storage (optimal 5M);
create rollback segment r02 tablespace rbs01 storage (optimal 5M);
create rollback segment r03 tablespace rbs01 storage (optimal 5M);
create rollback segment r04 tablespace rbs01 storage (optimal 5M);
create rollback segment r05 tablespace rbs01 storage (optimal 5M);
create rollback segment r06 tablespace rbs01 storage (optimal 5M);
create rollback segment RBS_LARGE tablespace rbs01 storage (initial 20M next 20M);

REM * Use ALTER ROLLBACK SEGMENT ONLINE to put rollback segments online 
REM * without shutting down and restarting the database.  
REM *

alter rollback segment r01 online;
alter rollback segment r02 online;
alter rollback segment r03 online;
alter rollback segment r04 online;
alter rollback segment r05 online;
alter rollback segment r06 online;


REM * Drop rollback segment from system tablespace.

alter rollback segment r0 offline;
drop rollback segment r0;

REM * Alter SYS and SYSTEM users.
REM *

alter user sys temporary tablespace temp;
alter user system default tablespace tools temporary tablespace temp;
