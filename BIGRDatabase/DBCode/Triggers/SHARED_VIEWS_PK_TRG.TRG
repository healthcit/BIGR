CREATE OR REPLACE TRIGGER SHARED_VIEWS_PK_TRG BEFORE
  INSERT ON SHARED_VIEWS REFERENCING OLD AS OLD NEW AS NEW FOR EACH ROW DECLARE seq_val NUMBER;
  BEGIN
    IF :new.id IS NULL THEN
      SELECT SHARED_VIEWS_SEQ.nextval INTO seq_val FROM dual;
      :new.id := seq_val;
    END IF;
  EXCEPTION
  WHEN OTHERS THEN
    raise_application_error(-20001, 'Primary key violation on ILTDS_ATTACHMENTS: '||SQLERRM);
  END SHARED_VIEWS_PK_TRG;
  /