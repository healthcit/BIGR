<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%
  // If the "resetForm" request parameter is "true", all of the input controls
  // should get their default values instead of the values in the
  // request parameters.
  //
  boolean resetForm = ("true".equals(request.getParameter("resetForm")));
  String ardaisId_1 = "";
  String ardaisId_2 = "";
  String consentId_1 = "";
  String consentId_2 = "";
  String hours = "";
  String minutes = "";
  String ampm = "";
  String bankable = "";
  
  if (! resetForm) {
    ardaisId_1 = ApiFunctions.safeString(request.getParameter("ardaisId_1"));
    ardaisId_2 = ApiFunctions.safeString(request.getParameter("ardaisId_2"));
    consentId_1 = ApiFunctions.safeString(request.getParameter("consentId_1"));
    consentId_2 = ApiFunctions.safeString(request.getParameter("consentId_2"));
    hours = ApiFunctions.safeString(request.getParameter("hours"));
    minutes = ApiFunctions.safeString(request.getParameter("minutes"));
    ampm = ApiFunctions.safeString(request.getParameter("ampm"));
    bankable = ApiFunctions.safeString(request.getParameter("bankable"));
  }
%>

<html>
<head>
<title>Consent Verification</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.form1.ardaisId_1.focus();
}

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
	document.form1.Submit.disabled = true;
}

myBanner = 'Consent Verification';
</script>
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
</head>
<body class="bigr" onload="initPage();">
<form name="form1" method="get" action="<html:rewrite page="/iltds/Dispatch"/>"
      onsubmit="return(onFormSubmit());">
  <div align="center"> 
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <% if (request.getAttribute("myError")!=null) { %>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><font color="#FF0000">
                  <b><bean:write name="myError"/></b></font></div>
              </td>
            </tr>
			<script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
            <% } // end myError %>
            <% if (request.getAttribute("confirmationMessage")!=null) { %>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><font color="#FF0000">
                  <b><bean:write name="confirmationMessage"/></b></font></div>
              </td>
            </tr>
            <% } // end confirmationMessage %>
            <tr class="white"> 
              <td class="grey">Donor ID:</td>
              <td> 
                <div align="left"> 
                  <input type="text" name="ardaisId_1" size="<%= (ValidateIds.LENGTH_DONOR_ID + 5)%>"
                    maxlength="<%= ValidateIds.LENGTH_DONOR_ID %>" value="<%= ardaisId_1 %>">
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Donor ID (confirm):</td>
              <td> 
                <input type="text" name="ardaisId_2" size="<%= (ValidateIds.LENGTH_DONOR_ID + 5)%>"
                  maxlength="<%= ValidateIds.LENGTH_DONOR_ID %>" value="<%= ardaisId_2 %>">
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey">Case ID:</td>
              <td> 
                <input type="text" name="consentId_1" size="<%= (ValidateIds.LENGTH_CASE_ID + 5)%>"
                  maxlength="<%= ValidateIds.LENGTH_CASE_ID %>" value="<%= consentId_1 %>">
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey" nowrap>Case ID (confirm):</td>
              <td> 
                <div align="left"> 
                  <input type="text" name="consentId_2" size="<%= (ValidateIds.LENGTH_CASE_ID + 5)%>"
                    maxlength="<%= ValidateIds.LENGTH_CASE_ID %>" value="<%= consentId_2 %>">
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey">Time of Consent: </td>
              <td> 
                <div align="left"> 
                  <select name="hours">
                    <% for (int j = 1 ; j <= 12; j ++) { %>
                    <option<%  if(!hours.equals("")){
						  	if(hours.equals(j + "")){
							out.print(" selected");
							}
						  } %>><%= j %></option>
                    <% } %>
                  </select>
                  : 
                  <select name="minutes">
                    <% for(int k = 0; k <= 59; k++){ %>
                    <option<%  if(!minutes.equals("")){
						  	if(minutes.equals(k + "") || minutes.equals("0" + k)){
							out.print(" selected");
							}
						  } %>> 
                    <% if((k + "").length() == 1){
						  out.print("0" + k);
						  }else { out.print(k); }
						  %>
                    <% } %>
                    </option>
                  </select>
                  <input type="radio" name="ampm" value="am" <% if(ampm.equals("am") || ampm.equals("")) out.print("checked");%>>
                  AM 
                  <input type="radio" name="ampm" value="pm" <% if(ampm.equals("pm")) out.print("checked");%>>
                  PM </div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey">Bankable Specimen:</td>
              <td> 
                <div align="left">Yes 
                  <input type="radio" name="bankable" value="Yes"<%if(bankable.equals("Yes") || bankable.equals("")) out.print(" checked");%>>
                  No 
                  <input type="radio" name="bankable" value="No"<%if(bankable.equals("No")) out.print(" checked");%>>
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="hidden" name="op" value="ConsentVerify">
                  <input type="submit" name="Submit" value="Confirm">
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</form>
</body>
</html>
