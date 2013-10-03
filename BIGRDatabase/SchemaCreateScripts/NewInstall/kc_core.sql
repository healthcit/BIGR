------------------------------------------------------------
--
-- Create Schema Script 
--   Source             : LEXINT1 
--   Schema             : ARDAIS_OWNER 
--   Script Created by  : SYS 
--   Script Created at  : 11/17/2006 
--   Physical Location  :  
--   Notes              : Initial Knowledge Capture objects
--         
-- © Copyright 2007, HealthCare IT Corporation
--         www.healthcit.com

-- "Set define off" turns off substitution variables. 
Set define off; 
set echo on linesize 100 feedback on verify on
set serveroutput on size 1000000 format wrapped;
SET LONG 20000
set longchunksize 20000
------------------------------------------------------------
------------  Begin  Schema DDL ----------------------------
------------------------------------------------------------

Prompt SEQUENCE CIR_FORM_DEFINITION_SEQ;
--
-- CIR_FORM_DEFINITION_SEQ  (Sequence) 
--
CREATE SEQUENCE CIR_FORM_DEFINITION_SEQ
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 1E27
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/

Prompt Table CIR_FORM_DEFINITION;
--
-- CIR_FORM_DEFINITION  (Table) 
--
CREATE TABLE CIR_FORM_DEFINITION
(
  FORM_DEFINITION_ID        VARCHAR2(10 BYTE)   NOT NULL,
  FORM_NAME                 VARCHAR2(256 BYTE)  NOT NULL,
  FORM_DEFINITION           CLOB                NOT NULL,
  FORM_DEFINITION_ENCODING  VARCHAR2(256 BYTE)  NOT NULL,
  ENABLED_YN                VARCHAR2(1 BYTE)    NOT NULL,
  FORM_TYPE                 VARCHAR2(256 BYTE)  NOT NULL
)
TABLESPACE CIR_TBL_TBS
LOGGING 
NOCOMPRESS 
LOB (FORM_DEFINITION) STORE AS 
      ( TABLESPACE  CIR_TBL_TBS 
        ENABLE      STORAGE IN ROW
        CHUNK       8192
        PCTVERSION  10
        NOCACHE
      )
NOCACHE
NOPARALLEL
NOMONITORING
/

Prompt Index PK_CIR_FORM_DEFINITION;
--
-- PK_CIR_FORM_DEFINITION  (Index) 
--
--  Dependencies: 
--   CIR_FORM_DEFINITION (Table)
--
CREATE UNIQUE INDEX PK_CIR_FORM_DEFINITION ON CIR_FORM_DEFINITION
(FORM_DEFINITION_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
NOPARALLEL
/


-- 
-- Non Foreign Key Constraints for Table CIR_FORM_DEFINITION 
-- 
Prompt Non-Foreign Key Constraints on Table CIR_FORM_DEFINITION;
ALTER TABLE CIR_FORM_DEFINITION ADD (
  CONSTRAINT PK_CIR_FORM_DEFINITION
 PRIMARY KEY
 (FORM_DEFINITION_ID)
    USING INDEX 
    TABLESPACE CIR_IDX_TBS)
/

Prompt Sequence CIR_FORM_DEFINITION_TAGS_SEQ;
--
-- CIR_FORM_DEFINITION_TAGS_SEQ  (Sequence) 
--
CREATE SEQUENCE CIR_FORM_DEFINITION_TAGS_SEQ
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 1E27
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/

Prompt Table CIR_FORM_DEFINITION_TAGS;
--
-- CIR_FORM_DEFINITION_TAGS  (Table) 
--
--  Dependencies: 
--   CIR_FORM_DEFINITION (Table)
--
CREATE TABLE CIR_FORM_DEFINITION_TAGS
(
  ID                  NUMBER                        NULL,
  FORM_DEFINITION_ID  VARCHAR2(10 BYTE)             NULL,
  TAG                 VARCHAR2(256 BYTE)            NULL,
  TAG_TYPE            VARCHAR2(256 BYTE)            NULL
)
TABLESPACE CIR_TBL_TBS
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING
/



Prompt Index PK_CIR_FORM_DEFINITION_TAGS;
--
-- PK_CIR_FORM_DEFINITION_TAGS  (Index) 
--
--  Dependencies: 
--   CIR_FORM_DEFINITION_TAGS (Table)
--
CREATE UNIQUE INDEX PK_CIR_FORM_DEFINITION_TAGS ON CIR_FORM_DEFINITION_TAGS
(ID)
LOGGING
TABLESPACE CIR_IDX_TBS
NOPARALLEL
/


Prompt Index CIR_FORM_DEFINITION_TAGS_IN1;
--
-- CIR_FORM_DEFINITION_TAGS_IN1  (Index) 
--
--  Dependencies: 
--   CIR_FORM_DEFINITION_TAGS (Table)
--
CREATE INDEX CIR_FORM_DEFINITION_TAGS_IN1 ON CIR_FORM_DEFINITION_TAGS
(FORM_DEFINITION_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
NOPARALLEL
/


-- 
-- Non Foreign Key Constraints for Table CIR_FORM_DEFINITION_TAGS 
-- 
Prompt Non-Foreign Key Constraints on Table CIR_FORM_DEFINITION_TAGS;
ALTER TABLE CIR_FORM_DEFINITION_TAGS ADD (
  CONSTRAINT PK_CIR_FORM_DEFINITION_TAGS
 PRIMARY KEY
 (ID)
    USING INDEX 
    TABLESPACE CIR_IDX_TBS)
/


-- 
-- Foreign Key Constraints for Table CIR_FORM_DEFINITION_TAGS 
-- 
Prompt Foreign Key Constraints on Table CIR_FORM_DEFINITION_TAGS;
ALTER TABLE CIR_FORM_DEFINITION_TAGS ADD (
  CONSTRAINT FK_CIR_FORM_DEF_FORM_TAGS 
 FOREIGN KEY (FORM_DEFINITION_ID) 
 REFERENCES CIR_FORM_DEFINITION (FORM_DEFINITION_ID))
/

Prompt Sequence CIR_FORM_SEQ;
--
-- CIR_FORM_SEQ  (Sequence) 
--
CREATE SEQUENCE CIR_FORM_SEQ
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 1E27
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
/

Prompt Table CIR_FORM;
--
-- CIR_FORM  (Table) 
--
--  Dependencies: 
--   CIR_FORM_DEFINITION (Table)
--
CREATE TABLE CIR_FORM
(
  FORM_ID             VARCHAR2(10 BYTE)         NOT NULL,
  DOMAIN_OBJECT_ID    VARCHAR2(256 BYTE)            NULL,
  DOMAIN_OBJECT_TYPE  VARCHAR2(256 BYTE)            NULL,
  CREATION_DATETIME   DATE                          NULL,
  FORM_DEFINITION_ID  VARCHAR2(10 BYTE)         NOT NULL,
  MODIFICATION_DATETIME  DATE                       NULL
)
TABLESPACE CIR_TBL_TBS
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING
/


Prompt Index PK_CIR_FORM;
--
-- PK_CIR_FORM  (Index) 
--
--  Dependencies: 
--   CIR_FORM (Table)
--
CREATE UNIQUE INDEX PK_CIR_FORM ON CIR_FORM
(FORM_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
NOPARALLEL
/


Prompt Index CIR_FORM_IN1;
--
-- CIR_FORM_IN1  (Index) 
--
--  Dependencies: 
--   CIR_FORM (Table)
--
CREATE INDEX CIR_FORM_IN1 ON CIR_FORM
(FORM_DEFINITION_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
NOPARALLEL
/


Prompt Index CIR_FORM_IN2;
--
-- CIR_FORM_IN2  (Index) 
--
--  Dependencies: 
--   CIR_FORM (Table)
--
CREATE INDEX CIR_FORM_IN2 ON CIR_FORM
(DOMAIN_OBJECT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
NOPARALLEL
/

-- 
-- Non Foreign Key Constraints for Table CIR_FORM 
-- 
Prompt Non-Foreign Key Constraints on Table CIR_FORM;
ALTER TABLE CIR_FORM ADD (
  CONSTRAINT PK_CIR_FORM
 PRIMARY KEY
 (FORM_ID)
    USING INDEX 
    TABLESPACE CIR_IDX_TBS)
/


-- 
-- Foreign Key Constraints for Table CIR_FORM 
-- 
Prompt Foreign Key Constraints on Table CIR_FORM;
ALTER TABLE CIR_FORM ADD (
  CONSTRAINT FK_CIR_FORM_FORM_DEF 
 FOREIGN KEY (FORM_DEFINITION_ID) 
 REFERENCES CIR_FORM_DEFINITION (FORM_DEFINITION_ID))
/

BEGIN
  DBMS_UTILITY.COMPILE_SCHEMA (USER);
END;
/

------------------------------------------------------------
-------------   End Create Schema Script -------------------
------------------------------------------------------------
