 
 
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 
 
 
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="/BIGR/css/bigr.css">
<title>BIGR - Reports</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script language="JavaScript" src="/BIGR/js/linkedlist.js"></script>
<script language="JavaScript" src="/BIGR/js/map.js"></script>
<script language="JavaScript"> 
<!--
 
function onSubmitClick(){
 // document.getElementById('pullList').style.display = "block";
  
}
 
function init(){
 // document.getElementById('pullList').style.display = "none";
}
 
function onResetClick(){
 // document.getElementById('pullList').style.display = "none";
}

function onAttach(){
  // document.getElementById('pullList').style.display = "none";
  window.open('/BIGR/testApps/fileUpload.jsp','fileUpload','toolbar=no,menubar=no,resizable=yes,status=yes,width=720,height=300,left=400,top=200')
 }

function onDelete(){
  // document.getElementById('pullList').style.display = "none";
 }
-->
</script>
</head>
<body class="bigr" onload="init();">
<form name="managePrivilegesForm" method="post" action="/BIGR/orm/managePrivileges.do">
 
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      
      
	</table>
  </div>
  <p>
<input type="hidden" name="objectType" value="User">
<input type="hidden" name="userData.userId" value="PGUPTA">
<input type="hidden" name="userData.accountId" value="PreetiTest">
<input type="hidden" name="userData.firstName" value="Preeti">
<input type="hidden" name="userData.lastName" value="Gupta">
<input type="hidden" name="accountData.id" value="">
<input type="hidden" name="accountData.name" value="">
  <div align="center">
	<!-- <b>HCIT<br>Case Pull Report</b>-->
<table align="center"></table>
  <br>
    <div id="donorList"> 
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
          <tr class="lightGrey"> 
              <td align="center" width="450px;"> 
                <b>
                 Donor: AX0000000497 (D-TN05-00525)</b>
              </td>
              <td align="center"> 
                <b>
                 <input type="button" name="btnAdd" value="Attach" onclick="onAttach();"></b>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="left"> 
                <a id="linkEditRole"
                    href="consentForm.docx"
                   onclick="onModifyClick();"> Consent Form</a>
              </td>
              <td align="center"> 
                  <input type="button" name="btnDelete" value="Delete" onclick="onDelete();">
              </td>
            </tr>
             <tr class="white"> 
               <td align="left"> 
                  <a id="linkEditRole"
                    href="consentForm.docx"
                   onclick="onModifyClick();">Proof Of Residence</a>
              </td>
              <td align="center"> 
                 <input type="button" name="btnDelete" value="Delete" onclick="onDelete();">
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
     <br>
  </div>
  
  <div id="caseList"> 
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
          <tr class="lightGrey"> 
              <td align="center" width="450px;"> 
                <b>
                Case: CX0000000517 (TN05-00525)</b>
              </td>
              <td align="center"> 
                <b>
                 <input type="button" name="btnAdd" value="Attach" onclick="onAttach();"></b>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="left"> 
                <a id="linkEditRole"
                     href="consentForm.docx"
                   onclick="onModifyClick();"> Document 1</a>
              </td>
              <td align="center"> 
                  <input type="button" name="btnDelete" value="Delete" onclick="onDelete();">
              </td>
            </tr>
             <tr class="white"> 
               <td align="left"> 
                  <a id="linkEditRole"
                     href="consentForm.docx"
                   onclick="onModifyClick();">Document 2</a>
              </td>
              <td align="center"> 
                 <input type="button" name="btnDelete" value="Delete" onclick="onDelete();">
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
     <br>
  </div>
  
  
  
   <div id="sampleList"> 
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
          <tr class="lightGrey"> 
              <td align="center" width="450px;"> 
                <b>
                Sample Id: SX0001D308 (TS05-0003966)</b>
              </td>
              <td align="center"> 
                <b>
                 <input type="button" name="btnAdd" value="Attach" onclick="onAttach();"></b>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="left"> 
                <a id="linkEditRole"
                   href="consentForm.docx"
                   onclick="onModifyClick();"> Document 1</a>
              </td>
              <td align="center"> 
                  <input type="button" name="btnDelete" value="Delete" onclick="onDelete();">
              </td>
            </tr>
             <tr class="white"> 
               <td align="left"> 
                  <a id="linkEditRole"
                   href="consentForm.docx"
                   onclick="onModifyClick();">Document 2</a>
              </td>
              <td align="center"> 
                 <input type="button" name="btnDelete" value="Delete" onclick="onDelete();">
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
     <br>
  </div>
  
  </div>
</form>
</body>
</html>
 

