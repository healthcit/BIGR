

<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>

<bean:define id="fieldId"><%=request.getParameter("fieldId") %></bean:define>
  
<logic:equal name="fieldId" value="libraryTissueOrigin"> 
 <logic:iterate name="queryForm" property="tissue.tissueOriginLabel" id="label">
	<li><bean:write name="label"/></li>
 </logic:iterate>
</logic:equal>

<logic:equal name="fieldId" value="libraryTissueFinding">  
 <logic:iterate name="queryForm" property="tissue.tissueFindingLabel" id="label">
	<li><bean:write name="label"/></li>
 </logic:iterate>
</logic:equal> 

<logic:equal name="fieldId" value="libraryCaseDiagnosis"> 
  <logic:iterate name="queryForm" property="diagnosis.caseDiagnosisLabel" id="label">
	                <li><bean:write name="label"/></li>
  </logic:iterate>
</logic:equal>  