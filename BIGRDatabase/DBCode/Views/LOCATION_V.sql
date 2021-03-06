CREATE OR REPLACE VIEW LOCATION_V
AS 
SELECT LOCATION_ADDRESS_ID LOCATION_ID,
       LOCATION_NAME NAME,
       LOCATION_ADDRESS_1 ADDRESS_1,
       LOCATION_ADDRESS_2 ADDRESS_2,
       LOCATION_CITY CITY,
       LOCATION_STATE STATE,
       LOCATION_ZIP ZIP_CODE,
       LOCATION_PHONE PHONE,
       LOCATION_COUNTRY COUNTRY
  FROM ILTDS_GEOGRAPHY_LOCATION
/
