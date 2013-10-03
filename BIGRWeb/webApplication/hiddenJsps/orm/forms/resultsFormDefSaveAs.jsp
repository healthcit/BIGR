<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.kc.form.def.BigrFormDefinition"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define id="myForm" name="resultsFormDefinitionForm" type="com.ardais.bigr.kc.web.form.def.ResultsFormDefinitionForm"/>

<%
  //if the system default is the results form being viewed, don't provide any default name 
  String existingName;
	if (Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(myForm.getFormDefinition().getFormDefinitionId())) {
	  existingName = "";
	}
	else {
	  existingName = myForm.getFormDefinition().getName();
	}
	//get any existing results forms (exluding the system default)
	List existingResultsForms = myForm.getResultsFormDefinitions();
  List nonDefaultResultsForms = new ArrayList();
  Iterator formIterator = existingResultsForms.iterator();
  while (formIterator.hasNext()) {
    BigrFormDefinition resultsForm = (BigrFormDefinition)formIterator.next();
    if (!Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(resultsForm.getFormDefinitionId())) {
      nonDefaultResultsForms.add(resultsForm);
    }
  }
  %>
  
<html>
<head>
<title>
  Save View As
</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>

  //method to validate that all necessary data and checks are done before
  //the form is closed
  function onSubmitClick() {
    document.forms[0].formName.value = trim(document.forms[0].formName.value);
    if (!isNameSpecified() || !isOverwriteConfirmedIfNecessary()) {
      return;
    }
    else {
      window.returnValue = document.forms[0].formName.value;
      window.close();
    }
  }

  function isNameSpecified() {
    var returnValue = false;
    var name = document.forms[0].formName.value;
    if (isEmpty(name)) {
      alert('Please enter a name for the form.');
      document.forms[0].formName.focus();
      returnValue = false;
    }
    else {
      returnValue = true;
    }
    return returnValue;
  }

  function isOverwriteConfirmedIfNecessary() {
    var returnValue = false;
    var origName = document.forms[0].formName.value;
    var name = origName.toUpperCase();
    if (!isEmpty(name)) {
      //if the specified name is the same as the original name, no need to
      //confirm an overwrite
      if (name == '<%= Escaper.jsEscapeInScriptTag(existingName.toUpperCase()) %>') {
        returnValue = true;
      }
      else {
        <%
          if (nonDefaultResultsForms.isEmpty()) {
        %>
          return true;
        <%
          }
          else {
        %>
        //if the specified name is the same as any of the existing names then
        //confirm the overwrite
        <%
            String ifOrElseIf = "if";
            formIterator = nonDefaultResultsForms.iterator();
            while (formIterator.hasNext()) {
              BigrFormDefinition resultsForm = (BigrFormDefinition)formIterator.next();
              String name = resultsForm.getName().toUpperCase();
        %>
        <%= ifOrElseIf %> (name == '<%= Escaper.jsEscapeInScriptTag(name) %>') {
          returnValue = confirm('You are about to overwrite form ' + origName + ' - press Ok to continue or Cancel to choose a different name.');
        }
        <%
        		  ifOrElseIf = "else if";
            }
        %>
         //if the specified name is not the same as any of the existing names then
         //there is no overwrite
         else {
           returnValue = true;
         }
        <%
          }
        %>
      }
    }
    else {
      returnValue = true;
    }
    return returnValue;
  }

function initPage() {
  self.focus();
  document.forms[0].formName.focus();
}
  
</script>

</head>
<center><h4>Save View As</h4></center>
<body class="bigr" onLoad="initPage();">
<form onSubmit="onSubmitClick(); return(false);">
  <div align="center">
    <br>
    <table border="0" cellspacing="0" cellpadding="2" class="foreground">
      <tr>
        <td align="right">
          Name:
        </td>
        <td>
          <input type="text" name="formName"  maxlength="256" size="50" value="<%=Escaper.htmlEscape(existingName)%>">
        </td>
      </tr>
    </table>
      <%
        if (!nonDefaultResultsForms.isEmpty()) {
      %>
    <br><br>
    <table>
      <tr>
        <td align="left">
          Existing forms (click a link to select that name):
        </td>
      </tr>
      <%
          formIterator = nonDefaultResultsForms.iterator();
          while (formIterator.hasNext()) {
            BigrFormDefinition resultsForm = (BigrFormDefinition)formIterator.next();
            String name = resultsForm.getName();
      %>
      <tr>
        <td align="left">
			    <span class="fakeLink" onclick="document.forms[0].formName.value='<%=Escaper.jsEscapeInXMLAttr(name)%>'"><%=name%></span>
				</td>
			</tr>
		  <%
          }
      %>
    </table>
      <%
        }
      %>
	  <br>
	  <br>
	  <br>
			<input type="button" name="btnSubmit" style="width:110" value="Ok" onClick="onSubmitClick();">&nbsp;&nbsp;&nbsp;
		  <input type="button" name="btnCancel" style="width:110" value="Cancel" onClick="window.close()">
  </div>
</form>
</body>
</html>

