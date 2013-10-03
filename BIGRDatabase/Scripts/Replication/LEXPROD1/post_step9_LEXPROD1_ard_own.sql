------------------------------------------------------------------------------------------
--	PART 3 - Run on LEXPROD1 as ARDAIS_OWNER
------------------------------------------------------------------------------------------
--CREATE INDEX ARD_CONCEPT_GRAPH_IN1 ON ARD_CONCEPT_GRAPH
--(GRAPH_CONTEXT)
--NOLOGGING
--TABLESPACE OTHER_IDX_TBS
--PCTFREE    10
--INITRANS   2
--MAXTRANS   255
--STORAGE    (
--            INITIAL          200K
--            NEXT             8K
--            MINEXTENTS       1
--            MAXEXTENTS       505
--            PCTINCREASE      0
--            FREELISTS        1
--            FREELIST GROUPS  1
--            BUFFER_POOL      DEFAULT
--           )
--NOPARALLEL;

--CREATE UNIQUE INDEX ARD_CONCEPT_GRAPH_UQ1 ON ARD_CONCEPT_GRAPH
--(GRAPH_CONTEXT, PARENT_CODE, CHILD_CODE)
--NOLOGGING
--TABLESPACE OTHER_IDX_TBS
--PCTFREE    10
--INITRANS   2
--MAXTRANS   255
--STORAGE    (
--            INITIAL          344K
--            NEXT             80K
--            MINEXTENTS       1
--            MAXEXTENTS       505
--            PCTINCREASE      0
--            FREELISTS        1
--            FREELIST GROUPS  1
--            BUFFER_POOL      DEFAULT
--           )
--NOPARALLEL;

CREATE INDEX ARD_POLICY_IN1 ON ARD_POLICY (DEFAULT_LOGICAL_REPOS_ID)
tablespace other_idx_tbs
NOLOGGING
storage (initial 1M next 1M pctincrease 0);

CREATE INDEX ARD_POLICY_IN2 ON ARD_POLICY (RESTRICTED_LOGICAL_REPOS_ID)
tablespace other_idx_tbs
NOLOGGING
storage (initial 1M next 1M pctincrease 0);

CREATE INDEX ES_ARDAIS_USER_IN1 ON ES_ARDAIS_USER
(ARDAIS_ACCT_KEY)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             8K
            MINEXTENTS       1
            MAXEXTENTS       255
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


CREATE UNIQUE INDEX ES_ARDAIS_USER_UQ1 ON ES_ARDAIS_USER
(ARDAIS_USER_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             8K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

-- MR_7223 - Add an index to BLOOD_COLL - 05/20/2004
CREATE INDEX ILTDS_BLOOD_COLLECTION_IN1 ON ILTDS_BLOOD_COLLECTION
(CONSENT_ID)
LOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             8K
            MINEXTENTS       1
            MAXEXTENTS       255
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX ILTDS_BTX_HISTORY_IN1 ON ILTDS_BTX_HISTORY
(BTX_END_DATETIME)
LOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          2M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

-- MR_6778 - Prevent duplicate rows in new BMS tables
CREATE UNIQUE INDEX ARD_LOGICAL_REPOS_USER_IN3 ON ARD_LOGICAL_REPOS_USER
(REPOSITORY_ID, ardais_user_id)
LOGGING
TABLESPACE OTHER_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE UNIQUE INDEX ARD_LOGICAL_REPOS_ITEM_IN3 ON ARD_LOGICAL_REPOS_ITEM
(repository_id, item_id, item_type)
LOGGING
TABLESPACE OTHER_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


CREATE INDEX ILTDS_SAMPLE_IN1 ON ILTDS_SAMPLE
(ASM_ID)
NOLOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          6240K
            NEXT             512K
            MINEXTENTS       1
            MAXEXTENTS       255
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


CREATE INDEX ILTDS_SAMPLE_IN2 ON ILTDS_SAMPLE
(BOX_BARCODE_ID)
NOLOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          4M
            NEXT             2M
            MINEXTENTS       1
            MAXEXTENTS       255
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


CREATE INDEX ILTDS_SAMPLE_IN3 ON ILTDS_SAMPLE
(PRODUCT_ID)
NOLOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          4M
            NEXT             2M
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


CREATE INDEX ILTDS_SAMPLE_IN4 ON ILTDS_SAMPLE
(PARENT_BARCODE_ID)
NOLOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          2M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


CREATE INDEX ILTDS_SAMPLE_IN5 ON ILTDS_SAMPLE
(QC_VERIFIED_DATE)
NOLOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


CREATE INDEX ILTDS_SAMPLE_IN6 ON ILTDS_SAMPLE
(PULL_DATE)
NOLOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          160K
            NEXT             64K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

-- MR_7264 - New colums in ILTDS_SAMPLE + trigg   - 06/16/2004
CREATE INDEX ILTDS_SAMPLE_IN7 ON ILTDS_SAMPLE
(CONSENT_ID)
NOLOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       255
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX ILTDS_SAMPLE_IN8 ON ILTDS_SAMPLE
(ARDAIS_ID)
NOLOGGING
TABLESPACE ILTDS_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       255
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE INDEX PDC_LOOKUP_IN1 ON PDC_LOOKUP
(CATEGORY_DOMAIN)
NOLOGGING
TABLESPACE OTHER_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          472K
            NEXT             8K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

CREATE UNIQUE INDEX ZPDC_LOOKUP_UQ1 ON PDC_LOOKUP
(SYSTEM_NAME)
LOGGING
TABLESPACE OTHER_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

--/* CREATE OR REPLACE TRIGGER LIMS_PATH_EVAL_UPDATE_RNA
--AFTER INSERT OR UPDATE OF TUMOR_CELLS, LESION_CELLS, NORMAL_CELLS,
--CELLULARSTROMA_CELLS, HYPOACELLULARSTROMA_CELLS, NECROSIS_CELLS, EXTERNAL_COMMENTS,
--INTERNAL_COMMENTS, REPORTED_YN ON LIMS_PATHOLOGY_EVALUATION
--FOR EACH ROW
--BEGIN
--   IF :NEW.REPORTED_YN = 'Y'
--   THEN
--      UPDATE RNA_BATCH_DETAIL SET
--			TUMOR_CELLS = :NEW.TUMOR_CELLS,
--			LESION_CELLS = :NEW.LESION_CELLS,
--			NORMAL_CELLS = :NEW.NORMAL_CELLS,
--			CELLULARSTROMA_CELLS = :NEW.CELLULARSTROMA_CELLS,
--			HYPOACELLULARSTROMA_CELLS = :NEW.HYPOACELLULARSTROMA_CELLS,
--			NECROSIS_CELLS = :NEW.NECROSIS_CELLS,
--			EXTERNAL_COMMENTS = :NEW.EXTERNAL_COMMENTS,
--			INTERNAL_COMMENTS = :NEW.INTERNAL_COMMENTS
--	  WHERE SAMPLE_BARCODE_ID = :NEW.SAMPLE_BARCODE_ID;
--   END IF;
--END;
--/
--*/
