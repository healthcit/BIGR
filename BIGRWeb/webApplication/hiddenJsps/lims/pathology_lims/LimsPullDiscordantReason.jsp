<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<html>
<head>
<title>
  Enter 
  <logic:present name="reasonType">  
    <bean:write name="reasonType"/>
  </logic:present> 
  Reason
</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script> 
 function checkReason(){
  var reasontext = document.forms['reason'].reasontext.value;
  var reasons = document.forms['reason'].pullReason;
  var bool = false;
  if (reasons != null) {
  	for (i=0; i<reasons.length; i++ ) {
  	 if (reasons[i].checked) {
		bool = true;
		break;
  	 }
  	}
  }	
  if ((bool == false) && (reasontext.length == 0)){
    <logic:present name="reasonType">  
      alert("Please enter a <bean:write name="reasonType"/> Reason.");
    </logic:present> 
  } else {
    var reason = '';
    var semicolonNeeded = false;
    if (reasons != null) {
      for (i=0; i<reasons.length; i++ ) {
  	   if (reasons[i].checked) {
  	    if (semicolonNeeded == true) {
  	       reason = reason + ';';
  	    }
		reason = reason + reasons[i].value;
		semicolonNeeded = true;
  	   }
  	  }
  	}
  	if (reasontext.length > 0) {
  	    if (semicolonNeeded == true) {
  	       reason = reason + ';';
  	    }
  	    reason = reason + reasontext;
  	}
  	var reportMostRecentEval = null;
  	if (document.forms['reason'].reportMostRecentEval && document.forms['reason'].reportMostRecentEval.checked) {
  		reportMostRecentEval = document.forms['reason'].reportMostRecentEval.value;
  	}
    window.returnValue = new returnedObject(reason,reportMostRecentEval);
	window.close();
  }
 }
 
 function returnedObject(reasonText, reportMostRecentEval) {
   this.reasonText = reasonText;
   this.reportMostRecentEval = reportMostRecentEval;
 } 
</script>
</head>
<body class="bigr" >
<form name="reason" method="post">
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" border="0" cellpadding="3" cellspacing="1">            
          </table>
        </td>
      </tr>
    </table>
  </div>
  <p>
  <div id="query" align="center"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
  <tr>
    <td>
     <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">          
      <tr class="yellow"> 
        <td colspan="2"> 
          <div align="center">
            <b>Enter
              <logic:present name="reasonType">  
              <bean:write name="reasonType"/>
              </logic:present> 
              Reason
            </b>
          </div>
        </td>
      </tr>
      <logic:equal name="reasonType" value="Pull">      
        <logic:present name="reasonList" >
				<logic:iterate name="reasonList" property="iterator" id="legalValue">
					<tr class="white">
						<td align="center" nowrap>
						<INPUT TYPE=CHECKBOX NAME="pullReason" value="<%= ((com.ardais.bigr.iltds.assistants.LegalValue)legalValue).getDisplayValue() %>"></td>
						<td nowrap><bean:write name="legalValue" property="displayValue"/></td>
					</tr>
				</logic:iterate>
			</logic:present>
	   </logic:equal>
      <tr class="white">
        <td align="center"> 
          <div>
            <b><bean:write name="reasonType"/> Reason</b>
          </div>
        </td>
        <td align="left"> 
          <div align="left"> 
            &nbsp;<textarea name="reasontext" cols="20" rows="5"></textarea> 
          </div>
        </td>
      </tr> 
      <logic:equal name="reasonType" value="Unpull">       
      <tr class="white"> 
        <td colspan="2"> 
          <div align="left">
		    <input type="checkbox" name="reportMostRecentEval" value="true">Report most recent reportable evaluation (if applicable)
          </div>
        </td>
      </tr>  
      </logic:equal>    
      <logic:notEqual name="reasonType" value="Unpull">       
      <tr class="white"> 
        <td colspan="2"> 
          <div align="center">
            <html:img page="/images/Warning.gif"/>
             By marking Pulled/Discordant, if the sample has a reported evaluation
             that evaluation will be marked unreported, and if the sample 
             is released it will be marked unreleased.            
          </div>
        </td>
      </tr>  
       </logic:notEqual>    
      <tr class="white"> 
        <td colspan="2"> 
          <div align="center"> 
			<input type="button" name="Confirm" value="Confirm" onClick="checkReason();">&nbsp;
		    <input type="button" name="Cancel" value="Cancel" onClick="window.close()">
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

