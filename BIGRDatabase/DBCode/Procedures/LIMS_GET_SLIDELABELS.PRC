CREATE OR REPLACE PROCEDURE LIMS_get_slidelabels(
 p_sample IN LIMS_SLIDE.SAMPLE_BARCODE_ID%TYPE,
 p_count IN PLS_INTEGER,
 p_labels OUT VARCHAR,
 p_rowcount OUT PLS_INTEGER,
 p_error OUT VARCHAR2
 )
IS
 v_labels VARCHAR2(300);
 zeros VARCHAR2(10);
 temp VARCHAR2(10);
 v_id number;
 v_sample_type ILTDS_SAMPLE.SAMPLE_TYPE_CID%TYPE;
 ERROR_BARCODE_EXCEPTION EXCEPTION;
 UNKNOWN_SAMPLE_TYPE_EXCEPTION EXCEPTION;
 UNSUPP_SAMPLE_TYPE_EXCEPTION EXCEPTION;
BEGIN

  -- Generate next available SLIDE label(s)
  FOR i IN 0 .. p_count-1 LOOP
    SELECT LIMS_SLIDE_LABEL_SEQ.NEXTVAL INTO v_id FROM DUAL;

	-- Determine sample type of the sample. Check if sample id contains the information needed
	-- to determine the sample type. If dealing with an imported sample, check the sample type
	-- field to determine slide id prefix. NOTE: if sample type cannot be determined, or if
	-- it isn't frozen or paraffin tissue, raise an exception.
	IF (substr(p_sample, 1, 1) = 'F')
	THEN
	  v_labels := 'SF' || v_id;
	ELSIF (substr(p_sample, 1, 1) = 'P')
	THEN
	  v_labels := 'SP' || v_id;
	ELSE
	  SELECT sample_type_cid INTO v_sample_type FROM ILTDS_SAMPLE WHERE SAMPLE_BARCODE_ID = p_sample;
	  IF v_sample_type IS NULL
	  THEN
	    raise UNKNOWN_SAMPLE_TYPE_EXCEPTION;
      ELSIF v_sample_type = 'CA00052C'
      THEN
        v_labels := 'SF' || v_id;
      ELSIF v_sample_type = 'CA09400C'
      THEN
	    v_labels := 'SP' || v_id;
	  ELSE
	    v_labels := 'SL' || v_id;
	  END IF;
	END IF;

	zeros:='0000000';
	temp:=substr(v_labels,3,length(v_labels));
	temp:=Lang_Integer.toRadixString(temp,16);
	p_labels:=p_labels||substr(v_labels,0,2)||substr(zeros,0,8-length(temp))||temp;

  END LOOP;

  -- no exception happended
  p_rowcount := SQL%ROWCOUNT;
EXCEPTION
  WHEN UNKNOWN_SAMPLE_TYPE_EXCEPTION THEN
    p_rowcount := -1;
	p_error := 'LIMS - get_slidelabels-- sample type could not be determined. ' || sqlerrm;
  WHEN UNSUPP_SAMPLE_TYPE_EXCEPTION THEN
    p_rowcount := -1;
	p_error := 'LIMS - get_slidelabels-- unsupported sample type (not frozen or paraffin tissue). ' || sqlerrm;
  WHEN NO_DATA_FOUND THEN
    p_rowcount := -1;
    p_error := 'LIMS - get_slidelabels-- no data found exception. ' || sqlerrm;
  WHEN OTHERS THEN
    p_rowcount := -1;
    p_error := 'LIMS - get_slidelabels-- error while generating new slide label. ' || sqlerrm;
END;
/
