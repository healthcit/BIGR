<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.JspHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
/**
 * Allows the user to create a new project.  Note that existing projects are
 * edited via the ProjectManage JSP. 
 *
 * @param  JspHelper.ID_HELPER  a <code>ProjectHelper</code> that is used to
 *    help construct this JSP.
 */
ProjectHelper helper = (ProjectHelper)request.getAttribute(JspHelper.ID_HELPER);
String submitToOp = "PtsProjectEdit";
String myBanner = "Create a New Project";
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title><%=myBanner%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript">
<!--
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = "<%=myBanner%>";
  document.all.<%=ProjectHelper.ID_PROJECT_NAME%>.focus();
}

function isValid() {
  return true;
}

function maxTextarea() { 
	var textarea = event.srcElement;
	if (textarea.value.length > textarea.maxlength*1) { 
    textarea.value = textarea.value.substring(0, textarea.maxlength*1);
	  alert("The maximum number of characters you can enter is " + textarea.maxlength);
    return false;
	}
}

//-->
</script>
</head>
<body class="bigr" onLoad="initPage();">
<form name="theForm" method="post" action="<html:rewrite page='/iltds/Dispatch'/>" onSubmit="return isValid();">
  <input type="hidden" name="op" value="<%=submitToOp%>">
  <div align="center"> 
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <%=helper.getMessagesForColspan(2)%>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><span style="color: red;">*</span> <b>Project Name:</b></div>
              </td>
              <td> 
                <input type="text" name="<%=ProjectHelper.ID_PROJECT_NAME%>" value="<%=helper.getProjectName()%>" size="60" maxlength="50" >
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><span style="color: red;">*</span> <b>Client:</b></div>
              </td>
              <td>
                <%=helper.getClientList()%>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Status:</b></div>
              </td>
              <td>
                <%=helper.getStatusList()%>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><span style="color: red;">*</span> <b>Request Date:</b></div>
              </td>
              <td>
                <%=helper.getRequestMonthList()%>
                <%=helper.getRequestDayList()%>
                <%=helper.getRequestYearList()%>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Notes:</b></div>
              </td>
              <td>
                <textarea name="<%=ProjectHelper.ID_NOTES%>" cols="60" rows="4" maxlength="255" onkeyup="maxTextarea();"></textarea>
              </td>
            </tr>
            <tr class="yellow"> 
              <td colspan="2" align="left" style="color: red;"> 
                * indicates a required field
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2" align="center"> 
                <input type="submit" value="Submit">
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
