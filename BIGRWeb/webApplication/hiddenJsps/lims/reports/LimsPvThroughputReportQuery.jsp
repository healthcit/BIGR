<%@ page import="java.sql.*" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<html>
<head>
<script language="JavaScript" src='<html:rewrite page="/js/calendar.js"/>'></script>
<SCRIPT LANGUAGE="JavaScript">

function MM_callJS(jsStr) {

  return eval(jsStr)
}

mybanner = 'PV Throughput Report';

</script>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<body class="bigr"
      onLoad="if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML=mybanner">
<div align="center"> 
</div>
<html:form  action="/lims/limsPvThroughputReport">
  <div align="center"> 
    <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table class="foreground" cellpadding="3" cellspacing="1" border="0"> 
          <logic:present name="org.apache.struts.action.ERROR">
		   <tr class="yellow"> 
		     <td colspan="2"> 
		       <div align="center">
		         <font color="#FF0000"><b><html:errors/></b></font>
		       </div>
		     </td>
		    </tr>
		   </logic:present>          
            <tr class="white"> 
              <td class="grey"> 
                <div align="right">User Name:</div>
              </td>
              <td>
              <html:select name="limsPvThroughputReportForm"  property="userName">
				 <logic:present name="limsPvThroughputReportForm" property="pathologistList">
				   <html:options name="limsPvThroughputReportForm" property="pathologistList" />
				 </logic:present>	
		   	  </html:select>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right">From Date:</div>
              </td>
              <td> 
                <html:text name="limsPvThroughputReportForm" property="fromDate"  size="12" maxlength="10" readonly="true"/>                
                <span class="fakeLink" onclick="openCalendar('fromDate')">Select Date</span>
              	<a href="javascript:clearDateField('fromDate')">Clear Date</a>
              </td>
            </tr>
            <tr class="white"> 
              <td class="grey"> 
                <div align="right">To Date:</div>
              </td>
              <td> 
                <html:text name="limsPvThroughputReportForm" property="toDate"  size="12" maxlength="10" readonly="true"/>
                <span class="fakeLink" onclick="openCalendar('toDate')">Select Date</span>                
              	<a href="javascript:clearDateField('toDate')">Clear Date</a>
              </td>
            </tr>
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="submit" value="Get Report">
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>   
</html:form>
</body>
</html>
