CREATE SEQUENCE ILTDS_ATTACHMENTS_SEQ MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 485 NOCACHE NOORDER NOCYCLE ; 

CREATE TABLE ILTDS_ATTACHMENTS 
  ( 
    ID NUMBER(10,0) NOT NULL ENABLE, 
    ARDAIS_ACCT_KEY VARCHAR2(10 BYTE) NOT NULL ENABLE, 
    ARDAIS_ID VARCHAR2(12 BYTE), 
    CONSENT_ID VARCHAR2(12 BYTE), 
    SAMPLE_BARCODE_ID VARCHAR2(12 BYTE), 
    IS_PHI_YN VARCHAR2(1 BYTE) DEFAULT 'N' NOT NULL ENABLE, 
    COMMENTS VARCHAR2(500 BYTE), 
    NAME VARCHAR2(150 BYTE) NOT NULL ENABLE, 
    ATTACHMENT BLOB NOT NULL ENABLE, 
    CREATED_BY VARCHAR2(20 BYTE), 
    CREATED_DATE TIMESTAMP (6), 
    CONTENTTYPE VARCHAR2(70 BYTE) NOT NULL ENABLE, 
    CONSTRAINT ILTDS_ATTACHMENTS_PK PRIMARY KEY (ID) USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645 PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT) TABLESPACE USERS ENABLE, 
    CONSTRAINT ILTDS_ATTACHMENTS_ES_ARDA_FK1 FOREIGN KEY (ARDAIS_ACCT_KEY) REFERENCES ES_ARDAIS_ACCOUNT (ARDAIS_ACCT_KEY) ON 
  DELETE CASCADE ENABLE 
  ) 
   TABLESPACE USERS LOB ( ATTACHMENT ) 
   STORE AS 
  ( 
    TABLESPACE USERS ENABLE STORAGE IN ROW CHUNK 8192 PCTVERSION 10 NOCACHE LOGGING STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645 PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT) 
  ) ; 


CREATE OR REPLACE TRIGGER ILTDS_ATTACHMENTS_PK_TRG BEFORE 
  INSERT ON ILTDS_ATTACHMENTS REFERENCING OLD AS OLD NEW AS NEW FOR EACH ROW DECLARE seq_val NUMBER; 
  BEGIN 
    IF :new.id IS NULL THEN 
      SELECT iltds_attachments_seq.nextval INTO seq_val FROM dual; 
      :new.id := seq_val; 
    END IF; 
  EXCEPTION 
  WHEN OTHERS THEN 
    raise_application_error(-20001, 'Primary key violation on ILTDS_ATTACHMENTS: '||SQLERRM); 
  END ILTDS_ATTACHMENTS_PK_TRG; 
  / 
  ALTER TRIGGER ILTDS_ATTACHMENTS_PK_TRG ENABLE; 


GRANT ALL PRIVILEGES ON ILTDS_ATTACHMENTS TO public ;

create public synonym ILTDS_ATTACHMENTS for ILTDS_ATTACHMENTS;

