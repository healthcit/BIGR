spool table_check.log
set linesize 132 pagesize 500
column owner format A15;
  column COLUMN_NAME format A30;
  column DATA_type format A10;
  column DATA_length format 9999;
  column DATA_precision format 9999;
  column TABLE_NAME format A30;

break on TABLE_NAME skip 2

  select 'local db' database, a.TABLE_NAME, a.COLUMN_NAME, a.DATA_type,  
         a.DATA_LENGTH, a.data_precision
    from dba_tab_columns a
   where a.table_name in ('ES_SHOPPING_CART_DETAIL','ES_ARDAIS_ACCOUNT','ARD_OBJECTS',
'ES_ARDAIS_USER','ARD_USER_ACCESS_MODULE','ARD_LOOKUP_ALL',
'ARD_USER_ACCESS','ARD_WORKGROUP','ARD_WORKGROUP_OBJECTS') 
  union all
  select 'remote db', b.TABLE_NAME, b.COLUMN_NAME, b.DATA_type, 
         b.DATA_LENGTH, b.data_precision
    from dba_tab_columns@fakelpd1 b 
   where b.table_name in ('ES_SHOPPING_CART_DETAIL','ES_ARDAIS_ACCOUNT','ARD_OBJECTS',
'ES_ARDAIS_USER','ARD_USER_ACCESS_MODULE','ARD_LOOKUP_ALL',
'ARD_USER_ACCESS','ARD_WORKGROUP','ARD_WORKGROUP_OBJECTS')
  order by TABLE_NAME,column_name
/

spool off
