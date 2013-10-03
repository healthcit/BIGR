<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.gulfstreambio.gboss.GbossFactory"%>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormHostElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");

FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormHostElementContext hostElementContext = formContext.getDataFormHostElementContext();
String attr = ArtsConstants.ATTRIBUTE_TOTAL_NUMBER_OF_CELLS;
String attrProperty = "sampleData." + Constants.getPropertyName(attr);
String label = hostElementContext.getDisplayName();
if (ApiFunctions.isEmpty(label)) {
	label = GbossFactory.getInstance().getDescription(attr);
}
String firstDisplayValue = "Select exponential representation";
%>
<div class="kcElement">
<label>
<%=hostElementContext.isRequired()?"<span class=\"kcRequiredInd\">*</span>":""%>
<%=label%>:
</label>
<html:text name="sampleForm" property="<%=attrProperty%>"
  size="6" maxlength="6"/>&nbsp;&nbsp;
<bigr:selectList name="sampleForm"
  property="sampleData.totalNumOfCellsExRepCui" 
  legalValueSetProperty="totalNumOfCellsExRepChoices"
  firstValue="" firstDisplayValue="<%=firstDisplayValue%>"/>
</div>		    
<script type="text/javascript">
GSBIO.bigr.reg.Elements.register("<%=attrProperty%>");
GSBIO.bigr.reg.Elements.register("sampleData.totalNumOfCellsExRepCui");
</script>