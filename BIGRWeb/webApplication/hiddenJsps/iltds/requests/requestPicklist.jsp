<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions"%>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic"%>
<%@ page import="com.ardais.bigr.iltds.web.form.RequestItemForm" %>
<%@ page import="com.ardais.bigr.iltds.helpers.RequestType" %>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<bean:define id="myForm" name="requestDtoForm" type="com.ardais.bigr.iltds.web.form.RequestForm"/>
<%
	String requestType = myForm.getRequestType();
	boolean showAdditionalColumns = !requestType.equalsIgnoreCase(RequestType.RESEARCH.toString()) &&
									!requestType.equalsIgnoreCase(RequestType.TRANSFER.toString());
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Request Picklist</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript" src="<html:rewrite page="/js/common.js"/>"> </script>
<script language="JavaScript">
<!--
var myBanner = 'Request PickList';

function onClickPrint() {
  setActionButtonEnabling(false);
  window.print();
  setActionButtonEnabling(true);
}

function onFormSubmit() {
  setActionButtonEnabling(false);
  return true;
}

function setActionButtonEnabling(isEnabled) {
  var f = document.forms[0];
  setInputEnabled(f,'submit',isEnabled);
  setInputEnabled(f,'print',isEnabled);
  setInputEnabled(f,'close',isEnabled);
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
  	//only display the request information if the request could be found.
  	//To tell if the request was found, see if there are any items.
  %>
  <logic:present name="requestDtoForm" property="requestItemFormsAsList">
    <bean:define id="items" name="requestDtoForm" property="requestItemFormsAsList" type="java.util.List"/>
    <html:form method="POST"
  	  action="/iltds/printRequestPickList"
  	  onsubmit="return(onFormSubmit());">
	  <html:hidden property="requestId"/>
	  <html:hidden property="itemDetailLevel"/>
	  <html:hidden property="boxDetailLevel"/>
	  <html:hidden property="getPickListInfo"/>
    <table border="0" cellspacing="0" cellpadding="0" align="center" class="background">
      <tr> 
        <td> 
          <table border="0" cellspacing="1" cellpadding="3" align="center" class="foreground">
            <tr class="white"> 
              <td > 
                <table border="0" cellspacing="0" cellpadding="0" class="background" align="center">
                  <tr> 
                    <td> 
                      <table border="0" cellspacing="1" cellpadding="3" class="foreground" width="382">
                        <tr class="yellow"> 
                          <td colspan="2"> 
                            <div align="center">
                              <font size="4">
                                Pick List for <bean:write name="requestDtoForm" property="requestType"/> Request <bean:write name="requestDtoForm" property="requestId"/>
							  </font>
							</div>
                      	  </td>
                    	</tr>
	                    <tr class="white"> 
						  <td class="grey">
						    <b>Request Submitted By: </b>
						  </td>
	                      <td>
	                        <bean:write name="requestDtoForm" property="requesterName"/>
	                      </td>
	                    </tr>
	                    <tr class="white"> 
						  <td class="grey">
						    <b>Number of items: </b>
						  </td>
	                      <td>
	                        <bean:size name="requestDtoForm" property="requestItemFormsAsList" id="itemCount"/>
	                        <bean:write name="itemCount"/>&nbsp;&nbsp;&nbsp;
	                      </td>
	                    </tr>
                      </table>
                	</td>
              	  </tr>
            	</table>          
		        <br>
			    <table border="0" cellspacing="1" cellpadding="3" align="center" class="foreground">
			      <tr class="white"> 
    			    <td align="right">
      				  Sort samples by:
		            </td>
    				<td align="left">
      				  <bigr:selectList styleClass="smallSelectBox" 
		                name="requestDtoForm" 
		                property="itemSort"
		                legalValueSetProperty="itemSortOptions"/>
		            </td>
		            <td>
		              &nbsp;&nbsp;&nbsp;&nbsp;
		              <input class="smallButton" type="submit" name="submit" value="Sort"> 
		            </td>
				  </tr>
				</table>
            	<br>
	            <table border="0" cellspacing="0" cellpadding="0" class="background">
	              <tr> 
	                <td > 
	                  <table border="0" cellspacing="1" cellpadding="3" class="foreground">
	                    <div align="left"> 
	                      <tr class="yellow">
	                        <td > 
	                          <b>Box ID</b>
	                        </td>
	                        <td>
	                          <b>Room</b>
	                        </td>
	                        <td>
	                          <b>Storage<br>Unit</b>
	                        </td>
	                        <td> 
	                          <b>Drawer</b>
	                        </td>
	                        <td > 
	                          <b>Slot</b>
	                        </td>
	                        <td> 
	                          <b>Cell<br>Ref</b>
	                        </td>
	                        <td> 
	                          <b>Sample ID</b>
	                        </td>
	                        <td> 
	                          <b>Sample Alias</b>
	                        </td>
	                        <td> 
	                          <b>Case ID</b> 
	                        </td>
	                        <td> 
	                          <b>Case Alias</b> 
	                        </td>
	                        <% 
	                        	if (showAdditionalColumns) {
	                        %>
	                        	<td> 
	                        	  <b>ASM<br>Pos</b>
	                        	</td>
	                        <%
	                        	}
	                        %>
						    <td> 
	                          <b>Sample<br>Type</b> 
	                        </td>
	                        <% 
	                        	if (showAdditionalColumns) {
	                        %>
						    	<td> 
	                          	  <b>App</b>
	                        	</td>
	                            <bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
	                              <td> 
	                                <b>Last two<br>slides (QCed?)</b>
	                              </td>
						          <td> 
	                                <b>DI</b>
	                              </td>
						          <td> 
	                                <b>BMS</b>
	                              </td>
							    </bigr:isInRole>						    
							    <td> 
	                              <b>Format<br>Detail</b>
	                            </td>
	                        <%
	                        	}
	                        %>
	                      </tr>
						</div>
					    <% 
					      String rowClass = "white"; 
					    %>
		  				<logic:iterate name="requestDtoForm" property="requestItemFormsAsList" id="item" type="com.ardais.bigr.iltds.web.form.RequestItemForm">
		                  <div align="center">
					  	    <tr class="<%=rowClass%>">
						  	  <%
						  	    if (rowClass.equals("white")) {
						  	  	  rowClass = "grey";
						  	    }
						  	    else {
						  	   	  rowClass = "white";
						  	    }
						  	  %>
		                      <td> 
		                        <bean:write name="item" property="boxId"/>
		                      </td>
		                      <td>
		                        <bean:write name="item" property="locationRoomId"/> 
		                      </td>
		                      <td> 
		                        <bean:write name="item" property="locationUnitName"/> 
		                      </td>
		                      <td> 
		                        <bean:write name="item" property="locationDrawerId"/> 
		                      </td>
		                      <td> 
		                        <bean:write name="item" property="locationSlotId"/> 
		                      </td>
		                      <td> 
		                        <bean:write name="item" property="translatedCellRef"/> 
		                      </td>
		                      <td> 
		                        <bean:write name="item" property="itemId"/>
		                      </td>
		                      <td> 
		                        <bean:write name="item" property="itemAlias"/>
		                      </td>
		                      <td> 
		                        <bean:write name="item" property="caseId"/>
		                      </td>
		                      <td> 
		                        <bean:write name="item" property="caseAlias"/>
		                      </td>
	                          <% 
	                        	  if (showAdditionalColumns) {
	                          %>
		                          <td> 
		                            <bean:write name="item" property="asmPosition"/>
		                          </td>
		                      <%
		                      	  }
		                      %>
		                      <td> 
		                        <bean:write name="item" property="sampleType"/>
		                      </td>
	                          <% 
	                        	  if (showAdditionalColumns) {
	                          %>
		                          <td> 
		                            <bean:write name="item" property="appearance"/>
		                          </td>
	                              <bigr:isInRole role1='<%=com.ardais.bigr.security.SecurityInfo.ROLE_SYSTEM_OWNER%>'>
		                            <td>
		                              <bean:write name="item" property="mostRecentSlides"/>
		                            </td>
		                            <td> 
		                              <bean:write name="item" property="donorInstitution"/>
		                            </td>
		                            <td> 
		                              <bean:write name="item" property="bms"/>
		                            </td>
								  </bigr:isInRole>						    
		                          <td>
		                            <bean:write name="item" property="formatDetail"/>
		                          </td>
		                      <%
		                      	  }
		                      %>
                    		  </tr>
		                    </div>
                    	</logic:iterate>
	                    <tr class="white" > 
	                      <td colspan="15" align="center"> 
	                        <input type="button" name="print" value="Print" onclick="onClickPrint();" class="noprint">
	                        &nbsp;&nbsp;&nbsp;
    						<input type="button" name="close" value="Close" onclick="window.close();" class="noprint">
	                      </td>
	                    </tr>
                  	  </table>
                	</td>
              	  </tr>
                </table>
              </td>
        	</tr>
      	  </table>
    	</td>
  	  </tr>
	</table>
	</html:form>
  </logic:present>
  <logic:notPresent name="requestDtoForm" property="requestItemFormsAsList">
    <div align="center">
      Request <bean:write name="requestDtoForm" property="requestId"/> could not be found.
      <p>
	  <input type="button" name="close" value="Close" onclick="window.close();" class="noprint">
    </div>
  </logic:notPresent>
</body>
</html>
