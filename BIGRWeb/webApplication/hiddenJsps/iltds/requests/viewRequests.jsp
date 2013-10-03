<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ardais.bigr.iltds.helpers.RequestSelect"%>
<%@ page import="com.ardais.bigr.iltds.helpers.RequestState"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>

<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define id="viewForm" name="findRequestsForm" type="com.ardais.bigr.iltds.web.form.FindRequestsForm"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>View My Requests</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script type="text/javascript" src="<html:rewrite page="/js/calendar.js"/>"> </script>
<script language="JavaScript">
<!--

var myBanner = 'View Requests';

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function setActionButtonEnabling(isEnabled) {
  var f = document.forms[0];
  setInputEnabled(f,'filter',isEnabled);
  setInputEnabled(f,'details',isEnabled);
}

function getDetailsForRequest(requestId) {
  setActionButtonEnabling(false);
  var URL = '<html:rewrite page="/iltds/showRequest.do"/>' + "?requestId=" + requestId + "&txType=<%=ResultsHelper.TX_TYPE_ORDER_DETAIL%>";
  var w = window.open(URL, "Details" + requestId, "scrollbars=yes,resizable=yes,top=0,left=0,width=1000,height=700");
  w.focus();
  setActionButtonEnabling(true);
}

function updateClosedRequestsValue() {
  if (document.forms[0].visibleClosedRequestsControl.checked) {
   document.forms[0].elements["requestFilter.includeClosedRequests"].value = "true";
   //alert('Closed requests = true');
  }
  else {
   document.forms[0].elements["requestFilter.includeClosedRequests"].value = "false";
   //alert('Closed requests = false');
  }
}

//-->
</script>
</head>

<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<html:form method="POST"
      action="/iltds/viewRequestsStart"
      onsubmit="return(onFormSubmit());">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
        <tr class="yellow"> 
	      <td> 
	        <div align="center">
	          <font color="#FF0000"><b><html:errors/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	    <tr class="yellow"> 
	      <td> 
	        <div align="center">
	          <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
	</table>
  </div>

<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
	    <tr class="yellow">
		  <td>
		    <b>Search criteria</b>
		  </td>
	    </tr>
	  </table>
	</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
  <tr class="grey">
	<td nowrap>
	  <b>Submission Date</b>
	</td>
	<td nowrap>
	  <b>Status</b>
	</td>
  </tr>
  <tr class="white">
 	<td>
 	  <html:text styleClass="smallTextBox" name="findRequestsForm"
	 		     property="startDate" size="12" maxlength="10" readonly="true" />
	  <span class="fakeLink" onclick="openCalendar('startDate')">Start Date</span>
	  &nbsp;&nbsp;&nbsp;&nbsp;
	  <a href="javascript:clearDateField('startDate')">Clear Start Date</a>
	  <br>
	  <html:text styleClass="smallTextBox" name="findRequestsForm" 
	             property="endDate" size="12" maxlength="10" readonly="true" /> 
	  <span class="fakeLink" onclick="openCalendar('endDate')">End Date</span>
	  &nbsp;&nbsp;&nbsp;&nbsp;
	  <a href="javascript:clearDateField('endDate')">Clear End Date</a>
	</td>
	<td valign="top">
	<%
		String checked = "";
		if (viewForm.getRequestFilter().isIncludeClosedRequests()) {
			checked = "checked";
		}
	%>
	  <input type="checkbox" name="visibleClosedRequestsControl" <%=checked%> value="true" onClick="updateClosedRequestsValue();">Include Closed Requests
	  <html:hidden property="requestFilter.includeClosedRequests"/>
	</td>
  </tr>
  <tr>
    <td colspan="3">
      &nbsp;
    </td>
  </tr>
  <tr>
	<td colspan="3">
  	  <div align="center">
	    <input class="smallButton" type="submit" name="filter" value="Search"> 
	  </div>
	</td>
  </tr>
</table>
<br>
<br>
<%
	int openCount = 0;
	if (viewForm.getOpenRequestDtos() != null) {
		openCount = viewForm.getOpenRequestDtos().size();
	}
%>
<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
  <tr>
    <td>
	  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
		<tr class="yellow">
		  <td>
			<b>
			  Open Requests (<%=openCount%>)
			</b>
		  </td>
		</tr>
	  </table>
	</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
  <tr class="grey">
	<td nowrap width="15%">
	  <b>Request Id</b>
	</td>
	<td nowrap width="15%">
	  <b>Type</b>
	</td>
	<td nowrap width="15%">
	  <b>Status</b>
	</td>
	<td nowrap width="15%">
	  <b>Submission Date</b>
	</td>
	<td noWrap width="40%">
		&nbsp;
	</td>
  </tr>
  <logic:present name="findRequestsForm" property="openRequestDtos">
    <% 
      String rowClass = "white"; 
      SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm aaa");
    %>
    <logic:iterate name="findRequestsForm" property="openRequestDtos" id="requestDto" type="com.ardais.bigr.javabeans.RequestDto">
  	  <tr class="<%=rowClass%>">
  	  <%
  	    if (rowClass.equals("white")) {
  	  	  rowClass = "grey";
  	    }
  	    else {
  	   	  rowClass = "white";
  	    }
  	  %>
	    <td nowrap width="15%">
	      <bigr:icpLink popup="true">
            <bean:write name="requestDto" property="id"/>
          </bigr:icpLink>
        </td>
	    <td nowrap width="15%">
          <%=requestDto.getType().toString()%>
        </td>
	    <td nowrap width="15%">
          <bean:write name="requestDto" property="state"/>
        </td>
	    <td nowrap width="15%">
          <%=formatter.format(requestDto.getCreateDate())%>
        </td>
        <td width="40%">
	      &nbsp;&nbsp;&nbsp;
	      <input class="smallButton" type="button" name="details" value="Details" onClick="getDetailsForRequest('<%=requestDto.getId()%>');"> 
        </td>
      </tr>
    </logic:iterate>
  </logic:present>
</table>
<% 
	/* 
	 * Only show the table for closed request information if it was requested by the user.
	 * Not that the closed request count cannot be used to determine this, as
	 * the user may have requested closed request information but there is none.
	 */
%>
<logic:present name="findRequestsForm" property="requestFilter">
  <logic:equal name="findRequestsForm" property="requestFilter.includeClosedRequests" value="true">
	<br>
	<br>
	<%
		int closedCount = 0;
		if (viewForm.getClosedRequestDtos() != null) {
			closedCount = viewForm.getClosedRequestDtos().size();
		}
	%>
	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
	  <tr>
	    <td>
		  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
			<tr class="yellow">
			  <td>
				<b>
				  Closed Requests (<%=closedCount%>)
				</b>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
	  <tr class="grey">
		<td nowrap width="15%">
		  <b>Request Id</b>
		</td>
		<td nowrap width="15%">
		  <b>Type</b>
		</td>
		<td nowrap width="15%">
		  <b>Status</b>
		</td>
		<td nowrap width="15%">
		  <b>Submission Date</b>
		</td>
		<td noWrap width="40%">
			&nbsp;
		</td>
	  </tr>
	  <logic:present name="findRequestsForm" property="closedRequestDtos">
	    <% 
	      String rowClass = "white"; 
	      SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm aaa");
	    %>
	    <logic:iterate name="findRequestsForm" property="closedRequestDtos" id="requestDto" type="com.ardais.bigr.javabeans.RequestDto">
	  	  <tr class="<%=rowClass%>">
	  	  <%
	  	    if (rowClass.equals("white")) {
	  	  	  rowClass = "grey";
	  	    }
	  	    else {
	  	   	  rowClass = "white";
	  	    }
	  	  %>
		    <td nowrap width="15%">
	          <bigr:icpLink popup="true">
	            <bean:write name="requestDto" property="id"/>
	          </bigr:icpLink>
	        </td>
		    <td nowrap width="15%">
	          <%=requestDto.getType().toString()%>
	        </td>
		    <td nowrap width="15%">
	          <bean:write name="requestDto" property="state"/>
	        </td>
		    <td nowrap width="15%">
	          <%=formatter.format(requestDto.getCreateDate())%>
	        </td>
	        <td width="40%">
		      &nbsp;&nbsp;&nbsp;
		      <input class="smallButton" type="button" name="details" value="Details" onClick="getDetailsForRequest('<%=requestDto.getId()%>');"> 
	        </td>
	      </tr>
	    </logic:iterate>
	  </logic:present>
	</table>
  </logic:equal>
</logic:present>

</html:form>
</body>
</html>
