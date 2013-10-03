<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%
        com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<html>
<head>
<script type="text/javascript">
  function checkEnter(event){ 	
	var code = 0;
	code = event.keyCode;
	
	//alert(code);
  
	  if(code == 13){
 	    return false;
      }
  }


function onLoadPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = mybanner;
}

mybanner = 'Slide Label Generation';

 

function scanUser(){
    event.returnValue = false; // for MR 5103
	document.forms[0].action = '<html:rewrite page="/lims/limsLabelGen.do"/>';
	document.forms[0].submit();
}

function scanBlock(){
    event.returnValue = false; // for MR 5103
	document.forms[0].action = '<html:rewrite page="/lims/limsLabelGen.do"/>';
	document.forms[0].submit();
}

var numSamples = 0;

function removeRow(obj){
	var allRows = document.all.slideTable.rows;
	for(j = 0 ; j < allRows.length ; j++){
		var submittedRow = allRows[j];
		if(obj == submittedRow.id){
		   document.all.slideTable.deleteRow(j);
		   numSamples--;
		} 
	}

	if(numSamples == 0){
		document.all.getLabels.disabled = true;
	}
	document.forms[0].sampleId.focus();
}



</script>

<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr" onLoad="onLoadPage();">

<html:form action="/lims/limsLabelGen.do">
<DIV align="center">
   
            <table id="slideTable" class="fgTableSmall" cellpadding="2" cellspacing="0" border="0" width="400">
            <% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
            <tr class="yellow"> 
              <td colspan="3"> 
                <div align="center"><font color="#FF0000"><b>
                <html:errors/></b></font></div>
              </td>
            </tr>
            <% } %>
          
            <tr id="header" class="green" > 
              <td colspan="3"> 
                <div align="center"> User ID:
                              
                              
               <logic:present name="limsLabelGenForm" property="limsUserId">
               	  <b><bean:write name="limsLabelGenForm" property="limsUserId"/></b>
               	  <html:hidden name="limsLabelGenForm" property="limsUserId"/>
               </logic:present>
               <logic:notPresent name="limsLabelGenForm" property="limsUserId">
                  <html:password property="limsUserId" size="12" maxlength="12" onchange="scanUser();" onkeydown='return checkEnter(event);'/>
                  <script>document.forms[0].limsUserId.focus();</script>
               </logic:notPresent>
                                     
                                </div>
              </td>
            </tr>
            <tr id="header" class="green"> 
              <td width="100">  
                <div align="center">Block Label</div>
              </td>
              <td width="150">  
                <div align="center">Number of Slides</div>
              </td>
              <td width="130">  
                <div align="center">Remove</div>
              </td>
            </tr>
            <bean:define id="showPulledLegend" type="String" value="false"/>
            <bean:define id="showRequestedLegend" type="String" value="false"/>
            <logic:present name="limsLabelGenForm" property="sampleList">
            
            <bean:define id="pulled" name="limsLabelGenForm" property="isPulledList" type="java.lang.String[]"/>
            <bean:define id="requested" name="limsLabelGenForm" property="isRequestedList" type="java.lang.String[]"/>
                        
            <logic:iterate id="sample" name="limsLabelGenForm" property="sampleList" indexId="index">
            <tr id='row<%= index%>' class="white"> 
            	<script>numSamples++;</script>
                <td width="100" height="25"> 
                  <bean:write name="sample"/>
                  <html:hidden property="sampleList" value='<%= sample.toString() %>'/>
                  <bean:define id="isPulledValue" value='<%= (pulled[index.intValue()] != null)?pulled[index.intValue()]:null%>'/>
                  <logic:match name="isPulledValue" value="true">
				  	<html:img page="/images/Error.gif"/>
				  	 <% showPulledLegend = "true"; %>
				  </logic:match>
				  <html:hidden property="isPulledList" value='<%= pulled[index.intValue()] %>'/>
				  <bean:define id="isRequestedValue" value='<%= (requested[index.intValue()] != null)?requested[index.intValue()]:null%>'/>
                  <logic:notMatch name="isRequestedValue" value="true">
                  	<html:img page="/images/Warning.gif"/>
                  	 <%showRequestedLegend = "true";%>
				  </logic:notMatch>
				  <html:hidden property="isRequestedList" value='<%= requested[index.intValue()] %>'/>
              </td>
                <td width="150" height="25"> 
                  <div align="center"> 
                  <bean:define id="count" name="limsLabelGenForm" property="slideCountlist" type="java.lang.String[]"/>
                  <html:text property="slideCountlist" value='<%= count[index.intValue()] %>' size="3" maxlength="3" onkeydown='return checkEnter(event);'/>
                </div>
              </td>
                <td width="130" height="25"> 
                  <div align="center">
                  <input type="button" name="removeSample" value="Remove" onclick="removeRow('row' + <%= index %>)" >
                  </div>
                </td>
            </tr>
            </logic:iterate>       
            </logic:present>
            <logic:present name="limsLabelGenForm" property="limsUserId">
            <tr class="white"> 
                <td width="100" height="25"> 
                <html:text name="limsLabelGenForm" property="sampleId" onchange="scanBlock()" maxlength="10" size="10" onkeydown='return checkEnter(event);'/>
                <script>document.forms[0].sampleId.focus();</script>
                
              </td>
                <td width="150" height="25"> 
                  <div align="center"> 
                  <html:text property="slideCountlist" value="1" size="3" maxlength="3" disabled="true" onkeydown='return checkEnter(event);'/>
                  <html:hidden property="slideCountlist" value="1"/>
                </div>
              </td>
                <td width="130" height="25"> 
                  <div align="center">
                  
                  </div>
                </td>
            </tr>
            </logic:present>
            
            <tr class="white"> 
              <td colspan="3"> 
                <div align="center"> 
               	<logic:present name="limsLabelGenForm" property="sampleList">
                  <html:submit property="getLabels" value="Get Label(s)"/>
                </logic:present>
                <logic:notPresent name="limsLabelGenForm" property="sampleList">
                  <html:submit property="getLabels" value="Get Label(s)" disabled="true"/>
                </logic:notPresent>
                </div>
              </td>
            </tr>
            
            <%if (showRequestedLegend.equals("true") || 
                  showPulledLegend.equals("true")) {%>     
            <tr class="white">
              <td colspan="3">
              <div align="center">
                <%if (showRequestedLegend.equals("true")) {%>
                  <html:img page="/images/Warning.gif"/><font size="1">This 
                  sample has not been ordered for Pathology Verification or R&amp;D.</font>
                <%}%>
                <%if (showPulledLegend.equals("true")) {%> 
                  <br>
                  <html:img page="/images/Error.gif"/><font size="1">This 
                  sample has been pulled.</font>
                  <%}%>
                </div>
              </td>                   
            </tr>
            <%}%> 
          </table>
    
         
</div>
</html:form>
</body>
</html>
