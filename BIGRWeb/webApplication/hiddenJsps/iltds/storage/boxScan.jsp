<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.orm.helpers.BoxScanData"%>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%
    String myError = ApiFunctions.safeToString(request.getAttribute("myError"));
    String myTitle = ApiFunctions.safeToString(request.getParameter("title"));
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
myBanner = '<%=request.getParameter("title")%>';
</script>
</head>
<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<div align="center"> 
  <form name="boxForm" method="POST" action="<html:rewrite page='/iltds/Dispatch'/>">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <% if (!ApiFunctions.isEmpty(myError)) { %>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><font color="#FF0000"><b><%= myError %></b></font></div>
              </td>
            </tr>
			<script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
            <% } %>
            <%
               BoxScanData bsd = (BoxScanData)request.getAttribute("boxScanData");

               String boxLayoutId = request.getParameter("boxLayoutId");
               String defaultBoxLayoutId = bsd.getDefaultBoxLayoutId();

               if (bsd.getNumberOfAccountBoxLayouts() > 1) {
                 defaultBoxLayoutId = (ApiFunctions.isEmpty(boxLayoutId)) ?  defaultBoxLayoutId : boxLayoutId;
            %>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right">Box Layout:&nbsp;</div>
              </td>
              <td>
                <bigr:selectList
                  property="boxLayoutId"
                  value="<%=defaultBoxLayoutId%>"
                  legalValueSet="<%=bsd.getAccountBoxLayoutSet()%>"
                />
              </td>
            </tr>
            <% } else {%>
            <input type="hidden" name="boxLayoutId" value="<%=bsd.getDefaultBoxLayoutId()%>"/>
            <% } %>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right">Box ID:&nbsp;</div>
              </td>
              <td> 
                <input type="text" name="boxID" size="20" maxlength="12">
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="submit" name="Submit" value="Continue">
                  <input type="hidden" name="op" value="BoxScanInitial">
				  <input type="hidden" name="title" value="<%= myTitle %>">
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </form>
</div>
<script language="JavaScript">
    document.boxForm.boxID.focus()
</script>
</body>
</html>
