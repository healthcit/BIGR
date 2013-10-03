<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory"%>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormHostElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormHostElementContext hostElementContext = formContext.getDataFormHostElementContext();
String attr = ArtsConstants.ATTRIBUTE_PROCEDURE;
String attrProperty = "sampleData." + Constants.getPropertyName(attr);
String attrOtherProperty = attrProperty + "Other";
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
<span onmouseout="return nd();" onmouseover="return overlib('Enter one or more words or partial words separated by semicolons to display procedures that contain all text in the dropdown list.<br>  Example: \'hear;val\' or \'val;hear\' returns a list of procedures containing \'heart\' AND \'valve\' in the name');">
<b><%=label%>:</b>
</span>
</label>
<bigr:procedureWithOther name="sampleForm"
  property="<%=attrProperty%>"
  otherProperty="<%=attrOtherProperty%>"
  otherPropertyLabel="<%=otherLabel%>"
  firstValue="" firstDisplayValue="<%=firstDisplayValue%>"
  includeAlphaLookup="true"
  mode="nolayout"/>
</div>
<script type="text/javascript">
GSBIO.bigr.reg.Elements.register("<%=attrProperty%>");
GSBIO.bigr.reg.Elements.register("<%=attrOtherProperty%>");
</script>