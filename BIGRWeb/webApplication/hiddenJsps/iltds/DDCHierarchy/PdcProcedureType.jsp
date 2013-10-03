<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.orm.helpers.BigrGbossData" %>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.gulfstreambio.gboss.GbossValue" %>
<%@ page import="com.gulfstreambio.gboss.GbossValueSet" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%
GbossValueSet valueSet = BigrGbossData.getValueSetAlphabetized(ArtsConstants.VALUE_SET_PROCEDURE_HIERARCHY);
List values = valueSet.getValues();
%>
<%
	String surgSpec = (String) request.getAttribute("procedure");							
%>

<select name="ProcedureType" onPropertyChange="showOther(); changed = true;">
  <option value="">Select</option>
  <%for (int i=0; i<values.size(); i++){ 
 %>
        <% GbossValue value = (GbossValue) values.get(i); %>
  <option value="<%=value.getCui()%>" <%if(surgSpec.equals(value.getCui())) out.print("selected");%>><%=value.getDescription()%></option>
  <% }%>
</select>
<input type='hidden' name="compName" value="Final_Pathology_Diagnosis">
<input type="Button" name="Open" value="..." onClick="window.open('<html:rewrite page="/orm/Dispatch?op=GetDxTcHierarchy&type=P"/>','Diagpage','width=250,height=350,top=160,left=230,scrollbars=yes');">