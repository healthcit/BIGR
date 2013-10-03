CREATE OR REPLACE FORCE VIEW derivation_v_e (derivation_id,
                                           PREPARED_BY_USER_ID,
                                           operation_cui,
                                           operation,
                                           operation_other
                                          )
AS
   SELECT derivation_id, 
          prepared_by PREPARED_BY_USER_ID,
          operation_cui, 
          lk1.lookup_type_cd_desc operation,
          other_operation operation_other
     FROM iltds_derivation, pdc_lookup lk1
    WHERE operation_cui = lk1.lookup_type_cd(+);
/
