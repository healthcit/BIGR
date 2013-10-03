<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, getServletContext(), "P");
%>

<%
	String formAction = "";
	String sampleAction = request.getParameter("sampleAction");
	if (sampleAction.equalsIgnoreCase("modifySample")) {
		formAction = "/dataImport/getSampleFormSummary";
	}
%>
<html>
<head>
<title>Select Sample</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
function initPage() {
  if (parent.topFrame) {
    parent.topFrame.document.all.banner.innerHTML = 'Select Sample';
  }
}
  
function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function setActionButtonEnabling(isEnabled) {
  var f = document.forms[0];
  setInputEnabled(f,'btnSelect',isEnabled);
}

function selectSample(sampleId) {
  if (!onFormSubmit()) {
    setActionButtonEnabling(true);
    return;
  }
  document.all['sampleData.sampleId'].value = sampleId;
  document.forms[0].submit();
}

</script>
</head>

<body class="bigr" onLoad="initPage();">
<html:form action="<%=formAction%>"  onsubmit="setActionButtonEnabling(false);">
  <html:hidden property="sampleData.sampleId"/>
  
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
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
  <p>

<table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground">
  <tr class="grey">
	<td noWrap width="10%" align="center">
		<b>Select</b>
	</td>
	<td nowrap width="15%" align="center">
	  <b>Sample Alias</b>
	</td>
	<td nowrap width="10%" align="center">
	  <b>Sample Id</b>
	</td>
	<td nowrap width="15%" align="center">
	  <b>Sample Type</b>
	</td>
	<td nowrap width="10%" align="center">
	  <b>Donor Id</b>
	</td>
	<td nowrap width="15%" align="center">
	  <b>Donor Alias</b>
	</td>
	<td nowrap width="10%" align="center">
	  <b>Consent Id</b>
	</td>
	<td nowrap width="15%" align="center">
	  <b>Consent Alias</b>
	</td>
  </tr>
  <logic:present name="sampleForm" property="matchingSamples">
    <% 
      String rowClass = "white"; 
    %>
    <logic:iterate name="sampleForm" property="matchingSamples" id="sampleData" type="com.ardais.bigr.javabeans.SampleData">
  	  <tr class="<%=rowClass%>">
  	  <%
  	    if (rowClass.equals("white")) {
  	  	  rowClass = "grey";
  	    }
  	    else {
  	   	  rowClass = "white";
  	    }
  	  %>
        <td width="10%" align="center">
	        <input class="smallButton" type="button" name="btnSelect" value="Select" onClick="selectSample('<%=sampleData.getSampleId()%>');"> 
        </td>
	      <td nowrap width="15%" align="center">
          <bean:write name="sampleData" property="sampleAlias"/>
        </td>
	      <td nowrap width="10%" align="center">
		      <bigr:icpLink popup="true">
            <bean:write name="sampleData" property="sampleId"/>
		      </bigr:icpLink>
        </td>
	      <td nowrap width="15%" align="center">
	        <bean:write name="sampleData" property="sampleType"/>
        </td>
	      <td nowrap width="10%" align="center">
		      <bigr:icpLink popup="true">
            <bean:write name="sampleData" property="ardaisId"/>
		      </bigr:icpLink>
        </td>
	      <td nowrap width="15%" align="center">
	        <bean:write name="sampleData" property="donorAlias"/>
        </td>
	      <td nowrap width="10%" align="center">
		      <bigr:icpLink popup="true">
            <bean:write name="sampleData" property="consentId"/>
		      </bigr:icpLink>
        </td>
	      <td nowrap width="15%" align="center">
	        <bean:write name="sampleData" property="consentAlias"/>
        </td>
      </tr>
    </logic:iterate>
  </logic:present>
</table>
</html:form>
</body>
</html>
