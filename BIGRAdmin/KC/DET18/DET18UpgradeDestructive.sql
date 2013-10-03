-- DET18 schema upgrade
-- 
-- This upgrade only modifies the schema from DET17 and does not migrate 
-- any existing data.  Therefore any existing data will be lost!
--

-- Handle columns that had a datatype change
alter table CIR_CASE_INFO drop (
CASE_ID, 
NUMBER_CASE
);
alter table CIR_CASE_INFO add (
CASE_ID VARCHAR2(4000), 
NUMBER_CASE VARCHAR2(4000)
);

alter table CIR_HOSPITALIZATION_INFO drop (
HOSPITAL_POSTAL_CODE
);
alter table CIR_HOSPITALIZATION_INFO add (
HOSPITAL_POSTAL_CODE VARCHAR2(4000)
);

alter table CIR_PT_INFO drop (
MRN, 
PARTICIPANT_ID, 
POSTAL_CODE, 
SSN);
alter table CIR_PT_INFO add (
MRN VARCHAR2(4000), 
PARTICIPANT_ID VARCHAR2(4000),
POSTAL_CODE VARCHAR2(4000), 
SSN VARCHAR2(4000)
);
