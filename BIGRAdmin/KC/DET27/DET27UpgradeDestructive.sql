
ALTER TABLE CIR_SAMPLE_INFO
 ADD (OTHER_TCGA_SOURCE_HOSPITAL  VARCHAR2(200 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (TCGA_SOURCE_HOSPITAL_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (COPATH_CASE  VARCHAR2(4000 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (TCGA_COMMENT  CLOB);

ALTER TABLE CIR_SAMPLE_INFO
 ADD (BLADDER_TUMOR_GRADE_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (QUALIFIES_FOR_TCGA_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (COPATH_CASE_CUI  VARCHAR2(30 BYTE));

ALTER TABLE CIR_SAMPLE_INFO
 ADD (TCGA_COMMENT_CUI  VARCHAR2(30 BYTE));
