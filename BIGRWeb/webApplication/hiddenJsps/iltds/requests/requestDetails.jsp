<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.util.IcpUtils"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %>
<%@ page import="com.ardais.bigr.iltds.web.form.RequestBoxForm" %>
<%@ page import="com.ardais.bigr.iltds.web.form.RequestItemForm" %>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>

<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define id="myForm" name="requestDtoForm" type="com.ardais.bigr.iltds.web.form.RequestForm"/>
<%
  	  SecurityInfo secInfo = WebUtils.getSecurityInfo(request); 
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Request Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script type="text/javascript" src='<html:rewrite page="/js/ssresults.js"/>'></script>
<script type="text/javascript" src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>
<%
	//start files needed for display of Aperio images (SWP-1061)
%>
<jsp:include page="/hiddenJsps/kc/misc/coreScript.jsp" flush="true"/>
<script type="text/javascript" src='<html:rewrite page="/js/integration/bigrAperio.js"/>'></script>
<script type="text/javaScript" src="<html:rewrite page="/js/lightbox/gsbioLightbox.js"/>"></script>
<script type="text/javaScript" src="<html:rewrite page="/hiddenJsps/kc/detViewer/js/scriptaculous.js?load=builder"/>"></script>
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/js/lightbox/gsbioLightbox.css"/>'>
<%
	//end files needed for display of Aperio images (SWP-1061)
%>
<script language="JavaScript">
<!--

var myBanner = 'Request Details';

function initPage() {
  commonInitPage();
  _isPageReadyForInteraction = true;
}

//-->
</script>
</head>

<body class="bigr" onload="initPage();">
	<% //div for "please wait" message %>
  <div id="waitMessage" style="display: none"> 
    <table align="center" border="0" cellspacing="0" cellpadding="0" class="background" width="300">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr align="center" class="yellow"> 
              <td><img id="waitMessageImage" 
                       src="<html:rewrite page='/images/pleasewait.gif'/>"
                       alt="Please Wait"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <% //div for whole page %>
	<div id="wholePage">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
        <tr class="yellow"> 
	      <td> 
	        <div align="center">
	          <font color="#FF0000"><b><html:errors/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	    <tr class="yellow"> 
	      <td> 
	        <div align="center">
	          <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
	</table>
  </div>
  <%
  	//the detail table calculates it's size based on the height of the window minus
  	//the height of the following Buttons div.  Since the real buttons are at the bottom
  	//of the page (and the detail table doesn't know about them when it is rendering
  	//itself, we define offsetHeight in this div so the detail table sizes itself
  	//with enough space left at the bottom of the page for the real buttons
  %>
  <div id="Buttons"
			style="position: absolute; width: 100%; overflow: hidden; z-index: 2;"
			offsetHeight=70>
  </div>
  <%
  	//only display the request information if the request could be found.
  	//To tell if the request was found, see if there is a request create date.
  %>
  <logic:present name="requestDtoForm" property="createDate">
	<script language="JavaScript">
	<!--
	
	function reloadTable(resultsFormDefinitionId) {
    this.focus();
	  if (resultsFormDefinitionId != null) {
			document.all.wholePage.style.display = 'none';
			document.all.waitMessage.style.display = 'block';
	    var URL = '<html:rewrite page="/iltds/showRequest.do"/>' + "?requestId=<bean:write name="requestDtoForm" property="requestId"/>" + "&txType=<%=ResultsHelper.TX_TYPE_ORDER_DETAIL%>";
	    URL = URL + "&resultsFormDefinitionId=" + resultsFormDefinitionId;
		  window.location.href = URL;
	  }
	}
	
	//-->
	</script>
	
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
 	        <tr class="white"> 
              <td class="yellow" colspan="2" align="center"> 
                  <b>Request <bean:write name="requestDtoForm" property="requestId"/></b>
              </td>
            </tr>
			<tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Request Type:</b></div>
              </td>
              <td> 
                <bean:write name="requestDtoForm" property="requestType"/>
              </td>
            </tr>
			<tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Submission Date:</b></div>
              </td>
              <td> 
                <bean:write name="requestDtoForm" property="createDate"/>
              </td>
            </tr>
			<tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Submitted By:</b></div>
              </td>
              <td> 
                <bean:write name="requestDtoForm" property="requesterName"/>
              </td>
            </tr>
			<tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>State:</b></div>
              </td>
              <td>
                <bean:write name="requestDtoForm" property="status"/>
                &nbsp;-&nbsp;
                <bean:write name="requestDtoForm" property="state"/>
              </td>
            </tr>
            <logic:present name="requestDtoForm" property="deliverToName">
			  <tr class="white"> 
                <td class="grey" > 
                  <div align="right"><b>Deliver To:</b></div>
                </td>
                <td> 
                  <bean:write name="requestDtoForm" property="deliverToName"/>
                </td>
              </tr>
            </logic:present>
            <logic:present name="requestDtoForm" property="destinationName">
			  <tr class="white"> 
                <td class="grey" > 
                  <div align="right"><b>Destination:</b></div>
                </td>
                <td> 
                  <bean:write name="requestDtoForm" property="destinationName"/>
                </td>
              </tr>
            </logic:present>
            <% 
            //because the form always returns a non-null Address we can't rely
            //on a logic:present tag on the address.  So, look to see if there
            //is a street address (which is a required field on addresses)
            //and if so we now to show the address
            %>
            <logic:notEmpty name="requestDtoForm" property="shippingAddress.locationAddress1">
			  <tr class="white"> 
                <td class="grey" > 
                  <div align="right"><b>Address:</b></div>
                </td>
                <td> 
                  <bean:write name="requestDtoForm" property="shippingAddress.contactName"/><br>
                  <bean:write name="requestDtoForm" property="shippingAddress.locationAddress1"/><br>
                  <logic:notEmpty name="requestDtoForm" property="shippingAddress.locationAddress2">
                    <bean:write name="requestDtoForm" property="shippingAddress.locationAddress2"/><br>
                  </logic:notEmpty>
                  <bean:write name="requestDtoForm" property="shippingAddress.locationCity"/>,&nbsp;
                  <bean:write name="requestDtoForm" property="shippingAddress.locationState"/>&nbsp;&nbsp;&nbsp;
                  <bean:write name="requestDtoForm" property="shippingAddress.locationZip"/><br>
                  <bean:write name="requestDtoForm" property="shippingAddress.country"/><br>
                </td>
              </tr>
            </logic:notEmpty>
			<tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Submitter Comments:</b></div>
              </td>
              <td>
                <bigr:beanWrite filter="true" whitespace="true" name="requestDtoForm" property="requesterComments"/>
              </td>
            </tr>
			<tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Request Manager Comments:</b></div>
              </td>
              <td>
                <bigr:beanWrite filter="true" whitespace="true" name="requestDtoForm" property="requestManagerComments"/>
              </td>
            </tr>
  			<logic:present name="requestDtoForm" property="requestBoxFormsAsList">
  			  <bean:define id="boxes" name="requestDtoForm" property="requestBoxFormsAsList" type="java.util.List"/>
              <%
                if (!ApiFunctions.isEmpty(boxes)) {
            	  StringBuffer buff = new StringBuffer();
            	  boolean includeComma = false;
            	  Iterator iterator = boxes.iterator();
            	  while (iterator.hasNext()) {
                    if (includeComma) {
                      buff.append(", ");
                    }
                    includeComma = true;
                    RequestBoxForm box = (RequestBoxForm)iterator.next();
                    buff.append(IcpUtils.preparePopupLink(box.getBoxId(),secInfo));
                  }
              %>
			  <tr class="white"> 
                <td class="grey" > 
                  <div align="right"><b>Boxes used to fulfill request:</b></div>
                </td>
                <td>
                  <%=buff.toString()%>
                </td>
              </tr>
              <%
                }
              %>
  		    </logic:present>
    	  </table>
		</td>
	  </tr>
	</table>  
  </div>  
  <p>
  <%
	  //set up the request attributes needed to display the view link
    request.setAttribute("resultsFormDefinitionId", myForm.getResultsFormDefinitionId());
    request.setAttribute("resultsFormDefinitions", myForm.getResultsFormDefinitions());
  %>
  <jsp:include page="/hiddenJsps/library/TissueDetailTable.jsp" flush="true">
    <jsp:param name="includeItemSelector" value="false"/>
    <jsp:param name="includeSelectViewLink" value="true"/>
    <jsp:param name="groupedByCase" value="true"/>
  </jsp:include>
  <p>
    <div align="center">
      <input type="button" name="Button" value="Print" onClick="window.print()" class="noprint">
      &nbsp;&nbsp;&nbsp;
	  <input type="button" value="Close" onclick="window.close();" class="noprint">
    </div>
  </logic:present>
  <logic:notPresent name="requestDtoForm" property="createDate">
    <div align="center">
      Request <bean:write name="requestDtoForm" property="requestId"/> could not be found.
      <p>
	  <input type="button" value="Close" onclick="window.close()" class="noprint">
    </div>
  </logic:notPresent>
  </div>
</body>
</html>
