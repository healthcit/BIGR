----------------------------------------------------------------------------------------------------
--   Allows the user to initialize a BIGR database that contains only
--			ARTS and ARD_OBJECT values
--
--   Input:  1) The userid to be created as the bootstrap user
--			 2) The account to be created as the bootstrap account
--			 3) The string representing the location of the bootstrap
--				account (e.g., LEX).  Not user visible.
--			 4) The string representing the branding name for the 
--				the bootstrap account (e.g., DUKE). Not user visible.
--			 5) The string representing the company name for the 
--				bootstrap organization (e.g., NOVARTIS). User visible.
--
--
-----------------------------------------------------------------------------------------------------
CREATE OR REPLACE procedure BIGR_INITIALIZE_CLEAN_DATABASE(
		p_ard_user_id varchar2,
		p_account_id varchar2,
		p_location_id varchar2,
		p_brand_name varchar2,
		p_company_name varchar2)
is

NUM_RETURNED	  INTEGER;
PARAM_LIST	      EXCEPTION;
ALREADY_RUN	      EXCEPTION;
v_bootstrap_user_address_id ardais_address.address_id%type;
v_boot_acct_contact_addr_id ardais_address.address_id%type;

BEGIN


  -- make sure all parameters have been passed in
  if p_ard_user_id is null or
     p_location_id is null or
     p_account_id is null or
     p_brand_name is null or
     p_company_name is null
    then
	  raise PARAM_LIST;
    end if;

  -- do not run the script if it seems to have already been run
  -- the check is to see if there are any records in ES_ARDAIS_ACCOUNT
  -- if there are any, then we assume the db has been initialized; if not, then we can do
  	select count(*) into NUM_RETURNED from es_ardais_account;
    if NUM_RETURNED > 0 then 
       raise ALREADY_RUN;
    end if;

  -- insert row in ARD_BRANDING for default branding image
  insert into ard_branding (brand_name, brand_logo) values ('GULFSTREAM', 'gsbio_185px.jpg');

  -- insert row in ARD_BRANDING for customer
  insert into ard_branding (brand_name, brand_logo) values (p_brand_name, 'set_name_of_logo.jpg');

  -- create system owner account and initial user
  -- start with the location...
  insert into ILTDS_GEOGRAPHY_LOCATION 
          (LOCATION_ADDRESS_ID,
	  LOCATION_NAME,
  	  LOCATION_ADDRESS_1,
	  LOCATION_ADDRESS_2,
          LOCATION_CITY,
          LOCATION_STATE,
          LOCATION_COUNTRY,
	  LOCATION_ZIP)
  VALUES  
          (p_location_id,
          'set_location_text',
    	  'addr1',
	  'addr2',
          'city',
          'Massachusetts',
          'United States',
	  '11111' );

  -- now, you can create the account...*/
  insert into ES_ARDAIS_ACCOUNT 
	(ARDAIS_ACCT_KEY,
	 ARDAIS_ACCT_TYPE,
	 ARDAIS_ACCT_STATUS,
    	 ARDAIS_ACCT_COMPANY_DESC,
	 ARDAIS_PARENT_ACCT_COMP_DESC,
    	 ARDAIS_ACCT_OPEN_DATE,
	 ARDAIS_ACCT_ACTIVE_DATE,
    	 PRIMARY_LOCATION,
	 BRAND_ID,
    	 REQUEST_MGR_EMAIL_ADDRESS,
	 LINKED_CASES_ONLY_YN,
	 SAMPLE_ALIASES_UNIQUE_YN,
	 SAMPLE_ALIASES_REQUIRED_YN,
	 PASSWORD_POLICY_CID,
    	 PASSWORD_LIFE_SPAN) 
    VALUES 
	 (p_account_id,
	 'SO',
	 'A',
	 p_company_name,
	 'System Owner',
 	 sysdate,
	 sysdate,
	 p_location_id,
	 (select brand_id from ard_branding where brand_name = 'GULFSTREAM'),
	 'mgr@company.com',
	 'N',
	 'N',
	 'N',
	 'CA01761C',
	 999);

  -- need addresses for the account contact and for the bootstrap user
  -- and for the shipping contact
  
  -- account contact address
  insert into ARDAIS_ADDRESS (
  	ADDRESS_ID,
  	ARDAIS_ACCT_KEY, 
  	ADDRESS_TYPE, 
  	ADDRESS_1, 
  	ADDRESS_2, 
  	ADDR_CITY, 
  	ADDR_STATE, 
  	ADDR_ZIP_CODE, 
  	ADDR_COUNTRY, 
	  FIRST_NAME, 
		LAST_NAME)
  VALUES
	(ARD_ADDRID_SEQ.NEXTVAL, 
	  p_account_id, 
  	'Contact',
  	'addr1',
  	'addr2',
  	'city',
  	'MA',
  	'11111',
  	'USA',
  	'firstname',
  	'lastname')
  returning ADDRESS_ID into v_boot_acct_contact_addr_id;
  
  --update account table to have the contact address id
  update es_ardais_account set contact_address_id = v_boot_acct_contact_addr_id
  where ardais_acct_key = p_account_id;
  
  -- bootstrap user address
  insert into ARDAIS_ADDRESS (
  	ADDRESS_ID,
  	ARDAIS_ACCT_KEY, 
  	ADDRESS_TYPE, 
  	ADDRESS_1, 
  	ADDRESS_2, 
  	ADDR_CITY, 
  	ADDR_STATE, 
  	ADDR_ZIP_CODE, 
  	ADDR_COUNTRY, 
	  FIRST_NAME, 
		LAST_NAME)
  VALUES
	(ARD_ADDRID_SEQ.NEXTVAL, 
	  p_account_id, 
  	'User',
  	'addr1',
  	'addr2',
  	'city',
  	'MA',
  	'11111',
  	'USA',
  	'firstname',
  	'lastname')
  returning ADDRESS_ID into v_bootstrap_user_address_id;

	-- create shipping account
  insert into ARDAIS_ADDRESS (
  	ADDRESS_ID,
  	ARDAIS_ACCT_KEY, 
  	ADDRESS_TYPE, 
  	ADDRESS_1, 
  	ADDRESS_2, 
  	ADDR_CITY, 
  	ADDR_STATE, 
  	ADDR_ZIP_CODE, 
  	ADDR_COUNTRY, 
	  FIRST_NAME, 
		LAST_NAME)
  VALUES
	(ARD_ADDRID_SEQ.NEXTVAL, 
	  p_account_id, 
  	'Ship To',
  	'addr1',
  	'addr2',
  	'city',
  	'MA',
  	'11111',
  	'USA',
  	'firstname',
  	'lastname');

  -- next, create the user
  insert into ES_ARDAIS_USER 
	(ARDAIS_USER_ID,
  	ARDAIS_ACCT_KEY,
  	PASSWORD,
  	USER_VERIF_QUESTION,
  	USER_VERIF_ANSWER,
  	USER_LASTNAME,
  	USER_FIRSTNAME,
  	USER_TITLE,
  	USER_FUNCTIONAL_TITLE,
  	USER_AFFILIATION,
  	USER_PHONE_NUMBER,
  	USER_PHONE_EXT,
  	USER_FAX_NUMBER,
  	USER_MOBILE_NUMBER,
  	USER_EMAIL_ADDRESS,
  	CREATED_BY,
  	CREATE_DATE,
  	UPDATE_DATE,
  	UPDATED_BY,
  	USER_ADDRESS_ID,
  	USER_ACTIVE_IND,
  	CONSECUTIVE_FAILED_LOGINS,
  	PROFILE_TOPICS_VER,
  	PROFILE_TOPICS,
  	PASSWORD_POLICY_CID,
  	PASSWORD_LAST_CHANGE_DATE,
  	USER_DEPARTMENT,
  	CONSECUTIVE_FAILED_ANSWERS
	)
	values
	(p_ard_user_id,
	 p_account_id, 
	'W6ph5Mm5Pz8GgiULbPgzG37mj9g',
	'The Ultimate answer to Life, the Universe and Everything is?...',
	'42',
	'BOOTSTRAP USER',
	'BOOTSTRAP USER',
	'',
	'',
	'',
	'111-111-1111',
	'',
	'',
	'',
	'test@test.com',
	'bootstrap',
	sysdate,
	null,
	null,
	v_bootstrap_user_address_id,
	'Y',
	0,
	null,
	null,
	'CA01761C',
	sysdate,
	'',
	0
	);

  -- finally, give the user access to the Admin User Mgmt transaction and let them run
  insert into ARD_USER_ACCESS_MODULE
	(ARDAIS_ACCT_KEY,
	ARDAIS_USER_ID,
 	OBJECT_ID,
	CREATE_DATE,
	CREATED_BY,
	UPDATE_DATE,
	UPDATED_BY)
  VALUES
	(p_account_id,
         p_ard_user_id,
	 '40_ORM_010_USERMNG',
	 sysdate,
	 'bootstrap',
	 sysdate,
	 'bootstrap');
	 
  -- also, create an out of network shipping address 
  insert into iltds_geography_location (location_address_id, location_name) 
  values ('OUTOFNETWORK', 'Out of network');
  	 

EXCEPTION
   when PARAM_LIST then
      dbms_output.put_line ('The parameter list provided to BIGR_INITIALIZE_CLEAN_DATABASE is not complete.  Exiting program.');
	    rollback;
  	  raise_application_error(-20004, 'The parameter list provided to BIGR_INITIALIZE_CLEAN_DATABASE is not complete.  Exiting program.');
   when ALREADY_RUN then
      dbms_output.put_line ('The script appears to have already run: there are records in the ES_ARDAIS_ACCOUNT table.  Exiting program.');
	    rollback;	  
	    raise_application_error(-20004, 'The script appears to have already run: there are records in the ES_ARDAIS_ACCOUNT table.  Exiting program.');
    when others then
      dbms_output.put_line ('An error occured: ' || sqlerrm);
	    rollback;
	    raise_application_error(-20004, 'An error occured: ' || sqlerrm);
END BIGR_INITIALIZE_CLEAN_DATABASE;
/
