<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory"%>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormHostElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define name="sampleForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.SampleForm"/>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormHostElementContext hostElementContext = formContext.getDataFormHostElementContext();
String attr = ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION;
String label = hostElementContext.getDisplayName();
if (ApiFunctions.isEmpty(label)) {
	label = GbossFactory.getInstance().getDescription(attr);
}
%>
<div class="kcElement">
<label><%=label%>:</label>
<bean:write name="myForm" property="sampleData.preservationDateTimeAsString"/>
</div>		    
