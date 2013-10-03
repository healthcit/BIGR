<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>
<%@ taglib uri="/tld/struts-logic"  prefix="logic" %>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td align="center"> 
<logic:equal name="icpData" property="showHistoryOnly" value="true">
              <b>Information for <bean:write name="icpData" property="id"/></b>
</logic:equal>
<logic:equal name="icpData" property="showHistoryOnly" value="false">
              <b><bean:write name="icpData" property="id"/> no longer exists.</b>
</logic:equal>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

