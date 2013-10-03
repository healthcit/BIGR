CREATE OR REPLACE PROCEDURE DATA_IMPORT_GET_ASM_ID(
 p_asmid OUT VARCHAR,
 p_error OUT VARCHAR2
 )
IS
 zeros VARCHAR2(9);
 v_id number;

BEGIN
  -- Generate next available donor id
    SELECT DATA_IMPORT_ASM_SEQ.NEXTVAL
    INTO v_id
    FROM DUAL;
    zeros:='000000000';
    p_asmid:='ASX'||substr(zeros,0,9-length(v_id))||v_id;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    p_error := 'DATA_IMPORT_GET_ASM_ID -- no data found exception' || sqlerrm;
  WHEN OTHERS THEN
    p_error := 'DATA_IMPORT_GET_ASM_ID -- error while generating new asm id' || sqlerrm;
END;
/
