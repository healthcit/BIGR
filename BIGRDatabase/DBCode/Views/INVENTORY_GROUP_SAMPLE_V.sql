CREATE OR REPLACE VIEW INVENTORY_GROUP_SAMPLE_V
AS 
SELECT repository_id inventory_group_id,
       item_id sample_id
  FROM ard_logical_repos_item
/
