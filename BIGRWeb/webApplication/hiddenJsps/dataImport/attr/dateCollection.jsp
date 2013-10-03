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
String attr = ArtsConstants.ATTRIBUTE_DATE_OF_COLLECTION;
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
<html:text name="sampleForm" property="<%=attrProperty%>" readonly="true"/>
&nbsp;
<span class="fakeLink" onclick="openCalendar('<%=attrProperty%>')">Select Date</span>
&nbsp;&nbsp;&nbsp;&nbsp;
<html:select name="sampleForm" property="sampleData.collectionDateTime.hour">
  <html:option value="">Hr</html:option>
	<html:options name="sampleForm" property="hourList"/>
</html:select>:
<html:select name="sampleForm" property="sampleData.collectionDateTime.minute">
  <html:option value="">Min</html:option>
  <html:options name="sampleForm" property="minuteList"/>
</html:select>
&nbsp;
<html:radio name="sampleForm" property="sampleData.collectionDateTime.meridian" value="AM"/>AM
<html:radio name="sampleForm" property="sampleData.collectionDateTime.meridian" value="PM"/>PM
&nbsp;&nbsp;&nbsp;
<html:button property="resetCollection" value="Clear" onclick="GSBIO.bigr.reg.clearDateTime('collection');"/>
</div>		    
<script type="text/javascript">
GSBIO.bigr.reg.Elements.register("<%=attrProperty%>");
GSBIO.bigr.reg.Elements.register("sampleData.collectionDateTime.hour");
GSBIO.bigr.reg.Elements.register("sampleData.collectionDateTime.minute");
GSBIO.bigr.reg.Elements.registerRadio("sampleData.collectionDateTime.meridian");
</script>