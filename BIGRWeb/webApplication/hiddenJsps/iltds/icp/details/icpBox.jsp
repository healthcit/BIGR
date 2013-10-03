<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.util.IcpUtils" %> 
<%@ page import="com.ardais.bigr.util.WebUtils" %> 
<%@ page import="com.ardais.bigr.security.SecurityInfo" %> 
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"  prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/tld/struts-html"  prefix="html" %>
<%@ taglib uri="/tld/bigr"         prefix="bigr" %>
<%
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
%>
<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="boxBean" name="icpData" property="data"
  type="com.ardais.bigr.javabeans.BoxDto"/>

<logic:notPresent name="icpBoxAttributesRestricted">
<table width="100%"cellpadding="0" cellspacing="0" border="0" class="background">
  <tr> 
    <td> 
      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
        <tr class="white"> 
          <td valign="top">
            <%= boxBean.boxIcpHtml(securityInfo) %>
          </td>                    
        </tr>
      </table>
    </td>
  </tr>
</table>
<br/>
</logic:notPresent>
