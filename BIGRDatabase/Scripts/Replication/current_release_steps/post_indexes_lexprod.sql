--------------------------------------------------------------------------
-- Play this script in ARDAIS_OWNER@LEXPROD1 to make it look like ARDAIS_OWNER@GENPROD1
--                                                                      --
-- Please review the script before using it to make sure it won't       --
-- cause any unacceptable data loss.                                    --
--                                                                      --
set linesize 500
spool post_indexes_lexprod.log
Prompt INDEX ES_ARDAIS_ACCOUNT_IN1;
--
-- ES_ARDAIS_ACCOUNT_IN1  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ACCOUNT_IN1 ON ARDAIS_OWNER.ES_ARDAIS_ACCOUNT
(BRAND_ID)
NOLOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_ACCOUNT_IN2;
--
-- ES_ARDAIS_ACCOUNT_IN2  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ACCOUNT_IN2 ON ARDAIS_OWNER.ES_ARDAIS_ACCOUNT
(PRIMARY_LOCATION)
NOLOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_ACCOUNT_IN3;
--
-- ES_ARDAIS_ACCOUNT_IN3  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ACCOUNT_IN3 ON ARDAIS_OWNER.ES_ARDAIS_ACCOUNT
(DEFAULT_BOX_LAYOUT_ID)
NOLOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_CONSENTVER_IN1;
--
-- ES_ARDAIS_CONSENTVER_IN1  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_CONSENTVER_IN1 ON ARDAIS_OWNER.ES_ARDAIS_CONSENTVER
(IRBPROTOCOL_ID)
NOLOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_IRB_IN1;
--
-- ES_ARDAIS_IRB_IN1  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_IRB_IN1 ON ARDAIS_OWNER.ES_ARDAIS_IRB
(ARDAIS_ACCT_KEY)
NOLOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_IRB_IN2;
--
-- ES_ARDAIS_IRB_IN2  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_IRB_IN2 ON ARDAIS_OWNER.ES_ARDAIS_IRB
(POLICY_ID)
NOLOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_ORDER_IN1;
--
-- ES_ARDAIS_ORDER_IN1  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ORDER_IN1 ON ARDAIS_OWNER.ES_ARDAIS_ORDER
(ARDAIS_ACCT_KEY, ARDAIS_USER_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_ORDER_IN2;
--
-- ES_ARDAIS_ORDER_IN2  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ORDER_IN2 ON ARDAIS_OWNER.ES_ARDAIS_ORDER
(ARDAIS_ACCT_KEY, BILL_TO_ADDR_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_ORDER_IN3;
--
-- ES_ARDAIS_ORDER_IN3  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ORDER_IN3 ON ARDAIS_OWNER.ES_ARDAIS_ORDER
(BILL_TO_ADDR_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_ORDER_IN4;
--
-- ES_ARDAIS_ORDER_IN4  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ORDER_IN4 ON ARDAIS_OWNER.ES_ARDAIS_ORDER
(ARDAIS_USER_ID, ARDAIS_ACCT_KEY)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          32K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_ORDER_STATUS_IN1;
--
-- ES_ARDAIS_ORDER_STATUS_IN1  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ORDER_STATUS_IN1 ON ARDAIS_OWNER.ES_ARDAIS_ORDER_STATUS
(ORDER_NUMBER)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_ORDER_STATUS_IN2;
--
-- ES_ARDAIS_ORDER_STATUS_IN2  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ORDER_STATUS_IN2 ON ARDAIS_OWNER.ES_ARDAIS_ORDER_STATUS
(ARDAIS_ACCT_KEY, ARDAIS_USER_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_ORDER_STATUS_IN3;
--
-- ES_ARDAIS_ORDER_STATUS_IN3  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_ORDER_STATUS_IN3 ON ARDAIS_OWNER.ES_ARDAIS_ORDER_STATUS
(ARDAIS_USER_ID, ARDAIS_ACCT_KEY)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          32K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_USER_IN1;
--
-- ES_ARDAIS_USER_IN1  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ARDAIS_USER_IN1 ON ARDAIS_OWNER.ES_ARDAIS_USER
(ARDAIS_ACCT_KEY)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ARDAIS_USER_UQ1;
--
-- ES_ARDAIS_USER_UQ1  (Index) 
--
CREATE UNIQUE INDEX ARDAIS_OWNER.ES_ARDAIS_USER_UQ1 ON ARDAIS_OWNER.ES_ARDAIS_USER
(ARDAIS_USER_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ORDER_LINE_IN1;
--
-- ES_ORDER_LINE_IN1  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ORDER_LINE_IN1 ON ARDAIS_OWNER.ES_ORDER_LINE
(ORDER_NUMBER)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          32K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_ORDER_LINE_IN2;
--
-- ES_ORDER_LINE_IN2  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_ORDER_LINE_IN2 ON ARDAIS_OWNER.ES_ORDER_LINE
(BARCODE_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_SHOPPING_CART_DETAIL_IN1;
--
-- ES_SHOPPING_CART_DETAIL_IN1  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_SHOPPING_CART_DETAIL_IN1 ON ARDAIS_OWNER.ES_SHOPPING_CART_DETAIL
(ARDAIS_ACCT_KEY, SHOPPING_CART_ID, ARDAIS_USER_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_SHOPPING_CART_DETAIL_IN2;
--
-- ES_SHOPPING_CART_DETAIL_IN2  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_SHOPPING_CART_DETAIL_IN2 ON ARDAIS_OWNER.ES_SHOPPING_CART_DETAIL
(SHOPPING_CART_ID, ARDAIS_ACCT_KEY, ARDAIS_USER_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          32K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_SHOPPING_CART_DETAIL_UQ2;
--
-- ES_SHOPPING_CART_DETAIL_UQ2  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_SHOPPING_CART_DETAIL_UQ2 ON ARDAIS_OWNER.ES_SHOPPING_CART_DETAIL
(BARCODE_ID, ARDAIS_USER_ID)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

Prompt INDEX ES_SHOPPING_CART_IN1;
--
-- ES_SHOPPING_CART_IN1  (Index) 
--
CREATE INDEX ARDAIS_OWNER.ES_SHOPPING_CART_IN1 ON ARDAIS_OWNER.ES_SHOPPING_CART
(ARDAIS_USER_ID, ARDAIS_ACCT_KEY)
LOGGING
TABLESPACE ES_IDX_TBS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          16K
            NEXT             40K
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;

spool off
