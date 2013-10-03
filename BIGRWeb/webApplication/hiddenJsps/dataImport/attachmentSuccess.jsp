
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<TITLE></TITLE>

</HEAD>

<logic:equal name="consent" value="Yes">
 <bean:define id="transitionURL"><%=request.getContextPath()%>/dataImport/getCaseFormSummary.do?consentId=<%=request.getParameter("consentId") %></bean:define>
</logic:equal> 
<logic:equal name="sample" value="Yes">
 <bean:define id="transitionURL"><%=request.getContextPath()%>/dataImport/getSampleFormSummary.do?sampleData.sampleId=<%=request.getParameter("sampleData.sampleId") %></bean:define>
</logic:equal> 

 
<BODY >

 
<script  TYPE="text/javascript" LANGUAGE="JavaScript">

   window.location='<bean:write name="transitionURL" />';
</script>


<P>Attach successfully, <A href='<bean:write name="transitionURL" />' />Go back</A></P>
</BODY>
</html:html>
