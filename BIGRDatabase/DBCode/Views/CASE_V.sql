CREATE OR REPLACE FORCE VIEW case_v (case_id,
                                     donor_id,
                                     account_id,
                                     consent_version,
                                     alias,
                                     pulled_user_id,
                                     pulled_reason_cui,
                                     pulled_requested_user_id,
                                     pulled_yn,
                                     pulled_comment,
                                     pulled_datetime
                                     )
AS
   SELECT consent.consent_id case_id, consent.ardais_id donor_id,
          consent.ardais_acct_key account_id,
          consent.consent_version_num consent_version,
          consent.customer_id alias,
          consent.consent_pull_staff_id pulled_user_id,
          consent.consent_pull_reason_cd,
          consent.consent_pull_request_by pulled_requested_user_id,
          DECODE (consent_pull_datetime, NULL, 'N', 'Y') pulled_yn,
          consent.consent_pull_comment pulled_comment,
          consent.consent_pull_datetime pulled_datetime
     FROM iltds_informed_consent consent,
          iltds_revoked_consent_archive revoked
    WHERE consent.consent_id = revoked.consent_id(+)
/
