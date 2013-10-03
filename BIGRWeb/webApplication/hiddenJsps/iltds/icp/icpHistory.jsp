<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%> 
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%@ taglib uri="/tld/datetime"     prefix="dt"   %>
<%--
  Get the history list from the icpData bean if it exists, otherwise
  attempt to get the history from a property named "btxHistoryRecords"
  on the btxDetails bean.
  --%>
<logic:present name="icpData">
  <bean:define id="historyList" name="icpData" property="history" type="java.util.List" />
</logic:present>
<logic:notPresent name="history">
  <logic:present name="btxDetails">
    <bean:define id="historyList" name="btxDetails" property="btxHistoryRecords" type="java.util.List" />
  </logic:present>
</logic:notPresent>

<logic:present name="historyList">
  <%-- Retreive history bean from scope and define it as "historyList" --%>
  
  <%-- Define parameter that is size of history list --%>
  <bean:size id="historySize" name="historyList"/>
  <%
    String transactionsStr = ((historySize.intValue() == 1) ? "Transaction" : "Transactions");
  %>
  
  <table border="1" bordercolor="#336699" cellspacing="1" cellpadding="1" width="100%" class="foreground">
    <tr class="yellow"> 
      <td align="center" colspan="5">
        <b>
				<bigr:hasPrivilege priv="<%=SecurityInfo.PRIV_ICP_SUPERUSER%>">
          Transaction History: 
        </bigr:hasPrivilege>
				<bigr:notHasPrivilege priv="<%=SecurityInfo.PRIV_ICP_SUPERUSER%>">
          Transaction History At Your Institution: 
        </bigr:notHasPrivilege>
        <bean:write name="historySize"/> <%= transactionsStr %>
        </b>
      </td>
    </tr>
    <%-- Iterate over the history list --%>
    <logic:iterate id="historyBean" name="historyList" type="com.ardais.bigr.iltds.btx.BTXHistoryDisplayLine">
      <bigr:row oddStyleClass="white" evenStyleClass="yellow">
        <td align="right" valign="top">
          <bean:write name="historyBean" property="transactionId"/>
        </td>
        <td valign="top">
          <bean:write name="historyBean" property="BTXTypeDisplayName"/>
        </td>
        <td valign="top">
          <bean:write name="historyBean" property="userId"/>
        </td>
        <td valign="top">
          <dt:format pattern="MMM d, yyyy h:mm a">
            <dt:parse pattern="yyyy-MM-dd HH:mm:ss.SS">
              <bean:write name="historyBean" property="endTimestamp"/>
            </dt:parse>
          </dt:format>
        </td>
        <td valign="top">
          <bean:write name="historyBean" property="detailsAsHTML" filter="false"/>
        </td>
      </bigr:row>
    </logic:iterate>
  </table>
</logic:present>
