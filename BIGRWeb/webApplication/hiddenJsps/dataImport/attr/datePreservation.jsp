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
String attr = ArtsConstants.ATTRIBUTE_DATE_OF_PRESERVATION;
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
<html:select name="sampleForm" property="sampleData.preservationDateTime.hour">
  <html:option value="">Hr</html:option>
	<html:options name="sampleForm" property="hourList"/>
</html:select>:
<html:select name="sampleForm" property="sampleData.preservationDateTime.minute">
  <html:option value="">Min</html:option>
  <html:options name="sampleForm" property="minuteList"/>
</html:select>
&nbsp;
<html:radio name="sampleForm" property="sampleData.preservationDateTime.meridian" value="AM"/>AM
<html:radio name="sampleForm" property="sampleData.preservationDateTime.meridian" value="PM"/>PM
&nbsp;&nbsp;&nbsp;
<html:button property="resetPreservation" value="Clear" onclick="GSBIO.bigr.reg.clearDateTime('preservation');"/>
</div>		    
<script type="text/javascript">
GSBIO.bigr.reg.Elements.register("<%=attrProperty%>");
GSBIO.bigr.reg.Elements.register("sampleData.preservationDateTime.hour");
GSBIO.bigr.reg.Elements.register("sampleData.preservationDateTime.minute");
GSBIO.bigr.reg.Elements.registerRadio("sampleData.preservationDateTime.meridian");
</script>