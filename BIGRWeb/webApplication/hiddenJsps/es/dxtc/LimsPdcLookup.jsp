<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<html>
<head>
<title><%= request.getParameter("title") %></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<link rel="stylesheet" type="text/css"
	href="<html:rewrite page="/css/bigr.css"/>">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script>
<!--
var menuIds = null;
var checkedArray = new Array();

//function to get all of the selected checkboxes (optionally excluding the 
//summary checkboxes i.e. "Disease of Breast", "Disease of Skin", etc)
function returnSelectedChoices() {
    //validate other value
    var span = document.getElementById('otherSpan');
    if (span != null) {
    	for (var j=0; j<span.children.length; j++) {
		if (span.children[j].type == "checkbox") {
			var box = span.children[j];
			if(box.checked == true){
				if(trim(document.all.otherValue.value) == ""){
					alert("Please enter a value for Other.");
					return false;
				}
			}
		}
		}
	}
    
    
    var returnValues = new Array();
    var count = 0;
    var checkArray = new Array();
    checkArray = document.all.values;
    for (i = 0 ; i < checkArray.length ; i++){
    	var box = checkArray[i];
    	if(box.checked){
    		returnValues[returnValues.length] = trim(box.value);
    	}
    
    }
    window.returnValue = returnValues;
    //alert("Returning array - length = " + returnValues.length + ", values = " + returnValues.toString());
    closeWindow();
}

function clearSelectedChoices() {
	var checkArray = new Array();
    checkArray = document.all.values;
    
    for (i = 0 ; i < checkArray.length ; i++){
    	checkArray[i].checked = false;
    }
}

//return the unchanged argument list if the window is closed
function cancelWindow(){
	closeWindow();
}

function closeWindow(){
  window.close();
}

function changeOtherValue(code, val, text){
	
	var span = document.getElementById('otherSpan');
    if (span != null) {
    	for (var j=0; j<span.children.length; j++) {
		if (span.children[j].type == "checkbox") {
			span.children[j].value = val + '!~!~other!#!#' + code;
			
		}
	}
    }
}

function changeOther(){
	document.all.otherValue.disabled = false;
	document.all.otherValue.value = '';
	document.all.otherValue.focus();

}	

function initCheckboxes(){
	var checkArray = new Array();
	checkArray = document.all.values;
	
	var stringSelectedValues = dialogArguments.toString();
	
	//alert(stringSelectedValues);
	
	for(k = 0 ; k < checkArray.length ; k++){
	     var temp = checkArray[k];
	     var tempArray = temp.value.split('!#!#');
	     if(stringSelectedValues.indexOf(tempArray[1]) != -1){
	     	temp.checked = true;;
	     }
	}
}

function checkEnter(event){ 	
	var code = 0;
	code = event.keyCode;
	
	//alert(code);
  
	  if(code == 13){
 	    return false;
      }
}
</script>
<body onLoad="initCheckboxes()">

<form name="pdcLookup">
<p align="center"><input type="button" name="Continue" value="Continue"
	onClick="returnSelectedChoices();"> <input type="button" name="Clear"
	value="Clear All" onClick="clearSelectedChoices();"> <input
	type="button" name="Cancel" value="Cancel" onClick="cancelWindow()"></p>
<div align="center">
<table class="fgTableSmall">
	<tr class="green">
		<td>&nbsp;</td>
		<td>Select</td>
	</tr>
	<logic:present name="valueSet">
		<logic:iterate name="valueSet" property="iterator" id="legalValue">
			<tr style="display: none;"><td colspan='2'><input type="checkbox" name="values"></td></tr>
			<logic:match name="legalValue" property="displayValue" value="Other"
				location="start">
				<tr>
					<td><bean:write name="legalValue" property="displayValue" /></td>
					<td><span id="otherSpan"> <input
						type="checkbox" name="values"
						value="<bean:write	name="legalValue" property="displayValue" />!#!#<bean:write name="legalValue" property="value" />"
						onclick="changeOther();"> </span></td>
				</tr>
				<tr>
					<td><input type="text" name="otherValue" maxlength="200" value="N\A" disabled
						onKeyDown="return checkEnter(event);" onblur="changeOtherValue('<bean:write name="legalValue" property="value" />', this.value, this);">
					</td>
					<td></td>
				</tr>
			</logic:match>
			<logic:notMatch name="legalValue" property="displayValue"
				value="Other" location="start">
				<tr>
					<td><bean:write name="legalValue" property="displayValue" /></td>
					<td><input type="checkbox" name="values"
						value="<bean:write	name="legalValue" property="displayValue" />!#!#<bean:write	name="legalValue" property="value" />">
					</td>
				</tr>
			</logic:notMatch>
		</logic:iterate>
	</logic:present>
</table>
</div>
<p align="center"><input type="button" name="Continue" value="Continue"
	onClick="returnSelectedChoices();"> <input type="button" name="Clear"
	value="Clear All" onClick="clearSelectedChoices();"> <input
	type="button" name="Cancel" value="Cancel" onClick="cancelWindow()"></p>
</form>
</body>
</html>
