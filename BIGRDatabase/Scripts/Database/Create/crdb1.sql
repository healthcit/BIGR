REM ***********************************************************
REM * File:  crdb1.sql
REM * Author:  Dean Marsh
REM * Date:  02/25/2002
REM *
REM * Purpose:  Create initial database with system tablespace
REM * and logs.  This script is designed to be used with
REM * create_newdb.sh and should be edited appropriately if
REM * used independantly.
REM *
REM * Comments:
REM *
REM ***********************************************************

create database LEXPROD1
    maxdatafiles 128
    maxinstances 2
    maxlogfiles  32
    character set "US7ASCII"
    datafile
	'/u02/oradata/LEXPROD1/system_01.dbf'	size 300M
    logfile
	'/u02/oradata/LEXPROD1/redo01.log'   	size 100M,
	'/u03/oradata/LEXPROD1/redo02.log' 	size 100M,
	'/u02/oradata/LEXPROD1/redo03.log'	size 100M,
	'/u03/oradata/LEXPROD1/redo04.log'	size 100M,
	'/u02/oradata/LEXPROD1/redo05.log' 	size 100M,
	'/u03/oradata/LEXPROD1/redo06.log'	size 100M;
disconnect

