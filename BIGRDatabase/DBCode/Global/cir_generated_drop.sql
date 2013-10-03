REM-----------------------------------------------------------------------
REM PROGRAM       cir_generated_drop.sql
REM USAGE         SQLPLUS>@cir_generated_drop.sql
REM FUNCTION      Delete all CIR_% tables (and associated sequences and synonyms) 
REM               except CIR_FORM, CIR_FORM_DEFINITION, CIR_FORM_DEFINITION_TAGS
REM         
REM CALLED BY
REM
REM NOTES         Allows you to quit before it actually does anything
REM
REM
REM AUTHOR        Phil DiPrima
REM Date          Jul 2007
REM Modified:     Aug 2007 - Print a note about runinng the grants/synoymns
REM-----------------------------------------------------------------------

set echo off
set serveroutput on size 1000000 format wrapped;
SET LONG 20000
set longchunksize 20000
set linesize 300
set heading off
set feedback off
set verify off
SET pagesize 0
set trim on

prompt 
prompt *************************************************************
prompt *      The HealthCare IT "dynamic" CIR drop utiltiy
prompt *
prompt *        Copyright 2007 HealthCare IT Corporation
prompt *                www.healthcit.com
prompt *
prompt *************************************************************
prompt 
prompt
prompt Do you want to delete all CIR_% tables (and associated sequences and synonyms)
prompt   except 
prompt   ........ CIR_FORM
prompt   ........ CIR_FORM_DEFINITION
prompt   ........ CIR_FORM_DEFINITION_TAGS
prompt   ........


/* Setup user and prefix variables for use later on */

col uname for a30 old_value currentusername noprint
select user as uname from dual
/ 
prompt   ........ You are logged in as user ==> &CURRENTUSERNAME <==
prompt 

col uname for a30 old_value schemaprefix noprint
select rtrim('&CURRENTUSERNAME','OWNER') as uname from dual
/

/* Give the user a count of the objects they are about to delete */


SELECT 'WARNING: You are about to delete '||count(OBJECT_NAME)||' CIR Tables!'
    FROM user_objects 
   WHERE OBJECT_NAME like 'CIR_%' 
     AND OBJECT_NAME not in('CIR_FORM','CIR_FORM_SEQ','CIR_FORM_DEFINITION','CIR_FORM_DEFINITION_SEQ','CIR_FORM_DEFINITION_TAGS','CIR_FORM_DEFINITION_TAGS_SEQ')
     AND OBJECT_TYPE in ('TABLE','SEQUENCE')
ORDER BY OBJECT_TYPE desc
/

/* Give the user the opportunity to quit at this point */

col which new_value which noprint
accept which_in prompt 'Continue? - (Y)es, (N)o [N]: '

SELECT DECODE( upper( substr( '&which_in', 1, 1)), 
                'Y', 'YES',
                'N', 'NO',
                'NO'
             ) which
FROM  DUAL
/

/* MAIN */

BEGIN

 IF '&which' = 'YES' THEN
     dbms_output.put_line('---------- cir_drop ----------');
     /* disable all constraints: */
     dbms_output.put_line('1) disable all constraints...');
     FOR I IN (
                 select 'alter table '||table_name||' disable constraint '||constraint_name SQL_STR
                   from user_constraints
                  where TABLE_NAME in(select TABLE_NAME from user_tables where IOT_TYPE is null and TABLE_NAME like 'CIR_%')
                    and TABLE_NAME not in('CIR_FORM','CIR_FORM_DEFINITION','CIR_FORM_DEFINITION_TAGS')
               order by decode(constraint_type,'R',0,'U',1,'P',2)
              ) 
    LOOP
       dbms_output.put_line('EXECUTE IMMEDIATE '||I.SQL_STR);
       EXECUTE IMMEDIATE I.SQL_STR;
    END LOOP;
    /**/    


   /* drop sequences: */
   dbms_output.put_line('2) drop the sequences...');
   FOR rec IN (
               SELECT OBJECT_TYPE,OBJECT_NAME
                 FROM user_objects
                WHERE OBJECT_NAME like 'CIR_%' 
                  AND OBJECT_NAME not in('CIR_FORM','CIR_FORM_SEQ','CIR_FORM_DEFINITION','CIR_FORM_DEFINITION_SEQ','CIR_FORM_DEFINITION_TAGS','CIR_FORM_DEFINITION_TAGS_SEQ')
                  AND OBJECT_TYPE in ('SEQUENCE')
              )
  LOOP
    dbms_output.put_line('EXECUTE IMMEDIATE drop ' ||rec.object_type||' '||rec.object_name);
    EXECUTE IMMEDIATE 'drop ' ||rec.object_type||' '||rec.object_name;
  END LOOP;

   /* drop synonyms: */
   dbms_output.put_line('3) drop the synonyms...');
   FOR rec IN (
               SELECT OWNER,SYNONYM_NAME AS OBJECT_NAME 
                 FROM all_synonyms 
                WHERE upper(table_owner) = upper('&CURRENTUSERNAME')
                  AND owner != 'PUBLIC'
                  AND SYNONYM_NAME like 'CIR_%'
                  AND SYNONYM_NAME not in('CIR_FORM','CIR_FORM_SEQ','CIR_FORM_DEFINITION','CIR_FORM_DEFINITION_SEQ','CIR_FORM_DEFINITION_TAGS','CIR_FORM_DEFINITION_TAGS_SEQ')
              )
  LOOP
    dbms_output.put_line('EXECUTE IMMEDIATE DROP SYNONYM ' ||rec.owner||'.'||rec.object_name);
    EXECUTE IMMEDIATE 'DROP SYNONYM ' ||rec.owner||'.'||rec.object_name;
  END LOOP;
  /**/

/* drop all tables: */
   dbms_output.put_line('4) drop the CIR tables...');
   FOR rec IN (
               SELECT OBJECT_TYPE,OBJECT_NAME
                 FROM user_objects
                WHERE OBJECT_NAME like 'CIR_%' 
                  AND OBJECT_NAME not in('CIR_FORM','CIR_FORM_SEQ','CIR_FORM_DEFINITION','CIR_FORM_DEFINITION_SEQ','CIR_FORM_DEFINITION_TAGS','CIR_FORM_DEFINITION_TAGS_SEQ')
                  AND OBJECT_TYPE in ('TABLE')
              )
  LOOP
    dbms_output.put_line('EXECUTE IMMEDIATE drop ' ||rec.object_type||' '||rec.object_name||' CASCADE CONSTRAINTS');
    EXECUTE IMMEDIATE 'drop ' ||rec.object_type||' '||rec.object_name||' CASCADE CONSTRAINTS';
  END LOOP;
  /**/

   /* enable all constraints: */
   /* Reverse the "order by decode constraint_type" because you can not enable a Foreign Key unless the Primary Key is enabled */
     dbms_output.put_line('5) re-enable all constraints...');
     FOR I IN (
                 select 'alter table '||table_name||' enable constraint '||constraint_name SQL_STR
                   from user_constraints
                  where TABLE_NAME in(select TABLE_NAME from user_tables where IOT_TYPE is null and TABLE_NAME like 'CIR_%')
               order by decode(constraint_type,'P',0,'U',2,'R',1)
             ) 
    LOOP
       dbms_output.put_line('EXECUTE IMMEDIATE '||I.SQL_STR);
       EXECUTE IMMEDIATE I.SQL_STR;
    END LOOP;
    /**/    

    /* Invalids: */
   dbms_output.put_line('6) Check for INVALID objects...');
   EXECUTE IMMEDIATE 'select OBJECT_NAME,status from user_objects where status = ''INVALID''';
  /**/

     dbms_output.put_line('------------------------------');
 ELSE
     dbms_output.put_line('');
     DBMS_OUTPUT.PUT_LINE('Operation Canceled.');
     dbms_output.put_line('');
 END IF;

  EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('SQLERRM: ' || SQLERRM);
        ROLLBACK;
END;
.
set timing on
spool cir_drop.log
prompt logged in as user ==> &CURRENTUSERNAME <==
/
set timing off

BEGIN

 IF '&which' = 'YES' THEN
     dbms_output.put_line('---------- Remaining CIR_% objects ----------');
     /* Display whats left */
     dbms_output.put_line('');
     dbms_output.put_line('------------------------------');
     FOR I IN (
                 SELECT count(OBJECT_NAME)||' CIR objects remain.' SQL_STR
                   FROM user_objects 
                  WHERE OBJECT_NAME like 'CIR_%'
               ORDER BY OBJECT_TYPE desc
             ) 
    LOOP
       dbms_output.put_line(I.SQL_STR);
    END LOOP;
    /**/    
     dbms_output.put_line(' ');

 ELSE
     dbms_output.put_line('');
     /* Display whats left */
     FOR I IN (
                 SELECT ' NONE of the '||count(OBJECT_NAME)||' CIR Tables were removed.' SQL_STR
                   FROM user_objects 
                  WHERE OBJECT_NAME like 'CIR_%' 
                    AND OBJECT_NAME not in('CIR_FORM','CIR_FORM_SEQ','CIR_FORM_DEFINITION','CIR_FORM_DEFINITION_SEQ','CIR_FORM_DEFINITION_TAGS','CIR_FORM_DEFINITION_TAGS_SEQ')
                    AND OBJECT_TYPE in ('TABLE','SEQUENCE')
               ORDER BY OBJECT_TYPE desc
             ) 
    LOOP
       dbms_output.put_line('-----------------------------------------');
       dbms_output.put_line(I.SQL_STR);
    END LOOP;
    /**/    
     dbms_output.put_line('-----------------------------------------');
     dbms_output.put_line('');
 END IF;

  EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('SQLERRM: ' || SQLERRM);
        ROLLBACK;
END;
.
/

spool off
set heading on	
set feedback on
set verify on
SET pagesize 80

