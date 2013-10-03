<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.kc.form.def.BigrFormDefinition"%>
<%@ page import="com.ardais.bigr.kc.form.def.TagTypes" %>
<%@ page import="com.ardais.bigr.kc.web.form.def.ResultsFormDefinitionForm"%>
<%@ page import="com.ardais.bigr.library.web.column.SampleColumn"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%@ page import="com.ardais.bigr.util.UniqueIdGenerator"%>
<%@ page import="com.gulfstreambio.kc.form.def.Tag" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinition" %>
<%@ page import="com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement" %>
<%@ page import="com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement" %>
<%@ page import="com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionElement" %>
<%@ page import="com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement" %>
<%@ page import="com.gulfstreambio.kc.util.KcUtils" %>
<%@ page import="com.ardais.bigr.kc.form.helpers.FormUtils" %>
<%@ page import="java.util.Map"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
UniqueIdGenerator idGen = new UniqueIdGenerator();
request.setAttribute("idgen", idGen);
%>

<bean:define id="myForm" name="resultsFormDefinitionForm" type="com.ardais.bigr.kc.web.form.def.ResultsFormDefinitionForm"/>
<html>
  <head>
    <title>Manage Views</title>
    <link rel="stylesheet" type="text/css"
	      href='<html:rewrite page="/css/bigr.css"/>'>
 <style type="text/css">
   #available {
     font-family: Tahoma, Arial, Helvetica, sans-serif;
     font-size: x-small;
     border-width: 1px; 
     border-left: ridge; 
     border-top: ridge; 
     border-right: inset; 
     border-bottom: inset; 
     padding: 0;
     width:100%; 
     height:300px; 
     overflow:auto;
   }

   #available ul { 
     list-style-type: none;
     margin: 0;
     padding: 0 0 0 0.1em;
   }

   #available ul ul { 
     padding-left: 1em;
   }

   #available li {
     background-color: #FFFFFF;
     color: #000000;
     padding-top: 0.1em;
   }

   #available li.leaf {
     padding-left: 1em;
   }

   #available li span { 
     padding: 0 0.2em 0 0.2em;
   }

   #available li span.selected { 
     background-color: #151B54;
     color: #FFFFFF;
   }
 </style>	      
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/prototype.js"/>"></script>
<script type="text/javascript">

var isWarnOnNavigate = true;
var isChangesMade = false;

function showWaitMessage(isVisible) {
  if (isVisible) {
	  document.all.waitMessage.style.display = 'inline';
	  document.all.wholePage.style.display = 'none';
  }
  else {
	  document.all.waitMessage.style.display = 'none';
	  document.all.wholePage.style.display = 'inline';
  }
}

function initPage() {
  isChangesMade = false;
  eViewList = document.all['formDefinition.formDefinitionId'];
	eAvailable = document.all.available;
	eSelected = document.all.selected;
	eAdd = document.all.btnAdd;
	eRemove = document.all.btnRemove;
	eRemoveAll = document.all.btnRemoveAll;
	eUp = document.all.up;
	eDown = document.all.down;
	eSave = document.all.btnSave;
	eSaveAs = document.all.btnSaveAs;
	eDelete = document.all.btnDelete;
	eOk = document.all.btnOk;
	eCancel = document.all.btnCancel;
	eForm = document.forms[0];
	eTTDiv = document.all.tooltip;
	eBlankOption = new Option("","",false,false);
	setActionButtonEnabling(true);
}

// ----    add/remove ---------

function onAddClick() {
  setActionButtonEnabling(false);
  <%
  	//note - for some reason, to actually disable the buttons I had to put the processing
  	//into another function and call that via setTimeout.
  %>
  setTimeout("onAddClickHelper()", 10);
}

function onAddClickHelper() {
  addIdx(chosenAvailableId);
	resetSelectionTooltip();  // selection may be in other list
	resetAvailableTooltip();  // selection may be in other list
  <%
	  //note - for some reason, to prevent clicks on disabled buttons from being honored I had to
	  //re-enable the buttons via setTimeout.  Otherwise, even though the button appeared to be
	  //disabled, clicks on it were honored.
  %>
  setTimeout("setActionButtonEnabling(true);", 10);
}

function addIdx(availableId) {
  var e = $(availableId);
	if (e) {
		isChangesMade = true;
		
		// The available id refers to the SPAN that contains the text being 
		// displayed, so find the SPAN's parent LI.  If the parent LI has a
		// 'leaf' class name then we know its a leaf, so move it to the selected
		// list if it is visible.  If it is not visible, then it is already moved.
		var parentLi = $(e.parentNode);
		if (parentLi.classNames().member('leaf') && parentLi.visible()) {

		  // Hide the LI in the available list.
		  parentLi.hide();

		  // Traverse the ancestors until we find the LI that has an 'etype'
		  // attribute.
		  var as = parentLi.ancestors();
			var n = as.length;
			var formLi = null;
			if (n > 0) {
			  for (var i = 0; i < n; i++) {
			    var a = as[i];
			    if ((a.tagName.toUpperCase() == 'LI') && (a.etype)) {
			      formLi = a;
			    }
			  }
		  }		  
		  
		  // Create the new option from the selected available item and add it
		  // to the selected list.
			var newOption = document.createElement('option');
			var text = e.innerText;
			if (formLi) {
				newOption.etype = formLi.etype;
				if (formLi.otype) {
				  text = text + " (" + formLi.otype + ")";
				}
			}
			newOption.text = text;
			newOption.tooltip = e.tooltip;
			newOption.value = e.id;
			newOption.id = "S" + e.id;
			eSelected[eSelected.length] = newOption;
		}

		// Otherwise the item selected in the available list is not a leaf, so
		// recursively call this on all of its leaf LIs.  In the recursive call, 
		// the id to use is on the leaf LI's first (and only) SPAN child.
		else {
			var children = parentLi.getElementsBySelector('li.leaf');
			var n = children.length;
			if (n > 0) {
			  for (var i = 0; i < n; i++) {
			    addIdx(children[i].down().id);
			  }
		  }		  
		}
	}
}

function onRemoveAllClick() {
  selectAllSel();
  onRemoveClick();
}

function onRemoveClick() {
  setActionButtonEnabling(false);
  <%
	  //note - for some reason, to actually disable the buttons I had to put the processing
	  //into another function and call that via setTimeout.
  %>
  setTimeout("onRemoveClickHelper()", 10);
}

function onRemoveClickHelper() {
	var len = eSelected.length;
	for (var i=0; i<len; i++) {
		if (eSelected[i].selected) {
			removeIdx(i); 
			i--; // decr i because next elem shifted down to current
			len--; // decr len because list is now smaller
		}
	}
	resetSelectionTooltip();  // selection may be in other list	
	resetAvailableTooltip();  // selection may be in other list
  <%
    //note - for some reason, to prevent clicks on disabled buttons from being honored I had to
    //re-enable the buttons via setTimeout.  Otherwise, even though the button appeared to be
    //disabled, clicks on it were honored.
  %>
  setTimeout("setActionButtonEnabling(true);", 10);
  
}

function removeIdx(i) {
	isChangesMade = true;
	var dataElementId = eSelected[i].value;
	eSelected.remove(i);
	//unhide the row in the available control
	var span = $(dataElementId);
	if (span) {
		$(span.parentNode).show();	  
	}
}

function adjustSelectedScrollPosition(direction){
  
  var currentTop = eSelected.scrollTop;
  var scrollMin = eSelected.scrollHeight - eSelected.scrollTop;
  
  if (direction == "up" ){
  var selHeight = eSelected.height;
  eSelected.scrollTop = currentTop - 16;
  }
  if (direction == "down"){
    var selHeight = eSelected.height;
    eSelected.scrollTop = currentTop + 16;
    }
}

// -------   move up /  move down ------

function onUpClick() {
	var len = eSelected.length;
	for (var i=0; i<len; i++) {
		if (eSelected[i].selected) {
			if (i==0) { // first selection is at top already?  quit.
				return;
			}
			isChangesMade = true;
			swap(eSelected, i, i-1);
		}
	}
	adjustSelectedScrollPosition("up");
}

function onDownClick() {
	var len = eSelected.length;
	for (var i=len-1; i>=0; i--) { // reverse loop, last selection first. 
		if (eSelected[i].selected) {
			if (i==len-1) { // last selection at bottom?  quit.
				return;
			}
			isChangesMade = true;
			swap(eSelected, i+1, i);
		}
	}
	adjustSelectedScrollPosition("down");
}

function swap(selctl, i ,j) {
	var tempI = selctl[i];
	var tempJ = selctl[j];
	selctl[i] = eBlankOption; // prevent hash overwrite with duplicate
	selctl[j] = tempI;
	selctl[i] = tempJ;
}

// --  last clicked support for selected side ---
function onChangeSelected() {
	updateLastSelection();
	if (lastSelection != -1) {
		displayTooltip(eSelected[lastSelection].id);
	}
}

var oldSelections = new Array();
var lastSelection = -1;

function resetSelectionTooltip() {
	oldSelections = new Array();
	lastSelection = -1;
	eTTDiv.innerHTML = '';
}

function updateLastSelection() {
	// do a diff between old and new selections
	var len = eSelected.length;
	for (var i=0; i<len; i++) {
		if (eSelected[i].selected) {
			if( !oldSelections[i]) {
				lastSelection=i;
			}
		}
	}

	// now set oldSelections to current
	for (var i=0; i<len; i++) {
		if (eSelected[i].selected)
			oldSelections[i]=true;
		else
			oldSelections[i]=false;
	}
}

//--  last clicked support for available side ---
var chosenAvailableId = null;

function selectAvailable() {
  var e = $(Event.element(event));
  var tn = e.tagName.toUpperCase();

  // If the user selected the text of a node, which is enclosed in a SPAN
  // with no descendant elements, then highlight the selected text.
  if ((tn == 'SPAN') && (e.descendants().length == 0)) {

    // If there is a previously chosen node that is not the current node, 
    // unselect it.
    if (chosenAvailableId && chosenAvailableId != e.id) {
      var old = $(chosenAvailableId);
      old.removeClassName('selected');
    }    

    // Select the chosen node.  The id is on the parent LI of the SPAN.
    chosenAvailableId = e.id;
    e.addClassName('selected');

    displayTooltip(chosenAvailableId);
	}
}

function resetAvailableTooltip() {
	eTTDiv.innerHTML = '';
}
	
// ---   other javascript (submit...) ----

function isDataValid() {
  if (eSelected.length > 0) {
    return true;
  }
  else {
  	alert("No data elements are selected - you must select at least one.");
    return false;
  }
}

function onSaveClick() {
  setActionButtonEnabling(false);
  if (!isDataValid()) {
  	setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    if (onSubmit()) {
      //set the name to be the name of the selected form.  This is to handle a situation where
      //the user clicked "Save As" and then had a problem with the save so was returned here, and
      //is now clicking "Save".  The hidden name field will contain the value they typed in during
      //the "Save As", which isn't correct
      document.all['formDefinition.name'].value = eViewList.options[eViewList.selectedIndex].text;
      <%
    	  //in case there were any issues saving the form, pass along the id of the selected form
    	  //so it will be chosen when we return.  Since we disable the id drop down the id has to
    	  //be appended to the action manually
      %>
      var formId = document.all['formDefinition.formDefinitionId'].value;
  	  eForm.action = eForm.action + '?formDefinition.formDefinitionId=' + formId;
      eForm.submit();
    }
  }
}

function onSaveAsClick() {
  setActionButtonEnabling(false);
  if (!isDataValid()) {
  	setActionButtonEnabling(true);
  }
  else {
    <%
      String url = "/kc/resultsformdef/saveAsStart.do?formDefinition.formDefinitionId=" + myForm.getFormDefinition().getFormDefinitionId();
    %>
	  var formName = window.showModalDialog("<html:rewrite page='<%=url%>'/>",null,'status:yes;resizable:yes;help:no;dialogWidth:500px;dialogHeight:500px');
    if (formName == null) {
      setActionButtonEnabling(true);
      return;
    }
    else {
      isWarnOnNavigate = false;
      document.all['formDefinition.name'].value = formName;
      //Save as always means a non-anonymous form
      document.all['formDefinition.anonymous'].value = 'false';
      if (onSubmit()) {
        <%
        	//in case there were any issues saving the form, pass along the id of the currently
        	//selected form in the dropdown so it will be chosen when we return.  Since we disable 
        	//the id drop down the id has to be appended to the action manually
        %>
        var formId = document.all['formDefinition.formDefinitionId'].value;
      	eForm.action = eForm.action + '?formDefinition.formDefinitionId=' + formId;;
        eForm.submit();
      }
    }
  }
}

function onDeleteClick() {
  isWarnOnNavigate = false;
  setActionButtonEnabling(false);
  var formId = document.all['formDefinition.formDefinitionId'].value;
  //update the form name - not necessary, but makes for less confusion if debugging (name
  //matches id)
  document.all['formDefinition.name'].value = eViewList.options[eViewList.selectedIndex].text;
  //since we disable the id drop down, append the value to the action manually
	var action = '<html:rewrite page="/kc/resultsformdef/delete.do"/>'
  action = action + '?formDefinition.formDefinitionId=' + formId;
	eForm.action = action;
  eForm.submit();
}

function onOkClick() {
  setActionButtonEnabling(false);
  if (!isDataValid()) {
  	setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    <%
    	//we need to handle the case where users have made changes on this form as well as the
    	//situation where a user has returned from an action that failed for some reason.  In 
    	//the second situation the form will have set a flag to indicate unsaved changes exist,
    	//so include that flag in our check
    %>
    if (isChangesMade || <%=myForm.isUnsavedChangesExist() %>) {
      //save the form as anonymous
      document.all['formDefinition.anonymous'].value = 'true';
      if (onSubmit()) {
        //indicate that the page should be closed
        document.all['closePage'].value = 'true';
        <%
        	//in case there were any issues saving the form, pass along the id of the currently
        	//selected form in the dropdown so it will be chosen when we return.  Since we disable 
        	//the id drop down the id has to be appended to the action manually
        %>
        var formId = document.all['formDefinition.formDefinitionId'].value;
      	eForm.action = eForm.action + '?formDefinition.formDefinitionId=' + formId;
        eForm.submit();
      }
    }
    else {
      returnFormId(false);
    }
  }
}

function returnFormId(useNull) {
	if (window.opener && !window.opener.closed && window.opener.reloadTable) {
	  var formId = null;
		if (!useNull) {
	    formId = document.all['formDefinition.formDefinitionId'].value;
		}
		window.opener.reloadTable(formId);
	}
  window.close();
}

function onCancelClick() {
  setActionButtonEnabling(false);
  var cancelAction = confirmNavigate() ? false : true;
  if (cancelAction) {
    setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    returnFormId(true);
  }
}

function onViewSelectionChange() {
  setActionButtonEnabling(false);
  var cancelAction = confirmNavigate() ? false : true;
  if (cancelAction) {
    //restore the dropdown to have the original form selected
    for (var i=0; i<eViewList.length; i++ ) {
      if (eViewList.options[i].value == '<%=myForm.getFormDefinition().getFormDefinitionId()%>') {
        eViewList.options[i].selected=true;
      }
    }
    setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    //update the form name - not necessary, but makes for less confusion if debugging (name
    //matches id)
    document.all['formDefinition.name'].value = eViewList.options[eViewList.selectedIndex].text;
    //since we disable the id drop down, append the value to the action manually
    var formId = document.all['formDefinition.formDefinitionId'].value;
    var action = '<html:rewrite page="/kc/resultsformdef/start.do"/>';
    action = action + '?formDefinition.formDefinitionId=' + formId;
  	eForm.action = action;
  	eForm.submit();
  }
}

function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  eViewList.disabled = isDisabled;
	eAdd.disabled = isDisabled;
	eRemove.disabled = isDisabled;
	eRemoveAll.disabled = isDisabled;
	eUp.disabled = isDisabled;
	eDown.disabled = isDisabled;
	eSave.disabled = isDisabled;
	eSaveAs.disabled = isDisabled;
	eDelete.disabled = isDisabled;
	eOk.disabled = isDisabled;
	eCancel.disabled = isDisabled;
	var formName = eViewList.options[eViewList.selectedIndex].text;
	if (formName.length > 30) {
	  formName = formName.substr(0,30) + "...";
	}
	eDelete.value = "Delete " + formName;
	<%
	  //if the system default view is selected then disable the save and delete buttons
	%>
	if (<%=Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(myForm.getFormDefinition().getFormDefinitionId())%>) {
    eSave.disabled = true;
    eDelete.disabled = true;
  }
}

function onSubmit() {
	setActionButtonEnabling(false);
	if (!isDataValid()) {
		setActionButtonEnabling(true);
		return false;
	}
	var idString = "";
	var elementAdded = false;
	for (var i=0; i<eSelected.length; i++) {
	  var e = eSelected.options[i];
	  if (elementAdded) {
	    idString = idString + "<%=ResultsFormDefinitionForm.COMMA%>";
	  }
    idString = idString + e.etype + "<%=ResultsFormDefinitionForm.SEPERATOR%>" + e.value;
	  elementAdded = true;
	}
	document.all['selectedElementIds'].value = idString;
	showWaitMessage(true);
	return true;
}

function selectAllSel() {
	var len = eSelected.length;
	for (var i=0; i<len; i++) {
		eSelected[i].selected = true;
	}
}

function confirmNavigate() {
  if (needNavagationWarning()) {
    return confirm("You have made changes that have not been saved.  Are you sure you want to continue?\n\nPress OK to continue, or Cancel to stay on the current page.");
  }
  else {
  	return true;
  }
}

function needNavagationWarning() {
  if (! isWarnOnNavigate) return false;
  return isChangesMade;
}

function showChildren() {
  var img, e = $(Event.element(event));
  var t = event.type;
  var tn = e.tagName.toUpperCase();

	// If the user double-clicked on a SPAN with no descendants, then it may
	// be the text of a parent node in the available list.  If it is, it will
	// have a sibling span with the image to toggle, so try to find it.
  if ((tn == 'SPAN') && (e.descendants().length == 0) && (t == 'dblclick')) {
    var p = e.previous();
    if (p) {
      var d = p.immediateDescendants();
      if (d.length > 0) {
        e = d[0];
        tn = e.tagName.toUpperCase();
      }
    }
  }

  // If the user clicked the collapse/expand image, which is in a SPAN in an 
  // LI, and the LI has a UL child, then toggle the visibility of the child UL.
  if (tn == 'IMG') {
    img = e;
    var e = $(img.parentNode);
    tn = e.tagName.toUpperCase();
    if (tn == 'SPAN') {
      e = $(e.parentNode);
      tn = e.tagName.toUpperCase();
      if (tn == 'LI') {
        var uls = e.getElementsBySelector('ul');        
        if (uls.length > 0) {
          e = uls[0];
          e.toggle();
          if (e.visible()) {
            img.src = '<html:rewrite page="/images/MenuOpened.gif"/>';
          }
          else {
            img.src = '<html:rewrite page="/images/MenuClosed.gif"/>';
          }
        }
      }
    }
  }
}

function displayTooltip(id) {
  var e = $(id);
	var inhtml = e.innerHTML;
	if (e.tooltip) {
	  inhtml = inhtml + ' -- ' + e.tooltip;
	}
	eTTDiv.innerHTML = inhtml
}

</script>
</head>
<center><h4>Manage Views</h4></center>
<p>
<%
//put onSelectStart="return false;" in the body to prevent users from trying to
//select multiple nodes from the available list.  Note that this doesn't prevent
//users from selecting multiple nodes from the selected list, since that action
//is a different event.
%>
<body class="bigr" onLoad="initPage();" onSelectStart="return false;">
<html:form method="POST" action="/kc/resultsformdef/createOrUpdate">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <font color="#FF0000"><b><html:errors/></b></font>
	    </td>
	  </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	  <tr class="white"> 
	    <td> 
	      <div align="left">
	        <font color="#000000"><b><bigr:btxActionMessages/></b></font>
	      </div>
	    </td>
	  </tr>
	  </logic:present>
	</table>
  </div>
  <p>
	<% //div for "please wait" message %>
  <div id="waitMessage" style="display:none;"> 
    <table align="center" border="0" cellspacing="0" cellpadding="0" class="background" width="300">
      <tr> 
        <td> 
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr align="center" class="yellow"> 
              <td><img id="waitMessageImage" 
                       src="<html:rewrite page='/images/pleasewait.gif'/>"
                       alt="Please Wait"></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <% //div for whole page %>
	<div id="wholePage">
	<div align="center">
View:
<bigr:selectList
  name="resultsFormDefinitionForm"
  property="formDefinition.formDefinitionId"
  legalValueSet="<%=FormUtils.getFormsAsLVS(myForm.getResultsFormDefinitions())%>"
  onchange="onViewSelectionChange();"/>
<html:hidden name="resultsFormDefinitionForm" property="formDefinition.name"/>
<html:hidden name="resultsFormDefinitionForm" property="formDefinition.userName"/>
<html:hidden name="resultsFormDefinitionForm" property="formDefinition.account"/>
<html:hidden name="resultsFormDefinitionForm" property="formDefinition.enabled"/>
<html:hidden name="resultsFormDefinitionForm" property="formDefinition.anonymous"/>
<html:hidden name="resultsFormDefinitionForm" property="selectedElementIds"/>
<html:hidden name="resultsFormDefinitionForm" property="closePage"/>
<p>
<table width="800px" cellpadding="2" cellspacing="0" border="0">
<tr>
  <td>Available data elements:</td><td></td><td>Selected data elements:</td>
</tr>
<tr>
	<td width="38%" valign="top">
    <div id="available" onclick="showChildren();selectAvailable();" ondblclick="showChildren();">
      <bigr:dataFormDefinitionsAsTable
        tableId="available"
        bigrColumns="<%= myForm.getBigrColumns() %>"
        dataFormDefinitions="<%= myForm.getDataFormDefinitions() %>"
        resultsFormDefinition="<%=myForm.getKcFormDefinition()%>"/>
    </div>
	</td>
	
	<td width="24%" valign="middle" align="center">
	<div>
		<input id="btnAdd" class="buttons" type="button" style="width:110" onclick="onAddClick();" value="Add &gt;&gt;">
		<p/>
		<input id="btnRemove" class="buttons" type="button" style="width:110" onclick="onRemoveClick();" value="&lt;&lt; Remove">
		<p/>
		<input id="btnRemoveAll" class="buttons" type="button" style="width:110" onclick="onRemoveAllClick();" value="&lt;&lt; Remove All">
	</div>
	</td>
	
	<td width="38%" valign="top">
	<select multiple name="selected" style="width:100%;height:300px;font-size:x-small" onchange="onChangeSelected();">
	<% 
  	  //process the elements from the selected form
	    Map bigrColumns = myForm.getBigrColumnsAsMap();
      Map dataForms = myForm.getDataFormDefinitionsAsMap();
	    ResultsFormDefinitionElement[] elements = myForm.getKcFormDefinition().getResultsFormElements().getResultsFormElements();
		  for (int i = 0; i < elements.length; i++) {
		    ResultsFormDefinitionElement element = elements[i];
		    if (element.isHostElement()) {
		      ResultsFormDefinitionHostElement hostElement = element.getResultsHostElement();
				  String hostId = hostElement.getHostId();
				  String dataElementId = Constants.BIGR + ResultsFormDefinitionForm.SEPERATOR + hostId;
				  SampleColumn column = (SampleColumn)bigrColumns.get(hostId);
		%>
<option id="<%=idGen.getUniqueId()%>" 
etype="<%=ResultsFormDefinitionForm.ELEMENT_TYPE_HOST%>" 
tooltip="<%=Escaper.htmlEscape(column.getHeaderTooltip())%>"
value="<%=dataElementId%>"><%=Escaper.htmlEscape(column.getRawHeaderText())%></option>
		<% 
		    }
		    else {
			    ResultsFormDefinitionDataElement dataElement = element.getResultsDataElement();
				  String cui = dataElement.getCui();
				  String formId = null;
				  Tag[] tags = dataElement.getTags();
				  for (int j = 0; j < tags.length; j++) {
				    //get the form id
				    Tag tag = tags[j];
				    if (TagTypes.PARENT.equalsIgnoreCase(tag.getType())) {
				      formId = tag.getValue();
				    }
				  }
				  //get the data form from which the data element came, and use the values
				  //from the form
				  BigrFormDefinition dataFormDefinition = (BigrFormDefinition)dataForms.get(formId);
				  DataFormDefinitionDataElement sourceDataElement = ((DataFormDefinition)dataFormDefinition.getKcFormDefinition()).getDataDataElement(cui);
	        String displayName = KcUtils.getDescription(sourceDataElement);
				  String dataElementId = formId + ResultsFormDefinitionForm.SEPERATOR + cui;
				  String objectType = dataFormDefinition.getObjectType();
%>
<option id="<%=idGen.getUniqueId()%>" 
etype="<%=ResultsFormDefinitionForm.ELEMENT_TYPE_DATA%>" 
value="<%= dataElementId %>"><%=Escaper.htmlEscape(displayName)%> (<%= objectType %>)</option>
<%		      
		    }
		  }
%>
	</select>
	</td>
</tr>
<tr>
	<td/><td/>
	<td>
		Display Order: 
		<img id="up" width="22" src="<html:rewrite page="/images/uparrow.gif"/>" onclick="onUpClick();" value="Up">
		&nbsp;&nbsp;&nbsp;
		<img id="down" width="22" src="<html:rewrite page="/images/dnarrow.gif"/>" onclick="onDownClick();" value="Down">
	</td>
<tr>
<tr>
	<td valign="bottom">Data element description <small>(last clicked)</small> </td>
	<td/>
	<td/>
</tr>
<tr><td colspan="3"> 
		<small>
			<div style="position: relative; overflow: auto; width:100%; height: 70; border: 2px solid #336699;" id="tooltip"> </div>
		</small>
	</td>
</tr>
<tr>
	<% if (WebUtils.getSecurityInfo(request).isHasPrivilege(SecurityInfo.PRIV_ORM_SHARE_VIEW)) { %>
	    <td colspan="3">
			<jsp:include page="sharedViewList.jsp" />
		</td>
	<% } %>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
</table>

<table width="800px" cellpadding="2" cellspacing="0" border="0">
<tr>
	<td align="left"> 	    
		<input type="button"  style="width:110" id="btnSave" onclick="onSaveClick()" value="Save"/> &nbsp;
		<input type="button"  style="width:110" id="btnSaveAs" onclick="onSaveAsClick();" value="Save As..."/>
	</td>
	<td/>
	<td align="right"> 	    
		<input type="button" id="btnDelete" onclick="onDeleteClick();" value="Delete"/>
	</td>
</tr>
<tr>
  <td>&nbsp;</td>
</tr>
<tr>
	<td align="left">
		<input type="button"  style="width:110" id="btnOk" onclick="onOkClick();" value="Ok"/> &nbsp;
		<input type="button"  style="width:110" id="btnCancel" onclick="onCancelClick();" value="Cancel"/>
	</td>
</tr>

</table>
</div>
</div>
</html:form>
<% 
  if (myForm.isClosePage()) {
%>
<script type="text/javascript">
  returnFormId(false);
</script>
<%
  }
%>

</body>
</html>