<%@ page import="com.ardais.bigr.reports.storagereport.StorageLocationInfo" %>
<%@ page import="com.ardais.bigr.reports.storagereport.StorageInfo" %>
<%@ page import="com.ardais.bigr.reports.storagereport.BoxInfo" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page language="java"%>

<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%@ taglib uri="/tld/struts-bean" prefix="bean" %>
<%@ taglib uri="/tld/struts-logic" prefix="logic" %>

<bean:define id="storageReportForm" name="storageReportForm" type="com.ardais.bigr.reports.web.form.StorageReportForm"/>
<bean:define id="reportInfo" name="storageReportForm" property="reportInfo" type="com.ardais.bigr.reports.storagereport.StorageReportInfo"/>

<style type="text/css">
	#reportContainer
	{
		width: 1032px;
	}
	#reportContainer .tableLevel1
	{
		border-width: 1px 1px 0 0;
		border-spacing: 0;
		border-style: solid;
		border-color: #656565;
	}
	#reportContainer table td
	{
		margin: 0;
		padding: 0;
		border-width: 0 0 1px 1px;
		border-style: solid;
		border-color: #656565;
	}
	#reportContainer table
	{
		table-layout: fixed;
	}
	#reportContainer .reportHeader
	{
		height: 40px;
		font-weight: bold;
		background-color: #eeeeee;
		text-align: center;
	}
	#reportContainer .reportHeader div
	{
		cursor: pointer;
	}
</style>

<%
	final List<StorageLocationInfo> locationItems = new ArrayList<StorageLocationInfo>();
	locationItems.add(storageReportForm.getReportTitles());
	locationItems.addAll(reportInfo.getStorageLocationsSorted());
%>

<div>
<div id="reportContainer">

	<table class="tableLevel1" border="0" cellpadding="0" cellspacing="0">
		<%
			boolean header = true;
			for (StorageLocationInfo info : locationItems)
			{
				final String headerClass = header ? "reportHeader" : "";
		%>
			<tr>
				<td width="200px" class="<%=headerClass%>" style="width:200px;">
					<% if (header) {%>
					<div onclick="showFilterInput(this)">
						<%=info.getLocation()%>
						<img src="/BIGR/images/filter.png" alt="[F]" style="display: none" />
					</div>
					<input type="text" style="width:100px; display: none;"
						   onkeyup="filterInput(this, 'tableLevel1', 0);" onblur="hideFilterInput(this);" />
					<% } else { %>
						<%=info.getLocation()%>
					<% } %>
				</td>
				<td>
					<table class="tableLevel2" border="0" cellpadding="0" cellspacing="0">
						<%  for (Iterator<StorageInfo> storageIterator = info.getStoragesSorted().iterator(); storageIterator.hasNext();)
							{
								final StorageInfo storage = storageIterator.next();

								final String storageBottomStyle = storageIterator.hasNext() ? "" : "border-bottom-width: 0";
						%>
							<tr>
								<td width="245px" style="width:245px; border-left-width:0; <%=storageBottomStyle%>" class="<%=headerClass%>">
									<% if (header) {%>
									<div onclick="showFilterInput(this)">
										<%=storage.getFreezer()%>
										<img src="/BIGR/images/filter.png" alt="[F]" style="display: none" />
									</div>
									<input type="text" style="width:100px; display: none;"
										   onkeyup="filterInput(this, 'tableLevel2', 0);" onblur="hideFilterInput(this);" />
									<% } else { %>
										<%=storage.getFreezer()%>
									<% } %>
								</td>
								<td width="245px" style="width:245px; <%=storageBottomStyle%>" class="<%=headerClass%>">
									<% if (header) {%>
									<div onclick="showFilterInput(this)">
										<%=storage.getStorageType()%>
										<img src="/BIGR/images/filter.png" alt="[F]" style="display: none" />
									</div>
									<input type="text" style="width:100px; display: none;"
										   onkeyup="filterInput(this, 'tableLevel2', 1);" onblur="hideFilterInput(this);" />
									<% } else { %>
										<%=storage.getStorageType()%>
									<% } %>
								</td>
								<td style="<%=storageBottomStyle%>">
									<table class="tableLevel3" border="0" cellpadding="0" cellspacing="0">
										<%  for (Iterator<BoxInfo> boxIterator = storage.getBoxInfosSorted().iterator(); boxIterator.hasNext();)
											{
												final BoxInfo box = boxIterator.next();

												final String boxBottomStyle = boxIterator.hasNext() ? "" : "border-bottom-width: 0";
										%>
											<tr>
												<td width="140px" style="width:140px; border-left-width:0; <%=boxBottomStyle%>" class="<%=headerClass%>">
													<% if (header) {%>
													<div onclick="showFilterInput(this)">
														<%=box.getBoxId()%>
														<img src="/BIGR/images/filter.png" alt="[F]" style="display: none" />
													</div>
													<input type="text" style="width:100px; display: none;"
														   onkeyup="filterInput(this, 'tableLevel3', 0);" onblur="hideFilterInput(this);" />
													<% } else { %>
														<%=box.getBoxId()%>
													<% } %>
												</td>
												<td width="100px" style="width:100px; text-align:center;<%=boxBottomStyle%>" class="<%=headerClass%>">
													<% if (header) {%>
													<div onclick="showFilterInput(this)">
														<%=box.getBoxLayout()%>
														<img src="/BIGR/images/filter.png" alt="[F]" style="display: none" />
													</div>
													<input type="text" style="width:80px; display: none;"
														   onkeyup="filterInput(this, 'tableLevel3', 1);" onblur="hideFilterInput(this);" />
													<% } else { %>
														<%=box.getBoxLayout()%>
													<% } %>
												</td>
												<td width="100px" style="width:100px; text-align:center;<%=boxBottomStyle%>" class="<%=headerClass%>">
													<% if (header) {%>
													<div onclick="showFilterInput(this)">
														<%=box.getEmptySlotCount()%>
														<img src="/BIGR/images/filter.png" alt="[F]" style="display: none" />
													</div>
													<input type="text" style="width:80px; display: none;"
														   onkeyup="filterInput(this, 'tableLevel3', 2);" onblur="hideFilterInput(this);" />
													<% } else { %>
														<%=box.getEmptySlotCount()%>
													<% } %>
												</td>
											</tr>
										<% } %>
									</table>
								</td>
							</tr>
						<% } %>
					</table>
				</td>
			</tr>
		<%
			header = false;
		}
		%>
	</table>

	<script type="text/javascript">
		//main functions
		function showFilterInput(div)
		{
			hideElement(div);
			var input = getInputElement(div.parentNode);
			showElement(input);
			input.focus();
		}
		function filterInput(input, tableClass, index)
		{
			processRowsFlags(input, tableClass, index);
			processRowsVisibility();
			removeEmptyRows();
		}
		function hideFilterInput(input)
		{
			hideElement(input);
			var parentNode = input.parentNode;
			showElement(getTextElement(parentNode));
			toggleElementVisibility(getFilterImage(parentNode),
									input.value != '');
		}

		//protected methods
		function processRowsFlags(input, tableClass, index)
		{
			var tables = getTableElementsByClassName(tableClass);
			for (var t = 0; t < tables.length; t++)
			{
				var table = tables[t];
				for (var i = 0, row; row = table.rows[i]; i++)
				{
					var cell = row.cells[index];
					if (!containsString(cell.className, 'reportHeader') && !containsString(cell.innerHTML, input.value))
					{
						addFilterHidden(row, tableClass, index);
					}
					else
					{
						removeFilterHidden(row, tableClass, index);
					}
				}
			}
		}
		function addFilterHidden(row, tableClass, index)
		{
			var composedValue = composeFilterHidden(tableClass, index);
			if (!containsString(row.appliedFilters, composedValue))
			{
				row.appliedFilters = row.appliedFilters != null
					? row.appliedFilters + composedValue
					: composedValue;
			}
		}
		function removeFilterHidden(row, tableClass, index)
		{
			var composedValue = composeFilterHidden(tableClass, index);
			if (containsString(row.appliedFilters, composedValue))
			{
				var parts = row.appliedFilters.split(composedValue);
				row.appliedFilters = parts[0] + parts[1];
			}
		}
		function composeFilterHidden(tableClass, index)
		{
			return'-' + tableClass + ':' + index + '-';
		}
		function processRowsVisibility()
		{
			processTableRowsVisibility('tableLevel1');
			processTableRowsVisibility('tableLevel2');
			processTableRowsVisibility('tableLevel3');
		}
		function processTableRowsVisibility(tableClass)
		{
			var tables = getTableElementsByClassName(tableClass);
			for (var t = 0; t < tables.length; t++)
			{
				var table = tables[t];
				for (var i = 0, row; row = table.rows[i]; i++)
				{
					toggleElementVisibility(row, row.appliedFilters == null || row.appliedFilters == '');
				}
			}
		}
		function removeEmptyRows()
		{
			removeEmptyTablesRows(getTableElementsByClassName('tableLevel2'));
			removeEmptyTablesRows(getTableElementsByClassName('tableLevel1'));
		}
		function removeEmptyTablesRows(tables)
		{
			for (var t = 0; t < tables.length; t++)
			{
				var table = tables[t];
				for (var i = 0, row; row = table.rows[i]; i++)
				{
					var innerTable = getInnerTable(row);
					if (isAllRowsHidden(innerTable))
					{
						hideElement(row);
					}
				}
			}
		}
		function isAllRowsHidden(table)
		{
			for (var i = 0, row; row = table.rows[i]; i++)
			{
				if (!isElementHidden(row))
				{
					return false;
				}
			}
			return true;
		}
		function getInnerTable(row)
		{
			return row.cells[row.cells.length - 1].children[0];
		}

		// helpers methods
		function getTableElementsByClassName(className)
		{
			var result = [];
			var allTables = document.getElementsByTagName("table");
			for (var i = 0; i < allTables.length; i++)
			{
				var table = allTables[i];
				if (table.className == className)
				{
					result.push(table);
				}
			}
			return result;
		}
		//getting element methods
		function getTextElement(container)
		{
			return getInnerElement(container, "div");
		}
		function getInputElement(container)
		{
			return getInnerElement(container, "input");
		}
		function getFilterImage(container)
		{
			return getInnerElement(container, "img");
		}
		function getInnerElement(container, tagName)
		{
			return container.getElementsByTagName(tagName)[0];
		}
		//visibility methods
		function showElement(element)
		{
			toggleElementVisibility(element, true);
		}
		function hideElement(element)
		{
			toggleElementVisibility(element, false);
		}
		function toggleElementVisibility(element, show)
		{
			element.style.display = show ? '' : 'none';
		}
		function isElementHidden(element)
		{
			return element.style.display == 'none';
		}
		//other methods
		function containsString(full, part)
		{
			return full != null && full.indexOf(part) != -1;
		}
	</script>

</div>
</div>