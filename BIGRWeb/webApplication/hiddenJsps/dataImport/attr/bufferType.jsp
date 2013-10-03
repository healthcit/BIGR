<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory"%>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormHostElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormHostElementContext hostElementContext = formContext.getDataFormHostElementContext();
String attr = ArtsConstants.ATTRIBUTE_BUFFER_TYPE;
String attrProperty = "sampleData." + Constants.getPropertyName(attr);
String attrOtherProperty = "sampleData.bufferTypeOther";
String label = hostElementContext.getDisplayName();
if (ApiFunctions.isEmpty(label)) {
	label = GbossFactory.getInstance().getDescription(attr);
}
String otherLabel = "Other " + label;
String firstDisplayValue = "Select " + label;
%>
<div class="kcElement">
<label>
<%=hostElementContext.isRequired()?"<span class=\"kcRequiredInd\">*</span>":""%>
<%=label%>:
</label>
<bigr:selectListWithOther name="sampleForm"
  property="<%=attrProperty%>" legalValueSetProperty="bufferTypeChoices"
  otherProperty="<%=attrOtherProperty%>" otherCode="<%=ArtsConstants.OTHER_BUFFER_TYPE%>"
  firstValue="" firstDisplayValue="<%=firstDisplayValue%>"/>
<br><%=otherLabel%>:&nbsp;
<bigr:otherText name="sampleForm" 
  property="<%=attrOtherProperty%>" parentProperty="<%=attrProperty%>"
  otherCode="<%=ArtsConstants.OTHER_BUFFER_TYPE%>" size="80" maxlength="200"/>
</div>
<script type="text/javascript">
GSBIO.bigr.reg.Elements.register("<%=attrProperty%>");
GSBIO.bigr.reg.Elements.register("<%=attrOtherProperty%>");
</script>