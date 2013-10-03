<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<html>
<head>
<title>Pathology Verification Start</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript">
function scanValue(){
	window.query.style.display='none';
	window.processing.style.display='block';
	document.forms[0].action = '<html:rewrite page="/lims/limsSlideQueryStart.do"/>';
	document.forms[0].submit();
}
function initializeForm() {
	window.processing.style.display='none';
	//MR6361. If this is the topmost window, don't try to set any properties on the parent
	if (this != parent) {
	  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = 'Sample Evaluations';
	}
	document.forms[0].id.focus();
}
</script>
</head>
<body class="bigr" onLoad="initializeForm();">
<div id="query" align="center"> 
  <html:form action="/lims/limsSlideQueryStart.do" onsubmit="window.query.style.display='none';window.processing.style.display='block';">
    <table cellpadding="0" cellspacing="0" border="0" class="background">
      <tr> 
        <td> 
          <table border="0" cellspacing="1" cellpadding="3" class="foreground">
            <logic:present name="org.apache.struts.action.ERROR">
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><font color="#FF0000"><b>
                <html:errors/></b></font></div>
              </td>
            </tr>
           </logic:present>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><b>Pathology Verification</b></div>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right"><b>Enter Slide/Sample ID:</b></div>
              </td>
              <td> 
                <div align="left"> 
                  <input type="text" name="id" size="10" AUTOCOMPLETE = "off" maxlength="10" onChange="scanValue();">
                </div>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center">                   
                  <input type="submit" name="Submit" value="Submit">
		        </div>
              </td>
            </tr>                 
          </table>
        </td>
      </tr>
    </table>
  </html:form>
</div>
<div id="processing" display="none"> 
  <table align="center" border="0" cellspacing="0" cellpadding="0" class="background" width="300">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
          <tr align="center" class="yellow"> 
            <td><html:img page="/images/processing.gif"/></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
