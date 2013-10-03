<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.WorkOrderHelper"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
/**
 * Allows the user to edit an existing work order or create a new one.
 *
 * @param  JspHelper.ID_HELPER  a <code>WorkOrderHelper</code> that contains
 *    the values of the existing work order, if editing an existing work order.
 */
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
WorkOrderHelper helper = (WorkOrderHelper)request.getAttribute(JspHelper.ID_HELPER);
String submitToOp = "PtsWorkOrderEdit";
String myBanner;
if (helper.isNew()) {
  myBanner = "Create a New Work Order";
}
else {
  myBanner = "Edit a Work Order";
}
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title><%=myBanner%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
<%
if (helper.isNew()) {
%>
  <%=helper.getAllStatusLists()%>
<%
}
%>

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = "<%=myBanner%>";
<%
if (helper.isNew()) {
%>
  replaceStatusList();
<%
}
%>
  document.all.<%=WorkOrderHelper.ID_WORKORDER_NAME%>.focus();
}

function isValid() {
    return true;
}

// Replace the list of statuses based on the currently selected work order type.
function replaceStatusList() {
  var type = document.all.<%=WorkOrderHelper.ID_TYPE%>.value;
  document.all._statusList.innerHTML = statuses[type];
}

function maxTextarea() { 
	var textarea = event.srcElement;
	if (textarea.value.length > textarea.maxlength*1) { 
    textarea.value = textarea.value.substring(0, textarea.maxlength*1);
	  alert("The maximum number of characters you can enter is " + textarea.maxlength);
    return false;
	}
}
//-->
</script>
</head>
<body class="bigr" onLoad="initPage();">
<form name="theForm" method="post" action="<html:rewrite page='/iltds/Dispatch'/>" onSubmit="return isValid();">
  <input type="hidden" name="op" value="<%=submitToOp%>">
  <input type="hidden" name="<%=ProjectHelper.ID_PROJECT_ID%>" value="<%=helper.getProjectId()%>">
<%
if (!helper.isNew()) {
%>
  <input type="hidden" name="<%=WorkOrderHelper.ID_WORKORDER_ID%>" value="<%=helper.getRawId()%>">
  <input type="hidden" name="<%=WorkOrderHelper.ID_TYPE%>" value="<%=helper.getRawType()%>">
  <input type="hidden" name="<%=WorkOrderHelper.ID_LISTORDER%>" value="<%=helper.getRawListOrder()%>">
<%
}
%>
  <div align="center"> 
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <%=helper.getMessagesForColspan(2)%>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right">
                <span style="color: red;">*</span> 
                <b>Work Order Name:</b></div>
              </td>
              <td> 
                <input type="text" name="<%=WorkOrderHelper.ID_WORKORDER_NAME%>" value="<%=helper.getWorkOrderName()%>" size="50" maxlength="50" >
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right">
                <span style="color: red;">*</span>
                <b>Work Order Type:</b></div>
              </td>
              <td> 
<%
if (helper.isNew()) {
%>
                <%=helper.getTypeList()%>
<%
}
else {
%>
                <%=helper.getType()%>
<%
}
%>
              </td>
            </tr>
            <tr class="white">
              <td class="grey" > 
                <div align="right"><b>Number of Samples:</b></div>
              </td>
              <td> 
                <input type="text" name="<%=WorkOrderHelper.ID_NUM_SAMPLES%>" value="<%=helper.getNumSamples()%>" size="6" maxlength="5" >
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Status:</b></div>
              </td>
                <td>
                <div id="_statusList">
                <%=helper.getStatusList()%>
                </div>
              </td>
            </tr>
            <tr class="white">
              <td class="grey" > 
                <div align="right"><b>Percent Complete:</b></div>
              </td>
              <td> 
                <input type="text" name="<%=WorkOrderHelper.ID_PERCENT_COMPLETE%>" value="<%=helper.getPercentComplete()%>" size="6" maxlength="3" >
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Start Date:</b></div>
              </td>
              <td>
                <%=helper.getStartMonthList()%>
                <%=helper.getStartDayList()%>
                <%=helper.getStartYearList()%>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>End Date:</b></div>
              </td>
              <td>
                <%=helper.getEndMonthList()%>
                <%=helper.getEndDayList()%>
                <%=helper.getEndYearList()%>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Notes:</b></div>
              </td>
              <td>
                <textarea name="<%=WorkOrderHelper.ID_NOTES%>" cols="60" rows="4" maxlength="255" onkeyup="maxTextarea();"><%=helper.getNotes()%></textarea>
              </td>
            </tr>
            <tr class="yellow"> 
              <td colspan="2" align="left" style="color: red;"> 
                * indicates a required field
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="submit" value="Submit">
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
