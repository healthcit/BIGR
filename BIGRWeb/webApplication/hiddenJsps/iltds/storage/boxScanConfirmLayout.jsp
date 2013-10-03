<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.assistants.LegalValueSet"%>
<%@ page import="com.ardais.bigr.javabeans.BoxLayoutDto"%>
<%@ page import="com.ardais.bigr.orm.helpers.BoxScanData"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ taglib uri="/tld/struts-html" prefix="html"%>
<%@ taglib uri="/tld/bigr" prefix="bigr"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
String boxId = request.getParameter("boxID");
if (boxId == null) {
  boxId = "";
}
BoxLayoutDto boxLayoutDto = (BoxLayoutDto)request.getAttribute("boxLayoutDto");
String boxLayoutId = boxLayoutDto.getBoxLayoutId();
BoxScanData bsd = (BoxScanData) request.getAttribute("boxScanData");
String systemBoxLayoutId = (String) request.getAttribute("systemBoxLayoutId");
LegalValueSet boxLayouts = bsd.getAccountBoxLayoutSet();
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
}

function onFormSubmit() {
  disableActionButtons();
  
  // Set the selected layout id.  If they elect to keep the current layout,
  // then we don't have to set the hidden input since it's already set to
  // the layout they selected.
  var choices = document.getElementsByName("choice");
  for (var i = 0; i < choices.length; i++) {
    if (choices[i].checked) {
      if (choices[i].value == "selected") {
        document.getElementById("empty").value = "true";
      }
      else if (choices[i].value == "system") {
        document.getElementById("boxLayoutId").value = "<%=systemBoxLayoutId%>";
      }
      else if (choices[i].value == "new") {
        var layouts = document.getElementsByName("allLayouts")[0];
        if (layouts.selectedIndex == 0) {
          alert("Please select a layout");
          enableActionButtons();
          return false;
        }
        document.getElementById("boxLayoutId").value = layouts.value;
        if (layouts.value != "<%=systemBoxLayoutId%>") {
          document.getElementById("empty").value = "true";
        }
      }
    }
  }
  return true;
}
  
function disableActionButtons() {
	document.form1.Submit.disabled = true;
}

function enableActionButtons() {
	document.form1.Submit.disabled = false;
}

function selectDifferentRadio() {
  // If the first "Select a layout" choice is selected, then don't check the
  // corresponding radio button.
  if (document.getElementsByName("allLayouts")[0].selectedIndex == 0) {
    return;
  }

  var choices = document.getElementsByName("choice");
  for (var i = 0; i < choices.length; i++) {
    if (choices[i].value == "new") {
      choices[i].checked = true;
    }
  }
}

myBanner = 'Box Scan Confirm Layout';
</script>
</head>
<body class="bigr" onLoad="initPage();">
<form name="form1" action="<html:rewrite page='/iltds/Dispatch'/>" method="POST"
	  onsubmit="return(onFormSubmit());">
  <input type="hidden" name="op" value="BoxScanInitial">
  <input type="hidden" name="boxID" value="<%=boxId%>">
  <input type="hidden" name="boxLayoutId" value="<%=boxLayoutId%>" id="boxLayoutId" />
  <input type="hidden" name="title" value="<%=request.getParameter("title")%>">
  <input type="hidden" name="confirm" value="false">
  <input type="hidden" name="empty" value="false">
  <div align="center">
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0">
            <% if(request.getAttribute("myError")!=null) { %>
            <tr class="yellow"> 
              <td colspan="2"> 
                <div align="center"><font color="#FF0000"><b><%= request.getAttribute("myError")%> </b></font></div>
              </td>
            </tr>
			<script>
			alert("<%= request.getAttribute("myError") %>");
			</script>
            <% } %>
            <tr class="white"> 
              <td class="grey"><b> Box ID:</b></td>
              <td><b><%=boxId%> 
                </b></td>
            </tr>
            
            <tr class="white"> 
              <td colspan="2">The selected box layout does not match the box layout
              expected by the system.  <br>Do you wish to:
            </tr>

            <tr class="white"> 
              <td>
                <input name="choice" type="radio" value="selected" checked/>
              </td>
              <td>
                Keep the selected layout (<%=boxLayouts.getDisplayValue(boxLayoutId)%>)
              </td>
            </tr>

            <tr class="white"> 
              <td>
                <input name="choice" type="radio" value="system"/>
              </td>
              <td>
                Select the layout expected by the system (<%=boxLayouts.getDisplayValue(systemBoxLayoutId)%>)
              </td>
            </tr>

<%
// Only allow the user to select a different box layout than the one they
// already selected and the one expected by the system if there are more than
// 2 layouts available, since we're showing those 2 above.
if (boxLayouts.getCount() > 2) {
  Set excluded = new HashSet();
  excluded.add(boxLayoutId);
  excluded.add(systemBoxLayoutId);
%>
            <tr class="white"> 
              <td>
                <input name="choice" type="radio" value="new"/>
              </td>
              <td>
                Select a different layout: 
                <bigr:selectList property="allLayouts"
                  value=""
                  firstValue=""
                  firstDisplayValue="Select a layout"
                  excludedValues="<%=excluded%>"
                  legalValueSet="<%=boxLayouts%>"
                  onchange="selectDifferentRadio();"
                />
              </td>
            </tr>
<%
}
%>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="submit" name="Submit" value="Continue">
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</form>
</body>
</html>
