<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.LineItemHelper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.ProjectHelper"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
/**
 * Allows the user to edit an existing line item or create a new one.
 *
 * @param  JspHelper.ID_HELPER  a <code>LineItemHelper</code> that contains
 *    the values of the existing line item, if editing an existing line item.
 */

com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, application, "P");
LineItemHelper helper = (LineItemHelper)request.getAttribute("helper");
String submitToOp = "PtsLineItemEdit";
String myBanner;
if (helper.isNew()) {
  myBanner = "Create a New Line Item";
}
else {
  myBanner = "Edit a Line Item";
}
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
  document.all.<%=LineItemHelper.ID_QUANTITY%>.focus();
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
  <input type="hidden" name="<%=ProjectHelper.ID_PROJECT_ID%>" value="<%=helper.getProjectId()%>">
<%
if (!helper.isNew()) {
%>
  <input type="hidden" name="<%=LineItemHelper.ID_LINEITEM_ID%>" value="<%=helper.getRawId()%>">
  <input type="hidden" name="<%=LineItemHelper.ID_LINEITEM_NUMBER%>" value="<%=helper.getRawLineItemNumber()%>">
<%
}
%>
  <div align="center"> 
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <%=helper.getMessagesForColspan(2)%>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right">
                <span style="color: red;">*</span> 
                <b>Quantity:</b></div>
              </td>
              <td> 
                <input type="text" name="<%=LineItemHelper.ID_QUANTITY%>" value="<%=helper.getQuantity()%>" size="6" maxlength="5" >
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" > 
                <div align="right"><b>Format:</b></div>
              </td>
                <td>
                <%=helper.getFormatList()%>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" valign="top" align="right"> 
                <span style="color: red;">*</span> <b>Description:</b>
              </td>
              <td>
                <textarea name="<%=LineItemHelper.ID_NOTES%>" cols="60" rows="12" maxlength="4000" onkeyup="maxTextarea();"><%=helper.getNotes()%></textarea>
              </td>
            </tr>
			      <tr class="white"> 
              <td class="grey" valign="top" align="right"> 
                <b>Comments:</b>
              </td>
              <td>
                <textarea name="<%=LineItemHelper.ID_COMMENTS%>" cols="60" rows="4" maxlength="255" onkeyup="maxTextarea();"><%=helper.getComments()%></textarea>
              </td>
            </tr>
            <tr class="yellow"> 
              <td colspan="2" align="left" style="color: red;"> 
                * indicates a required field
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="submit" value="Submit">
                </div>
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
