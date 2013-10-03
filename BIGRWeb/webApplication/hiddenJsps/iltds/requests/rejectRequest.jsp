<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>

<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Reject Request</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
<!--

var myBanner = 'Reject Request';

function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function submitCancel() {
  setActionButtonEnabling(false);
  var url = '<html:rewrite page="/iltds/manageRequestsStart.do"/>';
  window.location.href = url;
}

function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  if (f.btnSubmit != null) {
	f.btnSubmit.disabled = isDisabled;
  }
  f.cancel.disabled = isDisabled;
}

//-->
</script>
</head>

<body  class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<html:form method="POST"
      action="/iltds/rejectRequest.do"
      onsubmit="return(onFormSubmit());">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
        <tr class="yellow"> 
	      <td colspan="10"> 
	        <div align="center">
	          <font color="#FF0000"><b><html:errors/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	    <tr class="yellow"> 
	      <td colspan="10"> 
	        <div align="center">
	          <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
	</table>
  </div>
  <p>
  <logic:present name="requestDtoForm" property="requestId">
    <div align="center">
      <table class="background" cellpadding="0" cellspacing="0" border="0">
        <tr> 
          <td> 
            <table class="foreground" cellpadding="3" cellspacing="1" border="0">
 	          <tr class="white"> 
                <td class="yellow" colspan="2" align="center"> 
                  <b>Request <bean:write name="requestDtoForm" property="requestId"/></b>
  				  <html:hidden property="requestId"/>
                </td>
              </tr>
			  <tr class="white"> 
                <td class="grey" > 
                  <div align="right"><b>Request Type:</b></div>
                </td>
                <td> 
                  <bean:write name="requestDtoForm" property="requestType"/>
                </td>
              </tr>
			  <tr class="white"> 
                <td class="grey" > 
                  <div align="right"><b>Submission Date:</b></div>
                </td>
                <td> 
                  <bean:write name="requestDtoForm" property="createDate"/>
                </td>
              </tr>
			  <tr class="white"> 
                <td class="grey" > 
                  <div align="right"><b>Submitted By:</b></div>
                </td>
                <td> 
                  <bean:write name="requestDtoForm" property="requesterName"/>
                </td>
              </tr>
			  <tr class="white"> 
                <td class="grey" > 
                  <div align="right"><b>Submitter Comments:</b></div>
                </td>
                <td>
                  <bigr:beanWrite filter="true" whitespace="true" name="requestDtoForm" property="requesterComments"/>
                </td>
              </tr>
    	    </table>
		  </td>
	    </tr>
	  </table>  
      <p>
      <table border="0" cellspacing="1" cellpadding="3" class="background" width="150">
        <tr class="yellow">
  	      <td> 
  	        <div align="center"><b>Please enter the reason for rejecting this request:</b></div>
          </td>
        </tr>
        <tr class="white">
  	      <td align="left">
  		    <html:textarea property="requestManagerComments" rows="10" cols="80"
  		    onkeyup="maxTextarea(4000);"/>
  	      </td>
        </tr>
      </table>
      <p>
      <input type="submit" name="btnSubmit" value="Reject Request">
      <input type="button" name="cancel" value="Cancel" onClick="submitCancel();" >
    </div>
  </logic:present>
  <logic:notPresent name="requestDtoForm" property="requestId">
    <div align="center">
      <p>
        A Request must be specified.
      <p>
      <input type="button" name="cancel" value="Cancel" onClick="submitCancel();">
    </div>
  </logic:notPresent>
</html:form>
</body>
</html>
