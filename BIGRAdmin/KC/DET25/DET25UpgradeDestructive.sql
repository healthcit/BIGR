
ALTER TABLE CIR_SAMPLE_INFO
 ADD (BLOOD_SAMPLE_SOURCE_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (COLLECTION_FORMAT_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (SPECIES_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (CRI_FIXATIVE_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (OTHER_SPECIES  VARCHAR2(200 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (PARENT_TISSUE_DETAIL_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (SPECIMEN_DIMENSIONS_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (SPECIMEN_DIM_UNIT_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (SPECIMEN_DIMENSIONS  VARCHAR2(4000 BYTE));
 
ALTER TABLE CIR_SAMPLE_INFO
 ADD (OTHER_FROZEN_TISSUE_PREP  VARCHAR2(200 BYTE));