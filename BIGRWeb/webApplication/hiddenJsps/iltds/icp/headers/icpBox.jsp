<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %> 
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic"%>
<%@ taglib uri="/tld/bigr"          prefix="bigr" %>
<%@ taglib uri="/tld/datetime"      prefix="dt"   %>
<%
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
%>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="boxBean" name="icpData" property="data"
  type="com.ardais.bigr.javabeans.BoxDto"/>

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td colspan="4" align="center"> 
              <b>Box Information: <bean:write name="boxBean" property="boxId"/></b>
            </td>
          </tr>
<logic:present name="icpBoxAttributesRestricted">
          <tr class="white"> 
            <td colspan="4" align="center"> 
              <b>This box is not currently in a storage unit at your location.</b>
            </td>
          </tr>
</logic:present>
<logic:notPresent name="icpBoxAttributesRestricted">
          <tr class="white"> 
            <td class="grey" width="25%" align="right"> 
              <b>Location:</b>
            </td>
            <td width="75%" colspan="3">
              <%= boxBean.getBoxLocationDisplay(securityInfo) %>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" width="25%" align="right"> 
              <b>Storage Type:</b>
            </td>
            <td width="75%" colspan="3">
              <bean:write name="boxBean" property="storageTypeDesc"/>
            </td>
          </tr>
</logic:notPresent>
        </table>
      </td>
    </tr>
  </table>
</div>

