<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.javabeans.BoxLayoutDto"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%
    String myTitle = ApiFunctions.safeToString(request.getParameter("title"));
    String myBoxId = ApiFunctions.safeToString(request.getParameter("boxID"));
    BoxLayoutDto boxLayoutDto = (BoxLayoutDto)request.getAttribute("boxLayoutDto");
    String highlightColor = null;
    String highlightMessage = null;
    List highlightSampleIds = (List)request.getAttribute("nonExistingSampleIds");
    if (!ApiFunctions.isEmpty(highlightSampleIds)) {
      highlightColor = "blue";
      highlightMessage = "<b>Note:</b>" + Escaper.htmlEscapeAndPreserveWhitespace(" Samples shown in " 
          + highlightColor + " will be created when this box scan is completed.");
    }
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript">

function onFormSubmit() {
  disableActionButtons();
  return true;
}
  
function disableActionButtons() {
	document.form1.Submit.disabled = true;
	document.form1.Rescan.disabled = true;
}

myBanner = '<%= myTitle %>';
</script>
</head>
<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;">
<form name="form1" method="post" action="<html:rewrite page='/iltds/Dispatch'/>"
	  onsubmit="return(onFormSubmit());">
    <% if(request.getAttribute("myError")!=null) { %>
	   <script>
	     alert("<%= request.getAttribute("myError") %>");
	   </script>
       <table width="100%" class="foreground" cellpadding="3" cellspacing="1" border="0">
         <tr class="yellow"> 
           <td> 
             <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError")%> </b></font></div>
          </td>
         </tr>
       </table>
       <br>
    <% } %>
  <%
  	String confirmBoxStorageType = (String)request.getAttribute("confirmBoxStorageType");
  	LegalValueSet storageTypeChoices = (LegalValueSet)request.getAttribute("storageTypeChoices");
  	if (FormLogic.DB_YES.equalsIgnoreCase(confirmBoxStorageType)) {
  %>
  	  <html:hidden property="mustSpecifyStorageType" value="<%=FormLogic.DB_YES%>"/>
      <div align="center">
        Storage type of sample(s) in this box:&nbsp;&nbsp;
	    <bigr:selectList property="storageTypeCid"
	      firstValue="" firstDisplayValue="Select Storage Type"
	      legalValueSet="<%=storageTypeChoices%>"/>
	  </div>
	  <br>
  <%
  	}
  	else {
  	  String defaultStorageTypeCid = (String)request.getAttribute("defaultStorageTypeCid");
  %>
  	  <html:hidden property="mustSpecifyStorageType" value="<%=FormLogic.DB_YES%>"/>
  	  <html:hidden property="storageTypeCid" value="<%=defaultStorageTypeCid%>"/>
  <%
  	}
  %>
  <input type="hidden" name="title" value="<%= myTitle %>">
  <input type="hidden" name="boxID" value="<%= myBoxId %>">
  <input type="hidden" name="boxLayoutId" value="<%=boxLayoutDto.getBoxLayoutId()%>"/>
  <table class="background" cellpadding="0" cellspacing="0" border="0" align="center">
    <tr> 
      <td>                
        <table class="foreground" cellpadding="3" cellspacing="1" border="0">
          <tr class="white"> 
            <td class="grey" width="1%">
              <b>Box&nbsp;ID:</b>
            </td>
            <td width="99%">
              <%= myBoxId %> 
            </td>
          </tr>
          <tr class="white">
          	<td colspan="2">
		          <bigr:box
		            boxLayoutDto="<%=boxLayoutDto%>"
		            boxCellContentVar="smpl"
		            sampleTypeVar="smplType"
		            sampleAliasVar="smplAlias"
		            persistValue="true"
		            cellType="readonly"
		            highlightList="<%=highlightSampleIds%>"
		            highlightColor="<%=highlightColor%>"
		            highlightMessage="<%=highlightMessage%>"
		          />
		        </td>
		      </tr>
          <tr class="white"> 
            <td colspan="2"> 
              <div align="center"> 
                <input type="submit" name="Submit" value="Submit">
                <% 
                	String theURL = "/iltds/Dispatch?op=BoxScanInitial&boxID=";
                	if (request.getParameter("boxID")!=null) {
                		theURL = theURL + myBoxId + "&boxLayoutId=" + boxLayoutDto.getBoxLayoutId();
                	}
                	theURL = theURL + "&title=Box%20Scan%20Details"; 
                	theURL = theURL + "&confirm=false"; 
                %>
								<input type="button" name="Rescan" value="Re-Scan New Samples"
				    			onClick="MM_goToURL('this','<html:rewrite page="<%= theURL %>"/>');return document.MM_returnValue">
                <input type="hidden" name="op" value="BoxScanInsert">
              </div>
            </td>
          </tr>
        </table>
    </td>
  </tr>
</table>
</form>
<script language="JavaScript">
    document.form1.Submit.focus()
</script>
</body>
</html>
