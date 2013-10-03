CREATE OR REPLACE FORCE VIEW ROLE_V (ROLE_ID,
                                    NAME,
                                    ACCOUNT_ID)
AS
   SELECT ID ROLE_ID,
             NAME,
             ACCOUNT_ID
     FROM BIGR_ROLE
/
