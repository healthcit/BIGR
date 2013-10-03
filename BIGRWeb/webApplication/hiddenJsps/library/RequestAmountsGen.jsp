<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ardais.bigr.library.javabeans.*"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%@ page import="com.ardais.bigr.util.WebUtils"%>
<%@ page import="com.ardais.bigr.library.web.helper.*"%>
<%@ page import="com.ardais.bigr.query.ColumnPermissions"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>


<%	// -- Standard block for all query pages to find the proper txType
	// -- also MUST put a hidden txType field in the form
	String txType = null;
	com.ardais.bigr.library.web.form.QueryForm qform = 
		(com.ardais.bigr.library.web.form.QueryForm) request.getAttribute("queryForm");
	if (qform != null) {
		//System.out.println("found query form.  type=" + qform.getTxType());
		txType = qform.getTxType();
	}
	else {
		com.ardais.bigr.library.web.form.ResultsForm rform = 
			(com.ardais.bigr.library.web.form.ResultsForm) request.getAttribute("resultsForm");
		if (rform != null) {
			//System.out.println("found results form.  type=" + rform.getTxType());
			txType = rform.getTxType();
		}
		else {
			txType = request.getParameter("txType");
		}
	}
%>


<html:html>
<head>
<title>Request RNA Amounts</title>
<link rel="stylesheet" type="text/css"
	href='<html:rewrite page="/css/bigr.css"/>'>

<script type="text/javascript"
	src='<html:rewrite page="/js/common.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/ssresults.js"/>'></script>

<script type="text/javascript"
	src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>
<%
	//start files needed for display of Aperio images (SWP-1061)
%>
<jsp:include page="/hiddenJsps/kc/misc/coreScript.jsp" flush="true"/>
<script type="text/javascript" src='<html:rewrite page="/js/integration/bigrAperio.js"/>'></script>
<script type="text/javaScript" src="<html:rewrite page="/js/lightbox/gsbioLightbox.js"/>"></script>
<script type="text/javaScript" src="<html:rewrite page="/hiddenJsps/kc/detViewer/js/scriptaculous.js?load=builder"/>"></script>
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/js/lightbox/gsbioLightbox.css"/>'>
<%
	//end files needed for display of Aperio images (SWP-1061)
%>

<script type="text/javascript">

var myBanner = 'Specify Amounts for RNA Hold Request';

<%-- For just this page, override default tooltip settings so that the tips
     always appear above the labels.  Otherwise the tooltips can appear behind the
     drop-downs in the result table (MR 6087). --%>
  var ol_vauto = 0;
  var ol_vpos = 35; //35 = ABOVE


function setCustom() {
	var amtTxt = document.all.customAmt.value;
	var amt = trim(amtTxt);
	if (amt.length==0)
		return;
	amount = parseInt(amt);
	if (amount>100) {
		alert("The maximum amount is 100ug.");
		return;
	}
	if (amount < 1 || parseFloat(amt)!=amount) {
		alert("Please specify a positive non-fractional amount in micrograms.");
		return;
	}
	setAllAmounts(amount, true);
}

function setAllAmounts(amt) {
  setAllAmounts(parseInt(amt), false);
}

function setAllAmounts(amt, isCustom) {
	amts = document.all.amount; // select controls named 'amount'
	// check for the max attribute -- if it is there, we have a single Select control
	// and need to make it an array (note max is our attribute, not standard )
	if (amts.max) {
	   arr = new Array(1);
	   arr[0]=amts;
	   amts=arr;
	}
	for (var i=0; i<amts.length; i++) {
	  max = parseInt(amts[i].max);
	  if (max<10) // always allow some selection
	  	max = 10;
	  if (!isCustom)
		max = standardAliquot(max);
	  setto = Math.min(amt, max);
	  if (setto != amt) { // could not fill custom request?
	  	setto = standardAliquot(setto); // use best STANDARD amount
	  }
	  setDropdownToValue(amts[i], setto);
	}
}

function standardAliquot(amt) {
	if (amt>=100)
		amt = 100;
	else if (amt >= 50)
		amt = 50;
	else if (amt >= 20)
		amt = 20;
	else
		amt = 10;
	return amt;
}

// set the dropdown to text "val", even if that text is not already an option
function setDropdownToValue(selectcontrol, val) {
	opts = selectcontrol.options;
	var isExistingVal = false;
	for(var i=0; i<opts.length; i++) {
		var optvalstr = opts[i].value;
		optval = parseInt(optvalstr);
		if (optval==val) {
			selectcontrol.selectedIndex = i;
			isExistingVal=true;
		}
	}
	if (!isExistingVal) {
		addAndSelectValueInDropdown(selectcontrol, val);
	}	
}

// called to add a new value to the drop down and select it
// assumes the value is not already there
function addAndSelectValueInDropdown(selectControl, val) {
	var opt = document.createElement("OPTION");
	selectControl.options.add(opt);
	opt.innerText=val.toString();
	opt.value=val.toString();
	selectControl.selectedIndex = selectControl.options.length-1; // sel last one
}

function holdAdd() {
	document.all.wholePage.style.display = 'none';
	var form = document.forms[0];
	form.action = '<html:rewrite page="/library/holdAddWithAmounts"/>.do';
	return true;
}

function cancel() {
	document.all.wholePage.style.display = 'none';
	var form = document.forms[0];
	form.action = '<html:rewrite page="/library/showResults"/>.do';
	form.submit();
	return;
}

function initPage() {
    commonInitPage();
    enableInput();
    _isPageReadyForInteraction = true;
}

function enableInput() {
		if (document.all.btnHoldAdd != null) {
			document.all.btnHoldAdd.disabled=false;
		}
		if (document.all.btnCancel != null) {
			document.all.btnCancel.disabled=false;
		}
		if (document.all.btnSetCustom != null) {
			document.all.btnSetCustom.disabled=false;
		}
		if (document.all.customAmt != null) {
  		document.all.customAmt.disabled = false;
  	}
		// enable radio buttons
		var i = 0;
		var amtButtons = document.all.amt;
		if (amtButtons != null) {
  		for (i = 0; i < amtButtons.length; i++) {
	  	  amtButtons[i].disabled = false;
		  }
		}
}

</script>
</head>

<body class="bigr" onload="initPage();">
<div id="wholePage">
<div class="yellow"> <!-- instructions (static text) -->
The amount of RNA material available for any sample may vary depending on a variety of conditions
such as tissue composition and state of metabolic activity. The selection range provided
is based on standard aliquot sizes supported by your supplier. 
<p/>
To complete your request, please specify the amount of RNA you wish to reserve.
You may specify amounts individually or apply a single amount to all samples.
Once you have completed your selections and/or entries, press "Accept" to complete
the request and place items on hold. 
<p/>
Note: If the specified amount for ALL RNAs is not available for an individual RNA,
the largest available standard amount will be selected.
</div>

	<html:form action="/library/holdAddWithAmounts" style="margin: 0">
		<input type="hidden" name="txType" value="<%=txType%>"/>
		<!-- we are viewing RNA selections (not hold list or tissue) so set productType=rna-->
		<input type="hidden" name="productType" value="<%=ResultsHelper.PRODUCT_TYPE_RNA%>"/>

<!-- input controls for whole form -->
<table class="foreground" style="margin-top: 10px;">
	<col width = "40%" align = "left"/>
	<col width = "20" align="center"/>
	<col width = "50%"align="left"/>
<tr> 
	<td nowrap valign="top"> <!-- fixed amount radios -->
		Select a &quot;Standard&quot; amount for ALL samples 		
		<br/>
		<input type="radio" name="amt" disabled="true" value="10" onclick="setAllAmounts('10')"/> 5-10ug 
		<input type="radio" name="amt" disabled="true" value="20" onclick="setAllAmounts('20')"/> 20ug 
		<input type="radio" name="amt" disabled="true" value="50" onclick="setAllAmounts('50')"/> 50ug 
		<bigr:notIsInRole role1="<%=SecurityInfo.ROLE_DI%>"> 
			<input type="radio" name="amt" disabled="true" value="100" onclick="setAllAmounts('100')"/> 100ug 
		</bigr:notIsInRole>
	</td>
	<td/><!-- blank cell -->
	<td valign="top"> 
		<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_HOLD_RNA_CUST_AMT%>">
			Enter a &quot;Custom&quot; amount for ALL samples 
		 	<br/>
			Custom:
			<input type="text" name="customAmt" disabled="true" size="5"/>  
			<input type="button" name="btnSetCustom" disabled="true"
			  style="FONT-SIZE: xx-small" value="Apply" onClick="setCustom();"/>
			<br/>
			<em>If more RNA is available and you wish to have this amount used as a minimum 
			(e.g. 30ug or more), please include a note to that effect when confirming your 
			request.</em>
		</bigr:hasPrivilege>		
	</td>
</tr>
</table>

<font color="#FF0000"><b><html:errors/></b></font>

<p>
Select or enter a desired amount for each sample

	<div id="Buttons" style="position: absolute; width: 100%; overflow: hidden; z-index: 2; margin: 0; padding-bottom: 0.5em; border: 2px solid #336699; border-top: none;">
		<hr/>
		<em>Note: Your supplier takes great care to ensure that the amount of RNA displayed is available 
			for your selection.  We will make every effort to ensure that your requests are fulfilled.  
			However, if the inventory data displayed with an RNA sample record is inaccurate or a 
			custom amount exceeds the available material, you will be notified and provided with an 
			alternative.</em>
		<br/>
		<center>
		<input type="submit" 
		    name="btnHoldAdd"
    	    disabled="true"
			style="FONT-SIZE: xx-small" 
			value="Accept"
			onClick="return holdAdd();"/>
		<input type="button" 
		    name="btnCancel"
	        disabled="true"
			style="FONT-SIZE: xx-small" 
			value="Cancel"
			onClick="cancel();"/>
		</center>
	</div>
	
	<jsp:include page="/hiddenJsps/library/TissueDetailTable.jsp" flush="true">
	  <jsp:param name="includeItemSelector" value="false"/>
	</jsp:include>
	
	</html:form>
	
</div>
</body>
</html:html>
