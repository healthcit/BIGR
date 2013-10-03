<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %>
<%@ page import="com.ardais.bigr.javabeans.UserDto" %> 
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>
<%@ taglib uri="/tld/bigr" prefix="bigr" %>
<%@ taglib uri="/tld/datetime" prefix="dt" %>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="logicalRepositoryBean" name="icpData" property="data"
  type="com.ardais.bigr.iltds.databeans.LogicalRepositoryExtendedData"/>
<bean:define id="explicitUsers" name="logicalRepositoryBean"
  property="usersWithExplicitAccess" type="java.util.List"/>
<bean:define id="implicitUsers" name="logicalRepositoryBean"
  property="usersWithImplicitAccess" type="java.util.List"/>

<table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="foreground-small">
        <tr class="white">
          <td valign="top">
            <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
                    <tr class="yellow">
                      <td>
                        <div align="center"><b>Users with Explicit Access (<%= explicitUsers.size() %>)</b></div>
                      </td>
                    </tr>
                    <tr class="white">
                    	<td>
							<select multiple name="explicitUsers" style="width:100%;height:350px;">
<logic:iterate id="user" name="explicitUsers"
  type="com.ardais.bigr.javabeans.UserDto">
  							  <option value="<bean:write name="user" property="userId"/>">
  							    <bean:write name="user" property="lastName" ignore="true"/>,
  							    <bean:write name="user" property="firstName" ignore="true"/>&nbsp;
  							    (<bean:write name="user" property="accountId"/>:<bean:write name="user" property="userId"/>)
  							  </option>
</logic:iterate>
							</select>
						</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
          <td valign="top"> 
            <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
                    <tr class="yellow"> 
                      <td> 
                        <div align="center"><b>Users with Implicit Access (<%= implicitUsers.size() %>)</b></div>
                      </td>
                    </tr>
                    <tr class="white">
                      <td>
                        <div align="center">
							<select multiple name="implicitUsers" style="width:100%;height:350px">
							<logic:iterate id="user" name="implicitUsers"
							  type="com.ardais.bigr.javabeans.UserDto">
							  							  <option value="<bean:write name="user" property="userId"/>">
							  							    <bean:write name="user" property="lastName" ignore="true"/>,
							  							    <bean:write name="user" property="firstName" ignore="true"/>&nbsp;
							  							    (<bean:write name="user" property="accountId"/>:<bean:write name="user" property="userId"/>)
							  							  </option>
							</logic:iterate>
							</select>
						</div>
					  </td>
                    </tr>
                  </table>
                </td>
            </table>
          </td>
        </tr>
        </table>
      </td>
  </tr>
</table>
<br/>
