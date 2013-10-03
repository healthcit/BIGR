CREATE OR REPLACE PROCEDURE DATA_IMPORT_GET_DONOR_ID(
 p_donorid OUT VARCHAR,
 p_error OUT VARCHAR2
 )
IS
 zeros VARCHAR2(10);
 v_id number;

BEGIN
  -- Generate next available donor id
    SELECT DATA_IMPORT_DONOR_SEQ.NEXTVAL
    INTO v_id
    FROM DUAL;
    zeros:='000000000';
    p_donorid:='AX'||substr(zeros,0,10-length(v_id))||v_id;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    p_error := 'DATA_IMPORT_GET_DONOR_ID -- no data found exception' || sqlerrm;
  WHEN OTHERS THEN
    p_error := 'DATA_IMPORT_GET_DONOR_ID -- error while generating new donor id' || sqlerrm;
END;
/
