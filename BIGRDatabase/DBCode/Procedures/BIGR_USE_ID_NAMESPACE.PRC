CREATE OR REPLACE PROCEDURE BIGR_USE_ID_NAMESPACE(
  p_user_id es_ardais_user.ardais_user_id%TYPE,
  p_namespace varchar2
 )
--
-- WARNING: Only execute this procedure if explictly asked to do 
-- so by HealthCare IT Corporation, and then only with a namespace 
-- parameter provided by HealthCare IT Corporation for use in the 
-- specific software installation and database schema that you are 
-- working with.  In order to assure that namespaces never overlap 
-- across multiple installations of HealthCare IT Corporation's software, 
-- namespaces must be assigned only by HealthCare IT Corporation, and HealthCare 
-- IT Corporation will never assign the same namespace to more than one software 
-- installation, regardless of whether it is multiple installations for 
-- a single customer or installations for different customers.  Any 
-- other use of this procedure will void your support contract and you 
-- will be solely responsible for repairing any damage, including damage 
-- to other customer's software installations resulting from unauthorized 
-- namespace assignments on your part. 
--
AUTHID DEFINER
is
  v_cur_btx_id iltds_btx_history.btx_id%TYPE;
  v_note_clob clob;
  v_trans_text varchar2(4000);
  v_text_len number;
  v_cur_date date := sysdate;

  v_num_asm_seq_start number;
  v_num_asm_seq_max number;
  v_num_case_seq_start number;
  v_num_case_seq_max number;
  v_num_donor_seq_start number;
  v_num_donor_seq_max number;
  v_num_manifest_seq_start number;
  v_num_manifest_seq_max number;
  v_num_patheval_seq_start number;
  v_num_patheval_seq_max number;
  v_num_sample_seq_start number;
  v_num_sample_seq_max number;
  v_num_slide_seq_start number;
  v_num_slide_seq_max number;
  
  v_str_asm_seq_start varchar2(20);
  v_str_asm_seq_max varchar2(20);
  v_str_case_seq_start varchar2(20);
  v_str_case_seq_max varchar2(20);
  v_str_donor_seq_start varchar2(20);
  v_str_donor_seq_max varchar2(20);
  v_str_manifest_seq_start varchar2(20);
  v_str_manifest_seq_max varchar2(20);
  v_str_patheval_seq_start varchar2(20);
  v_str_patheval_seq_max varchar2(20);
  v_str_sample_seq_start varchar2(20);
  v_str_sample_seq_max varchar2(20);
  v_str_slide_seq_start varchar2(20);
  v_str_slide_seq_max varchar2(20);
  
  v_current_id_type varchar2(20);
  
  v_rec_count number;
  
  v_user varchar2(256);
  v_schema_prefix varchar2(50);
  v_schema_suffix varchar2(50);
  
  v_msg varchar2(4000);
  
  INVALID_NAMESPACE_ID exception;
  INVALID_USER_ID exception;
  NAMESPACE_ALREADY_IN_USE exception;
  NOT_OWNER_SCHEMA exception;
  
begin
  v_user := SYS_CONTEXT('USERENV','CURRENT_USER');

  -- The BIGR user id must be the id of a valid active user in an active System Owner account.
  -- 
  if p_user_id is null then
    raise INVALID_USER_ID;
  else
    select count(1) into v_rec_count from dual
    where exists(select 1 from es_ardais_user u, es_ardais_account a
	             where u.ardais_acct_key = a.ardais_acct_key
				   and u.ardais_user_id = p_user_id
				   and u.user_active_ind = 'Y'
           and a.ardais_acct_status = 'A'
				   and a.ardais_acct_type = 'SO');
      
    if v_rec_count = 0 then
      raise INVALID_USER_ID;
	end if;
  end if;
  
  -- The namespace id must have exactly three characters and each 
  -- must be a digit between 0 and 9.  We don't allow hex digits A-F 
  -- because some of our ids use decimal encoding and hex digits 
  -- would be invalid for those ids.
  --
  if p_namespace is null or length(p_namespace) <> 3 then
    raise INVALID_NAMESPACE_ID;
  elsif (substr(p_namespace, 1, 1) not between '0' and '9')
    OR (substr(p_namespace, 2, 1) not between '0' and '9')
    OR (substr(p_namespace, 3, 1) not between '0' and '9') then
      raise INVALID_NAMESPACE_ID;
  end if;
  
  -- Get the schema prefix and suffix, in both cases without the underscore.
  -- BIGR schema names have the from PREFIX_SUFFIX, where prefix identifies 
  -- a particular installation or customer, and SUFFIX is one of a fixed set 
  -- of values include OWNER, USER, READER, UTIL, etc.
  -- 
  v_schema_prefix := upper(substr(v_user, 1, instr(v_user, '_') - 1));
  v_schema_suffix := upper(substr(v_user, instr(v_user, '_') + 1));

  -- Verify that the schema we're running in is the OWNER schema.  This is 
  -- should really be managed through privileges, but just in cases a 
  -- non-OWNER schema is able to execute this, we want to prohibit it 
  -- anyways, since this procedure drops and creates sequences that are 
  -- to be owned only by the OWNER schema.
  --
  if v_schema_suffix <> 'OWNER' then
    raise NOT_OWNER_SCHEMA;
  end if;

  -- ASM and ASM Form ids contain 9 decimal digits.
  v_str_asm_seq_start := concat(p_namespace, '000000');
  v_str_asm_seq_max := concat(p_namespace, '999999');
  v_num_asm_seq_start := to_number(v_str_asm_seq_start, '099999999'); 
  v_num_asm_seq_max := to_number(v_str_asm_seq_max, '099999999'); 

  -- Case ids contain 10 decimal digits.
  v_str_case_seq_start := concat(p_namespace, '0000000');
  v_str_case_seq_max := concat(p_namespace, '9999999');
  v_num_case_seq_start := to_number(v_str_case_seq_start, '0999999999'); 
  v_num_case_seq_max := to_number(v_str_case_seq_max, '0999999999'); 

  -- Donor ids contain 10 decimal digits.
  v_str_donor_seq_start := concat(p_namespace, '0000000');
  v_str_donor_seq_max := concat(p_namespace, '9999999');
  v_num_donor_seq_start := to_number(v_str_donor_seq_start, '0999999999'); 
  v_num_donor_seq_max := to_number(v_str_donor_seq_max, '0999999999'); 

  -- Manifest ids contain 10 decimal digits.
  v_str_manifest_seq_start := concat(p_namespace, '0000000');
  v_str_manifest_seq_max := concat(p_namespace, '9999999');
  v_num_manifest_seq_start := to_number(v_str_manifest_seq_start, '0999999999'); 
  v_num_manifest_seq_max := to_number(v_str_manifest_seq_max, '0999999999'); 

  -- Pathology evaluation ids contain 8 hex digits.
  v_str_patheval_seq_start := concat(p_namespace, '00000');
  v_str_patheval_seq_max := concat(p_namespace, 'FFFFF');
  v_num_patheval_seq_start := to_number(v_str_patheval_seq_start, '0XXXXXXX'); 
  v_num_patheval_seq_max := to_number(v_str_patheval_seq_max, '0XXXXXXX'); 

  -- Sample ids contain 8 hex digits.
  v_str_sample_seq_start := concat(p_namespace, '00000');
  v_str_sample_seq_max := concat(p_namespace, 'FFFFF');
  v_num_sample_seq_start := to_number(v_str_sample_seq_start, '0XXXXXXX'); 
  v_num_sample_seq_max := to_number(v_str_sample_seq_max, '0XXXXXXX'); 

  -- Slide ids contain 8 hex digits.
  v_str_slide_seq_start := concat(p_namespace, '00000');
  v_str_slide_seq_max := concat(p_namespace, 'FFFFF');
  v_num_slide_seq_start := to_number(v_str_slide_seq_start, '0XXXXXXX'); 
  v_num_slide_seq_max := to_number(v_str_slide_seq_max, '0XXXXXXX');
  
  -- Check for ids already in use in the specified namespace.  A nice future enhancement 
  -- might be to allow, in some circumstances, setting sequences to a namespace that is 
  -- already in use and doing extra calculations to set the sequences high enough so that 
  -- there's no conflict with already-used values.  But for now we just prohibit assigning 
  -- a namespace that already has ids in it.

  select count(1) into v_rec_count from dual
  where exists(select 1 from iltds_asm where substr(asm_id, 4, 9) between v_str_asm_seq_start and v_str_asm_seq_max)
     OR exists(select 1 from iltds_asm_form where substr(asm_form_id, 4) between v_str_asm_seq_start and v_str_asm_seq_max);
  if v_rec_count > 0 then
    v_current_id_type := 'ASM or ASM Form';
    raise NAMESPACE_ALREADY_IN_USE;
  end if;
  
  select count(1) into v_rec_count from dual
  where exists(select 1 from iltds_informed_consent where substr(consent_id, 3) between v_str_case_seq_start and v_str_case_seq_max);
  if v_rec_count > 0 then
    v_current_id_type := 'Case';
    raise NAMESPACE_ALREADY_IN_USE;
  end if;
  
  select count(1) into v_rec_count from dual
  where exists(select 1 from pdc_ardais_donor where substr(ardais_id, 3) between v_str_donor_seq_start and v_str_donor_seq_max);
  if v_rec_count > 0 then
    v_current_id_type := 'Donor';
    raise NAMESPACE_ALREADY_IN_USE;
  end if;
  
  select count(1) into v_rec_count from dual
  where exists(select 1 from iltds_manifest where substr(manifest_number, 5) between v_str_manifest_seq_start and v_str_manifest_seq_max);
  if v_rec_count > 0 then
    v_current_id_type := 'Manifest';
    raise NAMESPACE_ALREADY_IN_USE;
  end if;
  
  select count(1) into v_rec_count from dual
  where exists(select 1 from lims_pathology_evaluation where substr(pe_id, 3) between v_str_patheval_seq_start and v_str_patheval_seq_max); 
  if v_rec_count > 0 then
    v_current_id_type := 'Pathology Evaluation';
    raise NAMESPACE_ALREADY_IN_USE;
  end if;
  
  select count(1) into v_rec_count from dual
  where exists(select 1 from iltds_sample where substr(sample_barcode_id, 3) between v_str_sample_seq_start and v_str_sample_seq_max)
     OR exists(select 1 from iltds_assigned_sample_ids where substr(sample_id_start, 3) between v_str_sample_seq_start and v_str_sample_seq_max)
     OR exists(select 1 from iltds_assigned_sample_ids where substr(sample_id_end, 3) between v_str_sample_seq_start and v_str_sample_seq_max);
  if v_rec_count > 0 then
    v_current_id_type := 'Sample';
    raise NAMESPACE_ALREADY_IN_USE;
  end if;
  
  select count(1) into v_rec_count from dual
  where exists(select 1 from lims_slide where substr(slide_id, 3) between v_str_slide_seq_start and v_str_slide_seq_max);
  if v_rec_count > 0 then
    v_current_id_type := 'Slide';
    raise NAMESPACE_ALREADY_IN_USE;
  end if;
  
  -- Now drop and recreate the sequences with the correct min/max/current value for each.
  -- To ensure that we can fit as many items as possible in a given namespace id 
  -- range, we create the sequences with the NOCACHE option so that we don't have 
  -- ids going unused when a connection is dropped.  If this turns out to be a performace
  -- problem we'll revisit the decision.  
  
  -- If you get an "ORA-01031: insufficient privileges" while trying to run this procedure 
  -- on one of the EXECUTE IMMEDIATE lines below, it is probably because in the schema 
  -- you are using, the _OWNER hasn't been directly granted the privilege to perform 
  -- the necessary operations (e.g. create a sequence).  In some of our older databases, 
  -- privileges such as CREATE SEQUENCE aren't granted directly to the *_OWNER schema 
  -- but are instead granted indirectly via a role such as the RESOURCE role. 
  -- For EXECUTE IMMEDIATE to work, the privileges must be granted directly rather than
  -- through a role.
  --
  -- To resolve this, grant the appropriate privileges directly to the correct 
  --  _OWNER schema.  For example: 
  -- 
  --  GRANT CREATE SEQUENCE TO CARA_OWNER; 
  
  -- ASM 
  select count(1) into v_rec_count from user_sequences
    where upper(sequence_name) = 'DATA_IMPORT_ASM_SEQ';
  if v_rec_count > 0 then
    execute immediate 'DROP SEQUENCE DATA_IMPORT_ASM_SEQ';
  end if;
    
  execute immediate 'CREATE SEQUENCE DATA_IMPORT_ASM_SEQ START WITH '||
    v_num_asm_seq_start||' MINVALUE '||v_num_asm_seq_start||' MAXVALUE '||v_num_asm_seq_max||
   ' NOCYCLE NOCACHE NOORDER';
  execute immediate 'GRANT SELECT ON DATA_IMPORT_ASM_SEQ TO '||v_schema_prefix||'_USER';
  
  -- Case 
  select count(1) into v_rec_count from user_sequences
    where upper(sequence_name) = 'DATA_IMPORT_CONSENT_SEQ';
  if v_rec_count > 0 then
    execute immediate 'DROP SEQUENCE DATA_IMPORT_CONSENT_SEQ';
  end if;
    
  execute immediate 'CREATE SEQUENCE DATA_IMPORT_CONSENT_SEQ START WITH '||
    v_num_case_seq_start||' MINVALUE '||v_num_case_seq_start||' MAXVALUE '||v_num_case_seq_max||
   ' NOCYCLE NOCACHE NOORDER';
  execute immediate 'GRANT SELECT ON DATA_IMPORT_CONSENT_SEQ TO '||v_schema_prefix||'_USER';
  
  -- Donor 
  select count(1) into v_rec_count from user_sequences
    where upper(sequence_name) = 'DATA_IMPORT_DONOR_SEQ';
  if v_rec_count > 0 then
    execute immediate 'DROP SEQUENCE DATA_IMPORT_DONOR_SEQ';
  end if;
    
  execute immediate 'CREATE SEQUENCE DATA_IMPORT_DONOR_SEQ START WITH '||
    v_num_donor_seq_start||' MINVALUE '||v_num_donor_seq_start||' MAXVALUE '||v_num_donor_seq_max||
   ' NOCYCLE NOCACHE NOORDER';
  execute immediate 'GRANT SELECT ON DATA_IMPORT_DONOR_SEQ TO '||v_schema_prefix||'_USER';
  
  -- Manifest 
  select count(1) into v_rec_count from user_sequences
    where upper(sequence_name) = 'SEQ_MANIFEST_NUMBER';
  if v_rec_count > 0 then
    execute immediate 'DROP SEQUENCE SEQ_MANIFEST_NUMBER';
  end if;
    
  execute immediate 'CREATE SEQUENCE SEQ_MANIFEST_NUMBER START WITH '||
    v_num_manifest_seq_start||' MINVALUE '||v_num_manifest_seq_start||' MAXVALUE '||v_num_manifest_seq_max||
   ' NOCYCLE NOCACHE NOORDER';
  execute immediate 'GRANT SELECT ON SEQ_MANIFEST_NUMBER TO '||v_schema_prefix||'_USER';
  
  -- Pathology Evaluation 
  select count(1) into v_rec_count from user_sequences
    where upper(sequence_name) = 'LIMS_PATHOLOGY_EVALUATION_SEQ';
  if v_rec_count > 0 then
    execute immediate 'DROP SEQUENCE LIMS_PATHOLOGY_EVALUATION_SEQ';
  end if;
    
  execute immediate 'CREATE SEQUENCE LIMS_PATHOLOGY_EVALUATION_SEQ START WITH '||
    v_num_patheval_seq_start||' MINVALUE '||v_num_patheval_seq_start||' MAXVALUE '||v_num_patheval_seq_max||
   ' NOCYCLE NOCACHE NOORDER';
  execute immediate 'GRANT SELECT ON LIMS_PATHOLOGY_EVALUATION_SEQ TO '||v_schema_prefix||'_USER';
  
  -- Sample 
  select count(1) into v_rec_count from user_sequences
    where upper(sequence_name) = 'DATA_IMPORT_SAMPLE_SEQ';
  if v_rec_count > 0 then
    execute immediate 'DROP SEQUENCE DATA_IMPORT_SAMPLE_SEQ';
  end if;
    
  execute immediate 'CREATE SEQUENCE DATA_IMPORT_SAMPLE_SEQ START WITH '||
    v_num_sample_seq_start||' MINVALUE '||v_num_sample_seq_start||' MAXVALUE '||v_num_sample_seq_max||
   ' NOCYCLE NOCACHE NOORDER';
  execute immediate 'GRANT SELECT ON DATA_IMPORT_SAMPLE_SEQ TO '||v_schema_prefix||'_USER';
  
  -- Slide 
  select count(1) into v_rec_count from user_sequences
    where upper(sequence_name) = 'LIMS_SLIDE_LABEL_SEQ';
  if v_rec_count > 0 then
    execute immediate 'DROP SEQUENCE LIMS_SLIDE_LABEL_SEQ';
  end if;
    
  execute immediate 'CREATE SEQUENCE LIMS_SLIDE_LABEL_SEQ START WITH '||
    v_num_slide_seq_start||' MINVALUE '||v_num_slide_seq_start||' MAXVALUE '||v_num_slide_seq_max||
   ' NOCYCLE NOCACHE NOORDER';
  execute immediate 'GRANT SELECT ON LIMS_SLIDE_LABEL_SEQ TO '||v_schema_prefix||'_USER';
  
  -- Create a transaction history record. 

  v_trans_text := 'Assigned '||p_namespace||' as the currently-active id namespace.';
  select length(v_trans_text) into v_text_len from dual;

  dbms_lob.createtemporary(v_note_clob, TRUE, DBMS_LOB.SESSION);
  dbms_lob.write(v_note_clob, v_text_len, 1, v_trans_text);

  btx.INSERT_HISTORY_NO_BOXLOC(v_cur_btx_id, v_cur_date, 'TXHNote', p_user_id, v_cur_date, null, null, null,
   							   null, null, null, null, null, null, null, null, null, null, null, null,
							   null, null, null, null, null, null, null, null, null, null, null, v_note_clob, null, null, null);

  dbms_lob.freetemporary(v_note_clob);
  
  commit;
  
  -- Output some details of the new ids ranges for various kinds of objects. 
  
  dbms_output.put_line('The new id ranges for namespace '||p_namespace||' are:');
  dbms_output.put_line('  ASM: '||v_str_asm_seq_start||' to '||v_str_asm_seq_max);
  dbms_output.put_line('  Case: '||v_str_case_seq_start||' to '||v_str_case_seq_max);
  dbms_output.put_line('  Donor: '||v_str_donor_seq_start||' to '||v_str_donor_seq_max);
  dbms_output.put_line('  Manifest: '||v_str_manifest_seq_start||' to '||v_str_manifest_seq_max);
  dbms_output.put_line('  Pathology Evaluation: '||v_str_patheval_seq_start||' to '||v_str_patheval_seq_max);
  dbms_output.put_line('  Sample: '||v_str_sample_seq_start||' to '||v_str_sample_seq_max);
  dbms_output.put_line('  Slide: '||v_str_slide_seq_start||' to '||v_str_slide_seq_max);

EXCEPTION
   when INVALID_NAMESPACE_ID then
     v_msg := 'The specified namespace is invalid, it must have exactly three digits, each from 0 to 9:  '||p_namespace;
     dbms_output.put_line(v_msg);
     rollback;
     raise_application_error(-20004, 'BIGR_USE_ID_NAMESPACE: '||v_msg);
	 
   when INVALID_USER_ID then
     v_msg := 'The specified user id is invalid, it must identify an active user in a System Owner account:  '||p_user_id;
     dbms_output.put_line(v_msg);
     rollback;
     raise_application_error(-20004, 'BIGR_USE_ID_NAMESPACE: '||v_msg);
  
   when NAMESPACE_ALREADY_IN_USE then
     v_msg := 'There are already ids in use for '||v_current_id_type||' in namespace '||p_namespace;
     dbms_output.put_line(v_msg);
     rollback;
     raise_application_error(-20004, 'BIGR_USE_ID_NAMESPACE: '||v_msg);
  
   when NOT_OWNER_SCHEMA then
     v_msg := 'This must only be run as using a *_OWNER schema, you are using '||v_user;
     dbms_output.put_line(v_msg);
     rollback;
     raise_application_error(-20004, 'BIGR_USE_ID_NAMESPACE: '||v_msg);
     
   when others then
     v_msg := 'An error occured for namespace ' || p_namespace || ': ' || sqlerrm;
     dbms_output.put_line(v_msg);
     rollback;
     raise_application_error(-20004, 'BIGR_USE_ID_NAMESPACE: '||v_msg);
end;
/
