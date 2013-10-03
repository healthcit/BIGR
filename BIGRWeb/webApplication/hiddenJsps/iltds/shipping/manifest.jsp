<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp" %>
<%@ page import="com.ardais.bigr.es.helpers.FormLogic" %>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
    com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<bean:define id="manifest"
  name="<%=com.ardais.bigr.web.taglib.BigrTagConstants.ACTION_FORM_KEY%>"
  type="com.ardais.bigr.iltds.web.form.ManifestForm"/>

<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">

<script language="JavaScript" src="<html:rewrite page="/js/common.js"/>"></script>
<script type="text/javascript">

function initPage() {
	if (parent.topFrame) {
  	// We're in the frameset, so update the banner.
	  parent.topFrame.document.all.banner.innerHTML = 'Shipping Manifest';
	}
	else {
  	// We're in a popup, so don't attempt to update the banner and add a close button.
		var closeButton = '<input type="button" name="btnClose" value="Close" onclick="onClickClose();" class="noprint">';
		document.all.buttons.innerHTML += closeButton;
	}
<logic:equal name="manifest" property="autoPrint" value="true">
  onClickPrint();
</logic:equal>
}

function onClickClose() {
  setActionButtonEnabling(false);
  window.close();
}

function onClickPrint() {
  <%-- The print button doesn't submit so we don't disable action buttons here.
    --%>
  window.print();
}
   
function setActionButtonEnabling(isEnabled) {
  var isDisabled = (! isEnabled);
  setInputEnabled(document.all, "btnPrint", isEnabled);
  setInputEnabled(document.all, "btnClose", isEnabled);
}
</script>
</head>
<body class="bigrPrintable" onload="initPage();">
<table width="640" class="background" cellpadding="0" cellspacing="0" border="0">
  <tr>
    <td>
      <table width="100%" class="foreground" cellpadding="3" cellspacing="1" border="0">
        <tr class="white"> 
          <td> 
            <div align="center"><br>
              <table class="background" cellpadding="0" cellspacing="0" border="0" width="90%">
                <tr><td>
                  <table width="100%" class="foreground" cellpadding="3" cellspacing="0" border="0">
                    <tr class="yellow"> 
						          <td colspan="2"> 
						            <div align="center"><font size="+3">Shipping Manifest</font></div>
						          </td>
		      			    </tr>		
						        <tr class="white"> 
		      			      <td align="left" valign="top">Produced By: 
										    <bean:write name="manifest" property="producedByName"/>
	                	  </td>
						          <td> 
		      			        <div align="right">Manifest Number: <bean:write name="manifest" property="manifestNumber"/></div>
						            <div align="right" class="barcode"><bean:write name="manifest" property="manifestBarcode" filter="false"/></div>
		      			      </td>
		            		</tr>
						      </table>
						    </td></tr>
						  </table>
              <br><br>
              <table width="90%" class="background" cellpadding="0" cellspacing="0" border="0">
                <tr><td>
	                <table class="foreground" width="100%" cellpadding="3" cellspacing="1" border="0">
	  	              <tr class="yellow"> 
		    	            <td><b>To:</b></td>
	      	            <td><b>From:</b></td>
		        	      </tr>
	          	      <tr class="white"> 
		            	    <td align="left" valign="top">
		            	    				   <logic:notEqual name="manifest" property="shipToAddress.addressType" value="<%=FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED%>"> 
												<bean:write name="manifest" property="shipToAddress.addressName"/><br>
											   </logic:notEqual>
		            	    				   <logic:equal name="manifest" property="shipToAddress.addressType" value="<%=FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED%>"> 
												<bean:write name="manifest" property="shipToAddress.contactName"/><br>
											   </logic:equal>
												<bean:write name="manifest" property="shipToAddress.locationAddress1"/><br>
												<bean:write name="manifest" property="shipToAddress.locationAddress2"/><br>
												<bean:write name="manifest" property="shipToAddress.locationCity"/>, 
												<bean:write name="manifest" property="shipToAddress.locationState"/>
												<bean:write name="manifest" property="shipToAddress.locationZip"/><br>
			                </td>
		  		            <td> 
												<bean:write name="manifest" property="shipFromAddress.addressName"/><br>
												<bean:write name="manifest" property="shipFromAddress.locationAddress1"/><br>
												<bean:write name="manifest" property="shipFromAddress.locationAddress2"/><br>
												<bean:write name="manifest" property="shipFromAddress.locationCity"/>, 
												<bean:write name="manifest" property="shipFromAddress.locationState"/>
												<bean:write name="manifest" property="shipFromAddress.locationZip"/><br>
	                  	</td>
	                  </tr>
              		</table>
              	</td></tr>
              </table>
            	<p>&nbsp;</p>
          	</div>

						<div align="center"> 
              <table width="80%" class="background" cellpadding="0" cellspacing="0" border="0">
                <tr><td>
                  <table class="background" width="100%" cellpadding="3" cellspacing="1" border="0">
										<tr class="green">
											<td colspan="2"><b> Handling Information</b></td>
										</tr>
                    <tr class="white"> 
		                  <td>
		                  	Package contains sensitive research materials.
						            Contents include diagnostic specimen packed in compliance with IATA packing instruction 650.
						            This product should only be handled by trained personnel wearing appropriate protective clothing.
										  </td>
		                </tr>
     			        </table>
     			      </td></tr>
     			    </table>
							<br>
              <table width="80%" class="background" cellpadding="0" cellspacing="0" border="0">
                <tr><td>
                	<table class="foreground" width="100%" cellpadding="3" cellspacing="1" border="0">
                  	<tr class="white"> 
                  		<td align="center">
                  			Total Number of Samples: <bean:write name="manifest" property="totalSamples"/>
                  		</td>
                		</tr>
              		</table>
              	</td></tr>
              </table>			
						</div>
            <br>

            <div align="center"> 
            <logic:present name="manifest" property="trackingNumber">
						  <table class="background" cellpadding="0" cellspacing="0" border="0">
                <tr><td>
                  <table class="foreground" cellpadding="3" cellspacing="1" border="0">
                    <tr class="white"> 
                      <td><b>Tracking Number:</b></td>
                      <td> 
                        <div align="center"><bean:write name="manifest" property="trackingNumber"/><br></div>
                        <div align="center" class="barcode"><bean:write name="manifest" property="trackingBarcode" filter="false"/></div>
                      </td>
                    </tr>
                  </table>
                </td></tr>
              </table>
            </logic:present>
            </div>
          </td>
        </tr>

				<tr class="white"><td>
          <div align="center">-------------------------------TEAR HERE-----------------------------------</div>
        </td></tr>

				<tr class="white"><td><br>
          <div align="center"> 
			  		<table class="background" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td> 
                  <table class="foreground" cellpadding="3" cellspacing="1" border="0">
                    <tr class="green"> 
                      <td colspan="7" align="center">
                      	<b><font size="4">Pick List</font></b>
                      </td>
                    </tr>
                    <tr class="yellow"> 
                      <td>Box ID</td>
											<td>Expected Samples</td>
                      <td>Room</td>
                      <td>Storage Unit</td>
                      <td>Drawer</td>
                      <td>Slot</td>
                      <td>Retrieved</td>
                    </tr>

									<logic:iterate name="manifest" property="boxes" id="box" indexId="boxIndex" type="com.ardais.bigr.javabeans.BoxDto">
                    <tr class="white"> 
                      <td><bean:write name="box" property="boxId"/></td>
                      <td><bean:write name="box" property="contentCount"/></td>
                      <td><bean:write name="box" property="room"/></td>
                      <td><bean:write name="box" property="unitName"/></td>
                      <td><bean:write name="box" property="drawer"/></td>
                      <td><bean:write name="box" property="slot"/></td>
                      <td><input type="checkbox" name="checkbox" value="checkbox"></td>
                    </tr>

									<logic:greaterThan name="box" property="contentCount" value="0">
                    <tr class="green"> 
                      <td colspan="7"> 
                        <div align="center"><b>Expected Contents of box: <%=box.getBoxId()%></b></div>
                      </td>
                    </tr>

                    <tr class="white"> 
                      <td colspan="7"> 
                    <bigr:box
                      boxLayoutName="box"
                      boxLayoutProperty="boxLayoutDto"
                      grouped="true"
                      boxIndex="<%=boxIndex.toString()%>"
                      boxFormName="manifest"
                      boxFormVar="box"
                      boxCellContentVar="cellContent"
                      sampleTypeVar="cellSampleType"
                      cellType="readonly"
                      printerFriendly="true"
                    />
                      </td>
                    </tr>
									</logic:greaterThan>
									</logic:iterate>
                  </table>
                </td>
              </tr>
            </table>
          </div>
          <br><br>
        
          <p align="center" id="buttons"> 
            <input type="button" name="btnPrint" value="Print" onclick="onClickPrint();" class="noprint">
          </p>
        </td></tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>