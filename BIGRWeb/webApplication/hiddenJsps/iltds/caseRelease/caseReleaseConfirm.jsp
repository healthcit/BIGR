<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}

function onClickCancel() {
  window.location = '<html:rewrite page="/iltds/Dispatch?op=CaseReleaseStart"/>';
}

myBanner = '<%= ((request.getParameter("title")!=null)?request.getParameter("title"):"") %>';
//-->
</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<div align="center"> 
  <form name="form1" method="post" action="<html:rewrite page="/iltds/Dispatch"/>">
    <table border="0" cellspacing="0" cellpadding="0" class="background">
      <tr> 
        <td> 
          <table border="0" cellspacing="1" cellpadding="3" class="foreground">
            <% if (request.getAttribute("myError")!=null) { %><tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError") %></b></font></div>
              </td>
            </tr><% } %>
            <tr class="white"> 
              <td class="grey" nowrap>Case ID:</td>
              <td nowrap><%= ((request.getParameter("consentID_1")!=null)?request.getParameter("consentID_1"):"") %> </td>
            </tr>
			<tr class="white"> 
              <td class="grey" nowrap>Pathology Diagnosis:</td>
              <td nowrap>
<% String diagnosis = (String)request.getAttribute("diagnosis");
			  		if (diagnosis != null && diagnosis.equalsIgnoreCase("Other Diagnosis")) {
						out.print(request.getParameter("OtherDiagnosis"));
						} else out.print(diagnosis);  %> </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="hidden" name="consentID" value="<%= ((request.getParameter("consentID_1")!=null)?request.getParameter("consentID_1"):"") %>">
                  <input type="hidden" name="diagnosis" value="<%= request.getParameter("DiseaseType") %>">
                  <input type="hidden" name="otherDiagnosis" value="<%= ((request.getParameter("OtherDiagnosis")!=null)?request.getParameter("OtherDiagnosis"):"") %>">
                  <input type="hidden" name="myError" value="Successful">
                  <input type="hidden" name="op" value="CaseReleaseInsert">
                  <input type="submit" name="Submit" value="Submit">
				  <input type="button" name="Cancel" value="Cancel" onclick="onClickCancel();">
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </form>
</div>
<script language="JavaScript">
		document.form1.Submit.focus()
		</script>
</body>
</html>
