
 
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 
 
 
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="/BIGR/css/bigr.css">
<title>Manage License</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script language="JavaScript" src="/BIGR/js/linkedlist.js"></script>
<script language="JavaScript" src="/BIGR/js/map.js"></script>
<script language="JavaScript"> 
<!--
 
function Privilege() {
  this.setId = setId;
  this.getId = getId;
  this.setDescription = setDescription;
  this.getDescription = getDescription;
  
  var _self = this;
  var _id;
  var _description;
  
  function setId(id) {
    _id = id;
  }
  
  function getId() {
    return _id;
  }
  
  function setDescription(description) {
    _description = description;
  }
  
  function getDescription() {
    return _description;
  }
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
		<b>HCIT<br>Donor and Case Registration Report</b>
<table align="center"></table>
  <br>
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
          <tr class="lightGrey"> 
              <td align="center"> 
                <b>
                 Donor ID</b>
              </td>
              <td align="center"> 
                <b>
                 Donor Alias</b>
              </td>
              <td align="center"> 
                <b>
                 Date Donor <br>Entered</b>
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
                 Date Case <br>Entered</b>
              </td>
              <td align="center"> 
                <b>
                 IRB Protocol/<br>Consent Version</b>
              </td>
              <td align="center"> 
                <b>
                 Policy</b>
              </td>
              <td align="center"> 
                <b>
                Date/Time of Consent</b>
              </td>
              <td align="center" title="Host element from standard case registration form"> 
                <b>
                 Case DX</b>
              </td>
              <td align="center" title="Samples Collected"> 
                <b>
       					Y/N</b>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 04/29/2007
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 04/29/2007
              </td>
              <td align="center"> 
                 2305-05-12R5ER/12/20/2005
              </td>
              <td align="center"> 
                 Biobank General
              </td>
              <td align="center"> 
                Apr 29, 2007 3:03 PM
              </td>
              <td align="center" title="Host element from standard case registration form"> 
                 -
              </td>
              <td align="center"> 
                Y
              </td>
            </tr>
             <tr class="white"> 
              <td align="center"> 
                 AX0000000461
              </td>
              <td align="center"> 
                 D-TN07-00226
              </td>
              <td align="center"> 
                 05/07/2007
              </td>
              <td align="center"> 
                 CX0000000481
              </td>
              <td align="center"> 
                 TN07-00226
              </td>
              <td align="center"> 
                 05/09/2007
              </td>
              <td align="center"> 
                 2305-06-12R6ER/Unspecified 
              </td>
              <td align="center"> 
                 Biobank General
              </td>
              <td align="center"> 
                May 9, 2007 5:21 PM
              </td>
              <td align="center" title="Host element from standard case registration form"> 
                 -
              </td>
              <td align="center"> 
                Y
              </td>
            </tr>
              <tr class="Grey"> 
              <td align="center"> 
                 AX0000000433
              </td>
              <td align="center"> 
                 D-TN06-01133
              </td>
              <td align="center"> 
                 04/29/2007
              </td>
              <td align="center"> 
                 CX0000000226
              </td>
              <td align="center"> 
                 TN06-01121
              </td>
              <td align="center"> 
                 04/29/2007
              </td>
              <td align="center"> 
                 2305-05-12R5ER/12/20/2005
              </td>
              <td align="center"> 
                 Biobank General
              </td>
              <td align="center"> 
                Apr 29, 2007 3:03 PM
              </td>
              <td align="center" title="Host element from standard case registration form"> 
                 -
              </td>
              <td align="center"> 
                Y
              </td>
            </tr>
             <tr class="white"> 
              <td align="center"> 
                 AX0000000466
              </td>
              <td align="center"> 
                 D-TN07-00225
              </td>
              <td align="center"> 
                 05/07/2007
              </td>
              <td align="center"> 
                 CX0000000460
              </td>
              <td align="center"> 
                 TN07-00222
              </td>
              <td align="center"> 
                 05/09/2007
              </td>
              <td align="center"> 
                 2305-06-12R6ER/Unspecified 
              </td>
              <td align="center"> 
                 Biobank General
              </td>
              <td align="center"> 
                May 11, 2007 5:21 PM
              </td>
              <td align="center" title="Host element from standard case registration form"> 
                 -
              </td>
              <td align="center"> 
                N
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 04/29/2007
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 04/29/2007
              </td>
              <td align="center"> 
                 2305-05-12R5ER/12/20/2005
              </td>
              <td align="center"> 
                 Biobank General
              </td>
              <td align="center"> 
                Apr 29, 2007 3:03 PM
              </td>
              <td align="center" title="Host element from standard case registration form"> 
                 -
              </td>
              <td align="center"> 
                Y
              </td>
            </tr>
             <tr class="white"> 
              <td align="center"> 
                 AX0000000461
              </td>
              <td align="center"> 
                 D-TN07-00226
              </td>
              <td align="center"> 
                 05/07/2007
              </td>
              <td align="center"> 
                 CX0000000481
              </td>
              <td align="center"> 
                 TN07-00226
              </td>
              <td align="center"> 
                 05/09/2007
              </td>
              <td align="center"> 
                 2305-06-12R6ER/Unspecified 
              </td>
              <td align="center"> 
                 Biobank General
              </td>
              <td align="center"> 
                May 9, 2007 5:21 PM
              </td>
              <td align="center" title="Host element from standard case registration form"> 
                 -
              </td>
              <td align="center"> 
                Y
              </td>
            </tr>
              <tr class="Grey"> 
              <td align="center"> 
                 AX0000000433
              </td>
              <td align="center"> 
                 D-TN06-01133
              </td>
              <td align="center"> 
                 04/29/2007
              </td>
              <td align="center"> 
                 CX0000000226
              </td>
              <td align="center"> 
                 TN06-01121
              </td>
              <td align="center"> 
                 04/29/2007
              </td>
              <td align="center"> 
                 2305-05-12R5ER/12/20/2005
              </td>
              <td align="center"> 
                 Biobank General
              </td>
              <td align="center"> 
                Apr 29, 2007 3:03 PM
              </td>
              <td align="center" title="Host element from standard case registration form"> 
                 -
              </td>
              <td align="center"> 
                Y
              </td>
            </tr>
             <tr class="white"> 
              <td align="center"> 
                 AX0000000466
              </td>
              <td align="center"> 
                 D-TN07-00225
              </td>
              <td align="center"> 
                 05/07/2007
              </td>
              <td align="center"> 
                 CX0000000460
              </td>
              <td align="center"> 
                 TN07-00222
              </td>
              <td align="center"> 
                 05/09/2007
              </td>
              <td align="center"> 
                 2305-06-12R6ER/Unspecified 
              </td>
              <td align="center"> 
                 Biobank General
              </td>
              <td align="center"> 
                May 11, 2007 5:21 PM
              </td>
              <td align="center" title="Host element from standard case registration form"> 
                 -
              </td>
              <td align="center"> 
                N
              </td>
            </tr>
            
            <tr class="white"> 
              <td align="left" class="ln3" colspan="11"> 
              </td>
             </tr>
       
            <tr class="white" > 
              <td align="right" class="grey" colspan="4"> 
                 <b>Total # Donors by IRB/Consent Version:</b>
              </td>
              <td align="left" class="white" colspan="2"> 
                 <b> <a href="donorCaseRegistration.jsp">4</a></b>
              </td>
             <td align="right" class="grey" colspan="3"> 
                 <b>Total # Cases by IRB/Consent Version:</b>
              </td>
               <td align="left" class="white" colspan="2"> 
                 <b> <a href="donorCaseRegistration.jsp">6</a></b>
              </td>
            </tr>
            
             <tr class="white"> 
              <td align="left" class="ln3" colspan="11"> 
              </td>
             </tr>
       
            <tr class="white" > 
              <td align="right" class="grey" colspan="4"> 
                 <b>Total # Donors by Policy (if no IRB):</b>
              </td>
              <td align="left" class="white" colspan="2"> 
                 <b> <a href="donorCaseRegistration.jsp">4</a></b>
              </td>
             <td align="right" class="grey" colspan="3"> 
                 <b>Total # Cases by Policy (if no IRB):</b>
              </td>
               <td align="left" class="white" colspan="2"> 
                 <b> <a href="donorCaseRegistration.jsp">2</a></b>
              </td>
            </tr>
            
             <tr class="white"> 
              <td align="left" class="ln3" colspan="11"> 
              </td>
             </tr>
       
            <!-- <tr class="white" > 
              <td align="right" class="grey" colspan="4"> 
                 <b>Total # Donors by IRB/Consent Version:</b>
              </td>
               <td align="left" class="white" colspan="2"> 
                 <b> <a href="">4</a></b>
              </td>
             <td align="right" class="grey" colspan="3"> 
                 <b>Total # Cases by IRB/Consent Version:</b>
              </td>
               <td align="left" class="white" colspan="2"> 
                 <b> <a href="">6</a></b>
              </td>
            </tr>
            
             <tr class="white"> 
              <td align="left" class="ln3" colspan="11"> 
              </td>
             </tr>-->
       
            <tr class="white" > 
              <td align="right" class="grey" colspan="4"> 
                 <b>Total # Donors:</b>
              </td>
               <td align="left" class="white" colspan="2"> 
                 <b>8</b>
              </td>
             <td align="right" class="grey" colspan="3"> 
                 <b>Total # Cases:</b>
              </td>
               <td align="left" class="white" colspan="2"> 
                 <b>8</b>
              </td>
            </tr>
             <tr class="white"> 
              <td align="left" class="ln3" colspan="11"> 
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
                 <a href="donorCaseRegistration.jsp">Download To Excel</a>
              </td>
            </tr>
          </table>
  </div>
</form>
</body>
</html>

