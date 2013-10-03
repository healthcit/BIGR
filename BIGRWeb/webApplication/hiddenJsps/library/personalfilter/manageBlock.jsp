<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-html" prefix="html" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/baseFunctions.js"></script>

<script type="text/javascript">
	function togglePersonalFilterVisible()
	{
		var link = $('toggleFilterBtn');
		var main = $('personalFilterMainBlock');
		if (main.style.display == 'none')
		{
			main.style.display = '';
			link.innerHTML = 'hide';
		}
		else
		{
			main.style.display = 'none';
			link.innerHTML = 'show';
		}
	}
</script>

<table border="0" cellspacing="0" cellpadding="0" class="foreground-small" width="100%">
	<col width="5%">
	<col width="90%">
	<col width="5%">
	<tbody>

		<tr>
			<td colspan="3">
				<table border="0" cellspacing="0" cellpadding="0" class="foreground-small" width="100%">
					<col width="90%">
					<col width="5%">
					<tbody>
						<tr>
							<td class="libraryTabTitle">
								<strong>
									Personal Filters
								</strong>
							</td>
							<td class="libraryTabTitle">
								<div id="toggleFilterBtn" onclick="togglePersonalFilterVisible();"
									 style="color: white; font-weight: bold; cursor: pointer;">
									show
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>

		<tr class="white" id="personalFilterMainBlock" style="display: none;">
			<td style="padding: 1em 0 0 0">&nbsp;</td>
			<td>

				<jsp:include page="filters.jsp" />

			</td>
			<td>&nbsp;</td>
		</tr>

	</tbody>
</table>