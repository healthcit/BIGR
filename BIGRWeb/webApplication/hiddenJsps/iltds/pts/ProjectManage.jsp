<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.LineItemHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.WorkOrderHelper"%>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
/**
 * Allows the user to manage an existing project.
 *
 * HttpServletRequest attributes:
 * <ul>
 * <li>
 *	JspHelper.ID_HELPER - A ProjectHelper representing the project to be
 *    managed.
 * </li>
 * </ul>
 */
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
ProjectHelper helper = (ProjectHelper)request.getAttribute(JspHelper.ID_HELPER);
String myBanner = "Manage Project (" + helper.getProjectName() + ")";
String submitToOp = "PtsProjectEdit";
String editWorkOrderOp = "PtsWorkOrderEditPrepare";
String editLineItemOp = "PtsLineItemEditPrepare";
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title><%=myBanner%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript">
<!--
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = "<%=myBanner%>";
  document.all.<%=Escaper.htmlEscapeAndPreserveWhitespace(ProjectHelper.ID_PROJECT_NAME)%>.focus();
}

function isValid() {
//TODO: finish this function
  return true;
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
  <div align="center">
  	<form name="theForm" method="post" action="<html:rewrite page='/iltds/Dispatch'/>" onSubmit="return isValid();">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          
          <input type="hidden" name="op" value="<%=submitToOp%>">
          <input type="hidden" name="<%=ProjectHelper.ID_PROJECT_ID%>" value="<%=helper.getId()%>">
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <%=helper.getMessagesForColspan(4)%>

<!-- Basic project information -->
			      <tr class="white"> 
              <td class="yellow" colspan="4" align="center"> 
                  <b>Basic Project Information</b>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><span style="color: red;">*</span> <b>Project Name:</b></div>
              </td>
              <td colspan="3"> 
                <input type="text" name="<%=Escaper.htmlEscapeAndPreserveWhitespace(ProjectHelper.ID_PROJECT_NAME)%>" value="<%=helper.getProjectName()%>" size="60" maxlength="50" >
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><span style="color: red;">*</span> <b>Client:</b></div>
              </td>
              <td>
                <%=helper.getClientList()%>
              </td>
              <td class="grey" > 
                <div align="right"><b>Requested By:</b></div>
              </td>
              <td>
                <%=helper.getRequestedBy()%>
                <input type="hidden" name="<%=ProjectHelper.ID_REQUESTED_BY%>" value="<%=helper.getDataBean().getRequestedBy()%>">
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><span style="color: red;">*</span> <b>Request Date:</b></div>
              </td>
              <td colspan="3">
                <%=helper.getRequestMonthList()%>
                <%=helper.getRequestDayList()%>
                <%=helper.getRequestYearList()%>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Status:</b></div>
              </td>
              <td>
                <%=helper.getStatusList()%>
              </td>
              <td class="grey" > 
                <div align="right"><b>% Complete:</b></div>
              </td>
              <td>
                <input type="text" name="<%=ProjectHelper.ID_PERCENT_COMPLETE%>" value="<%=helper.getPercentComplete()%>" size="4" maxlength="3" >
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Approved Date:</b></div>
              </td>
              <td>
                <%=helper.getApprovedMonthList()%>
                <%=helper.getApprovedDayList()%>
                <%=helper.getApprovedYearList()%>
              </td>
              <td class="grey" > 
                <div align="right"><b>Shipped Date:</b></div>
              </td>
              <td>
                <%=helper.getShippedMonthList()%>
                <%=helper.getShippedDayList()%>
                <%=helper.getShippedYearList()%>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Notes:</b></div>
              </td>
              <td colspan="3">
                <textarea name="<%=Escaper.htmlEscapeAndPreserveWhitespace(ProjectHelper.ID_NOTES)%>" cols="60" rows="4" maxlength="255" onkeyup="maxTextarea();"><%=helper.getNotes()%></textarea>
              </td>
            </tr>
            <tr class="yellow"> 
              <td colspan="4" align="left" style="color: red;"> 
                * indicates a required field
              </td>
            </tr>
            <tr><td colspan="4"></td></tr>
            <tr class="white"> 
              <td colspan="4"> 
                <div align="center"> 
                  <input type="submit" value="Submit">
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>

<!-- Line Items -->
    <p></p>
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
			      <tr class="yellow"> 
              <td colspan="5" align="center"><b>Line Items</b> 
              </td>
            </tr> 
<%
// Display the project's line items, with a link to edit each or add a new one.
StringBuffer href = new StringBuffer();
href.append("/iltds/Dispatch?op=");
href.append(editLineItemOp);
href.append('&');
href.append(ProjectHelper.ID_PROJECT_ID);
href.append('=');
href.append(helper.getId());
Iterator lineItems = helper.getLineItems().iterator();
if (lineItems.hasNext()) {
%>
			      <tr class="green"> 
              <td> 
                <div align="left"><b>#</b></div>
              </td>
              <td> 
                <div align="left"><b>Requested Quantity</b></div>
              </td>
              <td> 
                <div align="left"><b>Selected Quantity</b></div>
              </td>
              <td> 
                <div align="left"><b>Format</b></div>
              </td>
              <td>&nbsp; 
                
              </td>
            </tr>
<%
  while (lineItems.hasNext()) {
    LineItemHelper liHelper = (LineItemHelper)lineItems.next(); 
%>
			      <tr class="white">
<%
    int rowspan = 1;
    if (liHelper.hasNotes()) rowspan++;
    if (liHelper.hasComments()) rowspan++;
%>

              <td rowspan="<%=rowspan%>"> 
                <%=liHelper.getLineItemNumber()%>
              </td>
              <td> 
                <%=liHelper.getQuantity()%>
              </td>
              <td> 
                <%=liHelper.getSelectedQuantity()%>
              </td>
              <td> 
                <%=liHelper.getFormat()%>
              </td>
              <td rowspan="<%=rowspan%>"> 
              <% String theURL = href.toString() + "&" + LineItemHelper.ID_LINEITEM_ID + "=" + liHelper.getId(); %>
                <html:link page="<%= theURL %>">Edit</html:link> 
              </td>
            </tr>
<%
    if (liHelper.hasNotes()) {
%>
			      <tr class="white"> 
              <td colspan="3"><b>Description:</b><br> 
                <%=Escaper.htmlEscapeAndPreserveWhitespace(liHelper.getNotes())%>
              </td>
            </tr>
<%
    }
    if (liHelper.hasComments()) {
%>
			      <tr class="white"> 
              <td colspan="3"><b>Comments:</b><br> 
                <%=Escaper.htmlEscapeAndPreserveWhitespace(liHelper.getComments())%>
              </td>
            </tr>
<%
    }
%>
            <tr><td colspan="5"></td></tr>
<%
  }
}
else {  // no line items
%>
			      <tr class="yellow"> 
              <td colspan="5">There are no line items for this project. 
              </td>
            </tr> 
<%
}
%>
            <tr class="white"> 
              <td colspan="5"> 
                <div align="center"> 
                  <html:link page="<%= href.toString() %>">Add New Line Item</html:link>
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>

<!-- Work Orders -->
    <p></p>
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
			      <tr class="yellow"> 
              <td colspan="7" align="center"><b>Work Orders</b> 
              </td>
            </tr> 
<%
// Display the project's work orders, with a link to edit each or add a new one.
href = new StringBuffer();
href.append("/iltds/Dispatch?op=");
href.append(editWorkOrderOp);
href.append('&');
href.append(ProjectHelper.ID_PROJECT_ID);
href.append('=');
href.append(helper.getId());
Iterator workOrders = helper.getWorkOrders().iterator();
if (workOrders.hasNext()) {
%>
			      <tr class="green"> 
              <td> 
                <div align="left"><b>Work Order</b></div>
              </td>
              <td> 
                <div align="left"><b>Start Date</b></div>
              </td>
              <td> 
                <div align="left"><b>End Date</b></div>
              </td>
              <td> 
                <div align="left"><b>Status</b></div>
              </td>
              <td> 
                <div align="left"><b>% Complete</b></div>
              </td>
              <td> 
                <div align="left"><b># Samples</b></div>
              </td>
              <td>&nbsp; 
                
              </td>
            </tr>
            <tr><td colspan="7"></td></tr>
<%
  while (workOrders.hasNext()) {
    WorkOrderHelper woHelper = (WorkOrderHelper)workOrders.next(); 
%>
			      <tr class="white"> 
              <td> 
                <%=Escaper.htmlEscape(woHelper.getWorkOrderName())%>
              </td>
              <td> 
                <%=woHelper.getStartDate()%>
              </td>
              <td> 
                <%=woHelper.getEndDate()%>
              </td>
              <td> 
                <%=woHelper.getStatus()%>
              </td>
              <td> 
                <%=woHelper.getPercentComplete()%>
              </td>
              <td> 
                <%=woHelper.getNumSamples()%>
              </td>
              <td>
              <% String theURL = href.toString() + "&" + WorkOrderHelper.ID_WORKORDER_ID + "=" + woHelper.getId(); %>
                <html:link page="<%= theURL %>">Edit</html:link> 
              </td>
            </tr>
<%
    if (woHelper.hasNotes()) {
%>
			      <tr class="white"> 
              <td colspan="7"><b>Notes:</b><br> 
                <%=Escaper.htmlEscapeAndPreserveWhitespace(woHelper.getNotes())%>
              </td>
            </tr>
<%
    }
%>
            <tr><td colspan="7"></td></tr>
<%
  }
}
else {  // no work orders
%>
			      <tr class="yellow"> 
              <td colspan="7">There are no work orders for this project. 
              </td>
            </tr> 
<%
}
%>
            <tr class="white"> 
              <td colspan="7"> 
                <div align="center"> 
                  <html:link page="<%=href.toString()%>">Add New Work Order</html:link>
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	</form>
  </div>

</body>

</html>
