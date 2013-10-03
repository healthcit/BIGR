
 
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
  window.open('/BIGR/testApps/repositoryUtilization.jsp','repositoryUtilization','toolbar=no,menubar=yes,scrollbars=yes,resizable=yes,status=yes,width=875,height=600,left=400,top=200')
 
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
                <div align="center"><b>Repository Utilization Selection Criteria</b></div>
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Location:</b></div>
              </td>
               <td colspan="1"> 
                <select name="userData.accountId" multiple="multiple" size="4">
                <option value="" selected="selected">ALL</option>
                <option value="CA00011C">Rockville</option>
                <option value="CA11725C">Lexington</option>
                <option value="CA00052C" >Germantown</option>
                <option value="CA09400C">Potomac</option>
                </select>
              </td>
           <!--  </tr>
           <tr class="white">--> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Room:</b></div>
              </td>
               <td colspan="1"> 
                <select name="userData.accountId" multiple="multiple" size="4">
                <option value="" selected="selected">All</option>
               <option value="">RNA Lab</option>
								<option value="361294009">Repository</option>
								<option value="181613000">Room1</option>
								<option value="181199001">Room2</option>
                </select>
              </td>
            </tr>
             <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b><font color="Red">*</font>Storage Unit:</b></div>
              </td>
               <td colspan="1"> 
                <select name="userData.accountId" multiple="multiple" size="4">
                <option value="" selected="selected">All</option>
               <option value="61">-20C</option>
               <option value="501">-80C</option>
               <option value="82">Room Temp</option>
               <option value="81">Liquid Nitrogen</option>
               </select>
              </td>
            <!--</tr>
             <tr class="white" >-->
              <td class="grey" >
                <div align="right"><b><font color="Red">*</font>Sort Order:</b></div>
              </td>
               <td colspan="1" valign="top"> 
                <select name="userData.accountId">
                <option value="" selected="selected">Location</option>
                <option value="HCITTEST">Room</option>
                <option value="HCIT">Storage Unit </option>
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

