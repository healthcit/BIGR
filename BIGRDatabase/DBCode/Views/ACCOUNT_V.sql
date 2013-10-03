CREATE OR REPLACE FORCE VIEW account_v (account_id,
                                                   location_id,
                                                   active_yn,
                                                   company_name,
                                                   contact_phone_number,
                                                   contact_phone_extension,
                                                   contact_fax_number,
                                                   contact_email_address,
                                                   contact_mobile_number,
                                                   contact_address_1,
                                                   contact_address_2,
                                                   contact_city,
                                                   contact_state,
                                                   contact_zip_code,
                                                   contact_country,
                                                   contact_pager_number
                                                  )
AS
   SELECT eaa.ardais_acct_key account_id,
          eaa.primary_location location_id,
          DECODE (eaa.ardais_acct_status, 'I', 'N', 'A', 'Y') active_yn,
          eaa.ardais_acct_company_desc company_name,
          eaa.contact_phone_number,
          eaa.contact_phone_ext contact_phone_extension, 
          eaa.contact_fax_number,
          eaa.contact_email_address, eaa.contact_mobile_number,
          aa.address_1 contact_address_1, aa.address_2 contact_address_2,
          aa.addr_city contact_city, aa.addr_state contact_state,
          aa.addr_zip_code contact_zip_code, aa.addr_country contact_country,
          eaa.contact_pager_number
     FROM es_ardais_account eaa, ardais_address aa
    WHERE eaa.contact_address_id = aa.address_id(+)
/
