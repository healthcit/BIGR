package com.ardais.bigr.library.web.form;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roman Boris
 * @since 9/12/12
 */
public class QueryPersonalFilterForm extends BigrActionForm
{
	private static final long serialVersionUID = 3448494496677320890L;

	private String filterId;
	private String filterName;

	public QueryPersonalFilterForm()
	{
		reset();
	}

	public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request)
	{
		return null;
	}

	public String getFilterId()
	{
		return filterId;
	}

	public void setFilterId(String filterId)
	{
		this.filterId = filterId;
	}

	public String getFilterName()
	{
		return filterName;
	}

	public void setFilterName(String filterName)
	{
		this.filterName = filterName;
	}

	protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
		reset();
	}

	private void reset()
	{
		filterId = null;
		filterName = null;
	}
}
