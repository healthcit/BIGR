<%@ page language="java" import= "com.ardais.bigr.pdc.helpers.*,com.ardais.bigr.iltds.assistants.LegalValueSet,java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.pdc.oce.util.OceUtil" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%
	OceHelper helper = (OceHelper)request.getAttribute("oceHelper");
	List searchResults = helper.getList();
	int returnedRowCount = 0;
	if (searchResults != null) {
		returnedRowCount = searchResults.size();
	}
%>
<html>
<head>
<title>Other Bottom</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css"
	href="<html:rewrite page="/css/bigr.css"/>">
<script language="JavaScript">
<!--
	var myPage;
	var DTPpage;
	var preSelVal;
	function validateForm()
	{
	 if(document.forms[0].rowCount==null)
	 {
	 	return false;
	 }
	 var rowCount=document.forms[0].rowCount.value;
	 var bool=true;
	 for(var i=0;i<rowCount;i++)
	 {
	 	if(document.forms[0].elements['update'+i].checked)
	 	{
	 		if ((document.forms[0].elements['list'+i].selectedIndex==0) ||
	 		    (document.forms[0].elements['list'+i].selectedIndex == -1))
	 		{
	 		alert("You must make a selection from the alphabetized list selection menu for \n\""+document.forms[0].elements['otherText'+i].value + "\" (row " + (i+1) + ")");
	 		document.forms[0].elements['list'+i].focus();
	 		bool=false;
	 		break;
	 		}
	 	}
	 }
	 
	 if(bool==false)
	 {
	 return false;
	 }else
	 {
		document.forms[0].submit();
		//parent.topFrame.OtherTop.Update.disabled=true;
	 }
	}	
	function toggleUpdateButton()
	{
		 	parent.topFrame.OtherTop.Update.disabled=false;
	}

	function openReportWindow(otherLineId) {
		var url = '<html:rewrite page="/ddc/Dispatch?op=OcePathRawPrepare&popup=true&lineId="/>'+otherLineId;
		if (!myPage || myPage.closed) {
			myPage = window.open(url + '&otherLineId='+otherLineId,'PathAsciireport','width=900,height=500,top=160,left=50,scrollbars=yes');
		}
		else {
			myPage.close();
			myPage = window.open(url + '&otherLineId='+otherLineId,'PathAsciireport','width=900,height=500,top=160,left=50,scrollbars=yes');
		}
	}
	 
	function openPopupWindow(arg,clause) {
			document.forms[0].componentName.value = arg;			
			if (!DTPpage || DTPpage.closed) {
				var lWidth=window.screen.availWidth-510;
				var availHeight=window.screen.availHeight-50;
				DTPpage=window.open('<html:rewrite page="<%=helper.getPopupUrl()%>"/>&whereClause='+clause,'Diagpage','width=500,height='+availHeight+',top=0,left='+lWidth+',scrollbars=yes');				
				DTPpage.focus();
			}else {
				DTPpage.close();			
				var lWidth=window.screen.availWidth-510;
				var availHeight=window.screen.availHeight-50;
				DTPpage=window.open('<html:rewrite page="<%=helper.getPopupUrl()%>"/>&whereClause='+clause,'Diagpage','width=500,height='+availHeight+',top=0,left='+lWidth+',scrollbars=yes');
				DTPpage.focus();
			}
			
	}

	function toggleCheckBox(pickList,rowIndex) { //v2.0
		  preSelVal = pickList.value;	
		  if(pickList.selectedIndex==0)
		  {
			if(document.forms[0].elements['update' + rowIndex].checked)
			{
				document.forms[0].elements['update' + rowIndex].checked = false;
			}
		  }else
		  {
			document.forms[0].elements['update' + rowIndex].checked = true;
			document.forms[0].elements['inapp'+rowIndex].checked = false;
			document.forms[0].elements['concept'+rowIndex].checked = false;
			document.forms[0].elements['hold'+rowIndex].checked = false;
			parent.topFrame.OtherTop.Update.disabled = false;
		  }
	}
	
	function setPreSelectedValue(pickList,index) {
		document.forms[0].componentName.value = index;
		if(preSelVal != null)
		pickList.value = preSelVal;
	}
	
	function setPreSelected(str) {
		preSelVal = str;
	}
	
	function IsOtherBoxChecked(index) {
		if(window.event.srcElement.name.indexOf("update") == 0)
		{
			document.forms[0].elements['inapp'+index].checked = false;
			document.forms[0].elements['concept'+index].checked = false;
			document.forms[0].elements['hold'+index].checked = false;
		}else if(window.event.srcElement.name.indexOf("inapp") == 0)
		{
			document.forms[0].elements['update'+index].checked = false;
			document.forms[0].elements['concept'+index].checked = false;
			document.forms[0].elements['hold'+index].checked = false;
		}else if(window.event.srcElement.name.indexOf("concept") == 0)
		{
			document.forms[0].elements['update'+index].checked = false;
			document.forms[0].elements['inapp'+index].checked = false;
			document.forms[0].elements['hold'+index].checked = false;
		}else if(window.event.srcElement.name.indexOf("hold") == 0)
		{
			document.forms[0].elements['update'+index].checked = false;
			document.forms[0].elements['concept'+index].checked = false;
			document.forms[0].elements['inapp'+index].checked = false;
		}
	}

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<form name="OtherBottom" method="post" action="<html:rewrite page="/ddc/Dispatch?op=OceUpdate"/>"
	onsubmit="return validateForm();">
<table class="background" cellpadding="0" cellspacing="0" border="0" width="100%">
 <tr>
   <td>
	 <table class="foreground" cellpadding="3" cellspacing="1" border="0" width="100%">
	   <tr class="green">
		 <td colspan="5">
		   <b>
		     <font size="2"> 
			   <div align="left">
			     Table Name = <bean:write name="oceHelper" property="tableName" filter="false" />,
				 Attribute = <bean:write name="oceHelper" property="attributeName" filter="false" />, 
				 Status = <bean:write name="oceHelper"	property="statusName" filter="false" />
			       <nbsp><nbsp><nbsp><nbsp><nbsp>(<%=returnedRowCount %> rows found)
			   </div> 
			 </font>
		  </b>
	     </td>
	   </tr>
	<logic:equal name="oceHelper" property="displayWarning" value="true">
	   <tr class="yellow">
		 <td <bean:write name="oceHelper" property="otherTextWidth" filter="false"/> rowspan="2">
			<div align="center">
			  <font size="2">
			    <b>OTHER TEXT / PICK LIST</b>
			  </font>
			</div>
			  <input type="hidden" name="rowCount" value="<%=searchResults.size()%>">
			  <html:hidden name="oceHelper" property="tableName"/> 
			  <html:hidden name="oceHelper" property="status"/> 
			  <html:hidden name="oceHelper" property="attribute"/> 
			  <input type="hidden" name="componentName">
	     </td>
     <logic:equal name="oceHelper" property="displayPathReport"	value="true">
		 <td width="10%" rowspan="2">
		    <div align="center">
		      <font size="2">
		        <b>PATH REPORT</b>
		      </font>
		    </div>
		 </td>
     </logic:equal>
		 <td width="10%" rowspan="2">
		    <div align="center">
		      <font size="2">
		        <b>CLONE?</b>
		      </font>
		    </div>
		 </td>
		 <td width="11%">
			<div align="center">
			  <font size="2">
			    <b>UPDATE/INAPP</b>
			  </font>
			</div>
		 </td>
	   <tr class="yellow">
		 <td width="11%" align="center">
		   <font size="2">
		     <b>HOLD/CONCEPT</b>
		   </font>
		 </td>
	   </tr>
	 <logic:iterate name="oceHelper" id="databean" property="list" indexId="index" type="com.ardais.bigr.pdc.javabeans.OceRowData"><%--
       --%><tr class="<%= ((index.intValue() % 2) == 0) ? "white" : "grey" %>"><%--
		 --%><td <bean:write name="oceHelper" property="otherTextWidth" filter="false"/> rowspan="2"><%--
			--%><div align="left"><%--
			  --%><font size="2"><%--
			    --%><%=databean.getOtherText()%><%--
			  --></font><%--
			--%></div><%--
			--%><input type="hidden" name="otherLineId<%=index.intValue()%>" value="<%=databean.getLineId()%>"><%--
			--%><input type="hidden" name="otherText<%=index.intValue()%>" value="<bigr:beanWrite name="databean" property="otherText" filter="true" whitespace="true"/>"><%--
			--%><select name="list<%=index.intValue()%>" onChange="toggleCheckBox(this,'<%=index.intValue()%>');" onFocus="setPreSelectedValue(this,'<%=index.intValue()%>');"><%--
				--%><option value="">Select <bean:write name="oceHelper" property="attributeName" filter="false" /></option><%--
				    
				--%><%
				     LegalValueSet dropdownList =  helper.getDropdownList(databean.getWhereClause()); 
				     if(dropdownList != null) {
		  			 for(int k = 0; k < dropdownList.getCount(); k++){
	 		  	%><%--
					--%><option value="<%=dropdownList.getValue(k)%>"><%=dropdownList.getDisplayValue(k)%></option><%--
				--%><%}}%><%--
			--%></select> <%--
			--%><input type="Button" name="Open<%=index.intValue()%>" value="..." onClick="openPopupWindow('<%=index.intValue()%>','<%=Escaper.urlEncode(databean.getWhereClause())%>');"><%--
		 --%></td><%--
    --%><logic:equal name="oceHelper" property="displayPathReport" value="true"><%--
	   	 --%><td width="10%" align="center" rowspan="2"><%--
		   --%><a href="#" onClick="openReportWindow('<%=databean.getLineId()%>');"><%-- 
            --%><input type="button" name="Report<%=index.intValue()%>" value="..."><%--
           --%></a><%--
		 --%></td><%--
	--%></logic:equal><%--
	   	 --%><td width="10%" align="center" rowspan="2"><%--
				--%><div align="center"><%--
       				--%><% if (helper != null && !OceUtil.FILTERED_ATTRIBUTES.contains(helper.getAttribute())) { %><%--
		              --%><input type="checkbox" name="clone<%=index.intValue()%>" value="checkbox"><%--
		            --%><% } else { %><%--
		              --%><input type="checkbox" name="clone<%=index.intValue()%>" disabled=true value="checkbox"><%--
					--%><% } %><%--
		    	--%></div><%--
		 --%></td><%--
	     --%><td width="11%" valign="bottom"><%--
			--%><div align="center"><font size="1">U</font><%-- 
		      --%><input type="checkbox"	name="update<%=index.intValue()%>" value="checkbox" onClick="IsOtherBoxChecked(<%=index.intValue()%>);" ><%-- 
		      --%><font size="1">I</font><%-- 
		      --%><input type="checkbox" name="inapp<%=index.intValue()%>" value="checkbox" onClick="IsOtherBoxChecked(<%=index.intValue()%>);"><%--
		    --%></div><%--
		 --%></td><%--
	   --%></tr><%--
	   --%><tr class="<%= ((index.intValue() % 2) == 0) ? "white" : "grey" %>"><%--
		 --%><td width="11%" valign="bottom"><%--
			--%><div align="center"><%--
			  --%><font size="1">H</font><%--
			  --%><input type="checkbox" name="hold<%=index.intValue()%>" value="checkbox"	onClick="IsOtherBoxChecked(<%=index.intValue()%>);"><%-- 
			  --%><font size="1">C</font><%-- 
			  --%><input type="checkbox" name="concept<%=index.intValue()%>" value="checkbox" onClick="IsOtherBoxChecked(<%=index.intValue()%>);"><%--
			--%></div><%--
	     --%></td><%--
	   --%></tr>
	</logic:iterate>
	</logic:equal>
	<logic:notEqual name="oceHelper" property="displayWarning" value="true">
	   <tr class="yellow"> 
          <td colspan="5"> 
            <div align="center">
              <font face="Arial, Helvetica, sans-serif" size="2">
                <b>Oops! No data found.</b>
              </font>
            </div>
         </td>
       </tr>
    </logic:notEqual>
    </table>
  </td>
 </tr>
</table>
</form>
</body>
</html>
