
 
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
		<b>HCIT<br>Rolling Inventory Report Month by Month and YTD </b>
<table align="center"></table>
  <br>
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
          <tr class="lightGrey"> 
              <td align="center"> 
                <b>
                 Sample Type</b>
              </td>
              <td align="center"> 
                <b>
                 January 2010</b>
              </td>
              <td align="center"> 
                <b>
                 February 2010</b>
              </td>
              <td align="center"> 
                <b>
                 March 2010</b>
              </td>
               <td align="center"> 
                <b>
                 April 2010</b>
              </td>
               <td align="center"> 
                <b>
                 Total YTD<br>by Sample Type</b>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="center"> 
                Whole Blood
              </td>
              <td align="center"> 
                 0
              </td>
              <td align="center"> 
                 0
              </td>
              <td align="center"> 
                 0
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">101</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">101</a>
              </td>
            </tr>
             <tr class="white"> 
              <td align="center"> 
                Serum
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">20</a>
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">110</a>
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">5</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">101</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">236</a>
              </td>
            </tr>
             <tr class="grey"> 
              <td align="center"> 
                Plasma
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">10</a>
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">20</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">22</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">11</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">63</a>
              </td>
            </tr>
             <tr class="white"> 
              <td align="center"> 
                Frozen Tissue
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">20</a>
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">30</a>
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">40</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">21</a>
              </td>
              <td align="center"> 
               <a href="sampleRegistration.jsp">111</a>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="center"> 
                Paraffin Tissue
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">11</a>
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">12</a>
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">14</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">15</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">52</a>
              </td>
            </tr>
             <tr class="white"> 
              <td align="center"> 
                Fresh Tissue
              </td>
              <td align="center"> 
                 <a href="sampleRegistration.jsp">45</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">56</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">78</a>
              </td>
              <td align="center"> 
                <a href="sampleRegistration.jsp">88</a>
              </td>
              <td align="center"> 
               <a href="sampleRegistration.jsp">267</a>
              </td>
            </tr>
            <tr class="white"> 
              <td align="left" class="ln3" colspan="14"> 
              </td>
             </tr>
       
            <tr class="white" > 
              <td align="right" class="grey" colspan="1"> 
                 <b>Total Samples By Month:</b>
              </td>
              <td align="center" class="grey" colspan="1"> 
                 <b><a href="sampleRegistration.jsp">106</a></b>
              </td>
              <td align="center" class="grey" colspan="1"> 
                 <b><a href="sampleRegistration.jsp">228</a></b>
              </td>
              <td align="center" class="grey" colspan="1"> 
                 <b><a href="sampleRegistration.jsp">159</a></b>
              </td>
              <td align="center" class="grey" colspan="1"> 
                 <b><a href="sampleRegistration.jsp">337</a></b>
              </td>
              <td align="center" class="grey" colspan="1"> 
                 <b><a href="sampleRegistration.jsp">830</a></b>
              </td>
            </tr>
             <tr class="white"> 
              <td align="left" class="ln3" colspan="14"> 
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
                 <a href="ytdSample.jsp">Download To Excel</a>
              </td>
            </tr>
          </table>
  </div>
</form>
</body>
</html>

