----------------------------------------------------------------------------------------------------
--   Allows the user to assign a range of sample ids to an account.  This is used
--	 in the Sample Intake functionality to assign a group of sample ids to an
--	 account to allow them to bring non-Ardais samples into BIGR.
--
--   Input:  1) The user performing the action.  Must be a valid ES_ARDAIS_USER
--			 2) The number of sample ids to generate/assign.
--			 3) The account to which the sample ids should be assigned.  
--				Must be a valid ES_ARDAIS_ACCOUNT
--
--	 Output:
--			 1) First sample id in the range of generated/assigned ids
--			 2) Last sample id in the range of generated/assigned ids
-----------------------------------------------------------------------------------------------------

create or replace procedure BIGR_ASSIGN_SAMPLE_IDS(p_ard_user_id varchar2,
												   p_sample_count number,
												   p_account_id varchar2,
												   p_sample_id_start out varchar2,
												   p_sample_id_end out varchar2,
												   p_sample_id_next out varchar2)
is
    zeros             VARCHAR2(10);
    temp              VARCHAR2(10);
    v_id              NUMBER;
    start_id          VARCHAR2(12);
    end_id            VARCHAR2(12);
    v_user_exists     NUMBER;
    v_account_exists  NUMBER;
	PARAM_LIST	      EXCEPTION;
	INVALID_USER      EXCEPTION;
	INVALID_COUNT     EXCEPTION;
	INVALID_ACCOUNT   EXCEPTION;

BEGIN
	--make sure all parameters have been passed in
    if p_ard_user_id is null or
       p_sample_count is null or 
       p_account_id is null
    then
	  raise PARAM_LIST;
    end if;
    
    --make sure the user is valid
    select count(*) into v_user_exists
    from es_ardais_user
    where ardais_user_id = p_ard_user_id;
    if v_user_exists = 0
    then
       raise INVALID_USER;
    end if;
    
    --make sure the number of sample ids is valid.  note that 8192 = 2000 hex.
    if (p_sample_count <= 0) or (p_sample_count > 8192)
    then
       raise INVALID_COUNT;
    end if;
    
    --make sure the account is valid
    select count(*) into v_account_exists
    from es_ardais_account
    where ardais_acct_key = p_account_id;
    if v_account_exists = 0
    then
       raise INVALID_ACCOUNT;
    end if;

  -- Generate the sample ids
    for loop_counter in 1 .. p_sample_count
    loop
      --dbms_output.put_line ('Loop count = ' || loop_counter);
      SELECT DATA_IMPORT_SAMPLE_SEQ.NEXTVAL INTO v_id FROM DUAL;
      if (loop_counter = 1)
      then
        zeros := '00000000';
        temp := Lang_Integer.toRadixString(v_id,16);
        start_id := 'SX'||substr(zeros,0,8-length(temp))||temp;
        --dbms_output.put_line ('Start id = ' || start_id);
      end if;
      if (loop_counter = p_sample_count)
      then
        zeros := '00000000';
        temp := Lang_Integer.toRadixString(v_id,16);
        end_id := 'SX'||substr(zeros,0,8-length(temp))||temp;
        --dbms_output.put_line ('End id = ' || end_id);
      end if;
    end loop;
    
  --write the results of the execution to the appropriate table
  insert into ILTDS_ASSIGNED_SAMPLE_IDS (ARDAIS_ACCT_KEY, SAMPLE_ID_COUNT, SAMPLE_ID_START, SAMPLE_ID_END, SAMPLE_ID_NEXT, ARDAIS_USER_ID, ASSIGNMENT_DATE)
  	values (p_account_id, p_sample_count, start_id, end_id, start_id, p_ard_user_id, sysdate);
  	
  --set the output parameters
    p_sample_id_start := start_id;
    p_sample_id_end := end_id;
    p_sample_id_next := start_id;

EXCEPTION
   when PARAM_LIST then
      dbms_output.put_line ('The parameter list provided is not complete.  Exiting program.');
	  rollback;
  	  raise_application_error(-20004, 'The parameter list provided is not complete.  Exiting program.');
   when INVALID_USER then
      dbms_output.put_line ('User ' || p_ard_user_id || ' does not exist.  Exiting program.');
	  rollback;
  	  raise_application_error(-20004, 'User ' || p_ard_user_id || ' does not exist.  Exiting program.');
   when INVALID_COUNT then
      dbms_output.put_line ('The number of sample ids to assign must be between 1 and 8192.  Exiting program.');
	  rollback;
  	  raise_application_error(-20004, 'The number of sample ids to assign must be between 1 and 8192.  Exiting program.');
   when INVALID_ACCOUNT then
      dbms_output.put_line ('Account ' || p_account_id || ' does not exist.  Exiting program.');
	  rollback;
  	  raise_application_error(-20004, 'Account ' || p_account_id || ' does not exist.  Exiting program.');
   when others then
      dbms_output.put_line ('An error occured: ' || sqlerrm);
	  rollback;
  	  raise_application_error(-20004, 'An error occured: ' || sqlerrm);
END BIGR_ASSIGN_SAMPLE_IDS;
/
