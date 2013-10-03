<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
/**
 * Used to allow searching for a project for a transaction that requires a
 * project to be selected.  Allows searching be project name or project clients.
 * Also contains a link to the previously selected project, if any.  The 
 * following are the HTTP request attributes:
 * <ul>
 * <li>
 *	JspHelper.ID_HELPER - A ProjectHelper that holds the previously selected
 *    project, if any.  This allows a link to that project to be created in 
 *    this JSP.
 * </li>
 * <li>
 *	JspHelper.ID_FOR_OP - The op class that should be invoked after the search
 *    is complete.  This is the first op in the desired transaction.
 * </li>
 * <li>
 *	JspHelper.ID_FOR_OP_TEXT - Text that describes the transaction that can be 
 *    used in the link to the project.
 * </li>
 * </ul>
 */
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
ProjectHelper helper = (ProjectHelper)request.getAttribute(JspHelper.ID_HELPER);
String myBanner = "Search for a Project";
String submitToOp = "PtsProjectSelectPrepare"; 
String forOp = (String)request.getAttribute(JspHelper.ID_FOR_OP);
if (forOp == null) {
  forOp = request.getParameter(JspHelper.ID_FOR_OP);
} 
String forOpText = (String)request.getAttribute(JspHelper.ID_FOR_OP_TEXT);
if (forOpText == null) {
  forOpText = request.getParameter(JspHelper.ID_FOR_OP_TEXT);
} 
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
  document.all.<%=ProjectHelper.ID_CLIENT%>.selectedIndex = 0;
  document.all.<%=Escaper.htmlEscapeAndPreserveWhitespace(ProjectHelper.ID_PROJECT_NAME)%>.focus();
}

//-->
</script>
</head>
<body class="bigr" onLoad="initPage();">
  <div align="center"> 
  <form name="theForm" method="post" action="<html:rewrite page='/iltds/Dispatch'/>">
    <input type="hidden" name="op" value="<%=submitToOp%>">
    <input type="hidden" name="<%=JspHelper.ID_FOR_OP%>" value="<%=forOp%>">
    <input type="hidden" name="<%=JspHelper.ID_FOR_OP_TEXT%>" value="<%=forOpText%>">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <%=helper.getMessagesForColspan(2)%>
          
<%
if (helper.getRawId() != null) {
%>
<!-- Quick link to most recently referenced project. -->
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center">
                <% 
                	String theURL = "/iltds/Dispatch?op=" + forOp + "&" + ProjectHelper.ID_PROJECT_ID;
                	theURL = theURL + "=" + helper.getRawId();
                %>
                  <html:link page="<%= theURL %>"><%=forOpText%> <%=Escaper.htmlEscapeAndPreserveWhitespace(helper.getProjectName())%></html:link>
                </div>
              </td>
            </tr>
            <%=JspHelper.getBlankDivider(2)%>
            <%=JspHelper.getOrDivider(2)%>
<%
}
%>
<!-- Allows the user to search for a project given its id. -->
<!--            <tr> 
              <td class="yellow" colspan="2"> 
                <div align="center"><b>Search for a project by id:</b> 
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <table border="0" cellspacing="0" cellpadding="0" class="background" width="100%">
                  <tr> 
                    <td> 
                      <table border="0" cellspacing="1" cellpadding="3" vspace="0" class="foreground" width="100%">
                        <tr class="white"> 
                          <td class="grey" align="right"> 
                            <b>Project ID:</b>
                          </td>
                          <td> 
                            <input type="text" name="<%=ProjectHelper.ID_PROJECT_ID%>" size="20" maxlength="12" value="">
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
-->
<!-- Allows the user to search for a project given its name. -->
            <tr> 
              <td class="yellow" colspan="2"> 
                <div align="center"><b>Search for a project by name:</b> 
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <table border="0" cellspacing="0" cellpadding="0" class="background" width="100%">
                  <tr> 
                    <td> 
                      <table border="0" cellspacing="1" cellpadding="3" vspace="0" class="foreground" width="100%">
                        <tr class="white"> 
                          <td class="grey" align="right"> 
                            <b>Project Name:</b>
                          </td>
                          <td> 
                            <input type="text" name="<%=Escaper.htmlEscapeAndPreserveWhitespace(ProjectHelper.ID_PROJECT_NAME)%>" size="30" maxlength="50" value="">
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>

<!-- Allows the user to search for all projects with the specified criteria. -->
            <%=JspHelper.getBlankDivider(2)%>
            <%=JspHelper.getOrDivider(2)%>
            <tr class="yellow"> 
              <td align="center" colspan="2"><b>Search for a project by client:</b></td> 
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <table border="0" cellspacing="0" cellpadding="0" class="background" width="100%">
                  <tr> 
                    <td> 
                      <table border="0" cellspacing="1" cellpadding="3" vspace="0" class="foreground" width="100%">
                        <tr class="white"> 
                          <td class="grey" align="right"> 
                            <b>Client:</b>
                          </td>
                          <td> 
                            <%=helper.getClientList()%>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr class="white">
              <td colspan="2">
                <div align="center"> 
                  <input type="submit" name="submit" value="Submit">
                </div>
              </td>
            </tr>
          </form>
          </table>
        </td>
      </tr>
    </table>
  </div>
</form>
</body>
</html>
