CREATE OR REPLACE VIEW inventory_group_user_v
AS
   SELECT ard_logical_repos_user.repository_id inventory_group_id,
          ard_logical_repos_user.ardais_user_id user_id
     FROM ard_logical_repos_user
/
