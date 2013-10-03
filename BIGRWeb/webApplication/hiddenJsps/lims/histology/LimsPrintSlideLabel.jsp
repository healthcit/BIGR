<%@ page import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
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
    document.forms[0].action = '<html:rewrite page="/lims/limsPrintSlideLabelScanAll.do"/>';
	document.forms[0].submit();
}

function scanSlide() {
    event.returnValue = false; // for MR 5103
    document.forms[0].action = '<html:rewrite page="/lims/limsPrintSlideLabelScanAll.do"/>';
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
		document.all.printAllSlides.disabled = true;
	}
}

  function checkEnter(event){ 	
	var code = 0;
	code = event.keyCode;
	
	//alert(code);
  
	  if(code == 13){
 	    return false;
      }
  }


mybanner = 'Print Slide Label'; 

</SCRIPT>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr" onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML=mybanner;">
<html:form action="/lims/limsPrintSlideLabel.do">
  <div align="center">
    
          <table id="slideTable" class="fgTableSmall" cellpadding="2" cellspacing="1" border="0">
           <logic:present name="org.apache.struts.action.ERROR">
            <tr class="yellow"> 
              <td colspan="3"> 
                <div align="center"><font color="#FF0000"><b>
                <html:errors/></b></font></div>
              </td>
            </tr>
        	</logic:present>
        	<logic:present name="limsPrintSlideLabelForm" property="message">
		     <tr class="yellow"> 
		       <td colspan="6"> 
		         <div align="center">
		           <font color="#FF0000"><b>
		            <ul><bean:write name="limsPrintSlideLabelForm" property="message" filter="false" /></ul>
		           </b></font>
		         </div>
		       </td>
		     </tr> 
		    </logic:present>
            <tr id="header" class="white"> 
              <td class="grey"> 
                <div align="right">User ID Scan:</div>
              </td>
              <td colspan="2"> 
                <logic:present name="limsPrintSlideLabelForm" property="limsUserId">
               	  <b><bean:write name="limsPrintSlideLabelForm" property="limsUserId"/></b>
               	  <html:hidden name="limsPrintSlideLabelForm" property="limsUserId"/>
               </logic:present>
               <logic:notPresent name="limsPrintSlideLabelForm" property="limsUserId">
                  <html:password property="limsUserId" size="12" maxlength="12" onchange="scanUser();" onkeydown='return checkEnter(event);'/>
                  <script>document.forms[0].limsUserId.focus();</script>
               </logic:notPresent>
              </td>
             
            </tr>
            <tr id="header" class="white"> 
              <td class="grey"> 
                <div align="right">Slide Label Scan:</div>
              </td>
              <td colspan="2"> 
              	<logic:notPresent name="limsPrintSlideLabelForm" property="limsUserId">
				<html:text property="newSlideId" size="12" maxlength="12" onchange="scanSlide();" disabled="true" onkeydown='return checkEnter(event);'/>
                </logic:notPresent>
                <logic:present name="limsPrintSlideLabelForm" property="limsUserId">
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
              <td> 
                <div align="center">Print Label</div>
              </td>
            </tr>            
           <logic:present name="limsPrintSlideLabelForm" property="slideLabelList">
           <bean:define id="hasBeenPrintedArr" name="limsPrintSlideLabelForm" property="hasBeenPrinted" type="java.lang.String[]" />
           <logic:iterate id="slide" name="limsPrintSlideLabelForm" property="slideLabelList" indexId="index">
           <script>numberSlides++;</script>
           <tr id='row<%= index%>' class="<%= (hasBeenPrintedArr[index.intValue()].equals("true")) ? "grey" :"white" %>" > 
              <td> 
                <div align="center">
                  <bean:write name="slide"/><html:hidden property="slideLabelList" value='<%= slide.toString() %>'/>
                    <html:hidden property="hasBeenPrinted" value="<%= hasBeenPrintedArr[index.intValue()] %>"/>
                </div>
              </td>
              <td> 
                <div align="center"> 
                  <input type="button" name="removeSample" value="Remove" onclick="removeRow('row' + <%= index %>)" >
                </div>
              </td>
              <td> 
                <div align="center"> 
                  <input type="submit" value="Print"  name="Print" onClick="document.forms[0].printOneSlide.value='<%= slide.toString() %>';return true;">
                </div>
              </td>
            </tr>
            </logic:iterate>
            </logic:present>
            <tr id="header" class="white"> 
              <td colspan="3"> 
                <div align="center">
                	<input type="hidden" name="printOneSlide" value=""> 
                  	<logic:present name="limsPrintSlideLabelForm" property="slideLabelList">
                  	<input type="submit" name="printAllSlides" value="Print Label(s)">
                  	</logic:present>
                  	<logic:notPresent name="limsPrintSlideLabelForm" property="slideLabelList">
                  	<input type="submit" name="printAllSlides" value="Print Label(s)" disabled="true">
                  	</logic:notPresent>

                </div>
              </td>
            </tr>
            
          </table>
    
  </div>
</html:form>
</body>
</html>
