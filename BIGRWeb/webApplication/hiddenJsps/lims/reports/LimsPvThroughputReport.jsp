<%@ page import="java.util.*" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>

<html>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<body class="bigr">
<form name="menu" method="post" action="<html:rewrite page="/lims/AdminServlet"/>">
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
            <tr class="yellow"> 
              <td colspan="2" > 
                <div align="center">
                  <b>PV Throughput Report</b>    <bean:write name="limsPvThroughputReportForm"  property="fromDate"/> --
                  <bean:write name="limsPvThroughputReportForm"  property="toDate"/>                
                  <BR><b>Date: </b><%= Calendar.getInstance().getTime() %>
                  <BR><b>Created by: </b><%= (String)session.getAttribute("user") %> 
                </div>
              </td>
            </tr>
            <tr class="green">               
              <td> 
                <div align="center">
                  <b>User Name</b>
                </div>
              </td>            
              
              <td> 
                <div align="center">
                  <b>Total Processed</b>
                </div>
              </td>
            </tr> 
            <logic:present name="reportDetails">
            <logic:iterate name="reportDetails" property="iterator" id="legalValue">           
            <tr class="white">               
              <td> 
                <bean:write name="legalValue" property="value" />
              </td>              
              <td align="center"> 
                <bean:write name="legalValue" property="displayValue" />
              </td>
            </tr>     
            </logic:iterate>
            <logic:equal name="reportDetails" property="count" value="0">            
            <tr class="white">               
              <td> 
                <bean:write name="limsPvThroughputReportForm"  property="userName"/>
              </td>              
              <td align="center"> 
                0
              </td>
            </tr>    
            </logic:equal>
	        </logic:present> 
            <tr class="white"> 
              <td colspan="2"> 
                <div align="center"> 
                  <input type="Button" value="Print" onClick="window.print();">                  
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
