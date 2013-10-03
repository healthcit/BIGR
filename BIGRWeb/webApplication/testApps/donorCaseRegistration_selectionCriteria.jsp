
 
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 
 
 
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="/BIGR/css/bigr.css">
<title>BIGR- Reports</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script language="JavaScript" src="/BIGR/js/linkedlist.js"></script>
<script language="JavaScript" src="/BIGR/js/map.js"></script>
<script language="JavaScript" src="/BIGR/js/calendar.js"></script>
<script language="JavaScript"> 
<!--
 
function onSubmitClick(){
  window.open('/BIGR/testApps/donorCaseRegistration.jsp','donorCaseRegistration','toolbar=no,menubar=yes,scrollbars=yes,resizable=yes,status=yes,width=850,height=600,left=400,top=200')
 
}

function onResetClick(){
}
 
-->
</script>
</head>
<body class="bigr">
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
  <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr class="lightGrey"> 
              <td colspan="4"> 
                <div align="center"><b>Donor & Case Registration Selection Criteria</b></div>
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Donor Creation Date Range:</b></div>
              </td>
               <td colspan="3"> 
              From:<input type="text" name="donorFormDate" value="01/01/2010" READONLY size="10" onclick="openCalendar('donorFormDate')">
              &nbsp;&nbsp;&nbsp;&nbsp;
              To:<input type="text" name="donorToDate" value="04/16/2010" READONLY size="10" onclick="openCalendar('donorToDate')">
              </td>
            </tr>
              <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>Case Creation Date Range:</b></div>
              </td>
               <td colspan="3"> 
               From:<input type="text" name="caseFormDate" value="" READONLY size="10" onclick="openCalendar('caseFormDate')">
              &nbsp;&nbsp;&nbsp;&nbsp;
              To:<input type="text" name="caseToDate" value="" READONLY size="10" onclick="openCalendar('caseToDate')">
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>IRB Protocol/Consent Version:</b></div>
              </td>
               <td colspan="3"> 
                <select name="userData.accountId" multiple="multiple" size="4">
                <option value="" selected="selected">ALL</option>
                <option value="HCITTEST">HCIT IRB Protocol/Consent Version -1</option>
                <option value="HCIT">HCIT IRB Protocol/Consent Version -2</option>
                <option value="PreetiTest">HCIT IRB Protocol/Consent Version -3</option>
                <option value="HCIT">HCIT IRB Protocol/Consent Version -4</option>
                <option value="PreetiTest">HCIT IRB Protocol/Consent Version -5</option>
                </select>
              </td>
            </tr>
           <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Policy:</b></div>
              </td>
               <td colspan="3"> 
                <select name="userData.accountId" multiple="multiple" size="4">
                <option value="" selected="selected">All</option>
                <option value="HCITTEST">HCIT Policy -1</option>
                <option value="HCIT">HCIT Policy -2</option>
                <option value="PreetiTest">HCIT Policy -3</option>
                <option value="HCIT">HCIT Policy -4</option>
                <option value="PreetiTest">HCIT Policy -5</option>
                <option value="PreetiTest">------------------------------------</option>
                </select>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Sort Order:</b></div>
              </td>
               <td colspan="3"> 
                <select name="userData.accountId">
                <option value="" selected="selected">Donor ID</option>
                <option value="HCITTEST">Donor Alias</option>
                <option value="HCIT">Date Donor Entered </option>
                <option value="">Case ID</option>
                <option value="HCITTEST">Case Alias</option>
                <option value="HCIT">Date Case Entered </option>
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
  </div>
</form>
</body>
</html>

