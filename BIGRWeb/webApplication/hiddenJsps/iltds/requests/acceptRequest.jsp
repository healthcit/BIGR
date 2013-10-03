<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.api.ValidateIds" %>
<%@ page import="com.ardais.bigr.iltds.helpers.RequestSelect"%>
<%@ page import="com.ardais.bigr.iltds.web.form.RequestItemForm" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define id="myForm" name="requestDtoForm" type="com.ardais.bigr.iltds.web.form.RequestForm"/>
<html>
<head>
<%--
  ** If the confirm request parameter is set, confirm the form submit
  ** with an confirm dialog box. Ok will go forth and resubmit with
  ** the confirm flag set.
  --%>
<logic:present parameter="confirm">
<logic:equal parameter="confirm" value="true">
<bean:define id="confirmRequested" value="true" type="java.lang.String"/>
</logic:equal>
</logic:present>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Manage Requests</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script type="text/javascript" src="<html:rewrite page="/js/calendar.js"/>"> </script>
<script language="JavaScript">
<!--

var myBanner = 'Accept Request';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}

function checkKey(event)
{ 	
	var code = 0;
	code = event.keyCode;

	if ((code == 9) || (code == 13)) {
		addBox();
	}
}

function printPicklistForRequest(requestId) {
	var URL = '<html:rewrite page="/iltds/printRequestPickList.do"/>' + "?requestId=" + requestId;
	URL = URL + "&itemDetailLevel=" + <%=RequestSelect.ITEM_INFO_DETAILS%>;
	URL = URL + "&boxDetailLevel=" + <%=RequestSelect.BOX_INFO_NONE%>;
	URL = URL + "&getPickListInfo=true";
	var w = window.open(URL, "PickList" + requestId, "scrollbars=yes,resizable=yes,top=0,left=0,width=950,height=750");
	w.focus();
}

function confirmPartial() {
	var f = document.forms[0];
	setActionButtonEnabling(false);
	event.returnValue = false;
	
	var newComment = document.forms[0].confirmRequestManagerComments.value;
	document.forms[0].requestManagerComments.value = newComment;

	document.forms[0].action = '<html:rewrite page="/iltds/acceptRequestFinish.do?confirmFlag=true"/>';
	document.forms[0].submit();
}

function cancelConfirm() {
	var f = document.forms[0];
    setActionButtonEnabling(false);
	event.returnValue = false;
	document.forms[0].action = '<html:rewrite page="/iltds/acceptRequestBack.do"/>';
	document.forms[0].submit();
}

function addBox() {
    setActionButtonEnabling(false);
	event.returnValue = false;
	document.forms[0].action = '<html:rewrite page="/iltds/acceptRequestNext.do"/>';
	document.forms[0].submit();
}

function fulfillRequest() {
    setActionButtonEnabling(false);
	event.returnValue = false;
	document.forms[0].action = '<html:rewrite page="/iltds/acceptRequestFinish.do"/>';
	document.forms[0].submit();
}

function cancelFulfillment() {
    setActionButtonEnabling(false);
	event.returnValue = false;
	document.forms[0].action = '<html:rewrite page="/iltds/manageRequestsStart.do"/>';
	document.forms[0].submit();
}

function setActionButtonEnabling(isEnabled) {
	var f = document.forms[0];
	setInputEnabled(f,'next',isEnabled);
	setInputEnabled(f,'finish',isEnabled);
	setInputEnabled(f,'cancel',isEnabled);
}


// expand or collapse the information for a box
function showBox(tableId, imgId){
	var image;
	var alt;
	var displayValue;
	var table = document.all(tableId);
	var img = document.forms[0].elements[imgId];
	var isClosed = (table.style.display == 'none');
	if (isClosed) {
		image = '<html:rewrite page="/images/MenuOpened.gif"/>';
		alt = "Close box information";
		displayValue = 'inline';
	}
	else {
		image = '<html:rewrite page="/images/MenuClosed.gif"/>';
		alt = "Open box information";
		displayValue = 'none';
	}
	table.style.display = displayValue;
	img.src=image;
	img.alt=alt;
}

//-->
</script>
</head>
<body  class="bigr" onLoad="initPage();">
<html:form action="/iltds/acceptRequestStart" onsubmit="addBox();">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
      <tr class="yellow"> 
	    <td> 
	      <div align="white">
	        <font color="#FF0000"><b><html:errors/></b></font>
	      </div>
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
  <logic:present name="confirmRequested" scope="page">
  <html:hidden property="requestId"/>
  <html:hidden property="requestManagerComments"/>
  <logic:iterate name="requestDtoForm" property="requestBoxForms" id="element" indexId="boxIndex">
  <html:hidden property='<%="requestBoxForm[" + boxIndex + "].boxId"%>'/>
  <html:hidden property='<%="requestBoxForm[" + boxIndex + "].boxLayoutId"%>'/>
  <bean:define id="requestBoxForm" name="requestDtoForm" property='<%="requestBoxForm[" + boxIndex + "]"%>' type="com.ardais.bigr.iltds.web.form.RequestBoxForm"/>
  <%
    Iterator iterator = requestBoxForm.getContents().keySet().iterator();
    while (iterator.hasNext()) {
      String cellRef = (String)iterator.next();
  %>
    <html:hidden property='<%="requestBoxForm[" + boxIndex + "].cellContent[" + cellRef + "]"%>'/>
  <%}%>
  </logic:iterate>
  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
    <tr class="yellow"> 
      <td colspan="3"><div align="center"><b>Confirm Request Fulfillment</b></div></td>
    </tr>
    <tr class="white">
      <td colspan="2" class="grey" width="50%"><div align="right"><b>Request Id</b></div></td>
      <td width="50%">
        <bean:write name="requestDtoForm" property="requestId"/>
      </td>
    </tr>
    <tr class="white">
      <td colspan="2" class="grey" width="50%"><div align="right"><b>Submission Date</b></div></td>
      <td width="50%">
        <bean:write name="requestDtoForm" property="createDate"/>
      </td>
    </tr>
    <tr class="white">
      <td colspan="2" class="grey" width="50%"><div align="right"><b>Submitted By</b></div></td>
      <td width="50%">
        <bean:write name="requestDtoForm" property="requesterName"/>
      </td>
    </tr>
    <tr>
      <td colspan="3">
        &nbsp;
      </td>
    </tr>
    <tr class="white">
      <td colspan="3">
        &nbsp;
      </td>
    </tr>
    <tr class="yellow">
      <td colspan="3" nowrap>
        <div align="center">
          <b>The following expected items have not been scanned:</b>
        </div>
      </td>
    </tr>
    <tr class="white">
    <%
       int count=0;
       Map itemMap = myForm.getRequestItemsFormsByItemId();
    %>
      <logic:iterate name="requestDtoForm" property="unfulfilledItems" id="unfulfilledItem" type="java.lang.String">      
    <%
    	String unfufilledItemText = unfulfilledItem;
      RequestItemForm itemForm = (RequestItemForm)itemMap.get(unfulfilledItem);
      if (itemForm != null && !ApiFunctions.isEmpty(itemForm.getItemAlias())) {
        unfufilledItemText = unfufilledItemText + " (" + Escaper.htmlEscapeAndPreserveWhitespace(itemForm.getItemAlias()) + ")";
      }
    %>
        <td>
          <div align="center">
            <%=unfufilledItemText%>&nbsp;&nbsp;&nbsp;
          </div>
        </td>
        <%
        	//put 3 sample ids per row
        	count = count + 1;
        	if (count == 3) {
        		count = 0;
        %>
        	</tr>
        	<tr class="white">
        <%
        	}
        %>
      </logic:iterate>
    </tr>
    <tr>
      <td colspan="3">
        &nbsp;
      </td>
    </tr>
    <tr class="white">
      <td colspan="3">
        <div align="center">
          If you have additional items to scan, click "Cancel", <br>
          otherwise please provide details on why the expected <br>
          items have not been scanned.
        </div>
      </td>
    </tr>
    <tr>
      <td colspan="3">
        &nbsp;
      </td>
    </tr>
    <tr class="white"> 
      <td colspan="3">
        <div align="center">
          <b>Request Manager Comment</b>
        </div>
      </td>
    </tr>
    <tr class="white"> 
      <td colspan="3">
        <div align="center">
          <html:textarea property="confirmRequestManagerComments" cols="80" rows="10" onkeyup='maxTextarea(4000)'/>
        </div>
      </td>
    </tr>
    <tr class="white"> 
      <td colspan="3"> 
        <div class="noprint" align="center">
          <html:submit property="finish" value="Finish" onclick="confirmPartial()" style="font-size:xx-small; width: 90px;"/>
          <html:cancel property="cancel" value="Cancel" onclick="cancelConfirm()" style="font-size:xx-small; width: 90px;"/>
        </div>
      </td>
    </tr>
  </table>
  </logic:present>
  <logic:notPresent name="confirmRequested" scope="page">
  <bean:define id="boxes" name="requestDtoForm" property="requestBoxForms" type="java.util.Map"/>
  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
    <tr class="yellow">
      <td colspan="3"><b>Request Information</b></td>
      <td colspan="3"><b>Requested Items</b></td>
    </tr>
    <tr class="white">
      <td colspan="2" class="grey"><div align="right"><b>Request Id</b></div></td>
      <td><bean:write name="requestDtoForm" property="requestId"/></td>
      <html:hidden property="requestId"/>
      <td colspan="3" rowspan="3" valign="top">
<%
	String comma = "";
%>
        <logic:iterate name="requestDtoForm" property="requestItemForms" id="element" indexId="itemIndex">
<%=comma%>
<%
	comma = ",&nbsp;&nbsp;";
%>
          <bean:write name="requestDtoForm" property='<%="requestItemForm[" + itemIndex + "].itemId"%>'/>
          <logic:notEmpty name="requestDtoForm" property='<%="requestItemForm[" + itemIndex + "].itemAlias"%>'>
            (<bean:write name="requestDtoForm" property='<%="requestItemForm[" + itemIndex + "].itemAlias"%>'/>)
          </logic:notEmpty>
        </logic:iterate>
      </td>
    </tr>
    <tr class="white">
      <td colspan="2" class="grey"><div align="right"><b>Submission Date</b></div></td>
      <td><bean:write name="requestDtoForm" property="createDate"/></td>
    </tr>
    <tr class="white">
      <td colspan="2" class="grey"><div align="right"><b>Submitted By</b></div></td>
      <td><bean:write name="requestDtoForm" property="requesterName"/></td>
    </tr>
    <tr class="white">
      <td colspan="6" align="center">
        <bean:define name="requestDtoForm" property="requestId" id="requestId"/>
        <input class="smallButton" type="button" name="picklist" value="Picklist" onClick="printPicklistForRequest('<%=requestId%>');">
      </td>
    </tr>
    <tr class="white">
      <td colspan="6">&nbsp;</td>
    </tr>
    <tr class="yellow">
      <td colspan="3"><b>Submitter Comments</b></td>
      <td colspan="3"><b>Request Manager Comments</b></td>
    </tr>
    <tr class="white">
      <td colspan="3" valign="top"><bigr:beanWrite filter="true" whitespace="true" name="requestDtoForm" property="requesterComments"/></td>
      <td colspan="3">
        <html:textarea property="requestManagerComments" cols="60" rows="10" onkeyup='maxTextarea(4000)'/>
      </td>
    </tr>
    <tr>
      <td colspan="6">&nbsp;</td>
    </tr>
    <logic:notEqual name="boxes" property="empty" value="true">
    <tr>
      <td colspan="6" class="yellow"><b>Requested Boxes</b></td>
    </tr>
    </logic:notEqual>
  </table>
  <br>
  <div align="center">
    <% int tabIndex = 0;
       String strJS_focus = null;
    %>
    <%
      int boxCount = 0;
      List boxList = myForm.getRequestBoxFormsAsList();
      if (boxList != null) {
        boxCount = boxList.size();
	  }
    %>
    <logic:iterate name="requestDtoForm" property="requestBoxForms" id="element" indexId="boxIndex">
    <%
      boolean lastBox = (boxIndex.intValue() == boxCount-1);
      String tableId = "boxTable" + boxIndex;
      String imgId = tableId + "Img";
      String altText;
      String imgSource;
      String displayStyle;
      if (lastBox) {
        altText = "Close box information";    
        request = (HttpServletRequest) pageContext.getRequest(); 
        String contextPath = request.getContextPath(); 
        imgSource =  contextPath +"/images/MenuOpened.gif";	
        displayStyle = "inline";
        strJS_focus = "document.forms[0]['requestBoxForm["  +  (boxCount-1) + "].cellContent[1]'].focus();";
      }
      else {
        altText = "Open box information";
        request = (HttpServletRequest) pageContext.getRequest(); 
        String contextPath = request.getContextPath(); 
        imgSource =  contextPath +"/images/MenuClosed.gif";	
        displayStyle = "none";
      }
    %>
    <span onClick="showBox('<%=tableId%>','<%=imgId%>')" onKeyPress="showBox('<%=tableId%>','<%=imgId%>')">
      <img id="<%=imgId%>" alt="<%=altText%>" src="<%=imgSource%>"/>
      &nbsp;
	  Box ID:
    </span>
    <b><bean:write name="requestDtoForm" property='<%="requestBoxForm[" + boxIndex + "].boxId"%>'/></b>
    <html:hidden property='<%="requestBoxForm[" + boxIndex + "].boxId"%>'/>
    <html:hidden property='<%="requestBoxForm[" + boxIndex + "].boxLayoutId"%>'/>
    <br>
    <bean:define id="requestBoxForm" name="requestDtoForm" property='<%="requestBoxForm[" + boxIndex + "]"%>' type="com.ardais.bigr.iltds.web.form.RequestBoxForm"/>
   
    <table style="display: <%=displayStyle%>;" id="<%=tableId%>" border="0" cellspacing="1" cellpadding="3" class="foreground">
      <bigr:box
        boxLayoutName="requestBoxForm"
        boxLayoutProperty="boxLayoutDto"
        grouped="true"
        tabIndexStartPos="<%=Integer.toString(tabIndex)%>"
        boxIndex="<%=boxIndex.toString()%>"
        boxFormName="requestDtoForm"
        boxFormVar="requestBoxForm"
        boxCellContentVar="cellContent"
        cellType="input"
        inputMaxLength="30"
        inputSize="20"
      />
    </table>
    <p>
    <%
      tabIndex += requestBoxForm.getBoxLayoutDto().getBoxCapacity();
    %>
    </logic:iterate>
    <bean:define id="boxScanInfo" name="requestDtoForm" property="boxScanData" type="com.ardais.bigr.orm.helpers.BoxScanData"/>
    <logic:greaterThan name="boxScanInfo" property="numberOfAccountBoxLayouts" value="1">
    <%
      String defaultBoxLayoutId = (ApiFunctions.isEmpty(myForm.getNewBoxLayoutId())) ?  boxScanInfo.getDefaultBoxLayoutId() : myForm.getNewBoxLayoutId();
    %>
    Box Layout:&nbsp;
    <bigr:selectList
      name="requestDtoForm"
      property="newBoxLayoutId"
      value="<%=defaultBoxLayoutId%>"
      legalValueSet="<%=boxScanInfo.getAccountBoxLayoutSet()%>"
    />
    </logic:greaterThan>
    <logic:equal name="boxScanInfo" property="numberOfAccountBoxLayouts" value="1">
    <html:hidden property="newBoxLayoutId" value="<%=boxScanInfo.getDefaultBoxLayoutId()%>"/>
    </logic:equal>
    <logic:greaterEqual name="boxScanInfo" property="numberOfAccountBoxLayouts" value="1">
    <p>
    Enter Box Id:&nbsp;
    <html:text property="newBoxId" size="12" maxlength="<%=String.valueOf(ValidateIds.LENGTH_BOX_ID)%>" tabindex="<%=String.valueOf(tabIndex)%>" onkeydown="checkKey(event);"/>
    <p>
    <% if (strJS_focus != null) 
    	{ %>
   		<script>
   			<%=strJS_focus%>
   		</script> 
    <% 	}  else { %>
    	<script>
    		document.forms[0].newBoxId.focus();
    	</script>
    <% 	}  %>
    </logic:greaterEqual>
    <html:submit property="next" value="Next" onclick="addBox()" style="font-size:xx-small; width: 90px;"/>
    <html:button property="finish" value="Finish" disabled='<%=boxes.isEmpty()%>' onclick="fulfillRequest()" style="font-size:xx-small; width: 90px;"/>
    <html:cancel property="cancel" value="Cancel" onclick="cancelFulfillment()" style="font-size:xx-small; width: 90px;"/>
  </div>
  </logic:notPresent>
</html:form>
</body>
</html>
