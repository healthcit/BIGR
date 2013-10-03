<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.orm.databeans.ConsentDatabean"%>
<%@ page import="com.ardais.bigr.orm.databeans.IrbProtocolDatabean"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>IRB Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/calendar.js"/>"></script>
<script type="text/javascript">
function addConsentVer(param) {
  var f = document.form1;
  
  f.irbProtocolID.value = param;
  f.op.value = "AddConsentvertoIRB";
  f.submit();
}

function addIrbConsentVer() {
  var f = document.form1;
  
  if((f.consentAndIrb.value != "")
	   && (f.irbAndConsentVer.value != "")
	   && (f.policyId.value != "")) {
    

    f.op.value = "AddIRBandConsentVersion";
    f.submit();
  }
  else {
    alert("To add a new IRB, you must specifiy IRB Protocol, Policy, and Consent Version.");
  }
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

<%
	String lastIrbProtocolIDId = null;
	String lastConsentVersion = null;
	String lastExpirationDate = null;
	boolean retry = false;
	String consentValue = "";
	String expirationDateValue = "";
	
	if ((request.getAttribute("myError")!=null)) {
		lastIrbProtocolIDId = (String) request.getAttribute("irbProtocolID");
		lastConsentVersion = (String) request.getAttribute("consentVersion");
		lastExpirationDate = (String) request.getAttribute("expirationDate");
		retry = true;
	}

%>


</script>
</head>
<body class="bigr">
<form name="form1" method="post" action="<html:rewrite page='/orm/Dispatch'/>">
  <input type="hidden" name="accountID" value="<%=request.getParameter("accountID")%>">
  <input type="hidden" name="irbProtocolID" value="">
  <input type="hidden" name="op" value="SaveIRBConsentVerRelation">
  <table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
    <tr> 
      <td> 
        <table border="0" cellspacing="1" cellpadding="3" class="foreground">
		  <tr class="yellow"> 
            <td colspan="6"> 
			<div align="center"><b><%= ((request.getAttribute("myError")!=null)?request.getAttribute("myError"):" Add Consent Versions to Existing IRB Protocols") %></b></div>
			</td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="left" nowrap> 
              <b>IRB Protocol and Policy:</b>
            </td>
            <td class="grey" align="left"> 
              <b>Consent Version:</b>
            </td>
            <td class="grey" align="left"> 
              <b>Expiration Date:</b>
            </td>
            <td class="grey" align="center"> 
              <b>Active:</b>
            </td>
            <td class="grey" align="center"> 
              <b>Consent Text:</b>
            </td>
          </tr>
		  <%
		  Vector irbConsentList = new Vector();
		  if(request.getAttribute("IrbConsentList") != null)
		  {
		  	irbConsentList = (Vector)(request.getAttribute("IrbConsentList"));
		  }
		  int recordCount = 0;
		  for (int j =0; j< irbConsentList.size();j++)
		  {
		  	IrbProtocolDatabean irbCons = (IrbProtocolDatabean)irbConsentList.get(j);
		  	List consentList = irbCons.getConsentCollection();
		  
		   for(int i=0;i<consentList.size();i++)
			{
				ConsentDatabean consent = (ConsentDatabean)consentList.get(i);
			%>
			<tr class="white" > 
			<%
				if(i == 0) { 
				  // Only the first row for each IRB has the IRB column.
				  // That column has a rowspan that spans all of the consent
				  // version rows for that IRB.
			%>
			<td rowspan="<%=consentList.size()+1%>" align="left" nowrap> 
              <%=Escaper.htmlEscape(irbCons.getIrbName())%>
              <br><b>Policy:</b><br>
              <%=Escaper.htmlEscape(irbCons.getPolicyName())%>
            </td>
			<%  } // end if i == 0 %>            
		    <td align="left"> 
              <%=consent.getConsentVersionName()%>
            </td>
		    <td align="left" nowrap> 
                <input type="text" name="expirationDate" value="<%=consent.getExpirationDate()%>" READONLY size="10" onclick="openCalendar('expirationDate', 'false', 'false', '<%=recordCount%>')">
                <input type="hidden" name="consentVersionId" value="<%=consent.getConsentVersionID()%>"/>
            </td>
            <td align="center"> 
              <input type="checkbox" name="consentVersion" value="<%=consent.getConsentVersionID()%>" <%if(consent.isActive()) {out.print("checked");}%>>
            </td>
            <td align="center"> 
              <% String theURL = "/orm/Dispatch?op=PrepareConsentTexttoIRB&accountID="
                 + request.getParameter("accountID")
                 + "&irbProtocol="
                 + irbCons.getIrbId()
                 + "&consentIrb="
                 + consent.getConsentVersionID()
                 ; %>
              <input type="button" name="btnText<%=consent.getConsentVersionID()%>" value="View/Edit Text"
          		 onclick="MM_openBrWindow('<html:rewrite page="<%= theURL %>"/>','ConsentText','scrollbars=yes,resizable=yes,width=640,height=420');">
            </td>
          </tr>
		<% recordCount++; } // end for i (consentList) 
			String irbId = "" + irbCons.getIrbId();
			if (retry) {
				if (irbId.equals(lastIrbProtocolIDId)) {
					if (lastConsentVersion != null) {
						consentValue = lastConsentVersion;
					}
					else {
						consentValue = "";
					}
					if (lastExpirationDate != null) {
						expirationDateValue = lastExpirationDate;
					}
					else {
						expirationDateValue = "";
					}
				}
				else {
					consentValue = "";
					expirationDateValue = "";
				}			
			}				
		%>
		
			<tr class="white"> 
            <td> 
              <div align="left"> 
                <input type="text" name="<%=irbCons.getIrbId()%>" value="<%=consentValue%>" maxlength="25"
                  onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);">
              </div>
            </td>
            <td colspan="2"> 
                <input type="text" name="<%=irbCons.getIrbId()%>ExpirationDate" value="<%=expirationDateValue%>" size="10" READONLY onclick="openCalendar('<%=irbCons.getIrbId()%>ExpirationDate')">
            </td>
            <td> 
              <div align="center"> 
                <input type="button" name="addConsentVersion" value="Add"
                  onclick="addConsentVer(<%=irbCons.getIrbId()%>)">
              </div>
            </td>
          </tr>
		  <%
		  } // end for j (irbConsentList)
		  %>
		  
          <tr class="yellow"> 
            <td colspan="6"> 
              <div align="center"><b>Add New IRB Protocol and Consent Version</b></div>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" nowrap align="left"> 
              <b>IRB Protocol and Policy:</b>
            </td>
            <td class="grey" align="left" colspan="2"> 
              <b>Consent Version:</b>
            </td>
            <td colspan="2" class="grey" align="center">
              <b>Add:</b>
            </td>
          </tr>
          <tr class="white"> 
            <td align="left" nowrap valign="top"> 
              <input type="text" name="irbAndConsentVer" maxlength="45" size="27"
                onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);">
              <br><b>Policy:</b><br>
              <%
			    String policyId = ApiFunctions.safeString(request.getParameter("policyId"));
                int numPolicyChoices =
                  ((List) request.getAttribute("policyChoices")).size();
              %>
              <html:select property="policyId"
                 value='<%= policyId %>'>
                <option value="">Select</option>
                <html:options collection="policyChoices"
                  property="policyId"
                  labelProperty="policyName"/>
              </html:select>
            </td>
            <td align="left" valign="top" colspan="2"> 
              <input type="text" name="consentAndIrb" maxlength="25"
                onchange="javascript:while(''+this.value.charAt(0)==' ')this.value=this.value.substring(1,this.value.length);">
              <br><b>Expiration Date:</b><br>
                <input type="text" name="newExpirationDate" value="" READONLY size="10" onclick="openCalendar('newExpirationDate')">
            </td>
            <td colspan="2" align="center" valign="top"> 
              <input type="button" name="addIrbandConsentVer" value="Add" onclick="addIrbConsentVer()">
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="5"> 
              <div align="center"> 
                <input type="submit" name="Submit" value="Submit">
                <input type="reset" name="Submit2" value="Cancel" onclick="window.close()">
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
<script>
	window.focus();
</script></body>
</html>
