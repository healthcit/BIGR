<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.util.Constants" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<html>
<head>
<title>Maintain Box Layouts</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<html:rewrite page="/css/bigr.css"/>" type="text/css">
<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">
var myBanner = 'Maintain Box Layouts';
var isWarnOnNavigate = true;

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = myBanner;
  
  document.forms[0].numberOfColumns.focus();
}

function confirmNavigate() {
  if (needNavagationWarning()) {
    event.returnValue = "You have made changes that you have not submitted yet.  Are you sure that you want to continue?";
  }
}

function needNavagationWarning() {
  if (! isWarnOnNavigate) return false;
  
  var f = document.forms[0];
  if (f.numberOfColumns.value != f.numberOfColumns.defaultValue) return true;
  if (f.numberOfRows.value != f.numberOfRows.defaultValue) return true;

  var xAxisLabelSelect = getFirstWithName(f.elements, "xaxisLabelCid");
  if (getControlValue(xAxisLabelSelect) != getControlDefaultValue(xAxisLabelSelect)) return true;

  var yAxisLabelSelect = getFirstWithName(f.elements, "yaxisLabelCid");
  if (getControlValue(yAxisLabelSelect) != getControlDefaultValue(yAxisLabelSelect)) return true;

  var tabIndexSelect = getFirstWithName(f.elements, "tabIndexCid");
  if (getControlValue(tabIndexSelect) != getControlDefaultValue(tabIndexSelect)) return true;

  return false;
}

function onClickCancel() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;
  window.location = '<html:rewrite page="/orm/boxLayout/maintainBoxLayoutStart.do?resetForm=true"/>';
}

function onClickDelete(boxLayoutId) {
  setActionButtonEnabling(false);
  var f = document.forms[0];
  var cancelAction = false;
  var savedOperation = f.operation.value;
  var savedBoxLayoutId = f.boxLayoutId.value;
  f.operation.value = '<%=Constants.OPERATION_DELETE%>';
  f.boxLayoutId.value = boxLayoutId;

  if (needNavagationWarning()) {
    if (!confirm("You have made changes that have not been saved.  Are you sure you want to continue?\n\nPress OK to continue, or Cancel to stay on the current page.")) {
      cancelAction = true;
    }
  }
  
  if (!cancelAction && !onFormSubmit()) {
    cancelAction = true;
  }
  
  if (cancelAction) { // restore field values
    f.operation.value = savedOperation;
    f.boxLayoutId.value = savedBoxLayoutId;
    setActionButtonEnabling(true);
  }
  else {
    isWarnOnNavigate = false;
    f.submit();
  }
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  isWarnOnNavigate = false;

  return true;
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  var f = document.forms[0];
  f.btnSubmit.disabled = isDisabled;
  f.btnCancel.disabled = isDisabled;
  setInputEnabled(f, "btnDelete", isEnabled);
  setInputEnabled(document.all, "linkEditBoxLayout", isEnabled);
}
</script>
</head>
<body class="bigr" onload="initPage();" onbeforeunload="confirmNavigate();">
<html:form method="POST" action="/orm/boxLayout/maintainBoxLayoutSave" onsubmit="return(onFormSubmit());">
  <html:hidden property="operation"/>
  <html:hidden property="boxLayoutId"/>
  <table border="0" cellspacing="0" cellpadding="0" align="center">
    <%-- begin create/edit input table --%>
    <tr> 
      <td> 
        <table width="100%" border="1" cellspacing="1" cellpadding="3" class="foreground">
          <logic:present name="org.apache.struts.action.ERROR">
          <tr class="yellow">
	        <td colspan="2"><font color="#FF0000"><b><html:errors/></b></font></td>
          </tr>
          </logic:present>
          <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
          <tr class="yellow"> 
            <td colspan="2"><font color="#000000"><b><bigr:btxActionMessages/></b></font></td>
	      </tr>
		  </logic:present>
          <tr class="white"> 
<logic:equal name="maintainBoxLayoutForm" property="operation" value="<%= Constants.OPERATION_CREATE %>">
            <td colspan="2" align="center"><b>Create a new box layout</b></td>
</logic:equal>
<logic:equal name="maintainBoxLayoutForm" property="operation" value="<%= Constants.OPERATION_UPDATE %>">
            <td colspan="2" align="center">
              <b>Edit box layout:
              <bean:write name="maintainBoxLayoutForm" property="boxLayoutId" />
              </b>
            </td>
</logic:equal>
          </tr>
          <tr class="white"> 
            <td class="grey" nowrap>Number of Columns</td>
            <td> 
              <html:text property="numberOfColumns" size="10" maxlength="3"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" nowrap>Number of Rows</td>
            <td> 
              <html:text property="numberOfRows" size="10" maxlength="3"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" nowrap>X Axis Label</td>
            <td>
              <bigr:selectList
                name="maintainBoxLayoutForm" property="xaxisLabelCid" 
		        legalValueSetProperty="boxLayoutLabelTypeSet"
		        firstValue="" firstDisplayValue=""
		      />
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" nowrap>Y Axis Label</td>
            <td>
              <bigr:selectList
                name="maintainBoxLayoutForm" property="yaxisLabelCid" 
		        legalValueSetProperty="boxLayoutLabelTypeSet"
		        firstValue="" firstDisplayValue=""
		      />
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" nowrap>Tab Index</td>
            <td>
              <bigr:selectList
                name="maintainBoxLayoutForm" property="tabIndexCid" 
		        legalValueSetProperty="boxLayoutTabIndexSet"
		        firstValue="" firstDisplayValue=""
		      />
            </td>
          </tr>
          <tr class="white"> 
            <td colspan="2" align="center"> 
              <html:submit property="btnSubmit" value="Submit"/>
              <html:cancel property="btnCancel" value="Cancel" onclick="onClickCancel();"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table> <%-- end create/edit input table --%>
  <br/>
  <table border="0" cellspacing="0" cellpadding="0" align="center">
  <%-- begin box layout list table --%>
    <tr> 
      <td>
        <table width="100%" border="1" cellspacing="1" cellpadding="3" class="foreground">
	      <tr class="white">
      	    <td class="grey" colspan="5">
          	  To edit a box layout, click on its Box Layout Link.  To delete a box layout,
          	  click on its Delete button.  The box layout will be deleted as soon
          	  as you click on the button.  Only box layouts that are not associated
          	  with any account level box layouts or boxes in inventory can be deleted.
	        </td>
    	  </tr>
          <tr class="white">
            <td nowrap><b>Box Layout</b></td>
            <td nowrap align="center"><b>ICP</b></td>
            <td nowrap align="center"><b>Delete</b></td>
          </tr>
<logic:iterate id="lyForm" name="maintainBoxLayoutForm" property="boxLayoutForms" type="com.ardais.bigr.orm.web.form.BoxLayoutForm">
          <tr class="white">
            <td>
              <a id="linkEditBoxLayout"
                 href="<html:rewrite page="/orm/boxLayout/maintainBoxLayoutStart.do?operation=Update"/>&boxLayoutId=<bean:write name="lyForm" property="boxLayoutId"/>"
                 onclick="return(isLinkEnabled());">
                <bean:write name="lyForm" property="boxLayoutAttributes"/>
              </a>
            </td>
            <td align="center">
			  <bigr:icpLink linkText="ICP" popup="true">
			    <%=lyForm.getBoxLayoutId()%>
			  </bigr:icpLink>
            </td>
            <td align="center">
			  <button type="button" name="btnDelete" value="Delete"
                style="height: 16px; width: 16px;"
                onclick="onClickDelete('<bean:write name="lyForm" property="boxLayoutId"/>');">
                <html:img page="/images/delete.gif" alt="Delete" border="0"/>
              </button>
            </td>
          </tr>
</logic:iterate>
        </table>
      </td>
    </tr>
  </table> <%-- end box layout list table --%>
</html:form>
</body>
</html>
