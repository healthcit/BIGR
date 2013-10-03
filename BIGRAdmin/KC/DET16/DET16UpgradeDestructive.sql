-- DET16 schema upgrade
-- 
-- This upgrade only modifies the schema from DET15 and does not migrate 
-- existing data, except for the data elements that were changed from 
-- report/CLOB to text/VARCHAR2(4000).  All other existing data will be lost!
--
-- Ischemic time moved, so drop from followup info table and
-- add to case info table.
--
alter table CIR_FOLLOWUP_INFO drop (ISCHEMIC_TIME, ISCHEMIC_TIME_CUI);
alter table CIR_CASE_INFO add (ISCHEMIC_TIME NUMBER, ISCHEMIC_TIME_CUI VARCHAR2(30));

-- Change columns from CLOB to VARCHAR2(4000).
--
alter table CIR_PT_INFO add (
FIRST_NAME_V VARCHAR2(4000), 
FORM_COMPLETED_BY_V VARCHAR2(4000), 
LAST_NAME_V VARCHAR2(4000), 
MIDDLE_INITIAL_V VARCHAR2(4000), 
PT_ID_V VARCHAR2(4000));
update CIR_PT_INFO set FIRST_NAME_V = substr(FIRST_NAME, 0, 4000);
update CIR_PT_INFO set FORM_COMPLETED_BY_V = substr(FORM_COMPLETED_BY, 0, 4000);
update CIR_PT_INFO set LAST_NAME_V = substr(LAST_NAME, 0, 4000);
update CIR_PT_INFO set MIDDLE_INITIAL_V = substr(MIDDLE_INITIAL, 0, 4000);
update CIR_PT_INFO set PT_ID_V = substr(PT_ID, 0, 4000);
commit;
alter table CIR_PT_INFO drop (
FIRST_NAME, 
FORM_COMPLETED_BY, 
LAST_NAME, 
MIDDLE_INITIAL, 
PT_ID);
alter table CIR_PT_INFO rename column FIRST_NAME_V to FIRST_NAME;
alter table CIR_PT_INFO rename column FORM_COMPLETED_BY_V to FORM_COMPLETED_BY;
alter table CIR_PT_INFO rename column LAST_NAME_V to LAST_NAME;
alter table CIR_PT_INFO rename column MIDDLE_INITIAL_V to MIDDLE_INITIAL;
alter table CIR_PT_INFO rename column PT_ID_V to PT_ID;

alter table CIR_BLANK_FIELDS add (
BLANK_TEXT_ONE_V VARCHAR2(4000), 
BLANK_TEXT_TWO_V VARCHAR2(4000), 
BLANK_TEXT_THREE_V VARCHAR2(4000), 
BLANK_TEXT_FOUR_V VARCHAR2(4000), 
BLANK_TEXT_FIVE_V VARCHAR2(4000));
update CIR_BLANK_FIELDS set BLANK_TEXT_ONE_V = substr(BLANK_TEXT_ONE, 0, 4000);
update CIR_BLANK_FIELDS set BLANK_TEXT_TWO_V = substr(BLANK_TEXT_TWO, 0, 4000);
update CIR_BLANK_FIELDS set BLANK_TEXT_THREE_V = substr(BLANK_TEXT_THREE, 0, 4000);
update CIR_BLANK_FIELDS set BLANK_TEXT_FOUR_V = substr(BLANK_TEXT_FOUR, 0, 4000);
update CIR_BLANK_FIELDS set BLANK_TEXT_FIVE_V = substr(BLANK_TEXT_FIVE, 0, 4000);
commit;
alter table CIR_BLANK_FIELDS drop (
BLANK_TEXT_ONE, 
BLANK_TEXT_TWO, 
BLANK_TEXT_THREE, 
BLANK_TEXT_FOUR, 
BLANK_TEXT_FIVE);
alter table CIR_BLANK_FIELDS rename column BLANK_TEXT_ONE_V to BLANK_TEXT_ONE;
alter table CIR_BLANK_FIELDS rename column BLANK_TEXT_TWO_V to BLANK_TEXT_TWO;
alter table CIR_BLANK_FIELDS rename column BLANK_TEXT_THREE_V to BLANK_TEXT_THREE;
alter table CIR_BLANK_FIELDS rename column BLANK_TEXT_FOUR_V to BLANK_TEXT_FOUR;
alter table CIR_BLANK_FIELDS rename column BLANK_TEXT_FIVE_V to BLANK_TEXT_FIVE;

alter table CIR_CASE_INFO add (SPEC_NAME_V VARCHAR2(4000), SURGEON_V VARCHAR2(4000));
update CIR_CASE_INFO set SPEC_NAME_V = substr(SPEC_NAME, 0, 4000);
update CIR_CASE_INFO set SURGEON_V = substr(SURGEON, 0, 4000);
commit;
alter table CIR_CASE_INFO drop (SPEC_NAME, SURGEON);
alter table CIR_CASE_INFO rename column SPEC_NAME_V to SPEC_NAME;
alter table CIR_CASE_INFO rename column SURGEON_V to SURGEON;

alter table CIR_CLIN_PRES add (CLIN_COMMENTS_V VARCHAR2(4000));
update CIR_CLIN_PRES set CLIN_COMMENTS_V = substr(CLIN_COMMENTS, 0, 4000);
commit;
alter table CIR_CLIN_PRES drop (CLIN_COMMENTS);
alter table CIR_CLIN_PRES rename column CLIN_COMMENTS_V to CLIN_COMMENTS;

alter table CIR_FOLLOWUP_INFO add (MD_COMMENTS_V VARCHAR2(4000));
update CIR_FOLLOWUP_INFO set MD_COMMENTS_V = substr(MD_COMMENTS, 0, 4000);
commit;
alter table CIR_FOLLOWUP_INFO drop (MD_COMMENTS);
alter table CIR_FOLLOWUP_INFO rename column MD_COMMENTS_V to MD_COMMENTS;

alter table CIR_HOSPITALIZATION_INFO add (HOSPITAL_V VARCHAR2(4000));
update CIR_HOSPITALIZATION_INFO set HOSPITAL_V = substr(HOSPITAL, 0, 4000);
commit;
alter table CIR_HOSPITALIZATION_INFO drop (HOSPITAL);
alter table CIR_HOSPITALIZATION_INFO rename column HOSPITAL_V to HOSPITAL;

alter table CIR_SURGICAL_PATHOLOGY add (PATH_COMMENTS_V VARCHAR2(4000));
update CIR_SURGICAL_PATHOLOGY set PATH_COMMENTS_V = substr(PATH_COMMENTS, 0, 4000);
commit;
alter table CIR_SURGICAL_PATHOLOGY drop (PATH_COMMENTS);
alter table CIR_SURGICAL_PATHOLOGY rename column PATH_COMMENTS_V to PATH_COMMENTS;

alter table CIR_PATH_VERIFICATION add (
INTERNAL_NOTE_V VARCHAR2(4000), 
SLIDE_ALIAS_V VARCHAR2(4000), 
SUPPLIER_NOTE_V VARCHAR2(4000));
update CIR_PATH_VERIFICATION set INTERNAL_NOTE_V = substr(INTERNAL_NOTE, 0, 4000);
update CIR_PATH_VERIFICATION set SLIDE_ALIAS_V = substr(SLIDE_ALIAS, 0, 4000);
update CIR_PATH_VERIFICATION set SUPPLIER_NOTE_V = substr(SUPPLIER_NOTE, 0, 4000);
commit;
alter table CIR_PATH_VERIFICATION drop (INTERNAL_NOTE, SLIDE_ALIAS, SUPPLIER_NOTE);
alter table CIR_PATH_VERIFICATION rename column INTERNAL_NOTE_V to INTERNAL_NOTE;
alter table CIR_PATH_VERIFICATION rename column SLIDE_ALIAS_V to SLIDE_ALIAS;
alter table CIR_PATH_VERIFICATION rename column SUPPLIER_NOTE_V to SUPPLIER_NOTE;

-- Add new columns.
--
alter table CIR_BLANK_FIELDS add (
BLANK_CV1_ONE_CUI VARCHAR2(30), 
BLANK_CV1_TWO_CUI VARCHAR2(30), 
BLANK_CV1_THREE_CUI VARCHAR2(30), 
BLANK_CV1_FOUR_CUI VARCHAR2(30), 
BLANK_CV1_FIVE_CUI VARCHAR2(30), 
OTHER_BLANK_CV1_FIVE VARCHAR2(200));

alter table CIR_PT_INFO add (
AGREE_BLOOD_OBTAIN_CUI VARCHAR2(30),
AGREE_NEEDLE_CUI VARCHAR2(30),
DONATE_URINE_CUI VARCHAR2(30),
DONATE_TISSUE_CUI VARCHAR2(30),
DONATE_SALIVA_CUI VARCHAR2(30),
DONATE_CSF_CUI VARCHAR2(30),
DONATE_AMNIOTIC_CUI VARCHAR2(30),
AGREE_GENE_TEST_CUI VARCHAR2(30),
GENE_INFO_CUI VARCHAR2(30),
REVIEW_MED_REC_CUI VARCHAR2(30),
CONTACT_IN_FUTURE_CUI VARCHAR2(30),
DONATE_BLOOD_CUI VARCHAR2(30),
GENDER_CUI VARCHAR2(30),
OTHER_GENDER VARCHAR2(200));

alter table CIR_PATH_VERIFICATION add (
CELL_SOURCE_CUI VARCHAR2(30),
OTHER_CELL_SOURCE VARCHAR2(200),
SAMPLE_DX_CUI VARCHAR2(30),
OTHER_SAMPLE_DX VARCHAR2(200),
SAMPLE_TOO_CUI VARCHAR2(30),
OTHER_SAMPLE_TOO VARCHAR2(200),
SAMPLE_SOF_CUI VARCHAR2(30),
OTHER_SAMPLE_SOF VARCHAR2(200));

-- Add 2 new multivalued tables.
--
CREATE TABLE CIR_BLANK_FIELDS_MULTI (
ID NUMBER NOT NULL,
FORM_ID VARCHAR2(10),
DATA_ELEMENT_CUI VARCHAR2(30),
DATA_VALUE_CUI VARCHAR2(30),
OTHER_DATA_VALUE VARCHAR2(200))
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;

CREATE UNIQUE INDEX PK_CIR_BLANK_FIELDS_M ON CIR_BLANK_FIELDS_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_BLANK_FIELDS_MULTI ADD CONSTRAINT PK_CIR_BLANK_FIELDS_M PRIMARY KEY (ID);

CREATE INDEX CIR_BLANK_FIELDS_I3 ON CIR_BLANK_FIELDS_MULTI (FORM_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE TABLE CIR_PT_INFO_MULTI (
ID NUMBER NOT NULL,
FORM_ID VARCHAR2(10),
DATA_ELEMENT_CUI VARCHAR2(30),
DATA_VALUE_CUI VARCHAR2(30),
OTHER_DATA_VALUE VARCHAR2(200))
TABLESPACE CIR_TBL_TBS
PCTUSED    85
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;

CREATE UNIQUE INDEX PK_CIR_PT_INFO_M ON CIR_PT_INFO_MULTI (ID)
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

ALTER TABLE CIR_PT_INFO_MULTI ADD CONSTRAINT PK_CIR_PT_INFO_M PRIMARY KEY (ID);

CREATE INDEX CIR_PT_INFO_I3 ON CIR_PT_INFO_MULTI (FORM_ID)
LOGGING
TABLESPACE CIR_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             16K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;