CREATE OR REPLACE FORCE VIEW inventory_group_v (inventory_group_id,
                                                FULL_NAME,
                                                SHORT_NAME)
AS
   SELECT ID inventory_group_id,
             FULL_NAME,
             SHORT_NAME
     FROM ard_logical_repos
/
