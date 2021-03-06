set feedback on echo on
--
-- CIR_IDX_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE CIR_IDX_TBS
CREATE TABLESPACE CIR_IDX_TBS DATAFILE 
  '&1/cir_idx_tbs_01.dbf' SIZE 200M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;


--
-- CIR_TBL_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE CIR_TBL_TBS
CREATE TABLESPACE CIR_TBL_TBS DATAFILE 
  '&1/cir_tbl_tbs_01.dbf' SIZE 200M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;



--
-- ES_IDX_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE ES_IDX_TBS
CREATE TABLESPACE ES_IDX_TBS DATAFILE 
  '&1/es_idx_tbs_01.dbf' SIZE 100M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;


--
-- ES_TBL_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE ES_TBL_TBS
CREATE TABLESPACE ES_TBL_TBS DATAFILE 
  '&1/es_tbl_tbs_01.dbf' SIZE 100M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;


--
-- ILTDS_IDX_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE ILTDS_IDX_TBS
CREATE TABLESPACE ILTDS_IDX_TBS DATAFILE 
  '&1/iltds_idx_tbs_01.dbf' SIZE 750M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;


--
-- ILTDS_TBL_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE ILTDS_TBL_TBS
CREATE TABLESPACE ILTDS_TBL_TBS DATAFILE 
  '&1/iltds_tbl_tbs_01.dbf' SIZE 500M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;


--
-- LIMS_IDX_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE LIMS_IDX_TBS
CREATE TABLESPACE LIMS_IDX_TBS DATAFILE 
  '&1/lims_idx_tbs_01.dbf' SIZE 150M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;


--
-- LIMS_TBL_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE LIMS_TBL_TBS 
CREATE TABLESPACE LIMS_TBL_TBS DATAFILE 
  '&1/lims_tbl_tbs_01.dbf' SIZE 200M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;


--
-- OTHER_IDX_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE OTHER_IDX_TBS
CREATE TABLESPACE OTHER_IDX_TBS DATAFILE 
  '&1/other_idx_tbs_01.dbf' SIZE 600M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;


--
-- OTHER_TBL_TBS  (Tablespace) 
--
prompt CREATE TABLESPACE OTHER_TBL_TBS
CREATE TABLESPACE OTHER_TBL_TBS DATAFILE 
  '&1/other_tbl_tbs_01.dbf' SIZE 1400M
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO;

exit

