package com.ardais.bigr.library.web.action;

import com.ardais.bigr.filter.PersonalFilterService;
import com.ardais.bigr.filter.domain.SamplePersonalFilter;
import com.ardais.bigr.library.web.form.QueryForm;
import com.ardais.bigr.library.web.form.QueryPersonalFilterForm;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.thoughtworks.xstream.XStream;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roman Boris
 * @since 9/12/12
 */
public class QueryPersonalFilterAction extends BigrAction
{
	@Override
	protected ActionForward doPerform(BigrActionMapping mapping, BigrActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (mapping.getParameter().equalsIgnoreCase("SAVE"))
		{
			savePersonalFilter(mapping, actionForm, request, response);
		}
		else if (mapping.getParameter().equalsIgnoreCase("DELETE"))
		{
			deletePersonalFilter(mapping, actionForm, request, response);
		}

		return mapping.findForward("success");
	}

	protected void savePersonalFilter(BigrActionMapping mapping, BigrActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final QueryForm queryForm = (QueryForm) actionForm;

		final QueryPersonalFilterForm filterForm = queryForm.getFilterForm();

		final String value = new XStream().toXML(queryForm.getFilters());

		final SamplePersonalFilter filter = new SamplePersonalFilter(WebUtils.getSecurityInfo(request));

		filter.setName(filterForm.getFilterName());
		filter.setId(filterForm.getFilterId());
		filter.setValue(value);

		final String id = PersonalFilterService.SINGLETON.savePersonalFilter(filter);

		queryForm.getFilterForm().setFilterId(id);
		request.setAttribute("personalFilterId", id);
	}

	protected void deletePersonalFilter(BigrActionMapping mapping, BigrActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		final QueryPersonalFilterForm form = (QueryPersonalFilterForm) actionForm;

		PersonalFilterService.SINGLETON.deletePersonalFilter(form.getFilterId());
	}
}
