<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.iltds.helpers.IltdsUtils" %>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>
<title>Operation Samples</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/calendar.js"/>"></script>
<script language="JavaScript">

function initPage() {
  if (parent.topFrame) {
    //parent.topFrame.document.all.banner.innerHTML = "Derivative Operations";
    parent.topFrame.document.getElementById("banner").innerHTML = "Derivative Operations";
  }
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnNext.disabled = isDisabled;
}

function validate() {
  setActionButtonEnabling(false);
  return true;
}

function checkEnter(event) {
	var code = 0;
	code = event.keyCode;
	
	if (code == 13) {
		return false;
  }
}

</script>
</head>

<body class="bigr" onLoad="initPage();">
<html:form action="/iltds/derivation/derivationBatchSelect.do"  onsubmit="return(validate());">
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
    <%
    	StringBuffer parents = new StringBuffer(50);
			boolean includeComma = false;
    %>
		<logic:iterate name="derivationBatchForm" property="dto.derivations" indexId="derivationCount" id="derivation">
				<logic:iterate name="derivation" property="parents" indexId="parentCount" id="parent" type="com.ardais.bigr.javabeans.SampleData">
	  			<html:hidden name="derivationBatchForm" 
	  		  	property='<%="dto.derivation[" + derivationCount + "].parent[" + parentCount + "].sampleId"%>'/>
	  		  	<%
	  		  		if (includeComma) {
	  		  		  parents.append(", ");
	  		  		}
	  		  		parents.append(Escaper.htmlEscapeAndPreserveWhitespace(IltdsUtils.getSampleIdAndAlias(parent)));
	  		  		includeComma = true;
	  		  	%>
	  		</logic:iterate>
		</logic:iterate>
  <table border="0" class="foreground" cellpadding="3" cellspacing="0">
    <tr class="white"> 
      <td align="left">Operation: </td>
      <td align="left">
        <bigr:selectListWithOther name="derivationBatchForm" property="dto.operationCui"
          firstValue="" firstDisplayValue="Select an Operation"
          otherProperty="dto.otherOperation"
          legalValueSetProperty="operationChoices"
          otherCode='<%=ArtsConstants.OTHER_DERIVATION_OPERATION%>'/>
      </td>
    </tr>
    <tr class="white"> 
      <td align="left">Other Operation: </td>
      <td align="left">
	      <bigr:otherText
	        name="derivationBatchForm"
					parentProperty="dto.operationCui"
	        property="dto.otherOperation"
	        otherCode='<%=ArtsConstants.OTHER_DERIVATION_OPERATION%>'
	        onkeydown="return checkEnter(event);"
	        size="80"
	        maxlength="200"/>
      </td>
    </tr>
    <tr class="white"> 
      <td align="left">Date of operation: </td>
      <td align="left">
				<html:text name="derivationBatchForm" property="dto.operationDateAsString" readonly="true"/>&nbsp;
				<span class="fakeLink" onclick="openCalendar('dto.operationDateAsString')">Select Date</span>              
				&nbsp;&nbsp;
				<span class="fakeLink" onclick="document.all['dto.operationDateAsString'].value = ''">Clear Date</span>              
		  </td>
    </tr>
    <tr class="white"> 
      <td align="left">Prepared by: </td>
      <td>
        <bigr:selectList name="derivationBatchForm" property="dto.preparedBy"
          firstValue="" firstDisplayValue="Select"
          legalValueSetProperty="preparedByList"/>
      </td>
    </tr>
    <%
    	String parentList = parents.toString();
	  	if (!ApiFunctions.isEmpty(parentList)) {
    %>
      <tr class="white"> 
        <td align="left">
          Parent Samples:
        </td>
        <td align="left">
          <%= parentList %>
        </td>
      </tr>
    <%
	  	}
    %>
    <tr class="white">
      <td colspan="2" align="right">
        <input type="submit" name="btnNext" value="Next >"/> 
      </td>
    </tr>
    
  </table>
</html:form>
</body>
</html>
