CREATE OR REPLACE FORCE VIEW ROLE_USER_V (ROLE_ID,
                                          USER_ID)
AS
   SELECT ROLE_ID,
          USER_ID
     FROM BIGR_ROLE_USER
/
