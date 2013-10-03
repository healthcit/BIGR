<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.*"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<html>
<head>
<script language="javascript">
function popupPage(mypage, myname, w, h, scroll) {
      var winl = (screen.width - w) / 2;
      var wint = (screen.height - h) / 2;
      var winprops = 'height='+h+',width='+w+',top='+wint+',left='+winl+',scrollbars='+scroll+',resizable'
      win = window.open(mypage, myname, winprops, 'true')
      if (parseInt(navigator.appVersion) >= 4) { win.window.focus(); }
}
</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr">
<form name="GetUserLabel" method="post" action="<html:rewrite page="/orm/Dispatch"/>">
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <tr class="yellow"> 
              <td><b>User Id</b></td>
              <td><b>Print Label</b></td>
            </tr>
			<logic:present name="users">
			<logic:iterate id="userIds" name="users" indexId="index">
            <tr class="<%= ((index.intValue() % 2) == 0) ? "white" : "grey" %>"> 
              <td> 
                <%=userIds%>
              </td>
              <td align="center"> 
                <div align="center"> 
                  <input type="Button" value="print"
              onClick="popupPage('<html:rewrite page="/orm/Dispatch?op=PrintUserLabel&userid="/><%=userIds%>', 'popuppage', '550', '200', 'no')">
                </div>
              </td>
            </tr>
            </logic:iterate>
			</logic:present>
          </table>
        </td>
      </tr>
    </table>
    <input type="hidden" name="op" value="PrintUserLabel">
  </div>
</form>
</body>
</html>


