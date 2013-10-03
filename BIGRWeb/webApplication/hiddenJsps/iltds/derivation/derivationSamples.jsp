<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.configuration.SystemConfiguration"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define id="myForm" name="derivationBatchForm" type="com.ardais.bigr.iltds.web.form.DerivationBatchForm"/>

<html>
<head>
<title>Operation Samples</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/calendar.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/derivation.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/prototype.js"/>"></script>
<script language="JavaScript">

  var derivationBatch = new DerivationBatch(); 
  derivationBatch.setNumberOfDerivations(0); 
  derivationBatch.setTableId("derivationBatchTable");
<% 
boolean allowIdGeneration = !ApiFunctions.isEmpty(myForm.getLabelPrintingData());
boolean generateChildIds = myForm.getDto().isGenerateChildIds();

boolean allowMultipleParents = 
	SystemConfiguration.getDerivativeOperation(myForm.getDto().getOperationCui()).isMultipleParents();
if (allowMultipleParents) {
%>
  derivationBatch.setMultipleParentsAllowed(true);
<%
}
else {
%>
  derivationBatch.setMultipleParentsAllowed(false);
<%
}
%>
  
  var derivation;

function initPage() {
  if (parent.topFrame) {
    //This is not working in safari and firefox, EZ
    //parent.topFrame.document.all.banner.innerHTML = "Derivative Operations";
    parent.topFrame.document.getElementById("banner").innerHTML = "Derivative Operations";
  }
  updateGenerateChildIds();
}

function startOver() {
  setActionButtonEnabling(false);
  if (!handleStartOverRequest()) {
    setActionButtonEnabling(true);
  }
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnStartOver.disabled = isDisabled;
  f.btnNext.disabled = isDisabled;
}

function validate() {
  setActionButtonEnabling(false);
  return true;
}

function getChildIdsStyle() {
	var a = document.getElementsByName("dto.childIdsStyle");
	for (var i = 0, n = a.length; i < n; i++) {
	  if (a[i].checked) {
	    return a[i].value;
	  }
	}
  return null;
}

function updateGenerateChildIds() {
  var isGen = ("generated" == getChildIdsStyle());
  if (isGen) {
    $('childIdsLabel').innerHTML = 'Number of Derivatives';
    derivationBatch.setDerivativeGenerated(true);
    derivationBatch.showDerivativeCounts();
  }
  else {
    $('childIdsLabel').innerHTML = 'Derivative Sample Id(s) or Alias(es)';
    derivationBatch.setDerivativeGenerated(false);
    derivationBatch.showDerivativeBarcodes();
  }
}
</script>
</head>

<body class="bigr" onLoad="initPage();">
<html:form action="/iltds/derivation/derivationBatchLookup.do"  onsubmit="return(validate());">
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
    <br/>
    <br/>

<html:hidden name="derivationBatchForm" property="dto.operationCui"/>
<html:hidden name="derivationBatchForm" property="dto.otherOperation"/>

<%
// The batch information (operation, date, prepared by...)
%>
	<table border="0" class="foreground" cellpadding="3" cellspacing="0">
	  <tr>
	    <td align="left">
	      Operation: 
	    </td>
	    <td align="left">
	      <bean:write name="derivationBatchForm" property="operation"/>
	    </td>
	  </tr>
	  <tr>
	    <td align="left">
	      Date of operation: 
	    </td>
	    <td align="left">
	      <html:text name="derivationBatchForm" property="dto.operationDateAsString" readonly="true"/>&nbsp;
	      <span class="fakeLink" onclick="openCalendar('dto.operationDateAsString')">Select Date</span>              
	      &nbsp;&nbsp;
	      <span class="fakeLink" onclick="document.all['dto.operationDateAsString'].value = ''">Clear Date</span>              
	    </td>
	  </tr>
	  <tr>
	    <td align="left">
	      Prepared by: 
	    </td>
	    <td align="left">
	      <bigr:selectList name="derivationBatchForm" property="dto.preparedBy"
	        firstValue="" firstDisplayValue="Select"
	        legalValueSetProperty="preparedByList"/>
	    </td>
	  </tr>
	</table>

 	<hr style="border: 2px solid black;">

<%
if (allowIdGeneration) {
%>
 	<p>For each derivative operation, scan the parent sample id(s) or alias(es) and then either:
 	<br>
	&nbsp;<html:radio name="derivationBatchForm" property="dto.childIdsStyle" value="scanned" onclick="updateGenerateChildIds();"/>
  scan the derivative sample id(s) or alias(es), or 
	&nbsp;<html:radio name="derivationBatchForm" property="dto.childIdsStyle" value="generated" onclick="updateGenerateChildIds();"/>
	generate the derivative sample id(s)
  </p>
<%
}
else {
%>
 	<p>For each derivative operation, scan the parent sample id(s) or alias(es) and then scan 
 	the derivative sample id(s) or alias(es).
<%
}
%>

  <table id="derivationBatchTable" border="0" class="foreground" cellpadding="3" cellspacing="0">
    <tr class="white"> 
      <td></td>
      <td align="left" nowrap>Parent Id(s) or Alias(es)</td>
      <td></td>
      <td id="childIdsLabel" align="left" nowrap>
      <%=generateChildIds ? "Number of Derivatives" : "Derivative Sample Id(s) or Alias(es)"%>
      </td>
    </tr>
<%
int nextDerivations = 0; 
%>
		<logic:iterate name="derivationBatchForm" property="dto.derivations" indexId="derivationCount" id="derivation">
<%
int nextParent = 0;
int nextDerivative = 0;
%>

<script language="JavaScript">
  derivation = new Derivation(); 
  derivation.setIndex(<%=derivationCount%>);
  derivation.setParentGroupTdId('<%="parentGroup" + derivationCount%>');  
  derivation.setDerivativeGroupTdId('<%="derivativeGroup" + derivationCount%>');  
</script>

    <tr class="white"> 
			<td><%=String.valueOf(nextDerivations+1)%></td>
			<td id='<%="parentGroup" + derivationCount%>' align="left" nowrap>
				<logic:iterate name="derivation" property="parents" indexId="parentCount" id="parent">
					<script language="JavaScript">
					  derivation.incrementNumberOfParents();  
					</script>
	  			<html:text name="derivationBatchForm" 
	  		  	property='<%="dto.derivation[" + derivationCount + "].parent[" + parentCount + "].sampleId"%>' size="20" maxlength="30"/>
<% 
nextParent = parentCount.intValue() + 1;
%>
				</logic:iterate>
<%
// Only show the next blank parent input if multiple parents are allowed, or
// the next parent index is 0, meaning that there were no parents already 
// defined so we need to show at least one blank parent input.
if ((allowMultipleParents) || (nextParent == 0)) {
%>
				<script language="JavaScript">
				  derivation.incrementNumberOfParents();  
				</script>
  			<input type="text" name='<%="dto.derivation[" + derivationCount + "].parent[" + nextParent + "].sampleId"%>'
  				size="20" maxlength="30"
  				onfocus='<%="derivationBatch.handleEvent(" + derivationCount + ", true, " + nextParent + ");"%>'>
<%
}
%>
			</td>
      <td nowrap><img src="<html:rewrite page="/images/derivarrow.gif"/>"></td>
			<td id='<%="derivativeGroup" + derivationCount%>' align="left" nowrap>
			<span <%=generateChildIds ? "style=\"display:none;\"" : ""%>>
				<logic:iterate name="derivation" property="children" indexId="derivativeCount" id="derivative">
					<script language="JavaScript">
  					derivation.incrementNumberOfDerivatives();  
					</script>
      		<html:text name="derivationBatchForm" 
      		  property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].sampleId"%>' size="20" maxlength="30">
      		</html:text>
					<html:hidden name="derivationBatchForm" 
      		  property='<%="dto.derivation[" + derivationCount + "].child[" + derivativeCount + "].generatedSampleId"%>'>
      		</html:hidden>
<%
nextDerivative = derivativeCount.intValue() + 1;
%>
				</logic:iterate>
				<script language="JavaScript">
				  derivation.incrementNumberOfDerivatives();  
				</script>
	  		<input type="text" name='<%="dto.derivation[" + derivationCount + "].child[" + nextDerivative + "].sampleId"%>'
  				size="20" maxlength="30"
  				onfocus='<%="derivationBatch.handleEvent(" + derivationCount + ", false, " + nextDerivative + ");"%>'>
			</span>
			<span <%=generateChildIds ? "" : "style=\"display:none;\""%>>
				<html:text size="3" name="derivationBatchForm"
					property='<%="dto.derivation[" + derivationCount + "].generatedSampleCount"%>'>
				</html:text>
			</span>
			</td>
    </tr>
		<script language="JavaScript">
		  derivationBatch.setDerivation(derivation);  
		</script>
<%
nextDerivations = derivationCount.intValue() + 1;
%>
		</logic:iterate>
<script language="JavaScript">
  derivation= new Derivation(); 
  derivation.setIndex(<%=nextDerivations%>);
  derivation.setParentGroupTdId('<%="parentGroup" + nextDerivations%>');  
  derivation.setDerivativeGroupTdId('<%="derivativeGroup" + nextDerivations%>');  
</script>

    <tr class="white"> 
			<td><%=String.valueOf(nextDerivations+1)%></td>
			<td id='<%="parentGroup" + nextDerivations%>' align="left" nowrap>
	  		<input type="text" name='<%="dto.derivation[" + nextDerivations + "].parent[0].sampleId"%>'
	  			size="20" maxlength="30"
	  			onfocus='<%="derivationBatch.handleEvent(" + nextDerivations + ", true, 0);"%>'>
			</td>
      <td nowrap><img src="<html:rewrite page="/images/derivarrow.gif"/>"></td>
			<td align="left" id='<%="derivativeGroup" + nextDerivations%>' align="left" nowrap>
			<span <%=generateChildIds ? "style=\"display:none;\"" : ""%>>
	  		<input type="text" name='<%="dto.derivation[" + nextDerivations + "].child[0].sampleId"%>'
	  			size="20" maxlength="30"
	  			onfocus='<%="derivationBatch.handleEvent(" + nextDerivations + ", false, 0);"%>'>
			</span>
			<span <%=generateChildIds ? "" : "style=\"display:none;\""%>>
				<input type="text" size="3" 
					name='<%="dto.derivation[" + nextDerivations + "].generatedSampleCount"%>'
	  			onfocus='<%="derivationBatch.handleEvent(" + nextDerivations + ", false, 0);"%>'/>
			</span>
			</td>
    </tr>
<script language="JavaScript">
  derivation.incrementNumberOfParents();  
  derivation.incrementNumberOfDerivatives();  
  derivationBatch.setDerivation(derivation);  
</script>

	</table>

<%
// Buttons.
%>
 	<hr style="border: 2px solid black;">
  <table border="0" class="foreground" cellpadding="3" cellspacing="0" width="100%">
    <tr class="white"> 
      <td align="left">
        <input type="button" name="btnStartOver" value="Start Over" onclick="startOver();"/>
      </td>
      <td align="right">
        <input type="submit" name="btnNext" value="Next >"/> 
      </td>
    </tr>
  </table>
</html:form>
</body>
</html>