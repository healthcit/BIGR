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
<title>
  Print Confirmation
</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body class="bigr">
<div align="center">  
  <table class="background" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td> 
        <table class="foreground" cellpadding="3" cellspacing="1" border="0">
        
          <logic:present name="message">	
          <tr class="yellow"> 
              <td colspan="3"> 
                <div align="center"> 
                  <B><bean:write name="message"/></B>
                </div>
              </td>
            </tr>
            </logic:present>

          <tr class="white"> 
            <td colspan="3"> 
              <div align="center"> 
                <input type="Button" value="Close"
             onClick="window.close()">
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>