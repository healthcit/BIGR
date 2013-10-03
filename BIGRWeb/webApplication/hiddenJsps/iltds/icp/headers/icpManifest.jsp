<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ardais.bigr.util.Constants"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/datetime"     prefix="dt"   %>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="manifestDto" name="icpData" property="data"
  type="com.ardais.bigr.javabeans.ManifestDto" />
<bean:define id="fromAddress" name="manifestDto" property="shipFromAddress"
  type="com.ardais.bigr.iltds.assistants.Address" />
<bean:define id="toAddress" name="manifestDto" property="shipToAddress"
  type="com.ardais.bigr.iltds.assistants.Address" />
  
<%
  String manifestStatus = manifestDto.getShipmentStatus();
  String manifestStatusDisplay = "";
  if (com.ardais.bigr.iltds.helpers.FormLogic.MNFT_MFCREATED.equals(manifestStatus)) {
    manifestStatusDisplay = "Created, not yet packaged and shipped";
  }
  else if (com.ardais.bigr.iltds.helpers.FormLogic.MNFT_MFPACKAGED.equals(manifestStatus)) {
    manifestStatusDisplay = "Packaged, not yet shipped";
  }
  else if (com.ardais.bigr.iltds.helpers.FormLogic.MNFT_MFSHIPPED.equals(manifestStatus)) {
    manifestStatusDisplay = "Shipped, not yet received";
  }
  else if (com.ardais.bigr.iltds.helpers.FormLogic.MNFT_MFVERIFIED.equals(manifestStatus)) {
    manifestStatusDisplay = "Received, not yet stored";
  }
  else if (com.ardais.bigr.iltds.helpers.FormLogic.MNFT_MFRECEIVED.equals(manifestStatus)) {
    manifestStatusDisplay = "Received and stored";
  }
  else {
    manifestStatusDisplay = manifestStatus;
  }
%>

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td colspan="4" align="center"> 
              <b>Manifest: <bean:write name="icpData" property="id"/></b>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Status:</b>
            </td>
            <td>
              <%= manifestStatusDisplay %>
            </td>
            <td class="grey" align="right"> 
              <b>Tracking number:</b>
            </td>
            <td>
              <bean:write name="manifestDto" property="trackingNumber" ignore="true"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Created:</b>
            </td>
            <td>
              <logic:present name="manifestDto" property="createDate">
                <dt:format pattern="MM/dd/yyyy h:mm a" date="<%= manifestDto.getCreateDate() %>"/>
                by <bean:write name="manifestDto" property="createUserId" ignore="true"/>
              </logic:present>
            </td>
            <td class="grey" align="right"> 
              <b>Received:</b>
            </td>
            <td>
              <logic:present name="manifestDto" property="receiptVerifyDate">
                <dt:format pattern="MM/dd/yyyy h:mm a" date="<%= manifestDto.getReceiptVerifyDate() %>"/>
                by <bean:write name="manifestDto" property="receiptVerifyUserId" ignore="true"/>
              </logic:present>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Shipped:</b>
            </td>
            <td>
              <logic:present name="manifestDto" property="shipVerifyDate">
                <dt:format pattern="MM/dd/yyyy h:mm a" date="<%= manifestDto.getShipVerifyDate() %>"/>
                by <bean:write name="manifestDto" property="shipVerifyUserId" ignore="true"/>
              </logic:present>
            </td>
            <td class="grey" align="right"> 
              <b>Received Shipment Stored:</b>
            </td>
            <td>
              <logic:present name="manifestDto" property="receiptDate">
                <dt:format pattern="MM/dd/yyyy h:mm a" date="<%= manifestDto.getReceiptDate() %>"/>
                by <bean:write name="manifestDto" property="receiptUserId" ignore="true"/>
              </logic:present>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" colspan="2"> 
              <b>Ship From:</b>
              <bean:write name="fromAddress" property="addressAccountId" ignore="true"/><br/>
            </td>
            <td class="grey" colspan="2"> 
              <b>Ship To:</b>
			  <logic:equal name="toAddress" property="addressType" value="<%=com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED%>"> 
                <%=Constants.OUT_OF_NETWORK_DISPLAY%><br/>
			  </logic:equal>
			  <logic:notEqual name="toAddress" property="addressType" value="<%=com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED%>"> 
                <bean:write name="toAddress" property="addressAccountId" ignore="true"/><br/>
			  </logic:notEqual>
            </td>
          </tr>
          <tr class="white"> 
            <td nowrap colspan="2">
              <bean:write name="fromAddress" property="addressName" ignore="true"/><br/>
              <bean:write name="fromAddress" property="locationAddress1" ignore="true"/><br/>
              <bean:write name="fromAddress" property="locationAddress2" ignore="true"/><br/>
              <bean:write name="fromAddress" property="locationCity" ignore="true"/>, 
              <bean:write name="fromAddress" property="locationState" ignore="true"/> 
              <bean:write name="fromAddress" property="locationZip" ignore="true"/><br/>
              <bean:write name="fromAddress" property="country" ignore="true"/><br/>
            </td>
            <td nowrap colspan="2">
			  <logic:notEqual name="toAddress" property="addressType" value="<%=com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED%>"> 
                <bean:write name="toAddress" property="addressName" ignore="true"/><br/>
			  </logic:notEqual>
			  <logic:equal name="toAddress" property="addressType" value="<%=com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED%>"> 
                <bean:write name="toAddress" property="contactName" ignore="true"/><br/>
			  </logic:equal>
              <bean:write name="toAddress" property="locationAddress1" ignore="true"/><br/>
              <bean:write name="toAddress" property="locationAddress2" ignore="true"/><br/>
              <bean:write name="toAddress" property="locationCity" ignore="true"/>, 
              <bean:write name="toAddress" property="locationState" ignore="true"/> 
              <bean:write name="toAddress" property="locationZip" ignore="true"/><br/>
              <bean:write name="toAddress" property="country" ignore="true"/><br/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

