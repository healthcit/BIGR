

 
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
  document.getElementById('holdList').style.display = "block";
  
}

function init(){
  document.getElementById('holdList').style.display = "none";
}

function onResetClick(){
  document.getElementById('holdList').style.display = "none";
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
		<!-- <b>HCIT<br>Samples On Hold Report</b>-->
<table align="center"></table>
  <br>
  <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr class="lightGrey"> 
              <td colspan="4"> 
                <div align="center"><b>Samples On Hold Selection Criteria</b></div>
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
                <option value="" selected="selected">Sample ID</option>
                <option value="HCITTEST">Sample Alias</option>
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
     <div id="holdList"> 
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
          <tr class="lightGrey"> 
              <td align="center"> 
                <b>
                 Sample ID</b>
              </td>
               <td align="center"> 
                <b>
                 Sample Alias</b>
              </td>
               <td align="center"> 
                <b>
                 Date Placed On Hold</b>
              </td>
              <td align="center" title=""> 
                <b>
       					Requestor</b>
              </td>
              <td align="center" title=""> 
                <b>
       					Requestor's Account</b>
              </td>
              <td align="center" title=""> 
                <b>
       					#Days On Hold</b>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="center"> 
                 SX000029E1 
              </td>
              <td align="center"> 
                 22171-D4
              </td>
              <td align="center"> 
                 04/12/2010
              </td>
              <td align="center"> 
              Jaweed Mohammed
              </td>
               <td align="center"> 
              HealthCare IT(HCIT)
              </td> 
              <td align="center"> 
              4
              </td>
            </tr>
             <tr class="white"> 
              <td align="center"> 
                 SX00002934
              </td>
              <td align="center"> 
                 22134-D4
              </td>
              <td align="center"> 
                 03/11/2010
              </td>
                <td align="center"> 
              Gail Wiseman
              </td>
               <td align="center"> 
              Ardais (ARD0000001)
              </td> 
              <td align="center"> 
              35
              </td>
            </tr>
             <tr class="grey"> 
              <td align="center"> 
                 SX00002944
              </td>
              <td align="center"> 
                 22171-D5
              </td>
              <td align="center"> 
                 03/09/2010
              </td>
             <td align="center"> 
              Jaweed Mohammed
              </td>
               <td align="center"> 
              HealthCare IT(HCIT)
              </td> 
              <td align="center"> 
              37
              </td>
            </tr>
            <tr class="white"> 
              <td align="left" class="ln3" colspan="6"> 
              </td>
             </tr>
       
            <tr class="white" > 
              <td align="right" class="grey" colspan="1"> 
                 <b>Total Samples:</b>
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
                 <a href="samplesOnHoldReport.do?action=Read">Download To Excel</a>
              </td>
            </tr>
          </table>
          </div>
  </div>
</form>
</body>
</html>

