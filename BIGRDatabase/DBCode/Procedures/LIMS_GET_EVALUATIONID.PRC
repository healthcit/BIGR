CREATE OR REPLACE PROCEDURE LIMS_get_evaluationid(
 p_evalid OUT VARCHAR,
 p_error OUT VARCHAR2
 )
IS
 zeros VARCHAR2(10);
 temp VARCHAR2(10);
 v_id number;

BEGIN

  -- Generate next available evaluation id
    SELECT LIMS_PATHOLOGY_EVALUATION_SEQ.NEXTVAL
    INTO v_id
    FROM DUAL;
    zeros:='0000000';
    temp:=Lang_Integer.toRadixString(v_id,16);
    p_evalid:='PV'||substr(zeros,0,8-length(temp))||temp;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    p_error := 'LIMS_get_evaluationid -- no data found exception' || sqlerrm;
  WHEN OTHERS THEN
    p_error := 'LIMS_get_evaluationid -- error while generating new evaluation id' || sqlerrm;
END;
/
