CREATE OR REPLACE PROCEDURE DATA_IMPORT_GET_CONSENT_ID(
 p_consentid OUT VARCHAR,
 p_error OUT VARCHAR2
 )
IS
 zeros VARCHAR2(10);
 v_id number;

BEGIN
  -- Generate next available consent id
    SELECT DATA_IMPORT_CONSENT_SEQ.NEXTVAL
    INTO v_id
    FROM DUAL;
    zeros:='000000000';
    p_consentid:='CX'||substr(zeros,0,10-length(v_id))||v_id;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    p_error := 'DATA_IMPORT_GET_CONSENT_ID -- no data found exception' || sqlerrm;
  WHEN OTHERS THEN
    p_error := 'DATA_IMPORT_GET_CONSENT_ID -- error while generating new consent id' || sqlerrm;
END;
/
