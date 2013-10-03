<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%@ page import="com.ardais.bigr.iltds.helpers.TypeFinder" %>
<%@ page import="com.ardais.bigr.kc.form.def.BigrFormDefinition" %>
<%@ page import="com.ardais.bigr.kc.web.form.FormInstanceForm" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ taglib uri="/kctaglib" prefix="kc" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<%
// The form definition must be specified, so if it is not throw an exception.
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormDefinition formDefinition = formContext.getDataFormDefinition();
if (formDefinition == null) {
 	throw new JspException("FormDefinition not stored in form context.");
}

// The BigrFormDefinition must be in the request, so if it is not throw an exception
BigrFormDefinition bigrFormDefinition = (BigrFormDefinition)request.getAttribute(FormInstanceForm.REQUEST_ATTRIBUTE_BIGR_FORM_DEFINITION);
if (bigrFormDefinition == null) {
 	throw new JspException("BigrFormDefinition not stored in request.");
}

//determine where the header link should go
String objectType = bigrFormDefinition.getObjectType();
String bigrId = formContext.getDomainObjectId();
String url = request.getContextPath();
if (TypeFinder.DONOR.equalsIgnoreCase(objectType)) {
  url = url + "/ddc/Dispatch?op=DonorProfileSummaryPrepare&ardaisId=" + bigrId;
}
else if (TypeFinder.CASE.equalsIgnoreCase(objectType)) {
  url = url + "/dataImport/getCaseFormSummary.do?consentId=" + bigrId;  
}
else if (TypeFinder.SAMPLE.equalsIgnoreCase(objectType)) {
  url = url + "/dataImport/getSampleFormSummary.do?sampleData.sampleId=" + bigrId;  
}
else {
 	throw new JspException("Unknown object type encountered on BigrFormDefinition (" + objectType + ").");
}
objectType = objectType.substring(0,1).toUpperCase() + objectType.substring(1).toLowerCase();
%>
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>Create <%=objectType%> Information Form</title>
	<jsp:include page="/hiddenJsps/kc/data/misc/scriptAndCss.jsp" flush="true"/>
  <script language="JavaScript" src="<html:rewrite page="/js/bigrkc.js"/>"></script>
  <script type="text/javaScript" src="<html:rewrite page="/hiddenJsps/kc/detViewer/js/scriptaculous.js?load=builder"/>"></script>
  <link rel="stylesheet" type="text/css" href='<html:rewrite page="/js/lightbox/gsbioLightbox.css"/>'>
  <script type="text/javaScript" src="<html:rewrite page="/js/lightbox/gsbioLightbox.js"/>"></script>
  <script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>

	<script language="JavaScript" >
	function initPage() {
  	if (parent.topFrame != null) {
	    parent.topFrame.document.all.banner.innerHTML = 'Create <%=objectType%> Information Form';
		}		
//    positionItems();
    GSBIO.kc.data.FormInstances.getFormInstance().alternateRowColor();
  }
	</script>
	<link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/bigr.css"/>'>
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
  <body class="bigr" onload="initPage();" onbeforeunload="confirmNavigate();" style="margin-top: 0; margin-bottom: 0;">

		<div align="center">
      <table class="background" cellpadding="0" cellspacing="0" border="0">
        <tr><td> 
	        <table class="foreground" border="0" cellpadding="3" cellspacing="1">
	          <tr class="white"> 
	            <td class="yellow" align="right"><b><%=objectType%></b></td>
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

		<%
		  // DIV for errors
		%>
	  <div id="errorDiv" align="center">
		  <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
		    <logic:present name="org.apache.struts.action.ERROR">
		      <tr class="yellow"> 
		  	    <td> 
			       <font color="#FF0000"><b><html:errors/></b></font>
			      </td>
	  		  </tr>
			  </logic:present>
		    <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
			  <tr class="white"> 
			    <td> 
			      <div align="left">
			        <font color="#000000"><b><bigr:btxActionMessages/></b></font>
			      </div>
			    </td>
			  </tr>
			  </logic:present>
			</table>
		</div>
	<form name="formInstance" method="POST" action="<html:rewrite page='/kc/form/create.do'/>">
    <input type="hidden" name="form" value=""/>
	  <jsp:include page="/hiddenJsps/kc/data/formInstanceEdit.jsp" flush="true"/>
    <div id="div.buttons" style="overflow: hidden; width:100%; padding: 0.5em;">
    	<input type="button" name="saveButton" value="Save" class="buttons" onclick="handleSave();"/>
    	<input type="button" name="cancelButton" value="Cancel" class="buttons" onclick="handleCancel('<%=url%>');"/>
    </div>		
	</form>
  </body>
</html>