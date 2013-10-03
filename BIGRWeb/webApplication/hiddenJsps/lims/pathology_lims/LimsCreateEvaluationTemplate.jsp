<%@ page import="java.util.*,com.ardais.bigr.api.Escaper,com.ardais.bigr.iltds.helpers.FormLogic" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri='/tld/struts-logic' prefix='logic' %>
<%@ taglib uri="/tld/struts-template" prefix="template" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>

<html>
  <head>
    <title><template:get name='title'/></title>
    <link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
    <script type="text/javascript">
      var myBanner = '<template:get name='title'/>';
      var isFindingListFiltered = false;
   	  var enteredStructures = new Array();
  	  var enteredOtherStructures = new Array();
  	  var enteredLesions = new Array();
  	  var enteredOtherLesions = new Array();
  	  var confirmSubmitNoReport = false;

  function positionItems() {
    var leftColumnDiv = document.all.LeftColumnDiv;
    var headerDiv = document.all.Header;
    var headerHeight = leftColumnDiv.offsetTop + leftColumnDiv.offsetHeight;
    var offp = leftColumnDiv.offsetParent;
    while (offp != headerDiv) {
      headerHeight += offp.clientTop + offp.offsetTop;
      offp = offp.offsetParent;
    }
    headerHeight += (headerDiv.offsetHeight - headerDiv.clientHeight);

    headerDiv.style.pixelHeight = headerHeight;
    
    var detailsDiv = document.all.Details;
    var detailsHeight = document.body.clientHeight - detailsDiv.offsetTop - 5;
    if (detailsHeight < 200) {
      detailsHeight = 200;
    }

    <%-- Setting the detailsDiv width to a specific size then back to 100%
      -- works around an IE bug where the detailsDiv would come up initially
      -- wider than it should have been (by the width of a scroll bar). --%>
    detailsDiv.style.pixelWidth = detailsDiv.offsetWidth - 1;
    detailsDiv.style.width = "100%";
    
    detailsDiv.style.pixelHeight = detailsHeight;
  }
  
  function onResizePage() {
    positionItems();
  }

  function initPage() {
  	if (parent.topFrame) {
	    parent.topFrame.document.all.banner.innerHTML = myBanner;	
	}
    
   	document.limsCreateEvaluationForm.addInflammationButton.focus();
    document.limsCreateEvaluationForm.diagnosis.tabIndex=4;
    document.limsCreateEvaluationForm.tissueOfOrigin.tabIndex=6;
    document.limsCreateEvaluationForm.tissueOfFinding.tabIndex=9;
    
    document.limsCreateEvaluationForm.diagnosisOther.tabIndex=-1;
    document.limsCreateEvaluationForm.tissueOfOriginOther.tabIndex=-1;
    document.limsCreateEvaluationForm.tissueOfFindingOther.tabIndex=-1;  
    
    document.limsCreateEvaluationForm.showdiagnosisHierarchy.tabIndex=-1;  
    document.limsCreateEvaluationForm.showtissueOfOriginHierarchy.tabIndex=-1;
    document.limsCreateEvaluationForm.showtissueOfFindingHierarchy.tabIndex=-1;
    
    showStructures(false);
    
    <logic:present name="limsCreateEvaluationForm" property="invStatus">    
	  <logic:match name="limsCreateEvaluationForm" property="invStatus" value="<%=FormLogic.SMPL_COCONSUMED%>">    
	    <%-- Do a setTimeout to make the alert asynchronous, otherwise the page doesn't finish
	         painting until the user clicks the ok button. --%>
	  	window.setTimeout('alert("This sample is Consumed");', 1, 'JavaScript');
	  </logic:match>
	</logic:present>

    positionItems();
    <%-- Unless we force the details div to start out scrolled all the way to the top, it sometimes
         displays initially a bit off, for example when the page is redisplayed because of a validation
         error that's caught on the back end. --%>
    document.all.Details.scrollTop = 0;
  }

  function cancelPage() {
    disableActionButtons(true);
	window.location.href = '<html:rewrite page="/lims/limsSlideQueryStart.do"/>?id=<bean:write name="limsCreateEvaluationForm" property="slideId"/>';
  }
  
  function updateTotal() {  		
  		var total = 0;
  		var lesion = Number(document.forms[0].lesionValue.value); 
  		var normal = Number(document.forms[0].normalValue.value);
  		var tumor = Number(document.forms[0].tumorValue.value);
  		var hcStroma = Number(document.forms[0].hcstromaValue.value);
  		var haStroma = Number(document.forms[0].hastromaValue.value);
  		var necrosis = Number(document.forms[0].necrosisValue.value);
  		
  		total = total + lesion + normal + tumor + hcStroma + haStroma + necrosis;
  		//if NaN don't display value.
  		if (isNaN(total)) {
  			document.all.totalDiv.innerText = "";
  		} else {
  			document.all.totalDiv.innerText = total;
  		}
  }
  
  
  //computes a running total for Structures
  function updateStructureTotal(){
  		var structureList = new Array();
  		
  		structureList = document.forms[0].structureValueList;
  		
  		var total = 0;
  		if(structureList != null && typeof(structureList.length) == 'number'){
	  		for (i = 0 ; i < structureList.length ; i++){
	  			var temp = structureList[i];
	  			var tempValue = Number(temp.value);  							
	  			total = total + tempValue;
	  			
	  		}
  		} else if(structureList != null) {
  			var temp = structureList;
  			var tempValue = Number(temp.value);  			
  			total = total + tempValue;  			
  		}
  	
  		//check to see if we need to show the total row.
  		if(structureList != null){
  			document.all.structureTotalRow.style.display = 'inline';
  		} else {
	  		document.all.structureTotalRow.style.display = 'none';
  		}
  		//if NaN don't display value.
  		if ((!isNaN(total)) && (total >= 0)) {
  			document.all.structureTotalDiv.innerText = total;
  		} else {
  			document.all.structureTotalDiv.innerText = "";
  		}
  }
  
  //computes a running total for Lesions
  function updateLesionTotal(){
  		var lesionList = new Array();
  		
  		lesionList = document.forms[0].lesionValueList;
  		
  		var total = 0;
  		if(lesionList != null && typeof(lesionList.length) == 'number'){
  		for (i = 0 ; i < lesionList.length ; i++){
  			var temp = lesionList[i];
  			var tempValue = Number(temp.value);  			
  			total = total + tempValue;
  			
  		}
  		} else if(lesionList != null) {
  			var temp = lesionList;
  			var tempValue = Number(temp.value);  					
  			total = total + tempValue;
  			
  		}
  		
  }

  function onFormSubmit() {
    disableActionButtons(true);
    if (!dataIsValid()) {
      disableActionButtons(false);
      return false;
    }
    else {
      return true;
    }
  }

  function dataIsValid(){  
  	//if the user has pressed the submit/not report button, make sure that's what they
  	//intended to do
  	if (confirmSubmitNoReport) {
  		//turn the flag off so we don't ask them again unless they hit the submit/no report
  		//button again
  		setConfirmSubmitNoReport(false);
    	if (confirm("Note that you are submitting an evaluation without reporting it. \nPress Ok to save, or Cancel to choose another action.")) {
   		  document.limsCreateEvaluationForm.action = '<html:rewrite page="/lims/limsCreateEvaluation.do"/>';
   		}
   		else {
   			return false;
   		}
  	} 	
	//validate sample pathology
		//diagnosis
		if(document.forms[0].diagnosis.value == ''){
			alert("Please select a value for Sample Pathology");
			document.forms[0].diagnosis.focus();
			return false;
		}
		//diagnosisOther
		if(document.forms[0].diagnosisOther.disabled == false){
			if (trim(document.forms[0].diagnosisOther.value) == ''){
				alert("Please enter a value for Other Sample Pathology");
				document.forms[0].diagnosisOther.focus();
				return false;
			}
		}
	//validate site of origin
		//tissueOfOrigin
		if(document.forms[0].tissueOfOrigin.value == ''){
			alert("Please select a value for Tissue of Origin");
			document.forms[0].tissueOfOrigin.focus();
			return false;
		}
		//tissueOfOriginOther
		if(document.forms[0].tissueOfOriginOther.disabled == false){
			if (trim(document.forms[0].tissueOfOriginOther.value) == ''){
				alert("Please enter a value for Other Tissue of Origin");
				document.forms[0].tissueOfOriginOther.focus();
				return false;
			}
		}
	//validate site of finding
		//tissueOfFinding
		if(document.forms[0].tissueOfFinding.value == ''){
			alert("Please select a value for Tissue of Site of Finding");
			document.forms[0].tissueOfFinding.focus();
			return false;
		}
		//tissueOfFindingOther
		if(document.forms[0].tissueOfFindingOther.disabled == false){
			if (trim(document.forms[0].tissueOfFindingOther.value) == ''){
				alert("Please enter a value for Other Tissue of Site of Finding");
				document.forms[0].tissueOfFindingOther.focus();
				return false;
			}
		}
	//validate component %
		var lesionText = document.forms[0].lesionValue.value; 
  		var normalText = document.forms[0].normalValue.value;
  		var tumorText = document.forms[0].tumorValue.value;
  		var hcStromaText = document.forms[0].hcstromaValue.value;
  		var haStromaText = document.forms[0].hastromaValue.value;
  		var necrosisText = document.forms[0].necrosisValue.value;
		var lesion = Number(lesionText); 
  		var normal = Number(normalText);
  		var tumor = Number(tumorText);
  		var hcStroma = Number(hcStromaText);
  		var haStroma = Number(haStromaText);
  		var necrosis = Number(necrosisText);  		
  		
  		//Note that the call above to Number() can return a value of 0 even
  		//if the string passed to it is not a number.  For example, passing
  		//null or an empty string can return a 0.  So, to validate that we
  		//have a legitimate number make sure that the string is not null, not
  		//an empty string, and is a number.
   		if(normalText == null || normalText == '' || isNaN(normal)){
  			alert("The value for Normal is not a valid number.");
  			document.forms[0].normalValue.focus();
  			return false;
  		}
  		if(normal < 0 || normal > 100){
			alert("Please enter a valid value (0-100) for Normal.");
			document.forms[0].normalValue.focus();
			return false;
		}
  		
 		if(lesionText == null || lesionText == '' || isNaN(lesion)){
  			alert("The value for Lesion is not a valid number.");
  			document.forms[0].lesionValue.focus();
  			return false;
  		} 
  		if(lesion < 0 || lesion > 100){
			alert("Please enter a valid value (0-100) for Lesion.");
			document.forms[0].lesionValue.focus();
			return false;
		}
  		
  		if(tumorText == null || tumorText == '' || isNaN(tumor)){
  			alert("The value for Tumor is not a valid number.");
  			document.forms[0].tumorValue.focus();
  			return false;
  		}
  		if(tumor < 0 || tumor > 100){
			alert("Please enter a valid value (0-100) for Tumor.");
			document.forms[0].tumorValue.focus();
			return false;
		}
  		
  		if(hcStromaText == null || hcStromaText == '' || isNaN(hcStroma)){
  			alert("The value for Cellular Stroma is not a valid number.");
  			document.forms[0].hcstromaValue.focus();
  			return false;
  		}
  		if(hcStroma < 0 || hcStroma > 100){
			alert("Please enter a valid value (0-100) for Cellular Stroma.");
			document.forms[0].hcstromaValue.focus();
			return false;
		}
  		
  		if(haStromaText == null || haStromaText == '' || isNaN(haStroma)){
  			alert("The value for Hypo/Acellular Stroma is not a valid number.");
  			document.forms[0].hastromaValue.focus();
  			return false;
  		}
  		if(haStroma < 0 || haStroma > 100){
			alert("Please enter a valid value (0-100) for Hypo/Accellular Stroma.");
			document.forms[0].hastromaValue.focus();
			return false;
		}
  		
  		if(necrosisText == null || necrosisText == '' || isNaN(necrosis)){
  			alert("The value for Necrosis is not a valid number.");
  			document.forms[0].necrosisValue.focus();
  			return false;
  		}
  		if(necrosis < 0 || necrosis > 100){
			alert("Please enter a valid value (0-100) for Necrosis.");
			document.forms[0].necrosisValue.focus();
			return false;
		} 
  		
  		total = lesion + normal + tumor + hcStroma + haStroma + necrosis;
  		
  		if(total != 100){
  			alert("Composition Total must equal 100.");
  			return false;
  		}
	//validate structure %
		var structureList = new Array();
  		
  		structureList = document.forms[0].structureValueList;
  		
  		var structureTotal = 0;
  		if(structureList != null && typeof(structureList.length) == 'number'){
  		for (i = 0 ; i < structureList.length ; i++){
  			var temp = structureList[i];
  			var tempValue = Number(temp.value);
  			if(isNaN(tempValue) || tempValue < 0){
  				alert("The value: "+ temp.value +" is not a valid number.");
  				temp.focus();
  				return false;
  			} else {
  				structureTotal = structureTotal + tempValue;
  			}
  		} 
  		} else if(structureList != null) {
  			var temp = structureList;
  			var tempValue = Number(temp.value);
  			if(isNaN(tempValue) || tempValue < 0){
  				alert("The value: "+ temp.value +" is not a valid number.");
  				temp.focus();
  				return false;
  			} else {
  				structureTotal = tempValue;
  			}
  		
  		}
  		if((structureTotal != 100) && (structureList != null)){
  			alert("Structure Total must equal 100.");
  			return false;
  		}
  		
	//validate lesion %
		var lesionList = new Array();
  		
  		lesionList = document.forms[0].lesionValueList;
  		
  		var lesionTotal = 0;
  		if(lesionList != null && typeof(lesionList.length) == 'number'){
  		for (i = 0 ; i < lesionList.length ; i++){
  			var temp = lesionList[i];
  			var tempValue = Number(temp.value);
  			if(isNaN(tempValue) || tempValue < 0){
  				alert("The value: "+ temp.value +" is not a valid number.");
  				temp.focus();
  				return false;
  			} else {
  				lesionTotal = lesionTotal + tempValue;
  			}
  		}
  		} else if(lesionList != null) {
  			var temp = lesionList;
  			var tempValue = Number(temp.value);
  			if(isNaN(tempValue) || tempValue < 0){
  				alert("The value: "+ temp.value +" is not a valid number.");
  				temp.focus();
  				return false;
  			} else {
  				lesionTotal = tempValue;
  			}
  		
  		}
		if((lesionTotal != 100) && (lesionList != null)){
  			alert("Lesion Total must equal 100.");
  			return false;
  		} else if (lesionTotal == 100 && lesion == 0){
  			alert("Lesion must have a value if Lesions are specified.");
  			return false;
  		}
  		//if we made it here, the data is valid
  		return true;
  }
  
  
  //disallow enter to be pressed inside a textbox
  function checkEnter(event){ 	
	var code = 0;
	code = event.keyCode;
	
	//alert(code);
  
	  if(code == 13){
 	    return false;
      }
  }
  
  //generic method for adding internal or external comments.
  //NOTE:  value is only passed when recreating the page from data beans 
  // (after a form submit, copy, validation error, etc.)
  function addItem(obj, value){
  	
	var newRow;
	var newCellLabel;
	var newCellValue;
	var strNew;
	var allRows = document.all.features.rows;
	
	//add an external comment
	if(obj == 'ext'){
	   // check the text in the textbox and if it's empty, do nothing
	   if(document.forms[0].addExternal.value != '' || value != null){
	        //if value is passed in, don't use the textbox value, use the passed in value
	        if(value == null){
	        	strNew = htmlEscape(new String(trim(document.forms[0].addExternal.value)));
	        } else {
	        	strNew = htmlEscape(trim(value));
	        }
	        if(strNew == ''){
	        	return false;
	       	}
			
			document.forms[0].addExternal.value = '';
			
			//loop through the comment rows, find the next available position and add the new row
			for(j = 0 ; j < allRows.length ; j++){
		  		var submittedRow = allRows[j];
		  		//if the type attribute of the row equals 'efc' it means we have reached
		  		//the end of the current section and we need to insert a row in the current postion
		  		if('efc' == submittedRow.getAttribute("type")){
		   	 		newRow = document.all.features.insertRow(j);
			 		break;
		  		} 
			}
			document.forms[0].addExternal.focus();
	   } else {
	   		return false;
	   }
	} else if(obj == 'inte'){
	   if(document.forms[0].addInternal.value != '' || value != null){
	   		if(value == null){
	   			strNew = htmlEscape(new String(trim(document.forms[0].addInternal.value)));
	   		} else {
	   			strNew = htmlEscape(trim(value));
	   		}
		    if(strNew == ''){
	        	return false;
	       	}
			document.forms[0].addInternal.value = '';
			for(j = 0 ; j < allRows.length ; j++){
				var submittedRow = allRows[j];
		  		//if the type attribute of the row equals 'iqic' it means we have reached
		  		//the end of the current section and we need to insert a row in the current postion
				if('iqic' == submittedRow.getAttribute("type")){
		   	 		newRow = document.all.features.insertRow(j);
			 		break;
				}	 
			}
			document.forms[0].addInternal.focus()
	   } else {
	   		return false;
	   }
	}

		//set all the cells and styles for the comment row.	
		newRow.className = "white";
	
		newCellLabel = newRow.insertCell();
		newCellLabel.align = "center";
		newCellValue = newRow.insertCell();	
		if(obj == 'ext') {
			newCellLabel.innerHTML = "<td><input type='checkbox' name='externalOtherList'  value='" + strNew + "' checked></td>";
		}else if(obj == 'inte') {
			newCellLabel.innerHTML = "<td><input type='checkbox' name='internalOtherList' value='" + strNew + "' checked></td>";		
		}
		newCellValue.innerHTML = "<td>" + strNew + "</td>";
	
  } 
  
 
  //removes a row when the 'X' image is pressed.
  function hideRow(obj, type){
	var allRows = document.all.composition.rows;
	for(j = 0 ; j < allRows.length ; j++){
		var submittedRow = allRows[j];
		if(submittedRow.getAttribute("type") == type){
			if(obj == submittedRow.getAttribute("code")){
		   		document.all.composition.deleteRow(j);
		   		//remove the text from structures and lesions to ensure that 
		   		//if it is a structure or lesion, the same value can be added again
		   		enteredStructures[obj] = null;
		   		enteredLesions[obj] = null;
			}
		}
	}
	//a total has been changed, update them all to be safe
	updateTotal();
	updateStructureTotal();
	updateLesionTotal();
  }
  
  //removes an other row when the 'X' image is pressed.
  function hideRowOther(obj, type){
 	var allRows = document.all.composition.rows;
	for(j = 0 ; j < allRows.length ; j++){
		var submittedRow = allRows[j];
		if(submittedRow.getAttribute("type") == type){
			if(obj == submittedRow.getAttribute("value")){
		   		document.all.composition.deleteRow(j);
		   		//remove the text from structures and lesions to ensure that 
		   		//if it is a structure or lesion, the same value can be added again
		   		enteredOtherStructures[obj] = null;
		   		enteredOtherLesions[obj] = null;
			}
		}
	}
	//a total has been changed, update them all to be safe
	updateTotal();
	updateStructureTotal();
	updateLesionTotal();
  }

 //Method for inserting a lesion row
 //NOTE: number is only passed in if page is being recreated (copy, validation error, etc)
 function addLesion(value, code, isOther, number){
  	var htmlValue = htmlEscape(value);
  	
  	//handle duplicates for lesion entry.  Similar for structures and lesions
  	//other features are handled in the getSelectedHierarchy
  	if(isOther == 'true' || isOther == true){
		if(enteredOtherLesions[value] != null){
			return;
		} else {
			enteredOtherLesions[value] = value;
		}
	} else {
		if(enteredLesions[code] != null){
			return;
		} else {
			enteredLesions[code] = value;
		}
	}
	
  	var newRow;
	var newCellLabel;
	var newCellValue;
	var deleteCell;
	var emptyCell;
	 
	var type;
	var allRows = document.all.composition.rows;
	
	//loop through the available rows and insert a new row on top of the inflammation row
	//which is the bottom on lesion
	for(j = 0 ; j < allRows.length ; j++){
		var submittedRow = allRows[j];
		
		if('inflammationheader' == submittedRow.getAttribute("type")){
		   	 newRow = document.all.composition.insertRow(j);
			 break;
		} 
	}
	
	//fill out the new row with table cells and all pertinent info
	newRow.className = "white";
	newRow.setAttribute("code", code);
	newRow.setAttribute("value", value);
	
	//the type attribute is different for standard ARTS code and other codes
	//specify the difference in the type attribute
	if(isOther == 'true' || isOther == true){
		type = "lesionOther";
	} 
	else {
		type = "lesion";	
	}
	newRow.setAttribute("type", type);
	
	newCellLabel = newRow.insertCell();
	newCellLabel.className = "level_3";
	deleteCell = newRow.insertCell();
	deleteCell.align = 'center';
	newCellValue = newRow.insertCell();
	newCellValue.align = "center";
	emptyCell = newRow.insertCell();
	
	newCellLabel.innerHTML =  htmlValue + 											 
							  "<input type='hidden' name='lesionTextList' value='" + htmlValue + "'>" +
							  "<input type='hidden' name='lesionIsOtherList' value='"+ isOther +"'>" + 
							  "<input type='hidden' name='lesionCodeList' value='"+ code +"'>";
	if(isOther == 'true') {
		deleteCell.innerHTML = "<a href='javascript:void(0)' tabindex='-1'><img src='<html:rewrite page="/images/delete.gif"/>' alt='Delete' border=0 onClick='hideRowOther(\"" + jsEscapeInXMLAttr(value) + "\", \""+ type +"\")'></a>";
	} 
	else {
		deleteCell.innerHTML = "<a href='javascript:void(0)' tabindex='-1'><img src='<html:rewrite page="/images/delete.gif"/>' alt='Delete' border=0 onClick='hideRow(\"" + code + "\", \""+ type +"\")'></a>";
	}
	
	//if number is passed in, use the value passed in. Otherwise default to 0
	if(number != null && number != ''){
		newCellValue.innerHTML = "<input type='text' size='3' maxlength='3' value='" + number + "' name='lesionValueList' onBlur='updateLesionTotal();' tabindex='13' onKeyDown='return checkEnter(event);'>";
	} else {
		newCellValue.innerHTML = "<input type='text' size='3' maxlength='3' value='0' name='lesionValueList' onBlur='updateLesionTotal();' tabindex='13' onKeyDown='return checkEnter(event);'>";
	}
	
	emptyCell.innerHTML = "&nbsp;";

	//if number is passed in, the total will be updated.
	updateLesionTotal();
	
  }
  
  
  function addStructure(value, code, isOther, number){
  	var type;
	if(isOther == 'true' || isOther == true){
		type = "structureOther";
		if(enteredOtherStructures[value] != null){
			return;
		} else {
			enteredOtherStructures[value] = value;
		}
	} 
	else {
		type = "structure";	
		
		if(enteredStructures[code] != null){
			return;
		} else {
			enteredStructures[code] = value;
		}
		
	}
  	
  	var htmlValue = htmlEscape(value);
  	
	var newRow;
	var newCellLabel;
	var newCellValue;
	var deleteCell;
	
	var allRows = document.all.composition.rows;
	for(j = 0 ; j < allRows.length ; j++){
		var submittedRow = allRows[j];
		
		if('tumorheader' == submittedRow.getAttribute("type")){
		   	 newRow = document.all.composition.insertRow(j);
			 break;
		} 
	}

	
	newRow.setAttribute("type", type);
	
	newRow.className = "white";
	newRow.setAttribute("code", code);
	newRow.setAttribute("value", value);
	newCellLabel = newRow.insertCell();
	newCellLabel.className = "level_3";
	deleteCell = newRow.insertCell();
	deleteCell.align = 'center';
	newCellValue = newRow.insertCell();
	newCellValue.align = 'center';
	emptyCell = newRow.insertCell();
	
	newCellLabel.innerHTML =  htmlValue + "<input type='hidden' name='structureTextList' value='"+ htmlValue +"'><input type='hidden' name='structureIsOtherList' value='"+ isOther +"'><input type='hidden' name='structureCodeList' value='"+ code +"'>";
	
	if( number != null && number !=  ''){
		newCellValue.innerHTML = "<input type='text' size='3' maxlength='3' value='" + number + "' name='structureValueList' name='structureValueList'  tabindex='17' onBlur='updateStructureTotal();' onKeyDown='return checkEnter(event);'>";
	} else {
		newCellValue.innerHTML = "<input type='text' size='3' maxlength='3' value='0' name='structureValueList' name='structureValueList'  tabindex='17' onBlur='updateStructureTotal();' onKeyDown='return checkEnter(event);'>";
	}
    
    if(isOther == 'true' || isOther == true){
	    deleteCell.innerHTML = "<a href='javascript;' tabindex='-1'><img src='<html:rewrite page="/images/delete.gif"/>' alt='Delete' border=0 onClick='hideRowOther(\"" + jsEscapeInXMLAttr(value) + "\", \""+ type +"\")'></a>";				
	} else {
		deleteCell.innerHTML = "<a href='javascript;' tabindex='-1'><img src='<html:rewrite page="/images/delete.gif"/>' alt='Delete' border=0 onClick='hideRow(\"" + code + "\", \"" + type + "\")'></a>";				
	}
	emptyCell.innerHTML = "&nbsp;";
	
	updateStructureTotal();
	
  }


  //generic function to add the 3 feature types.
  function addFeature(value, code, type, isOther){
  	
	var htmlValue = htmlEscape(value);
  	
	var newRow;
	var newCellLabel;
	var deleteCell;
	var emptyCell;
	var codeName;
	var textName;
	var isOtherName;
	
	//nested if block to add the row in the appropriate place and the row will 
	//be filled in later on.
	if(type == 'tumor'){
		
		var allRows = document.all.composition.rows;
		codeName = 'tumorFeatureCodeList';
		textName = 'tumorFeatureTextList';
		isOtherName = 'tumorFeatureIsOtherList';
		for(j = 0 ; j < allRows.length ; j++){
			var submittedRow = allRows[j];
			
			if('hcsheader' == submittedRow.getAttribute("type")){
		   	 	newRow = document.all.composition.insertRow(j);
			 	break;
			} 
		}
	} else if(type == 'cstroma'){
	
		var allRows = document.all.composition.rows;
		codeName = 'cellularFeatureCodeList';
		textName = 'cellularFeatureTextList';
		isOtherName = 'cellularFeatureIsOtherList';
		for(j = 0 ; j < allRows.length ; j++){
			var submittedRow = allRows[j];
		
			if('hasheader' == submittedRow.getAttribute("type")){
		   		newRow = document.all.composition.insertRow(j);
				break;
			} 
		}
	} if(type == 'hastroma'){
	
		var allRows = document.all.composition.rows;
		codeName = 'hypoCellularFeatureCodeList';
		textName = 'hypoCellularFeatureTextList';
		isOtherName = 'hypoCellularFeatureIsOtherList';
		for(j = 0 ; j < allRows.length ; j++){
			var submittedRow = allRows[j];
		
			if('necrosisheader' == submittedRow.getAttribute("type")){
		   		 newRow = document.all.composition.insertRow(j);
				 break;
			} 
		}
	}
	
	if(isOther == 'true' || isOther == true){
		type = type + "Other";
	} 
	
	
	//fill in the row.  all this information is the same other than the type, so fill it in now
	newRow.setAttribute("type", type);
	newRow.className = "white";
	newRow.setAttribute("code", code);
	newRow.setAttribute("value", value); 
	newCellLabel = newRow.insertCell();
	newCellLabel.className = "level_3";
	newCellLabel.colSpan = 2;
	deleteCell = newRow.insertCell();
	deleteCell.align = 'center';
	emptyCell = newRow.insertCell();
	newCellLabel.innerHTML = htmlValue + "<input type='hidden' name='" + textName + "' value='" + htmlValue +"'><input type='hidden' name='" + isOtherName + "' value='" + isOther +"'><input type='hidden' name='" + codeName + "' value='" + code +"'>";
	if(isOther == 'true'){
	    deleteCell.innerHTML = "<a href='javascript:void(0)' tabindex='-1'><img src='<html:rewrite page="/images/delete.gif"/>' alt='Delete' border=0 onClick='hideRowOther(\"" + jsEscapeInXMLAttr(value) + "\", \""+ type +"\")'></a>";				
	} else {
		deleteCell.innerHTML = "<a href='javascript:void(0)' tabindex='-1'><img src='<html:rewrite page="/images/delete.gif"/>' alt='Delete' border=0 onClick='hideRow(\"" + code + "\", \""+ type +"\")'></a>";				
	}
	emptyCell.innerHTML = "&nbsp;";
  }
  
  function addInflammation(value, code, isOther, number){
  	
  	var htmlValue = htmlEscape(value);
  	
	var newRow;
	var newCellLabel;
	var newCellValue;
	var deleteCell;
	var emptyCell;
	
	var allRows = document.all.composition.rows;
	for(j = 0 ; j < allRows.length ; j++){
		var submittedRow = allRows[j];
		
		if('structureheader' == submittedRow.getAttribute("type")){
		   	 newRow = document.all.composition.insertRow(j);
			 break;
		} 
	}
	
	var type;
	if(isOther == 'true' || isOther == true){
		type = "inflammationOther";
	} 
	else {
		type = "inflammation";	
	}
	newRow.setAttribute("type", type);
	
	newRow.className = "white";
	newRow.setAttribute("code", code);
	newRow.setAttribute("value", value);
	
	newCellLabel = newRow.insertCell();
	newCellLabel.className = "level_3";
	newCellValue = newRow.insertCell();
	newCellValue.align = 'center';
	deleteCell = newRow.insertCell();
	deleteCell.align = 'center';
	emptyCell = newRow.insertCell();
	
	newCellLabel.innerHTML = htmlValue + "<input type='hidden' name='inflammationTextList' value='" + htmlValue +"'><input type='hidden' name='inflammationCodeList' value='" + code +"'>";
	if(number != null && number == '1'){
		newCellValue.innerHTML = "<input type='hidden' name='inflammationIsOtherList' value='" + isOther +"'><select name='inflammationValueList' tabindex='15'><option value='1' selected>Mild</option><option value='2'>Moderate</option><option value='3'>Severe</option></select>"
	} else if(number != null && number == '2'){
		newCellValue.innerHTML = "<input type='hidden' name='inflammationIsOtherList' value='" + isOther +"'><select name='inflammationValueList' tabindex='15'><option value='1'>Mild</option><option value='2' selected>Moderate</option><option value='3'>Severe</option></select>"
	} else if(number != null && number == '3'){
		newCellValue.innerHTML = "<input type='hidden' name='inflammationIsOtherList' value='" + isOther +"'><select name='inflammationValueList' tabindex='15'><option value='1'>Mild</option><option value='2'>Moderate</option><option value='3' selected>Severe</option></select>"
	} else {
		newCellValue.innerHTML = "<input type='hidden' name='inflammationIsOtherList' value='" + isOther +"'><select name='inflammationValueList' tabindex='15'><option value='1'>Mild</option><option value='2'>Moderate</option><option value='3'>Severe</option></select>"
	}
	
	if(isOther == 'true'){
	    deleteCell.innerHTML = "<a href='javascript:void(0)' tabindex='-1'><img src='<html:rewrite page="/images/delete.gif"/>' alt='Delete' border=0 onClick='hideRowOther(\"" + jsEscapeInXMLAttr(value) + "\", \""+ type +"\")'></a>";				
	} else {
		deleteCell.innerHTML = "<a href='javascript:void(0)' tabindex='-1'><img src='<html:rewrite page="/images/delete.gif"/>' alt='Delete' border=0 onClick='hideRow(\"" + code + "\", \"" + type + "\")'></a>";				
	}
	emptyCell.innerHTML = "&nbsp;"
		
  }
     
     
  //function that does most of the work for adding new rows for inflammations and features   
  function getSelectedHierarchy(theURL,features, type) {
	var firstSelectedValues = new Array();
  	var windowParameters = new Array();
	
   	var allRows = document.all.composition.rows;
	
	// based upon the type, collect all the currently displayed values
	//windowParameters holds a list of only ARTS values.  other values are not 
	//passed to the popup hierarchy window.  This allows multiple others to be entered.
	for(j = 0 ; j < allRows.length ; j++){
		var submittedRow = allRows[j];
		if(submittedRow.getAttribute("type") == (type + "Other")){
			firstSelectedValues[j] = submittedRow.getAttribute("code");
		} 
		else if(submittedRow.getAttribute("type") == type){
			firstSelectedValues[j] = submittedRow.getAttribute("code");
			windowParameters[j] = submittedRow.getAttribute("code");
		}
	}
	
        
	var oldSelectedValues = firstSelectedValues.toString();
    var selectedValues = new Array();
	
	//pass the currently displayed values to the popup so they can be selected already upon loading.
	//returns a new list of selected codes
	selectedValues = window.showModalDialog(theURL,windowParameters,features);
	
	//the popup was canceled.
	if(selectedValues == null){
		return;
	}
	
	//loop through the new values and do a comparison and remove any rows that are not there 
	//anymore
	var stringSelectedValues = '';
	for(q = 0 ; q < selectedValues.length ; q++){
		stringSelectedValues += selectedValues[q]; 
	}
	for(k = 0 ; k < firstSelectedValues.length ; k++){
	     var temp = firstSelectedValues[k];
	     if(stringSelectedValues.indexOf(temp) == -1){
	     	hideRow(temp, type);
	     }
	}
	
	//loop through the selected values and add all new rows
	var i = 0;
	while(i < selectedValues.length ) {
		if (selectedValues[i] != null) {
			
			//split the code from the text value.
			var sParts = selectedValues[i].split('!#!#');
			var isOther = false;
			if(sParts.length == 2){
			  value = sParts[0];
			  //split on the 'other' delimeter.  if it returns 2 tokens, it's an other
			  var other = value.split('!~!~');
			  if(other.length == 2){
			     isOther = true;
			     value = other[0];
			  }
			  
			  //grab the code, check to see if it is currently in the list.
			  //if the row already exists, we don't want to add another row.
			  code = sParts[1];
			  if(oldSelectedValues.indexOf(code) == -1 || isOther){
			  	if(type == "lesion"){
				    addLesion(value, code, isOther, null);
				} else if(type == "structure"){
					addStructure(value, code, isOther, null);
				} else if(type == "inflammation"){
					addInflammation(value, code, isOther, null);
				} else if(type == "tumor" || type == "cstroma" || type == "hastroma"){
					addFeature(value, code, type, isOther);
				} 
			  }
			}
		}	
		i++;
	}
  
  }
  
  function copySiteOfFinding() {
    if (isFindingListFiltered) {
    	alert("Tissue of Site of Finding is filtered now. Refresh Tissue of Site of Finding and copy again.");
    } else {
		document.limsCreateEvaluationForm.tissueOfFinding.value = document.limsCreateEvaluationForm.tissueOfOrigin.value;
		if (document.limsCreateEvaluationForm.tissueOfOriginOther.disabled) {
			document.limsCreateEvaluationForm.tissueOfFindingOther.value = "N/A";
			document.limsCreateEvaluationForm.tissueOfFindingOther.disabled = true;
		}else if(!document.limsCreateEvaluationForm.tissueOfOriginOther.disabled) {
			document.limsCreateEvaluationForm.tissueOfFindingOther.disabled = false;
			document.limsCreateEvaluationForm.tissueOfFindingOther.value = 
						document.limsCreateEvaluationForm.tissueOfOriginOther.value;
		}
	}	 
  }
  function addReason(URL, windowParameters, name) {
    disableActionButtons(true);
    var reason = null;
  	var returnedObject = window.showModalDialog(URL, windowParameters, name);
  	if (returnedObject != null) {
  	  reason = returnedObject.reasonText;
  	}
  	
  	if (reason != null) {
  		if (name == 'pull') {
  			document.limsCreateEvaluationForm.action = '<html:rewrite page="/lims/limsPullSample.do"/>';
		  	document.limsCreateEvaluationForm.reason.value = reason;
		} else if (name == 'discordant') {
			document.limsCreateEvaluationForm.action = '<html:rewrite page="/lims/limsDiscordantSample.do"/>';
			document.limsCreateEvaluationForm.reason.value = reason;
		}
		setWindowTarget("_self");
  	  	document.limsCreateEvaluationForm.submit();
  	}
  	else {
  		disableActionButtons(false);
  	}
  }
  
  function setWindowTarget(target) {
	document.limsCreateEvaluationForm.target=target;
  }
  
  function disableActionButtons(bool) {
    document.limsCreateEvaluationForm.submitNotReport.disabled=bool;
    document.limsCreateEvaluationForm.submitReport.disabled=bool;
    document.limsCreateEvaluationForm.cancel.disabled=bool;
    document.limsCreateEvaluationForm.pullButton.disabled=bool;
    document.limsCreateEvaluationForm.discordantButton.disabled=bool;
  }
  
  function reportForm() {
  	document.limsCreateEvaluationForm.action = '<html:rewrite page="/lims/limsCreateEvaluationReportEvaluation.do"/>';
  }
  
  function setConfirmSubmitNoReport(value) {
  	confirmSubmitNoReport = value;
  }
  
  function showWarning(message) {
    //disable the action buttons, to prevent the user from submitting twice
    disableActionButtons(true);
	var bool = confirm(message);
	if (bool) {
		document.limsCreateEvaluationForm.userWarned.value = "true";
		setWindowTarget("_self");
        document.limsCreateEvaluationForm.submit();
	    return true;	
	}else {
		document.limsCreateEvaluationForm.userWarned.value = "";
		//user has chosen to cancel, so re-enable the action buttons
        disableActionButtons(false);
	}
  }
  
  function viewPvReport() {
  	document.limsCreateEvaluationForm.action = '<html:rewrite page="/lims/limsCreateEvaluationPvReport.do"/>';
  	setWindowTarget("_pvWindow");
  	document.limsCreateEvaluationForm.submit();
  }
  
  //new function for handling adding structures from dropdown
  function handleStructures(type){
  	//based upon whether we are adding an other, a structure, or a group of structures,
  	//call the appropriate javascript function defined in LimsCreateEvaluationForm.populateStructureInfo  
  	if(type == 'other'){
  		var otherText = document.all.otherStructureText.value;
  		otherText = trim(otherText);
  		if(otherText == null || otherText == ''){
  			return;
  		}
  		addStructure(otherText,	'<%= com.ardais.bigr.lims.helpers.LimsConstants.OTHER_STRUCTURE %>', true, 0);
  		document.all.otherStructureText.value = '';
  	} else if (type == 'tissue'){
  		var tissue = document.all.tissueDrop.options[document.all.tissueDrop.selectedIndex].value;
  		if(tissue == null || tissue == ''){
  			return;
  		}
  		//this function is defined in the form
  		addStructuresByTissue(tissue);
  	} else if (type == 'structure'){
  		var structure = document.all.structureDrop.options[document.all.structureDrop.selectedIndex].value;
  		if(structure == null || structure == ''){
  			return;
  		}
  		//this function is defined in the form
  		addStructuresByStructure(structure);
  	}
  }
  
  
  //new function for handling adding lesions from dropdown.
  function handleLesions(type){
  	if(type == 'other'){
  		var otherText = document.all.otherLesionText.value;
  		otherText = trim(otherText);
  		if(otherText == null || otherText == ''){
  			return;
  		}
  		addLesion(otherText,	'<%= com.ardais.bigr.iltds.helpers.FormLogic.OTHER_DX %>', true, 0);
  		document.all.otherLesionText.value = '';
  	} else if (type == 'lesion'){
  		var lesion = document.all.lesionDrop.options[document.all.lesionDrop.selectedIndex].value;
  		if(lesion == null || lesion == ''){
  			return;
  		}
  		//this function is defined in the form
  		addLesionFromDropDown(lesion);
  	}
  }
  
  var structuresOpen = false;
  var lesionsOpen = false;

function workaroundRepaintBug4287() {
  // Works around bug #4287, really only an IE5 problem
  // so far but we just do it in all browsers for now.
  window.resizeBy(0, 1);
  window.resizeBy(0, -1);
  setTimeout('jiggleSelectVisibility();', 1);
}

function jiggleSelectVisibility() {
  // Works around bug #4287, really only an IE5 problem
  // so far but we just do it in all browsers for now.
  var selects = document.all.tags('SELECT');
  var i = 0;

  for (i = 0; i < selects.length; i++) {
    var e = selects[i];
    // 'hidden' and 'visible' aren't the only possible values
    // for visibility, so we save off the old value first.
    var oldValue = e.style.visibility;
    alert(e.style.visibility);
    e.style.visibility = 'hidden';
    e.style.visibility = oldValue;
  }
}

  //handles the flag for whether structures are open or not.
  function changeStructure(){
	showStructures(!structuresOpen);	 	
  }
  
  //handles the flag for whether structures are open or not.
  function changeLesion(){
	showLesions(!lesionsOpen);	 	
  }

  //expand of collapse the structure options
  function showStructures(bool){
	var displayValue;
	var disabled;
	if(bool){
		structuresOpen = true;
		document.all.structureImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		document.all.structureImg.alt = "Close menu";
		displayValue = 'inline';
		disabled = false;
	} else {
		structuresOpen = false;
		document.all.structureImg.src = "<html:rewrite page='/images/MenuClosed.gif'/>";
		document.all.structureImg.alt = "Open menu";
		displayValue = 'none';
		disabled = true;
	}
	
	var tableArray = document.all.composition.rows;
	for(i = 0 ; i < tableArray.length ; i++){
		var submittedRow = tableArray[i];	
		if(submittedRow.id == 'structureadd'){
			submittedRow.style.display = displayValue;
			submittedRow.disabled = disabled;
		}
	}	
	document.all.tissueDrop.style.display = displayValue;
	document.all.tissueDrop.disabled = disabled;
	document.all.structureDrop.style.display = displayValue;
	document.all.structureDrop.disabled = disabled;		 	
}

  //expand of collapse the structure options
  function showLesions(bool){
	var displayValue;
	var disabled;
	if(bool){
		lesionsOpen = true;
		document.all.lesionImg.src = "<html:rewrite page='/images/MenuOpened.gif'/>";
		document.all.lesionImg.alt = "Close menu";
		displayValue = 'inline';
		disabled = false;
	} else {
		lesionsOpen = false;
		document.all.lesionImg.src = "<html:rewrite page='/images/MenuClosed.gif'/>";
		document.all.lesionImg.alt = "Open menu";
		displayValue = 'none';
		disabled = true;
	}
	
	var tableArray = document.all.composition.rows;
	for(i = 0 ; i < tableArray.length ; i++){
		var submittedRow = tableArray[i];	
		if(submittedRow.id == 'lesionadd'){
			submittedRow.style.display = displayValue;
			submittedRow.disabled = disabled;
		}
	}	
	document.all.lesionDrop.style.display = displayValue;
	document.all.lesionDrop.disabled = disabled;
	
}
  //these 2 bean writes print out the javascript defined dynamically in the form
  //they handle the inserting of structure and lesion rows based upon the drop downs.
  <bean:write name="limsCreateEvaluationForm" property="structureJavaScript" filter="false"/>
  <bean:write name="limsCreateEvaluationForm" property="lesionJavaScript" filter="false"/>
  
  window.onresize = onResizePage;
    </script>
  </head>
<body class="bigr" onLoad="initPage();" style="margin-top: 0; margin-bottom: 0;">
 <html:form  action="/lims/limsCreateEvaluation.do" onsubmit="return(onFormSubmit());">
    <html:hidden name="limsCreateEvaluationForm" property="createMethod"/>     
    <html:hidden name="limsCreateEvaluationForm" property="userWarned"/>
    <html:hidden name="limsCreateEvaluationForm" property="sourceEvaluationId"/>            
    <logic:present name="popup">
    	<input type="hidden" name="popup" value="true">
    </logic:present>
    <div id="Header" style="display: block; overflow: auto; border: 1px solid #336699; width:100%; height: 250px;"> 
      <template:get name='header' flush="true"/>
    </div>
    <div id="Details" style="display: block; position: relative; overflow: auto; width:100%; border-width: 2px 2px 2px 2px; border-style: solid; border-color: #336699; margin-top: 10px;"> 
      <script type="text/javascript">positionItems();</script>
      <template:get name='detail' flush="true"/><br>
      <jsp:include page="/hiddenJsps/lims/pathology_lims/includes/LimsSampleComposition.jsp" flush="true"/>
    </div>
    
<script type="text/javascript">
	<logic:present name="limsCreateEvaluationForm"
		property="lesionCodeList">
		<bean:define id="lesionText" name="limsCreateEvaluationForm"
			property="lesionTextList" type="java.lang.String[]" />
		<bean:define id="lesionValue" name="limsCreateEvaluationForm"
			property="lesionValueList" type="java.lang.String[]" />
		<bean:define id="lesionIsOther" name="limsCreateEvaluationForm"
			property="lesionIsOtherList" type="java.lang.String[]" />
		<logic:iterate id="lesionCode" name="limsCreateEvaluationForm"
			property="lesionCodeList" indexId="index">
          addLesion('<%= Escaper.jsEscapeInScriptTag(lesionText[index.intValue()])%>', '<%= lesionCode%>', '<%= lesionIsOther[index.intValue()]%>', '<%= lesionValue[index.intValue()]%>');	
		</logic:iterate>
	</logic:present>
	<logic:present name="limsCreateEvaluationForm"
		property="inflammationCodeList">
		<bean:define id="inflammationText" name="limsCreateEvaluationForm"
			property="inflammationTextList" type="java.lang.String[]" />
		<bean:define id="inflammationValue" name="limsCreateEvaluationForm"
			property="inflammationValueList" type="java.lang.String[]" />
		<bean:define id="inflammationIsOther" name="limsCreateEvaluationForm"
			property="inflammationIsOtherList" type="java.lang.String[]" />
		<logic:iterate id="inflammationCode" name="limsCreateEvaluationForm"
			property="inflammationCodeList" indexId="index">
  			addInflammation('<%= Escaper.jsEscapeInScriptTag(inflammationText[index.intValue()])%>', '<%= inflammationCode %>', '<%= inflammationIsOther[index.intValue()] %>', '<%= inflammationValue[index.intValue()] %>');
		</logic:iterate>
	</logic:present>
	<logic:present name="limsCreateEvaluationForm"
		property="structureCodeList">
		<bean:define id="structureText" name="limsCreateEvaluationForm"
			property="structureTextList" type="java.lang.String[]" />
		<bean:define id="structureValue" name="limsCreateEvaluationForm"
			property="structureValueList" type="java.lang.String[]" />
		<bean:define id="structureIsOther" name="limsCreateEvaluationForm"
			property="structureIsOtherList" type="java.lang.String[]" />
		<logic:iterate id="structureCode" name="limsCreateEvaluationForm"
			property="structureCodeList" indexId="index">
  			addStructure('<%= Escaper.jsEscapeInScriptTag(structureText[index.intValue()]) %>', '<%= structureCode %>', '<%= structureIsOther[index.intValue()] %>', '<%= structureValue[index.intValue()]%>');
		</logic:iterate>
	</logic:present>
	<logic:present name="limsCreateEvaluationForm"
		property="tumorFeatureCodeList">
		<bean:define id="tumorText" name="limsCreateEvaluationForm"
			property="tumorFeatureTextList" type="java.lang.String[]"  />
		<bean:define id="tumorIsOther" name="limsCreateEvaluationForm"
			property="tumorFeatureIsOtherList" type="java.lang.String[]"  />
		<logic:iterate id="tumorCode" name="limsCreateEvaluationForm"
			property="tumorFeatureCodeList" indexId="index">
				addFeature('<%= Escaper.jsEscapeInScriptTag(tumorText[index.intValue()]) %>', '<%= tumorCode %>', 'tumor', '<%= tumorIsOther[index.intValue()] %>');
		</logic:iterate>
	</logic:present>
	<logic:present name="limsCreateEvaluationForm"
		property="cellularFeatureCodeList">
		<bean:define id="cellularText" name="limsCreateEvaluationForm"
			property="cellularFeatureTextList" type="java.lang.String[]"  />
		<bean:define id="cellularIsOther" name="limsCreateEvaluationForm"
			property="cellularFeatureIsOtherList" type="java.lang.String[]"  />
		<logic:iterate id="cellularCode" name="limsCreateEvaluationForm"
			property="cellularFeatureCodeList" indexId="index">
			addFeature('<%= Escaper.jsEscapeInScriptTag(cellularText[index.intValue()]) %>', '<%= cellularCode %>', 'cstroma', '<%= cellularIsOther[index.intValue()] %>');
		</logic:iterate>
	</logic:present>
	<logic:present name="limsCreateEvaluationForm"
		property="hypoCellularFeatureCodeList">
		<bean:define id="hypoCellularText" name="limsCreateEvaluationForm"
			property="hypoCellularFeatureTextList" type="java.lang.String[]"  />
		<bean:define id="hypoCellularIsOther" name="limsCreateEvaluationForm"
			property="hypoCellularFeatureIsOtherList" type="java.lang.String[]" />
		<logic:iterate id="hypoCellularCode" name="limsCreateEvaluationForm"
			property="hypoCellularFeatureCodeList" indexId="index">
			addFeature('<%= Escaper.jsEscapeInScriptTag(hypoCellularText[index.intValue()] )%>', '<%= hypoCellularCode %>', 'hastroma', '<%= hypoCellularIsOther[index.intValue()] %>');
		</logic:iterate>
	</logic:present>
	<logic:present name="limsCreateEvaluationForm" property="internalOtherList">
	<logic:iterate id="internalOther" name="limsCreateEvaluationForm" property="internalOtherList">
			addItem("inte", '<%= Escaper.jsEscapeInScriptTag((String)internalOther) %>');
	</logic:iterate>	
	</logic:present>
	<logic:present name="limsCreateEvaluationForm" property="externalOtherList">
	<logic:iterate id="externalOther" name="limsCreateEvaluationForm" property="externalOtherList">
			addItem("ext", "<%= Escaper.jsEscapeInScriptTag((String)externalOther) %>");
	</logic:iterate>	
	</logic:present>
	<logic:equal name="limsCreateEvaluationForm" property="showWarning" value="true">
	 showWarning('<bean:write name="limsCreateEvaluationForm" property="warningMessage" filter="false"/>');
    </logic:equal>

	<%-- after the page is completely loaded, update all the totals. --%>
	updateTotal();
	updateStructureTotal();
	updateLesionTotal();
</script>
</html:form>
</body>
</html> 
