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
GbossValueSet valueSet = BigrGbossData.getValueSetAlphabetized(ArtsConstants.VALUE_SET_TISSUE_HIERARCHY);
List values = valueSet.getValues();
%>
 <select name="TissueType" onChange="showOther2()">
  <% 
  String organ = (String)request.getAttribute("organ");
  if(organ != null && organ.equals("Not available")) { %>
  <option value="">Select</option>
  <% } else { %>
  <option value="<%= organ %>"><%= organ %></option>
  <% } %>
 <%for (int i=0; i<values.size(); i++){ 
 %>

        <% GbossValue value = (GbossValue) values.get(i); %>
        <option value="<%=value.getDescription()%>" selected><%=value.getDescription()%></option>
                 
        <% }%>
</select>
        
<input type='hidden' name="compName" value="Final_Pathology_Diagnosis">
<input type="Button" name="Open" value="..." onClick="window.open('<html:rewrite page="/orm/Dispatch?op=GetLIMSDxTcHierarchy&type=T"/>','Diagpage','width=200,height=350,top=160,left=230,scrollbars=yes');">