
 
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
  document.getElementById('pullList').style.display = "block";
  
}

function init(){
  document.getElementById('pullList').style.display = "none";
}

function onResetClick(){
  document.getElementById('pullList').style.display = "none";
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
  <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr class="lightGrey"> 
              <td colspan="4"> 
                <div align="center"><b>Case Pull Report Selection Criteria</b></div>
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>Date Range:</b></div>
              </td>
               <td colspan="3"> 
              From:<input type="text" name="donorFormDate" value="" READONLY size="10" onclick="openCalendar('donorFormDate')">
              &nbsp;&nbsp;&nbsp;&nbsp;
              To:<input type="text" name="donorToDate" value="" READONLY size="10" onclick="openCalendar('donorToDate')">
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Sort Order:</b></div>
              </td>
               <td colspan="3"> 
                <select name="userData.accountId">
                <option value="" selected="selected">Case ID</option>
                <option value="HCITTEST">Case Alias</option>
                </select>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="4"> 
                <div align="center"> 
                    <input type="button" name="btnSubmit" value="Submit" onclick="onSubmitClick();">
                    <input type="button" name="btnReset" value="Reset" onclick="onResetClick();">
                </div>
              </td>
            </tr>
            
          </table>
        </td>
      </tr>
    </table>
    <br>
    <div id="pullList"> 
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
          <tr class="lightGrey"> 
              <td align="center"> 
                <b>
                 Case Pull Date</b>
              </td>
              <td align="center"> 
                <b>
                 Case ID</b>
              </td>
               <td align="center"> 
                <b>
                 Case Alias</b>
              </td>
              <td align="center"> 
                <b>
       					Case Pull Reason</b>
              </td>
               <td align="center"> 
                <b>
       					Sample ID's</b>
              </td>
               <td align="center"> 
                <b>
       					Sample Checked Out</b>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="center"> 
                 04/12/2010
              </td>
              <td align="center"> 
                 CX000029E1 
              </td>
              <td align="center"> 
                 T06258 
              </td>
               <td align="center"> 
               Reason1
              </td>
              <td align="center"> 
               SX000029E1
              </td>
              <td align="center"> 
               Y
              </td>
            </tr>
             <tr class="white"> 
               <td align="center"> 
                 03/11/2010
              </td>
              <td align="center"> 
                 CX00002934
              </td>
              <td align="center"> 
                 T062577
              </td>
                <td align="center"> 
               Reason2
              </td>
              <td align="center"> 
               SX000029E2
              </td>
              <td align="center"> 
               N
              </td>
            </tr>
             <tr class="grey"> 
              <td align="center"> 
                 03/09/2010
              </td>
              <td align="center"> 
                 CX00002944
              </td>
              <td align="center"> 
                 TEST
              </td>
                <td align="center"> 
               Reason3
              </td>
               <td align="center"> 
               SX00002944
              </td>
              <td align="center"> 
               Y
              </td>
            </tr>
            <tr class="white"> 
              <td align="left" class="ln3" colspan="6"> 
              </td>
             </tr>
       
            <tr class="white" > 
              <td align="right" class="grey" colspan="1"> 
                 <b>Total Cases:</b>
              </td>
              <td align="left" class="white" colspan="5"> 
                 <b>3</b>
              </td>
            </tr>
            
             <tr class="white"> 
              <td align="left" class="ln3" colspan="6"> 
              </td>
             </tr>
          </table>
        </td>
      </tr>
    </table>
     <br>
          <table cellpadding="0" cellspacing="0" border="0" class="foreground" align="center">
          <tr class="white" > 
              <td align="center" class="white"> 
                 <a href="casePullReport.do?action=Read">Download To Excel</a>
              </td>
            </tr>
          </table>
  </div>
  </div>
</form>
</body>
</html>

