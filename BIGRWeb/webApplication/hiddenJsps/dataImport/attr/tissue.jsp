<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory"%>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormHostElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.ardais.bigr.library.web.form.QueryForm" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormHostElementContext hostElementContext = formContext.getDataFormHostElementContext();
String attr = ArtsConstants.ATTRIBUTE_TISSUE;
String attrProperty = "sampleData." + Constants.getPropertyName(attr);
String attrOtherProperty = attrProperty + "Other";
String label = hostElementContext.getDisplayName();

if(request.getParameter("fieldId")!= null)
attrProperty = attrProperty+"&" + request.getParameter("fieldId");

if (ApiFunctions.isEmpty(label)) {
	label = GbossFactory.getInstance().getDescription(attr);
}
String otherLabel = "Other " + label;
String firstDisplayValue = "Select " + label;

%>
<div class="kcElement">
<label>
<%=hostElementContext.isRequired()?"<span class=\"kcRequiredInd\">*</span>":""%>
<span onmouseout="return nd();" onmouseover="return overlib('Enter one or more words or partial words separated by semicolons to display tissues that contain all text in the  dropdown list.<br>  Example: \'colon;sig\' or \'sigmoid;colon\' returns a list of tissues containing \'colon\' AND \'sigmoid\' in the name');">
<b><%=label%>:</b>
</span>
</label>

</div>

<logic:present name="CONTROL_OTHER">
   <% attrOtherProperty=QueryForm.CONTROL_OTHER ;%>
</logic:present>

<bigr:tissueWithOther name="sampleForm"
  property="<%=attrProperty%>"
  otherProperty="<%=attrOtherProperty%>"
  otherPropertyLabel="<%=otherLabel%>"
  firstValue="" firstDisplayValue="<%=firstDisplayValue%>"
  includeAlphaLookup="true"
  mode="nolayout"/>

 
<script type="text/javascript">

GSBIO.bigr.reg.Elements.register("<%=attrProperty%>");
GSBIO.bigr.reg.Elements.register("<%=attrOtherProperty%>");

</script>