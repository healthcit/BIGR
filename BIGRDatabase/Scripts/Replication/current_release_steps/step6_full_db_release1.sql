spool step6_full_db_release1.log

DROP TRIGGER LIMS_PATH_EVAL_UPDATE_RNA
/
prompt
prompt ==========   MR8434   ========== 

DROP TABLE CIR_FORM cascade constraints;

CREATE TABLE CIR_FORM_DEFINITION
(
  FORM_DEFINITION_ID         VARCHAR2(10)  NOT NULL,
  FORM_NAME                  VARCHAR2(256) NOT NULL,
  FORM_DEFINITION            CLOB          NOT NULL,
  FORM_DEFINITION_ENCODING   VARCHAR2(256) NOT NULL,
  ENABLED_YN                 VARCHAR2(1)   NOT NULL
)
TABLESPACE CIR_TBL_TBS
STORAGE (INITIAL     1M  
         NEXT        1M 
            MINEXTENTS     1)
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING
/

ALTER TABLE CIR_FORM_DEFINITION ADD CONSTRAINT PK_CIR_FORM_DEFINITION PRIMARY KEY (FORM_DEFINITION_ID)
USING INDEX TABLESPACE CIR_IDX_TBS
/


CREATE TABLE CIR_FORM_DEFINITION_TAGS
(
  ID                         NUMBER        NOT NULL,
  FORM_DEFINITION_ID         VARCHAR2(10)  NOT NULL,
  TAG                        VARCHAR2(256) NOT NULL
)
TABLESPACE CIR_TBL_TBS
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING
/


ALTER TABLE CIR_FORM_DEFINITION_TAGS ADD CONSTRAINT PK_CIR_FORM_DEFINITION_TAGS PRIMARY KEY (ID)
USING INDEX TABLESPACE CIR_IDX_TBS
/

ALTER TABLE CIR_FORM_DEFINITION_TAGS ADD CONSTRAINT FK_CIR_FORM_DEF_FORM_TAGS FOREIGN KEY (FORM_DEFINITION_ID) REFERENCES CIR_FORM_DEFINITION (FORM_DEFINITION_ID);


CREATE TABLE CIR_FORM
(
  FORM_ID             VARCHAR2(10)   NOT NULL,
  DOMAIN_OBJECT_ID    VARCHAR2(256),
  DOMAIN_OBJECT_TYPE  VARCHAR2(256),
  CREATION_DATETIME   DATE,
  FORM_DEFINITION_ID  VARCHAR2(10)   NOT NULL
)
TABLESPACE CIR_TBL_TBS
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING
/


ALTER TABLE CIR_FORM ADD CONSTRAINT PK_CIR_FORM PRIMARY KEY (FORM_ID)
USING INDEX TABLESPACE CIR_IDX_TBS
/

ALTER TABLE CIR_FORM ADD CONSTRAINT FK_CIR_FORM_FORM_DEF FOREIGN KEY (FORM_DEFINITION_ID) REFERENCES CIR_FORM_DEFINITION (FORM_DEFINITION_ID);


CREATE SEQUENCE CIR_FORM_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE NOCYCLE CACHE 20;

CREATE SEQUENCE CIR_FORM_DEFINITION_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE NOCYCLE CACHE 20;

CREATE SEQUENCE CIR_FORM_DEFINITION_TAGS_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE NOCYCLE CACHE 20;

prompt ==========   END MR8434   ==========  
--
--   
prompt 

prompt ==========   MR8383   ========== 
ALTER TABLE ES_SHOPPING_CART_DETAIL DROP (PRODUCT_ID)
/

commit
/
 
prompt ==========   END MR8383   ==========   
prompt
prompt ==========   MR8374   ========== 
-- DDL
-- DML Continues below

drop sequence ORM_WORKGROUP_SEQ;

--add new columns on account table

alter table es_ardais_account add ACCOUNT_DATA CLOB;

alter table es_ardais_account add ACCOUNT_DATA_ENCODING VARCHAR2(255);

--add new column to ard_objects table to eventually become primary key

alter table ard_objects add privilege_id varchar2(21);

--removal of obsolete columns on es_ardais_user

alter table es_ardais_user drop column ARDAIS_EMPLOYEE_IND;

alter table es_ardais_user drop column ARDAIS_PRIMARY_ACCT_MGR_IND;

alter table es_ardais_user drop column ARDAIS_USER_TYPE;

alter table es_ardais_user drop column ABILITY_TO_APPROVE_IND;

alter table es_ardais_user drop column USER_LEVEL_CODE;

alter table es_ardais_user drop column PUR_OFF_CONTRACT_IND;

commit
/

prompt ==========   END MR8374   ==========   
prompt
prompt
prompt ==========   MR8535   ========== 
alter table es_ardais_user add user_department varchar2(200);
commit
/
  
prompt ==========   END MR8535 ==========   
prompt
prompt ==========   MR8464   ========== 

prompt  From Cir_drop.sql script


REM ************************************************************************************
REM * ClinPres DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_CLIN_PRES_NOTE;
DROP TABLE CIR_CLIN_PRES_MULTI;
DROP TABLE CIR_CLIN_PRES;


REM ************************************************************************************
REM * ClinDisProg DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_CLIN_DIS_PROG_NOTE;
DROP TABLE CIR_CLIN_DIS_PROG_MULTI;
DROP TABLE CIR_CLIN_DIS_PROG;



REM ************************************************************************************
REM * Treatment DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_TREATMENT_NOTE;
DROP TABLE CIR_TREATMENT_MULTI;
DROP TABLE CIR_TREATMENT;


REM ************************************************************************************
REM * AncillaryStudies DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_ANCILLARY_STUDIES_NOTE;
DROP TABLE CIR_ANCILLARY_STUDIES;


REM ************************************************************************************
REM * BlooGaseArte DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_BLOO_GASE_ARTE_NOTE;
DROP TABLE CIR_BLOO_GASE_ARTE;

REM ************************************************************************************
REM * ChemistrySurvey DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_CHEMISTRY_SURVEY_NOTE;
DROP TABLE CIR_CHEMISTRY_SURVEY;

REM ************************************************************************************
REM * Chemistry DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_CHEMISTRY_NOTE;
DROP TABLE CIR_CHEMISTRY;

REM ************************************************************************************
REM * ChromosomeStudies DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_CHROMOSOME_STUDIES_NOTE;
DROP TABLE CIR_CHROMOSOME_STUDIES_MULTI;
DROP TABLE CIR_CHROMOSOME_STUDIES;

REM ************************************************************************************
REM * Coagulation DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_COAGULATION_NOTE;
DROP TABLE CIR_COAGULATION;

REM ************************************************************************************
REM * Cytology DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_CYTOLOGY_NOTE;
DROP TABLE CIR_CYTOLOGY;

REM ************************************************************************************
REM * GeneStudies DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_GENE_STUDIES_NOTE;
DROP TABLE CIR_GENE_STUDIES_MULTI;
DROP TABLE CIR_GENE_STUDIES;

REM ************************************************************************************
REM * HematologySurvey DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_HEMATOLOGY_SURVEY_NOTE;
DROP TABLE CIR_HEMATOLOGY_SURVEY;

REM ************************************************************************************
REM * Hematology DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_HEMATOLOGY_NOTE;
DROP TABLE CIR_HEMATOLOGY;

REM ************************************************************************************
REM * Histochemistry DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_HISTOCHEMISTRY_NOTE;
DROP TABLE CIR_HISTOCHEMISTRY;

REM ************************************************************************************
REM * Immunohistochemistry DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_IMMUNOHISTOCHEMISTRY_NOTE;
DROP TABLE CIR_IMMUNOHISTOCHEMISTRY_MULTI;
DROP TABLE CIR_IMMUNOHISTOCHEMISTRY;

REM ************************************************************************************
REM * Immunophenotype DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_IMMUNOPHENOTYPE_NOTE;
DROP TABLE CIR_IMMUNOPHENOTYPE;

REM ************************************************************************************
REM * Microbiology DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_MICROBIOLOGY_NOTE;
DROP TABLE CIR_MICROBIOLOGY;

REM ************************************************************************************
REM * Stains DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_STAINS_NOTE;
DROP TABLE CIR_STAINS;

REM ************************************************************************************
REM * Urinalysis DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_URINALYSIS_NOTE;
DROP TABLE CIR_URINALYSIS_MULTI;
DROP TABLE CIR_URINALYSIS;

REM ************************************************************************************
REM * ImagingStudies DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_IMAGING_STUDIES_NOTE;
DROP TABLE CIR_IMAGING_STUDIES;

REM ************************************************************************************
REM * SkinTests DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_SKIN_TESTS_NOTE;
DROP TABLE CIR_SKIN_TESTS;

REM ************************************************************************************
REM * SurgicalStudies DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_SURGICAL_STUDIES_NOTE;
DROP TABLE CIR_SURGICAL_STUDIES;


REM ************************************************************************************
REM * PastMediHist DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_PAST_MEDI_HIST_NOTE;
DROP TABLE CIR_PAST_MEDI_HIST_MULTI;
DROP TABLE CIR_PAST_MEDI_HIST;


REM ************************************************************************************
REM * SurgicalPathology DROP SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
DROP TABLE CIR_SURGICAL_PATHOLOGY_NOTE;
DROP TABLE CIR_SURGICAL_PATHOLOGY_MULTI;
DROP TABLE CIR_SURGICAL_PATHOLOGY;



DROP TABLE CIR_DATE_ADE;
DROP TABLE CIR_EXPOSURE_HX_ADE;
DROP TABLE CIR_GENDER_ADE;
DROP TABLE CIR_HCU_ADE;
DROP TABLE CIR_HCU_ADE_MULTI;
DROP TABLE CIR_HRT_ADE;
DROP TABLE CIR_HRT_ADE_MULTI;
DROP TABLE CIR_PHYSICAL_ADE;
DROP TABLE CIR_CHEMO_ADE;
DROP TABLE CIR_MEDICATION_ADE;
DROP TABLE CIR_TREATMENT_ADE;
DROP TABLE CIR_RADIOTHERAPY_ADE;
DROP TABLE CIR_RADIOTHERAPY_ADE_MULTI;
DROP TABLE CIR_SURGERY_ADE;
DROP TABLE CIR_TRANSPLANT_ADE;
DROP TABLE CIR_STUDY_REPORT_ADE;
DROP TABLE CIR_OBJ_STUDY_REPORT_ADE;
DROP TABLE CIR_LAB_NUMERIC_ADE;
DROP TABLE CIR_LAB_TECHNIQUE_ADE;
DROP TABLE CIR_LAB_MICRO_ADE;
DROP TABLE CIR_LAB_MICRO_ADE_MULTI;
DROP TABLE CIR_ALCOHOL_HX_ADE;
DROP TABLE CIR_TOBACCO_HX_ADE;
DROP TABLE CIR_OCCUPATION_ADE;
DROP TABLE CIR_CANCER_HX_ADE;
DROP TABLE CIR_CANCER_HX_ADE_MULTI;
DROP TABLE CIR_DIABETES_HX_ADE;
DROP TABLE CIR_DIABETES_HX_ADE_MULTI;
DROP TABLE CIR_HYPERTENSION_HX_ADE;
DROP TABLE CIR_PAST_CONDITIONS_ADE;
DROP TABLE CIR_FAMILY_HX_ADE;
DROP TABLE CIR_CHEMO_HX_ADE;
DROP TABLE CIR_RADIOTHERAPY_HX_ADE;
DROP TABLE CIR_DETERMIN_ADE;

DROP SEQUENCE CIR_SEQ;
DROP TABLE CIR_EOCE_NOTE;
DROP TABLE CIR_EOCE_MULTI;
DROP TABLE CIR_EOCE;


prompt  End of Cir_drop.sql script

drop table CIR_CRYSTALS_MULTI;
drop table CIR_CRYSTALS_NOTE;
drop table CIR_GENETIC_STUDIES_MULTI;
drop table CIR_GENETIC_STUDIES_NOTE;
drop table CIR_KARYOTYPE_MULTI;
drop table CIR_KARYOTYPE_NOTE;
drop table CIR_LABORATORY_STUDIES_MULTI;
drop table CIR_LABORATORY_STUDIES_NOTE;
drop table CIR_PLOIDY_CAT_NOTE;
drop table CIR_CRYSTALS;
drop table CIR_GENETIC_STUDIES;
drop table CIR_KARYOTYPE;
drop table CIR_LABORATORY_STUDIES;
drop table CIR_PLOIDY_CAT;
 
prompt  From Cir_create.sql
 
REM ************************************************************************************
REM * Problem CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_PROBLEM (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
,DIAGNOSIS_CUI   VARCHAR2(30)
,OTHER_DIAGNOSIS   VARCHAR2(200)
  ,AGE_DIAG   NUMBER
,AGE_DIAG_CUI   VARCHAR2(30)
  ,DATE_INIT_DIAG   DATE
,DATE_INIT_DIAG_CUI   VARCHAR2(30)
  ,VISIT_CUI   VARCHAR2(30)
    )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_PROBLEM ON CIR_PROBLEM (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_PROBLEM ADD CONSTRAINT PK_CIR_PROBLEM PRIMARY KEY (ID);

 

 
CREATE TABLE CIR_PROBLEM_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_PROBLEM_N ON CIR_PROBLEM_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_PROBLEM_NOTE ADD CONSTRAINT PK_CIR_PROBLEM_N PRIMARY KEY (ID);
ALTER TABLE CIR_PROBLEM_NOTE ADD CONSTRAINT FK_CIR_PROBLEM_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_PROBLEM (ID);

CREATE INDEX CIR_PROBLEM_N_IN1 ON CIR_PROBLEM_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * ClinPres CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_CLIN_PRES (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
,HEIGHT   NUMBER
,HEIGHT_CUI   VARCHAR2(30)
  ,WEIGHT   NUMBER
,WEIGHT_CUI   VARCHAR2(30)
  ,BMI   NUMBER
,BMI_CUI   VARCHAR2(30)
  ,BODY_SURF_AREA   NUMBER
,BODY_SURF_AREA_CUI   VARCHAR2(30)
  ,BLOO_PRES_SYST   NUMBER
,BLOO_PRES_SYST_CUI   VARCHAR2(30)
  ,BLOO_PRES_DIAS   NUMBER
,BLOO_PRES_DIAS_CUI   VARCHAR2(30)
  ,PULSE_PRESSURE   NUMBER
,PULSE_PRESSURE_CUI   VARCHAR2(30)
  ,PALPATED_PULSE   NUMBER
,PALPATED_PULSE_CUI   VARCHAR2(30)
  ,PULSE_OXIMETRY   NUMBER
,PULSE_OXIMETRY_CUI   VARCHAR2(30)
  ,WAIST_CIRCUMFERENCE   NUMBER
,WAIST_CIRCUMFERENCE_CUI   VARCHAR2(30)
  ,HEENT_CUI   VARCHAR2(30)
  ,CNS_CUI   VARCHAR2(30)
  ,NECK_CUI   VARCHAR2(30)
  ,BREASTS_CUI   VARCHAR2(30)
  ,CARDIOVASCULAR_CUI   VARCHAR2(30)
  ,RESPIRATORY_CUI   VARCHAR2(30)
  ,GASTROINTESTINAL_CUI   VARCHAR2(30)
  ,URINARY_CUI   VARCHAR2(30)
  ,GENITALIA_CUI   VARCHAR2(30)
  ,MUSCULOSKELETAL_CUI   VARCHAR2(30)
  ,NEUROLOGIC_CUI   VARCHAR2(30)
  ,DERMATOLOGIC_CUI   VARCHAR2(30)
  ,HEMA_LYMP_CUI   VARCHAR2(30)
  ,ENDO_META_CUI   VARCHAR2(30)
  ,PSYCHOLOGIC_CUI   VARCHAR2(30)
    )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CLIN_PRES ON CIR_CLIN_PRES (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CLIN_PRES ADD CONSTRAINT PK_CIR_CLIN_PRES PRIMARY KEY (ID);

 

CREATE TABLE CIR_CLIN_PRES_MULTI (
ID   NUMBER NOT NULL
,PARENT_ID   NUMBER NOT NULL
,DATA_ELEMENT_CUI   VARCHAR2(30)
,DATA_VALUE_CUI   VARCHAR2(30)
,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CLIN_PRES_M ON CIR_CLIN_PRES_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CLIN_PRES_MULTI ADD CONSTRAINT PK_CIR_CLIN_PRES_M PRIMARY KEY (ID);
ALTER TABLE CIR_CLIN_PRES_MULTI ADD CONSTRAINT FK_CIR_CLIN_PRES_M0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_CLIN_PRES (ID);

CREATE INDEX CIR_CLIN_PRES_M_IN1 ON CIR_CLIN_PRES_MULTI (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 
CREATE TABLE CIR_CLIN_PRES_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CLIN_PRES_N ON CIR_CLIN_PRES_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CLIN_PRES_NOTE ADD CONSTRAINT PK_CIR_CLIN_PRES_N PRIMARY KEY (ID);
ALTER TABLE CIR_CLIN_PRES_NOTE ADD CONSTRAINT FK_CIR_CLIN_PRES_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_CLIN_PRES (ID);

CREATE INDEX CIR_CLIN_PRES_N_IN1 ON CIR_CLIN_PRES_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
 
 
 
REM ************************************************************************************
REM * ClinDisProg CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_CLIN_DIS_PROG (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CLIN_DIS_PROG ON CIR_CLIN_DIS_PROG (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CLIN_DIS_PROG ADD CONSTRAINT PK_CIR_CLIN_DIS_PROG PRIMARY KEY (ID);

CREATE TABLE CIR_CLIN_DIS_PROG_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CLIN_DIS_PROG_E ON CIR_CLIN_DIS_PROG_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CLIN_DIS_PROG_EAV ADD CONSTRAINT PK_CIR_CLIN_DIS_PROG_E PRIMARY KEY (ID);
ALTER TABLE CIR_CLIN_DIS_PROG_EAV ADD CONSTRAINT FK_CIR_CLIN_DIS_PROG_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_CLIN_DIS_PROG (ID);

CREATE INDEX CIR_CLIN_DIS_PROG_E_IN1 ON CIR_CLIN_DIS_PROG_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

CREATE TABLE CIR_CLIN_DIS_PROG_MULTI (
ID   NUMBER NOT NULL
,PARENT_ID   NUMBER NOT NULL
,DATA_ELEMENT_CUI   VARCHAR2(30)
,DATA_VALUE_CUI   VARCHAR2(30)
,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CLIN_DIS_PROG_M ON CIR_CLIN_DIS_PROG_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CLIN_DIS_PROG_MULTI ADD CONSTRAINT PK_CIR_CLIN_DIS_PROG_M PRIMARY KEY (ID);
ALTER TABLE CIR_CLIN_DIS_PROG_MULTI ADD CONSTRAINT FK_CIR_CLIN_DIS_PROG_M0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_CLIN_DIS_PROG (ID);

CREATE INDEX CIR_CLIN_DIS_PROG_M_IN1 ON CIR_CLIN_DIS_PROG_MULTI (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 
CREATE TABLE CIR_CLIN_DIS_PROG_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CLIN_DIS_PROG_N ON CIR_CLIN_DIS_PROG_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CLIN_DIS_PROG_NOTE ADD CONSTRAINT PK_CIR_CLIN_DIS_PROG_N PRIMARY KEY (ID);
ALTER TABLE CIR_CLIN_DIS_PROG_NOTE ADD CONSTRAINT FK_CIR_CLIN_DIS_PROG_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_CLIN_DIS_PROG (ID);

CREATE INDEX CIR_CLIN_DIS_PROG_N_IN1 ON CIR_CLIN_DIS_PROG_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
 
 
 
 
 
 
REM ************************************************************************************
REM * Treatment CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_TREATMENT (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
,ELEC_THER_CUI   VARCHAR2(30)
    )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_TREATMENT ON CIR_TREATMENT (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_TREATMENT ADD CONSTRAINT PK_CIR_TREATMENT PRIMARY KEY (ID);

 

CREATE TABLE CIR_TREATMENT_MULTI (
ID   NUMBER NOT NULL
,PARENT_ID   NUMBER NOT NULL
,DATA_ELEMENT_CUI   VARCHAR2(30)
,DATA_VALUE_CUI   VARCHAR2(30)
,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_TREATMENT_M ON CIR_TREATMENT_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_TREATMENT_MULTI ADD CONSTRAINT PK_CIR_TREATMENT_M PRIMARY KEY (ID);
ALTER TABLE CIR_TREATMENT_MULTI ADD CONSTRAINT FK_CIR_TREATMENT_M0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_TREATMENT (ID);

CREATE INDEX CIR_TREATMENT_M_IN1 ON CIR_TREATMENT_MULTI (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 
CREATE TABLE CIR_TREATMENT_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_TREATMENT_N ON CIR_TREATMENT_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_TREATMENT_NOTE ADD CONSTRAINT PK_CIR_TREATMENT_N PRIMARY KEY (ID);
ALTER TABLE CIR_TREATMENT_NOTE ADD CONSTRAINT FK_CIR_TREATMENT_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_TREATMENT (ID);

CREATE INDEX CIR_TREATMENT_N_IN1 ON CIR_TREATMENT_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
 
 
 
 
 
REM ************************************************************************************
REM * AncillaryStudies CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_ANCILLARY_STUDIES (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_ANCILLARY_STUDIES ON CIR_ANCILLARY_STUDIES (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_ANCILLARY_STUDIES ADD CONSTRAINT PK_CIR_ANCILLARY_STUDIES PRIMARY KEY (ID);

CREATE TABLE CIR_ANCILLARY_STUDIES_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_ANCILLARY_STUDIES_E ON CIR_ANCILLARY_STUDIES_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_ANCILLARY_STUDIES_EAV ADD CONSTRAINT PK_CIR_ANCILLARY_STUDIES_E PRIMARY KEY (ID);
ALTER TABLE CIR_ANCILLARY_STUDIES_EAV ADD CONSTRAINT FK_CIR_ANCILLARY_STUDIES_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_ANCILLARY_STUDIES (ID);

CREATE INDEX CIR_ANCILLARY_STUDIES_E_IN1 ON CIR_ANCILLARY_STUDIES_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_ANCILLARY_STUDIES_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_ANCILLARY_STUDIES_N ON CIR_ANCILLARY_STUDIES_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_ANCILLARY_STUDIES_NOTE ADD CONSTRAINT PK_CIR_ANCILLARY_STUDIES_N PRIMARY KEY (ID);
ALTER TABLE CIR_ANCILLARY_STUDIES_NOTE ADD CONSTRAINT FK_CIR_ANCILLARY_STUDIES_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_ANCILLARY_STUDIES (ID);

CREATE INDEX CIR_ANCILLARY_STUDIES_N_IN1 ON CIR_ANCILLARY_STUDIES_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
 
 
 
 
 
 
 
 
 
 
REM ************************************************************************************
REM * BlooGaseArte CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_BLOO_GASE_ARTE (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_BLOO_GASE_ARTE ON CIR_BLOO_GASE_ARTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_BLOO_GASE_ARTE ADD CONSTRAINT PK_CIR_BLOO_GASE_ARTE PRIMARY KEY (ID);

CREATE TABLE CIR_BLOO_GASE_ARTE_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_BLOO_GASE_ARTE_E ON CIR_BLOO_GASE_ARTE_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_BLOO_GASE_ARTE_EAV ADD CONSTRAINT PK_CIR_BLOO_GASE_ARTE_E PRIMARY KEY (ID);
ALTER TABLE CIR_BLOO_GASE_ARTE_EAV ADD CONSTRAINT FK_CIR_BLOO_GASE_ARTE_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_BLOO_GASE_ARTE (ID);

CREATE INDEX CIR_BLOO_GASE_ARTE_E_IN1 ON CIR_BLOO_GASE_ARTE_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_BLOO_GASE_ARTE_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_BLOO_GASE_ARTE_N ON CIR_BLOO_GASE_ARTE_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_BLOO_GASE_ARTE_NOTE ADD CONSTRAINT PK_CIR_BLOO_GASE_ARTE_N PRIMARY KEY (ID);
ALTER TABLE CIR_BLOO_GASE_ARTE_NOTE ADD CONSTRAINT FK_CIR_BLOO_GASE_ARTE_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_BLOO_GASE_ARTE (ID);

CREATE INDEX CIR_BLOO_GASE_ARTE_N_IN1 ON CIR_BLOO_GASE_ARTE_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * ChemistrySurvey CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_CHEMISTRY_SURVEY (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHEMISTRY_SURVEY ON CIR_CHEMISTRY_SURVEY (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHEMISTRY_SURVEY ADD CONSTRAINT PK_CIR_CHEMISTRY_SURVEY PRIMARY KEY (ID);

CREATE TABLE CIR_CHEMISTRY_SURVEY_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHEMISTRY_SURVEY_E ON CIR_CHEMISTRY_SURVEY_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHEMISTRY_SURVEY_EAV ADD CONSTRAINT PK_CIR_CHEMISTRY_SURVEY_E PRIMARY KEY (ID);
ALTER TABLE CIR_CHEMISTRY_SURVEY_EAV ADD CONSTRAINT FK_CIR_CHEMISTRY_SURVEY_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_CHEMISTRY_SURVEY (ID);

CREATE INDEX CIR_CHEMISTRY_SURVEY_E_IN1 ON CIR_CHEMISTRY_SURVEY_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_CHEMISTRY_SURVEY_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHEMISTRY_SURVEY_N ON CIR_CHEMISTRY_SURVEY_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHEMISTRY_SURVEY_NOTE ADD CONSTRAINT PK_CIR_CHEMISTRY_SURVEY_N PRIMARY KEY (ID);
ALTER TABLE CIR_CHEMISTRY_SURVEY_NOTE ADD CONSTRAINT FK_CIR_CHEMISTRY_SURVEY_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_CHEMISTRY_SURVEY (ID);

CREATE INDEX CIR_CHEMISTRY_SURVEY_N_IN1 ON CIR_CHEMISTRY_SURVEY_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Chemistry CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_CHEMISTRY (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHEMISTRY ON CIR_CHEMISTRY (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHEMISTRY ADD CONSTRAINT PK_CIR_CHEMISTRY PRIMARY KEY (ID);

CREATE TABLE CIR_CHEMISTRY_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHEMISTRY_E ON CIR_CHEMISTRY_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHEMISTRY_EAV ADD CONSTRAINT PK_CIR_CHEMISTRY_E PRIMARY KEY (ID);
ALTER TABLE CIR_CHEMISTRY_EAV ADD CONSTRAINT FK_CIR_CHEMISTRY_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_CHEMISTRY (ID);

CREATE INDEX CIR_CHEMISTRY_E_IN1 ON CIR_CHEMISTRY_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_CHEMISTRY_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHEMISTRY_N ON CIR_CHEMISTRY_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHEMISTRY_NOTE ADD CONSTRAINT PK_CIR_CHEMISTRY_N PRIMARY KEY (ID);
ALTER TABLE CIR_CHEMISTRY_NOTE ADD CONSTRAINT FK_CIR_CHEMISTRY_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_CHEMISTRY (ID);

CREATE INDEX CIR_CHEMISTRY_N_IN1 ON CIR_CHEMISTRY_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * ChromosomeStudies CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_CHROMOSOME_STUDIES (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHROMOSOME_STUDIES ON CIR_CHROMOSOME_STUDIES (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHROMOSOME_STUDIES ADD CONSTRAINT PK_CIR_CHROMOSOME_STUDIES PRIMARY KEY (ID);

CREATE TABLE CIR_CHROMOSOME_STUDIES_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHROMOSOME_STUDIES_E ON CIR_CHROMOSOME_STUDIES_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHROMOSOME_STUDIES_EAV ADD CONSTRAINT PK_CIR_CHROMOSOME_STUDIES_E PRIMARY KEY (ID);
ALTER TABLE CIR_CHROMOSOME_STUDIES_EAV ADD CONSTRAINT FK_CIR_CHROMOSOME_STUDIES_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_CHROMOSOME_STUDIES (ID);

CREATE INDEX CIR_CHROMOSOME_STUDIES_E_IN1 ON CIR_CHROMOSOME_STUDIES_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

CREATE TABLE CIR_CHROMOSOME_STUDIES_MULTI (
ID   NUMBER NOT NULL
,PARENT_ID   NUMBER NOT NULL
,DATA_ELEMENT_CUI   VARCHAR2(30)
,DATA_VALUE_CUI   VARCHAR2(30)
,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHROMOSOME_STUDIES_M ON CIR_CHROMOSOME_STUDIES_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHROMOSOME_STUDIES_MULTI ADD CONSTRAINT PK_CIR_CHROMOSOME_STUDIES_M PRIMARY KEY (ID);
ALTER TABLE CIR_CHROMOSOME_STUDIES_MULTI ADD CONSTRAINT FK_CIR_CHROMOSOME_STUDIES_M0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_CHROMOSOME_STUDIES (ID);

CREATE INDEX CIR_CHROMOSOME_STUDIES_M_IN1 ON CIR_CHROMOSOME_STUDIES_MULTI (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 
CREATE TABLE CIR_CHROMOSOME_STUDIES_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHROMOSOME_STUDIES_N ON CIR_CHROMOSOME_STUDIES_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHROMOSOME_STUDIES_NOTE ADD CONSTRAINT PK_CIR_CHROMOSOME_STUDIES_N PRIMARY KEY (ID);
ALTER TABLE CIR_CHROMOSOME_STUDIES_NOTE ADD CONSTRAINT FK_CIR_CHROMOSOME_STUDIES_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_CHROMOSOME_STUDIES (ID);

CREATE INDEX CIR_CHROMOSOME_STUDIES_N_IN1 ON CIR_CHROMOSOME_STUDIES_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Coagulation CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_COAGULATION (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_COAGULATION ON CIR_COAGULATION (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_COAGULATION ADD CONSTRAINT PK_CIR_COAGULATION PRIMARY KEY (ID);

CREATE TABLE CIR_COAGULATION_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_COAGULATION_E ON CIR_COAGULATION_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_COAGULATION_EAV ADD CONSTRAINT PK_CIR_COAGULATION_E PRIMARY KEY (ID);
ALTER TABLE CIR_COAGULATION_EAV ADD CONSTRAINT FK_CIR_COAGULATION_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_COAGULATION (ID);

CREATE INDEX CIR_COAGULATION_E_IN1 ON CIR_COAGULATION_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_COAGULATION_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_COAGULATION_N ON CIR_COAGULATION_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_COAGULATION_NOTE ADD CONSTRAINT PK_CIR_COAGULATION_N PRIMARY KEY (ID);
ALTER TABLE CIR_COAGULATION_NOTE ADD CONSTRAINT FK_CIR_COAGULATION_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_COAGULATION (ID);

CREATE INDEX CIR_COAGULATION_N_IN1 ON CIR_COAGULATION_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Cytology CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_CYTOLOGY (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CYTOLOGY ON CIR_CYTOLOGY (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CYTOLOGY ADD CONSTRAINT PK_CIR_CYTOLOGY PRIMARY KEY (ID);

CREATE TABLE CIR_CYTOLOGY_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CYTOLOGY_E ON CIR_CYTOLOGY_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CYTOLOGY_EAV ADD CONSTRAINT PK_CIR_CYTOLOGY_E PRIMARY KEY (ID);
ALTER TABLE CIR_CYTOLOGY_EAV ADD CONSTRAINT FK_CIR_CYTOLOGY_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_CYTOLOGY (ID);

CREATE INDEX CIR_CYTOLOGY_E_IN1 ON CIR_CYTOLOGY_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_CYTOLOGY_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CYTOLOGY_N ON CIR_CYTOLOGY_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CYTOLOGY_NOTE ADD CONSTRAINT PK_CIR_CYTOLOGY_N PRIMARY KEY (ID);
ALTER TABLE CIR_CYTOLOGY_NOTE ADD CONSTRAINT FK_CIR_CYTOLOGY_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_CYTOLOGY (ID);

CREATE INDEX CIR_CYTOLOGY_N_IN1 ON CIR_CYTOLOGY_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * GeneStudies CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_GENE_STUDIES (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_GENE_STUDIES ON CIR_GENE_STUDIES (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_GENE_STUDIES ADD CONSTRAINT PK_CIR_GENE_STUDIES PRIMARY KEY (ID);

CREATE TABLE CIR_GENE_STUDIES_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_GENE_STUDIES_E ON CIR_GENE_STUDIES_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_GENE_STUDIES_EAV ADD CONSTRAINT PK_CIR_GENE_STUDIES_E PRIMARY KEY (ID);
ALTER TABLE CIR_GENE_STUDIES_EAV ADD CONSTRAINT FK_CIR_GENE_STUDIES_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_GENE_STUDIES (ID);

CREATE INDEX CIR_GENE_STUDIES_E_IN1 ON CIR_GENE_STUDIES_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

CREATE TABLE CIR_GENE_STUDIES_MULTI (
ID   NUMBER NOT NULL
,PARENT_ID   NUMBER NOT NULL
,DATA_ELEMENT_CUI   VARCHAR2(30)
,DATA_VALUE_CUI   VARCHAR2(30)
,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_GENE_STUDIES_M ON CIR_GENE_STUDIES_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_GENE_STUDIES_MULTI ADD CONSTRAINT PK_CIR_GENE_STUDIES_M PRIMARY KEY (ID);
ALTER TABLE CIR_GENE_STUDIES_MULTI ADD CONSTRAINT FK_CIR_GENE_STUDIES_M0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_GENE_STUDIES (ID);

CREATE INDEX CIR_GENE_STUDIES_M_IN1 ON CIR_GENE_STUDIES_MULTI (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 
CREATE TABLE CIR_GENE_STUDIES_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_GENE_STUDIES_N ON CIR_GENE_STUDIES_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_GENE_STUDIES_NOTE ADD CONSTRAINT PK_CIR_GENE_STUDIES_N PRIMARY KEY (ID);
ALTER TABLE CIR_GENE_STUDIES_NOTE ADD CONSTRAINT FK_CIR_GENE_STUDIES_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_GENE_STUDIES (ID);

CREATE INDEX CIR_GENE_STUDIES_N_IN1 ON CIR_GENE_STUDIES_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * HematologySurvey CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_HEMATOLOGY_SURVEY (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HEMATOLOGY_SURVEY ON CIR_HEMATOLOGY_SURVEY (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HEMATOLOGY_SURVEY ADD CONSTRAINT PK_CIR_HEMATOLOGY_SURVEY PRIMARY KEY (ID);

CREATE TABLE CIR_HEMATOLOGY_SURVEY_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HEMATOLOGY_SURVEY_E ON CIR_HEMATOLOGY_SURVEY_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HEMATOLOGY_SURVEY_EAV ADD CONSTRAINT PK_CIR_HEMATOLOGY_SURVEY_E PRIMARY KEY (ID);
ALTER TABLE CIR_HEMATOLOGY_SURVEY_EAV ADD CONSTRAINT FK_CIR_HEMATOLOGY_SURVEY_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_HEMATOLOGY_SURVEY (ID);

CREATE INDEX CIR_HEMATOLOGY_SURVEY_E_IN1 ON CIR_HEMATOLOGY_SURVEY_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_HEMATOLOGY_SURVEY_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HEMATOLOGY_SURVEY_N ON CIR_HEMATOLOGY_SURVEY_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HEMATOLOGY_SURVEY_NOTE ADD CONSTRAINT PK_CIR_HEMATOLOGY_SURVEY_N PRIMARY KEY (ID);
ALTER TABLE CIR_HEMATOLOGY_SURVEY_NOTE ADD CONSTRAINT FK_CIR_HEMATOLOGY_SURVEY_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_HEMATOLOGY_SURVEY (ID);

CREATE INDEX CIR_HEMATOLOGY_SURVEY_N_IN1 ON CIR_HEMATOLOGY_SURVEY_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Hematology CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_HEMATOLOGY (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HEMATOLOGY ON CIR_HEMATOLOGY (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HEMATOLOGY ADD CONSTRAINT PK_CIR_HEMATOLOGY PRIMARY KEY (ID);

CREATE TABLE CIR_HEMATOLOGY_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HEMATOLOGY_E ON CIR_HEMATOLOGY_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HEMATOLOGY_EAV ADD CONSTRAINT PK_CIR_HEMATOLOGY_E PRIMARY KEY (ID);
ALTER TABLE CIR_HEMATOLOGY_EAV ADD CONSTRAINT FK_CIR_HEMATOLOGY_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_HEMATOLOGY (ID);

CREATE INDEX CIR_HEMATOLOGY_E_IN1 ON CIR_HEMATOLOGY_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_HEMATOLOGY_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HEMATOLOGY_N ON CIR_HEMATOLOGY_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HEMATOLOGY_NOTE ADD CONSTRAINT PK_CIR_HEMATOLOGY_N PRIMARY KEY (ID);
ALTER TABLE CIR_HEMATOLOGY_NOTE ADD CONSTRAINT FK_CIR_HEMATOLOGY_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_HEMATOLOGY (ID);

CREATE INDEX CIR_HEMATOLOGY_N_IN1 ON CIR_HEMATOLOGY_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Histochemistry CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_HISTOCHEMISTRY (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HISTOCHEMISTRY ON CIR_HISTOCHEMISTRY (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HISTOCHEMISTRY ADD CONSTRAINT PK_CIR_HISTOCHEMISTRY PRIMARY KEY (ID);

CREATE TABLE CIR_HISTOCHEMISTRY_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HISTOCHEMISTRY_E ON CIR_HISTOCHEMISTRY_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HISTOCHEMISTRY_EAV ADD CONSTRAINT PK_CIR_HISTOCHEMISTRY_E PRIMARY KEY (ID);
ALTER TABLE CIR_HISTOCHEMISTRY_EAV ADD CONSTRAINT FK_CIR_HISTOCHEMISTRY_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_HISTOCHEMISTRY (ID);

CREATE INDEX CIR_HISTOCHEMISTRY_E_IN1 ON CIR_HISTOCHEMISTRY_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_HISTOCHEMISTRY_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HISTOCHEMISTRY_N ON CIR_HISTOCHEMISTRY_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HISTOCHEMISTRY_NOTE ADD CONSTRAINT PK_CIR_HISTOCHEMISTRY_N PRIMARY KEY (ID);
ALTER TABLE CIR_HISTOCHEMISTRY_NOTE ADD CONSTRAINT FK_CIR_HISTOCHEMISTRY_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_HISTOCHEMISTRY (ID);

CREATE INDEX CIR_HISTOCHEMISTRY_N_IN1 ON CIR_HISTOCHEMISTRY_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Immunohistochemistry CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_IMMUNOHISTOCHEMISTRY (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMMUNOHISTOCHEMISTRY ON CIR_IMMUNOHISTOCHEMISTRY (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMMUNOHISTOCHEMISTRY ADD CONSTRAINT PK_CIR_IMMUNOHISTOCHEMISTRY PRIMARY KEY (ID);

CREATE TABLE CIR_IMMUNOHISTOCHEMISTRY_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMMUNOHISTOCHEMISTRY_E ON CIR_IMMUNOHISTOCHEMISTRY_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMMUNOHISTOCHEMISTRY_EAV ADD CONSTRAINT PK_CIR_IMMUNOHISTOCHEMISTRY_E PRIMARY KEY (ID);
ALTER TABLE CIR_IMMUNOHISTOCHEMISTRY_EAV ADD CONSTRAINT FK_CIR_IMMUNOHISTOCHEMISTRY_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_IMMUNOHISTOCHEMISTRY (ID);

CREATE INDEX CIR_IMMUNOHISTOCHEMISTRY_E_IN1 ON CIR_IMMUNOHISTOCHEMISTRY_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

CREATE TABLE CIR_IMMUNOHISTOCHEMISTRY_MULTI (
ID   NUMBER NOT NULL
,PARENT_ID   NUMBER NOT NULL
,DATA_ELEMENT_CUI   VARCHAR2(30)
,DATA_VALUE_CUI   VARCHAR2(30)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMMUNOHISTOCHEMISTRY_M ON CIR_IMMUNOHISTOCHEMISTRY_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMMUNOHISTOCHEMISTRY_MULTI ADD CONSTRAINT PK_CIR_IMMUNOHISTOCHEMISTRY_M PRIMARY KEY (ID);
ALTER TABLE CIR_IMMUNOHISTOCHEMISTRY_MULTI ADD CONSTRAINT FK_CIR_IMMUNOHISTOCHEMISTRY_M0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_IMMUNOHISTOCHEMISTRY (ID);

CREATE INDEX CIR_IMMUNOHISTOCHEMISTRY_M_IN1 ON CIR_IMMUNOHISTOCHEMISTRY_MULTI (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 
CREATE TABLE CIR_IMMUNOHISTOCHEMISTRY_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMMUNOHISTOCHEMISTRY_N ON CIR_IMMUNOHISTOCHEMISTRY_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMMUNOHISTOCHEMISTRY_NOTE ADD CONSTRAINT PK_CIR_IMMUNOHISTOCHEMISTRY_N PRIMARY KEY (ID);
ALTER TABLE CIR_IMMUNOHISTOCHEMISTRY_NOTE ADD CONSTRAINT FK_CIR_IMMUNOHISTOCHEMISTRY_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_IMMUNOHISTOCHEMISTRY (ID);

CREATE INDEX CIR_IMMUNOHISTOCHEMISTRY_N_IN1 ON CIR_IMMUNOHISTOCHEMISTRY_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Immunophenotype CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_IMMUNOPHENOTYPE (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMMUNOPHENOTYPE ON CIR_IMMUNOPHENOTYPE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMMUNOPHENOTYPE ADD CONSTRAINT PK_CIR_IMMUNOPHENOTYPE PRIMARY KEY (ID);

CREATE TABLE CIR_IMMUNOPHENOTYPE_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMMUNOPHENOTYPE_E ON CIR_IMMUNOPHENOTYPE_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMMUNOPHENOTYPE_EAV ADD CONSTRAINT PK_CIR_IMMUNOPHENOTYPE_E PRIMARY KEY (ID);
ALTER TABLE CIR_IMMUNOPHENOTYPE_EAV ADD CONSTRAINT FK_CIR_IMMUNOPHENOTYPE_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_IMMUNOPHENOTYPE (ID);

CREATE INDEX CIR_IMMUNOPHENOTYPE_E_IN1 ON CIR_IMMUNOPHENOTYPE_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_IMMUNOPHENOTYPE_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMMUNOPHENOTYPE_N ON CIR_IMMUNOPHENOTYPE_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMMUNOPHENOTYPE_NOTE ADD CONSTRAINT PK_CIR_IMMUNOPHENOTYPE_N PRIMARY KEY (ID);
ALTER TABLE CIR_IMMUNOPHENOTYPE_NOTE ADD CONSTRAINT FK_CIR_IMMUNOPHENOTYPE_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_IMMUNOPHENOTYPE (ID);

CREATE INDEX CIR_IMMUNOPHENOTYPE_N_IN1 ON CIR_IMMUNOPHENOTYPE_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Microbiology CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_MICROBIOLOGY (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_MICROBIOLOGY ON CIR_MICROBIOLOGY (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_MICROBIOLOGY ADD CONSTRAINT PK_CIR_MICROBIOLOGY PRIMARY KEY (ID);

CREATE TABLE CIR_MICROBIOLOGY_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_MICROBIOLOGY_E ON CIR_MICROBIOLOGY_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_MICROBIOLOGY_EAV ADD CONSTRAINT PK_CIR_MICROBIOLOGY_E PRIMARY KEY (ID);
ALTER TABLE CIR_MICROBIOLOGY_EAV ADD CONSTRAINT FK_CIR_MICROBIOLOGY_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_MICROBIOLOGY (ID);

CREATE INDEX CIR_MICROBIOLOGY_E_IN1 ON CIR_MICROBIOLOGY_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_MICROBIOLOGY_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_MICROBIOLOGY_N ON CIR_MICROBIOLOGY_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_MICROBIOLOGY_NOTE ADD CONSTRAINT PK_CIR_MICROBIOLOGY_N PRIMARY KEY (ID);
ALTER TABLE CIR_MICROBIOLOGY_NOTE ADD CONSTRAINT FK_CIR_MICROBIOLOGY_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_MICROBIOLOGY (ID);

CREATE INDEX CIR_MICROBIOLOGY_N_IN1 ON CIR_MICROBIOLOGY_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Stains CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_STAINS (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_STAINS ON CIR_STAINS (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_STAINS ADD CONSTRAINT PK_CIR_STAINS PRIMARY KEY (ID);

CREATE TABLE CIR_STAINS_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_STAINS_E ON CIR_STAINS_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_STAINS_EAV ADD CONSTRAINT PK_CIR_STAINS_E PRIMARY KEY (ID);
ALTER TABLE CIR_STAINS_EAV ADD CONSTRAINT FK_CIR_STAINS_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_STAINS (ID);

CREATE INDEX CIR_STAINS_E_IN1 ON CIR_STAINS_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_STAINS_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_STAINS_N ON CIR_STAINS_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_STAINS_NOTE ADD CONSTRAINT PK_CIR_STAINS_N PRIMARY KEY (ID);
ALTER TABLE CIR_STAINS_NOTE ADD CONSTRAINT FK_CIR_STAINS_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_STAINS (ID);

CREATE INDEX CIR_STAINS_N_IN1 ON CIR_STAINS_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * Urinalysis CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_URINALYSIS (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_URINALYSIS ON CIR_URINALYSIS (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_URINALYSIS ADD CONSTRAINT PK_CIR_URINALYSIS PRIMARY KEY (ID);

CREATE TABLE CIR_URINALYSIS_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_URINALYSIS_E ON CIR_URINALYSIS_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_URINALYSIS_EAV ADD CONSTRAINT PK_CIR_URINALYSIS_E PRIMARY KEY (ID);
ALTER TABLE CIR_URINALYSIS_EAV ADD CONSTRAINT FK_CIR_URINALYSIS_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_URINALYSIS (ID);

CREATE INDEX CIR_URINALYSIS_E_IN1 ON CIR_URINALYSIS_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

CREATE TABLE CIR_URINALYSIS_MULTI (
ID   NUMBER NOT NULL
,PARENT_ID   NUMBER NOT NULL
,DATA_ELEMENT_CUI   VARCHAR2(30)
,DATA_VALUE_CUI   VARCHAR2(30)
,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_URINALYSIS_M ON CIR_URINALYSIS_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_URINALYSIS_MULTI ADD CONSTRAINT PK_CIR_URINALYSIS_M PRIMARY KEY (ID);
ALTER TABLE CIR_URINALYSIS_MULTI ADD CONSTRAINT FK_CIR_URINALYSIS_M0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_URINALYSIS (ID);

CREATE INDEX CIR_URINALYSIS_M_IN1 ON CIR_URINALYSIS_MULTI (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 
CREATE TABLE CIR_URINALYSIS_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_URINALYSIS_N ON CIR_URINALYSIS_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_URINALYSIS_NOTE ADD CONSTRAINT PK_CIR_URINALYSIS_N PRIMARY KEY (ID);
ALTER TABLE CIR_URINALYSIS_NOTE ADD CONSTRAINT FK_CIR_URINALYSIS_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_URINALYSIS (ID);

CREATE INDEX CIR_URINALYSIS_N_IN1 ON CIR_URINALYSIS_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * ImagingStudies CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_IMAGING_STUDIES (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMAGING_STUDIES ON CIR_IMAGING_STUDIES (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMAGING_STUDIES ADD CONSTRAINT PK_CIR_IMAGING_STUDIES PRIMARY KEY (ID);

CREATE TABLE CIR_IMAGING_STUDIES_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMAGING_STUDIES_E ON CIR_IMAGING_STUDIES_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMAGING_STUDIES_EAV ADD CONSTRAINT PK_CIR_IMAGING_STUDIES_E PRIMARY KEY (ID);
ALTER TABLE CIR_IMAGING_STUDIES_EAV ADD CONSTRAINT FK_CIR_IMAGING_STUDIES_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_IMAGING_STUDIES (ID);

CREATE INDEX CIR_IMAGING_STUDIES_E_IN1 ON CIR_IMAGING_STUDIES_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_IMAGING_STUDIES_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_IMAGING_STUDIES_N ON CIR_IMAGING_STUDIES_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_IMAGING_STUDIES_NOTE ADD CONSTRAINT PK_CIR_IMAGING_STUDIES_N PRIMARY KEY (ID);
ALTER TABLE CIR_IMAGING_STUDIES_NOTE ADD CONSTRAINT FK_CIR_IMAGING_STUDIES_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_IMAGING_STUDIES (ID);

CREATE INDEX CIR_IMAGING_STUDIES_N_IN1 ON CIR_IMAGING_STUDIES_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
 
 
 
 
 
REM ************************************************************************************
REM * SkinTests CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_SKIN_TESTS (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SKIN_TESTS ON CIR_SKIN_TESTS (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SKIN_TESTS ADD CONSTRAINT PK_CIR_SKIN_TESTS PRIMARY KEY (ID);

CREATE TABLE CIR_SKIN_TESTS_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SKIN_TESTS_E ON CIR_SKIN_TESTS_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SKIN_TESTS_EAV ADD CONSTRAINT PK_CIR_SKIN_TESTS_E PRIMARY KEY (ID);
ALTER TABLE CIR_SKIN_TESTS_EAV ADD CONSTRAINT FK_CIR_SKIN_TESTS_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_SKIN_TESTS (ID);

CREATE INDEX CIR_SKIN_TESTS_E_IN1 ON CIR_SKIN_TESTS_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_SKIN_TESTS_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SKIN_TESTS_N ON CIR_SKIN_TESTS_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SKIN_TESTS_NOTE ADD CONSTRAINT PK_CIR_SKIN_TESTS_N PRIMARY KEY (ID);
ALTER TABLE CIR_SKIN_TESTS_NOTE ADD CONSTRAINT FK_CIR_SKIN_TESTS_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_SKIN_TESTS (ID);

CREATE INDEX CIR_SKIN_TESTS_N_IN1 ON CIR_SKIN_TESTS_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
REM ************************************************************************************
REM * SurgicalStudies CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_SURGICAL_STUDIES (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SURGICAL_STUDIES ON CIR_SURGICAL_STUDIES (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SURGICAL_STUDIES ADD CONSTRAINT PK_CIR_SURGICAL_STUDIES PRIMARY KEY (ID);

CREATE TABLE CIR_SURGICAL_STUDIES_EAV (
  ID   NUMBER NOT NULL,
  PARENT_ID   NUMBER NOT NULL,
  DATA_ELEMENT_CUI VARCHAR2(30),
  DATA_VALUE_CUI   VARCHAR2(30),
  DATA_VALUE_DATE      DATE,
  DATA_VALUE_DATE_DPC  VARCHAR2(30),  
  DATA_VALUE_CLOB  CLOB,
  DATA_VALUE       NUMBER,
  OTHER_DATA_VALUE VARCHAR(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SURGICAL_STUDIES_E ON CIR_SURGICAL_STUDIES_EAV (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SURGICAL_STUDIES_EAV ADD CONSTRAINT PK_CIR_SURGICAL_STUDIES_E PRIMARY KEY (ID);
ALTER TABLE CIR_SURGICAL_STUDIES_EAV ADD CONSTRAINT FK_CIR_SURGICAL_STUDIES_E FOREIGN KEY (PARENT_ID) REFERENCES CIR_SURGICAL_STUDIES (ID);

CREATE INDEX CIR_SURGICAL_STUDIES_E_IN1 ON CIR_SURGICAL_STUDIES_EAV (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 

 
CREATE TABLE CIR_SURGICAL_STUDIES_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SURGICAL_STUDIES_N ON CIR_SURGICAL_STUDIES_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SURGICAL_STUDIES_NOTE ADD CONSTRAINT PK_CIR_SURGICAL_STUDIES_N PRIMARY KEY (ID);
ALTER TABLE CIR_SURGICAL_STUDIES_NOTE ADD CONSTRAINT FK_CIR_SURGICAL_STUDIES_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_SURGICAL_STUDIES (ID);

CREATE INDEX CIR_SURGICAL_STUDIES_N_IN1 ON CIR_SURGICAL_STUDIES_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
 
 
 
REM ************************************************************************************
REM * PastMediHist CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_PAST_MEDI_HIST (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
,ALC_DRINK_PER_DAY_CUI   VARCHAR2(30)
  ,TOBA_USE_INDI_CUI   VARCHAR2(30)
  ,BORN_PREMATURELY_CUI   VARCHAR2(30)
  ,CANCER_INDICATOR_CUI   VARCHAR2(30)
  ,DIAB_MELL_INDI_CUI   VARCHAR2(30)
  ,HYPE_INDI_CUI   VARCHAR2(30)
  ,MULT_BIRTH_CUI   VARCHAR2(30)
  ,GRAVIDA   NUMBER
,GRAVIDA_CUI   VARCHAR2(30)
  ,PARA   NUMBER
,PARA_CUI   VARCHAR2(30)
  ,AGE_FIRS_PREG   NUMBER
,AGE_FIRS_PREG_CUI   VARCHAR2(30)
  ,PREGNANT_NOW_CUI   VARCHAR2(30)
  ,LACTATING_NOW_CUI   VARCHAR2(30)
  ,AGE_MENA   NUMBER
,AGE_MENA_CUI   VARCHAR2(30)
  ,AGE_MENO   NUMBER
,AGE_MENO_CUI   VARCHAR2(30)
  ,LASTMENS_FIRSTDAY   DATE
,LASTMENS_FIRSTDAY_CUI   VARCHAR2(30)
  ,DAYS_SIN_LAST_MENS   NUMBER
,DAYS_SIN_LAST_MENS_CUI   VARCHAR2(30)
  ,DAYS_BETW_PERI   NUMBER
,DAYS_BETW_PERI_CUI   VARCHAR2(30)
  ,LENG_REPR_LIFE   NUMBER
,LENG_REPR_LIFE_CUI   VARCHAR2(30)
  ,HORM_CONT_USE_CUI   VARCHAR2(30)
  ,HORM_REPL_THER_CUI   VARCHAR2(30)
  ,EXERCISE_LEVEL_CUI   VARCHAR2(30)
     )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_PAST_MEDI_HIST ON CIR_PAST_MEDI_HIST (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_PAST_MEDI_HIST ADD CONSTRAINT PK_CIR_PAST_MEDI_HIST PRIMARY KEY (ID);

 

CREATE TABLE CIR_PAST_MEDI_HIST_MULTI (
ID   NUMBER NOT NULL
,PARENT_ID   NUMBER NOT NULL
,DATA_ELEMENT_CUI   VARCHAR2(30)
,DATA_VALUE_CUI   VARCHAR2(30)
,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_PAST_MEDI_HIST_M ON CIR_PAST_MEDI_HIST_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_PAST_MEDI_HIST_MULTI ADD CONSTRAINT PK_CIR_PAST_MEDI_HIST_M PRIMARY KEY (ID);
ALTER TABLE CIR_PAST_MEDI_HIST_MULTI ADD CONSTRAINT FK_CIR_PAST_MEDI_HIST_M0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_PAST_MEDI_HIST (ID);

CREATE INDEX CIR_PAST_MEDI_HIST_M_IN1 ON CIR_PAST_MEDI_HIST_MULTI (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 
CREATE TABLE CIR_PAST_MEDI_HIST_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_PAST_MEDI_HIST_N ON CIR_PAST_MEDI_HIST_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_PAST_MEDI_HIST_NOTE ADD CONSTRAINT PK_CIR_PAST_MEDI_HIST_N PRIMARY KEY (ID);
ALTER TABLE CIR_PAST_MEDI_HIST_NOTE ADD CONSTRAINT FK_CIR_PAST_MEDI_HIST_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_PAST_MEDI_HIST (ID);

CREATE INDEX CIR_PAST_MEDI_HIST_N_IN1 ON CIR_PAST_MEDI_HIST_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
 
 
 
 
 
 
 
 
 
 
 
 
REM ************************************************************************************
REM * SurgicalPathology CREATE SQL
REM * Generated using version 1.0 of the DataElementTaxonomy xml file.
REM ************************************************************************************
CREATE TABLE CIR_SURGICAL_PATHOLOGY (
ID   NUMBER NOT NULL
,FORM_ID VARCHAR2(10)
,PATH_REPO_DIAG_CUI   VARCHAR2(30)
,OTHER_PATH_REPO_DIAG   VARCHAR2(200)
  ,DATE_SURG   DATE
,DATE_SURG_CUI   VARCHAR2(30)
  ,PATH_REPO_TEXT   CLOB
,PATH_REPO_TEXT_CUI   VARCHAR2(30)
  ,TISSUE_ORIG_CUI   VARCHAR2(30)
,OTHER_TISSUE_ORIG   VARCHAR2(200)
  ,SITE_FIND_CUI   VARCHAR2(30)
,OTHER_SITE_FIND   VARCHAR2(200)
  ,SURGICAL_PROCEDURE_CUI   VARCHAR2(30)
,OTHER_SURGICAL_PROCEDURE   VARCHAR2(200)
  ,NEED_BIOP_NUMB   NUMBER
,NEED_BIOP_NUMB_CUI   VARCHAR2(30)
  ,NUMB_CORE_POS   NUMBER
,NUMB_CORE_POS_CUI   VARCHAR2(30)
  ,NUMB_CORE_TAKE   NUMBER
,NUMB_CORE_TAKE_CUI   VARCHAR2(30)
  ,PERC_CORE_POS   NUMBER
,PERC_CORE_POS_CUI   VARCHAR2(30)
  ,LESI_DIAM1   NUMBER
,LESI_DIAM1_CUI   VARCHAR2(30)
  ,LESI_DIAM2   NUMBER
,LESI_DIAM2_CUI   VARCHAR2(30)
  ,LESI_DIAM3   NUMBER
,LESI_DIAM3_CUI   VARCHAR2(30)
  ,HEART_WEIGHT   NUMBER
,HEART_WEIGHT_CUI   VARCHAR2(30)
  ,LEFT_ATRI_DIAM   NUMBER
,LEFT_ATRI_DIAM_CUI   VARCHAR2(30)
  ,LEFT_VENT_DIAM   NUMBER
,LEFT_VENT_DIAM_CUI   VARCHAR2(30)
  ,LEFT_VENT_WALL_THIC   NUMBER
,LEFT_VENT_WALL_THIC_CUI   VARCHAR2(30)
  ,RIGH_ATRI_DIAM   NUMBER
,RIGH_ATRI_DIAM_CUI   VARCHAR2(30)
  ,RIGH_VENT_DIAM   NUMBER
,RIGH_VENT_DIAM_CUI   VARCHAR2(30)
  ,RIGH_VENT_WALL_THIC   NUMBER
,RIGH_VENT_WALL_THIC_CUI   VARCHAR2(30)
  ,NUMB_POLYPS   NUMBER
,NUMB_POLYPS_CUI   VARCHAR2(30)
  ,SIZE_LEUK_CUI   VARCHAR2(30)
  ,A6_PATH_TUMO_STG_CUI   VARCHAR2(30)
  ,MACF_PATH_TUMO_STG_CUI   VARCHAR2(30)
  ,A6_PATH_LYM_NODE_STG_CUI   VARCHAR2(30)
  ,HAN_PATH_LN_LOCA_CUI   VARCHAR2(30)
  ,LYM_NODE_INVO   NUMBER
,LYM_NODE_INVO_CUI   VARCHAR2(30)
  ,MACF_PATH_LN_STG_CUI   VARCHAR2(30)
  ,MF_PATH_LYM_NODE_STG_CUI   VARCHAR2(30)
  ,PERC_LYM_NODE_INVO   NUMBER
,PERC_LYM_NODE_INVO_CUI   VARCHAR2(30)
  ,AJCC6_PATH_CUI   VARCHAR2(30)
  ,KADISH_STAGE_CUI   VARCHAR2(30)
  ,MACF_PATH_MET_STG_CUI   VARCHAR2(30)
  ,LOW_THOR_ESOPH_CUI   VARCHAR2(30)
  ,MD_THOR_ESOPH_METSTG_CUI   VARCHAR2(30)
  ,UP_THOR_ESO_PAMETSTG_CUI   VARCHAR2(30)
  ,A6_PATH_STG_GROUP_CUI   VARCHAR2(30)
  ,BURK_LYMP_PATH_CUI   VARCHAR2(30)
  ,EXGONGERM_PATHSG_CUI   VARCHAR2(30)
  ,MACF_PATH_STG_GROUP_CUI   VARCHAR2(30)
  ,MASA_THYM_STG_GROUP_CUI   VARCHAR2(30)
  ,OLEP_STG_GROUP_CUI   VARCHAR2(30)
  ,BIL_CIRR_PATH_E_CUI   VARCHAR2(30)
  ,AP_ALLO_GLOM_SCOR_CUI   VARCHAR2(30)
  ,AP_ART_HYAL_THK_SCOR_CUI   VARCHAR2(30)
  ,AP_E_ALLOGLOM_SCOR_CUI   VARCHAR2(30)
  ,AP_INTIM_THICK_SCOR_CUI   VARCHAR2(30)
  ,AP_INTE_FIBR_SCOR_CUI   VARCHAR2(30)
  ,AP_INTI_ARTE_SCOR_CUI   VARCHAR2(30)
  ,AP_MESMATR_INCR_SCOR_CUI   VARCHAR2(30)
  ,AP_MC_IN_INFLAM_SCOR_CUI   VARCHAR2(30)
  ,AP_TUBU_ATRO_SCOR_CUI   VARCHAR2(30)
  ,ALLO_PATH_TUBU_SCOR_CUI   VARCHAR2(30)
  ,BANF97_REN_ALLO_CLAS_CUI   VARCHAR2(30)
  ,BONEGIANT_TUM_GRAD_CUI   VARCHAR2(30)
  ,BRAA_BRAA_ALZH_STG_CUI   VARCHAR2(30)
  ,BRAA_BRAA_PARK_STG_CUI   VARCHAR2(30)
  ,BRESLOWS_THICKNESS   NUMBER
,BRESLOWS_THICKNESS_CUI   VARCHAR2(30)
  ,CANC_UK_PRIPATH_CLAS_CUI   VARCHAR2(30)
,OTHER_CANC_UK_PRIPATH_CLAS   VARCHAR2(200)
  ,CARC_HIST_CLAS_CUI   VARCHAR2(30)
  ,CERA_PLAQ_SCOR_CUI   VARCHAR2(30)
  ,CERV_INTR_NEOP_GRAD_CUI   VARCHAR2(30)
  ,CHONDROSARCOMA_GRADE_CUI   VARCHAR2(30)
  ,CHRO_GAST_TYPE_CUI   VARCHAR2(30)
  ,CLARK_LEVEL_CUI   VARCHAR2(30)
  ,DEPTH_TUMOR_INVA_CUI   VARCHAR2(30)
  ,ENDO_ADEN_HIST_GRAD_CUI   VARCHAR2(30)
  ,ENNE_STAG_MUSC_SARC_CUI   VARCHAR2(30)
  ,ESOP_DYSP_GRAD_CUI   VARCHAR2(30)
  ,FIBROSIS_GRADE_CUI   VARCHAR2(30)
  ,FIBROSIS_SCORE_CUI   VARCHAR2(30)
  ,FOLL_LYMP_HIST_GRAD_CUI   VARCHAR2(30)
  ,GLAS_GLOM_JUGU_CLAS_CUI   VARCHAR2(30)
  ,GLEA_PRIMARY_SCORE   NUMBER
,GLEA_PRIMARY_SCORE_CUI   VARCHAR2(30)
  ,GLEA_SECOND_SCORE   NUMBER
,GLEA_SECOND_SCORE_CUI   VARCHAR2(30)
  ,GLEASON_SCORE   NUMBER
,GLEASON_SCORE_CUI   VARCHAR2(30)
  ,HISTOLOGIC_GRADE_CUI   VARCHAR2(30)
  ,HUNT_DIS_PATH_GRAD_CUI   VARCHAR2(30)
  ,IMMA_TERA_HIST_GRAD_CUI   VARCHAR2(30)
  ,INTR_NECR_CUI   VARCHAR2(30)
  ,JAP_CLASS_DYSP_CUI   VARCHAR2(30)
  ,LYMPHATIC_INVASION_CUI   VARCHAR2(30)
  ,NIA_REAG_NEUR_CRIT_CUI   VARCHAR2(30)
  ,NOTT_COMB_HIST_GRAD_CUI   VARCHAR2(30)
  ,NUMB_INDI_TUMO_PRES   NUMBER
,NUMB_INDI_TUMO_PRES_CUI   VARCHAR2(30)
  ,OLEP_PATHOLOGY_CUI   VARCHAR2(30)
  ,PADO_INTE_DYSP_CLAS_CUI   VARCHAR2(30)
  ,PERC_TISSUE_POS   NUMBER
,PERC_TISSUE_POS_CUI   VARCHAR2(30)
  ,PERIPORTAL_NECROSIS_CUI   VARCHAR2(30)
  ,PERITONEAL_DISS_CUI   VARCHAR2(30)
  ,PORTAL_INFLAMMATION_CUI   VARCHAR2(30)
  ,RESIDUAL_TUMOR_CUI   VARCHAR2(30)
  ,SEROSAL_INVASION_CUI   VARCHAR2(30)
  ,SHIMADA_CLASS_CUI   VARCHAR2(30)
  ,TUMOR_LATERALITY_CUI   VARCHAR2(30)
  ,UTER_DEGR_DIFF_CUI   VARCHAR2(30)
  ,VAGI_INTR_NEOP_GRAD_CUI   VARCHAR2(30)
  ,VENOUS_INVASION_CUI   VARCHAR2(30)
  ,VIENNA_DYSP_CLASS_CUI   VARCHAR2(30)
  ,VULV_INTR_NEOP_GRAD_CUI   VARCHAR2(30)
  ,WHO_CNS_GRAD_CUI   VARCHAR2(30)
  ,WHO_HISTCLAS_DCNEO_CUI   VARCHAR2(30)
  ,WHO_HISTCLAS_MYELDIS_CUI   VARCHAR2(30)
  ,WHO_MDS_HIST_CLAS_CUI   VARCHAR2(30)
  ,WHO_NASO_CARC_GRAD_CUI   VARCHAR2(30)
  ,WHO_PNS_GRAD_CUI   VARCHAR2(30)
  ,WHO_THYM_CLAS_CUI   VARCHAR2(30)
   ,AGNOR_COUNT   NUMBER
,AGNOR_COUNT_CUI   VARCHAR2(30)
  ,APOPTOSIS_CUI   VARCHAR2(30)
,OTHER_APOPTOSIS   VARCHAR2(200)
  ,AVER_MITO_PER_HPF_CUI   VARCHAR2(30)
  ,CALCIFICATION_CUI   VARCHAR2(30)
  ,CELL_ORIGIN_CUI   VARCHAR2(30)
,OTHER_CELL_ORIGIN   VARCHAR2(200)
  ,CELL_PROLIF_EL_CUI   VARCHAR2(30)
  ,CELL_SIZE_CUI   VARCHAR2(30)
,OTHER_CELL_SIZE   VARCHAR2(200)
  ,FIBROCYSTIC_CHANGES_CUI   VARCHAR2(30)
  ,MITOTIC_INDEX_CUI   VARCHAR2(30)
  ,MYEL_CELL_MASS   NUMBER
,MYEL_CELL_MASS_CUI   VARCHAR2(30)
  ,PLAS_CELL_INDE_CUI   VARCHAR2(30)
  ,PROLIFERATION_INDEX   NUMBER
,PROLIFERATION_INDEX_CUI   VARCHAR2(30)
  ,SPHASE_CUI   VARCHAR2(30)
  ,TELOMERASE_ACTIVITY_CUI   VARCHAR2(30)
  ,TUMO_MASS_SIZE_MICRO   NUMBER
,TUMO_MASS_SIZE_MICRO_CUI   VARCHAR2(30)
  ,TUMOR_NECROSIS_CUI   VARCHAR2(30)
  ,ELEC_MICR_REPO   CLOB
,ELEC_MICR_REPO_CUI   VARCHAR2(30)
    )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SURGICAL_PATHOLOGY ON CIR_SURGICAL_PATHOLOGY (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SURGICAL_PATHOLOGY ADD CONSTRAINT PK_CIR_SURGICAL_PATHOLOGY PRIMARY KEY (ID);

 

CREATE TABLE CIR_SURGICAL_PATHOLOGY_MULTI (
ID   NUMBER NOT NULL
,PARENT_ID   NUMBER NOT NULL
,DATA_ELEMENT_CUI   VARCHAR2(30)
,DATA_VALUE_CUI   VARCHAR2(30)
,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SURGICAL_PATHOLOGY_M ON CIR_SURGICAL_PATHOLOGY_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SURGICAL_PATHOLOGY_MULTI ADD CONSTRAINT PK_CIR_SURGICAL_PATHOLOGY_M PRIMARY KEY (ID);
ALTER TABLE CIR_SURGICAL_PATHOLOGY_MULTI ADD CONSTRAINT FK_CIR_SURGICAL_PATHOLOGY_M0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_SURGICAL_PATHOLOGY (ID);

CREATE INDEX CIR_SURGICAL_PATHOLOGY_M_IN1 ON CIR_SURGICAL_PATHOLOGY_MULTI (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

 
CREATE TABLE CIR_SURGICAL_PATHOLOGY_NOTE (
ID   NUMBER NOT NULL,
PARENT_ID   NUMBER NOT NULL,
DATA_ELEMENT_CUI   VARCHAR2(30),
NOTE   VARCHAR2(1000)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SURGICAL_PATHOLOGY_N ON CIR_SURGICAL_PATHOLOGY_NOTE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SURGICAL_PATHOLOGY_NOTE ADD CONSTRAINT PK_CIR_SURGICAL_PATHOLOGY_N PRIMARY KEY (ID);
ALTER TABLE CIR_SURGICAL_PATHOLOGY_NOTE ADD CONSTRAINT FK_CIR_SURGICAL_PATHOLOGY_N0 FOREIGN KEY (PARENT_ID) REFERENCES CIR_SURGICAL_PATHOLOGY (ID);

CREATE INDEX CIR_SURGICAL_PATHOLOGY_N_IN1 ON CIR_SURGICAL_PATHOLOGY_NOTE (PARENT_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL
;

  
 
 
 
 
 
 
 
 
 
 
 
 
 
CREATE TABLE CIR_DATE_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_ELEMENT   DATE
    ,DATE_ELEMENT_DPC   VARCHAR2(30)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_DATE_ADE ON CIR_DATE_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_DATE_ADE ADD CONSTRAINT PK_CIR_DATE_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_EXPOSURE_HX_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,YEAR_EXPOSED   NUMBER
    ,YEAR_SINC_EXPOSED   NUMBER
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_EXPOSURE_HX_ADE ON CIR_EXPOSURE_HX_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_EXPOSURE_HX_ADE ADD CONSTRAINT PK_CIR_EXPOSURE_HX_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_GENDER_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,EXPL_OTHER_GENDER   CLOB
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_GENDER_ADE ON CIR_GENDER_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_GENDER_ADE ADD CONSTRAINT PK_CIR_GENDER_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_HCU_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_HCU_START   DATE
    ,DATE_HCU_START_DPC   VARCHAR2(30)
    ,DATE_RECENT_HCU   DATE
    ,DATE_RECENT_HCU_DPC   VARCHAR2(30)
    ,DAYS_SINC_HCU   NUMBER
    ,DURATION_TREATMENT   NUMBER
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HCU_ADE ON CIR_HCU_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HCU_ADE ADD CONSTRAINT PK_CIR_HCU_ADE PRIMARY KEY (ID);
CREATE TABLE CIR_HCU_ADE_MULTI (
    ID   NUMBER NOT NULL
    ,PARENT_ID   NUMBER NOT NULL
    ,DATA_ELEMENT_CUI   VARCHAR2(30)
    ,DATA_VALUE_CUI   VARCHAR2(30)
    ,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HCU_ADE_M ON CIR_HCU_ADE_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HCU_ADE_MULTI ADD CONSTRAINT PK_CIR_HCU_ADE_M PRIMARY KEY (ID);
 CREATE TABLE CIR_HRT_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_HRT_START   DATE
    ,DATE_HRT_START_DPC   VARCHAR2(30)
    ,DATE_RECENT_HRT   DATE
    ,DATE_RECENT_HRT_DPC   VARCHAR2(30)
    ,DAYS_SINC_HRT   NUMBER
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HRT_ADE ON CIR_HRT_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HRT_ADE ADD CONSTRAINT PK_CIR_HRT_ADE PRIMARY KEY (ID);
CREATE TABLE CIR_HRT_ADE_MULTI (
    ID   NUMBER NOT NULL
    ,PARENT_ID   NUMBER NOT NULL
    ,DATA_ELEMENT_CUI   VARCHAR2(30)
    ,DATA_VALUE_CUI   VARCHAR2(30)
    ,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HRT_ADE_M ON CIR_HRT_ADE_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HRT_ADE_MULTI ADD CONSTRAINT PK_CIR_HRT_ADE_M PRIMARY KEY (ID);
 CREATE TABLE CIR_PHYSICAL_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,AGE_FIND_APPE   NUMBER
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_PHYSICAL_ADE ON CIR_PHYSICAL_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_PHYSICAL_ADE ADD CONSTRAINT PK_CIR_PHYSICAL_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_CHEMO_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_CHEMO_START   DATE
    ,DATE_CHEMO_START_DPC   VARCHAR2(30)
    ,DATE_CHEMO_END   DATE
    ,DATE_CHEMO_END_DPC   VARCHAR2(30)
    ,DAYS_SINCLST_CHEMO   NUMBER
    ,ROUT_ADMI_CUI   VARCHAR2(30)
    ,OTHER_ROUT_ADMI   VARCHAR2(200)
    ,RESP_CHEMO_CUI   VARCHAR2(30)
    ,OTHER_RESP_CHEMO   VARCHAR2(200)
    ,OFF_TREAT_REASON_CUI   VARCHAR2(30)
    ,OTHER_OFF_TREAT_REASON   VARCHAR2(200)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHEMO_ADE ON CIR_CHEMO_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHEMO_ADE ADD CONSTRAINT PK_CIR_CHEMO_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_MEDICATION_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_MEDI_START   DATE
    ,DATE_MEDI_START_DPC   VARCHAR2(30)
    ,DATE_MEDI_END   DATE
    ,DATE_MEDI_END_DPC   VARCHAR2(30)
    ,DAYS_SINCLST_MEDI   NUMBER
    ,RESP_MEDI_CUI   VARCHAR2(30)
    ,OTHER_RESP_MEDI   VARCHAR2(200)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_MEDICATION_ADE ON CIR_MEDICATION_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_MEDICATION_ADE ADD CONSTRAINT PK_CIR_MEDICATION_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_TREATMENT_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_TREAT   DATE
    ,DATE_TREAT_DPC   VARCHAR2(30)
    ,RESP_TREAT_CUI   VARCHAR2(30)
    ,OTHER_RESP_TREAT   VARCHAR2(200)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_TREATMENT_ADE ON CIR_TREATMENT_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_TREATMENT_ADE ADD CONSTRAINT PK_CIR_TREATMENT_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_RADIOTHERAPY_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_RADIO_START   DATE
    ,DATE_RADIO_START_DPC   VARCHAR2(30)
    ,DATE_RADIO_END   DATE
    ,DATE_RADIO_END_DPC   VARCHAR2(30)
    ,DAYS_SINCLST_RADIO   NUMBER
    ,TOTL_RADIO_DOS   NUMBER
    ,RESP_RADIO_CUI   VARCHAR2(30)
    ,OTHER_RESP_RADIO   VARCHAR2(200)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_RADIOTHERAPY_ADE ON CIR_RADIOTHERAPY_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_RADIOTHERAPY_ADE ADD CONSTRAINT PK_CIR_RADIOTHERAPY_ADE PRIMARY KEY (ID);
CREATE TABLE CIR_RADIOTHERAPY_ADE_MULTI (
    ID   NUMBER NOT NULL
    ,PARENT_ID   NUMBER NOT NULL
    ,DATA_ELEMENT_CUI   VARCHAR2(30)
    ,DATA_VALUE_CUI   VARCHAR2(30)
    ,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_RADIOTHERAPY_ADE_M ON CIR_RADIOTHERAPY_ADE_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_RADIOTHERAPY_ADE_MULTI ADD CONSTRAINT PK_CIR_RADIOTHERAPY_ADE_M PRIMARY KEY (ID);
 CREATE TABLE CIR_SURGERY_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_OF_SURGERY   DATE
    ,DATE_OF_SURGERY_DPC   VARCHAR2(30)
    ,OPERATIVE_NOTE   CLOB
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_SURGERY_ADE ON CIR_SURGERY_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_SURGERY_ADE ADD CONSTRAINT PK_CIR_SURGERY_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_TRANSPLANT_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_TRANSPLANT   DATE
    ,DATE_TRANSPLANT_DPC   VARCHAR2(30)
    ,TRANSPLANT_SOURCE_CUI   VARCHAR2(30)
    ,OTHER_TRANSPLANT_SOURCE   VARCHAR2(200)
    ,TRANS_OPERAT_NOTE   CLOB
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_TRANSPLANT_ADE ON CIR_TRANSPLANT_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_TRANSPLANT_ADE ADD CONSTRAINT PK_CIR_TRANSPLANT_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_STUDY_REPORT_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_STUDY   DATE
    ,STUDY_RPRT   CLOB
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_STUDY_REPORT_ADE ON CIR_STUDY_REPORT_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_STUDY_REPORT_ADE ADD CONSTRAINT PK_CIR_STUDY_REPORT_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_OBJ_STUDY_REPORT_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_OBJECT_STUDY   DATE
    ,OBJECT_STUDY_CUI   VARCHAR2(30)
    ,OTHER_OBJECT_STUDY   VARCHAR2(200)
    ,OBJECT_STUDY_RPRT   CLOB
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_OBJ_STUDY_REPORT_ADE ON CIR_OBJ_STUDY_REPORT_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_OBJ_STUDY_REPORT_ADE ADD CONSTRAINT PK_CIR_OBJ_STUDY_REPORT_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_LAB_NUMERIC_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_NUME_RESU_TEST   DATE
    ,DATE_NUME_RESU_TEST_DPC   VARCHAR2(30)
    ,CLIN_TEST_RESU_CUI   VARCHAR2(30)
    ,TEST_NORM_RANG   CLOB
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_LAB_NUMERIC_ADE ON CIR_LAB_NUMERIC_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_LAB_NUMERIC_ADE ADD CONSTRAINT PK_CIR_LAB_NUMERIC_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_LAB_TECHNIQUE_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_TECH_SPEC_TEST   DATE
    ,DATE_TECH_SPEC_TEST_DPC   VARCHAR2(30)
    ,TEST_TECHNIQUE_CUI   VARCHAR2(30)
    ,OTHER_TEST_TECHNIQUE   VARCHAR2(200)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_LAB_TECHNIQUE_ADE ON CIR_LAB_TECHNIQUE_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_LAB_TECHNIQUE_ADE ADD CONSTRAINT PK_CIR_LAB_TECHNIQUE_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_LAB_MICRO_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_MICR_TEST   DATE
    ,CULTURE_RESULT_CUI   VARCHAR2(30)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_LAB_MICRO_ADE ON CIR_LAB_MICRO_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_LAB_MICRO_ADE ADD CONSTRAINT PK_CIR_LAB_MICRO_ADE PRIMARY KEY (ID);
CREATE TABLE CIR_LAB_MICRO_ADE_MULTI (
    ID   NUMBER NOT NULL
    ,PARENT_ID   NUMBER NOT NULL
    ,DATA_ELEMENT_CUI   VARCHAR2(30)
    ,DATA_VALUE_CUI   VARCHAR2(30)
    ,OTHER_DATA_VALUE   VARCHAR2(200)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_LAB_MICRO_ADE_M ON CIR_LAB_MICRO_ADE_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_LAB_MICRO_ADE_MULTI ADD CONSTRAINT PK_CIR_LAB_MICRO_ADE_M PRIMARY KEY (ID);
 CREATE TABLE CIR_ALCOHOL_HX_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,ALCOHOL_AMOUNT   NUMBER
    ,ALCOHOL_DURATION   NUMBER
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_ALCOHOL_HX_ADE ON CIR_ALCOHOL_HX_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_ALCOHOL_HX_ADE ADD CONSTRAINT PK_CIR_ALCOHOL_HX_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_TOBACCO_HX_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,TOBACCO_AMOUNT   NUMBER
    ,TOBACCO_STOPPED   DATE
    ,TOBACCO_STOPPED_DPC   VARCHAR2(30)
    ,YEAR_NOT_SMOK   NUMBER
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_TOBACCO_HX_ADE ON CIR_TOBACCO_HX_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_TOBACCO_HX_ADE ADD CONSTRAINT PK_CIR_TOBACCO_HX_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_OCCUPATION_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,YEARS_AT_OCC   NUMBER
    ,YEARS_SINCE_OCC   NUMBER
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_OCCUPATION_ADE ON CIR_OCCUPATION_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_OCCUPATION_ADE ADD CONSTRAINT PK_CIR_OCCUPATION_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_CANCER_HX_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,CANCER_LOCATION_CUI   VARCHAR2(30)
    ,OTHER_CANCER_LOCATION   VARCHAR2(200)
    ,DATE_DIAG   DATE
    ,DATE_DIAG_DPC   VARCHAR2(30)
    ,AGE_AT_DIAGNOSIS   NUMBER
    ,CANCER_STATUS_CUI   VARCHAR2(30)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CANCER_HX_ADE ON CIR_CANCER_HX_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CANCER_HX_ADE ADD CONSTRAINT PK_CIR_CANCER_HX_ADE PRIMARY KEY (ID);
CREATE TABLE CIR_CANCER_HX_ADE_MULTI (
    ID   NUMBER NOT NULL
    ,PARENT_ID   NUMBER NOT NULL
    ,DATA_ELEMENT_CUI   VARCHAR2(30)
    ,DATA_VALUE_CUI   VARCHAR2(30)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CANCER_HX_ADE_M ON CIR_CANCER_HX_ADE_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CANCER_HX_ADE_MULTI ADD CONSTRAINT PK_CIR_CANCER_HX_ADE_M PRIMARY KEY (ID);
 CREATE TABLE CIR_DIABETES_HX_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DIAB_MELL_DUR   NUMBER
    ,DIAB_MELL_TYPE_CUI   VARCHAR2(30)
    ,DIAB_MELL_CONT_CUI   VARCHAR2(30)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_DIABETES_HX_ADE ON CIR_DIABETES_HX_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_DIABETES_HX_ADE ADD CONSTRAINT PK_CIR_DIABETES_HX_ADE PRIMARY KEY (ID);
CREATE TABLE CIR_DIABETES_HX_ADE_MULTI (
    ID   NUMBER NOT NULL
    ,PARENT_ID   NUMBER NOT NULL
    ,DATA_ELEMENT_CUI   VARCHAR2(30)
    ,DATA_VALUE_CUI   VARCHAR2(30)
)
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_DIABETES_HX_ADE_M ON CIR_DIABETES_HX_ADE_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_DIABETES_HX_ADE_MULTI ADD CONSTRAINT PK_CIR_DIABETES_HX_ADE_M PRIMARY KEY (ID);
 CREATE TABLE CIR_HYPERTENSION_HX_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,HYPE_DUR   NUMBER
    ,HYPERTENSION_TYPE_CUI   VARCHAR2(30)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_HYPERTENSION_HX_ADE ON CIR_HYPERTENSION_HX_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_HYPERTENSION_HX_ADE ADD CONSTRAINT PK_CIR_HYPERTENSION_HX_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_PAST_CONDITIONS_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_ONSET   DATE
    ,DATE_ONSET_DPC   VARCHAR2(30)
    ,AGE_DIAG_PAST_COND   NUMBER
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_PAST_CONDITIONS_ADE ON CIR_PAST_CONDITIONS_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_PAST_CONDITIONS_ADE ADD CONSTRAINT PK_CIR_PAST_CONDITIONS_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_FAMILY_HX_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,AGE_DIAG_FAMI_HIST   NUMBER
    ,DEGR_RELAT_CUI   VARCHAR2(30)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_FAMILY_HX_ADE ON CIR_FAMILY_HX_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_FAMILY_HX_ADE ADD CONSTRAINT PK_CIR_FAMILY_HX_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_CHEMO_HX_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_PR_CHEMO_START   DATE
    ,DATE_PR_CHEMO_START_DPC   VARCHAR2(30)
    ,DATE_PR_CHEMO_END   DATE
    ,DATE_PR_CHEMO_END_DPC   VARCHAR2(30)
    ,DAYS_SINCLSTPR_CHEMO   NUMBER
    ,RESP_PRIO_CHEMO_CUI   VARCHAR2(30)
    ,OTHER_RESP_PRIO_CHEMO   VARCHAR2(200)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_CHEMO_HX_ADE ON CIR_CHEMO_HX_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_CHEMO_HX_ADE ADD CONSTRAINT PK_CIR_CHEMO_HX_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_RADIOTHERAPY_HX_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,DATE_PR_RADIO_START   DATE
    ,DATE_PR_RADIO_START_DPC   VARCHAR2(30)
    ,DATE_PR_RADIO_END   DATE
    ,DATE_PR_RADIO_END_DPC   VARCHAR2(30)
    ,DAYS_SINCLSTPR_RADIO   NUMBER
    ,RESP_PRIO_RADIO_CUI   VARCHAR2(30)
    ,OTHER_RESP_PRIO_RADIO   VARCHAR2(200)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_RADIOTHERAPY_HX_ADE ON CIR_RADIOTHERAPY_HX_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_RADIOTHERAPY_HX_ADE ADD CONSTRAINT PK_CIR_RADIOTHERAPY_HX_ADE PRIMARY KEY (ID);
 CREATE TABLE CIR_DETERMIN_ADE (
    ID                  NUMBER                NOT NULL
    ,PARENT_ID               NUMBER
    ,DATA_ELEMENT_CUI        VARCHAR2(30)
    ,STG_GRAD_DET_CUI   VARCHAR2(30)
 )
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;
CREATE UNIQUE INDEX PK_CIR_DETERMIN_ADE ON CIR_DETERMIN_ADE (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_DETERMIN_ADE ADD CONSTRAINT PK_CIR_DETERMIN_ADE PRIMARY KEY (ID);
  

CREATE SEQUENCE CIR_SEQ START WITH 1 INCREMENT BY 1 NOMAXVALUE NOCYCLE CACHE 20;



prompt  End of Cir_create.sql script


prompt ==========   END MR8464 ==========
prompt
prompt
prompt ==========   MR8495  ========== 

ALTER TABLE ES_ARDAIS_USER
 ADD (CONSECUTIVE_FAILED_ANSWERS  NUMBER DEFAULT 0 NOT NULL)
/
commit
/

prompt ==========   END MR8495 ==========   
prompt
prompt ==========   MR8593  ========== 

drop table cir_donor_multi;

drop table cir_donor;

drop sequence cir_donor_multi_seq;

commit
/

prompt ==========   END MR8593 ==========  
prompt
prompt ==========   MR8627  ========== 

REM
REM create the indexes for FK that the developers didn't create
REM
REM

CREATE INDEX ARD_USER_ACCESS_MODULE_IN2 ON ARD_USER_ACCESS_MODULE
(ARDAIS_USER_ID, ARDAIS_ACCT_KEY)
LOGGING
TABLESPACE OTHER_IDX_TBS
STORAGE    (
            INITIAL          32K
            NEXT             32K
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX CIR_FORM_IN1 ON CIR_FORM
(FORM_DEFINITION_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
STORAGE    (
            INITIAL          32K
            NEXT             32K
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX CIR_FORM_DEFINITION_TAGS_IN1 ON CIR_FORM_DEFINITION_TAGS
(FORM_DEFINITION_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
STORAGE    (
            INITIAL          32K
            NEXT             32K
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX ES_ARDAIS_ORDER_IN4 ON ES_ARDAIS_ORDER
(ARDAIS_USER_ID, ARDAIS_ACCT_KEY)
LOGGING
TABLESPACE ES_IDX_TBS
STORAGE    (
            INITIAL          32K
            NEXT             32K
            MAXEXTENTS       2147483645
            PCTINCREASE      0
           )
NOPARALLEL;

CREATE INDEX ES_ARDAIS_ORDER_STATUS_IN3 ON ES_ARDAIS_ORDER_STATUS
(ARDAIS_USER_ID, ARDAIS_ACCT_KEY)
LOGGING
TABLESPACE ES_IDX_TBS
STORAGE    (
            INITIAL          32K
            NEXT             32K
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX ES_SHOPPING_CART_DETAIL_IN2 ON ES_SHOPPING_CART_DETAIL
(SHOPPING_CART_ID, ARDAIS_ACCT_KEY, ARDAIS_USER_ID)
LOGGING
TABLESPACE ES_IDX_TBS
STORAGE    (
            INITIAL          32K
            NEXT             32K
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX ILTDS_DERIVATION_IN1 ON ILTDS_DERIVATION
(OPERATION_CUI)
LOGGING
TABLESPACE ILTDS_IDX_TBS
STORAGE    (
            INITIAL          32K
            NEXT             32K
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX ILTDS_DERIVATION_IN2 ON ILTDS_DERIVATION
(PREPARED_BY)
LOGGING
TABLESPACE ILTDS_IDX_TBS
STORAGE    (
            INITIAL          32K
            NEXT             32K
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX ILTDS_SAMPLE_IN28 ON ILTDS_SAMPLE
(BUFFER_TYPE_CUI)
LOGGING
TABLESPACE ILTDS_IDX_TBS
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MAXEXTENTS       2147483645
            PCTINCREASE      0
           )
NOPARALLEL;

CREATE INDEX ILTDS_SAMPLE_IN29 ON ILTDS_SAMPLE
(TOTAL_NUM_OF_CELLS_EX_REP_CUI)
LOGGING
TABLESPACE ILTDS_IDX_TBS
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX ILTDS_SAMPLE_GENEALOGY_IN1 ON ILTDS_SAMPLE_GENEALOGY
(CHILD_SAMPLE_BARCODE_ID)
LOGGING
TABLESPACE ILTDS_IDX_TBS
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX ILTDS_SAMPLE_GENEALOGY_IN2 ON ILTDS_SAMPLE_GENEALOGY
(DERIVATION_ID)
LOGGING
TABLESPACE ILTDS_IDX_TBS
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


commit
/

spool off
