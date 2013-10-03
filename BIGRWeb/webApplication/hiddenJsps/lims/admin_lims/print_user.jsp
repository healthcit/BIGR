<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
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
<form name="menu" method="post" Action="<html:rewrite page="/lims/AdminServlet"/>">
<div align="center">
  <table class="background" cellpadding="0" cellspacing="0" border="0">
    <tr> 
      <td> 
        <table class="foreground" cellpadding="3" cellspacing="1" border="0">
<% if (request.getAttribute("admin.msg") != null) { %>
          <script>
            alert("<%= request.getAttribute("admin.msg") %>");
          </script>
          <tr class="yellow"> 
            <td> 
              <div align="center"><%= request.getAttribute("admin.msg") %> </div>
            </td>
          </tr>
<% } else { %>
          <% 
            String username = (String)request.getAttribute("username");
            String usergroup = (String)request.getAttribute("usergroup");
          %>
          <tr class="yellow">
            <td> 
              <div align="center">Click to print the user ID 
              </div>
            </td>
          </tr>
          <tr class="white"> 
              <td> 
                <div align="center">
                  <input type="Button" value="Print Label"
        onClick="popupPage('<html:rewrite page="/lims/AdminServlet?cmd=do-print-user&username="/><%= username %>&usergroup=<%= usergroup %>','popuppage', '550','200','no')">
                  <input type="hidden" name="cmd" value="abort">
                </div>
              </td>
            </tr>
          
        </table>
      </td>
    </tr>
    <% } %>
  </table>
</div>
</form>
</body>
</html>
