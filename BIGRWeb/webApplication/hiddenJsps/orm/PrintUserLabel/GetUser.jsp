<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<script Language="javascript">

function MM_callJS(jsStr) {

    return eval(jsStr)
}

mybanner = 'Print User Label';

</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css" />">
</head>
<body class="bigr" onLoad="if (parent.topFrame) {parent.topFrame.document.all.banner.innerHTML=mybanner;} document.menu.userid.focus();">
<form name="menu" method="post" action="<html:rewrite page="/orm/Dispatch" />">
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
          <logic:present name="message">	
          <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"> 
                  <FONT color="#ff0000"><B><bean:write name="message"/></B></FONT>
                </div>
              </td>
            </tr>
            </logic:present>	  	
            <tr class="white"> 
              <td class="grey"> 
                <div align="right">User ID:</div>
              </td>
              <td> 
                <div align="left"> 
                  <input type="text" size="12" name="userid" maxlength="10">
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="submit" value="Get User(s)">
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <FONT size="1">Leave User ID field blank to get list of all Users.</FONT>
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <input type="hidden" name="op" value="PrintUserLabelGetUser">
  </div>
</form>
</body>
</html>

