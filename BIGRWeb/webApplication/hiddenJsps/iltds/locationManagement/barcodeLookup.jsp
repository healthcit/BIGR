<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
myBanner = 'Update Box Location';
</script>
</head>
<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
      <div align="center"> 
        <form name="consentRelease" method="post" action="<html:rewrite page='/iltds/Dispatch'/>">
   
          
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
              <td> 
                
          <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="332">
            <% if(request.getAttribute("myError")!=null) { %>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError") %></b></font></div>
              </td>
            </tr>
			<script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
            <% } %>
            <tr class="white"> 
              <td class="grey" width="188"><b>Scan a box or Sample ID:</b></td>
              <td width="130"> 
                <input type="text" name="barcodeID" size="<%= (ValidateIds.LENGTH_BOX_ID + 5)%>" maxlength="<%= ValidateIds.LENGTH_BOX_ID %>">
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="hidden" name="op" value="LocationLookupStart">
                  <input type="submit" name="Submit" value="Continue">
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
		document.consentRelease.barcodeID.focus()
		</script>
</body>
</html>
