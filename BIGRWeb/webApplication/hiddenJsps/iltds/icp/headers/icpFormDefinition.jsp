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
<bean:define id="formDef" name="icpData" property="data"
  type="com.ardais.bigr.kc.form.def.BigrFormDefinition"/>

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td colspan="4" align="center"> 
              <b>Form Definition Id: <bean:write name="formDef" property="formDefinitionId"/></b>
            </td>
          </tr>
           <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Form Name:</b>
            </td>
            <td>
              <bean:write name="formDef" property="name"/>
            </td>
            <td class="grey" align="right"> 
              <b>Type:</b>
            </td>
            <td>
              <bean:write name="formDef" property="formType"/>
            </td>
          </tr>
          <tr class="white"> 
            <logic:notEmpty name="formDef" property="userName">
              <td class="grey" align="right"> 
                <b>Account:</b>
              </td>
              <td>
                <bean:write name="formDef" property="account"/>
              </td>
              <td class="grey" align="right"> 
                <b>UserName:</b>
              </td>
              <td>
                <bean:write name="formDef" property="userName"/>
              </td>
          </logic:notEmpty>
          <logic:empty name="formDef" property="userName">
            <td class="grey" align="right"> 
              <b>Account:</b>
            </td>
            <td colspan="3">
              <bean:write name="formDef" property="account"/>
            </td>
          </logic:empty>
          </tr>
          <logic:notEmpty name="formDef" property="objectType">
            <tr class="white">
	            <td class="grey" align="right">
	              <b>Object Type:</b>
	            </td>
	            <td colspan="3">
	              <bean:write name="formDef" property="objectType"/>
	            </td>
            </tr>
          </logic:notEmpty>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Enabled:</b>
            </td>
            <td colspan="3">
              <bean:write name="formDef" property="enabled"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

