 
 
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
 
function onSubmit(){
 window.close();
}

function onAttach(){
  // document.getElementById('pullList').style.display = "none";
  window.open('/BIGR/testApps/fileUpload.jsp','fileUpload','toolbar=no,menubar=no,resizable=yes,status=yes,width=700,height=600,left=400,top=200')
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
  
  <div id="caseList"> 
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground" width="700px;">
          <tr class="lightGrey"> 
              <td align="center" colspan="3"> 
                <b>
                Attach Documents</b>
              </td>
            </tr>
            <tr class="Grey"> 
             <td align="left"> Attachment 1</td>
             <td align="center"> 
                  <INPUT type="text" name="name1" size="20" value="">
              </td>
              <td align="center"> 
                  <INPUT type="file" name="inputFile1" size="40" value="">
              </td>
            </tr>
             <tr class="white"> 
                <td align="left"> Attachment 2</td>
                <td align="center"> 
                  <INPUT type="text" name="name2" size="20" value="">
              </td>
              <td align="center"> 
                  <INPUT type="file" name="inputFile2" size="40" value="">
              </td>
              </tr>
 							<tr class="Grey"> 
             <td align="left"> Attachment 3</td>
             <td align="center"> 
                  <INPUT type="text" name="name3" size="20" value="">
              </td>
              <td align="center"> 
                  <INPUT type="file" name="inputFile3" size="40" value="">
              </td>
            </tr>
            <tr class="white">
            <td align="center" colspan="3">
							 <div align="center"> 
                  <input type="button" name="btnSubmit" value="Submit" onclick="onSubmit();">
                </div>

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
 

