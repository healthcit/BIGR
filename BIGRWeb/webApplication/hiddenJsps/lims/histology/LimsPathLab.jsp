<%@ page import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.lims.web.form.LimsPathLabForm"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<html>
<head>
<SCRIPT>

function MM_callJS(jsStr) {

  return eval(jsStr)
}
var numberSlides = 0;
function scanUser() {
    event.returnValue = false; // for MR 5103
	document.forms[0].submit();
}

function scanSlide() { 
    event.returnValue = false; // for MR 5103
	document.forms[0].submit();
}

function removeRow(obj){
	var allRows = document.all.slideTable.rows;
	for(j = 0 ; j < allRows.length ; j++){
		var submittedRow = allRows[j];
		if(obj == submittedRow.id){
		   document.all.slideTable.deleteRow(j);
		   numberSlides--;
		}
	}
	if(numberSlides == 0){
		document.all.getReport.disabled = true;
	}
	
	document.all.newSlideId.focus();
}

  function checkEnter(event){ 	
	var code = 0;
	code = event.keyCode;
	
	//alert(code);
  
	  if(code == 13){
 	    return false;
      }
  }

<%
	LimsPathLabForm myForm = (LimsPathLabForm)pageContext.findAttribute("limsPathLabForm");	
%>

mybanner = 'Slide Path Lab'; 

</SCRIPT>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML=mybanner;">
<html:form action="/lims/limsPathLab.do">
  <div align="center">
          <table id="slideTable" class="fgTableSmall" cellpadding="2" cellspacing="1" border="0">
            <% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><font color="#FF0000"><b>
                <html:errors/></b></font></div>
              </td>
            </tr>
        	<% } %>
            <tr id="header" class="white"> 
              <td class="grey"> 
                <div align="right">User ID Scan:</div>
              </td>
              <td> 
                <logic:present name="limsPathLabForm" property="limsUserId">
               	  <b><bean:write name="limsPathLabForm" property="limsUserId"/></b>
               	  <html:hidden name="limsPathLabForm" property="limsUserId"/>
               </logic:present>
               <logic:notPresent name="limsPathLabForm" property="limsUserId">
                  <html:password property="limsUserId" size="12" maxlength="12" onchange="scanUser();" onkeydown='return checkEnter(event);'/>
                  <script>document.forms[0].limsUserId.focus();</script>
               </logic:notPresent>
              </td>
             
            </tr>
            <tr id="header" class="white"> 
              <td class="grey"> 
                <div align="right">Slide Label Scan:</div>
              </td>
              <td> 
              	<logic:notPresent name="limsPathLabForm" property="limsUserId">
				<html:text property="newSlideId" size="12" maxlength="10" onchange="scanSlide();" disabled="true" onkeydown='return checkEnter(event);'/>
                </logic:notPresent>
                <logic:present name="limsPathLabForm" property="limsUserId">
				<html:text property="newSlideId" size="12" maxlength="10" onchange="scanSlide();" onkeydown='return checkEnter(event);'/>
				<script>document.forms[0].newSlideId.focus();</script>
				</logic:present>
              </td>
            </tr>
  
            <tr id="header" class="green"> 
              <td> 
                <div align="center">Label Scan</div>
              </td>
              <td> 
                <div align="center">Remove</div>
              </td>
            </tr>
           
           <logic:present name="limsPathLabForm" property="slideIdList">
           <logic:iterate id="slide" name="limsPathLabForm" property="slideIdList" indexId="index">
           <script>numberSlides++;</script>
           <tr id='row<%= index%>' class="white"> 
              <td> 
                <div align="center">
                	<bean:write name="slide"/>
                	<html:hidden property="slideIdList" value='<%= slide.toString() %>'/> 
                </div>
              </td>
              <td> 
                <div align="center"> 
                  <input type="button" name="removeSample" value="Remove" onclick="removeRow('row' + <%= index %>)" >
                </div>
              </td>
            </tr>
            </logic:iterate>
            </logic:present>
            <tr id="header" class="white"> 
              <td colspan=2> 
                <div align="center"> 
                  	<logic:present name="limsPathLabForm" property="slideIdList">
                  	<input type="submit" name="getReport" value="Get Report">
                  	</logic:present>
                  	<logic:notPresent name="limsPathLabForm" property="slideIdList">
                  	<input type="submit" name="getReport" value="Get Report" disabled="true">
                  	</logic:notPresent>

                </div>
              </td>
            </tr>
            
          </table>
    
  </div>
</html:form>
</body>
</html>
