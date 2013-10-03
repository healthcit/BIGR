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
<bean:define id="manageForm" name="findRequestsForm" type="com.ardais.bigr.iltds.web.form.FindRequestsForm"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Manage Requests</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script type="text/javascript" src="<html:rewrite page="/js/calendar.js"/>"> </script>
<script language="JavaScript">
<!--

var myBanner = 'Manage Requests';

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function setActionButtonEnabling(isEnabled) {
  var f = document.forms[0];
  setInputEnabled(f,'filter',isEnabled);
  setInputEnabled(f,'accept',isEnabled);
  setInputEnabled(f,'reject',isEnabled);
  setInputEnabled(f,'details',isEnabled);
  setInputEnabled(f,'picklist',isEnabled);
  setInputEnabled(f,'boxContents',isEnabled);
}

function acceptRequest(requestId) {
  if (!onFormSubmit()) {
    setActionButtonEnabling(true);
    return;
  }
  document.forms[0].action = '<html:rewrite page="/iltds/acceptRequestStart.do"/>';
  document.forms[0].requestId.value = requestId;
  document.forms[0].submit();
}

function rejectRequest(requestId) {
  if (!onFormSubmit()) {
    setActionButtonEnabling(true);
    return;
  }
  document.forms[0].action = '<html:rewrite page="/iltds/rejectRequestStart.do"/>';
  document.forms[0].requestId.value = requestId;
  document.forms[0].submit();
}

function getDetailsForRequest(requestId) {
  setActionButtonEnabling(false);
  var URL = '<html:rewrite page="/iltds/showRequest.do"/>' + "?requestId=" + requestId + "&txType=<%=ResultsHelper.TX_TYPE_ORDER_DETAIL%>";
  var w = window.open(URL, "Details" + requestId, "scrollbars=yes,resizable=yes,top=0,left=0,width=1000,height=700");
  w.focus();
  setActionButtonEnabling(true);
}

function printPicklistForRequest(requestId) {
  setActionButtonEnabling(false);
  var URL = '<html:rewrite page="/iltds/printRequestPickList.do"/>' + "?requestId=" + requestId;
  URL = URL + "&itemDetailLevel=" + <%=RequestSelect.ITEM_INFO_DETAILS%>;
  URL = URL + "&boxDetailLevel=" + <%=RequestSelect.BOX_INFO_NONE%>;
  URL = URL + "&getPickListInfo=true";
  var w = window.open(URL, "PickList" + requestId, "scrollbars=yes,resizable=yes,top=0,left=0,width=950,height=700");
  w.focus();
  setActionButtonEnabling(true);
}

function printBoxContentsForRequest(requestId) {
  setActionButtonEnabling(false);
  var URL = '<html:rewrite page="/iltds/printRequestBoxContents.do"/>' + "?requestId=" + requestId;
  URL = URL + "&itemDetailLevel=" + <%=RequestSelect.ITEM_INFO_NONE%>;
  URL = URL + "&boxDetailLevel=" + <%=RequestSelect.BOX_INFO_DETAILS%>;
  var w = window.open(URL, "BoxContents" + requestId, "scrollbars=yes,resizable=yes,top=0,left=0,width=750,height=700");
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

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = myBanner;
  }
}

//-->
</script>
</head>

<body  class="bigr" onLoad="initPage();">
<html:form method="POST"
      action="/iltds/manageRequestsStart"
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
	          <b><bigr:btxActionMessages/></b>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
	</table>
  </div>
<p>
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
	  <b>Submitted By</b>
	</td>
	<td nowrap>
	  <b>Submission Date</b>
	</td>
	<td nowrap>
	  <b>Status</b>
	</td>
  </tr>
  <tr class="white">
    <td valign="top">
      <bigr:selectList styleClass="smallSelectBox" 
		             name="findRequestsForm" 
		             property="requestFilter.requesterUserId"
		             legalValueSetProperty="requesters"
		             firstValue="" firstDisplayValue="All"/>
    </td>
 	<td>
 	  <html:text styleClass="smallTextBox" name="findRequestsForm"
	 		     property="startDate" size="12" maxlength="10" readonly="true"/>
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
		if (manageForm.getRequestFilter().isIncludeClosedRequests()) {
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
	if (manageForm.getOpenRequestDtos() != null) {
		openCount = manageForm.getOpenRequestDtos().size();
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
	<td nowrap width="11%">
	  <b>Request Id</b>
	</td>
	<td nowrap width="8%">
	  <b>Type</b>
	</td>
	<td nowrap width="8%">
	  <b>Status</b>
	</td>
	<td nowrap width="13%">
	  <b>Submitted By</b>
	</td>
	<td nowrap width="10%">
	  <b>Submission Date</b>
	</td>
	<td noWrap width="50%">
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
	    <td nowrap width="11%">
				<bigr:icpLink popup="true">
          <bean:write name="requestDto" property="id"/>
				</bigr:icpLink>
        </td>
	    <td nowrap width="8%">
          <%=requestDto.getType().toString()%>
        </td>
	    <td nowrap width="8%">
          <bean:write name="requestDto" property="state"/>
        </td>
	    <td nowrap width="13%">
          <bean:write name="requestDto" property="requesterName"/>
        </td>
	    <td nowrap width="10%">
          <%=formatter.format(requestDto.getCreateDate())%>
        </td>
        <td width="50%">
          <logic:equal name="requestDto" property="state" value="<%=RequestState.PENDING.toString()%>">
	        &nbsp;
	        <input class="smallButton" type="button" name="details" value="Details" onClick="getDetailsForRequest('<%=requestDto.getId()%>');"> 
	        &nbsp;
	        <input style="width: 8.25em;" class="smallButton" type="button" name="picklist" value="Picklist" onClick="printPicklistForRequest('<%=requestDto.getId()%>');"> 
	        &nbsp;
	        <input class="smallButton" type="button" name="accept" value="Accept" onClick="acceptRequest('<%=requestDto.getId()%>');"> 
	        &nbsp;
	        <input class="smallButton" type="button" name="reject" value="Reject" onClick="rejectRequest('<%=requestDto.getId()%>');"> 
	      </logic:equal>
          <logic:equal name="requestDto" property="state" value="<%=RequestState.FULFILLED.toString()%>">
	        &nbsp;
	        <input class="smallButton" type="button" name="details" value="Details" onClick="getDetailsForRequest('<%=requestDto.getId()%>');"> 
	        &nbsp;
      	    <input style="width: 8.25em;" class="smallButton" type="button" name="boxContents" value="Box Contents" onClick="printBoxContentsForRequest('<%=requestDto.getId()%>');"> 
	      </logic:equal>
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
		if (manageForm.getClosedRequestDtos() != null) {
			closedCount = manageForm.getClosedRequestDtos().size();
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
		<td nowrap width="11%">
		  <b>Request Id</b>
		</td>
		<td nowrap width="8%">
		  <b>Type</b>
		</td>
		<td nowrap width="8%">
		  <b>Status</b>
		</td>
		<td nowrap width="13%">
		  <b>Submitted By</b>
		</td>
		<td nowrap width="10%">
		  <b>Submission Date</b>
		</td>
		<td noWrap width="50%">
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
		    <td nowrap width="11%">
  				<bigr:icpLink popup="true">
	          <bean:write name="requestDto" property="id"/>
  				</bigr:icpLink>
	        </td>
		    <td nowrap width="8%">
	          <%=requestDto.getType().toString()%>
	        </td>
		    <td nowrap width="8%">
	          <bean:write name="requestDto" property="state"/>
	        </td>
		    <td nowrap width="13%">
	          <bean:write name="requestDto" property="requesterName"/>
	        </td>
		    <td nowrap width="10%">
	          <%=formatter.format(requestDto.getCreateDate())%>
	        </td>
	        <td width="50%">
		      &nbsp;
		      <input class="smallButton" type="button" name="details" value="Details" onClick="getDetailsForRequest('<%=requestDto.getId()%>');"> 
		      <%
		      	//we need to provide the "Print Box Contents" button for unrejected
		      	//requests.  This is done so that the rep manager can print out 
		      	//the box contents for Research Requests, if he had not already
		      	//done so earlier in the process.
		      %>
		      <logic:notEqual name="requestDto" property="state" value="<%=RequestState.REJECTED.toString()%>">
	            &nbsp;
      	        <input style="width: 8.25em;" class="smallButton" type="button" name="boxContents" value="Box Contents" onClick="printBoxContentsForRequest('<%=requestDto.getId()%>');"> 
      	      </logic:notEqual>
	        </td>
	      </tr>
	    </logic:iterate>
	  </logic:present>
	</table>
  </logic:equal>
</logic:present>
<input type="hidden" name="requestId">

</html:form>
</body>
</html>
