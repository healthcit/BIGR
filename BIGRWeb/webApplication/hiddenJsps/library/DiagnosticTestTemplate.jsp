<%@ page errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ page import="com.ardais.bigr.security.*"%>
<%@ page import="com.ardais.bigr.orm.helpers.BigrGbossData"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="java.util.Iterator"%>

<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>

	<tr>
		<td/>
        <%
         int testNumber=0;
         int totalCount=0;
         String diagnosticConceptId = (String) request.getAttribute("diagnosticConceptId");
         String diagnosticValueSet = (String) request.getAttribute("diagnosticValueSet");
         String diagnosticResultList = (String) request.getAttribute("diagnosticResultList");
         String testLabel = (String) request.getAttribute("testLabel");
         
         Iterator iter = BigrGbossData.getValueSet(diagnosticValueSet).getValues().iterator();
         if (request.getAttribute("totalCount") != null) {
         	totalCount = ApiFunctions.safeInteger((request.getAttribute("totalCount").toString())).intValue();
         }
         if (request.getAttribute("testNumber") != null) {
         	testNumber = ApiFunctions.safeInteger((request.getAttribute("testNumber").toString())).intValue();
         }
         long count = ApiFunctions.iteratorSize(iter);
         String sCount = count + "";
         String sTotalCount = totalCount + "";
         String sTestNumber = testNumber + "";
         String onclickScript ="checkEitherPerformedOrOthers('" + sTestNumber + "', '" + sTotalCount + "', '" + sCount + "');";
         String sProperty = "";
        %>
        <td align="left" style="padding-top: 0.5em;">
          <%=testLabel%> 
          <html:hidden property="diagnosticTest.diagnosticConceptId" value="<%=diagnosticConceptId%>"/>
          <html:hidden property="diagnosticTest.diagnosticValueSet" value="<%=diagnosticValueSet%>"/>
        </td>
        <td nowrap> 
		  <%
		  sProperty = "diagnosticTest.performedHiddenIndividual[" + testNumber + "]";
		  %>
		  <bean:define id="testValue" name="queryForm" property="<%=sProperty%>" type="java.lang.String"/>
          <html:hidden property="diagnosticTest.performedHidden" value='<%=testValue%>'/>
          <html:multibox property="diagnosticTest.performed" onclick="<%=onclickScript%>" value='<%= Constants.PERFORMED %>'/>Performed&nbsp;
        </td>
        <td align="center"> 
		  <%
		  sProperty = "diagnosticTest.testResultHiddenIndividual[" + testNumber + "]";
		  %>
          <html:hidden property="diagnosticTest.startIndex" value="<%=sTotalCount%>"/>
		  <bean:define id="testResult" name="queryForm" property="<%=sProperty%>" type="java.lang.String"/>
          <html:hidden property="diagnosticTest.testResultHidden" value='<%=testResult%>'/>
          <%
          sProperty = "diagnosticTest." + diagnosticResultList;
          %>
          <logic:iterate name="queryForm" property="<%=sProperty%>" id="diagnosticTest" type="com.gulfstreambio.gboss.GbossValue">
          	<html:multibox property="diagnosticTest.testResult" onclick="<%=onclickScript%>" value='<%= diagnosticTest.getCui() %>'/><%= diagnosticTest.getDescription() %>&nbsp;
          </logic:iterate>
          <html:hidden property="diagnosticTest.testCount" value="<%=sCount%>"/>
        </td>
        <td nowrap align="right"> 
		  <%
		  sProperty = "diagnosticTest.testShowHiddenIndividual[" + testNumber + "]";
		  %>
		  <bean:define id="testShow" name="queryForm" property="<%=sProperty%>" type="java.lang.String"/>
          <html:hidden property="diagnosticTest.testShowHidden" value='<%=testShow%>'/>
          <html:multibox property="diagnosticTest.testShow" onclick="<%=onclickScript%>" value='<%= Constants.SHOW %>'/>Show Results&nbsp;
        </td>
        <td/>
        <%
         totalCount+=count;
         sTotalCount = totalCount + "";
         request.setAttribute("totalCount", sTotalCount);
         testNumber++;
         sTestNumber = testNumber + "";
         request.setAttribute("testNumber", sTestNumber);
         %>
	</tr>
