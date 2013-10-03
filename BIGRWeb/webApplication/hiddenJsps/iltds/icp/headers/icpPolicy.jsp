<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.iltds.assistants.LogicalRepository" %>
<%@ page import="com.ardais.bigr.iltds.helpers.FormLogic" %>
<%@ page import="com.ardais.bigr.iltds.helpers.IltdsUtils" %>
<%@ page import="com.ardais.bigr.util.IcpUtils" %>
<%@ page import="com.ardais.bigr.util.WebUtils" %>

<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>

<bean:define id="icpData" name="icpData" type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="policy" name="icpData" property="data" type="com.ardais.bigr.iltds.databeans.PolicyExtendedData" />

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
          <tr class="green"> 
            <td colspan="4" align="center"> 
              <b>Policy: <bean:write name="icpData" property="id"/></b>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Account:</b>
            </td>
            <td colspan="3">
              <bean:write name="policy" property="accountName"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Policy Name:</b>
            </td>
            <td colspan="3">
              <bean:write name="policy" property="policyName"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Allocation Numerator:</b>
            </td>
            <td>
              <bean:write name="policy" property="allocationNumerator"/>
            </td>
            <td class="grey" align="right"> 
              <b>Default Inventory Group:</b>
            </td>
            <%
              String lrId = null;
              String prefixedLrId = null;
              String lrShortName = null;
              LogicalRepository lr = null;
              String link = null;
              
              lrId = policy.getDefaultLogicalReposId();
              lr = IltdsUtils.getLogicalRepositoryData(lrId);
              lrShortName = lr.getShortName();

              prefixedLrId = FormLogic.makePrefixedLogicalRepositoryId(lrId);
              link = IcpUtils.prepareLink(prefixedLrId, lrShortName, WebUtils.getSecurityInfo(request));
            %>
            <td>
              <%= link %>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Allocation Denominator:</b>
            </td>
            <td>
              <bean:write name="policy" property="allocationDenominator"/>
            </td>
            <td class="grey" align="right"> 
              <b>Restricted Inventory Group:</b>
            </td>
            <%
              link = ApiFunctions.EMPTY_STRING;
              lrId = policy.getRestrictedLogicalReposId();
              if (!ApiFunctions.isEmpty(lrId)) {
                lr = IltdsUtils.getLogicalRepositoryData(lrId);
                lrShortName = lr.getShortName();

                prefixedLrId = FormLogic.makePrefixedLogicalRepositoryId(lrId);
                link = IcpUtils.prepareLink(prefixedLrId, lrShortName, WebUtils.getSecurityInfo(request));
              }
            %>
            <td>
              <%= link %>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Allocation Format:</b>
            </td>
            <td colspan="3">
              <bean:write name="policy" property="allocationFormat"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Associated IRBs:</b>
            </td>
            <td colspan="3">
              <bean:write name="policy" property="associatedIrbs"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

