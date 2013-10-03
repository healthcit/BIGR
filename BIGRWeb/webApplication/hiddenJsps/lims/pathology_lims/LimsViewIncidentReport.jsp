<%@ page import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.lims.javabeans.IncidentReportData" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ page import="com.ardais.bigr.lims.helpers.LimsConstants" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%
	IncidentReportData data = (IncidentReportData)request.getAttribute(IncidentReportData.INCIDENT_REPORT_KEY);
	if (data == null) {
		data = new IncidentReportData();
	}
	if (data.getLineItems() == null) {
		data.setLineItems(new ArrayList());
	}

	LegalValueSet actionSet = (LegalValueSet) request.getAttribute("actionSet");	
%>
<html>

<head>
<title>Incident Report</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
<!--

	//method to validate the data on the form
	function validateForm()
	{
	 if(document.forms[0].rowCount==null)
	 {
	 	return false;
	 }
	 var rowCount=document.forms[0].rowCount.value;
	 var validationPassed=true;
	 for(var i=0;i<rowCount;i++)
	 {
	   var action;
	   var reason;
	   var toBeSaved;
	   if (rowCount == 1) {
	     action = document.forms[0].incidentActionList.value;
	     reason = document.forms[0].reasonList.value;
	     toBeSaved = document.forms[0].saveList.value;
	   }
	   else {
	     action = document.forms[0].incidentActionList[i].value;
	     reason = document.forms[0].reasonList[i].value;
	     toBeSaved = document.forms[0].saveList[i].value;
	   }
	   if (toBeSaved == "true") {
	     if (action == null || action =="") {
	       alert('An action must be specified for the incident on row ' + (i+1) + '.');
	       validationPassed = false;
	       break;
	     }
	     if (reason == null || reason =="") {
	       alert('A reason must be specified for the incident on row ' + (i+1) + '.');
	       validationPassed = false;
	       break;
	     }
	   }
	 }
	 ////if the server-side successfully saves the incidents, this page will be closed.  Offer the
	 ////user an opportunity to print the page before that happens
	 //if (validationPassed) {
	 //  if (confirm("This page will close after saving - would you like to print the page before it closes?\n(Press OK to print, Cancel to skip printing)")) {
	 //    window.print();
	 //  }
	 //}
	 return validationPassed;
	}
	
	//method to keep the hidden field used to track if a row should be saved
	//in sync with the checkbox that indicates if the row should be saved.  We 
	//cannot just use the checkboxes themselves, because when the form is submitted 
	//only the checkboxes that have been checked will be sent, and we need a value 
	//for each row.	
	function updateSaveList(index)
	{
		var condition;
		var control;
	 	var rowCount=document.forms[0].rowCount.value;		
		//since arrays are only created when there are multiple rows
		//we have to take that into consideration.
		if (rowCount == 1) {
			condition = document.forms[0].save.checked;
			control = document.forms[0].saveList;
		}
		else {
			condition = document.forms[0].save[index].checked;
			control = document.forms[0].saveList[index];
		}
		if (condition) {
			control.value="true";
		}
		else {
			control.value="false";
		}
	}	
	
	//method to check the "Save" check box on each row.	
	function selectAllRows()
	{
	 	var rowCount=document.forms[0].rowCount.value; 	
		//since arrays are only created when there are multiple rows
		//we have to take that into consideration.
		if (rowCount == 1) {
			document.forms[0].save.checked = true;
			updateSaveList(0);
		}
		else {
	 		for(var i=0;i<rowCount;i++)
		 	{
				document.forms[0].save[i].checked = true;
				updateSaveList(i);
		 	}
		}
	}

	//method that fires when a choice is selected from one of the action dropdowns.
	//it takes in the index of the control and the value from that control, and:
	//1) updates the array containing all of the action selections to have the new value.
	//2) blanks out the existing reason and requestor values, if the new action is different from the
	//   existing action
	//3) if the new value is not the code for "other" it blanks out the appropriate value in the
	//   array of "other" values
	function setAction(index, value) {
	  //if the index is -1, we're dealing with the action drop-down on the "Apply to All" table
	  //and in that case we just need to blank out the existing reason and requestor values.
	  //if the new value is for "Sample Pulled", put a comment into the display value field to
	  //let the user know the pull reason for the individual samples will be used when the "Apply
	  //to All" button is pressed, and don't allow them to compose a reason.
	  if (index == -1) {
        if (value == '<%= LimsConstants.INCIDENT_ACTION_PULL %>') {
          document.forms[0].reasonDisplayValueApplyAll.value = "Sample pull reason will be applied";
          document.forms[0].composeReasonApplyAll.disabled = true;
          document.forms[0].applyReasonApplyAll.disabled = true;
        }
        else {
          document.forms[0].reasonDisplayValueApplyAll.value = "";
          document.forms[0].composeReasonApplyAll.disabled = false;
          document.forms[0].applyReasonApplyAll.disabled = false;
        }
        document.forms[0].reasonApplyAll.value = "";
        document.forms[0].requestorCodeApplyAll.value = "";
	  }
	  else {
	 	var rowCount=document.forms[0].rowCount.value;
	 	//if the new value is different from the existing value assign the new value to the control 
	 	//and blank out any existing reason/requestor values.  We do this because the reason
		//is specific to the action, so if we've changed the action the reason is no longer valid.
		//Also, if the selected value is not the choice for "other", clear the value in the 
		//"other" array.  If we don't do this, if the user selects the choice for "other" 
		//from the drop down and types something into the other text box, and then
		//selects a non-"other" value from the dropdown, the "other" array will continue to
		//hold the old "other" value.  If the new value is the code for "Sample Pull", default
		//the reason to the sample's pull reason.
		if (rowCount == 1) {
		  if (document.forms[0].incidentActionList.value != value) {
			document.forms[0].incidentActionList.value = value;
			document.forms[0].reasonDisplayValue.value = "";
			document.forms[0].reasonList.value = "";
			document.forms[0].requestorCodeList.value = "";
		    if (value != "<%= LimsConstants.INCIDENT_ACTION_OTHER %>") {
		      setOtherAction(index,"");
		    }
		    if (value == '<%= LimsConstants.INCIDENT_ACTION_PULL %>') {
		      document.forms[0].reasonDisplayValue.value = document.forms[0].pullReason.value;
			  document.forms[0].reasonList.value = document.forms[0].pullReason.value;
		    }
		  }
		}
		else {
		  if (document.forms[0].incidentActionList[index].value != value) {
			document.forms[0].incidentActionList[index].value = value;
			document.forms[0].reasonDisplayValue[index].value = "";
			document.forms[0].reasonList[index].value = "";
			document.forms[0].requestorCodeList[index].value = "";
		    if (value != "<%= LimsConstants.INCIDENT_ACTION_OTHER %>") {
		      setOtherAction(index,"");
		    }
		    if (value == '<%= LimsConstants.INCIDENT_ACTION_PULL %>') {
		      document.forms[0].reasonDisplayValue[index].value = document.forms[0].pullReason[index].value;
			  document.forms[0].reasonList[index].value = document.forms[0].pullReason[index].value;
		    }
		  }
		}
	  }	
	}

	//method to store the value of a given "other" text box into the array used to track the
	//values of all the "other" text boxes
	function setOtherAction(index, value) {
 	  var rowCount=document.forms[0].rowCount.value;
	  if (rowCount == 1) {
		document.forms[0].otherIncidentActionList.value = value;
	  }
	  else {
		document.forms[0].otherIncidentActionList[index].value = value;
	  }
	}

	//method to copy the value of the action dropdown (and it's other text value)
	//in the "Apply to All" table to all the action dropdowns.  This table is shown
	//only when there are multiple rows, so we don't need to check for that condition
	function applyActionToAll() {
	  //if no choice has been specified from the action dropdown tell the user
	  //and return
	  if (document.forms[0].incidentActionApplyAll.value == "") {
		alert('You must select a choice from the dropdown before taking this action.');
		return;
	  }
	  else {
	    var rowCount=document.forms[0].rowCount.value;
	    var selectedIndex = document.forms[0].incidentActionApplyAll.selectedIndex;
		var otherText = document.forms[0].otherIncidentActionApplyAll.value;
		for (var i=0; i<rowCount; i++) {
		  document.forms[0].incidentAction[i].selectedIndex = selectedIndex;
		  document.forms[0].incidentAction[i].fireEvent("onchange");
		  //if the selection is anything but "other", firing the onchange event above
		  //takes care of setting the value in the other text box and updating the array of other
		  //values (since setAction is called in the onchange event of the dropdown),
		  //so only set the value of the other text box if the choice in the dropdown is other
		  if (document.forms[0].incidentActionApplyAll.value == "<%= LimsConstants.INCIDENT_ACTION_OTHER %>") {
		    var control = document.getElementById("otherIncidentAction" + i);
		    control.value = otherText;
		    control.fireEvent("onblur");
		  }
		}
	  }
	}
	
	//method to bring up the "Reason" pop up as a modal window
	function getReasonAndRequestorInfo(index) {
	  var actionControl;
	  //only allow the user to bring up the pop-up if they've specified an action.  To enforce
	  //this, we must look at the appropriate action drop-down.  If the index passed in is -1 
	  //then we're dealing with the "Apply to All" action drop-down, otherwise it's the action
	  //drop-down on a row
	  if (index == -1) {
	    actionControl = document.forms[0].incidentActionApplyAll;
	  }
	  else {
	    var rowCount=document.forms[0].rowCount.value;
		if (rowCount == 1) {
		  actionControl = document.forms[0].incidentAction;
		}
		else {
		  actionControl = document.forms[0].incidentAction[index];
		}			
	  }
	  var action = actionControl.value;
	  if (action == null || action == "") {
		alert('You must specify an action before specifying a reason.');
		return;
	  }		
	  //bring up the reason pop-up
	  var reasonText = actionControl.options[actionControl.selectedIndex].text;
      var reasonInfo = window.showModalDialog('/BIGR/lims/limsIncidentReason.do?reasonText=' + reasonText + '&actionCode=' + action,null,'resizable:yes;help:no;dialogWidth:500px;dialogHeight:520px');      	
      //if anything was returned from the pop-up, update the appropriate reason/requestor control.  If the 
      //index passed in is -1 then we're dealing with the "Apply to All" reason/requestor, otherwise it's the 
      //reason/requestor on a row
      if (reasonInfo != null) {
        var reasonDisplayValueControl;
        var reasonControl;
        var requestorCodeControl;
	    if (index == -1) {
	      reasonDisplayValueControl = document.forms[0].reasonDisplayValueApplyAll;
	      reasonControl = document.forms[0].reasonApplyAll;
	      requestorCodeControl = document.forms[0].requestorCodeApplyAll;
	    }
	    else {
	      var rowCount=document.forms[0].rowCount.value;
      	  if (rowCount == 1) {
		    reasonDisplayValueControl = document.forms[0].reasonDisplayValue;
		    reasonControl = document.forms[0].reasonList;
		    requestorCodeControl = document.forms[0].requestorCodeList;
      	  }
      	  else {
		    reasonDisplayValueControl = document.forms[0].reasonDisplayValue[index];
		    reasonControl = document.forms[0].reasonList[index];
		    requestorCodeControl = document.forms[0].requestorCodeList[index];
		  }
		}
		reasonDisplayValueControl.value = reasonInfo.reasonText;
		reasonControl.value = reasonInfo.reasonText;
		requestorCodeControl.value = reasonInfo.requestorCode;
	  }
	}
	
	//method to prevent the user from entering <RETURN> in a control
	function checkEnter(event) {
		var code = 0;
		code = event.keyCode;		
		if (code == 13) {
			return false;
	    }
	}	
	
	//method to copy the reason and requestor value in the "Apply to All" table to all the 
	//reason/requestor controls.  This table is shown only when there are multiple rows, 
	//so we don't need to check for that condition
	function applyReasonAndRequestorToAll() {
	  //if no reason/requester has been specified tell the user and return
	  if (document.forms[0].reasonApplyAll.value == "") {
		alert('You must compose a reason before taking this action.');
		return;
	  }
	  else {
		//only copy the reason to those rows that have the same action selected
	    var rowCount=document.forms[0].rowCount.value;
		var selectedActionCode = document.forms[0].incidentActionApplyAll.value;
		for (var i=0; i<rowCount; i++) {
		  if (document.forms[0].incidentAction[i].value == selectedActionCode) {
		    document.forms[0].reasonDisplayValue[i].value = document.forms[0].reasonDisplayValueApplyAll.value;
			document.forms[0].reasonList[i].value = document.forms[0].reasonApplyAll.value;
			document.forms[0].requestorCodeList[i].value = document.forms[0].requestorCodeApplyAll.value;
		  }
		}
	  }
	}	
-->
</script>
</head>
<body>
<html:form method="post" action="/lims/limsCreateIncidents"
	onsubmit="return validateForm();">
	<input type="hidden" name="rowCount" value="<%=data.getLineItems().size()%>">
	
<div align="center"> 
  <logic:present name='<%= com.ardais.bigr.lims.javabeans.IncidentReportData.INCIDENT_REPORT_KEY %>'>
  <bean:define name='<%= com.ardais.bigr.lims.javabeans.IncidentReportData.INCIDENT_REPORT_KEY %>' id="report" type="com.ardais.bigr.lims.javabeans.IncidentReportData"/>
  <table border="0" cellspacing="1" cellpadding="2" class="fgTableSmall">
    <tr class="yellow"> 
      <td>
        <div align="center"><b>Pathology QC Incident Report</b></div>
      </td>
    </tr>
  </table>
  <br>
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="fgTableSmall">
	<logic:present name="org.apache.struts.action.ERROR">
	  <tr class="yellow"> 
	    <td colspan="10"> 
	      <div align="left">
	        <font color="#FF0000">
	          <b>
	            <html:errors/>
	          </b>
	        </font>
	      </div>
	    </td>
	  </tr>
	</logic:present>
  </table>
  <br>
  <table border="0" cellspacing="1" cellpadding="2" class="fgTableSmall">
    <tr class="grey"> 
      <td nowrap>
        <b>Created by:</b>
      </td>
      <td nowrap class="white">
        <%= request.getSession(false).getAttribute("user") %>
      </td>
    </tr>
    <tr class="grey"> 
      <td nowrap>
        <b>Created on:</b>
      </td>
      <td nowrap class="white">
        <%= Calendar.getInstance().getTime() %>
      </td>
    </tr>
  </table>
  <br>
  <table border="0" cellspacing="1" cellpadding="2" class="fgTableSmall">
    <tr class="grey"> 
      <td nowrap align="center">
        <b>General Comments</b>
      </td>      
    </tr>
    <tr class="white"> 
      <td width="470" nowrap>
        <html:textarea property="generalComments" value="<%= data.getGeneralComments() %>" 
          style="font-size:xx-small;overflow:visible" rows="10" cols="90"
          onkeyup="maxTextarea(2000);"/>
      </td>      
    </tr>
  </table>
  <% if (data.getLineItems().size() > 1) { %>
    <br>
    <div align="center">
      <table border="0" cellspacing="0" cellpadding="2" class="fgTableSmall">
        <tr class="yellow">
          <td nowrap colspan="2">
            <div align="center">
              <b>
                Apply to all rows
              </b>
            </div>
          </td>
        </tr>
        <tr class="green">
          <td nowrap align="center">
            <b>Action</b>
          </td>
          <td nowrap align="center">
            <b>Reason</b>
          </td>
        </tr>
        <tr class="white">
          <td>
  		    <table cellspacing="0" cellpadding="2" border="0">
              <tr>
                <td nowrap style="border: none">
                  <div align="left">
		            <bigr:selectListWithOther 
		              style="font-size:xx-small;"
		              property="incidentActionApplyAll"
		              otherProperty="otherIncidentActionApplyAll"
		              onchange="setAction(-1, this.value);"
		              legalValueSet="<%= actionSet %>"
		              otherCode="<%= LimsConstants.INCIDENT_ACTION_OTHER %>"
		              firstValue="" firstDisplayValue=""/>
		          </div>
		        </td>
		      </tr>
		      <tr>
                <td style="border: none">
                  <div align="left">
                    If other selected:<br>
	                <input type="text"
		              style="font-size:xx-small;"
		              name="otherIncidentActionApplyAll"
		              value="N/A"
		              onkeydown="return checkEnter(event);"
		              disabled
		              size="27"
		              maxlength="200"/>
		          </div>
		        </td>
		      </tr>
		      <tr>
		        <td style="border: none" colspan="2">
		          <div align="center">
		            <input type="button"
		              value="Apply to All"
		              style="font-size:xx-small;"
		              onClick="applyActionToAll();"/>
		          </div>
		        </td>
		      </tr>
		    </table>
          </td>
          <td>
  		    <table cellspacing="0" cellpadding="2" border="0">
              <tr>
                <td style="border: none" nowrap width="200">
		          <div align="center">
      			    <html:textarea property="reasonDisplayValueApplyAll" disabled="true" value="" style="font-size:xx-small;overflow:visible" rows="2" cols="35"/>
      			    <html:hidden property="reasonApplyAll" value=""/>
      			    <html:hidden property="requestorCodeApplyAll" value=""/>
		          </div>
                </td>
              </tr>
		      <tr>
		        <td style="border: none" nowrap width="200">
		          <div align="center">
		            <input type="button"
		              value="Compose"
		              id="composeReasonApplyAll"
		              style="font-size:xx-small;"
		              onClick="getReasonAndRequestorInfo(-1);"/>
		            &nbsp;&nbsp;
		            <input type="button"
		              value="Apply to All"
		              id="applyReasonApplyAll"
		              style="font-size:xx-small;"
		              onClick="applyReasonAndRequestorToAll();"/>
		          </div>
		        </td>
		      </tr>
		    </table>
          </td>
        </tr>
      </table>
    </div>
  <% } %>
  <br>
  <logic:present name="report" property="lineItems">
  <table border="0" cellspacing="0" cellpadding="2" class="fgTableSmall">
    <tr class="green"> 
      <td rowspan="2" nowrap align="center">
        <b>Mark<br>to<br>Save</b>
      </td>
      <td rowspan="2" nowrap align="center">
        <b>Sample ID</b>
      </td>
      <td nowrap align="center">
        <b>Case ID</b>
      </td>
      <td nowrap align="center">
        <b>Slide ID</b>
      </td>
      <td rowspan="2" nowrap align="center">
        <b>Action</b>
      </td>
      <td rowspan="2" nowrap align="center">
        <b>Reason</b>
      </td>
    </tr>
    <tr class="green"> 
      <td nowrap align="center">
        <b>Asm Position</b>
      </td>
      <td nowrap align="center">
        <b>Pathologist</b>
      </td>
    </tr>

    <logic:iterate name="report" property="lineItems" indexId="index" id="lineItem" type="com.ardais.bigr.lims.javabeans.IncidentReportLineItem">
    <tr class="white"> 
      <td align="center" rowspan="2">
        <% if (lineItem.isSave()) { %>
      	  <input type="checkbox" name="save" checked onClick="updateSaveList(<%= index.intValue() %>);">
      	  <html:hidden property="saveList" value="true"/>
    	<% } else { %>
      	  <input type="checkbox" name="save" onClick="updateSaveList(<%= index.intValue() %>);">
      	  <html:hidden property="saveList" value="false"/>
    	<% } %>
      </td>
      <td align="center" rowspan="2">
      	<bean:write name="lineItem" property="sampleId"/>  
      	<html:hidden property="sampleIdList" value="<%= lineItem.getSampleId() %>"/>
      </td>
      <td>
      	<bean:write name="lineItem" property="caseId"/>
      	<html:hidden property="caseIdList" value="<%= lineItem.getCaseId() %>"/>
      </td>
      <td>
      	<bean:write name="lineItem" property="slideId"/>  
      	<html:hidden property="slideIdList" value="<%= lineItem.getSlideId() %>"/>
      </td>
      <td rowspan="2">
  		<table cellspacing="0" cellpadding="2" border="0">
          <tr>
            <td style="border: none">
              <div align="left">
		        <bigr:selectListWithOther 
		          style="font-size:xx-small;"
		          property="incidentAction"
		          otherProperty='<%= "otherIncidentAction" + index %>'
		          onchange='<%="setAction("+index+", this.value);"%>'
		          legalValueSet="<%= actionSet %>"
		          otherCode="<%= LimsConstants.INCIDENT_ACTION_OTHER %>"
		          firstValue="<%= lineItem.getAction() %>"
		          firstDisplayValue="<%= actionSet.getDisplayValue(lineItem.getAction()) %>"/>
		      </div>
		    </td>
		    <html:hidden property="incidentActionList" value="<%= lineItem.getAction() %>"/>
		  </tr>
		  <tr>
            <td style="border: none">
              <div align="left">
              If other selected:<br>
	            <input type="text"
		          style="font-size:xx-small;"
		          name='<%= "otherIncidentAction" + index %>'
		          <% if (!lineItem.getAction().equals(LimsConstants.INCIDENT_ACTION_OTHER)) { %>
		            value="N/A"
		            disabled
		          <% } else { %>
		            value="<%= lineItem.getActionOther() %>"
		          <% } %>
		          onblur='<%="setOtherAction("+index+", this.value);"%>'
		          onkeydown="return checkEnter(event);"
		          size="27"
 		          maxlength="200"/>
		      </div>
		    </td>
		    <% if (!lineItem.getAction().equals(LimsConstants.INCIDENT_ACTION_OTHER)) { %>
		      <html:hidden property="otherIncidentActionList" value=""/>
		    <% } else { %>
		      <html:hidden property="otherIncidentActionList" value="<%= lineItem.getActionOther() %>"/>
		    <% } %>
		    <% if (lineItem.getPullReason() == null || lineItem.getPullReason().trim().equals("")) { %>
		      <html:hidden property="pullReason" value=""/>
		    <% } else { %>
		      <html:hidden property="pullReason" value="<%= lineItem.getPullReason() %>"/>
		    <% } %>
		  </tr>
		</table>
      </td>
      <td rowspan="2" width="199">
  		<table cellspacing="0" cellpadding="2" border="0">
          <tr>
            <td style="border: none" width="200">
		      <div align="center">
      			<html:textarea property="reasonDisplayValue" disabled="true" value="<%= lineItem.getReason() %>" style="font-size:xx-small;overflow:visible" rows="2" cols="35"/>
      			<html:hidden property="reasonList" value="<%= lineItem.getReason() %>"/>
      			<html:hidden property="requestorCodeList" value="<%= lineItem.getRequestorCode() %>"/>
		      </div>
            </td>
          </tr>
		  <tr>
		    <td style="border: none" width="200">
		      <div align="center">
		        <input type="button"
		          value="Compose"
		          style="font-size:xx-small;"
		          onClick="getReasonAndRequestorInfo(<%= index %>);"/>
		      </div>
		    </td>
		  </tr>
		</table>
      </td>
    </tr>
    <tr class="white"> 
      <td>
      	<bean:write name="lineItem" property="asmPosition"/>  
      	<html:hidden property="asmPositionList" value="<%= lineItem.getAsmPosition() %>"/>
      </td>
      <td>
      	<bean:write name="lineItem" property="pathologist"/>  
      	<html:hidden property="pathologistList" value="<%= lineItem.getPathologist() %>"/>
      </td>
    </tr>
    <tr><td><td><td><td><td><td></tr>
    <tr><td><td><td><td><td><td></tr>
  	</logic:iterate>
  </table>
  </logic:present>
  	<% if (data.getLineItems().size() > 0) { %>
  		<div align="left">
  		<br>
	  	<input type="button" class="noprint" value="Select All" onClick="selectAllRows();">
	  	</div>
	<% } %>
  <br>
  <table width="100%" border="0" cellspacing="0" cellpadding="2">
  	<% if (data.getLineItems().size() > 0) { %>
      <tr colspan="2" class="noprint">
        <td align="left">
	      <font color="#FF0000" style="font-size:x-small;">
	        <b>
	          Note: This window will close after saving.  To print a copy of this screen press the "Print" button before saving.
	        </b>
	      </font>
	    </td>
	  </tr>
	<% } %>
    <tr>
      <td align="left">
  	    <% if (data.getLineItems().size() > 0) { %>
	        <input type="submit" class="noprint" value="Save">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <% } %>
	        <input type="button" class="noprint" value="Cancel" onClick="window.close()">
	  </td>
	  <td align="right">
    	<input type="button" value="Print" onClick="window.print()" class="noprint">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
  </table>       
  </logic:present>
  <logic:notPresent name="<%= com.ardais.bigr.lims.javabeans.IncidentReportData.INCIDENT_REPORT_KEY %>">
  No Data has been found for the Path QC Incident Report.
  </logic:notPresent>
</div>
</html:form>
</body>
</html>
