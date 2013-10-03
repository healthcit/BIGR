-- DET19 schema upgrade
-- 
-- This upgrade only modifies the schema from DET18 and does not migrate 
-- any existing data.  Therefore any existing data will be lost!
--

-- Add new columns.
--
alter table CIR_SAMPLE_INFO add (
STAIN_TYPE_CUI VARCHAR2(30),
OTHER_STAIN_TYPE VARCHAR2(200),
SUSPENSION_MEDIUM_CUI VARCHAR2(30),
OTHER_SUSPENSION_MEDIUM VARCHAR2(200)
);