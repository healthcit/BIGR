CREATE OR REPLACE FORCE VIEW sample_v_e (sample_id,
                                       case_id,
                                       donor_id,
                                       last_known_location_id,
                                       alias,
                                       sample_type_cui,
                                       sample_type,
                                       born_datetime,
                                       pulled_yn,
                                       pulled_datetime,
                                       pulled_reason
                                       )
AS
   SELECT iltds_sample.sample_barcode_id sample_id,
          iltds_sample.consent_id case_id, 
          iltds_sample.ardais_id donor_id,
          iltds_sample.last_known_location_id, 
          iltds_sample.customer_id alias,
          iltds_sample.sample_type_cid sample_type_cui,
          lk1.lookup_type_cd_desc sample_type,
          iltds_sample.born_date born_datetime,
          iltds_sample.pull_yn,
          iltds_sample.pull_date, 
          iltds_sample.pull_reason
     FROM iltds_sample, pdc_lookup lk1
    WHERE sample_type_cid = lk1.lookup_type_cd(+)
/
