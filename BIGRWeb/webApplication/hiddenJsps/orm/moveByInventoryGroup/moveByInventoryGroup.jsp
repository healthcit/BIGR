<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ page import="com.ardais.bigr.util.Constants" %>

<%
	String action = request.getParameter("action");
	String textBanner = "";
	if (action.equalsIgnoreCase(Constants.OPERATION_ADD)) {
	 	textBanner = "Add Samples To Inventory Group";
	}
	else if (action.equalsIgnoreCase(Constants.OPERATION_REMOVE)) {
	 	textBanner = "Remove Samples From Inventory Group";
	}	
%>
<html>
<head>
<title>Move Samples By Inventory Group</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script language="JavaScript" src="<html:rewrite page="/js/BigrLib.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Add Samples To Inventory Group';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = '<%=textBanner%>';
  deselectAllFromList("sampleIds");
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  if (!selectAllIds()) {
    setActionButtonEnabling(true);
    return false;
  }
  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  disableControlCollection('sampleIdsButton', isDisabled);
}

function onClickCancel() {
  setActionButtonEnabling(false);
  var action = document.all.action.value;
  if (action == "Add") {
  	window.location = '<html:rewrite page="/orm/moveByIg/moveByIgStart.do?action=Add"/>';
  }
  else {
  	window.location = '<html:rewrite page="/orm/moveByIg/moveByIgStart.do?action=Remove"/>';
  }
}

function selectAllIds() {
  if (!addItemToList("sampleId", "sampleIds", IGNORE_TYPE, true)) {return false;}
  selectAllFromList("sampleIds");
  return true;
}

</script>
</head>
<body onload="initPage();">
<html:form method="POST" action="/orm/moveByIg/moveByIg" onsubmit="return(onFormSubmit());">
  <html:hidden name="moveByInventoryGroupForm" property="action"/>
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
  <br>
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
		  <tr class="white" align="left" valign="center">
		    <td style="padding: 1em 0 1em 0;">
		      Sample Id or Alias
		      <br>
		      <input type="text" name="sampleId" size="20" maxlength="30"
  		         onBlur="if (document.all['sampleId'].value.length != 0) {
    			           if (!isValidId_Alert(document.all['sampleId'].value, IGNORE_TYPE, true)) {
    		   		         document.all['sampleId'].value = '';
    		   		         document.all['sampleId'].focus();
    		   	           }
    		             }"
    		  />
		    </td>
		    <td align="center" style="padding: 1em;">
		      <bigr:idlist property="sampleIdsButton" type="IGNORE_TYPE" idfrom="sampleId" idlist="sampleIds" style="FONT-SIZE:xx-small"/>
		    </td>
		    <td style="padding: 1em 0 1em 0;">
		      Sample IDs
		      <br>
		      <html:select property="sampleIds" size="20" multiple="true" style="width: 150px">
		        <logic:present name="moveByInventoryGroupForm" property="sampleIds">
		          <html:options property="sampleIds"/>
		        </logic:present>
		      </html:select>
		    </td>
		  </tr>
        </table>
      </td>
    </tr>
  </table>
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr class="white"> 
              <td nowrap>Inventory Group:</td>
            </tr>
            <tr class="white"> 
              <td>
                <bigr:selectList
                  name="moveByInventoryGroupForm" property="inventoryGroup" 
		          legalValueSetProperty="inventoryGroupChoices"
		          firstValue="" firstDisplayValue="Select"
		        />
              </td>
            </tr>
            <tr>
              <td colspan="3">&nbsp;</td>
            </tr> 
            <tr class="white"> 
              <td colspan="3" align="left">Comment:</td>
            </tr>
		    <tr>
		      <td colspan="3" align="left">
		        <html:textarea property="note" cols="80" rows="10" onkeyup='maxTextarea(500)'/>
		      </td>
		    </tr>
        </table> 
            <br>
        <table width="100%" border="0" cellspacing="1" cellpadding="3" class="foreground">
            <tr class="white"> 
              <td align="center">
	              <html:submit property="btnSubmit" value="Submit"/>
	              <html:cancel property="btnCancel" value="Cancel" onclick="onClickCancel();"/>
              </td>
            </tr>
        </table>
      </td>
    </tr>
  </table>
</html:form>
</body>

</html>