<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.javabeans.BoxLayoutDto"%>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Expected Samples</title>
</head>
<body class="bigr">
<table border="0" cellspacing="0" cellpadding="0" class="background" width="100%">
  <tr> 
    <td> 
      <% BoxLayoutDto boxLayoutDto = (BoxLayoutDto)request.getAttribute("boxLayoutDto");%>
      <table border="0" cellspacing="1" cellpadding="3" class="foreground" width="100%">
        <tr class="yellow"> 
          <td> 
            <div align="center" ><b>Expected Samples</b></div>
          </td>
        </tr>
        <tr class="green"> 
          <td> 
            <div align="center" ><b>Box Number:</b> <%=request.getParameter("boxID")%></div>
          </td>
        </tr>
        <% List pulledSampleList = (List)request.getAttribute("pulledSampleList");%>
    <tr class="white"> 
      <td> 
        <bigr:box
          boxLayoutDto="<%=boxLayoutDto%>"
          boxCellContentVar="sample"
          sampleTypeVar="sampleType"
          sampleAliasVar="sampleAlias"
          highlightList="<%=pulledSampleList%>"
          cellType="readonly"
          printerFriendly="true"
        />
      </td>
    </tr>
        <tr class="white" align="center"> 
          <td> 
              <input type="button" name="Button" value="Print" onclick="window.print();" class="noprint">
              <input type="button" name="Button" value="Close" onclick="window.close();" class="noprint">
              <br>
              <font color="#FF0000">*Red Samples have been pulled or revoked.</font> 
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
