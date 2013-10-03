<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
  
<html>
<head>
<title>
  Copy Attribute Details
</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/derivation.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">

  var popupInput = window.dialogArguments;
  var sampleId = popupInput.sampleId;
  var candidateAttributes = popupInput.attributes;

  function initPage() {
    self.focus();
  }

  function isAttributeSelected() {
    var returnValue = false;
    for (var i=0; i<candidateAttributes.length; i++) {
      var checkboxName = candidateAttributes[i].getCui();
      if (document.forms[0][checkboxName].checked) {
        returnValue = true;
      }
    }
    if (!returnValue) {
      alert('Please select at least one attribute to copy.');
    }
    return returnValue;
  }

  function isCopyToSelected() {
    var returnValue = true;
    var value = getControlValue(getFirstWithName(document.forms[0].elements, 'copyTo'));
    if (isEmpty(value)) {
      alert('Please specify the child samples to which the attribute(s) should be copied.');
      returnValue = false;
    }
    //if the choice is "Next N samples", make sure the user specified a valid number for N
    else {
      if (COPY_TO_CHOICE_NEXT_N == value) {
        var numberOfRows = document.forms[0]["numberOfRows"].value;
        if (isEmpty(numberOfRows)) {
          alert('Please enter the number of child samples to which the attribute(s) should be copied.');
          returnValue = false;
        }
        else if (!numberOfRows.match(/^\d+$/) || numberOfRows <= 0) {
          alert('Please enter an integer value greater than 0 for the number of child samples to which the attribute(s) should be copied.');
          returnValue = false;
        }
      }
    }
    return returnValue;
  }

  function onSubmitClick() {
    var attributeSelected = isAttributeSelected();
    var copyToSelected = isCopyToSelected();
    var dataIsValid = attributeSelected && copyToSelected;
    if (!dataIsValid) {
      return;
    }
    else {
      var popupOutput = new Object();
      popupOutput.selectedCopyTo = getControlValue(getFirstWithName(document.forms[0].elements, 'copyTo'));
      popupOutput.selectedCopyToSize = document.forms[0]["numberOfRows"].value;
      var selectedAttributes = new Array();
      var selectedAttributeCount = 0;
      popupOutput.selectedAttributes = selectedAttributes;
      for (var i=0; i<candidateAttributes.length; i++) {
        var checkboxName = candidateAttributes[i].getCui();
        if (document.forms[0][checkboxName].checked) {
          <%
          // To get around a "cannot execute freed script" error, return just the cui (not 
          // an Attribute object)
          %>          
          selectedAttributes[selectedAttributeCount] = candidateAttributes[i].getCui();
          selectedAttributeCount = selectedAttributeCount + 1;
        }
      }
      window.returnValue = popupOutput;
      window.close();
    }
  }

  function onSelectAllClick() {
    for (var i=0; i<candidateAttributes.length; i++) {
      var checkboxName = candidateAttributes[i].getCui();
      document.forms[0][checkboxName].checked = true;
    }
  }
  
  function onCopyToSelection() {
    var value = getControlValue(getFirstWithName(document.forms[0].elements, 'copyTo'));
    document.forms[0]["numberOfRows"].disabled = (COPY_TO_CHOICE_NEXT_N != value);
  }

	//prevent form submission if the enter key is pressed
  function checkEnter(event) {
  	var code = 0;
  	code = event.keyCode;
  	
  	if (code == 13) {
  		return false;
      }
  }
  
</script>

</head>
<br>
<center><h4>Copy Attributes from <script>document.write(sampleId);</script></h4></center>
<body class="bigr" onLoad="initPage();">
<form>
  <div align="center">
    <br>
    <table width="80%" border="0" cellspacing="0" cellpadding="2" class="foreground">
      <tr>
        <td align="left">
			    <table border="0" cellspacing="0" cellpadding="2" class="foreground">
			      <tr>
			        <td>
			          <b>Attributes to copy</b>
			        </td>
			      </tr>
			        <script>
			          for (var i=0; i<candidateAttributes.length; i++) {
			            var checkboxName = candidateAttributes[i].getCui();
			            document.write("<tr>");
			            document.write("  <td>");
			            document.write("    <input type=\"checkbox\" name=\"");
			            document.write(checkboxName);
			            document.write("\" value=\"");
			            document.write(candidateAttributes[i].getCui());
			            document.write("\">");
			            document.write(candidateAttributes[i].getCuiDescription());
			            document.write("  </td>");
			            document.write("</tr>");
			          }
			        </script>
			      <tr>
			        <td>
			          &nbsp;
			        </td>
			      </tr>
			      <tr>
			        <td align="left">
							  <html:button property="btnSelectAll" styleClass="libraryButtons" 
							     onclick='onSelectAllClick();'>
								  Select All
							  </html:button>
			        </td>
			      </tr>
			    </table>
        </td>
        <td align="right" valign="top">
			    <table border="0" cellspacing="0" cellpadding="2" class="foreground">
			      <tr>
			        <td>
			          <b>Apply copy to</b>
			        </td>
			      </tr>
			      <tr>
			        <td align="left">
							  <input type="radio" onClick="onCopyToSelection();" name="copyTo" value="All">All samples in the derivation
			        </td>
			      </tr>
			      <tr>
			        <td align="left">
							  <input type="radio" onClick="onCopyToSelection();" name="copyTo" checked value="AllAfterSource">All samples in the derivation after the source
			        </td>
			      </tr>
			      <tr>
			        <td align="left">
							  <input type="radio" onClick="onCopyToSelection();" name="copyTo" value="NextN">Next &nbsp;
							  <input type="text" name="numberOfRows" size=2 maxlength=3 onkeypress="return checkEnter(event);" disabled> &nbsp; samples in the derivation after the source
			        </td>
			      </tr>
			    </table>
        </td>
      </tr>
    </table>
	  <br>
	  <br>
	  <b>Note:  Any existing values for selected attribute(s) will be overwritten.</b>
	  <br>
	  <br>
	  <br>
			<input type="button" name="btnSubmit" style="width:110" value="Ok" onClick="onSubmitClick();">&nbsp;&nbsp;&nbsp;
		  <input type="button" name="btnCancel" style="width:110" value="Cancel" onClick="window.close()">
  </div>
</form>
</body>
</html>

