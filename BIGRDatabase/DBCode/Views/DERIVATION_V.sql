CREATE OR REPLACE FORCE VIEW derivation_v (derivation_id,
                                           PREPARED_BY_USER_ID,
                                           operation_cui,
                                           operation_other
                                          )
AS
   SELECT iltds_derivation.derivation_id,
          iltds_derivation.prepared_by PREPARED_BY_USER_ID, 
          iltds_derivation.operation_cui,
          iltds_derivation.other_operation operation_other
     FROM iltds_derivation
/
