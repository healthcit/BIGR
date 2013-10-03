------------------------------------------------------------
--
-- Create Schema Script 
--   Source             : LEXINT1 
--   Schema             : ADS_USER 
--   Script Created by  : SYS 
--   Script Created at  : 10/02/2006 
--   Physical Location  :  
--   Notes              :  
--         
-- � Copyright 2006, HealthCare IT Corporation
--         www.healthcit.com

-- "Set define off" turns off substitution variables. 
Set define off; 
set echo on linesize 200 term on
------------------------------------------------------------
------------  Begin  Schema DDL ----------------------------
------------------------------------------------------------

CREATE TABLE ADS_ATTACHT
(
  ATTACHID NUMBER (11,0) NOT NULL,
  SLIDEID VARCHAR2 (50),
  ATTACHFILENAME VARCHAR2 (50),
  ATTACHPATHANDNAME VARCHAR2 (255),
  ATTACHDESCRIPTION VARCHAR2 (80)
)
TABLESPACE OTHER_TBL_TBS
NOCACHE
NOPARALLEL
LOGGING
/

CREATE TABLE ADS_IMAGET
(
  IMAGEID NUMBER (11,0) DEFAULT 0,
  IMAGEFILENAME VARCHAR2 (50) NOT NULL,
  SLIDEID VARCHAR2 (50),
  USERNAME VARCHAR2 (50),
  IMAGETYPE VARCHAR2 (40),
  MAGNIFICATION VARCHAR2 (20),
  BESTIMAGE NUMBER (1,0) DEFAULT 0 NOT NULL,
  CAPTUREDATE DATE NOT NULL,
  IMAGENOTES VARCHAR2 (255),
  IMAGEPATH VARCHAR2 (255),
  TNAILPATH VARCHAR2 (255),
  OVERLAY VARCHAR2 (255),
  IMAGEONREPORT NUMBER (1,0) DEFAULT 0 NOT NULL,
  SEARCHQRY NUMBER (1,0) DEFAULT 0 NOT NULL,
  PATHOLOGIST_ID VARCHAR2 (10)
)
TABLESPACE OTHER_TBL_TBS
NOCACHE
NOPARALLEL
LOGGING
/

CREATE TABLE ADS_NEWNUMBER
(
  NUMBERID NUMBER (11,0) DEFAULT 0 NOT NULL,
  CHANGEFIELD VARCHAR2 (50)
)
TABLESPACE OTHER_TBL_TBS
NOCACHE
NOPARALLEL
LOGGING
/

CREATE TABLE ADS_PDCDATAT
(
  SLIDEID VARCHAR2 (50) NOT NULL
)
TABLESPACE OTHER_TBL_TBS
NOCACHE
NOPARALLEL
LOGGING
/

CREATE TABLE ADS_STATIONPARMST
(
  STATIONID VARCHAR2 (20) NOT NULL,
  SNIFFIN VARCHAR2 (255),
  SNIFFOUT VARCHAR2 (255),
  WEBSERVER VARCHAR2 (255),
  ROOT VARCHAR2 (10),
  LOCLETTER VARCHAR2 (20),
  NETLETTER VARCHAR2 (20),
  PIXOLE VARCHAR2 (255),
  OVERLAY VARCHAR2 (255),
  IMAGES VARCHAR2 (255),
  TNAILS VARCHAR2 (255),
  STARTIN VARCHAR2 (75),
  QREPORT VARCHAR2 (75),
  FREPORT VARCHAR2 (75),
  ATTACH VARCHAR2 (255),
  USERNAME VARCHAR2 (50),
  USERLEVEL VARCHAR2 (50),
  ALIAS VARCHAR2 (20),
  SMTPHOST VARCHAR2 (50),
  SMTPFROM VARCHAR2 (255),
  LOG VARCHAR2 (25),
  LASTJOBYEAR NUMBER (5,0),
  LASTJOBNUMBER NUMBER (5,0),
  SEMOEM VARCHAR2 (255),
  WEBHUNTERADDRESS VARCHAR2 (50),
  CURRENTPROJECTNUMBER VARCHAR2 (10),
  CURRENTUSERNAME VARCHAR2 (50),
  COMMPORT VARCHAR2 (4),
  ODBC VARCHAR2 (255)
)
TABLESPACE OTHER_TBL_TBS
NOCACHE
NOPARALLEL
LOGGING
/

CREATE TABLE ADS_SUPPORTDATA
(
  ELEMENTGROUP VARCHAR2 (50) NOT NULL,
  ELEMENT VARCHAR2 (50) NOT NULL,
  ELEMENTORDER NUMBER (11,0) DEFAULT 0,
  DROPDOWNNUMBER NUMBER (11,0) DEFAULT 0
)
TABLESPACE OTHER_TBL_TBS
NOCACHE
NOPARALLEL
LOGGING
/

CREATE TABLE ADS_SYSPARMST
(
  NEXTPICNUM VARCHAR2 (50) NOT NULL
)
TABLESPACE OTHER_TBL_TBS
NOCACHE
NOPARALLEL
LOGGING
/

CREATE TABLE ADS_USERT
(
  USERNAME VARCHAR2 (50) NOT NULL,
  USERLEVEL VARCHAR2 (50),
  PASSWORD VARCHAR2 (50),
  PASSWORDEXPIRE DATE
)
TABLESPACE OTHER_TBL_TBS
NOCACHE
NOPARALLEL
LOGGING
/

/* =============================================================================== */
/* Sequences                                                                       */
/* =============================================================================== */

CREATE SEQUENCE ADS_IMAGEFILE_SEQ
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 1E27
  MINVALUE 0
  CACHE 20
  NOCYCLE
  NOORDER
/

CREATE SEQUENCE ADS_NEXTNUMBER_SEQ
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 1E27
  MINVALUE 0
  CACHE 20
  NOCYCLE
  NOORDER
/

CREATE SEQUENCE SEQ_6102_1
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 1E27
  MINVALUE 1
  CACHE 20
  NOCYCLE
  NOORDER
/

CREATE SEQUENCE SEQ_6106_1
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 1E27
  MINVALUE 1
  CACHE 20
  NOCYCLE
  NOORDER
/

CREATE SEQUENCE SEQ_6108_1
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 1E27
  MINVALUE 1
  CACHE 20
  NOCYCLE
  NOORDER
/

/* =============================================================================== */
/* Comments                                                                        */
/* =============================================================================== */


/* =============================================================================== */
/* Indexes                                                                         */
/* =============================================================================== */

CREATE INDEX ADS_IMAGET_IN
ON ADS_IMAGET
(
  IMAGEID 
)
TABLESPACE OTHER_IDX_TBS
NOLOGGING
/

CREATE INDEX ADS_IMAGET_IN2
ON ADS_IMAGET
(
  SLIDEID 
)
TABLESPACE OTHER_IDX_TBS
LOGGING
/

CREATE INDEX ADS_STATIONPARMST_IN
ON ADS_STATIONPARMST
(
  USERLEVEL 
)
TABLESPACE OTHER_IDX_TBS
NOLOGGING
/

/* =============================================================================== */
/* Primary Key and Unique Constraints                                              */
/* =============================================================================== */

ALTER TABLE ADS_ATTACHT ADD
(
  CONSTRAINT PK_ADS_ATTACHT
  PRIMARY KEY
  (
    ATTACHID
  )
  NOT DEFERRABLE INITIALLY IMMEDIATE
  USING INDEX 
  TABLESPACE OTHER_IDX_TBS
  ENABLE
)
/

ALTER TABLE ADS_IMAGET ADD
(
  CONSTRAINT PK_ADS_IMAGET
  PRIMARY KEY
  (
    IMAGEFILENAME
  )
  NOT DEFERRABLE INITIALLY IMMEDIATE
  USING INDEX 
  TABLESPACE OTHER_IDX_TBS
  ENABLE
)
/

ALTER TABLE ADS_NEWNUMBER ADD
(
  CONSTRAINT PK_ADS_NEWNUMBER
  PRIMARY KEY
  (
    NUMBERID
  )
  NOT DEFERRABLE INITIALLY IMMEDIATE
  USING INDEX 
  TABLESPACE OTHER_IDX_TBS
  ENABLE
)
/

ALTER TABLE ADS_PDCDATAT ADD
(
  CONSTRAINT PK_ADS_PDCDATAT
  PRIMARY KEY
  (
    SLIDEID
  )
  NOT DEFERRABLE INITIALLY IMMEDIATE
  USING INDEX 
  TABLESPACE OTHER_IDX_TBS
  ENABLE
)
/

ALTER TABLE ADS_STATIONPARMST ADD
(
  CONSTRAINT PK_ADS_STATIONPARMST
  PRIMARY KEY
  (
    STATIONID
  )
  NOT DEFERRABLE INITIALLY IMMEDIATE
  USING INDEX 
  TABLESPACE OTHER_IDX_TBS
  ENABLE
)
/

ALTER TABLE ADS_SUPPORTDATA ADD
(
  CONSTRAINT PK_ADS_SUPPORTDATA
  PRIMARY KEY
  (
    ELEMENTGROUP,
    ELEMENT
  )
  NOT DEFERRABLE INITIALLY IMMEDIATE
  USING INDEX 
  TABLESPACE OTHER_IDX_TBS
  ENABLE
)
/

ALTER TABLE ADS_SYSPARMST ADD
(
  CONSTRAINT PK_ADS_SYSPARMST
  PRIMARY KEY
  (
    NEXTPICNUM
  )
  NOT DEFERRABLE INITIALLY IMMEDIATE
  USING INDEX 
  TABLESPACE OTHER_IDX_TBS
  ENABLE
)
/

ALTER TABLE ADS_USERT ADD
(
  CONSTRAINT PK_ADS_USERT
  PRIMARY KEY
  (
    USERNAME
  )
  NOT DEFERRABLE INITIALLY IMMEDIATE
  USING INDEX 
  TABLESPACE OTHER_IDX_TBS
  ENABLE
)
/

------------------------------------------------------------
-------------   End Schema DDL  ----------------------------
------------------------------------------------------------

/* =============================================================================== */
/* Compile Schema                                                                  */
/* =============================================================================== */

/*
BEGIN
  DBMS_UTILITY.COMPILE_SCHEMA (USER);
END;
/
*/

