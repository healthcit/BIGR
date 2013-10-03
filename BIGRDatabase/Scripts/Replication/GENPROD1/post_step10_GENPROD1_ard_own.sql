------------------------------------------------------------------------------------------
--	Run on GENPROD1 as ARDAIS_OWNER
------------------------------------------------------------------------------------------
CREATE INDEX RNA_RNA_LIST_IN1 ON RNA_RNA_LIST
(VIALTOUSE)
LOGGING
TABLESPACE OTHER_IDX_TBS
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


CREATE UNIQUE INDEX RNA_RNA_LIST_UQ1 ON RNA_RNA_LIST
(VIALTOSEND)
LOGGING
TABLESPACE OTHER_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          80K
            NEXT             32K
            MINEXTENTS       1
            MAXEXTENTS       505
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

