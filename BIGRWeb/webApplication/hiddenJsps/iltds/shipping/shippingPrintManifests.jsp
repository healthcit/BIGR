<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Print Manifests</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
var myBanner = 'Print Manifests';

function onClickPrintManifest(manifestNumber) {
  var theUrl = '<html:rewrite page="/iltds/shipping/printManifest.do"/>?autoPrint=true&manifestNumber=' + manifestNumber;
  window.open(theUrl, 'BIGRManifest',
    'scrollbars=yes,resizable=yes,width=700,height=700,top=0');
}

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}
//-->
</script>
</head>
<body class="bigr" onload="initPage();">
<div align="center"> 
  <p>&nbsp;</p>
  <form name="form1" onsubmit="return(false);">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <% if (request.getAttribute("myError")!= null && !request.getAttribute("myError").equals("")) { %>
            <tr class="green"> 
              <td colspan="4"> 
                <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError") %> </b></font></div>
              </td>
            </tr>
            <% } %>
            <tr class="yellow"> 
              <td colspan="4"> 
                <div align="center"><b>Print Manifests</b></div>
              </td>
            </tr>
            
            <tr class="yellow"> 
		          <td>Manifest Number:</td>
		          <td>Status:</td>
              <td>Tracking Number:</td>
              <td>Print:</td>
            </tr>
            <% Vector waybillList = (Vector) request.getAttribute("waybillList");%>
            <% if ((waybillList != null) && (waybillList.size() > 0)) { %>
            <% for (int i=0; i<waybillList.size(); i++) {
								   Hashtable tmpHash = (Hashtable) waybillList.elementAt(i);
								   String waybill = (String) tmpHash.get("waybill");
								   String manifestStatus = (String) tmpHash.get("status");
								   String manifest = (String) tmpHash.get("manifest");
								   String manifestStatusDisplay = manifestStatus;
								   if (FormLogic.MNFT_MFCREATED.equals(manifestStatus)) {
								     manifestStatusDisplay = "Created";
								   }
								   else if (FormLogic.MNFT_MFPACKAGED.equals(manifestStatus)) {
								     manifestStatusDisplay = "Packaged";
								   }
								   else if (FormLogic.MNFT_MFSHIPPED.equals(manifestStatus)) {
								     manifestStatusDisplay = "Shipped";
								   }
								   %>
            <tr class="<%= ((i % 2) == 0) ? "white" : "grey" %>"> 
              <td><%= manifest %> </td>
              <td><%= manifestStatusDisplay %> </td>
			        <td><%= waybill %></td>
              <td> 
                <input type="button" name="btnPrintManifest" value="Print Manifest"
                  onclick="onClickPrintManifest('<%= manifest %>');">
              </td>
            </tr>
            <% } %>
            <% } %>
          </table>
        </td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>