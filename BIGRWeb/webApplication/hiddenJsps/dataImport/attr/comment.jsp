<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory"%>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormHostElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormHostElementContext hostElementContext = formContext.getDataFormHostElementContext();
String attr = ArtsConstants.ATTRIBUTE_COMMENT;
String attrProperty = "sampleData." + Constants.getPropertyName(attr);
String label = hostElementContext.getDisplayName();
if (ApiFunctions.isEmpty(label)) {
	label = GbossFactory.getInstance().getDescription(attr);
}
%>
<div class="kcElement">
<label>
<%=hostElementContext.isRequired()?"<span class=\"kcRequiredInd\">*</span>":""%>
<%=label%>:
</label>
<html:textarea name="sampleForm" property="<%=attrProperty%>" rows="10" cols="80" onkeyup="BigrTextAreaComments.enforceMaxLength();"/>
<script language="JavaScript">
  BigrTextAreaComments = new BigrTextArea('<%=attrProperty%>');
  BigrTextAreaComments.maxLength = 255;
</script>
</div>		    
<script type="text/javascript">
GSBIO.bigr.reg.Elements.register("<%=attrProperty%>");
</script>