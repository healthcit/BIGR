CREATE OR REPLACE FUNCTION              fn_last_status(
    pSampleid  IN iltds_sample_status.SAMPLE_BARCODE_ID%type,
    pStatuses  in Varchar2
 ) Return iltds_sample_status.STATUS_TYPE_CODE%type Is

     Type tCursor is ref cursor;
     vCursor      tCursor;
     vStatusTypeCode  iltds_sample_status.STATUS_TYPE_CODE%type   := null;
     vMyQuery         varchar2(512);
     vStatuses        varchar2(360);
     vSampleid        iltds_sample_status.SAMPLE_BARCODE_ID%type;
BEGIN
   vSampleid := pSampleid;
   vStatuses := pStatuses;


     If vStatuses is null Then
       vMyQuery := 'select STATUS_TYPE_CODE from iltds_sample_status where SAMPLE_BARCODE_ID = '||''''|| pSampleId ||''''||' order by SAMPLE_STATUS_DATETIME desc'
    ;

     Else
       vStatuses := concat('''',vStatuses);
       vStatuses := concat(vStatuses,'''');
       vStatuses := replace(vStatuses,'|',''',''');

       vMyQuery := 'select STATUS_TYPE_CODE from iltds_sample_status where SAMPLE_BARCODE_ID = '||''''|| pSampleId ||''''||'and STATUS_TYPE_CODE in (' || vStatuses || ') order by SAMPLE_STATUS_DATETIME desc'

    ;

     End If;
     --dbms_output.put_line(vMyQuery);
     --return vMyQuery;
     Open vCursor For vMyQuery;
     Fetch vCursor Into vStatusTypeCode;

     If vStatusTypeCode is not null Then
       Return vStatusTypeCode;
     Else
       Return 'No Data Found';
     End If;
   Exception
     When others Then
       Return substr(sqlerrm,1,255);
   END;
/

