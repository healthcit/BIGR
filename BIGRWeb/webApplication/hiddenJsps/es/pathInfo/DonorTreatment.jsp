<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<title>Treatment</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr">
<div align="center"> 
  <%
com.ardais.bigr.es.helpers.FormLogic formLogic = new com.ardais.bigr.es.helpers.FormLogic();


java.util.Hashtable donorData = (java.util.Hashtable) session.getAttribute("DonorData");
String donor_id = (String) request.getAttribute("donor_id");
java.util.Hashtable thisDonor = (java.util.Hashtable) donorData.get(donor_id);
java.util.ArrayList donorTreatments = (java.util.ArrayList) thisDonor.get("DonorTreatment");

Integer index = new Integer(request.getParameter("index"));
int i = index.intValue();

java.util.Hashtable donorTreatment = (java.util.Hashtable) donorTreatments.get(i);
%>
  <p> 
  <table class="background" cellpadding="0" cellspacing="0" border="0" width="80%">
    <tr> 
      <td> 
        <table class="foreground" border="0" cellspacing="1" cellpadding="3" width="100%">
          <tr class="green"> 
            <td colspan="7"> 
              <div align="center"><b>Treatment Detail</b></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" > 
              <div align="right"><b>Donor Id</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><%= formLogic.getValue(donorTreatment, "ARDAIS_ID") %></div>
            </td>
          </tr>
         
          <tr class="white"> 
            <td class="grey"  > 
              <div align="right"><b>Treatment Type</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><%= formLogic.getValue(donorTreatment, "TREATMENT_TYPE") %> </div>
            </td>
          </tr>
         
          <tr class="white"> 
            <td class="grey"  > 
              <div align="right"><b>Treatment Start Date</b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorTreatment, "START_DATE_MM") %>/<%= formLogic.getValue(donorTreatment, "START_DATE_YYYY") %></div>
            </td>
            <td class="grey" > 
              <div align="right"><b>Treatment End Date</b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorTreatment, "END_DATE_MM") %>/<%= formLogic.getValue(donorTreatment, "END_DATE_YYYY") %></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"  > 
              <div align="right"><b>Treatment Duration</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><%= formLogic.getValue(donorTreatment, "TREATMENT_DURATION") %></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"  > 
              <div align="right"><b>Response</b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorTreatment, "TREATMENT_RESPONSE") %></div>
            </td>
            <td class="grey" > 
              <div align="right"><b>Treatment Complication</b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorTreatment, "TREATMENT_COMPLICATION_DETAIL") %></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"  > 
              <div align="right"><b>Dose<br>
                (For Meds, Radiation Rx)</b></div>
            </td>
            <td> 
              <div align="left"><%= formLogic.getValue(donorTreatment, "DOSAGE")%> <%= formLogic.getValue(donorTreatment, "DOSAGE_UNIT") %></div>
            </td>
            <td class="grey" > 
              <div align="right"><b>Route</b></div>
            </td>
            <td class="white" ><%= formLogic.getValue(donorTreatment, "TREATMENT_ROUTE") %> </td>
          </tr>
          <tr class="white"> 
            <td class="grey"  > 
              <div align="right"><b>Frequency</b></div>
            </td>
            <td><%= formLogic.getValue(donorTreatment, "TREATMENT_FREQUENCY") %></td>
            <td class="grey" > 
              <div align="right"><b>Mantle Field<br>
                (For Radiation Rx) </b></div>
            </td>
            <td><%= formLogic.getValue(donorTreatment, "MANTLE_FIELD_DESC") %> </td>
          </tr>
          <tr class="white"> 
            <td class="grey"  > 
              <div align="right"><b>Note</b></div>
            </td>
            <td colspan="3"><%= formLogic.getValue(donorTreatment, "TREATMENT_NOTE") %></td>
          </tr>
          <tr class="white"> 
            <td colspan="4"> 
              <div align="center"> 
                <input type="button" name="Button" value="Back" onClick="history.go(-1);">
              </div>
            </td>
          </tr>
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
