<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-html"   prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<title>Diagnosis</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr">
<div align="center"> 
  <%
com.ardais.bigr.es.helpers.FormLogic formLogic = new com.ardais.bigr.es.helpers.FormLogic();


java.util.Hashtable donorData = (java.util.Hashtable) session.getAttribute("DonorData");
String donor_id = (String) request.getAttribute("donor_id");
java.util.Hashtable thisDonor = (java.util.Hashtable) donorData.get(donor_id);
java.util.ArrayList donorDiagnosiss = (java.util.ArrayList) thisDonor.get("DonorDiagnosis");

Integer index = new Integer(request.getParameter("index"));
int i = index.intValue();

java.util.Hashtable donorDiagnosis = (java.util.Hashtable) donorDiagnosiss.get(i);
%>
  <p> 
  <table class="background" cellpadding="0" cellspacing="0" border="0" width="80%">
    <tr>
      <td> 
        <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
          <tr class="green"> 
            <td colspan="4"> 
              <div align="center"><b>Diagnosis Detail</b></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>&nbsp;Donor ID</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "ARDAIS_ID") %></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Diagnosis Type</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "DIAGNOSIS_TYPE") %></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Diagnosis Name</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "DIAGNOSIS_CONCEPT_ID") %> </div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Date of Onset</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "DATE_OF_DX_MM") %>/<%= formLogic.getValue(donorDiagnosis, "DATE_OF_DX_YYYY") %></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Age at Onset</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "AGE_AT_DIAGNOSIS") %></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Diagnosis Note</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "NOTE") %></div>
            </td>
          </tr>
          <!-- For Cancer Only Starts Here -->
		  <% 
		  if(donorDiagnosis.get("METASTISIS_IND") != null &&  ((String)donorDiagnosis.get("METASTISIS_IND")).equals("Y")){
		 
		  %>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Did This Cancer Metastasize?</b></div>
            </td>
            <td colspan="3"> 
              <p align="left"><%= formLogic.getValue(donorDiagnosis, "METASTISIS_IND") %>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Location 1</b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "METASTISIS_LOCATION_1") %></div>
            </td>
            <td class="grey"> 
              <div align="right"> <b>&nbsp;How verified </b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "LOCATION_1_VERIFICATION") %></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Location 2</b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "METASTISIS_LOCATION_ 2") %></div>
            </td>
            <td class="grey"> 
              <div align="right"><b>&nbsp;How verified </b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "LOCATION_2_VERIFICATION") %></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>Location 3</b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "METASTISIS_LOCATION_ 3") %></div>
            </td>
            <td class="grey"> 
              <div align="right"><b>How verified </b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorDiagnosis, "LOCATION_2_VERIFICATION") %></div>
            </td>
          </tr>
		  <% } %>
          <tr class="white"> 
            <td colspan="5"> 
              <div align="center"> 
                <input type="button" name="Button" value="Back" onClick="history.go(-1);">
              </div>
            </td>
          </tr>
          <!-- For Cancer Only End Here -->
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
<script>
window.focus();
</script>
</html>
