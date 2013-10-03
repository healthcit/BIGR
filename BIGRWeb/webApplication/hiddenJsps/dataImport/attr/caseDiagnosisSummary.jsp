<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.orm.helpers.BigrGbossData" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormHostElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<bean:define name="caseForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.CaseForm"/>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormHostElementContext hostElementContext = formContext.getDataFormHostElementContext();
String label = hostElementContext.getDisplayName();
if (ApiFunctions.isEmpty(label)) {
	label = "Diagnosis";
}
String dx = BigrGbossData.getDiagnosisDescription(myForm.getDiagnosis());
String dxOther = myForm.getDiagnosisOther();
if (!ApiFunctions.isEmpty(dxOther)) {
  dx = dx + " (" + dxOther + ")";
}
%>
<div class="kcElement">
<label><%=label%>:</label>
<%=dx%>
</div>
