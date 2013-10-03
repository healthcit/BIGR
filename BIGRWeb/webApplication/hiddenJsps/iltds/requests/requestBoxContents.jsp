<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.iltds.web.form.RequestBoxForm" %>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Request Box Contents</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script language="JavaScript">
<!--
var myBanner = 'Request Box Contents';

function onClickPrint() {
  <%-- The print button doesn't submit so we don't disable action buttons here.
    --%>
  window.print();
}
//-->
</script>
</head>

<body class="bigr">
<%
  // DIV for errors
%>
  <div id="errorDiv" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
      <logic:present name="org.apache.struts.action.ERROR">
        <tr class="yellow"> 
	      <td> 
	        <div align="center">
	          <font color="#FF0000"><b><html:errors/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
      <logic:present name="com.ardais.BTX_ACTION_MESSAGES">
	    <tr class="yellow"> 
	      <td> 
	        <div align="center">
	          <font color="#FF0000"><b><bigr:btxActionMessages/></b></font>
	        </div>
	      </td>
	    </tr>
	  </logic:present>
	</table>
  </div>
  <p>
  <%
  	//only display the box information if the request has any boxes associated
  	//with it.
  %>
  <logic:present name="requestDtoForm" property="requestBoxFormsAsList">
    <bean:define id="boxes" name="requestDtoForm" property="requestBoxFormsAsList" type="java.util.List"/>
    <%
  		int boxCount = boxes.size();
    %>
    <div align="center">
	  <logic:iterate name="requestDtoForm" property="requestBoxFormsAsList" id="box" indexId="boxIndex" type="com.ardais.bigr.iltds.web.form.RequestBoxForm">
        <table border="0" cellspacing="1" cellpadding="3" align="center" class="foreground">
          <tr class="white"> 
            <td> 
              <table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
                <tr> 
                  <td> 
                    <table border="0" cellspacing="1" cellpadding="3" class="foreground" width="382">
			 	      <tr class="white"> 
			            <td class="yellow" colspan="2" align="center"> 
			              <b>Request <bean:write name="requestDtoForm" property="requestId"/></b>
			            </td>
			          </tr>
				      <tr class="white"> 
			            <td class="grey" > 
			              <div align="right">
			                <b>Request Type:</b>
			              </div>
			            </td>
			            <td> 
			              <bean:write name="requestDtoForm" property="requestType"/>
			            </td>
			          </tr>
					  <tr class="white"> 
			            <td class="grey" > 
			              <div align="right">
			                <b>Submission Date:</b>
			              </div>
			            </td>
			            <td> 
			              <bean:write name="requestDtoForm" property="createDate"/>
			            </td>
			          </tr>
					  <tr class="white"> 
			            <td class="grey" > 
			              <div align="right">
			                <b>Submitted By:</b>
			              </div>
			            </td>
			            <td> 
			              <bean:write name="requestDtoForm" property="requesterName"/>
			            </td>
			          </tr>
                    </table>
                  </td>
                </tr>
              </table>
              <p>
              <div align="center">
			  <table class="background" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td> 
                    <table class="foreground" cellpadding="3" cellspacing="1" border="0">
                      <tr class="green" align="center"> 
      					<% String prop = "requestBoxForm[" + boxIndex + "].boxId";%>
      					<td>
        				  <b><bean:write name="requestDtoForm" property="<%=prop%>"/>&nbsp;(Box <%=boxIndex.intValue()+1%> of <%=boxCount%>)</b>
      					</td>
                      </tr>
                      <%
                        if (box.isShipped()) {
                      %>
                      <tr class="white" align="center"> 
                        <td>
                           This box has already been shipped.
                        </td>
                      </tr>
                      <%
                        }
					  %>

			    <tr class="white"> 
			      <td colspan="2"> 
                      <bean:define id="requestBoxForm" name="requestDtoForm" property='<%="requestBoxForm[" + boxIndex + "]"%>' type="com.ardais.bigr.iltds.web.form.RequestBoxForm"/>
                      <bigr:box
                        boxLayoutName="requestBoxForm"
                        boxLayoutProperty="boxLayoutDto"
                        grouped="true"
                        boxIndex="<%=boxIndex.toString()%>"
                        boxFormName="requestDtoForm"
                        boxFormVar="requestBoxForm"
                        boxCellContentVar="cellContent"
		                    sampleAliasVar="cellSampleAlias"
                        cellType="readonly"
                        printerFriendly="true"
                      />
			      </td>
			    </tr>
                    </table>
                  </td>
                </tr>
              </table>
              </div>
            </td> 
          </tr>
        </table>
        <br><br><br><br><br><br><br>
        <% 
          //if this isn't the last box, insert a page break so we get one box
          //per page when the user clicks the print button
          if ((boxIndex.intValue() + 1) < boxCount) {
        %>
          <div style="page-break-before: always"></div>
        <%
          }
        %>
      </logic:iterate>
      <table border="0" cellspacing="1" cellpadding="3" class="foreground">
        <tr class="white" > 
          <td colspan="15" align="center"> 
            <input type="button" name="Button" value="Print" onclick="onClickPrint();" class="noprint">
            &nbsp;&nbsp;&nbsp;
			<input type="button" value="Close" onclick="window.close();" class="noprint">
          </td>
        </tr>
  	  </table>
    </div>
  </logic:present>
  <logic:notPresent name="requestDtoForm" property="requestBoxFormsAsList">
    <div align="center">
      There are no boxes associated with request <bean:write name="requestDtoForm" property="requestId"/>.
      <p>
	  <input type="button" value="Close" onclick="window.close();" class="noprint">
    </div>
  </logic:notPresent>
</body>
</html>
