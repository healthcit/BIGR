<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%@ page import="com.ardais.bigr.api.ApiProperties" %>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<html>
<head>
<title>Business Objects</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Start Business Objects';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}

function startBusObj() {

	// need to get the URL from api.properties
	var url = null;
	url = "<%=ApiProperties.getProperty("api.reports.serverURL")%>";
	
	if (url != null) {

		// ok, start it up
  		var w = window.open(url,
    	'_blank',
    	'scrollbars=yes, status=yes, resizable=yes,width=1000,height=700, top=5, left=5');
  
  		w.focus();
  		}
  		
  	else {
  		alert("cannot find report server property");
  	}
}

function onFormSubmit() {
  setActionButtonEnabling(false);

  return true;
}
   

</script>
</head>

<body class="bigr" onload="initPage();">
<html:errors/>
<html:form action="/orm/reports/busObjStart"
    onsubmit="return(onFormSubmit());">
    
<p>You are about to start Business Objects.
 Click on Start Business Objects and log in with your User ID and Password.
   Under Corporate Documents click on the link for your Donor Institution.  Click on the desired report from the list of reports.  <b>Always</b> click on the Refresh Data icon in the upper right
     hand corner of the report <IMG border="0" src="<html:rewrite page="/images/iconRefresh.gif"/>" width="18" height="18"> and follow the prompts to update with the most current data.
To close the report, click on the X to return to the list of reports.</p>
<p>When you have finished running reports, click on Logout. Close the Business Objects window to return to BIGR&#174; Library</p>    

<input type="button" style="font-size: xx-small;" value="Start Business Objects" onclick="startBusObj();">


</html:form>
</body>
</html>