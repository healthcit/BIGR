<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.WebUtils" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>

<%
	SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
%>

<bean:define id="icpData" name="icpData" type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="boxLayout" name="icpData" property="data" type="com.ardais.bigr.javabeans.BoxLayoutExtendedDto" />

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
          <tr class="green"> 
            <td colspan="4" align="center"> 
              <b>Box Layout: <bean:write name="icpData" property="id"/></b>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right" valign="top"> 
              <b>Description:</b>
            </td>
            <td colspan="3" valign="top">
              <bean:write name="boxLayout" property="descriptionHtml" filter="false"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right" valign="top"> 
              <b>Account Usage:</b>
            </td>
            <td colspan="3" valign="top">
			        <div style="overflow-y: auto; height: 50px;">
                <%= boxLayout.getFilteredAccountUsageHtml(securityInfo) %>
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

