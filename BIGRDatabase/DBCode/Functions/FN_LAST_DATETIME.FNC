CREATE OR REPLACE FUNCTION fn_last_DATETIME(
    pSampleid  IN iltds_sample_status.SAMPLE_BARCODE_ID%type,
    pStatuses  in Varchar2
 ) Return Varchar2 Is

     Type tCursor is ref cursor;
     vCursor      tCursor;
     vSampleStatusDatetime  iltds_sample_status.SAMPLE_STATUS_DATETIME%type   := null;
     vMyQuery         varchar2(512);
     vStatuses        varchar2(360);
     vSampleid        iltds_sample_status.SAMPLE_BARCODE_ID%type;
BEGIN
   vSampleid := pSampleid;
   vStatuses := pStatuses;

   If vStatuses is null Then
     vMyQuery := 'select SAMPLE_STATUS_DATETIME from iltds_sample_status where SAMPLE_BARCODE_ID = '||''''|| pSampleId ||''''||' order by SAMPLE_STATUS_DATETIME desc'
   ;

   Else
       vStatuses := concat('''',vStatuses);
       vStatuses := concat(vStatuses,'''');
       vStatuses := replace(vStatuses,'|',''',''');

   vMyQuery := 'select SAMPLE_STATUS_DATETIME from iltds_sample_status where SAMPLE_BARCODE_ID = '||''''|| pSampleId ||''''||'and STATUS_TYPE_CODE in (' || vStatuses || ') order by SAMPLE_STATUS_DATETIME desc'
    ;

   End If;
     --dbms_output.put_line(vMyQuery);
     --return vMyQuery;
     Open vCursor For vMyQuery;
     Fetch vCursor Into vSampleStatusDatetime;

     If vSampleStatusDatetime is not null Then
       ---Return vSampleStatusDatetime;
     Return to_char(vSampleStatusDatetime,'MM/DD/YYYY HH24:MI:SS');
     Else
       Return 'No Data Found';
     End If;
   Exception
     When others Then
       Return substr(sqlerrm,1,255);
END;
/

