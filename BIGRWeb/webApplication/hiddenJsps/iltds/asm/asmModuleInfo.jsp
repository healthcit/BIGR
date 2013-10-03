<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ page import="java.util.Iterator"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean"  %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"  %>
<%@ taglib uri="/tld/struts-html"  prefix="html"  %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>

<bean:define id="asm" name="asmInfo" type="com.ardais.bigr.iltds.databeans.AsmData"/>
<%
//Vector myFrozen = (Vector) ((request.getAttribute("myFrozen")!=null)?request.getAttribute("myFrozen"):"");
//Vector myParaffin = (Vector) ((request.getAttribute("myParaffin")!=null)?request.getAttribute("myParaffin"):"");

   String moduleLetter = (String)((request.getAttribute("moduleLetter")!=null)?request.getAttribute("moduleLetter"):"");
   String editButton = "";
   if(moduleLetter.equals("A") || 
   	  moduleLetter.equals("D") || 
	  moduleLetter.equals("G") || 
	  moduleLetter.equals("J") ||
	  moduleLetter.equals("M") ||
	  moduleLetter.equals("P") ||
	  moduleLetter.equals("S") ||
	  moduleLetter.equals("V")){
	  editButton = "A";
	} else if (moduleLetter.equals("B") || 
			   moduleLetter.equals("E") || 
			   moduleLetter.equals("H") || 
			   moduleLetter.equals("K")	||
	  		   moduleLetter.equals("N") ||
	  		   moduleLetter.equals("Q") ||
	  		   moduleLetter.equals("T") ||
	  		   moduleLetter.equals("W")){
    		editButton = "B";
	} else if (moduleLetter.equals("C") || 
	           moduleLetter.equals("F") || 
			   moduleLetter.equals("I") || 
			   moduleLetter.equals("L") ||
	  		   moduleLetter.equals("O") ||
		   	   moduleLetter.equals("R") ||
	    	   moduleLetter.equals("U") ||
	  		   moduleLetter.equals("X")){
			editButton = "C";
	}
%>
<html>
<head>
<title>ASM Module Information</title>
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript">
<!--

var OTHER_TISSUE_CODE = "91723000";
var MM_4_5_CODE = "CA00609C";

var frozenArray = new Array();
var paraffinArray = new Array();
var frozenInt = 0;
var paraffinInt = 0;
var paraffinExists = "false";

//populate an array of the frozen samples
<logic:iterate id="fr_samps" name="asm" property="frozen_samples">
	frozenArray[frozenInt] = "<bean:write name="fr_samps" property="sample_id"/>";
	frozenInt++;
</logic:iterate>

//populate an array of the paraffin samples
<logic:iterate id="pa_samps" name="asm" property="paraffin_samples">
	paraffinArray[paraffinInt] = "<bean:write name="pa_samps" property="sample_id"/>";
	paraffinInt++;
</logic:iterate>

function toggleFrozenRow(sampleid, disableflag) {
//  sampleid -- the sample id to be operated on
//  disableflag -- 'D' disable this row
//  disableflag -- 'E' enable this row
//  disableflag -- 'X' determine what operation should be performed

	var theObj_frozspecs = eval("document.forms[0].meets_specs_" + sampleid);
	var theObj_frozcomments = eval("document.forms[0].comment" + sampleid);
	var theObj_present = eval("document.forms[0].present" + sampleid);
	var theObj_format = eval("document.forms[0].format_detail" + sampleid);
	
	if (disableflag == "D") {  // explicitly call disable
		// note: must set both radio buttons...
		theObj_frozspecs[0].disabled = true;
		theObj_frozspecs[1].disabled = true;
		theObj_format.disabled = true;
		theObj_frozcomments.disabled = true;
		}
	if (disableflag == "E") { // explicitly call enable
		// note: must set both radio buttons...
		theObj_frozspecs[0].disabled = false;
		theObj_frozspecs[1].disabled = false;
		theObj_format.disabled = false;
		theObj_frozcomments.disabled = false;
		}
	if (disableflag == "X") {   // else, determine which way to toggle based on current state...
		if (theObj_present.checked == true) {
			// currently checked, so enable other fields...
			theObj_frozspecs[0].disabled = false;
			theObj_frozspecs[1].disabled = false;
			theObj_format.disabled = false;
			theObj_frozcomments.disabled = false;
		}
		else  {
			// currently not checked, so disable other fields...
			theObj_frozspecs[0].disabled = true;
			theObj_frozspecs[1].disabled = true;
			theObj_format.disabled = true;
			theObj_frozcomments.disabled = true;		
		}	
	}	
}  // end function toggleFrozenRow

function toggleParaffin(sampleid, disableflag) {
//  sampleid -- the sample id to be operated on
//  disableflag -- 'D' disable this row
//  disableflag -- 'E' enable this row
//  disableflag -- 'X' determine what operation should be performed

	var theObj_pfinspecs;
	var theObj_min, theObj_max, theObj_width;
	
	theObj_pfinspecs = eval("document.forms[0].pfin_meets_specs_" + sampleid);
	theObj_min = eval("document.forms[0].min_thickness" + sampleid);
	theObj_max = eval("document.forms[0].max_thickness" + sampleid);
	theObj_width = eval("document.forms[0].width_across" + sampleid);
	theObj_papresent = eval("document.forms[0].pfin_present" + sampleid);
	theObj_typefix = eval("document.forms[0].type_fixative");
	theObj_hoursfix = eval("document.forms[0].hoursFixative");
	theObj_pacomment = eval("document.forms[0].pfin_comment" + sampleid);
	theObj_paraffinFeatureCid = eval("document.forms[0].paraffinFeatureCid" + sampleid);
	theObj_otherParaffinFeature = eval("document.forms[0].otherParaffinFeature" + sampleid);

	//alert("entering togglePfinRow: sampleid/disableflag" + sampleid + "/" + disableflag);

	if (disableflag == "D") {  // explicitly call disable
		// note: must set both radio buttons...
		// MR 4849 use this call to disable child rows
		theObj_pfinspecs[0].disabled = true;		
		theObj_pfinspecs[1].disabled = true;
		theObj_pacomment.disabled = true;
		theObj_min.disabled = true;
		theObj_max.disabled = true;
		theObj_width.disabled = true;
		theObj_typefix.disabled = true;
		theObj_hoursfix.disabled = true;

		theObj_paraffinFeatureCid.disabled = true;
		if (theObj_paraffinFeatureCid.value == "<%=FormLogic.OTHER_PARAFFIN_FEATURE%>") {
			theObj_otherParaffinFeature.disabled = true;
		}
	}
	if (disableflag == "E") { // explicitly call enable
		// note: must set both radio buttons...
		theObj_pfinspecs[0].disabled = false;			
		theObj_pfinspecs[1].disabled = false;
		theObj_pacomment.disabled = false;
		document.forms[0].min_thickness.disabled = false;
		document.forms[0].max_thickness.disabled = false;
		document.forms[0].width_across.disabled = false;
		document.forms[0].pfin_yesno_specs[0].disabled = false;
		document.forms[0].pfin_yesno_specs[1].disabled = false;
		theObj_typefix.disabled = false;
		theObj_hoursfix.disabled = false;

		theObj_paraffinFeatureCid.disabled = false;
		if (theObj_paraffinFeatureCid.value == "<%=FormLogic.OTHER_PARAFFIN_FEATURE%>") {
			theObj_otherParaffinFeature.disabled = false;
		}
	}
	if (disableflag == "X") {   // else, determine which way to toggle...
		if ((theObj_papresent.checked == true))
		{
			// currently checked, so enable other fields as appropriate...

			theObj_pfinspecs[0].disabled = false;
			theObj_pfinspecs[1].disabled = false;
			theObj_pacomment.disabled = false;

			theObj_paraffinFeatureCid.disabled = false;
			if (theObj_paraffinFeatureCid.value == "<%=FormLogic.OTHER_PARAFFIN_FEATURE%>") {
				theObj_otherParaffinFeature.disabled = false;
			}

			if (theObj_pfinspecs[1].checked == true) {
				theObj_min.disabled = false;
				theObj_max.disabled = false;
				theObj_width.disabled = false;
			}
			else {
				theObj_min.disabled = true;
				theObj_max.disabled = true;
				theObj_width.disabled = true;
			}

			document.forms[0].pfin_yesno_specs[0].disabled = false;
			document.forms[0].pfin_yesno_specs[1].disabled = false;
			if (document.forms[0].pfin_yesno_specs[1].checked == true) {
				theObj_typefix.disabled = false;
				theObj_hoursfix.disabled = false;
			}
			else {
				theObj_typefix.disabled = true;
				theObj_hoursfix.disabled = true;
			}
		}
		else  {
			// pfin_present currently not checked, so disable other fields...
			theObj_pfinspecs[0].disabled = true;
			theObj_pfinspecs[1].disabled = true;
			theObj_pacomment.disabled = true;
			theObj_min.disabled = true;
			theObj_max.disabled = true;
			theObj_width.disabled = true;
			document.forms[0].pfin_yesno_specs[0].disabled = true;
			document.forms[0].pfin_yesno_specs[1].disabled = true;
			theObj_typefix.disabled = true;
			theObj_hoursfix.disabled = true;
			
			theObj_paraffinFeatureCid.disabled = true;
			if (theObj_paraffinFeatureCid.value == "<%=FormLogic.OTHER_PARAFFIN_FEATURE%>") {
				theObj_otherParaffinFeature.disabled = true;
			}
		}		
	}	
}

function toggleParaffinSample(sampleid) {
//  sampleid -- the sample id to be operated on

	var theObj_pfinspecs;
	var theObj_min, theObj_max, theObj_width;
	
	theObj_pfinspecs = eval("document.forms[0].pfin_meets_specs_" + sampleid);
	theObj_min = eval("document.forms[0].min_thickness" + sampleid);
	theObj_max = eval("document.forms[0].max_thickness" + sampleid);
	theObj_width = eval("document.forms[0].width_across" + sampleid);

	if (theObj_pfinspecs[1].checked == true) {
		// paraffin sample meets specs is no
		theObj_min.disabled = false;
		theObj_max.disabled = false;
		theObj_width.disabled = false;

	}
	else {  // paraffin sample meets specs is yes
		theObj_min.disabled = true;
		theObj_max.disabled = true;
		theObj_width.disabled = true;

        theObj_min.options[0].selected = true;
        theObj_max.options[0].selected = true;
        theObj_width.options[0].selected = true;
	}
}

function toggleParaffinProcessing()
{
	if (document.forms[0].pfin_yesno_specs[0].checked == true) {  // yes Processing Meets Specs
		document.forms[0].hoursFixative.disabled = true;
		document.forms[0].type_fixative.disabled = true;
        document.forms[0].type_fixative.options[0].selected = true;
		document.forms[0].hoursFixative.value = "";
	}
	if (document.forms[0].pfin_yesno_specs[1].checked == true) {
		document.forms[0].hoursFixative.disabled = false;
		document.forms[0].type_fixative.disabled = false;
	}
}

function validWeight() {
  var weight = document.forms[0].moduleWeight.value;
	if (!((weight == "") || ((weight >=0) && (weight <=99999))) ) {
		alert("Must be between 0-99999 mg, inclusive");
		document.forms[0].moduleWeight.value = "";
		document.forms[0].moduleWeight.focus();
		}
}

function validHoursFixative() {
  var hours = document.forms[0].hoursFixative.value;
    if ( 
    	((document.forms[0].type_fixative.options[0].selected == true)) &&
	     (!(((hours == "")) || ((hours >=19) && (hours <=999))))
	   ) {
		alert("Hours in Fixative must be between 19-999, inclusive");
		document.forms[0].hoursFixative.value="";
		document.forms[0].hoursFixative.focus();
	}
	else if (!(((hours == "")) || ((hours >=0) && (hours <=999)))) {
		alert("Hours in Fixative must be between 0-999, inclusive");
		document.forms[0].hoursFixative.value="";
		document.forms[0].hoursFixative.focus();
    }
}

function SelectAll() {

	// use this function to handle both selecting and deselecting all...
	var i=0;
	var theObj_samp;  

	// they should all be selected...
	if (document.forms[0].formatAll.checked == true) {	
		<logic:iterate id="fr_samps" name="asm" property="frozen_samples">
			theObj_samp = eval("document.forms[0].present" + frozenArray[i]);
			theObj_samp.checked = true;
			i++;
		</logic:iterate>
	
		i=0;	
		<logic:iterate id="pa_samps" name="asm" property="paraffin_samples">
			theObj_samp = eval("document.forms[0].pfin_present" + paraffinArray[i]);
			theObj_samp.checked = true;
			i++;
		</logic:iterate>
		
		// MR 4435 changes to support enabling and disabling fields
		initializeFrozenRows('X');
		initializeParaffinRows('X');
	}
	else {  // they should be deselected...note that you cannot deselect disabled ones...
		//alert("deselect all");
		i=0;
		
		<logic:iterate id="fr_samps" name="asm" property="frozen_samples">
			theObj_samp = eval("document.forms[0].present" + frozenArray[i]);
			if (theObj_samp.disabled == false) {
				theObj_samp.checked = false;
				toggleFrozenRow('<bean:write name="fr_samps" property="sample_id"/>', 'D');
			}
			i++;
		</logic:iterate>
		
		i=0;
		<logic:iterate id="pa_samps" name="asm" property="paraffin_samples">
			theObj_samp = eval("document.forms[0].pfin_present" + paraffinArray[0]);
			if (theObj_samp.disabled == false) {
				theObj_samp.checked = false;
				toggleParaffin('<bean:write name="pa_samps" property="sample_id"/>', 'D');
			}
			i++;
		</logic:iterate>
	}
} // end SelectAll

function initializeAllFields() {
	initializeFrozenRows('X');
	initializeParaffinRows('X');
	if (document.forms[0] && document.forms[0].searchDiagtissue_type) { document.forms[0].searchDiagtissue_type.focus(); }
} // end initializeAllFields

function initializeFrozenRows(op) {
	<logic:iterate id="fr_samps" name="asm" property="frozen_samples">
			toggleFrozenRow('<bean:write name="fr_samps" property="sample_id"/>', op);
	</logic:iterate>	
}

function initializeParaffinRows(op) {
	var count = 0;
	<logic:iterate id="pa_samps" name="asm" property="paraffin_samples">
			if (count == 0) {
				toggleParaffin('<bean:write name="pa_samps" property="sample_id"/>', op);
			}
			else { // MR 4849 child paraffins should have all values disabled
				toggleParaffin('<bean:write name="pa_samps" property="sample_id"/>', 'D');
			}
			count++;
	</logic:iterate>	
}

function checkBusinessRules() {
    setActionButtonEnabling(false);
    
	if(paraffinExists == "true"){
	  var theObj = eval("document.forms[0].pfin_meets_specs_" + paraffinArray[0] + "[1]");
	  var theObj_min = eval("document.forms[0].min_thickness" + paraffinArray[0]);
	  var theObj_max = eval("document.forms[0].max_thickness" + paraffinArray[0]);
	  var theObj_width = eval("document.forms[0].width_across" + paraffinArray[0]);
	}
	
	// verify tissue has been selected...
	if (document.forms[0].tissue_type.value == "") {
		alert("Please select a Tissue");
		document.forms[0].tissue_type.focus();
		setActionButtonEnabling(true);
		return;
		}
		
	// if other tissue is selected, verify that value is entered in other_tissue field
	if (document.forms[0].tissue_type.value == <%=FormLogic.OTHER_TISSUE%>) {
		if (document.forms[0].other_tissue.value == "") {
			alert("Please fill in Other Tissue");
			document.forms[0].other_tissue.focus();
			setActionButtonEnabling(true);
			return;
			}
		}
	// else verify that no value is in other tissue field...
	else {
		document.forms[0].other_tissue.value="";
	}

	// verify gross appearance has been selected...
	if (document.forms[0].diseased.value == "") {
		alert("Please select Gross Appearance");
		document.forms[0].diseased.focus();
		setActionButtonEnabling(true);
		return;
		}
		
	// 4/29/03 sat -- removed check for module weight with snap frozen samples
	
  if(paraffinExists == "true") {
		
	// Paraffin sample, min thickness must be less than or equal to max thickness
	// NOTE: this is a lousy way to handle the comparisons, but our hands are tied at the moment
	// by the ARTS codes.   The code is dependent on ARTS values for the largest value (4-5mm) being
	// the lowest value, CA00609C, in the sort order of the code (and conversely, for the smallest value to
	// be the highest value, CA00612C, in the sort order 
	if ((theObj_min.value.localeCompare(theObj_max.value)) < 0) {
		alert("Min Thickness cannot be greater than Max Thickness");
		theObj_min.focus();
		setActionButtonEnabling(true);
		return;
		}	
	

	// Paraffin Processing Meets Spec is no, therefore must have a value for total hours in fixative
	if (document.forms[0].pfin_yesno_specs[1].checked == true) {
		if (document.forms[0].hoursFixative.value == "") {
			alert("Paraffin Processing Meets Spec is No, therefore please enter Total Hours in Fixative");
			document.forms[0].hoursFixative.focus();
			setActionButtonEnabling(true);
			return;	
			}
	}


	// Paraffin sample, if Sample Meets is no, then prompt if min=max=width=(4-5)
	if (theObj.checked == true) {
		if (theObj_min.value == MM_4_5_CODE) {
			if ((theObj_min.value.localeCompare(theObj_max.value)) == 0) {
				if ((theObj_min.value.localeCompare(theObj_width.value)) == 0) {
					if (!(confirm("Paraffin Sample Size Meets Spec is No, but Min Thickness, Max Thickness, and Width Across are in spec.  Select OK to submit these values, or select Cancel to change these values.") ) ) {
						theObj_min.focus();
						setActionButtonEnabling(true);
						return;		
					}
				}
			}
		}
	}
	
	if (theObj.checked == true) {		
  	  var hrs = document.forms[0].hoursFixative.value;
      if ( 
    	((document.forms[0].type_fixative.options[0].selected == true)) &&
	     (!(((hrs == "")) || ((hrs >=19) && (hrs <=999))))
	   ) {
		alert("Hours in Fixative must be between 19-999, inclusive");
		document.forms[0].hoursFixative.value="";
		document.forms[0].hoursFixative.focus();
	  }
	  else if (!(((hrs == "")) || ((hrs >=0) && (hrs <=999)))) {
		alert("Hours in Fixative must be between 0-999, inclusive");
		document.forms[0].hoursFixative.value="";
		document.forms[0].hoursFixative.focus();
      }
    }


	// before submitting, set the hidden fields to get around the issue of disabled fields not holding their value
	var theObj_minThick, theObj_minThickState;
	var theObj_maxThick, theObj_maxThickState;	
	var theObj_width, theObj_widthState;
	var theObj_typeFix, theObj_typeFixState;
	

	for (j=0; j < paraffinArray.length; j++) {
		theObj_minThick = eval("document.forms[0].min_thickness" + paraffinArray[j]);
		theObj_minThickState = eval("document.forms[0].min_thickness" + paraffinArray[j] + "state");
		theObj_minThickState.value = theObj_minThick.value;

		theObj_maxThick = eval("document.forms[0].max_thickness" + paraffinArray[j]);
		theObj_maxThickState = eval("document.forms[0].max_thickness" + paraffinArray[j] + "state");
		theObj_maxThickState.value = theObj_maxThick.value;

		theObj_width = eval("document.forms[0].width_across" + paraffinArray[j]);
		theObj_widthState = eval("document.forms[0].width_across" + paraffinArray[j] + "state");
		theObj_widthState.value = theObj_width.value;

		theObj_typeFix = eval("document.forms[0].type_fixative");
		theObj_typeFixState = eval("document.forms[0].type_fixative_state");
		theObj_typeFixState.value = theObj_typeFix.value;

		theObj_otherParaffinFeature = eval("document.forms[0].otherParaffinFeature" + paraffinArray[j]);
		
		if (trim(theObj_otherParaffinFeature.value) == "") {
			alert("Please specify other paraffin feature (cannot be blank).");
			theObj_otherParaffinFeature.focus();
			setActionButtonEnabling(true);
			return;
		}
	}
  } // end if paraffinExists
  
    setParentButton();
    
	//alert("Reached end of check business rules -- submit");	
	// finally, we are ready to submit...
	document.forms[0].submit();
}

function confirmCancel() {
  setActionButtonEnabling(false);
  if (confirm("All changes made will be lost. Are you sure you want to cancel?")) {
    window.close();
  }
  else {
    setActionButtonEnabling(true);
  }
}

function taLimit() {
	var taObj=event.srcElement;
	if (taObj.value.length==taObj.maxLength*1 || (event.keyCode==13 && taObj.value.length==(taObj.maxLength*1)-1)) return false;
}

function taCount(visCnt) { 
	var taObj=event.srcElement;
	if (taObj.value.length>taObj.maxLength*1) taObj.value=taObj.value.substring(0,taObj.maxLength*1);
	if (visCnt) visCnt.innerText=taObj.maxLength-taObj.value.length;
}

var myBanner = 'Edit';

function setParentButton() {
	var parent = window.opener;
	if ((!parent.closed) && (parent.document.all.ASM<%=editButton%> != null)) {
		parent.document.all.ASM<%= editButton %>.value = myBanner;
	}
	return true;
}

function checkEnter(event) {
	var code = 0;
	code = event.keyCode;
	
	if (code == 13) {
		return false;
    }
}

function initPage() {
  window.focus();
  initializeAllFields();
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.form1;
  if (f.Submit) f.Submit.disabled = isDisabled;
  if (f.Submit2) f.Submit2.disabled = isDisabled;
}

//-->
</script>

<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<% 

// initialize variables from request to be used in processing...
  
String tissue = (String)request.getAttribute("organ");
String otherTissue = (String)request.getAttribute("otherTissue");
if (tissue == null) {
	otherTissue = "N/A";
} else if(!tissue.equals(FormLogic.OTHER_TISSUE)) {
	otherTissue = "N/A";
} else if(otherTissue == null){
	otherTissue = "N/A";
}


LegalValueSet format_details = new LegalValueSet();
format_details = (LegalValueSet) request.getAttribute("sampleFormat"); 

LegalValueSet minThickness = new LegalValueSet();
minThickness = (LegalValueSet) request.getAttribute("minThickness"); 

LegalValueSet maxThickness = new LegalValueSet();
maxThickness = (LegalValueSet) request.getAttribute("maxThickness"); 

LegalValueSet widthAcross = new LegalValueSet();
widthAcross = (LegalValueSet) request.getAttribute("widthAcross"); 
		
LegalValueSet fixatives = new LegalValueSet();
fixatives = (LegalValueSet) request.getAttribute("fixatives"); 

LegalValueSet paraffinFeatureSet = new LegalValueSet();
paraffinFeatureSet = (LegalValueSet) request.getAttribute("paraffinFeatureSet"); 

%>

<body class="bigr" onload="initPage();">

<logic:present name="criticalError">
     <div align="center">
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       <table class="foreground" cellpadding="3" cellspacing="1" border="1">
          <tr class="yellow" align="center"> 
            <td> 
              <b><font color="#FF0000"><%= request.getAttribute("criticalError") %></font></b>
            </td>
          </tr>
          <tr class="white" align="center">
             <td>
     			<input type="button" name="NoSamples" value="Close" onclick="window.close()">
             </td>
          </tr>
     </table>
     </div>
</logic:present>

<logic:notPresent name="criticalError">
  <form name="form1" method="post" action="<html:rewrite page="/iltds/Dispatch"/>">
    <logic:present name="asm">       
      <table class="background" border="0" cellpadding="0" cellspacing="0"><tr><td>
	    <table class="fixedTable" cellspacing="1">
	      <col width="90">
	      <col width="90">
	      <col width="100">
	      <col width="110">
	      <col width="100">
	      <col width="160">
	      <col width="280">
			
          <logic:present name="myRetry">
          <tr class="green"> 
            <td colspan="7"> 
              <div align="center"><font color="#FF0000">
	      <logic:iterate name="myRetry" id="retry">
	      <bean:write name="retry"/><br>
	      </logic:iterate>
	      </font></div>
            </td>
          </tr>
		 
          </logic:present>
          <tr class="white"> 
            <td class="grey" colspan="7" align="center"> 
              <b>Module <%= moduleLetter %></b>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey"> 
              <div align="right"><b>ASM Form ID:</b></div>
            </td>
            <td colspan="2"> 
              <div align="left"><b><bean:write name="asm" property="asm_form_id"/> 
                <input type="hidden" name="module" value="<bean:write name="asm" property="asm_id"/>">
                </b></div>
            </td>
            <td class="grey"> 
              <div align="right"><b>Case ID:</b></div>
            </td>
            <td colspan="3"> 
              <div align="left"><b><bean:write name="asm" property="case_id"/> 
                <input type="hidden" name="consentID" value="<bean:write name="asm" property="case_id"/>">
                </b></div>
            </td>
          </tr>
 
		  
		 <bigr:tissueWithOther name="asm"
          	property="tissue_type" propertyLabel="Tissue:"
          	otherProperty="other_tissue" otherPropertyLabel="Other Tissue:"
          	required="true" colspan="6"
          	firstValue="" firstDisplayValue="Select Tissue"
          	includeAlphaLookup="true"/> 
		  
		  
		  
		  <tr class="white">
		    <td class="grey"> 
              <div align="right"><b>Gross Appearance:</b><font color = "red">*</font></div>
            </td>
            <td colspan="2"> 
              <select name="diseased">
	      		<option value="">Select</option>
				<logic:present name="asm" property="appearance">
                	<option value="D" <logic:equal name="asm" property="appearance" value="D">selected</logic:equal>>Gross Diseased</option>
                	<option value="N" <logic:equal name="asm" property="appearance" value="N">selected</logic:equal>>Gross Normal</option>
                	<option value="M" <logic:equal name="asm" property="appearance" value="M">selected</logic:equal>>Gross Mixed</option>
                	<option value="U" <logic:equal name="asm" property="appearance" value="U">selected</logic:equal>>Gross Unknown</option>
				</logic:present>
				<logic:notPresent name="asm" property="appearance">
					<option value="D">Gross Diseased</option>
                	<option value="N">Gross Normal</option>
               		<option value="M">Gross Mixed</option>
                	<option value="U">Gross Unknown</option>
				</logic:notPresent>
              </select>
            </td>
            <td class="grey">
				<div align="left"><b>Module Weight mg:</b></div>
			</td>	
			<td colspan="3">
			 	<input type="text" name="moduleWeight" 
			 	value="<bean:write name="asm" property="module_weight"/>"
			 	onblur="validWeight();" size="5" maxlength="5"
							width="5" >
			</td>	
		  </tr>
		  
		  
		  <tr class="white"> 
			  <td class="grey">
				<div align="right"><b>Module Comments:</b></div>
			  </td>	
			  <td colspan="6">
			  	<textarea name="moduleComments" cols="50" maxLength=255  onkeyup="maxTextarea(255);"><bean:write name="asm" property="module_comments"/></textarea>
			  </td>		  
		  </tr>
          <tr class="yellow"> 
            <td> 
              <div align="center"><b>Present</b></div>
            </td>
            <td> 
              <div align="center"><b>Position</b></div>
            </td>
            <td> 
              <div align="center"><b>Barcode</b></div>
            </td>
            <td>
              <div align="center"><b>Sample Size Meets Spec</b></div>
            </td>
            <td colspan="2">
              <div align="center"><b>Sample Format Detail</b></div>
            </td>
            <td> 
              <div align="center"><b>Sample Comments</b></div>
            </td>
          </tr>
          <tr class="grey" valign="middle"> 
            <td> 
              <div align="center"> 
                <input type="checkbox" name="formatAll" onclick="SelectAll();">
                <b>Select All</b> </div>
            </td>
            <td class="green" colspan="6"><div align="center"><b>Frozen Samples</b></div></td>
          </tr>
          <logic:iterate id="sample" name="asm" property="frozen_samples" type="com.ardais.bigr.iltds.databeans.SampleData">
	  <tr class="white" valign="middle" > 
            <td> 
              <input type="checkbox" name="present<bean:write name="sample" property="sample_id"/>"
				 onClick="toggleFrozenRow('<bean:write name="sample" property="sample_id"/>','X');"
                value="<bean:write name="sample" property="exists"/>"
                <%//MR6976 - samples that have only ASMPRESENT status values are allowed to be unchecked (so they can be deleted)
                	Iterator statusIterator = ApiFunctions.safeList(sample.getStatuses()).iterator();
                	boolean found = false;
                	String disabled = " ";
                	while (statusIterator.hasNext() && !found) {
                		String status = (String)statusIterator.next();
                		if (!FormLogic.SMPL_ASMPRESENT.equals(status)) {
                			found = true;
                			disabled = disabled + "disabled";
                		}
                	}
                %>
	      	    <logic:equal name="sample" property="exists" value="true">
	      	    	checked <%=disabled%>
	      	    </logic:equal>>
			  <input type="hidden" name="present<bean:write name="sample" property="sample_id"/>state" value="<logic:equal name="sample" property="exists" value="true">checked <%=disabled%></logic:equal>">
            </td>
            <td> 
              <div align="center"><bean:write name="sample" property="asm_position"/></div>
            </td>
            <td> 
              <div align="center"><bean:write name="sample" property="sample_id"/></div>
            </td>
            <td>
                <%String frSpecs=sample.getSampleSizeMeetsSpecs();%>
	           	 <input type="radio" name="meets_specs_<bean:write name="sample" property="sample_id"/>" value="Y"
   					<%if ( (frSpecs == null) || (frSpecs.equals("Y")) ) {%>checked <%}%>>Yes
				 <input type="radio" name="meets_specs_<bean:write name="sample" property="sample_id"/>" value="N"
   					<%if (frSpecs != null) {
   						if (frSpecs.equals("N")) {%>checked<%}
   					}%>>No
            </td>
            <td colspan="2">
            	<%String formCID=sample.getFormatDetailCid();%>
				<SELECT name="format_detail<bean:write name="sample" property="sample_id"/>" >
						<%for (int i = 0 ; i < format_details.getCount(); i++){%>
						<OPTION value="<%=format_details.getValue(i)%>" 
						<%if (format_details.getValue(i).equals(formCID)) {%>
						  selected <%}%> >
							<%=format_details.getDisplayValue(i)%>
							</OPTION>
						<%}%>
				</SELECT>
            </td>            
            <td> 
              <div align="center"> 
                <textarea name="comment<bean:write name="sample" property="sample_id"/>" cols="30" maxLength=255 onkeyup="maxTextarea(255);"><bean:write name="sample" property="comment"/></textarea>
              </div>
            </td>
          </tr>

          </logic:iterate>
          <tr class="green"> 
            <td colspan="7"> 
              <div align="center"><b>Paraffin Sample</b></div>
            </td>
          </tr>
      <logic:iterate id="sample" name="asm" property="paraffin_samples" indexId="index" type="com.ardais.bigr.iltds.databeans.SampleData">
	  	<bean:define id="paraffin" name="sample" toScope="request"/>
	  	<tr class="white" valign="middle"> 
            <td rowspan="2"> 
              <input type="checkbox" name="pfin_present<bean:write name="sample" property="sample_id"/>" value="<bean:write name="sample" property="exists"/>"
				 onclick="toggleParaffin('<bean:write name="sample" property="sample_id"/>','X');"              
              <%//MR6976 - samples that have only ASMPRESENT status values are allowed to be unchecked (so they can be deleted)
            	  Iterator statusIterator = ApiFunctions.safeList(sample.getStatuses()).iterator();
            	  boolean found = false;
            	  String disabled = " ";
            	  while (statusIterator.hasNext() && !found) {
            		  String status = (String)statusIterator.next();
            		  if (!FormLogic.SMPL_ASMPRESENT.equals(status)) {
            		  	  found = true;
            			  disabled = disabled + "disabled";
            		  }
            	  }
              %>
	      	<logic:equal name="sample" property="exists" value="true">
	      		checked <%=disabled%>
	      	</logic:equal>>
			  <input type="hidden" name="present<bean:write name="sample" property="sample_id"/>state" value="<logic:equal name="sample" property="exists" value="true">checked <%=disabled%></logic:equal>">
            </td>
            <td rowspan="2"> 
              <div align="center"><bean:write name="sample" property="asm_position"/></div>
            </td>
            <td rowspan="2"> 
              <div align="center"><bean:write name="sample" property="sample_id"/></div>
            </td>
            <td rowspan="2">
            	<%String pfSpecs=sample.getSampleSizeMeetsSpecs();%>
				 <input type="radio" name="pfin_meets_specs_<bean:write name="sample" property="sample_id"/>"
					onclick="toggleParaffinSample('<bean:write name="sample" property="sample_id"/>');" value="Y"           	 
   					<%if ( (pfSpecs == null) || (pfSpecs.equals("Y")) ) {%>checked <%}%>>Yes
            	 <input type="radio" name="pfin_meets_specs_<bean:write name="sample" property="sample_id"/>" 
					onclick="toggleParaffinSample('<bean:write name="sample" property="sample_id"/>');" value="N"           	 
    					<%if (pfSpecs != null) {
   						if (pfSpecs.equals("N")) {%>checked<%}
   					}%>>No
            </td>
            <td colspan="2" rowspan="2">&nbsp;</td>
            
             <td><bigr:selectListWithOther 
                  style="font-size:xx-small;"
                  property='<%="paraffinFeatureCid"+sample.getSample_id()%>'
                  otherProperty='<%="otherParaffinFeature"+sample.getSample_id()%>'
                  legalValueSet="<%=paraffinFeatureSet%>"
                  otherCode="<%=FormLogic.OTHER_PARAFFIN_FEATURE%>"
                  value="<%=sample.getParaffinFeatureCid()%>"/></td>
            
           
            
        </tr>
        <tr class="white" valign="middle">
          <td>
            <div align="left">
            <% String paraffinFeatureCid = sample.getParaffinFeatureCid();
               if ((paraffinFeatureCid != null) && paraffinFeatureCid.equals(FormLogic.OTHER_PARAFFIN_FEATURE)) { %>
              <bigr:otherText
                style="font-size:xx-small;"
                property='<%="otherParaffinFeature"+sample.getSample_id()%>'
                value='<%=sample.getOtherParaffinFeature()%>'
                name='sample'
                parentProperty='paraffinFeatureCid'
                otherCode='<%=FormLogic.OTHER_PARAFFIN_FEATURE%>'
                onkeydown="return checkEnter(event);"
                size="53"
                maxlength="200"/>
            <% } else { %>
              <input type="text"
                style="font-size:xx-small;"
                name="<%="otherParaffinFeature"+sample.getSample_id()%>"
                value="N/A"
                onkeydown="return checkEnter(event);"
                disabled
                size="53"
                maxlength="200"/>
            <% } %>
            </div>
          </td>
        </tr>
        <tr class="white" valign="middle">
            <td class="grey">
              <div align="center"><b>Minimum Thickness</b></div>
            </td>
            <td>
                <%String minThick=sample.getDiMinThicknessCid();%>
				<SELECT name="min_thickness<bean:write name="sample" property="sample_id"/>" >
						<%for (int i = 0 ; i < minThickness.getCount(); i++){%>
						<OPTION value="<%=minThickness.getValue(i)%>"
						<%if (minThickness.getValue(i).equals(minThick)) {%>
						  selected <%}%> >
							<%=minThickness.getDisplayValue(i)%>
							</OPTION>
						<%}%>
				</SELECT>
			  <input type="hidden" name="min_thickness<bean:write name="sample" property="sample_id"/>state" value="<bean:write name="sample" property="diMinThicknessCid"/>">

            </td>            
            <td class="grey">
              <div align="center"><b>Maximum Thickness</b></div>
            </td>
            <td>
            	<%String maxThick=sample.getDiMaxThicknessCid();%>
				<SELECT name="max_thickness<bean:write name="sample" property="sample_id"/>" >
						<%for (int i = 0 ; i < maxThickness.getCount(); i++){%>
						<OPTION value="<%=maxThickness.getValue(i)%>"
						<%if (maxThickness.getValue(i).equals(maxThick)) {%>
						  selected <%}%> >						 
							<%=maxThickness.getDisplayValue(i)%>
							</OPTION>
						<%}%>
				</SELECT>
			    <input type="hidden" name="max_thickness<bean:write name="sample" property="sample_id"/>state" value="<bean:write name="sample" property="diMaxThicknessCid"/>">
			
            </td>
            <td class="grey">
              <div align="center"><b>Width Across Cut Surface</b></div>
            </td>                        
            <td>
            	<%String widthAcr=sample.getDiWidthAcrossCid();%>
				<SELECT name="width_across<bean:write name="sample" property="sample_id"/>" >
						<%for (int i = 0 ; i < widthAcross.getCount(); i++){%>
						<OPTION value="<%=widthAcross.getValue(i)%>" 
						<%if (widthAcross.getValue(i).equals(widthAcr)) {%>
						  selected <%}%> >
							<%=maxThickness.getDisplayValue(i)%>
							</OPTION>
						<%}%>
				</SELECT>
			  <input type="hidden" name="width_across<bean:write name="sample" property="sample_id"/>state" value="<bean:write name="sample" property="diWidthAcrossCid"/>">
			
            </td>
            
               
            
            
             <td> 
              <div align="center"> 
                <textarea name="pfin_comment<bean:write name="sample" property="sample_id"/>" cols="30" maxLength=255 onkeyup="maxTextarea(255);"><bean:write name="sample" property="comment"/></textarea>
              </div>
            </td>
            
        </tr>
        
	  </logic:iterate>

      <logic:present name="paraffin">
      <bean:define id="sample" name="paraffin" type="com.ardais.bigr.iltds.databeans.SampleData"/>
	  <tr class="yellow" valign="middle">
	  	<td colspan="7"> 
            <div align="center"><b>Paraffin Processing Meets Spec</b></div>
        </td>
	  </tr>
	  <tr class="white" valign="middle"> 
	  	<td colspan="2" align="right">
            	<%String pfMeets=asm.getPfin_meets_specs();%>				 
            	 <input type="radio" name="pfin_yesno_specs"
            	 	onclick="toggleParaffinProcessing();" value="Y" 
   					<%if ( (pfMeets == null) || (pfMeets.equals("Y")) ) {%>checked <%}%>>Yes
            	 <input type="radio" name="pfin_yesno_specs"
            	    onclick="toggleParaffinProcessing();" value="N"
   					<%if (pfMeets != null) {
   						if (pfMeets.equals("N")) {%>checked<%}
   					}%>>No
	  	</td>
        <td class="grey"> 
              <div align="center"><b>Type of Fixative:</b></div>
        </td>
        <td align="left"> 
            	<%String fixType=sample.getType_fixative();%>	    	
				<SELECT name="type_fixative" onclick="validHoursFixative();">
						<%for (int i = 0 ; i < fixatives.getCount(); i++){%>
						<OPTION value="<%=fixatives.getValue(i)%>"
						<%if (fixatives.getValue(i).equals(fixType)) {%>
						  selected <%}%> >						
							<%=fixatives.getDisplayValue(i)%>
							</OPTION>
						<%}%>
				</SELECT>
			  <input type="hidden" name="type_fixative_state" value="<bean:write name="sample" property="type_fixative"/>">
	
        </td>
                <td class="grey"> 
              <div align="center"><b>Total Hours in Fixative:</b></div>
        </td>
        <td>
     		<input type="text" name="hoursFixative" size="3" maxlength="3"
     			 	value="<bean:write name="sample" property="hoursInFixative"/>"
							width="3" onblur="validHoursFixative();">
        </td><td>&nbsp;</td>
      </tr>
      <script>
        paraffinExists = "true";
      </script>
  </logic:present>

          <input type="hidden" name="op" value="ASMModuleInfoInsert">
          <tr class="white"> 
            <td colspan="7"> 
              <div align="center"> 
                <input type="button" name="Submit" value="Submit" onclick="checkBusinessRules()">
                <input type="button" name="Submit2" value="Cancel" onclick="confirmCancel()">
              </div>
            </td>
          </tr>
        </table>
</td></tr></table>
        </form>
    </logic:present>
</logic:notPresent>
</body>


<logic:present name="myRetry">
 
<script>
alert('<logic:iterate id="retry" name="myRetry">  <bean:write name="retry"/>  </logic:iterate>');
</script>
 
</logic:present>
<HEAD><META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"></HEAD>     
</html>
