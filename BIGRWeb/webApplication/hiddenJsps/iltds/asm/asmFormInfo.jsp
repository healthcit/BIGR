<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean"  %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"  %>
<%@ taglib uri="/tld/struts-html"  prefix="html"  %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%		


String asmFormID = (String)((request.getAttribute("asmFormID")!=null)?request.getAttribute("asmFormID"):"");
String consentID = (String)((request.getAttribute("consentID")!=null)?request.getAttribute("consentID"):"");
String grossHour = (String)((request.getAttribute("grossHour")!=null)?request.getAttribute("grossHour"):"");
String grossMinute = (String)((request.getAttribute("grossMinute")!=null)?request.getAttribute("grossMinute"):"");
String grossAmpm = (String)((request.getAttribute("grossAmpm")!=null)?request.getAttribute("grossAmpm"):"");
String removeHour = (String)((request.getAttribute("removeHour")!=null)?request.getAttribute("removeHour"):"");
String removeMinute = (String)((request.getAttribute("removeMinute")!=null)?request.getAttribute("removeMinute"):"");
String removeAmpm = (String)((request.getAttribute("removeAmpm")!=null)?request.getAttribute("removeAmpm"):"");
String formSaved = (String) (request.getAttribute("formSaved"));
String linked = (String) ((request.getAttribute("linked")!=null)?request.getAttribute("linked"):"N");
String procedureDay = (String)((request.getAttribute("procedureDay")!=null)?request.getAttribute("procedureDay"):"");
String procedureMonth = (String)((request.getAttribute("procedureMonth")!=null)?request.getAttribute("procedureMonth"):"");
String procedureYear = (String)((request.getAttribute("procedureYear")!=null)?request.getAttribute("procedureYear"):"");
String procedure_date_yn = (String)((request.getAttribute("procedure_date_yn")!=null)?request.getAttribute("procedure_date_yn"):"N");
String procDateValue = new String();
if (procedure_date_yn.equals("Y")) {
	 procDateValue = (procedureMonth.concat("/")).concat(procedureDay.concat("/")).concat(procedureYear);
}


		int grossHourInt = -1;
		int grossMinuteInt = -1;
		int grossAmpmInt = -1;
		int removeHourInt = -1;
		int removeMinuteInt = -1;
		int removeAmpmInt = -1;
		
		
		if(grossHour != null && !grossHour.equals("")){
		  grossHourInt = (new Integer(grossHour.trim())).intValue();
		} 
		if(grossMinute != null && !grossMinute.equals("")){
		  grossMinuteInt = (new Integer(grossMinute)).intValue();
		}
		if(grossAmpm != null && !grossAmpm.equals("")){
		  grossAmpmInt = (new Integer(grossAmpm)).intValue();	
		}
		
		if(removeHour != null && !removeHour.equals("")){
		  removeHourInt = (new Integer(removeHour)).intValue();
		}
		if(removeMinute != null && !removeMinute.equals("")){
		  removeMinuteInt = (new Integer(removeMinute)).intValue();
		}
		if(removeAmpm != null && !removeAmpm.equals("")){
		  removeAmpmInt = (new Integer(removeAmpm)).intValue();
		}
		
		Vector staffNames = new Vector();
		staffNames = (Vector) request.getAttribute("staffNames");
		// Commented out by Steve T for MR 4435, since these fields are no longer valid...
		String prevEmployee = (String)((request.getAttribute("employee")!= null)?request.getAttribute("employee"):"");	
		// String employeeFirst = (String)((request.getAttribute("employeeFirst")!= null)?request.getAttribute("employeeFirst"):"");	
		// String employeeLast = (String)((request.getAttribute("employeeLast")!= null)?request.getAttribute("employeeLast"):"");	


		LegalValueSet acc_surgical_remove_times = new LegalValueSet();
		acc_surgical_remove_times = (LegalValueSet) request.getAttribute("timeRemovals"); 
		
		int    indexSelAccTimeInt = -1;
		String indexSelAccTime = (String) ((request.getAttribute("accSurgRemoval")!= null)?request.getAttribute("accSurgRemoval"):"");
		if(indexSelAccTime != null && !indexSelAccTime.equals("")){
		  indexSelAccTimeInt = (new Integer(indexSelAccTime)).intValue();
		}
%>
<bean:define id="asmForm" name="asmFormData"/>
<html>
<head>
<title>ASM Form Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src='<html:rewrite page="/js/calendar.js"/>'></script>
<script language="JavaScript">
<!--
var changed = false;

var OTHER_PROCEDURE_CODE="CA00004P";

var myBanner = 'ASM Form Information';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  document.form1.searchDiagprocedure.focus();
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.form1;
  f.btnSubmit.disabled = isDisabled;
}

function checkBusinessLogic(linked){
    setActionButtonEnabling(false);
    
	var rHour = document.forms[0].removeHour.value;
	var rMinute = document.forms[0].removeMinute.value;
	var gHour = document.forms[0].grossHour.value;
	var gMinute = document.forms[0].grossMinute.value;

	
		if (nothingSelected() == true) {
			alert("No data was entered in the ASM Form header.");
			setActionButtonEnabling(true);
			return;
		}


		if (document.forms[0].procedure.value == "") {
			alert("Please select a Procedure");
			document.forms[0].procedure.focus();
			setActionButtonEnabling(true);
			return;
			}

		if (document.forms[0].procedure.value == OTHER_PROCEDURE_CODE) {
			if (document.forms[0].otherProcedure.value == "") {
				alert("Please fill in Other Procedure");
				document.forms[0].otherProcedure.focus();
				setActionButtonEnabling(true);
				return;
				}
			}


		// MF 6819 added the Procedure Date field.  Only applicable to linked cases.
		//  must have either a date selected or the not available checkbox selected
		//  note that the software prohibits the user from selecting both...
		if (linked == "Y") { 
			if ( (document.forms[0].proc_date_avail.checked == false) && 
				((document.forms[0].procedure_date.value == null) || (document.forms[0].procedure_date.value=="")) 
				) {	
				alert("Please select Procedure Date or check Not Available.");
				setActionButtonEnabling(true);
				return;
				}
			// verify that the date is not in the future
			if ((document.forms[0].procedure_date.value != null) || (document.forms[0].procedure_date.value != "")) {
				var currentDate = new Date();
				var selectDate = document.forms[0].procedure_date.value;
				var selectYear = selectDate.substr(6,4); 
				var selectMonth = selectDate.substr(0,2) - 1;
				var selectDay = selectDate.substr(3,2);
				//alert("prior to construct date=" + selectYear + " " + selectMonth + " " + selectDay);
				var constructProcDate = new Date(selectYear, selectMonth, selectDay);
				//alert("prior to compare, constructProcDate=" + constructProcDate + ":currentDate=" + currentDate);
				if (constructProcDate > currentDate) {
					alert("Procedure Date cannot be in the future. " + constructProcDate);
					setActionButtonEnabling(true);
					return;			
					}
				}
			}
		
 	
		
		
		// SAT 12/21/02  MR 4435 new required field
		if (document.forms[0].accSurgRemoval.selectedIndex == 0) {
			alert("Please select an Accuracy of Surgical Removal Time.");
			setActionButtonEnabling(true);
			return;
			}
			
		
		//commented out by Rusty Nelson for MR 3933.  We should be checking the date no matter what is selected.
		//solves the problem if procedure is selected and nothing else is selected (resulting in a NullPointerException)
		//if (anythingSelected() == true) {
			if((rHour == '-1') || (rMinute == '-1')){
				alert("Please select a complete Time of Surgical Removal");
				setActionButtonEnabling(true);
				return;
			}
			
			
			if ((document.forms[0].removeAmpm[0].checked == false) && (document.forms[0].removeAmpm[1].checked == false)) {
				alert("Please select AM or PM for Time of Surgical Removal");
				setActionButtonEnabling(true);
				return;
			}
		
			if((gHour == '-1') || (gMinute == '-1')){
				alert("Please select a complete Time of Grossing");
				setActionButtonEnabling(true);
				return;
			}
			
			if ((document.forms[0].grossAmpm[0].checked == false) && (document.forms[0].grossAmpm[1].checked == false)) {
				alert("Please select AM or PM for Time of Grossing");
				setActionButtonEnabling(true);
				return;
			}
		
			var removeTime = new Date();
			var removeHourIndex = document.forms[0].removeHour.selectedIndex;
			var removeMinuteIndex = document.forms[0].removeMinute.selectedIndex;
			if (document.forms[0].removeAmpm[1].checked == true) {
				if (removeHourIndex != 12) {
				   removeTime.setHours(Number(document.forms[0].removeHour[removeHourIndex].text) + 12, 
				                              document.forms[0].removeMinute[removeMinuteIndex].text, 
							      0);
				}
				else {
				    removeTime.setHours(document.forms[0].removeHour[removeHourIndex].text, 
				                        document.forms[0].removeMinute[removeMinuteIndex].text, 
							0);
				}
			}
			else {
			    if (removeHourIndex == 12) {
				   removeTime.setHours(0, 
				                       document.forms[0].removeMinute[removeMinuteIndex].text, 
						       0);
			   }else {
			          removeTime.setHours(document.forms[0].removeHour[removeHourIndex].text, 
			                              document.forms[0].removeMinute[removeMinuteIndex].text, 
					              0);
			  }			      
			}
			var grossTime = new Date();
			var grossHourIndex = document.forms[0].grossHour.selectedIndex;
			var grossMinuteIndex = document.forms[0].grossMinute.selectedIndex;
		
			if (document.forms[0].grossAmpm[1].checked == true) {
				if (grossHourIndex != 12) {
				   grossTime.setHours(Number(document.forms[0].grossHour[grossHourIndex].text) + 12, 
				                             document.forms[0].grossMinute[grossMinuteIndex].text,
							     0);
				}
				else {
				    grossTime.setHours(document.forms[0].grossHour[grossHourIndex].text,
				                       document.forms[0].grossMinute[grossMinuteIndex].text, 
						       0);
				}
			}
			else {
			    if (grossHourIndex == 12) {
			    grossTime.setHours(0, 
			                       document.forms[0].grossMinute[grossMinuteIndex].text, 
					       0);
			    }else {
			    grossTime.setHours(document.forms[0].grossHour[grossHourIndex].text, 
			                       document.forms[0].grossMinute[grossMinuteIndex].text, 
					       0);
			    }			    
			}
			var tenMinutes = 10 * 60 * 1000;
			var twoHours = 2 * 60 * 60 * 1000;
			var diffTime = 0;
			
			<!--Following if block added by Nagaraja Rao on 04/08/2002 for MR 3475  -->
			if ((document.forms[0].removeAmpm[1].checked == true) && (document.forms[0].grossAmpm[0].checked == true)) {
				grossTime.setHours(grossTime.getHours() + 24);
			}			
			diffTime = grossTime.getTime() - removeTime.getTime();
			if ((diffTime >= 0) && diffTime <= tenMinutes) {
				if (!confirm("Time difference between Surgical removal and grossing is less than or equal to 10 minutes. Do you want to proceed?")) {
     				setActionButtonEnabling(true);
					return;
				}
			}
			if (diffTime >= twoHours || diffTime < 0) {
				if (!confirm("Time difference between Surgical removal and grossing is greater than or equal to 2 hours. Do you want to proceed?")) {
     				setActionButtonEnabling(true);
					return;
				}
			}
		document.forms[0].submit();
}

function anythingSelected() {
	var rHour = document.forms[0].removeHour.value;
	var rMinute = document.forms[0].removeMinute.value;
	var gHour = document.forms[0].grossHour.value;
	var gMinute = document.forms[0].grossMinute.value;
	
	if(rHour != '-1') return true;
	if(rMinute != '-1') return true;
	if(gHour != '-1') return true;
	if(gMinute != '-1') return true;
	
	if (document.forms[0].removeAmpm[0].checked == true) return true;
	if (document.forms[0].removeAmpm[1].checked == true) return true;
	
	if (document.forms[0].grossAmpm[0].checked == true) return true;
	if (document.forms[0].grossAmpm[1].checked == true) return true;
	
	return false;
}

function nothingSelected() {
	if (anythingSelected() == true) return false;
	// if (document.forms[0].ProcedureType[document.forms[0].ProcedureType.selectedIndex].text !="Select") return false;
	// Commented by Nagaraja. MR 3475.
	// if (document.forms[0].employeeDrop[document.forms[0].employeeDrop.selectedIndex].text !="Select") return false;
	// Commented out by Steve T.  MR 4435.  The employee first name/last name are no longer used
	// if (document.forms[0].employeeFirst.value != null && document.forms[0].employeeFirst.value != "") return false;
	// if (document.forms[0].employeeLast.value != null && document.forms[0].employeeLast.value != "") return false;
	return true;
}

function editAsm(asmId, asmABC) {
  <% if (formSaved.equals("N")) { %>
    alert("Please complete and submit ASM Form header information");
	return;
  <% } else { %>
  var theURL = '<html:rewrite page="/iltds/Dispatch"/>?op=ASMModuleInfo&module=' + asmId;
  var openAsmEdit = false;

  if(changed){
    if (confirm("ASM Form header information is not saved automatically.  Do you want to continue?")) {
      openAsmEdit = true;
    }
  } else {
    openAsmEdit = true;
  }
  
  if (openAsmEdit) {
    MM_openBrWindow(theURL, 'EditAsm' + asmABC,
      'scrollbars=yes,resizable=yes,width=970,height=700,top=0,left=50');
  }
  <% } %>
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}


function selectDate(fullDateFldName) {
	openCalendar(fullDateFldName);
	var dateSelected = document.forms[0].elements[fullDateFldName].value;	
	if (dateSelected != null && dateSelected != "") {
		document.forms[0].proc_date_avail.checked = false;
	}
}
function selectedNotAvailable() {
 // since not available selected, we want to blank out the date 
 if (document.forms[0].proc_date_avail.checked == true) {	
 	document.forms[0].procedure_date.value = "";
 	}
}

//-->
</script>
	<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>

<body class="bigr" onload="initPage();">
<form name="form1" method="post" action="<html:rewrite page="/iltds/Dispatch"/>">
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td>
		<TABLE class="foreground" cellpadding="3" cellspacing="1" border="0">
			<% if(request.getAttribute("myError")!=null) { %>
			<TBODY>
				<TR class="green">
					<TD colspan="2">
					<DIV align="center"><FONT color="#FF0000"><B><%= request.getAttribute("myError") %>
					</B></FONT></DIV>
					</TD>
				</TR>
				<SCRIPT>
			alert("<%= request.getAttribute("myError") %>");
			</SCRIPT>
				<% } %>
				<% if(request.getAttribute("pulled")!=null) { %>
				<TR class="yellow">
					<TD colspan="2">
					<DIV align="center"><B><FONT color="#FF0000"><%= ((request.getAttribute("pulled")!=null)?request.getAttribute("pulled"):"") %></FONT></B></DIV>
					</TD>
				</TR>
				<SCRIPT>
			alert("<%= request.getAttribute("pulled") %>");
			</SCRIPT>
				<% } %>
				<TR class="yellow">
					<TD>
					<DIV align="left">ASM Form ID:<B><%= asmFormID %></B></DIV>
					</TD>
					<TD>
					<DIV align="left">Case ID:<B><%= consentID %></B></DIV>
					</TD>
				</TR>
				
				
		 		<bigr:procedureWithOther name="asmForm"
          			property="procedure" propertyLabel="Procedure:"
          			otherProperty="otherProcedure" otherPropertyLabel="Other Procedure:"
          			required="true"
          			firstValue="" firstDisplayValue="Select Procedure"
          			includeAlphaLookup="true"/>


				<% if (linked.equals("N")) { %>
				<TR class="white">
					<TD class="grey">
					<DIV align="right"><b>Date of Procedure:</b></DIV>
					</TD>
					<td colspan="2"> Not Applicable for Unlinked Cases
					</td>
				</TR>
				<% } else { %>
				<TR class="white">
					<TD class="grey">
					<DIV align="right"><b>Date of Procedure (Linked Case):</b>&nbsp;<font color="red">*</font></DIV>
					</TD>
					<TD> 
						<% if (procedure_date_yn.equals("N")) { %>
						   	<INPUT readonly type="text" name="procedure_date">                
	                		<span class="fakeLink" onclick="selectDate('procedure_date')">Select Date</span>	
	                		<b>OR</b> Not Available:
	                		<INPUT checked type="checkbox" name="proc_date_avail" onclick="selectedNotAvailable()">
	                	<% } else { %>
						   	<INPUT readonly type="text" name="procedure_date" value="<%=procDateValue%>">                
	                		<a href="javascript:selectDate('procedure_date')">Select Date</a>	
	                		<b>OR</b> Not Available:
	                		<INPUT type="checkbox" name="proc_date_avail" onclick="selectedNotAvailable()">	                	
	                	<% } %>
					</TD>
				</TR>				
				<% } %>



				<TR class="white">
					<TD class="grey">
					<DIV align="right"><b>Time of Surgical Removal:</b>&nbsp;<font color="red">*</font></DIV>
					</TD>
					<TD><SELECT name="removeHour" onchange="changed = true;">
						<OPTION value="-1"
							<%if(removeHourInt == -1){out.print("selected");}%>>Hr</OPTION>
						<%  	
								for( int i = 1 ; i <= 12 ; i++){
								%>
						<OPTION <%if(i == removeHourInt){out.print("selected");}%>><%=i%></OPTION>
						<%}%>
					</SELECT> : <SELECT name="removeMinute" onchange="changed = true;">
						<OPTION value="-1"
							<%if(removeMinuteInt == -1){out.print("selected");}%>>Min</OPTION>
						<%  	
								for( int i = 0 ; i <= 59 ; i++){
								%>
						<OPTION <%if(i == removeMinuteInt){out.print("selected");}%>><%if(i >= 0 && i <= 9) {
									out.print("0"+ i); }
									else out.print(i +"");%></OPTION>
						<%}%>
					</SELECT> <INPUT type="radio" name="removeAmpm" value="am"
						<% if (removeAmpmInt == java.util.Calendar.AM){out.print("checked");}%>
						onPropertyChange="changed = true;"> AM <INPUT type="radio"
						name="removeAmpm" value="pm"
						<% if (removeAmpmInt == java.util.Calendar.PM){out.print("checked");}%>
						onPropertyChange="changed = true;"> PM</TD>
				</TR>
				
				
				<TR class="white">
					<TD class="grey" nowrap align="right"><b>Accuracy of Surgical Removal
					Time:</b>&nbsp;<font color="red">*</font></TD>
					<TD nowrap>
					<SELECT name="accSurgRemoval" onchange="changed = true;">
						<OPTION value="" <%if( indexSelAccTimeInt == 0){out.print("selected");}%>> Select</OPTION>
						<%for (int i = 0 ; i < acc_surgical_remove_times.getCount(); i++){%>
						<OPTION value="<%=acc_surgical_remove_times.getValue(i)%>"
							<%if( indexSelAccTimeInt == i){out.print("selected");}%> >
							<%=acc_surgical_remove_times.getDisplayValue(i)%>
							</OPTION>
						<%}%>
					</SELECT>					
					</TD>
				</TR>
				
				
				
				
				
				<TR class="white">
					<TD class="grey">
					<DIV align="right"><b>Time of Grossing:</b>&nbsp;<font color="red">*</font></DIV>
					</TD>
					<TD><SELECT name="grossHour" onchange="changed = true;">
						<OPTION value="-1"
							<%if(grossHourInt == -1){out.print("selected");}%>>Hr</OPTION>
						<%  	
								for( int i = 1 ; i <= 12 ; i++){
								%>
						<OPTION <%if(i == grossHourInt){out.print("selected");}%>><%=i%></OPTION>
						<%}%>
					</SELECT> : <SELECT name="grossMinute" onchange="changed = true;">
						<OPTION value="-1"
							<%if(grossMinuteInt == -1){out.print("selected");}%>>Min</OPTION>
						<%  	
								for( int i = 0 ; i <= 59 ; i++){
								%>
						<OPTION <%if(i == grossMinuteInt){out.print("selected");}%>><%if(i >= 0 && i <= 9) {
									out.print("0"+ i); }
									else out.print(i +"");%></OPTION>
						<%}%>
					</SELECT> <INPUT type="radio" name="grossAmpm" value="am"
						<% if (grossAmpmInt == java.util.Calendar.AM){out.print("checked");}%>
						onPropertyChange="changed = true;"> AM <INPUT type="radio"
						name="grossAmpm" value="pm"
						<% if (grossAmpmInt == java.util.Calendar.PM){out.print("checked");}%>
						onPropertyChange="changed = true;"> PM</TD>
				</TR>
				<TR class="white">
					<TD class="grey">
					<DIV align="right"><b>Samples prepared by:</b>&nbsp;<font color="red">*</font></DIV>
					</TD>
					<TD><SELECT name="employeeDrop" onchange="changed = true;">
						<OPTION value="">Select</OPTION>
						<%for (int i = 0 ; i < (staffNames.size()/2) ; i++){%>
						<OPTION value="<%=staffNames.get((i*2)+1)%>"
							<% if (staffNames.get((i*2)+1).equals(prevEmployee)) out.print("selected");%>><%= staffNames.get(i*2)%></OPTION>
						<%}%>
					</SELECT></TD>
				</TR>
				<TR class="white">
					<TD colspan="2">
					<DIV align="center"><B> Please press Submit to save ASM Form header
					information. </B></DIV>
					</TD>
				</TR>
				<TR class="white">
					<TD colspan="2">
					<DIV align="center"><INPUT type="hidden" name="op"
						value="ASMFormInfoInsert"> <INPUT type="hidden" name="asmFormID"
						value="<%=asmFormID%>"> <INPUT type="hidden" name="consentID"
						value="<%=consentID%>"> <INPUT type="button" name="btnSubmit"
						value="Submit" onclick="checkBusinessLogic('<%=linked%>');"></DIV>
					</TD>
				</TR>
				<TR class="white">
					<TD colspan="2">
					<DIV align="center">
					<TABLE class="background" cellpadding="0" cellspacing="0"
						border="0" width="75%">
						<TBODY>
							<TR>
								<TD>
								<TABLE class="foreground" cellpadding="3" cellspacing="1"
									border="0" width="100%">
									<%
					  String module1 = (String)request.getAttribute("module1");
					  String module2 = (String)request.getAttribute("module2");
					  String module3 = (String)request.getAttribute("module3");
					  String module1Letter = module1.substring(module1.length() - 1);
					  String module2Letter = module2.substring(module2.length() - 1);
					  String module3Letter = module3.substring(module3.length() - 1);
					  %>
									<TBODY>
										<TR class="yellow">
											<TD>
											<DIV align="center">ASM <%=module1Letter%></DIV>
											</TD>
											<TD>
											<DIV align="center">ASM <%=module2Letter%></DIV>
											</TD>
											<TD>
											<DIV align="center">ASM <%=module3Letter%></DIV>
											</TD>
										</TR>
										<TR class="white">
											<TD>
											<DIV align="center"><INPUT type="button" name="ASMA"
												value='<%=((request.getAttribute("label1")!= null)?request.getAttribute("label1"):"Enter")%>'
												onclick="editAsm('<%= module1 %>', 'A');"></DIV>
											</TD>
											<TD>
											<DIV align="center"><INPUT type="button" name="ASMB"
												value='<%=((request.getAttribute("label2")!= null)?request.getAttribute("label2"):"Enter")%>'
												onclick="editAsm('<%= module2 %>', 'B');"></DIV>
											</TD>
											<TD>
											<DIV align="center"><INPUT type="button" name="ASMC"
												value='<%=((request.getAttribute("label3")!= null)?request.getAttribute("label3"):"Enter")%>'
												onclick="editAsm('<%= module3 %>', 'C');"></DIV>
											</TD>
										</TR>
									</TBODY>
								</TABLE>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
					</DIV>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</td>
      </tr>
    </table>
  </div>
<input type="hidden" name="module1" value="<%=module1%>">
<input type="hidden" name="module2" value="<%=module2%>">
<input type="hidden" name="module3" value="<%=module3%>">
</form>
</body>
</html>
