<%@ page import="com.ardais.bigr.filter.PersonalFilterService" %>
<%@ page import="com.ardais.bigr.util.WebUtils" %>
<%@ page import="com.ardais.bigr.security.SecurityInfo" %>
<%@ page import="com.ardais.bigr.filter.domain.SamplePersonalFilter" %>
<%@ page import="com.ardais.bigr.filter.domain.PersonalFilter" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<bean:define id="filterForm" name="queryForm" property="filterForm" type="com.ardais.bigr.library.web.form.QueryPersonalFilterForm" />

<%
	final SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
	final List<PersonalFilter> userFilters =
		PersonalFilterService.SINGLETON.getUserFilters(securityInfo.getAccount(),
													   securityInfo.getUsername(),
													   SamplePersonalFilter.class);
	request.setAttribute("userFilters", userFilters);
%>

<script type="text/javascript">
	function toggleSaveButton(disabled)
	{
		$('saveBtn').disabled = disabled;
	}

	function filterChanged()
	{
		var disabled = $F('filterNameSelect') == '';
		$('loadBtn').disabled = disabled;
		$('deleteBtn').disabled = disabled;
		toggleSaveButton(true);
	}

	function filterNameChange()
	{
		var disabled = $F('filterName') == '';
		$('saveNewBtn').disabled = disabled;
	}

	function savePersonalFilter(asNew)
	{
		selectAllIds();
		var filterId = asNew ? null : $F('filterNameSelect');
		var url = '<html:rewrite page="/library/sample/saveFilter"/>' + '.do';
		var params = {'filterForm.filterName' : $('filterName').value};
		bigrBaseFunctions.getParametersFromForm(document.forms[0], params);
		params['filterForm.filterId'] = filterId;
		params['kc.queryCriteria'] = <%=com.gulfstreambio.kc.web.support.WebUtils.getJavaScriptQueryElementsToRequestParameter()%>
		var callback = function(result)
		{
			bigrBaseFunctions.updateOptionsFromJson('filterNameSelect', result.evalJSON());
			$('filterMessages').innerHTML = '<strong>Personal Filter saved successfully</strong>';
		};
		bigrBaseFunctions.doAjaxRequest(url, params, callback);
	}

	function loadPersonalFilter()
	{
		var filterId = $F('filterNameSelect');
		var url = '<html:rewrite page="/library/start"/>' + '.do?txType=TxSampSel&filterForm.filterId=' + filterId;
		document.location.href = url;
	}

	function deletePersonalFilter()
	{
		var url = '<html:rewrite page="/library/sample/deleteFilter"/>' + '.do';
		var params = {'filterId' : $F('filterNameSelect')};
		var callback = function(result)
		{
			bigrBaseFunctions.updateOptionsFromJson('filterNameSelect', result.evalJSON());
			$('filterMessages').innerHTML = '<strong>Personal Filter deleted successfully</strong>';
		};
		bigrBaseFunctions.doAjaxRequest(url, params, callback);
	}
</script>

<div id="filterMessages"></div>

<table width="420px" border="0" cellpadding="3px">
	<tr>
		<td>
			<html:text styleId="filterName"
					   title="Filter Name"
					   style="width:200px"
					   property="filterForm.filterName"
					   onchange="filterNameChange()" />
		</td>
		<td>
			<html:button styleId="saveBtn" property="" disabled="true" onclick="savePersonalFilter(false);">
				Save
			</html:button>
		</td>
		<td>
			<html:button styleId="saveNewBtn" property="" onclick="savePersonalFilter(true);">
				Save as new
			</html:button>
		</td>
	</tr>
	<tr>
		<td>
			<html:select styleId="filterNameSelect"
						 style="width:200px"
						 property="filterForm.filterId"
						 onchange="filterChanged()">
				<html:option value="">&nbsp;</html:option>
				<html:options collection="userFilters" property="id" labelProperty="name" />
			</html:select>
		</td>
		<td>
			<html:button styleId="loadBtn" property="" onclick="loadPersonalFilter();">
				Load
			</html:button>
		</td>
		<td>
			<html:button styleId="deleteBtn" property="" onclick="deletePersonalFilter();">
				Delete
			</html:button>
		</td>
	</tr>
</table>

<script type="text/javascript">
	filterChanged();
	filterNameChange();
	bigrBaseFunctions.setSelectedOption('filterNameSelect', '<%=filterForm.getFilterId()%>');
</script>

<% if (StringUtils.isNotBlank(filterForm.getFilterId())) { %>
	<script type="text/javascript">
		toggleSaveButton(false);
	</script>
<% } %>