<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script>
function initForm() {
    if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;;
    <% if ((String)request.getParameter("DiseaseType") != null ) { %>
      document.forms[0].DiseaseType.value='<%= request.getParameter("DiseaseType") %>';
    <% } %>
}
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
myBanner = 'Case Release';
function showOther1() {

	if(document.forms[0].DiseaseType.value != null && document.forms[0].DiseaseType.value == 'CA00038D^^') {
	   document.forms[0].OtherDiagnosis.value=""; 	
	   document.forms[0].OtherDiagnosis.disabled=false;
	   document.forms[0].OtherDiagnosis.focus(); 	
	} else {
	   document.forms[0].OtherDiagnosis.value="N/A";
	   document.forms[0].OtherDiagnosis.disabled=true;
	}
}
function disablePopup() {
  if ((masterListArray["DiseaseType"] != null) 
      && (masterListArray["DiseaseType"].length != document.all.DiseaseType.length)) {
    document.all.Open.disabled = true;
  }
  else {
    document.all.Open.disabled = false;
  }
}
</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr" onLoad="initForm();">
      
<div align="center"> <br>
  <br>
  <form name="menu" method="post" action="<html:rewrite page="/iltds/Dispatch"/>">
        
        
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr>
            <td>
              
          <table border="0" cellspacing="1" cellpadding="3" class="foreground">
            <% if (request.getAttribute("myError")!=null) { %><tr class="yellow"> 
                  
              <td colspan="2"> 
                <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError") %></b></font></div>
                  </td>
                </tr>
				<script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
				<% } %>
                  <tr class="white"> 
                    
              <td class="grey" nowrap>Case ID:</td>
                    
              <td nowrap> 
                <input type="text" name="consentID_1" size="<%= (ValidateIds.LENGTH_CASE_ID + 5)%>" maxlength="<%= ValidateIds.LENGTH_CASE_ID %>" value="<%= ((request.getParameter("consentID_1")!=null)?request.getParameter("consentID_1"):"") %>">
                    </td>
                  </tr>
                  <tr class="white"> 
                    
              <td class="grey" nowrap>Case ID (confirm):</td>
                    
              <td nowrap> 
                <input type="text" name="consentID_2" size="<%= (ValidateIds.LENGTH_CASE_ID + 5)%>" maxlength="<%= ValidateIds.LENGTH_CASE_ID %>" value="<%= ((request.getParameter("consentID_2")!=null)?request.getParameter("consentID_2"):"") %>">
                    </td>
                  </tr>
				  <tr class="white">
              <td class="grey" nowrap>Pathology Diagnosis:</td>
				  
              <td nowrap>
			    <input type="text" name="searchDiag" maxlength="20" onKeyPress="if(event.keyCode == 13){findListOptions(this.form.searchDiag, this.form.DiseaseType);showOther1();disablePopup();return false;}" >
				<input type="button" value="Search" name="search" onClick="findListOptions(this.form.searchDiag, this.form.DiseaseType);showOther1();disablePopup();">
				<input type="button" value="Refresh" name="search" onClick="restoreListOptions(this.form.searchDiag, this.form.DiseaseType);showOther1();disablePopup();">
 				<br>
				<jsp:include page="/hiddenJsps/iltds/LIMSHierarchy/PdcDiseaseType.jsp" flush="true"/>
			  </td>
			</tr>
			  <tr class="white"> 
				  
                  
              <td class="grey" nowrap>Other Pathology Diagnosis:</td>
				  
              <td nowrap> 
                <input type="text" name="OtherDiagnosis" value="" size="35" maxlength="200">
					(when other diagnosis is selected) </td>
				</tr>
                <tr class="white"> 
                    <td colspan="2"> 
                      <div align="center"> <font size="4">
                        <input type="hidden" name="op" value="CaseReleaseConfirm">
                        </font>
<input type="submit" name="Submit" value="Confirm">
                        <font size="4"> </font></div>
                    </td>
                </tr>
              </table>
</td>
          </tr>
        </table></form>
      </div>
 
 <script language="JavaScript">
		document.menu.consentID_1.focus();
		document.forms[0].OtherDiagnosis.value="N/A";
	   document.forms[0].OtherDiagnosis.disabled=true;
		</script>
</body>
</html>
