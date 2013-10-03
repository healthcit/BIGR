<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="repository" name="icpData" property="data"
  type="com.ardais.bigr.iltds.databeans.LogicalRepositoryExtendedData" />

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td colspan="6" align="center"> 
              <b>Inventory Group: <bean:write name="icpData" property="id"/></b>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Full name:</b>
            </td>
            <td colspan="5">
              <bean:write name="repository" property="fullName"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Short name:</b>
            </td>
            <td>
              <bean:write name="repository" property="shortName"/>
            </td>
<% if (ApiProperties.isEnabledLegacy()) { %>            
            <td class="grey" align="right"> 
              <b>BMS?</b>
            </td>
            <td>
              <bean:write name="repository" property="bmsYN"/>
            </td>
<% } %>
            <td class="grey" align="right"> 
              <b>Items:</b>
            </td>
            <td>
              <bean:write name="repository" property="itemCount"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

