---------------------------------------------------------------------------------------
--	File: 	full_db_release_upgrade.sql
--	Author:	GSB
--	Date:	06/12/2009 
--	
--	Purpose: This script will be run to upgrade an existing database to
--		 the next release.  It can only upgrade a database from one release to the
--		 next and cannot do multiple releases.
--
--	SWP-1058	Implementation of roles in BIGR		6/12/09
--
-----------------------------------------------------------------------------------------------------------------
--
-----------------------------------------------------------------------------------------------------------------
-- SQLPlus setup
--
set linesize 200 echo off feedback on verify on echo off
-- Some DML uses an ampersand so lose the define
spool full_db_release_upgrade.log
--
-----------------------------------------------------------------------------------------------------------------
--  DDL
-----------------------------------------------------------------------------------------------------------------
prompt ==========   SWP-1058 ========== 
prompt
CREATE TABLE BIGR_ROLE
(
  ID VARCHAR2 (10) NOT NULL,
  NAME VARCHAR2 (50) NOT NULL,
  ACCOUNT_ID VARCHAR2 (10) NOT NULL
)
TABLESPACE OTHER_TBL_TBS;

CREATE INDEX BIGR_ROLE_IN1 ON BIGR_ROLE (ACCOUNT_ID) TABLESPACE OTHER_IDX_TBS;
CREATE UNIQUE INDEX BIGR_ROLE_IN2 ON BIGR_ROLE (NAME, ACCOUNT_ID) TABLESPACE OTHER_IDX_TBS;
ALTER TABLE BIGR_ROLE ADD CONSTRAINT PK_BIGR_ROLE PRIMARY KEY (ID);
ALTER TABLE BIGR_ROLE ADD CONSTRAINT FK_BIGR_ROLE_TO_ACCOUNT FOREIGN KEY (ACCOUNT_ID) REFERENCES ES_ARDAIS_ACCOUNT (ARDAIS_ACCT_KEY);
CREATE SEQUENCE BIGR_ROLE_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE NOCYCLE CACHE 20;



CREATE TABLE BIGR_ROLE_PRIVILEGE
(
  ID NUMBER NOT NULL,
  ROLE_ID VARCHAR2 (10) NOT NULL,
  PRIVILEGE_ID VARCHAR2 (21) NOT NULL
)
TABLESPACE OTHER_TBL_TBS;

CREATE INDEX BIGR_ROLE_PRIVILEGE_IN1 ON BIGR_ROLE_PRIVILEGE (ROLE_ID) TABLESPACE OTHER_IDX_TBS;
CREATE INDEX BIGR_ROLE_PRIVILEGE_IN2 ON BIGR_ROLE_PRIVILEGE (PRIVILEGE_ID) TABLESPACE OTHER_IDX_TBS;
CREATE UNIQUE INDEX BIGR_ROLE_PRIVILEGE_IN3 ON BIGR_ROLE_PRIVILEGE (ROLE_ID, PRIVILEGE_ID) TABLESPACE OTHER_IDX_TBS;
ALTER TABLE BIGR_ROLE_PRIVILEGE ADD CONSTRAINT PK_BIGR_ROLE_PRIVILEGE PRIMARY KEY (ID);
ALTER TABLE BIGR_ROLE_PRIVILEGE ADD CONSTRAINT FK_BIGR_ROLE_PRIVILEGE_TO_ROLE FOREIGN KEY (ROLE_ID) REFERENCES BIGR_ROLE (ID);
ALTER TABLE BIGR_ROLE_PRIVILEGE ADD CONSTRAINT FK_BIGR_ROLE_PRIVILEGE_TO_PRIV FOREIGN KEY (PRIVILEGE_ID) REFERENCES ARD_OBJECTS (OBJECT_ID);
CREATE SEQUENCE BIGR_ROLE_PRIVILEGE_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE NOCYCLE CACHE 20;



CREATE TABLE BIGR_ROLE_USER
(
  ID NUMBER NOT NULL,
  ROLE_ID VARCHAR2 (10) NOT NULL,
  USER_ID VARCHAR2 (12) NOT NULL
)
TABLESPACE OTHER_TBL_TBS;

CREATE INDEX BIGR_ROLE_USER_IN1 ON BIGR_ROLE_USER (ROLE_ID) TABLESPACE OTHER_IDX_TBS;
CREATE INDEX BIGR_ROLE_USER_IN2 ON BIGR_ROLE_USER (USER_ID) TABLESPACE OTHER_IDX_TBS;
CREATE UNIQUE INDEX BIGR_ROLE_USER_IN3 ON BIGR_ROLE_USER (ROLE_ID, USER_ID) TABLESPACE OTHER_IDX_TBS;
ALTER TABLE BIGR_ROLE_USER ADD CONSTRAINT PK_BIGR_ROLE_USER PRIMARY KEY (ID);
ALTER TABLE BIGR_ROLE_USER ADD CONSTRAINT FK_BIGR_ROLE_USER_TO_ROLE FOREIGN KEY (ROLE_ID) REFERENCES BIGR_ROLE (ID);
ALTER TABLE BIGR_ROLE_USER ADD CONSTRAINT FK_BIGR_ROLE_USER_TO_USER FOREIGN KEY (USER_ID) REFERENCES ES_ARDAIS_USER (ARDAIS_USER_ID);
CREATE SEQUENCE BIGR_ROLE_USER_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE NOCYCLE CACHE 20;


INSERT INTO ARD_OBJECTS (OBJECT_ID, OBJECT_DESCRIPTION, CREATE_DATE, CREATED_BY, UPDATE_DATE, UPDATED_BY, LONG_DESCRIPTION, URL, TOP_MENU, SUB_MENU, OBJECT_TYPE, PRIVILEGE_ID) VALUES ('40_ORM_015_MAINTROLES', 'Maintain Roles', sysdate, 'JEsielionis', sysdate, 'JEsielionis','ORM', '/orm/role/maintainRoleStart.do?resetForm=true', 'BIGR Administration', 'SUB_MENU', 'M', '40_ORM_015_MAINTROLES');


prompt ==========   END SWP-1058 ========== 
prompt 

commit
/

prompt
prompt
set feedback on
-----------------------------------------------------------------------------------------------------------------
--  DML
-----------------------------------------------------------------------------------------------------------------
--  prompt ==========   SWP-  ========== 
--  prompt  

--  prompt ==========   END SWP- ========== 
--  prompt 

--  commit
--  /
-- ==========   RUN FINISH SCRIPTS   ========== 

-----------------------------------------------------------------------------------------------------------------
-- SQLPlus finish
--
-- Compile Schema                                                                  
-- BEGIN
--  DBMS_UTILITY.COMPILE_SCHEMA (USER);
-- END;
-- /

spool off
-----------------------------------------------------------------------
