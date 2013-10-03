CREATE OR REPLACE PROCEDURE BIGR_GET_ROLE_ID(
 p_roleid OUT VARCHAR,
 p_error OUT VARCHAR2
 )
IS
 zeros VARCHAR2(10);
 temp VARCHAR2(10);
 v_id number;

BEGIN

  -- Generate next available role id
    SELECT BIGR_ROLE_SEQ.NEXTVAL
    INTO v_id
    FROM DUAL;
    zeros:='0000000';
    temp:=Lang_Integer.toRadixString(v_id,16);
    p_roleid:='RL'||substr(zeros,0,8-length(temp))||temp;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    p_error := 'BIGR_GET_ROLE_ID -- no data found exception' || sqlerrm;
  WHEN OTHERS THEN
    p_error := 'BIGR_GET_ROLE_ID -- error while generating new role id' || sqlerrm;
END;
/
