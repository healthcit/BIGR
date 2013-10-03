<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.RequestType"%>
<%@ page import="com.ardais.bigr.iltds.helpers.IltdsUtils"%>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define id="myForm" name="requestDtoForm" type="com.ardais.bigr.iltds.web.form.RequestForm"/>
<html:html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title><bean:write name="requestDtoForm" property="requestType"/> Request Selected Items -- Complete Request Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
<!--

var myBanner = '<bean:write name="requestDtoForm" property="requestType"/> Request Selected Items - Complete Request Summary';

function initPage() {
  if (parent.topFrame != null) {
    parent.topFrame.document.all.banner.innerHTML = myBanner; 
  }
  showOrHideShippingAddressFields();
}

function showOrHideShippingAddressFields() {
  var isDisabled;
<%
  if (RequestType.TRANSFER.toString().equalsIgnoreCase(myForm.getRequestType())) { 
%>
  var f = document.forms[0];
  if (f.destinationId.value == '<%=IltdsUtils.OUT_OF_NETWORK_LOCATION %>') {
    document.all.shippingAddressInfo.style.display = 'inline';
    isDisabled = false;
  }
  else {
    document.all.shippingAddressInfo.style.display = 'none';
    isDisabled = true;
  }
<%
  } else  { 
%>
    document.all.shippingAddressInfo.style.display = 'none';
    isDisabled = true;
<%
  } 
%>
  document.all['shippingAddress.firstName'].disabled = isDisabled;
  document.all['shippingAddress.middleName'].disabled = isDisabled;
  document.all['shippingAddress.lastName'].disabled = isDisabled;
  document.all['shippingAddress.locationAddress1'].disabled = isDisabled;
  document.all['shippingAddress.locationAddress2'].disabled = isDisabled;
  document.all['shippingAddress.locationCity'].disabled = isDisabled;
  document.all['shippingAddress.locationState'].disabled = isDisabled;
  document.all['shippingAddress.locationZip'].disabled = isDisabled;
  document.all['shippingAddress.country'].disabled = isDisabled;
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function submitCancel() {
  if (!onFormSubmit()) {
    setActionButtonEnabling(true);
    return;
  }
  <% 
    if (RequestType.TRANSFER.toString().equalsIgnoreCase(myForm.getRequestType())) {
  %>
	  var url = '<html:rewrite page="/library/holdView.do?txType=TxRequestSamples"/>';
  <%
    } else if (RequestType.RESEARCH.toString().equalsIgnoreCase(myForm.getRequestType())) {
  %>
	  var url = '<html:rewrite page="/library/holdView.do?txType=TxSampSel"/>';
  <%
  	}
  %>  
 	window.location.href = url;
}


function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
}

//-->
</script>
</head>
<body  class="bigr" onload="initPage();">
<%
	String action = "";
    if (RequestType.TRANSFER.toString().equalsIgnoreCase(myForm.getRequestType())) {
      action = "/iltds/createTransferRequest.do";
    }
    else if (RequestType.RESEARCH.toString().equalsIgnoreCase(myForm.getRequestType())) {
      action = "/iltds/createResearchRequest.do";
    }
%>
<html:form method="POST" action="<%=action%>" onsubmit="return(onFormSubmit());">
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <logic:present name="org.apache.struts.action.ERROR">
    <tr> 
      <td>
        <table border="0" cellspacing="1" cellpadding="1" class="foreground-small">
          <tr class="white">
		    <td><div align="left"><font color="#FF0000"><b><html:errors/></b></font></div></td>
		  </tr>
		</table>
	  </td>
	</tr>
	</logic:present>
	<logic:present name="com.ardais.BTX_ACTION_MESSAGES">
    <tr> 
      <td>
        <table border="0" cellspacing="1" cellpadding="1" class="foreground-small">
          <tr class="white">
		    <td><div align="left"><font color="#000000"><b><bigr:btxActionMessages/></b></font></div></td>
		  </tr>
		</table>
	  </td>
	</tr>
	</logic:present>
    <tr>
      <td>
        <table border="0" cellspacing="1" cellpadding="3" align="center" class="foreground">
          <tr class="white"> 
            <td > 
              <table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
                <tr> 
                  <td> 
                    <table border="0" cellspacing="1" cellpadding="3" class="foreground" width="382">
                      <tr class="white">
                        <%
    				      if (RequestType.TRANSFER.toString().equalsIgnoreCase(myForm.getRequestType())) { 
                        %>
                   	    <td class="grey"><b>Destination<font color="red">*</font> :</b></td>
                      	<td><bigr:selectList
							property="destinationId"
							name="requestDtoForm"
							legalValueSetProperty="shippingPartnersAsLegalValueSet"
							onchange="showOrHideShippingAddressFields();"
							firstValue="" firstDisplayValue="Select"/>
                        <%
                    	  }
                        %>
					    <td class="grey"><b>Created By:</b></td>
                        <td><bean:write name="requestDtoForm" property="requesterName"/></td>
                        <html:hidden property="requesterName"/>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
     
    		  <span id="shippingAddressInfo">         
              <br>
              <table border="0" cellspacing="1" cellpadding="3" class="background">
    			<tr class="yellow"> 
      			  <td colspan="2"> 
        			<div align="center"><b>Shipping Address</b></div>
      			  </td>
    			</tr>
    			<tr class="white"> 
      			  <td align="right"><b>First Name<font color="red">*</font> :</b></td>
      			  <td align="left">
      			    <html:text name="requestDtoForm" property="shippingAddress.firstName" maxlength="35" size="35"/>
      			  </td>
    			</tr>
     			<tr class="white"> 
			      <td> 
			        <div align="right"><b>Middle Name :</b></div>
			      </td>
      			  <td align="left">
      			    <html:text name="requestDtoForm" property="shippingAddress.middleName" maxlength="2" size="2"/>
      			  </td>
    			</tr>
    			<tr class="white"> 
		          <td> 
		            <div align="right"><b>Last Name<font color="red">*</font> :</b></div>
		          </td>
      			  <td align="left">
      			    <html:text name="requestDtoForm" property="shippingAddress.lastName" maxlength="30" size="30"/>
      			  </td>
    			</tr>
    			<tr class="white"> 
			      <td> 
			        <div align="right"><b>Address 1<font color="red">*</font> :</b></div>
			      </td>
			      <td align="left"> 
      			    <html:text name="requestDtoForm" property="shippingAddress.locationAddress1" maxlength="60" size="60"/>
			      </td>
			    </tr>
			    <tr class="white"> 
			      <td> 
			        <div align="right"><b>Address 2 :</b></div>
			      </td>
			      <td align="left"> 
      			    <html:text name="requestDtoForm" property="shippingAddress.locationAddress2" maxlength="60" size="60"/>
			      </td>
			    </tr>
			    <tr class="white"> 
			      <td> 
			        <div align="right"><b>City <font color="red">*</font>:</b></div>
			      </td>
			      <td align="left"> 
      			    <html:text name="requestDtoForm" property="shippingAddress.locationCity" maxlength="25" size="25"/>
			      </td>
    			</tr>
    			<tr class="white"> 
			      <td> 
        			<div align="right"><b>State<font color="red">*</font> :</b></div>
			      </td>
			      <td align="left"> 
      			    <html:text name="requestDtoForm" property="shippingAddress.locationState" maxlength="25" size="25"/>
			      </td>
			    </tr>
			    <tr class="white"> 
			      <td> 
			        <div align="right"><b>Zip Code<font color="red">*</font> :</b></div>
			      </td>
			      <td align="left"> 
      			    <html:text name="requestDtoForm" property="shippingAddress.locationZip" maxlength="15" size="15"/>
			      </td>
    			</tr>
    			<tr class="white"> 
			      <td> 
			        <div align="right"><b>Country<font color="red">*</font> :</b></div>
			      </td>
			      <td align="left"> 
      			    <html:text name="requestDtoForm" property="shippingAddress.country" maxlength="35" size="35"/>
      			  </td>
      			</tr>
      		  </table>
      		  </span>
               
              <br>
              <table border="0" cellspacing="1" cellpadding="3" class="background" width="150">
                <tr class="yellow">
               	  <td> 
              	    <div align="center"><b>Please enter comments for your request:</b></div>
                  </td>
                </tr>
                <tr class="white">
                  <td align="left">
              		<html:textarea property="requesterComments" rows="10" cols="80" onkeyup="maxTextarea(4000);"/>
	          	  </td>
                </tr>
              </table>
            </td>
          </tr>
	      <tr class="white" >
		    <td colspan="6" align="center">
              <logic:iterate name="requestDtoForm" property="requestItemForms" id="element" indexId="itemIndex">
              <html:hidden name="requestDtoForm" property='<%="requestItemForm[" + itemIndex + "].itemId"%>'/>
              </logic:iterate>
              <html:hidden property="requesterId"/>
		      <input type="submit" name="btnSubmit" value="Submit Request">
			  <input type="button" name="btnCancel" value="Cancel" onclick="submitCancel();" >      
		    </td>
	      </tr>
        </table>
      </td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
