<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.LineItemHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.SampleTableHelper"%>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
ProjectHelper helper = (ProjectHelper)request.getAttribute(JspHelper.ID_HELPER);
String myBanner = "Add Samples to Project (" + helper.getProjectName() + ")";
String submitToOp = "PtsSampleSearchPrepare";
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
}

//-->
</script>
</head>
<body class="bigr" onLoad="initPage();">
<form name="theForm" method="post" action="<html:rewrite page='/iltds/Dispatch'/>" onsubmit="return false;">
  <div align="center">
   <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 

<%
// Display the project's line items, with a link to select samples for each.
StringBuffer href = new StringBuffer();
href.append("/iltds/Dispatch?op=");
href.append(submitToOp);
href.append('&');
href.append(ProjectHelper.ID_PROJECT_ID);
href.append('=');
href.append(helper.getId());
List lineItems = helper.getLineItems();
int size = lineItems.size();
if (size > 0) {
%>
<!-- Line Items -->
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
<%
  for (int i = 0; i < size; i++) {
    LineItemHelper liHelper = (LineItemHelper)lineItems.get(i); 
    if (i > 0) {
%>
            <%=JspHelper.getBlankDivider(5)%>
<%
    } 
%>
			      <tr class="yellow"> 
              <td colspan="5" align="center"> 
                  <b>Line Item #<%=liHelper.getLineItemNumber()%></b>
              </td>
            </tr>
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
              <td> 
                &nbsp;
              </td>
            </tr>
			      <tr class="white"> 
              <td> 
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
              <td>
                <% String liHref = href + "&" + LineItemHelper.ID_LINEITEM_ID + "=" + liHelper.getId(); %>
                <html:link page="<%=liHref%>">Select Samples</html:link> 
              </td>
            </tr>
<%
    if (liHelper.hasNotes()) {
%>
			      <tr class="white"> 
              <td colspan="5"><b>Description:</b><br> 
                <%=Escaper.htmlEscapeAndPreserveWhitespace(liHelper.getNotes())%>
              </td>
            </tr>
<%
    }
    if (liHelper.hasComments()) {
%>
			      <tr class="white"> 
              <td colspan="5"><b>Comments:</b><br> 
                <%=Escaper.htmlEscapeAndPreserveWhitespace(liHelper.getComments())%>
              </td>
            </tr>
<%
    }
    SampleTableHelper tableHelper = liHelper.getSampleTableHelper();
%>
          <tr>
            <td style="padding: 0; background-color: white;"></td>
            <td style="padding: 0;" colspan="4">

            <!-- Samples -->  
            <%=tableHelper.getSampleTable()%>
            
            </td>
          </tr>
<%
  }  // while more line items
}  // if has line items
else {  // no line items
%>
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
			      <tr class="yellow"> 
              <td>There are no line items for this project. 
              </td>
            </tr>
<%
}
%>
          </table>
        </td>
      </tr>
    </table>
</form>
</body>
</html>
