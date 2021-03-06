CREATE OR REPLACE TRIGGER ILTDS_BOX_LAYOUT_PK_TRG
BEFORE INSERT
ON ILTDS_BOX_LAYOUT
REFERENCING NEW AS NEW OLD AS OLD
FOR EACH ROW
DECLARE
	v_seq_val NUMBER;
BEGIN
  IF :new.box_layout_id IS NULL
  THEN
    SELECT ILTDS_BOX_LAYOUT_ID_SEQ.nextval INTO v_seq_val FROM dual;
	:new.box_layout_id := 'LY'||LPAD(TO_CHAR(v_seq_val), 6, '0');
  END IF;
EXCEPTION
  WHEN others THEN
    raise_application_error(-20001, 'Primary key violation on ILTDS_BOX_LAYOUT: '||SQLERRM);
END ILTDS_BOX_LAYOUT_PK_TRG;
/
