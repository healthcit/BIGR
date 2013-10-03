
 
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
  window.open('/BIGR/testApps/ytdSample.jsp','ytdSample','toolbar=no,menubar=yes,scrollbars=yes,resizable=yes,status=yes,width=875,height=600,left=400,top=200')
 
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
                <div align="center"><b>Rolling Inventory Report Selection Criteria</b></div>
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Sample Creation Date Range:</b></div>
              </td>
               <td colspan="3"> 
              From:<input type="text" name="donorFormDate" value="01/01/2010" READONLY size="10" onclick="openCalendar('donorFormDate')">
              &nbsp;&nbsp;&nbsp;&nbsp;
              To:<input type="text" name="donorToDate" value="04/16/2010" READONLY size="10" onclick="openCalendar('donorToDate')">
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Sample Type:</b></div>
              </td>
               <td colspan="3"> 
                <select name="userData.accountId" multiple="multiple" size="4">
                <option value="" selected="selected">ALL</option>
                <option value="CA00011C">Buffy Coat</option>
                <option value="CA11725C">DNA</option>
                <option value="CA00052C" >Frozen Tissue</option>
                <option value="CA09400C">Paraffin Tissue</option>
                <option value="CA09402C">PBMC</option>
                <option value="CA09556C">PBMC Pellet</option>
                <option value="CA09563C">Plasma</option>
                <option value="CA11117C">Serum</option>
                <option value="CA11144C">Whole Blood</option>
                </select>
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Inventory Group:</b></div>
              </td>
               <td colspan="3"> 
                <select name="userData.accountId" multiple="multiple" size="4">
                <option value="" selected="selected">All</option>
               <option value="61">2005 New</option>
               <option value="501">Archive</option>
               <option value="82">Ardais Current Projects</option>
               <option value="81">Ardais GSK</option>
               <option value="9">Beth Israel Deaconess Medical Center Restricted</option>
               </select>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Sort Order:</b></div>
              </td>
               <td colspan="3"> 
                <select name="userData.accountId">
                <option value="" selected="selected">Sample Type</option>
                <option value="HCITTEST">Sample Type Code</option>
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

