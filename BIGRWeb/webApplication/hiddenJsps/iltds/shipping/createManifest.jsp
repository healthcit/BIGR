<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic" %>
<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>

<html>
<head>
<bean:define id="manifest"
  name="<%=com.ardais.bigr.web.taglib.BigrTagConstants.ACTION_FORM_KEY%>"
  type="com.ardais.bigr.iltds.web.form.ManifestForm"/>

<title>Create Manifest</title>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Create Manifest';

var boxCounter = 0;

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  document.all.scannedBoxId.focus();
}

function checkTab(event) { 	
	var code = 0;
	code = event.keyCode;  
	if (code == 9) {
	  var textBox = document.all.scannedBoxId;
	  textBox.value = trim(textBox.value);
		if (textBox.value.length > 0) {
			onClickAddToManifest();
			event.returnValue = false;
		}
	}
}

function addToManifest() {
  var textBox = document.all.scannedBoxId;
  textBox.value = trim(textBox.value);

  if (textBox.value.length > 0) {
		// Add the box id to a new row in the scanned boxes table.  Also add a hidden
		// input to capture the box id.
		var newRow = document.all.scannedBoxes.insertRow(-1);
		newRowId = "boxRow" + boxCounter++;
		newRow.className = "white";
		newRow.id = newRowId;
		var newCell = newRow.insertCell(0);
		var escapedId = htmlEscape(document.all.scannedBoxId.value);
		var cellContents = escapedId;
		cellContents += '<input type="hidden" name="boxIds" value="';
		cellContents += escapedId;
		cellContents += '"/>';
		newCell.innerHTML = cellContents;
		var newCell = newRow.insertCell(1);
		cellContents = "<input type=\"button\" name=\"btnRemove\" value=\"Remove\" onclick=\"onClickRemoveFromManifest('";
		cellContents += newRowId;
		cellContents += "');\"/>";
		newCell.innerHTML = cellContents;
	}

	// Clear the scanned box id and give it focus.
	document.all.scannedBoxId.value = "";
  document.all.scannedBoxId.focus();
}

function removeFromManifest(boxRowId) {
	var allRows = document.all.scannedBoxes.rows;
	for (i = 0; i < allRows.length; i++) {
		var row = allRows[i];
		if (row.id == boxRowId) {
			document.all.scannedBoxes.deleteRow(i);
		}
	}
}

function onClickAddToManifest() {
  // doesn't submit, so currently no button disabling
  addToManifest();
}

function onClickRemoveFromManifest(boxRowId) {
  // doesn't submit, so currently no button disabling
  removeFromManifest(boxRowId);
}

function onFormSubmit() {
  setActionButtonEnabling(false);

  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  setInputEnabled(f, "btnCreate", isEnabled);
  setInputEnabled(f, "btnAdd", isEnabled);
  setInputEnabled(f, "btnRemove", isEnabled);
}

</script>
</head>

<body class="bigr" onload="initPage();">
<html:errors/>
<html:form action="/iltds/shipping/createManifest"
    onsubmit="return(onFormSubmit());">
	<table class="background" cellpadding="0" cellspacing="0" border="0" align="center">
		<tr> 
			<td>
				<table id="scannedBoxes" class="foreground" cellpadding="3" cellspacing="1" border="0">
          <tr class="yellow"> 
            <td colspan="2" align="center"> 
              <b>Please Scan Boxes For New Manifest</b>
            </td>
          </tr>
					<tr class="white"> 
						<td class="grey"><b>Scan Box Barcode:</b></td>
						<td>
							&nbsp;<input type="text" name="scannedBoxId" size="15" maxlength="12" 
							        onkeydown="checkTab(event);">
							&nbsp;<input type="button" name="btnAdd" value="Add to Manifest"
							        onclick="onClickAddToManifest();">
						</td>
					</tr>
					<tr class="white"><td colspan="2">&nbsp;</td></tr>
					<tr class="white"> 
						<td class="green" colspan="2" align="center">
							<b>Boxes on Manifest:</b>
						</td>
					</tr>

					<logic:iterate name="manifest" property="boxIdList" id="boxId" indexId="boxIndex" type="java.lang.String">
					<tr class="white" id="<%=boxIndex%>">
                      <td>
                        <%=boxId%>
                        <input type="hidden" name="boxIds" value="<%=boxId%>"/>
                      </td>
					  <td>
					    <input type="button" name="btnRemove" value="Remove" onclick="onClickRemoveFromManifest(<%=boxIndex%>);"/>
					  </td>
					</tr>
					</logic:iterate>

				</table>
			</td>
		</tr> 
		<tr> 
			<td>
				<table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
					<tr class="white"> 
						<td align="center">
						  <html:submit property="btnCreate" value="Create Manifest"/>
						</td>
					</tr>
				</table>
			</td>
		</tr> 
	</table>
</html:form>
</body>
</html>