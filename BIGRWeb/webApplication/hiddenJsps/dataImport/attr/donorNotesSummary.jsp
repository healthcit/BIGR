<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormHostElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormHostElementContext hostElementContext = formContext.getDataFormHostElementContext();
String label = hostElementContext.getDisplayName();
if (ApiFunctions.isEmpty(label)) {
	label = "Donor Notes";
}
%>
<div class="kcElement">
<label>
<%=label%>:
</label>
<html:textarea name="donor" property="donorProfileNotes" cols="80" rows="15" readonly="true"/> 
</div>
