define USER_OWNER = &1
define USER_USER = &2
define USER_READER = &3
define USER_DERIV = &4
define USER_ADS = &5
define USER_UTIL = &11

define OWNERPASSWORD = &6
define USERPASSWORD = &7
define READERPASSWORD = &8
define DERIVPASSWORD = &9
define ADSPASSWORD = &10
define UTILPASSWORD = &12

drop user &USER_OWNER cascade;
drop user &USER_USER cascade;
drop user &USER_READER cascade;
drop user &USER_DERIV cascade;
drop user &USER_ADS cascade;
drop user &USER_UTIL cascade;

CREATE USER &USER_OWNER
  IDENTIFIED BY &OWNERPASSWORD
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 2 Roles
  GRANT CONNECT TO &USER_OWNER;
  GRANT RESOURCE TO &USER_OWNER;
  ALTER USER &USER_OWNER DEFAULT ROLE ALL;
  -- 22 System Privileges
  GRANT CREATE TYPE TO &USER_OWNER;
  GRANT CREATE VIEW TO &USER_OWNER;
  GRANT CREATE TABLE TO &USER_OWNER;
  GRANT ALTER SESSION TO &USER_OWNER;
  GRANT CREATE CLUSTER TO &USER_OWNER;
  GRANT CREATE SYNONYM TO &USER_OWNER;
  GRANT CREATE TRIGGER TO &USER_OWNER;
  GRANT CREATE SEQUENCE TO &USER_OWNER;
  GRANT CREATE PROCEDURE TO &USER_OWNER;
  GRANT DROP ANY SYNONYM TO &USER_OWNER;
  GRANT ALTER ANY SNAPSHOT TO &USER_OWNER;
  GRANT CREATE ANY SYNONYM TO &USER_OWNER;
  GRANT DROP ANY DIRECTORY TO &USER_OWNER;
  GRANT RESTRICTED SESSION TO &USER_OWNER;
  GRANT CREATE ANY SNAPSHOT TO &USER_OWNER;
  GRANT DROP PUBLIC SYNONYM TO &USER_OWNER;
  GRANT CREATE ANY DIRECTORY TO &USER_OWNER;
  GRANT CREATE DATABASE LINK TO &USER_OWNER;
  GRANT UNLIMITED TABLESPACE TO &USER_OWNER;
  GRANT CREATE PUBLIC SYNONYM TO &USER_OWNER;
  GRANT SELECT ANY DICTIONARY TO &USER_OWNER;
  GRANT ALTER ROLLBACK SEGMENT TO &USER_OWNER;
  -- Oracle Text
  GRANT execute on ctx_ddl TO &USER_OWNER;
  GRANT CTXAPP to &USER_OWNER;

CREATE USER &USER_USER
  IDENTIFIED BY &USERPASSWORD
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 2 Roles
  GRANT CONNECT TO &USER_USER;
  GRANT RESOURCE TO &USER_USER;
  ALTER USER &USER_USER DEFAULT ROLE ALL;
  -- 1 System Privilege
  GRANT UNLIMITED TABLESPACE TO &USER_USER;

CREATE USER &USER_READER
  IDENTIFIED BY &READERPASSWORD
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 2 Roles
  GRANT CONNECT TO &USER_READER;
  GRANT RESOURCE TO &USER_READER;
  ALTER USER &USER_READER DEFAULT ROLE ALL;
  -- 1 System Privilege
  GRANT UNLIMITED TABLESPACE TO &USER_READER;

CREATE USER &USER_UTIL
  IDENTIFIED BY &UTILPASSWORD
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 2 Roles 
  GRANT CONNECT TO &USER_UTIL;
  GRANT RESOURCE TO &USER_UTIL;
  ALTER USER &USER_UTIL DEFAULT ROLE ALL;
  -- 1 System Privilege
  GRANT UNLIMITED TABLESPACE TO &USER_UTIL;

CREATE USER &USER_DERIV
  IDENTIFIED BY &DERIVPASSWORD
  DEFAULT TABLESPACE USERS
  TEMPORARY TABLESPACE TEMP
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  -- 2 Roles 
  GRANT CONNECT TO &USER_DERIV;
  GRANT RESOURCE TO &USER_DERIV;
  ALTER USER &USER_DERIV DEFAULT ROLE ALL;
  -- 1 System Privilege 
  GRANT UNLIMITED TABLESPACE TO &USER_DERIV;
  
CREATE USER &USER_ADS
IDENTIFIED BY &ADSPASSWORD
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP
PROFILE DEFAULT
ACCOUNT UNLOCK;
-- 3 Roles for &USER_ADS
GRANT CONNECT TO &USER_ADS;
GRANT RESOURCE TO &USER_ADS;
ALTER USER &USER_ADS DEFAULT ROLE ALL;
-- 15 System Privileges for &USER_ADS
GRANT CREATE TYPE TO &USER_ADS;
GRANT CREATE VIEW TO &USER_ADS;
GRANT CREATE TABLE TO &USER_ADS;
GRANT ALTER SESSION TO &USER_ADS;
GRANT CREATE CLUSTER TO &USER_ADS;
GRANT CREATE SESSION TO &USER_ADS;
GRANT CREATE SYNONYM TO &USER_ADS;
GRANT CREATE TRIGGER TO &USER_ADS;
GRANT CREATE SEQUENCE TO &USER_ADS;
GRANT CREATE PROCEDURE TO &USER_ADS;
GRANT ALTER ANY SNAPSHOT TO &USER_ADS;
GRANT RESTRICTED SESSION TO &USER_ADS;
GRANT CREATE ANY SNAPSHOT TO &USER_ADS;
GRANT CREATE DATABASE LINK TO &USER_ADS;
GRANT UNLIMITED TABLESPACE TO &USER_ADS;

exit;
