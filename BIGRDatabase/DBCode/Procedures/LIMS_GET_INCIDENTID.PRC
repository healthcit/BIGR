CREATE OR REPLACE PROCEDURE LIMS_GET_INCIDENTID(
 p_incidentid OUT VARCHAR,
 p_error OUT VARCHAR2
 )
IS
 zeros VARCHAR2(10);
 temp VARCHAR2(10);
 v_id number;

BEGIN

  -- Generate next available incident id
    SELECT LIMS_INCIDENT_SEQ.NEXTVAL
    INTO v_id
    FROM DUAL;
    zeros:='0000000';
    temp:=Lang_Integer.toRadixString(v_id,16);
    p_incidentid:='IN'||substr(zeros,0,8-length(temp))||temp;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    p_error := 'LIMS_GET_INCIDENTID -- no data found exception' || sqlerrm;
  WHEN OTHERS THEN
    p_error := 'LIMS_GET_INCIDENTID -- error while generating new incident id' || sqlerrm;
END;
/
