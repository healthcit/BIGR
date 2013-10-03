<%@ page import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>

<html>

<head>
<script language="JavaScript">
</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr">


<form name="menu" method="post" action="<html:rewrite page="/lims/AdminServlet"/>">
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
            <tr class="yellow"> 
              <td colspan="7"> 
                <div align="center"><font color="#FF0000"><b>
                <html:errors/></b></font></div>
              </td>
            </tr>
        	<% } %>
            <tr class="yellow"> 
              <td colspan="8"> 
                <div align="center"><b>Report Date: </b><%= Calendar.getInstance().getTime() %></div>
              </td>
            </tr>
            <tr class="green"> 
              <td> 
                <div align="center"><b>Sample Label</b></div>
              </td>
              <td> 
                <div align="center"><b>Slide Label</b></div>
              </td>
              <td> 
                <div align="center"><b>DI Name</b></div>
              </td>
              <td> 
                <div align="center"><b>Case ID</b></div>
              </td>
              <td> 
                <div align="center"><b>Sample Type</b></div>
              </td>
              <td> 
                <div align="center"><b>Procedure</b></div>
              </td>
              <td> 
                <div align="center"><b>Cut Level</b></div>
              </td>
            </tr>
            <logic:present name="limsPathLabForm" property="sampleIdList">
            <logic:iterate id="sample" name="limsPathLabForm" property="sampleIdList" indexId="index">
            	<bean:define id="slide" name="limsPathLabForm" property="slideIdList" type="java.lang.String[]"/>
            	<bean:define id="di" name="limsPathLabForm" property="diNameList" type="java.lang.String[]"/>
            	<bean:define id="caseId" name="limsPathLabForm" property="caseIdList" type="java.lang.String[]"/>
            	<bean:define id="sampleTypeDisplay" name="limsPathLabForm" property="sampleTypeDisplayList" type="java.lang.String[]"/>
		        <bean:define id="cutLevel" name="limsPathLabForm" property="cutLevelList" type="java.lang.String[]"/>   
		        <bean:define id="procedure" name="limsPathLabForm" property="procedureList" type="java.lang.String[]"/>
            <tr class="white"> 
              <td> 
                <bean:write name="sample"/>
                  
                  <html:hidden property="sampleIdList" value='<%= sample.toString() %>'/>
              </td>
              <td> 
                <%= slide[index.intValue()]%>
              </td>
              <td> 
                <%= di[index.intValue()]%>
              </td>
              <td> 
                <%= caseId[index.intValue()]%>
              </td>
              <td> 
                <%= sampleTypeDisplay[index.intValue()]%>
              </td>
              <td> 
                <%= procedure[index.intValue()]%>
              </td>
              <td>
              	<%= cutLevel[index.intValue()]%>
              </td>
            </tr>
            </logic:iterate>
            </logic:present>
            <tr class="white"> 
              <td colspan="8"> 
                <div align="center"> 
                  <input type="Button" value="Print" 
                    onClick="window.print()">
                  
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</form>
</body>
</html>
