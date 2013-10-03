<%@ page language="java" import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<title>Other_Top</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<script type="text/javascript" src="<html:rewrite page="/js/calendar.js"/>"> </script>
<script language="JavaScript">
var Attrib=new Array();
myBanner = 'Other Code Editor';
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
function showOtherData() {
		missinginfo = "";
		var tableName = document.forms[0].tableName.value;
		var attribute = document.forms[0].attribute.value;
		var attributeName = document.forms[0].attribute.options[document.forms[0].attribute.selectedIndex].text;
		var status = "";
		var listOrder = "";
		for(var i=0;i<document.forms[0].status.length;i++) {
			if(document.forms[0].status[i].checked)	{
				status = document.forms[0].status[i].value;				
				break;
			}
		}
		for(var i = 0; i < document.forms[0].listOrder.length ; i++) {
			if(document.forms[0].listOrder[i].checked) {
				listOrder = document.forms[0].listOrder[i].value;				
				break;
			}
		}
		var startDate = document.forms[0].startDate.value;
		var endDate = document.forms[0].endDate.value;
		var oceUrl = '<html:rewrite page="/ddc/Dispatch?op=OceQuery"/>';
		parent.mainFrame.location=oceUrl + "&tableName=" + tableName+"&attribute="+attribute+"&status="+status+"&listOrder="+listOrder+"&attributeName="+attributeName + "&startDate=" + startDate + "&endDate=" + endDate;	
}
	<%
		Map TableColumns = (Map)session.getAttribute("TableColumns");
		List columns = null;
		Iterator iterator = null;
		if (TableColumns != null) {				
			iterator = TableColumns.keySet().iterator();
			String TableName="";
			while (iterator.hasNext()) {
			  TableName = (String)iterator.next();
	%>
		Attrib["<%=TableName%>"] = new Array();		  
	<%
			  columns = (List)TableColumns.get(TableName);
			  for (int i = 0; i < columns.size(); i++) {
			  
	%>			
			Attrib["<%=TableName%>"][<%=i%>] = "<%= columns.get(i) %>";
			
	<%	  } 
		}
	   }	
	%>
	function selectAttribute()
{
	var tableName=document.forms[0].tableName.value;
	for(i=document.forms[0].attribute.length;i>0;i--){
        document.forms[0].attribute.remove(i);    
    }
	if(tableName!="")
	{
		for(var i=0;i<Attrib[tableName].length;i++)
		{
			var str=Attrib[tableName][i];
			document.forms[0].attribute.options[i+1]=new Option(str.substr(3),str.substr(0,2));
		}
	}
}
	</script>
	</head>
	<%
  	response.setHeader("Cache-Control", "no-cache");
  	response.setHeader("Pragma", "no-cache");
  	response.setDateHeader("Expires", 0);
    %>
	<body bgcolor="#FFFFFF" text="#000000"
	      onLoad="if (parent.topFrame) parent.parent.topFrame.document.all.banner.innerHTML = myBanner;">
	<form name="OtherTop" method="post">
	
  <table class="background" cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
    <tr ><td>
				
        <table class="foreground-small" cellpadding="3" cellspacing="1" border="0" width="100%">
          <tr class="grey"> 
            <td width="10%"> 
              <div align="right"><b>Table Name</b></div>
            </td>
            <td width="40%"> 
              <select styleClass="smallSelectBox"  name="tableName" onChange="selectAttribute();">
                <option selected>Select Table Name</option>
                <%		
					if(TableColumns != null)  {
					  iterator = TableColumns.keySet().iterator();
					  while (iterator.hasNext()) {
			  			String TableName = (String)iterator.next();			
				%>
                <option value="<%=TableName%>"><%=TableName%></option>
                <%  }
                   }
                %>
              </select>
            </td>
            <td width="10%"><div align="right"><b>Attribute</b></div></td>
            <td width="40%"> 
              <select styleClass="smallSelectBox"  name="attribute" >
                <option selected>Select Attribute</option>
              </select>
            </td>
          </tr>
          <tr class="grey"> 
            <td width="10%"> 
              <div align="right"><b>Status</b></div>
            </td>
            <td width="40%">
	  		  <table class="foreground-small" cellspacing="0" cellpadding="2" border="0">
	            <tr>
	              <td nowrap style="border: none">
	                <div align="right">
			          <input type="radio" name="status" value="F">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
			          Fixed
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="right">
			          <input type="radio" name="status" value="O">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
			          Obsolete
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="right">
			          <input type="radio" name="status" value="A">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
			          All
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="right">
			          <input type="radio" name="status" value="I">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
			          Inapp
			        </div>
			      </td>
			    </tr>
	            <tr>
	              <td nowrap style="border: none">
	                <div align="right">
			          <input type="radio" name="status" value="N">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
			          New
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="right">
			          <input type="radio" name="status" value="H">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
			          Hold
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="right">
			          <input type="radio" name="status" value="C">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
			          Concept
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="right">
			          <input type="radio" name="status" value="NHC">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
			          Not Fixed
			        </div>
			      </td>
			    </tr>
			  </table>
            </td>
            <td width="10%"><div align="right"><b>List Order</b></div></td>
            <td width="40%"> 
	  		  <table class="foreground-small" cellspacing="0" cellpadding="2" border="0">
	  		    <tr>
	              <td nowrap style="border: none">
	                <div align="right">
                      <input type="radio" name="listOrder" value="Asc">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
              	      Oldest First 
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="right">
                      <input type="radio" name="listOrder" value="Dsc">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
                      Newest First 
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="right">
                      <input type="radio" name="listOrder" value="Alpha">
			        </div>
			      </td>
	              <td nowrap style="border: none">
	                <div align="left">
                      Alphabetic 
			        </div>
			      </td>
			    </tr>
			  </table>
            </td>
          </tr>
          <tr class="grey"> 
            <td width="10%"><div align="right"><b>Date Created</b></div></td>
            <td width="40%"> 
		 	  <input type="text" name="startDate" size="12" maxlength="10" readonly="true">
			  <span class="fakeLink" onclick="openCalendar('startDate')">Start Date</span>
			  &nbsp;&nbsp;&nbsp;&nbsp;
			  <a href="javascript:clearDateField('startDate')">Clear Start Date</a>
			  <br>
		 	  <input type="text" name="endDate" size="12" maxlength="10" readonly="true">
			  <span class="fakeLink" onclick="openCalendar('endDate')">End Date</span>
			  &nbsp;&nbsp;&nbsp;&nbsp;
			  <a href="javascript:clearDateField('endDate')">Clear End Date</a>
			</td>
            <td width="50%" colspan="2">&nbsp;</td>
          </tr>
          <tr class="grey"> 
            <td colspan="4"> 
              <div align="center"> 
                <input class="smallButton" type="reset" name="Reset" value="Reset">
                <input class="smallButton" type="button" name="Button" value="Search" onClick="showOtherData();">
                <input class="smallButton" type="button" name="Update" value="Update" onClick="javascript:parent.mainFrame.validateForm();" >
              </div>
            </td>
          </tr>
        </table>
	</td></tr></table>
	
	</form>
	
	</body>
	</html>
