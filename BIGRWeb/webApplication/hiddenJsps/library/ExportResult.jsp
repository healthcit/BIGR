<%!
	boolean showColumn(com.ardais.bigr.library.web.column.SampleColumn col)
	{
		return org.apache.commons.lang.BooleanUtils.negate(
			col instanceof com.ardais.bigr.library.web.column.SampleSelectIsPvIsRestrictedIsBmsIdColumn
				|| col instanceof com.ardais.bigr.library.web.column.SampleActionColumn
		);
	}
%><%
	response.setHeader("Content-Disposition", "inline;filename=\"SamplesExport.csv\"");
	response.setHeader("Content-Type", "application/csv");
	response.setHeader("Pragma", "");
	response.setHeader("Cache-Control", "");

	String txType;
	final com.ardais.bigr.library.web.form.QueryForm qform =
		(com.ardais.bigr.library.web.form.QueryForm)request.getAttribute("queryForm");
	if (qform != null)
	{
		txType = qform.getTxType();
	}
	else
	{
		final com.ardais.bigr.library.web.form.ResultsForm rform =
			(com.ardais.bigr.library.web.form.ResultsForm)request.getAttribute("resultsForm");
		if (rform != null)
		{
			txType = rform.getTxType();
		}
		else
		{
			txType = request.getParameter("txType");
		}
	}

	final com.ardais.bigr.library.web.helper.ResultsHelper helper =
		com.ardais.bigr.library.web.helper.ResultsHelper.get(txType, request);
	if (helper == null)
	{
		throw new com.ardais.bigr.api.ApiException("Request attribute " + com.ardais.bigr.library.web.helper.ResultsHelper.KEY
													   + txType + " not defined in TissueDetailTable.jsp");
	}

	synchronized (helper)
	{
		// Get data to output in the actual table (row numbers and sample data).
		final com.ardais.bigr.library.SampleViewData viewData = helper.getHelpers();

		viewData.setFirstItemIndex(helper.getCurrentChunkIndex());

		//unless directed to not include a callback method, use the positionButtonsAndDetails
		//method defined below
		String itemViewElementsStartedCallback = request.getParameter("itemViewElementsStartedCallback");
		if (!"none".equalsIgnoreCase(itemViewElementsStartedCallback))
		{
			if (com.ardais.bigr.api.ApiFunctions.isEmpty(itemViewElementsStartedCallback))
			{
				itemViewElementsStartedCallback = "positionButtonsAndDetails";
			}
			viewData.setItemViewElementsStartedCallback(itemViewElementsStartedCallback);
		}

		// Default is true.
		final boolean includeItemSelector = !("false".equalsIgnoreCase(request.getParameter("includeItemSelector")));
		viewData.setIncludeItemSelector(includeItemSelector);

		// This is used to add case-grouping lines.  Default is false.
		viewData.setGroupedByCase("true".equalsIgnoreCase(request.getParameter("groupedByCase")));

		//If there was a message passed in to be used when there are no samples
		//available for display, use it
		final String noSampleMessage = request.getParameter("noSampleMessage");
		if (!com.ardais.bigr.api.ApiFunctions.isEmpty(noSampleMessage))
		{
			viewData.setHtmlForEmptyDisplay(noSampleMessage);
		}

		final java.util.List columns = viewData.getColumns().getColumns();

		for (Object o : columns)
		{
			final com.ardais.bigr.library.web.column.SampleColumn col = (com.ardais.bigr.library.web.column.SampleColumn) o;
			if (showColumn(col))
			{
				if (showColumn(col))
				{
					if (col instanceof com.ardais.bigr.library.web.column.CompositeSampleColumn)
					{
						final com.ardais.bigr.library.web.column.CompositeSampleColumn composCol = (com.ardais.bigr.library.web.column.CompositeSampleColumn)col;
						out.write(composCol.getHeaderForCSV());
						out.write(",");
					}
					else
					{
						out.write("\"" + org.apache.commons.lang.StringUtils.defaultString(col.getRawHeaderText(), "") + "\"");
						out.write(",");
					}
				}
			}
		}

		out.write("\n");

		final java.util.Iterator rowIter = viewData.getSampleHelpers().iterator();
		for (int i = 0; rowIter.hasNext(); i++)
		{
			final com.ardais.bigr.library.web.helper.SampleSelectionHelper ssHelper =
				(com.ardais.bigr.library.web.helper.SampleSelectionHelper) rowIter.next();
			final com.ardais.bigr.library.web.column.SampleRowParams rp =
				new com.ardais.bigr.library.web.column.SampleRowParams(i, ssHelper, viewData);

			for (Object o : columns)
			{
				final com.ardais.bigr.library.web.column.SampleColumn col = (com.ardais.bigr.library.web.column.SampleColumn) o;
				if (showColumn(col))
				{
					if (col instanceof com.ardais.bigr.library.web.column.CompositeSampleColumn)
					{
						final com.ardais.bigr.library.web.column.CompositeSampleColumn composCol =
							(com.ardais.bigr.library.web.column.CompositeSampleColumn)col;
						out.write(composCol.getBodyForCSV(rp));
						out.write(",");
					}
					else
					{
						out.write("\"" + col.getRawBody(rp) + "\"");
						out.write(",");
					}
				}
			}
			out.write("\n");
		}
	}
%>