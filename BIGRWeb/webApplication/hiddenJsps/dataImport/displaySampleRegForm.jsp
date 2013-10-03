<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="javax.servlet.jsp.JspException" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

//The form definition must be specified, so if it is not throw an exception.
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormDefinition formDefinition = formContext.getDataFormDefinition();
String ignoreErrors = (String)request.getAttribute("ignoreErrors");
if (!"true".equals(ignoreErrors)) {
%>
<logic:present name="org.apache.struts.action.ERROR">
<div id="sampleTypeErrorsDiv" class="errorsMessages" style="display:none;">
<html:errors/>
</div>
</logic:present>
<logic:present name="com.ardais.BTX_ACTION_MESSAGES">
<div id="sampleTypeMessagesDiv" class="errorsMessages" style="display:none;">
<bigr:btxActionMessages/>
</div>
</logic:present>
<%
}
if (formDefinition != null) {
%>
<kc:formInstancePagesEdit/>
<%
}
%>