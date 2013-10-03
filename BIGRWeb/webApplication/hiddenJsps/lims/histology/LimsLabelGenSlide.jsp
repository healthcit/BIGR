<%@ page language="java" import="java.util.*, java.io.*" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%@ page import="com.ardais.bigr.lims.web.form.LimsLabelGenForm" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<html>
<head>
<script>
var numberSlides = 0;
function removeRow(sample, slide){
	var allRows = document.all.slideTable.rows;
	var currentSample = '';
	var currentSampleCount = 1;
	var index = 0;
	for(j = 0 ; j < allRows.length ; j++){
		var submittedRow = allRows[j];
		//if it's a header row, don't check it
		if(submittedRow.id == 'ignore'){
		 	continue;
		}
		
		var divName = 'div' + submittedRow.getAttribute("slide");
		var rowSample = submittedRow.getAttribute("sample");
		//alert('button sample: ' + sample + '\nbutton slide: ' + slide + '\nsubmitted slide ' + submittedRow.getAttribute("slide") + '\ncurrent sample: ' + currentSample + '\ncurrent sample count: ' + currentSampleCount + '\nsubmitted slide ' + submittedRow.getAttribute("sample"));
		
		if(slide == submittedRow.getAttribute("slide")){
		   index = j;
		} 
		else if(currentSample == ''){
			//alert('in currentSample == blank');
			currentSample = rowSample;
			document.all.item(divName).innerText = '1';
			currentSampleCount = 2;
		} 
		else if (currentSample != rowSample){
			//alert('in currentSample != sample');
			currentSample = rowSample;
			document.all.item(divName).innerText = '1';
			currentSampleCount = 2;
		} 
		else {
			//alert('in else ');
			document.all.item(divName).innerText = currentSampleCount;
			currentSampleCount++;
		}
	}

	document.all.slideTable.deleteRow(index);
	numberSlides--;
	if(numberSlides == 0){
		document.all.Submit.disabled = true;
		document.forms[0].action = '<html:rewrite page="/lims/limsLabelGen.do"/>';
		document.forms[0].submit();
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

function applyStainTypeToAll() {
  var root = document.forms[0];
  var index = root.applyStainTypeToAllList.selectedIndex;
  var count = root.procedureList.length;
  for (i=0; i<count; i++) {
  	var ctrl = root.procedureList[i];
  	if ('SELECT' == ctrl.tagName) {
  		ctrl.options[index].selected = true;
  	}
  }
}

</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr">
<html:form action="/lims/limsLabelGenSaveLabel.do">
<DIV align="center">
      <table id="slideTable" class="fgTableSmall" cellpadding="2" cellspacing="0" border="0" width="600">
		<% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
            <tr id="ignore" class="yellow"> 
              <td colspan="6"> 
                <div align="center"><font color="#FF0000"><b>
                <html:errors/></b></font></div>
              </td>
            </tr>
        <% } %>
		<tr id="ignore" sample=" " slide=" " class="green"> 
          <td colspan="6"> 
            <div align="center"><b>Please enter slide information</b></div>
          </td>
        </tr>
	<bean:define id="myForm" name="limsLabelGenForm"
  		type="com.ardais.bigr.lims.web.form.LimsLabelGenForm" />
      <%
      	boolean showApplyToAllList = false;
      	if (myForm.getBlockLabelList()!= null) {
      		showApplyToAllList = myForm.getBlockLabelList().length > 1;
      	}
      	if (showApplyToAllList) {
      %>
		<tr id="ignore" sample=" " slide=" " class="white">
			<td colspan="6" align="center">
			   <br>
			   <b>Procedure:<b>&nbsp;
			   <html:select property="applyStainTypeToAllList" value="H&E">
			   		<% //MAKE SURE THIS OPTION LIST STAYS IN SYNC WITH OPTION LIST BELOW %>
                    <html:option value="Elastic Stain">Elastic Stain</html:option>
                    <html:option value="H&E">H&amp;E</html:option>
                    <html:option value="IHC">IHC</html:option>
  					<html:option value="Safranin O">Safranin O</html:option>
                    <html:option value="Unstained">Unstained</html:option>
                    <html:option value="No Stain: Data/Images from adjacent sample">No Stain: Data/Images from adjacent sample</html:option>
			   </html:select>
			   &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="ApplyToAll" value="Apply to All" onClick="applyStainTypeToAll();">
			   <br>&nbsp;
			</td>
		</tr>
      <%
      	}
      %>
        <tr id="ignore" sample="" slide="" class="green"> 
          <td width="110"> 
            <div align="center"><b>Block Label</b></div>
          </td>
          <td width="110"> 
            <div align="center"><b>Slide Label</b></div>
          </td>
          <td width="110"> 
            <div align="center"><b>Procedure</b></div>
          </td>
          <td width="40"> 
            <div align="center"><b>Cut Level</b></div>
          </td>
          <td width="40"> 
            <div align="center"><b>Slide</b></div>
          </td>
          <td width="90"> 
            <div align="center"><b>Remove</b></div>
          </td>
        </tr>
      <%  
      	String currentSample = "";
      	int currentSlideNumber = 0;
      	boolean firstTime = true;
      %>
      <logic:present name="limsLabelGenForm" property="blockLabelList">
      <logic:iterate id="sample" name="limsLabelGenForm" property="blockLabelList" indexId="index">
        <bean:define id="isNew" name="limsLabelGenForm" property="isNewList" type="java.lang.String[]"/>
        <bean:define id="isNewValue" value='<%= (isNew[index.intValue()] != null)?isNew[index.intValue()]:null%>'/>        
        <bean:define id="procedure" name="limsLabelGenForm" property="procedureList" type="java.lang.String[]"/>
		<bean:define id="slideTemp" name="limsLabelGenForm" property="slideLabelList" type="java.lang.String[]"/>
			<%
             if(currentSample.equals(sample)){
             	currentSlideNumber++;
             } else {
             
             if(firstTime){
             	firstTime = false;
             } 
             else {
             %>
             <tr id="ignore" sample=" " slide=" " class="white">
             	<td colspan="6">
             		&nbsp;
             	</td>
             </tr>
             <%
             }
             	currentSample = (String)sample;
             	currentSlideNumber = 1;
             }
            %>
		
		<tr sample='<%= sample.toString() %>' slide='<%= slideTemp[index.intValue()]%>' class="white"> 
          <td> 
                  <div align="center"><b><bean:write name="sample"/>
                  
                  <html:hidden property="blockLabelList" value='<%= sample.toString() %>'/></b></div>
          </td>
		
          <td width="110"><div align="center">
            
            <%= slideTemp[index.intValue()] %>  
            <html:hidden property="slideLabelList" value='<%= slideTemp[index.intValue()] %>'/>          
            </div>
          </td>
		  <td width="110"> 
                <div align="center"> 
                <logic:match name="isNewValue" value="true">
                <script>numberSlides++;</script>
                <html:select name="limsLabelGenForm" property="procedureList" value='<%= procedure[index.intValue()]%>'>
			   		<% //MAKE SURE THIS OPTION LIST STAYS IN SYNC WITH OPTION LIST ABOVE %>
                    <html:option value="Elastic Stain">Elastic Stain</html:option>
                    <html:option value="H&E">H&amp;E</html:option>
                    <html:option value="IHC">IHC</html:option>
  					<html:option value="Safranin O">Safranin O</html:option>
                    <html:option value="Unstained">Unstained</html:option>
                    <html:option value="No Stain: Data/Images from adjacent sample">No Stain: Data/Images from adjacent sample</html:option>
                </html:select>
                </logic:match>
                <logic:notMatch name="isNewValue" value="true">
                	<%= procedure[index.intValue()]%>
                	<html:hidden property="procedureList" value='<%= procedure[index.intValue()]%>'/>
				</logic:notMatch>
             
                </div>
              </td>
              <td width="40">
              	 
                <div align="center"> 
                <bean:define id="cut" name="limsLabelGenForm" property="cutLevelList" type="java.lang.String[]"/>
                <bean:define id="newSlide" name="limsLabelGenForm" property="isNewList" type="java.lang.String[]"/>
				<html:hidden property="isNewList" value='<%= newSlide[index.intValue()] %>'/>				          
                <logic:match name="isNewValue" value="true">
                <html:text property="cutLevelList" value='<%= cut[index.intValue()] %>' size="3" maxlength="3" onkeydown='return checkEnter(event);'/>
                </logic:match>
                <logic:notMatch name="isNewValue" value="true">
                  	<%= cut[index.intValue()]%>
                  	<html:hidden property="cutLevelList" value='<%= cut[index.intValue()] %>'/>
                </logic:notMatch>
                </div>
              </td>
		  <td width="40"> 
		  	<div id='<%= "div" + slideTemp[index.intValue()]%>' align="center">
        	<%= currentSlideNumber%>
        	</div>
          </td>
		  
          <td valign="bottom" width="90"> 
            <div align="center"> 
            <logic:match name="isNewValue" value="true">
                <input type="button" name="Button" value="Remove" onClick="removeRow('<%= sample.toString() %>', '<%= slideTemp[index.intValue()]%>');">
            </logic:match>
            </div>
          </td>
        
		</tr> 
	  </logic:iterate>
	  	<tr id="ignore" class="white">
	  	 <td colspan="6"><div align="center">
	  	 	<html:submit property="Submit" value="Save Slide(s)"/>
	  	 	<html:hidden name="limsLabelGenForm" property="limsUserId"/>
	  	 	</div>
	  	 </td>
	  	</tr>
	  </logic:present>	
	</table>
</div>
</html:form>
</body>
</html>
