-- DET21 schema upgrade
-- 
-- This upgrade only modifies the schema from DET19 (DET20 was skipped) and does
-- not migrate any existing data.  Therefore any existing data will be lost!
--

-- Add new columns.
--
ALTER TABLE CIR_SAMPLE_INFO
 ADD (SAMPLE_TECHNIQUE_CUI  VARCHAR2(30));
 
ALTER TABLE CIR_SAMPLE_INFO
 ADD (OTHER_SAMPLE_TECHNIQUE  VARCHAR2(200));
 
ALTER TABLE CIR_SAMPLE_INFO
 ADD (SAMPLE_SUBTYPE_CUI  VARCHAR2(30));
  
ALTER TABLE CIR_SAMPLE_INFO
 ADD (SAMPLE_SUBTYPE  VARCHAR2(4000));
