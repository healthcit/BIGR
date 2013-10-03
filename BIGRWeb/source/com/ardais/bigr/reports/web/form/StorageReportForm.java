package com.ardais.bigr.reports.web.form;

import com.ardais.bigr.reports.storagereport.BoxInfo;
import com.ardais.bigr.reports.storagereport.StorageInfo;
import com.ardais.bigr.reports.storagereport.StorageLocationInfo;
import com.ardais.bigr.reports.storagereport.StorageReportInfo;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roman Boris
 * @since 5/17/13
 */
public class StorageReportForm
	extends BigrActionForm
{
	private static final long serialVersionUID = 1L;

	protected StorageReportInfo reportInfo;

	public StorageReportInfo getReportInfo()
	{
		return reportInfo;
	}

	public void setReportInfo(StorageReportInfo reportInfo)
	{
		this.reportInfo = reportInfo;
	}

	@Override
	protected void doReset(BigrActionMapping mapping, HttpServletRequest request)
	{
	}

	@Override
	protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request)
	{
		return null;
	}

	public StorageLocationInfo getReportTitles()
	{
		final StorageLocationInfo location = new StorageLocationInfo("Location");

		final StorageInfo freezer = location.findStorageInfo("Storage type", "Freezer");

		freezer.getBoxInfos().add(new BoxInfo("Box ID", "Box layout", "Empty slot count"));

		return location;
	}
}
