<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormHostElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.ardais.bigr.library.web.form.QueryForm" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormHostElementContext hostElementContext = formContext.getDataFormHostElementContext();
String label = hostElementContext.getDisplayName();
if (ApiFunctions.isEmpty(label)) {
	label = "Diagnosis";
}
String otherLabel = "Other " + label;
String diagnosis ="diagnosis";
String diagnosisOther = "diagnosisOther";

if(request.getParameter("fieldId")!= null)
diagnosis = diagnosis+"&" + request.getParameter("fieldId");

%>
<div class="kcElement">
<label>
<%=hostElementContext.isRequired()?"<span class=\"kcRequiredInd\">*</span>":""%>
<span onmouseout="return nd();" onmouseover="return overlib('Enter one or more words or partial words separated by semicolons to display diagnoses that contain all text in the  dropdown list.<br>  Example: \'adeno;lung;acinar\' or \'lung;aci;adeno\' returns a list of diagnoses containing \'adeno\' AND \'lung\' AND \'acinar\' in the name');">
<b><%=label%>:</b>
</span>
</label>

<logic:present name="CONTROL_OTHER">
   <% diagnosisOther=QueryForm.CONTROL_OTHER ;%>
</logic:present>

<bigr:diagnosisWithOther name="caseForm" 
  property="<%=diagnosis%>"
	otherProperty="<%=diagnosisOther%>"
  otherPropertyLabel="<%=otherLabel%>"
	firstValue="" firstDisplayValue="Select" 
	includeAlphaLookup="true"
  mode="nolayout"/>
</div>
