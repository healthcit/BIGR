
 
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
  document.getElementById('orphanList').style.display = "block";
  
}

function init(){
  document.getElementById('orphanList').style.display = "none";
}

function onResetClick(){
  document.getElementById('orphanList').style.display = "none";
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
		<!-- <b>HCIT<br>Orphan Samples Report</b>-->
<table align="center"></table>
  <br>
  <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr class="lightGrey"> 
              <td colspan="4"> 
                <div align="center"><b>Orphan Samples Selection Criteria</b></div>
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Date Range:</b></div>
              </td>
               <td colspan="3"> 
              From:<input type="text" name="donorFormDate" value="01/01/2010" READONLY size="10" onclick="openCalendar('donorFormDate')">
              &nbsp;&nbsp;&nbsp;&nbsp;
              To:<input type="text" name="donorToDate" value="04/16/2010" READONLY size="10" onclick="openCalendar('donorToDate')">
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
     <div id="orphanList"> 
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
          <tr class="lightGrey"> 
              <td align="center"> 
                <b>
                 Orphan Date</b>
              </td>
              <td align="center"> 
                <b>
                 Sample ID</b>
              </td>
               <td align="center"> 
                <b>
                 Sample Alias</b>
              </td>
              <td align="center" title="Room/storage unit/drawer/slot"> 
                <b>
       					Last Location</b>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="center"> 
                 04/12/2010
              </td>
              <td align="center"> 
                 SX000029E1 
              </td>
              <td align="center"> 
                 22171-D4
              </td>
                <td align="left"> 
               <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
             <tr class="white"> 
               <td align="center"> 
                 03/11/2010
              </td>
              <td align="center"> 
                 SX00002934
              </td>
              <td align="center"> 
                 22134-D4
              </td>
                <td align="left"> 
              <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
             <tr class="grey"> 
              <td align="center"> 
                 03/09/2010
              </td>
              <td align="center"> 
                 SX00002944
              </td>
              <td align="center"> 
                 22171-D5
              </td>
                <td align="left"> 
              <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
            <tr class="white"> 
              <td align="left" class="ln3" colspan="4"> 
              </td>
             </tr>
       
            <tr class="white" > 
              <td align="right" class="grey" colspan="1"> 
                 <b>Total Samples:</b>
              </td>
              <td align="left" class="white" colspan="3"> 
                 <b>3</b>
              </td>
            </tr>
            
             <tr class="white"> 
              <td align="left" class="ln3" colspan="4"> 
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
                 <a href="orphanReport.do?action=Read">Download To Excel</a>
              </td>
            </tr>
          </table>
     </div>
  </div>
</form>
</body>
</html>

