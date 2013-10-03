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
GbossValueSet valueSet = BigrGbossData.getValueSetAlphabetized(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY);
List values = valueSet.getValues();
%>

 <select name="DiseaseType" >
 <%for (int i=0; i<values.size(); i++){ 
 %>

        <% GbossValue value = (GbossValue) values.get(i); %>
        <option value="<%=value.getCui()%>" selected><%=value.getDescription()%></option>
                 
        <% }%>
</select>
        
<input type='hidden' name="compName" value="Final_Pathology_Diagnosis">
<input type="Button" name="Open" value="..." onClick="window.open('<html:rewrite page="/orm/Dispatch?op=GetDxTcHierarchy&type=D"/>','Diagpage','width=700,height=350,top=160,left=230,scrollbars=yes');">