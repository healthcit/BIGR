<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
/**
 * Presents a list of projects, and allows one of them to be selected for a 
 * transaction.  The following are the HTTP request attributes:
 * <ul>
 * <li>
 *	JspHelper.ID_HELPERS - A List of ProjectHelpers that holds the projects
 *    that were obtained by a query against some search criteria.
 * </li>
 * </ul>
 * The following are the HTTP request parameters:
 * <ul>
 * <li>
 *	JspHelper.ID_FOR_OP - The op class that should be invoked when a project
 *    is selected.  This is the first op in the desired transaction.
 * </li>
 * <li>
 *	JspHelper.ID_FOR_OP_TEXT - Text that describes the transaction that can be 
 *    used in the link to the project.
 * </li>
 * </ul>
 */
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
List helpers = (List)request.getAttribute(JspHelper.ID_HELPERS);
String forOp = (String)request.getParameter(JspHelper.ID_FOR_OP); 
String forOpText = (String)request.getParameter(JspHelper.ID_FOR_OP_TEXT);
String myBanner = "Search for a Project";
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

<!-- Projects -->
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
			      <tr class="yellow"> 
              <td colspan="6" align="center"> 
                  <b>Projects</b>
              </td>
            </tr>
<%
// Display the projects, with a link to select each for the specified op.
Iterator projects = helpers.iterator();
if (projects.hasNext()) {
%>
			      <tr class="green"> 
              <td align="left"> 
                <b>Project ID</b>
              </td>
              <td align="left"> 
                <b>Project Name</b>
              </td>
              <td align="left"> 
                <b>Client</b>
              </td>
              <td align="left"> 
                <b>Request Date</b>
              </td>
              <td align="left"> 
                <b>Status</b>
              </td>
              <td> 
                &nbsp;
              </td>
            </tr>
<%
  while (projects.hasNext()) {
    ProjectHelper helper = (ProjectHelper)projects.next(); 
%>
			      <tr class="white"> 
              <td> 
                <%=helper.getId()%>
              </td>
              <td> 
                <%=Escaper.htmlEscapeAndPreserveWhitespace(helper.getProjectName())%>
              </td>
              <td> 
                <%=helper.getClient()%>
              </td>
              <td> 
                <%=helper.getRequestDate()%>
              </td>
              <td> 
                <%=helper.getStatus()%>
              </td>
              <td>
              <% String theURL="/iltds/Dispatch?op=" + forOp + "&" + ProjectHelper.ID_PROJECT_ID + "=" + helper.getRawId(); %>
                <html:link page="<%= theURL %>"><%=forOpText%> <%=Escaper.htmlEscapeAndPreserveWhitespace(helper.getProjectName())%></html:link>
              </td>
            </tr>
<%
  }
}
else {  // no projects
%>
			      <tr class="green"> 
              <td colspan="6">There are no projects that match the search criteria. 
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
