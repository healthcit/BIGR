<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.lims.helpers.LimsConstants" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-template" prefix="template" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>

<html>
  <head>
    <title><template:get name='title'/></title>
    <link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <script type="text/javascript" src="<html:rewrite page="/js/overlib.js"/>"> </script>
	<script type="text/javascript" src='<html:rewrite page="/js/common.js"/>'></script>
    <script type="text/javascript">
      var myBanner = '<template:get name='title'/>';
      window.onresize = onResizePage;
      var headerDiv = null;
	  var enterDiv = null;
      
  function openPvReportWindow(peId){
	
	window.open("<html:rewrite page="/lims/limsViewPvReport.do"/>?peId=" + peId ,"", "scrollbars=yes, status=yes, resizable=yes,width=700,height=700")


  }
     
  function positionItems() {
    var detailsDiv = document.all.Details;
    var maxHeight = document.body.clientHeight - detailsDiv.offsetTop - 5;
    if (maxHeight < 200) {
      maxHeight = 200;
    }

    <%-- Setting the detailsDiv width to a specific size then back to 100%
      -- works around an IE bug where the detailsDiv would come up initially
      -- wider than it should have been (by the width of a scroll bar). --%>
    detailsDiv.style.width = detailsDiv.offsetWidth - 1;
    detailsDiv.style.width = "100%";
    detailsDiv.style.height = maxHeight;
  }

  function initPage() {
  	if (parent.topFrame) {
	    parent.topFrame.document.all.banner.innerHTML = myBanner;
	}
    positionItems();
    document.limsManageEvaluationsForm.id.focus();
  }

  var priorResizeHeight = 0;

  function onResizePage() {
    var currentHeight = document.body.clientHeight;
    if (currentHeight == priorResizeHeight) return;
    priorResizeHeight = currentHeight;
    positionItems();
  }
  
function setReportEvaluation(){    
    var checked = false;
   
    if((typeof(document.limsManageEvaluationsForm.sourceEvaluationId) == 'object')) {
     	if (typeof(document.limsManageEvaluationsForm.sourceEvaluationId.value) == 'string') {
    		if (document.limsManageEvaluationsForm.sourceEvaluationId.checked) {
				checked = true;
			}
    	} else {
			for (i=0; i< document.limsManageEvaluationsForm.sourceEvaluationId.length; i++) {
				if (document.limsManageEvaluationsForm.sourceEvaluationId[i].checked) {
					checked = true;
					break;
				}	
			}
		}
		
		if (!checked) {
			alert("You must first select an evaluation before attempting this operation.");
		}else {	
	  		document.limsManageEvaluationsForm.isReportEvaluation.value = 'true';
	  		document.limsManageEvaluationsForm.action = '<html:rewrite page="/lims/limsReportEvaluation.do"/>';
	  		//blank out the value in the "scan new slide/sample" text box, as we want to continue to display
	  		//information for the currently selected slide/sample (leaving this value in the text box will
	  		//cause the app to behave as if the user hit the submit button
	  		document.forms[0].id.value='';  
  	  		document.limsManageEvaluationsForm.submit();
  		}     
    }else {
       alert("There are no evaluations to select.");
    } 
}
function setPullStatus(URL, windowParameters, name) {
    var userWarned = 'true';
    <logic:present name="limsManageEvaluationsForm" property="discordant"> 
    <logic:equal name="limsManageEvaluationsForm" property="discordant" value="Yes">
      userWarned = confirm("This sample is marked as Discordant. Do you wish to continue ?");
    </logic:equal>
    </logic:present>
    
    if ( userWarned) {
    	var reason = null;
    	var reportMostRecentEval = null;
	  	var returnedObject = window.showModalDialog(URL, windowParameters, name);
	  	if (returnedObject != null) {
		  	reason = returnedObject.reasonText;
		  	reportMostRecentEval = returnedObject.reportMostRecentEval;
	  	}
		document.limsManageEvaluationsForm.reason.value = reason;
	  	if (reason != null) {
	  	  if (name == 'pull') {
			document.limsManageEvaluationsForm.action = '<html:rewrite page="/lims/limsPullSample.do"/>';
	  	  }else if (name == "unpull") {
		    document.limsManageEvaluationsForm.action = '<html:rewrite page="/lims/limsUnpullSample.do"/>';
		    if (reportMostRecentEval == 'true') {
		      document.limsManageEvaluationsForm.reportMostRecentEval.value = 'true';
		    }
	  	  }
	  	  //blank out the value in the "scan new slide/sample" text box, as we want to continue to display
	  	  //information for the currently selected slide/sample (leaving this value in the text box will
	  	  //cause the app to behave as if the user hit the submit button
	  	  document.forms[0].id.value='';  
	  	  document.limsManageEvaluationsForm.submit();
	  	}
    }	  	
}

function showViewIncidentReportPopup(sampleId) {
    var url = '<html:rewrite page="/lims/limsViewIncidentReport.do"/>?sampleId=' + sampleId;
	var w = window.open(url,
	  'IncidentReportWindow',
	  'scrollbars=yes, status=yes, resizable=yes,width=700,height=700');
	w.focus();
}

function openImage(URL)  {
	var w = window.open(URL, "ViewslideImage", "scrollbars=no, resizable=yes,width=700,height=650");
	w.focus();
}

function scanValue() {
  if (document.forms[0].id.value.length >= 0) { 
	if (document.all.item("slideId") != null) {
		document.limsManageEvaluationsForm.slideId.value = '';
	}
	document.limsManageEvaluationsForm.sampleId.value = '';
	document.limsManageEvaluationsForm.action = '<html:rewrite page="/lims/limsSlideQueryStart.do"/>';
	document.limsManageEvaluationsForm.submit();
  }
}

function setSlideId(slideId) {
	document.limsManageEvaluationsForm.editCopyId.value = slideId;
}

function copyEvaluation() {	
	var checked = false;
	if((typeof(document.limsManageEvaluationsForm.sourceEvaluationId) == 'object')) {
	
		if (typeof(document.limsManageEvaluationsForm.sourceEvaluationId.value) == 'string') {
    		if (document.limsManageEvaluationsForm.sourceEvaluationId.checked) {
				checked = true;
			}
    	} else {
			for (i=0; i< document.limsManageEvaluationsForm.sourceEvaluationId.length; i++) {
				if (document.limsManageEvaluationsForm.sourceEvaluationId[i].checked) {
					checked = true;
					break;
				}	
			}
		}
		if (!checked) {
			alert("You must first select an evaluation before attempting this operation.");
			return false;
		}else {
			if (document.limsManageEvaluationsForm.editCopyId.value == '') {
				alert("A slide must be entered in order to Edit or Copy the selected evaluation.");
				return false;
			}else {
	  		    //blank out the value in the "scan new slide/sample" text box, as we want to continue to display
	  		    //information for the currently selected slide/sample (leaving this value in the text box will
	  		    //cause the app to behave as if the user hit the submit button
	  		    document.forms[0].id.value='';			
				document.limsManageEvaluationsForm.isEditCopy.value = 'true';
				return true;
			}
		}
	}else {
		 alert("There are no evaluations to select.");
		 return false;
	}		
	
}

function viewRawPathReport(){
	<logic:present name="limsManageEvaluationsForm" property="pathReportId">
		<logic:notEqual name="limsManageEvaluationsForm" property="pathReportId" value="">
			window.open('<html:rewrite page="/ddc/Dispatch"/>?op=PathRawPrepare&popup=true&pathReportId=<bean:write name="limsManageEvaluationsForm" property="pathReportId"/>','ASCII2373','scrollbars=yes,resizable=yes,status=yes,width=640,height=480');
		</logic:notEqual>
		<logic:equal name="limsManageEvaluationsForm" property="pathReportId" value="">
			alert("There is no raw path report available for this sample.");
		</logic:equal>
	</logic:present>
}
function checkEnter(event) { 
     var code = 0; 
     code = event.keyCode;
     if (code == 13) {
          return false;
    }
}
function checkKey(event) {       
     var code = 0; 
     code = event.keyCode;  
     if (code == 13) { 
       scanValue(); 
     } 
}

    </script>
  </head>
  <body class="bigr" <logic:notPresent name="popup">onLoad="initPage();"</logic:notPresent> style="margin-top: 0; margin-bottom: 0;">
  	<logic:present name="popup">
  		<script>
  			window.opener.invokeFunction('<bean:write name="limsManageEvaluationsForm" property="sampleId"/>', '<%= LimsConstants.LIMS_TX_UPDATEPV %>');
  			window.close();
  		</script>
  	</logic:present>
  	<logic:notPresent name="popup">
	  	<html:form action="/lims/limsManageEvaluations">    
	  	  <html:hidden property="reportMostRecentEval" value="false"/>
	    <div id="Header" style="width:100%; z-index: 2; "> 
	      <template:get name='header' flush="true"/>
	      <br>
	    </div>
	    <div id="Details" style="position: static; width:100%;  border-width: 2px 2px 2px 2px; border-style: solid; border-color: #336699;"> 
	      <script type="text/javascript">positionItems();</script>
	      <template:get name='detail' flush="true"/><br>
	      <jsp:include page="/hiddenJsps/iltds/icp/icpHistory.jsp" flush="true"/>
	    </div>
	    </html:form>
	 </logic:notPresent>
	</body>
	  
</html> 
