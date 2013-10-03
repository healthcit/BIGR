<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%@ page import="com.ardais.bigr.iltds.helpers.TypeFinder" %>
<%@ page import="com.ardais.bigr.kc.form.def.BigrFormDefinition" %>
<%@ page import="com.ardais.bigr.kc.web.form.FormInstanceForm" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<%
// The form definition must be specified, so if it is not throw an exception.
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormDefinition formDefinition = formContext.getDataFormDefinition();
if (formDefinition == null) {
 	throw new JspException("FormDefinition not stored in form context.");
}

//The BigrFormDefinition must be in the request, so if it is not throw an exception
BigrFormDefinition bigrFormDefinition = (BigrFormDefinition)request.getAttribute(FormInstanceForm.REQUEST_ATTRIBUTE_BIGR_FORM_DEFINITION);
if (bigrFormDefinition == null) {
throw new JspException("BigrFormDefinition not stored in request.");
}

//determine where the header link should go
String objectType = bigrFormDefinition.getObjectType();
String bigrId = formContext.getDomainObjectId();
String url = request.getContextPath();
if (TypeFinder.DONOR.equalsIgnoreCase(objectType)) {
url = url + "/ddc/Dispatch?op=DonorProfileSummaryPrepare&ardaisId=" + bigrId + "&readOnly=true";
}
else if (TypeFinder.CASE.equalsIgnoreCase(objectType)) {
url = url + "/dataImport/getCaseFormSummary.do?consentId=" + bigrId + "&readOnly=true";
}
else if (TypeFinder.SAMPLE.equalsIgnoreCase(objectType)) {
url = url + "/dataImport/getSampleFormSummary.do?sampleData.sampleId=" + bigrId + "&readOnly=true";
}
else {
throw new JspException("Unknown object type encountered on BigrFormDefinition (" + objectType + ").");
}
objectType = objectType.substring(0,1).toUpperCase() + objectType.substring(1).toLowerCase();
%>
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>Summary of <%=objectType%> Information Form</title>
	<jsp:include page="/hiddenJsps/kc/data/misc/scriptAndCss.jsp" flush="true"/>
	<link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/bigr.css"/>'>
  <script language="JavaScript" src="<html:rewrite page="/js/bigrkc.js"/>"></script>
	<script language="JavaScript" >
  function initPage() {
  	if (parent.topFrame != null) {
	    parent.topFrame.document.all.banner.innerHTML = 'Summary of <%=objectType%> Information Form';
		}		
    //positionItems();
    GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
  }
	</script>
<style type="text/css">
.kcElements {
  margin: 0;
  padding: 0;
  font-family: tahoma,verdana,arial,helvetica,sans-serif;
  font-size: 8pt;
  width:99%;
  overflow: auto; 
}
.buttons {
  font-family: tahoma,verdana,arial,helvetica,sans-serif;
  font-size: 9pt;
  border: 1px solid #000000;
}
</style>
  </head>
  <body class="bigr" onload="initPage();" style="margin-top: 0; margin-bottom: 0;">
		<div align="center">
      <table class="background" cellpadding="0" cellspacing="0" border="0">
        <tr><td> 
	        <table class="foreground" border="0" cellpadding="3" cellspacing="1">
	          <tr class="white"> 
	            <td class="yellow" align="right"><b><%=objectType %></b></td>
	            <td>
	            	<a href="<%=url%>"><%=bigrId%></a>
	            </td>
	            <td class="yellow" align="right"><b>Form</b></td>
	            <td>
	            	<%=Escaper.htmlEscape(formDefinition.getName())%>
	            </td>
	          </tr>
	        </table>
        </td></tr>
      </table>
    </div>

	<form name="formInstance">
	  <jsp:include page="/hiddenJsps/kc/data/formInstanceSummary.jsp" flush="true"/>
    <div id="div.buttons" align="center" style="padding: 0.5em;">
      <input type="button" value="Back" class="buttons" onclick="window.location.href='<%=url%>';">
    </div>
  </form>
  </body>
</html> 