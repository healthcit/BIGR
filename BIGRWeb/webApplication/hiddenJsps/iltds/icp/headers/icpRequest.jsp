<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.text.SimpleDateFormat"%>
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
<bean:define id="requestDto" name="icpData" property="data"
  type="com.ardais.bigr.javabeans.RequestDto" />

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td colspan="6" align="center"> 
              <b>Request: <bean:write name="icpData" property="id"/></b>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Type:</b>
            </td>
            <td>
              <bean:write name="requestDto" property="type"/>
            </td>
            <td class="grey" align="right"> 
              <b>Created:</b>
            </td>
            <td colspan="3">
              <dt:format pattern="MM/dd/yyyy h:mm a" date="<%= requestDto.getCreateDate() %>"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Requested by:</b>
            </td>
            <td>
              <bean:write name="requestDto" property="requesterId"/>
              (<bean:write name="requestDto" property="requesterName"/>)
            </td>
            <td class="grey" align="right"> 
              <b>State:</b>
            </td>
            <td>
              <bean:write name="requestDto" property="status"/> -
              <bean:write name="requestDto" property="state"/>
            </td>
            <td class="grey" align="right"> 
              <b>Items:</b>
            </td>
            <td>
              <bean:write name="requestDto" property="itemCount"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

