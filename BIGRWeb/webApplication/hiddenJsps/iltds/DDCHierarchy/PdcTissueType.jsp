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
String temp = request.getParameter("TissueType");

String organ = (String) request.getAttribute("organ");
if(temp == null){
} else if (temp.equals("")){
} else organ = temp;
//if(temp != null || !temp.equals("")){
//	organ = temp;
//}
if(organ == null) organ = "";
%>
 <select name="TissueType" onPropertyChange="showOther();">
 <option value="" <%if(organ.equals(""))out.print("selected");%>>Select</option>
 <%for (int i=0; i<values.size(); i++){ 
 %>

        <% GbossValue value = (GbossValue) values.get(i); %>
        <option value="<%=value.getCui()%>" <%if (organ.equals(value.getCui())) out.print("selected");%>><%=value.getDescription()%></option>
                 
        <% }%>
</select>
        
<input type='hidden' name="compName" value="Final_Pathology_Diagnosis">
<input type="Button" name="Open" value="..." onClick="window.open('<html:rewrite page="/orm/Dispatch?op=GetDxTcHierarchy&type=T"/>','Diagpage','width=400,height=350,top=160,left=230,scrollbars=yes');">