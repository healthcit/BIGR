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
	label = "Zip Code";
}
%>
<div class="kcElement">
<label>
<%=hostElementContext.isRequired()?"<span class=\"kcRequiredInd\">*</span>":""%>
<%=label%>:
</label>
<html:text name="donor" property="zipCode" size="4" maxlength="3"/> 
<font size="1">Enter first 3 digits of zip code only</font>
</div>