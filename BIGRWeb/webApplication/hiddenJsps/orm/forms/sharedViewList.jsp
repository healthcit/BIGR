<%@ page import="com.ardais.bigr.javabeans.RoleDto" %>
<%@ page import="com.ardais.bigr.kc.form.def.BigrFormDefinition" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>

<bean:define id="myForm" name="resultsFormDefinitionForm" type="com.ardais.bigr.kc.web.form.def.ResultsFormDefinitionForm"/>

<script type="text/javascript">
	function shareThisViewToRole(roleId)
	{
		var elem = document.getElementById('shareView_' + roleId);
		document.getElementById('defView_' + roleId).disabled = !elem.checked;
		if (!elem.checked)
		{
			document.getElementById('defView_' + roleId).checked = false;
		}
	}
</script>

Share this view:<br/>

<table class="sharedViewsTable">
	<tr class="sharedViewsTH">
		<td>Role name</td>
		<td>Shared</td>
		<td>Default</td>
	</tr>
	<%
		final BigrFormDefinition formDefinition = myForm.getFormDefinition();
		final List<String> shared = formDefinition.getRolesSharedTo() != null
			? Arrays.asList(formDefinition.getRolesSharedTo())
			: new ArrayList<String>();
		final List<String> defaultViews = formDefinition.getDefaultSharedViews() != null
			? Arrays.asList(formDefinition.getDefaultSharedViews())
			: new ArrayList<String>();

		for(Object o : myForm.getAvailableRoles())
		{
			final RoleDto role = (RoleDto) o;
			final String roleId = role.getId();
	%>
			<tr>
				<td>
					<%=role.getName()%>
				</td>
				<td>
					<% final boolean roleIsShared = shared.contains(roleId);

					   if (roleIsShared) { %>
						<input id="shareView_<%=roleId%>" type="checkbox" onclick="shareThisViewToRole('<%=roleId%>')" name="formDefinition.rolesSharedTo" value="<%=roleId%>" checked="checked" />
					<% } else { %>
						<input id="shareView_<%=roleId%>" type="checkbox" onclick="shareThisViewToRole('<%=roleId%>')" name="formDefinition.rolesSharedTo" value="<%=roleId%>" />
					<% } %>
				</td>
				<td>
					<% if (!roleIsShared) { %>
						<input id="defView_<%=roleId%>" type="checkbox" name="formDefinition.defaultSharedViews" value="<%=roleId%>" disabled="disabled" />
					<% } else if (defaultViews.contains(roleId)) { %>
						<input id="defView_<%=roleId%>" type="checkbox" name="formDefinition.defaultSharedViews" value="<%=roleId%>" checked="checked" />
					<% } else { %>
						<input id="defView_<%=roleId%>" type="checkbox" name="formDefinition.defaultSharedViews" value="<%=roleId%>" />
					<% } %>
				</td>
			</tr>
	<%
		}
	%>
</table>