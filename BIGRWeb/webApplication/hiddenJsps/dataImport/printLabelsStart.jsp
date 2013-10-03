<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.configuration.SystemConfiguration" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValue" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%@ page import="java.util.Iterator" %>
<bean:define name="printLabelsForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.PrintLabelsForm"/>
<html>
<head>
<title>Print Labels</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/BigrLib.js"/>"></script>
<script type="text/javascript">

function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'Print Labels';
  }
  updateObjectTypeChoices();
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnContinue.disabled = isDisabled;
}

function updateObjectTypeChoices() {
  //clear the existing object type choices
  for(var i=document.forms[0].objectType.length; i>0; i--)
  {
    document.forms[0].objectType.remove(i);    
  }
  var value = document.forms[0].action.value;
  if ('<%=Constants.LABEL_PRINTING_ACTION_PREPRINT%>' == value) {
    document.forms[0].objectType.options[1]=new Option('<%=Escaper.jsEscapeInScriptTag(ApiFunctions.capitalize(Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE))%>', '<%=Escaper.jsEscapeInScriptTag(Constants.LABEL_PRINTING_OBJECT_TYPE_SAMPLE)%>');
  }
  else {
<%
    LegalValueSet objectTypes = myForm.getObjectTypeChoices();
  	Iterator objectTypeIterator = objectTypes.getIterator();
		int index = 1;
  	while (objectTypeIterator.hasNext()) {
  	  LegalValue objectType = (LegalValue)objectTypeIterator.next();
%>
      document.forms[0].objectType.options[<%=index%>]=new Option('<%=Escaper.jsEscapeInScriptTag(objectType.getDisplayValue())%>', '<%=Escaper.jsEscapeInScriptTag(objectType.getValue())%>');
<%
			index++;
  	}
%>
  }
  //if there is only one choice (other than "Select") in the drop-down, select it
  selectSingleDropDownChoice(document.forms[0].objectType, 2);
}

</script>
</head>
<body onload="initPage();">
<html:form method="POST" action="/dataImport/printLabelsDetails" onsubmit="return(onFormSubmit());">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <div align="white">
	        <font color="#FF0000"><b><html:errors/></b></font>
	      </div>
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
  <br>
  
  <table border="0" cellspacing="1" cellpadding="3" class="foreground">
		<tr class="white">
			<td align="right" valign="center">
		  	Specify the action you wish to take:
		  </td>
		  <td align="left" valign="center">
				<bigr:selectList name="printLabelsForm" property="action"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSetProperty="actionChoices"
  				onchange="updateObjectTypeChoices();"/>
		  </td>
		</tr>
		<tr class="white">
			<td align="right" valign="center">
		  	Specify the type of object for which to print labels:
		  </td>
		  <td align="left" valign="center">
				<bigr:selectList name="printLabelsForm" property="objectType"
  				firstValue="" firstDisplayValue="Select"
  				legalValueSetProperty="objectTypeChoices"/>
		  </td>
		</tr>
	</table>  
	<br>
	<table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
	    <tr class="white"> 
	      <td align="center">
	          <html:submit property="btnContinue" value="Continue"/>
	      </td>
	    </tr>
	</table>
</html:form>
</body>

</html>