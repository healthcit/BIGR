<%@ page language="java" errorPage="/hiddenJsps/reportError/errorReport.jsp"%>
<%@ page import="com.ardais.bigr.api.ApiProperties"%>
<%@ page import="com.ardais.bigr.javabeans.PrivilegeDto" %>
<%@ page import="com.ardais.bigr.javabeans.RoleDto" %>
<%@ page import="com.ardais.bigr.javabeans.UserDto" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="java.util.Iterator" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<%@ taglib uri="/tld/struts-bean"   prefix="bean" %>

<bean:define id="icpData" name="icpData"
  type="com.ardais.bigr.iltds.databeans.IcpData" />
<bean:define id="role" name="icpData" property="data"
  type="com.ardais.bigr.javabeans.RoleDto" />

<div align="left"> 
  <table width="100%" cellpadding="0" cellspacing="0" border="0" class="background">
    <tr> 
      <td> 
        <table width="100%" border="0" cellspacing="1" cellpadding="2" class="foreground-small">
          <tr class="green"> 
            <td colspan="6" align="center"> 
              <b>Role: <bean:write name="icpData" property="id"/></b>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Name:</b>
            </td>
            <td colspan="5">
              <bean:write name="role" property="name"/>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Privileges:</b>
            </td>
<%
				Iterator privilegeIterator = role.getPrivileges().iterator();
				boolean appendComma = false;
				boolean privilegeIncluded = false;
				StringBuffer privileges = new StringBuffer(256);
				while (privilegeIterator.hasNext()) {
				  if (appendComma) {
				    privileges.append(", ");
				  }
				  appendComma = true;
				  privileges.append(Escaper.htmlEscape(((PrivilegeDto)privilegeIterator.next()).getDescription()));
				  privilegeIncluded = true;
				}
				if (!privilegeIncluded) {
				  privileges.append("none");
				}
%>
            <td colspan="5">
              <%=privileges.toString() %>
            </td>
          </tr>
          <tr class="white"> 
            <td class="grey" align="right"> 
              <b>Users:</b>
            </td>
<%
				Iterator userIterator = role.getUsers().iterator();
				appendComma = false;
				boolean userIncluded = false;
				StringBuffer users = new StringBuffer(256);
				while (userIterator.hasNext()) {
				  if (appendComma) {
				    users.append(", ");
				  }
				  appendComma = true;
				  UserDto user = (UserDto)userIterator.next();
				  users.append(Escaper.htmlEscape(user.getFirstName() + " " + user.getLastName() + " (" + user.getUserId() + ")"));
				  userIncluded = true;
				}
				if (!userIncluded) {
				  users.append("none");
				}
%>
            <td colspan="5">
              <%=users.toString() %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

