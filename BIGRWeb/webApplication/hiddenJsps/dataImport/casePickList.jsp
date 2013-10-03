<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.Escaper"%>
<%@ page import="com.ardais.bigr.iltds.helpers.DateHelper" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="java.util.Date" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<bean:define name="pickListForm" id="myForm" type="com.ardais.bigr.dataImport.web.form.PickListForm"/>

<%
	String banner = "";
	String caseAction = request.getParameter("caseAction");
		if (caseAction.equalsIgnoreCase("pullCase")) {
			banner = "Case Pull Report";
		}
		else if (caseAction.equalsIgnoreCase("revokeCase")) {
			banner = "Consent Revocation Report";
		}
%>
<html>
<head>
<title><%=banner%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
<!--
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}
myBanner = 'Case Pull Report';

function initPage() {
  if (parent.topFrame) parent.topFrame.document.all.banner.innerHTML = '<%=banner%>';
}
//-->
</script><link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
</head>
<script>
<!--

//--!>
</script>
<body class="bigrPrintable" onLoad="initPage();">

      <div align="center">
  <table class="background" cellpadding="0" cellspacing="0" border="0">
    <tr><td>
        <table class="foreground" cellpadding="3" cellspacing="1" border="0">
          <tr class="white"> 
            <td> 
              <div align="center"><font size="+1"><b><%=banner%></b></font></div>
            </td>
          </tr>
          <tr class="white"> 
            <td> 
              <div align="center"> 
                <table class="background" cellpadding="0" cellspacing="0" border="0">
                  <tr> 
                    <td> 
                      <table border="0" cellspacing="1" cellpadding="3" class="foreground">
                        <tr class="yellow"> 
                          <td colspan="2"> 
                            <div align="center"><b>Report Overview</b></div>
                          </td>
                        </tr>
                        <tr class="white"> 
                          <td class="grey"> 
                            <div align="left"><b>Case ID:</b></div>
                          </td>
                          <td > 
                            <div align="left"><bean:write name="pickListForm" property="consentId"/></div>
                          </td>
                        </tr>
                        <tr class="white"> 
                          <td  class="grey"> 
                            <div align="left"><b>Produced By:</b></div>
                          </td>
                          <td > 
                            <div align="left"><bean:write name="pickListForm" property="reportGeneratedByName"/></div>
                          </td>
                        </tr>
                        <tr class="white"> 
                          <td class="grey"> 
                            <div align="left"><b>Report Generated On:</b></div>
                          </td>
                          <td> 
                            <div align="left"><%= (new DateHelper(new Date(System.currentTimeMillis()))).getFormattedDate() %></div>
                          </td>
                        </tr>
<%
		if (caseAction.equalsIgnoreCase("pullCase")) {
%>
                        <logic:present name="pickListForm" property="consentData.pullRequestedByName">
                          <tr class="white"> 
                            <td class="grey"> 
                              <div align="left"><b>Requested By:</b></div>
                            </td>
                            <td> 
                              <div align="left"><bean:write name="pickListForm" property="consentData.pullRequestedByName"/></div>
                            </td>
                          </tr>
                        </logic:present>
<%
		}
		else if (caseAction.equalsIgnoreCase("revokeCase")) {
%>
                          <tr class="white"> 
                            <td class="grey"> 
                              <div align="left"><b>Requested By:</b></div>
                            </td>
                            <td> 
                              <div align="left"><bean:write name="pickListForm" property="revokeStaffName"/></div>
                            </td>
                          </tr>
<%
}

		if (caseAction.equalsIgnoreCase("pullCase")) {
%>
                        <logic:present name="pickListForm" property="consentData.pullComment">
                          <tr class="white"> 
                            <td class="grey"> 
                              <div align="left"><b>Pull Comment:</b></div>
                            </td>
                            <td>
                              <bean:define id="pullComment" name="pickListForm" property="consentData.pullComment" type="java.lang.String"/>
                              <div align="left"><%=Escaper.htmlEscapeAndPreserveWhitespace(pullComment)%></div>
                            </td>
                          </tr>
                        </logic:present>
<%
		}
%>
                      </table>
                    </td>
                  </tr>
                </table>
                <br>
                <table class="background" cellpadding="0" cellspacing="0" border="0">
                  <tr> 
                    <td> 
                      <table border="0" cellspacing="1" cellpadding="4" class="foreground">
                        <logic:empty name="pickListForm" property="samplesInRepository">
                          <logic:empty name="pickListForm" property="samplesInTransit">
                            <logic:empty name="pickListForm" property="samplesCheckedOut">
                          	  <tr class="green"> 
                            	<td colspan="9"> 
                              	  <div align="center">
                                	<b>No samples are associated with this Case.</b>
                              	  </div>
                            	</td>
                          	  </tr>
                            </logic:empty>
                          </logic:empty>
                        </logic:empty>
                        <% 
                        	//iterate over the samples in the repository first,
                        	//displaying the samples at each location.
                        %>
                        <logic:notEmpty name="pickListForm" property="samplesInRepository">
                          <logic:notEmpty name="pickListForm" property="locations">
							<logic:iterate name="pickListForm" property="locations"
								id="location" indexId="locationIndex"
								type="com.ardais.bigr.javabeans.LocationData">
						      <tr class="white">
							    <td colspan="9">&nbsp;</td>
						      </tr> 
                              <tr class="green"> 
                                <td colspan="9"> 
                                  <div align="left">
                                    <b>Samples at <bean:write name="location" property="name"/>:&nbsp;<bean:write name="location" property="addressId"/></b>
                                  </div>
                          		</td>
                        	  </tr>
                        	  <tr class="yellow"> 
                                <td> 
                                  <div align="center"><b>Sample ID:</b></div>
                          		</td>
                          	    <td> 
                            	  <div align="center"><b>Box ID:</b></div>
                          	    </td>
                          	    <td> 
                            	  <div align="center"><b>Room:</b></div>
                          	    </td>
                                <td> 
                                  <div align="center"><b>Storage Unit:</b></div>
                                </td>
                                <td> 
                                  <div align="center"><b>Drawer:</b></div>
                                </td>
                                <td> 
                                  <div align="center"><b>Slot:</b></div>
                                </td>
                                <td> 
                                  <div align="center"><b>Cell Ref:</b></div>
                                </td>
                                <td> 
                                  <div align="center"><b>Retrieved:</b></div>
                                </td>
                                <td> 
                                  <div align="center"><b>Checked Out:</b></div>
                                </td>
                              </tr>
							  <%
								//var to determine row color.  Cannot use sampleIndex, as not
								//every row is going to be displayed in this section.  For example
								//if the 1st and 3rd samples in the list are at this location, then
								//the sampleIndex values will be 1 and 3 so both rows will be grey.
								int count = 0;
							  %>
							  <logic:iterate name="pickListForm" property="samplesInRepository"
								id="sample" indexId="sampleIndex"
								type="com.ardais.bigr.javabeans.SampleData">
								<logic:present name="sample" property="storageLocation">
								  <logic:equal name="sample" property="storageLocation.locationAddressID" value="<%=location.getAddressId() %>">
                        			<tr class="<%= ((count % 2) == 0) ? "white" : "grey" %>">
                        			  <%
                        			  	count = count + 1;
                        			  %>
                          			  <td>
                            			<div align="center"><bean:write name="sample" property="sampleId"/></div>
                          			  </td>
                          			  <td> 
                            			<div align="center"><bean:write name="sample" property="boxBarcodeId"/></div>
                          			  </td>
                          			  <td> 
                            			<div align="center"><bean:write name="sample" property="storageLocation.roomID"/></div>
                          			  </td>
                          			  <td> 
                            			<div align="center"><bean:write name="sample" property="storageLocation.unitName"/></div>
                          			  </td>
                          			  <td> 
                            			<div align="center"><bean:write name="sample" property="storageLocation.drawerID"/></div>
                          			  </td>
                          			  <td> 
                            			<div align="center"><bean:write name="sample" property="storageLocation.slotID"/></div>
                          			  </td>
                          			  <td> 
                            			<div align="center"><bean:write name="sample" property="location"/></div>
                          			  </td>
                          			  <td> 
                            			<div align="center"> 
                              			  <input type="checkbox" name="checkbox" value="checkbox">
                            			</div>
                          			  </td>
                          			  <td> 
                            			<div align="center"> 
                              			  <input type="checkbox" name="checkbox" value="checkbox">
                            			</div>
                          			  </td>
                        			</tr>
								  </logic:equal>
								</logic:present>
							  </logic:iterate>
							</logic:iterate>
                          </logic:notEmpty>
                        </logic:notEmpty>
                        <% 
                        	//iterate over the samples in transit.
                        %>
                        <logic:notEmpty name="pickListForm" property="samplesInTransit">
                          <tr class="white">
							<td colspan="9">&nbsp;</td>
						  </tr> 
						  <tr class="green"> 
                            <td colspan="9"> 
                              <div align="left"><b>Samples in Transit</b></div>
                            </td>
                          </tr>
                          <tr class="yellow"> 
                            <td> 
                              <div align="center"><b>Sample ID:</b></div>
                            </td>
                            <td> 
                              <div align="center"><b>Box ID:</b></div>
                            </td>
                            <td colspan="5"> 
                              <div align="center"><b>Manifest ID:</b></div>
                            </td>
                            <td> 
                              <div align="center"><b>Retrieved:</b></div>
                            </td>
                            <td> 
                              <div align="center"><b>Checked Out:</b></div>
                            </td>
                          </tr>
						  <logic:iterate name="pickListForm" property="samplesInTransit"
							id="sample" indexId="sampleIndex"
							type="com.ardais.bigr.javabeans.SampleData">
                			<tr class="<%= ((sampleIndex.intValue() % 2) == 0) ? "white" : "grey" %>"> 
                  			  <td>
                    			<div align="center"><bean:write name="sample" property="sampleId"/></div>
                  			  </td>
                  			  <td> 
                    			<div align="center"><bean:write name="sample" property="boxBarcodeId"/></div>
                  			  </td>
                  			  <td colspan="5"> 
                    			<div align="center"><bean:write name="sample" property="manifest.manifestNumber"/></div>
                  			  </td>
	                          <td> 
	                            <div align="center"> 
	                              <input type="checkbox" name="checkbox" value="checkbox">
	                            </div>
	                          </td>
	                          <td> 
	                            <div align="center"> 
	                              <input type="checkbox" name="checkbox" value="checkbox">
	                            </div>
	                          </td>
	                        </tr>
						  </logic:iterate>
                        </logic:notEmpty>
                        <% 
                        	//iterate over the samples checked out.
                        %>
                        <logic:notEmpty name="pickListForm" property="samplesCheckedOut">
                          <tr class="white">
							<td colspan="9">&nbsp;</td>
						  </tr> 
                          <tr class="green"> 
                            <td colspan="9"> 
                              <div align="left"><b>Samples Not In the Repository (checked out, never annotated, etc.)</b></div>
                            </td>
                          </tr>
                          <tr class="yellow"> 
                            <td> 
                              <div align="center"><b>Sample ID:</b></div>
                            </td>
                            <td> 
                              <div align="center"><b>Date:</b></div>
                            </td>
                            <td colspan="5"> 
                              <div align="center"><b>Status:</b></div>
                            </td>
                            <td> 
                              <div align="center"><b>Retrieved:</b></div>
                            </td>
                            <td> 
                              <div align="center"><b>Checked Out:</b></div>
                            </td>
                          </tr>
						  <logic:iterate name="pickListForm" property="samplesCheckedOut"
							id="sample" indexId="sampleIndex"
							type="com.ardais.bigr.javabeans.SampleData">
                			<tr class="<%= ((sampleIndex.intValue() % 2) == 0) ? "white" : "grey" %>"> 
                  			  <td>
                    			<div align="center"><bean:write name="sample" property="sampleId"/></div>
                  			  </td>
                  			  <td> 
                    			<div align="center"><%=(sample.getInvStatusDate() == null) ? "&nbsp;" : (new DateHelper(sample.getInvStatusDate())).getFormattedDate() %></div>
                  			  </td>
                  			  <td colspan="5"> 
                    			<div align="center"><%= FormLogic.lookupCheckOutStatusOrReason(sample.getInvStatus()) %></div>
                  			  </td>
                              <td> 
                                <div align="center"> 
                                  <input type="checkbox" name="checkbox" value="checkbox">
                                </div>
                              </td>
                              <td> 
                                <div align="center"> 
                                  <input type="checkbox" name="checkbox" value="checkbox">
                                </div>
                              </td>
                            </tr>
                          </logic:iterate>
                        </logic:notEmpty>
                      </table>
                    </td>
                  </tr>
                </table>
                <br>
                <input type="button" name="Button" value="Print" onClick="MM_callJS('window.print();')" class="noprint">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" name="Button" value="Close" onClick='window.close();' class="noprint">
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
