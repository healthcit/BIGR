<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiException"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.iltds.helpers.IltdsUtils" %>
<%@ page import="com.ardais.bigr.javabeans.DerivationDto" %>
<%@ page import="com.ardais.bigr.javabeans.SampleData" %>
<%@ page import="com.ardais.bigr.library.SampleViewData"%>
<%@ page import="com.ardais.bigr.library.web.helper.ResultsHelper"%>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %>
<%@ page import="com.ardais.bigr.util.ArtsConstants" %>
<%@ page import="com.ardais.bigr.util.IcpUtils" %> 
<%@ page import="com.ardais.bigr.util.WebUtils"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define name="derivationBatchForm" id="batchForm" type="com.ardais.bigr.iltds.web.form.DerivationBatchForm"/>
<bean:define name="derivationBatchForm" property="dto" id="batchDto" type="com.ardais.bigr.javabeans.DerivationBatchDto"/>
<html>
<head>
<title>Operation Samples</title>
<%
	String CHECK_BOX_NAME = "selectedSampleIds";
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/calendar.js"/>"></script>
<script language="JavaScript" src='<html:rewrite page="/js/ssresults.js"/>'></script>
<script type="text/javascript" src='<html:rewrite page="/js/overlib.js"/>'><!-- overLIB (c) Erik Bosrup --></script>
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
<script language="JavaScript">

function initPage() {
  if (parent.topFrame) {
    //parent.topFrame.document.all.banner.innerHTML = "Derivative Operations";
    parent.topFrame.document.getElementById("banner").innerHTML = "Derivative Operations";
  }
  _isPageReadyForInteraction = true;
}
  
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnStartOver.disabled = isDisabled;
  f.btnPrint.disabled = isDisabled;
  if (isControlCollection('btnSelectAll')) {
    disableControlCollection('btnSelectAll', isDisabled);
  }
  else {
    f.btnSelectAll.disabled = isDisabled;
  }
  if (isControlCollection('btnUnselectAll')) {
    disableControlCollection('btnUnselectAll', isDisabled);
  }
  else {
    f.btnUnselectAll.disabled = isDisabled;
  }
}

function validate() {
  setActionButtonEnabling(false);
  return true;
}

function confirmNavigation() {
  if (_selectedCounts.getTotal() > 0) {
    var msg = 'You have selected samples but have not begun a new derivation.   You will need to reselect the samples when the page is refreshed.  Do you want to continue?';
    msg = msg + '\n\nPress OK to continue, or Cancel to stay on the current page.';
    if (!confirm(msg)) {
      return false;
    }
  }
  return true;
}

function reloadTable(resultsFormDefinitionId) {
  this.focus();
  if (resultsFormDefinitionId != null && confirmNavigation()) {
		document.all.wholePage.style.display = 'none';
		document.all.waitMessage.style.display = 'block';
		document.forms[0].resultsFormDefinitionId.value = resultsFormDefinitionId; 
		document.forms[0].action = '<html:rewrite page="/iltds/derivation/derivationBatchSummary"/>' + '.do';
	  document.forms[0].submit();
  }
}

var derivationToChildSamples = new Object();
var sampleList;

<%
Iterator derivationIterator = batchDto.getDerivations().iterator();
while (derivationIterator.hasNext()) {
  DerivationDto derivation = (DerivationDto)derivationIterator.next();
  String derivationId = derivation.getDerivationId();
%>
sampleList = new Object();
derivationToChildSamples.<%=derivationId%> = sampleList;
<%
  Iterator childIterator = derivation.getChildren().iterator();
  while (childIterator.hasNext()) {
    SampleData childSampleData = (SampleData) childIterator.next();
    String childSampleId = childSampleData.getSampleId();
    if (ApiFunctions.isEmpty(childSampleId)) {
      childSampleId = childSampleData.getGeneratedSampleId();
    }
%>
sampleList.<%=childSampleId%> = '<%=childSampleId%>';
<%
  }
}
%>

function selectSamplesInDerivation(derivationId, isSelected) {
  var sampleList = derivationToChildSamples[derivationId];
  if (sampleList != null) {
    if (isControlCollection('<%=CHECK_BOX_NAME%>')) {
      var control = document.all['<%=CHECK_BOX_NAME%>'];
      for (var i=0; i<control.length; i++) {
        var sampleId = sampleList[control[i].value];
        if (sampleId != null) {
          //if the checkbox should be selected but is not, fire it's onclick event
          if (isSelected && !control[i].checked) {
            control[i].click();
          }
          //if the checkbox should not be selected but is, fire it's onclick event
          if (!isSelected && control[i].checked) {
            control[i].click();
          }
        }
      }
    }
    else {
      var sampleId = sampleList[document.all['<%=CHECK_BOX_NAME%>'].value];
      if (sampleId != null) {
        //if the checkbox should be selected but is not, fire it's onclick event
        if (isSelected && !document.all['<%=CHECK_BOX_NAME%>'].checked) {
          document.all['<%=CHECK_BOX_NAME%>'].click();
        }
        //if the checkbox should not be selected but is, fire it's onclick event
        if (!isSelected && document.all['<%=CHECK_BOX_NAME%>'].checked) {
          document.all['<%=CHECK_BOX_NAME%>'].click();
        }
      }
    }
  }
}

<%
	if (!ApiFunctions.isEmpty(batchForm.getLabelPrintingData())) {
%>
function onClickPrintLabels() {
  setActionButtonEnabling(false);
  var theURL = '<html:rewrite page="/iltds/derivation/derivationBatchPrintLabelsStart.do?removeEmptyDerivations=false"/>';
  <%
  	StringBuffer urlAdditions = new StringBuffer(128);
  	Iterator dtoIterator = batchDto.getDerivations().iterator();
  	int count = 0;
  	while (dtoIterator.hasNext()) {
  	  DerivationDto derivation = (DerivationDto)dtoIterator.next();
  	  urlAdditions.append("&");
  	  urlAdditions.append("dto.derivation[");
  	  urlAdditions.append(count);
  	  urlAdditions.append("].derivationId=");
  	  urlAdditions.append(derivation.getDerivationId());
  	  count = count + 1;
  	}
  %>
  theURL = theURL + '<%=urlAdditions.toString()%>';
  var w = window.open(theURL, 'PrintDerivationLabels',
    'scrollbars=yes,resizable=yes,status=yes,width=1000,height=700,top=0');
  w.focus();
  
  setActionButtonEnabling(true);
}
<%
	}
%>

</script>
</head>

<body class="bigr" onLoad="initPage();">

<html:form action="/iltds/derivation/derivationBatchStart.do"  onsubmit="return(validate());">
<html:hidden name="derivationBatchForm" property="resultsFormDefinitionId"/>
<%
  String resultsFormDefinitionId = batchForm.getResultsFormDefinitionId();
  List resultsFormDefinitions = batchForm.getResultsFormDefinitions();
  String txType = ResultsHelper.TX_TYPE_DERIV_OPS;
  ResultsHelper helper = ResultsHelper.get(txType, request);
	if (helper == null) {
		throw new ApiException("Request attribute " + ResultsHelper.KEY + txType
		  + " not defined in derivationResults.jsp");
	}

	//---------------------------  synch entire request on the ResultsHelper
	synchronized (helper) {
	//---------------------------  synch entire request on the ResultsHelper
		
		// Get data to output in the actual table (row numbers and sample data).
		SampleViewData viewData = helper.getHelpers();
%>
	<% //div for "please wait" message %>
  <div id="waitMessage" style="display: none"> 
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
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	      <td><font color="#FF0000"><b><html:errors/></b></font></td>
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
  <div style="margin-bottom: 3px;">
    <bigr:selectViewLinkMenu
      currentResultsFormDefinitionId="<%=resultsFormDefinitionId%>"
      resultsFormDefinitions="<%=resultsFormDefinitions%>"
      menuId="selectViewMenu"
    />
    <bigr:selectViewLink
      currentResultsFormDefinitionId="<%=resultsFormDefinitionId%>"
      resultsFormDefinitions="<%=resultsFormDefinitions%>"
      menuId="selectViewMenu"
    />
  </div>
  
	<table border="0" class="foreground" cellpadding="3" cellspacing="1">
    <tr class="white">
      <td>
	      The following derivation 
				<%=(batchDto.getDerivations().size() > 1) ? "operations were" : "operation was"%>
	      successfully recorded.
			</td>
    </tr>
    <tr class="white"><td colspan="2">&nbsp;</td></tr>
    <tr class="white">
      <td>
        Operation: <bean:write name="derivationBatchForm" property="operation"/>
        &nbsp;&nbsp;-&nbsp;&nbsp;
      	Operation Date: <%=ApiFunctions.isEmpty(batchDto.getOperationDateAsString()) ? "(not specified)" : batchDto.getOperationDateAsString()%>
        &nbsp;&nbsp;-&nbsp;&nbsp;
      	Prepared by: <%=ApiFunctions.isEmpty(batchDto.getPreparedByName()) ? "(not specified)" : batchDto.getPreparedByName()%>
      </td>
    </tr>
	</table>

 	<hr style="border: 2px solid black;">

  <logic:iterate name="batchDto" property="derivations" indexId="derivationCount" id="derivation" type="com.ardais.bigr.javabeans.DerivationDto">
	<table border="0" class="foreground" cellpadding="3" cellspacing="1">
			<% int derivationCountInt = derivationCount.intValue(); %>
			<%
			    String derivationId = derivation.getDerivationId();
			    viewData.setCategory(derivationId);
					viewData.setIncludeItemSelector(true);
					viewData.setViewElementId(derivationId);
					viewData.setItemSelectorName(CHECK_BOX_NAME);
			%>
			<html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCountInt + "].derivationId"%>'/>
 	   	<tr class="white">
			  <td><%=String.valueOf(derivationCountInt+1)%>.</td>
			  <td>
		    	<%=(derivation.getParents().size() > 1) ? "Parent Barcodes:" : "Parent Barcode:"%>
      		<logic:iterate name="derivation" property="parents" indexId="parentCount" id="parent">
		    		<%=(parentCount.intValue() > 0) ? ", " : ""%>
        		<% 
				      		SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
				        	String sampleId = derivation.getParent(parentCount.intValue()).getSampleId();
				        	String linkText = IltdsUtils.getSampleIdAndAlias(derivation.getParent(parentCount.intValue()));
									String link = IcpUtils.preparePopupLink(sampleId, linkText, securityInfo);
						%>
				    <%= link %>
				    <%
				    	//note - to prevent this deriv op from being removed from the struts form because it 
				    	//is empty we need to include the parent sample ids and/or child sample ids on the 
				    	//page, so the parent ids are included here
				    %>
				    <html:hidden name="derivationBatchForm" property='<%="dto.derivation[" + derivationCount + "].parent[" + parentCount + "].sampleId"%>'/>
					</logic:iterate>
			  </td>
 	  	</tr>
 	  	<tr class="white">
			  <td>&nbsp;</td>
		 		<td>
		 		  Derivatives &nbsp;&nbsp;&nbsp;&nbsp;
		 		  <% 
		 		     String selectOnClick = "selectSamplesInDerivation('" + derivationId + "', true);";
		 		     String unselectOnClick = "selectSamplesInDerivation('" + derivationId + "', false);";
		 		  %>
		 		  <html:button property="btnSelectAll" styleClass="libraryButtons" onclick="<%=selectOnClick%>">
		 		    Select All"
		 		  </html:button>&nbsp;&nbsp;
		 		  <html:button property="btnUnselectAll" styleClass="libraryButtons" onclick="<%=unselectOnClick%>">
		 		    Unselect All"
		 		  </html:button><br>
				</td>
			</tr>
	</table>
					<div style="margin: 5px 0px 12px 15px;">
					  <bigr:inventoryItemView itemViewData="<%= viewData %>" />
					</div>
 	<hr style="border: 2px solid black;">
  </logic:iterate> <% // all derivations in the batch %>

  <table border="0" class="foreground" cellpadding="3" cellspacing="0" width="100%">
    <tr class="white"> 
<%
	if (!ApiFunctions.isEmpty(batchForm.getLabelPrintingData())) {
%>
      <td align="left">
 			  <input type="button" name="btnPrintLabels" value="Print Labels..." onclick="onClickPrintLabels();"/>
      </td>
      <td align="center">
 			  <input type="submit" name="btnStartOver" value="Start New Derivation"/>
      </td>
      <td align="right">
     	  <input type="button" name="btnPrint" value="Print Page" onclick="window.print();"/> 
		  </td>
<%
	}
	else {
%>
      <td align="left">
 			  <input type="submit" name="btnStartOver" value="Start New Derivation"/>
      </td>
      <td align="right">
     	  <input type="button" name="btnPrint" value="Print Page" onclick="window.print();"/> 
		  </td>
<%
	}
%>
    </tr>
	</table>
</div>
<%
	}	// END RESULTSHELPER SYNCHRONIZED BLOCK
%>
</html:form>
</body>
</html>
